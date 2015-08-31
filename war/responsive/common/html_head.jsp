<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
UserModel acsessUserModel = (UserModel) request.getAttribute("acsessUserModel");

String pageTitle = (String) request.getAttribute("pageTitle");
String pageDescription = (String) request.getAttribute("pageDescription");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=pageTitle %> | PluCial</title>
<link rel="icon" type="image/png" href="/favicon.png" >

<!-- SP -->
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum=1, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<!-- /SP -->

<!-- OGP -->
<meta property="og:title" content="<%=pageTitle %> | PluCial" />
<meta property="og:type" content="article" />
<meta property="og:site_name" content="PluCial">
<meta property="og:email" content="info@plucial.com">
<%if(acsessUserModel != null) { %>
<meta property="og:image" content="<%=acsessUserModel.getCoverPhotoUrlString() != null? acsessUserModel.getCoverPhotoUrlString() : "/images/backstretch.png" %>" />
<%} %>
<%if(pageDescription != null) { %>
<meta property="og:description" content="<%=pageDescription%> | PluCial">
<%} %>
<!-- /OGP -->

<!-- CSS -->
<link href="/css/responsive/reset.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/css/responsive/common.css" rel="stylesheet" type="text/css" media="screen" />
<!-- /CSS -->

<!-- JavaScript -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script src="/js/responsive/common_script.js"></script>
<!-- /JavaScript -->


<!-- RSS -->
<%if(acsessUserModel != null) { %>
<link rel="alternate" type="application/rss+xml" title="Google+ <%=acsessUserModel.getDisplayName() %> all" href="/rss/<%=acsessUserModel.getKey().getName() %>/">
<link rel="alternate" type="application/rss+xml" title="Google+ <%=acsessUserModel.getDisplayName() %> <%=Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST %>" href="/rss/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST %>">
<link rel="alternate" type="application/rss+xml" title="Google+ <%=acsessUserModel.getDisplayName() %> <%=Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE %>" href="/rss/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE %>">
<link rel="alternate" type="application/rss+xml" title="Google+ <%=acsessUserModel.getDisplayName() %> <%=Constants.GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE %>" href="/rss/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE %>">
<link rel="alternate" type="application/rss+xml" title="Google+ <%=acsessUserModel.getDisplayName() %> <%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO %>" href="/rss/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO %>">
<link rel="alternate" type="application/rss+xml" title="Google+ <%=acsessUserModel.getDisplayName() %> <%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO %>" href="/rss/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO %>">
<link rel="alternate" type="application/rss+xml" title="Google+ <%=acsessUserModel.getDisplayName() %> <%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE %>" href="/rss/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE %>">
<%} %>
<!-- /RSS -->

<link  href="http://fotorama.s3.amazonaws.com/4.5.2/fotorama.css" rel="stylesheet">
<script src="http://fotorama.s3.amazonaws.com/4.5.2/fotorama.js"></script>
