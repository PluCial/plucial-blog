<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.service.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%
List<UserModel> userList = (List<UserModel>)request.getAttribute("userList");
%>
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
	<%
	for(UserModel userModel: userList) {
	%>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/post</loc>
		<changefreq>daily</changefreq>
		<priority>0.8</priority>
	</url>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/share</loc>
		<changefreq>daily</changefreq>
		<priority>0.8</priority>
	</url>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/note</loc>
		<changefreq>daily</changefreq>
		<priority>0.8</priority>
	</url>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/photo</loc>
		<changefreq>daily</changefreq>
		<priority>0.8</priority>
	</url>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/video</loc>
		<changefreq>daily</changefreq>
		<priority>0.8</priority>
	</url>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/article</loc>
		<changefreq>daily</changefreq>
		<priority>0.8</priority>
	</url>
	<%
		List<DateModel> dateList = DateService.getDateModelList(userModel);
		if(dateList != null && dateList.size() > 0) {
	%>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/</loc>
		<lastmod><%=dateList.get(0).getLastmodOfFormat() %></lastmod>
		<changefreq>daily</changefreq>
		<priority>0.5</priority>
	</url>
	<% 		for(DateModel dateModel: dateList) {%>
	<url>
		<loc>http://plucial.com/u/<%=userModel.getKey().getName() %>/<%=dateModel.getDate() %></loc>
		<lastmod><%=dateModel.getLastmodOfFormat() %></lastmod>
		<changefreq>monthly</changefreq>
		<priority>0.5</priority>
	</url>
	<%		} %>
	<%	} %>
	<%} %>
</urlset>
