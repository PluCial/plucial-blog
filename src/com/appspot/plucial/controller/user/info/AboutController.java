package com.appspot.plucial.controller.user.info;

import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.controller.user.UserBaseController;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.DateService;

public class AboutController extends UserBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        List<DateModel> dateModelList = DateService.getDateModelList(userModel, null);
        requestScope("dateModelList", dateModelList);

        requestScope("userModel", userModel);

        if(isSmartPhone()) {
            return forward("/user_sp/info/about.jsp");
        }else {
            return forward("/user/info/about.jsp");
        }
    }
}
