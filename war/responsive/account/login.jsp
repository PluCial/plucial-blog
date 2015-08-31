<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
String oauthUrl = (String) request.getAttribute("oauthUrl");
%>
<html lang="ja">
<head>
<!-- main header -->
<jsp:include page="/responsive/common/html_head.jsp" />
<!-- /main header -->
<link href="/css/responsive/info.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/css/responsive/account.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript">
   (function () {
	   var po = document.createElement('script');
	   po.type = 'text/javascript';
	   po.async = true;
	   po.src = 'https://plus.google.com/js/client:plusone.js?onload=start';
	   var s = document.getElementsByTagName('script')[0];
	   s.parentNode.insertBefore(po, s);
	   })();
</script>
</head>
<body>
	<!-- main header -->
	<jsp:include page="/responsive/common/main_header.jsp" />
	<!-- /main header -->

	<!-- main menu -->
	<jsp:include page="/responsive/common/main_menu.jsp" />
	<!-- /main menu -->

	<div id="contents">

		<section>
			<div class="details">
				<img src="/images/socialmedia.png" width="100%" />
				<h2>PluCialログイン</h2>
				<p>PluCialにユーザー登録もしくはログインするにはGoogle+アカウントを使ってログインしてください。</p>
				<p>また、PluCialを利用する場合は、以下の規約に同意したことを意味します。以下の規約を注意してお読みください。</p>
				<p style="text-align: center;"><a href="/info/agreement" >PluCialの規約</a></p>
			</div>

			<div class="details">
				<!-- Google+ ログインボタン Start -->
					<div id="signinButton" style="margin: 20px 0;text-align: center;">
<%-- 						<span class="g-signin"
							data-scope="<%=Constants.GOOGLE_PLUS_API_SCOPE %> <%=Constants.GOOGLE_URLSHORTENER_API_SCOPE %>"
							data-clientid="<%=Constants.GOOGLE_PROJECT_CLIENT_ID %>"
							data-redirecturi="postmessage"
							data-accesstype="offline"
							data-approvalprompt="force"
							data-cookiepolicy="single_host_origin"
							data-cookiepolicy="<%=Constants.GOOGLE_DATA_COOKIEPOLICY %>"
							data-callback="signInCallback"></span> --%>
							<a href="<%=oauthUrl %>" class="g-signin"><img alt="Sign in Google" src="/images/google-sign-in-button.png" style="width: 200px;" /></a>
					</div>
					<!-- Google+ ログインボタン End -->
			</div>

			<div class="details">
				<h2>PluCialからのお知らせ</h2>
				<p>PluCialのGoogle+ ページをフォローすると最新情報を受け取れます。</p>
				<div style="text-align: center;padding: 30px 0;">
					<div class="g-page" data-width="273" data-href="//plus.google.com/u/0/111586968979366611293" data-layout="landscape" data-rel="publisher"></div>
				</div>
			</div>

			<div class="details">
				<h2>フィードバック</h2>
				<p>バグ報告やご要望がある場合は以下のコミュニティをご利用ください。</p>
				<div style="text-align: center;padding: 30px 0;">
					<div class="g-community" data-width="273" data-href="https://plus.google.com/communities/114206134116790035982" data-layout="landscape"></div>
				</div>
			</div>

		</section>
	</div>

	<!-- adsense base -->
	<jsp:include page="/responsive/common/adsense_base.jsp" />
	<!-- /adsense base -->

	<jsp:include page="/responsive/common/ajax_result.jsp" />

	<!-- ユーザーログイン後のコールバック Start -->
<!-- 		<script type="text/javascript">
			function signInCallback(authResult) {
			// デバッグ用ログの表示
			console.log(authResult);

  			if (authResult['code']) {

				 // ユーザーが認証されたのでログイン ボタンを非表示にします。
				$('#lean_overlay').css({"display":"block", "display":"-moz-box", "display":"-webkit-box"});
       			$('#signinButton').attr('style', 'display: none');

    				// コードをサーバーに送信します
      				$.ajax({
      					type: 'POST',
      					url: '/account/userLogin',
      					data: {'code': authResult['code']},
      					success: function(result) {
      						window.location = "/account/loggedIn";
      					},
      					error : function(){
      						$('#result').html('ログイン失敗しました。');
      					}
    				});
  			} else if (authResult['error']) {
    			// エラーが発生しました。
    			// 可能性のあるエラー コード:
    			//   「access_denied」 - ユーザーがアプリへのアクセスを拒否しました
    			//   「immediate_failed」 - ユーザーを自動的にログインできませんでした
    			// console.log（「There was an error: 」 + authResult[「エラー」]）;
  			}
		}
		</script> -->
	<!-- ユーザーログイン後のコールバック End -->
</body>
</html>
