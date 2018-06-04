<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>メインメニュー</title>
</head>
<body>
	<header> ようこそ<br>
	${userName}さん<br>
	<form action="/chat/logout" method="POST">
		<button type='submit' name='action' value='logout'>ログアウト</button>
	</form>
	</header>
	<h1>チャット研修プログラム</h1>
	<h2>メインメニュー</h2>
	<br>■会員一覧
	<br>

	<c:forEach var="nom" items="${otherNo}" varStatus="status">
		<a
			href="/chat/directMessage?otherNo=${otherNo[status.index]}&otherName=${otherName[status.index]}"
			class='partnerNameLink'>${nom} ${otherName[status.index]}
			さん（メッセージへ）<br>
		</a>
		<c:out value="${message[status.index]}" />
		<br>
		<br>
	</c:forEach>


	<br>■グループ一覧
	<br>

	<c:forEach var="group" items="${groupNo}" varStatus="status">
		<a href="/chat/groupMessage?groupNo=${groupNo[status.index]}"
			class='nameLink'>${group} ${groupName[status.index]}（グループメッセージへ）<br>
		</a>
		<c:out value="${groupMessage[status.index]}" />
		<br>
		<br>
	</c:forEach>

	<br>
	<br>
	<form action="/chat/makeGroup" method="POST">
		<input type="submit" value="グループの作成">
	</form>
	<form action="/chat/myPage" method="POST" target="newtab">
		<input type="submit" value="プロフィール画面へ">
	</form>


</body>
</html>