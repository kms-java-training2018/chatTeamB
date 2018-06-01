package bean;

public class ShowProfileBean {
	/** 選択したユーザ名 */
	private String userName;

	/** そのユーザの自己紹介文 */
	private String myPageText;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMyPageText() {
		return myPageText;
	}

	public void setMyPageText(String myPageText) {
		this.myPageText = myPageText;
	}
}