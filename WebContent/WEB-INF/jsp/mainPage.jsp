<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>メインメニュー</title>
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
<!-- ここまでヘッダー ===============================================================================================-->
<!-- ここからメイン ================================================================-->
	<main>
		<div class="space"></div>

<!-- 		会員一覧(個チャへのリンク) -->
		<div class="messageLinkArea">
			<div class="contentsBox">
				<div class="listHeadding">
					<label>■会員一覧</label>
				</div>
				<ul class="messageLinkList">

<!-- 					会員一覧を表示 -->
					<c:forEach var="otherUser" items="${otherUserList}">
						<li>
							<a
								href="/chat/directMessage?otherUserNo=${otherUser.otherNo}&otherUserName=${otherUser.otherName}"
								class="partnerNameLink"> ${otherUser.otherName} さん（メッセージへ）
							</a>
							<p><c:out value="${otherUser.message}" /></p>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>


<!-- 		グループ一覧(グルチャへのリンク) -->
		<div class="messageLinkArea">
			<div class="contentsBox">
				<div class="listHeadding">
					<label>■グループ一覧</label>
					<form action="/chat/makeGroup" method="POST">
						<button type='submit' name='action' value='groupTransition'>グループ作成</button>
					</form>
				</div>
				<ul class="messageLinkList">
					<c:forEach var="userGroup" items="${userGroupList}">
						<li>
							<a
								href="/chat/groupMessage?userGroupNo=${userGroup.groupNo}&userGroupName=${userGroup.groupName}"
								class="nameLink"> ${userGroup.groupName}（グループメッセージへ）
							</a>
							<p><c:out value="${userGroup.groupMessage}" /></p>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>


		<div>
						<!-- 	プロフィール編集リンク -->
		<a href="/chat/myPage">マイページ(プロフィール編集)</a>
	<!-- プロフィール編集ボタン -->
		<form action="/chat/makeGroup" method="POST">
			<button type='submit' name='action' value='groupTransition'>グループ作成</button>
		</form>

		<form action="/chat/myPage" method="POST">
			<button type='submit' name='action' value='myPageTransition'>プロフィール編集</button>
		</form>
		</div>




	</main>
<!-- ここまでメイン ================================================================-->
<!-- ここからフッター ================================================================-->
	<footer>
	</footer>
<!-- ここまでフッター ================================================================-->
	</body>
</html>