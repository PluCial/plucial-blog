package com.appspot.plucial.controller.task;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.UserService;
import com.appspot.plucial.utils.Utils;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.urlshortener.Urlshortener;
import com.google.api.services.urlshortener.model.Url;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class TwitterPostTaskController extends Controller {

    private static final Logger logger = Logger.getLogger(TwitterPostTaskController.class.getName());

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public Navigation run() throws Exception {

        UserModel userModel = null;

        try{
            userModel = getUser();
        }catch(Exception e) {
            return null;
        };

        // タスクは成功するまで実行されるため、失敗時は例外をキャッチして再実行をさせない
        try{
            // アクティビティの取得
            String activityId = asString("activityId");

            ActivityModel activityModel = ActivityService.getActivity(activityId);
            if(activityModel == null) return null;

            // 短縮URLの取得
            String shortUrl = getShortUrl(userModel, activityModel);

            // Twitter メッセージの作成
            String msg = getMessage(activityModel, shortUrl);

            // POST
            if(activityModel.getAttachmentsType() != null
                    && (activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)
                          || activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM))
                    && activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST)) {

                // 自分が投稿した写真付きの場合もしくアルバム
                imageUpload(userModel, msg, activityModel.getAttachmentsImageUrlString());


            }else {
                postMessage(userModel, msg);
            }

        }catch(Exception e) {
            logger.severe(e.toString());
        }

        return null;
    }


    /**
     * UserModelの取得
     * @return
     * @throws Exception
     */
    public UserModel getUser() throws Exception {

        String userId = asString("user");

        UserModel userModel = UserService.getOrNull(userId);

        if(userModel == null) throw new Exception();

        return userModel;
    }

    /**
     * 短縮URLの取得
     * @return
     * @throws Exception
     */
    public String getShortUrl(UserModel userModel, ActivityModel activityModel) throws Exception {

        // アプリ承認オブジェクトの生成
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setRefreshToken(userModel.getRefreshToken());

        // Urlshortener オブジェクトの生成
        Urlshortener.Builder builder = new Urlshortener.Builder(TRANSPORT, JSON_FACTORY, credential);
        builder.setApplicationName(Constants.GOOGLE_APPLICATION_NAME);
        Urlshortener urlshortener = builder.build();

        // URLの設定
        Url url = new Url();
        url.setLongUrl("http://plucial.com/u/" + userModel.getKey().getName() + "/a/" +activityModel.getKey().getName());

        // 短縮URLの発行
        url = urlshortener.url().insert(url).execute();

        return url.getId();
    }

    /**
     * Twitterの取得
     * @throws IOException
     */
    private String getMessage(ActivityModel activityModel, String shortUrl) throws Exception {

        String twitterMessage = null;

        // 再共有の場合
        if(activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
            // 追加コメントが存在しない場合
            if(activityModel.getAnnotationString() == null) {

                // タイトルが存在しない場合
                if(activityModel.getTitleString() == null) {
                    return "[ #PluCial " + shortUrl + " ]";

                 // タイトル存在する場合
                }else {
                    twitterMessage = activityModel.getTitleString();
                }

             // 追加コメントが存在する場合
            }else {
                twitterMessage = activityModel.getAnnotationString();
            }

         // 通常投稿の場合
        }else {
            // タイトルが存在しない場合
            if(activityModel.getTitleString() == null) {

                // 添付コンテンツのタイトルが存在しない場合
                if(activityModel.getAttachmentsDisplayNameString() == null) {
                    return "[ #PluCial " + shortUrl + " ]";

                 // 添付コンテンツのタイトルが存在する場合
                }else {
                    twitterMessage = activityModel.getAttachmentsDisplayNameString();
                }

             // タイトル存在する場合
            }else {
                twitterMessage = activityModel.getTitleString();
            }

        }

        if(twitterMessage == null) {
            return "[ #PluCial " + shortUrl + " ]";
        }else {
            // リンクタグを削除
            twitterMessage = Utils.removeLinkTags(twitterMessage);
            // 太文字を削除
            twitterMessage = Utils.removeBTags(twitterMessage);

            // 改行の変換
            twitterMessage = twitterMessage.replace("<br>", "\n");
            // spanタグを削除
            twitterMessage = Utils.removeSpanTags(twitterMessage);

        }

        String lastString = ("\n[ #PluCial " + shortUrl + " ]");
        int limit_num = 100;

        // 画像の場合はTwitterメディア分の23文字を減らす
        if(activityModel.getAttachmentsType() != null
                && (activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)
                        || activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM))
                && activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST)) {
            limit_num = limit_num - 23;
        }

        // ドメイン分 の 23文字を削減
        int domainCount = getDomainCount(twitterMessage);
        limit_num = limit_num - (domainCount * 23);
        // URL複数含まれる投稿はマイナスになる可能性がある
        if(limit_num < 0) return "[ #PluCial " + shortUrl + " ]";

        // タイトルの文字数が問題ない場合
        if(twitterMessage.length() <= limit_num) return twitterMessage + lastString;

        // タイトルの文字数が制限オーバーの場合
        String message = twitterMessage.substring(0, limit_num - 3) + "...";
        return message + lastString;
    }

    /**
     * Twitter Post
     * @param userModel
     * @param msg
     * @return
     */
    private void postMessage(UserModel userModel, String msg) throws Exception {

        System.out.println(msg);

        // 承認情報の生成
        ConfigurationBuilder cb = getConfigurationBuilder(userModel);

        // Titterオブジェクトの生成
        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        Twitter twitter = twitterFactory.getInstance();

        twitter.updateStatus(msg);
    }

    /**
     * Twitter Post
     * @param userModel
     * @param msg
     * @return
     */
    private void imageUpload(UserModel userModel, String msg, String imagePath) throws Exception {

        URLFetchService fetchService =
                URLFetchServiceFactory.getURLFetchService();
        HTTPResponse fetchResponse = fetchService.fetch(new URL(imagePath));

        InputStream inputStream = new ByteArrayInputStream(fetchResponse.getContent());

        // 承認情報の生成
        ConfigurationBuilder cb = getConfigurationBuilder(userModel);

        Configuration conf = cb.setMediaProvider(MediaProvider.TWITTER.name()).build();

        // ImageUploadオブジェクトの生成
        ImageUpload imageUpload = new ImageUploadFactory(conf).getInstance();

        imageUpload.upload("plucial-image", inputStream, msg);
    }

    /**
     * 承認情報の生成
     * @param userModel
     * @param msg
     * @return
     */
    private ConfigurationBuilder getConfigurationBuilder(UserModel userModel) throws Exception {

        // 承認情報の生成
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(Constants.TWITTER_APP_API_KEY)
        .setOAuthConsumerSecret(Constants.TWITTER_APP_API_SECRET)
        .setOAuthAccessToken(userModel.getTwitterAccessToken())
        .setOAuthAccessTokenSecret(userModel.getTwitterTokenSecret());

        return cb;
    }

    private int getDomainCount(String msg) {

        String domainPattern = "[a-z0-9\\-\\.]+\\.(com|org|biz|net|mil|edu|(co\\.[a-z].))";

            Pattern patt = Pattern.compile(domainPattern);
            Matcher matcher = patt.matcher(msg);

            int count = 0;

            while(matcher.find()){
                count++;
             }

            return count;
    }


}
