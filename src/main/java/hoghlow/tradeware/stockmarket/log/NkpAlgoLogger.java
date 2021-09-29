package hoghlow.tradeware.stockmarket.log;

public class NkpAlgoLogger {
	
	private static String CLASS_APPEND = ">>>";
	private static String METHOD_APPEND = ">>";
	//private static String MESSAGE_APPEND = ">";
	private static String INFO = "INFO::";
	private static String ERROR = "ERROR::";
	
	private static StringBuffer messageBuffer =  new StringBuffer();

	public static void logInfo(Class<?> clazz, String method, String message) {
		if (messageBuffer.length() > 0) {
			messageBuffer.delete(0, messageBuffer.length());
		}
		messageBuffer.append(INFO).append(clazz).append(CLASS_APPEND).append(method).append(METHOD_APPEND)
				.append(message);
		System.out.println(messageBuffer.toString());
	}
	
	public static void logError(Class<?> clazz, String method, String message) {
		if (messageBuffer.length() > 0) {
			messageBuffer.delete(0, messageBuffer.length());
		}
		messageBuffer.append(ERROR).append(clazz).append(CLASS_APPEND).append(method).append(METHOD_APPEND)
				.append(message);
		System.out.println(messageBuffer.toString());
	}
	
	
	public static void printWithNoNewLine(String message) {
		System.out.print(message);
	}
	
	public static void printWithNewLine(String message) {
		System.out.println(message);
	}

}
