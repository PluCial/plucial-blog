package com.appspot.plucial.controller.pub;

import java.util.ArrayList;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class ActivityController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        String activityId = asString("activityId");
        ActivityModel activityModel = ActivityService.getActivity(activityId);

        ArrayList<ActivityModel> activityList = new ArrayList<ActivityModel>();
        if(activityModel != null) {
            activityList.add(activityModel);
        }


        // リクエストスコープの設定
        requestScope("activityList", activityList);
        if(activityModel != null) {
            if(activityModel.getTitleString() != null) {
                requestScope("pageTitle", activityModel.getTitleString() + " - " + acsessUserModel.getDisplayName());

            }else if(activityModel.isAttachmentsFlg() && activityModel.getAttachmentsDisplayNameString() != null) {
                requestScope("pageTitle", activityModel.getAttachmentsDisplayNameString() + " - " + acsessUserModel.getDisplayName());

            }else {
                requestScope("pageTitle", "Google+での投稿 - " + acsessUserModel.getDisplayName());
            }
        }

        return forward("/responsive/activity_only.jsp");
    }
}
