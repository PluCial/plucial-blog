<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
String serviceUserCount = (String)request.getAttribute("serviceUserCount");
String dateValue = new java.text.SimpleDateFormat( "yyyy", java.util.Locale.US).format(new Date());
%>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Google+の投稿を他のSNSに自動転送し自分の投稿を一元管理するサービス" />
	<meta name="keywords" content="plucial,プラシャル,google+,blog,ブログ" />
    <meta property="og:image" content="http://plucial.com/images/index/140926-0004.png" />

    <title>PluCial - Google+を起点とした新しいソーシャルライフの形をあなたに</title>

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

  <body class="landing">

		<!-- Header -->
			<header id="header" class="alt">
				<h1><a href="/">PluCial</a></h1>
				<nav id="nav">
					<ul>
						<li><a href="/">ホーム</a></li>
						<li>
							<a href="" class="icon fa-angle-down">メニュー</a>
							<ul>
								<li><a href="/info/agreement">利用規約</a></li>
								<li><a href="/info/privacy">プライバシーポリシー</a></li>
 								<li><a href="/info/contact">お問い合わせ</a></li>
 								<li><a href="https://takahara-kou.appspot.com/" target="_blank">運用者情報</a></li>
							</ul>
						</li>
						<li><a href="/account/login" class="button">ログイン</a></li>
					</ul>
				</nav>
			</header>

		<!-- Banner -->
			<section id="banner">
				<h2 style="font-weight: 400;"><span style="color:#dd4b39">P</span>lu<span style="color:#555">C</span>ial</h2>
				<p>Google+を起点とした新しいソーシャルライフの形をあなたに</p>
				<ul class="actions">
					<li><a href="/account/login" class="button special">はじめる(ログイン)</a></li>
					<li><a href="#" class="button">PluCialとは</a></li>
				</ul>

				<ul class="list-inline">
                            <li style="height: 77px;display: inline-block;vertical-align: bottom;">
                            	<div class="g-plusone" data-size="tall" data-href="${thisPageUrl }"></div>
                            </li>
                            <li style="height: 77px;display: inline-block;vertical-align: bottom;">
                            	<a href="https://twitter.com/share" class="twitter-share-button" data-count="vertical">Tweet</a><script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
                            </li>
                            <li style="height: 77px;display: inline-block;vertical-align: bottom;">
                            	<div class="fb-like" data-href="${thisPageUrl }" data-send="false" data-layout="box_count" data-width="55" data-show-faces="true" data-font="arial"></div>
                            </li>
                            <li style="height: 77px;display: inline-block;vertical-align: bottom;">
                            	<a href="http://b.hatena.ne.jp/entry/http://plucial.com/" class="hatena-bookmark-button" data-hatena-bookmark-title="PluCial - Google+の投稿をブログにするサービス" data-hatena-bookmark-layout="vertical-balloon" data-hatena-bookmark-lang="ja" title="このエントリーをはてなブックマークに追加"><img src="http://b.st-hatena.com/images/entry-button/button-only@2x.png" alt="このエントリーをはてなブックマークに追加" width="20" height="20" style="border: none;" /></a><script type="text/javascript" src="http://b.st-hatena.com/js/bookmark_button.js" charset="utf-8" async="async"></script>
                            </li>
                        </ul>
			</section>

		<!-- Main -->
			<section id="main" class="container">

				<section class="box special">
					<header class="major">
						<h2>Google+の投稿を他のSNSに自動転送し<br />自分の投稿を一元管理</h2>
						<p>Google+の投稿を他のSNSに転送するだけではない！<br />さらに以前の投稿を見つけやすいようにブログとして自動保存。<br /></p>
						
						<!-- <div style="color:red;margin: 3em 0 1em 0;border: 1px solid #ccc;border-radius: 5px;padding:10px;text-align: left;"><h4 style="color: red;text-align: center;">【重要なお知らせ】</h4>現在、本サービスが利用しているGoogle+ API(Googleが提供している正式API)に障害があり、アカウントによっては正常に動作しない可能性があります。Googleの担当チームが現在修正しておりますので、修正完了後には自動的に復旧します。<br /><div style="margin-top:1em;text-align: right;"><a href="https://plus.google.com/111586968979366611293/posts/PpyHaxKLj1f" target="_blank">詳しくはこちら</a></div></div> -->
					</header>
					
					<span class="image featured"><img src="/images/index/pic01.jpg" alt="" /></span>
				</section>

				<!-- CTA -->
			<section id="cta" style="background: #83d3c9;color: #fff;">

				<h2>投稿を戦略的に転送</h2>
				<p>ハッシュタグモードを使えば、Google+の投稿時にどのSNSに転送するかを簡単に切り替えられます。<br />例えば、この投稿をFacebookに転送せずTwitterやEvernoteにだけ転送したい場合は<br />その投稿にハッシュタグの #TE を付けるだけ。</p>
				<p>今後さらにGoogle+ページ、Facebookページ、その他の様々なSNSやサービスに対応する予定！</p>
			</section>

				<section class="box special features">
					<div class="features-row">
						<section>
							<img style="margin: 0 0 2em 0;" src="/images/index/twitter_circle.png" width="118" alt="">
							<h3>Google+ → Twitter</h3>
							<p>お持ちのTwitterアカウントを設定するだけでGoogle+での投稿が自動的にTwitterに転送されます。画像にも対応し、140文字制限を気にせずに投稿することができます。</p>
						</section>
						<section>
							<img style="margin: 0 0 2em 0;" src="/images/index/facebook_circle.png" width="118" alt="">
							<h3>Google+ → Facebook</h3>
							<p>お持ちのFacebookアカウントを設定するだけでGoogle+での投稿が自動的にFacebookに転送されます。余計なリンクなどは一切含まれません。</p>
						</section>
					</div>
					<div class="features-row">
						<section>
							<img style="margin: 0 0 2em 0;" src="/images/index/evernote_circle.png" width="118" alt="">
							<h3>Google+ → Evernote</h3>
							<p>お持ちのEvernoteアカウントを設定するだけでGoogle+での投稿が自動的にEvernoteに転送されます。添付情報を含めてEvernoteで最も見やすい形に変換されます。</p>
						</section>
						<section>
							<img style="margin: 0 0 2em 0;" src="/images/index/tumblr_circle.png" width="118" alt="">
							<h3>Google+ → Tumblr</h3>
							<p>お持ちのTumblrアカウントを設定するだけでGoogle+での投稿が自動的にTumblrに転送されます。<br /><span style="color:#dd4b39;font-size:14px;">※近々公開予定</span></p>
						</section>
					</div>
				</section>

				<div class="row">
					<div class="6u">

						<section class="box special">
							<span class="image featured"><img src="/images/index/sample.png" alt="" /></span>
							<h3>レスポンシブデザイン</h3>
							<p>レスポンシブデザインを採用し、あなたのブログページは様々なデバイスに対応できます。</p>
							<ul class="actions">
								<li><a href="/u/117110339030145503094/" class="button alt">サンプルを見る</a></li>
							</ul>
						</section>

					</div>
					<div class="6u">

						<section class="box special">
							<span class="image featured"><img src="/images/index/users.png" alt="" /></span>
							<h3>コミュニティ</h3>
							<p>PluCialはコミュニティを通して利用者の声をしっかりサービスに反映しています。</p>
							<ul class="actions">
								<li><a href="https://plus.google.com/communities/114206134116790035982" class="button alt">コミュニティへ</a></li>
							</ul>
						</section>

					</div>
				</div>

			</section>

		<!-- CTA -->
			<section id="cta">

				<h2>メディア紹介について</h2>
				<p>PluCialはメディア紹介を歓迎します。<br />PluCialをもっと多くな方に使って頂くためには是非お力をお貸しください。<br />個人の方でももしPluCialを応援したいという方がいましたら、<br />お持ちのメディア(ブログ、SNSなど)を使ってPluCialの宣伝に是非ご協力ください。</p>

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

			<jsp:include page="/responsive/common/common_script.jsp" />

	</body>

</html>
