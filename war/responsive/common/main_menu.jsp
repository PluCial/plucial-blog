<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.appspot.plucial.Constants" %>
<%@ page import="com.appspot.plucial.model.*" %>
<%@ page import="com.appspot.plucial.utils.JspUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
String dateValue = new java.text.SimpleDateFormat( "yyyy", java.util.Locale.US).format(new Date());
String userId = (String) request.getAttribute("userId");
String qstr = (String) request.getAttribute("qstr");

UserModel acsessUserModel = (UserModel) request.getAttribute("acsessUserModel");
List<DateModel> dateModelList = (List<DateModel>) request.getAttribute("dateModelList");

UserModel loginUserModel = null;
boolean isLogin = Boolean.valueOf((String) request.getAttribute("isLogin"));
if(isLogin) {
	loginUserModel = (UserModel) request.getAttribute("loginUserModel");
}

String dateCursor = null;
boolean dateHasNext = false;
if (request.getAttribute("date_cursor") != null && request.getAttribute("date_hasNext") != null) {
	dateCursor = (String) request.getAttribute("date_cursor");
	dateHasNext = Boolean.valueOf((String) request.getAttribute("date_hasNext"));
}
%>
	<div id="main-menu">

			<%if(acsessUserModel != null) { %>
 			<div id="main-menu-tab">
 				<ul>
 					<li><a href="#" id="main-menu-search-tab" class="select" onclick="main_menu_tab_change_to_search();return false;">投稿</a></li>
 					<li><a href="#" id="main-menu-etc-tab" onclick="main_menu_tab_change_to_etc();return false;">その他</a></li>
 				</ul>
 			</div>

 			<div id="main-menu-search">
 				<dl class="navigation">
					<dt>キーワード検索</dt>
				</dl>
 				<div id="search-box">
 					<form id="search" action="/pub/search" method="get">
						<input type="text" name="q" value="<%=qstr != null ? qstr : "" %>" placeholder="投稿を検索">
						<input type="hidden" name="user" value="<%=userId %>">
						<input type="submit" value="検索" style="display: none;">
					</form>
					<p>検索したいキーワードを半角スペース区切りでご入力ください。</p>
 				</div>
 				<dl class="navigation">
					<dt>投稿種類</dt>
					<dd><a href="/ut/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_VERB_TYPE_POST%>"><span>投稿</span></a></dd>
					<dd><a href="/ut/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE%>"><span>再共有</span></a></dd>
				</dl>
				<dl class="navigation">
					<dt>添付ファイル</dt>
					<dd><a href="/ut/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_OBJECT_TYPE_NOTE%>"><span>添付なし</span></a></dd>
					<dd><a href="/ut/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_PHOTO%>"><span>写真付き</span></a></dd>
					<dd><a href="/ut/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO%>"><span>動画付き</span></a></dd>
					<dd><a href="/ut/<%=acsessUserModel.getKey().getName() %>/<%=Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE%>"><span>リンク付き</span></a></dd>
				</dl>
 				<dl class="navigation" id="navigation-date-list">
					<dt>最近の投稿</dt>
					<%for(DateModel dateMenuModel:dateModelList) { %>
					<dd><a href="/u/<%=acsessUserModel.getKey().getName() %>/<%=dateMenuModel.getDate() %>"><span><%=dateMenuModel.getDateJP() %></span></a></dd>
					<%} %>
					<%if(dateHasNext) { %>
					<dd class="read-more-date-box">
						<a class="next-contents-link" href="#" onClick="read_more_dates('/pub/ajax/getMoreDates?user=<%=userId %>&cursor=<%=dateCursor %>');return false;">
							<span>・・・</span>
						</a>
					</dd>
					<%} %>
				</dl>
<%-- 				<%if(dateHasNext) { %>
				<dl class="navigation read-more-date-box">
					<dd><a class="next-contents-link" href="#" onClick="read_more_dates('/pub/ajax/getMoreDates?user=<%=userId %>&cursor=<%=dateCursor %>');return false;"><span>・・・</span></a></dd>
				</dl>
				<%} %> --%>
 			</div>
 			<%} %>

 			<div id="main-menu-etc" style="<%=acsessUserModel == null ? "display:block;" : "display:none;" %>">
				<%if(isLogin) { %>
 				<dl class="navigation">
 					<dt>アクション</dt>
					<dd><a class="help" href="/account/setting"><span>アカウントの設定</span></a></dd>
					<dd><a class="help" href="/account/getNewActivitys"><span>投稿の取り込み</span></a></dd>
				</dl>
				<%} %>

				<%if(isLogin) { %>
				<dl class="navigation">
					<dt>プロモーション機能</dt>
					<dd><a class="help" href="/fm39/<%=loginUserModel.getKey().getName() %>/"><span>フォローありがとう</span></a></dd>
				</dl>
				<%} %>

 				<dl class="navigation">
					<dt>PluCial</dt>
					<dd><a class="help" href="/info/userList"><span>ご利用者一覧</span></a></dd>
					<dd><a class="help" href="/info/agreement"><span>利用規約</span></a></dd>
					<dd><a class="help" href="/info/privacy"><span>プライバシー</span></a></dd>
					<dd><a class="help" href="https://takahara-kou.appspot.com/" target="_blank"><span>運営者情報</span></a></dd>
					<%if(isLogin) { %>
					<dd><a class="help" href="/account/userDelete"><span>退会について</span></a></dd>
					<%} %>
				</dl>

				<dl class="navigation">
					<dt>ソーシャルメディア</dt>
					<dd><a class="google-plus" href="https://plus.google.com/111586968979366611293" target="_blank"><span>Google+ ページ</span></a></dd>
					<dd><a class="google-plus" href="https://plus.google.com/communities/114206134116790035982" target="_blank"><span>コミュニティ</span></a></dd>
				</dl>
			</div>

			<%if(isLogin) { %>
			<div id="action-box">
 				<a class="input_activity_button" href="/account/logout">
					<span class="label">ログアウト</span>
				</a>
 			</div>
 			<%}else { %>
 			<div id="action-box">
 				<a class="input_activity_button" style="font-size: 1.2em;" href="/account/login">
					<span class="label">はじめる(ログイン)</span>
				</a>
				<p>PluCial はGoogle+の投稿を自動的にブログとして保存し、TwitterやFacebookに自動転送するサービスです。</p>
 			</div>
 			<%} %>

			<p class="copyright" style="margin-bottom:80px">© <%=dateValue %> PluCial. All rights reserved.</p>
		</div>