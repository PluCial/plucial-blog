package com.appspot.plucial.controller.account.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

/**
 * ユーザー追加コントローラー
 * @author takahara
 *
 */
public class FacebookModeChangeController extends BaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        userModel.setFacebookRepostHashtagFlg(!userModel.isFacebookRepostHashtagFlg());

        UserService.put(userModel);

        return null;
    }
}
