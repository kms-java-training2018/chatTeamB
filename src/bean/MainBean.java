package bean;

public class MainBean {

	/** ログインしたユーザの会員番号*/
	private String userNo;

	/** ログインしたユーザの会員名*/
	private String userName;

	/** 他のユーザの会員番号*/
	private String otherNo[];

	/** 他のユーザの会員名*/
	private String otherName[];

	/** 会話内容 */
	private String message[];

	/** エラーメッセージ */
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public String[] getOtherNo() {
		return otherNo;
	}

	public void setOtherNo(String otherNo[]) {
		this.otherNo = otherNo;
	}

	public String[] getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName[]) {
		this.otherName = otherName;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String message[]) {
		this.message = message;
	}



}
