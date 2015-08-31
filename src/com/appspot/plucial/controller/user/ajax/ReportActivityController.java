package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

public class ReportActivityController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        UserModel newUserModel = UserService.getOrNull(userModel.getKey().getName());

        // タスクを追加
        // リフレッシュトークンが存在し、実行中でないユーザーのみ
        if(newUserModel != null && newUserModel.getRefreshToken() != null && !newUserModel.isActivityBotPerformingFlg()) {

            // 実行中フラグをtrueにする
            newUserModel.setActivityBotPerformingFlg(true);
            UserService.put(newUserModel);

            Queue queue = QueueFactory.getQueue("activitys-bot");
            queue.add(Builder.withUrl("/task/activitysBotTask").param("user", newUserModel.getKey().getName()));
        }

        if(isSmartPhone()) {
            return forward("/user_sp/ajax/report_activity.jsp");
        }else {
            return forward("/user/ajax/report_activity.jsp");
        }
    }
}
