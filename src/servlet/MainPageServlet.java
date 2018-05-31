package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MainBean;
import bean.SessionBean;
import model.MainModel;

public class MainPageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		System.out.println("メインにきました");
		//セッションが開始されたいたら処理を開始
		HttpSession session = req.getSession();
		if (session == null) {
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// Beanの初期化
		MainBean mainBean = new MainBean();
		SessionBean sessionBean = new SessionBean();
		sessionBean = (SessionBean) session.getAttribute("session");

		MainModel model = new MainModel();

		// 認証処理
		try {

			mainBean = model.newMessage(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("メインサーブレット、認証処理キャッチ");
		}
		ArrayList<String> otherNo = new ArrayList<String>();
		ArrayList<String> otherName = new ArrayList<String>();
		ArrayList<String> message = new ArrayList<String>();

		otherNo = mainBean.getOtherNo();
		otherName = mainBean.getOtherName();
		message = mainBean.getMessage();

		req.setAttribute("otherNo", otherNo);
		req.setAttribute("otherName", otherName);
		req.setAttribute("message", message);

//		req.setAttribute("mainBean", mainBean);

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("doGet");
		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

	}

}