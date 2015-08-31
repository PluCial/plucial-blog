package com.appspot.plucial.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.memcache.Memcache;
import org.slim3.util.StringUtil;

import com.appspot.plucial.Constants;
import com.appspot.plucial.dao.UserModelDao;
import com.appspot.plucial.meta.UserModelMeta;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.utils.Utils;
import com.google.api.services.plus.model.Person.Cover;
import com.google.api.services.plus.model.Person.Image;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;


public class UserService {

    private static final UserModelDao userModelDao = new UserModelDao();

    /**
     *  ユーザーカウントキャッシュキー
     */
    public static final String SERVICE_USER_COUNT = "service_user_count";

    /**
     *  ユーザーリストキャッシュキー
     */
    public static final String USER_LIST_FIRST_30 = "user_list_first_30";

    /**
     *  全ユーザーリストキャッシュキー(10000件まで)
     */
    public static final String ALL_USER_LIST = "all_user_list";

    /**
     *  グループユーザーリストキャッシュキー
     */
    public static final String GROUP_USER_LIST = "group_user_list";


    /**
     * ユーザーカウントの取得
     * @return
     */
    public static int getUserCount() {

        int serviceUserCount = (Integer) (Memcache.get(SERVICE_USER_COUNT) == null ? 0 : Memcache.get(SERVICE_USER_COUNT));
        if(serviceUserCount > 0) return serviceUserCount;

        serviceUserCount = userModelDao.getUserCount();
        if(serviceUserCount > 0) Memcache.put(SERVICE_USER_COUNT, serviceUserCount);

        return serviceUserCount;
    }

    /**
     * ユーザーリストを取得
     * @return
     */
    public static S3QueryResultList<UserModel> getUserList(String cursor) {

        S3QueryResultList<UserModel> list = null;

        // 最初のページをキャッシュする
        if (StringUtil.isEmpty(cursor)) {
            // Memcache に存在した場合はMemcache内のmodelを返す
            list = Memcache.get(USER_LIST_FIRST_30);
            if(Utils.isNotEmpty(list)) return list;

            // DBから取得し、存在した場合はMemcacheに入れる
            list = userModelDao.getUserList(Constants.SERVICE_USER_LIST_LIMIT_NUM);
            if(Utils.isNotEmpty(list)) Memcache.put(USER_LIST_FIRST_30, list);

        }else {
            list = userModelDao.getUserList(Constants.SERVICE_USER_LIST_LIMIT_NUM, cursor);

        }

        return list == null ? new S3QueryResultList<UserModel>(new ArrayList<UserModel>(), null, null, null, false) : list;
    }

    /**
     * すべてのユーザーリストを取得
     * @return
     */
    public static List<UserModel> getAllUserList() {
        List<UserModel> userList = Memcache.get(ALL_USER_LIST);
        if(Utils.isNotEmpty(userList)) return userList;

        userList = userModelDao.getAllUserList();
        if(Utils.isNotEmpty(userList)) Memcache.put(ALL_USER_LIST, userList);

        return userList;
    }

    /**
     * グループのユーザーリストを取得
     * @param groupId
     * @return
     */
    public static List<UserModel> getGroupUserList(int groupId) {

        List<UserModel> userList = Memcache.get(getGroupUserListMemcache(groupId));
        if(Utils.isNotEmpty(userList)) return userList;

        userList = userModelDao.getGroupUserList(groupId);
        if(Utils.isNotEmpty(userList)) Memcache.put(getGroupUserListMemcache(groupId), userList);

        return userList == null ? new ArrayList<UserModel>() : userList;
    }

    /**
     * ユーザーの取得
     * @param email
     * @return
     */
    public static UserModel getOrNull(String userID) {

        Key key = createKey(userID);
        UserModel model = Memcache.get(key.toString());
        if(model != null) return model;

        model = userModelDao.getOrNull(key);
        if(model != null) Memcache.put(model.getKey().toString(), model);

        return model;
    }

    /**
     * PUT
     * @param model
     * @return
     */
    public static UserModel put(UserModel model) {
        // 永久化
        userModelDao.put(model);

        // 自信のキャッシュをクリア
        Memcache.delete(model.getKey().toString());

        // グループのキャッシュをクリア
        clearGroupUserListMemcache(model.getGroup());

        return model;
    }

