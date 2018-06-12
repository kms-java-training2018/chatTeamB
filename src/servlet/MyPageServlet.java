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

public class MyPageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.setCharacterEncoding("utf-8");
		//セッションが存在していたら処理を開始
		//セッション取得
		HttpSession session = req.getSession();
		/** クラスSessionBeanの初期化 */
		SessionBean sessionBean = (SessionBean) session.getAttribute("session");
		//　セッションがない場合エラー画面に移動
		if (sessionBean.getUserNo().equals(null) || sessionBean.getUserName().equals(null)) {
			System.out.println("セッションがないです");
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		MyPageBean myPageBean = new MyPageBean();
		String action = req.getParameter("action");
		switch (action) {
		case "myPageTransition":
			// セッションがある場合
			// Beanを使うための初期化
			MyPageModel model = new MyPageModel();

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
			break;

		case "profileUpdate":

			// プロフィール更新時
			// 文字型に変換
			// プロフィール編集後、「プロフィール更新」ボタン押下時
			String editName = (String) req.getParameter("inputUserName");
			String editText = (String) req.getParameter("inputUserSelfIntro");
			//入力文字数のチェック
			myPageBean.setErrorMessage("");
			if (editName.length() > 30 || editText.length() > 100) {
				myPageBean.setErrorMessage("入力文字数が不正です。\n正しい文字数で入力してください");

				req.setAttribute("errorMessage", myPageBean.getErrorMessage());
				req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
			}

			boolean result = true;
			// Beanの初期化
			MyPageModel updateModel = new MyPageModel();
			//sessionBean = (SessionBean) session.getAttribute("session");
			myPageBean.setUserNo(sessionBean.getUserNo());
			myPageBean.setUserName(editName);
			myPageBean.setMyPageText(editText);

			// 情報取得
			try {
				result = updateModel.profileUpdate(myPageBean);

			} catch (Exception e) {
				e.printStackTrace();
				req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
				System.out.println("プロフィール編集サーブレット、認証処理キャッチ");
			}
			if (result == false) {
				System.out.println("プロフィール編集できませんでした");
			}
			//変更された表示名をセッションにセット
			sessionBean.setUserName(myPageBean.getUserName());
			session.setAttribute("userName", sessionBean.getUserName());
			session.setAttribute("session", sessionBean);

			// メインメニューに遷移
			req.getRequestDispatcher("/main").forward(req, res);
			break;

		default:
			System.out.println("スイッチ文エラー");
			break;

		}
	}
}
