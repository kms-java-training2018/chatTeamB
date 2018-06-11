package bean;

public class DirectMessageBean {
	//------------------------------------------------------------------------------
	// ここからフィールド
	//------------------------------------------------------------------------------

	/** 会話内容（メッセージ） */
	private String  message;

	/** メッセージが自分の物か他人の物か判断する番号
	 * （自分="0"、他人="1"が代入される）
	 */
	private String judge;

	/** リスト格納用会話番号 */
	private String messageNo;

	/** 送信対象者番号 */
	private String  toSendUserNo = new String();

	/** 会員番号 */
	private String userNo;

	/** ログインユーザ表示名 */
	private String userName;

	/** 相手ユーザ表示名 */
	private String otherName;

	//------------------------------------------------------------------------------
	// ここからメソッド
	//------------------------------------------------------------------------------

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

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}


}
