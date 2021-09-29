package nr4.sathvikashviktechtrader.stockmarket.util;

public interface Constants {
	String TIME_PATTERN = "hh:mm:ss";
	String TIME_ZONE = "Asia/Kolkata";
	String DATE_PATTERN = "ddMMMyyyy";
	String DATE_PATTERN_HIST_DATA = "yyyy-MM-dd HH:mm:ss";
	String DATE_PATTERN_REPORT_DATE = "yyyy-MM-dd";
	
	String NA = "NA";
	String BUY = "BUY";
	String SELL = "SELL";

	String BUY_SPIKE = "BUY_SPIKE";
	String SELL_SPIKE = "SELL_SPIKE";

	String PROFIT = "PROFIT";
	String LOSS = "LOSS";
	String BUY_EXIT_PROFIT = "BUY_EXIT_PROFIT";
	String SELL_EXIT_PROFIT = "SELL_EXIT_PROFIT";
	String BUY_EXIT_LOSS = "BUY_EXIT_LOSS";
	String EXIT_FORCE = "_FROCE";// "_FROCE";
	String SELL_EXIT_LOSS = "SELL_EXIT_LOSS";
	String BUY_TARGET = " BUY TARGET DONE @ ";
	String SELL_TARGET = " SELL TARGET DONE @ ";
	String WITH_PROFIT = " WITH PROFIT @  ";
	String CLOSED_WITH_LOSE = " CLOSED WITH LOSE @ ";
	String WITH_LOSS = " WITH LOSS @ ";
	String BUY_ACTIVATED_MSG = " @ BUY Activated @ ";
	String SELL_ACTIVATED_MSG = " @ SELL Activated @ ";
	String BUY_ACTIVATE = " @ BUY Activated @ ";
	String SELL_ACTIVATE = " @ SELL Activated @ ";
	String BUYER_SELLER_DIFF_FAILED = "BUYER-SELLER DIFF_FAILED";
	String BUYER_SELLER_DIFF_FAILED_MSG = " @ BUYER-SELLER DIFF FAILED @ ";
	String CANDLE_MOVEMENT_RULE_FAILED = "CANDLE_MOVE_FAILED(SMALL_CANDLE)";

	String DISQUALIFIED_GAP_UP = "Disqualified (Gap up)";
	String DISQUALIFIED_GAP_DOWN = "Disqualified (Gap down)";
	String DISQUALIFIED_TRADABLE_STATE_INCORRECT = "Disqualified (Tradable State Incorrect)";
	double TARGET_AMOUNT = 2000d;
	double TARGET_CO_STOP_LOSS = 3000d;//4500d;// 5000d;
	double LIMIT_ORDER_PLACABLE_PRICE = 0.0005d;
	double IGNORABLE_OPEN_SPIKE = 0.0005d;
	double TOP_TRADER_INCREAMENT = 0.05d;
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

	String GREEN_BUY_PRESSURE = "GREEN_BUY";
	String GREEN_SELL_PRESSURE = "GREEN_SELL";
	String RED_BUY_PRESSURE = "RED_BUY";
	String RED_SELL_PRESSURE = "RED_SELL";

	String EMPTY_STRING = "";
	String EXCHANGE_NSE = "NSE";
	String EXCHANGE_NFO = "NFO";
	String FUTURE_KEY_PREFIX_NSE = "NSE:";
	String FUTURE_KEY_PREFIX_NFO = "NFO:";
	String FUTURE_KEY_SUFFIX_NFO = "FUT";
	String FUTURE_SYMBOL = "(FUT)";

	String COMMA = ",";
	String UNDER_SCORE = "_";
	String KITE_HIST_DATA_15_MINUTES_INTERVAL = "15minute";
	String GREEN_CANDLE = "G";
	String RED_CANDLE = "R";
	String SPACE = " ";
	String COMMA_SPACE = ", ";
	// For Kite Connect
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
	String ORDER_PLACEMENT_FAILED = "Order placement failled -- ";
	String ORDER_CANCELLED_SUCCESSFUL = "Order closed successfully -- ";
	String ORDER_CANCELLED_ERROR = "Order closing failed -- ";
	
