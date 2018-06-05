<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- JSを指定 -->
<script src="../js/main.js"></script>
<title>グループメッセージ</title>
</head>
<body>
	<!-- 以下メモ===========================================================================================================

【リンク】
・GET通信。
・URLにパラメーターを持たせる方法
<a href="リンク先URL?変数名1=値1&変数名2=値2&変数名3=値3&...">リンクメッセージ</a>

【formタグ】
・1つのformタグの中に複数のsubmitは設置可能だが、そのform内のすべてがパラメーターとして送られる。
	→パラメーターを限定したい場合は、それぞれformタグでくくる

 以上メモ============================================================================================================-->

	<!-- ここからヘッダー ===============================================================================================-->
	<header> ようこそ<br>
	${userName}さん<br>
	<form action="/chat/main" method="POST">
		<button type='submit' name='action' value='logout'>ログアウト</button>
	</form>

	<form action="/chat/main" method="POST">
		<button type="submit" name="action" value="toMainPage" class="button">メインメニューへ戻る</button>
	</form>

	<!-- ここまでヘッダー ===============================================================================================-->
	</header>
	<!-- ここからボディ ===============================================================================================-->
	<h1>チャット研修プログラム</h1>
	<h2>グループメッセージ</h2>
		<h2>
		<a href="/chat/showProfile">グループ：${groupNo}</a>
	</h2>

	<!-- 	【グループ脱退】 -->
	<form action="/chat/groupMessage" method="POST">
		<button type="submit" name="action" value="leaveGroup"
			class="leaveGroup">グループ脱退</button>
		<input type="hidden" name="groupNo" value="${requestScope.groupNo}">
	</form>

	<!-- for文でメッセージを全て表示させる -->
	<c:forEach var="list" items="${list}" varStatus="status">
		<!-- if文で自分と他人のメッセージを分ける -->
		<c:if test="${list.listJudge == '0'}" var="judge" />
		<!-- 自分のメッセージの場合 -->
		<c:if test="${judge}">
			<br>
			<div style="position: relative; left: 500px">
				<div style="display: inline-block; border: 1px solid #cccccc">
					<br> ｛
					<c:out value="${list.userName}" />
					<br>：
					<c:out value="${list.listMessage}" />
					<br>：Judge番号
					<c:out value="${list.listJudge}" />
					<br>：会話番号
					<c:out value="${list.listMessageNo}" />
					<br>：会員番号
					<c:out value="${list.userNo}" />
					｝ <br>
					<form action="/chat/groupMessage" method="POST">
						<button type="submit" name="action" value="deleteMessage"
							class="deleteMessage">削除</button>
						<input type="hidden" name="messageNo"
							value="${list.listMessageNo}">
					</form>
				</div>
			</div>
		</c:if>

		<!-- 他人のメッセージの場合 -->
		<c:if test="${!judge}">
			<br>
			<div style="position: relative; left: 100px">
				<div style="display: inline-block; border: 1px solid #cccccc">

					<!-- ここから編集中 -->

					<br> <a href="/chat/showProfile?userNo=<c:out value="${list.userNo}" />"
						class="link" target=”_blank”> ｛<c:out
							value="${list.otherName}" />さん

					<!-- ここまで編集中 -->

					</a> <br>：
					<c:out value="${list.listMessage}" />
					<br>：Judge番号
					<c:out value="${list.listJudge}" />
					<br>：会話番号
					<c:out value="${list.listMessageNo}" />
					<br>：会員番号
					<c:out value="${list.userNo}" />
					｝ <br>
				</div>
			</div>
		</c:if>
	</c:forEach>
	<br>


















	<!-- 	【メッセージ送信】 -->
	<form action="/chat/groupMessage" method="POST">
		<input type="text" placeholder="ここにメッセージを入力" name="inputMessage"
			class="messageInputBox"> <input type="hidden" name="groupNo"
			value="${requestScope.groupNo}">
		<button type='submit' name='action' value='sendMessage' class="button">送信</button>
	</form>


	<!-- ここまでボディ ===============================================================================================-->
	<!-- ここからフッター ===============================================================================================-->
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
	<!-- ここまでフッター ===============================================================================================-->
</body>
</html>