package com.appspot.plucial.controller.account;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class UserDeleteController extends BaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {
        return forward("/responsive/account/user_delete.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "PluCialを退会する";
    }

    @Override
    protected String setPageDescription() {
        return "PluCialを退会する";
    }
}
