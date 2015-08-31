package com.appspot.plucial.controller.user;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.exception.UserLoginException;
import com.appspot.plucial.model.UserModel;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public abstract class UserBaseController extends Controller {

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    protected Navigation run() throws Exception {

        // アクセス承認
        try {
            UserModel userModel = getLoginUser();
            return execute(userModel);

        }catch(UserLoginException e) {
            if(isSmartPhone()) {
                return forward("/user_sp/no_login.jsp");
            }else {
                return forward("/user/no_login.jsp");
            }

        }catch(GoogleJsonResponseException ex) {
            return forward("/user/ajax/no_login.jsp");
        }

    }

    /**
     * 登録ユーザーの場合、登録情報を取得する。
     * 登録ユーザーではない、もしくGoogleアカウントにログインしていない場合は、
     * エラーを生成
     * @return
     * @throws Exception
     */
    public UserModel getLoginUser() throws Exception {
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
    protected boolean isSmartPhone() {

        String userAgent = request.getHeader("User-Agent").toLowerCase();

        if(userAgent != null && (userAgent.indexOf("iphone") > 0 || userAgent.indexOf("android") > 0)) {
            return true;
        }

        return false;
    }

    /**
     * 登録ユーザーの場合、登録情報を取得する。
     * 登録ユーザーではない、もしくGoogleアカウントにログインしていない場合は、
     * エラーを生成
     * @return
     * @throws Exception
     */
//    public UserModel getLoginUser() throws Exception {
//        // Google App Engineのユーザーサービスからユーザー情報を取得
//        UserService us = UserServiceFactory.getUserService();
//        User user = us.getCurrentUser();
//
//        // Googleアカウントにログインしていない場合
//        if(user == null) throw new UserLoginException();
//
//        // 登録ユーザーかどうかをチェック
//        System.out.println(user.getUserId());
//        UserModel userModel = com.appspot.plucial.service.UserService.getOrNull(user.getEmail());
//        if(userModel == null) throw new UserLoginException();
//
//        return userModel;
//    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(UserModel userModel) throws Exception;

}
