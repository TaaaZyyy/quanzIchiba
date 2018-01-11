package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fw.DBManager;
import fw.ResultSetBeanMapping;
import fw.Sanitizing;

public class ItemDAO {

	// allMappingの宣言と初期化。無名クラスを使用。
	private ResultSetBeanMapping<Item> allMapping = new ResultSetBeanMapping<Item>(){
		public Item createFromResultSet(ResultSet rs) throws SQLException {
			Item item = new Item();
			item.setItemNo(rs.getInt("ITEM_NO"));
			item.setItemCategoryCode(rs.getString("ITEM_CATEGORY_CODE"));
			item.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
			item.setItemName(rs.getString("ITEM_NAME"));
			item.setExplanation(rs.getString("EXPLANATION"));
			item.setPrice(rs.getInt("PRICE"));
			item.setRecommendFLG(rs.getInt("RECOMMEND_FLG"));
			item.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE_TIME"));
			return item;
		}
	};

	private ResultSetBeanMapping<Item> MappingForSearchList = new ResultSetBeanMapping<Item>(){
		public Item createFromResultSet(ResultSet rs) throws SQLException {
			Item item = new Item();
			item.setItemNo(rs.getInt("ITEM_NO"));
//			item.setItemCategoryCode(rs.getString("ITEM_CATEGORY_CODE"));
			item.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
			item.setItemName(rs.getString("ITEM_NAME"));
//			item.setExplanation(rs.getString("EXPLANATION"));
			item.setPrice(rs.getInt("PRICE"));
			item.setRecommendFLG(rs.getInt("RECOMMEND_FLG"));
//			item.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE_TIME"));
			return item;
		}
	};

	private ResultSetBeanMapping<Item> MappingForCategoryCheck = new ResultSetBeanMapping<Item>(){
		public Item createFromResultSet(ResultSet rs) throws SQLException {
			Item item = new Item();
//			item.setItemNo(rs.getInt("ITEM_NO"));
			item.setItemCategoryCode(rs.getString("ITEM_CATEGORY_CODE"));
//			item.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
//			item.setItemName(rs.getString("ITEM_NAME"));
//			item.setExplanation(rs.getString("EXPLANATION"));
//			item.setPrice(rs.getInt("PRICE"));
//			item.setRecommendFLG(rs.getInt("RECOMMEND_FLG"));
//			item.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE_TIME"));
			return item;
		}
	};
	private ResultSetBeanMapping<Item> MappingCategoryName = new ResultSetBeanMapping<Item>(){
		public Item createFromResultSet(ResultSet rs) throws SQLException {
			Item item = new Item();
//			item.setItemNo(rs.getInt("ITEM_NO"));
//			item.setItemCategoryCode(rs.getString("ITEM_CATEGORY_CODE"));
			item.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
//			item.setItemName(rs.getString("ITEM_NAME"));
//			item.setExplanation(rs.getString("EXPLANATION"));
//			item.setPrice(rs.getInt("PRICE"));
//			item.setRecommendFLG(rs.getInt("RECOMMEND_FLG"));
//			item.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE_TIME"));
			return item;
		}
	};

	private ResultSetBeanMapping<Item> mappingItemNo = new ResultSetBeanMapping<Item>(){
		public Item createFromResultSet(ResultSet rs) throws SQLException {
			Item item = new Item();
			item.setItemNo(rs.getInt("ITEM_NO"));
//			item.setItemCategoryCode(rs.getString("ITEM_CATEGORY_CODE"));
//			item.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
//			item.setItemName(rs.getString("ITEM_NAME"));
//			item.setExplanation(rs.getString("EXPLANATION"));
//			item.setPrice(rs.getInt("PRICE"));
//			item.setRecommendFLG(rs.getInt("RECOMMEND_FLG"));
//			item.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE_TIME"));
			return item;
		}
	};


// インスタンス化を用意にするため、インスタンスを呼び出すメソッドを作る。
	public static ItemDAO getInstance() {
		return new ItemDAO();
	}







