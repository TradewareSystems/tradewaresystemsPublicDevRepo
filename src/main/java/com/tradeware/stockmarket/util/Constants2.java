package com.tradeware.stockmarket.util;

public interface Constants2 {

	String CALL_ENTRY = "ENTRY......";
	String CALL_EXIT = "EXIT......";
	String CALL_ENTRY_EXIT_DURATION = " EXIT......  Duration_>_:_";
	
	String CLASS_DATABASE_HELPER = "DatabaseHelper";
	String METHOD_GET_USER_DATA_BEAN = "getUserDataBean";
	String METHOD_SAVE_USER_DATA_BEAN = "saveUserDataBean";
	String METHOD_GET_USER_DATA_BEAN_BY_EMAIL = "getUserDataBeanByEmail";
	String METHOD_GET_USER_DATA_BEAN_BY_CONFIRMATION_TOKEN = "getUserDataBeanByConfirmationToken";
	String METHOD_FIND_ALL_BY_TRADED_DATE_STAMP_ORDER_BY_SYMBOL_NAME_ASC_TRADED_AT_DTTM_ASC = "findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc";
	String METHOD_FIND_ALL_BY_TRADED_DATE_STAMP_FILTER_ITEMS_ORDER_BY_TRADED_AT_DTTM_DESC = "findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc";
	String METHOD_FIND_ALL_BY_TRADED_DATE_STAMP_AND_OHLC_STATE_EQ_TRADABLE_STATE_ORDER_BY_SYMBOL_ASC_TRADED_AT_DT_TM_ASC = "findAllByTradedDateStampAndOhlcStateEqTradableStateOrderBySymbolAscTradedAtDtTmAsc";
	String METHOD_FIND_ALL_BY_STRONG_VWAP_AND_VOLUME_RULE = "findAllByStrongVwapAndVolumeRule";
	String METHOD_FIND_ALL_BY_TRADING_RULE_7TIMES_STRENTH = "findAllByTradingRule7TimesStrenth";
	String METHOD_FIND_ALL_BY_TRADING_RULE_OPEN_BTWN_AVG_HI_LO_CLS_AND_VWAP = "findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap";
	String METHOD_FIND_ALL_NIFTY_OR_BANKNIFTY = "findAllNIFTYorBANKNIFTY";
	String METHOD_FIND_ALL_BY_STRONG_VOLUME_PRESSURE_RULE = "findAllByStrongVolumePressureRule";
	String METHOD_FIND_ALL_BY_STRONG_VOLUME_PRESSURE_RULE_MATCH = "findAllByStrongVolumePressureRuleMatch";
	String METHOD_FIND_ALL_BY_SMA_VWAP_LEVEL_2_RULE = "findAllBySmaVwapLevel2Rule";
	String METHOD_FIND_ALL_BY_SMA_VWAP_LEVEL_2_PLUS_RULE = "findAllBySmaVwapLevel2PlusRule";
	String METHOD_FIND_ALL_BY_INIT_PROFITABLE_PROD_RULE = "findAllByInitProfitableProdRule";
	String METHOD_FIND_ALL_BY_INIT_PROFITABLE_FILTER_PROD_RULE = "findAllByInitProfitableFilterProdRule";
	String METHOD_FIND_ALL_BY_VOLUME_STRENGTH_TRADES = "findAllByVolumeStrengthTrades";
	String METHOD_FIND_ALL_BY_STOCHASTIC_VOLUME_STRENGTH_TRADES = "findAllByStochasticVolumeStrengthTrades";
	String METHOD_FIND_ALL_BY_STRONG_STOCHASTIC_VOLUME_STRENGTH_TRADES = "findAllByStrongStochasticVolumeStrengthTrades";
	String METHOD_FIND_ALL_BY_STOCHASTIC_RULE1_TRADES = "findAllByStochasticRule1Trades";
	String METHOD_FIND_ALL_BY_STOCHASTIC_RULE2_TRADES = "findAllByStochasticRule2Trades";
	String METHOD_FIND_ALL_BY_STOCHASTIC_STRONG_RULE3_TRADES = "findAllByStochasticStrongRule3Trades";
	String METHOD_FIND_ALL_BY_STOCHASTIC_BASIC_RULE_TRADES = "findAllByStochasticBasicRuleTrades";
	String METHOD_FIND_ALL_BY_PROFIIT_TRADES = "findAllByProfiitTrades";
	String METHOD_CHECK_TODAY_TRADING_NOT_FORCE_CLOSED_BY_CROSS_MAX_LOSS_LIMIT = "checkTodayTradingNotForceClosedByCrossMaxLossLimit";
	String CLASS_TRADEWARE_WEB_CONTROLLER = "TradewareWebController";
	String METHOD_INITATE_HOME_PATH = "initateHomePath";
	
