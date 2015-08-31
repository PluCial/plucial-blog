<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
List<ActivityModel> activityList = (List<ActivityModel>) request.getAttribute("activityList");
String thisPageUrl = (String)request.getAttribute("thisPageUrl");

String userId = (String) request.getAttribute("userId");
String contentsType = (String) request.getAttribute("contentsType");

UserModel acsessUserModel = (UserModel) request.getAttribute("acsessUserModel");
boolean isLogin = Boolean.valueOf((String) request.getAttribute("isLogin"));
UserModel loginUserModel = null;
if(isLogin) {
	loginUserModel = (UserModel) request.getAttribute("loginUserModel");
}

String cursor = null;
boolean hasNext = false;
if (request.getAttribute("cursor") != null && request.getAttribute("hasNext") != null) {
	cursor = (String) request.getAttribute("cursor");
	hasNext = Boolean.valueOf((String) request.getAttribute("hasNext"));
}
%>

			<%
			if(activityList != null) {
			for(ActivityModel activityModel: activityList) {%>
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

					<%if(isLogin && acsessUserModel.getKey().getName().equals(loginUserModel.getKey().getName())) { %>
					<footer>
						<a href="/account/activityDelete?activity=<%=activityModel.getKey().getName() %>">削除</a>
					</footer>
					<%} %>
				</section>
			<%
				}
			} %>

			<%if(hasNext) { %>
				<div class="read-more-activity-box">
					<a class="next-contents-link" href="#" onClick="read_more_activity('/pub/ajax/getMoreActivitys?user=<%=userId %>&type=<%=contentsType%>&cursor=<%=cursor %>');return false;">もっと読み込む</a>
				</div>
			<%} %>
