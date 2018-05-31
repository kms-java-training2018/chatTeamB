package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.SessionBean;
import bean.ShowProfileBean;

public class ShowProfileModel {
	public ShowProfileBean ShowProfile(SessionBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder(); // SQL文の格納用
		String userNo = bean.getUserNo();
		ShowProfileBean showProfileBean = new ShowProfileBean();

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

			// 表示名と自己紹介文を取得
			// SQL作成
			sb.append("SELECT ");
			sb.append("user_name ");
			sb.append(" ,my_page_text ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append(" WHERE ");
			sb.append(" user_no = '" + userNo + "' ");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			System.out.println("SQL実行完了");

			// SQL実行結果に1行目があるかどうか(DBに該当データがあるかどうか)
			if (!rs.next()) { // 無かった場合
				showProfileBean.setOtherUserName("");
				showProfileBean.setOtherPageText("");
			} else { // あった場合
				// beanに、DB会員マスタの表示名と自己紹介文、空文字を代入
				showProfileBean.setOtherUserName(rs.getString("user_name"));
				showProfileBean.setOtherPageText(rs.getString("my_page_text"));
			}
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

		System.out.println("マイページモデルを抜けます");
		return showProfileBean;
	}

}
