<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="com.appspot.plucial.utils.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
ActivityModel activityModel = (ActivityModel) request.getAttribute("activityModel");
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
				<div class="line">
					<h2>この投稿を削除しますか！？</h2>
					<p>削除すると元に戻すことができません。</p>
				</div>
			</div>

			<!-- activity -->
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
							<p><%=Utils.removeLinkTags(JspUtils.getActivityContentNotNull(activityModel.getAnnotationString())) %></p>
						<%}else { %>
							<p><%=Utils.removeLinkTags(JspUtils.getActivityContentNotNull(activityModel.getContentString())) %></p>
						<%} %>
					</div>

					<!-- 添付情報あり -->
					<%if(activityModel.isAttachmentsFlg()) { %>
						<div class="attachments-box">
							<!-- 画像情報あり -->
							<%if(activityModel.getAttachmentsImageUrlString() != null) {%>

								<!-- 写真付きの場合 -->
								<%if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO)) { %>
								<div class="fotorama" data-allowfullscreen="true" data-fit="scaledown">
									<%if(activityModel.getAttachmentsFullImageUrlString() == null) { %>
										<img src="<%=activityModel.getAttachmentsImageUrlString()%>">
									<%}else { %>
										<img src="<%=activityModel.getAttachmentsImageUrlString()%>" data-full="<%=activityModel.getAttachmentsFullImageUrlString()%>">
									<%} %>
								</div>

								<!-- アルバムの場合 -->
								<%}else if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM)) {%>
								<div class="fotorama" data-allowfullscreen="true" data-fit="scaledown">
									<%if(activityModel.getAlbumModelListRef() != null && activityModel.getAlbumModelListRef().getModelList() != null) { %>
										<%for(AlbumModel albumModel: activityModel.getAlbumModelListRef().getModelList()) { %>
										<img src="<%=Utils.changeAlbumUrl(albumModel) %>">
										<%} %>
									<%} %>
								</div>

								<!-- 動画付きの場合 -->
								<%}else if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)
									&& activityModel.getEmbedUrlString() != null
									&& activityModel.getEmbedUrlString().startsWith("https://www.youtube.com/")) {%>
								<div class="fotorama" data-nav="thumbs" data-allowfullscreen="true">
									<a href="<%=activityModel.getEmbedUrlString()%>" rel="nofollow" target="_blank">
										<img src="<%=activityModel.getAttachmentsImageUrlString()%>">
									</a>
								</div>

								<!-- その他（リンク付きで画像ありの場合） -->
								<%}else {%>
									<img src="<%=activityModel.getAttachmentsImageUrlString()%>">
								<%} %>
							<%}%>

							<%if(!activityModel.getAttachmentsType().getCategory().equals("photo")) { %>
							<div class="details attachments">
								<p><a href="<%=activityModel.getAttachmentsUrlString()%>" target="_blank" rel=”nofollow”><%=activityModel.getAttachmentsDisplayNameString() %></a></p>
								<p style="text-align: left;"><%=Utils.removeLinkTags(JspUtils.getNotNull(activityModel.getAttachmentsContentString())) %></p>
							</div>
							<%}%>
						</div>
					<%} %>
				</section>
			<!-- /activity -->

			<div class="welcome">
				<div class="action-box">
					<a href="#" class="input_activity_button" onclick="javascript:history.back();return false;" style="margin-right: 20px;">キャンセル</a>
					<a class="input_activity_button" href="/account/ajax/activityDelete?activity=<%=activityModel.getKey().getName() %>">削除</a>
				</div>
			</div>

		</div>
</body>
</html>
