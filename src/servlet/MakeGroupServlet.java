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

		req.setCharacterEncoding("utf-8");
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
		MakeGroupBean mgBean = new MakeGroupBean();
		String action = req.getParameter("action");
		switch (action) {
		case "groupTransition":

			// メインメニューからの遷移
			ArrayList<MakeGroupBean> allUserList = new ArrayList<MakeGroupBean>();
			MakeGroupModel makeGroupModel = new MakeGroupModel();
			//MakeGroupBean makeGroupBean = new MakeGroupBean();
			// プロフィール情報の取得（認証処理）
			try {
				allUserList = makeGroupModel.allUserGet();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("メイクグループサーブレット、認証処理キャッチ");
			}

			req.setAttribute("errorMessage", mgBean.getErrorMessage());
			req.setAttribute("allUserList", allUserList);
			req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
			break;

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

			if (groupName.length() > 30 || !(memberList.contains(sessionBean.getUserNo()))) {
				mgBean.setErrorMessage("エラーが発生しました。\nグループ名は30文字以内で入力し、\n自分をチェックしてグループを作成してください");
				req.setAttribute("action", "groupTransition");
				req.getRequestDispatcher("/makeGroup").forward(req, res);
			}

			GroupInfoModel gimodel = new GroupInfoModel();
			gimodel.groupCreate(sessionBean.getUserNo(), groupName, memberList);
			req.getRequestDispatcher("/main").forward(req, res);
			break;

		default:
			System.out.println("スイッチ文エラー");
			break;

		}
	}
}
