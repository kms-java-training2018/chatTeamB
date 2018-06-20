package servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MakeGroupBean;
import bean.SessionBean;
import model.GroupInfoModel;
import model.MakeGroupModel;

public class MakeGroupServlet extends HttpServlet {

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

		boolean userCheck = true;
		MakeGroupBean mgBean = new MakeGroupBean();
		String action = req.getParameter("action");

		switch (action) {

		case "groupCreate":
			// グループ作成時
			// 文字型に変換

			String groupName = (String) req.getParameter("inputGroupName");
			String[] groupMemberNo = req.getParameterValues("selectMember");
			ArrayList<String> memberList = new ArrayList<String>();
			for (String mem : groupMemberNo) {
				memberList.add(mem);
			}
			//入力文字数のチェック
			mgBean.setErrorMessage("");

			if (groupName.getBytes(Charset.forName("UTF-8")).length > 30
					|| groupName.getBytes(Charset.forName("UTF-8")).length == 0
					|| !(memberList.contains(sessionBean.getUserNo()))) {
				mgBean.setErrorMessage("エラーが発生しました。\nグループ名は30バイト以内で入力し、\nあなたをメンバーに含めてグループを作成してください");
				req.setAttribute("errorMessage", mgBean.getErrorMessage());
				userCheck = false;
			}

			if (userCheck == true) {
				try {
					GroupInfoModel gimodel = new GroupInfoModel();
					gimodel.groupCreate(sessionBean.getUserNo(), groupName, memberList);
					req.getRequestDispatcher("/main").forward(req, res);
				} catch (Exception e) {
					e.printStackTrace();
					// セッションを削除
					session.invalidate();
					// エラー画面に遷移
					req.setAttribute("errorMessage", "DB接続中にエラーが発生しました。");
					req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
				}
			}
			break;

		}
		// ページ遷移
		ArrayList<MakeGroupBean> allUserList = new ArrayList<MakeGroupBean>();
		MakeGroupModel makeGroupModel = new MakeGroupModel();
		//MakeGroupBean makeGroupBean = new MakeGroupBean();
		// プロフィール情報の取得（認証処理）
		try {
			allUserList = makeGroupModel.allUserGet();
		} catch (Exception e) {
			e.printStackTrace();
			// セッションを削除
			session.invalidate();
			// エラー画面に遷移
			req.setAttribute("errorMessage", "DB接続中にエラーが発生しました。");
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		req.setAttribute("allUserList", allUserList);
		req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
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

	}
}
