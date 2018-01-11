package crud;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Item;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/Update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

	/**
	 * 確認画面からゲットメソッドで呼びだされます。DBに情報を反映させ、完了画面へリダイレクトします。
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("============UpdateServlet / GET=============");
		HttpSession session = request.getSession();

		String updateParameter = (String)session.getAttribute("updatePara");
		if (updateParameter==null || !updateParameter.equals("1")) {
			System.out.println("エラー判定。リダイレクトします。");
			response.sendRedirect("update_complete.jsp");
			return;
		}

		Item item = (Item)session.getAttribute("updateItem");
		try {
			int updateResult = dao.ItemDAO.getInstance().update(item);
			System.out.println("アップデート件数：" + updateResult);
			if (updateResult == 1) {
				System.out.println("一件のアップデートが実行されました。");
				session.setAttribute("updateResult", 1);
			} else {
				System.out.println("アップデートに失敗しました。");
				session.setAttribute("updateResult", 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("itemReference", item);
		session.removeAttribute("updateItem");
		session.removeAttribute("updatePara");
		response.sendRedirect("update_complete.jsp");
   }



	/**
	 * update.jsp にて入力された値からitemオブジェクトを作成し、確認画面へフォワードします。
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("============UpdateServlet/ POST=============");
		int itemNo = Integer.parseInt(request.getParameter("ITEM_NO"));
		String itemCategoryCode = request.getParameter("ITEM_CATEGORY_CODE");
		String itemName = request.getParameter("ITEM_NAME");
		String explanation = request.getParameter("EXPLANATION");
		int price = Integer.parseInt(request.getParameter("PRICE"));
		int recommendFLG = "1".equals(request.getParameter("RECOMMEND_FLG")) ? 1 : 0;

		Item item = new Item();
		item.setItemNo(itemNo);
		item.setItemCategoryCode(itemCategoryCode);
		item.setItemCategoryNameById(itemCategoryCode);
		item.setItemName(itemName);
		item.setExplanation(explanation);
		item.setPrice(price);
		item.setRecommendFLG(recommendFLG);

		HttpSession session = request.getSession();
		session.setAttribute("updateItem", item);

		String url = "";
		url = ValidateInput.getInstance().validateInputForUpdate(request, itemNo, itemCategoryCode, itemName, explanation)==0
				? "/update_confirm.jsp"
				:  "/update.jsp";

		request.getServletContext().getRequestDispatcher(url).forward(request, response);

	}
}
