package com.appspot.plucial.controller.pub;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.Constants;
import com.appspot.plucial.exception.NoContentsException;
import com.appspot.plucial.exception.UserLoginException;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.model.UserUrlsModel;
import com.appspot.plucial.service.DateService;
import com.appspot.plucial.service.UserService;
import com.appspot.plucial.service.UserUrlsService;

public abstract class PubBaseController extends Controller {

    @Override
    protected Navigation run() throws Exception {

        UserModel acsessUserModel = null;
        UserModel loginUserModel = null;

        try {
            // アクセスしているページのユーザー情報を取得
            acsessUserModel = getAccessUserModel();
            S3QueryResultList<DateModel> dateModelList = DateService.getDateModelList(acsessUserModel, null);
            requestScope("date_cursor", dateModelList.getEncodedCursor());
            requestScope("date_hasNext", String.valueOf(dateModelList.hasNext()));

            List<UserUrlsModel> profileUrlList = UserUrlsService.getUrlsListByType(acsessUserModel, Constants.USER_URLS_TYPE_PROFILE);
            List<UserUrlsModel> contributorUrlList = UserUrlsService.getUrlsListByType(acsessUserModel, Constants.USER_URLS_TYPE_CONTRIBUTOR);

            requestScope("acsessUserModel", acsessUserModel);
            requestScope("userId", acsessUserModel.getKey().getName());
            requestScope("dateModelList", dateModelList);
            requestScope("profileUrlList", profileUrlList);
            requestScope("contributorUrlList", contributorUrlList);

            // ログインユーザー情報を取得
            loginUserModel = getLoginUserModel();
            requestScope("isLogin", String.valueOf(loginUserModel != null));
            requestScope("loginUserModel", loginUserModel);

            // ログイン済みの場合
            return execute(acsessUserModel, loginUserModel, true, isSmartPhone());

        } catch (UserLoginException e) {
            // ログインしていない場合
            return execute(acsessUserModel, loginUserModel, false, isSmartPhone());

        } catch (NoContentsException e) {
            // アクセスページのユーザーが存在しない場合
            return redirect("/error/404.html");
        }

    }

    /**
     * 登録ユーザーの場合、登録情報を取得する。
     * 登録ユーザーではない、もしくGoogleアカウントにログインしていない場合は、
     * エラーを生成
     * @return
     * @throws Exception
     */
    private UserModel getAccessUserModel() throws NoContentsException {

        String userId = asString("user");

        UserModel userModel = UserService.getOrNull(userId);

        if(userModel == null) throw new NoContentsException();

        return userModel;
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
    protected abstract Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception;

}
