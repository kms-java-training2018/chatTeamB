<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>プロフィール</title>
</head>
<body>
<!-- ここからヘッダー ===============================================================================================-->
<header>
<!-- 	ログインユーザーの表示名 -->
	ようこそ<br>
	${sessionScope.userName}さん<br>

<!-- 	ログアウトリンク -->
	<a href="javascript:void(0)" onclick="logout()">ログアウト</a>

</header>
<!-- ここまでヘッダー ===============================================================================================-->


	<h1>チャット研修プログラム</h1>
	<h2>プロフィール確認</h2>
	<p>${userName}さん</p>
	<p>自己紹介 ${myPageText}</p>
	<input type="button" value="閉じる" onClick="window.close();">

</body>
</html>