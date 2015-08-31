package com.appspot.plucial.controller.account.evernote;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.EvernoteApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.controller.account.BaseController;
import com.appspot.plucial.model.UserModel;
import com.evernote.auth.EvernoteService;

public class OAuthController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        String thisUrl = request.getRequestURL().toString();
        String cbUrl = thisUrl.substring(0, thisUrl.lastIndexOf('/') + 1) + Constants.EVERNOTE_APP_OAUTH_CALLBACK;

        // Evernote Service の生成
        Class<? extends EvernoteApi> providerClass = EvernoteApi.Sandbox.class;
        if (Constants.EVERNOTE_SERVICE == EvernoteService.PRODUCTION) {
          providerClass = org.scribe.builder.api.EvernoteApi.class;
        }


        OAuthService service = new ServiceBuilder()
            .provider(providerClass)
            .apiKey(Constants.EVERNOTE_CONSUMER_KEY)
            .apiSecret(Constants.EVERNOTE_CONSUMER_SECRET)
            .callback(cbUrl)
            .build();

        // 一時トークンを生成
        Token scribeRequestToken = service.getRequestToken();

        // OAuth承認のためのSecretを取得してセッションに保存
        sessionScope("evernoteRequestTokenSecret", scribeRequestToken.getSecret());

        // 承認画面
        String authorizationUrl = Constants.EVERNOTE_SERVICE.getAuthorizationUrl(scribeRequestToken.getToken());

        return redirect(authorizationUrl);
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
