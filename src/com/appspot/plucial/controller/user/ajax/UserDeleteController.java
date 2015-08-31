package com.appspot.plucial.controller.user.ajax;

import java.io.IOException;

import org.slim3.controller.Navigation;

import com.appspot.plucial.controller.user.UserBaseController;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.google.api.client.http.GenericUrl;

public class UserDeleteController extends UserBaseController {

    /**
     * 「POST /api/disconnect」で公開します。
     *
     * Google+ プラットフォームの利用規約に基づき、このエンドポイントは以下の処理を行います。
     *
     *   1. アプリに格納されている、Google から取得したすべてのデータを削除する。
     *   2. このアプリに対して発行されたすべてのトークンを無効にする。
     *
     * リクエストのペイロードを受け取らず、現在セッションによって識別されているユーザー
     * の接続を解除します。
     *
     * 接続されていたユーザーを表す次の JSON
     * 応答を返します:
     *
     *   「Successfully disconnected.」（正常に接続解除しました。）
     *
     * 以下のエラーを、対応する HTTP 応答コードと共に生成します:
     * 401: 「Unauthorized request」（未承認の要求）  接続解除の対象となる接続済みのユーザーはいませんでした。
     * 500: 「Failed to revoke token for given user: 」（特定ユーザーのトークンを無効にできませんでした）
     *      + エンドポイントを無効にするための失敗した接続からのエラー。
     *
     * @see javax.servlet.http.HttpServlet#doPost(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        // データの全削除
        UserService.delete(userModel);

        try {
            revokeToken(userModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    /**
     * 特定のアクセス トークンを無効にして、結果的にその他すべてのアクセス トークンを無効にし、
     * このユーザーについてこのアプリに対して発行されたトークンを更新します。
     *
     * この操作は基本的にユーザーをアプリから接続解除しますが、アプリのアクティビティは
     * Google 内で有効状態が維持されます。  したがって、同一のユーザーが後でアプリに戻り、
     * ログイン、再同意して、アプリの使用を再開できます。
     * @throws IOException 要求の最中にネットワーク エラーが発生しました。
     */
    protected static void revokeToken(UserModel userModel) throws Exception {
      TRANSPORT.createRequestFactory().buildGetRequest(new GenericUrl(
          String.format("https://accounts.google.com/o/oauth2/revoke?token=%s",userModel.getAccessToken()))).execute();
    }
}
