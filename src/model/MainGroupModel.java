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

public class MainGroupModel {

	public ArrayList<MainBean> getGroup(SessionBean bean) {

		// 初期化

		StringBuilder sb = new StringBuilder();
		String userNo = bean.getUserNo();
		ArrayList<MainBean> userGroupList = new ArrayList<MainBean>();

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
			sb.append(" AND out_flag = 0 ");
			sb.append("ORDER ");
			sb.append("BY ");
			sb.append("m_group.group_no ");
			sb.append("DESC");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				//得た値をBeanにセット
				MainBean mainBean = new MainBean();
				mainBean.setGroupNo(rs.getString("group_no"));
				mainBean.setGroupName(rs.getString("group_name"));
				userGroupList.add(mainBean);
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

			for (MainBean groupInfo : userGroupList) {

				StringBuilder sb2 = new StringBuilder();
				// SQL作成
				sb2.append("SELECT ");
				sb2.append("message ");
				sb2.append("FROM ");
				sb2.append("t_message_info ");
				sb2.append("WHERE ");
				sb2.append("(message_no = (");
				sb2.append("SELECT ");
				sb2.append("MAX(message_no) ");
				sb2.append("FROM ");
				sb2.append("t_message_info ");
				sb2.append("WHERE ");
				sb2.append("to_send_group_no = " + groupInfo.getGroupNo());
				sb2.append("AND delete_flag = " + 0 + "))");

				// SQL実行
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sb2.toString());

				if (rs.next()) {
					//trueなら今参照されているグループに最新メッセージをセット
					groupInfo.setGroupMessage(rs.getString("message"));
				} else {
					//falseなら会話を始めましょうをセット
					groupInfo.setGroupMessage("会話を始めましょう");
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

		//mainBeanをまとめて返す
		return userGroupList;
	}
}
