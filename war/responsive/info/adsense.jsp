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
				<h1>Google AdSense広告の利用について</h1>
				<p>当サイトでは、AdSenseの利用者自身のAdSenseアカウントによる広告の設置を認めています。</p>
				<p>利用者は、本サービスでGoogle AdSenseプログラムをご利用することにより、ここに記載されているポリシーについて十分理解したことになりますので、以下を注意してお読みください。</p>
			</div>

			<section>
				<div class="details">
					<h2>ポリシー</h2>
					<p>利用者のAdSense広告ユニットを表示の際には、利用者のAdSenseアカウントに対してのアクセスを当サイトに対し許可をする必要があります。</p>
					<p>AdSenseアカウントへのアクセス許可の過程では「AdSenseデータの表示」という、権限に対する許可を求めるダイアログが表示されます。</p>
					<p>AdSense収益の分配についてはAdSenseのシステムによって自動化されており、PluCialでの有益分配はしておりません。また、利用者のPluCialでの収益データに関しても取得または保持はしておりません。</p>
					<p>当サイトではこの「AdSenseデータの表示」権限は以下の目的以外では利用いたしません。</p>
					<ul>
						<li>利用者のAdSenseアカウント情報の取得(アカウントIDなど)</li>
						<li>広告ユニットの情報の取得(広告ユニットID)</li>
					</ul>
					<p>また、利用者のAdSense情報を頂くには以下の目的があります。</p>
					<ul>
						<li>利用者のAdSense広告ユニットの設置手順を簡略化するため</li>
						<li>利用者のAdSense広告ユニットを設置する際のコピー＆貼付けをする際のミスを防ぐため</li>
						<li>悪質なスクリプトの埋め込みを防ぐため</li>
						<li>PluCialのデザインの統一を考慮し、決まったサイズの広告ユニットかどうのチェックを行うため</li>
					</ul>
					<p>利用者のAdSenseレポートデータの閲覧などプライバシーに関わる情報に対し、当サイトが無断でアクセス、取得、閲覧等を行うことはありません。</p>
					<p>また、AdSense広告の表示を停止したい場合は、プロフィールページにある「Google AdSense 広告」内の「Google AdSense アカウント」にある×ボタンをクリックしてご登録のAdSense情報を削除してください。</p>
				</div>

				<div class="details">
					<h2>広告の表示位置</h2>
					<p>PluCialでは利用者の以下の公開ページにて利用者自身のAdSense広告を表示しています。PluCialの運用状況により表示位置を変更することがありますので、ご了承ください。</p>
					<h3>プロフィールページ</h3>
					<p>http://plucial.com/u/(Google+ ID)/</p>
					<p>プロフィールの下にあるスポンサー部分</p>
					<img width="100%" src="/images/agreement/ad_profile.png" />

					<h3>日々のブログページ</h3>
					<p>http://plucial.com/u/(Google+ ID)/(日にち)</p>
					<p>右下にあるスポンサー部分</p>
					<img width="100%" src="/images/agreement/ad_date.png" />
				</div>

			</section>
		</div>
</body>
</html>
