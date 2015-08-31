package com.appspot.plucial.controller.account.ajax;

import java.util.Date;
import java.util.List;

import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.appspot.plucial.service.UserUrlsService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.Urls;
import com.google.appengine.api.datastore.Text;

/**
 * ユーザー追加コントローラー
 * @author takahara
 *
 */
public class UpdateUserProfileController extends BaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        // トークン情報の取得（アクセストークン、リフレッシュトークン・・・）
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setRefreshToken(userModel.getRefreshToken());

        // リフレッシュトークンを元にアクセストークンを更新
        credential.refreshToken();


        Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(Constants.GOOGLE_APPLICATION_NAME)
        .build();
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

        userModel.setUpdateCheckDate(new Date());
        UserService.put(userModel);

        // URLSを削除して再登録
        UserUrlsService.deleteAll(userModel);
        if(person.getUrls() != null && person.getUrls().size() > 0) {
            List<Urls> urlsList = person.getUrls();

            for(Urls urls: urlsList) {
                UserUrlsService.put(userModel, urls.getValue(), urls.getType(), urls.getLabel());
            }
        }

        return null;
    }
}
