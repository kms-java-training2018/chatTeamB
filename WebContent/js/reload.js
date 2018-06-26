function reload() {
	var timer = "5000"; // 指定ミリ秒単位
	window.location.reload();
	alert("接続OK");

	setTimeout(ReloadPage, timer);
}

/*
 * // フレームをreloadする方法 function doReloadTheFrame() {
 *  // フレームのDOM要素を取得 var iframe = document.getElementById('frametarget');
 *  // フレームをreload iframe.contentWindow.location.reload(true);
 *  } window.addEventListener('load', function () {
 *  // 5秒ごとに、フレームをreload setInterval(doReloadTheFrame, 5000);
 *
 * });
 */

/*
 * function dojQueryAjax() {
 *  // jQueryのajaxメソッドを使用しajax通信 $.ajax({ type : "GET", // GETメソッドで通信
 *
 * url : "./WebContent/WEB-INF/jsp/groupMessage.jsp", // 取得先のURL
 *
 * cache : true, // キャッシュして読み込み
 *  // 通信成功時に呼び出されるコールバック success : function(data) {
 *
 * $('#ajaxreload').html(data);
 *  }, // 通信エラー時に呼び出されるコールバック error : function() {
 *
 * alert("Ajax通信エラー");
 *  } });
 *  };
 *
 * window.addEventListener('load', function() {
 *
 * setTimeout(dojQueryAjax, 5000);
 *
 * });
 */
