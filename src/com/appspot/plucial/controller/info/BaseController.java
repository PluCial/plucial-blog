package com.appspot.plucial.controller.info;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.exception.UserLoginException;
import com.appspot.plucial.model.UserModel;

public abstract class BaseController extends Controller {

    @Override
    protected Navigation run() throws Exception {

        UserModel loginUserModel = null;

        requestScope("pageTitle", setPageTitle());
        requestScope("pageDescription", setPageDescription());

        try {

            // ログインユーザー情報を取得
            loginUserModel = getLoginUserModel();
            requestScope("isLogin", String.valueOf(loginUserModel != null));
            requestScope("loginUserModel", loginUserModel);

            // ログイン済みの場合
            return execute(loginUserModel, true, isSmartPhone());

        } catch (UserLoginException e) {
            // ログインしていない場合
            return execute(loginUserModel, false, isSmartPhone());

        }

    }

    /**
     * ログインチェック
     * @return
     * @throws Exception
     */
    private UserModel getLoginUserModel() throws UserLoginException {
        // セッションに含まれるステート
        UserModel userModel = sessionScope("userModel");

        if(userModel == null) throw new UserLoginException();

        return userModel;
    }

    /**
     * デバイスがスマートフォンであるか判定
     * @param request
     * @return
     */
    private boolean isSmartPhone() {

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
    protected abstract Navigation execute(UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception;

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
