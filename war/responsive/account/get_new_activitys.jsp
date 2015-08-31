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
			<h1>Google+ の投稿をPluCialに取込む。</h1>
			<p>PluCialでは自動取込みを行っていますが、ご利用者による手動取込み機能も提供しています。</p>
		</div>

		<section>
			<div class="details">
				<img src="/images/setting/google-plus-back.png" width="100%">
				<h2>自動取込み</h2>
				<p>PluCialは一定の時間毎にご利用者の投稿を自動的に取込んでおります。今後運営状況に合わせてできるだけこの時間を短くして行く予定です。</p>
			</div>

			<div class="details">
				<h2>手動取込み</h2>
				<p>手動で新しい投稿を取り込みたい場合は以下の取り込みボタンをご利用ください。</p>
				<p style="color: red">注意：Google+に投稿してから実際PluCialに取込めるまで15分ほどのタイムラグが発生します。</p>
					<div class="action-box">
					<a href="#" onclick="get_new_activitys();return false;">新しい投稿を取り込む</a>
				</div>
			</div>

		</section>

		<!-- adsense box -->
		<jsp:include page="/responsive/common/advertisement_box.jsp" />
		<!-- /adsense box -->
	</div>

	<!-- ajax -->
	<jsp:include page="/responsive/common/ajax_result.jsp" />
	<script type="text/javascript">
		// update profile ajax
		function get_new_activitys() {
			ajax_start();
			$.ajax({
				type : 'POST',
				url : '/account/ajax/getNewActivitys',
				dataType : 'html',
				timeout : 1000000000,
				success : function(result) {
					window.location = "/account/loggedIn";
				},
				error : function() {
					reset_pop();
				}
			});
		};
	</script>
	<!-- ajax -->

	<!-- adsense base -->
	<jsp:include page="/responsive/common/adsense_base.jsp" />
	<!-- /adsense base -->
</body>
</html>
