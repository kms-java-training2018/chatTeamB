package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String prof = request.getParameter("");
		// メインメニューからの遷移
		// セッションが保持されているかの確認(メイン→プロフ遷移時)
		// セッションがない場合、エラー画面に遷移
		HttpSession session = req.getSession();
		if (session == null) {
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

//		// Beanを使うための初期化
//		LoginBean bean = new LoginBean();
//		MyPageModel model = new MyPageModel();
//		String userNo = bean.getUserNo();
//		String userName = bean.getUserName();
//
//		// パラメータの取得
//		String userId = (String) req.getParameter("userId");
//		String password = (String) req.getParameter("password");
//
//		bean.setUserId(userId);
//		bean.setPassword(password);

		// セッションがある場合、プロフィール情報の取得（認証処理）
//		try {
//			bean = model.authentication(bean);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("プロフィールサーブレット、認証処理キャッチ");
//		}

		// 自分のプロフィール画面に遷移
		req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);

//		// プロフィール更新時
//		// 入力文字数のチェック
//		//		if (//!ユーザー名が1以上30以下 && 自己紹介文が1以上100以下) {
//		//			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jspp").forward(req, res);
//		//		}
//
//		// セッションが保持されているかの確認(更新時)
//		HttpSession session = req.getSession();
//		if (session == null) {
//			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jspp").forward(req, res);
//		}
//
//		// セッションがある場合、自分のプロフィール画面に遷移
//		// Beanの初期化
//		LoginBean bean = new LoginBean();
//		String userNo = bean.getUserNo();
//		String userName = bean.getUserName();
//
//		req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
//
//	}
//
//	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
//
//		req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
//
	}
}
