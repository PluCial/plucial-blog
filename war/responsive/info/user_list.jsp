<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
List<UserModel> userList = (List<UserModel>)request.getAttribute("userList");
String pageTitle = (String) request.getAttribute("pageTitle");

%>
<html lang="ja">
<head>
<!-- main header -->
<jsp:include page="/responsive/common/html_head.jsp" />
<!-- /main header -->
<link href="/css/responsive/userlist.css" rel="stylesheet" type="text/css" media="screen" />
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
				<div class="line">
					<h2>PluCial <%=pageTitle %></h2>
				</div>
			</div>

			<!-- adsense box -->
			<jsp:include page="/responsive/common/advertisement_box.jsp" />
			<!-- /adsense box -->

			<!-- user list -->
			<div id="activity-contents">
				<jsp:include page="/responsive/common/user_list.jsp" />
			</div>
			<!-- /user list -->

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
</body>
</html>
