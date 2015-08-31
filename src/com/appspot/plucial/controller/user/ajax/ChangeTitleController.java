package com.appspot.plucial.controller.user.ajax;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.DateService;

public class ChangeTitleController extends AjaxBaseController {

    @Override
    protected Navigation execute(UserModel userModel) throws Exception {

        String date = asString("date");
        String title = asString("title");
        System.out.println(date + ":" + title);
        if(date == null || date.isEmpty()) return null;
        if(title == null || title.isEmpty()) return null;

        DateModel dateModel = DateService.getOrNull(userModel, date);
        System.out.println(dateModel);
        if(dateModel == null) return null;

        dateModel.setTitle(title);
        DateService.put(userModel, dateModel);

        return null;
    }
}
