package com.appspot.plucial.controller.task;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.TextSearchService;
import com.appspot.plucial.service.UserService;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class CreateTextSeearchDocumentTaskController extends Controller {

    private static final Logger logger = Logger.getLogger(CreateTextSeearchDocumentTaskController.class.getName());

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public Navigation run() throws Exception {

        UserModel userModel = null;

        try{
            userModel = getUser();
        }catch(Exception e) {
            return null;
        };

        // タスクは成功するまで実行されるため、失敗時は例外をキャッチして再実行をさせない
        List<ActivityModel> activityList =  userModel.getActivityModelListRef().getModelList();
        for(ActivityModel model: activityList) {
            try{
                TextSearchService.createDocument(model, userModel);

            }catch(Exception e) {
                logger.severe(e.toString());
            }
        }

        return null;
    }

    /**
     * UserModelの取得
     * @return
     * @throws Exception
     */
    public UserModel getUser() throws Exception {

        String userId = asString("user");

        UserModel userModel = UserService.getOrNull(userId);

        if(userModel == null) throw new Exception();

        return userModel;
    }
}
