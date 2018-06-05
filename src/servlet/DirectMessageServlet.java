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
import model.DirectMessageModelLook;
import model.MessageInfoModel;

public class DirectMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("DirectMessageServletにきました");

		//セッション取得
		HttpSession session = req.getSession();
		//　セッションがない場合エラー画面に移動
		if (session == null) {
			System.out.println("セッションがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		////////////////////////////////////////////////////////////////////////
		//		初期化
		////////////////////////////////////////////////////////////////////////

		// ページの行き先変更変数
		String direction = "/WEB-INF/jsp/directMessage.jsp";
		/** クラスSessionBeanの初期化 */
		SessionBean sessionBean = new SessionBean();
		/** クラスDirectMessageBeanの初期化 */
		DirectMessageBean directMessageBean = new DirectMessageBean();
		/** クラスDirectMessageModelLookのインスタンス取得　*/
		DirectMessageModelLook model = new DirectMessageModelLook();
		/** jspに持っていくArrayList（会話内容、judge、会話番号）初期化　*/
		ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();

		////////////////////////////////////////////////////////////////////////
		//		ここからdirectMessageBeanに必要な値を入れる
		////////////////////////////////////////////////////////////////////////

		// セッションスコープの"session"をクラスSessionBeanに代入
		sessionBean = (SessionBean) session.getAttribute("session");


		// SessionBeanからログインユーザの会員番号取得
		directMessageBean.setUserNo(sessionBean.getUserNo()); /*"1"メインページが出来次第こちらを使う　*/
		System.out.println("UserNo：" + directMessageBean.getUserNo());

		// SessionBeanからログインユーザの表示名取得
		directMessageBean.setUserName(sessionBean.getUserName()); /*"私の表示名"メインページが出来次第こちらを使う　*/
		String userName = directMessageBean.getUserName();
		System.out.println("UserName：" + userName);

		// セッションスコープから送信対象者の会員番号取得
		directMessageBean.setToSendUserNo(/*"2"*/req.getParameter("otherNo"));
		String toSendUserNo = directMessageBean.getToSendUserNo();
		System.out.println("ToSendUserNo：" + directMessageBean.getToSendUserNo());
		// パラメータ送信対象者の会員番号が存在しない場合エラー画面に遷移する
			if (toSendUserNo.equals(null)) {
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

		// リクエストスコープから送信対象者の表示名取得
		directMessageBean.setOtherName(/*"test"*/(String) req.getParameter("otherName"));
		String otherName = directMessageBean.getOtherName();
		System.out.println("OtherName：" + otherName);


		session.setAttribute("dMBean", directMessageBean);
		////////////////////////////////////////////////////////////////////////
		//		ここまでdirectMessageBeanに必要な値を入れる
		////////////////////////////////////////////////////////////////////////

		// DirectMessageModelLookへ移動会話情報取得処理
		try {
			System.out.println("DirectMessageModelLookにいきます");
			list = model.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DirectMessageServlet、認証処理キャッチ");
		}
		System.out.println("DirectMessageServletに帰ってきました");

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
			+ "：会話番号：" + dMBean.getListMessageNo());
		}

		// リクエストスコープにいれてjspに送る
		req.setAttribute("list", list);
		req.setAttribute("otherName", otherName);
		req.setAttribute("userName", userName);
		req.setAttribute("toSendUserNo", toSendUserNo);
		System.out.println("チェックしてください:" + userName);
		req.setAttribute("directMessageBean", directMessageBean);
		req.getRequestDispatcher(direction).forward(req, res);
	}



	//------------------------------------------------------------------------------
	// ここから先はdoPost
	//------------------------------------------------------------------------------
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("DirectMessageServletにきました");

		//セッション取得
		HttpSession session = req.getSession();

		String action = req.getParameter("action");

		switch (action) {

		////////////////////////////////////////////////////////////////////////
		//		ここからsendMessage処理
		////////////////////////////////////////////////////////////////////////

		case "sendMessage":
			/** クラスMessageInfoModelのインスタンス取得　*/
			MessageInfoModel entryMessage = new MessageInfoModel();
			// DirectMessageBean directMessageBean = new DirectMessageBean();
			String sendUserNo = req.getParameter("userNo");
			String message = req.getParameter("inputMessage");
			String toSendAddress = req.getParameter("toSendUserNo");
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
		//		ここからlookMessage処理（遷移時の処理）
		////////////////////////////////////////////////////////////////////////

		// 必要ないかも？
//		 case "lookMessage":
			// System.out.println("lookMessage処理にきました");

			//　セッションがない場合エラー画面に移動
			if (session == null) {
				System.out.println("セッションがないです");
				session = req.getSession(false);
				session = null;
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}


			////////////////////////////////////////////////////////////////////////
			//		初期化
			////////////////////////////////////////////////////////////////////////

			// ページの行き先変更変数
			String direction = "/WEB-INF/jsp/directMessage.jsp";
			/** クラスSessionBeanの初期化 */
			SessionBean sessionBean = new SessionBean();
			/** クラスDirectMessageBeanの初期化 */
			DirectMessageBean directMessageBean = new DirectMessageBean();

			DirectMessageBean dMessageBean = new DirectMessageBean();
			dMessageBean = (DirectMessageBean) session.getAttribute("dMBean");









			/** クラスDirectMessageModelLookのインスタンス取得　*/
			DirectMessageModelLook model = new DirectMessageModelLook();
			/** jspに持っていくArrayList（会話内容、judge、会話番号）初期化　*/
			ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();



			////////////////////////////////////////////////////////////////////////
			//		ここからdirectMessageBeanに必要な値を入れる
			////////////////////////////////////////////////////////////////////////

			// セッションスコープの"session"をクラスSessionBeanに代入
			sessionBean = (SessionBean) session.getAttribute("session");

			// SessionBeanからログインユーザの会員番号取得
			directMessageBean.setUserNo(sessionBean.getUserNo()); /*req.getParameter("userNo")メインページが出来次第こちらを使う　sessionBean.getUserNo()*/
			System.out.println("UserNoは" + directMessageBean.getUserNo());

			// SessionBeanからログインユーザの表示名取得
			directMessageBean.setUserName(sessionBean.getUserName()); /*req.getParameter("userName")メインページが出来次第こちらを使う　sessionBean.getUserName()*/
			String userName = directMessageBean.getUserName();
			System.out.println("UserName：" + userName);

			// リクエストスコープから送信対象者の会員番号取得
			directMessageBean.setToSendUserNo(dMessageBean.getToSendUserNo()/*req.getParameter("toSendUserNo")*/);
			String toSendUserNo = directMessageBean.getToSendUserNo();
			System.out.println("ToSendUserNo：" + directMessageBean.getToSendUserNo());
			// パラメータ送信対象者の会員番号が存在しない場合エラー画面に遷移する
				if (toSendUserNo.equals(null)) {
					req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
				}

			// リクエストスコープから送信対象者の表示名取得
			directMessageBean.setOtherName(dMessageBean.getOtherName()/*req.getParameter("otherName")*/);
			String otherName = directMessageBean.getOtherName();
			System.out.println("OtherName：" + otherName);

			////////////////////////////////////////////////////////////////////////
			//		ここまでdirectMessageBeanに必要な値を入れる
			////////////////////////////////////////////////////////////////////////


			// DirectMessageModelLookへ移動会話情報取得処理
			try {
				System.out.println("DirectMessageModelLookにいきます");
				list = model.lookMessage(directMessageBean);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("DirectMessageServlet、認証処理キャッチ");
			}
			System.out.println("DirectMessageServletに帰ってきました");

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
				System.out.println(dMBean.getListMessage());
				System.out.println(dMBean.getListJudge());
			}

			// リクエストスコープにいれてjspに送る
			req.setAttribute("list", list);
			req.setAttribute("otherName", otherName);
			req.setAttribute("userName", userName);
			req.setAttribute("toSendUserNo", toSendUserNo);
			req.setAttribute("directMessageBean", directMessageBean);
			System.out.println("更新終わり");
			req.getRequestDispatcher(direction).forward(req, res);

			// 必要ないかも？
			// break;
		////////////////////////////////////////////////////////////////////////
		//		ここまでlookMessage処理
		////////////////////////////////////////////////////////////////////////


	}
}