	String METHOD_SAVE_OPTION_CHAIN_DATA = "saveOptionChainData";
	String METHOD_UPDATE_BEST_TRADE = "updateBestTrade";
	String METHOD_FIND_BY_SYMBOLID_AND_DATESTAMP_AND_PARENTRECORDIND_TRUE = "findBySymbolIdAndDateStampAndParentRecordIndTrue";
	String METHOD_FIND_BY_SYMBOLID_AND_PARENTRECORDIND_TRUE_FOR_CURRENTDAY = "findBySymbolIdAndParentRecordIndTrueForCurrentDay";
	String METHOD_FIND_BY_TRADED_DATESTAMP_ORDERBY_TRADEDATDTTMDESC = "findAllByTradedDateStampOrderByTradedAtDtTmDesc";
	String METHOD_UPDATE_TRADE_AVGHLC = "updateTradeAVGHLC";
	
	String METHOD_SAVE_ALL_SYMBOLS = "saveAllSymbols";
	String METHOD_FIND_ALL_SYMBOLS = "findAllSymbols";
	String METHOD_FIND_ALL_ORDER_BY_CLOSE_200_SMA_TRADABLE_RATIO = "findAllOrderByClose200SmaTradableRatio";
	String METHOD_FIND_BY_SYMBOL_ID = "findBySymbolId";
	String METHOD_FIND_ALL_VALID_SYMBOLS = "findAllValidSymbols";
	String METHOD_FIND_ALL_VALID_INDEX_SYMBOLS = "findAllValidIndexSymbols";
	String METHOD_FINDALLBYFNOINDANDVALIDINDORDERBYSYMBOLID = "findAllByFnoIndAndValidIndOrderBySymbolId";
	String METHOD_FINDALLBYFNOINDANDVALIDINDANDCATEGORYFILTERORDERBYSYMBOLID = "findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId";
	
	String METHOD_GET_RUNNING_TRADES = "getRunningTrades";
	String METHOD_SAVE_TRADE_ON_ENTRY = "saveTradeOnEntry";
	String METHOD_UPDATE_TRADE = "updateTrade";
	String METHOD_TRADE_CLOSE = "manualTradeClose";
	String METHOD_UPDATE_POSITIVE_NEGATIVE_MOVES = "updatePositiveNegativeMoves";
	String METHOD_UPDATE_ADUJUST_TRADE = "updateAdujustTrade";
	
	String METHOD_MONTHLY_REPORT_FWD_ENGULFING = "findAllMonthlyReportByForwardEngulfingRuleTrades";
	String METHOD_MONTHLY_REPORT_FWD_ENGULFING_LVL2 = "findAllMonthlyReportByForwardEngulfingLvl2RuleTrades";
	String METHOD_MONTHLY_REPORT_FWD_ENGULFING_LVL3 = "findAllMonthlyReportByForwardEngulfingLvl3RuleTrades";
	String METHOD_MONTHLY_REPORT_SMA_VWAP = "findAllMonthlyReportBySmaVwapRuleTrades";
	String METHOD_MONTHLY_REPORT_SMA_VWAP_UNION = "findAllMonthlyReportBySmaVwapRuleUnionTrades";
	String METHOD_MONTHLY_REPORT_SMA_VWAP_RULE2 = "findAllMonthlyReportBySmaVwapRule2Trades";
	
	String CLASS_KITE_CONNECT_TOOL  = "KiteConnectTool";
	String METHOD_KITE_CONNECT_REDIRECT_AFTER_LOGIN = "kiteConnectRedirectAfterLogin";
	String METHOD_KITE_CONNECT_REDIRECT_HIST_AFTER_LOGIN = "kiteConnectRedirectHistAfterLogin";
	String METHOD_KITE_CONNECT_CREATE_SESSION= "kiteConnectCreateSession";
	String METHOD_KITE_CONNECT_CREATE_SESSION_HIST = "kiteConnectCreateSessionHist";
	String METHOD_PLACE_COVER_ORDER = "placeCoverOrder";
	String METHOD_CANCEL_COVER_ORDER = "cancelCoverOrder";
	
