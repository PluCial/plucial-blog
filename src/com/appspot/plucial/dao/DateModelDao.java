package com.appspot.plucial.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModelRefAttributeMeta;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.datastore.Sort;

import com.appspot.plucial.meta.DateModelMeta;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.google.appengine.api.datastore.Query.SortDirection;

public class DateModelDao extends DaoBase<DateModel>{

    /**
     * DateModelのリストを取得
     * @param userModel
     * @return
     */
    public List<DateModel> getDateModelList(UserModel userModel) {

        DateModelMeta meta = DateModelMeta.get();
        ModelRefAttributeMeta<DateModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;

        // 月初めの日のキー値以上のDateModelを取得
        List<DateModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()))
                .sort(new Sort(DateModelMeta.get().key.getName(), SortDirection.DESCENDING))
                .asList();

        return list;
    }

    /**
     * DateModelのリストを取得
     * @param userModel
     * @return
     */
    public S3QueryResultList<DateModel> getDateModelList(UserModel userModel, int num) {

        DateModelMeta meta = DateModelMeta.get();
        ModelRefAttributeMeta<DateModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;

        // 月初めの日のキー値以上のDateModelを取得
        S3QueryResultList<DateModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()))
                .sort(new Sort(DateModelMeta.get().key.getName(), SortDirection.DESCENDING))
                .limit(num)
                .asQueryResultList();

        return list;
    }

    /**
     * DateModelのリストを取得
     * @param userModel
     * @return
     */
    public S3QueryResultList<DateModel> getDateModelList(UserModel userModel, int num, String cursor) {

        DateModelMeta meta = DateModelMeta.get();
        ModelRefAttributeMeta<DateModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;

        // 月初めの日のキー値以上のDateModelを取得
        S3QueryResultList<DateModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()))
                .encodedStartCursor(cursor)
                .sort(new Sort(DateModelMeta.get().key.getName(), SortDirection.DESCENDING))
                .limit(num)
                .asQueryResultList();

        return list;
    }

}
