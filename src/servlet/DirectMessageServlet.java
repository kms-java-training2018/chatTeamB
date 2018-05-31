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
		System.out.println("DirectMessageServletにきました");

		//セッション取得
		HttpSession session = req.getSession();
		//セッションがない場合エラー画面に移動
		if (session == null) {
			System.out.println("セッションがないです");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// Beanの初期化
		// LoginModel参考
		/** クラスSessionBeanのインスタンス取得 */
		SessionBean sessionBean = new SessionBean();
		// セッションの"session"をクラスSessionBeanに代入
		sessionBean = (SessionBean) session.getAttribute("session");

		// オリジナル
		/** クラスDirectMessageBeanのインスタンス取得 */
		DirectMessageBean directMessageBean = new DirectMessageBean();
		///////directMessageBean

		// メソッド使うための下準備
		/** クラスDirectMessageModelLookのインスタンス取得
		 * メッセージ内容取得メソッドを使えるようにする */
		DirectMessageModelLook model = new DirectMessageModelLook();

		/** 参考までに
		 * 	sessionBean = (SessionBean) session.getAttribute("session");
		 *上記１行の処理で済む内容を次の長文で表現しようと考えていた
		 * 反省！！！！

			// セッションスコープ内のパラメータ取得
			// セッションスコープ内のクラスsessionBean内の変数UserNoをgetUserNoメソッドで取得
			String userNo = (String) session.getAttribute(sessionBean.getUserNo());
			// セッションスコープ内のクラスsessionBean内の変数userNameをgetuserNameメソッドで取得
			String userName = (String) session.getAttribute(sessionBean.getUserName());
			// クラスsessionBean内のそれぞれの変数にset~メソッドで代入
			sessionBean.setUserNo(userNo);
			sessionBean.setUserNo(userName);
		*/

		//------------------------------------------------------------------------------
		// ここから後回し
		//------------------------------------------------------------------------------
		// TODO 相手の会員番号をメインページからのリクエストスコープで取得
		// (例)request.getParameter("相手の会員番号（送信対象者番号）");　？

		// 相手の会員番号をクラスDirectMessageBeanにセットlookMessageメソッドで使う。（送信対象者番号と比較する）

		// directMessageBean.setToSendUserNo(request.getParameter("相手の会員番号（送信対象者番号）"));　？

		//------------------------------------------------------------------------------
		// ここまで後回し
		//------------------------------------------------------------------------------

		/** メッセージ内容を格納するリストを初期化（DirectMessage.jspに持っていくため） */
		ArrayList<String> message = new ArrayList<String>();

		/** 送信対象者番号を格納するリスト初期化（DirectMessage.jspに持っていくため）
		ArrayList<String> toSendUserNo = new ArrayList<String>();
		*/

		/** 自分のものか相手のかメッセージを分ける判断をするリストを初期化（DirectMessage.jspに持っていくため）
		 * エラーが出た場合<String>型にする
		 */
		ArrayList<String> judge = new ArrayList<String>();


		// クラスsessionBean内の変数｛userName,UserNo｝を
		// クラスDirectMessageBean内にコピー
		directMessageBean.setUserNo(sessionBean.getUserNo());
		directMessageBean.setUserName(sessionBean.getUserName());

		//------------------------------------------------------------------------------
		// ここまで完成
		//------------------------------------------------------------------------------

		// TODO　認証処理とは何か？
		try {
		directMessageBean = model.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DirectMessageServlet、認証処理キャッチ");
		}



		// DBからメッセージ内容を取得し格納する
		message = directMessageBean.getMessage();
		// DBから送信対象者番号を取得し格納する
		judge = directMessageBean.getJudge();

		// 必要ないかも？メッセジーの個数カウント用変数
		int count = 0;



		// 確認用
		for (int i = 0; i < message.size(); i++) {
			System.out.println(message.get(i));
			System.out.println(judge.get(i));

			// いらないかも？
			// req.setAttribute("myMessage", message.get(i));
			// req.setAttribute("othersMessage", message.get(i));
			count = i;
		}

		// 必要ないかも？メッセジーの個数をjspに送る
		req.setAttribute("count", count);

		// 以下は必要！！！！
		req.setAttribute("message", message);
		req.setAttribute("judge", judge);

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
