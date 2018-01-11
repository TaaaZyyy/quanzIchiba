package crud;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ItemDAO;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/Delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int itemNo = Integer.parseInt(request.getParameter("ITEM_NO"));
		HttpSession session = request.getSession();

		String deleteParameter = (String)session.getAttribute("deletePara");
		if (deleteParameter==null || !deleteParameter.equals("1")) {
			System.out.println("エラー判定。リダイレクトします。");
			response.sendRedirect("delete_complete.jsp");
			return;
		}

		try {
			int deleteResult = ItemDAO.getInstance().delete(itemNo);
			System.out.println("デリート件数：" + deleteResult);
			if (deleteResult == 1) {
				System.out.println("一件のデリートが実行されました。");
				session.setAttribute("deleteResult", 1);
			} else {
				System.out.println("インサートに失敗しました。");
				session.setAttribute("deleteResult", 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.removeAttribute("itemReference");
		session.removeAttribute("deletePara");
		response.sendRedirect("delete_complete.jsp");
	}
}
