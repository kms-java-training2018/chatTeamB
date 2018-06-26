package servlet;

/**
 * @author mitsuno-shinki
 * ユーザのプロフィール編集画面
 * TODO 受け取る値によって処理分岐
 */

import java.io.IOException;
import java.nio.charset.Charset;

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

		boolean byteCheck = true;
		MyPageBean myPageBean = new MyPageBean();
		String action = req.getParameter("action");
		switch (action) {

		case "profileUpdate":

			// プロフィール更新時
			// 文字型に変換
			// プロフィール編集後、「プロフィール更新」ボタン押下時
			String editName = (String) req.getParameter("inputUserName");
			String editText = (String) req.getParameter("inputUserSelfIntro");
			//入力文字数のチェック
			myPageBean.setErrorMessage("");
			if (editName.getBytes(Charset.forName("UTF-8")).length == 0
					|| editName.getBytes(Charset.forName("UTF-8")).length > 30
					|| editText.getBytes(Charset.forName("UTF-8")).length == 0
					|| editText.getBytes(Charset.forName("UTF-8")).length > 100) {
				myPageBean.setErrorMessage("設定されているバイト数の範囲外です。");
				byteCheck = false;
				req.setAttribute("errorMessage", myPageBean.getErrorMessage());

			}
			if (byteCheck == true) {
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
					// セッションを削除
					session.invalidate();
					// エラー画面に遷移
					req.setAttribute("errorMessage", "DB接続中にエラーが発生しました。");
					req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
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
			}

		}
		MyPageModel model = new MyPageModel();

		// メインメニューからの遷移
		// プロフィール情報の取得（認証処理）
		try {
			myPageBean = model.profileGet(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "DB接続中にエラーが発生しました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		String userName = myPageBean.getUserName();
		String myPageText = myPageBean.getMyPageText();

		//セット
		req.setAttribute("userName", userName);
		req.setAttribute("myPageText", myPageText);

		// 自分のプロフィール画面に遷移
		req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
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

		MyPageModel model = new MyPageModel();
		MyPageBean myPageBean = new MyPageBean();

		// メインメニューからの遷移
		// プロフィール情報の取得（認証処理）
		try {
			myPageBean = model.profileGet(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "DB接続中にエラーが発生しました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
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
