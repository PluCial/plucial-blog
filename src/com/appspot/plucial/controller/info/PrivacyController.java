package com.appspot.plucial.controller.info;

import org.slim3.controller.Navigation;
import com.appspot.plucial.model.UserModel;

public class PrivacyController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        return forward("/responsive/info/privacy.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "プライバシーポリシー";
    }

    @Override
    protected String setPageDescription() {
        return "PluCialをご利用いただきありがとうございます。PluCialを利用者の皆様にご利用いただくにあたり、PluCialが利用者情報をどのように利用し、プライバシーをどのように保護するかをご説明します。";
    }
}
