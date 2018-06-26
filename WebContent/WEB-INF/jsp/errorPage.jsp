<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>エラー</title>
		<!-- 全画面共通cssを指定 -->
		<link rel="stylesheet" type="text/css" href="./css/main.css">
		<!-- 個別ページcssを指定 -->
		<link rel="stylesheet" type="text/css" href="./css/individual.css">
	</head>
	<body>
<!-- ここからヘッダー ================================================================---->
<!-- 	<header> -->


<!-- 	</header> -->
<!-- ここまでヘッダー ================================================================-->
<!-- ここからメイン ================================================================-->
	<main>
		<div class="space"></div>

		<div class="contentsBox">
			<p>エラーです：${errorMessage}</p>
			<a href="/chat/login"> ログイン画面へ </a>
		</div>

	</main>
<!-- ここまでメイン ================================================================-->
<!-- ここからフッター ================================================================-->
	<footer>
	</footer>
<!-- ここまでフッター ================================================================-->

	</body>

</html>