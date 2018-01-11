package crud;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Item;
import dao.ItemDAO;
import fw.Sanitizing;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("============SerchServlet=============");
			// セッションの「categoryNameRecieved」「itemNameRecieved」は
			// 再度検索画面を表示するときに使用するので remove しない。
			String categoryNameRecieved = request.getParameter("ITEM_CATEGORY_NAME");
			String itemNameRecieved = request.getParameter("ITEM_NAME");
			String formattedItemNameRecieved = Sanitizing.getInstance().againstXSS(itemNameRecieved);
			HttpSession session = request.getSession();
			session.setAttribute("categoryNameRecieved", categoryNameRecieved);
			session.setAttribute("itemNameRecieved", formattedItemNameRecieved);
			System.out.println("itemNameRecieved : " + itemNameRecieved);
			System.out.println("categoryNameRecieved : " + categoryNameRecieved);
//			System.out.println(request.getAttribute("itemNameRecieved"));

			// 検索結果画面にALLと表示させるためにセッションに空欄の情報を入れる。
			boolean categoryIsBlank = false;
			boolean nameIsBlank = false;
			session.removeAttribute("categoryIsBlank");
			session.removeAttribute("nameIsBlank");
			if (categoryNameRecieved.equals("") || categoryNameRecieved == null) {
				categoryIsBlank = true;
				session.setAttribute("categoryNameRecieved", "");
				session.setAttribute("categoryIsBlank", 1);
			}
			if (itemNameRecieved.equals("") || itemNameRecieved == null) {
				nameIsBlank = true;
				session.setAttribute("itemNameRecieved", "");
				session.setAttribute("nameIsBlank", 1);
			}

			/**
			 * null 空欄 でなければ全件検索。それ以外はあいまい検索。
			 */
			List<Item>list;
			if (categoryIsBlank && nameIsBlank) {
				System.out.println("全件検索を行います。");
				list = ItemDAO.getInstance().findAll();
			} else {
				// アプリ側から分類名を操作できない現状、必要がないが、仕様書に記載されているために一応実装する。
				if (!("".equals(categoryNameRecieved))) {
					if (!(ItemDAO.getInstance().hasMatchCategory(categoryNameRecieved))) {
						System.out.println("分類名が不正です。");
						return;
					}
				}
				System.out.println("あいまい検索を行います。");
				// fuzzySearch() のなかで \ を \\ にリプレイスしているが、LIKE検索ではうまく行かなかった。
				// ここでさらに \ を \\ にリプレイスする必要がある。
				String itemNameForSearch = itemNameRecieved.replace("\\", "\\\\");
				list = ItemDAO.getInstance().fuzzySearch(categoryNameRecieved, itemNameForSearch);
			}
			session.setAttribute("list", list);
			session.setAttribute("listSize", list.size());
			request.getRequestDispatcher("/search.jsp").forward(request, response);

		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
