package com.appspot.plucial.controller.account.twitter;

import org.slim3.controller.Navigation;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import com.appspot.plucial.Constants;
import com.appspot.plucial.controller.account.BaseController;
import com.appspot.plucial.model.UserModel;

public class OAuthController extends BaseController {

    private static TwitterFactory twitterFactory = new TwitterFactory();

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        // Titterオブジェクトの生成
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(Constants.TWITTER_APP_API_KEY, Constants.TWITTER_APP_API_SECRET);

        // リクエストトークンの生成
        RequestToken twitterRequestToken = twitter.getOAuthRequestToken();

        // RequestTokenをセッションに保存しておきます。
        sessionScope("twitter", twitter);
        sessionScope("twitterRequestToken", twitterRequestToken);

        // 認証画面にリダイレクトするためのURLを生成
        String oauthUrl = twitterRequestToken.getAuthorizationURL();

        return redirect(oauthUrl);

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
