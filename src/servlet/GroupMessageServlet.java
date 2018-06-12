package servlet;

import java.io.IOException;
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

//		// 【デバッグ用】-------------------------------------
//		// セッション切れた状況を作り出す
//		HttpSession session = req.getSession(true);
//		session.invalidate();
//		------------------------------------------------------

		// セッションがタイムアウトしてるかどうかの判定
		HttpSession session = req.getSession(false);
		if(session == null){
			//nullならセッションは切れている。
			// エラー画面に遷移
			req.setAttribute("errorMessage", "セッションがタイムアウトになりました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

		////////////////////////////////////////////////////////////////////////
		//		セッションとパラメーターから情報を取得
		////////////////////////////////////////////////////////////////////////
		// セッションから：ログインユーザーの会員番号・表示名
		SessionBean sessionBean = (SessionBean) session.getAttribute("session");
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

		// 【セッションにログイン情報が保持されているか判定】
		// ---保持されていない場合
		if (userNo.equals(null) || userName.equals(null)) {
		    // セッションを削除
			session = req.getSession(false);
		    session = null;
		    // エラー画面に遷移
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		// 	【遷移先グループにログインユーザーが参加しているか判定】
		// ---グループメンバーでなかった場合
		if(groupInfoModel.judgeGroupMember(userNo,groupNo) == false) {
			// セッションを削除
			session = req.getSession(false);
		    session = null;
		    // エラー画面に遷移
		    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		// 【グループ会話内容取得】
		try {
			list = groupMessageModelLook.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// レコードが取得出来なかった場合
		if (list == null) {
			// セッションを削除
			session = req.getSession(false);
			session = null;
			// エラーページに遷移
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		////////////////////////////////////////////////////////////////////////
		//		リクエストに情報をセット(jspへ)
		////////////////////////////////////////////////////////////////////////
		// グループ会話内容
		req.setAttribute("list", list);
		// 	グループ情報（グループ番号・グループ名）
		req.setAttribute("groupInfo", groupMessageBean);

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
		//		セッションとパラメーターから情報を取得
		////////////////////////////////////////////////////////////////////////
		// セッションから：ログインユーザーの会員番号・表示名
		HttpSession session = req.getSession();
		SessionBean sessionBean = (SessionBean)session.getAttribute("session");
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
			if(groupInfoModel.judgeGroupMember(userNo,groupNo) == false) {
				// セッションを削除
				session = req.getSession(false);
			    session = null;
			    // エラー画面に遷移
			    req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

			// TODO 【入力されたメッセージが1桁以上100桁以内か判定】
			// ---不正な入力だった場合、エラーメッセージを設定し、グループ画面に遷移する。
			// TODO jspにもエラーメッセージ表示コード書く、beanにエラーメッセージフィールド追加



			// 【入力されたメッセージを会話情報テーブルに登録】
			// ---登録処理が失敗した場合(メソッドの戻り値がfalseの場合)
			if (messageInfoModel.entryMessage(userNo, inputMessage, groupNo, 1) == false) {
				// セッションの情報を削除する
				session = req.getSession(false);
				session = null;
				// エラーページに遷移
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

			// ---登録処理が成功した場合
			// switch文を抜けて、ページ遷移処理を行う。
		break;

// 【メッセージ削除時】-------------------------------------------------------------------------------------------------
//		グループ画面で削除ボタンを押したときに実行される処理。
//		ダイアログで論理削除確認後(JSP)、会話情報を論理削除する。
//		あわせて、最新の会話情報テーブルの情報を表示

		case "deleteMessage":

			// リクエストから論理削除するmessageNoを取得
			String messageNo = req.getParameter("messageNo");

			// メッセージ削除用のメソッドを呼び出し(削除処理を実行し)、
			// 【削除処理が失敗した場合(メソッドの戻り値がfalseの場合)】
			if (messageInfoModel.deleteMessage(messageNo) == false) {
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

			// グループ脱退用のメソッドを呼び出し
			// 【グループ脱退処理が失敗した場合】
			if(groupInfoModel.groupLeave(userNo, groupNo) == false) {
				// セッションの情報を削除する
				session = req.getSession(false);
				session = null;

				// エラーページに遷移
				req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			}

			// 【グループ脱退処理が成功した場合】
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

		// 【グループ会話内容取得】
		try {
			list = groupMessageModelLook.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ---レコードが取得出来なかった場合
		if (list == null) {
			// セッションを削除
			session = req.getSession(false);
			session = null;
			// エラーページに遷移
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		////////////////////////////////////////////////////////////////////////
		//		リクエストに情報をセット(jspへ)
		////////////////////////////////////////////////////////////////////////
		// グループ会話内容
		req.setAttribute("list", list);
		// 	グループ情報（グループ番号・グループ名）
		req.setAttribute("groupInfo", groupMessageBean);

		// groupMessage.jspへ遷移
		req.getRequestDispatcher(direction).forward(req, res);


	}
}
