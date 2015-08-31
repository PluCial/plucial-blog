package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class DeleteAdsenseUnitController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String size = asString("size");

        if(size == null || size.trim().isEmpty()) return null;

        if(size.equals("300_250")) {
            userModel.setAdSenseUnitCodeW300H250(null);

        }else if(size.equals("728_90")) {
            userModel.setAdSenseUnitCodeW728H90(null);
        }

        UserService.put(userModel);

        return null;
    }
}
