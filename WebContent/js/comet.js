$(function() {
	connect();
	$("#btn").click(button)
});

/* 接続 */
function connect() {
	$.ajax({
		url : "./polling",
		type : "GET",
		data : {},
		complete : fin,
		success : pushed
	});

}

/* 投稿 */
function pushed(data, status, hxr) {
	$t = $("<div>").wrapInner(data);
	$("#result").append($t);
}

/* 接続終了の再接続 */
function fin(hxr, status) {
	connect();
}

/* ボタンが押されたときの処理 */
function button() {
	$.post("./push", {
		text : $("#txt").val()
	});
	$("#txt").val("");
}

