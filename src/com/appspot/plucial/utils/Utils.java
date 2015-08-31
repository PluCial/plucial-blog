package com.appspot.plucial.utils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.AlbumModel;
import com.appspot.plucial.model.UserModel;

public class Utils {

    private static final Pattern COMMENT_START = Pattern.compile("<[^>/!]{1}[^>]*?>");
    private static final Pattern COMMENT_END = Pattern.compile("</[^>!]*?>");

    private static final Pattern LINK_COMMENT_START = Pattern.compile("<a[^>/!]{1}[^>]*?>");
    private static final Pattern LINK_COMMENT_END = Pattern.compile("</a[^>!]*?>");

    private static final Pattern B_TAG_COMMENT_START = Pattern.compile("<b[^>!]*?>");
    private static final Pattern B_TAG_COMMENT_END = Pattern.compile("</b[^>!]*?>");

    private static final Pattern SPAN_TAG_COMMENT_START = Pattern.compile("<span[^>!]*?>");
    private static final Pattern SPAN_TAG_COMMENT_END = Pattern.compile("</span[^>!]*?>");

    public static String removeAllTags(String str) {
        return removePattern(removePattern(str, COMMENT_START), COMMENT_END);
    }

    public static String removeLinkTags(String str) {
        return removePattern(removePattern(str, LINK_COMMENT_START), LINK_COMMENT_END);
    }

    public static String removeBTags(String str) {
        return removePattern(removePattern(str, B_TAG_COMMENT_START), B_TAG_COMMENT_END);
    }

    public static String removeSpanTags(String str) {
        return removePattern(removePattern(str, SPAN_TAG_COMMENT_START), SPAN_TAG_COMMENT_END);
    }

    public static String removePattern(String target, Pattern pattern) {
        if (target == null || target.trim().equals("")) {
            return target;
        }

        Matcher matcher = pattern.matcher(target);
        StringBuilder buff = new StringBuilder();
        int start = 0;
        while (matcher.find()) {
            buff.append(target.substring(start, matcher.start()));
            start = matcher.end();
        }
        buff.append(target.substring(start, target.length()));

        return buff.toString();
    }

    /**
     * List型の引数がnullまたは空であればtrueを返します
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * List型の引数がnullでも空でもなければtrueを返却します
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }


    /**
     * 2つの日付の差を求めます。
     * java.util.Date 型の日付 date1 - date2 が何日かを返します。
     *
     * 計算方法は以下となります。
     * 1.最初に2つの日付を long 値に変換します。
     * 　※この long 値は 1970 年 1 月 1 日 00:00:00 GMT からの
     * 　経過ミリ秒数となります。
     * 2.次にその差を求めます。
     * 3.上記の計算で出た数量を 1 日の時間で割ることで
     * 　日付の差を求めることができます。
     * 　※1 日 ( 24 時間) は、86,400,000 ミリ秒です。
     *
     * @param date1    日付 java.util.Date
     * @param date2    日付 java.util.Date
     * @return    2つの日付の差
     */
    public static int differenceDays(Date date1,Date date2) {
        long datetime1 = date1.getTime();
        long datetime2 = date2.getTime();
        long one_date_time = 1000 * 60 * 60 * 24;
        long diffDays = (datetime1 - datetime2) / one_date_time;
        return (int)diffDays;
    }

    /**
     * アルバムURL変更
     * @param albumModel
     * @return
     */
    public static String changeAlbumUrl(AlbumModel albumModel) {

        String url = albumModel.getImageUrlString();
        if(url == null) {
            return "";
        }

        try {
            String tmp = "w" + albumModel.getWidth() + "-" + "h" + albumModel.getHeight() + "-p/";

            return url.replace(tmp, "");

        } catch (Exception e) {
            return url;
        }

    }

    /**
     * Twitter 転送チェック
     * @param userModel
     * @param activityModel
     * @return
     */
    public static boolean isTwitterRepost(UserModel userModel, ActivityModel activityModel) {

        if(userModel.getTwitterAccessToken() != null && userModel.getTwitterTokenSecret() != null) {

            // ハッシュタグモードではなければ転送する
            if(!userModel.isTwitterRepostHashtagFlg()) return true;

            String hashtag = getRepostHashtag(activityModel);

            if(hashtag == null) return false;

            // ハッシュタグモードで t が含まれていれば転送する
            if(hashtag.toLowerCase().indexOf("t") != -1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Facebook 転送チェック
     * @param userModel
     * @param activityModel
     * @return
     */
    public static boolean isFacebookRepost(UserModel userModel, ActivityModel activityModel) {

        if(userModel.getFacebookAccessToken() != null && userModel.getFacebookAccountName() != null) {

            // ハッシュタグモードではなければ転送する
            if(!userModel.isFacebookRepostHashtagFlg()) return true;

            String hashtag = getRepostHashtag(activityModel);

            if(hashtag == null) return false;

            // ハッシュタグモードで f が含まれていれば転送する
            if(hashtag.toLowerCase().indexOf("f") != -1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Evernote 転送チェック
     * @param userModel
     * @param activityModel
     * @return
     */
    public static boolean isEvernoteRepost(UserModel userModel, ActivityModel activityModel) {

        if(userModel.getEvernoteAccessToken() != null) {

            // ハッシュタグモードではなければ転送する
            if(!userModel.isEvernoteRepostHashtagFlg()) return true;

            String hashtag = getRepostHashtag(activityModel);

            if(hashtag == null) return false;

            // ハッシュタグモードで f が含まれていれば転送する
            if(hashtag.toLowerCase().indexOf("e") != -1) {
                return true;
            }
        }

        return false;
    }

    /**
     * ハッシュタグ転送時のハッシュタグを取得
     * @param activityModel
     * @return
     */
    public static String getRepostHashtag(ActivityModel activityModel) {

        String contents = null;

        // 再共有の場合
        if(activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
            contents = activityModel.getAnnotationString();

        }else {
            contents = activityModel.getContentString();
        }

        if(contents == null) return null;

        String pattern = ".*(>#[tfeTFE]{1,3}<).*";
        Pattern patt = Pattern.compile(pattern);
        Matcher matcher = patt.matcher(contents);

        if(matcher.find()){
            return matcher.group(1);
        }

        return null;
    }

    /**
     * 転送しないかどうかをチェック
     * @param activityModel
     * @return
     */
    public static boolean isNoShare(ActivityModel activityModel) {

        String contents = null;

        // 再共有の場合
        if(activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
            contents = activityModel.getAnnotationString();

        }else {
            contents = activityModel.getContentString();
        }

        if(contents == null) return false;

        String pattern = ".*(>#ns<).*";
        Pattern patt = Pattern.compile(pattern);
        Matcher matcher = patt.matcher(contents);

        return matcher.find();
    }


}
