package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MakeGroupModel {

	public MakeGroupBean alluser() {
		// 初期化
		MakeGroupBean makeGroupBean = new MakeGroupBean();
		StringBuilder sb = new StringBuilder();
		String userNo = bean.getUserNo();
		String userName = bean.getUserName();
		ArrayList<String> otherNo = new ArrayList<String>();
		ArrayList<String> otherName = new ArrayList<String>();
		ArrayList<String> message = new ArrayList<String>();

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
			sb.append("ORDER ");
			sb.append("BY ");
			sb.append(" user_no ");
			sb.append("DESC");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				//ログインしているユーザの会員番号と名前を省く
				if (!((rs.getString("user_no").equals(userNo)) || (rs.getString("user_name").equals(userName)))) {
					otherNo.add(rs.getString("user_no"));
					otherName.add(rs.getString("user_name"));

				}
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
		return mainBean;
	}

}
