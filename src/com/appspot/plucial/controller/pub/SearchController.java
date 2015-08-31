package com.appspot.plucial.controller.pub;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.TextSearchService;

public class SearchController extends PubBaseController {

    @Override
    protected Navigation execute(UserModel acsessUserModel, UserModel loginUserModel, boolean isLogin, boolean isSmartPhone) throws Exception {

        String qstr = asString("q");

        List<ActivityModel> activityModelList = null;
        if(StringUtil.isEmpty(qstr)) {
            activityModelList = new ArrayList<ActivityModel>();
        }else {
            String[] str1Ary = qstr.split(" ");
            activityModelList = TextSearchService.findDocument(acsessUserModel, str1Ary);
        }

        // リクエストスコープの設定
        requestScope("qstr", qstr);
        requestScope("activityList", activityModelList);

        requestScope("pageTitle", "\"" + qstr + "\"を含まれる " + acsessUserModel.getDisplayName() + " の投稿");
        requestScope("pageDescription", "\"" + qstr + "\"を含まれる " + acsessUserModel.getDisplayName() + " の投稿");

        return forward("/responsive/activity.jsp");
    }
}
