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
		//		初期化
		////////////////////////////////////////////////////////////////////////
		// 戻り値用リスト
		ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();
		// SQL文の格納用
		StringBuilder sb = new StringBuilder();
		// SQL文実行結果格納用
		ResultSet rs = null;


		/////////////////////////////////////////////////////////////////////////////
		//		SQL文実行
		/////////////////////////////////////////////////////////////////////////////

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
			sb.append(" MESSAGE_NO, ");
			sb.append(" USER_NAME ");
			sb.append("FROM ");
			sb.append(" T_MESSAGE_INFO A ");
			sb.append(" FULL OUTER JOIN M_USER B ");
			sb.append(" ON A.SEND_USER_NO = B.USER_NO ");
			sb.append("WHERE ");
			sb.append(" TO_SEND_GROUP_NO =" + toSendGroupNo);
			sb.append(" AND DELETE_FLAG = 0 ");
			sb.append("ORDER BY A.REGIST_DATE ");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			rs = stmt.executeQuery(sb.toString()); // 実行し、その結果を格納


		/////////////////////////////////////////////////////////////////////////////
		//		処理：レコードごとに、DirectMessageBeanのフィールドに値をセット
		/////////////////////////////////////////////////////////////////////////////
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
				// 送信者の会員番号がログインユーザーの会員番号と一致した場合
				if (userNo.equals(rs.getString("SEND_USER_NO"))) {
					// listjudge→0
					directMessageBean.setJudge("0");
					// 送信者の表示名→userName
					directMessageBean.setUserName(rs.getString("USER_NAME"));

				// 送信者の会員番号がログインユーザーの会員番号と一致しなかった場合
				} else {
					// listjudge→1
					directMessageBean.setJudge("1");
					// 送信者の表示名→otherName
					directMessageBean.setOtherName(rs.getString("USER_NAME"));
				}
				// 1つの会話情報をリストに追加
				list.add(directMessageBean);

//				// デバッグ用：取得した会話内容をコンソールに表示
//				System.out.println("会話内容：" + directMessageBean.getMessage()
//						+ "：判別内容：" + directMessageBean.getJudge()
//						+ "：会員番号：" + directMessageBean.getUserNo()
//						+ "：表示名：" + directMessageBean.getUserName()
//						+ "：会話番号：" + directMessageBean.getMessageNo());

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
