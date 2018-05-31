package model;

/**
 * @author mitsuno-shinki
 * ユーザの属しているグループ一覧、
 * 最新メッセージ表示
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.MainBean;
import bean.SessionBean;

public class MainModelGroup {
	public MainBean getGroup(SessionBean bean) {

		// 初期化
		MainBean mainBean = new MainBean();
		StringBuilder sb = new StringBuilder();
		String userNo = bean.getUserNo();
		ArrayList<String> groupNo = new ArrayList<String>();
		ArrayList<String> groupName = new ArrayList<String>();
		ArrayList<String> groupMessage = new ArrayList<String>();

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

		// ログインしたユーザが属しているグループ番号取得
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// SQL作成
			sb.append("SELECT ");
			sb.append("DISTINCT ");
			sb.append("m_group.group_no, ");
			sb.append("group_name ");
			sb.append("FROM ");
			sb.append("t_group_info ");
			sb.append("INNER JOIN ");
			sb.append("m_group ");
			sb.append("ON ");
			sb.append("m_group.group_no = t_group_info.group_no ");
			sb.append("WHERE ");
			sb.append("user_no = " + userNo);
			sb.append("ORDER ");
			sb.append("BY ");
			sb.append("m_group.group_no ");
			sb.append("DESC");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				//得た値をアレイリストに追加
				groupNo.add(rs.getString("group_no"));
				groupName.add(rs.getString("group_name"));
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


		// ログインしたユーザが属しているグループの最新メッセージ取得
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			for (String nom : groupNo) {

				StringBuilder sb3 = new StringBuilder();
				// SQL作成
				sb3.append("SELECT ");
				sb3.append("message ");
				sb3.append("FROM ");
				sb3.append("t_message_info ");
				sb3.append("WHERE ");
				sb3.append("(message_no = (");
				sb3.append("SELECT ");
				sb3.append("MAX(message_no) ");
				sb3.append("FROM ");
				sb3.append("t_message_info ");
				sb3.append("WHERE ");
				sb3.append("to_send_group_no = " + nom + "))");

				// SQL実行
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sb3.toString());

				if (rs.next()) {
					//trueならアレイリストに値を追加
					groupMessage.add(rs.getString("message"));
				} else {
					//falseならアレイリストに会話を始めましょうを追加
					groupMessage.add("会話を始めましょう");
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

		//mainBeanにセット
		mainBean.setGroupNo(groupNo);
		mainBean.setGroupName(groupName);
		mainBean.setGroupMessage(groupMessage);

		//mainBeanをまとめて返す
		return mainBean;
	}
}
