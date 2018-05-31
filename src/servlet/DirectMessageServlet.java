package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DirectMessageBean;
import bean.SessionBean;
import model.DirectMessageModelLook;

public class DirectMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// 初期化
		// LoginModel参考
		/** クラスSessionBean取得 */
		SessionBean bean = new SessionBean();
		/** メッセージ内容取得メソッドを使えるようにする */
		DirectMessageModelLook model = new DirectMessageModelLook();

		// セッションスコープ内のパラメータ取得
		HttpSession session = req.getSession();

		/** もしかして必要ない？なぜならsessionbeanのなかにはいっているし、sessionbean取得している
		String userNo = (String) req.getAttribute(bean.getUserNo());
		String userName = (String) req.getAttribute(bean.getUserName());
		*/

		// オリジナル
		/** メッセージ内容を格納するリスト（DirectMessage.jspに持っていくため） */
		ArrayList<String> list = new ArrayList<String>();

		/** クラスDirectMessageBean取得 */
		DirectMessageBean directMessageBean = new DirectMessageBean();


		// DBからメッセージ内容を取得しリストにいれる
		list = directMessageBean.getMessage();


		// メッセジーの個数カウント用変数
		int count = 0;
		for (int i =0; i < list.size(); i++) {
			System.out.println(list.get(i));
			req.setAttribute("list", list.get(i));
			count = i;
		}
		// メッセジーの個数をjspに送る
		req.setAttribute("count", count);



		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
	}



	//------------------------------------------------------------------------------
	// ここから先は後回し
	//------------------------------------------------------------------------------
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 初期化

		/** SQL文実行結果を格納する */
		ResultSet rs = null;

		SessionBean bean = new SessionBean();
		DirectMessageModelLook model = new DirectMessageModelLook();



		try {
			while (rs.next()) {
				System.out.println(rs.getString("MESSAGE"));
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
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
