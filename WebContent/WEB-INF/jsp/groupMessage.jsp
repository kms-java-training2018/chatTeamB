<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="7">

<!-- JSを指定 -->

<script type="text/javascript" src="./js/main.js"></script>


<title>グループメッセージ</title>
</head>
<body>
	<!-- 以下メモ===========================================================================================================

【リンク】
・GET通信。
・URLにパラメーターを持たせる方法
<a href="リンク先URL?変数名1=値1&変数名2=値2&変数名3=値3&...">リンクメッセージ</a>

【formタグ】
・1つのformタグの中に複数のsubmitは設置可能だが、そのform内のすべてがパラメーターとして送られる。
	→パラメーターを限定したい場合は、それぞれformタグでくくる


	<input type="hidden" name="groupNo" value="${groupInfo.groupNo}">
	<input type="hidden" name="groupName" value="${groupInfo.groupName}">

 以上メモ============================================================================================================-->

	<!-- ここからヘッダー ===============================================================================================-->
	<header> <!-- 	ログインユーザーの表示名 --> ようこそ<br>
	${userName}さん<br>

	<!-- 	ログアウトリンク --> <a href="javascript:void(0)" onclick="logout()">ログアウト</a>

	</header>
	<!-- ここまでヘッダー ===============================================================================================-->

	<!-- ここからボディ ===============================================================================================-->
	<h1>チャット研修プログラム</h1>
	<h2>グループメッセージ</h2>
	<h2>グループ：${groupInfo.groupName}</h2>

	<!-- 	【グループ脱退ボタン】 -->
	<!-- 	ログインユーザーがグループ作成者だった場合 -->
	<c:if test="${judgeGroupCreator == true}">
		<button type="button" disabled>グループ脱退</button>
		<p>※グループ作成者はグループを脱退できません。
	</c:if>

	<!-- 	ログインユーザーがグループ作成者でなかった場合 -->
	<c:if test="${judgeGroupCreator == false}">
		<form action="/chat/groupMessage" name="leaveGroup" method="POST"
			onsubmit="return leaveGroupJS();">
			<button name="action" value="leaveGroup">グループ脱退</button>
			<input type="hidden" name="groupNo" value="${groupInfo.groupNo}">
			<input type="hidden" name="groupName" value="${groupInfo.groupName}">
		</form>
	</c:if>


	<!-- for文でメッセージを全て表示させる -->
	<c:forEach var="list" items="${list}" varStatus="status">
		<!-- if文で自分と他人のメッセージを分ける -->
		<c:if test="${list.judge == '0'}" var="judge" />
		<!-- 自分のメッセージの場合 -->
		<c:if test="${judge}">
			<br>
			<div style="position: relative; left: 500px">
				<div style="display: inline-block; border: 1px solid #cccccc">
					<br>
					<c:out value="${list.userName}" />
					さん <br>
					<c:out value="${list.message}" />
					<p hidden>
						<br>：会話番号：
						<c:out value="${list.messageNo}" />
						<br>：会員番号：
						<c:out value="${list.userNo}" />
					</p>
					<br>

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
			</div>
		</c:if>

		<!-- 他人のメッセージの場合 -->
		<c:if test="${!judge}">
			<br>
			<div style="position: relative; left: 100px">
				<div style="display: inline-block; border: 1px solid #cccccc">

					<!-- 					【送信者が脱退者かどうかで表示名の表示方法を変える】 -->
					<c:if test="${list.otherName == '送信者不明'}" var="leaver" />

					<!-- 					----送信者がグループメンバーだった場合 -->
					<c:if test="${!leaver}">
						<!-- 相手の表示名をリンク表示(別タブでプロフ画面遷移) -->
						<br>
						<a
							href="/chat/showProfile?userNo=<c:out value="${list.userNo}" />"
							class="link" target="_blank"> <c:out
								value="${list.otherName}" />さん
						</a>
						<br>
						<c:out value="${list.message}" />
						<p hidden>
							<br>：会話番号：
							<c:out value="${list.messageNo}" />
							<br>：会員番号：
							<c:out value="${list.userNo}" />
						</p>
						<br>
					</c:if>

					<!-- 					----送信者が脱退者だった場合 -->
					<!-- 相手の表示名をラベル表示) -->
					<c:if test="${leaver}">
						<br>
						<c:out value="${list.otherName}" />
						<br>
						<c:out value="${list.message}" />
						<p hidden>
							<br>：会話番号：
							<c:out value="${list.messageNo}" />
							<br>：会員番号：
							<c:out value="${list.userNo}" />
						</p>
						<br>
					</c:if>

				</div>
			</div>
		</c:if>
	</c:forEach>
	<br>



	<!-- 	【メッセージ送信】 -->
	${errorMessage}
	<form action="/chat/groupMessage" method="POST"
		onsubmit="return checkDoubleSubmit();">
		<input type="text" placeholder="ここにメッセージを入力" name="inputMessage"
			class="messageInputBox" id="midashi1"> <input type="hidden"
			name="groupNo" value="${groupInfo.groupNo}"> <input
			type="hidden" name="groupName" value="${groupInfo.groupName}">
		<button type='submit' name='action' value='sendMessage' class="button"
			id="btn">送信</button>
	</form>


	<!-- ここまでボディ ===============================================================================================-->
	<!-- ここからフッター ===============================================================================================-->
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
	<!-- ここまでフッター ===============================================================================================-->
</body>
</html>