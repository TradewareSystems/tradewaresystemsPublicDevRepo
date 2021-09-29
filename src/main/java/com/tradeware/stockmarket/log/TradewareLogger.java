package com.tradeware.stockmarket.log;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tradeware.stockmarket.bean.SystemErrorDataBean;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

public class TradewareLogger {

	public static Boolean isDebugLevelEnable = Boolean.TRUE;

	private static final Logger logger = LoggerFactory.getLogger(TradewareLogger.class);
	
	// TODO - Move to proper place
	public static String getExceptionStackTraceAsStrting(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		/*if (stringWriter.toString().length() > 1024) {
			return stringWriter.toString().substring(0, 1020);
		}*/
		if (stringWriter.toString().length() > 256) {
			return stringWriter.toString().substring(0, 255);
		}
		return stringWriter.toString();
	}

	public static void saveErrorLog(String className, String methodName, Throwable e, String errorType,
			String errorSevirity) {
		SystemErrorDataBean exception = new SystemErrorDataBean(className, methodName,
				getExceptionStackTraceAsStrting(e), errorType, errorSevirity, Boolean.TRUE);
		DatabaseHelper.getInstance().saveSystemErrorDataBean(exception);
		TradewareLogger.sysoutPrintlnForLocalTest(exception);
		e.printStackTrace();
	}

	public static void saveFatalErrorLog(String className, String methodName, Throwable e, String errorType) {
		SystemErrorDataBean exception = new SystemErrorDataBean(className, methodName,
				getExceptionStackTraceAsStrting(e), errorType, Constants.ERROR_SEVERIRITY_FATAL, Boolean.TRUE);
		DatabaseHelper.getInstance().saveSystemErrorDataBean(exception);
		TradewareLogger.sysoutPrintlnForLocalTest(exception);
		e.printStackTrace();
	}

	public static void saveInfoLog(String className, String methodName, String infoMessage) {
		SystemErrorDataBean exception = new SystemErrorDataBean(className, methodName, infoMessage,
				Constants.ERROR_TYPE_JUST_SYSOUT_INFO, Constants.ERROR_JUST_INFO, Boolean.FALSE);
		DatabaseHelper.getInstance().saveSystemErrorDataBean(exception);
		TradewareLogger.sysoutPrintlnForLocalTest(exception);
	}

	public static void sysoutPrintlnForLocalTest(String message) {
		 //System.out.println(message);
		//logger.info(message);
	}

	public static void sysoutPrintlnForLocalTest(SystemErrorDataBean exception) {
		sysoutPrintlnForLocalTest(
		 exception.getClassName() + " : " + exception.getMethodName() + " : " +
	 exception.getErrorMessage());
		logger.info(exception.getClassName() + " : " + exception.getMethodName() + " : " + exception.getErrorMessage());
	}
}
