package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログアウト用のサーブレットです。
 * 各ページのヘッダーからログアウトボタンを押すと、このサーブレットに飛びます。
 * 遷移先ページは、ログインページです。
 * @author iyo-marina
 *
 */
public class LogoutServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		HttpSession session = req.getSession();
		session = req.getSession(false);
		session.invalidate();
		req.getRequestDispatcher("/WEB-INF/jsp/logout.jsp").forward(req, res);

	}
}