	// ======================検索==========================
	/**
	 * 商品マスタ検索画面に出力するデータを取得するメソッド。全件検索で使用。
	 */
	public List<Item> findAll() throws SQLException {
		String sql = "SELECT ITEM.ITEM_NO, ITEM_CATEGORY.ITEM_CATEGORY_NAME, ITEM.ITEM_NAME, ITEM.PRICE, ITEM.RECOMMEND_FLG "
				+ "FROM ITEM_CATEGORY INNER JOIN ITEM "
				+ "ON ITEM_CATEGORY.ITEM_CATEGORY_CODE = ITEM.ITEM_CATEGORY_CODE "
				+ "ORDER BY ITEM.ITEM_CATEGORY_CODE ASC, ITEM.ITEM_NAME ASC "
				;
		return DBManager.simpleFind(sql, MappingForSearchList);
	}

	/**
	 * 商品マスタ検索画面に出力するデータを取得するメソッド。あいまい検索で使用。
	 * @throws SQLException
	 */
	public List<Item> fuzzySearch(String categoryNameReceived, String nameReceived) throws SQLException {
		categoryNameReceived = Sanitizing.getInstance().againstSQLinjection(categoryNameReceived);
		nameReceived = Sanitizing.getInstance().againstSQLinjection(nameReceived);
		String sqlAdditive = "";
		if (!("".equals(categoryNameReceived))) {
			sqlAdditive = "AND ITEM_CATEGORY.ITEM_CATEGORY_NAME = '" + categoryNameReceived + "' ";
		}

		String sql = "SELECT ITEM.ITEM_NO, ITEM_CATEGORY.ITEM_CATEGORY_NAME, ITEM.ITEM_NAME, ITEM.PRICE, ITEM.RECOMMEND_FLG "
				+ "FROM ITEM_CATEGORY INNER JOIN ITEM "
				+ "ON ITEM_CATEGORY.ITEM_CATEGORY_CODE = ITEM.ITEM_CATEGORY_CODE "
				+ "WHERE ITEM.ITEM_NAME LIKE '%" + nameReceived + "%' "
				+ sqlAdditive
				+ "ORDER BY ITEM.ITEM_CATEGORY_CODE ASC, ITEM.ITEM_NAME ASC "
				;
		return DBManager.simpleFind(sql, MappingForSearchList);
	}

/**
 * 選択したカテゴリがDB上に存在するかしらべるメソッド。
 */
	public boolean hasMatchCategory(String categoryName) throws SQLException {
		categoryName = Sanitizing.getInstance().againstSQLinjection(categoryName);
		String sql = "SELECT ITEM_CATEGORY_CODE "
					+ "FROM ITEM_CATEGORY "
					+ "WHERE ITEM_CATEGORY_NAME = "
					+ "'" + categoryName + "'"
					;
		List<Item> list = DBManager.simpleFind(sql, MappingForCategoryCheck);
		if (list.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * カテゴリIDからカテゴリ名を取得する。
	 * @param categoryId
	 * @return categoryName
	 * @throws SQLException
	 */
	public String findCategoryName(String categoryId) throws SQLException {
		String sql =
				"SELECT ITEM_CATEGORY_NAME FROM ITEM_CATEGORY "
				+ "WHERE ITEM_CATEGORY_CODE = '" + categoryId + "'";
		List<Item> item = DBManager.simpleFind(sql,  MappingCategoryName);
		return item.get(0).getItemCategoryName();
	}

	/**
	 * 詳細情報画面を表示するための要素をDBから取得する。
	 * @param itemNo
	 * @return item
	 * @throws SQLException
	 */
	public Item referTo(String itemNo) throws SQLException {
		itemNo = Sanitizing.getInstance().againstSQLinjection(itemNo);
		String sql = "SELECT * "
					+ "FROM ITEM_CATEGORY INNER JOIN ITEM "
					+ "ON ITEM_CATEGORY.ITEM_CATEGORY_CODE = ITEM.ITEM_CATEGORY_CODE "
					+ "WHERE ITEM.ITEM_NO = '" + itemNo + "' "
					;
		List<Item> list = DBManager.simpleFind(sql, allMapping);
		if (list.size()==0) return null;
		return list.get(0);
	}




	/**
	 * 引数のカテゴリ・商品名の商品がDBに存在しているか検索する。
	 * @param itemName
	 * @return DBに存在するならtrue。しないならfalse。
	 * @throws SQLException
	 */
	public boolean isInDB(String itemName, String itemCategoryCode) throws SQLException {
		itemName = Sanitizing.getInstance().againstSQLinjection(itemName);
		String sql = "SELECT ITEM_NO FROM ITEM "
				+ "WHERE ITEM_NAME = '" + itemName + "' "
				+ "AND ITEM_CATEGORY_CODE = '" + itemCategoryCode + "'"
				;
		List<Item> list = DBManager.simpleFind(sql, mappingItemNo);
		return list.isEmpty() ? false : true;
	}
	/**
	 * 引数のカテゴリ・商品名の商品がDBに存在しているか検索する。指定したitemNoは検索から除外する。
	 * @param itemName
	 * @return DBに存在するならtrue。しないならfalse。
	 * @throws SQLException
	 */
	public boolean isInDB(String itemName, String itemCategoryCode, int itemNo) throws SQLException {
		itemName = Sanitizing.getInstance().againstSQLinjection(itemName);
		String sql = "SELECT ITEM_NO FROM ITEM "
				+ "WHERE ITEM_NAME = '" + itemName + "' "
				+ "AND ITEM_CATEGORY_CODE = '" + itemCategoryCode + "' "
				+ "AND ITEM_NO <> '" + itemNo + "' "
				;
		List<Item> list = DBManager.simpleFind(sql, mappingItemNo);
		return list.isEmpty() ? false : true;
	}









	// ======================<作成>==========================
	public int insert(Item item) throws SQLException {
		String sql = "INSERT INTO item"
				+ "(ITEM_CATEGORY_CODE, ITEM_NAME, EXPLANATION, PRICE, RECOMMEND_FLG, LAST_UPDATE_DATE_TIME) "
				+ " VALUES ( "
				+ " '" + item.getItemCategoryCode() + "' "
				+ " , '" + Sanitizing.getInstance().againstSQLinjection(item.getItemName()) + "' "
				+ " , '" + Sanitizing.getInstance().againstSQLinjection(item.getExplanation()) + "' "
				+ " , " + item.getPrice() + " "
				+ " , '" + item.getRecommendFLG() + "' "
				+ " , NOW())"
				;
		return DBManager.simpleUpdate(sql);
	}




	// ======================<編集>==========================
	public int update(Item item) throws SQLException {
		String sql = "UPDATE ITEM SET "
				+ "ITEM_CATEGORY_CODE = '" + item.getItemCategoryCode() + "', "
				+ "ITEM_NAME = '" + Sanitizing.getInstance().againstSQLinjection(item.getItemName()) + "', "
				+ "EXPLANATION = '"+ Sanitizing.getInstance().againstSQLinjection(item.getExplanation())+ "', "
				+ "PRICE = '" + item.getPrice() + "', "
				+ "RECOMMEND_FLG = '" + item.getRecommendFLG() + "', "
				+ "LAST_UPDATE_DATE_TIME = NOW() "
				+ "WHERE ITEM_NO = " + item.getItemNo()
				;
		return DBManager.simpleUpdate(sql);

	}




	// ======================<削除>==========================
	public int delete(int itemNo) throws SQLException {
		String sql = "DELETE FROM ITEM WHERE ITEM_NO = " + itemNo;
		return DBManager.simpleUpdate(sql);
	}
}
