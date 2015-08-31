package com.appspot.plucial.controller;

import org.slim3.controller.router.RouterImpl;
import org.slim3.util.RequestUtil;

/**
 * 公開部分ルーティング
 * addRoutingメソッドの順番変更は禁止！
 * @author takahara
 *
 */
public class AppRouter extends RouterImpl {

	public AppRouter() {

	    // index.html
	    addRouting(
	        "/",
	        "/index");

	    // アクティビティ
        addRouting(
                "/u/{userId}/a/{activityId}",
                "/pub/activity?user={userId}&activityId={activityId}");

	    // プロフィール
        addRouting(
                "/u/{userId}",
                "/pub/profile?user={userId}");
        addRouting(
                "/u/{userId}/",
                "/pub/profile?user={userId}");

        // Thank you Flow Me
        addRouting(
                "/fm39/{userId}",
                "/pub/fm39?user={userId}");
        addRouting(
                "/fm39/{userId}/",
                "/pub/fm39?user={userId}");

        // アクティビティ by Type
        addRouting(
                "/ut/{userId}/{type}",
                "/pub/getActivitysByType?user={userId}&type={type}");

        // アクティビティ
        addRouting(
                "/u/{userId}/{date}",
                "/pub/dateActivity?user={userId}&date={date}");

        // rss all
        addRouting(
                "/rss/{userId}/",
                "/userRss?user={userId}");

        // rss by Type
        addRouting(
                "/rss/{userId}/{type}",
                "/userRss?user={userId}&type={type}");
	}


	/**
	 * <pre>
	 * 拡張子つきURIが静的リクエストと見なされるのを回避するには、
	 * RouterImpl#IsStatic()をオーバーライドして、処理を記述する。
	 * </pre>
	 */
	@Override
	public boolean isStatic(String path) throws NullPointerException {
		boolean isStatic = super.isStatic(path);

		// 複数の拡張子を解除する場合は "||" を使用する。
		if("html".equals(RequestUtil.getExtension(path)) || "xml".equals(RequestUtil.getExtension(path))) {
			return false;
		} else {
			return isStatic;
		}
	}

}