	String METHOD_ADD_INDEXES_TO_TRADE2 = "addIndexesToTrade2";
	String METHOD_VALID_KITE_ACCESS = "validKiteAccess";
	String METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN="updateKiteFutureKeyAndInstrumentToken";
	String METHOD_GET_ROUND_UP_TO_TWO_VALUE = "getRoundupToTwoValue";
	String METHOD_GET_ROUND_UP_TO_TWO_VALUE_UP  = "getRoundupToTwoValueUp";
	String METHOD_UPDATE_DAY_OHLC = "updateDayOHLC";
	String METHOD_UPDATE_LIVE_OHLC = "updateLiveOHLC";
	String ERROR_KITE_INPUT_SYMBOLS_ARRAY_IS_NULL_OR_EMPTY = "KITE INPUT :: symbolsArray is null or empty";
	
	String CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD = "TradingDataMapHistData15MinuteThread";
	String METHOD_RUN = "run()";
	String METHOD_REFRESH_TRADING_DATA_MAP_15_MINUTE= "refreshTradingDataMap15Minute";
	String METHOD_INIT_GET_15_MINUTE_CANDLE_DATA = "initGet15MinuteCandleData";
	String METHOD_REPAEAT_15_MINUTUE_CANDLE_DATA_FULL = "repaeat15MinutueCandleDataFull";
	String METHOD_GET_15_MINUTUE_CANDLE_DATA_FULL = "get15MinutueCandleDataFull";
	String METHOD_APPLY_NR7_RULE = "applyNR7Rule";
	String INVALID_KITE_HIST_DATA_FATAL_ERROR = ">>> histDataList is null OR size is <5 >>>";
	
	String CLASS_ABSTRACT_DATE_TOOL = "";
	String METHOD_GET_FROM_DATE_FOR_KITE_HIST_DATA_ON_CURRENT_DAY_9_00 = "getFromDateForKiteHistDataOnCurrentDay_9_00";
	String METHOD_GET_FROM_DATE_FOR_KITE_HIST_DATA_ON_CURRENT_DAY_9_15 = "getFromDateForKiteHistDataOnCurrentDay_9_15";
	String METHOD_GET_TO_DATE_FOR_KITE_HIST_DATA_WITH_CURRENT_TIME= "getToDateForKiteHistData_WithCurrentTime";
	String METHOD_GET_TO_DATE_FOR_KITE_HIST_DATA_ON_CURRENT_DAY_9_45 = "getToDateForKiteHistDataOnCurrentDay_9_45";
	String METHOD_GET_HEIKIN_ASHI_FROM_DATE_FOR_KITE_HIST_DATA = "getHeikinAshiFromDateForKiteHistData";
	String METHOD_GET_PREVIOUS_FROM_DATE_FOR_KITE_HIST_HR_DATA = "getPreviousFromDateForKiteHistHrData";
	String METHOD_GET_PREVIOUS_TO_DATE_FOR_KITE_HIST_HR_DATA = "getPreviousToDateForKiteHistHrData";
	String METHOD_GET_TRADE_DATE_FOR_REPORT = "getTradeDateForReport";
	
	String CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD = "TradingDataMapHistData1MinuteThread";
	String METHOD_INIT_GET_1_MINUTE_CANDLE_DATA = "initGet1MinuteCandleData";
	String METHOD_REPAEAT_1_MINUTUE_CANDLE_DATA_FULL = "repaeat1MinutueCandleDataFull";
	String METHOD_GET_1_MINUTUE_CANDLE_DATA_FULL = "get1MinutueCandleDataFull";
	
	String METHOD_INIT_GET_5_MINUTE_CANDLE_DATA = "initGet5MinuteCandleData";
	
	String CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD = "TradingDataMapHistData60MinuteThread";
	String METHOD_INIT_GET_60_MINUTE_CANDLE_DATA = "initGet60MinuteCandleData";
	String METHOD_INIT_GET_DAY_CANDLE_DATA  = "initGetDayCandleData";
	String METHOD_REPAEAT_60_MINUTUE_CANDLE_DATA_FULL = "repaeat60MinutueCandleDataFull";
	String METHOD_REPAEAT_DAY_CANDLE_DATA_FULL = "repaeatDayCandleDataFull";
	String METHOD_GET_60_MINUTUE_CANDLE_DATA_FULL = "get60MinutueCandleDataFull";
	String METHOD_GET_DAY_CANDLE_DATA_FULL = "getDayCandleDataFull";
	
	String CLASS_TRADING_DATA_MAP_HIST_DATA_1_HOUR_THREAD= "TradingDataMapHistData1HourStochasticThread";
	String CLASS_TRADING_DATA_MAP_HIST_DATA_STC_15_MINUTE_THREAD = "TradingDataMapHistData15MinuteStochasticThread";
	String CLASS_TRADING_DATA_MAP_HIST_DATA_STC_5_MINUTE_THREAD = "TradingDataMapHistData5MinuteStochasticThread";
	
