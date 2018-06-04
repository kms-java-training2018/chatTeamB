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
	<a href="/chat/showProfile">あいて</a>：グループメッセージのサンプルだよー（´・ω・｀）
	<br> あなた：がっくし（´・ω・｀）
	<br>
	<br>




<!-- 	【メッセージ送信】 -->
	<form action="/chat/groupMessage" method="POST">
		<input type="text" placeholder="ここにメッセージを入力" name="inputMessage" class="messageInputBox">
		<input type="hidden" name="groupNo" value="${requestScope.groupNo}">
		<button type='submit' name='action' value='sendMessage' class="button">送信</button>
	</form>


<!-- 	【メッセージ削除】 -->
	<form action="/chat/groupMessage" method="POST">
		<button type="submit" name="action" value="deleteMessage" id="deleteMessage">削除</button>
		<input type="hidden" name="messageNo" value="【EL式で、該当の会話番号】">
	</form>

<!-- 	【グループ脱退】 -->
	<form action="/chat/groupMessage" method="POST">
	<button type="submit" name="action" value="leaveGroup" class="leaveGroup">
		グループ脱退</button>
	</form>

<!-- ここまでボディ ===============================================================================================-->
<!-- ここからフッター ===============================================================================================-->
<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
<!-- ここまでフッター ===============================================================================================-->
</body>
</html>