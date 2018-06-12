package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.MakeGroupBean;

public class MakeGroupModel {

	public ArrayList<MakeGroupBean> allUserGet() {
		// 初期化


//		ArrayList<String> allUserNo = new ArrayList<String>();
//		ArrayList<String> allUserName = new ArrayList<String>();
		ArrayList<MakeGroupBean> allUserList = new ArrayList<MakeGroupBean>();

		StringBuilder sb = new StringBuilder();
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

		// 会員番号と表示名取得
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// SQL作成
			sb.append("SELECT ");
			sb.append("user_no ");
			sb.append(" ,user_name ");
			sb.append("FROM ");
			sb.append(" m_user ");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				MakeGroupBean makeGroupBean = new MakeGroupBean();
				makeGroupBean.setAllUserNo(rs.getString("user_no"));
				makeGroupBean.setAllUserName(rs.getString("user_name"));
				allUserList.add(makeGroupBean);
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

		return allUserList;
	}

}
