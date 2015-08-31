package com.appspot.plucial.controller.account.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

/**
 * ユーザー追加コントローラー
 * @author takahara
 *
 */
public class TwitterModeChangeController extends BaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        userModel.setTwitterRepostHashtagFlg(!userModel.isTwitterRepostHashtagFlg());

        UserService.put(userModel);

        return null;
    }
}