	String CLASS_TRADEWARE_SCANNER = "TradewareScanner";
	String CLASS_TRADEWARE_DAY_LEVEL_SCANNER = "TradewareDayLevelScanner";
	
	String CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE  = "NSEIndiaCloudDataBaseToolNewNseSite";
	String METHOD_CONNECT_TO_NSE_INDIA_AND_GET_STOCK_DATA = "connectToNSEIndiaAndGetStockData";
	String METHOD_CONNECT_TO_NSE_INDIA_AND_GET_STOCK_DATA_2 = "connectToNSEIndiaAndGetStockData(OVERRIDE)";
	String METHOD_CONNECT_TO_NSE_INDIA_AND_GET_LOT_SIZE_FOR_FNO_STOCK = "connectToNSEIndiaAndGetLotSizeForFnOStock";
	String METHOD_SAVE_STOCKS_DATA_TO_CLOUD_DATA_BASE="saveStocksDataToCloudDatabase";
	String METHOD_RETRIEVE_SYMBOL_BEAN_LIST="retrieveSymbolBeanList";
	String METHOD_RETRIVE_OPTION_CHAIN_DATA_FOR_EACH_STOCK = "retriveOptionChainDataForEachStock";
	
	String METHOD_CONNECT_TO_NSE_INDIA_AND_AUTO_COMPLETE_SEARCH = "connectToNSEIndiaAndAutoCompleteSearch";
	String METHOD_CONNECT_TO_NSE_INDIA_AND_GET_ADDITIONAL_STOCK_DATA = "connectToNSEIndiaAndGetAdditionalStockData";
	String METHOD_CONNECT_TO_NSE_INDIA_AND_GET_ADDITIONAL_STOCK_DATA_BY_SYMBOL = "connectToNSEIndiaAndGetAdditionalStockDataBySymbol";
	String METHOD_CALUCLATE_SMA = "caluclateSma";
	String METHOD_CONNECT_AND_GET_STOCK_HISTORCAL_DATA_FOR_200_SMA = "connectAndGetStockHistorcalDataFor200Sma";
	String CLASS_SCHEDULER_CONFIG = "SchedulerConfig";
	String METHOD_START_AND_RUN_BUY_SELL_SCANNER = "startAndRunBuySellScanner";
	String METHOD_RUN_15_MINUTUE_CANDLE_DATA = "run15MinutueCandleData";
	String METHOD_GET_TO_DATE_FOR_1_MINUTE_KITE_HIST_DATA_HIST = "getToDateFor1MinuteKiteHistDataHist";
	String METHOD_GET_TO_DATE_FOR_5_MINUTE_KITE_HIST_DATA_HIST = "getToDateFor5MinuteKiteHistDataHist";
	
	
	String CLASS_ACTIVITY_AUDIT_SERVICE = "ActivityAuditService";
	String METHOD_SAVE_ACTIVITY_AUDIT_DATA_BEAN = "saveActivityAuditDataBean";
	String METHOD_FIND_ALL_ACTIVITY_BY_DATE_STAMP_ORDER_BY_DATE_TIME_STAMP_DESC = "findAllActivityByDateStampOrderByDateTimeStampDesc";
	
	String CLASS_SYSTEM_ERROR_SERVICE = "SystemErrorService";
	String METHOD_FIND_ALL_BY_DATE_STAMP_ORDER_BY_DATE_TIME_STAMP_DESC = "findAllByDateStampOrderByDateTimeStampDesc";
	String CLASS_TRADEWARE_ORDER_PLACEMENT_RULE_TOOL = "TradewareOrderPlacementRuleTool";
	String METHOD_PLACE_TRADE_ORDER = "placeTradeOrder"; 
	String METHOD_WAIT_FOR_ENGULFING_STRATEGY_RULE = "waitForEngulfingStrategyRule";
	
	String CLASS_PROFIT_LOSS_SUMMARY_SERVICE = "ProfitLossSummaryService";
	String METHOD_SAVE_ALL_PROFIT_LOSS_SUMMARY_LIST = "saveAllProfitLossSummaryList";
	String METHOD_SAVE_PROFIT_LOSS_SUMMARY_BEAN ="saveProfitLossSummaryBean";
	String METHOD_FIND_ALL_PROF_LOSS_SUMM_BY_DATE = "findAllProfLossSummByDate";
	
	String CLASS_ADDITIONAL_DATA_FILLUP_HELPER = "AdditionalDataFillupHelper";
	String METHOD_FIND_HEIKIN_ASHI_TREND = "findHeikinAshiTrend";
	String METHOD_FIND_STOCHASTIC_TREND = "findStochasticTrend";
	
