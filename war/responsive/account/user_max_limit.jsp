<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<html lang="ja">
<head>
<!-- main header -->
<jsp:include page="/responsive/common/html_head.jsp" />
<!-- /main header -->
<link href="/css/responsive/info.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/css/responsive/account.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
	<!-- main header -->
	<jsp:include page="/responsive/common/main_header.jsp" />
	<!-- /main header -->

	<!-- main menu -->
	<jsp:include page="/responsive/common/main_menu.jsp" />
	<!-- /main menu -->

	<div id="contents">

		<div class="welcome">
			<h1>一時的に新規登録を停止させて頂きます。</h1>
		</div>

		<section>
			<div class="details">
				<p>PluCialをご登録頂きありがとうございます。大変申し訳ございませんが、ユーザー増加により一時的に新規の登録を停止させて頂いております。</p>
				<h3>一時的に停止する原因と理由について</h3>
				<p>PluCialはGoogle+ APIを利用して、ご利用者のGoogle+投稿を自動的に取得しています。</p>
				<p>Google+ APIは一日10,000リクエストの制限があるため、既存のご利用者様に影響が出ないように新規の登録を停止させて頂いています。</p>
				
				<h3>新規登録の再開について</h3>
				<p>現在、PluCialが利用しているGoogle+ APIの制限緩和をGoogle+ API担当者にリクエストしています。</p>
				<p>PluCialはGoogleの審査によりポリシーやセキュリティなどの面で問題がないと判断されれば、APIの利用制限され、新規登録を再開致します。</p>
				<p>大変申し訳ございませんが、今しばらくお待ち頂きますよう宜しくお願い申し上げます。</p>
				<p>また、再開のお知らせはPluCialのGoogle+ ページにてお知らせさせて頂きます。</p>
			</div>

			<div class="details">
 				<h2>PluCialからのお知らせ</h2>
				<p>PluCialのGoogle+ ページをフォローすると最新情報を受け取れます。</p>
				<div style="text-align: center;padding: 30px 0;">
					<div class="g-page"
						data-width="273"
						data-href="//plus.google.com/u/0/111586968979366611293" data-layout="landscape" data-rel="publisher" ></div>
				</div>
			</div>

		</section>
	</div>

	<!-- ajax -->
	<jsp:include page="/responsive/common/ajax_result.jsp" />

	<!-- adsense base -->
	<jsp:include page="/responsive/common/adsense_base.jsp" />
	<!-- /adsense base -->
</body>
</html>
