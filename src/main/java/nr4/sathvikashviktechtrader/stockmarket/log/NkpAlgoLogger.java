package nr4.sathvikashviktechtrader.stockmarket.log;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.tradeware.stockmarket.bean.SystemErrorDataBean;

import nr4.sathvikashviktechtrader.stockmarket.helper.DatabaseHelper;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;

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
		//System.out.println(messageBuffer.toString());
		saveInfoLog(Constants.CLASS_NKPALGOLOGGER, Constants.METHOD_LOGINFO+method, message);
	}
	
	public static void logError(Class<?> clazz, String method, String message) {
		if (messageBuffer.length() > 0) {
			messageBuffer.delete(0, messageBuffer.length());
		}
		messageBuffer.append(ERROR).append(clazz).append(CLASS_APPEND).append(method).append(METHOD_APPEND)
				.append(message);
		//System.out.println(messageBuffer.toString());
		saveInfoLog(Constants.CLASS_NKPALGOLOGGER, Constants.METHOD_LOGERROR+method, message);
	}
	
	
	public static void printWithNoNewLine(String message) {
		//System.out.print(message);
	}
	
	public static void printWithNewLine(String message) {
		//System.out.println(message);
		saveInfoLog(Constants.CLASS_NKPALGOLOGGER, Constants.METHOD_PRINTWITHNEWLINE, message);
	}

	// TODO - Move to proper place
	public static String getExceptionStackTraceAsStrting(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	public static void saveErrorLog(String className, String methodName, Throwable e, String errorType,
			String errorSevirity) {
		SystemErrorDataBean exception = new SystemErrorDataBean(className, methodName,
				getExceptionStackTraceAsStrting(e), errorType, errorSevirity, Boolean.TRUE);
		DatabaseHelper.getInstance().saveSystemErrorDataBean(exception);

	}

	public static void saveInfoLog(String className, String methodName, String infoMessage) {
		SystemErrorDataBean exception = new SystemErrorDataBean(className, methodName, infoMessage,
				Constants.ERROR_TYPE_SYSOUT_INFO, Constants.ERROR_JUST_INFO, Boolean.FALSE);
		DatabaseHelper.getInstance().saveSystemErrorDataBean(exception);
	}
}
