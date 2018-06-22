<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>メッセージ</title>
<!-- JSを指定 -->
	<script type="text/javascript" src="./js/main.js"></script>
	<link rel="stylesheet" type="text/css" href="./css/directMessage.css">
</head>
<body id = "backGround">

<!-- ここからヘッダー ===============================================================================================-->
<header>
<!-- 	ログインユーザーの表示名 -->
	ようこそ<br>
	${directMessageBean.userName} さん <br>

<!-- 	ログアウトリンク -->
	<a href="javascript:void(0)" onclick="logout()">ログアウト</a>

</header>
<!-- ここまでヘッダー ===============================================================================================-->

	<h1>
		<a href="/chat/showProfile?userNo=${directMessageBean.toSendUserNo}"
			class="link" target=”_blank”>${directMessageBean.otherName} さん</a>
	</h1>

	<!-- for文でメッセージを全て表示させる -->
	<c:forEach var="directMessageList" items="${directMessageList}" varStatus="status">
		<!-- if文で自分と他人のメッセージを分ける -->
		<c:if test="${directMessageList.judge == '0'}" var="judge" />

		<!-- 自分のメッセージの場合 -->
		<c:if test="${judge}">
			<div class = "userMessage">
				<div class= "userMessage-title">
					>><c:out value="${directMessageList.userName}" /> さん
				</div>
				<p>
					<c:out value="${directMessageList.message}" />
				</p>
				<p1 hidden>
					<br>：会話番号：<c:out value="${directMessageList.messageNo}" />
					<br>：会員番号：<c:out value="${directMessageList.userNo}" />
				</p1>
			</div>
			<form action="/chat/directMessage" method="POST" id = "deleteMessageButton" onsubmit="return deleteMessageJS();">
				<button name="action" value="deleteMessage">削除</button>
				<input type="hidden" name="messageNo" value="${directMessageList.messageNo}">
			</form>
			<br>
		</c:if>

		<!-- 他人のメッセージの場合 -->
		<c:if test="${!judge}">
			<div class = "otherMessage">
				<div class= "otherMessage-title">
					>><a href="/chat/showProfile?userNo=<c:out value="${directMessageList.userNo}" />" class="link" target=”_blank”>
						<c:out value="${directMessageList.otherName}" /> さん
					</a>
				</div>
				<p>
					<c:out value="${directMessageList.message}" />
				</p>
				<p1 hidden>
					<br>：会話番号：<c:out value="${directMessageList.messageNo}" />
					<br>：会員番号：<c:out value="${directMessageList.userNo}" />
				</p1>
			</div>
			<br>
		</c:if>
	</c:forEach>

	<br>
	${errorMessage}
	<form action="/chat/directMessage" method="POST" onsubmit="return checkDoubleSubmit();">
		<textarea placeholder="ここにメッセージを入力" name="inputMessage" rows="5" cols="50"></textarea>
		<input type="hidden" name="directMessageBean"value="${directMessageBean}">
		<br> <button type="submit" name="action" value="sendMessage" class="button">メッセージ送信</button>
	</form>

	<form action="/chat/main" method="POST">
		<button type="submit" name="action" value="toMainPage" class="button">メインページへ戻る</button>
	</form>

</body>
</html>