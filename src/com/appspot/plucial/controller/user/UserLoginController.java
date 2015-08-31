package com.appspot.plucial.controller.user;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;

/**
 * ユーザー追加コントローラー
 * @author takahara
 *
 */
public class UserLoginController extends Controller {

    private static final HttpTransport TRANSPORT = new NetHttpTransport();
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public Navigation run() throws Exception {

        try {

            // ユーザーログイン
            login();

        }catch(Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("No Login:login");
        }

        return null;
    }

    /**
     * ユーザー登録
     * @return
     * @throws Exception
     */
    private void login() throws Exception {

        String code = asString("code");

        // ---------------------------------------------------------
        // トークン情報の取得
        // ---------------------------------------------------------
        // 承認コードをアクセス・更新トークンにアップグレードします。
        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                    TRANSPORT,
                    JSON_FACTORY,
                    Constants.GOOGLE_PROJECT_CLIENT_ID,
                    Constants.GOOGLE_PROJECT_CLIENT_SECRET,
                    code,
                    "postmessage").execute();

        // トークン情報の取得（アクセストークン、リフレッシュトークン・・・）
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
        .setFromTokenResponse(tokenResponse);


        // ---------------------------------------------------------
        // トークン情報の有効チェック
        // ---------------------------------------------------------
        // トークンの有効チェック
        Oauth2 oauth2 = new Oauth2.Builder(
            TRANSPORT, JSON_FACTORY, credential).build();
        Tokeninfo tokenInfo = oauth2.tokeninfo()
            .setAccessToken(credential.getAccessToken()).execute();
        // トークン情報にエラーがあれば、中断すしま。
        if (tokenInfo.containsKey("error")) {
            throw new Exception();
        }

        // 受け取ったトークンが自分のアプリのものであることを確認します。
        if (!tokenInfo.getIssuedTo().equals(Constants.GOOGLE_PROJECT_CLIENT_ID)) {
            throw new Exception();
        }

        // ユーザー情報の取得
        UserModel userModel = UserService.getOrNull(tokenInfo.getUserId());

        if(userModel == null) {

            // ---------------------------------------------------------
            // ユーザー登録
            // ---------------------------------------------------------

            // Google Plus APIを使ってユーザー情報を取得する
            Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(Constants.GOOGLE_APPLICATION_NAME)
            .build();
            Person person = plus.people().get("me").execute();

            userModel = UserService.put(
                tokenInfo.getUserId(),
                tokenInfo.getEmail(),
                person.getUrl(),
                person.getDisplayName(),
                person.getImage(),
                person.getTagline(),
                person.getBraggingRights(),
                person.getAboutMe(),
                person.getCover(),
                credential.getAccessToken(),
                credential.getRefreshToken()
                );

            // ユーザー数のキャッシュをクリア
            UserService.clearUserCountAndListMemcache();

        }else {
            // ---------------------------------------------------------
            // ユーザーログイン
            // ---------------------------------------------------------
            // ログインユーザーのアクセストークンとリフレッシュトークンを更新
            userModel.setAccessToken(credential.getAccessToken());
            if(credential.getRefreshToken() != null) {
                userModel.setRefreshToken(credential.getRefreshToken());
            }
            UserService.put(userModel);
        }

        // ユーザー情報をセッションに入れる
        sessionScope("userModel", userModel);
    }
}
