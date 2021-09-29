package org.tradeware.stockmarket.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.tradeware.stockmarket.bean.OptionChainDataBean;
import org.tradeware.stockmarket.bean.TradewareAppInfoBean;

import com.tradeware.clouddatabase.bean.SymbolBean;

public class StockUtil {

	//Temporary code start
	public static String serverHostName = null;
	//Temporary code end
	//public static final String DEFAULT_USER_NIRANJAN = "ZI7952";
	public static String DEFAULT_USER = "AF7508";
	// TODO: Have l to move to db and cross check should be with data.
	private static Map<String, String> adminUsers = new HashMap<String, String>();
	private static TradewareAppInfoBean appBean =  new TradewareAppInfoBean(); 
	public static Map<String, String> getAdmins() {
		if (adminUsers.isEmpty()) {
			adminUsers.put("ZI7952", "yqkmtg4c7okf7beo");
			adminUsers.put("AF7508", "cwat7f1yga0ohmdf");

			adminUsers.put("yqkmtg4c7okf7beo", "nxviv0tetbwgmwaxd7g3zw7o74mg19jw");
			adminUsers.put("cwat7f1yga0ohmdf", "zrpvqskzr1v4xi72mwjdhtmjpyeltdhq");
			
			//appBean = new TradewareAppInfoBean();
		}
		return adminUsers;
	}

	public static TradewareAppInfoBean getAppBean() {
		return appBean;
	}

	public static Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect> kiteConnectObjectsForAdmins = new Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect>();
	public static Hashtable<String, com.zerodhatech.models.User> kiteConnectAdminUsers = new Hashtable<String, com.zerodhatech.models.User>();

	//private static final String URL1 = "https://nseindia.com/live_market/dynaContent/live_watch/get_quote/getFOHistoricalData.jsp?underlying=";
	
	private static final String URL1 = "https://www1.nseindia.com/live_market/dynaContent/live_watch/get_quote/getFOHistoricalData.jsp?underlying=";
	private static final String URL2 = "&instrument=FUTSTK&type=-&strike=-&fromDate=undefined&toDate=undefined&datePeriod=3months&expiry=";
	private static final String URL3 = "&instrument=FUTSTK&type=-&strike=-&fromDate=undefined&toDate=undefined&datePeriod=3months&expiry=";

	public static String get2WeeksHistoricalDataUrlAjax(String symbol, String currentMonthExpiry) {
		String historicalDataUrlAjax = URL1 + getSymbolForURL(symbol) + URL2 + currentMonthExpiry;
		if (null == historicalDataUrlAjaxSample) {
			historicalDataUrlAjaxSample = historicalDataUrlAjax;
			System.out.println(historicalDataUrlAjaxSample);
		}
		
		if("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol)) {
			historicalDataUrlAjax = historicalDataUrlAjax.replace("FUTSTK", "FUTIDX");
		}
		
		return historicalDataUrlAjax;
	}

	public static String get3MonthsHistoricalDataUrlAjax(String symbol, String currentMonthExpiry) {
		String intradayDataUrlAjax = URL1 + getSymbolForURL(symbol) + URL3 + currentMonthExpiry;
		if (null == intradayDataUrlAjaxSample) {
			intradayDataUrlAjaxSample = intradayDataUrlAjax;
			System.out.println(intradayDataUrlAjax);
		}
		if("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol)) {
			intradayDataUrlAjax = intradayDataUrlAjax.replace("FUTSTK", "FUTIDX");
		}
		return intradayDataUrlAjax;
	}

	static String historicalDataUrlAjaxSample, intradayDataUrlAjaxSample;// Temporary indicator

	public static String getSymbolForURL(String symbol) {
		symbol = symbol.replaceAll(" ", "%20");
		symbol = symbol.replaceAll("&", "%26");
		return symbol;
		// String[] check = {"L%26TFH", "BANKNIFTY", "M%26MFIN", "M%26M"};
	}

	private static Map<String, Integer> map = new HashMap<String, Integer>();
	public static Map<String, Integer> getSymbols() {
		if (!map.isEmpty()) {
			return map;
		}
		/**
		 * Hard code a map with symbol and lot size OR connect to NSEindia.in and fetch
		 * live future lot size values for each symbol and index. example :
		 * mapTicker.put("ACC", new Double(20));
		 */
		return map;
	}

