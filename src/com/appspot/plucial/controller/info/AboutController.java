package com.appspot.plucial.controller.info;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class AboutController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        return forward("/responsive/info/about.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "運営者情報";
    }

    @Override
    protected String setPageDescription() {
        return "PluCialの中にいる人。新しい物好きのシステムエンジニアです。毎日毎日コツコツ頑張っています。宜しくお願いします。";
    }
}
