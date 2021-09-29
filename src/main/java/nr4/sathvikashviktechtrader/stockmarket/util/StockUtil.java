package nr4.sathvikashviktechtrader.stockmarket.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class StockUtil {
	public static String serverHostName = null;
	/*private static NkpAppInfoBean appBean = new NkpAppInfoBean();

	public static NkpAppInfoBean getAppBean() {
		return appBean;
	}*/

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

	private static Map<String, Integer> map = new HashMap<String, Integer>();
	private static Map<String, Integer> indexMap = new HashMap<String, Integer>();

	public static Map<String, Integer> getIndexSymbols() {
		if (!indexMap.isEmpty()) {
			return indexMap;
		}
		indexMap.put("NIFTY 50", new Integer(75));
		indexMap.put("NIFTY BANK", new Integer(20));
		return indexMap;
	}

	public static Map<String, Integer> getSymbols() {
		if (!map.isEmpty()) {
			return map;
		}

		map.put("TATACONSUM", new Integer(2700));
		map.put("UJJIVAN", new Integer(1700));
		map.put("TATACHEM", new Integer(900));
		map.put("HINDUNILVR", new Integer(300));
		map.put("AUROPHARMA", new Integer(1000));
		map.put("PVR", new Integer(400));
		map.put("UPL", new Integer(900));
		map.put("INDUSINDBK", new Integer(400));
		map.put("CONCOR", new Integer(1563));
		map.put("SUNTV", new Integer(1200));
		map.put("AMARAJABAT", new Integer(800));
		map.put("LUPIN", new Integer(700));
		map.put("SUNPHARMA", new Integer(1250));
		map.put("DABUR", new Integer(1250));
		map.put("NIITTECH", new Integer(375));
		map.put("BPCL", new Integer(1800));
		map.put("ICICIPRULI", new Integer(1500));
		map.put("EXIDEIND", new Integer(2900));
		map.put("BAJAJFINSV", new Integer(125));//map.put("CESC", new Integer(800));
		map.put("INDIGO", new Integer(300));
		map.put("TITAN", new Integer(750));
		map.put("BAJAJ-AUTO", new Integer(250));
		map.put("CANBK", new Integer(2600));
		map.put("BAJFINANCE", new Integer(250));
		map.put("INFRATEL", new Integer(2000));
		map.put("ZEEL", new Integer(1700));
		map.put("RELIANCE", new Integer(500));
		map.put("ICICIBANK", new Integer(1375));
		map.put("DRREDDY", new Integer(250));
		map.put("HINDPETRO", new Integer(2100));
		map.put("CIPLA", new Integer(1150));
		map.put("DIVISLAB", new Integer(400));
		map.put("COALINDIA", new Integer(2700));
		map.put("CENTURYTEX", new Integer(600));
		map.put("UBL", new Integer(700));
		map.put("ACC", new Integer(400));
		map.put("GODREJCP", new Integer(800));
		map.put("MARICO", new Integer(1300));
		map.put("PIDILITIND", new Integer(500));
		map.put("BERGEPAINT", new Integer(1100));
		map.put("BALKRISIND", new Integer(800));
		map.put("ITC", new Integer(2400));
		map.put("TORNTPHARM", new Integer(500));
		map.put("SRTRANSFIN", new Integer(600));
		map.put("GODREJPROP", new Integer(650));
		map.put("IBULHSGFIN", new Integer(1200));
		map.put("BHARTIARTL", new Integer(1851));
		map.put("TATASTEEL", new Integer(1500));
		map.put("CADILAHC", new Integer(2200));
		map.put("LT", new Integer(375));
		map.put("GRASIM", new Integer(750));
		map.put("MINDTREE", new Integer(800));
		map.put("SBIN", new Integer(3000));
		map.put("MFSL", new Integer(1300));
		map.put("WIPRO", new Integer(3200));
		map.put("HDFC", new Integer(250));
		map.put("ADANIPORTS", new Integer(2500));
		map.put("INFY", new Integer(1200));
		map.put("TORNTPOWER", new Integer(3000));
		map.put("SIEMENS", new Integer(550));
		map.put("HCLTECH", new Integer(1400));
		map.put("CUMMINSIND", new Integer(900));
		map.put("MCDOWELL-N", new Integer(1250));
		map.put("BIOCON", new Integer(2300));
		map.put("JUSTDIAL", new Integer(1400));
		map.put("TECHM", new Integer(1200));
		map.put("SBILIFE", new Integer(750));
		map.put("DLF", new Integer(3300));
		map.put("TCS", new Integer(250));
		map.put("AMBUJACEM", new Integer(2500));
		map.put("SRF", new Integer(250));
		map.put("CHOLAFIN", new Integer(2500));
		map.put("APOLLOTYRE", new Integer(3000));
		map.put("COLPAL", new Integer(700));
		map.put("JUBLFOOD", new Integer(500));
		map.put("HDFCBANK", new Integer(500));
		map.put("AXISBANK", new Integer(1200));
		map.put("RAMCOCEM", new Integer(800));
		map.put("VOLTAS", new Integer(1000));
		map.put("MUTHOOTFIN", new Integer(750));
		map.put("M&MFIN", new Integer(1600));
		map.put("JSWSTEEL", new Integer(2300));
		map.put("ASIANPAINT", new Integer(300));
		map.put("BATAINDIA", new Integer(550));
		map.put("TVSMOTOR", new Integer(1350));
		map.put("GLENMARK", new Integer(1400));
		map.put("HAVELLS", new Integer(1000));
		map.put("KOTAKBANK", new Integer(400));
		map.put("PETRONET", new Integer(3000));
		map.put("BHARATFORG", new Integer(1300));
		map.put("LICHSGFIN", new Integer(1300));
		map.put("IGL", new Integer(1375));
		map.put("ESCORTS", new Integer(1100));
		map.put("MGL", new Integer(600));
		map.put("RBLBANK", new Integer(1500));
		map.put("M&M", new Integer(1000));
		map.put("HDFCLIFE", new Integer(900));
		map.put("PEL", new Integer(309));
		map.put("BANDHANBNK", new Integer(1200));
		map.put("APOLLOHOSP", new Integer(500));

//map.put("EICHERMOT", new Integer(30));
//map.put("PAGEIND", new Integer(25));
//map.put("MRF", new Integer(10));
//map.put("NAUKRI", new Integer(200));
//map.put("HEROMOTOCO", new Integer(200));
//map.put("BRITANNIA", new Integer(200));
//map.put("SHREECEM", new Integer(50));
//map.put("MARUTI", new Integer(100));
//map.put("NESTLEIND", new Integer(50));
//map.put("BAJAJFINSV", new Integer(125));
//map.put("ULTRACEMCO", new Integer(200));
//map.put("BOSCHLTD", new Integer(40));

//map.put("PNB", new Integer(8300));
//map.put("MANAPPURAM", new Integer(6000));
//map.put("PFC", new Integer(6200));
//map.put("TATAMOTORS", new Integer(4300));
//map.put("NTPC", new Integer(4800));
//map.put("ASHOKLEY", new Integer(8000));
//map.put("TATAPOWER", new Integer(9000));
//map.put("POWERGRID", new Integer(4000));
//map.put("JINDALSTEL", new Integer(5000));
//map.put("NCC", new Integer(9000));
//map.put("HINDALCO", new Integer(3500));
//map.put("L&TFH", new Integer(5600));
//map.put("NATIONALUM", new Integer(11700));
//map.put("EQUITAS", new Integer(4900));
//map.put("IDFCFIRSTB", new Integer(12000));
//map.put("NMDC", new Integer(6000));
//map.put("GAIL", new Integer(5334));
//map.put("VEDL", new Integer(3500));
//map.put("BEL", new Integer(6000));
//map.put("BHEL", new Integer(10400));
//map.put("MOTHERSUMI", new Integer(5000));
//map.put("ADANIPOWER", new Integer(10000));
//map.put("RECLTD", new Integer(6000));
//map.put("BANKBARODA", new Integer(5400));
//map.put("GMRINFRA", new Integer(45000));
//map.put("IDEA", new Integer(98000));
//map.put("SAIL", new Integer(15700));
//map.put("IOC", new Integer(4000));
//map.put("ADANIENT", new Integer(4000));
//map.put("ONGC", new Integer(4100));
//map.put("FEDERALBNK", new Integer(7000));
//map.put("YESBANK", new Integer(8800));

		return map;
	}

	private static Map<String, Integer> allSymbolMap = new HashMap<String, Integer>();
	public static Map<String, Integer> getAllSymbols() {
		if (!allSymbolMap.isEmpty()) {
			return allSymbolMap;
		}
		allSymbolMap.putAll(getSymbols());
		
		allSymbolMap.put("EICHERMOT", new Integer(30));
		allSymbolMap.put("PAGEIND", new Integer(25));
		allSymbolMap.put("MRF", new Integer(10));
		allSymbolMap.put("NAUKRI", new Integer(200));
		allSymbolMap.put("HEROMOTOCO", new Integer(200));
		allSymbolMap.put("BRITANNIA", new Integer(200));
		allSymbolMap.put("SHREECEM", new Integer(50));
		allSymbolMap.put("MARUTI", new Integer(100));
		allSymbolMap.put("NESTLEIND", new Integer(50));
		allSymbolMap.put("BAJAJFINSV", new Integer(125));
		allSymbolMap.put("ULTRACEMCO", new Integer(200));
		allSymbolMap.put("BOSCHLTD", new Integer(40));

		allSymbolMap.put("PNB", new Integer(8300));
		allSymbolMap.put("MANAPPURAM", new Integer(6000));
		allSymbolMap.put("PFC", new Integer(6200));
		allSymbolMap.put("TATAMOTORS", new Integer(4300));
		allSymbolMap.put("NTPC", new Integer(4800));
		allSymbolMap.put("ASHOKLEY", new Integer(8000));
		allSymbolMap.put("TATAPOWER", new Integer(9000));
		allSymbolMap.put("POWERGRID", new Integer(4000));
		allSymbolMap.put("JINDALSTEL", new Integer(5000));
		allSymbolMap.put("NCC", new Integer(9000));
		allSymbolMap.put("HINDALCO", new Integer(3500));
		allSymbolMap.put("L&TFH", new Integer(5600));
		allSymbolMap.put("NATIONALUM", new Integer(11700));
		allSymbolMap.put("EQUITAS", new Integer(4900));
		allSymbolMap.put("IDFCFIRSTB", new Integer(12000));
		allSymbolMap.put("NMDC", new Integer(6000));
		allSymbolMap.put("GAIL", new Integer(5334));
		allSymbolMap.put("VEDL", new Integer(3500));
		allSymbolMap.put("BEL", new Integer(6000));
		allSymbolMap.put("BHEL", new Integer(10400));
		allSymbolMap.put("MOTHERSUMI", new Integer(5000));
		allSymbolMap.put("ADANIPOWER", new Integer(10000));
		allSymbolMap.put("RECLTD", new Integer(6000));
		allSymbolMap.put("BANKBARODA", new Integer(5400));
		allSymbolMap.put("GMRINFRA", new Integer(45000));
		allSymbolMap.put("IDEA", new Integer(98000));
		allSymbolMap.put("SAIL", new Integer(15700));
		allSymbolMap.put("IOC", new Integer(4000));
		allSymbolMap.put("ADANIENT", new Integer(4000));
		allSymbolMap.put("ONGC", new Integer(4100));
		allSymbolMap.put("FEDERALBNK", new Integer(7000));
		allSymbolMap.put("YESBANK", new Integer(8800));

		return allSymbolMap;
	}
}
