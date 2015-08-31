<?xml version='1.0' encoding='utf-8'?>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
List<ActivityModel> activityList = (List<ActivityModel>) request.getAttribute("activityList");
UserModel userModel = (UserModel) request.getAttribute("userModel");
String baseURL = (String) request.getAttribute("baseURL");
String type = (String) request.getAttribute("type");
String dateValue = new java.text.SimpleDateFormat( "yyyy", java.util.Locale.US).format(new Date());
%>
<rss version='2.0'>
    <channel>
    	<%if(type == null) { %>
        <title>Google+ <%=userModel.getDisplayName() %></title>
        <description>Google+ <%=userModel.getDisplayName() %></description>
        <%}else { %>
        <title>Google+ <%=userModel.getDisplayName() %> <%=type %></title>
        <description>Google+ <%=userModel.getDisplayName() %> <%=type %></description>
        <%} %>
        <link><%=baseURL %>u/<%=userModel.getKey().getName() %>/</link>
        <language>ja</language>
        <copyright>(C) <%=dateValue %> PluCial. All rights reserved.</copyright>
         <managingEditor>info@plucial.com</managingEditor>
        <%
        if(activityList != null) {
        	for(ActivityModel activityModel: activityList) {
        		if(activityModel.isPublicFlg()) {
        			String lastmod = new java.text.SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss Z", java.util.Locale.US).format(activityModel.getPublished());
		%>
        <item>
        	<%if(activityModel.isAttachmentsFlg() && activityModel.getAttachmentsDisplayNameString() != null) { %>
			<title><%=JspUtils.subContentsString(activityModel.getAttachmentsDisplayNameString(), 50) %></title>
			<%}else if(activityModel.getTitleString() != null) { %>
			<title><%=JspUtils.subContentsString(activityModel.getTitleString(), 50) %></title>
			<%} %>
            <link><%=activityModel.getUrlString() %></link>
            <comments><%=activityModel.getUrlString() %></comments>
            <pubDate><%=lastmod %></pubDate>
        </item>
        <%} %>
        <%} %>
        <%} %>
</channel>
</rss>
