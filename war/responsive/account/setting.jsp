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
<style type="text/css">
section#twitter div.action-box a.twitter-button {
color: #fff;
background-color: #55acee;
background-image: linear-gradient(rgba(0,0,0,0),rgba(0,0,0,0.05));
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#00000000, endColorstr=#0C000000)";
border: 1px solid #3b88c3;
box-shadow: inset 0 1px 0 rgba(255,255,255,0.1);
font-weight: normal;
text-shadow: none;
}

section#facebook div.action-box a.facebook-button {
color: #fff;
background-color: #3c5d96;
background-repeat: repeat-x;
background-image: linear-gradient(#6079ab,#3c5d96);
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#6079ab, endColorstr=#3c5d96, GradientType=0)";
border-color: #3c5a98;
border-bottom-color: #273b64;
text-align: left;
text-shadow: 0 -1px 1px rgba(0,0,0,0.5);
box-shadow: inset 0 1px 0 rgba(255,255,255,0.1);
}

section#evernote div.action-box a.evernote-button {
color: #fff;
background-color: #2dbe60;
background-image: linear-gradient(rgba(0,0,0,0),rgba(0,0,0,0.05));
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#00000000, endColorstr=#0C000000)";
border: 1px solid #1fae52;
box-shadow: inset 0 1px 0 rgba(255,255,255,0.1);
font-weight: normal;
text-shadow: none;
}
</style>
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
			<h1>アカウントの設定</h1>
		</div>

		<%if(loginUserModel.getTwitterAccessToken() == null || loginUserModel.getTwitterTokenSecret() == null) { %>
		<section id="twitter">
			<div class="details">
				<img src="/images/setting/twitter_back.png" width="100%">
				<h2>Twitterと連携する</h2>
				<p>お持ちのTwitterアカウントを連携すると、あなたのGoogle+での投稿が自動的にTwitterに投稿されます。</p>
				<p>インターバルは10分です。</p>

				<div class="action-box">
					<a class="twitter-button" href="/account/twitter/oAuth">Twitterと連携する</a>
				</div>
			</div>
		</section>
		<%}else { %>
		<section id="twitter">
			<div class="details">
				<img src="/images/setting/twitter_back.png" width="100%">
				<h2>Twitterと連携する</h2>
				<p>利用中のTwitterアカウント：<span style="font-weight:bold"><%=loginUserModel.getTwitterAccountName() %></span></p>
				<p>ハッシュタグモードに変更すると、#t が入っている投稿がTwitterに転送されます。</p>
				<p>Facebookへの連携もご利用の場合は #ft などのハッシュタグを使ってFacebookとTwitter両方に投稿を転送できます。</p>
				<div class="action-box">
					<label style="font-size:1.4em;"><input type="checkbox" name="twitterHashTag" id="twitterHashTag" onchange="twitter_mode_change();" <%=loginUserModel.isTwitterRepostHashtagFlg()? "checked" : "" %>>ハッシュタグモード</label>
				</div>
				<div class="action-box">
					<a href="/account/twitter/deleteAccount" class="input_activity_button">連携を解除</a>
				</div>
			</div>
		</section>
		<%} %>

		<%if(loginUserModel.getFacebookAccessToken() == null || loginUserModel.getFacebookAccountName() == null) { %>
		<section id="facebook">
			<div class="details">
				<img src="/images/setting/facebook_back.png" width="100%">
				<h2>Facebookと連携する</h2>
				<p>お持ちのFacebookアカウントを連携すると、あなたのGoogle+での投稿が自動的にFacebookに投稿されます。</p>
				<p>インターバルは10分です。</p>

 				<div class="action-box">
					<a class="facebook-button" href="/account/facebook/oAuth">Facebookと連携する</a>
				</div>
			</div>
		</section>
		<%}else { %>
		<section id="facebook">
			<div class="details">
				<img src="/images/setting/facebook_back.png" width="100%">
				<h2>Facebookと連携する</h2>
				<p>利用中のFacebookアカウント：<span style="font-weight:bold"><%=loginUserModel.getFacebookAccountName() %></span></p>
				<p>ハッシュタグモードに変更すると、#f が入っている投稿がFacebookに転送されます。</p>
				<p>Twitterへの連携もご利用の場合は #tf などのハッシュタグを使ってFacebookとTwitter両方に投稿を転送できます。</p>
				<div class="action-box">
					<label style="font-size:1.4em;"><input type="checkbox" name="facebookHashTag" onchange="facebook_mode_change();" <%=loginUserModel.isFacebookRepostHashtagFlg()? "checked" : "" %>>ハッシュタグモード</label>
				</div>
				<div class="action-box">
					<a href="/account/facebook/deleteAccount" class="input_activity_button">連携を解除</a>
				</div>
			</div>
		</section>
		<%} %>

		<%if(loginUserModel.getEvernoteAccessToken() == null) { %>
		<section id="evernote">
			<div class="details">
				<img src="/images/setting/evernote_back.png" width="100%">
				<h2>Evernoteと連携する</h2>
				<p>お持ちのEvernoteアカウントを連携すると、あなたのGoogle+での投稿が自動的にEvernoteに投稿されます。</p>
				<p>インターバルは6時間です。ただし、TwitterなどのSNSとの連携もご利用の場合はインターバルが10分になります。</p>

 				<div class="action-box">
					<a class="evernote-button" href="/account/evernote/oAuth">Evernoteと連携する</a>
				</div>
			</div>
		</section>
		<%}else { %>
		<section id="evernote">
			<div class="details">
				<img src="/images/setting/evernote_back.png" width="100%">
				<h2>Evernoteと連携する</h2>
				<p>ハッシュタグモードに変更すると、#e が入っている投稿がEvernoteに転送されます。</p>
				<p>TwitterやFacebookへの連携もご利用の場合は #tfe などのハッシュタグを使ってFacebookとTwitterとEvernoteに投稿を同時に転送できます。</p>
				<div class="action-box">
					<label style="font-size:1.4em;"><input type="checkbox" name="evernoteHashTag" onchange="evernote_mode_change();" <%=loginUserModel.isEvernoteRepostHashtagFlg()? "checked" : "" %>>ハッシュタグモード</label>
				</div>
				<div class="action-box">
					<a href="/account/evernote/deleteAccount" class="input_activity_button">連携を解除</a>
				</div>
			</div>
		</section>
		<%} %>

		<section>
			<div class="details">
				<h2>公開プロフィールの更新</h2>
				<p>Google+ に設定した公開プロフィールを元にPluCialのプロフィールを更新することができます。Google+ に設定した背景画像、アイコン画像、キャッチコピーなどを変更した場合にお使いください。</p>
				<div class="action-box">
					<a href="#" class="input_activity_button" onclick="update_user_profile();return false;">プロフィールの更新！</a>
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
		function update_user_profile() {
			ajax_start();
			$.ajax({
				type : 'POST',
				url : '/account/ajax/updateUserProfile',
				dataType : 'html',
				timeout : 1000000000,
				success : function(result) {
					reset_pop();
				},
				error : function() {
					reset_pop();
				}
			});
		};

		function twitter_mode_change() {
			ajax_start();
			$.ajax({
				type : 'POST',
				url : '/account/ajax/twitterModeChange',
				dataType : 'html',
				timeout : 1000000000,
				success : function(result) {
					reset_pop();
				},
				error : function() {
					reset_pop();
				}
			});
		};

		function facebook_mode_change() {
			ajax_start();
			$.ajax({
				type : 'POST',
				url : '/account/ajax/facebookModeChange',
				dataType : 'html',
				timeout : 1000000000,
				success : function(result) {
					reset_pop();
				},
				error : function() {
					reset_pop();
				}
			});
		}

		function evernote_mode_change() {
			ajax_start();
			$.ajax({
				type : 'POST',
				url : '/account/ajax/evernoteModeChange',
				dataType : 'html',
				timeout : 1000000000,
				success : function(result) {
					reset_pop();
				},
				error : function() {
					reset_pop();
				}
			});
		}
	</script>

	<!-- adsense base -->
	<jsp:include page="/responsive/common/adsense_base.jsp" />
	<!-- /adsense base -->
</body>
</html>
