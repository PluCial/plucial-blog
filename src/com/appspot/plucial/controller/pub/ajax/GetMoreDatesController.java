package com.appspot.plucial.controller.pub.ajax;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.controller.pub.PubBaseController;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.DateService;

public class GetMoreDatesController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {
        String cursor = asString("cursor");
        String userId = asString("user");

        requestScope("userId", userId);

        // 対象のアクティビティリストを取得
        S3QueryResultList<DateModel> dateModelList = DateService.getDateModelList(acsessUserModel, cursor);

        // リクエストスコープの設定
        requestScope("dateModelList", dateModelList);
        requestScope("date_cursor", dateModelList.getEncodedCursor());
        requestScope("date_hasNext", String.valueOf(dateModelList.hasNext()));

        return forward("/responsive/common/date_list.jsp");
    }
}
