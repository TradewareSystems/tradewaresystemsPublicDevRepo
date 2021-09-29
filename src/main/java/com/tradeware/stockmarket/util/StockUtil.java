package com.tradeware.stockmarket.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.tradeware.stockmarket.bean.AppInfoDataBean;

public class StockUtil {
	public static String serverHostName = null;
	private static AppInfoDataBean appBean = new AppInfoDataBean();

	public static AppInfoDataBean getAppBean() {
		return appBean;
	}

	private static String threadScannerLastRunnngSatusTime = "Scanner thread not runnng...";

	public static String getThreadScannerLastRunnngSatusTime() {
		return threadScannerLastRunnngSatusTime;
	}

	public static void setThreadScannerLastRunnngSatusTime(String threadScannerLastRunnngSatusTime) {
		StockUtil.threadScannerLastRunnngSatusTime = threadScannerLastRunnngSatusTime;
	}

	public static String DEFAULT_USER = "AF7508";
	private static Map<String, String> adminUsers = new HashMap<String, String>();

	public static Map<String, String> getAdmins() {
		if (adminUsers.isEmpty()) {
			if (adminUsers.isEmpty()) {
				adminUsers.put("ZI7952", "yqkmtg4c7okf7beo");
				adminUsers.put("AF7508", "cwat7f1yga0ohmdf");
				adminUsers.put("ZL6602", "cv224d9it4hpvhy4");

				adminUsers.put("yqkmtg4c7okf7beo", "nxviv0tetbwgmwaxd7g3zw7o74mg19jw");
				adminUsers.put("cwat7f1yga0ohmdf", "zrpvqskzr1v4xi72mwjdhtmjpyeltdhq");
				adminUsers.put("cv224d9it4hpvhy4", "gga58pni8k89ter8279mhe8b7sa1ql70");
			}
		}
		return adminUsers;
	}

	private static Map<String, String> adminUsersHist = new HashMap<String, String>();

	public static Map<String, String> getAdminsHist() {
		if (adminUsersHist.isEmpty()) {
			if (adminUsersHist.isEmpty()) {
				adminUsersHist.put("AF7508", "q3taz66g36la2t47");
				adminUsersHist.put("q3taz66g36la2t47", "jj72295un2memww6vg51t67w8p4cdxhd");
			}
		}
		return adminUsersHist;
	}

	public static Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect> kiteConnectAdmins = new Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect>();
	public static Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect> kiteConnectAdminsHist = new Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect>();

	public static String getSymbolForURL(String symbol) {
		symbol = symbol.replaceAll(" ", "%20");
		symbol = symbol.replaceAll("&", "%26");
		return symbol;
		// String[] check = {"L%26TFH", "BANKNIFTY", "M%26MFIN", "M%26M"};
	}
	
	
	
	
	
	
	
	
	private static final String FROM_DATE = "FROM_DATE";
	private static final String TO_DATE = "TO_DATE";
	private static final String EXPIRY_DATE = "EXPIRY_DATE";
	
	private static final String DERIVATIVE_HIST_DATA_URL = 
			"https://www.nseindia.com/api/historical/fo/derivatives?&from=FROM_DATE&to=TO_DATE&expiryDate=EXPIRY_DATE&instrumentType=FUTSTK&symbol=";
	
	private static final String EQUITY_HIST_DATA_URL = 
			"https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=FROM_DATE&to=TO_DATE&symbol=";
	
	static String historicalDataUrlAjaxSample, intradayDataUrlAjaxSample;// Temporary indicator
	public static String getDerivativeHistDataUrl(String symbol, String currentMonthExpiry, String fromDate,
			String toDate) {
		String historicalDataUrlAjax = DERIVATIVE_HIST_DATA_URL.replaceAll(FROM_DATE, fromDate)
				.replaceAll(TO_DATE, toDate).replaceAll(EXPIRY_DATE, currentMonthExpiry) + getSymbolForURL(symbol);
		if (null == historicalDataUrlAjaxSample) {
			historicalDataUrlAjaxSample = historicalDataUrlAjax;
			System.out.println(historicalDataUrlAjaxSample);
		}

		if ("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol) || "FINNIFTY".equals(symbol)) {
			historicalDataUrlAjax = historicalDataUrlAjax.replace("FUTSTK", "FUTIDX");
		}
		return historicalDataUrlAjax;
	}
	
	public static String getEquityHistDataUrl(String symbol, String fromDate,
			String toDate) {
		String historicalDataUrlAjax = EQUITY_HIST_DATA_URL.replaceAll(FROM_DATE, fromDate)
				.replaceAll(TO_DATE, toDate) + getSymbolForURL(symbol);
		if (null == historicalDataUrlAjaxSample) {
			historicalDataUrlAjaxSample = historicalDataUrlAjax;
			System.out.println(historicalDataUrlAjaxSample);
		}

		if ("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol) || "FINNIFTY".equals(symbol)) {
			historicalDataUrlAjax = historicalDataUrlAjax.replace("FUTSTK", "FUTIDX");
		}
		return historicalDataUrlAjax;
	}
	
	
	
	
	
	
	
	
	private static Map<String, Integer> indexOIMap = new HashMap<String, Integer>();
	public static Map<String, Integer> getIndexOISymbols() {
		if (!indexOIMap.isEmpty()) {
			return indexOIMap;
		}
		indexOIMap.put("NIFTY", new Integer(75));
		indexOIMap.put("BANKNIFTY", new Integer(25));
		indexOIMap.put("FINNIFTY", new Integer(40));
		return indexOIMap;
	}

}
