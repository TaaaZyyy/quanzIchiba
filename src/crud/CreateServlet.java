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
 * Servlet implementation class CreateServlet
 */
@WebServlet("/Create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }
/**
 * 確認画面からゲットメソッドで呼びだされます。DBに情報を反映させ、完了画面へリダイレクトします。
 */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("============CreateServlet / GET=============");
		HttpSession session = request.getSession();

		String createParameter = (String)session.getAttribute("createPara");
		if (createParameter==null || !createParameter.equals("1")) {
			System.out.println("エラー判定。リダイレクトします。");
			response.sendRedirect("create_complete.jsp");
			return;
		}

		Item item = (Item)session.getAttribute("createItem");
		try {
			int insertResult = dao.ItemDAO.getInstance().insert(item);
			System.out.println("インサート件数：" + insertResult);
			if (insertResult == 1) {
				System.out.println("一件のインサートが実行されました。");
				session.setAttribute("insertResult", 1);
			} else {
				System.out.println("インサートに失敗しました。");
				session.setAttribute("insertResult", 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.removeAttribute("createPara");
		session.removeAttribute("createItem");
		response.sendRedirect("create_complete.jsp");
   }

/**
 * create.jsp にて入力された値からitemオブジェクトを作成し、確認画面へフォワードします。
 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("============CreateServlet / POST=============");
		String itemCategoryCode = request.getParameter("ITEM_CATEGORY_CODE");
		String itemName = request.getParameter("ITEM_NAME");
		String explanation = request.getParameter("EXPLANATION");
		int price = Integer.parseInt(request.getParameter("PRICE"));
		int recommendFLG = "1".equals(request.getParameter("RECOMMEND_FLG")) ? 1 : 0;

		Item item = new Item();
		item.setItemCategoryCode(itemCategoryCode);
		item.setItemCategoryNameById(itemCategoryCode);
		item.setItemName(itemName);
		item.setExplanation(explanation);
		item.setPrice(price);
		item.setRecommendFLG(recommendFLG);

		HttpSession session = request.getSession();
		session.setAttribute("createItem", item);

		String url = "";
		url = ValidateInput.getInstance().validateInputForCreate(request, itemName, explanation, itemCategoryCode)==0
				? "/create_confirm.jsp"
				:  "/create.jsp";

		request.getServletContext().getRequestDispatcher(url).forward(request, response);

	}
}
