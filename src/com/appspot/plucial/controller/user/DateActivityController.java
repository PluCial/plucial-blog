package com.appspot.plucial.controller.user;

import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.DateService;

public class DateActivityController extends UserBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String dateString = asString("date");

        List<DateModel> dateModelList = DateService.getDateModelList(userModel, null);
        requestScope("dateModelList", dateModelList);

        DateModel dateModel = DateService.getOrNull(userModel, dateString);
        if(dateModel == null) {
            if(isSmartPhone()) {
                return forward("/no_contents_sp.jsp");
            }else {
                return forward("/no_contents.jsp");
            }
        }

        requestScope("dateModel", dateModel);

        List<ActivityModel> activityModelList = ActivityService.getActivityListByDate(userModel, dateModel);
        requestScope("activityList", activityModelList);

        requestScope("userModel", userModel);

        if(isSmartPhone()) {
            return forward("/user_sp/date_activity.jsp");
        }else {
            return forward("date_activity.jsp");
        }
    }
}
