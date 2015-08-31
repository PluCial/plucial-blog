package com.appspot.plucial.controller.account.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class ActivityDeleteController extends BaseController {


    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String activityId = asString("activity");

        ActivityModel activityModel = ActivityService.getActivity(activityId);

        if(activityModel == null) return redirect("/");

        try {
            ActivityService.delete(userModel, activityModel);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return redirect("/");
    }
}
