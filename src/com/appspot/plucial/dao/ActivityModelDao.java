package com.appspot.plucial.dao;

import org.slim3.datastore.CoreAttributeMeta;
import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModelRefAttributeMeta;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.datastore.Sort;
import org.slim3.util.StringUtil;

import com.appspot.plucial.meta.ActivityModelMeta;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ActivityModelDao extends DaoBase<ActivityModel>{

    /**
     * ---------------------------------------------------------------------------------------------------
     * アクティビティリストを取得(User)
     * @param userModel
     * @return
     * ---------------------------------------------------------------------------------------------------
     */
    private S3QueryResultList<ActivityModel> getActivitysByUser(UserModel userModel, int num) {

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()))
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                .asQueryResultList();

        return list;
    }

    public S3QueryResultList<ActivityModel> getActivitysByUser(UserModel userModel, int num, String cursor) {

        if (StringUtil.isEmpty(cursor)) return getActivitysByUser(userModel, num);

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()))
                .encodedStartCursor(cursor)
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                .asQueryResultList();

        return list;
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     * アクティビティリストを取得(post or share)
     * @param userModel
     * @return
     * ---------------------------------------------------------------------------------------------------
     */
    private S3QueryResultList<ActivityModel> getActivitysByPostType(UserModel userModel, String postType, int num) {

        Category category = new Category(postType);

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;
        CoreAttributeMeta<ActivityModel,Category> categoryMeta = meta.verb;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()), categoryMeta.equal(category))
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                .asQueryResultList();

        return list;
    }

    public S3QueryResultList<ActivityModel> getActivitysByPostType(UserModel userModel, String postType, int num, String cursor) {

        if (StringUtil.isEmpty(cursor)) return getActivitysByPostType(userModel, postType, num);

        Category category = new Category(postType);

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;
        CoreAttributeMeta<ActivityModel,Category> categoryMeta = meta.verb;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()), categoryMeta.equal(category))
                .encodedStartCursor(cursor)
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                 .asQueryResultList();

        return list;
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     * アクティビティリストを取得(ノート)
     * <pre>添付がないアクティビティ<pre>
     * @param userModel
     * @return
     * ---------------------------------------------------------------------------------------------------
     */
    private S3QueryResultList<ActivityModel> getNoteActivitys(UserModel userModel, int num) {

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()), meta.attachmentsFlg.equal(false))
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                .asQueryResultList();

        return list;
    }

    public S3QueryResultList<ActivityModel> getNoteActivitys(UserModel userModel, int num, String cursor) {

        if (StringUtil.isEmpty(cursor)) return getNoteActivitys(userModel, num);

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()), meta.attachmentsFlg.equal(false))
                .encodedStartCursor(cursor)
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                 .asQueryResultList();

        return list;
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     * アクティビティリストを取得(article or video or album or photo)
     * @param userModel
     * @return
     * ---------------------------------------------------------------------------------------------------
     */
    private S3QueryResultList<ActivityModel> getActivitysByAttachmentsType(UserModel userModel, String type, int num) {

        Category category = new Category(type);

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;
        CoreAttributeMeta<ActivityModel,Category> categoryMeta = meta.attachmentsType;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()), categoryMeta.equal(category))
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                .asQueryResultList();

        return list;
    }

    public S3QueryResultList<ActivityModel> getActivitysByAttachmentsType(UserModel userModel, String type, int num, String cursor) {

        if (StringUtil.isEmpty(cursor)) return getActivitysByAttachmentsType(userModel, type, num);

        Category category = new Category(type);

        ActivityModelMeta meta = ActivityModelMeta.get();
        ModelRefAttributeMeta<ActivityModel, ModelRef<UserModel>, UserModel> refMeta = meta.userModelRef;
        CoreAttributeMeta<ActivityModel,Category> categoryMeta = meta.attachmentsType;

        S3QueryResultList<ActivityModel> list = Datastore.query(meta)
                .filter(refMeta.equal(userModel.getKey()), categoryMeta.equal(category))
                .encodedStartCursor(cursor)
                .sort(new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING))
                .limit(num)
                 .asQueryResultList();

        return list;
    }

}
