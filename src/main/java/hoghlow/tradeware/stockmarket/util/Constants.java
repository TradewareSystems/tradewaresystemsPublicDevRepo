package hoghlow.tradeware.stockmarket.util;

public interface Constants {

	String TIME_ZONE = "Asia/Kolkata";
	String DATE_PATTERN = "ddMMMyyyy";
	String DATE_PATTERN_HIST_DATA = "yyyy-MM-dd HH:mm:ss";
	String TIME_PATTERN = "hh:mm:ss";
	String DATE_PATTERN_REPORT_DATE = "yyyy-MM-dd";

	String EXCHANGE_NSE = "NSE";
	String EXCHANGE_NFO = "NFO";
	String FUTURE_KEY_PREFIX_NSE = "NSE:";
	String FUTURE_KEY_PREFIX_NFO = "NFO:";
	String FUTURE_KEY_SUFFIX_NFO = "FUT";
	String FUTURE_SYMBOL = "(FUT)";

	String KITE_HIST_DATA_5_MINUTES_INTERVAL = "5minute";
	String KITE_HIST_DATA_10_MINUTES_INTERVAL = "10minute";
	String KITE_HIST_DATA_15_MINUTES_INTERVAL = "15minute";
	String KITE_HIST_DATA_DAY_INTERVAL = "day";

	String JSON_DATA_KEY_DATA = "data";
	String JSON_DATA_KEY_SYMBOL = "symbol";
	String JSON_DATA_KEY_CHANGE = "chn";
	String JSON_DATA_KEY_CHANGE_PERCENTAGE = "perChn";
	String JSON_DATA_KEY_PREV_CLOSE = "pCls";
	String JSON_DATA_KEY_QUANTITY = "trdQnty";
	String JSON_DATA_KEY_VALUE = "iVal";
	String JSON_DATA_KEY_MKT_CAP = "mktCap";
	String JSON_DATA_KEY_YEAR_HIGH = "yHigh";
	String JSON_DATA_KEY_YEAR_LOW = "yLow";
	String COMMA = ",";
	String EMPTY_STRING = "";
	String UNDER_SCRORE = "_";
	String SPACE = " ";
	String BREAK_LINE = "<BR>";
	double MEMORY_IN_MB = 0.000001 ;
	String COMMA_SPACE = ", ";
	int DECIMAL_PLACES = 2;
	String NA = "NA";
	String BUY = "BUY";
	String SELL = "SELL";
	String OHLC_BUY_FAIL = "BUY_FAIL";
	String OHLC_SELL_FAIL = "SELL_FAIL";
	String BUY_FAILED_ONDAY = "BUY_FAILED_ONDAY";
	String SELL_FAILED_ONDAY = "SELL_FAILED_ONDAY";
	String BUY_FAILED_5MIN_CANDLE = "BUY_FAILED_5MIN_CANDLE";
	String SELL_FAILED_5MIN_CANDLE = "SELL_FAILED_5MIN_CANDLE";
	String CANDLE_DATA_NOT_AVAILABLE = "CANDLE_DATA_NOT_AVAILABLE";
	
	String BUYER_SELLER_DIFF_FAILED = "BUYER-SELLER DIFF_FAILED";
	String BUY_FAILED_BUYER_SELLER_DIFF = "BUY_FAILED_BUYER-SELLER DIFF";
	String SELL_FAILED_BUYER_SELLER_DIFF = "SELL_FAILED_BUYER-SELLER DIFF";
	
	String LEVEL2_BUY = "LEVEL2_BUY";
	String LEVEL1_BUY = "LEVEL1_BUY";
	String LEVEL2_SELL = "LEVEL2_SELL";
	String LEVEL1_SELL = "LEVEL1_SELL";
	String LEVEL_NA = "LEVEL_UNCLEAR";//"LEVEL_NA";
	
	String GREEN_BUY = "GREE_BUY";
	String GREEN_SELL = "GREE_SELL";
	String RED_BUY = "RED_BUY";
	String RED_SELL = "RED_SELL";
	
	String PROFIT = "PROFIT";
	String LOSS = "LOSS";
	String BUY_EXIT_PROFIT = "BUY_EXIT_PROFIT";
	String SELL_EXIT_PROFIT = "SELL_EXIT_PROFIT";
	String BUY_EXIT_LOSS = "BUY_EXIT_LOSS";
	String SELL_EXIT_LOSS = "SELL_EXIT_LOSS";
	String EXIT_FORCE = "_F";//"_FROCE";
	String ACTIVATED = "Activated";
	
