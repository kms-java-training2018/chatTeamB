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
	${sessionScope.userName}さん<br>
	<form action="/chat/logout" method="POST">
		<button type='submit' name='action' value='logout'>ログアウト</button>
	</form>
	</header>
	<h1>チャット研修プログラム</h1>
	<h2>メインメニュー</h2>
	<br>■会員一覧
	<br>

	<c:forEach var="oUList" items="${otherUserList}" varStatus="status">
		<a
			href="/chat/directMessage?otherUserList=${oUList}"
			class="partnerNameLink">${oUList.otherNo} ${oUList.otherName}
			さん（メッセージへ）<br>
		</a>
		<c:out value="${oUList.message}" />
		<br>
		<br>
	</c:forEach>


	<br>■グループ一覧
	<br>

	<c:forEach var="uGList" items="${userGroupList}" varStatus="status">
		<a href="/chat/groupMessage?userGroupList=${uGList}"
			class='nameLink'>${uGList.groupNo} ${uGList.groupName}（グループメッセージへ）<br>
		</a>
		<c:out value="${uGList.groupMessage}" />
		<br>
		<br>
	</c:forEach>

	<br>
	<br>
	<form action="/chat/makeGroup" method="POST">
		<button type='submit' name='action' value='groupTransition'>グループ作成</button>
	</form>
	<form action="/chat/myPage" method="POST">
		<button type='submit' name='action' value='myPageTransition'>プロフィール編集</button>
	</form>


</body>
</html>