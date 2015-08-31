package com.appspot.plucial.controller.account;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;

public class LoggedInController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        S3QueryResultList<ActivityModel> activityModelList = ActivityService.getActivitysByUser(loginUserModel, null);

        // アクティビティリストが存在しない場合
        if(activityModelList == null || activityModelList.size() == 0) {

            return forward("/responsive/account/thank_you.jsp");
        }

        return redirect("/u/" + loginUserModel.getKey().getName() + "/");

    }

    @Override
    protected String setPageTitle() {
        return "PluCialにご登録頂きありがとうございます。";
    }

    @Override
    protected String setPageDescription() {
        return "PluCialにご登録頂きありがとうございます。";
    }
}
