package com.appspot.plucial.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class JspUtils {
    /**
     * nullを空文字に変換
     * @param target
     * @return
     */
    public static String getNotNull(String target){
        if(target == null) return "";
        return target;
    }

    /**
     * nullを空文字に変換
     * @param target
     * @return
     */
    public static String getActivityContentNotNull(String target){
//        if(target == null) return "<span style=\"font-size: 12px;color: #3d3a3b;\">&lt;共有&gt;</span>";
        if(target == null) return "";
        return target;
    }

    /**
     * 文字の切り出し
     * @param str
     * @param num
     * @return
     */
    public static String subContentsString(String str, int num) {

        if(str == null) return "";

        if(str.length() > num) {
            return str.substring(0, num) + "....";
        }

        return str;
    }

    /**
     * URLエンコード
     * @param src
     * @return
     */
    public static String urlEncoder(String str) {
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String changeProfileImageSize(String imgUrl, int size) {
        return  imgUrl.replace("?sz=50", "?sz=" + size);
    }

    public static boolean isTwitterUrl(String url) {
        return url.indexOf("twitter.com") > 0;
    }

    public static boolean isFaceBookUrl(String url) {
        return url.indexOf("facebook.com") > 0;
    }
}
