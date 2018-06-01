package bean;

import java.util.ArrayList;

public class SessionBean {
	/** 会員番号 */
	private String userNo;

	/** 表示名 */
	private String userName;

	/** 他のユーザの会員番号*/
	private ArrayList<String> otherNo = new ArrayList<String>();

	/** ログインしたユーザの属しているグループ番号*/
	private ArrayList<String> groupNo;

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

	public ArrayList<String> getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(ArrayList<String> groupNo) {
		this.groupNo = groupNo;
	}

}
