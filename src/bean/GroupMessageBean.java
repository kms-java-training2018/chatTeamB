package bean;
/**
 * グループメッセージ画面内処理で使用するBean。
 * @author iyo-marina
 */
public class GroupMessageBean {

	// フィールドの宣言
	/** グループ番号 */
	private String groupNo;

	/** グループ名 */
	private String groupName;

	// 以下getter/setterメソッド
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
}
