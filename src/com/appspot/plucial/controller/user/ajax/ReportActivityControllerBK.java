package com.appspot.plucial.controller.user.ajax;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.exception.DataInvalidException;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.utils.Utils;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;

public class ReportActivityControllerBK extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        // トークン情報の取得（アクセストークン、リフレッシュトークン・・・）
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setAccessToken(userModel.getAccessToken())
        .setRefreshToken(userModel.getRefreshToken());

        Plus plus = new Plus(TRANSPORT, JSON_FACTORY, credential);
        Plus.Activities.List listActivities = plus.activities().list("me", "public").setFields(Constants.TARGET_ACTIVITY_FIELDS);

        // 最初のページのリクエストを実行します
        ActivityFeed activityFeed = listActivities.execute();

        // リクエストのラップを解除し、必要な部分を抽出します
        List<Activity> activities = activityFeed.getItems();

        // 返却用リスト
        List<ActivityModel> activityModelList = new ArrayList<ActivityModel>();


        try {
            while (activities != null) {
                for (Activity activity : activities) {


                    Date baseDate = new Date();

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(activity.getPublished().getValue());
                    calendar.add(Calendar.HOUR, 9);

                    Date published = calendar.getTime();

//                    if(activity.getId().equals("z13nyfjxioabg33uo22qyvnrbr3tghqac04")) {
//                        System.out.println("baseDate:" + baseDate + "|" + "published:" + published + "|" + activity.getPublished().toStringRfc3339());
//                        System.out.println(Utils.differenceDays(baseDate, published));
//                    }

                    // 今日のアクティビティのみを取込む
                    if(Utils.differenceDays(baseDate, published) != 0) {
                        throw new DataInvalidException();
                    }

                    try {

                        ActivityModel oldModel = ActivityService.getActivity(activity.getId());

                        // 新規アクティビティ もしくは 更新されている場合
                        if(oldModel == null || oldModel.getUpdated().before(new Date(activity.getUpdated().getValue()))) {
                            ActivityModel activityModel = ActivityService.putActivity(activity, userModel);
                            activityModelList.add(activityModel);
                        }

                    }catch (Exception e) {};
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
        }catch (DataInvalidException e) {};

        requestScope("activityList", activityModelList);
        requestScope("userModel", userModel);

        if(isSmartPhone()) {
            return forward("/user_sp/ajax/report_activity.jsp");
        }else {
            return forward("/user/ajax/report_activity.jsp");
        }
    }
}
