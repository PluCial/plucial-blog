package com.appspot.plucial.controller.pub.ajax;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.Constants;
import com.appspot.plucial.controller.pub.PubBaseController;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class GetMoreActivitysController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        String type = asString("type");
        String cursor = asString("cursor");
        String userId = asString("user");

        requestScope("contentsType", type);
        requestScope("userId", userId);

        // 対象のアクティビティリストを取得
        S3QueryResultList<ActivityModel> activityModelList = null;

        if(type.equals(Constants.GOOGLE_ACTIVITY_TYPE_ALL)) {
            activityModelList = ActivityService.getActivitysByUser(acsessUserModel, cursor);

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST) || type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {

            activityModelList = ActivityService.getUserActivitysListByPostType(acsessUserModel, type, cursor);

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE)) {

            activityModelList = ActivityService.getUserNoteActivitys(acsessUserModel, cursor);

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)
                || type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)
                || type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)) {

            activityModelList = ActivityService.getUserActivitysByAttachmentsType(acsessUserModel, type, cursor);

        }

        // リクエストスコープの設定
        requestScope("activityList", activityModelList);
        requestScope("cursor", activityModelList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(activityModelList.hasNext()));

        return forward("/responsive/common/activity.jsp");
    }
}
