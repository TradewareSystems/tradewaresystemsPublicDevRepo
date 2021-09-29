package com.tradeware.stockmarket.util;

public interface Constants3 {
	// Constants for controller
	String FORCE_START_APP_REQUEST = "Request recieved, App forcely starting...";
	String FORCE_START_APP_TIME_VALID = "App can be force startable in trading time only (9:15 AM t0 3:30 PM IST), Not now.  ";
	String FORCE_START_APP_IN_PROGRESS = "App is already force started, Please try to login to app after 5 minutes. ";
	String FORCE_START_APP_UP_RUNNING = "App is already up and running and scanneer threads are in monitoring mode. If needed down the app from PCF and start up again.  ";
	String FORCE_START_APP_ADMIN_LOGIN_VALID = "App adminstrator KITE CONNECT session is not yet established, App cannot be force startable, Please contact admin team.  ";
	String FORCE_START_APP_UNSUCCESS = "App force start process unsuccessful, Please contact admin team.  ;";
	String FORCE_START_APP_SUCCESSFUL = " App forcely started, Trading scanners are started and montoring... ";

	String NSE_TOOL_PROCESS_SUCCESSFUL = "NKPALGOTRADER DATA SET COMPLETED, NSEIndiaTool instantiated successfully. ";
	String ADMIN_USER_KITE_SESSION__SUCCESSFUL = "Secondary admin and Algo trader user KITE CONNECT session created successfully. ";
	String FREE_CALL_DATA_PROCSS_SUCCESSFUL = "Algo trader 20 free call date prepared and published successfully. ";
	String ALGO_CALL_LEVEL_PROCESS_SUCCESSFUL = "Algo trader postional and intraday levels prepared successfully. ";
	String OPTION_TRADE_SCANNER_PROCSS_SUCCESSFUL = "Algo trader Option call trading scanner instantiated and started successfully. ";
	String FUTURE_TRADE_SCANNER_PROCSS_SUCCESSFUL = "Algo trader Narrow 7 call trading scanner instantiated and started successfully. ";
	String NORROW7_TRADE_SCANNER_PROCSS_SUCCESSFUL = "Algo trader Future call trading scanner instantiated and started successfully. ";
	String UNIVERSAL_TRADE_SCANNER_PROCSS_SUCCESSFUL = "Algo trader Universal strategy call trading scanner instantiated and started successfully. ";
	String STRONGER_WEAKER_TRADE_SCANNER_PROCSS_SUCCESSFUL = "Algo trader Stronger/Weaker stock call trading scanner instantiated and started successfully. ";
	String ORB15_BREAKOUT_TRADE_SCANNER_PROCSS_SUCCESSFUL = "Algo trader Opening range 15 minute break out  call trading scanner instantiated and started successfully. ";

	// Exception block error for loggrt
	String FATAL_ERROR = "FATAL ERROR :: ";
	String ERROR_ON_DATA_SETUP = "Exception thrown at NKPAlgoTraderSchedulerConfig.setupNkpAlgoTraderTradeData()";
	String ERROR_ON_FREE_CALL_DATA_SETUP = "Exception thrown at NKPAlgoTraderSchedulerConfig.prepareRandom20FreeCallData()";
	String ERROR_ON_USER_SESSION_SETUP = "Exception thrown at NKPAlgoTraderSchedulerConfig.prepareRandom20FreeCallData()";
	String ERROR_ON_SCANNER_SETUP = "Exception thrown at NKPAlgoTraderSchedulerConfig.startAutoTradingScanners()";
	String ERROR_ON_TRADE_LEVEL_SETUP = "Exception thrown at NKPAlgoTraderSchedulerConfig.upatePositionalAndIntradayLevels()";
}
