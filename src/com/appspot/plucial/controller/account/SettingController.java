package com.appspot.plucial.controller.account;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class SettingController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        return forward("/responsive/account/setting.jsp");

    }

    @Override
    protected String setPageTitle() {
        return "アカウントの設定";
    }

    @Override
    protected String setPageDescription() {
        return "アカウントの設定";
    }
}
