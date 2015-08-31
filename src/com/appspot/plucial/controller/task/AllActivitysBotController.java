package com.appspot.plucial.controller.task;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

public class AllActivitysBotController extends Controller {

    @Override
    public Navigation run() throws Exception {

        String userId = asString("user");
        UserModel userModel = UserService.getOrNull(userId);

        if(userModel.getRefreshToken() != null && !userModel.isActivityBotPerformingFlg()) {

            // 実行中フラグをtrueにする
            userModel.setActivityBotPerformingFlg(true);
            UserService.put(userModel);

            Queue queue = QueueFactory.getQueue("all-activitys-bot");
            queue.add(Builder.withUrl("/task/allActivitysBotTask").param("user", userModel.getKey().getName()));

        }

        return null;

    }
}
