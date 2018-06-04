package bean;

import java.util.ArrayList;

public class DirectMessageBean {
	//------------------------------------------------------------------------------
	// ここからフィールド
	//------------------------------------------------------------------------------
	/** 会話内容（メッセージ） */
	private ArrayList<String>  message = new ArrayList<String>();

	/** リスト格納用会話内容（メッセージ） */
	private String  listMessage;

	/** リスト格納用判断する番号 */
	private String listJudge;

	/** リスト格納用会話番号 */
	private String listMessageNo;

	/** 送信対象者番号 */
	private String  toSendUserNo = new String();

	/** 会員番号 */
	private String userNo;

	// 必要ないかも？
	/** ログインユーザ表示名 */
	private String userName;

	/** 相手ユーザ表示名 */
	private String otherName;


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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<String> getJudge() {
		return judge;
	}

	public void setJudge(ArrayList<String> judge) {
		this.judge = judge;
	}

	public String getListMessage() {
		return listMessage;
	}

	public void setListMessage(String listMessage) {
		this.listMessage = listMessage;
	}

	public String getListJudge() {
		return listJudge;
	}

	public void setListJudge(String listJudge) {
		this.listJudge = listJudge;
	}

	public String getListMessageNo() {
		return listMessageNo;
	}

	public void setListMessageNo(String listMessageNo) {
		this.listMessageNo = listMessageNo;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
}
