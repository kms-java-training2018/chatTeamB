package bean;

public class MainBean {

	/** 他のユーザの会員番号*/
	private String otherNo;

	/** 他のユーザの会員名*/
	private String otherName;

	/** 会話内容 */
	private String message;

	/** ログインしたユーザの属しているグループ番号*/
	private String groupNo;

	/** ログインしたユーザの属しているグループ名*/
	private String groupName;

	/** ログインしたユーザの属しているグループの最新メッセージ*/
	private String groupMessage;

	public String getOtherNo() {
		return otherNo;
	}

	public void setOtherNo(String otherNo) {
		this.otherNo = otherNo;
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

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupMessage() {
		return groupMessage;
	}

	public void setGroupMessage(String groupMessage) {
		this.groupMessage = groupMessage;
	}

}
