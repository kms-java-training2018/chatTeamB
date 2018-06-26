package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SvDbViewRangeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		try {

			//パラメータ取得
			String userNo = req.getParameter("userNo");
			String sendUserNo = req.getParameter("sendUserNo");
			String response = "";

			//処理（DB呼び出し等）
			StringBuilder sb = new StringBuilder();
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
			// 会話情報テーブルから二者間の最新メッセージを取得
			try {
				conn = DriverManager.getConnection(url, user, dbPassword);

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
				sb2.append("((send_user_no = " + userNo);
				sb2.append("OR send_user_no = " + sendUserNo + ")");
				sb2.append("AND (to_send_user_no = " + userNo);
				sb2.append("OR to_send_user_no = " + sendUserNo + ") ");
				sb2.append("AND (delete_flag = " + 0 + "))))");

				// SQL実行
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sb2.toString());

				if (rs.next()) {
					//今参照されている会員に最新のメッセージをセット
					response = rs.getString("message");
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

			//出力(レスポンスをmapに格納してJSON化)

			//JSONマップ
			Map<String, String> mapMsg = new HashMap<String, String>();

			//追加
			mapMsg.put("response", response);

			//マッパ(JSON <-> Map, List)
			ObjectMapper mapper = new ObjectMapper();

			//json文字列
			String jsonStr = mapper.writeValueAsString(mapMsg); //list, map

			//ヘッダ設定
			res.setContentType("application/json;charset=UTF-8"); //JSON形式, UTF-8

			//pwオブジェクト
			PrintWriter pw = res.getWriter();

			//出力
			pw.print(jsonStr);

			//クローズ
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
