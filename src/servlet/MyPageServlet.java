package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MyPageBean;
import bean.MyPageUpdateBean;
import bean.SessionBean;
import model.MyPageModel;
import model.MyPageUpdateModel;

public class MyPageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// メインメニューから受け取る
		//		String userId = (String) req.getParameter("userId");
		//		String password = (String) req.getParameter("password");

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
		MyPageModel model = new MyPageModel();
		SessionBean bean = new SessionBean();
		bean = (SessionBean) session.getAttribute("session");
		MyPageBean myPageBean = new MyPageBean();
		//		String a = bean.getUserNo();
		//		System.out.println(a);

		// プロフィール情報の取得（認証処理）
		try {
			myPageBean = model.ProfileGet(bean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("プロフィールサーブレット、認証処理キャッチ");
		}

		// 自分のプロフィール画面に遷移
		req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);





		// プロフィール画面から受け取る
		//		String password = (String) req.getParameter("password");
		//		String userId = (String) req.getParameter("userId");
		// プロフィール更新時
		// セッションが保持されているかの確認
		// セッションがない場合
		HttpSession sessionUpdate = req.getSession();
		if (sessionUpdate == null) {
			// エラー画面に遷移
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jspp").forward(req, res);
		}


		// セッションがある場合
		// Beanの初期化
		MyPageUpdateModel modelUpdate = new MyPageUpdateModel();
		SessionBean beanUpdate = new SessionBean();
		beanUpdate = (SessionBean) session.getAttribute("session");
		beanUpdate.getUserNo();
		MyPageUpdateBean myPageUpdateBean = new MyPageUpdateBean();
		// 文字型に変換
		String user_name = myPageUpdateBean.getUserName();
		String my_page_text = myPageUpdateBean.getMyPageText();
		// 文字の長さを設定
		int userName = user_name.length();
		int myPageText = my_page_text.length();


		//入力文字数のチェック
		if (userName <= 30 && myPageText <= 100) {
			// 情報取得
			try {
				myPageUpdateBean = modelUpdate.ProfileUpdateGet(beanUpdate);
			} catch (Exception e) {
				e.printStackTrace();
				req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
				System.out.println("プロフィールサーブレット、認証処理キャッチ");
			}
			// メインメニューに遷移
			req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

		} else {
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

	}

}