    /**
     * PUT
     * @param email
     * @param userId
     * @param displayName
     * @param userImage
     * @param tagline
     * @param braggingRights
     * @param accessToken
     * @param refreshToken
     * @return
     */
    public static UserModel put(
            String userId,
            String email,
            String url,
            String displayName,
            Image userImage,
            String tagline,
            String braggingRights,
            String aboutMe,
            Cover cover,
            String accessToken,
            String refreshToken) {

        UserModel userModel = new UserModel();

        Key key = createKey(userId);

        userModel.setKey(key);

        // email
        if(email != null && !email.isEmpty()) {
            userModel.setEmail(email);
        }

        // url
        if(url != null && !url.isEmpty()) {
            userModel.setUrl(new Text(url));
        }

        // 表示名
        if(displayName != null && !displayName.isEmpty()) {
            userModel.setDisplayName(displayName);
        }

        // 写真
        if(userImage != null && !userImage.isEmpty()) {
            userModel.setImageUrl(new Text(userImage.getUrl()));
        }

        // キャッチ
        if(tagline != null && !tagline.isEmpty()) {
            userModel.setTagline(new Text(tagline));
        }

        // 特技
        if(braggingRights != null && !braggingRights.isEmpty()) {
            userModel.setBraggingRights(new Text(braggingRights));
        }

        // About Me
        if(aboutMe != null && !aboutMe.isEmpty()) {
            userModel.setAboutMe(new Text(aboutMe));
        }

        // 背景画像url
        if(cover != null && cover.getCoverPhoto() != null && cover.getCoverPhoto().getUrl() != null && !cover.getCoverPhoto().getUrl().isEmpty()) {
            userModel.setCoverPhotoUrl(new Text(cover.getCoverPhoto().getUrl()));
        }

        // アクセストークン
        if(userImage != null && !accessToken.isEmpty()) {
            userModel.setAccessToken(accessToken);
        }

        // リフレッシュトークン
        if(refreshToken != null && !refreshToken.isEmpty()) {
            userModel.setRefreshToken(refreshToken);
        }

        // 新しいユーザーをまず(0〜9)グループに割り当てる
        Random rnd = new Random();
        userModel.setGroup(rnd.nextInt(10));

        // チェック日を設定
        userModel.setUpdateCheckDate(new Date());

        return put(userModel);
    }

    private static Key createKey(String userID) {
        return Datastore.createKey(UserModelMeta.get(), userID);
    }

    /**
     * Delete
     * @param model
     * @return
     */
    public static void delete(UserModel userModel) {

        // ユーザーのすべてのDateModelを取得
        List<DateModel> dateModelList = userModel.getDateModelListRef().getModelList();

        for(DateModel dateModel: dateModelList) {
            // DateModelに紐づくつべてのActivityModel を取得
            List<ActivityModel> activityModelList = dateModel.getActivityModelListRef().getModelList();

            for(ActivityModel activityModel: activityModelList) {
                // アクティビティの削除
                ActivityService.delete(userModel, activityModel);
            }

            // DateModelの削除
            DateService.delete(userModel, dateModel);
        }

        // URLリストの削除
        UserUrlsService.deleteAll(userModel);

        userModelDao.delete(userModel.getKey());

        // 自信のキャッシュをクリア
        Memcache.delete(userModel.getKey().toString());

        // ユーザーカウントのキャッシュクリア
        clearUserCountAndListMemcache();
        // グループのキャッシュをクリア
        clearGroupUserListMemcache(userModel.getGroup());
    }

    /**
     * ユーザー数のキャッシュをクリア
     */
    public static void clearUserCountAndListMemcache() {
        Memcache.delete(SERVICE_USER_COUNT);
        Memcache.delete(ALL_USER_LIST);
//        Memcache.delete(EXAMPLE_USER_LIST);
    }

    /**
     * グループユーザーリストキャッシュキーの取得
     */
    public static String getGroupUserListMemcache(int groupId) {

        return GROUP_USER_LIST + "_" + groupId;
    }

    /**
     * グループユーザーリストのキャッシュをクリア
     */
    public static void clearGroupUserListMemcache(int groupId) {
        Memcache.delete(getGroupUserListMemcache(groupId));
    }

}
