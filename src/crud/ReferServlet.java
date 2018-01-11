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
import dao.ItemDAO;

/**
 * Servlet implementation class Refer
 */
@WebServlet("/Refer")
public class ReferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReferServlet() {
        super();
    }

	/**
	 * 詳細画面で表示するItemオブジェクトを用意します。
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			Item item = ItemDAO.getInstance().referTo(request.getParameter("referTo"));
			session.setAttribute("itemReference", item);
			request.getServletContext().getRequestDispatcher("/reference.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
