package login;

public class AdminUser {

	public AdminUser() {
	}

	private String adminId;
	private String password;

	/**
	 * adminIdを取得します。
	 * @return adminId
	 */
	public String getAdminId() {
	    return adminId;
	}
	/**
	 * adminIdを設定します。
	 * @param adminId adminId
	 */
	public void setAdminId(String adminId) {
	    this.adminId = adminId;
	}
	/**
	 * passwordを取得します。
	 * @return password
	 */
	public String getPassword() {
	    return password;
	}
	/**
	 * passwordを設定します。
	 * @param password password
	 */
	public void setPassword(String password) {
	    this.password = password;
	}


}
