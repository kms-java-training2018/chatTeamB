package bean;

public class MyPageBean {

	/** ログインしているユーザ番号 */
	private String userNo;

	/** ログインしているユーザ名 */
	private String userName;

	/** ユーザの自己紹介文 */
	private String myPageText;

	/** エラーメッセージ */
	private String errorMessage;

//	/** 編集したユーザ名 */
//	private String updateUserName;
//
//	/** 編集した自己紹介文 */
//	private String updateMyPageText;

	public String getMyPageText() {
		return myPageText;
	}

	public void setMyPageText(String myPageText) {
		this.myPageText = myPageText;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

//	public String getUpdateUserName() {
//		return updateUserName;
//	}
//
//	public void setUpdateUserName(String updateUserName) {
//		this.updateUserName = updateUserName;
//	}
//
//	public String getUpdateMyPageText() {
//		return updateMyPageText;
//	}
//
//	public void setUpdateMyPageText(String updateMyPageText) {
//		this.updateMyPageText = updateMyPageText;
//	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

}
