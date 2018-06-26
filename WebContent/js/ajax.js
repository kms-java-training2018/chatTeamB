function ajax(userNo, sendUserNo) {

	// リクエストJSON
	var request = {
		userNo : userNo,
		sendUserNo : sendUserNo
	};

	// ajaxでservletにリクエストを送信

	$
			.ajax({
				type : "GET", // GET / POST
				url : "/SvDbViewRangeServlet", // 送信先のServlet URL
				data : request, // リクエストJSON
				async : true, // true:非同期(デフォルト), false:同期
				success : function(data) {
					// 通信が成功した場合に受け取るメッセージ
					message = data["response"];
					return message;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("リクエスト時になんらかのエラーが発生しました：" + textStatus + ":\n"
							+ errorThrown);
				}
			});

}
