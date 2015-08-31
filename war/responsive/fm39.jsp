<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
UserModel acsessUserModel = (UserModel) request.getAttribute("acsessUserModel");

UserModel loginUserModel = null;
boolean isLogin = Boolean.valueOf((String) request.getAttribute("isLogin"));
if(isLogin) {
	loginUserModel = (UserModel) request.getAttribute("loginUserModel");
}
%>
<html lang="ja">
<head>
<!-- main header -->
<jsp:include page="/responsive/common/html_head.jsp" />
<meta property="og:image" content="http://plucial.com/images/thank-you.jpg" />
<!-- /main header -->
</head>
<body>
	<!-- main header -->
	<jsp:include page="/responsive/common/main_header.jsp" />
	<!-- /main header -->

	<!-- main menu -->
	<jsp:include page="/responsive/common/main_menu.jsp" />
	<!-- /main menu -->

	<!-- main menu -->
	<jsp:include page="/responsive/common/user_profile_section.jsp" />
	<!-- /main menu -->

	<div id="contents">
		<%if(isLogin && loginUserModel.getKey().getName().equals(acsessUserModel.getKey().getName())) { %>
		<div class="welcome">
				<div>
					<h2>このページを使ってフォロワーに感謝の気持ちを伝えよう</h2>
					<p>このページをフォローしてくれた方に送ると、フォローしてくれたお礼と共に自分のプロフィール、自分の他のメディア（ブログやソーシャルメディア）、自分の最近の投稿などを同時に見てもらうことができます。</p>
					<p>このページをフォロワーに送る場合は以下の共有ボタンをご利用ください。また、ブラウザによって下の共有ボタンがうまく動作しない場合はこのページのURLをコピーしてお使いください。</p>
					<div style="text-align: right;margin-top: 10px;">
						<span
							class="g-interactivepost"
							data-contenturl="http://plucial.com/fm39/<%=loginUserModel.getKey().getName() %>/"
							data-contentdeeplinkid="/fm39/<%=loginUserModel.getKey().getName() %>/"
							data-clientid="<%=Constants.GOOGLE_PROJECT_CLIENT_ID %>"
							data-cookiepolicy="single_host_origin"
							data-prefilltext="フォローして頂きありがとうございます。改めて自己紹介をさせてください。"
							data-calltoactionlabel="READ_MORE"
							data-calltoactionurl="http://plucial.com/fm39/<%=loginUserModel.getKey().getName() %>/">
							<span class="icon">&nbsp;</span>
							<span class="label">共有しよう！</span>
						</span>
					</div>
				</div>
			</div>
			<hr />
			<%} %>

			<div class="welcome">
				<div class="line profile-img-box">
					<img class="profile-img" src="<%=JspUtils.changeProfileImageSize(acsessUserModel.getImageUrlString(), 50) %>" alt="<%=acsessUserModel.getDisplayName() %>" />
				</div>
				<div class="line">
					<h2>フォローして頂きありがとうございます！</h2>
					<p>最近私のGoogle+での投稿です。よかったら見てください♪</p>
				</div>
			</div>

			<!-- activity -->
			<jsp:include page="/responsive/common/activity.jsp" />
			<!-- /activity -->

			<div class="welcome">
				<div class="line profile-img-box">
					<img class="profile-img" src="<%=JspUtils.changeProfileImageSize(acsessUserModel.getImageUrlString(), 50) %>" alt="<%=acsessUserModel.getDisplayName() %>" />
				</div>
				<div class="line">
					<h2>最後まで読んで頂きありがとうございます。</h2>
					<p>これから宜しくお願いします。</p>
				</div>
			</div>

			<hr />

			<div class="welcome" style="margin-top: 0;">
				<div>
					<h2>PluCial のプロモーション機能</h2>
					<p style="font-size: 1.4em;line-height:1.8em;">PluCial のプロモーション機能を使うとご利用の各ソーシャルメディアのフォロワーにもっと自分のことを知ってもらうことができます。</p>
					<p style="font-size: 1.4em;line-height:1.8em;">この機能を利用するにはPluCialに登録する必要があります。</p>
					<p style="font-size: 1.4em;">
						<a href="/" style="background-repeat: no-repeat;background-position: left center;background-size: 15px auto;background-image: url(/images/new-window.png);">
							<span style="margin-left: 30px;">PluCial とは</span>
						</a>
					</p>
				</div>
			</div>

			<!-- adsense box -->
			<jsp:include page="/responsive/common/advertisement_box.jsp" />
			<!-- /adsense box -->

		</div>

		<!-- adsense base -->
		<jsp:include page="/responsive/common/adsense_base.jsp" />
		<!-- /adsense base -->
</body>
</html>
