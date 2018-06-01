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

		// ページの行き先変更変数、初期値はエラー画面とする
		String direction = "/WEB-INF/jsp/directMessage.jsp";

		//セッション取得
		HttpSession session = req.getSession();
		/**
		//セッションがない場合エラー画面に移動
		if (session == null) {
			System.out.println("セッションがないです");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}
		 */

		// Beanの初期化

		// LoginModel参考
		/** クラスSessionBeanのインスタンス取得 */
		SessionBean sessionBean = new SessionBean();
		// セッションの"session"をクラスSessionBeanに代入
		sessionBean = (SessionBean) session.getAttribute("session");

		/** クラスDirectMessageBeanのインスタンス取得 */
		DirectMessageBean directMessageBean = new DirectMessageBean();
		directMessageBean.setUserNo("1"/*メインページが出来次第こちらを使う　sessionBean.getUserNo()*/);

		// 相手の会員番号をクラスDirectMessageBeanにセットlookMessageメソッドで使う。（送信対象者番号と比較する）
		directMessageBean.setToSendUserNo("2"/*(String) req.getParameter("相手の会員番号（送信対象者番号）")*/);
		System.out.println("ToSendUserNoは" + directMessageBean.getToSendUserNo());

		directMessageBean.setOtherName("お~い"/*(String) req.getParameter("相手の表示名")*/);
		System.out.println("OtherNameは" + directMessageBean.getOtherName());

		//------------------------------------------------------------------------------
		// ここから後回し
		//------------------------------------------------------------------------------
		// TODO 相手の会員番号をメインページからのリクエストスコープで取得


		//------------------------------------------------------------------------------
		// ここまで後回し
		//------------------------------------------------------------------------------


		///////directMessageBean
		////////////////////////////////////////////////////////////////////////////
		//
		//DirectMessageBeannにセッションスコープからuserNo（会員番号）取得
		//
		//     その後
		//
		// クラスsessionBean内の変数｛UserNo｝をクラスDirectMessageBean内にコピー
		//
		////////////////////////////////////////////////////////////////////////////



		// メソッド使うための下準備
		/** クラスDirectMessageModelLookのインスタンス取得
		 * メッセージ内容取得メソッドを使えるようにする */
		DirectMessageModelLook model = new DirectMessageModelLook();


		// jspに持っていくArrayList（会話内容、judge、会話番号）初期化
		ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();


		/** 必要ないかも？
		*メッセージ内容を格納するリストを初期化（DirectMessage.jspに持っていくため）
		*ArrayList<String> message = new ArrayList<String>();
		*送信対象者番号を格納するリスト初期化（DirectMessage.jspに持っていくため）
		*ArrayList<String> toSendUserNo = new ArrayList<String>();
		*自分のものか相手のかメッセージを分ける判断をするリストを初期化（DirectMessage.jspに持っていくため）
		*エラーが出た場合<String>型にする
		*ArrayList<String> judge = new ArrayList<String>();
		*/


		System.out.println("UserNoは" + directMessageBean.getUserNo());
		// 削除予定
		// directMessageBean.setUserName("2"/*sessionBean.getUserName()*/);
		// System.out.println("UserNameは" + directMessageBean.getUserName());

		//------------------------------------------------------------------------------
		// ここまで完成
		//------------------------------------------------------------------------------

		// TODO　認証処理とは何か？
		try {
			System.out.println("DirectMessageModelLookにいきます");
			list = model.lookMessage(directMessageBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DirectMessageServlet、認証処理キャッチ");
		}
		System.out.println("DirectMessageServletに帰ってきました");

		// 必要ないかも？メッセジーの個数カウント用変数
		// DBからメッセージ内容を取得し格納する
		//message = directMessageBean.getMessage();
		// DBから送信対象者番号を取得し格納する
		//judge = directMessageBean.getJudge();
		//int count = 0;

		DirectMessageBean dMBean = new DirectMessageBean();
		// 必要ないかも？確認用
		for (int i = 0; i < list.size(); i++) {
			dMBean = list.get(i);
			System.out.println(dMBean.getListMessage());
			System.out.println(dMBean.getListJudge());
		}
		// 必要！！！
		req.setAttribute("list", list);

		req.getRequestDispatcher(direction).forward(req, res);
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


	}
}
