package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DirectMessageBean;
import bean.SessionBean;
import model.GroupMessageModelLook;
import model.MessageInfoModel;

public class GroupMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("GroupMessageServletにきました");

		//セッション取得
		HttpSession session = req.getSession();
		/**　セッションがない場合エラー画面に移動
		if (session == null) {
			System.out.println("セッションがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}
		 */

		////////////////////////////////////////////////////////////////////////
		//		初期化
		////////////////////////////////////////////////////////////////////////

		// ページの行き先変更変数
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		/** クラスSessionBeanの初期化 */
		SessionBean sessionBean = new SessionBean();
		/** クラスDirectMessageBeanの初期化 */
		DirectMessageBean directMessageBean = new DirectMessageBean();
		/** クラスDirectMessageModelLookのインスタンス取得　*/
		GroupMessageModelLook model = new GroupMessageModelLook();
		/** jspに持っていくArrayList（会話内容、judge、会話番号）初期化　*/
		ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();

		////////////////////////////////////////////////////////////////////////
		//		ここからdirectMessageBeanに必要な値を入れる
		////////////////////////////////////////////////////////////////////////

		// セッションスコープの"session"をクラスSessionBeanに代入
		sessionBean = (SessionBean) session.getAttribute("session");


		// SessionBeanからログインユーザの会員番号取得
		directMessageBean.setUserNo("1"/*メインページが出来次第こちらを使う　sessionBean.getUserNo()*/);
		System.out.println("UserNo：" + directMessageBean.getUserNo());

		// SessionBeanからログインユーザの表示名取得
		directMessageBean.setUserName("私の表示名"/*メインページが出来次第こちらを使う　sessionBean.getUserName()*/);
		String userName = directMessageBean.getUserName();
		System.out.println("UserName：" + userName);

		// セッションスコープから送信対象者の会員番号取得
		// パラメータ送信対象者の会員番号が存在しない場合エラー画面に遷移する
		//	if ((String) req.getParameter("相手の会員番号（送信対象者番号）").equals(null)) {
		//		req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		//	}
		directMessageBean.setToSendUserNo("1"/*(String) req.getParameter("相手の会員番号（送信対象者番号）")*/);
		System.out.println("ToSendUserNo：" + directMessageBean.getToSendUserNo());

		// リクエストスコープから送信対象者の表示名取得
		directMessageBean.setOtherName("お~い"/*(String) req.getParameter("相手の表示名")*/);
		String otherName = directMessageBean.getOtherName();
		System.out.println("OtherName：" + otherName);

		////////////////////////////////////////////////////////////////////////
		//		ここまでdirectMessageBeanに必要な値を入れる
		////////////////////////////////////////////////////////////////////////

		// DirectMessageModelLookへ移動会話情報取得処理
		try {
			System.out.println("GroupMessageModelLookにいきます");
			list = model.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DirectMessageServlet、認証処理キャッチ");
		}
		System.out.println("GroupMessageServletに帰ってきました");

		// レコードが取得出来なかった場合エラー画面に遷移する
		if (list == null) {
			System.out.println("レコードがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// デバック用
		DirectMessageBean dMBean = new DirectMessageBean();
		for (int i = 0; i < list.size(); i++) {
			dMBean = list.get(i);
			System.out.println("会話内容：" + dMBean.getListMessage()
			+ "：判別内容：" + dMBean.getListJudge()
			+ "：会員番号：" + dMBean.getUserNo()
			+ "：表示名：" + dMBean.getUserName()
			+ "：会話番号：" + dMBean.getListMessageNo());
		}

		// リクエストスコープにいれてjspに送る
		req.setAttribute("list", list);
		req.setAttribute("otherName", otherName);
		req.setAttribute("userName", userName);
		req.setAttribute("directMessageBean", directMessageBean);
		req.getRequestDispatcher(direction).forward(req, res);
	}


	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// 初期化
		MessageInfoModel model = new MessageInfoModel();
//		boolean result;


		// セッションの値を取得
		HttpSession session = req.getSession();
		String userName = (String)session.getAttribute("userName");
		String userNo = (String)session.getAttribute("userNo");

		// パラメーターの値を取得
		String action = req.getParameter("action");
		switch (action) {


// 【メッセージ送信時】-------------------------------------------------------------------------------------------------
//		メッセージ画面で送信ボタンを押したときに実行される処理。
//		パラメータチェックに成功した場合、会話情報テーブルにメッセージを登録する。
//		あわせて、最新の会話情報テーブルの情報を表示

		case "sendMessage":

			// パラメーターの値を受け取る
			String inputMessage = req.getParameter("inputMessage");
			String groupNo = req.getParameter("groupNo");


			// メッセージ登録用のメソッドを呼び出し(登録処理を実行し)、
			// 【登録処理が失敗した場合(メソッドの戻り値がfalseの場合)】
			if (model.entryMessage(userNo, inputMessage, groupNo, 1) == false) {

				// セッションの情報を削除する
				session = req.getSession(false);
				session = null;

				// エラーページに遷移
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}


			// 【登録処理が成功した場合】
			// switch文を抜けて、ページ遷移処理を行う。
		break;

// 【メッセージ削除時】-------------------------------------------------------------------------------------------------
//		グループ画面で削除ボタンを押したときに実行される処理。
//		ダイアログで論理削除確認後(JSP)、会話情報を論理削除する。
//		あわせて、最新の会話情報テーブルの情報を表示

		case "deleteMessage":

			// パラメーターの値を受け取る
			String messageNo = req.getParameter("messageNo");

			// メッセージ削除用のメソッドを呼び出し(削除処理を実行し)、
			// 【削除処理が失敗した場合(メソッドの戻り値がfalseの場合)】
			if (model.deleteMessage(messageNo) == false) {
				// セッションの情報を削除する
				session = req.getSession(false);
				session = null;

				// エラーページに遷移
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

			// 【削除処理が成功した場合】
			// switch文を抜けて、ページ遷移処理を行う。
		break;

// 【グループ脱退時】---------------------------------------------------------------------------------------------------
//		グループ画面で脱退ボタンを押したときに実行される処理。
//		ダイアログで論理削除確認後、グループ情報テーブルから論理削除する。
//		成功した場合、メインメニュー画面に遷移

		case "leaveGroup":

			// (2)-1 セッション情報の会員番号とをグループ番号を条件に、会話情報テーブルから論理削除する


			// (2)-2 レコードを論理削除できなかった場合、エラー画面に遷移する。


			req.getRequestDispatcher("/WEB-INF/jsp/groupMessage.jsp").forward(req, res);
		break;

// 【どのcaseにも当てはまらない時】-------------------------------------------------------------------------------------

		default:

			System.out.println("不正なパラメーターが送られました。＠GroupMessageServlet");

		break;
		}

//		【ページ遷移処理】==============================================================================================
		// メッセージ送信時、メッセージ削除時にこの処理に入る。


		// パラメーターを設定




	}
}
