package com.appspot.plucial.controller;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class SitemapsController extends Controller {

    @Override
    protected Navigation run() throws Exception {

        String groupId = asString("group");

        if(groupId != null && !groupId.trim().equals("")) {
            List<UserModel> userList = UserService.getGroupUserList(Integer.valueOf(groupId));

            requestScope("userList", userList);


            return forward("/users_sitemaps.jsp");
        }

        return forward("/sitemaps.jsp");

    }
}
