package com.appspot.plucial.controller.pub;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class GetActivitysByTypeController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        String type = asString("type");

        requestScope("contentsType", type);

        // 対象のアクティビティリストを取得
        S3QueryResultList<ActivityModel> activityModelList = null;
        if(type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST) || type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {

            activityModelList = ActivityService.getUserActivitysListByPostType(acsessUserModel, type, null);

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE)) {

            activityModelList = ActivityService.getUserNoteActivitys(acsessUserModel, null);

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)
                || type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)
                || type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)) {

            activityModelList = ActivityService.getUserActivitysByAttachmentsType(acsessUserModel, type, null);

        }

        // リクエストスコープの設定
        requestScope("activityList", activityModelList);
        requestScope("cursor", activityModelList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(activityModelList.hasNext()));

        // タイトルの設定
        String typeName = "";
        if(type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST)) {
            typeName = "通常投稿";
        }else if(type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
            typeName = "再共有した投稿";

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE)) {
            typeName = "テキスト投稿";

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)) {
            typeName = "写真付き投稿";

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)) {
            typeName = "動画付き付き投稿";

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)) {
            typeName = "リンク付き投稿";

        }

        requestScope("pageTitle", acsessUserModel.getDisplayName() + " の" + typeName);
        requestScope("pageDescription", acsessUserModel.getDisplayName() + " の" + typeName);

        setThisURI(acsessUserModel, type);

        return forward("/responsive/activity.jsp");
    }

    protected void setThisURI(UserModel userModel, String type) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        String baseURL = requestURL.replace(requestURI, "") + "/";

        requestScope("thisPageUrl", baseURL + "ut/" + userModel.getKey().getName() + "/" + type);
    }
}
