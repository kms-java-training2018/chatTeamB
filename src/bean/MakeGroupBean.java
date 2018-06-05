package bean;

import java.util.ArrayList;

public class MakeGroupBean {

	/** 全ユーザの会員番号*/
	private ArrayList<String> allUserNo = new ArrayList<String>();

	/** 全ユーザの会員名*/
	private ArrayList<String> allUserName = new ArrayList<String>();

	/** エラーメッセージ */
	private String errorMessage;

	public ArrayList<String> getAllUserNo() {
		return allUserNo;
	}

	public void setAllUserNo(ArrayList<String> allUserNo) {
		this.allUserNo = allUserNo;
	}

	public ArrayList<String> getAllUserName() {
		return allUserName;
	}

	public void setAllUserName(ArrayList<String> allUserName) {
		this.allUserName = allUserName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
