package com.appspot.plucial;

import com.evernote.auth.EvernoteService;

public class Constants {

    /** グループ数 */
//    public static final int USER_GROUP_LIMIT = 20;

    // ---------------------------------------------------------------------------
    // Google Project Info
    // ---------------------------------------------------------------------------
    public static final String GOOGLE_APPLICATION_NAME = "PluCial";
    /** クライアントID */
    public static final String GOOGLE_PROJECT_CLIENT_ID = "649075818200.apps.googleusercontent.com";
    /** クライアントシークレット */
    public static final String GOOGLE_PROJECT_CLIENT_SECRET = "Sm5hA0mt7W9oQ-kmJo4MX9xC";
    /** Google プロジェクトスコープ */
    public static final String GOOGLE_PLUS_API_SCOPE = "https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email";
    /** Google プロジェクトスコープ */
    public static final String GOOGLE_ADSENSE_API_SCOPE = "https://www.googleapis.com/auth/adsense.readonly";
    /** Google プロジェクトスコープ */
    public static final String GOOGLE_URLSHORTENER_API_SCOPE = "https://www.googleapis.com/auth/urlshortener";
    /** Google+ API Fields */
    public static final String TARGET_ACTIVITY_FIELDS = "nextPageToken, updated, items(id,title,url,published,updated,verb,annotation,object(actor,objectType,content,attachments))";
    /** GOOGLE data-cookiepolicy */
    public static final String GOOGLE_DATA_COOKIEPOLICY = "single_host_origin";
//    public static final String GOOGLE_DATA_COOKIEPOLICY = "uri";

    /** ユーザー制限数 */
    public static final int SERVICE_USER_LIST_LIMIT_NUM = 30;

    /** ユーザー情報とアクティビティ情報の更新頻度 */
    public static final int CHECK_AND_UPDATE_INFO_LIMIT_DAYS = -30;

    // ---------------------------------------------------------------------------
    // Google Activity 定数
    // ---------------------------------------------------------------------------
    /** アクティビティタイプall */
    public static final String GOOGLE_ACTIVITY_TYPE_ALL = "all";

    /** アクティビティタイプpost */
    public static final String GOOGLE_ACTIVITY_VERB_TYPE_POST = "post";
    /** アクティビティタイプshare */
    public static final String GOOGLE_ACTIVITY_VERB_TYPE_SHARE = "share";

    /** アクティビティのオブジェクトタイプnote */
    public static final String GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE = "note";
    /** アクティビティのオブジェクトタイプactivity */
    public static final String GOOGLE_ACTIVITY_OBJECT_TYPE_ACTIVITY = "activity";

    /** アクティビティの添付情報タイプphoto */
    public static final String GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO = "photo";
    /** アクティビティの添付情報タイプalbum */
    public static final String GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM = "album";
    /** アクティビティの添付情報タイプvideo */
    public static final String GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO = "video";
    /** アクティビティの添付情報タイプarticle */
    public static final String GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE = "article";

    // ---------------------------------------------------------------------------
    // User URLS 定数
    // ---------------------------------------------------------------------------
    /** タイプotherProfile */
    public static final String USER_URLS_TYPE_PROFILE = "otherProfile";
    /** タイプcontributor */
    public static final String USER_URLS_TYPE_CONTRIBUTOR = "contributor";


    // ---------------------------------------------------------------------------
    // Twitter 定数
    // ---------------------------------------------------------------------------
    /** Twitter App API Key */
    public static final String TWITTER_APP_API_KEY = "TqYHkJrL4XQKukOQnKZTQjHmW";

    /** Twitter App API secret */
    public static final String TWITTER_APP_API_SECRET = "szbyO6a2YFbxPgGROLV3Ya6O7RKXJWZmt8rWJVqOtwn5WyESYp";

    // ---------------------------------------------------------------------------
    // Facebook 定数
    // ---------------------------------------------------------------------------
    /** FaceBook App API Key */
    public static final String FACEBOOK_APP_API_KEY = "268112023393520";

    /** FaceBook App API secret */
    public static final String FACEBOOK_APP_API_SECRET = "cb684983a43cae8deef473f5616e0997";

    /** Facebook Call Back */
    public static final String FACEBOOK_APP_OAUTH_CALLBACK = "http://plucial.com/account/facebook/addFacebookAccount";

    /** FaceBook App API PERMISSIONS */
    public static final String FACEBOOK_APP_API_PERMISSIONS = "public_profile,publish_actions";


    // ---------------------------------------------------------------------------
    // Evernote 定数
    // ---------------------------------------------------------------------------
    /** Evernote API KEY */
    public static final String EVERNOTE_CONSUMER_KEY = "lwminukckx";

    /** Evernote API SECRET */
    public static final String EVERNOTE_CONSUMER_SECRET = "26a8b68fe42e27b3";

    /** Evernote service(本番/SANDBOX) */
    public static final EvernoteService EVERNOTE_SERVICE = EvernoteService.PRODUCTION;

    /** Facebook Call Back */
    public static final String EVERNOTE_APP_OAUTH_CALLBACK = "addEvernoteAccount";
}
