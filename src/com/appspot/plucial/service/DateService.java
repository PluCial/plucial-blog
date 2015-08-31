package com.appspot.plucial.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.memcache.Memcache;
import org.slim3.util.StringUtil;

import com.appspot.plucial.dao.DateModelDao;
import com.appspot.plucial.meta.DateModelMeta;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.utils.Utils;
import com.google.appengine.api.datastore.Key;


public class DateService {

    private static final DateModelDao dao = new DateModelDao();

    /**
     *  リストキャッシュキー(ALL)
     */
    public static final String USER_DATE_ALL_LIST_MEMCACHE_KEY = "user_date_all_list_memcache_key";

    /**
     *  リストキャッシュキー
     */
    public static final String USER_DATE_LIST_MEMCACHE_KEY = "user_date_list_memcache_key";

    /**
     * ユーザーの取得
     * @param email
     * @return
     */
    public static DateModel getOrNull(UserModel userModel, Date date) {

        Key key = createKey(userModel, date);
        DateModel model = Memcache.get(key.toString());
        if(model != null) return model;

        model = dao.getOrNull(key);
        if(model != null) Memcache.put(model.getKey().toString(), model);

        return model;
    }

    /**
     * ユーザーの取得
     * @param email
     * @return
     */
    public static DateModel getOrNull(UserModel userModel, String dateString) {

        Key key = Datastore.createKey(DateModelMeta.get(), dateString + "_" + userModel.getKey().getName());
        DateModel model = Memcache.get(key.toString());
        if(model != null) return model;

        model = dao.getOrNull(key);
        if(model != null) Memcache.put(model.getKey().toString(), model);

        return model;
    }

    /**
     * PUT
     * @param model
     * @return
     */
    public static DateModel put(UserModel userModel, DateModel model) {
        // 永久化
        dao.put(model);

        // キャッシュをクリア
        clearMemcache(userModel, model);

        return model;
    }

    /**
     * delete
     * @param model
     * @return
     */
    public static void delete(UserModel userModel, DateModel model) {

        dao.delete(model.getKey());

        // キャッシュをクリア
        clearMemcache(userModel, model);
    }

    /**
     * PUT
     * @param userId
     * @param date
     * @return
     */
    public static DateModel put(UserModel userModel, Date date) {

        DateModel dateModel = new DateModel();

        Key key = createKey(userModel, date);

        dateModel.setKey(key);
        dateModel.setDate(getDateString(date));
        dateModel.getUserModelRef().setModel(userModel);

        return put(userModel, dateModel);
    }

    /**
     * DateModelのリストを取得(ALL)
     * @param userModel
     * @return
     */
    public static List<DateModel> getDateModelList(UserModel userModel) {

        // Memcache に存在した場合はMemcache内のmodelを返す
        List<DateModel> list = Memcache.get(getAllDateModelListMemcacheKey(userModel));
        if(Utils.isNotEmpty(list)) return list;

        // DBから取得し、存在した場合はMemcacheに入れる
        list = dao.getDateModelList(userModel);
        if(Utils.isNotEmpty(list)) Memcache.put(getAllDateModelListMemcacheKey(userModel), list);

        return list == null ? new ArrayList<DateModel>() : list;
    }

    /**
     * DateModelのリストを取得
     * @param userModel
     * @return
     */
    public static S3QueryResultList<DateModel> getDateModelList(UserModel userModel, String cursor) {

        S3QueryResultList<DateModel> list = null;

        // 最初のページをキャッシュする
        if (StringUtil.isEmpty(cursor)) {
            // Memcache に存在した場合はMemcache内のmodelを返す
            list = Memcache.get(getDateModelListMemcacheKey(userModel));
            if(Utils.isNotEmpty(list)) return list;

            // DBから取得し、存在した場合はMemcacheに入れる
            list = dao.getDateModelList(userModel, 10);
            if(Utils.isNotEmpty(list)) Memcache.put(getDateModelListMemcacheKey(userModel), list);
        }else {
            list = dao.getDateModelList(userModel, 10, cursor);
        }

        return list == null ? new S3QueryResultList<DateModel>(new ArrayList<DateModel>(), null, null, null, false) : list;
    }

    /**
     * キーの作成
     * @param userModel
     * @param date
     * @return
     */
    private static Key createKey(UserModel userModel, Date date) {
        return Datastore.createKey(DateModelMeta.get(), getDateString(date) + "_" + userModel.getKey().getName());
    }

    /**
     * 日付文字列を取得
     * @param date
     * @return
     */
    private static String getDateString(Date date) {
        return new java.text.SimpleDateFormat(
            "yyyyMMdd", java.util.Locale.US).format(date);
    }

    /**
     * DateListのキャッシュキーを取得
     * @param userModel
     * @return
     */
    private static String getDateModelListMemcacheKey(UserModel userModel) {
        return USER_DATE_LIST_MEMCACHE_KEY + "_" + userModel.getKey().getName();
    }

    /**
     * DateListのキャッシュキーを取得(ALL)
     * @param userModel
     * @return
     */
    private static String getAllDateModelListMemcacheKey(UserModel userModel) {
        return USER_DATE_ALL_LIST_MEMCACHE_KEY + "_" + userModel.getKey().getName();
    }

    /**
     * キャッシュクリア
     * @param userModel
     * @param dateModel
     */
    private static void clearMemcache(UserModel userModel, DateModel dateModel) {
        // 自信のキャッシュをクリア
        Memcache.delete(dateModel.getKey().toString());

        // リストのキャッシュをクリア
        Memcache.delete(getDateModelListMemcacheKey(userModel));
        // リストのキャッシュをクリア(ALL)
        Memcache.delete(getAllDateModelListMemcacheKey(userModel));
    }

}
