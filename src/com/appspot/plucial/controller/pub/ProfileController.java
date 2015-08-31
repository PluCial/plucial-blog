package com.appspot.plucial.controller.pub;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class ProfileController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        requestScope("contentsType", Constants.GOOGLE_ACTIVITY_TYPE_ALL);

        S3QueryResultList<ActivityModel> activityModelList = ActivityService.getActivitysByUser(acsessUserModel, null);

        // リクエストスコープの設定
        requestScope("activityList", activityModelList);
        requestScope("cursor", activityModelList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(activityModelList.hasNext()));

        requestScope("pageTitle", acsessUserModel.getDisplayName() + " の投稿");
        requestScope("pageDescription", acsessUserModel.getDisplayName() + " の投稿");

        setThisURI(acsessUserModel);

        return forward("/responsive/activity.jsp");
    }

    protected void setThisURI(UserModel userModel) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        String baseURL = requestURL.replace(requestURI, "") + "/";

        requestScope("thisPageUrl", baseURL + "u/" + userModel.getKey().getName() + "/");
    }
}
