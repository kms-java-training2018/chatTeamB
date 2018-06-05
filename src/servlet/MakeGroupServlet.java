package servlet;

import java.io.IOException;
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
		case "groupTransition":

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

			req.setAttribute("allUserNo", makeGroupBean.getAllUserNo());
			req.setAttribute("allUserName", makeGroupBean.getAllUserName());

			req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
			break;

		case "groupCreate":
			// グループ作成時
			// 文字型に変換
			MakeGroupBean mgBean = new MakeGroupBean();
			String groupName = (String) req.getParameter("inputGroupName");
			String[] groupMember = req.getParameterValues("selectMember");
			System.out.println(groupMember);
			ArrayList<String> memberList = new ArrayList<String>();
			for (String mem : groupMember) {
				memberList.add(mem);
			}
			//入力文字数のチェック
			mgBean.setErrorMessage("");
			if (groupName.length() > 30 || (!(memberList.contains(session.getAttribute("userNo"))))) {
				mgBean.setErrorMessage("エラーが発生しました。\nグループ名は30文字以内で入力し、\n自分をチェックしてグループを作成してください");
				req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
			}
			req.setAttribute("errorMessage", mgBean.getErrorMessage());

			SessionBean sessionBean = new SessionBean();
			sessionBean = (SessionBean) session.getAttribute("session");
			GroupInfoModel gimodel = new GroupInfoModel();
			gimodel.groupCreate(sessionBean.getUserNo(), groupName, memberList);
			break;

		default:
			System.out.println("スイッチ文エラー");
			break;

		}
	}
}
