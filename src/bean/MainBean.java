package bean;

import java.util.ArrayList;

public class MainBean {

	/** ログインしたユーザの会員番号*/
	private String userNo;

	/** ログインしたユーザの会員名*/
	private String userName;

	/** 他のユーザの会員番号*/
	private ArrayList<String> otherNo = new ArrayList<String>();

	/** 他のユーザの会員名*/
	private ArrayList<String> otherName = new ArrayList<String>();

	/** 会話内容 */
	private ArrayList<String> message = new ArrayList<String>();

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

	public ArrayList<String> getOtherNo() {
		return otherNo;
	}

	public void setOtherNo(ArrayList<String> otherNo) {
		this.otherNo = otherNo;
	}

	public ArrayList<String> getOtherName() {
		return otherName;
	}

	public void setOtherName(ArrayList<String> otherName) {
		this.otherName = otherName;
	}

	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}



}