	String OI = "OI--";
	String OI_DAY_HIGH = "OI_HIGH--";
	String OI_DAY_LOW = "OI_LOW--";
	String LATEST_OI = "LATEST_OI_H_L--";
	
	
	//
String CLASS_SATHVIKASHVIKTECHTRADERTOOL = "NR7AndUTCommonTool";
	String ERROR_SEVERIRITY_FATAL = "FATAL_ERROR";
	String ERROR_SEVERIRITY_ERROR = "ERROR";
	String ERROR_TYPE_EXCEPTION ="Exception";
	String ERROR_TYPE_PARSEEXCEPTION = "ParseException";
	String ERROR_TYPE_NETWORKEXCEPTION = "NetworkException";
	String ERROR_TYPE_TOKENEXCEPTION = "TokenException";
	String ERROR_TYPE_UNKNOWNHOSTEXCEPTION = "UnknownHostException";
	String ERROR_TYPE_SOCKETTIMEOUTEXCEPTION= "SocketTimeoutException";
	String ERROR_TYPE_KITEEXCEPTION = "KiteException";
	String ERROR_TYYE_ARITHMETICEXCEPTION = "ArithmeticException";
	String CLASS_ABSTRACTDATETOOL = "AbstractDateTool";
	String METHOD_GETTRADEDATEFORREPORT = "getTradeDateForReport";
	
	String VIEW_SOURCE_HIGHLOWREPORTFULL = "highLowReportFull";
	String VIEW_SOURCE_HIGHLOWREPORTRUNNING = "highLowReportRunning";
	
	String CALL_ENTRY = "ENTRY......";
	String CALL_EXIT   = "EXIT......";
	String ERROR_JUST_INFO = "Information";
	String ERROR_SEVER_ERROR= "Sever Error";
	String ERROR_TYPE_SYSOUT_INFO = "SysoutInfo";
	String CLASS_ABSTRACTALGOTRADERTOOL = "AbstractAlgoTraderTool";
	String TRADABLE_BASE_MAP_EMPTY = "Tradable base map is empty";
	String HIST_DATA_SIZE_LESS_THAN_21 = "Historical data size less than 21 to calculate RSI";
	String METHO_UPDATEKITEFUTUREKEYANDINSTRUMENTTOKEN = "updateKiteFutureKeyAndInstrumentToken";
	
	String CLASS_HIGHLOWSTRATEGYTOOL = "HighLowStrategyTool";
	String METHOD_GET15MINUTUECANDLEDATA = "get15MinutueCandleData";
	String METHOD_GET15MINUTUECANDLEDATAFULL = "get15MinutueCandleDataFull";
	String METHOD_CALUCLATEDAYVWAP = "caluclateDayVWAP";
	String METHOD_CALUCLATERSI = "caluclateRSI";
	String METHOD_GET5MINUTUECANDLEDATAFORADDITIONALINFO = "get5MinutueCandleDataForAdditionalInfo";
	
	String CLASS_KITECONNECTTOOL = "KiteConnectTool";
	String METHO_UPDATEDAYOHLC = "updateDayOHLC";
	String METHO_KITECONNECTREDIRECTAFTERLOGIN = "kiteConnectRedirectAfterLogin";
	String METHO_KITECONNECTREDIRECTHISTAFTERLOGIN= "kiteConnectRedirectHistAfterLogin";
	String METHO_KITECONNECTCREATESESSION = "kiteConnectCreateSession";
	String METHO_KITECONNECTCREATESESSIONHIST = "kiteConnectCreateSessionHist";
	String FATAL_ERROR_ADMIN_LOGIN_REQUIRED = "FATAL ERROR ADMIN LOGIN REQUIRED";
	
	String CLASS_SCHEDULERCONFIG = "SchedulerConfig";
	String METHOD_GET5MINUTUECANDLEDATA = "scheduler_get5MinutueCandleData";
	
	String CLASS_TECHTRADERWEBCONTROLLER = "TechTraderWebController";
	String METHOD_INIATEHOMEPATH = "iniateHomePath";
	
	String CLASS_NKPALGOLOGGER = "NkpAlgoLogger";
	String METHOD_PRINTWITHNEWLINE = "printWithNewLine";
	String METHOD_LOGERROR = "logError_";
	String METHOD_LOGINFO = "logInfo_";
}
