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
    <meta name="description" content="Google+の投稿を自動的にブログとして保存し、Twitter、FaceBookにも自動転送するサービス。" />
	<meta name="keywords" content="plucial,プラシャル,google+,blog,ブログ" />
    <meta property="og:image" content="http://plucial.com//assets/img/140724-0001.png" />

    <title>PluCial - Google+をもっともっとパワフルに(無料)</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="assets/css/main.css" rel="stylesheet">

    <!-- Fonts from Google Fonts -->
	<link href='http://fonts.googleapis.com/css?family=Lato:300,400,900' rel='stylesheet' type='text/css'>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/"><b>PluCial</b></a>
        </div>
        <nav class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/info/agreement">利用規約</a></li>
            <li><a href="/info/privacy">プライバシー</a></li>
            <li><a href="/info/userList">ご利用者一覧</a></li>
            <li><a href="/info/about">運用者情報</a></li>
          </ul>
        </nav><!--/.nav-collapse -->
      </div>
    </div>

	<div id="headerwrap">
		<div class="container">
			<div class="row">
				<div style="margin-bottom: 20px;">
					<h1 style="margin-bottom: 50px;line-height: 1.3;">Google+を<br />もっともっとパワフルに<br />(無料)</h1>

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
                        <hr class="intro-divider">

					<a class="login" href="/account/login" class="btn btn-warning btn-lg"><span>はじめる(ログイン)</span></a>

					<div style="margin-top:20px;margin-bottom: 100px;">
						<a href="https://chrome.google.com/webstore/detail/plucial/dbjnolbjnjglioonpohhfhnhfmlgnpop" target="_blank"><img alt="plucial Chrome App" src="/assets/img/chrome_web_store.png"></a>
					</div>
				</div><!-- /col-lg-6 -->

			</div><!-- /row -->
		</div><!-- /container -->
	</div><!-- /headerwrap -->


	<div class="container">
		<div class="row mt centered">
			<div class="col-lg-6 col-lg-offset-3">
				<h2>Google+の投稿を他のSNSに自動投稿</h2>
<!-- 				<h3>他のソーシャルメディアのアカウントをお持ちの場合は簡単な設定だけですぐにご利用頂けます。</h3> -->
			</div>
		</div><!-- /row -->

		<div class="row mt centered">
			<div class="col-lg-4">
				<img src="assets/img/twitter_circle.png" width="180" alt="">
				<h4>Google+ → Twitter</h4>
				<p>お持ちのTwitterアカウントを設定するだけでGoogle+での投稿が自動的にTwitterに転送されます。</p>
			</div><!--/col-lg-4 -->

			<div class="col-lg-4">
				<img src="assets/img/facebook_circle.png" width="180" alt="">
				<h4>Google+ → FaceBook</h4>
				<p>お持ちのFacebookアカウントを設定するだけでGoogle+での投稿が自動的にFacebookに転送されます。</p>

			</div><!--/col-lg-4 -->

			<div class="col-lg-4">
				<img src="assets/img/evernote_circle.png" width="180" alt="">
				<h4>Google+ → Evernote</h4>
				<p>お持ちのEvernoteアカウントを設定するだけでGoogle+での投稿が自動的にEvernoteに転送されます。<a href="http://plucial.blogspot.jp/search/label/Evernote" target="_blank">詳しくはこちら</a></p>

			</div><!--/col-lg-4 -->
		</div><!-- /row -->
	</div><!-- /container -->

	<div class="container">
		<hr>
		<div class="row centered">
			<div class="col-lg-6 col-lg-offset-3">
				<a href="/account/login" class="btn btn-warning btn-lg">今すぐはじめる</a>
			</div>
			<div class="col-lg-3"></div>
		</div><!-- /row -->
		<hr>
	</div><!-- /container -->

	<div class="container">
		<div class="row mt centered">
			<div class="col-lg-6 col-lg-offset-3">
				<h2>自動的にブログとして保存</h2>
				<h3 style="line-height: 1.5;text-align: left;">あなたのGoogle+での投稿をブログとして保存することで、過去の投稿を様々な検索機能を使って素早く検索することができます。</h3>
				<div style="margin-top:50px;">
					<strong>サンプル</strong>
					<a style="display: block;text-align: center;" href="http://plucial.com/u/117110339030145503094/">
                    	<img style="width:100%" class="img-responsive" src="assets/img/140630-0001.png" alt="">
                    </a>
				</div>
			</div>
		</div><!-- /row -->

		<!-- CAROUSEL -->
