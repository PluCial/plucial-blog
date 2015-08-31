package com.appspot.plucial.controller;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {

        UserModel userModel = sessionScope("userModel");
        if(userModel != null) {
            return redirect("/u/" + userModel.getKey().getName() + "/");
        }


        requestScope("serviceUserCount", String.valueOf(UserService.getUserCount()));
//        requestScope("exampleUserList", UserService.getExampleUserList());

        return forward("/index.jsp");
    }
}
