package com.appspot.plucial.controller.task;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.exception.DataInvalidException;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.UserService;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;

public class AllActivitysBotTaskController extends Controller {

    private static final Logger logger = Logger.getLogger(AllActivitysBotTaskController.class.getName());

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
            getActivitys(userModel);

        }catch(TokenResponseException te) {

            logger.severe("User Token error:" + userModel.getKey().getId());

        }catch(Exception e) {
            logger.severe(e.toString());

        }finally {
            // Task実行完了に変更
            userModel.setActivityBotPerformingFlg(false);
            userModel.setInputActivityLastFinishFlg(true);
            UserService.put(userModel);
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

        while (activities != null) {

            for (Activity activity : activities) {

                ActivityModel oldModel = ActivityService.getActivity(activity.getId());

                try {
                    if(oldModel == null) {
                        ActivityService.putActivity(activity, userModel);
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
        }

    }
}
