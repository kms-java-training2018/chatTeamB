package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import bean.MyPageBean;

public class MyPageUpdateModel {
	public MyPageBean profileUpdateGet(MyPageBean myPageBean) {
		// 初期化
		boolean result = true;
		String updateUserName = myPageBean.getUpdateUserName();
		String updateMyPageText = myPageBean.getUpdateMyPageText();
		String userNo = myPageBean.getUserNo();

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
			int rs = stmt.executeUpdate(sb.toString()); // 実行し、その結果(更新された行数)を格納

			// 処理
			// SQL実行後に、更新されたレコードが無かった場合(削除処理が失敗)
			if (rs == 0) {
				// 実行結果をfalse(失敗)に設定
				result = false;
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
		return myPageBean;
	}
}
