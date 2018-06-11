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
import model.DirectMessageModel;
import model.MessageInfoModel;

public class DirectMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("DirectMessageServletにきました");

		// 文字コード指定
		req.setCharacterEncoding("utf-8");

		//セッション取得
		HttpSession session = req.getSession();

		////////////////////////////////////////////////////////////////////////
		//		初期化
		////////////////////////////////////////////////////////////////////////

		// ページの行き先変更変数
		String direction = "/WEB-INF/jsp/directMessage.jsp";
		/** クラスSessionBeanの初期化 */
		SessionBean sessionBean = (SessionBean) session.getAttribute("session");//new SessionBean();

		/** クラスMainBeanの初期化 */
//		MainBean mainBean =(MainBean) req.getParameter("otherUser");/////////////////////

		/** クラスDirectMessageBeanの初期化 */
		DirectMessageBean directMessageBean = new DirectMessageBean();
		/** クラスDirectMessageModelLookのインスタンス取得　*/
		DirectMessageModel directMessageModel = new DirectMessageModel();
		/** jspに持っていくArrayList（会話内容、judge、会話番号、表示名）初期化　*/
		ArrayList<DirectMessageBean> directMessageList = new ArrayList<DirectMessageBean>();

		// ログイン判定処理
		// セッションがない場合エラー画面に移動
		if (sessionBean.getUserNo().equals(null) || sessionBean.getUserName().equals(null)) {
			System.out.println("セッションがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		////////////////////////////////////////////////////////////////////////
		//		ここからdirectMessageBeanに必要な値を入れる
		////////////////////////////////////////////////////////////////////////

		// SessionBeanからログインユーザの会員番号取得
		directMessageBean.setUserNo(sessionBean.getUserNo());
		System.out.println("UserNo：" + directMessageBean.getUserNo());

		// SessionBeanからログインユーザの表示名取得
		directMessageBean.setUserName(sessionBean.getUserName());
		System.out.println("UserName：" + directMessageBean.getUserName());

		// セッションスコープから送信対象者の会員番号取得
		directMessageBean.setToSendUserNo(req.getParameter("otherUserNo")/*mainBean.getOtherNo()*/);////////////////////////
		String toSendUserNo = directMessageBean.getToSendUserNo();
		System.out.println("ToSendUserNo：" + directMessageBean.getToSendUserNo());
		// パラメータ送信対象者の会員番号が存在しない場合エラー画面に遷移する
		if (toSendUserNo.equals(null)) {
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// リクエストスコープから送信対象者の表示名取得
		directMessageBean.setOtherName(req.getParameter("otherUserName")/*mainBean.getOtherName()*/);/////////////////////
		System.out.println("OtherName：" + directMessageBean.getOtherName());

		// セッションスコープにdirectMessageBeanをセット
		session.setAttribute("dMBean", directMessageBean);

		////////////////////////////////////////////////////////////////////////
		//		ここまでdirectMessageBeanに必要な値を入れる
		////////////////////////////////////////////////////////////////////////

		// DirectMessageModelLookへ移動会話情報取得処理
		try {
			System.out.println("DirectMessageModelLookにいきます");
			directMessageList = directMessageModel.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DirectMessageServlet、認証処理キャッチ");
		}
		System.out.println("DirectMessageServletに帰ってきました");

		// レコードが取得出来なかった場合エラー画面に遷移する
		if (directMessageList == null) {
			System.out.println("レコードがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// デバック用
		DirectMessageBean dMBean = new DirectMessageBean();
		for (int i = 0; i < directMessageList.size(); i++) {
			dMBean = directMessageList.get(i);
			System.out.println("会話内容：" + dMBean.getMessage()
					+ "：判別内容：" + dMBean.getJudge()
					+ "：会員番号：" + dMBean.getUserNo()
					+ "：会話番号：" + dMBean.getMessageNo());
		}

		// リクエストスコープにいれてjspに送る
		req.setAttribute("directMessageList", directMessageList);
		req.setAttribute("directMessageBean", directMessageBean);
		req.getRequestDispatcher(direction).forward(req, res);
	}

	//------------------------------------------------------------------------------
	// ここからdoPost
	//------------------------------------------------------------------------------
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("DirectMessageServletにきました");

		// 文字コード指定
		req.setCharacterEncoding("utf-8");

		//セッション取得
		HttpSession session = req.getSession();

		String action = req.getParameter("action");

		/** クラスDirectMessageBeanの初期化 */
		DirectMessageBean directMessageBean = (DirectMessageBean) session.getAttribute("dMBean");

		switch (action) {

		////////////////////////////////////////////////////////////////////////
		//		ここからsendMessage処理
		////////////////////////////////////////////////////////////////////////

		case "sendMessage":
			/** クラスMessageInfoModelのインスタンス取得　*/
			MessageInfoModel entryMessage = new MessageInfoModel();

			String sendUserNo = directMessageBean.getUserNo();//req.getParameter("userNo");
			String message = req.getParameter("inputMessage");
			String toSendAddress = directMessageBean.getToSendUserNo();//req.getParameter("toSendUserNo");
			int judgeAddress = 0;
			boolean result1 = entryMessage.entryMessage(sendUserNo, message, toSendAddress, judgeAddress);
			System.out.println("会話情報を登録しました");
			if (result1 == false) {
				System.out.println("会話情報を登録できません");
				session = req.getSession(false);
				session = null;
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

			break;
		////////////////////////////////////////////////////////////////////////
		//		ここまでsendMessage処理
		////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////
		//		ここからdeleteMessage処理
		////////////////////////////////////////////////////////////////////////
		case "deleteMessage":

			// パラメーターを受け取る
			String messageNo = req.getParameter("messageNo");

			// メッセージの論理削除処理を実行
			// 【メッセージの論理削除処理が失敗した場合】
			MessageInfoModel deleteMessage = new MessageInfoModel();
			if (deleteMessage.deleteMessage(messageNo) == false) {
				System.out.println("会話情報を論理削除できません");
				// セッションを削除
				session = req.getSession(false);
				session = null;
				// エラーページへ遷移
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

			// 【メッセージの論理削除処理が成功した場合】
			// switch文を抜けて、ページ表示処理をする。

			break;
		////////////////////////////////////////////////////////////////////////
		//		こまでdeleteMessage処理
		////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////
		//		どれにも当てはまらない場合
		////////////////////////////////////////////////////////////////////////

		default:

			System.out.println("不正なパラメーターが送られました。＠GroupMessageServlet");

			break;
		}

		////////////////////////////////////////////////////////////////////////
		//		ここから遷移時の処理
		////////////////////////////////////////////////////////////////////////


		////////////////////////////////////////////////////////////////////////
		//		初期化
		////////////////////////////////////////////////////////////////////////

		// ページの行き先変更変数
		String direction = "/WEB-INF/jsp/directMessage.jsp";
		/** クラスSessionBeanの初期化 */
		SessionBean sessionBean = (SessionBean) session.getAttribute("session");//new SessionBean();
		/** クラスDirectMessageModelLookのインスタンス取得　*/
		DirectMessageModel model = new DirectMessageModel();
		/** jspに持っていくArrayList（会話内容、judge、会話番号）初期化　*/
		ArrayList<DirectMessageBean> directMessageList = new ArrayList<DirectMessageBean>();

		// ログイン判定処理
		//　セッションがない場合エラー画面に移動
		if (sessionBean.getUserNo().equals(null) || sessionBean.getUserName().equals(null)) {
			System.out.println("セッションがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		// DirectMessageModelLookへ移動会話情報取得処理
		try {
			System.out.println("DirectMessageModelLookにいきます");
			directMessageList = model.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DirectMessageServlet、認証処理キャッチ");
		}
		System.out.println("DirectMessageServletに帰ってきました");

		// レコードが取得出来なかった場合エラー画面に遷移する
		if (directMessageList == null) {
			System.out.println("レコードがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// デバック用
		DirectMessageBean dMBean = new DirectMessageBean();
		for (int i = 0; i < directMessageList.size(); i++) {
			dMBean = directMessageList.get(i);
			System.out.println("会話内容：" + dMBean.getMessage()
			+ "：判別内容：" + dMBean.getJudge()
			+ "：会員番号：" + dMBean.getUserNo()
			+ "：会話番号：" + dMBean.getMessageNo());
		}

		// リクエストスコープにいれてjspに送る
		req.setAttribute("directMessageList", directMessageList);
		req.setAttribute("directMessageBean", directMessageBean);
		System.out.println("更新終わり");
		req.getRequestDispatcher(direction).forward(req, res);
	}
}
