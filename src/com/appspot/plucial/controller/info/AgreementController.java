package com.appspot.plucial.controller.info;

import org.slim3.controller.Navigation;
import com.appspot.plucial.model.UserModel;

public class AgreementController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {
        return forward("/responsive/info/greement.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "利用規約";
    }

    @Override
    protected String setPageDescription() {
        return "PluCialをご利用いただきありがとうございます。ユーザーは、本サービスを利用することにより、本規約に同意したことになります。以下を注意してお読みください。";
    }
}
