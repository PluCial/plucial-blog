package com.appspot.plucial.controller.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.exception.DataInvalidException;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.AlbumService;
import com.appspot.plucial.service.UserService;
import com.appspot.plucial.service.UserUrlsService;
import com.appspot.plucial.utils.Utils;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments.Thumbnails;
import com.google.api.services.plus.model.ActivityFeed;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.Urls;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

public class ActivitysBotTaskController extends Controller {

    private static final Logger logger = Logger.getLogger(ActivitysBotTaskController.class.getName());

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public Navigation run() throws Exception {

        UserModel userModel = null;

        try{
            userModel = getUser();
        }catch(Exception e) {
            e.printStackTrace();
        };

        // タスクは成功するまで実行されるため、失敗時は例外をキャッチして再実行をさせない
        try{
            getActivitys(userModel);

        }catch(TokenResponseException te) {

            logger.severe("User Token error:" + userModel.getKey().getName());
            logger.severe(te.getContent());

            // 利用者がGoogle+ 上で権限を取り消しした場合リフレッシュトークンが無効になる
            // トークンエラーが発生した場合は利用者のリフレッシュトークンをNULLに変更
//            userModel.setRefreshToken(null);
//            UserService.put(userModel);

        }catch(Exception e) {
            logger.severe(e.toString());
            e.printStackTrace();
        }
//        }finally {
//            // Task実行完了に変更
//            userModel.setActivityBotPerformingFlg(false);
//            UserService.put(userModel);
//        }

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

        try {
            updateUserInfo(userModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userModel;
    }

    /**
     * アクティビティの取得
     * @throws IOException
     */
    private void getActivitys(UserModel userModel) throws Exception {

        // トークン情報の作成
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setRefreshToken(userModel.getRefreshToken());

        // リフレッシュトークンを元にアクセストークンを更新
        credential.refreshToken();

        // Google+ API の設定（デフォルト1回につき20件）
        Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(Constants.GOOGLE_APPLICATION_NAME)
        .build();


        Plus.Activities.List listActivities = plus.activities().list("me", "public").setFields(Constants.TARGET_ACTIVITY_FIELDS);

        // 最初のページのリクエストを実行します
        ActivityFeed activityFeed = listActivities.execute();

        // リクエストのラップを解除し、必要な部分を抽出します
        List<Activity> activities = activityFeed.getItems();

        // Twitter対象リスト
        List<String> activityIdList = new ArrayList<String>();
        // Facebook対象リスト
        List<String> facebookActivityIdList = new ArrayList<String>();
        // Evernote対象リスト
        List<String> evernoteActivityIdList = new ArrayList<String>();

        // Activityの保存
        for (Activity activity : activities) {

            ActivityModel oldModel = ActivityService.getActivity(activity.getId());

            try {
                // 新しい投稿の場合
                if(oldModel == null) {
                    // 新しいアクティビティを保存
                    ActivityModel newActivityModel = ActivityService.putActivity(activity, userModel);

                    // アルバム内の画像を保存
                    List<Attachments> attachmentsList = activity.getObject().getAttachments();
                    if(attachmentsList != null
                            && attachmentsList.size() > 0) {

                        Attachments attachment = attachmentsList.get(0);
                        if(attachment.getThumbnails() != null && attachment.getThumbnails().size() > 0) {

                            for(Thumbnails thumbnails: attachment.getThumbnails()) {
                                if(thumbnails != null && thumbnails.getImage() != null) {
                                    AlbumService.put(newActivityModel, thumbnails.getUrl(), thumbnails.getImage().getUrl(), thumbnails.getImage().getHeight(), thumbnails.getImage().getWidth());
                                }
                            }
                        }
                    }

                    // 他のSNSへの転送
                    if(!Utils.isNoShare(newActivityModel)) {
                        // twitterへPOST対象リストを作成
                        if(Utils.isTwitterRepost(userModel, newActivityModel)) {
                            activityIdList.add(activity.getId());
                        }

                        // facebookへPOST対象リストを作成
                        if(Utils.isFacebookRepost(userModel, newActivityModel)) {
                            facebookActivityIdList.add(activity.getId());
                        }

                        // EvernoteへPOST対象リストを作成
                        if(Utils.isEvernoteRepost(userModel, newActivityModel)) {
                            evernoteActivityIdList.add(activity.getId());
                        }
                    }

                }else {
                    // 存在した場合、繰り返しを終了する
                    break;
                }

            }catch (DataInvalidException e) {
                e.printStackTrace();
            };
        }


        // Activity Id リストを逆順でTwitterにPOST
        for(int i=activityIdList.size(); i > 0; i--) {
            Queue queue = QueueFactory.getQueue("twitter-queue-group" + userModel.getGroup());
            queue.add(Builder.withUrl("/task/twitterPostTask").param("user", userModel.getKey().getName()).param("activityId", activityIdList.get(i - 1)));
        }

        // Activity Id リストを逆順でFaceBookにPOST
        for(int i=facebookActivityIdList.size(); i > 0; i--) {
            Queue queue = QueueFactory.getQueue("facebook-queue-group" + userModel.getGroup());
            queue.add(Builder.withUrl("/task/faceBookPostTask").param("user", userModel.getKey().getName()).param("activityId", facebookActivityIdList.get(i - 1)));
        }

        // Activity Id リストを逆順でEvernoteにPOST
        for(int i=evernoteActivityIdList.size(); i > 0; i--) {
            Queue queue = QueueFactory.getQueue("evernote-queue-group" + userModel.getGroup());
            queue.add(Builder.withUrl("/task/evernotePostTask").param("user", userModel.getKey().getName()).param("activityId", evernoteActivityIdList.get(i - 1)));
        }
    }

    /**
     * ユーザー情報の更新チェック・更新
     * @param userModel
     * @return
     * @throws IOException
     * @throws DataInvalidException
     */
    private static boolean updateUserInfo(UserModel userModel) throws IOException, DataInvalidException {

        // The date of 30 days ago
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, Constants.CHECK_AND_UPDATE_INFO_LIMIT_DAYS);

        // When it passes on the 30th
        if(userModel.getUpdateCheckDate() == null || userModel.getUpdateCheckDate().before(cal.getTime())) {

//            logger.info("Start user Info Check!");

            // GoogleCredential
            GoogleCredential credential = new GoogleCredential.Builder()
            .setJsonFactory(JSON_FACTORY)
            .setTransport(TRANSPORT)
            .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
            .setRefreshToken(userModel.getRefreshToken());

            // リフレッシュトークンを元にアクセストークンを更新
            credential.refreshToken();

            // Plus Object
            Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(Constants.GOOGLE_APPLICATION_NAME)
            .build();

            Person person = null;
            try {
                person = plus.people().get("me").execute();

            } catch (GoogleJsonResponseException e) {
                // When deleted
                if(e.getStatusCode() == 404) {
//                    UserService.delete(userModel);
                    logger.info("Delete user!");

                    return true;
                }

                e.printStackTrace();
            }


            if(person != null) {

//                logger.info("user Info Update!");

                // 表示名
                if(person.getDisplayName() != null && !person.getDisplayName().isEmpty()) {
                    userModel.setDisplayName(person.getDisplayName());
                }

                // 写真
                if(person.getImage() != null && !person.getImage().isEmpty()) {
                    userModel.setImageUrl(new Text(person.getImage().getUrl()));
                }

                // キャッチ
                if(person.getTagline() != null && !person.getTagline().isEmpty()) {
                    userModel.setTagline(new Text(person.getTagline()));
                }

                // 特技
                if(person.getBraggingRights() != null && !person.getBraggingRights().isEmpty()) {
                    userModel.setBraggingRights(new Text(person.getBraggingRights()));
                }

                // About Me
                if(person.getAboutMe() != null && !person.getAboutMe().isEmpty()) {
                    userModel.setAboutMe(new Text(person.getAboutMe()));
                }

                // 背景画像url
                if(person.getCover() != null
                        && person.getCover().getCoverPhoto() != null
                        && person.getCover().getCoverPhoto().getUrl() != null
                        && !person.getCover().getCoverPhoto().getUrl().isEmpty()) {
                    userModel.setCoverPhotoUrl(new Text(person.getCover().getCoverPhoto().getUrl()));
                }

                userModel.setUpdateCheckDate(new Date());
                UserService.put(userModel);

                // URLSを削除して再登録
                UserUrlsService.deleteAll(userModel);
                if(person.getUrls() != null && person.getUrls().size() > 0) {
                    List<Urls> urlsList = person.getUrls();

                    for(Urls urls: urlsList) {
                        UserUrlsService.put(userModel, urls.getValue(), urls.getType(), urls.getLabel());
                    }
                }

                return true;
            }

            // Update UpdateCheckDate
            userModel.setUpdateCheckDate(new Date());
            UserService.put(userModel);
        }

//        logger.info("Not 30 days");

        return false;
    }
}
