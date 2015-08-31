<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
UserModel acsessUserModel = (UserModel) request.getAttribute("acsessUserModel");

String pageTitle = (String) request.getAttribute("pageTitle");

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

	<!-- profile -->
	<jsp:include page="/responsive/common/user_profile_section.jsp" />
	<!-- /profile -->

	<div id="contents">

			<div class="welcome">
				<div class="line profile-img-box">
					<img class="profile-img" src="<%=JspUtils.changeProfileImageSize(acsessUserModel.getImageUrlString(), 50) %>" alt="<%=acsessUserModel.getDisplayName() %>" />
				</div>
				<div class="line">
					<h2><%=pageTitle %></h2>
				</div>
			</div>

			<!-- activity -->
			<div id="activity-contents">
				<jsp:include page="/responsive/common/activity.jsp" />
			</div>
			<!-- /activity -->

			<!-- adsense box -->
			<jsp:include page="/responsive/common/advertisement_box.jsp" />
			<!-- /adsense box -->

		</div>

		<!-- sharebar -->
		<jsp:include page="/responsive/common/sharebar.jsp" />
		<!-- /sharebar -->

		<!-- adsense base -->
		<jsp:include page="/responsive/common/adsense_base.jsp" />
		<!-- /adsense base -->

		<!-- common_jsp -->
		<jsp:include page="/responsive/common/common_script.jsp" />
		<!-- /common_jsp -->

		<jsp:include page="/responsive/common/ajax_result.jsp" />
</body>
</html>
