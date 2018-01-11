package debug;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class PrintAttributeListFilter
 */
public class PrintAttributeListFilter implements Filter {

    /**
     * Default constructor.
     */
    public PrintAttributeListFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		String thisURL = ((HttpServletRequest)request).getRequestURI();
		System.out.println("==============PrintAttributeListFillter.java===============");
		System.out.println("現在地：" + thisURL);

		System.out.println("#####REQUEST#####");
		Enumeration<String> names = request.getAttributeNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			Object value = request.getAttribute(key);
			System.out.println(key + "=" + value);
		}

		System.out.println("#####SESSION#####");
		HttpSession session = ((HttpServletRequest) request).getSession();
		Enumeration<String> namesS = session.getAttributeNames();
		while (namesS.hasMoreElements()) {
			String key = namesS.nextElement();
			Object value = session.getAttribute(key);
			System.out.println(key + "=" + value);
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
