package bean;

import java.util.ArrayList;

public class DirectMessageBean {
	//------------------------------------------------------------------------------
	// ここからフィールド
	//------------------------------------------------------------------------------
	/** 会話内容（メッセージ） */
	private ArrayList<String>  message = new ArrayList<String>();


	/** 送信対象者番号 */
	private String  toSendUserNo = new String();


	/** 会員番号 */
	private String userNo;

	// 必要ないかも？
	/** 表示名 */
	private String userName;

	/** メッセージが自分の物か他人の物か判断する番号
	 * （自分="0"、他人="1"が代入される）
	 */
	private ArrayList<String> judge = new ArrayList<String>();

	//------------------------------------------------------------------------------
	// ここからメソッド
	//------------------------------------------------------------------------------


	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}


	public String getToSendUserNo() {
		return toSendUserNo;
	}

	public void setToSendUserNo(String toSendUserNo) {
		this.toSendUserNo = toSendUserNo;
	}


	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	//------------------------------------------------------------------------------
	// ここから必要ないかも？
	//------------------------------------------------------------------------------
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	//------------------------------------------------------------------------------
	// ここまで必要ないかも？
	//------------------------------------------------------------------------------


	public ArrayList<String> getJudge() {
		return judge;
	}

	public void setJudge(ArrayList<String> judge) {
		this.judge = judge;
	}
}
