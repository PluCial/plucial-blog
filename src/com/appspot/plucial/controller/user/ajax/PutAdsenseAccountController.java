package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class PutAdsenseAccountController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String accountId = asString("id");

        if(accountId == null || accountId.trim().isEmpty()) return null;

        if(userModel.getAdSenseAccountId() != null && !userModel.getAdSenseAccountId().equals(accountId)) {
            userModel.setAdSenseUnitCodeW300H250(null);
            userModel.setAdSenseUnitCodeW728H90(null);
        }

        userModel.setAdSenseAccountId(accountId);

        UserService.put(userModel);

        return null;
    }
}
