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
		// セッションが保持されているかの確認
		// セッションがない場合
		HttpSession session = req.getSession();
		if (session == null) {
			// エラー画面に遷移
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}
		String userNo = req.getParameter("userNo");
		System.out.println("userNo：" + userNo);

//		TODO セッションチェックは後で確認
//		if (userNo == null) {
//			// エラー画面に遷移
//			session = req.getSession(false);
//			session = null;
//			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
//		}

		// 初期化
		MyPageModel showProfileModel = new MyPageModel();
		SessionBean sessionBean = new SessionBean();
		MyPageBean showProfileBean = new MyPageBean();
//		sessionBean = (SessionBean) session.getAttribute("session");
		sessionBean.setUserNo(userNo);

		// プロフィール情報の取得（認証処理）
		try {
			System.out.println("profileGetにいきます");
			showProfileBean = showProfileModel.profileGet(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ShowProfileサーブレット、認証処理キャッチ");
		}
		System.out.println("ShowProfileServletに帰ってきました");

		String userName = showProfileBean.getUserName();
		String myPageText = showProfileBean.getMyPageText();

		//セット
		req.setAttribute("userName", userName);
		req.setAttribute("myPageText", myPageText);

		req.getRequestDispatcher("/WEB-INF/jsp/showProfile.jsp").forward(req, res);
	}

}
