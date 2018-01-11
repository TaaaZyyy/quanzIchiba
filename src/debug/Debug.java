package debug;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

public class Debug {

	public Debug() {
	}

	public static Debug getInstance() {
		return new Debug();
	}

	public void printRequestAttributeList(ServletRequest request) {
		System.out.println("#####REQUEST#####");
		Enumeration<String> names = request.getAttributeNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			Object value = request.getAttribute(key);
			System.out.println(key + "=" + value);
		}
	}

}
