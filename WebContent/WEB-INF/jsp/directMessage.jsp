<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>個人チャット</title>
</head>
<body>

	<header> ようこそ<br>
	${userName}さん<br>
	<form action="/chat/logout" method="POST">
		<button type="submit" name="action" value="logout" class="button">ログアウト</button>
	</form>
	</header>


	<h1>チャット研修プログラム</h1>
	<h2>
		<a href="/chat/showProfile">${otherName} さん</a>
	</h2>

	<!-- for文でメッセージを全て表示させる -->
	<c:forEach var="list" items="${list}" varStatus="status">
		<!-- if文で自分と他人のメッセージを分ける -->
		<c:if test="${list.listJudge == '0'}" var="judge" />
		<!-- 自分のメッセージの場合 -->
		<c:if test="${judge}">
			<br>
			<div align="center" >
			<div style="display:inline-block; border: 1px solid #cccccc">
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
				<form action="/chat/directMessage" method="POST">
					<button type="submit" name="action" value="deleteMessage"
						class="deleteMessage">削除</button>
					<input type="hidden" name="messageNo" value="${list.listMessageNo}">
				</form>
			</div>
			</div>
		</c:if>

		<!-- 他人のメッセージの場合 -->
		<c:if test="${!judge}">
			<br>
			<div style="display:inline-block; border: 1px solid #cccccc">
				<br> <a href="/chat/showProfile?userNo=${list.toSendUserNo}"
					class="link" target=”_blank”> ｛<c:out value="${list.otherName}" />さん
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
		</c:if>
	</c:forEach>





	<br>
	<form action="/chat/directMessage" method="POST">
		<textarea placeholder="ここにメッセージを入力" name="inputMessage" rows="5" cols="50"></textarea>
		<input type="hidden" name="userNo" value="${directMessageBean.userNo}">
		<input type="hidden" name="userName" value="${directMessageBean.userName}">
		<input type="hidden" name="toSendUserNo" value="${directMessageBean.toSendUserNo}">
		<input type="hidden" name="otherName" value="${directMessageBean.otherName}"> <br>
		<button type='submit' name='action' value='sendMessage' class="button">メッセージ送信</button>
	</form>


	<!-- 正式版 -->
	<form action="/chat/main" method="POST">
		<button type="submit" name="action" value="toMainPage" class="button">メインメニューへ戻る</button>
	</form>

</body>
</html>