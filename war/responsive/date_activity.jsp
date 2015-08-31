<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
UserModel acsessUserModel = (UserModel) request.getAttribute("acsessUserModel");
List<ActivityModel> newActivityModelList = (List<ActivityModel>) request.getAttribute("newActivityModelList");

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

			<%if(newActivityModelList != null && newActivityModelList.size() > 0) { %>
			<hr>
			<div class="welcome">
				<div class="line profile-img-box">
					<img class="profile-img" src="<%=JspUtils.changeProfileImageSize(acsessUserModel.getImageUrlString(), 50) %>" alt="<%=acsessUserModel.getDisplayName() %>" />
				</div>
				<div class="line">
					<h2><%=acsessUserModel.getDisplayName() %> の最近の投稿</h2>
				</div>
			</div>
			<%	for(ActivityModel activityModel: newActivityModelList) {%>
				<section>
					<header>
						<div class="go-g-link-box">
 							<a href="<%=activityModel.getUrlString() %>" target="_blank" rel="nofollow"><img width="13px" src="/images/new-window.png"></a>
						</div>

						<div class="activity-time-box">
							<%
							String thisDateTime = new java.text.SimpleDateFormat("yyyy-MM-dd",
								java.util.Locale.US).format(activityModel.getPublished());
							String thisDateValue = new java.text.SimpleDateFormat(
							"yyyy年MM月dd日 HH時mm分ss秒", java.util.Locale.US).format(activityModel.getPublished());
							%>
							<time datetime="<%=thisDateTime%>"><%=thisDateValue%></time>
						</div>
					</header>

					<div class="details">
						<%if(activityModel.isShareActorFlg() && activityModel.getAnnotationString() != null && !activityModel.getAnnotationString().trim().equals("")) { %>
							<p><%=JspUtils.getActivityContentNotNull(activityModel.getAnnotationString()) %></p>
						<%}else { %>
							<p><%=JspUtils.getActivityContentNotNull(activityModel.getContentString()) %></p>
						<%} %>
					</div>

					<!-- 添付情報あり -->
					<%if(activityModel.isAttachmentsFlg()) { %>
						<div class="attachments-box">
							<%if(activityModel.getAttachmentsImageUrlString() != null) {%>
								<%if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)) { %>
								<a class="g-photo" href="<%=activityModel.getAttachmentsImageUrlString()%>" rel="nofollow">
									<img src="<%=activityModel.getAttachmentsImageUrlString()%>">
								</a>

								<%}else if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)
									&& activityModel.getEmbedUrlString() != null
									&& activityModel.getEmbedUrlString().startsWith("https://www.youtube.com/")) {%>
 								<a class="various fancybox.iframe" href="<%=activityModel.getEmbedUrlString()%>?autoplay=1" rel="nofollow" target="_blank">
									<img src="<%=activityModel.getAttachmentsImageUrlString()%>">
								</a>
								<%}%>
							<%}%>

							<%if(!activityModel.getAttachmentsType().getCategory().equals("photo")) { %>
							<div class="details attachments">
								<p><a href="<%=activityModel.getAttachmentsUrlString()%>" target="_blank" rel=”nofollow”><%=activityModel.getAttachmentsDisplayNameString() %></a></p>
								<p style="text-align: left;"><%=JspUtils.getNotNull(activityModel.getAttachmentsContentString()) %></p>
							</div>
							<%}%>
						</div>
					<%} %>
				</section>
			<%} %>

			<!-- adsense box -->
			<jsp:include page="/responsive/common/advertisement_box.jsp" />
			<!-- /adsense box -->
			<%} %>

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
