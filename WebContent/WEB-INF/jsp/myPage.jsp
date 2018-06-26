<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>プロフィール編集</title>
		<!-- jsを指定 -->
		<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="./js/main.js"></script>
		<!-- cssを指定 -->
		<link rel="stylesheet" type="text/css" href="./css/main.css">
		<link rel="stylesheet" type="text/css" href="./css/individual.css">
	</head>

	<body>

<!-- ここからヘッダー ================================================================---->
	<header>
		<div id="header">
			<!-- ヘッダーの中で中央寄せのものたち -->
			<div></div>

			<!-- ヘッダーの中で左寄せのものたち -->
			<div></div>

			<!-- ヘッダーの中で右寄せのものたち -->
			<div class="right">
				<div id="menu">
					<!-- 	ログインユーザーの表示名 -->
					<div id="loginUserName">${sessionScope.userName}さん</div>
					    <ul class="child">
							<!-- 	プロフィール編集リンク -->
							<li><a href="/chat/myPage">マイページ(プロフィール編集)</a></li>
							<!-- 	ログアウトリンク -->
							<li><div id="logoutLink"><a href="javascript:void(0)" onclick="logout()">ログアウト</a></div></li>
						</ul>
				</div>
			</div>
		</div>
	</header>
<!-- ここまでヘッダー ================================================================-->
<!-- ここからメイン ================================================================-->
	<main>
		<div class="space"></div>

		<div>
			<div class="contentsBox">
				<h2>マイページ</h2>
				<p>${errorMessage}</p>

				<form action="/chat/myPage" method="POST">
					<p>表示名</p>
					<p>
						<input type="text" name="inputUserName" value="${userName}"
							class="inputUserProf">
					</p>
					<p>自己紹介</p>
					<p>
						<input type="text" name="inputUserSelfIntro" value="${myPageText}"
							class="inputUserProf">
					</p>


					<button type='submit' name='action' value='profileUpdate'>プロフィールを更新</button>

				</form>

			</div>
		</div>




	</main>
<!-- ここまでメイン ================================================================-->
<!-- ここからフッター ================================================================-->
	<footer>
		<form action="/chat/main" method="POST">
				<input type="submit" value="メインメニューに戻る">
		</form>
	</footer>
<!-- ここまでフッター ================================================================-->

	</body>
</html>