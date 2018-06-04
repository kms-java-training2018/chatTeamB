package servlet;

/**
 * @author mitsuno-shinki
 * ユーザのプロフィール編集画面
 * TODO 受け取る値によって処理分岐
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
import model.MyPageUpdateModel;

public class MyPageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

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
		SessionBean sessionBean = new SessionBean();
		sessionBean = (SessionBean) session.getAttribute("session");
		MyPageBean myPageBean = new MyPageBean();

		String action = req.getParameter("action");
		switch (action) {
		case "profileUpdate":

			// プロフィール更新時
			// 文字型に変換
			// プロフィール編集後、「プロフィール更新」ボタン押下時
			String editName = (String) req.getParameter("editName");
			String editText = (String) req.getParameter("editText");
			//入力文字数のチェック
			myPageBean.setErrorMessage("");
			if (editName.length() > 30 || editText.length() > 100) {
				myPageBean.setErrorMessage("入力文字数が不正です。\n正しい文字数で入力してください");

				req.setAttribute("errorMessage", myPageBean.getErrorMessage());
				req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
			}

			// Beanの初期化
			MyPageUpdateModel update = new MyPageUpdateModel();
			sessionBean = (SessionBean) session.getAttribute("session");

			myPageBean.setUpdateUserName(editName);
			myPageBean.setUpdateMyPageText(editText);

			// 情報取得
			try {
				myPageBean = update.profileUpdateGet(sessionBean);
			} catch (Exception e) {
				e.printStackTrace();
				req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
				System.out.println("プロフィール編集サーブレット、認証処理キャッチ");
			}
			// メインメニューに遷移
			req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

		default:

			// メインメニューからの遷移
			// プロフィール情報の取得（認証処理）
			try {
				myPageBean = model.profileGet(sessionBean);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("プロフィールサーブレット、認証処理キャッチ");
			}

			String userName = myPageBean.getUserName();
			String myPageText = myPageBean.getMyPageText();

			//セット
			req.setAttribute("userName", userName);
			req.setAttribute("myPageText", myPageText);

			// 自分のプロフィール画面に遷移
			req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);

		}
	}
}
