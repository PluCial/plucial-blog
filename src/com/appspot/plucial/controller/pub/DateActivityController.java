package com.appspot.plucial.controller.pub;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.DateService;

public class DateActivityController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        String dateString = asString("date");
        requestScope("contentsType", Constants.GOOGLE_ACTIVITY_TYPE_ALL);


        DateModel dateModel = DateService.getOrNull(acsessUserModel, dateString);
        if(dateModel == null) {
            return null;
        }
        requestScope("dateModel", dateModel);

        List<ActivityModel> activityModelList = ActivityService.getActivityListByDate(acsessUserModel, dateModel);
        requestScope("activityList", activityModelList);

        // 最近の投稿
        S3QueryResultList<ActivityModel> newActivityModelList = ActivityService.getActivitysByUser(acsessUserModel, null);
        ArrayList<ActivityModel> activityModelLimt5List = new ArrayList<ActivityModel>();
        int i = 0;
        for(ActivityModel activityModel: newActivityModelList) {
            i++;
            if(i > 5) {
                break;
            }

            activityModelLimt5List.add(activityModel);
        }
        requestScope("newActivityModelList", activityModelLimt5List);

        requestScope("pageTitle", dateModel.getDateJP() + " " + acsessUserModel.getDisplayName() + " の投稿");
        requestScope("pageDescription", dateModel.getDateJP() + " " + acsessUserModel.getDisplayName() + " の投稿");

        setThisURI(acsessUserModel, dateModel);

        return forward("/responsive/date_activity.jsp");
    }

    protected void setThisURI(UserModel userModel, DateModel dateModel) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        String baseURL = requestURL.replace(requestURI, "") + "/";

        requestScope("thisPageUrl", baseURL + "u/" + userModel.getKey().getName() + "/" + dateModel.getDate());
    }
}
