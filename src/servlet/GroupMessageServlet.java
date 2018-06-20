package servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DirectMessageBean;
import bean.GroupMessageBean;
import bean.SessionBean;
import model.GroupInfoModel;
import model.GroupMessageModelLook;
import model.MessageInfoModel;

public class GroupMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 文字コードを指定
		req.setCharacterEncoding("utf-8");

// 【ページ遷移時】-------------------------------------------------------------------------------------------------

		////////////////////////////////////////////////////////////////////////
		//		パラメーターチェック
		////////////////////////////////////////////////////////////////////////

//		// 【デバッグ用】-------------------------------------
//		// セッション切れた状況を作り出す
//		HttpSession session = req.getSession(true);
//		session.invalidate();
//		------------------------------------------------------

		// 【セッションが開始しているかどうかの判定】
		HttpSession session = req.getSession(false);
		// ---開始していない場合(タイムアウト含む)
		if(session == null){
			//nullならセッションは切れている。
			// エラー画面に遷移
			req.setAttribute("errorMessage", "セッションが開始されていない、もしくはタイムアウトになりました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		// ---すでに開始している場合
		}else {
			// 開始済みセッションを取得
			session = req.getSession(true);
			}

		// 【セッション内にログイン情報を保持しているかどうかの判定】
		SessionBean sessionBean = (SessionBean) session.getAttribute("session");
		// ---保持されていない場合
		if (sessionBean == null
				||sessionBean.getUserNo().equals(null)
				||sessionBean.getUserName().equals(null)) {
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "ログインされていません。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// 【パラメータに遷移先groupNoが存在するかの判定】
		// ---存在しない場合
		if (req.getParameter("userGroupNo") == null) {
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "遷移先グループが選択されていません。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		////////////////////////////////////////////////////////////////////////
		//		セッションとパラメーターから情報を取得
		////////////////////////////////////////////////////////////////////////
		// セッションから：ログインユーザーの会員番号・表示名
		String userNo = sessionBean.getUserNo();
		String userName = sessionBean.getUserName();

		// パラメーターから(メインメニュー)：遷移先groupNo･gropuName
		String groupNo = req.getParameter("userGroupNo");
		String groupName =req.getParameter("userGroupName");


		////////////////////////////////////////////////////////////////////////
		//		初期化
		//			※グループメッセージ画面においても、
		//			　メッセージ表示方法は個人チャット画面と同様の処理のため、
		//			　DirectMessageBeanを使用する。
		////////////////////////////////////////////////////////////////////////
		// ページの行き先変更変数
		String direction = "/WEB-INF/jsp/groupMessage.jsp";

		// GroupInfoModel：グループメンバー判定用
		GroupInfoModel groupInfoModel = new GroupInfoModel();
		// GroupMessageModelLook：グループ会話内容取得用
		GroupMessageModelLook groupMessageModelLook = new GroupMessageModelLook();

		// DirectMessageBean：1つの会話情報格納用
		DirectMessageBean directMessageBean = new DirectMessageBean();
		// GroupMessageBean：グループ番号・グループ名格納用
		GroupMessageBean groupMessageBean = new GroupMessageBean();

		// ArrayList：1つの会話情報を要素に、グループ会話内容を格納する
		ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();

		// ログインユーザーが遷移先グループの作成者かどうかの判定結果を格納用
		boolean judgeGroupCreator = true;


		////////////////////////////////////////////////////////////////////////
		//		Beanに情報をセット
		////////////////////////////////////////////////////////////////////////
		// directMessageBean
		directMessageBean.setUserNo(userNo);
		directMessageBean.setUserName(userName);
		directMessageBean.setToSendUserNo(groupNo);

		// groupMessageBean
		groupMessageBean.setGroupNo(groupNo);
		groupMessageBean.setGroupName(groupName);


		////////////////////////////////////////////////////////////////////////
		//		処理
		////////////////////////////////////////////////////////////////////////

		// 【遷移先グループにログインユーザーが参加しているか判定】
		// ---グループメンバーでなかった場合
		try {
			if(groupInfoModel.judgeGroupMember(userNo,groupNo) == false) {
				// セッションを削除
				session.invalidate();
				// エラー画面に遷移
				req.setAttribute("errorMessage", "グループメンバーではありません。");
			    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			    }
		// ---DB接続中にエラーが発生した場合
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "グループメンバー判定処理中にエラーが発生しました。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// 【遷移先グループのグループ作成者がログインユーザーか判定】
		try {
			// ----作成者でなかった場合
			if(groupInfoModel.judgeGroupCreator(userNo,groupNo) == false) {
				judgeGroupCreator = false;
			    }
		// ---DB接続中にエラーが発生した場合
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "グループ作成者判定処理中にエラーが発生しました。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// 【グループ会話内容取得】
		try {
			list = groupMessageModelLook.lookMessage(directMessageBean);
			// レコードが取得出来なかった場合
			if (list == null) {
				// セッションを削除
				session.invalidate();
				// エラー画面に遷移
				req.setAttribute("errorMessage", "会話内容が取得できませんでした。");
			    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			    }
		// ---DB接続中にエラーが発生した場合
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "会話内容取得処理中にエラーが発生しました。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		////////////////////////////////////////////////////////////////////////
		//		リクエストに情報をセット(jspへ)
		////////////////////////////////////////////////////////////////////////
		// グループ会話内容
		req.setAttribute("list", list);
		// 	グループ情報（グループ番号・グループ名）
		req.setAttribute("groupInfo", groupMessageBean);
		// グループ作成者かどうか
		req.setAttribute("judgeGroupCreator", judgeGroupCreator);

		// groupMessage.jspへ遷移
		req.getRequestDispatcher(direction).forward(req, res);


		////////////////////////////////////////////////////////////////////////
		//		以下デバッグ用
		////////////////////////////////////////////////////////////////////////

//		// 取得したグループ会話内容をコンソールに表示
//		DirectMessageBean dMBean = new DirectMessageBean();
//		for (int i = 0; i < list.size(); i++) {
//			dMBean = list.get(i);
//			System.out.println("会話内容：" + dMBean.getListMessage()
//			+ "：判別内容：" + dMBean.getListJudge()
//			+ "：会員番号：" + dMBean.getUserNo()
//			+ "：表示名：" + dMBean.getUserName()
//			+ "：会話番号：" + dMBean.getListMessageNo());
//		}


	}



// 	【以下POSTメソッド】================================================================================================
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 文字コードを指定
		req.setCharacterEncoding("utf-8");

		////////////////////////////////////////////////////////////////////////
		//		パラメーターチェック
		////////////////////////////////////////////////////////////////////////

//		// 【デバッグ用】-------------------------------------
//		// セッション切れた状況を作り出す
//		HttpSession session = req.getSession(true);
//		session.invalidate();
//		------------------------------------------------------

		// 【セッションが開始しているかどうかの判定】
		HttpSession session = req.getSession(false);
		// ---開始していない場合(タイムアウト含む)
		if(session == null){
			//nullならセッションは切れている。
			// エラー画面に遷移
			req.setAttribute("errorMessage", "セッションが開始されていない、もしくはタイムアウトになりました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		// ---すでに開始している場合
		}else {
			// 開始済みセッションを取得
			session = req.getSession(true);
			}

		// 【セッション内にログイン情報を保持しているかどうかの判定】
		SessionBean sessionBean = (SessionBean) session.getAttribute("session");
		// ---保持されていない場合
		if (sessionBean == null
				||sessionBean.getUserNo().equals(null)
				||sessionBean.getUserName().equals(null)) {
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "ログインされていません。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		////////////////////////////////////////////////////////////////////////
		//		セッションとパラメーターから情報を取得
		////////////////////////////////////////////////////////////////////////
		// セッションから：ログインユーザーの会員番号・表示名
		String userName = sessionBean.getUserName();
		String userNo = sessionBean.getUserNo();

		// パラメーターから(グループメッセージ)：groupNo･gropuName
		String groupNo = req.getParameter("groupNo");
		String groupName =req.getParameter("groupName");

		// 処理分岐用
		String action = req.getParameter("action");


		////////////////////////////////////////////////////////////////////////
		//		初期化
		//			※グループメッセージ画面においても、
		//			　メッセージ表示方法は個人チャット画面と同様の処理のため、
		//			　DirectMessageBeanを使用する。
		////////////////////////////////////////////////////////////////////////

		// ページの行き先変更変数
		String direction = "/WEB-INF/jsp/groupMessage.jsp";

		// MessageInfoModel：メッセージ送信・削除処理用
		MessageInfoModel messageInfoModel = new MessageInfoModel();
		// GroupInfoModel：グループ脱退処理用
		GroupInfoModel groupInfoModel = new GroupInfoModel();
		// GroupMessageModelLook：グループ会話内容取得用
		GroupMessageModelLook groupMessageModelLook = new GroupMessageModelLook();

		// DirectMessageBean：1つの会話情報格納用
		DirectMessageBean directMessageBean = new DirectMessageBean();
		// GroupMessageBean：グループ番号・グループ名格納用
		GroupMessageBean groupMessageBean = new GroupMessageBean();

		// ArrayList：1つの会話情報を要素に、グループ会話内容を格納する
		ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();

		// ログインユーザーが遷移先グループの作成者かどうかの判定結果を格納用
		boolean judgeGroupCreator = true;

		////////////////////////////////////////////////////////////////////////
		//		Beanに情報をセット
		////////////////////////////////////////////////////////////////////////
		// directMessageBean
		directMessageBean.setUserNo(userNo);
		directMessageBean.setUserName(userName);
		directMessageBean.setToSendUserNo(groupNo);

		// groupMessageBean
		groupMessageBean.setGroupNo(groupNo);
		groupMessageBean.setGroupName(groupName);


		////////////////////////////////////////////////////////////////////////
		//		処理
		////////////////////////////////////////////////////////////////////////


		switch (action) {


// 【メッセージ送信時】-------------------------------------------------------------------------------------------------
//		メッセージ画面で送信ボタンを押したときに実行される処理。
//		パラメータチェックに成功した場合、会話情報テーブルにメッセージを登録する。
//		あわせて、最新の会話情報テーブルの情報を表示

		case "sendMessage":

			// 【リクエストから入力されたメッセージを取得】
			String inputMessage = req.getParameter("inputMessage");

			// 【メッセージを送信したユーザーがグループメンバーか判定】
			// ---グループメンバーでなかった場合
			try {
				if(groupInfoModel.judgeGroupMember(userNo,groupNo) == false) {
					// セッションを削除
					session.invalidate();
					// エラー画面に遷移
					req.setAttribute("errorMessage", "グループメンバーではありません。");
				    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
				    }
			// ---DB接続中にエラーが発生した場合
			} catch (Exception e) {
				e.printStackTrace();
				// セッションを削除
				session.invalidate();
				// エラー画面に遷移
				req.setAttribute("errorMessage", "グループメンバー判定処理中にエラーが発生しました。");
			    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

			//【入力されたメッセージが1バイト以上100バイト以内か判定】
			// ---不正な入力だった場合
			if (inputMessage.getBytes(Charset.forName("UTF-8")).length <= 0
					|| 100 < inputMessage.getBytes(Charset.forName("UTF-8")).length) {
				// エラーメッセージを設定
				req.setAttribute("errorMessage", "文字数は1バイト以上100バイト以下にしてください。");
				// 行き先はGourpMessageServlet。(switch文を抜けてページ遷移処理を行う。)

			} else {

				// 【入力されたメッセージを会話情報テーブルに登録】
				// ---登録処理が失敗した場合(メソッドの戻り値がfalseの場合)
				try {
					if (messageInfoModel.entryMessage(userNo, inputMessage, groupNo, 1) == false) {
					// セッションを削除
					session.invalidate();
					// エラー画面に遷移
					req.setAttribute("errorMessage", "メッセージが送信できませんでした。");
				    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
					}
				// ---DB接続中にエラーが発生した場合
				} catch (Exception e) {
					e.printStackTrace();
					// セッションを削除
					session.invalidate();
					// エラー画面に遷移
					req.setAttribute("errorMessage", "メッセージ登録処理中にエラーが発生しました。");
				    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
				}


				// ---登録処理が成功した場合
				// switch文を抜けて、ページ遷移処理を行う。
			}
		break;



// 【メッセージ削除時】-------------------------------------------------------------------------------------------------
//		グループ画面で削除ボタンを押したときに実行される処理。
//		ダイアログで論理削除確認後(JSP)、会話情報を論理削除する。
//		あわせて、最新の会話情報テーブルの情報を表示

		case "deleteMessage":

			// リクエストから論理削除するmessageNoを取得
			String messageNo = req.getParameter("messageNo");

			// 【メッセージ削除用処理】
			// ---削除処理が失敗した場合(メソッドの戻り値がfalseの場合)
			try {
				if (messageInfoModel.deleteMessage(messageNo) == false) {
					// セッションを削除
					session.invalidate();
					// エラー画面に遷移
					req.setAttribute("errorMessage", "メッセージを削除できませんでした。");
				    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
				}
			// ---DB接続中にエラーが発生した場合
			} catch (Exception e) {
				e.printStackTrace();
				// セッションを削除
				session.invalidate();
				// エラー画面に遷移
				req.setAttribute("errorMessage", "メッセージ論理削除処理中にエラーが発生しました。");
			    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}


			// ---削除処理が成功した場合
			// switch文を抜けて、ページ遷移処理を行う。
		break;



// 【グループ脱退時】---------------------------------------------------------------------------------------------------
//		グループ画面で脱退ボタンを押したときに実行される処理。
//		ダイアログで論理削除確認後、グループ情報テーブルから論理削除する。
//		成功した場合、メインメニュー画面に遷移

		case "leaveGroup":

			// 【グループ脱退処理】
			// ---グループ脱退処理が失敗した場合
			try {
				if(groupInfoModel.groupLeave(userNo, groupNo) == false) {
					// セッションを削除
					session.invalidate();
					// エラー画面に遷移
					req.setAttribute("errorMessage", "グループ脱退に失敗しました。");
				    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
				}
			// ---DB接続中にエラーが発生した場合
			} catch (Exception e) {
				e.printStackTrace();
				// セッションを削除
				session.invalidate();
				// エラー画面に遷移
				req.setAttribute("errorMessage", "グループ脱退処理中にエラーが発生しました。");
			    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}


			// ---グループ脱退処理が成功した場合
			// メインメニューに遷移
			req.getRequestDispatcher("/main").forward(req, res);

		break;

// 【どのcaseにも当てはまらない時】-------------------------------------------------------------------------------------
		default:

			System.out.println("不正なパラメーターが送られました。＠GroupMessageServlet");

		break;
		}

//	【ページ遷移処理】==============================================================================================
	// メッセージ送信時、メッセージ削除時にこの処理に入る。

		// 【遷移先グループのグループ作成者がログインユーザーか判定】
		try {
			// ----作成者でなかった場合
			if(groupInfoModel.judgeGroupCreator(userNo,groupNo) == false) {
				judgeGroupCreator = false;
			    }
		// ---DB接続中にエラーが発生した場合
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "グループ作成者判定処理中にエラーが発生しました。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// 【グループ会話内容取得】
		try {
			list = groupMessageModelLook.lookMessage(directMessageBean);
			// レコードが取得出来なかった場合
			if (list == null) {
				// セッションを削除
				session.invalidate();
				// エラー画面に遷移
				req.setAttribute("errorMessage", "会話内容が取得できませんでした。");
			    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			    }
		// ---DB接続中にエラーが発生した場合
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "会話内容取得中にエラーが発生しました。");
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		////////////////////////////////////////////////////////////////////////
		//		リクエストに情報をセット(jspへ)
		////////////////////////////////////////////////////////////////////////
		// グループ会話内容
		req.setAttribute("list", list);
		// 	グループ情報（グループ番号・グループ名）
		req.setAttribute("groupInfo", groupMessageBean);
		// グループ作成者かどうか
		req.setAttribute("judgeGroupCreator", judgeGroupCreator);

		// groupMessage.jspへ遷移
		req.getRequestDispatcher(direction).forward(req, res);


	}
}
