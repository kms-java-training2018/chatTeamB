<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>グループメッセージ</title>
		<!-- jsを指定 -->
		<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="./js/main.js"></script>
		<!-- cssを指定 -->
		<link rel="stylesheet" type="text/css" href="./css/main.css">
		<link rel="stylesheet" type="text/css" href="./css/individual.css">
		<link rel="stylesheet" type="text/css" href="./css/directMessage.css">

	</head>
	<body>
<!-- ここからヘッダー ===============================================================================================-->
	<header>
		<div id="header">
			<!-- ヘッダーの中で左寄せのものたち -->
			<div class="left">
				<!-- メインページ画面へ戻るボタン -->
				<form action="/chat/main" method="POST">
					<button type="submit" name="action" value="toMainPage" class="button">メインページへ戻る</button>
				</form>

				<!-- 【グループ脱退ボタン】 -->
				<div class="dispLeaveGroupButtonArea">
					<!-- ログインユーザーがグループ作成者だった場合 -->
					<c:if test="${judgeGroupCreator == true}">
						<button type="button" disabled>グループ脱退</button>
						<p>※グループ作成者はグループを脱退できません。
					</c:if>

					<!-- ログインユーザーがグループ作成者でなかった場合 -->
					<c:if test="${judgeGroupCreator == false}">
						<form action="/chat/groupMessage" name="leaveGroup" method="POST"
							onsubmit="return leaveGroupJS();">
							<button name="action" value="leaveGroup">グループ脱退</button>
							<input type="hidden" name="groupNo" value="${groupInfo.groupNo}">
							<input type="hidden" name="groupName" value="${groupInfo.groupName}">
						</form>
					</c:if>
				</div>
			</div>

			<!-- ヘッダーの中で中央寄せのものたち -->
			<div class="center">
				<h2>グループ：${groupInfo.groupName}</h2>
			</div>

			<!-- ヘッダーの中で右寄せのものたち -->
			<div class="right">
				<div id="menu">
					<!-- 	ログインユーザーの表示名 -->
					<div id="loginUserName">ようこそ　${userName}さん</div>
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

<!-- ここからメイン ===============================================================================================-->
		<main>
		<div class="space"></div>

		<!-- メッセージ表示欄 -->
		<div class="dispMessageList">
			<div class="contentsBox">
				<div class="dispMessageListInner">

					<!-- for文でメッセージを全て表示させる -->
					<c:forEach var="list" items="${list}" varStatus="status">
					<!-- if文で自分と他人のメッセージを分ける -->
					<c:if test="${list.judge == '0'}" var="judge" />

					<!-- 自分のメッセージの場合 -->
					<c:if test="${judge}">
					<div class="userMessageArea">
						<!-- 個別メッセージ -->
						<div class = "userMessage">
							<div class= "userMessage-title">>><c:out value="${list.userName}" />さん</div>
							<p><c:out value="${list.message}" /></p>
							<p1 hidden>
								<br>：会話番号：
								<c:out value="${list.messageNo}" />
								<br>：会員番号：
								<c:out value="${list.userNo}" />
							</p1>
						</div>
						<!-- 削除ボタン -->
						<form action="/chat/groupMessage" method="POST"
							id="deleteMessageButton" onsubmit="return deleteMessageJS();">
							<button name="action" value="deleteMessage">削除</button>
							<input type="hidden" name="messageNo" value="${list.messageNo}">
							<input type="hidden" name="groupNo" value="${groupInfo.groupNo}">
							<input type="hidden" name="groupName"
								value="${groupInfo.groupName}">
						</form>
					</div>
					</c:if>


					<!-- 他人のメッセージの場合 -->
					<c:if test="${!judge}">
					<div class="otherMessageArea">
						<div class = "otherMessage">

							<!-- 【送信者が脱退者かどうかで表示名の表示方法を変える】 -->
							<c:if test="${list.otherName == '送信者不明'}" var="leaver" />

							<!-- ----送信者がグループメンバーだった場合 -->
							<c:if test="${!leaver}">
							<!-- 相手の表示名をリンク表示(別タブでプロフ画面遷移) -->
							<div class= "otherMessage-title">
								<a href="/chat/showProfile?userNo=<c:out value="${list.userNo}" />"
									class="link" target="_blank">
									>><c:out value="${list.otherName}" />さん
								</a>
							</div>
							<!-- メッセージを表示 -->
							<p><c:out value="${list.message}" /></p>
							<p1 hidden>
								<br>：会話番号：
								<c:out value="${list.messageNo}" />
								<br>：会員番号：
								<c:out value="${list.userNo}" />
							</p1>
							</c:if>

							<!-- ----送信者が脱退者だった場合 -->
							<c:if test="${leaver}">
							<!-- 相手の表示名をラベル表示) -->
							<div class= "otherMessage-title">
								<c:out value="${list.otherName}" />
							</div>
							<!-- メッセージを表示 -->
							<p><c:out value="${list.message}" /></p>
							<p1 hidden>
								<br>：会話番号：
								<c:out value="${list.messageNo}" />
								<br>：会員番号：
								<c:out value="${list.userNo}" />
							</p1>
							</c:if>


						</div>
					</div>
					</c:if>
					</c:forEach>
				</div>
			</div>
		</div>

		<!-- メッセージ入力・送信ボタン欄 -->
		<div class="inputMessageArea">
			<div class="contentsBox">
				<form action="/chat/groupMessage" method="POST"
					onsubmit="return checkDoubleSubmit();">

					<!--メッセージ入力欄 -->
					<textarea  placeholder="ここにメッセージを入力" name="inputMessage"
							class="messageInputBox" id="midashi1" rows="5" cols="50"></textarea>
							<input type="hidden" name="groupNo" value="${groupInfo.groupNo}">
							<input type="hidden" name="groupName" value="${groupInfo.groupName}">

					<!-- エラーメッセージ表示欄 -->
					<p>${errorMessage}</p>

					<!-- 送信ボタン -->
					<button type='submit' name='action' value='sendMessage'>送信</button>
				</form>
			</div>
		</div>


			<div class="space"></div>
		</main>
<!-- ここまでメイン ===============================================================================================-->
<!-- ここからフッター ===============================================================================================-->

<!-- ここまでフッター ===============================================================================================-->
	</body>
</html>