package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginBean;

public class MainPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		//セッションが開始されたいたら処理を開始
		HttpSession session = req.getSession();
		if (session == null) {
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// Beanの初期化
		LoginBean bean = new LoginBean();
		String userNo = bean.getUserNo();
		String userName = bean.getUserName();

		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		//				bean.setErrorMessage("");
		//				bean.setUserId("");
		//				bean.setPassword("");
		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

	}

}