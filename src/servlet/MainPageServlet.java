package servlet;

/**
 * @author mitsuno-shinki
 * 会員とグループの一覧・最新メッセージをjspに送る
 */

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
import model.MainGroupModel;

public class MainPageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

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

		// 初期化
		MainModel mainModel = new MainModel();
		MainGroupModel mainGroupModel = new MainGroupModel();
		ArrayList<MainBean> otherUserList = new ArrayList<MainBean>();
		ArrayList<MainBean> userGroupList = new ArrayList<MainBean>();

		// 認証処理
		try {
			//会員一覧と最新メッセージを取得
			otherUserList = mainModel.newMessage(sessionBean);
			//グループ一覧と最新メッセージを取得
			userGroupList = mainGroupModel.getGroup(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("メインページサーブレット、認証処理キャッチ");
		}

		//ヘッダー用にユーザ名をセッションにセット
		session.setAttribute("userName", sessionBean.getUserName());

		req.setAttribute("otherUserList", otherUserList);
		req.setAttribute("userGroupList", userGroupList);

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

}