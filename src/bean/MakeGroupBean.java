package bean;

public class MakeGroupBean {

	/** 全ユーザの会員番号*/
	private String allUserNo;

	/** 全ユーザの会員名*/
	private String allUserName;

	/** エラーメッセージ*/
	private String errorMessage;

	public String getAllUserNo() {
		return allUserNo;
	}

	public void setAllUserNo(String allUserNo) {
		this.allUserNo = allUserNo;
	}

	public String getAllUserName() {
		return allUserName;
	}

	public void setAllUserName(String allUserName) {
		this.allUserName = allUserName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
