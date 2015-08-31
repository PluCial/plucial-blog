<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
String userId = (String) request.getAttribute("userId");

List<DateModel> dateModelList = (List<DateModel>) request.getAttribute("dateModelList");

String dateCursor = null;
boolean dateHasNext = false;
if (request.getAttribute("date_cursor") != null && request.getAttribute("date_hasNext") != null) {
	dateCursor = (String) request.getAttribute("date_cursor");
	dateHasNext = Boolean.valueOf((String) request.getAttribute("date_hasNext"));
}
%>
					<%for(DateModel dateMenuModel:dateModelList) { %>
					<dd><a href="/u/<%=userId %>/<%=dateMenuModel.getDate() %>"><span><%=dateMenuModel.getDateJP() %></span></a></dd>
					<%} %>

					<%if(dateHasNext) { %>
					<dd class="read-more-date-box">
						<a class="next-contents-link" href="#" onClick="read_more_dates('/pub/ajax/getMoreDates?user=<%=userId %>&cursor=<%=dateCursor %>');return false;">
							<span>・・・</span>
						</a>
					</dd>
					<%} %>