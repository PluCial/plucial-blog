<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
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
			<h1>PluCialを退会する</h1>
		</div>

		<section>
			<div class="details">

					<h2>データの削除</h2>
					<p>PluCialを退会すると、利用者がPluCialに取込んだすべてのデータが削除されます。再度これらのデータを復元することはできませんのでご注意ください。</p>
					<ul>
						<li>利用者のGoogle+のプロフィール情報</li>
						<li>利用者がPluCialに取込んだすべてのアクティビティ</li>
						<li>利用者のAdSense情報（AdSense情報を設定した場合）</li>
					</ul>

					<h2>PluCialに付与した権限について</h2>
					<p>退会処理を行うとPluCialアプリが自動的にGoogle+から切断されます。また利用者がPluCialアプリに対して付与したすべての権限が無効になります。</p>

					<div class="action-box">
						<a href="#" class="input_activity_button"
							onclick="user_delete();return false;">PluCialを退会する</a>
					</div>
			</div>

		</section>

		<!-- adsense box -->
		<jsp:include page="/responsive/common/advertisement_box.jsp" />
		<!-- /adsense box -->
	</div>

	<!-- ajax -->
	<jsp:include page="/responsive/common/ajax_result.jsp" />
	<script type="text/javascript">
		function user_delete() {
			ajax_start();
			$.ajax({
				type : 'POST',
				url : '/account/ajax/userDelete',
				dataType : 'html',
				timeout : 1000000000,
				success : function(result) {
					window.location = "/";
				},
				error : function() {
					reset_pop();
				}
			});
		};
		</script>

	<!-- adsense base -->
	<jsp:include page="/responsive/common/adsense_base.jsp" />
	<!-- /adsense base -->
</body>
</html>
