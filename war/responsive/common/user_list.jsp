<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
List<UserModel> userList = (List<UserModel>) request.getAttribute("userList");

String userCursor = null;
boolean userHasNext = false;
if (request.getAttribute("user_cursor") != null && request.getAttribute("user_hasNext") != null) {
	userCursor = (String) request.getAttribute("user_cursor");
	userHasNext = Boolean.valueOf((String) request.getAttribute("user_hasNext"));
}
%>

				<%for(UserModel userModel: userList) { %>
				<section>
					<div class="profile-contents">
						<div>
							<a href="/u/<%=userModel.getKey().getName() %>/">
								<img src="<%=JspUtils.changeProfileImageSize(userModel.getImageUrlString(), 80) %>" alt="<%=userModel.getDisplayName() %>" />
							</a>
						</div>
						<div class="user-contents">
							<h2><a href="/u/<%=userModel.getKey().getName() %>/"><%=userModel.getDisplayName() %></a></h2>
							<p><a href="/u/<%=userModel.getKey().getName() %>/"><%=userModel.getTaglineString() != null ? userModel.getTaglineString() : "" %></a></p>
						</div>
					</div>

					<div class="action-box">
						<a class="link-botton" href="/u/<%=userModel.getKey().getName() %>/">View</a>
					</div>
				</section>
				<%} %>

				<%if(userHasNext) { %>
				<div class="read-more-activity-box">
					<a class="next-contents-link" href="#" onClick="read_more_users('/info/ajax/getUserList?cursor=<%=userCursor %>');return false;">もっと読み込む</a>
				</div>
				<%} %>