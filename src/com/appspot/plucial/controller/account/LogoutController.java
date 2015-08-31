package com.appspot.plucial.controller.account;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class LogoutController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        // セッション削除
        removeSessionScope("userModel");

        return redirect("/");

    }

    @Override
    protected String setPageTitle() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    protected String setPageDescription() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
}
