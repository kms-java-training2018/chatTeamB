<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- JSを指定 -->
	<script src="../js/groupMessage.js"></script>
<title>グループメッセージ</title>
</head>
<body>
	<h1>チャット研修プログラム</h1>
	<h2>グループメッセージ</h2>
	<a href="/chat/showProfile">あいて</a>：グループメッセージのサンプルだよー（´・ω・｀）
	<br> あなた：がっくし（´・ω・｀）
	<br>
	<br>
	<form action="/chat/groupMessage" method="POST">
		<input type="text" placeholder="ここにメッセージを入力" name="inputMessage">
		<button type='submit' name='action' value='inputMessage'>送信</button>


		<button type="submit" name='action' value='deleteMessage' id='deleteMessage'
<!-- 			onclick="alert('このメッセージを削除しますか？')" -->
			>メッセージ削除</button>


		<button type="submit" name='action' value='leaveGroup'id='leaveGroup'
<!-- 			onclick="alert('このグループを脱退しますか？')" -->
			>グループ脱退</button>

	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>