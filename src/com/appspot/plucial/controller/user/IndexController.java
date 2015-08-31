package com.appspot.plucial.controller.user;

import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.DateService;

public class IndexController extends UserBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        List<DateModel> dateModelList = DateService.getDateModelList(userModel, null);
        requestScope("dateModelList", dateModelList);

        requestScope("userModel", userModel);

        if(isSmartPhone()) {
            return forward("/user_sp/index.jsp");
        }else {
            return forward("index.jsp");
        }

    }
}
