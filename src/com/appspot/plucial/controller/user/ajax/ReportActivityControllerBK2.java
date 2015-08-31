package com.appspot.plucial.controller.user.ajax;

import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.exception.DataInvalidException;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;

public class ReportActivityControllerBK2 extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        // トークン情報の取得（アクセストークン、リフレッシュトークン・・・）
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
//        .setAccessToken(userModel.getAccessToken())
        .setRefreshToken(userModel.getRefreshToken());

        System.out.println("Old AccessToken:" +  userModel.getAccessToken());

        // リフレッシュトークンを元にアクセストークンを更新
        credential.refreshToken();

        System.out.println("new AccessToken:" +  credential.getAccessToken());

        Plus plus = new Plus(TRANSPORT, JSON_FACTORY, credential);
        Plus.Activities.List listActivities = plus.activities().list("me", "public").setFields(Constants.TARGET_ACTIVITY_FIELDS);

        // 最初のページのリクエストを実行します
        ActivityFeed activityFeed = listActivities.execute();

        // リクエストのラップを解除し、必要な部分を抽出します
        List<Activity> activities = activityFeed.getItems();

        // 返却用カウント数
        int activityCount = 0;


        try {
            while (activities != null) {
                for (Activity activity : activities) {

                        ActivityModel oldModel = ActivityService.getActivity(activity.getId());

                        // 新規アクティビティ
                        if(oldModel == null) {
                            // アクティビティを追加
                            try {
                                ActivityService.putActivity(activity, userModel);
                                // カウントの加算
                                activityCount++;

                            }catch (Exception e) {};

                        }else {
                            // 最後まで取り込みが完了しているユーザーで、
                            // 存在するアクティビティに到着した場合は処理を終了する。
                            if(userModel.isInputActivityLastFinishFlg()) {
                                throw new DataInvalidException();
                            }
                        }
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
            }

            // 初めて、全取り込みを行ったユーザーの全取り込み完了フラグを更新
            if(!userModel.isInputActivityLastFinishFlg()) {
                userModel.setInputActivityLastFinishFlg(true);
                UserService.put(userModel);
            }

        }catch (DataInvalidException ex) {
            // 何もしない。

        }catch (Exception e) {
            // 初めて、全取り込みを行ったユーザーの全取り込み完了フラグを更新
            if(!userModel.isInputActivityLastFinishFlg()) {
                userModel.setInputActivityLastFinishFlg(true);
                UserService.put(userModel);
            }

            e.printStackTrace();
        };

        requestScope("activityCount", String.valueOf(activityCount));

        if(isSmartPhone()) {
            return forward("/user_sp/ajax/report_activity.jsp");
        }else {
            return forward("/user/ajax/report_activity.jsp");
        }
    }
}
