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

	<div id="contents" style="margin-top: 0;">

			<!-- activity -->
			<jsp:include page="/responsive/common/activity.jsp" />
			<!-- /activity -->

<%-- 			<div class="welcome">
				<div class="line profile-img-box">
					<img class="profile-img" src="<%=JspUtils.changeProfileImageSize(acsessUserModel.getImageUrlString(), 50) %>" alt="<%=acsessUserModel.getDisplayName() %>" />
				</div>
				<div class="line">
					<h2>私の投稿をご覧頂きありがとうございます！</h2>
					<p>私の他の投稿をまとめて見るには <a href="/u/<%=acsessUserModel.getKey().getName() %>">こちら</a></p>
				</div>
			</div> --%>

			<!-- adsense box -->
			<jsp:include page="/responsive/common/advertisement_box.jsp" />
			<!-- /adsense box -->

			<hr />

			<div class="welcome" style="margin-top: 0;">
				<div>
					<h2>あなたもPluCialを使ってみませんか！？</h2>
					<p style="font-size: 1.4em;line-height:1.8em;">PluCial はGoogle+の投稿を自動的にブログとして保存し、TwitterやFacebookに自動転送するサービスです。</p>
					<p style="font-size: 1.4em;line-height:1.8em;">Google+のアカウントがあればどなたでも利用できます。(無料)</p>
					<p style="font-size: 1.4em;">
						<a href="/" style="background-repeat: no-repeat;background-position: left center;background-size: 15px auto;background-image: url(/images/new-window.png);">
							<span style="margin-left: 30px;">詳しくはこちら</span>
						</a>
					</p>
				</div>
			</div>

		</div>

		<!-- adsense base -->
		<jsp:include page="/responsive/common/adsense_base.jsp" />
		<!-- /adsense base -->
</body>
</html>
