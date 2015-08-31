package com.appspot.plucial.controller.account.facebook;

import org.slim3.controller.Navigation;

import com.appspot.plucial.controller.account.BaseController;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class DeleteAccountController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        loginUserModel.setFacebookAccessToken(null);
        loginUserModel.setFacebookAccountName(null);

        UserService.put(loginUserModel);

        return redirect("/account/setting");

    }

    @Override
    protected String setPageTitle() {
        return null;
    }

    @Override
    protected String setPageDescription() {
        return null;
    }
}
