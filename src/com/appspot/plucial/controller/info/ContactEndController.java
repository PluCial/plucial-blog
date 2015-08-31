package com.appspot.plucial.controller.info;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class ContactEndController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin,
            boolean isSmartPhone) throws Exception {
        return forward("/responsive/info/contactEnd.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "お問い合わせ送信完了";
    }

    @Override
    protected String setPageDescription() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
}
