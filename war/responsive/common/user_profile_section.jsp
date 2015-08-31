<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
UserModel acsessUserModel = (UserModel) request.getAttribute("acsessUserModel");
List<UserUrlsModel> profileUrlList = (List<UserUrlsModel>) request.getAttribute("profileUrlList");
List<UserUrlsModel> contributorUrlList = (List<UserUrlsModel>) request.getAttribute("contributorUrlList");
%>

			<section id="user-profile-section" style="background-image: url(/images/backstretch.png), url(<%=acsessUserModel.getCoverPhotoUrlString() %>);">

				<div id="user-profile-contents">
					<div id="user-profile-img-box">
						<a href="/u/<%=acsessUserModel.getKey().getName() %>/">
							<img class="profile-img" src="<%=JspUtils.changeProfileImageSize(acsessUserModel.getImageUrlString(), 80) %>" alt="<%=acsessUserModel.getDisplayName() %>" />
						</a>
					</div>

					<h2><%=acsessUserModel.getDisplayName() %></h2>
					<p id="tagline-string"><%=acsessUserModel.getTaglineString() != null ? acsessUserModel.getTaglineString() : "" %></p>

					<div id="user-social-list">
						<a href="<%=acsessUserModel.getUrlString() %>" target="_blank" style="text-decoration:none;">
							<img src="/images/footer/googlePlus.png" alt="Google+" style="border:0;width:40px;height:40px;">
						</a>
						<%if(profileUrlList != null && profileUrlList.size() > 0) { %>
							<%for(UserUrlsModel userUrlsModel: profileUrlList) { %>
								<%if(JspUtils.isTwitterUrl(userUrlsModel.getValueString())) { %>
						<a href="<%=userUrlsModel.getValueString() %>" target="_blank" style="text-decoration:none;">
							<img src="/images/footer/twitter.png" style="border:0;width:40px;height:40px;">
						</a>
								<%}else if(JspUtils.isFaceBookUrl(userUrlsModel.getValueString())) { %>
						<a href="<%=userUrlsModel.getValueString() %>" target="_blank" style="text-decoration:none;">
							<img src="/images/footer/facebook.png" style="border:0;width:40px;height:40px;">
						</a>
							<%} %>
					<%} %>
					<%} %>
				</div>

				<%if(contributorUrlList != null && contributorUrlList.size() > 0) { %>
				<div id="contributor">
					<dl>
						<dt>他の投稿先</dt>
						<%for(UserUrlsModel userUrlsModel: contributorUrlList) { %>
						<dd><a href="<%=userUrlsModel.getValueString() %>" target="_blank"><span><%=userUrlsModel.getLabelString() %></span></a></dd>
						<%} %>
					</dl>
				</div>
				<%} %>
				</div>
			</section>