	String BUY_ACTIVATE = " @ BUY Activated @ ";
	String SELL_ACTIVATE = " @ SELL Activated @ ";
	String BUY_TARGET = " BUY TARGET DONE @ ";
	String SELL_TARGET = " SELL TARGET DONE @ ";
	String WITH_PROFIT = " WITH PROFIT @  ";
	String CLOSED_WITH_LOSE= " CLOSED WITH LOSE @ ";
	String WITH_LOSS = " WITH LOSS @ ";
	
	String NIFTY_50 = "NIFTY_50";
	String NIFTY_50_STRENGTH_TREND = ", NIFTY_STRENTH_TREND_";
	// UI messages
	String INVALID_USER = "Invalid user, Not an admin user.";
	String ALREADY_LOGGED_IN = "Nkp algo trader admin user alrady logged in. And setup the Nkp algo trader data for the day.";

	// Constants for controller
	String FORCE_START_APP_REQUEST = "Request recieved, App forcely starting...";
	String FORCE_START_APP_TIME_VALID = "App can be force startable in trading time only(9:15 AM t0 3:30 PM ist), Not now.  ";
	String FORCE_START_APP_IN_PROGRESS = "App is already force started, Please try to login to app after 5 minutes. ";
	String FORCE_START_APP_UP_RUNNING = "App is already up and running and scanneer threads are in monitoring mode. If needed down the app from PCF and start up again.  ";
	String FORCE_START_APP_ADMIN_LOGIN_VALID = "App adminstrator KITE CONNECT session is not yet established, App cannot be force startable, Please contact admin team.  ";
	String FORCE_START_APP_UNSUCCESS = "App force start process unsuccessful, Please contact admin team.  ;";
	String FORCE_START_APP_SUCCESSFUL = " App forcely started, Trading scanners are started and montoring... ";

	String PROGRESS_DOT = ". ";
	String SCANNER_PROGRESS_STRING = "   HighLowScannerThread running ......................................  ";
	
	String LAST_CANDLE_RED = "R";
	String LAST_CANDLE_GREEN = "G";
	//For Kite Connect
	String ORDER_VARIETY_BO = "bo";
	String ORDER_VARIETY_CO = "co";
	String ORDER_TYPE_LIMIT = "LIMIT";// "BO";//"MARKET";
	String ORDER_TYPE_MARKET = "MARKET";
	String PRODUCT_MIS = "MIS";
	String VALIDITY_DAY = "DAY";
	String ORDER_STATUS_OPEN = "OPEN";
	String ORDER_STATUS_COMPLETE = "COMPLETE";
	String ORDER_STATUS_REJECTed = "REJECTED";
	String ORDER_STATUS_CANCELLED = "CANCELLED";
	String ORDER_STATUS_CO_2ND_LEG_TRIGGER_PENDING = "TRIGGER PENDING";
	int TWO = 2;
	double TARGET_AMOUNT = 2000d;
	double TARGET_CO_STOP_LOSS = 3000d;//4500d;//5000d;
	double LIMIT_ORDER_PLACABLE_PRICE = 0.0005d;
	String ORDER_PLACEMENT_FAILED = "Order placement failled -- ";
	String ORDER_CANCELLED_SUCCESSFUL = "Order closed successfully -- ";
	String ORDER_CANCELLED_ERROR = "Order closing failed -- ";
	
	// FOR NIFTY 50 String
	String TRADABLE_STATE = "TRADABLE_TREND_";
	String VOLUME_TREND = "VOL_TREND_";
	String STRENTH_TREND = "CNDL_H-C/C-L_TREND_";
	String CANDLE_MOVEMENT = "CANDLE_MVMNT_";
	String LTP = "LTP_";
	String VWAP = "VWAP_";
	String DAY1_MOVEMENT = "DAY1_MOVE_";
	String DAY2_MOVEMENT = "DAY2_MOVE_";
	String CPR_5 = "CPR_5_";
	String CPR_15 = "CPR_15_";
	String TRADABLE_STRENTH = "TRADABLE_STRENTH_";
	String DAY1_STRENTH_FACTOR = "DAY1_STRN_FACT_";
	String DAY2_STRENTH_FACTOR = "DAY2_STRN_FACT_";
	String NR4_RULE = "NR4_RULE_";
	String SMALL_CANDLE = "SMALL_CANDLE_";
}
