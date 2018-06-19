package servlet;

/**
 * @author mitsuno-shinki
 *
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MyPageBean;
import bean.SessionBean;
import model.MyPageModel;

public class ShowProfileServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("ShowProfileServletにきました");
		// メインメニューからの遷移
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
		String userNo = req.getParameter("userNo");
		System.out.println("userNo：" + userNo);

		// 初期化
		MyPageModel showProfileModel = new MyPageModel();
		MyPageBean showProfileBean = new MyPageBean();
		//		sessionBean = (SessionBean) session.getAttribute("session");
		sessionBean.setUserNo(userNo);

		// プロフィール情報の取得（認証処理）
		try {
			System.out.println("profileGetにいきます");
			showProfileBean = showProfileModel.profileGet(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "DB接続中にエラーが発生しました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		String userName = showProfileBean.getUserName();
		String myPageText = showProfileBean.getMyPageText();

		//セット
		req.setAttribute("userName", userName);
		req.setAttribute("myPageText", myPageText);

		req.getRequestDispatcher("/WEB-INF/jsp/showProfile.jsp").forward(req, res);
	}

}
