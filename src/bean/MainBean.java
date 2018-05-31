package bean;

/**
 * @author mitsuno-shinki
 * メインメニュー用のBean
 */

import java.util.ArrayList;

public class MainBean {

	/** 他のユーザの会員番号*/
	private ArrayList<String> otherNo = new ArrayList<String>();

	/** 他のユーザの会員名*/
	private ArrayList<String> otherName = new ArrayList<String>();

	/** 会話内容 */
	private ArrayList<String> message = new ArrayList<String>();

	/** ログインしたユーザの属しているグループ番号*/
	private ArrayList<String> groupNo;

	/** ログインしたユーザの属しているグループ名*/
	private ArrayList<String> groupName = new ArrayList<String>();

	/** ログインしたユーザの属しているグループの最新メッセージ*/
	private ArrayList<String> groupMessage;

	/** エラーメッセージ */
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public ArrayList<String> getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(ArrayList<String> groupNo) {
		this.groupNo = groupNo;
	}

	public ArrayList<String> getGroupMessage() {
		return groupMessage;
	}

	public void setGroupMessage(ArrayList<String> groupMessage) {
		this.groupMessage = groupMessage;
	}

	public ArrayList<String> getGroupName() {
		return groupName;
	}

	public void setGroupName(ArrayList<String> groupName) {
		this.groupName = groupName;
	}

}
