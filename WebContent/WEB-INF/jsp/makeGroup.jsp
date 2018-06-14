<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>グループ作成</title>
<!-- JSを指定 -->
	<script type="text/javascript" src="./js/main.js"></script>
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
	<h2>グループ作成</h2>
	<p>${errorMessage}</p>
	<p>グループ名</p>
	<form action="/chat/makeGroup" method="post">
		<input type="text" name="inputGroupName" class="inputGroupCreate">
		<p>メンバーを選ぶ</p>

		<!-- 【グループ作成画面、グループメンバー選択箇所】 -->

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



	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>

