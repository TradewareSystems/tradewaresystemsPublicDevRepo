package org.tradeware.stockmarket.util;

public interface Constants {

	String JSON_DATA_KEY_DATA = "data";
	String JSON_DATA_KEY_SYMBOL = "symbol";
	String JSON_DATA_KEY_52WEEK_HIGH = "wkhi";
	String JSON_DATA_KEY_52WEEK_LOW = "wklo";
	String JSON_DATA_KEY_LTP = "ltP";
	String COMMA = ",";
	String IMAGE_ATTRIBUTE = "img";
	String IMAGE_SOURCE_ATTRIBUTE = "/common/images/btn_go.gif";
	String RESPONSE_DIV = "responseDiv";
	String JSON_DATA_KEY_MARKET_LOT = "marketLot";

	
	int INDEX_2 = 2;
	int INDEX_5 = 5;
	int INDEX_7 = 7;
	int INDEX_9 = 9;
	String EXCHANGE_NFO = "NFO";
	String EXCHANGE_FUTUE_NFO = "NFO:";
	String FUTURE_KET_INSTRUMENT = "FUT";
	// Time constants
	String TIME_PATTERN = "hh:mm:ss";
	String DATE_PATTERN = "ddMMMyyyy";
	String DATE_PATTERN_NSE = "dd-MMM-yyyy";
	String DATE_PATTERN_NEW_NSE_SITE = "dd-MM-yyyy";
	String TIMEZONE_IST = "Asia/Kolkata";
	String STOCK_INSTRUMENT_TYPE_FUTURE = "FUTSTK";

	String EMPTY_STRING = "";
	String HYPHEN = "-";
	String SPACE = "  ";
	String TAG_TABLE_ROW = "tr";

	int ZERO_INT = 0;
	int ONE_INT = 1;
	int TWO_INT = 2;
	int THREE_INT = 3;
	int FOUR_INT = 4;
	int FIVE_INT = 5;
	int SIX_INT = 6;
	int SEVEN_INT = 7;
	long ZERO_LONG = 0;
	double ZERO_DOUBLE = 0;
	double ONE_LAC = 100000;
	
	double ADDITONAL_CHECK = 1.5;
	double LIMIT_RANGE = 0.0025;  
	double BUY_SELL_DIFF_1000 = 1000;
	double TARGET_2000 = 2000;
	double MAX_LOSS_2000 = -2000;
	//TIME
	int FIVE = 5;
	int NINE = 9;
	int ELEVEN = 11;
	int FIFTEEN = 15;
	int TWENTY = 20;
	int TWENTY_NINE = 29;
	int THIRTY = 30;
	int FIFTY = 50;
	int FIFTY_NINE =  59;
	int TWO_HUNDRED = 200;
	
	String NA = "NA";
	String BUY = "BUY";
	String SELL  = "SELL";
	String OPTION_CALL = "CE";
	String OPTION_PUT = "PE";
	String FUTURE_BUY = "FUT BUY";
	String FUTURE_SELL = "FUT SELL";
	String OPTION_BUY = "OPTION BUY";
	String OPTION_SELL = "OPTION SELL";
	String MARKET = "MARKET";
	String LIMIT = "LIMIT";
	String NRML  = "NRML";
	String DAY = "DAY";
	String MIS = "MIS";
	String REGULAR = "regular";
	String EXIT  = "EXIT";
	String DONE = "DONE";
	String BREAK= "<BR>";
	String LINE_BREAK= "<BR> - @ ";
	String AT_RATE_OF = " @ ";
	String PROFIT = "PROFIT";
	String LOSS = "LOSS";
	String BUYER_AT = "Buyer at";
	String SELLER_AT = "Seller at";
	String BUY_SELL_DIFF = "BuyerSellerDiff";
	String SYMBOL_NOT_AVAILABLE = "SYMBOL NOT AVAILABLE";
	String BUYER_SELLER_RULE = "BUYER/SELLER DIFF RULE FAILED";//"BUYER/SELLER DIFF OR BUYER/SELLER PRICE RULE FAILED";
	String BUYER_SELLER_RULE_2 = "BUYER/SELLER DIFF RULE FAILED_@2";
	String FAIL = "_FAIL";
	String FRESH_CALL = "FRESH CALL";
	String CALL_ALREADY_PLACED_VALID = "Call already placed in this month. <BR> Still valid as huge buyers/sellers are present.";
	
	//UI messages
	String INVALID_USER = "Invalid user, Not an admin user.";
	String ALREADY_LOGGED_IN = "Tardeware admin user alrady logged in. And setup the Tardeware data for the day.";
	
	//
	String SELLER_PRICE = "Seller @";
	String BUYER_PRICE = "Buyer @ ";
	String LAST_PRICE = "LTP @ ";
	String CHNAGE_PERCENTAGE = "Change % : ";
	String MAX_LEVEL =  "MaxLevel : ";
	String MIN_LEVEL =  "MinLevel : ";
	String FINAL_PROFIT = "FINAL PROFIT @ ";
	String FINAL_LOSS = "FINAL LOSS AMT @";
	String EXIT_CALL = "<BR> EXIT";
	String PROFIT_AMT ="Profit Amt :";
	String LOSS_AMT ="Loss Amt :";
	int BUY_RULE_CHANGE_PERCENTAGE = 5;
	int SELL_RULE_CHANGE_PERCENTAGE = -5;
	double FUTURE_CHANGE_RATE = 0.0050;
	String BUY_RULE_FAILED = "LTP is more than " + LIMIT_RANGE + " % of Positional uptrend value.";
	String SELL_RULE_FAILED = "LTP is less than " + LIMIT_RANGE + " % of Positional downtrend value.";
	String BUY_RULE_CHANGE_FAILED= "Futire change is greater then "+BUY_RULE_CHANGE_PERCENTAGE+"%";
	String SELL_RULE_CHANGE_FAILED = "Futire change is less then "+SELL_RULE_CHANGE_PERCENTAGE+"%";
	String VOLUME_RULE_FAILED = "Buyer seller volume Rule failed";
	String CHANGE_PERCENTAGE_FAILED = BUY_RULE_CHANGE_PERCENTAGE+"% Change Rule failed";
	String CHANGE_LEVEL_FAILED = "Max/Min Change Level failed";
	
