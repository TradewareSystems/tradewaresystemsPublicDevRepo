package hoghlow.tradeware.stockmarket.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.tradeware.stockmarket.bean.AppInfoDataBean;

public class StockUtil {
	public static String serverHostName = null;
	private static AppInfoDataBean appBean =  new AppInfoDataBean(); 
	public static AppInfoDataBean getAppBean() {
		return appBean;
	}
	public static String DEFAULT_USER = "AF7508";// "ZI7952";
	// TODO: Have to move to db and cross check should be with data.
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
	public static Hashtable<String, com.zerodhatech.models.User>  kiteConnectAdminUsers = new Hashtable<String, com.zerodhatech.models.User>();
	public static Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect>  kiteConnectAdmins = new Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect>();
	public static Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect> kiteConnectAdminsHist = new Hashtable<String, com.zerodhatech.kiteconnect.KiteConnect>();

	private static Map<String, Integer> map = new HashMap<String, Integer>();
	private static Map<String, Integer> indexMap = new HashMap<String, Integer>();
	public static Map<String, Integer> getIndexSymbols() {
		if (!indexMap.isEmpty()) {
			return indexMap;
		}
		indexMap.put("NIFTY 50", new Integer(75));
		//indexMap.put("NIFTY BANK", new Integer(75));
		return indexMap;
	}
	
	public static Map<String, Integer> getSymbols() {
		
		map.put("RELIANCE", new Integer(505));
		map.put("JUBLFOOD", new Integer(500));
		map.put("BHARTIARTL", new Integer(1851));
		map.put("BALKRISIND", new Integer(800));
		map.put("AXISBANK", new Integer(1200));
		//map.put("ASIANPAINT", new Integer(600));
		
		//map.put("BPCL", new Integer(1800));
		//map.put("GRASIM", new Integer(750));
		//.put("INDIGO", new Integer(300));
		
		//map.put("MUTHOOTFIN", new Integer(1500));
		//map.put("AUROPHARMA", new Integer(1000));//map.put("PEL", new Integer(302));
		//map.put("TITAN", new Integer(750));
		//map.put("ZEEL", new Integer(1300));
		
		//map.put("TECHM", new Integer(1200));
		//map.put("ICICIBANK", new Integer(1375));
		
		
		//map.put("GLENMARK", new Integer(1400));
		//map.put("RELIANCE", new Integer(500));
		//map.put("TATASTEEL", new Integer(1500));
		//map.put("BHARATFORG", new Integer(1300));
		//map.put("INFY", new Integer(1200));
		
		//map.put("ESCORTS", new Integer(1100));
		//map.put("M&M", new Integer(1000));
		//map.put("INFY", new Integer(1200));
		//map.put("BATAINDIA", new Integer(550));

		
		//map.put("ASIANPAINT", new Integer(600));
		//map.put("BPCL", new Integer(1800));
		//.put("INDIGO", new Integer(300));
		//map.put("ZEEL", new Integer(1300));
		//map.put("TATASTEEL", new Integer(1500));
		//map.put("BHARATFORG", new Integer(1300));
		//map.put("M&M", new Integer(1000));
		//map.put("INFY", new Integer(1200));
		//map.put("SBIN", new Integer(3000));
		//map.put("SIEMENS", new Integer(550));
		//map.put("SUNTV", new Integer(1000));
		//map.put("TATACHEM", new Integer(900));
		//map.put("UJJIVAN", new Integer(1600));
		//map.put("UPL", new Integer(900));
		//map.put("VOLTAS", new Integer(1000));
		//map.put("ZEEL", new Integer(1300));
		
		if (!map.isEmpty()) {
			return map;
		}
		map.put("ACC", new Integer(400));
		map.put("APOLLOHOSP", new Integer(500));
		map.put("APOLLOTYRE", new Integer(3000));
		map.put("ASIANPAINT", new Integer(600));
		map.put("AUROPHARMA", new Integer(1000));
		map.put("AXISBANK", new Integer(1200));
		map.put("BAJAJ-AUTO", new Integer(250));
		map.put("BALKRISIND", new Integer(800));
		map.put("BATAINDIA", new Integer(550));
		map.put("BERGEPAINT", new Integer(2200));
		map.put("BHARATFORG", new Integer(1200));
		map.put("BHARTIARTL", new Integer(1851));
		map.put("BIOCON", new Integer(1800));
		map.put("BPCL", new Integer(1800));
		map.put("BRITANNIA", new Integer(200));
		map.put("CENTURYTEX", new Integer(600));
		map.put("CESC", new Integer(800));
		map.put("CIPLA", new Integer(1000));
		map.put("COALINDIA", new Integer(2200));
		map.put("DLF", new Integer(2800));
		map.put("DRREDDY", new Integer(250));
		map.put("ESCORTS", new Integer(1100));
		map.put("GLENMARK", new Integer(1000));
		map.put("GODREJCP", new Integer(800));
		map.put("GRASIM", new Integer(750));
		map.put("HDFC", new Integer(250));
		map.put("HDFCBANK", new Integer(500));
		map.put("HINDUNILVR", new Integer(300));
		map.put("IBULHSGFIN", new Integer(800));
		map.put("ICICIBANK", new Integer(1375));
		map.put("INDIGO", new Integer(300));
		map.put("INDUSINDBK", new Integer(400));
		map.put("INFY", new Integer(1200));
		map.put("JUBLFOOD", new Integer(500));
		map.put("KOTAKBANK", new Integer(400));
		map.put("LUPIN", new Integer(700));
		map.put("M&M", new Integer(1000));
		map.put("MUTHOOTFIN", new Integer(1500));
		map.put("PEL", new Integer(309));
		map.put("PVR", new Integer(400));
		map.put("RBLBANK", new Integer(1200));
		map.put("RELIANCE", new Integer(500));
		map.put("SBIN", new Integer(3000));
		map.put("SIEMENS", new Integer(550));
		map.put("SUNTV", new Integer(1000));
		map.put("TATACHEM", new Integer(900));
		map.put("TATAGLOBAL", new Integer(2700));
		map.put("TATAMOTORS", new Integer(3000));
		map.put("TATASTEEL", new Integer(1061));
		map.put("TCS", new Integer(250));
		map.put("TECHM", new Integer(1200));
		map.put("TITAN", new Integer(750));
		map.put("UJJIVAN", new Integer(1600));
		map.put("UPL", new Integer(900));
		map.put("VOLTAS", new Integer(1000));
		map.put("ZEEL", new Integer(1300));
		return map;
	}
}
