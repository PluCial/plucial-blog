package com.appspot.plucial.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.memcache.Memcache;
import org.slim3.util.StringUtil;

import com.appspot.plucial.Constants;
import com.appspot.plucial.dao.ActivityModelDao;
import com.appspot.plucial.exception.DataInvalidException;
import com.appspot.plucial.meta.ActivityModelMeta;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.AlbumModel;
import com.appspot.plucial.model.DateModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.utils.Utils;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments.Thumbnails;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

public class ActivityService {

//    private static final Logger logger = Logger.getLogger(ActivityService.class.getName());

    private static final ActivityModelDao dao = new ActivityModelDao();

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /**
     *  日ごとリストキャッシュキー
     */
    public static final String USER_DATE_ACTIVITY_LIST_MEMCACHE_KEY = "user_date_activity_list_memcache_key";

    /**
     *  新しいアクティビティリストキャッシュキー
     */
    public static final String USER_ACTIVITY_LIST_TYPE_ALL_MEMCACHE_KEY = "user_activity_list_type_all_memcache_key";

    /**
     *  アクティビティリストキャッシュキー
     */
    public static final String USER_ACTIVITY_LIST_TYPE_POST_MEMCACHE_KEY = "user_activity_list_type_post_memcache_key";

    /**
     *  アクティビティリストキャッシュキー
     */
    public static final String USER_ACTIVITY_LIST_TYPE_SHARE_MEMCACHE_KEY = "user_activity_list_type_share_memcache_key";

    /**
     *  アクティビティリストキャッシュキー
     */
    public static final String USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_NOTE_MEMCACHE_KEY = "user_activity_list_type_attachments_note_memcache_key";

    /**
     *  アクティビティリストキャッシュキー
     */
    public static final String USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_PHOTO_MEMCACHE_KEY = "user_activity_list_type_attachments_photo_memcache_key";

    /**
     *  アクティビティリストキャッシュキー
     */
    public static final String USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_VIDEO_MEMCACHE_KEY = "user_activity_list_type_attachments_video_memcache_key";

    /**
     *  アクティビティリストキャッシュキー
     */
    public static final String USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_ARTICLE_MEMCACHE_KEY = "user_activity_list_type_attachments_article_memcache_key";

