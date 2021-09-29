package com.tradeware.clouddatabase.engine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.service.SymbolService;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaCloudDataBaseToolNewNseSiteAdditional {

	private static WebClient webClient = null;

	@Autowired
	private SymbolService symbolService;
	
	@Autowired
	private KiteConnectTool kiteConnectTool;
	
	@Autowired
	private TradewareTool tradewareTool;

	public NSEIndiaCloudDataBaseToolNewNseSiteAdditional() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setTimeout(20000);
		
		symbolSet = new HashSet<String>();
		additionalSymbolSet = new HashSet<String>();
		failureList = new ArrayList<String>();
	}

	private static final String SEARCH_QUERY_URL = "https://www.nseindia.com/api/search/autocomplete?q=";
	private static final String STOCK_HIST_DATA_URL = "https://www.nseindia.com/api/historical/cm/equity?symbol=";
	//"https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=05-03-2021&to=05-06-2021&symbol=VGUARD"

	private static final String COMMA = ",";
	private static final String EMPTY_STRING = "";
	private static final String JSON_DATA_KEY_DATA = "data";
	private static final String JSON_DATA_KEY_SYMBOL = "symbol";
	private static final String JSON_DATA_KEY_SYMBOLS = "symbols";
	private static final String JSON_DATA_KEY_RESULT_TYPE = "result_type";
	private static final String JSON_DATA_KEY_RESULT_SUB_TYPE = "result_sub_type";
	private static final String JSON_DATA_KEY_EQUITY = "equity";
	private static final String JSON_DATA_KEY_SERIES_EQUITY = "EQ";
	
	private static final String JSON_DATA_KEY_CH_SYMBOL = "CH_SYMBOL";
	private static final String JSON_DATA_KEY_CH_52WEEK_HIGH = "CH_52WEEK_HIGH_PRICE";
	private static final String JSON_DATA_KEY_CH_52WEEK_LOW = "CH_52WEEK_LOW_PRICE";
	private static final String JSON_DATA_KEY_CH_LAST_PRICE = "CH_LAST_TRADED_PRICE";
	private static final String JSON_DATA_KEY_CH_CLOSE_PRICE = "CH_CLOSING_PRICE";
	private static final Integer ADDTIONAL_CATEGORYFLTER = 101;

	private Map<String, SymbolBean> additionalStocks = new LinkedHashMap<String, SymbolBean>();

	private static final String BUY = "BUY";
	private static final String SELL = "SELL";
	private static final double NEAR_YR_HI_LO_RANGE = 0.025;//0.05;
	
	private void prepareAlgoCloudData() {
		long startTinme = System.currentTimeMillis();
		getTradewareSymbolList();
		searchAllSymbols();
		connectToNSEIndiaAndGetAdditionalStockData();
		saveStocksDataToCloudDatabase();
		long endTinme = System.currentTimeMillis();
		
		
		
		Date myDate = new Date(endTinme - startTinme);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String myTime = formatter.format(myDate);
		System.out.println("Total Time : " + myTime);
	}
	
	private static final char CHAR_A = 'a';
	private static final char CHAR_Z = 'z';
	private Set<String> symbolSet  = null;
	private Set<String> additionalSymbolSet = null;
	private List<String> failureList = null;
	
	private void getTradewareSymbolList() {
		List<SymbolBean> list = retrieveSymbolBeanList();
		for (SymbolBean symbolBean : list) {
			symbolSet.add(symbolBean.getSymbolId());
		}
	}
	
	private void searchAllSymbols() {
		char char1;
		char char2;
		StringBuilder sb = new StringBuilder();
	    for(char1 = CHAR_A; char1 <= CHAR_Z; ++char1) {
	    	for(char2 = CHAR_A; char2 <= CHAR_Z; ++char2) {
	    		sb.append(char1).append(char2);
	    		connectToNSEIndiaAndAutoCompleteSearch(sb.toString());
	    		sb.delete(0, sb.length());
	    	}
	    }
	}
	
	private void connectToNSEIndiaAndAutoCompleteSearch(String searchChars) {
		try {
			String response = webClient.getPage(SEARCH_QUERY_URL + searchChars).getWebResponse().getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_SYMBOLS);
			if (null != rawJsonDataArray && !rawJsonDataArray.isEmpty()) {
				for (int i = 0; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					String resltType = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_RESULT_TYPE));
					String resltSubType = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_RESULT_SUB_TYPE));
					if (JSON_DATA_KEY_EQUITY.equals(resltSubType) && JSON_DATA_KEY_SYMBOL.equals(resltType)) {
						String symbol = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL));
						additionalSymbolSet.add(symbol);
					}
				}
			} else {
				/*TradewareLogger.saveInfoLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
						Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_AUTO_COMPLETE_SEARCH,
						">>>> NO DATA NO DATA NODATA FOR ---- " + searchChars);*/
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			/*try {
				webClient.getPage("https://www.nseindia.com");
			} catch (FailingHttpStatusCodeException | IOException e1) {
				
			}*/
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_AUTO_COMPLETE_SEARCH, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	private void connectToNSEIndiaAndGetAdditionalStockData() {
		try {
			webClient.getPage("https://www.nseindia.com");
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_ADDITIONAL_STOCK_DATA_BY_SYMBOL, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		if (null != additionalSymbolSet && !additionalSymbolSet.isEmpty()) {
			for (String symbol : additionalSymbolSet) {
				if (!symbolSet.contains(symbol)) {
					connectToNSEIndiaAndGetAdditionalStockDataBySymbol(symbol);	
				}
			}
		} else {
			TradewareLogger.saveInfoLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_ADDITIONAL_STOCK_DATA,
					">>>> ERROR ---- ");
		}
	}
	
	private void connectToNSEIndiaAndGetAdditionalStockDataBySymbol(String symbolKey) {
		try {
			//webClient.getPage("https://www.nseindia.com");
			//String response = webClient.getPage(STOCK_HIST_DATA_URL+getSymbolForURL(symbolKey)).getWebResponse().getContentAsString();
			String response = webClient.getPage(retrieveHistoricalDataURLForEquityWith1YearPeriod(symbolKey)).getWebResponse().getContentAsString();
			
			
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
			if (null != rawJsonDataArray && !rawJsonDataArray.isEmpty()) {
				for (int i = 0; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					String symbol = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_CH_SYMBOL));
//System.out.println("----------->>> "+symbol);
					if (!additionalStocks.keySet().contains(symbol)) {
						SymbolBean bean = new SymbolBean();
						bean.setSymbolId(symbol);
						bean.setEquityInd(true);
						bean.setFnoInd(false);
						bean.setIndexInd(false);
						bean.setSector(JSON_DATA_KEY_SERIES_EQUITY);

						bean.setWeek52High(
								Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_CH_52WEEK_HIGH))
										.replaceAll(COMMA, EMPTY_STRING)));
						bean.setWeek52Low(
								Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_CH_52WEEK_LOW))
										.replaceAll(COMMA, EMPTY_STRING)));
						Double lastPrice = Double
								.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_CH_LAST_PRICE))
										.replaceAll(COMMA, EMPTY_STRING));
						bean.setLastPrice(lastPrice);
						updateNearYearHighOrLow(bean);
						updateAdditionalSymbolProperties(bean);
						bean.setCategoryFilter(ADDTIONAL_CATEGORYFLTER);
						bean.setLotSize(((int) (25000 / lastPrice)) * 5);
						caluclateSma(bean/*, rawJsonDataArray*/);
						isSmaIsTradable(bean);
						additionalStocks.put(bean.getSymbolId(), bean);
					} else {
						break;
					}
					// System.out.println("Lot size finding - " + bean.getSymbol());
				}
			} else {
				/*TradewareLogger.saveInfoLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
						Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_STOCK_DATA_2,
						">>>> ERROR ---- ");*/
				failureList.add(symbolKey);
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			failureList.add(symbolKey);
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_ADDITIONAL_STOCK_DATA_BY_SYMBOL, e, Constants.ERROR_TYPE_EXCEPTION);
		} catch (Exception e) {
			failureList.add(symbolKey);
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_ADDITIONAL_STOCK_DATA_BY_SYMBOL, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}


	private static String getSymbolForURL(String symbol) {
		return symbol.replaceAll("&", "%26");
		// String[] check = {"L%26TFH", "BANKNIFTY", "M%26MFIN", "M%26M"};
	}
	
	private void updateNearYearHighOrLow(SymbolBean bean) {
		try {
			double highRange = kiteConnectTool
					.getRoundupToTwoValue(bean.getLastPrice() + (bean.getLastPrice() * NEAR_YR_HI_LO_RANGE));
			double lowRange = kiteConnectTool
					.getRoundupToTwoValue(bean.getLastPrice() - (bean.getLastPrice() * NEAR_YR_HI_LO_RANGE));
			bean.setYrHiLoNearInd((bean.getWeek52High() <= highRange) || (bean.getWeek52Low() >= lowRange));

			if (bean.getWeek52High() <= highRange) {
				if (null != bean.getWeek52High() && bean.getWeek52High() > 0) {
					bean.setYrHiLoNearPer(
							kiteConnectTool.getRoundupToTwoValue(bean.getLastPrice() / bean.getWeek52High()));
					bean.setYrHiLoNearTrend(BUY);
					bean.setValidInd(true);
				}
			} else if (bean.getWeek52Low() >= lowRange) {
				if (null != bean.getLastPrice() && bean.getLastPrice() > 0) {
					bean.setYrHiLoNearTrend(SELL);
					bean.setValidInd(true);
					bean.setYrHiLoNearPer(
							kiteConnectTool.getRoundupToTwoValue(bean.getWeek52Low() / bean.getLastPrice()));
				}
			}
		} catch (Exception e) {
			System.out.println(bean.getSymbolId() + " -::::::::--  " + bean.getLastPrice());
		}
	}
	
	private void updateAdditionalSymbolProperties(SymbolBean bean) {
		ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
				.atZone(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Date.from(zdt.toInstant()));
	}

	private void saveStocksDataToCloudDatabase() {
		try {
	//System.out.println("stockListsize"+stockList.size());
			symbolService.saveAll( new ArrayList<SymbolBean>(additionalStocks.values()));
			int count = 1;
			System.out.println("--------------------SUCCESS SYMBOLS------------------------");
			for(String symbol : additionalStocks.keySet()) {
				System.out.println("  "+count+".  "+symbol +"	-  "+additionalStocks.get(symbol).getLotSize());
				count++;
			}
			System.out.println("-----------------------------------------------------------------");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("-----------------------------------------------------------------");
			count = 1;
			System.out.println("--------------------FAILED SYMBOLS------------------------");
			
			for(String symbol1 : failureList) {
				System.out.println("  "+count+".  "+symbol1 +"	-  ");
				count++;
			}
			
			
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_SAVE_STOCKS_DATA_TO_CLOUD_DATA_BASE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		TradewareLogger.saveInfoLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
				Constants.METHOD_SAVE_STOCKS_DATA_TO_CLOUD_DATA_BASE, ">>> Data base created successfully....");
	}

	public List<SymbolBean> getPrepareAlgoCloudDataAndGetSymbolList() {
		prepareAlgoCloudData();
		return  new ArrayList<SymbolBean>(additionalStocks.values());
	}

	public List<SymbolBean> retrieveSymbolBeanList() {
		List<SymbolBean> symbolList = null;
		try {
			symbolList = symbolService.findAll();
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_RETRIEVE_SYMBOL_BEAN_LIST, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return symbolList;
	}
	
	
	public String retrieveHistoricalDataURLForEquityWith1YearPeriod(String symbol) {
		String historicalDataUrl = null;
		try {
			historicalDataUrl = getEquityHistDataUrl(symbol,
					tradewareTool.getFromDateWithOneYearHistDataNewNseSite(),
					tradewareTool.getCurrentMonthToDateNewNseSite());
		}catch(Exception e) {
				e.printStackTrace();
		}
		return historicalDataUrl;
	}
	//Move it StockUtil
	static String historicalDataUrlAjaxSample, intradayDataUrlAjaxSample;// Temporary indicator
	private static final String FROM_DATE = "FROM_DATE";
	private static final String TO_DATE = "TO_DATE";
	private static final String EQUITY_HIST_DATA_URL = "https://www.nseindia.com/api/historical/cm/equity?from=FROM_DATE&to=TO_DATE&symbol=";
	public static String getEquityHistDataUrl(String symbol, String fromDate,
			String toDate) {
		String historicalDataUrlAjax = EQUITY_HIST_DATA_URL.replaceAll(FROM_DATE, fromDate)
				.replaceAll(TO_DATE, toDate) + getSymbolForURL(symbol);
		if (null == historicalDataUrlAjaxSample) {
			historicalDataUrlAjaxSample = historicalDataUrlAjax;
			System.out.println(historicalDataUrlAjaxSample);
		}

		if ("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol) || "FINNIFTY".equals(symbol)) {
			historicalDataUrlAjax = historicalDataUrlAjax.replace("FUTSTK", "FUTIDX");
		}
		return historicalDataUrlAjax;
	}
	
	public static Double  smaClose = 0d, sma9 = 0d, sma21 = 0d, sma50 = 0d, sma100 = 0d, sma200 = 0d; 

	private void caluclateSma(SymbolBean bean/*, JSONArray rawJsonDataArray*/) {
		try {
			smaClose = 0d;
			sma9 = 0d;
			sma21 = 0d;
			sma50 = 0d;
			sma100 = 0d;
			sma200 = 0d;
			
			JSONArray rawJsonDataArrayList = connectAndGetStockHistorcalDataFor200Sma(bean.getSymbolId());
			
			for (int i = 0; i < rawJsonDataArrayList.size(); i++) {
				JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArrayList.get(i);
				smaClose = smaClose + Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_CH_CLOSE_PRICE))
						.replaceAll(COMMA, EMPTY_STRING));

				if (i + 1 == 200) {
					bean.setClose200Sma(kiteConnectTool.getRoundupToTwoValue(smaClose / 200));
				} else if (i + 1 == 100) {
					bean.setClose100Sma(kiteConnectTool.getRoundupToTwoValue(smaClose / 100));
				} else if (i + 1 == 50) {
					bean.setClose50Sma(kiteConnectTool.getRoundupToTwoValue(smaClose / 50));
				} else if (i + 1 == 21) {
					bean.setClose21Sma(kiteConnectTool.getRoundupToTwoValue(smaClose / 21));
				} else if (i + 1 == 9) {
					bean.setClose9Sma(kiteConnectTool.getRoundupToTwoValue(smaClose / 9));
				}
			}
		} catch (Exception e) {
System.out.println("Exception caluclateSma----------->>> "+bean.getSymbolId());
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CALUCLATE_SMA, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	public static Double tradablePriceRatio = 0d; 

	private void isSmaIsTradable(SymbolBean bean) {
		if (null != bean && null != bean.getLastPrice() && bean.getLastPrice() > 0 && null != bean.getClose200Sma()
				&& bean.getClose200Sma() > 0) {
			tradablePriceRatio = (bean.getLastPrice() * 5) / 100;

			bean.setIsClose200SmaTradable((bean.getClose200Sma() > (bean.getLastPrice() - tradablePriceRatio))
					&& (bean.getClose200Sma() < (bean.getLastPrice() + tradablePriceRatio)));
			bean.setClose200SmaTradableRatio(kiteConnectTool.getRoundupToTwoValue(
					(Math.abs(bean.getLastPrice() - bean.getClose200Sma()) * 100) / bean.getLastPrice()));
		}
	}
	
	private static String fromDate = null, toDate = null, historicalDataUrlAjax = null;
	private static JSONArray rawJsonDataArray = null, rawJsonDataArrayWithWholeData;
@SuppressWarnings("unchecked")
private JSONArray connectAndGetStockHistorcalDataFor200Sma(String symbolKey) {
	try {
		toDate = null;
		fromDate = null;
		rawJsonDataArray = null;
		rawJsonDataArrayWithWholeData = null;
		
		toDate =  tradewareTool.getCurrentMonthToDateNewNseSite();
		fromDate = tradewareTool.getFromDateByToDateForOneYearHistDataNewNseSite(toDate, 60);
		
		for (int i=0;i<=5;i++) {
			
//System.out.println("fromDate  --> "+fromDate+"  ------>>  "+toDate);
			historicalDataUrlAjax =getEquityHistDataUrl(symbolKey,
					fromDate,
					toDate);
			String response = webClient.getPage(historicalDataUrlAjax).getWebResponse().getContentAsString();

			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
			
			
			
			toDate =  tradewareTool.getFromDateByToDateForOneYearHistDataNewNseSite(fromDate, 1);
			fromDate = tradewareTool.getFromDateByToDateForOneYearHistDataNewNseSite(toDate, 60);
			if (null == rawJsonDataArrayWithWholeData) {
				rawJsonDataArrayWithWholeData = rawJsonDataArray;
			} else {
				rawJsonDataArrayWithWholeData.addAll(rawJsonDataArray);
			}
//System.out.println("rawJsonDataArrayWithWholeData  --> "+rawJsonDataArrayWithWholeData.size()+" rawJsonDataArray ------>>  "+rawJsonDataArray.size());
		}
		

	} catch (Exception e) {
		System.out.println("Exception caluclateSma----------->>> " + symbolKey);
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
				Constants.METHOD_CONNECT_AND_GET_STOCK_HISTORCAL_DATA_FOR_200_SMA, e,
				Constants.ERROR_TYPE_EXCEPTION);
	}
	return rawJsonDataArrayWithWholeData;
}
}
