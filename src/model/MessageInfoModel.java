package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * グループメッセージ画面ビジネスロジック
 */
public class MessageInfoModel {

//【メッセージ登録用】-------------------------------------------------------------------------------------------------
/**
 * 個人チャット、グループチャット共に、メッセージを送信したときに会話情報テーブルに情報を登録する。
 * 戻り値の型はbooleanで、処理が成功した場合はtrue、失敗の場合はfalseが返ってくる。
 * @param sendUserNo (String)
 * @param message (String)
 * @param toSendAddress (String)
 * @param judgeAddress (int) 個人チャットは0、グループチャットは1を指定してください。
 * @return boolean
 */
	public boolean entryMessage(String sendUserNo, String message, String toSendAddress, int judgeAddress) {
		// 初期化
		StringBuilder sb = new StringBuilder();		// SQL文の格納用
		boolean result = true;					// 処理実行結果格納用(戻り値用)
		String toSendUserNo = null;	// 送信先がユーザーだった場合、値に会員番号が入る
		String toSendGroupNo = null;	// 送信先がグループだった場合、値にグループ番号が入る

		// 送信先がユーザーかグループか判別
		if (judgeAddress == 0) {		// 送信先がユーザーの場合
			toSendUserNo = toSendAddress;

		} else if (judgeAddress == 1) {	// 送信先がグループの場合
			toSendGroupNo = toSendAddress;
		}

		// 参照DBを指定
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
			// TODO 自動采番、文整える
			sb.append("INSERT INTO ");
			sb.append(" T_MESSAGE_INFO ");
				sb.append("(MESSAGE_NO,");		//1
				sb.append(" SEND_USER_NO,");	//2
				sb.append(" MESSAGE,");			//3
				sb.append(" TO_SEND_USER_NO,");	//4
				sb.append(" TO_SEND_GROUP_NO,");	//5
				sb.append(" REGIST_DATE)");		//6
			sb.append(" VALUES");
				sb.append("( message_sequence.nextval");		//1
				sb.append(" ' "+ sendUserNo +"'");		//2
				sb.append(" ' "+ message +"'");		//3
				sb.append(" ' "+ toSendUserNo +"'");		//4
				sb.append(" ' "+ toSendGroupNo +"'");		//5
				sb.append(" to_date(sysdate))");		//6

			// SQL実行
			Statement stmt = conn.createStatement();			// SQL文をデータベースに送るためのStatementオブジェクトを生成
			int rs = stmt.executeUpdate(sb.toString());			// 実行し、その結果を格納

			// 処理
			// SQL実行後に、更新されたレコードが無かった場合(削除処理が失敗)
			if (rs == 0) {
				// 実行結果をfalse(失敗)に設定
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



// 【メッセージ削除用】-------------------------------------------------------------------------------------------------
/**
 * メッセージ削除用のメソッド。
 * 会話番号を引数とし、会話情報テーブルの該当のレコードの削除フラグを1に更新する。
 * @param messageNo (String)
 *	String型の会話番号1つを引数とする。
 * @return boolean
 * 	削除処理が完了したらtrue、失敗したらfalseが返ってくる。
 */
	public boolean deleteMessage(String messageNo) {
		// 初期化
		StringBuilder sb = new StringBuilder();		// SQL文の格納用
		boolean result = true;					// 処理実行結果格納用(戻り値用)

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
			sb.append(" T_MESSAGE_INFO ");
			sb.append("SET ");
			sb.append(" DELETE_FLAG  = 1");
			sb.append("WHERE ");
			sb.append(" MESSAGE_NO = '" + messageNo + "' ");


			// SQL実行
			Statement stmt = conn.createStatement();		// SQL文をデータベースに送るためのStatementオブジェクトを生成
			int rs = stmt.executeUpdate(sb.toString());		// 実行し、その結果(更新された行数)を格納


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

}


//【グループ脱退用】-------------------------------------------------------------------------------------------------
/**
* @author mitsuno-shinki
* グループ脱退用のメソッド。
* グループ番号とユーザ番号を引数とし、グループ情報テーブルの該当のレコードの脱退者フラグを1に更新する。
*/