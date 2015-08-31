<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
String serviceUserCount = (String)request.getAttribute("serviceUserCount");
/* List<UserModel> exampleUserList = (List<UserModel>)request.getAttribute("exampleUserList"); */
String dateValue = new java.text.SimpleDateFormat( "yyyy", java.util.Locale.US).format(new Date());
%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum=1, minimal-ui">
    <meta name="description" content="PluCialはあなたのGoogle+での投稿をブログにするサービスです。" />
	<meta name="keywords" content="plucial,プラシャル,google+,blog,ブログ" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">

    <title>PluCial - Google+の投稿をブログにするサービス</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="/css/landing-page.css" rel="stylesheet">

    <!-- Custom Google Web Font -->
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css'>

    <style type="text/css">
.adslot_1 { width: 300px; height: 250px; }
@media (min-width:500px) { .adslot_1 { width: 468px; height: 60px; } }
@media (min-width:800px) { .adslot_1 { width: 728px; height: 90px; } }
@media (min-width:1200px) { #main-menu { display: block; } body > header nav.main-menu {display: none} }
</style>
</head>

<body>

<!--     <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/account/login">ログイン</a>
            </div>

            Collect the nav links, forms, and other content for toggling
            <div class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="/info/agreement">利用規約</a>
                    </li>
                    <li><a href="/info/privacy">プライバシー</a>
                    </li>
                    <li><a href="/info/about">運営者情報</a>
                    </li>
                </ul>
            </div>
            /.navbar-collapse
        </div>
        /.container
    </nav> -->

    <div class="intro-header">

        <div class="container">

            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h1>PluCial</h1>
                        <h3 style="line-height: 1.5;">Google+の投稿を<br />ブログとして保存する<br />(無料)</h3>
                        <hr class="intro-divider">
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
                        <ul class="list-inline intro-social-buttons">
                            <li>
                            	<a href="/account/login" class="btn btn-lg login"><span class="network-name">今すぐはじめる(ログイン)</span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.intro-header -->

    <div class="content-section-a">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">Google+の投稿を自動的に保存</h2>
                    <p class="lead">PluCialに登録するとあなたのGoogle+での一般公開した投稿が自動的にブログとして保存されます。</p>
                    <p><a href="http://plucial.com/info/userList">ご利用者一覧</a></p>
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                	<p>サンプル</p>
                	<a href="http://plucial.com/u/117110339030145503094/">
                    	<img class="img-responsive" src="/images/index/140630-0001.png" style="width: 100%;">
                    </a>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

    <div class="content-section-b">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">Google+の投稿を素早く検索</h2>
                    <p class="lead">PluCialに保存したGoogle+投稿を日付ごとに検索する以外にも様々な検索方法を使って素早く検索可能です。</p>
                    <ul>
                    	<li><strong>複数キーワードによるテキスト検索</strong></li>
                    	<li>自分が書いた投稿の絞り込み表示</li>
                    	<li>再共有した投稿の絞り込み表示</li>
                    	<li>添付なしの投稿の絞り込み表示</li>
                    	<li>画像付き投稿の絞り込み表示</li>
                    	<li>動画付き投稿の絞り込み表示</li>
                    	<li>リンク付き投稿の絞り込み表示</li>
                    </ul>
                </div>
                <div class="col-lg-5 col-sm-pull-6  col-sm-6">
                    <img class="img-responsive" src="/images/index/search.jpg" alt="">
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-b -->

    <div class="content-section-a">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">投稿タイプ別のRSSを利用可能</h2>
                    <p class="lead">PluCialに保存したGoogle+の投稿を投稿タイプ別にRSSで取得できます。</p>
                    <ul>
                    	<li>全投稿タイプのRSS</li>
                    	<li>添付ファイルなし投稿のRSS</li>
                    	<li>画像を添付した投稿のRSS</li>
                    	<li>動画を添付した投稿のRSS</li>
                    	<li>リンクを添付した投稿のRSS</li>
                    </ul>
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                    <img width="200px" class="img-responsive" src="/images/index/rss.png" alt="">
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

    <div class="banner">

        <div class="container">

            <div class="row">
                <div class="col-lg-6">
                    <h2>最新情報</h2>
                    <p>PluCialのGoogle+ページをフォローするとPluCialに関する最新情報を受け取れます。</p>
                </div>
                <div class="col-lg-6">
                    <ul class="list-inline banner-social-buttons">
                        <li><a href="https://plus.google.com/111586968979366611293" class="btn btn-default btn-lg"><span class="">PluCialをフォローする</span></a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.banner -->

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <ul class="list-inline">
                        <li class="footer-menu-divider">&sdot;</li>
                        <li><a href="/info/agreement">利用規約</a>
                        </li>
                        <li class="footer-menu-divider">&sdot;</li>
                        <li><a href="/info/privacy">プライバシー</a>
                        </li>
                        <li class="footer-menu-divider">&sdot;</li>
                        <li><a href="/info/about">運営者情報</a>
                        </li>
                        <li class="footer-menu-divider">&sdot;</li>
                        <li><a href="http://plucial.com/info/userList">ご利用者一覧</a>
                        </li>
                    </ul>
                    <div class="advertisement_box" style="text-align: center;">
						<h3>スポンサー</h3>
						<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
						<!-- responsive -->
						<ins class="adsbygoogle adslot_1"
							style="display:inline-block;"
							data-ad-client="ca-pub-9291943008696804"
							data-ad-slot="4660484463"
							data-ad-format="auto"></ins>
						<script>(adsbygoogle = window.adsbygoogle || []).push({});</script>
					</div>
                    <p class="copyright text-muted small">Copyright &copy; PluCial <%=dateValue %>. All Rights Reserved</p>
                </div>
            </div>
        </div>
    </footer>

	<jsp:include page="/responsive/common/common_script.jsp" />

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