	private static Map<String, Double> mapTicker = new HashMap<String, Double>();
	public static Map<String, Double> getSymbolTickerMap() {
		if (!mapTicker.isEmpty()) {
			return mapTicker;
		}
		/**
		 * Hard code a map with symbol and ticker OR connect to NSEindia.in and fetch
		 * live option ticker values for each symbol and index. example :
		 * mapTicker.put("ACC", new Double(20));
		 */

		return mapTicker;
	}
	
	
	//New Nse site
	private static Map<String, Integer> indexMap = new HashMap<String, Integer>();
	public static Map<String, Integer> getIndexSymbols() {
		if (!indexMap.isEmpty()) {
			return indexMap;
		}
		indexMap.put("NIFTY", new Integer(75));
		indexMap.put("BANKNIFTY", new Integer(25));
		return indexMap;
	}
	
	private static final String FROM_DATE = "FROM_DATE";
	private static final String TO_DATE = "TO_DATE";
	private static final String EXPIRY_DATE = "EXPIRY_DATE";
	
	private static final String LAST_5DAY_AVG_DATA_URL = 
			"https://www.nseindia.com/api/historical/fo/derivatives?&instrumentType=FUTSTK&from=FROM_DATE&to=TO_DATE&expiryDate=EXPIRY_DATE&symbol=";
	
	public static String getLast5DayAvgDataUrl(String symbol, String currentMonthExpiry, String fromDate,
			String toDate) {
		String historicalDataUrlAjax = LAST_5DAY_AVG_DATA_URL.replaceAll(FROM_DATE, fromDate)
				.replaceAll(TO_DATE, toDate).replaceAll(EXPIRY_DATE, currentMonthExpiry) + getSymbolForURL(symbol);
		if (null == historicalDataUrlAjaxSample) {
			historicalDataUrlAjaxSample = historicalDataUrlAjax;
			System.out.println(historicalDataUrlAjaxSample);
		}

		if ("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol)) {
			historicalDataUrlAjax = historicalDataUrlAjax.replace("FUTSTK", "FUTIDX");
		}
		return historicalDataUrlAjax;
	}
	
	
	//Playable Option chain picks;
	private static List<OptionChainDataBean> options = new ArrayList<OptionChainDataBean>();
	
