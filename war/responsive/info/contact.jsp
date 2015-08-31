<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
String dateValue = new java.text.SimpleDateFormat( "yyyy", java.util.Locale.US).format(new Date());
String name = (String)request.getAttribute("name");
String email = (String)request.getAttribute("email");
String subject = (String)request.getAttribute("subject");
String message = (String)request.getAttribute("message");
String error = (String)request.getAttribute("error");
%>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Google+の投稿を他のSNSに自動転送し自分の投稿を一元管理するサービス" />
	<meta name="keywords" content="plucial,プラシャル,google+,blog,ブログ" />
    <meta property="og:image" content="http://plucial.com/images/index/140926-0004.png" />

    <title>PluCial - Google+を起点とした新しいソーシャルライフの形(無料)</title>

    <!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
		<script src="/js/alpha/jquery.min.js"></script>
		<script src="/js/alpha/jquery.dropotron.min.js"></script>
		<script src="/js/alpha/jquery.scrollgress.min.js"></script>
		<script src="/js/alpha/skel.min.js"></script>
		<script src="/js/alpha/skel-layers.min.js"></script>
		<script src="/js/alpha/init.js"></script>
		<noscript>
			<link rel="stylesheet" href="/css/alpha/skel.css" />
			<link rel="stylesheet" href="/css/alpha/style.css" />
			<link rel="stylesheet" href="/css/alpha/style-wide.css" />
		</noscript>
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->

  </head>

  <div id="skel-layers-wrapper" style="position: absolute; left: 0px; right: 0px; top: 0px;">

		<!-- Header -->
			<header id="header">
				<h1><a href="/">PluCial</a></h1>
				<nav id="nav">
					<ul>
						<li><a href="/">ホーム</a></li>
						<li>
							<a href="" class="icon fa-angle-down">メニュー</a>
							<ul>
								<li><a href="/info/agreement">利用規約</a></li>
								<li><a href="/info/privacy">プライバシーポリシー</a></li>
								<li><a href="/info/about">運用者情報</a></li>
 								<li><a href="/info/contact">お問い合わせ</a></li>
							</ul>
						</li>
						<li><a href="/account/login" class="button">ログイン</a></li>
					</ul>
				</nav>
			</header>

		<!-- Main -->
			<section id="main" class="container small">
				<header>
					<h2>お問い合わせ</h2>
					<p>すべての項目を正しくご記入の上送信してください。</p>
				</header>
				<div class="box">
					<form action="/info/contactMsgSend" method="post">
						<div class="row uniform half collapse-at-2">
							<div class="6u">
								<input type="text" name="name" placeholder="Name" value="<%=name == null ? "" : name %>" />
							</div>
							<div class="6u">
								<input type="text" name="email" placeholder="Email" value="<%=email == null ? "" : email %>" />
							</div>
						</div>
						<div class="row uniform half">
							<div class="12u">
								<input type="text" name="subject" placeholder="Subject" value="<%=subject == null ? "" : subject %>" />
							</div>
						</div>
						<div class="row uniform half">
							<div class="12u">
								<textarea name="message" placeholder="Message" rows="7"><%=message == null ? "" : message %></textarea>
							</div>
						</div>
						<div class="row uniform">
							<div class="12u">
								<p style="color: red"><%=error == null ? "" : error %></p>
								<ul class="actions align-center">
									<li><input type="submit" value="送信" /></li>
								</ul>
							</div>
						</div>
					</form>
				</div>
			</section>


		<!-- Footer -->
			<footer id="footer">
				<ul class="icons">
					<li><a href="https://plus.google.com/+Plucial/posts" class="icon fa-google-plus"><span class="label">Google+</span></a></li>
					<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
					<li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
				</ul>
				<ul class="copyright">
					<li>Copyright &copy; PluCial <%=dateValue %>. All Rights Reserved.</li>
				</ul>
			</footer>


</div>
</body>

</html>
