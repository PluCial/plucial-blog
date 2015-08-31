package com.appspot.plucial.controller.account.twitter;

import java.util.Random;

import org.slim3.controller.Navigation;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.appspot.plucial.controller.account.BaseController;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class AddTwitterAccountController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        // Titterオブジェクトの生成
        Twitter twitter = sessionScope("twitter");
        RequestToken twitterRequestToken = sessionScope("twitterRequestToken");
        String verifier = asString("oauth_verifier");

        AccessToken accessToken = null;

        try {
            // RequestTokenからAccessTokenを取得
            accessToken = twitter.getOAuthAccessToken(twitterRequestToken, verifier);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Twitterアカウント情報と承認情報を保存
        if(accessToken != null){
            loginUserModel.setTwitterAccountName(twitter.getScreenName());
            loginUserModel.setTwitterAccessToken(accessToken.getToken());
            loginUserModel.setTwitterTokenSecret(accessToken.getTokenSecret());

            // ユーザーグループを変更
            if(loginUserModel.getGroup() < 10) {
                Random rnd = new Random();
                loginUserModel.setGroup(rnd.nextInt(10) + 10);
            }

            UserService.put(loginUserModel);
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
