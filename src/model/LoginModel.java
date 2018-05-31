package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.LoginBean;

/**
 * ログイン画面ビジネスロジック
 */
public class LoginModel {

	/**
	 * ログイン認証用メソッドの宣言
	 * @param Loginbean型	(ユーザーが入力したIDとパスワードが格納されてる）
	 * @return Loginbean型	(ログイン可能の場合、ID・パスワード・空のエラーメッセが返ってくる)
	 * 					(ログイン不可の場合、文字列セット済みエラーメッセが返ってくる)
	 */
	public LoginBean authentication(LoginBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();		// SQL文の格納用
		String userId = bean.getUserId();
		String password = bean.getPassword();

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
			// 会員IDとパスワードが一致した会員の、会員番号と表示名を取得
			sb.append("SELECT ");
			sb.append(" user_no ");
			sb.append(" ,user_name ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append("WHERE ");
			sb.append(" user_id = '" + userId + "' ");
			sb.append(" AND password = '" + password + "'");

			// SQL実行
			Statement stmt = conn.createStatement();			// SQL文をデータベースに送るためのStatementオブジェクトを生成
			ResultSet rs = stmt.executeQuery(sb.toString());	// 実行し、その結果を格納

			// SQL実行結果に1行目があるかどうか(DBに該当データがあるかどうか)
			if (!rs.next()) {	// 無かった場合
				bean.setErrorMessage("パスワードが一致しませんでした。");
			} else {			// あった場合
				// beanに、DB会員マスタの会員番号・表示名と、空文字を代入
				bean.setUserNo(rs.getString("user_no"));
				bean.setUserName(rs.getString("user_name"));
				bean.setErrorMessage("");						// ログインの可否を空のエラーメッセで判断する
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

		return bean;
	}
}
