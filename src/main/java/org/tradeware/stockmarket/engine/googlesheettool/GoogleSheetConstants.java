package org.tradeware.stockmarket.engine.googlesheettool;

public interface GoogleSheetConstants {

	// Free call data, Random 20 symbls
	String GOOGL_SHEET_POST_URL_FREE_CALL_DATA_UPDATE = 
			"https://script.google.com/macros/s/AKfycbzXaQkhLSIm6yIqTwR50mwRxMQkIfwQKBrO_oAKN_XzsxlU089Z/exec?action=freeCallData&freeData=";
	// Placed option call upates
	String GOOGL_SHEET_POST_URL_OPTION_CALL_DATA_UPDATE = 
			"https://script.google.com/macros/s/AKfycbzXaQkhLSIm6yIqTwR50mwRxMQkIfwQKBrO_oAKN_XzsxlU089Z/exec?action=optionCallDataUpdate&optionData=";
	
	//Morning Freecall data JSON keys
	String SYMBOL = "symbol";
	String LOTSIZE = "lotsize";
	String BUY = "buy";
	String SELL = "sell";
	String LTP = "ltp";
	String OPEN = "open";
	String HIGH = "high";
	String LOW = "low";
	String CLOSE = "close";
	String VOLUMES = "volumes";
	String BUYQUANTITY = "buyQuantity";
	String SELLQUANTITY = "sellQuantity";
	String OI = "oi";
	String CHANGE = "change";
	String NKPPROFIT = "nkpProfit";
	String ZERO_STRING = "0";
	String DEFAULT_ZERO = "0.0";
	

}