	public static List<OptionChainDataBean> getPlayOptionsList() {
		if (options.isEmpty()) {

			OptionChainDataBean bean11 = new OptionChainDataBean("HINDUNILVR");
			bean11.setStrongOrder(15);
			bean11.setGoForTrade(Constants.BUY);
			bean11.setBestEntry(2178.15);
			bean11.setLtpOnDecision(2178.15);
			bean11.setPlayanleInd(true);
			options.add(bean11);

			OptionChainDataBean bean10 = new OptionChainDataBean("SBILIFE");
			bean10.setStrongOrder(14);
			bean10.setGoForTrade(Constants.BUY);
			bean10.setBestEntry(842.0);
			bean10.setLtpOnDecision(842.0);
			bean10.setPlayanleInd(true);
			options.add(bean10);

			OptionChainDataBean bean3 = new OptionChainDataBean("HDFCBANK");
			bean3.setStrongOrder(13);
			bean3.setGoForTrade(Constants.BUY);
			bean3.setBestEntry(1035.0);
			bean3.setLtpOnDecision(1035.0);
			bean3.setPlayanleInd(true);
			options.add(bean3);
			
			OptionChainDataBean bean19 = new OptionChainDataBean("SRTRANSFIN");
			bean19.setStrongOrder(19);
			bean19.setGoForTrade(Constants.BUY);
			bean19.setBestEntry(680d);
			bean19.setLtpOnDecision(685.9);
			bean19.setPlayanleInd(true);
			options.add(bean19);

			OptionChainDataBean bean20 = new OptionChainDataBean("BHARTIARTL");
			bean20.setStrongOrder(11);
			bean20.setGoForTrade(Constants.BUY);
			bean20.setBestEntry(515d);
			bean20.setLtpOnDecision(529.95);
			bean20.setPlayanleInd(true);
			options.add(bean20);

			OptionChainDataBean bean18 = new OptionChainDataBean("BALKRISIND");
			bean18.setStrongOrder(18);
			bean18.setGoForTrade(Constants.BUY);
			bean18.setBestEntry(130d);
			bean18.setLtpOnDecision(1309.9);
			bean18.setPlayanleInd(true);
			options.add(bean18);

			OptionChainDataBean bean21 = new OptionChainDataBean("PVR");
			bean21.setStrongOrder(21);
			bean21.setGoForTrade(Constants.SELL);
			bean21.setBestEntry(1258.0);
			bean21.setLtpOnDecision(1258.0);
			bean21.setPlayanleInd(true);
			options.add(bean21);

			OptionChainDataBean bean22 = new OptionChainDataBean("AUROPHARMA");
			bean22.setStrongOrder(17);
			bean22.setGoForTrade(Constants.SELL);
			bean22.setBestEntry(1258.0);
			bean22.setLtpOnDecision(1258.0);
			bean22.setPlayanleInd(true);
			options.add(bean22);

			OptionChainDataBean bean23 = new OptionChainDataBean("CHOLAFIN");
			bean23.setStrongOrder(27);
			bean23.setGoForTrade(Constants.SELL);
			bean23.setBestEntry(226.3);
			bean23.setLtpOnDecision(226.3);
			bean23.setPlayanleInd(true);
			options.add(bean23);

			OptionChainDataBean bean110 = new OptionChainDataBean("VOLTAS");
			bean110.setStrongOrder(-3);
			bean110.setGoForTrade(Constants.SELL);
			bean110.setBestEntry(6300d);
			bean110.setLtpOnDecision(618.05);
			bean110.setPlayanleInd(true);
			options.add(bean110);

			OptionChainDataBean bean7 = new OptionChainDataBean("ULTRACEMCO");
			bean7.setStrongOrder(7);
			bean7.setGoForTrade(Constants.BUY);
			bean7.setBestEntry(4000d);
			bean7.setLtpOnDecision(4004.95);
			bean7.setPlayanleInd(true);
			options.add(bean7);

			OptionChainDataBean bean101 = new OptionChainDataBean("SRF");
			bean101.setStrongOrder(-2);
			bean101.setGoForTrade(Constants.BUY);
			bean101.setBestEntry(4100.0);
			bean101.setLtpOnDecision(4200.0);
			bean101.setPlayanleInd(true);
			options.add(bean101);

			OptionChainDataBean bean5 = new OptionChainDataBean("JUBLFOOD");
			bean5.setStrongOrder(5);
			bean5.setGoForTrade(Constants.SELL);
			bean5.setBestEntry(1950d);
			bean5.setLtpOnDecision(1896.2);
			bean5.setPlayanleInd(true);
			options.add(bean5);

			OptionChainDataBean bean120 = new OptionChainDataBean("TITAN");
			bean120.setStrongOrder(-1);
			bean120.setGoForTrade(Constants.SELL);
			bean120.setBestEntry(1150.75);
			bean120.setLtpOnDecision(1097.75);
			bean120.setPlayanleInd(true);
			options.add(bean120);

			OptionChainDataBean bean6 = new OptionChainDataBean("TATACONSUM");
			bean6.setStrongOrder(6);
			bean6.setGoForTrade(Constants.SELL);
			bean6.setBestEntry(560d);
			bean6.setLtpOnDecision(541.0);
			bean6.setPlayanleInd(true);
			options.add(bean6);

			OptionChainDataBean bean121 = new OptionChainDataBean("INDIGO");
			bean121.setStrongOrder(-5);
			bean121.setGoForTrade(Constants.SELL);
			bean121.setBestEntry(1162.0);
			bean121.setLtpOnDecision(1162.0);
			bean121.setPlayanleInd(true);
			options.add(bean121);

			OptionChainDataBean bean122 = new OptionChainDataBean("LT");
			bean122.setStrongOrder(-5);
			bean122.setGoForTrade(Constants.SELL);
			bean122.setBestEntry(1100.0);
			bean122.setLtpOnDecision(991.4);
			bean122.setPlayanleInd(true);
			options.add(bean122);

			OptionChainDataBean bean9 = new OptionChainDataBean("HCLTECH");
			bean9.setStrongOrder(9);
			bean9.setGoForTrade(Constants.BUY);
			bean9.setBestEntry(1800d);
			bean9.setLtpOnDecision(1988.0);
			bean9.setPlayanleInd(true);
			options.add(bean9);

			OptionChainDataBean bean4 = new OptionChainDataBean("MUTHOOTFIN");
			bean4.setStrongOrder(16);
			bean4.setGoForTrade(Constants.BUY);
			bean4.setBestEntry(1100d);
			bean4.setLtpOnDecision(1216d);
			bean4.setPlayanleInd(true);
			options.add(bean4);
		}
		return options;
	}
}
