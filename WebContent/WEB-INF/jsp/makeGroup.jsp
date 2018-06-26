<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>グループ作成</title>
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

<!-- 		グループ作成のための入力欄 -->
		<div>
			<div class="contentsBox">
				<label>グループ作成</label>
				<p>${errorMessage}</p>

		<form action="/chat/makeGroup" method="post">
			<input type="text" name="inputGroupName" class="inputGroupCreate">
			<p></p>

			<!-- 【グループ作成画面、グループメンバー選択箇所】 -->
			<p>${userErrorMessage}</p>

			<c:forEach var="allUserList" items="${allUserList}" varStatus="status">
				<c:choose>
					<c:when test="${allUserList.allUserName == sessionScope.userName}">
						<%-- 自分 --%>
						<label><input type="checkbox" name="selectMember"
							value="${allUserList.allUserNo}" checked="checked" required>${allUserList.allUserName}さん<br></label>
					</c:when>
					<%-- 他ユーザ --%>
					<c:otherwise>
						<label><input type="checkbox" name="selectMember"
							value="${allUserList.allUserNo}">${allUserList.allUserName} さん</label>
						<br>
					</c:otherwise>
				</c:choose>
			</c:forEach>


			<button type='submit' name='action' value='groupCreate'>グループ作成</button>
		</form>

			</div>
		</div>


	</main>
<!-- ここまでメイン ================================================================-->
<!-- ここからフッター ================================================================-->
	<footer>
	</footer>
<!-- ここまでフッター ================================================================-->




		<form action="/chat/main" method="POST">
			<input type="submit" value="メインメニューに戻る">
		</form>
	</body>
</html>

