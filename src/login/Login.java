package login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fw.Sanitizing;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * 入力されたIDとパスでログイン認証を行います。
	 * SQLインジェクション回避のためのエスケープは LoginDAO.java でおこないます。
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user =  request.getParameter("ADMIN_ID");
		String pass =  request.getParameter("PASSWORD");
		HttpSession session = request.getSession();
		String loginParam = (String) session.getAttribute("loginParam");
		String redirectTo = null;
		try {
			if (LoginDAO.getInstance().isAdminUsr(user, pass)) {
				loginParam = "ok";
				session.setAttribute("user",Sanitizing.getInstance().againstXSS(user));
				redirectTo = "./search.jsp";
			} else {
				loginParam = "ng";
				redirectTo = "./index.jsp";
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		session.setAttribute("loginParam", loginParam);
//		System.out.println("ログインパラメーター（Servlet）：" + (String) session.getAttribute("loginParam"));
		response.sendRedirect(redirectTo);
	}
}
