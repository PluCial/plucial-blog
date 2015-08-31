package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.DateService;

public class RestrictionActivityController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String activityId = asString("id");
        System.out.println(activityId);
        if(activityId == null || activityId.isEmpty()) {
            return null;
        }

        ActivityModel model = ActivityService.getActivity(activityId);
        if(model == null)  return null;

        model.setPublicFlg(!model.isPublicFlg());

        DateModel dateModel = DateService.getOrNull(userModel, model.getPublished());
        ActivityService.putActivity(userModel, dateModel, model);

        return null;
    }
}
