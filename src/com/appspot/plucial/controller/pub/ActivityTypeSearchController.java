package com.appspot.plucial.controller.pub;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class ActivityTypeSearchController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        return forward("/pub_sp/activity_type_search.jsp");
    }
}
