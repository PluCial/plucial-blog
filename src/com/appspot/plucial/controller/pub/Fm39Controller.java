package com.appspot.plucial.controller.pub;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class Fm39Controller extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {


        S3QueryResultList<ActivityModel> activityModelList = ActivityService.getUserActivitysListByPostType(acsessUserModel, Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST, null);

        ArrayList<ActivityModel> activityModelLimt3List = new ArrayList<ActivityModel>();
        int i = 0;
        for(ActivityModel activityModel: activityModelList) {
            i++;
            if(i > 3) {
                break;
            }

            activityModelLimt3List.add(activityModel);
        }

        // リクエストスコープの設定
        requestScope("activityList", activityModelLimt3List);
        requestScope("pageTitle", "フォローして頂きありがとうございます。改めて自己紹介をさせてください。- " + acsessUserModel.getDisplayName());
        requestScope("pageDescription", "これから宜しくお願いします。");

        return forward("/responsive/fm39.jsp");
    }
}
