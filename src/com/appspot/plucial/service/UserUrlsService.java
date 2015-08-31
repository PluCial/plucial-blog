package com.appspot.plucial.service;

import java.util.ArrayList;
import java.util.List;

import org.slim3.memcache.Memcache;

import com.appspot.plucial.dao.UserUrlsModelDao;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.model.UserUrlsModel;
import com.appspot.plucial.utils.Utils;
import com.google.appengine.api.datastore.Text;

public class UserUrlsService {

    private static UserUrlsModelDao dao = new UserUrlsModelDao();

    /**
     *  リストキャッシュキー
     */
    public static final String USER_URLS_LIST_MEMCACHE_KEY = "user_urls_list_memcache_key";

    /**
     * PUT
     * @param model
     * @return
     */
    private static UserUrlsModel put(UserUrlsModel model) {
        // 永久化
        dao.put(model);

        return model;
    }

    /**
     * PUT
     * @param value
     * @param type
     * @param label
     * @return
     */
    public static UserUrlsModel put(UserModel userModel,
            String value,
            String type,
            String label) {

        UserUrlsModel model = new UserUrlsModel();

        // value
        if(value != null && !value.isEmpty()) {
            model.setValue(new Text(value));
        }

        // type
        if(type != null && !type.isEmpty()) {
            model.setType(type);
        }

        // label
        if(label != null && !label.isEmpty()) {
            model.setLabel(new Text(label));
        }

        // 関連
        model.getUserModelRef().setModel(userModel);

        return put(model);
    }

    /**
     * ユーザーのURLSリストの取得
     * @param userModel
     * @param dateModel
     * @return
     */
    public static List<UserUrlsModel> getUrlsList(UserModel userModel) {

        // Memcache に存在した場合はMemcache内のmodelを返す
        List<UserUrlsModel> list = Memcache.get(getUserUrlsListMemcacheKey(userModel));
        if(Utils.isNotEmpty(list)) return list;

        // DBから取得し、存在した場合はMemcacheに入れる
        list = userModel.getUserUrlsModelListRef().getModelList();
        if(Utils.isNotEmpty(list)) Memcache.put(getUserUrlsListMemcacheKey(userModel), list);

        return list == null ? new ArrayList<UserUrlsModel>() : list;
    }

    /**
     * ユーザーのURLSリストの取得
     * @param userModel
     * @param dateModel
     * @return
     */
    public static List<UserUrlsModel> getUrlsListByType(UserModel userModel, String type) {

        // 全リストを取得
        List<UserUrlsModel> allList = getUrlsList(userModel);

        // 新しいリストを作成
        List<UserUrlsModel> list = new ArrayList<UserUrlsModel>();

        // 対象タイプのリストを抽出
        for(UserUrlsModel model: allList) {
            if(model.getType() != null && model.getType().equals(type)) {
                list.add(model);
            }
        }

        return list;
    }

    /**
     * Delete All
     * @param model
     * @return
     */
    public static void deleteAll(UserModel userModel) {

        // キャッシュを防ぐためDBから取得
        UserModel user = UserService.getOrNull(userModel.getKey().getName());

        // ユーザーのすべてのurlsModelを取得
        List<UserUrlsModel> list = user.getUserUrlsModelListRef().getModelList();

        if(list == null) return;

        for(UserUrlsModel model: list) {

            // DateModelの削除
            dao.delete(model.getKey());
        }

        // リストのキャッシュをクリア
        Memcache.delete(getUserUrlsListMemcacheKey(userModel));
    }

    /**
     * DateListのキャッシュキーを取得
     * @param userModel
     * @param dateModel
     * @return
     */
    private static String getUserUrlsListMemcacheKey(UserModel userModel) {
        return USER_URLS_LIST_MEMCACHE_KEY
                + "_" + userModel.getKey().getName();
    }

}
