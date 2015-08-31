package com.appspot.plucial.controller;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.util.StringUtil;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.UserService;

public class UserRssController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        String type = asString("type");
        String userId = asString("user");

        // ユーザー情報の取得
        UserModel userModel = UserService.getOrNull(userId);
        if(userModel == null) return null;

        // 対象のアクティビティリストを取得
        S3QueryResultList<ActivityModel> activityModelList = null;

        if(StringUtil.isEmpty(type)) {
            activityModelList = ActivityService.getActivitysByUser(userModel, null);

        }else {
            if(type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST) || type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {

                activityModelList = ActivityService.getUserActivitysListByPostType(userModel, type, null);

            }else if(type.equals(Constants.GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE)) {

                activityModelList = ActivityService.getUserNoteActivitys(userModel, null);

            }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)
                    || type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)
                    || type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)) {

                activityModelList = ActivityService.getUserActivitysByAttachmentsType(userModel, type, null);

            }
        }

        // リクエストスコープの設定
        requestScope("activityList", activityModelList);
        requestScope("userModel", userModel);
        requestScope("type", type);

        setBaseURI(userModel);

        return forward("/user_rss.jsp");
    }


    protected void setBaseURI(UserModel userModel) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        String baseURL = requestURL.replace(requestURI, "") + "/";

        requestScope("baseURL", baseURL);
    }
}
