<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>	ログイン</title>
		<!-- 全画面共通cssを指定 -->
		<link rel="stylesheet" type="text/css" href="./css/main.css">
		<!-- 個別ページcssを指定 -->
		<link rel="stylesheet" type="text/css" href="./css/individual.css">
	</head>
	<body>
<!-- ここからヘッダー ================================================================---->

<!-- ここまでヘッダー ================================================================-->
<!-- ここからメイン ================================================================-->
	<main>
<!-- 		チャットタイトル・説明 -->

		<div>
			<img alt="しまゆまチャット" src="./img/chatTitleImg.png">
		</div>
		<div class="chatTitle">

			<div class="contentsBox">
				<label>「しまゆまチャット」って？？</label>
				<p>メンバーの三野進紀(し)、石井雅大(ま)、稲田雄士(ゆ)、伊與真莉奈(ま)が由来になっています！</p>
				<br>
				<p>ちなみに･･･</p>
				<p>このチャットは、青、緑、赤、黄の4色を基調にして作成しています！</p>
				<p>各メンバーの好きな色を組み合わせて、Bチームらしさを表現しました！</p>

			</div>


		</div>


<!-- 		ログインフォーム -->
		<div class="loginForm">
			<div class="contentsBox">
				<h2>ログイン</h2>
				<form action="/chat/login" method="POST">
					<p>会員ID</p>
					<input type="text" name="userId" value="${loginBean.userId}">
					<p>パスワード</p>
					<input type="password" name="password" value="${loginBean.password}">
					<br>
					<p>${requestScope.errorMessage}</p>
					<input type="submit" value="ログイン">
				</form>

			</div>


		</div>

	</main>
<!-- ここまでメイン ================================================================-->
<!-- ここからフッター ================================================================-->
	<footer>
<!-- 		JS有効にしてねメッセージ -->
		<div>
			<p>このサイトはJavaScriptを有効にしてご利用ください。</p>
		</div>




	</footer>
<!-- ここまでフッター ================================================================-->


	</body>
</html>