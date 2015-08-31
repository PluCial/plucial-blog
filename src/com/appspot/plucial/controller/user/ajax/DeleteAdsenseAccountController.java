package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class DeleteAdsenseAccountController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        userModel.setAdSenseAccountId(null);
        userModel.setAdSenseUnitCodeW300H250(null);
        userModel.setAdSenseUnitCodeW728H90(null);

        UserService.put(userModel);

        return null;
    }
}