    /**
     * アクティビティの取得
     * @param activityId
     * @return
     */
    public static ActivityModel getActivity(String activityId) {

        Key key = createKey(activityId);
        ActivityModel model = Memcache.get(key.toString());

        if(model == null) {
            model = dao.getOrNull(key);

            if(model != null) {
                Memcache.put(model.getKey().toString(), model);

            }else {
                return null;
            }
        }

        UserModel usermodel = model.getUserModelRef().getModel();
        try {
            if(updateActivity(model, usermodel)) {
                model = dao.getOrNull(key);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    /**
     * 対象日にちのアクティビティリストの取得
     * @param userModel
     * @param dateModel
     * @return
     */
    public static List<ActivityModel> getActivityListByDate(UserModel userModel, DateModel dateModel) {

        // Memcache に存在した場合はMemcache内のmodelを返す
        List<ActivityModel> list = Memcache.get(getUserDateActivitylListMemcacheKey(userModel, dateModel));

        if(Utils.isEmpty(list)) {
            // DBから取得し、存在した場合はMemcacheに入れる
            list = dateModel.getActivityModelListRef().getModelList();
            if(Utils.isNotEmpty(list)) {
                Memcache.put(getUserDateActivitylListMemcacheKey(userModel, dateModel), list);
            }else {
                return new ArrayList<ActivityModel>();
            }
        }

        // check And UpdateActivity
        if(list != null) {

            boolean memcacheUpdateFlg = false;

            for(ActivityModel model: list) {
                try {
                    // 更新チェック
                    if(updateActivity(model, userModel)) {
                        memcacheUpdateFlg = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // リストを再取得
            if(memcacheUpdateFlg) {
                list = dateModel.getActivityModelListRef().getModelList();
            }

            return list;

        }else {
            return new ArrayList<ActivityModel>();
        }

    }

    /**
     * 新しいアクティビティリストの取得
     * @param userModel
     * @param dateModel
     * @return
     */
    public static S3QueryResultList<ActivityModel> getActivitysByUser(UserModel userModel, String cursor) {

        S3QueryResultList<ActivityModel> list = null;

        // 最初のページをキャッシュする
        if (StringUtil.isEmpty(cursor)) {
            // Memcache に存在した場合はMemcache内のmodelを返す
            list = Memcache.get(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ALL_MEMCACHE_KEY, userModel));

            // Memcache に存在しない場合は再取得し、Memcacheに登録
            if(Utils.isEmpty(list)) {
                // DBから取得し、存在した場合はMemcacheに入れる
                list = dao.getActivitysByUser(userModel, 20, cursor);
                if(Utils.isNotEmpty(list)) {
                    Memcache.put(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ALL_MEMCACHE_KEY, userModel), list);
                }
            }

        // 2ページ以後
        }else {
            list = dao.getActivitysByUser(userModel, 20, cursor);

        }

        // check And UpdateActivity
        if(list != null) {

            boolean memcacheUpdateFlg = false;

            for(ActivityModel model: list) {
                try {
                    // 更新チェック
                    if(updateActivity(model, userModel)) {
                        memcacheUpdateFlg = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // リストを再取得
            if(memcacheUpdateFlg) {
                list = dao.getActivitysByUser(userModel, 20, cursor);
            }

            return list;

        }else {
            return null;
        }

    }

    /**
     * アクティビティリストの取得(post or share)
     * @param userModel
     * @param dateModel
     * @return
     */
    public static S3QueryResultList<ActivityModel> getUserActivitysListByPostType(UserModel userModel,String type, String cursor) {

        S3QueryResultList<ActivityModel> list = null;
        String memchcheKey = null;

        if(type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST)) {
            memchcheKey = USER_ACTIVITY_LIST_TYPE_POST_MEMCACHE_KEY;

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
            memchcheKey = USER_ACTIVITY_LIST_TYPE_SHARE_MEMCACHE_KEY;

        }else {
            return null;

        }

        // 最初のページをキャッシュする
        if (StringUtil.isEmpty(cursor)) {
            // Memcache に存在した場合はMemcache内のmodelを返す
            list = Memcache.get(getUserActivitylListMemcacheKey(memchcheKey, userModel));
            if(Utils.isEmpty(list)) {

                // DBから取得し、存在した場合はMemcacheに入れる
                list = dao.getActivitysByPostType(userModel, type, 20, cursor);
                if(Utils.isNotEmpty(list)) {
                    Memcache.put(getUserActivitylListMemcacheKey(memchcheKey, userModel), list);
                }
            }

        }else {
            list = dao.getActivitysByPostType(userModel, type, 20, cursor);

        }

        // check And UpdateActivity
        if(list != null) {

            boolean memcacheUpdateFlg = false;

            for(ActivityModel model: list) {
                try {
                    // 更新チェック
                    if(updateActivity(model, userModel)) {
                        memcacheUpdateFlg = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // リストを再取得
            if(memcacheUpdateFlg) {
                list = dao.getActivitysByPostType(userModel, type, 20, cursor);
            }

            return list;

        }else {
            return null;
        }

    }

    /**
     * アクティビティリストの取得(article or video or album or photo)
     * @param userModel
     * @param dateModel
     * @return
     */
    public static S3QueryResultList<ActivityModel> getUserActivitysByAttachmentsType(UserModel userModel, String type, String cursor) {

        S3QueryResultList<ActivityModel> list = null;
        String memchcheKey = null;

        if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)) {
            memchcheKey = USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_PHOTO_MEMCACHE_KEY;

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)) {
            memchcheKey = USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_VIDEO_MEMCACHE_KEY;

        }else if(type.equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)) {
            memchcheKey = USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_ARTICLE_MEMCACHE_KEY;

        }else {
            return null;

        }

        // 最初のページをキャッシュする
        if (StringUtil.isEmpty(cursor)) {
            // Memcache に存在した場合はMemcache内のmodelを返す
            list = Memcache.get(getUserActivitylListMemcacheKey(memchcheKey, userModel));
            if(Utils.isEmpty(list)) {

                // DBから取得し、存在した場合はMemcacheに入れる
                list = dao.getActivitysByAttachmentsType(userModel, type, 20, cursor);
                if(Utils.isNotEmpty(list)) {
                    Memcache.put(getUserActivitylListMemcacheKey(memchcheKey, userModel), list);
                }
            }

        }else {
            list = dao.getActivitysByAttachmentsType(userModel, type, 20, cursor);

        }

        // check And UpdateActivity
        if(list != null) {

            boolean memcacheUpdateFlg = false;

            for(ActivityModel model: list) {
                try {
                    // 更新チェック
                    if(updateActivity(model, userModel)) {
                        memcacheUpdateFlg = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // リストを再取得
            if(memcacheUpdateFlg) {
                list = dao.getActivitysByAttachmentsType(userModel, type, 20, cursor);
            }

            return list;

        }else {
            return null;
        }

    }

    /**
     * テキストアクティビティリストの取得(text)
     * @param userModel
     * @param dateModel
     * @return
     */
    public static S3QueryResultList<ActivityModel> getUserNoteActivitys(UserModel userModel, String cursor) {

        S3QueryResultList<ActivityModel> list = null;

        // 最初のページをキャッシュする
        if (StringUtil.isEmpty(cursor)) {
            // Memcache に存在した場合はMemcache内のmodelを返す
            list = Memcache.get(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_NOTE_MEMCACHE_KEY, userModel));
            if(Utils.isEmpty(list)) {

                // DBから取得し、存在した場合はMemcacheに入れる
                list = dao.getNoteActivitys(userModel, 20, cursor);
                if(Utils.isNotEmpty(list)) {
                    Memcache.put(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_NOTE_MEMCACHE_KEY, userModel), list);
                }
            }

        }else {
            list = dao.getNoteActivitys(userModel, 20, cursor);

        }

        // check And UpdateActivity
        if(list != null) {

            boolean memcacheUpdateFlg = false;

            for(ActivityModel model: list) {
                try {
                    // 更新チェック
                    if(updateActivity(model, userModel)) {
                        memcacheUpdateFlg = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // リストを再取得
            if(memcacheUpdateFlg) {
                list = dao.getNoteActivitys(userModel, 20, cursor);
            }

            return list;

        }else {
            return null;
        }

    }


    /**
     * アクティビティ PUT
     * @param model
     * @return
     */
    public static ActivityModel putActivity(UserModel userModel, DateModel dateModel, ActivityModel model) {

        // -------------------------------------------------------
        // 登録処理
        // -------------------------------------------------------
        dao.put(model);

        // キャッシュクリア
        clearMemcache(userModel, dateModel, model);

        // 全件検索のドキュメントを作成
        try {
            TextSearchService.createDocument(model, userModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    /**
     * アクティビティ delete
     * @param model
     * @return
     */
    public static void delete(UserModel userModel, ActivityModel model) {

        DateModel dateModel = model.getDateModelRef().getModel();

        // 検索ドキュメントを削除
        TextSearchService.deleteDocument(userModel, model);

        // アルバムをすべて削除
        if(model.getAlbumModelListRef() != null && model.getAlbumModelListRef().getModelList() != null) {
            for(AlbumModel albumModel: model.getAlbumModelListRef().getModelList()) {
                AlbumService.delete(albumModel);
            }
        }

        // アクティビティの削除
        dao.delete(model.getKey());

        // キャッシュクリア
        clearMemcache(userModel, dateModel, model);
    }

    /**
     * アクティビティ PUT
     * @param activity
     * @return
     */
    public static ActivityModel putActivity(Activity activity, UserModel userModel) throws DataInvalidException {

        ActivityModel model = new ActivityModel();

        // -------------------------------------------------------
        // 基本情報の設定
        // -------------------------------------------------------
        // key
        model.setKey(createKey(activity.getId()));

        // 公開フラグ
        model.setPublicFlg(true);

        // アクティビティタイプ(post or share)
        if(!activity.getVerb().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST)
                && !activity.getVerb().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
            throw new DataInvalidException();
        }else {
            model.setVerb(new Category(activity.getVerb()));
        }

        // タイトル
        if(activity.getTitle() != null && !activity.getTitle().trim().isEmpty()) {
            String title = activity.getTitle();
            model.setTitle(new Text(title));
        }

        // 公開日
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(activity.getPublished().getValue());
        calendar.add(Calendar.HOUR, 9);
        model.setPublished(calendar.getTime());

        // 更新日
        model.setUpdated(new Date(activity.getUpdated().getValue()));

        // URL
        model.setUrl(new Text(activity.getUrl()));

        // コンテントの設定
        if(activity.getObject().getContent() != null
                && !activity.getObject().getContent().trim().isEmpty()) {
            String content = activity.getObject().getContent();
            model.setContent(new Text(content));
        }


        // -------------------------------------------------------
        // 添付情報の設定
        // -------------------------------------------------------
        List<Attachments> attachmentsList = activity.getObject().getAttachments();
        if(attachmentsList != null && attachmentsList.size() > 0) {
            // 添付情報の一つ目のみを対象とする
            Attachments attachment = attachmentsList.get(0);

            if(attachment.getObjectType() == null) {
                throw new DataInvalidException();
            }

            // 添付フラグをTrueに設定
            model.setAttachmentsFlg(true);

            // 添付情報のタイプ
            if(attachment.getObjectType() != null) {
                if(attachment.getObjectType().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)
                        || attachment.getObjectType().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)
                        || attachment.getObjectType().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)
                        || attachment.getObjectType().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM)) {
                    model.setAttachmentsType(new Category(attachment.getObjectType()));

                }else {
                    throw new DataInvalidException();
                }
            }else {
                throw new DataInvalidException();
            }

            // 添付情報の表示名
            if(attachment.getDisplayName() != null && !attachment.getDisplayName().isEmpty()) {
                model.setAttachmentsDisplayName(new Text(attachment.getDisplayName()));
            }

            // 添付情報のコンテンツ
            if(attachment.getContent() != null && !attachment.getContent().isEmpty()) {
                String content = attachment.getContent();
                model.setAttachmentsContent(new Text(content));
            }

            // 添付情報のURL
            if(attachment.getUrl() != null) {
                model.setAttachmentsUrl(new Text(attachment.getUrl()));
            }

            // 添付情報のイメージURL
            if(attachment.getImage() != null && attachment.getImage().getUrl() != null) {
                model.setAttachmentsImageUrl(new Text(attachment.getImage().getUrl()));
            }

            // 添付情報のFULLイメージURL
            if(attachment.getFullImage() != null && attachment.getFullImage().getUrl() != null) {
                model.setAttachmentsFullImageUrl(new Text(attachment.getFullImage().getUrl()));
            }

            // 動画情報（添付情報は動画の場合）
            if(attachment.getEmbed() != null) {
                if(attachment.getImage() != null && attachment.getImage().getUrl() != null) {
                    model.setAttachmentsImageUrl(new Text(attachment.getImage().getUrl()));
                }

                model.setEmbedType(attachment.getEmbed().getType());
                model.setEmbedUrl(new Text(attachment.getEmbed().getUrl()));
            }

            // アルバムの場合(アクティビティに画像を設定 twtter POSTを簡単にするため)
            if(attachment.getThumbnails() != null && attachment.getThumbnails().size() > 0) {
                Thumbnails thumbnails = attachment.getThumbnails().get(0);
                if(thumbnails != null && thumbnails.getImage() != null) {
                    model.setAttachmentsImageUrl(new Text(thumbnails.getImage().getUrl()));
                }
            }
        }

        // -------------------------------------------------------
        // シェアの場合の元情報
        // -------------------------------------------------------
        if(activity.getVerb().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)
                && activity.getObject().getActor() != null) {

            model.setShareActorFlg(true);

            model.setActorId(activity.getObject().getActor().getId());
            model.setActorDisplayName(activity.getObject().getActor().getDisplayName());
            model.setActorUrl(new Text(activity.getObject().getActor().getUrl()));

            if(activity.getObject().getActor().getImage() != null && activity.getObject().getActor().getImage().getUrl() != null) {
                model.setActorImageUrl(new Text(activity.getObject().getActor().getImage().getUrl()));
            }

            // 追加コメント
            if(activity.getAnnotation() != null
                    && !activity.getAnnotation().trim().isEmpty()) {
                String content = activity.getAnnotation();
                model.setAnnotation(new Text(content));
            }
        }

        // -------------------------------------------------------
        // 親の設定
        // -------------------------------------------------------
        // userModelの設定
        model.getUserModelRef().setModel(userModel);

        // DateModelの設定
        DateModel dateModel = DateService.getOrNull(userModel, model.getPublished());
        if(dateModel == null) {
            dateModel = DateService.put(userModel, model.getPublished());
        }
        model.getDateModelRef().setModel(dateModel);

        // アクティビティの更新チェック日時を更新
        model.setUpdateCheckDate(new Date());

        // アクティビティ
        return putActivity(userModel, dateModel, model);
    }

    /** アクティビティのキーを取得 */
    private static Key createKey(String activityId) {
        return Datastore.createKey(ActivityModelMeta.get(), activityId);
    }

    /**
     * アクティビティリストのキャッシュキーを取得
     * @param userModel
     * @return
     */
    private static String getUserActivitylListMemcacheKey(String listType, UserModel userModel) {
        return listType + "_" + userModel.getKey().getName();
    }

    /**
     * DateListのキャッシュキーを取得
     * @param userModel
     * @param dateModel
     * @return
     */
    private static String getUserDateActivitylListMemcacheKey(UserModel userModel, DateModel dateModel) {
        return USER_DATE_ACTIVITY_LIST_MEMCACHE_KEY
                + "_" + userModel.getKey().getName()
                + "_" + dateModel.getKey().getName();
    }

    /**
     * キャッシュクリア
     * @param userModel
     * @param dateModel
     */
    private static void clearMemcache(UserModel userModel, DateModel dateModel, ActivityModel activityModel) {
        // 自信のキャッシュをクリア
        Memcache.delete(activityModel.getKey().toString());

        // アクティビティリストのキャッシュをクリア
        Memcache.delete(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ALL_MEMCACHE_KEY, userModel));
        Memcache.delete(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_POST_MEMCACHE_KEY, userModel));
        Memcache.delete(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_SHARE_MEMCACHE_KEY, userModel));
        Memcache.delete(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_NOTE_MEMCACHE_KEY, userModel));
        Memcache.delete(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_PHOTO_MEMCACHE_KEY, userModel));
        Memcache.delete(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_VIDEO_MEMCACHE_KEY, userModel));
        Memcache.delete(getUserActivitylListMemcacheKey(USER_ACTIVITY_LIST_TYPE_ATTACHMENTS_ARTICLE_MEMCACHE_KEY, userModel));

        // 日ごとのアクティビティリストのキャッシュをクリア
        Memcache.delete(getUserDateActivitylListMemcacheKey(userModel, dateModel));
    }

