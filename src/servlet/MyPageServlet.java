package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		//セッション保持の確認
		HttpSession session = request.getSession();
		if(session == null) {
			req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
		}


	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		StringBuilder sb = new StringBuilder();		// SQL文の格納用

		//SQL作成
		sb.append("SELECT");
		sb.append("user_name");
		sb.append("my_page_text");
		sb.append("FROM");
		sb.append("m_user");
		sb.append("WHERE");
		sb.append("user_no like");

		//SQL実行
		Statement stmt = conn.createStatement();			// SQL文をデータベースに送るためのStatementオブジェクトを生成
		ResultSet rs = stmt.executeQuery(sb.toString());	// 実行し、その結果を格納


		req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);

	}
}
