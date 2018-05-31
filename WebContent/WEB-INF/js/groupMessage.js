/**
 * グループメッセージ画面でのJavaScriptファイルです。
 * 作成者：いよ
 */

window.onload=function(){
	// メッセージ削除ボタン押下時、確認ダイアログを表示
	document.getElementById("deleteMessage").onclick=
    function(){
	alert('このメッセージを削除してよろしいですか？')

	}

}

window.onload=function(){
	//グループ脱退ボタン押下時、確認ダイアログを表示
	document.getElementById("leaveGroup").onclick=
	    function(){
		alert('このグループから脱退してよろしいですか？')

		}

}
