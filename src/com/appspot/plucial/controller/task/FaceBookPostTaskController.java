package com.appspot.plucial.controller.task;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.UserService;
import com.appspot.plucial.utils.Utils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Media;
import facebook4j.PhotoUpdate;
import facebook4j.auth.AccessToken;

public class FaceBookPostTaskController extends Controller {

    private static final Logger logger = Logger.getLogger(FaceBookPostTaskController.class.getName());

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

            // 再共有の場合は投稿対象外にする
            if(activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
                return null;
            }

            // Facebook オブジェクトの取得
            Facebook facebook = getFaceBookObject(userModel);


            // メッセージの作成
            String msg = getMessage(activityModel);

            // POST
            if(!activityModel.isAttachmentsFlg()) {
                // 添付がない場合
                if(msg != null) {
                    facebook.postStatusMessage(msg);
                }

            }else if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)) {
                // リンク付き投稿の場合
                URL attachmentsUrl = new URL(activityModel.getAttachmentsUrlString());

                if(msg == null) {
                    facebook.postLink(attachmentsUrl);

                }else {
                    facebook.postLink(attachmentsUrl, msg);
                }

            }else if(activityModel.getAttachmentsType() != null
                    && (activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)
                    || activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM))
                      && activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST)) {
                // 写真付き投稿の場合

                Media media = getPhotoMedia(activityModel.getAttachmentsImageUrlString());
                PhotoUpdate photoUpdate = new PhotoUpdate(media);

                if(msg != null) {
                    photoUpdate.setMessage(msg);
                }

                facebook.postPhoto(photoUpdate);


//            }else if(activityModel.getAttachmentsType() != null
//                    &activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM)
//                      && activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST)) {
//                // アルバム投稿の場合
//
//                AlbumUpdate albumUpdate = new AlbumUpdate("PluCial");
//
//                // メッセージを追加
//                if(msg != null) {
//                    albumUpdate.setMessage(msg);
//                }
//
//                // アルバムを作成
//                String albumId = facebook.createAlbum(albumUpdate);
//
//                // アルバムの写真リストを取得
//                List<AlbumModel> albmList = activityModel.getAlbumModelListRef().getModelList();
//
//                // アルバムに写真の追加
//                for(AlbumModel albumModel: albmList) {
//                    Media media = getPhotoMedia(Utils.changeAlbumUrl(albumModel));
//                    facebook.addAlbumPhoto(albumId, media);
//                }


            }else if(activityModel.getAttachmentsType() != null
                    && activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)) {
                // 動画の場合
                URL embedUrl = new URL(activityModel.getEmbedUrlString());

                if(msg == null) {
                    facebook.postLink(embedUrl);

                }else {
                    facebook.postLink(embedUrl, msg);
                }
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
     * メッセージの取得
     * @throws IOException
     */
    private String getMessage(ActivityModel activityModel) throws Exception {

        String message = null;

        // 再共有の場合
        if(activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
            message =  activityModel.getAnnotationString();

         // 通常投稿の場合
        }else {
            message =  activityModel.getContentString();

        }

        if(message != null) {
            // 改行の変換(必ず先に行う)
            message = message.replace("<br />", "\n");
            
            // 改行の変換(必ず先に行う)
            message = message.replace("&amp;", "");

            // リンクタグを削除
            message = Utils.removeLinkTags(message);
            // 太文字を削除
            message = Utils.removeBTags(message);
            // spanタグを削除
            message = Utils.removeSpanTags(message);
        }

        System.out.println(message);

        return message;
    }



    /**
     * 承認情報の生成
     * @param userModel
     * @param msg
     * @return
     */
    private Facebook getFaceBookObject(UserModel userModel) throws Exception {

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(Constants.FACEBOOK_APP_API_KEY, Constants.FACEBOOK_APP_API_SECRET);
        facebook.setOAuthPermissions(Constants.FACEBOOK_APP_API_PERMISSIONS);
        facebook.setOAuthAccessToken(new AccessToken(userModel.getFacebookAccessTokenString()));

        return facebook;
    }

    private Media getPhotoMedia(String imageUrl) throws MalformedURLException, IOException {

        URLFetchService fetchService =
                URLFetchServiceFactory.getURLFetchService();
        HTTPResponse fetchResponse = fetchService.fetch(new URL(imageUrl));

        InputStream inputStream = new ByteArrayInputStream(fetchResponse.getContent());

        return new Media("photo by plucial", inputStream);

    }

}
