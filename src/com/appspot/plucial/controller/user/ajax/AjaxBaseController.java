package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.controller.user.UserBaseController;
import com.appspot.plucial.exception.UserLoginException;
import com.appspot.plucial.model.UserModel;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public abstract class AjaxBaseController extends UserBaseController {

    @Override
    protected Navigation run() throws Exception {

        // アクセス承認
        try {
            UserModel userModel = getLoginUser();

            return execute(userModel);

        }catch(UserLoginException e) {
            if(isSmartPhone()) {
                return forward("/user_sp/ajax/no_login.jsp");
            }else {
                return forward("/user/ajax/no_login.jsp");
            }

        }catch(GoogleJsonResponseException ex) {
            if(isSmartPhone()) {
                return forward("/user_sp/ajax/no_login.jsp");
            }else {
                return forward("/user/ajax/no_login.jsp");
            }
        }

    }

}
