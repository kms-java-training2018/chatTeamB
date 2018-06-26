package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GroupInfoModel {
	//【グループ作成用】-------------------------------------------------------------------------------------------------
	/**
	* @author mitsuno-shinki
	* グループ作成用のメソッド。
	* グループ名とグループに入っているユーザ番号を引数とし、
	* グループマスタとグループ情報テーブルに追加する。
	*/

	public boolean groupCreate(String userNo, String groupName, ArrayList<String> allUserNo) {
		// 初期化
		StringBuilder sb = new StringBuilder(); // SQL文の格納用
		boolean result = true; // 処理実行結果格納用(戻り値用)
		String newGroupNo=""; //自動採番されたグループ番号格納用

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
			// 作成者番号をログインユーザの会話番号とし、
			// グループ名とともにグループマスタに登録
			sb.append("INSERT INTO ");
			sb.append("M_GROUP ");
			sb.append("(GROUP_NO, ");
			sb.append("GROUP_NAME, ");
			sb.append("REGIST_USER_NO, ");
			sb.append("REGIST_DATE) ");
			sb.append("VALUES ");
			sb.append("(GROUP_CREATE_SEQUENCE.NEXTVAL, ");
			sb.append("'" + groupName + "', ");
			sb.append("'" + userNo + "', ");
			sb.append("systimestamp)");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			int rs = stmt.executeUpdate(sb.toString()); // 実行し、その結果(更新された行数)を格納

			// 処理
			// SQL実行後に、更新されたレコードが無かった場合(削除処理が失敗)
			if (rs == 0) {
				// 実行結果をfalse(失敗)に設定
				result = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = false;

			//SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 接続作成
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);
			StringBuilder sb2 = new StringBuilder(); // SQL文の格納用

			// SQL作成
			// 今作ったグループ番号取得
			sb2.append("SELECT ");
			sb2.append("MAX(GROUP_NO) ");
			sb2.append("FROM ");
			sb2.append("M_GROUP");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			ResultSet rs = stmt.executeQuery(sb2.toString()); // 実行し、その結果(更新された行数)を格納
			// SQL実行結果に1行目があるかどうか(DBに該当データがあるかどうか)
			if (rs.next()) { // あった場合
				newGroupNo = rs.getString("MAX(GROUP_NO)");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = false;

			//SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// SQL作成
			// グループマスタの情報を基に
			// グループ情報テーブルに登録(自分以外のユーザ番号)

			for (String num : allUserNo) {
				StringBuilder sb3 = new StringBuilder(); // SQL文の格納用
				sb3.append("INSERT INTO ");
				sb3.append("T_GROUP_INFO ");
				sb3.append("(GROUP_NO, ");
				sb3.append("USER_NO, ");
				sb3.append("REGIST_DATE )");
				sb3.append("VALUES ");
				sb3.append("( '" + newGroupNo + "', '");
				sb3.append(num + "', ");
				sb3.append("systimestamp)");

				// SQL実行
				Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
				int rs = stmt.executeUpdate(sb3.toString()); // 実行し、その結果(更新された行数)を格納

				// 処理
				// SQL実行後に、更新されたレコードが無かった場合(削除処理が失敗)
				if (rs == 0) {
					// 実行結果をfalse(失敗)に設定
					result = false;
				}
			}
		}

		catch (

		SQLException e) {
			e.printStackTrace();
			result = false;

			//SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// メソッドの戻り値(実行結果)を返す
		return result;

	}

	//【グループ脱退用】-------------------------------------------------------------------------------------------------
	/**
	* @author mitsuno-shinki
	* グループ脱退用のメソッド。
	* グループ番号とユーザ番号を引数とし、
	* グループ情報テーブルの該当のレコードの脱退者フラグを1に更新する。
	*/

	public boolean groupLeave(String userNo, String groupNo) {
		// 初期化
		StringBuilder sb = new StringBuilder(); // SQL文の格納用
		boolean result = true; // 処理実行結果格納用(戻り値用)

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
			// 会話番号を条件に会話情報を論理削除する
			sb.append("UPDATE ");
			sb.append("T_GROUP_INFO ");
			sb.append("SET ");
			sb.append("OUT_FLAG  = 1 ");
			sb.append("WHERE ");
			sb.append("USER_NO = '" + userNo + "' ");
			sb.append("AND GROUP_NO = '" + groupNo + "'");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			int rs = stmt.executeUpdate(sb.toString()); // 実行し、その結果(更新された行数)を格納

			// 処理
			// SQL実行後に、更新されたレコードが無かった場合(削除処理が失敗)
			if (rs == 0) {
				// 実行結果をfalse(失敗)に設定
				result = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = false;

			//SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// メソッドの戻り値(実行結果)を返す
		return result;

	}

	//【グループメンバーかどうか判定用】------------------------------------------------------------------------------------
	/**
	 * ログインユーザーが、遷移しようとしているグループのメンバーかどうかを判定するメソッド。
	 * 戻り値はbooleanで、メンバーの一員だった場合true、違った場合はfalseが返ってくる。
	 * @param userNo (String) ログインユーザーのuserNo
	 * @param groupNo (String) メッセージ画面を開こうとしているgroupNo
	 * @return boolean
	 */
	public boolean judgeGroupMember(String userNo, String groupNo) {
		// 初期化
		StringBuilder sb = new StringBuilder(); // SQL文の格納用
		boolean result = true; // 処理実行結果格納用(戻り値用)

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
			sb.append("USER_NO ");
			sb.append("FROM ");
			sb.append("t_group_info ");
			sb.append("WHERE ");
			sb.append("group_no = '" + groupNo + "'");
			sb.append("AND user_no = '" + userNo + "'");
			sb.append("AND out_flag = 0");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			ResultSet rs = stmt.executeQuery(sb.toString()); // 実行し、その結果を格納

			// 処理
			// SQL実行結果に1行目があるかどうか(DBに該当データがあるかどうか)
			if (!rs.next()) { // 無かった場合
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

		// メソッドの戻り値(実行結果)を返す
		return result;

	}



//【ログインユーザーがグループ作成者かどうか判定用】------------------------------------------------------------------------------------
/**
 * ログインユーザーがグループ作成者かどうか判定します。戻り値はboolean型で、判定結果が返ってきます。
 * @param userNo (String)
 * @param groupNo (String)
 * @return boolean (判定の結果)
 */
	public boolean judgeGroupCreator(String userNo, String groupNo) {
		// 初期化
		StringBuilder sb = new StringBuilder(); // SQL文の格納用
		boolean result = true; // 処理実行結果格納用(戻り値用)

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
			sb.append("SELECT");
			sb.append(" GROUP_NO ");
			sb.append("FROM");
			sb.append(" M_GROUP ");
			sb.append("WHERE");
			sb.append(" GROUP_NO = '" + groupNo + "'");
			sb.append(" AND REGIST_USER_NO = '" + userNo + "'");

			// SQL実行
			Statement stmt = conn.createStatement(); // SQL文をデータベースに送るためのStatementオブジェクトを生成
			ResultSet rs = stmt.executeQuery(sb.toString()); // 実行し、その結果を格納

			// 処理
			// SQL実行結果に1行目があるかどうか(DBに該当データがあるかどうか)
			if (!rs.next()) { // 無かった場合
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

		// メソッドの戻り値(実行結果)を返す
		return result;
	}
}
