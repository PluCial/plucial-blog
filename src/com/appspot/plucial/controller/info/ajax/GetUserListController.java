package com.appspot.plucial.controller.info.ajax;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.controller.info.BaseController;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;

public class GetUserListController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {
        String cursor = asString("cursor");

        // 対象のアクティビティリストを取得
        S3QueryResultList<UserModel> userList = UserService.getUserList(cursor);

        // リクエストスコープの設定
        requestScope("userList", userList);
        requestScope("user_cursor", userList.getEncodedCursor());
        requestScope("user_hasNext", String.valueOf(userList.hasNext()));

        return forward("/responsive/common/user_list.jsp");
    }

    @Override
    protected String setPageTitle() {
        return null;
    }

    @Override
    protected String setPageDescription() {
        return null;
    }
}
