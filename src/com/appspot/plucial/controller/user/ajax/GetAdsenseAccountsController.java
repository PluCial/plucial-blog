package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.UserModel;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.model.Accounts;

public class GetAdsenseAccountsController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        // トークン情報の取得（アクセストークン、リフレッシュトークン・・・）
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setAccessToken(userModel.getAccessToken())
        .setRefreshToken(userModel.getRefreshToken());

        try {
            AdSense adSense = new AdSense(TRANSPORT, JSON_FACTORY, credential);

            // アカウント（複数）を取得
            Accounts accounts = adSense.accounts().list().execute();
            requestScope("accounts", accounts);

        }catch(Exception e) {
            // アカウント存在しない場合はエラーになるが、エラーの表示をJSPで行うので何もしない。
        }

        if(isSmartPhone()) {
            return forward("/user_sp/ajax/select_ad_account.jsp");
        }else {
            return forward("/user/ajax/select_ad_account.jsp");
        }
    }
}
