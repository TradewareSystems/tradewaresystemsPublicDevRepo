package com.tradeware.stockmarket.tool;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;

public class TelegramMessageTool {
	private static TelegramMessageTool singletonTool;

	private TelegramMessageTool() {
	}

	public static TelegramMessageTool getInstance() {
		if (null == singletonTool) {
			singletonTool = new TelegramMessageTool();
		}
		return singletonTool;
	}
	
	private static final String BASE_URL_TELEGRAM = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
	private static final String API_TOKEN = "989152705:AAE1N_00d9AWLi922cAXFDesKZEvHCh5Fhk";
	private static final String  CHAT_ID = "@AutoMonitor";
	private static final String NEW_LINE_TELEGRAM = "%0A";
	private static final String NEW_LINE_HTML1 = "<BR>";
	private static final String NEW_LINE_HTML2 = "<br>";
	
	private static final String COMMA_SPACE = ", ";
	
	public void sendTelegramMessage(String message) {
		 String urlString = BASE_URL_TELEGRAM;
		if (null != message && message.length() > 0) {
			message = message.trim();
			message = message.replaceAll(NEW_LINE_HTML1, NEW_LINE_TELEGRAM);
			message = message.replaceAll(NEW_LINE_HTML2, NEW_LINE_TELEGRAM);
			
			message = message.replaceAll(COMMA_SPACE, NEW_LINE_TELEGRAM);

			urlString = String.format(urlString, API_TOKEN, CHAT_ID, message);

			try {
				URL url = new URL(urlString);
				URLConnection conn = url.openConnection();
				InputStream is = new BufferedInputStream(conn.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static StringBuffer lastRuntime = new StringBuffer("Theard not yet started");

	public void setLastRuntime(String lastRuntimeNew) {
		lastRuntime.delete(0, lastRuntime.length());
		lastRuntime.append(lastRuntimeNew);

	}
	
	//private static String urlStringTrade = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
	private static final String BASE_URL_TELEGRAM_TRADE = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
	private static final String API_TOKEN_TRADE = "893294997:AAGDgrrcYCOGdK2OL89lWyTipj_GQMpZcxc";
	private static final String  CHAT_ID_TRADE = "@AlgotraderAutoMonitor";
	
	private static final String NEW_LINE_TELEGRAM_TRADE = "%0A";
	private static final String NEW_LINE_HTML1_TRADE = "<BR>";
	private static final String NEW_LINE_HTML2_TRADE = "<br>";
	
	public void sendTelegramMessageTrade(String message) {
		String urlStringTrade = BASE_URL_TELEGRAM_TRADE;
		try {
		if (null != message && message.length() > 0) {
			message = message.trim();
			message = message.replaceAll(NEW_LINE_HTML1_TRADE, NEW_LINE_TELEGRAM_TRADE);
			message = message.replaceAll(NEW_LINE_HTML2_TRADE, NEW_LINE_TELEGRAM_TRADE);

			urlStringTrade = String.format(urlStringTrade, API_TOKEN_TRADE, CHAT_ID_TRADE, message);

			try {
				URL url = new URL(urlStringTrade);
				URLConnection conn = url.openConnection();
				InputStream is = new BufferedInputStream(conn.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		} catch(Exception e) {
			TradewareLogger.saveErrorLog(CLASS, METHOD, e, Constants.ERROR_TYPE_EXCEPTION, Constants.ERROR_SEVERIRITY_FATAL);
		}
	}
	
	static String CLASS = "TelegramMessageTool";
	static String METHOD = "sendTelegramMessageTrade";
	
}
