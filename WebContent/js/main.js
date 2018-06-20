/**
 * =====以下メモ========================================================================================================
 * 【!!!これはこれは、メイン(全画面共通)のJavaScriptです!!!】 作成者：いよ
 *
 * 【このjsを指定するときは、以下をjspファイル内<head></head>に追加】 <!-- 全画面共通jsを指定 --> <script
 * src="../js/main.js"></script>
 *
 *
 * =====以上メモ========================================================================================================
 */

//window.onload = function() {
//
//	// メッセージ削除ボタン押下時、確認ダイアログを表示
////	document.getElementById("deleteMessage").onclick = function() {
////		alert('このメッセージを削除してよろしいですか？');
////	}
//
//	// グループ脱退ボタン押下時、確認ダイアログを表示
//	document.getElementsByClassName('leaveGroup').onclick = function() {
//		alert('このグループから脱退してよろしいですか？')
//	}
//
//	// 他ユーザープロフ確認画面で「閉じる」ボタン押下時、タブを閉じる
//	document.getElementsByClassName('closeTab').onclick = function() {
//		window.close();
//	}
//
//	// ログアウト確認ダイアログ
//	document.getElementsByClassName('logout').onclick = function() {
//		alert('ログアウトしますか？')
//	}
//
//}

function deleteMessageJS() {

	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if (window.confirm('メッセージを削除しますか？')) {
		alert('削除しました'); // 警告ダイアログを表示
		return true;
	}
	// 「OK」時の処理終了

	// 「キャンセル」時の処理開始
	else {

		alert('キャンセルしました'); // 警告ダイアログを表示
		return false;
	}
	// 「キャンセル」時の処理終了

}

function leaveGroupJS() {

	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if (window.confirm('このグループから脱退しますか？')) {
		alert('グループから脱退しました'); // 警告ダイアログを表示
		return true;
	}
	// 「OK」時の処理終了

	// 「キャンセル」時の処理開始
	else {
		alert('キャンセルされました'); // 警告ダイアログを表示
		return false;

	}
	// 「キャンセル」時の処理終了

}

function logout() {

	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if (window.confirm('ログアウトしますか？')) {

		location.href = "/chat/logout"; // ログアウトページ へジャンプ

	}
	// 「OK」時の処理終了

	// 「キャンセル」時の処理開始
	else {

		alert('キャンセルされました'); // 警告ダイアログを表示

	}
	// 「キャンセル」時の処理終了

}

//メッセージ削除　チェック用
/*function logout() {

	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if (window.confirm('ログアウトしますか？')) {


		return true;
	}
	// 「OK」時の処理終了

	// 「キャンセル」時の処理開始
	else {


		return ("return false");

	}
	// 「キャンセル」時の処理終了

}
*/