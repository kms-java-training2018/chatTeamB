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
 * @author mitsuno-shinki
 * メインメニュー画面(自分以外の会員の番号と名前を取得、
 * 二者間の最新メッセージを取得)
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

		// 会話情報テーブルから二者間の最新メッセージを取得
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			for (String nom : otherNo) {

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
				sb2.append("((send_user_no = " + userNo );
				sb2.append("OR send_user_no = " + nom + ")");
				sb2.append("AND (to_send_user_no = " + userNo );
				sb2.append("OR to_send_user_no = " + nom + "))))");

				// SQL実行
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sb2.toString());

				if (rs.next()) {
					message.add(rs.getString("message"));
				} else {
					//falseなら"会話を始めましょう"をアレイリストに追加
					message.add("会話を始めましょう");
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
		mainBean.setOtherNo(otherNo);
		mainBean.setOtherName(otherName);
		mainBean.setMessage(message);

		//まとめてmainBeanを返す
		return mainBean;
	}
}
