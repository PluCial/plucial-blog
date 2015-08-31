package com.appspot.plucial.controller.user;

import org.slim3.controller.Navigation;

import com.appspot.plucial.controller.BaseController;

public class LoginController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        if(isSmartPhone()) {
            return forward("/user_sp/login.jsp");
        }else {
            return forward("/user/login.jsp");
        }
    }
}
