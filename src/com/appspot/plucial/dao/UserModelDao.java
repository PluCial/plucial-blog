package com.appspot.plucial.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;

import com.appspot.plucial.meta.UserModelMeta;
import com.appspot.plucial.model.UserModel;

public class UserModelDao extends DaoBase<UserModel>{

    UserModelMeta meta = UserModelMeta.get();

    public int getUserCount() {

        return Datastore.query(meta).count();
    }

    public S3QueryResultList<UserModel> getUserList(int limit) {

        return Datastore.query(meta)
                .limit(limit)
                .asQueryResultList();
    }

    public S3QueryResultList<UserModel> getUserList(int limit, String cursor) {

        return Datastore.query(meta)
                .encodedStartCursor(cursor)
                .limit(limit)
                .asQueryResultList();
    }

    public List<UserModel> getAllUserList() {
        return Datastore.query(meta).asList();
    }

    public List<UserModel> getGroupUserList(int groupId) {
        return Datastore.query(meta).filter(meta.group.equal(groupId)).asList();
    }

}