    /**
     * アクティビティのチェックと更新
     * <pre>
     * Googleからの要求により：
     * You may store anonymized or aggregate data indefinitely,
     * and make that available for download,
     * but user information and original post content should be refreshed every 30 days, at least.
     * The easiest way to do this is probably to simply re-run your API queries every month, and replace the posts you store with the new results,
     * so that any modified/deleted posts on Google+ are consistent with your application.
     * Please let us know if/when you have updated your app to comply with this policy and we will be happy to continue processing your request.
     * </pre>
     * @param activityModel
     * @return
     * @throws IOException
     * @throws DataInvalidException
     */
    private static boolean updateActivity(ActivityModel activityModel, UserModel userModel) throws IOException, DataInvalidException {

        // The date of 30 days ago
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, Constants.CHECK_AND_UPDATE_INFO_LIMIT_DAYS);

        // When it passes on the 30th
        if(activityModel.getUpdateCheckDate() == null || activityModel.getUpdateCheckDate().before(cal.getTime())) {

//            logger.info("Start Activity Check!");

            // GoogleCredential
            GoogleCredential credential = new GoogleCredential.Builder()
            .setJsonFactory(JSON_FACTORY)
            .setTransport(TRANSPORT)
            .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
            .setRefreshToken(userModel.getRefreshToken());

            // リフレッシュトークンを元にアクセストークンを更新
            credential.refreshToken();

            // Plus Object
            Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(Constants.GOOGLE_APPLICATION_NAME)
            .build();

            // Re-acquisition of Activity
            Activity activity = null;
            try {
                activity = plus.activities().get(activityModel.getKey().getName()).execute();

            } catch (GoogleJsonResponseException e) {
                // When deleted
                if(e.getStatusCode() == 404) {
//                    logger.info("Activity Delete");
                    delete(userModel, activityModel);

                    return true;
                }
            }


            if(activity != null) {
                Date oldModelUpdateDate = activityModel.getUpdated();
                Date newModelUpdateDate = new Date(activity.getUpdated().getValue());

                // Activity Is Updated
                if(!oldModelUpdateDate.equals(newModelUpdateDate)) {
//                    logger.info("Activity Update!");
                    // Replace Activity And UpdateCheckDate
                    putActivity(activity, userModel);

                    return true;
                }
            }

            // Update UpdateCheckDate
            activityModel.setUpdateCheckDate(new Date());
            putActivity(userModel, activityModel.getDateModelRef().getModel(), activityModel);

        }

//        logger.info("Not 30 days!");

        return false;
    }

}
