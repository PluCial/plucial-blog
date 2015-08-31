package com.appspot.plucial.controller.account.facebook;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.controller.account.BaseController;
import com.appspot.plucial.model.UserModel;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;

public class OAuthController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(Constants.FACEBOOK_APP_API_KEY, Constants.FACEBOOK_APP_API_SECRET);
        facebook.setOAuthPermissions(Constants.FACEBOOK_APP_API_PERMISSIONS);
        sessionScope("facebook", facebook);


        return redirect(facebook.getOAuthAuthorizationURL(Constants.FACEBOOK_APP_OAUTH_CALLBACK));

    }

    @Override
    protected String setPageTitle() {
        return null;
    }

    @Override
    protected String setPageDescription() {
        return null;
    }
}
