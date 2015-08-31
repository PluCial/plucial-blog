package com.appspot.plucial.controller.account;

import java.net.URLEncoder;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.controller.BaseController;

public class LoginController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {

        requestScope("pageTitle", "PluCialログイン");
        requestScope("pageDescription", "PluCialログイン");

        StringBuffer callbackURL = request.getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/userLogin");

        String oauthUrl = "https://accounts.google.com/o/oauth2/auth?scope="
                + URLEncoder.encode(Constants.GOOGLE_PLUS_API_SCOPE + " " + Constants.GOOGLE_URLSHORTENER_API_SCOPE, "utf-8")
                + "&redirect_uri="
                + URLEncoder.encode(callbackURL.toString(), "utf-8")
                + "&response_type=code"
                + "&client_id="
                + Constants.GOOGLE_PROJECT_CLIENT_ID
                + "&approval_prompt=force"
                + "&access_type=offline";

        requestScope("oauthUrl", oauthUrl);

        System.out.println(oauthUrl);

        return forward("/responsive/account/login.jsp");
    }
}
