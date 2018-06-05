package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MakeGroupBean;
import model.MakeGroupModel;

public class MakeGroupServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// セッションが保持されているかの確認
		// セッションがない場合
		HttpSession session = req.getSession();

		if (session == null) {
			// エラー画面に遷移
			session = req.getSession(false);
			session = null;
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}
		String action = req.getParameter("action");
		switch (action) {

		case groupCreate:
			// グループ作成時
			// 文字型に変換
			MakeGroupBean mgBean = new MakeGroupBean();
			String groupName = (String) req.getParameter("inputGroupName");
			String[] groupMember = req.getParameter("inputGroupMember");
			ArrayList<String> memberList = new ArrayList<String>();
			for (String mem:groupMember) {
				memberList.add(mem);
			}
			//入力文字数のチェック
			//TODO<p>${errorMessage}</p>これをjspに
			mgBean.setErrorMessage("");
			req.setAttribute("errorMessage", mgBean.getErrorMessage());
			if (groupName.length() > 30 || memberList.contains(session.getAttribute("userName"))) {
				mgBean.setErrorMessage("入力文字数が不正です。\n正しい文字数で入力してください");
				req.setAttribute("errorMessage", mgBean.getErrorMessage());
				req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
			}



		default:

			// メインメニューからの遷移
			MakeGroupModel model = new MakeGroupModel();
			MakeGroupBean makeGroupBean = new MakeGroupBean();
			// プロフィール情報の取得（認証処理）
			try {
				makeGroupBean = model.allUserGet();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("メイクグループサーブレット、認証処理キャッチ");
			}

			session.setAttribute("allUserNo", makeGroupBean.getAllUserNo());
			session.setAttribute("allUserName", makeGroupBean.getAllUserName());

			req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
		}
	}
}
