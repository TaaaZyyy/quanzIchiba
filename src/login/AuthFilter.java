package login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	private String[] excludes;

	@Override
	public void init(FilterConfig config) throws ServletException {
		String exclude = config.getInitParameter("exclude");
		if (exclude == null) return;
		this.excludes = exclude.split(",");
	}

	private boolean isExcludePath(String thisURL, String thisContextPath) {
		String[] excludes = this.excludes;
		for(String path : excludes) {
			path = thisContextPath + path;
			// 除外対象パスの最後が「*」の場合、indexOfで含まれるか確認。
			if(path.indexOf("*") == path.length() - 1) {
				if(thisURL.indexOf(path.substring(0, path.length() - 2)) >= 0) {
					return true;
				}
			} else {
				// 上記以外は、完全一致。
				if (thisURL.equals(path)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String thisURL = ((HttpServletRequest)request).getRequestURI();
		String thisContextPath = ((HttpServletRequest)request).getContextPath();
//		System.out.println("============AuthFilter=============");
//		System.out.println("対象のパスは：" + thisURL);
//		System.out.println("認証除外対象 T/F：" + isExcludePath(thisURL, thisContextPath));

		if (isExcludePath(thisURL, thisContextPath)) {
		} else {
			HttpSession session = ((HttpServletRequest) request).getSession();
			String loginParam = (String) session.getAttribute("loginParam");
//			System.out.println("ログイン状態：" +loginParam);
			if ("ok".equals(loginParam)) {
//				System.out.println("認証通りました");
			} else {
//				System.out.println("認証通りませんでした");
				System.out.println("リダイレクトします");
				((HttpServletResponse) response).sendRedirect("index.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}

}
