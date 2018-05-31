package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.DirectMessageBean;

public class DirectMessageModelLook {
	// メッセージ閲覧メソッドの宣言
	public DirectMessageBean lookMessage(DirectMessageBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder(); // SQL文の格納用
		/** ログインユーザーの会員番号 */
		// DirectMessageBeanクラス内の会員番号を参照する
		String userNo = bean.getUserNo();

		// DirectMessageBeanクラス内の送信対象者番号を参照する
		/** 相手ユーザーの会員番号 */
		String toSendUserNo = bean.getToSendUserNo();

		/** 会話内容（メッセージ）を格納する */
		ArrayList<String> message = new ArrayList<String>();

		/** メッセージが自分の物か他人の物か判断する番号
		 * （自分="0"、他人="1"が代入される）を格納する
		 */
		ArrayList<String> judge = new ArrayList<String>();

		/** SQL文実行結果を格納する */
		ResultSet rs = null;

		// データベースに接続する準備
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

		// 接続作成
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			//------------------------------------------------------------------------------
			// 今日はここまで
			//------------------------------------------------------------------------------

			// SQL作成
			sb.append("SELECT ");
			sb.append(" MESSAGE, ");
			sb.append(" SEND_USER_NO ");
			sb.append("FROM ");
			sb.append(" T_MESSAGE_INFO ");
			sb.append("WHERE ");
			sb.append(" SEND_USER_NO IN (" + userNo + "," + toSendUserNo + ") ");
			sb.append(" AND TO_SEND_USER_NO IN (" + userNo + "," + toSendUserNo + ") ");
			sb.append(" AND DELETE_FLAG = 0 ");
			sb.append("ORDER BY REGIST_DATE ");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			rs = stmt.executeQuery(sb.toString()); // 実行し、その結果を格納

			while (rs.next()) {
				message.add(rs.getString("MESSAGE"));
				if (rs.getString("SEND_USER_NO") == userNo) {
					judge.add(rs.getString("0"));
				} else {
					judge.add(rs.getString("0"));
				}
			}

			/**
			// SQL実行結果に1行目があるかどうか(DBに該当データがあるかどうか)
			if (!rs.next()) { // 無かった場合
				bean.setErrorMessage("パスワードが一致しませんでした。");
			} else { // あった場合
				// beanに、DB会員マスタの各値を代入
				bean.setUserNo(rs.getString("user_no"));
				bean.setUserName(rs.getString("user_name"));
				bean.setErrorMessage("");
			}
			*/

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