<!-- 		<div class="row mt centered">
			<div class="col-lg-6 col-lg-offset-3">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
				  Indicators
				  <ol class="carousel-indicators">
				    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
				    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
				    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
				  </ol>

				  Wrapper for slides
				  <div class="carousel-inner">
				    <div class="item active">
				      <img src="assets/img/p01.png" alt="">
				    </div>
				    <div class="item">
				      <img src="assets/img/p02.png" alt="">
				    </div>
				    <div class="item">
				      <img src="assets/img/p03.png" alt="">
				    </div>
				  </div>
				</div>
			</div>/col-lg-8
		</div>/row-->
	</div> <!--/container -->

	<div class="container">
		<hr>
		<div class="row centered">
			<h3>最新情報を受け取る</h3>
			<div class="col-lg-6 col-lg-offset-3">
				<a href="https://plus.google.com/111586968979366611293" class="btn btn-warning btn-lg">最新情報にはこちら</a>
			</div>
			<div class="col-lg-3"></div>
		</div><!-- /row -->
		<hr>
	</div><!-- /container -->

	<div class="container">
		<div class="row mt centered">
			<div class="col-lg-6 col-lg-offset-3">
				<h2>利用者からの声</h2>
			</div>
		</div><!-- /row -->

		<div class="row mt centered">
			<div class="col-lg-4">
				<img class="img-circle" src="https://lh3.googleusercontent.com/-fXSF_Nv9urU/AAAAAAAAAAI/AAAAAAAAgrA/oxXe4FZ_aJE/photo.jpg?sz=140" alt="Miyata Takeshi (猫砂)">
				<h4><a href="http://plucial.com/u/112702801573917507083/">Miyata Takeshi</a></h4>
				<p style="text-align: left;">ブログは興味はあるけれど面倒くさそう、Google+ 以外にSNSアカウント増やすのも更に面倒くさそう・・・・そんな私にピッタリなサービスだと思います。<br />
					やはり有限な資源は再利用が一番のエコかな思います。<br />
					見栄えもG+より格段に良くて自分は何もしていないのにもう一人前のブロガーになった気分です(^^ゞ﻿</p>
			</div><!--/col-lg-4 -->

			<div class="col-lg-4">
				<img class="img-circle" src="https://lh4.googleusercontent.com/-Lv0ygpSo7l0/AAAAAAAAAAI/AAAAAAAAD64/TsOLu4YcWfc/photo.jpg?sz=140" alt="Ran Masumi">
				<h4><a href="http://plucial.com/u/106067662015478203906/">Ran Masumi</a></h4>
				<p style="text-align: left;">世の中には色々なツールがありますが、久々に使いやすいシンプルな構成で、かつ複雑さのない所が今後利用者の方にも敷居が低くヒットしそうな予感がします。<br />
				またスマホからも拝見しましたが、ＰＣと同じデザインながらも操作性がよく見た目もスッキリしていてフォロワーさんのフィードを上手くメニューに取り込めるようになると、FlipBoardアプリより見やすいかもしれないです。</p>
			</div><!--/col-lg-4 -->

			<div class="col-lg-4">
				<img class="img-circle" src="https://lh3.googleusercontent.com/-4ekUNpjmc-E/AAAAAAAAAAI/AAAAAAAADhg/OQNZfklsOO0/photo.jpg?sz=140" alt="中禅寺夢玉之助">
				<h4><a href="http://plucial.com/u/103061452172297066488/">中禅寺夢玉之助</a></h4>
				<p style="text-align: left;">最初に登録だけはしていたのですが、あらためて見直したところ、コレは良いですね。シンプルでありながら、ツボを押さえてます。これは素晴らしい！﻿</p>
			</div><!--/col-lg-4 -->
		</div><!-- /row -->
	</div><!-- /container -->

	<div class="container">
		<div class="row centered">
			<div class="col-lg-6 col-lg-offset-3">
				<a href="/info/userList" class="btn btn-warning btn-lg">ご利用者一覧</a>
			</div>
			<div class="col-lg-3"></div>
		</div><!-- /row -->
		<hr>
		<p class="centered">Copyright &copy; PluCial <%=dateValue %>. All Rights Reserved</p>
<%-- 		<p><fmt:message key="index.message.test"/></p> --%>
	</div><!-- /container -->


    <jsp:include page="/responsive/common/common_script.jsp" />

     <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>

<script type="text/javascript">
  window.___gcfg = {lang: 'ja'};

  (function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    po.src = 'https://apis.google.com/js/plusone.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
</script>

  </body>
</html>
