package com.appspot.plucial.controller.account.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.exception.DataInvalidException;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.AlbumService;
import com.appspot.plucial.utils.Utils;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments.Thumbnails;
import com.google.api.services.plus.model.ActivityFeed;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

/**
 * ユーザー追加コントローラー
 * @author takahara
 *
 */
public class GetNewActivitysController extends BaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        getActivitys(userModel);

        return null;
    }


    /**
     * アクティビティの取得
     * @throws IOException
     */
    private void getActivitys(UserModel userModel) throws Exception {

        int limit =  asString("num") == null || asString("num").equals("") ? 1 : Integer.valueOf(asString("num"));

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
        List<String> twitterActivityIdList = new ArrayList<String>();
        // Facebook対象リスト
        List<String> facebookActivityIdList = new ArrayList<String>();
        // Evernote対象リスト
        List<String> evernoteActivityIdList = new ArrayList<String>();

        int count = 1;

        while (activities != null && count <= limit) {

            for (Activity activity : activities) {

                ActivityModel oldModel = ActivityService.getActivity(activity.getId());

                try {
                    if(oldModel == null) {
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
                                twitterActivityIdList.add(activity.getId());
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
                    }

                }catch (DataInvalidException e) {};
            }

            // 次のページのトークンが null になると、最終ページに到達したことがわかります。
            // その場合は break。
            if (activityFeed.getNextPageToken() == null) {
                break;
            }

            // 次のページのアクティビティをリクエストする準備をします
            listActivities.setPageToken(activityFeed.getNextPageToken());

            // 次のページのリクエストを実行して処理します
            activityFeed = listActivities.execute();
            activities = activityFeed.getItems();

            // API呼び出しカウントを加算
            count++;
        }


        // Activity Id リストを逆順でTwitterにPOST
        for(int i=twitterActivityIdList.size(); i > 0; i--) {
            Queue queue = QueueFactory.getQueue("twitter-queue-group" + userModel.getGroup());
            queue.add(Builder.withUrl("/task/twitterPostTask").param("user", userModel.getKey().getName()).param("activityId", twitterActivityIdList.get(i - 1)));
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
}
