package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class PutAdsenseUnitController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String size = asString("size");
        String code = asString("code");

        System.out.println(size + ":" + code);

        if(size == null || size.trim().isEmpty()) return null;
        if(code == null || code.trim().isEmpty()) return null;

        if(size.equals("300_250")) {
            userModel.setAdSenseUnitCodeW300H250(code);

        }else if(size.equals("728_90")) {
            userModel.setAdSenseUnitCodeW728H90(code);
        }

        UserService.put(userModel);

        return null;
    }
}
