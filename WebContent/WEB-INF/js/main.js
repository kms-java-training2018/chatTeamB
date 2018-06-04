/**
 * =====以下メモ========================================================================================================
 * 【!!!これはこれは、メイン(全画面共通)のJavaScriptです!!!】
 * 作成者：いよ
 *
 * 【このjsを指定するときは、以下をjspファイル内<head></head>に追加】
 * <!-- 全画面共通jsを指定 -->
 * 		<script src="../js/main.js"></script>
 *
 *
 * =====以上メモ========================================================================================================
 */

window.onload=function(){

	// メッセージ削除ボタン押下時、確認ダイアログを表示
	document.getElementById("deleteMessage").onclick=
	    function(){
		alert('このメッセージを削除してよろしいですか？');
		}


	//グループ脱退ボタン押下時、確認ダイアログを表示
	document.getElementsByClassName('leaveGroup').onclick=
	    function(){
		alert('このグループから脱退してよろしいですか？')
		}


	// 他ユーザープロフ確認画面で「閉じる」ボタン押下時、タブを閉じる
	document.getElementsByClassName('closeTab').onclick=
	    function(){
		window.close();
		}

};

