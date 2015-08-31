package com.appspot.plucial.controller.account;

import java.util.ArrayList;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class ActivityDeleteController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {
        String activityId = asString("activity");

        ActivityModel activityModel = ActivityService.getActivity(activityId);

        ArrayList<ActivityModel> activityList = new ArrayList<ActivityModel>();
        if(activityModel != null) {
            activityList.add(activityModel);
        }

        requestScope("activityModel", activityModel);
        requestScope("activityList", activityList);

        return forward("/responsive/account/activity_delete.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "アクティビティの削除";
    }

    @Override
    protected String setPageDescription() {
        return "アクティビティの削除";
    }
}
