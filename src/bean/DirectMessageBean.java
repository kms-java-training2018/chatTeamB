package bean;

import java.util.ArrayList;

public class DirectMessageBean {
	/** 会話内容（メッセージ） */
	private ArrayList<String>  message = new ArrayList<String>();

	/** 送信対象者番号 */
	private ArrayList<String>  toSendUserNo = new ArrayList<String>();

	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}

	public ArrayList<String> getToSendUserNo() {
		return toSendUserNo;
	}

	public void setToSendUserNo(ArrayList<String> toSendUserNo) {
		this.toSendUserNo = toSendUserNo;
	}
}
