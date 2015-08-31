package com.appspot.plucial.controller.account;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class GetNewActivitysController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        return forward("/responsive/account/get_new_activitys.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "新しい投稿の取り込み";
    }

    @Override
    protected String setPageDescription() {
        return "新しい投稿の取り込み";
    }
}