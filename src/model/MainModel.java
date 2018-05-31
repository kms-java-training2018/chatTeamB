package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.MainBean;
import bean.SessionBean;

/**
 * ログイン画面ビジネスロジック
 */
public class MainModel {

	public MainBean newMessage(SessionBean bean) {
		// 初期化
		MainBean mainBean = new MainBean();
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

			System.out.println("一つ目開始");

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

		// 会話情報テーブルから二者間の最新メッセージを取得
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			int count = 0;
			System.out.println("二つ目開始");
			for (String nom : otherNo) {
				if(count ==2) {
					System.out.println("2回目");
				}
				// SQL作成
				sb.append("SELECT ");
				sb.append("message ");
				sb.append("FROM ");
				sb.append("t_message_info ");
				sb.append("WHERE ");
				sb.append("(message_no = (");
				sb.append("SELECT ");
				sb.append("MAX(message_no) ");
				sb.append("FROM ");
				sb.append("t_message_info ");
				sb.append("WHERE ");
				sb.append("((send_user_no = " + userNo );
				sb.append("OR send_user_no = " + nom + ")");
				sb.append("AND (to_send_user_no = " + userNo );
				sb.append("OR to_send_user_no = " + nom + "))))");

				// SQL実行
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sb.toString());

				if (rs.next()) {
					message.add(rs.getString("message"));
				} else {
					message.add("会話を始めましょう");
				}
				count++;
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

		mainBean.setOtherNo(otherNo);
		mainBean.setOtherName(otherName);
		mainBean.setMessage(message);

		return mainBean;
	}
}
