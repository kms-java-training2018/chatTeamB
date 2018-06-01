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
	<h1>チャット研修プログラム</h1>
	<h2>
		メッセージ<a href="/chat/showProfile">あいて</a>
	</h2>

	<!--  <span style="border: 5px solid black; padding : 20px ;"> -->
	あなた：メッセージのサンプルだよー（｀・ω・´）
	<!-- </span> -->
	<br>
	<a href="/chat/showProfile">あいて</a>：いえーい（｀・ω・´）
	<br>

	<br>


	<c:forEach var="list" items="${list}" varStatus="status">
		<c:if test="${list.listJudge == '0'}" var="judge" />
		<c:if test="${judge}">
			<br>
				｛自分：<c:out value="${list.listMessage}" />
				：<c:out value="${list.listJudge}" />
				：<c:out value="${list.listMessageNo}" />
				｝
			<br>
			<a href="/chat/directMessage?messageNo=${list.listMessageNo}"
				id='deleteMessage'>メッセージ削除</a>
			<br>
		</c:if>
		<c:if test="${!judge}">
			<br>
				｛<c:out value="${list.otherName}" />　さん：<c:out value="${list.listMessage}" />
				：<c:out value="${list.listJudge}" />
				：<c:out value="${list.listMessageNo}" />
				｝
			<br>
		</c:if>
	</c:forEach>





	<br>


	<form action="/chat/directMessage" method="POST">
		<input type="submit" value="メッセージの送信">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューへ戻る">
	</form>
</body>
</html>