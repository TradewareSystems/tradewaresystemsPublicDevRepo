package org.tradeware.stockmarket.engine.alphavantage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.tradeware.stockmarket.bean.ORBStockDataBean;

public class AlphavantageTool {

	private static AlphavantageTool singleton = null;

//	private static final String SMA_CROSS_OVER_5MIN 
//		= "https://www.alphavantage.co/query?function=SMA&interval=5min&time_period=9&series_type=close&apikey=MCAF9B429I44328U&symbol=NSE:";
//	
	private static final String MINUTE_5_CANDLE_DATA = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&interval=5min&apikey=MCAF9B429I44328U&symbol=NSE:";

	private static final String EMPTY_STRING = "";
	private static final String REMOVE_SLASH = "\"";

	private static final String REMOVE_COLON = ":";
	private static final String REMOVE_OPEN = "1. open";
	private static final String REMOVE_HIGH = "2. high";
	private static final String REMOVE_LOW = "3. low";
	private static final String REMOVE_CLOSE = "4. close";
	private static final String CANDLE_GREEN = "G";
	private static final String CANDLE_RED = "R";

	private AlphavantageTool() {
	}

	public static AlphavantageTool getInstance() {
		return singleton;
	}

	public void updateORBData(ORBStockDataBean bean) {
		String value = null;
		String alphavantageURL = MINUTE_5_CANDLE_DATA + bean.getStockName();
		try {
			URL oracle = new URL(alphavantageURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			int counter = 0;
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (counter == 11 || counter == 17) {
					value = inputLine;
					value = value.replace(REMOVE_OPEN, EMPTY_STRING);
					value = value.replace(REMOVE_COLON, EMPTY_STRING);
					value = value.replaceAll(REMOVE_SLASH, EMPTY_STRING).trim();
					if (counter == 11) {
						bean.setOpenCandle1(Double.parseDouble(value));
					} else if (counter == 17) {
						bean.setOpenCandle2(Double.parseDouble(value));
					}
				} else if (counter == 12 || counter == 18) {
					value = inputLine;
					value = value.replace(REMOVE_HIGH, EMPTY_STRING);
					value = value.replace(REMOVE_COLON, EMPTY_STRING);
					value = value.replaceAll(REMOVE_SLASH, EMPTY_STRING).trim();
					if (counter == 12) {
						bean.setHighCandle1(Double.parseDouble(value));
					} else if (counter == 18) {
						bean.setHighCandle2(Double.parseDouble(value));
					}
				} else if (counter == 13 || counter == 19) {
					value = inputLine;
					value = value.replace(REMOVE_LOW, EMPTY_STRING);
					value = value.replace(REMOVE_COLON, EMPTY_STRING);
					value = value.replaceAll(REMOVE_SLASH, EMPTY_STRING).trim();
					if (counter == 13) {
						bean.setLowCandle1(Double.parseDouble(value));
					} else if (counter == 19) {
						bean.setLowCandle2(Double.parseDouble(value));
					}
				} else if (counter == 14 || counter == 20) {
					value = inputLine;
					value = value.replace(REMOVE_CLOSE, EMPTY_STRING);
					value = value.replace(REMOVE_COLON, EMPTY_STRING);
					value = value.replaceAll(REMOVE_SLASH, EMPTY_STRING).trim();
					if (counter == 14) {
						bean.setCloseCandle1(Double.parseDouble(value));
					} else if (counter == 20) {
						bean.setCloseCandle2(Double.parseDouble(value));
						break;
					}
				}
				counter++;
			}
			bean.setCandle1Code((bean.getOpenCandle1() < bean.getCloseCandle1()) ? CANDLE_GREEN : CANDLE_RED);
			bean.setCandle2Code((bean.getOpenCandle2() < bean.getCloseCandle2()) ? CANDLE_GREEN : CANDLE_RED);
		} catch (Exception e) {
			bean.setErrorMessage("Failed  - " + e.getMessage());
		}
	}
}
