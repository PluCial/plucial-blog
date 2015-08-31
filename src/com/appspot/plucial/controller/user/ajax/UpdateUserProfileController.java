package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.appengine.api.datastore.Text;

/**
 * ユーザー追加コントローラー
 * @author takahara
 *
 */
public class UpdateUserProfileController extends AjaxBaseController {

    private static final HttpTransport TRANSPORT = new NetHttpTransport();
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        // トークン情報の取得（アクセストークン、リフレッシュトークン・・・）
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setAccessToken(userModel.getAccessToken())
        .setRefreshToken(userModel.getRefreshToken());

        // Google Plus APIを使ってユーザー情報を取得する
        Plus plus = new Plus(TRANSPORT, JSON_FACTORY, credential);
        Person person = plus.people().get("me").execute();

        // 表示名
        if(person.getDisplayName() != null && !person.getDisplayName().isEmpty()) {
            userModel.setDisplayName(person.getDisplayName());
        }

        // 写真
        if(person.getImage() != null && !person.getImage().isEmpty()) {
            userModel.setImageUrl(new Text(person.getImage().getUrl()));
        }

        // キャッチ
        if(person.getTagline() != null && !person.getTagline().isEmpty()) {
            userModel.setTagline(new Text(person.getTagline()));
        }

        // 特技
        if(person.getBraggingRights() != null && !person.getBraggingRights().isEmpty()) {
            userModel.setBraggingRights(new Text(person.getBraggingRights()));
        }

        // About Me
        if(person.getAboutMe() != null && !person.getAboutMe().isEmpty()) {
            userModel.setAboutMe(new Text(person.getAboutMe()));
        }

        // 背景画像url
        if(person.getCover() != null
                && person.getCover().getCoverPhoto() != null
                && person.getCover().getCoverPhoto().getUrl() != null
                && !person.getCover().getCoverPhoto().getUrl().isEmpty()) {
            userModel.setCoverPhotoUrl(new Text(person.getCover().getCoverPhoto().getUrl()));
        }

        UserService.put(userModel);

        if(isSmartPhone()) {
            return forward("/user_sp/ajax/update_user_profile.jsp");
        }else {
            return forward("/user/ajax/update_user_profile.jsp");
        }
    }
}
