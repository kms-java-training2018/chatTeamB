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
import model.MainModelGroup;

public class MainPageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		//セッションが存在していたら処理を開始
		HttpSession session = req.getSession();
		if (session == null) {
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		}

		// 初期化
		MainBean mainBean = new MainBean();
		MainBean mainBeanGroup = new MainBean();
		SessionBean sessionBean = new SessionBean();
		sessionBean = (SessionBean) session.getAttribute("session");
		MainModel model = new MainModel();
		MainModelGroup modelGroup = new MainModelGroup();

		// 認証処理
		try {
			//会員一覧と最新メッセージを取得
			mainBean = model.newMessage(sessionBean);
			//グループ一覧と最新メッセージを取得
			mainBeanGroup = modelGroup.getGroup(sessionBean);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("メインページサーブレット、認証処理キャッチ");
		}

		//会員番号、名前、最新メッセージ用のアレイリスト生成
		ArrayList<String> otherNo = new ArrayList<String>();
		ArrayList<String> otherName = new ArrayList<String>();
		ArrayList<String> message = new ArrayList<String>();

		//それぞれに値を格納
		otherNo = mainBean.getOtherNo();
		otherName = mainBean.getOtherName();
		message = mainBean.getMessage();

		//セット
		req.setAttribute("otherNo", otherNo);
		req.setAttribute("otherName", otherName);
		req.setAttribute("message", message);

		//グループ番号、グループ名、最新メッセージ用のアレイリスト生成
		ArrayList<String> groupNo = new ArrayList<String>();
		ArrayList<String> groupName = new ArrayList<String>();
		ArrayList<String> groupMessage = new ArrayList<String>();

		//それぞれに値を格納
		groupNo = mainBeanGroup.getGroupNo();
		groupName = mainBeanGroup.getGroupName();
		groupMessage = mainBeanGroup.getGroupMessage();

		//セット
		req.setAttribute("groupNo", groupNo);
		req.setAttribute("groupName", groupName);
		req.setAttribute("groupMessage", groupMessage);

		// メッセージ画面用仮
		// まずはSessionBeanに情報をセット
		sessionBean.setOtherNo(mainBean.getOtherNo());

		// グループ画面用仮
		// まずはSessionBeanに情報をセット
		sessionBean.setGroupNo(mainBean.getGroupNo());

		// セッションに、SessionBeanをセット
		session.setAttribute("session", sessionBean);

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("doGet");
		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

	}

}