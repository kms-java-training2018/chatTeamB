package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.DirectMessageBean;

public class GroupMessageModelLook {
	/**
	 * グループ会話内容を取得するメソッド。戻り値は、要素がDirectMessageBean型のArrayListで、
	 * 1つの会話情報を格納したBeanを、リストで返します。(グループの会話内容全体)
	 * グループ脱退者はbean内userNameを「送信者不明」を入れます。
	 * @param bean (DirectMessageBean)
	 * @return ArrayList<DirectMessageBean>
	 */
	public ArrayList<DirectMessageBean> lookMessage(DirectMessageBean bean) {

		////////////////////////////////////////////////////////////////////////
		//		引数beanから情報を取得
		////////////////////////////////////////////////////////////////////////
		String userNo = bean.getUserNo();
		String toSendGroupNo = bean.getToSendUserNo();


		////////////////////////////////////////////////////////////////////////
		//		初期化・DB接続準備
		////////////////////////////////////////////////////////////////////////
		// グループ脱退者の会員番号格納用
		ArrayList<String> leaveUserNoList = new ArrayList<String>();
		// 戻り値用リスト
		ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();

		// SQL文(①グループ脱退者の会員番号取得)の格納用
		StringBuilder sb = new StringBuilder();
		// SQL文(②グループ全体の会話内容取得)の格納用
		StringBuilder sb2 = new StringBuilder();

		// SQL文実行結果格納用
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


		/////////////////////////////////////////////////////////////////////////////
		//		SQL文(①グループ脱退者の会員番号取得)実行
		/////////////////////////////////////////////////////////////////////////////

		// 接続作成
		try {
		conn = DriverManager.getConnection(url, user, dbPassword);

		// SQL作成
		sb.append("SELECT ");
		sb.append(" USER_NO ");
		sb.append("FROM ");
		sb.append(" T_GROUP_INFO ");
		sb.append("WHERE ");
		sb.append(" GROUP_NO =" + toSendGroupNo);
		sb.append(" AND OUT_FLAG = 1 ");

		// SQL実行
		Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
		rs = stmt.executeQuery(sb.toString()); // 実行し、その結果を格納


		/////////////////////////////////////////////////////////////////////////////
		//		処理：脱退者の会員番号をListにセット
		/////////////////////////////////////////////////////////////////////////////
		while (rs.next()) {
			leaveUserNoList.add(rs.getString("USER_NO"));
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



		/////////////////////////////////////////////////////////////////////////////
		//		SQL文(②グループ全体の会話内容取得)実行
		/////////////////////////////////////////////////////////////////////////////

		// 接続作成
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// SQL作成
			sb2.append("SELECT ");
			sb2.append(" A.MESSAGE, ");
			sb2.append(" A.SEND_USER_NO, ");
			sb2.append(" A.MESSAGE_NO, ");
			sb2.append(" B.USER_NAME ");
			sb2.append("FROM ");
			sb2.append(" T_MESSAGE_INFO A ");
			sb2.append(" FULL OUTER JOIN M_USER B ");
			sb2.append(" ON A.SEND_USER_NO = B.USER_NO ");
			sb2.append("WHERE ");
			sb2.append(" A.TO_SEND_GROUP_NO =" + toSendGroupNo);
			sb2.append(" AND A.DELETE_FLAG = 0 ");
			sb2.append("ORDER BY A.REGIST_DATE ");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			rs = stmt.executeQuery(sb2.toString()); // 実行し、その結果を格納


		/////////////////////////////////////////////////////////////////////////////
		//		処理：レコードごとに、DirectMessageBeanのフィールドに値をセット
		/////////////////////////////////////////////////////////////////////////////

			// グループに脱退者がいない場合
			if (leaveUserNoList.size() == 0) {
				while (rs.next()) {
					// DirectMessageBean初期化
					DirectMessageBean directMessageBean = new DirectMessageBean();
					// メッセージ→listMessage
					directMessageBean.setMessage(rs.getString("MESSAGE"));
					// 会話番号→listMessageNo
					directMessageBean.setMessageNo(rs.getString("MESSAGE_NO"));
					// 送信者の会員番号→userNo
					directMessageBean.setUserNo(rs.getString("SEND_USER_NO"));

					// 【送信者がログインユーザーかどうかを判定】
					// ----送信者の会員番号がログインユーザーの会員番号と一致した場合
					if (userNo.equals(rs.getString("SEND_USER_NO"))) {
						// listjudge→0(自分)
						directMessageBean.setJudge("0");
						// 送信者の表示名→userName
						directMessageBean.setUserName(rs.getString("USER_NAME"));

					// ----送信者の会員番号がログインユーザーの会員番号と一致しなかった場合
					} else {
						// listjudge→1(相手)
						directMessageBean.setJudge("1");
						// 送信者の表示名→otherName
						directMessageBean.setOtherName(rs.getString("USER_NAME"));
					}
					// 1つの会話情報をリストに追加
					list.add(directMessageBean);

				}
			// グループに脱退者がいた場合
			}else {
				while (rs.next()) {
					// DirectMessageBean初期化
					DirectMessageBean directMessageBean = new DirectMessageBean();
					// メッセージ→listMessage
					directMessageBean.setMessage(rs.getString("MESSAGE"));
					// 会話番号→listMessageNo
					directMessageBean.setMessageNo(rs.getString("MESSAGE_NO"));
					// 送信者の会員番号→userNo
					directMessageBean.setUserNo(rs.getString("SEND_USER_NO"));

					// 【送信者がログインユーザーかどうかを判定】
					// ----送信者の会員番号がログインユーザーの会員番号と一致した場合
					if (userNo.equals(rs.getString("SEND_USER_NO"))) {
						// listjudge→0(自分)
						directMessageBean.setJudge("0");
						// 送信者の表示名→userName
						directMessageBean.setUserName(rs.getString("USER_NAME"));

					// ----送信者の会員番号がログインユーザーの会員番号と一致しなかった場合
					} else {
						// 【送信者が脱退者がどうか判定】
						// ----脱退者リストに送信者が含まれていた場合
						if (leaveUserNoList.contains(rs.getString("SEND_USER_NO"))) {
							// listjudge→1(相手)
							directMessageBean.setJudge("1");
							// 送信者の表示名→otherName
							directMessageBean.setOtherName("送信者不明");
						// ----そうでない場合
						} else {
							// listjudge→1(相手)
							directMessageBean.setJudge("1");
							// 送信者の表示名→otherName
							directMessageBean.setOtherName(rs.getString("USER_NAME"));
						}
					}
					// 1つの会話情報をリストに追加
					list.add(directMessageBean);

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

		return list;
	}
}
