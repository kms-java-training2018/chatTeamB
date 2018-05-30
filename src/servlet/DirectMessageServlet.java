package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DirectMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// セッションの存在チェック。
		// 送信対象者（会話相手）の会員番号が存在するかチェック。


		// パラメーターが不明の場合エラー。


		// 会話情報マスタからSQLを使って、会話情報取得。
		// セッション情報の自分の会員番号と送信者番号、
		// 送信対象者の会員番号と送信対象者番号を条件に会話情報取得。
		// この際、論理削除がされた会話情報は出さない。（メッセージ削除フラグが０の会話情報をwhere文で条件とする）
		// 自分のメッセージと相手のメッセージは別に取得する？if文で違いを判断する？
		// 自分のメッセージは削除出来るようにし、右側に表示する？

//------------------------------------------------------------------------------
// 今日はここを作る
//------------------------------------------------------------------------------

		// レコードが取得できない場合エラー。

		// 取得できた場合directMessage.jspに出力。


		//----------------------------------------------------------------

		// メッセージ入力欄からPOST通信があった場合、name：messageに何かしら値がある場合
		// directMessage.jspのメッセージ入力欄の値が100桁より多い場合エラー。

		// セッション情報の会員番号を条件に、directMessage.jspのメッセージ入力欄の
		// 内容を会話情報マスタに登録する。（自分の会員番号を送信者番号のカラムに入れる）

		// 内容を登録できなかった場合エラー。

		// 再びメッセージ画面に移動、遷移時の処理が走り、メッセージが更新される。

		//----------------------------------------------------------------

		// 削除ボタンが押された場合、name：deleteに何かしら値がある場合
		// 確認ダイアログをJSで表示する。

		// OKが押下された場合以下の処理へ進む。

		// 会話番号から削除したいメッセージを一意に認識する。
		// セッション情報の会員番号を条件に会話情報を論理削除。（SQL文でメッセージ削除フラグに１を入れる）

		// 削除処理を失敗した場合エラー。

		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);

	}
}
