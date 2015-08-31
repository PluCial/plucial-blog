package com.appspot.plucial.controller.task;

import java.util.List;
import java.util.Random;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

public class ActivitysBotController extends Controller {

    @Override
    public Navigation run() throws Exception {

        String groupId = asString("group");

        List<UserModel> userList = UserService.getGroupUserList(Integer.valueOf(groupId));

        for(UserModel userModel: userList) {

//            if(userModel.getRefreshToken() != null && !userModel.isActivityBotPerformingFlg()) {
            if(userModel.getRefreshToken() != null) {

                // 実行中フラグをtrueにする
//                userModel.setActivityBotPerformingFlg(true);

                // SNS連携する場合は、10 〜 19のグループに変更、連携しない場合は 0 〜 9 までのグループに変更
                if((userModel.getTwitterAccessToken() != null && userModel.getTwitterTokenSecret() != null)
                        || (userModel.getFacebookAccessTokenString() != null && userModel.getFacebookAccountName() != null)) {
                    if(userModel.getGroup() < 10) {
                        Random rnd = new Random();
                        userModel.setGroup(rnd.nextInt(10) + 10);
                    }

                }else {
                    if(userModel.getGroup() >= 10) {
                        Random rnd = new Random();
                        userModel.setGroup(rnd.nextInt(10));
                    }

                }

                UserService.put(userModel);

                Queue queue = QueueFactory.getQueue("activitys-queue-group" + userModel.getGroup());
                queue.add(Builder.withUrl("/task/activitysBotTask").param("user", userModel.getKey().getName()));

            }
        }

        return null;
    }
}
