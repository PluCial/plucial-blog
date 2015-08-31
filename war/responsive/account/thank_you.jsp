<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
UserModel loginUserModel = (UserModel) request.getAttribute("loginUserModel");
%>
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
			<h1>PluCialにご登録頂きありがとうございます。</h1>
		</div>

		<section>
			<div class="details">
<!-- 				<h2>自動取込み</h2>
				<p>PluCialは以下の時間にご利用者様の投稿を自動的に取込んでおります。</p>
				<ul class="list-contents">
					<li>日本時間：3:00</li>
					<li>日本時間：9:00</li>
					<li>日本時間：15:00</li>
					<li>日本時間：21:00</li>
				</ul>
			</div> -->

			<div class="details">
<!-- 				<h2>PluCialからのお知らせ</h2>
				<p>PluCialのGoogle+ ページをフォローすると最新情報を受け取れます。</p>
				<div style="text-align: center;padding: 30px 0;">
					<div class="g-page"
						data-width="273"
						data-href="//plus.google.com/u/0/111586968979366611293" data-layout="landscape" data-rel="publisher" ></div>
				</div> -->

				<p>過去の投稿を取込んでブログを完成しましょう。過去の投稿はここでしか取込めませんのでご注意ください。</p>
				<h3>過去の投稿100件を取込む</h3>
				<p>下のGoogle共有ボタンを使って自分のブログページを友達と共有すると、過去の投稿の取り込み件数が 20件から100件 になります。また、取込み処理は共有後に自動的に開始されます。</p>
				<p style="color:red">PC以外の一部の端末や一部のブラウザではご利用頂けない場合がございますのでご了承ください。</p>

				<div style="margin: 20px;text-align: right;" class="action-box">
					<div class="g-plus" data-action="share" data-annotation="none" data-height="24" data-href="http://plucial.com/u/<%=loginUserModel.getKey().getName() %>" data-onendinteraction="get_new_activitys_100"></div>
				</div>

				<hr>

				<h3>友達と共有せずに20件のみを取込む</h3>
				<p>友達と共有しない場合は下のリンクを使って過去の投稿20件を取込むことができます。</p>

				<div style="margin: 20px;text-align: right;" class="action-box">
					<a style="font-size: 12px;" href="#" onclick="get_new_activitys_40();return false;">20件のみを取込む</a>
				</div>
			</div>

		</section>
	</div>

	<!-- ajax -->
	<jsp:include page="/responsive/common/ajax_result.jsp" />
	<script type="text/javascript">
		// update profile ajax
		function get_new_activitys_100() {
			ajax_start();
			$.ajax({
				type : 'POST',
				url : '/account/ajax/getNewActivitys?num=5',
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
		// update profile ajax
		function get_new_activitys_40() {
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
