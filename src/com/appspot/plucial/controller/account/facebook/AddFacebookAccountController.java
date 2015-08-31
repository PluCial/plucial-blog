package com.appspot.plucial.controller.account.facebook;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.controller.account.BaseController;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.google.appengine.api.datastore.Text;

import facebook4j.Facebook;
import facebook4j.RawAPIResponse;
import facebook4j.User;
import facebook4j.auth.AccessToken;

public class AddFacebookAccountController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        Facebook facebook = sessionScope("facebook");
        String oauthCode = asString("code");

        System.out.println("oauthCode: " + oauthCode);

        try {
            // アクセストークンの取得
            AccessToken accessToken = facebook.getOAuthAccessToken(oauthCode);
            String tokenString = accessToken.getToken();

            // アクセスを2ヶ月有効トークンに変更
            Map<String, String> params = new HashMap<String, String>();
            params.put("client_id", Constants.FACEBOOK_APP_API_KEY);
            params.put("client_secret", Constants.FACEBOOK_APP_API_SECRET);
            params.put("grant_type", "fb_exchange_token");
            params.put("fb_exchange_token", tokenString);

            RawAPIResponse apiResponse = facebook.callGetAPI("/oauth/access_token", params);
            String response = apiResponse.asString();
            AccessToken newAccessToken = new AccessToken(response);
            String newToken = newAccessToken.getToken();

            if(newToken != null) {
                User facebookUser = facebook.getMe();

                loginUserModel.setFacebookAccessToken(new Text(newToken));
                loginUserModel.setFacebookAccountName(facebookUser.getName());

                // ユーザーグループを変更
                if(loginUserModel.getGroup() < 10) {
                    Random rnd = new Random();
                    loginUserModel.setGroup(rnd.nextInt(10) + 10);
                }

                UserService.put(loginUserModel);
            }


        } catch (Exception e) {
            redirect("/account/setting");
        }


        return redirect("/account/setting");

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
