package com.appspot.plucial.controller.user.ajax;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.UserModel;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.model.AdUnit;
import com.google.api.services.adsense.model.AdUnit.ContentAdsSettings;
import com.google.api.services.adsense.model.AdUnits;

public class GetAdsenseUnitsController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String accountId = asString("id");
        String size = asString("size");

        if(accountId == null || accountId.trim().isEmpty()) return null;

        // トークン情報の取得（アクセストークン、リフレッシュトークン・・・）
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setAccessToken(userModel.getAccessToken())
        .setRefreshToken(userModel.getRefreshToken());

        AdSense adSense = new AdSense(TRANSPORT, JSON_FACTORY, credential);

        // アカウントIDを取得
        System.out.println("Account ID:" + asString("id"));
        // 対象クライアントID
        String adClientId = "ca-" + accountId;

        // AdSense Unit の取得(有効な広告のみ)
        AdUnits adUnits = adSense.adunits().list(adClientId).setIncludeInactive(false).execute();
        if(adUnits.getItems() == null || adUnits.getItems().size() <= 0) {
            return forward("select_ad_unit.jsp");
        }

        List<AdUnit> targetUnits = new ArrayList<AdUnit>();
        for(AdUnit adUnit: adUnits.getItems()) {

            String targetSizeString = "SIZE_" + size;

            // 広告ユニットのセッティング情報を取得
            ContentAdsSettings contentAdsSettings = adUnit.getContentAdsSettings();

            if(targetSizeString.equals(contentAdsSettings.getSize())) {
                targetUnits.add(adUnit);
            }
        }

        requestScope("units", targetUnits);
        requestScope("size", size);

        if(isSmartPhone()) {
            return forward("/user_sp/ajax/select_ad_unit.jsp");
        }else {
            return forward("/user/ajax/select_ad_unit.jsp");
        }
    }
}
