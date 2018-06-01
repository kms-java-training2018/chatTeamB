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

<!-- 以下メモ===========================================================================================================

【リンク】
・GET通信。
・URLにパラメーターを持たせる方法
<a href="リンク先URL?変数名1=値1&変数名2=値2&変数名3=値3&...">リンクメッセージ</a>

【formタグ】
・1つのformタグの中に複数のsubmitは設置可能だが、そのform内のすべてがパラメーターとして送られる。
	→パラメーターを限定したい場合は、それぞれformタグでくくる

 以上メモ============================================================================================================-->




<body>
	<h1>チャット研修プログラム</h1>
	<h2>グループメッセージ</h2>
	<a href="/chat/showProfile">あいて</a>：グループメッセージのサンプルだよー（´・ω・｀）
	<br> あなた：がっくし（´・ω・｀）
	<br>
	<br>


<!-- 【グループ作成画面、グループメンバー選択箇所】 -->
<form action="/chat/makeGroup" method="post">
      <label><input type="checkbox" name="selectMember[]" value="ログインユーザー(作成者)" checked="checked" required>自分</label>
      <label><input type="checkbox" name="selectMember[]" value="※他ユーザー会員番号番号">他ユーザー名</label>
      <label><input type="checkbox" name="selectMember[]" value="※他ユーザー会員番号番号">他ユーザー名</label>
      <input type="submit" value="送信">
</form>

<!-- 	【メッセージ送信】 -->
	<form action="/chat/groupMessage" method="POST">
		<input type="text" placeholder="ここにメッセージを入力" name="inputMessage">
		<button type='submit' name='action' value='sendMessage'>送信</button>
	</form>


<!-- 	【メッセージ削除】 -->
	<form action="/chat/groupMessage" method="POST">
		<input type="hidden" name='action' value='deleteMessage' id='deleteMessage'>
		<p>メッセージ削除
		<a href="/chat/groupMessage?messageNo=messageNo" id='deleteMessage'>メッセージ削除</a>
	</form>

<!-- 	【グループ脱退】 -->
	<form>
	<button type="submit" name='action' value='leaveGroup'id='leaveGroup'>
		グループ脱退</button>

	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>