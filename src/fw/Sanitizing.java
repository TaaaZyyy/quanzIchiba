package fw;

public class Sanitizing {

	public Sanitizing() {
	}

	public static Sanitizing getInstance() {
		return new Sanitizing();
	}

	public String againstXSS(String str) {
		str = str.replace("&", "&amp;");
		str = str.replace("<","&lt;");
		str = str.replace(">","&gt;");
		str = str.replace("\"", "&quot;");
		str = str.replace("'", "&apos;");
		return str;
	}

	public String againstSQLinjection(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace("'", "''");
//		str = str.replace("+", "a");
//		str = str.replace("-", "a");
//		str = str.replace("*", "a");
//		str = str.replace("/", "a");
//		str = str.replace("%", "a");	// ワイルドカードをエスケープ
//		str = str.replace("_", "a");
//		str = str.replace(";", "a");
//		str = str.replace("&", "a");
//		str = str.replace("|", "a");
//		str = str.replace("(", "a");
//		str = str.replace(")", "a");
//		str = str.replace("\\", "a");
		return str;
	}
}
