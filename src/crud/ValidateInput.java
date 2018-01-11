package crud;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import dao.ItemDAO;

public class ValidateInput {

	public ValidateInput() {
	}

	public static ValidateInput getInstance() {
		return new ValidateInput();
	}



	public int validateInputForCreate(HttpServletRequest request, String itemName, String explanation, String itemCategoryCode) {
		int numOfErr = 0;
//		商品名重複チェック
		System.out.println("入力チェック開始 create (from ValidateInput.java)");
		try {
			if (ItemDAO.getInstance().isInDB(itemName, itemCategoryCode)) {
				System.out.println("異常な入力値（商品名）：商品名重複 (from ValidateInput.java)");
				request.setAttribute("overrapErr", "yes");
				numOfErr++;
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		商品名・説明妥当性チェック
		numOfErr += validateInput(request,itemName,explanation);
		return numOfErr;
	}


	public int validateInputForUpdate(HttpServletRequest request, int itemNo, String itemCategoryCode, String itemName, String explanation) {
		int numOfErr = 0;
//		商品名重複チェック(自身の名前を検索条件から外すため、itemNoの値を要求する)
		System.out.println("入力チェック開始 update (from ValidateInput.java)");
		try {
			if (ItemDAO.getInstance().isInDB(itemName, itemCategoryCode, itemNo)) {
				System.out.println("異常な入力値（商品名）：商品名重複 (from ValidateInput.java)");
				request.setAttribute("overrapErr", "yes");
				numOfErr++;
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		商品名・説明妥当性チェック
		numOfErr += validateInput(request,itemName,explanation);
		return numOfErr;
	}





	// 以下、内部処理メソッド
	private int validateInput(HttpServletRequest request, String itemName, String explanation) {
		int numOfErr = 0;
//		====================<商品名妥当性チェック>=======================
		if (ValidateInput.getInstance().isOverCharacterLimitOfName(itemName)) {
			System.out.println("異常な入力値（商品名）：入力制限超過 (from ValidateInput.java)");
			System.out.println("itemName : " + itemName);
			System.out.println("itemName.length() : " + itemName.length());
			request.setAttribute("limitOverName", "yes");
			numOfErr++;
		}
		if (ValidateInput.getInstance().isOnlySpaceInName(itemName)) {
			System.out.println("異常な入力値（商品名）：空白のみ入力 (from ValidateInput.java)");
			request.setAttribute("onlySpaceName", "yes");
			numOfErr++;
		}
//		====================<説明妥当性チェック>=======================
		if (ValidateInput.getInstance().isOverCharacterLimitOfExp(explanation)) {
			System.out.println("異常な入力値（説明）：入力制限超過 (from ValidateInput.java)");
			request.setAttribute("limitOverExp", "yes");
			numOfErr++;
		}
		if (ValidateInput.getInstance().isOnlySpaceInExp(explanation)) {
			System.out.println("異常な入力値（説明）：空白のみ入力 (from ValidateInput.java)");
			request.setAttribute("onlySpaceExp", "yes");
			numOfErr++;
		}
		return numOfErr;
	}






	// <新規商品名・妥当性チェック>
		private boolean isOverCharacterLimitOfName(String itemName) {
			boolean overCharacterLimit = false;
			if (itemName.length()>100) {
				overCharacterLimit = true;
			}
			return overCharacterLimit;
		}
		private boolean isOnlySpaceInName(String itemName) {
			boolean isOnlySpace = false;
			String str = itemName;
			str = str.replace(" ", "");
			str = str.replace("　", "");
			str = str.replaceAll("[\n]", "");
			str = str.replaceAll("\t", "");
			if (str.length()==0) {
				isOnlySpace = true;
			}
			return isOnlySpace;
		}
	// <新規説明・妥当性チェック>
	private boolean isOverCharacterLimitOfExp(String explanation) {
		boolean overCharacterLimit = false;
		if (explanation.length()>500) {
			System.out.println("異常な入力値（説明）：入力制限超過 (from ValidateInput.java)");
			overCharacterLimit = true;
		}
		return overCharacterLimit;
	}
	private boolean isOnlySpaceInExp(String explanation) {
		boolean isOnlySpace = false;
		String str = explanation;
		str = str.replace(" ", "");
		str = str.replace("　", "");
		str = str.replaceAll("[\r\n]", "");
		str = str.replaceAll("[\n]", "");
		str = str.replaceAll("[\r]", "");
		str = str.replaceAll("\t", "");
		if (str.length()==0) {
			System.out.println("異常な入力値（説明）：空白のみ入力 (from ValidateInput.java)");
			isOnlySpace = true;
		}
	return isOnlySpace;
}
}
