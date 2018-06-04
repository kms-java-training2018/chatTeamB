<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
			<div align="center">
				<br> ｛
				<c:out value="${list.userName}" />
				<br>：
				<c:out value="${list.listMessage}" />
				<br>：
				<c:out value="${list.listJudge}" />
				：
				<c:out value="${list.listMessageNo}" />
				｝ <br> <a
					href="/chat/directMessage?messageNo=${list.listMessageNo}"
					id='deleteMessage'>メッセージ削除 </a> <br>
			</div>
		</c:if>

		<!-- 他人のメッセージの場合 -->
		<c:if test="${!judge}">
			<br>
			<a href="/chat/showProfile"> ｛<c:out value="${list.otherName}" />さん
			</a>
			<br>：<c:out value="${list.listMessage}" />
			<br>：<c:out value="${list.listJudge}" />
				：<c:out value="${list.listMessageNo}" />
				｝
			<br>
		</c:if>
	</c:forEach>





	<br>
	<form action="/chat/directMessage" method="POST">
		<textarea placeholder="ここにメッセージを入力" name="inputMessage" rows="5" cols="50"></textarea>
		<br>
		<button type='submit' name='action' value='sendMessage' class="button">メッセージ送信</button>
	</form>

	<br>
	<form action="/chat/directMessage" method="POST">
		<textarea name="message" rows="5" cols="50"></textarea>
		<br>
		<input type="submit" value="メッセージの送信">
	</form>

	<br>
	<form action="/chat/main" method="POST">
		<button type="submit" name="action" value="toMainPage" class="button">メインメニューへ戻る</button>
	</form>

</body>
</html>