	String TRADABLE_RULE = "TRADABLE RULE";
	String TRADABLE_RULE_SUCCESS = "TRADABLE RULE SUCCESS";
	String ADDITIONAL_CHECK_RULE = "ADDITIONAL_CHECK_RULE";
	String LOT_SIZE_OUT_OF_RANGE = "LOT SIZE OUT OF RANGE";
	String FUT_BUY_TRADE_RULE_FAILED = FUTURE_BUY + AT_RATE_OF + "TRADABLE RULE FAILED";
	String FUT_BUY_TRADE_RULE_SUCCESS = FUTURE_BUY + AT_RATE_OF + "TRADABLE RULE SUCCESS";
	String FUT_SELL_TRADE_RULE_FAILED = FUTURE_SELL + AT_RATE_OF + "TRADABLE RULE FAILED";
	String FUT_SELL_TRADE_RULE_SUCCESS = FUTURE_SELL + AT_RATE_OF + TRADABLE_RULE_SUCCESS;
	
	//NARROW 7 and UT 
	String ACTIVATED = "Activated";
	String TARGET_DONE = "TARGET DONE";
	String TARGET_CLOSE_LOSS = "Closed with loss";
	String DISQUALIFIED_GAP_UP = "Diaqualified (Gap up)";
	String DISQUALIFIED_GAP_DOWN= "Diaqualified (Gap down)";
	String CLOSED_LOSS = "CLOSED WITH LOSE @ ";
	
	//Constants for controller
	String FORCE_START_APP_REQUEST = "Request recieved, App forcely starting...";
	String FORCE_START_APP_TIME_VALID = "App can be force startable in trading time only(9:15 AM t0 3:30 PM ist), Not now.  ";
	String FORCE_START_APP_IN_PROGRESS = "App is already force started, Please try to login to app after 5 minutes. ";
	String FORCE_START_APP_UP_RUNNING = "App is already up and running and scanneer threads are in monitoring mode. If needed down the app from PCF and start up again.  ";
	String FORCE_START_APP_ADMIN_LOGIN_VALID = "App adminstrator KITE CONNECT session is not yet established, App cannot be force startable, Please contact admin team.  ";
	String FORCE_START_APP_UNSUCCESS = "App force start process unsuccessful, Please contact admin team.  ;";
	String FORCE_START_APP_SUCCESSFUL = " App forcely started, Trading scanners are started and montoring... ";
	
	String NSE_TOOL_PROCESS_SUCCESSFUL =  "TRADEWARE DATA SET COMPLETED, NSEIndiaTool instantiated successfully. ";
	String ADMIN_USER_KITE_SESSION__SUCCESSFUL =  "Secondary admin and Algo trader user KITE CONNECT session created successfully. ";
	String FREE_CALL_DATA_PROCSS_SUCCESSFUL =  "Algo trader 20 free call date prepared and published successfully. ";
	String ALGO_CALL_LEVEL_PROCESS_SUCCESSFUL = "Algo trader postional and intraday levels prepared successfully. ";
	String OPTION_TRADE_SCANNER_PROCSS_SUCCESSFUL =  "Algo trader Option call trading scanner instantiated and started successfully. ";
	String FUTURE_TRADE_SCANNER_PROCSS_SUCCESSFUL =  "Algo trader Narrow 7 call trading scanner instantiated and started successfully. ";
	String NORROW7_TRADE_SCANNER_PROCSS_SUCCESSFUL =  "Algo trader Future call trading scanner instantiated and started successfully. ";
	String UNIVERSAL_TRADE_SCANNER_PROCSS_SUCCESSFUL =  "Algo trader Universal strategy call trading scanner instantiated and started successfully. ";
	String STRONGER_WEAKER_TRADE_SCANNER_PROCSS_SUCCESSFUL =  "Algo trader Stronger/Weaker stock call trading scanner instantiated and started successfully. ";
	String ORB15_BREAKOUT_TRADE_SCANNER_PROCSS_SUCCESSFUL =  "Algo trader Opening range 15 minute break out  call trading scanner instantiated and started successfully. ";

	//Exception block error for loggrt
	String FATAL_ERROR = "FATAL ERROR :: ";
	String ERROR_ON_DATA_SETUP = "Exception thrown at TradewareSchedulerConfig.setupTradewareTradeData()";
	String ERROR_ON_FREE_CALL_DATA_SETUP = "Exception thrown at TradewareSchedulerConfig.prepareRandom20FreeCallData()";
	String ERROR_ON_USER_SESSION_SETUP = "Exception thrown at TradewareSchedulerConfig.prepareRandom20FreeCallData()";
	String ERROR_ON_SCANNER_SETUP = "Exception thrown at TradewareSchedulerConfig.startAutoTradingScanners()";
	String ERROR_ON_TRADE_LEVEL_SETUP = "Exception thrown at TradewareSchedulerConfig.upatePositionalAndIntradayLevels()";
}
