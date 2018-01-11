package login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fw.DBManager;
import fw.ResultSetBeanMapping;
import fw.Sanitizing;

public class LoginDAO {

	/**
	 *  ResultSetのレコードをAdminUserのインスタンスに変換するクラス
	 *  ここでしか利用されないので、
	 *  無名クラスとして、この場所でインスタンス化します。
	 */
	private ResultSetBeanMapping<AdminUser> allMapping = new ResultSetBeanMapping<AdminUser>() {
		@Override
		public AdminUser createFromResultSet(ResultSet rs) throws SQLException {
			AdminUser user = new AdminUser();
			user.setAdminId(rs.getString("ADMIN_ID"));
			user.setPassword(rs.getString("PASSWORD"));
			return user;
		}
	};

	/**
	 * このクラスのインスタンスを取得します。
	 */
	public static LoginDAO getInstance() {
		return new LoginDAO();
	}


	/**
	 * ユーザーが管理者ユーザーか判断します。
	 * @param userId
	 * @param password
	 * @return 登録されている管理者ユーザーに一致したらtrueを返します。
	 * @throws SQLException
	 */
	public boolean isAdminUsr(String userId, String password) throws SQLException {
		userId = Sanitizing.getInstance().againstSQLinjection(userId);
		password = Sanitizing.getInstance().againstSQLinjection(password);
		String sql = "SELECT * FROM ADMIN_USER "
				+ "WHERE ADMIN_ID = '" + userId + "' && PASSWORD = '" + password +"'";
		List<AdminUser> list = DBManager.simpleFind(sql, allMapping);
		if (list.size() == 0) {
			return false;
		} else {
		return true;
		}
	}


}
