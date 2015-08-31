package com.appspot.plucial.controller.info;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class AdsenseController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        return forward("/responsive/info/adsense.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "Google AdSense広告の利用について";
    }

    @Override
    protected String setPageDescription() {
        return "利用者は、本サービスでGoogle AdSenseプログラムをご利用することにより、ここに記載されているポリシーについて十分理解したことになりますので、以下を注意してお読みください。";
    }
}
