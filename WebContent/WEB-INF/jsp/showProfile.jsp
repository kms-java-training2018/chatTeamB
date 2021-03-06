<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>プロフィール</title>
		<!-- jsを指定 -->
		<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="./js/main.js"></script>
		<!-- cssを指定 -->
		<link rel="stylesheet" type="text/css" href="./css/main.css">
		<link rel="stylesheet" type="text/css" href="./css/individual.css">
	</head>

	<body>
<!-- ここからヘッダー ===============================================================================================-->
	<header>
	<!-- 	ログインユーザーの表示名 -->
		ようこそ<br>
		${sessionScope.userName}さん<br>

	</header>
<!-- ここまでヘッダー ===============================================================================================-->
<!-- ここからメイン ================================================================-->
	<main>
		<div class="space"></div>

		<div>
			<div class="contentsBox">
				<h2>プロフィール確認</h2>
				<p>${userName}さん</p>
				<p>自己紹介： ${myPageText}</p>
				<input type="button" value="閉じる" onClick="window.close();">
			</div>
		</div>



	</main>
<!-- ここまでメイン ================================================================-->
<!-- ここからフッター ================================================================-->
	<footer>
	</footer>
<!-- ここまでフッター ================================================================-->

	</body>
</html>