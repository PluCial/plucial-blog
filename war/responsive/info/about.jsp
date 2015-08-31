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
				<div class="line profile-img-box">
					<img class="profile-img" src="https://lh5.googleusercontent.com/-erS-KYCKu08/AAAAAAAAAAI/AAAAAAAAEWg/jqicu1PtHMU/photo.jpg?sz=70" alt="Takahara Kou" />
				</div>
				<div class="line">
					<h1>高原 功(Takahara Kou)と申します。</h1>
					<p>システムエンジニア</p>
				</div>
			</div>

			<section>
				<div class="details">
					<h3>どんな人？</h3>
					<p>O型(血液型)の見た目がちょっとだけハンサムのシステムエンジニアです。(笑)</p>
					<p>10年前にホームレスになっていた所、ある東京のシステム会社様に拾って頂きました。それから恩返しできるように精一杯システムエンジニアとして生きてきました。頑張ったかいもあって、今は妻とかわいい娘２人と平和に暮らしています。</p>
					<p>最近は、日々システムエンジニアとしての仕事や育児・家事をしながら、自分が便利と思うアプリやサービスを作っています。</p>

					<h3>なぜ一人でサービスを作るのか？</h3>
					<p>システムエンジニアの仕事ももちろん面白いですが、どうしても物足りない感があります。言われた仕事をこなす以外も、まだまだ自分にできることがきっとあると信じています。</p>
					<p>成功するかどうかは関係ありません。自分でサービスを作るのは「自分はまだまだできることがある」ことを証明するためでもあり、自分が自分らしく生きて行くためです。</p>
					<p>そしてサービスを作ることで、いろんな方との繋がりも増え、自分自身は何者で、何ができるのか、何がまだ足りないのかを少しずつわかってきました。そういう意味では、サービス作りは私にとって自分探しの旅かもしれません。(笑)</p>

					<h3>絡んでもいい？</h3>
					<p><b>「人となり」</b></p>
					<p>SNSで多くの方と繋がりが増えたことでこの言葉の本当の意味を少しわかってきた気がします。これからもっともっと多くの方と繋がりを持ちたいと思っていますので、お気軽に絡んでください♪(Google+を中心に生息している)</p>
				</div>

				<div class="details">
					<h3>Google+で絡むには？</h3>
					<p><a href="https://plus.google.com/+takaharakou" target="_blank">takahara kou</a></p>
				</div>

				<div class="details">
					<h3>Twitterで絡むには？</h3>
					<p><a href="https://twitter.com/SE_PAPIKO" target="_blank">@SE_PAPIKO</a></p>
				</div>

				<div class="details">
					<h3>Facebookで絡むには？</h3>
					<p><a href="https://www.facebook.com/profile.php?id=100007135908776" target="_blank">Kou Takahara</a></p>
				</div>

				<div class="details">
					<h3>PluCialに関する問合せはこちら</h3>
					<p>メール：info@plucial.com</p>
				</div>

			</section>
		</div>
</body>
</html>
