package servlet;

/**
 * @author mitsuno-shinki
 * 会員とグループの一覧・最新メッセージをjspに送る
 */

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MainBean;
import bean.SessionBean;
import model.MainGroupModel;
import model.MainModel;

public class MainPageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// 【セッションが開始しているかどうかの判定】
		HttpSession session = req.getSession(false);
		// ---開始していない場合(タイムアウト含む)
		if (session == null) {
			//nullならセッションは切れている。
			// エラー画面に遷移
			req.setAttribute("errorMessage", "セッションが開始されていない、もしくはタイムアウトになりました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
			// ---すでに開始している場合
		} else {
			// 開始済みセッションを取得
			session = req.getSession(true);
		}

		// 【セッション内にログイン情報を保持しているかどうかの判定】
		SessionBean sessionBean = (SessionBean) session.getAttribute("session");

		// ---保持されていない場合
		if (sessionBean == null
				|| sessionBean.getUserNo().equals(null)
				|| sessionBean.getUserName().equals(null)) {
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "ログインされていません。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// 初期化
		MainModel mainModel = new MainModel();
		MainGroupModel mainGroupModel = new MainGroupModel();
		ArrayList<MainBean> otherUserList = new ArrayList<MainBean>();
		ArrayList<MainBean> userGroupList = new ArrayList<MainBean>();

		// 認証処理
		try {
			//会員一覧と最新メッセージを取得
			otherUserList = mainModel.newMessage(sessionBean);
			//グループ一覧と最新メッセージを取得
			userGroupList = mainGroupModel.getGroup(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
            session.invalidate();
            // エラー画面に遷移
            req.setAttribute("errorMessage", "DB接続中にエラーが発生しました。");
            req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		//ヘッダー用にユーザ名をセッションにセット
		session.setAttribute("userName", sessionBean.getUserName());

		req.setAttribute("otherUserList", otherUserList);
		req.setAttribute("userGroupList", userGroupList);

		//セッションにも他ユーザーの情報をセット
		session.setAttribute("toSendUserList", otherUserList);

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

}