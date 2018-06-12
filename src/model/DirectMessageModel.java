package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.DirectMessageBean;

public class DirectMessageModel {
	// メッセージ閲覧メソッドの宣言
	public ArrayList<DirectMessageBean> lookMessage(DirectMessageBean DirectMessageBean) {
		System.out.println("DirectMessageModelLookにきました");

		////////////////////////////////////////////////////////////////////////
		//		初期化
		////////////////////////////////////////////////////////////////////////

		// jspに持っていくリスト（会話内容、judge、会話番号、表示名）初期化
		ArrayList<DirectMessageBean> directMessageList = new ArrayList<DirectMessageBean>();

		// SQL文の格納用変数初期化
		StringBuilder sb = new StringBuilder();

		// DirectMessageBeanクラス内の会員番号を参照する
		/** ログインユーザーの会員番号 */
		String userNo = DirectMessageBean.getUserNo();
		System.out.println("UserNo：" + userNo);

		// DirectMessageBeanクラス内の相手ユーザーの表示名を参照する
		/** ログインユーザーの表示名 */
		String userName = DirectMessageBean.getUserName();
		System.out.println("UserName：" + userName);

		// DirectMessageBeanクラス内の送信対象者番号を参照する
		/** 相手ユーザーの会員番号 */
		String toSendUserNo = DirectMessageBean.getToSendUserNo();
		System.out.println("ToSendUserNo：" + toSendUserNo);

		// DirectMessageBeanクラス内の相手ユーザーの表示名を参照する
		/** 相手ユーザーの表示名 */
		String otherName = DirectMessageBean.getOtherName();
		System.out.println("OtherName：" + otherName);

		/////////////////////////////////////////////////////////////////////////////
		//		SQL文実行
		/////////////////////////////////////////////////////////////////////////////

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

			// SQL作成
			sb.append("SELECT ");
			sb.append(" MESSAGE, ");
			sb.append(" SEND_USER_NO, ");
			sb.append(" MESSAGE_NO ");
			sb.append("FROM ");
			sb.append(" T_MESSAGE_INFO ");
			sb.append("WHERE ");
			sb.append(" SEND_USER_NO IN (" + userNo + "," + toSendUserNo + ") ");
			sb.append(" AND TO_SEND_USER_NO IN (" + userNo + "," + toSendUserNo + ") ");
			sb.append(" AND DELETE_FLAG = 0 ");
			sb.append("ORDER BY MESSAGE_NO ");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			rs = stmt.executeQuery(sb.toString()); // 実行し、その結果を格納

			//			if (!rs.next()) {	// 無かった場合
			//				bean.setErrorMessage("パスワードが一致しませんでした。");
			//			}

			while (rs.next()) {
				// クラスDirectMessageBean初期化
				DirectMessageBean directMessageBean = new DirectMessageBean();
				// メッセージをクラスDirectMessageBeanのmessageに入れる
				directMessageBean.setMessage(rs.getString("MESSAGE"));
				// 会話番号をクラスDirectMessageBeanのmessageNoに入れる
				directMessageBean.setMessageNo(rs.getString("MESSAGE_NO"));
				// 会員番号をクラスDirectMessageBeanのmessageNoに入れる
				directMessageBean.setUserNo(rs.getString("SEND_USER_NO"));
				// 送信者番号がログインユーザーの会員番号と一致した場合、listjudgeに0を代入
				if (userNo.equals(rs.getString("SEND_USER_NO"))) {
					directMessageBean.setJudge("0");
					directMessageBean.setUserName(userName);
					// 送信者番号がログインユーザーの会員番号と一致しなかった場合、listjudgeに0を代入
				} else {
					directMessageBean.setJudge("1");
					directMessageBean.setOtherName(otherName);
				}
				System.out.println("会話内容：" + directMessageBean.getMessage()
						+ "：判別内容：" + directMessageBean.getJudge()
						+ "：会員番号：" + directMessageBean.getUserNo()
						+ "：会話番号：" + directMessageBean.getMessageNo());
				directMessageList.add(directMessageBean);
			}

			/**
			SQLが実行されたかどうかのパラメーターチェック
			bean.setMessage(message);
			bean.setJudge(judge);
			 */
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

		return directMessageList;
	}
}
