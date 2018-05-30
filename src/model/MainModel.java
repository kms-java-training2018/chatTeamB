package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.LoginBean;
import bean.MainBean;

/**
 * ログイン画面ビジネスロジック
 */
public class MainModel {

	public MainBean authentication(MainBean bean) {
		// 初期化
		LoginBean log = new LoginBean();
		LoginModel model = new LoginModel();
		StringBuilder sb = new StringBuilder();
		String userNo = log.getUserNo();
		String userName = log.getUserName();

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

		// 認証処理
		log.getUserId();
		log.getPassword();
		try {
			log = model.authentication(log);
		} catch (Exception e) {
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
				if (!(rs.getString("user_no").equals(userNo) && rs.getString("user_name").equals(userName))) {
					bean.setOtherNo(rs.getString("user_no"));
					bean.setOtherName(rs.getString("user_name"));
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

		// 会話情報テーブルから二者間の最新メッセージを取得
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// SQL作成
			sb.append("SELECT ");
			sb.append("  ");
			sb.append("FROM ");
			sb.append(" ");
			sb.append("ORDER ");
			sb.append("BY ");
			sb.append("  ");
			sb.append("DESC");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				bean.setOtherName(rs.getString("user_name"));
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

		return bean;
	}
}
