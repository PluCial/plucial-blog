package com.appspot.plucial.controller.account;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.exception.UserLoginException;
import com.appspot.plucial.model.UserModel;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public abstract class BaseController extends Controller {

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    protected Navigation run() throws Exception {

        requestScope("pageTitle", setPageTitle());
        requestScope("pageDescription", setPageDescription());

        // アクセス承認
        try {
            UserModel loginUserModel = getLoginUser();
            requestScope("isLogin", String.valueOf(loginUserModel != null));
            requestScope("loginUserModel", loginUserModel);

            return execute(loginUserModel);

        }catch(UserLoginException e) {
            return redirect("/account/login");
        }

    }

    /**
     * 登録ユーザーの場合、登録情報を取得する。
     * 登録ユーザーではない、もしくGoogleアカウントにログインしていない場合は、
     * エラーを生成
     * @return
     * @throws Exception
     */
    public UserModel getLoginUser() throws UserLoginException {

        UserModel userModel = sessionScope("userModel");

        if(userModel == null) throw new UserLoginException();

        return userModel;
    }

    /**
     * デバイスがスマートフォンであるか判定
     * @param request
     * @return
     */
    protected boolean isSmartPhone() {

        String userAgent = request.getHeader("User-Agent").toLowerCase();

        if(userAgent != null && (userAgent.indexOf("iphone") > 0 || userAgent.indexOf("android") > 0)) {
            return true;
        }

        return false;
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(UserModel loginUserModel) throws Exception;

    /**
     * ページタイトルの設定
     * @return
     * @throws Exception
     */
    protected abstract String setPageTitle();

    /**
     * ページタイトルの設定
     * @return
     * @throws Exception
     */
    protected abstract String setPageDescription();

}
