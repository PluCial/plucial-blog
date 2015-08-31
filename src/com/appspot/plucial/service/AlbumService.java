package com.appspot.plucial.service;

import com.appspot.plucial.dao.AlbumModelDao;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.AlbumModel;
import com.google.appengine.api.datastore.Text;


public class AlbumService {

    private static final AlbumModelDao dao = new AlbumModelDao();

    /**
     * PUT
     * @param model
     * @return
     */
    public static AlbumModel put(ActivityModel activityModel, String url, String imageUrl, Long height, long width) {

        AlbumModel model = new AlbumModel();
        model.setUrl(new Text(url));
        model.setImageUrl(new Text(imageUrl));
        model.setHeight(height);
        model.setWidth(width);

        // 親の設定
        model.getActivityModelRef().setModel(activityModel);

        // 永久化
        dao.put(model);

        return model;
    }

    public static void delete(AlbumModel model) {
        dao.delete(model.getKey());
    }

}
