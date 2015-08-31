package com.appspot.plucial.controller.info;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class UserListController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        S3QueryResultList<UserModel> userList = UserService.getUserList(null);

        // リクエストスコープの設定
        requestScope("userList", userList);
        requestScope("user_cursor", userList.getEncodedCursor());
        requestScope("user_hasNext", String.valueOf(userList.hasNext()));

        return forward("/responsive/info/user_list.jsp");
    }

    @Override
    protected String setPageTitle() {
        return "ご利用者一覧";
    }

    @Override
    protected String setPageDescription() {
        return "ご利用者一覧";
    }
}