	String CALL_OPTION_TRADING_TOOL = "OptionTradingTool";
	String METHOD_GET_OPTION_TICKER_KEY = "getOptionTickerKey";
	
	String ERROR_SEVERIRITY_ERROR = "ERROR";
	String ERROR_TYPE_EXCEPTION = "Exception";
	String ERROR_SEVERIRITY_FATAL = "FATAL_ERROR";
	String ERROR_JUST_INFO = "Information";
	String ERROR_TYPE_JUST_SYSOUT_INFO = "SysoutInfo";
	
	
	String  ERROR_TYPE_KITE_EXCEPTION= "KiteException";
	String  ERROR_TYPE_PARSE_EXCEPTION = "ParseException";
	String ERROR_TYPE_TOKEN_EXCEPTION = "TokenException";
	String ERROR_TYPE_NETWORK_EXCEPTION = "NetworkException";
	String ERROR_TYPE_JSON_EXCEPTION = "JSONException";
	String ERROR_TYPE_INPUT_EXCEPTION = "KiteInputException";
	String ERROR_TYPE_SOCKET_TIME_OUT_EXCEPTION = "SocketTimeoutException";
	String ERROR_TYPE_UNKNOWN_HOST_EXCEPTION = "UnknownHostException";
	String ERROR_TYPE_NUMBER_FORMAT_EXCEPTION = "NumberFormatException";
	String ERROR_ON_UPDATE_OHLC = "FATAL ERROR ADMIN LOGIN REQUIRED";
	
	
	String METHOD_REPAEAT15MINUTUECANDLEDATAFULL_FAILED ="<span style='color:red'> SeriousError repaeat15MinutueCandleDataFull()  --- FAILED HERE <span>";
	String METHOD_REPAEAT5MINUTUECANDLEDATAFULL_FAILED ="<span style='color:red'> SeriousError repaeat5MinutueCandleDataFull()  --- FAILED HERE <span>";
	String METHOD_REPAEAT10MINUTUECANDLEDATAFULL_FAILED ="<span style='color:red'> SeriousError repaeat10MinutueCandleDataFull()  --- FAILED HERE <span>";
	String METHOD_KITE_HIST_DATE_ERROR = "<span style='color:red'> SeriousError KitehistDataList is null OR size is <3 <span>";

	
	
	
	
	
	
	
	
	
	
	
	
	String CLASS_NSEINDIATOOLOPTIONCHAINTOOL = "NSEIndiaToolOptionChainTool";
	String METHOD_RETRIVEOPTIONCHAINDATAFOREACHSTOCK = "retriveOptionChainDataForEachStock";
	String METHOD_RETRIEVETOPGAINERLOOSERDATA = "retrieveTopGainerLooserData";
	
	String CLASS_NSEINDIACLOUDDATABASETOOLNEWNSESITE = "NSEIndiaCloudDataBaseToolNewNseSite";
	String METHOD_CONNECTTONSEINDIAANDGETSTOCKDATA  = "connectToNSEIndiaAndGetStockData";
	String METHOD_CONNECTTONSEINDIAANDGETSTOCKDATA_2  = "connectToNSEIndiaAndGetStockData()";
	String METHOD_CONNECTTONSEINDIAANDGETLOTSIZEFORFNOSTOCK = "connectToNSEIndiaAndGetLotSizeForFnOStock";
	String METHOD_SAVESTOCKSDATATOCLOUDDATABASE = "saveStocksDataToCloudDatabase";
	String METHOD_RETRIEVESYMBOLBEANLIST = "retrieveSymbolBeanList";
	

	String METHOD_FIND_BY_OPTION_STRATEGY_TRADE_ID = "findByOptionStrategyTradeId";
	String METHOD_FIND_RUNNING_TRADE_BY_TRADE_ID = "findRunningTradeByTradeId";
	String METHOD_FIND_BY_OPEN_OPTION_STRATEGY_TRADES = "findByOpenOptionStrategyTrades";
	String METHOD_FIND_BY__TRADED_DATE_STAMP = "findAllByTradedDateStamp";
	String METHOD_FIND_BY_POSITION_AND_STRATEGY= "findByTradePositionAndTradeStrategy";
	String METHOD_FIND_BY_RUNNING_TRADE = "findAllByRunningTrade";
	String METHOD_FIND_BY_RUNNING_CHILD_TRADE = "findAllByRunningChildTrade";
	String METHOD_SAVE_SHORT_STRANGLE_OPTION_TRADE = "saveShortStrangleOptionTrade";
}
