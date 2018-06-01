package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.MyPageBean;
import bean.SessionBean;

public class MyPageUpdateModel {
	public MyPageBean profileUpdateGet(SessionBean sessionBean) {
		// 初期化
		MyPageBean myPageBean = new MyPageBean();
		String updateUserName = myPageBean.getUpdateUserName();
		String updateMyPageText = myPageBean.getUpdateMyPageText();
		String userNo = sessionBean.getUserNo();

		StringBuilder sb = new StringBuilder(); // SQL文の格納用
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_B";
		String dbPassword = "B_DEV_TEAM";

		// JDBCドライバーのロード
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 接続を作成
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// 表示名と自己紹介文を更新
			// SQL作成
			sb.append("UPDATE ");
			sb.append("m_user ");
			sb.append("SET ");
			sb.append("user_name = '" + updateUserName + "'");
			sb.append(", my_page_text = '" + updateMyPageText + "' ");
			sb.append("where user_no = '" + userNo + "' ");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

		} catch (SQLException e) {
			e.printStackTrace();
			//SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return myPageBean;
	}
}
