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
				<h1>プライバシーポリシー</h1>
				<p>PluCialをご利用いただきありがとうございます。PluCialを利用者の皆様にご利用いただくにあたり、PluCialが利用者情報をどのように利用し、プライバシーをどのように保護するかをご説明します。</p>
			</div>

			<section>
				<div class="details">
					<h2>概要</h2>
					<p>PluCialは利用者から、Google+プロフィール情報、Google+のアクティビティ情報(一般公開のアクティビティのみ)を収集しています。これらの情報はご利用者がPluCialを退会した場合、すべてシステムから自動的に削除されます。</p>

					<h3>情報の収集方法</h3>
					<p>PluCialはGoogleが提供しているGoogle+ APIを使って、必要な情報を収集しています。これら以外の方法で情報収集を行うことがありません。</p>

					<h3>情報の保持</h3>
					<p>PluCialはGoogleが提供している各APIを使って収集した情報をGoogleが提供しているプラットフォームGoogle App Engineに保持しています。ご利用者の同意なく、それ以外の場所でのデータ保持やデータベース作成を行うことがありません。</p>

					<h3>収集情報の利用</h3>
					<p>これらの情報についてはPluCialがご利用者の同意なく、以下の目的以外で利用することがありません。</p>
					<ul>
						<li>ご利用者から収集した情報を、サービスの提供、維持、保護および改善、新しいサービスの開発、ならびに、PluCial と利用者の保護のために利用します。</li>
						<li>利用者のメールアドレスを使用して、PluCial サービスに関する情報を通知することがあります。</li>
					</ul>

					<h3>他者への情報提供</h3>
					<p>PluCialではいかなる理由であっても、ご利用者の同意なくPluCial以外の企業、組織、個人にご利用者から収集した情報を提供することはありません。</p>

					<h3>情報の保護</h3>
					<p>PluCialは、PluCialが保持する情報への不正アクセスや、不正な改変、開示または破壊から、利用者を保護するよう努めます。</p>
				</div>

				<div class="details">
					<h2>収集情報の詳細</h2>
					<h3>プロフィール情報</h3>
					<p>PluCialではご登録頂く際にGoogle+ APIを使ってご利用者のプロフィール情報を収集します。これには以下の情報が含まれます。</p>
					<ul>
						<li>Google+ ID</li>
						<li>メールアドレス</li>
						<li>プロフィールURL</li>
						<li>表示名</li>
						<li>ユーザープロフィール写真URL</li>
						<li>キャッチコピー</li>
						<li>特技</li>
						<li>自己紹介</li>
					</ul>

					<!-- <h3>AdSense情報</h3>
					<p>PluCialではAdSenseの利用者自身のAdSenseアカウントによる広告の設置を認めています。AdSense広告をご利用する際に、ご利用者のGoogle+アカウントに紐づく以下のAdSenseアカウント情報の収集を行っています。</p>
					<ul>
						<li>AdSenseアカウント情報</li>
						<li>AdSenseユニット情報</li>
					</ul>
					<p>また、AdSenseについての詳細ポリシーや規約については、<a href="/info/adsense">Google AdSense広告の利用について</a>をご参照ください。</p> -->

					<h3>Google+のアクティビティ情報</h3>
					<p>PluCialではご利用者のGoogle+でのアクティビティを取込む場合に各アクティビティの以下の情報を収集しています。</p>
					<ul>
						<li>ID</li>
						<li>タイプ</li>
						<li>URL</li>
						<li>タイトル</li>
						<li>公開日時</li>
						<li>更新日時</li>
						<li>アクティビティコンテンツ</li>
						<li>公開元情報</li>
						<li>添付コンテンツ情報</li>
					</ul>
				</div>

				<div class="details">
					<h2>その他</h2>
					<h3>当サイトに掲載されている広告について</h3>
					<p>当サイトでは、第三者配信の広告サービス（Googleアドセンス、A8.net、Amazonアソシエイト）を利用しています。
					このような広告配信事業者は、ユーザーの興味に応じた商品やサービスの広告を表示するため、当サイトや他サイトへのアクセスに関する情報 『Cookie』(氏名、住所、メール アドレス、電話番号は含まれません) を使用することがあります。
					またGoogleアドセンスに関して、このプロセスの詳細やこのような情報が広告配信事業者に使用されないようにする方法については、<a href="http://www.google.co.jp/policies/technologies/ads/" target="_blank">ここ</a>をご参照ください。</p>

					<h3>当サイトが使用しているアクセス解析ツールについて</h3>
					<p>当サイトでは、Googleによるアクセス解析ツール「Googleアナリティクス」を利用しています。
					このGoogleアナリティクスはトラフィックデータの収集のためにCookieを使用しています。
					このトラフィックデータは匿名で収集されており、個人を特定するものではありません。
					この機能はCookieを無効にすることで収集を拒否することが出来ますので、お使いのブラウザの設定をご確認ください。
					この規約に関して、詳しくは<a href="http://www.google.com/analytics/terms/jp.html" target="_blank">ここ</a>をご参照ください。</p>
				</div>

			</section>
		</div>
</body>
</html>
