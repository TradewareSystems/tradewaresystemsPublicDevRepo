package com.tradeware.clouddatabase.engine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.service.SymbolService;
import com.tradeware.stockmarket.bean.OptionChainDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaCloudDataBaseToolNewNseSite {

	private static WebClient webClient = null;

	@Autowired
	private SymbolService symbolService;
	
	@Autowired
	private KiteConnectTool kiteConnectTool;
	
	@Autowired
	private TradewareTool tradewareTool;

	public NSEIndiaCloudDataBaseToolNewNseSite() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setTimeout(20000);
	}

	private static final String BASE_URL = "https://www.nseindia.com/api/equity-master";
	private static final String STOCKS_URL = "https://www.nseindia.com/api/equity-stockIndices?index=";
	private static final String STOCKS_URL_FNO = "https://www.nseindia.com/api/equity-stockIndices?index=";
	private static final String FO_STOCKS_LOT_SIZE_URL = "https://www.nseindia.com/api/quote-derivative?symbol=";

	private static final String JSON_DATA_KEY_MARKET_LOT = "marketLot";
	private static final String COMMA = ",";
	private static final String EMPTY_STRING = "";
	private static final String JSON_DATA_KEY_DATA = "data";
	private static final String JSON_DATA_KEY_SYMBOL = "symbol";
	private static final String JSON_DATA_KEY_YEAR_HIGH = "yearHigh";
	private static final String JSON_DATA_KEY_YEAR_LOW = "yearLow";
	private static final String JSON_DATA_KEY_LAST_PRICE = "lastPrice";
	private static final String JSON_DATA_KEY_STOCKS = "stocks";
	private static final String JSON_DATA_KEY_META_DATA = "metadata";
	private static final String JSON_DATA_KEY_MARKET_ORDER_BOOK = "marketDeptOrderBook";
	private static final String JSON_DATA_KEY_MARKET_TRADE_INFO = "tradeInfo";
	private static final String FUTURE_AND_OPTION_STOCK_WAT_NEW = "Securities in F&O";
	private static final String NEW_STOCK_TYPE_PERMITTED_TO_TRADE = "Permitted to Trade";
	
	private static final String JSON_DATA_KEY_CH_CLOSE_PRICE = "CH_CLOSING_PRICE";
	private static final String JSON_DATA_KEY_EXPIRY_DATE = "expiryDate";
	private static final String JSON_DATA_KEY_EXPIRY_DATES = "expiryDates";

	private Map<String, SymbolBean> fnoStocks = new TreeMap<String, SymbolBean>();
	private Map<String, SymbolBean> nonFnoStocks = new LinkedHashMap<String, SymbolBean>();

	private static final String BUY = "BUY";
	private static final String SELL = "SELL";
	private static final double NEAR_YR_HI_LO_RANGE = 0.025;//0.05;
	
	private void prepareAlgoCloudData() {
		connectToNSEIndiaAndGetStockData();
		saveStocksDataToCloudDatabase();
	}

	private void connectToNSEIndiaAndGetStockData() {
		try {
			String response = webClient.getPage(BASE_URL).getWebResponse().getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);

			@SuppressWarnings("unchecked")
			Set<String> indexGroupSet = rawJsonData.keySet();
			for (String IndexGroup : indexGroupSet) {
				JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(IndexGroup);
				for (int i = 0; i < rawJsonDataArray.size(); i++) {
					String stockType = String.valueOf(rawJsonDataArray.get(i));
					// System.out.println("stockType >>" + stockType);
					String stockUrl = STOCKS_URL + stockType;
					if (FUTURE_AND_OPTION_STOCK_WAT_NEW.equals(stockType)) {
						stockUrl = STOCKS_URL_FNO + getSymbolForURL(stockType.toUpperCase());
						connectToNSEIndiaAndGetStockData(stockUrl, stockType, true);
					} else {
						if (NEW_STOCK_TYPE_PERMITTED_TO_TRADE.equals(stockType)) {
							stockType = getSymbolForURL(stockType.toUpperCase());
							stockUrl = stockUrl.replaceAll(NEW_STOCK_TYPE_PERMITTED_TO_TRADE, stockType);
							
						}
						connectToNSEIndiaAndGetStockData(stockUrl, stockType, false);
					}
				}
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_STOCK_DATA, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	private void connectToNSEIndiaAndGetStockData(String stockUrl, String sector, boolean fnoInd) {
		try {
			webClient.getPage("https://www.nseindia.com");
			String response = webClient.getPage(stockUrl).getWebResponse().getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
			if (null != rawJsonDataArray && !rawJsonDataArray.isEmpty()) {
				for (int i = 0; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					String symbol = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL));
					if (!nonFnoStocks.containsKey(symbol) && !fnoStocks.containsKey(symbol)) {
						SymbolBean bean = new SymbolBean();
						bean.setEquityInd(true);
						bean.setFnoInd(fnoInd);

						bean.setSector(sector);
						bean.setIndexInd(symbol.equals(sector));
						
						/*if (symbol.equals(sector)) {
							System.out.println(symbol);
						}
						if (!symbol.equals(sector)
								|| (!(symbol.equals(Constants.NIFTY_50) || symbol.equals(Constants.NSE_SITE_FINNIFTY_SYMBOL)
										|| symbol.equals(Constants.NSE_SITE_NIFTY_BANK_SYMBOL)))) {
							break;
						}*/

						bean.setSymbolId(symbol);
						bean.setWeek52High(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_YEAR_HIGH))
								.replaceAll(COMMA, EMPTY_STRING)));
						bean.setWeek52Low(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_YEAR_LOW))
								.replaceAll(COMMA, EMPTY_STRING)));
						Double lastPrice = Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_LAST_PRICE))
								.replaceAll(COMMA, EMPTY_STRING));
						bean.setLastPrice(lastPrice);
						updateNearYearHighOrLow(bean);
						//if (fnoInd) {
						if (fnoInd || isFonDerivatice(bean)) {
							connectToNSEIndiaAndGetLotSizeForFnOStock(bean);
							//fnoStocks.put(bean.getSymbolId(), bean);
							// System.out.println("map.put(\""+bean.getSymbolId()+"\", new
							// Integer("+bean.getLotSize()+"));");
							
							//20-Feb-2021 START - Ticker Size
							/*OptionChainDataBean optionBean = new OptionChainDataBeaoovn(bean.getSymbolId());
							optionBean.setIndexInd(bean.getIndexInd());
							optionBean = retriveOptionChainDataForEachStock( optionBean);
							bean.setOptionTickerSize(optionBean.getTickerSize());*/
							//connectToNSEIndiaAndGetLotSizeForFnOStock has refactored to remove above
							
							/////////////////////caluclateSma(bean/*, rawJsonDataArray*/);  ///Temporaryly stoping for scoket tmeout exception
							isSmaIsTradable(bean);
							fnoStocks.put(bean.getSymbolId(), bean);
							
//		System.out.println(bean.getSymbolId()+" --  "+bean.getOptionTickerSize());
							//20-Feb-2021 END
							
						} else {
							if (!nonFnoStocks.keySet().contains(symbol)) {
								bean.setLotSize(((int) (25000 / lastPrice)) * 5);
			/////////////////////caluclateSma(bean/*, rawJsonDataArray*/);  ///Temporaryly stoping for scoket tmeout exception
								isSmaIsTradable(bean);
								nonFnoStocks.put(bean.getSymbolId(), bean);
							}
							
						}
						// System.out.println("Lot size finding - " + bean.getSymbol());
						System.out.println("Sysmbols Prepared yet : " + nonFnoStocks.size() + fnoStocks.size());
						
					}
				}
			} else {
				TradewareLogger.saveInfoLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
						Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_STOCK_DATA_2,
						">>>> NO DATA NO DATA NODATA FOR ---- " + sector);
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_STOCK_DATA_2, e, Constants.ERROR_TYPE_EXCEPTION);
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

	List<SymbolBean> stockList = new ArrayList<SymbolBean>();
	SortedSet<Double> optionTickerSet = new TreeSet<Double>();
	List<Double> optionTickerList;
	String expiryDate = null;
	Double optionTicker = null;

	private void connectToNSEIndiaAndGetLotSizeForFnOStock(SymbolBean bean) {
		try {
			expiryDate = null;
			optionTicker = null;
			optionTickerSet.clear();
			String SymbolKey = isFonDerivatice(bean) ? getNseSiteIndexSymbol(bean.getSymbolId())
					: getSymbolForURL(bean.getSymbolId());
			String response = webClient.getPage(FO_STOCKS_LOT_SIZE_URL + SymbolKey).getWebResponse()
					.getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_STOCKS);
			if (null != rawJsonData && null != rawJsonDataArray && !rawJsonDataArray.isEmpty()) {
				expiryDate = String.valueOf(((JSONArray) rawJsonData.get(JSON_DATA_KEY_EXPIRY_DATES)).get(0));
				for (int i = 0; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					JSONObject jsonMetaDataObject = (JSONObject) jsonSymbolObject.get(JSON_DATA_KEY_META_DATA);
					if (null != expiryDate
							&& expiryDate.equals(String.valueOf(jsonMetaDataObject.get(JSON_DATA_KEY_EXPIRY_DATE)))) {
						if (null == bean.getLotSize()) {
							JSONObject jsonMktOrderBookObject = (JSONObject) jsonSymbolObject
									.get(JSON_DATA_KEY_MARKET_ORDER_BOOK);
							JSONObject jsonMktTradeinfoObject = (JSONObject) jsonMktOrderBookObject
									.get(JSON_DATA_KEY_MARKET_TRADE_INFO);

							bean.setLotSize(Integer
									.valueOf(String.valueOf(jsonMktTradeinfoObject.get(JSON_DATA_KEY_MARKET_LOT))));
							// break;
						}
						optionTicker = 
								Double.valueOf(String.valueOf(jsonMetaDataObject.get(JSON_DATA_KEY_STRIKE_PRICE)));
						if (null != optionTicker && optionTicker > 0) {
							optionTickerSet.add(optionTicker);
						}
					}
				}
				optionTickerList = optionTickerSet.stream().sorted().collect(Collectors.toList());
				if (optionTickerSet.size() > 2) {
					bean.setOptionTickerSize(Math.abs(optionTickerList.get(optionTickerSet.size() / 2)
							- optionTickerList.get((optionTickerSet.size() / 2) - 1)));
				}
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_LOT_SIZE_FOR_FNO_STOCK, e,
					Constants.ERROR_TYPE_EXCEPTION);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_TO_NSE_INDIA_AND_GET_LOT_SIZE_FOR_FNO_STOCK, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	
	private boolean isFonDerivatice(SymbolBean bean) {
		return ((null != bean.getIndexInd() && bean.getIndexInd()) && (bean.getSymbolId().equals(Constants.NIFTY_50)
				|| bean.getSymbolId().equals(Constants.NSE_SITE_FINNIFTY_SYMBOL)
				|| bean.getSymbolId().equals(Constants.NSE_SITE_NIFTY_BANK_SYMBOL)));
	}
	
	private String getNseSiteIndexSymbol(String symbolKey) {
		if (Constants.NIFTY_50.equals(symbolKey)) {
			symbolKey = Constants.NIFTY;
		} else if (Constants.NSE_SITE_FINNIFTY_SYMBOL.equals(symbolKey)) {
			symbolKey = Constants.FINNIFTY;
		} else if (Constants.NSE_SITE_NIFTY_BANK_SYMBOL.equals(symbolKey)) {
			symbolKey = Constants.BANKNIFTY;
		}
		//https://www.nseindia.com/api/quote-derivative?symbol=FINNIFTY
		return symbolKey;
	}

	private void saveStocksDataToCloudDatabase() {
		for (SymbolBean bean : nonFnoStocks.values()) {
			if (!stockList.contains(bean)) {
				// bean.setSymbolId(counter);
				ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
						.atZone(ZoneId.of(Constants.TIME_ZONE));
				bean.setDateTimeStamp(Date.from(zdt.toInstant()));
				stockList.add(bean);
			}
		}
//System.out.println("stockListsize"+stockList.size());

		for (SymbolBean bean : fnoStocks.values()) {
			// bean.setSymbolId(counter);
			bean.setFnoInd(true);
			if (null != bean.getLotSize()) {
			//if (bean.getLotSize() >= 100 && bean.getLotSize() <= 7000) {
			if (bean.getLotSize() > 200 && bean.getLotSize() < 2999) {
				bean.setCategoryFilter(1);
			} else {
				bean.setCategoryFilter(2);
			}
			} else {
				System.out.println("<<<<<<<<<<<>>>>>>>>>>>>>>>>>"+bean.getSymbolId());
				continue;
			}
			bean.setValidInd(true);
			bean.setFnoInd(true);
			ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
					.atZone(ZoneId.of(Constants.TIME_ZONE));
			bean.setDateTimeStamp(Date.from(zdt.toInstant()));
			stockList.add(bean);
		}
		try {
			symbolService.saveAll(stockList);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_SAVE_STOCKS_DATA_TO_CLOUD_DATA_BASE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		TradewareLogger.saveInfoLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
				Constants.METHOD_SAVE_STOCKS_DATA_TO_CLOUD_DATA_BASE, ">>> Data base created successfully....");
	}

	public List<SymbolBean> getPrepareAlgoCloudDataAndGetSymbolList() {
		prepareAlgoCloudData();
		return stockList;
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
	
	
	
	
	
	
	//20-Feb-2021 Start - Option ticker data
		JSONParser jsonParser = new JSONParser();
		// private static final String URL =
		// "https://nseindia.com/marketinfo/sym_map/symbolMapping.jsp?instrument=OPTSTK&date=-&segmentLink=17&symbol=";
		private static final String URL = "https://www.nseindia.com/api/option-chain-equities?symbol=";
		// private static final String URL =
		// "https://www.nseindia.com/get-quotes/derivatives?symbol=";
		private static final String URLINDEX = "https://www.nseindia.com/api/option-chain-indices?symbol=";

		public OptionChainDataBean retriveOptionChainDataForEachStock(OptionChainDataBean bean) {// (String symbol) {
			// OptionChainDataBean bean = new OptionChainDataBean(symbol);
			try {
				//webClient.getPage("https://www.nseindia.com");
				HtmlPage mainPage = webClient.getPage("https://www.nseindia.com/get-quotes/equity?symbol="+ StockUtil.getSymbolForURL(bean.getSymbol()));
				//DomElement subTabEq = mainPage.getElementById("subtab-equity");
				//subTabEq.getEl
				//DomElement quoteLtp = mainPage.getElementById("quoteLtp");
				//System.out.println(bean.getSymbol()+" :   "+quoteLtp.getTextContent());
				
				String dataURL = URL + StockUtil.getSymbolForURL(bean.getSymbol());
				if (null != bean.getIndexInd() && bean.getIndexInd()) {
					String symbol = bean.getSymbol();
					if (bean.getSymbol().equals(Constants.NIFTY) || bean.getSymbol().equals(Constants.NSE_SITE_FINNIFTY_SYMBOL) || bean.getSymbol().equals(Constants.NIFTYBANK)) {
						symbol = bean.getSymbol().equals(Constants.NIFTYBANK) ? Constants.BANKNIFTY : bean.getSymbol(); 
						dataURL = URLINDEX + StockUtil.getSymbolForURL(symbol.replaceAll(Constants.SPACE, EMPTY_STRING));
					} else {
						return bean;
					}
				}
				String pageResponse = webClient.getPage(dataURL).getWebResponse().getContentAsString();

				JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
				JSONObject rawJsonDataArrayFiltered = (JSONObject) rawJsonData.get(JSON_DATA_KEY_FILTERED);
				if (null != rawJsonDataArrayFiltered) {
					JSONArray rawJsonDataArray = (JSONArray) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_DATA);
					for (int i = 0; i < rawJsonDataArray.size(); i++) {
						JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
						if (/* null == bean.getStrikePrice1() */0 == bean.getStrikePrice1()) {
							bean.setStrikePrice1(
									Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
						} else if (/* null == bean.getStrikePrice2() */0 == bean.getStrikePrice2()) {
							bean.setStrikePrice2(
									Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
							bean.setTickerSize(
									kiteConnectTool.getRoundupToOneValue(bean.getStrikePrice2() - bean.getStrikePrice1()));
							break;
						}
					}
				}
		} catch (Exception e) {
			//failedResultDataMap.add(bean.getSymbol());
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_RETRIVE_OPTION_CHAIN_DATA_FOR_EACH_STOCK + bean.getSymbol(), e,
					Constants.ERROR_TYPE_EXCEPTION);
			
			retriveOptionChainDataForEachStock(bean);
		}
		return bean;
	}
		private static final String JSON_DATA_KEY_FILTERED = "filtered";
		private static final String JSON_DATA_KEY_STRIKE_PRICE = "strikePrice";
		
		//20-Feb-2021 END
		
		
		
		
		
		
		
		
		
		
		
		
	// Move it StockUtil
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
				historicalDataUrlAjax = StockUtil.getEquityHistDataUrl(symbolKey,
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
			

		} catch (java.net.SocketTimeoutException e) {
			System.out.println("Exception caluclateSma----------->>> " + symbolKey);
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_AND_GET_STOCK_HISTORCAL_DATA_FOR_200_SMA, e,
					Constants.ERROR_TYPE_SOCKET_TIME_OUT_EXCEPTION);
			
			
			try {
				webClient.getPage("https://www.nseindia.com");
				//connectAndGetStockHistorcalDataFor200Sma(symbolKey);
			} catch (FailingHttpStatusCodeException | IOException e1) {
				TradewareLogger.sysoutPrintlnForLocalTest(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE
						+ Constants.SEPARATOR + Constants.METHOD_CONNECT_AND_GET_STOCK_HISTORCAL_DATA_FOR_200_SMA
						+ Constants.SEPARATOR);
			}
		} catch (Exception e) {
			System.out.println("Exception caluclateSma----------->>> " + symbolKey);
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSE_INDIA_CLOUD_DATA_BASE_TOOL_NEW_NSE_SITE,
					Constants.METHOD_CONNECT_AND_GET_STOCK_HISTORCAL_DATA_FOR_200_SMA, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return rawJsonDataArrayWithWholeData;
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
			if (null != rawJsonDataArrayList) {
				for (int i = 0; i < rawJsonDataArrayList.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArrayList.get(i);
					smaClose = smaClose
							+ Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_CH_CLOSE_PRICE))
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
}
