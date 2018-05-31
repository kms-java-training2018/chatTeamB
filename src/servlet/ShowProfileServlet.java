package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SessionBean;
import bean.ShowProfileBean;
import model.ShowProfileModel;

public class ShowProfileServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// メインメニューから受け取る
		String otherNo = (String) req.getParameter("otherNo");

		// メインメニューからの遷移
		// セッションが保持されているかの確認
		// セッションがない場合
		HttpSession session = req.getSession();
		if (session == null) {
			// エラー画面に遷移
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}


		// セッションがある場合
		// Beanを使うための初期化
		ShowProfileModel modelShowProfile = new ShowProfileModel();
		SessionBean beanShowProfile = new SessionBean();
		beanShowProfile = (SessionBean) session.getAttribute("session");
		ShowProfileBean showProfileBean = new ShowProfileBean();


		// プロフィール情報の取得（認証処理）
				try {
					showProfileBean = modelShowProfile.ShowProfile(beanShowProfile);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("ShowProfileサーブレット、認証処理キャッチ");
				}

		req.getRequestDispatcher("/WEB-INF/jsp/showProfile.jsp").forward(req, res);
	}

}
