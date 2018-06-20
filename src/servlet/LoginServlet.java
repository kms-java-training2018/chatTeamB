package servlet;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginBean;
import bean.SessionBean;
import model.LoginModel;

/**
 * ログイン画面用サーブレット
 */
public class LoginServlet extends HttpServlet {

	/**
	 * 初期表示
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Beanの初期化
		LoginBean bean = new LoginBean();
		bean.setErrorMessage("");
		bean.setUserId("");
		bean.setPassword("");

		req.setAttribute("loginBean", bean);
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 初期化
		LoginBean bean = new LoginBean();
		LoginModel model = new LoginModel();
		String direction = "/WEB-INF/jsp/login.jsp";

		// パラメータの取得
		String userId = (String) req.getParameter("userId");
		String password = (String) req.getParameter("password");

		// 桁数チェック：ID、パスワード共に1バイト以上20バイト以下
		if (userId.getBytes(Charset.forName("UTF-8")).length <= 0
				|| 20 < userId.getBytes(Charset.forName("UTF-8")).length
				|| password.getBytes(Charset.forName("UTF-8")).length <= 0
				|| 20 < password.getBytes(Charset.forName("UTF-8")).length) {
			// エラーメッセージをリクエストにセット
			req.setAttribute("errorMessage", "設定されているバイト数の範囲外です");
			req.getRequestDispatcher(direction).forward(req, res);
		}

		// 文字種チェック：ID、パスワード共に半角英数のみ受け付ける
		if (!userId.matches("^[0-9a-zA-Z]+$")
				|| !password.matches("^[0-9a-zA-Z]+$")) {
			// エラーメッセージをリクエストにセット
			req.setAttribute("errorMessage", "IDとパスワードは半角英数で入力してください。");
			req.getRequestDispatcher(direction).forward(req, res);
		}


		bean.setUserId(userId);
		bean.setPassword(password);

		// 認証処理
		try {
			bean = model.authentication(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 登録済み会員情報の取得に成功した場合(ログイン可の場合)セッション情報をセット
		if ("".equals(bean.getErrorMessage())) {			// ログイン成功の場合、エラーメッセは空文字
			// ますはSessionBeanに情報をセット
			SessionBean sessionBean = new SessionBean();
			sessionBean.setUserName(bean.getUserName());
			sessionBean.setUserNo(bean.getUserNo());
			// セッションに、SessionBeanをセット
			HttpSession session = req.getSession();
			session.setAttribute("session", sessionBean);

			// 行き先を次の画面に（サーブレットのURLを指定）
			direction = "/main";

		// 登録済み会員情報の取得に失敗した場合(ログイン不可の場合)、ログイン画面に遷移しエラーメッセを表示
		} else {
			// beanのerrorMessageをパラメーターにセット
			req.setAttribute("errorMessage", bean.getErrorMessage());

		}
		req.getRequestDispatcher(direction).forward(req, res);
	}
}
