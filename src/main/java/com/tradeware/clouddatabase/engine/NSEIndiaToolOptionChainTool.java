package com.tradeware.clouddatabase.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tradeware.clouddatabase.bean.OptionChainInfoBean;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.service.OptionChainInfoService;
import com.tradeware.clouddatabase.service.SymbolService;
import com.tradeware.stockmarket.bean.OptionChainDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.bean.TopGainerLooserDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.DatabaseMapper;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaToolOptionChainTool {

//temp
public static int totalSymbolsProcessed;
	private static NSEIndiaToolOptionChainTool singletonTool;
	private static WebClient webClient = null;

	@Autowired
	private SymbolService symbolService;
	
	@Autowired
	private DatabaseMapper databaseMapper;

	public Hashtable<String, String> getKiteFutureKeyMap() {
		return kiteFutureKeyMap;
	}

	private Hashtable<String, String> kiteFutureKeyMap = new Hashtable<String, String>();

	private Hashtable<String, OptionChainDataBean> optionChainDataMap = new Hashtable<String, OptionChainDataBean>();

	public Hashtable<String, OptionChainDataBean> getOptionChainDataMap() {
		return optionChainDataMap;
	}

	private Hashtable<String, OptionChainDataBean> optionChainBaseDataMap = new Hashtable<String, OptionChainDataBean>();

	public Hashtable<String, OptionChainDataBean> getOptionChainBaseDataMap() {
		return optionChainBaseDataMap;
	}

	private Hashtable<String, OptionChainDataBean> optionChainTempDataMap = new Hashtable<String, OptionChainDataBean>();
	private Hashtable<String, OptionChainDataBean> optionChainIdleDataMap = new Hashtable<String, OptionChainDataBean>();

	public Hashtable<String, OptionChainDataBean> getOptionChainTempDataMap() {
		return optionChainTempDataMap;
	}

	private Hashtable<String, OptionChainDataBean> optionChainDataMapIndex = new Hashtable<String, OptionChainDataBean>();

	public Hashtable<String, OptionChainDataBean> getOptionChainDataMapIndex() {
		return optionChainDataMapIndex;
	}

	public static NSEIndiaToolOptionChainTool getInstance() {
		if (null == singletonTool) {
			webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setCssEnabled(false);
			singletonTool = new NSEIndiaToolOptionChainTool();
		}
		return singletonTool;
	}

	public NSEIndiaToolOptionChainTool() {
		if (null == singletonTool) {
			webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			// singletonTool = new NSEIndiaToolOptionChainTool();
			singletonTool = this;
		}
		// retriveOptionChainDataForEachStock("ACC");
	}

	public List<SymbolBean> retrieveSymbolBeanList() {
		List<SymbolBean> symbolList = null;
		try {
			//symbolList = symbolService.findAll();
			List<SymbolDataBean> symbolDataBeans = DatabaseHelper.getInstance()
					.findAllByFnoIndAndValidIndOrderBySymbolId(Boolean.TRUE, Boolean.TRUE);
			
			symbolList = new ArrayList<SymbolBean>(symbolDataBeans.size());
			for (SymbolDataBean symbolDataBean : symbolDataBeans) {
				SymbolBean bean = databaseMapper.mapSymbolDataBeanToSymbolBean(symbolDataBean);
				symbolList.add(bean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
					Constants.METHOD_RETRIEVESYMBOLBEANLIST, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return symbolList;
	}

	public void forcePrepareOptionHistoryData() {
		retrieveTopGainerLooserData(null);
		getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData();

		for (String symbol : optionChainTempDataMap.keySet()) {
			OptionChainDataBean beanOI = retriveOptionChainDataForEachStock(optionChainTempDataMap.get(symbol));
			beanOI.setCandleNumber(-1);
			beanOI.setTimeFrameNumber(-1);
			beanOI.setParentRecordInd(false);
			beanOI.getOITrend();
			beanOI.handleSortOrderAndStyle();

			ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
					.atZone(ZoneId.of(Constants.TIME_ZONE));
			beanOI.setDateTimeStamp(Date.from(zdt.toInstant()));
		}
		// while (!failedResultDataMap.isEmpty()) {
		if (!failedResultDataMap.isEmpty()) {
			retryOnFaildResultDataMap();
		}
		optionChainDataMap.clear();
		optionChainDataMap.putAll(optionChainTempDataMap);
		optionChainTempDataMap.clear();

		optionChainDataMapIndex.clear();
		for (String index : StockUtil.getIndexOISymbols().keySet()) {
			OptionChainDataBean indexOi = new OptionChainDataBean(index);
			indexOi.setIndexInd(true);
			indexOi.setLotSize(StockUtil.getIndexOISymbols().get(index));
			indexOi = retriveOptionChainDataForEachStock(indexOi);
			indexOi.setTickerSize(
					kiteConnectTool.getRoundupToTwoValue(indexOi.getStrikePrice2() - indexOi.getStrikePrice1()));
			indexOi.getOITrend();
			indexOi.handleSortOrderAndStyle();

			indexOi.setCandleNumber(getCandleNumber());
			indexOi.setTimeFrameNumber(timeFrameNumber);
			ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
					.atZone(ZoneId.of(Constants.TIME_ZONE));
			indexOi.setDateTimeStamp(Date.from(zdt.toInstant()));
			optionChainDataMapIndex.put(index, indexOi);
		}
	}

	@Autowired
	private KiteConnectTool kiteConnectTool;

	public void init_NSEIndiaTool() {
		retrieveStockAndPrepareOptionHistoryData(Boolean.TRUE);
		optionChainDataMap.clear();
		optionChainDataMap.putAll(optionChainTempDataMap);
	}

	/* private */public void getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData() {

		/*
		 * OptionChainDataBean bean = new OptionChainDataBean("PVR");
		 * bean.setLotSize(407); optionChainBaseDataMap.put("PVR", bean);
		 */

		if (null == optionChainBaseDataMap || optionChainBaseDataMap.isEmpty()) {
			/*
			 * List<SymbolBean> list =new ArrayList<SymbolBean>(); SymbolBean b2 = new
			 * SymbolBean(); b2.setSymbolId("PVR"); b2.setLotSize(407); b2.setFnoInd(true);
			 * b2.setWeek52High(350d); b2.setWeek52Low(161d); list.add(b2); SymbolBean b =
			 * new SymbolBean(); b.setSymbolId("DRREDDY"); b.setLotSize(250);
			 * b.setFnoInd(true); b.setWeek52High(1500d); b.setWeek52Low(1001d);
			 * list.add(b); SymbolBean b3 = new SymbolBean(); b3.setSymbolId("BHARTIARTL");
			 * b3.setLotSize(1851); b3.setFnoInd(true); b3.setWeek52High(1500d);
			 * b3.setWeek52Low(1001d); list.add(b3);
			 */

			// System.out.println("Total Sysmbols size - "+list.size());
			prepareOptionChainDataMap();
		} else {
			for (OptionChainDataBean dataBean : optionChainBaseDataMap.values()) {
				optionChainTempDataMap.put(dataBean.getSymbol(), dataBean.clone());
			}
		}

		// System.out.println("Total Size -- "+optionChainBaseDataMap.size());
	}

	private void prepareOptionChainDataMap() {
		List<SymbolBean> list = retrieveSymbolBeanList();
		for (SymbolBean symbolBean : list) {
			/*
			 * if (null != symbolBean.getFnoInd() && symbolBean.getFnoInd() &&
			 * !heavySymbolList().contains(symbolBean.getSymbolId())) {
			 */
			if (null != symbolBean.getFnoInd() && symbolBean.getFnoInd()
					&& (symbolBean.getLotSize() >= 100 && symbolBean.getLotSize() <= /* 4000 */6600)
					&& !heavySymbolList().contains(symbolBean.getSymbolId())) {
				OptionChainDataBean bean = new OptionChainDataBean(symbolBean.getSymbolId());
				bean.setWeek52High(symbolBean.getWeek52High());
				bean.setWeek52Low(symbolBean.getWeek52Low());
				bean.setLotSize(symbolBean.getLotSize());
				optionChainBaseDataMap.put(symbolBean.getSymbolId(), bean);

				optionChainTempDataMap.put(symbolBean.getSymbolId(), bean.clone());
			} else if (symbolBean.getSymbolId().equals("NIFTY") || symbolBean.getSymbolId().equals("BANKNIFTY")
					|| symbolBean.getSymbolId().equals("FINNIFTY")) {
				OptionChainDataBean bean = new OptionChainDataBean(symbolBean.getSymbolId());
				bean.setWeek52High(symbolBean.getWeek52High());
				bean.setWeek52Low(symbolBean.getWeek52Low());
				bean.setLotSize(symbolBean.getLotSize());
				bean.setIndexInd(true);
				optionChainBaseDataMap.put(symbolBean.getSymbolId(), bean);

				optionChainTempDataMap.put(symbolBean.getSymbolId(), bean.clone());
			}
		}
		
		addIndexesToOptionChainBaseDataMap();
	}

	List<String> list = new ArrayList<String>();

	private List<String> heavySymbolList() {
		if (list.isEmpty()) {
			list.add("MRF");
			list.add("BOSCHLTD");
			list.add("PAGEIND");
			list.add("SHREECEM");
		}
		return list;
	}

	private void addIndexesToOptionChainBaseDataMap() {
		if (sathvikAshvikTechTraderTool.getNextExpiryDatesList().isEmpty()) {
			bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.NIFTY);
			bankNiftyTradeTool.flterOutNextExpiryDates(Constants.NIFTY);
		}
		double conter = 500;
		for (String expDate : sathvikAshvikTechTraderTool.getNextExpiryDatesList()) {
			conter = conter - 10;
			if (!optionChainBaseDataMap.contains(Constants.NIFTY + Constants.SPACE +expDate)) {

				OptionChainDataBean bean = new OptionChainDataBean(Constants.NIFTY + Constants.SPACE +expDate);
				// bean.setWeek52High(symbolBean.getWeek52High());
				// bean.setWeek52Low(symbolBean.getWeek52Low());
				bean.setLotSize(75);
				bean.setIndexInd(true);
				bean.setExpiryDate(expDate);
				bean.setStrongOrder(conter+ 500);
				optionChainBaseDataMap.put(bean.getSymbol(), bean);

				optionChainTempDataMap.put(bean.getSymbol(), bean.clone());
			}
			if (!optionChainBaseDataMap.contains(Constants.BANKNIFTY + Constants.SPACE +expDate)) {
				OptionChainDataBean bean = new OptionChainDataBean(Constants.BANKNIFTY + Constants.SPACE +expDate);
				// bean.setWeek52High(symbolBean.getWeek52High());
				// bean.setWeek52Low(symbolBean.getWeek52Low());
				bean.setLotSize(25);
				bean.setIndexInd(true);
				bean.setExpiryDate(expDate);
				bean.setStrongOrder(conter+ 350);
				optionChainBaseDataMap.put(bean.getSymbol(), bean);

				optionChainTempDataMap.put(bean.getSymbol(), bean.clone());
			}
			if (!optionChainBaseDataMap.contains(Constants.FINNIFTY + Constants.SPACE +expDate)) {
				OptionChainDataBean bean = new OptionChainDataBean(Constants.FINNIFTY + Constants.SPACE +expDate);
				// bean.setWeek52High(symbolBean.getWeek52High());
				// bean.setWeek52Low(symbolBean.getWeek52Low());
				bean.setLotSize(40);
				bean.setIndexInd(true);
				bean.setExpiryDate(expDate);
				bean.setStrongOrder(conter+ 200);
				optionChainBaseDataMap.put(bean.getSymbol(), bean);

				optionChainTempDataMap.put(bean.getSymbol(), bean.clone());
			}
		}
	}
	public void retrieveStockAndPrepareOptionHistoryData(Boolean parentRecordInd) {
		try {
		getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData();

		for (String symbol : optionChainTempDataMap.keySet()) {
			if (!optionChainIdleDataMap.keySet().contains(symbol)) {
				OptionChainDataBean beanOI = retriveOptionChainDataForEachStock(optionChainTempDataMap.get(symbol));
				beanOI = updatePreviuousOpenInterestStyles(beanOI);
				// StockUtil.getSymbolTickerMap().put(symbol, beanOI.getTickerSize());
				// System.out.println("Option Ticker : "+symbol+" -
				// "+StockUtil.getSymbolTickerMap().get(symbol));
				beanOI.setCandleNumber(getCandleNumber());
				beanOI.setTimeFrameNumber(timeFrameNumber);
				beanOI.setParentRecordInd(parentRecordInd);
				beanOI.getOITrend();
				beanOI.handleSortOrderAndStyle();

				beanOI.setCandleNumber(getCandleNumber());
				beanOI.setTimeFrameNumber(timeFrameNumber);
				ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
						.atZone(ZoneId.of(Constants.TIME_ZONE));
				beanOI.setDateTimeStamp(Date.from(zdt.toInstant()));
				
				//
				TopGainerLooserDataBean topGainLoseBean = topGainerLooserDataMap.get(symbol);
				if (null != topGainLoseBean) {
					beanOI.setChangePercentage(topGainLoseBean.getPercentageChange()); 
					beanOI.setSortOrder(topGainLoseBean.getSortOrder());
					beanOI.setTopTenGainLooseSortOrder(topGainLoseBean.getTopTenGainLooseSortOrder());
					beanOI.setYearHigh(topGainLoseBean.getYearHigh());
					beanOI.setYearLow(topGainLoseBean.getYearLow());
				}
				//
				
				DatabaseHelper.getInstance().saveOptionChainData(beanOI);
	//sathvikAshvikTechTraderTool.sendOptionChainTradeMessage(beanOI);
				totalSymbolsProcessed++;
			} else {
				optionChainTempDataMap.put(symbol, optionChainIdleDataMap.get(symbol));
			}
		}
		// optionChainDataMap =
		// retriveOptionChainSpurtsDataForEachStock(optionChainDataMap);
//saveAllOptionChainData(new ArrayList<OptionChainDataBean>(optionChainTempDataMap.values()));
		// while (!failedResultDataMap.isEmpty()) {
		if (!failedResultDataMap.isEmpty()) {
			retryOnFaildResultDataMap();
		}
		//retriveOptionChainDataForEachIndex(parentRecordInd);
		} catch (java.util.ConcurrentModificationException e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
						Constants.METHOD_RETRIVEOPTIONCHAINDATAFOREACHSTOCK , e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
		
	}

	private List<String> failedResultDataMap = new ArrayList<String>();

	public List<String> getFailedResultDataMap() {
		return failedResultDataMap;
	}

	public void retryOnFaildResultDataMap() {/*

		if (!failedResultDataMap.isEmpty()) {
			Iterator<String> it = failedResultDataMap.listIterator();

			while (it.hasNext()) {
				String symbol = it.next();
				kiteFutureKeyMap.put(symbol, sathvikAshvikTechTraderTool.getKiteFuturekey(symbol));
				OptionChainDataBean beanOI = retriveOptionChainDataForEachStock(optionChainTempDataMap.get(symbol));
				beanOI.setCandleNumber(getCandleNumber());
				beanOI.setTimeFrameNumber(timeFrameNumber);
				beanOI.setParentRecordInd(true);
				beanOI.getOITrend();
				beanOI.handleSortOrderAndStyle();

				beanOI.setCandleNumber(getCandleNumber());
				beanOI.setTimeFrameNumber(timeFrameNumber);
				ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
						.atZone(ZoneId.of(Constants.TIME_ZONE));
				beanOI.setDateTimeStamp(Date.from(zdt.toInstant()));
				it.remove();
			}
		}
	*/}

	// optionChainInfoService
	@Autowired
	private OptionChainInfoService optionChainInfoService;

	public List<OptionChainInfoBean> saveAllOptionChainData(List<OptionChainDataBean> beans) {
		List<OptionChainInfoBean> baseList = new ArrayList<OptionChainInfoBean>(beans.size());
		;
		try {

			for (OptionChainDataBean bean : beans) {
				baseList.add(
						// databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(bean,
						// sathvikAshvikTechTraderTool));
						databaseMapper.mapToOptionChainInforBean(bean));
			}
			baseList = optionChainInfoService.saveAll(baseList);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog("NSEIndiaToolNewNseSite", "saveAllOptionChainData", e, "EXCEPTION");
		}
		return baseList;
	}

	private void retriveOptionChainDataForEachIndex(Boolean parentRecordInd) {
		optionChainDataMapIndex.clear();
		for (String index : StockUtil.getIndexOISymbols().keySet()) {
			OptionChainDataBean indexOi = new OptionChainDataBean(index);
			indexOi.setIndexInd(true);
			indexOi.setLotSize(StockUtil.getIndexOISymbols().get(index));
			indexOi = retriveOptionChainDataForEachStock(indexOi);
			indexOi = updatePreviuousOpenInterestStyles(indexOi);
			indexOi.setTickerSize(
					kiteConnectTool.getRoundupToTwoValue(indexOi.getStrikePrice2() - indexOi.getStrikePrice1()));
			indexOi.getOITrend();
			indexOi.handleSortOrderAndStyle();
			indexOi.setParentRecordInd(parentRecordInd);

			indexOi.setCandleNumber(getCandleNumber());
			indexOi.setTimeFrameNumber(timeFrameNumber);
			ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
					.atZone(ZoneId.of(Constants.TIME_ZONE));
			indexOi.setDateTimeStamp(Date.from(zdt.toInstant()));
			optionChainDataMapIndex.put(index, indexOi);
			DatabaseHelper.getInstance().saveOptionChainData(indexOi);
		}
		// saveAllOptionChainData(new
		// ArrayList<OptionChainDataBean>(optionChainDataMapIndex.values()));
	}

	public void refreshOIDataForAll(boolean clearAllData) {
		failedResultDataMap.clear();
		//clearOptionChainTempDataMap(clearAllData);
		optionChainTempDataMap.clear();
		resetCandleNumber();
		retrieveStockAndPrepareOptionHistoryData(Boolean.FALSE);
		optionChainDataMap.clear();
		optionChainDataMap.putAll(optionChainTempDataMap);
		optionChainIdleDataMap.clear();
	}
	
	private void clearOptionChainTempDataMap(boolean clearAllData) {
		optionChainIdleDataMap.clear();
		if (!clearAllData) {
			for (String symbol : optionChainTempDataMap.keySet()) {
				OptionChainDataBean beanOI = optionChainTempDataMap.get(symbol);
				if (null != beanOI && null != beanOI.getOITrend() && beanOI.getOITrend().contains(Constants.NA)) {
					optionChainIdleDataMap.put(symbol, beanOI);
				}
			}
		}
	}
	

	String INVALID_OPEN_INTREST_VAL = "-";
	int top1CallOIRowIndex = 0, top2CallOIRowIndex = 0, top3CallOIRowIndex = 0;
	int top1PutOIRowIndex = 0, top2PutOIRowIndex = 0, top3PutOIRowIndex = 0;
	double top1CallOI = 0, top2CallOI = 0, top3CallOI = 0;
	double top1PutOI = 0, top2PutOI = 0, top3PutOI = 0;

	private void clearIndexes() {
		top1CallOIRowIndex = 0;
		top2CallOIRowIndex = 0;
		top3CallOIRowIndex = 0;
		top1PutOIRowIndex = 0;
		top2PutOIRowIndex = 0;
		top3PutOIRowIndex = 0;
		top1CallOI = 0;
		top2CallOI = 0;
		top3CallOI = 0;
		top1PutOI = 0;
		top2PutOI = 0;
		top3PutOI = 0;
	}

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
			clearIndexes();

			//webClient.getPage("https://www.nseindia.com");
			HtmlPage mainPage = webClient.getPage("https://www.nseindia.com/get-quotes/equity?symbol="+ StockUtil.getSymbolForURL(bean.getSymbol()));
			//DomElement subTabEq = mainPage.getElementById("subtab-equity");
			//subTabEq.getEl
			//DomElement quoteLtp = mainPage.getElementById("quoteLtp");
			//System.out.println(bean.getSymbol()+" :   "+quoteLtp.getTextContent());
			
			String dataURL = URL + StockUtil.getSymbolForURL(bean.getSymbol());
			if (null != bean.getIndexInd() && bean.getIndexInd()) {
				//dataURL = URLINDEX + StockUtil.getSymbolForURL(bean.getSymbol().replaceAll(SPACE, EMPTY_STRING));
				retriveOptionChainDataForEachIndexOnExpiry(bean);
			} else {
				String pageResponse = webClient.getPage(dataURL).getWebResponse().getContentAsString();

				JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
				JSONObject rawJsonDataArrayFiltered = (JSONObject) rawJsonData.get(JSON_DATA_KEY_FILTERED);
				if (null != rawJsonDataArrayFiltered) {
					JSONArray rawJsonDataArray = (JSONArray) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_DATA);
					
					//if (null != bean.getEx) {
						for (int i = 0; i < rawJsonDataArray.size(); i++) {
							JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
							if (/* null == bean.getStrikePrice1() */0 == bean.getStrikePrice1()) {
								bean.setStrikePrice1(
										Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
							} else if (/* null == bean.getStrikePrice2() */0 == bean.getStrikePrice2()) {
								bean.setStrikePrice2(
										Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
								bean.setTickerSize(
										kiteConnectTool.getRoundupToTwoValue(bean.getStrikePrice2() - bean.getStrikePrice1()));
							}

							Object callData = jsonSymbolObject.get(JSON_DATA_KEY_CE);
							Object putData = jsonSymbolObject.get(JSON_DATA_KEY_PE);
							if (null != callData && null != putData) {
								String callOI = getCellValue(((JSONObject) callData).get(JSON_DATA_KEY_OPEN_INTREST));
								String putOI = getCellValue(((JSONObject) putData).get(JSON_DATA_KEY_OPEN_INTREST));
								bean.setUnderlyingPrice(Double
										.valueOf(String.valueOf(((JSONObject) callData).get(JSON_DATA_KEY_UNDERLYING_VALUE))));

								// handle CALLS Data
								if (callOI != null && !"".equals(callOI) && !INVALID_OPEN_INTREST_VAL.equals(callOI)) {
									if (0 == top1CallOIRowIndex) {
										top1CallOIRowIndex = i;
										top1CallOI = Double.valueOf(callOI);
									} else if (0 == top2CallOIRowIndex) {
										double temp2CallOI = Double.valueOf(callOI);
										if (top1CallOI > temp2CallOI) {
											top2CallOI = temp2CallOI;
											top2CallOIRowIndex = i;
										} else {
											top2CallOI = new Double(top1CallOI);
											top1CallOI = new Double(temp2CallOI);
											top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
											top1CallOIRowIndex = new Integer(i);
										}
									} else if (0 == top3CallOIRowIndex) {
										double temp3CallOI = Double.valueOf(callOI);
										if (top1CallOI > temp3CallOI) {
											if (top2CallOI > temp3CallOI) {
												top3CallOI = temp3CallOI;
												top3CallOIRowIndex = i;
											} else {
												top3CallOI = new Double(top2CallOI);
												top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
												top2CallOI = new Double(temp3CallOI);
												top2CallOIRowIndex = i;
											}
										} else {
											top3CallOI = new Double(top2CallOI);
											top2CallOI = new Double(top1CallOI);
											top1CallOI = new Double(temp3CallOI);
											top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
											top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
											top1CallOIRowIndex = new Integer(i);
										}
									} else {
										double tempCallOI = Double.valueOf(callOI);
										if (top1CallOI > tempCallOI) {
											if (top2CallOI > tempCallOI) {
												if (top3CallOI > tempCallOI) {
													// Do nothing
												} else {
													top3CallOI = new Double(tempCallOI);
													top3CallOIRowIndex = new Integer(i);
												}
											} else {
												top3CallOI = new Double(top2CallOI);
												top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
												top2CallOI = new Double(tempCallOI);
												top2CallOIRowIndex = new Integer(i);
											}
										} else {
											top3CallOI = new Double(top2CallOI);
											top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
											top2CallOI = new Double(top1CallOI);
											top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
											top1CallOI = new Double(tempCallOI);
											top1CallOIRowIndex = new Integer(i);
										}
									}

								}

								// handle PUTS Data
								if (putOI != null && !"".equals(putOI) && !INVALID_OPEN_INTREST_VAL.equals(putOI)) {
									if (0 == top1PutOIRowIndex) {
										top1PutOIRowIndex = i;
										top1PutOI = Double.valueOf(putOI);
									} else if (0 == top2PutOIRowIndex) {
										double temp2PutOI = Double.valueOf(putOI);
										if (top1PutOI > temp2PutOI) {
											top2PutOI = temp2PutOI;
											top2PutOIRowIndex = i;
										} else {
											top2PutOI = new Double(top1PutOI);
											top1PutOI = new Double(temp2PutOI);
											top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
											top1PutOIRowIndex = new Integer(i);
										}
									} else if (0 == top3PutOIRowIndex) {
										double temp3PutOI = Double.valueOf(putOI);
										if (top1PutOI > temp3PutOI) {
											if (top2PutOI > temp3PutOI) {
												top3PutOI = temp3PutOI;
												top3PutOIRowIndex = i;
											} else {
												top3PutOI = new Double(top2PutOI);
												top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
												top2PutOI = new Double(temp3PutOI);
												top2PutOIRowIndex = i;
											}
										} else {
											top3PutOI = new Double(top2PutOI);
											top2PutOI = new Double(top1PutOI);
											top1PutOI = new Double(temp3PutOI);
											top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
											top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
											top1PutOIRowIndex = new Integer(i);
										}
									} else {
										double tempPutOI = Double.valueOf(putOI);
										if (top1PutOI > tempPutOI) {
											if (top2PutOI > tempPutOI) {
												if (top3PutOI > tempPutOI) {
													// Do nothing
												} else {
													top3PutOI = new Double(tempPutOI);
													top3PutOIRowIndex = new Integer(i);
												}
											} else {
												top3PutOI = new Double(top2PutOI);
												top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
												top2PutOI = new Double(tempPutOI);
												top2PutOIRowIndex = new Integer(i);
											}
										} else {
											top3PutOI = new Double(top2PutOI);
											top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
											top2PutOI = new Double(top1PutOI);
											top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
											top1PutOI = new Double(tempPutOI);
											top1PutOIRowIndex = new Integer(i);
										}
									}
								}
							}
						}
						// updateOptionChainDataBean (bean, tableRows);
						bean.setTotalOpenInterestCall((Double.valueOf(getCellValue(String.valueOf(
								((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_CE)).get(JSON_DATA_KEY_TOTAL_OI)))))
								* bean.getLotSize());
						bean.setTotalOIVolumesCall(Double.valueOf(
								getCellValue(String.valueOf(((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_CE))
										.get(JSON_DATA_KEY_TOTAL_VOLUMES)))));

						bean.setTotalOpenInterestPut((Double.valueOf(getCellValue(String.valueOf(
								((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_PE)).get(JSON_DATA_KEY_TOTAL_OI)))))
								* bean.getLotSize());
						bean.setTotalOIVolumesPut(Double.valueOf(
								getCellValue(String.valueOf(((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_PE))
										.get(JSON_DATA_KEY_TOTAL_VOLUMES)))));
						updateOptionChainDataBeanNewSite(bean, rawJsonDataArray);
				//	}

				} else {
					// System.out.println(bean.getSymbol() + " -- Data not retrived properly....");
					failedResultDataMap.add(bean.getSymbol());
					TradewareLogger.saveInfoLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
							Constants.METHOD_RETRIVEOPTIONCHAINDATAFOREACHSTOCK,
							bean.getSymbol() + "  --  Data not retrived properly....");
					//System.out.println(dataURL);
				}
			}
			
		} catch (Exception e) {
			failedResultDataMap.add(bean.getSymbol());
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
					Constants.METHOD_RETRIVEOPTIONCHAINDATAFOREACHSTOCK + bean.getSymbol(), e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return bean;
	}

	private void updateOptionChainDataBeanNewSite(OptionChainDataBean bean, JSONArray rawJsonDataArray) {
		JSONObject callData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top1CallOIRowIndex))
				.get(JSON_DATA_KEY_CE);
		/*bean.setTop1OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST)))))
						* bean.getLotSize());
		bean.setTop1OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());*/
		bean.setTop1OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST))))));
		bean.setTop1OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST))))));
		
		bean.setTop1OIVolumesCall(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop1OINetChangeCall(getRoundupToTwoValue(
				Double.valueOf(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop1StrikePriceCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_STRIKE_PRICE)))));
		
		bean.setTop1OpenInterestCallBidVal1(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_BID_PRICE)))));
		bean.setTop1OpenInterestCallAskVal1(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_ASK_PRICE)))));
		bean.setTop1OpenInterestCallBidAskDiffVal1(kiteConnectTool
				.getRoundupToTwoValue(bean.getTop1OpenInterestCallAskVal1() - bean.getTop1OpenInterestCallBidVal1()));
		bean.setTop1OpenInterestCallBidAskAmtDiffVal1(
				kiteConnectTool.getRoundupToTwoValue(bean.getTop1OpenInterestCallBidAskDiffVal1() * bean.getLotSize()));
		bean.setTop1ImpliedVolatilityCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_IV)))));
		
		callData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top2CallOIRowIndex)).get(JSON_DATA_KEY_CE);
		/*bean.setTop2OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST)))))
						* bean.getLotSize());
		bean.setTop2OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());*/
		bean.setTop2OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST))))));
		bean.setTop2OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST))))));
		
		bean.setTop2OIVolumesCall(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop2OINetChangeCall(getRoundupToTwoValue(
				Double.valueOf(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop2StrikePriceCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_STRIKE_PRICE)))));

		bean.setTop2OpenInterestCallBidVal1(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_BID_PRICE)))));
		bean.setTop2OpenInterestCallAskVal1(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_ASK_PRICE)))));
		bean.setTop2OpenInterestCallBidAskDiffVal1(kiteConnectTool
				.getRoundupToTwoValue(bean.getTop2OpenInterestCallAskVal1() - bean.getTop2OpenInterestCallBidVal1()));
		bean.setTop2OpenInterestCallBidAskAmtDiffVal1(
				kiteConnectTool.getRoundupToTwoValue(bean.getTop2OpenInterestCallBidAskDiffVal1() * bean.getLotSize()));
		bean.setTop2ImpliedVolatilityCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_IV)))));
		
		callData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top3CallOIRowIndex)).get(JSON_DATA_KEY_CE);
		/*bean.setTop3OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST)))))
						* bean.getLotSize());
		bean.setTop3OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());*/
		bean.setTop3OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST))))));
		bean.setTop3OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST))))));
		
		bean.setTop3OIVolumesCall(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop3OINetChangeCall(getRoundupToTwoValue(
				Double.valueOf(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop3StrikePriceCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_STRIKE_PRICE)))));
		
		bean.setTop3OpenInterestCallBidVal1(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_BID_PRICE)))));
		bean.setTop3OpenInterestCallAskVal1(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_ASK_PRICE)))));
		bean.setTop3OpenInterestCallBidAskDiffVal1(kiteConnectTool
				.getRoundupToTwoValue(bean.getTop3OpenInterestCallAskVal1() - bean.getTop3OpenInterestCallBidVal1()));
		bean.setTop3OpenInterestCallBidAskAmtDiffVal1(
				kiteConnectTool.getRoundupToTwoValue(bean.getTop3OpenInterestCallBidAskDiffVal1() * bean.getLotSize()));
		bean.setTop3ImpliedVolatilityCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_IV)))));

		// Put Data
		JSONObject putData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top1PutOIRowIndex)).get(JSON_DATA_KEY_PE);
		/*bean.setTop1OpenInterestPut((new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST)))))
				* bean.getLotSize());
		bean.setTop1OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());*/
		bean.setTop1OpenInterestPut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST))))));
		bean.setTop1OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST))))));
		
		bean.setTop1OIVolumesPut(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop1OINetChangePut(getRoundupToTwoValue(
				Double.valueOf(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop1StrikePricePut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_STRIKE_PRICE)))));

		bean.setTop1OpenInterestPutBidVal1(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_BID_PRICE)))));
		bean.setTop1OpenInterestPutAskVal1(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_ASK_PRICE)))));
		bean.setTop1OpenInterestPutBidAskDiffVal1(kiteConnectTool
				.getRoundupToTwoValue(bean.getTop1OpenInterestPutAskVal1() - bean.getTop1OpenInterestPutBidVal1()));
		bean.setTop1OpenInterestPutBidAskAmtDiffVal1(
				kiteConnectTool.getRoundupToTwoValue(bean.getTop1OpenInterestPutBidAskDiffVal1() * bean.getLotSize()));
		bean.setTop1ImpliedVolatilityPut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_IV)))));
		
		putData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top2PutOIRowIndex)).get(JSON_DATA_KEY_PE);
		/*bean.setTop2OpenInterestPut((new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST)))))
				* bean.getLotSize());
		bean.setTop2OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());*/
		bean.setTop2OpenInterestPut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST))))));
		bean.setTop2OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST))))));
		bean.setTop2OIVolumesPut(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop2OINetChangePut(getRoundupToTwoValue(
				Double.valueOf(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop2StrikePricePut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_STRIKE_PRICE)))));

		bean.setTop2OpenInterestPutBidVal1(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_BID_PRICE)))));
		bean.setTop2OpenInterestPutAskVal1(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_ASK_PRICE)))));
		bean.setTop2OpenInterestPutBidAskDiffVal1(kiteConnectTool
				.getRoundupToTwoValue(bean.getTop2OpenInterestPutAskVal1() - bean.getTop2OpenInterestPutBidVal1()));
		bean.setTop2OpenInterestPutBidAskAmtDiffVal1(
				kiteConnectTool.getRoundupToTwoValue(bean.getTop2OpenInterestPutBidAskDiffVal1() * bean.getLotSize()));
		bean.setTop2ImpliedVolatilityPut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_IV)))));
		
		putData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top3PutOIRowIndex)).get(JSON_DATA_KEY_PE);
		/*bean.setTop3OpenInterestPut((new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST)))))
				* bean.getLotSize());
		bean.setTop3OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());*/
		bean.setTop3OpenInterestPut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST))))));
		bean.setTop3OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST))))));
		
		bean.setTop3OIVolumesPut(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop3OINetChangePut(getRoundupToTwoValue(
				Double.valueOf(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop3StrikePricePut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_STRIKE_PRICE)))));
		
		bean.setTop3OpenInterestPutBidVal1(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_BID_PRICE)))));
		bean.setTop3OpenInterestPutAskVal1(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_ASK_PRICE)))));
		bean.setTop3OpenInterestPutBidAskDiffVal1(kiteConnectTool
				.getRoundupToTwoValue(bean.getTop3OpenInterestPutAskVal1() - bean.getTop3OpenInterestPutBidVal1()));
		bean.setTop3OpenInterestPutBidAskAmtDiffVal1(
				kiteConnectTool.getRoundupToTwoValue(bean.getTop3OpenInterestPutBidAskDiffVal1() * bean.getLotSize()));
		bean.setTop3ImpliedVolatilityPut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_IV)))));

		bean.getOITrend();// this additional call as new nseindia site has underlying price in json data
	}

	private String getCellValue(String cellValue) {
		if (null == cellValue || cellValue.trim().length() == 0 || INVALID_OPEN_INTREST_VAL.equals(cellValue)) {
			return "0";
		} else {
			return cellValue.replaceAll(",", "");
		}
	}

	private String getCellValue(Object cellValueObj) {
		if (null == cellValueObj) {
			return "0";
		} else {
			String cellValue = String.valueOf(cellValueObj);
			if (cellValue.trim().length() == 0 || INVALID_OPEN_INTREST_VAL.equals(cellValue)) {
				return "0";
			} else {
				return cellValue.replaceAll(",", "");
			}
		}
	}

	public double getRoundupToTwoValue(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
	}
	
	
	public OptionChainDataBean retriveOptionChainDataForEachIndexOnExpiry(OptionChainDataBean bean) {// (String symbol) {
		// OptionChainDataBean bean = new OptionChainDataBean(symbol);
		try {
			clearIndexes();

			//webClient.getPage("https://www.nseindia.com");
			HtmlPage mainPage = webClient.getPage("https://www.nseindia.com/get-quotes/equity?symbol="+ StockUtil.getSymbolForURL(bean.getSymbol().replace(bean.getExpiryDate(), EMPTY_STRING).replaceAll(SPACE, EMPTY_STRING)));
			//DomElement subTabEq = mainPage.getElementById("subtab-equity");
			//subTabEq.getEl
			//DomElement quoteLtp = mainPage.getElementById("quoteLtp");
			//System.out.println(bean.getSymbol()+" :   "+quoteLtp.getTextContent());
			
			String dataURL = URL + StockUtil.getSymbolForURL(bean.getSymbol().replace(bean.getExpiryDate(), EMPTY_STRING).replaceAll(SPACE, EMPTY_STRING));
			if (null != bean.getIndexInd() && bean.getIndexInd()) {
				dataURL = URLINDEX + StockUtil.getSymbolForURL(bean.getSymbol().replace(bean.getExpiryDate(), EMPTY_STRING).replaceAll(SPACE, EMPTY_STRING));
			}
			String pageResponse = webClient.getPage(dataURL).getWebResponse().getContentAsString();

			JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
			JSONObject rawJsonDataArrayFiltered = (JSONObject) rawJsonData.get(JSON_DATA_KEY_FILTERED);
			JSONObject rawJsonDataArrayRecrds = (JSONObject) rawJsonData.get(JSON_DATA_KEY_RECORDS);
			if (null != rawJsonDataArrayRecrds) {
				JSONArray rawJsonDataArray = (JSONArray) rawJsonDataArrayRecrds.get(JSON_DATA_KEY_DATA);
				
				//if (null != bean.getEx) {
					for (int i = 0; i < rawJsonDataArray.size(); i++) {
						JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
						if (null != bean.getExpiryDate() && bean.getExpiryDate().equals(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_EXPIRY_DATE)))) {

						if (/* null == bean.getStrikePrice1() */0 == bean.getStrikePrice1()) {
							bean.setStrikePrice1(
									Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
						} else if (/* null == bean.getStrikePrice2() */0 == bean.getStrikePrice2()) {
							bean.setStrikePrice2(
									Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
							bean.setTickerSize(
									kiteConnectTool.getRoundupToTwoValue(bean.getStrikePrice2() - bean.getStrikePrice1()));
						}

						Object callData = jsonSymbolObject.get(JSON_DATA_KEY_CE);
						Object putData = jsonSymbolObject.get(JSON_DATA_KEY_PE);
						if (null != callData && null != putData) {
							String callOI = getCellValue(((JSONObject) callData).get(JSON_DATA_KEY_OPEN_INTREST));
							String putOI = getCellValue(((JSONObject) putData).get(JSON_DATA_KEY_OPEN_INTREST));
							bean.setUnderlyingPrice(Double
									.valueOf(String.valueOf(((JSONObject) callData).get(JSON_DATA_KEY_UNDERLYING_VALUE))));

							// handle CALLS Data
							if (callOI != null && !"".equals(callOI) && !INVALID_OPEN_INTREST_VAL.equals(callOI)) {
								if (0 == top1CallOIRowIndex) {
									top1CallOIRowIndex = i;
									top1CallOI = Double.valueOf(callOI);
								} else if (0 == top2CallOIRowIndex) {
									double temp2CallOI = Double.valueOf(callOI);
									if (top1CallOI > temp2CallOI) {
										top2CallOI = temp2CallOI;
										top2CallOIRowIndex = i;
									} else {
										top2CallOI = new Double(top1CallOI);
										top1CallOI = new Double(temp2CallOI);
										top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
										top1CallOIRowIndex = new Integer(i);
									}
								} else if (0 == top3CallOIRowIndex) {
									double temp3CallOI = Double.valueOf(callOI);
									if (top1CallOI > temp3CallOI) {
										if (top2CallOI > temp3CallOI) {
											top3CallOI = temp3CallOI;
											top3CallOIRowIndex = i;
										} else {
											top3CallOI = new Double(top2CallOI);
											top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
											top2CallOI = new Double(temp3CallOI);
											top2CallOIRowIndex = i;
										}
									} else {
										top3CallOI = new Double(top2CallOI);
										top2CallOI = new Double(top1CallOI);
										top1CallOI = new Double(temp3CallOI);
										top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
										top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
										top1CallOIRowIndex = new Integer(i);
									}
								} else {
									double tempCallOI = Double.valueOf(callOI);
									if (top1CallOI > tempCallOI) {
										if (top2CallOI > tempCallOI) {
											if (top3CallOI > tempCallOI) {
												// Do nothing
											} else {
												top3CallOI = new Double(tempCallOI);
												top3CallOIRowIndex = new Integer(i);
											}
										} else {
											top3CallOI = new Double(top2CallOI);
											top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
											top2CallOI = new Double(tempCallOI);
											top2CallOIRowIndex = new Integer(i);
										}
									} else {
										top3CallOI = new Double(top2CallOI);
										top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
										top2CallOI = new Double(top1CallOI);
										top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
										top1CallOI = new Double(tempCallOI);
										top1CallOIRowIndex = new Integer(i);
									}
								}

							}

							// handle PUTS Data
							if (putOI != null && !"".equals(putOI) && !INVALID_OPEN_INTREST_VAL.equals(putOI)) {
								if (0 == top1PutOIRowIndex) {
									top1PutOIRowIndex = i;
									top1PutOI = Double.valueOf(putOI);
								} else if (0 == top2PutOIRowIndex) {
									double temp2PutOI = Double.valueOf(putOI);
									if (top1PutOI > temp2PutOI) {
										top2PutOI = temp2PutOI;
										top2PutOIRowIndex = i;
									} else {
										top2PutOI = new Double(top1PutOI);
										top1PutOI = new Double(temp2PutOI);
										top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
										top1PutOIRowIndex = new Integer(i);
									}
								} else if (0 == top3PutOIRowIndex) {
									double temp3PutOI = Double.valueOf(putOI);
									if (top1PutOI > temp3PutOI) {
										if (top2PutOI > temp3PutOI) {
											top3PutOI = temp3PutOI;
											top3PutOIRowIndex = i;
										} else {
											top3PutOI = new Double(top2PutOI);
											top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
											top2PutOI = new Double(temp3PutOI);
											top2PutOIRowIndex = i;
										}
									} else {
										top3PutOI = new Double(top2PutOI);
										top2PutOI = new Double(top1PutOI);
										top1PutOI = new Double(temp3PutOI);
										top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
										top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
										top1PutOIRowIndex = new Integer(i);
									}
								} else {
									double tempPutOI = Double.valueOf(putOI);
									if (top1PutOI > tempPutOI) {
										if (top2PutOI > tempPutOI) {
											if (top3PutOI > tempPutOI) {
												// Do nothing
											} else {
												top3PutOI = new Double(tempPutOI);
												top3PutOIRowIndex = new Integer(i);
											}
										} else {
											top3PutOI = new Double(top2PutOI);
											top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
											top2PutOI = new Double(tempPutOI);
											top2PutOIRowIndex = new Integer(i);
										}
									} else {
										top3PutOI = new Double(top2PutOI);
										top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
										top2PutOI = new Double(top1PutOI);
										top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
										top1PutOI = new Double(tempPutOI);
										top1PutOIRowIndex = new Integer(i);
									}
								}
							}
						}
					}
			}
					
			//	}
					
					// updateOptionChainDataBean (bean, tableRows);
					bean.setTotalOpenInterestCall((Double.valueOf(getCellValue(String.valueOf(
							((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_CE)).get(JSON_DATA_KEY_TOTAL_OI)))))
							* bean.getLotSize());
					bean.setTotalOIVolumesCall(Double.valueOf(
							getCellValue(String.valueOf(((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_CE))
									.get(JSON_DATA_KEY_TOTAL_VOLUMES)))));

					bean.setTotalOpenInterestPut((Double.valueOf(getCellValue(String.valueOf(
							((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_PE)).get(JSON_DATA_KEY_TOTAL_OI)))))
							* bean.getLotSize());
					bean.setTotalOIVolumesPut(Double.valueOf(
							getCellValue(String.valueOf(((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_PE))
									.get(JSON_DATA_KEY_TOTAL_VOLUMES)))));
					updateOptionChainDataBeanNewSite(bean, rawJsonDataArray);

			} else {
				// System.out.println(bean.getSymbol() + " -- Data not retrived properly....");
				failedResultDataMap.add(bean.getSymbol());
				TradewareLogger.saveInfoLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
						Constants.METHOD_RETRIVEOPTIONCHAINDATAFOREACHSTOCK,
						bean.getSymbol() + "  --  Data not retrived properly....");
				//System.out.println(dataURL);
			}
		} catch (Exception e) {
			failedResultDataMap.add(bean.getSymbol());
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
					Constants.METHOD_RETRIVEOPTIONCHAINDATAFOREACHSTOCK + bean.getSymbol(), e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return bean;
	}

	private static final String JSON_DATA_KEY_FILTERED = "filtered";
	private static final String JSON_DATA_KEY_RECORDS = "records";
	private static final String JSON_DATA_KEY_DATA = "data";
	private static final String JSON_DATA_KEY_CE = "CE";
	private static final String JSON_DATA_KEY_PE = "PE";
	private static final String JSON_DATA_KEY_TOTAL_OI = "totOI";
	private static final String JSON_DATA_KEY_TOTAL_VOLUMES = "totVol";
	private static final String JSON_DATA_KEY_OPEN_INTREST = "openInterest";
	private static final String JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST = "changeinOpenInterest";
	private static final String JSON_DATA_KEY_TOTAL_TRADED_VOLUME = "totalTradedVolume";
	private static final String JSON_DATA_KEY_CHANGE_NET_CHANGE = "change";
	private static final String JSON_DATA_KEY_UNDERLYING_VALUE = "underlyingValue";
	private static final String JSON_DATA_KEY_STRIKE_PRICE = "strikePrice";
	private static final String JSON_DATA_KEY_EXPIRY_DATE = "expiryDate";
	private static final String EMPTY_STRING = "";
	private static final String SPACE = "";
	
	private static final String JSON_DATA_KEY_BID_PRICE = "bidprice";
	private static final String JSON_DATA_KEY_ASK_PRICE = "askPrice";
	private static final String JSON_DATA_KEY_IV = "impliedVolatility";

	@Autowired
	private TradewareTool sathvikAshvikTechTraderTool;
	
	@Autowired
	private NSEIndiaBankNiftyTradeToolNewNseSite bankNiftyTradeTool;
	
	private int candleNumber = 0;
	private int timeFrameNumber = 5;

	public int getCandleNumber() {
		if (0 == candleNumber) {
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			// LocalDateTime startTime =
			// LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(9, 00, 00,
			// 00));
			LocalDateTime startTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(9, 15, 00, 00));
			// LocalDateTime closedTime = startTime.plusMinutes(15);
			LocalDateTime closedTime = startTime.plusMinutes(5);
			while (sathvikAshvikTechTraderTool.isTradingTime()) {
				if (currentTime.isAfter(startTime) && currentTime.isBefore(closedTime)) {
					if (0 == currentTime.getMinute()) {
						timeFrameNumber = 60;
					} /*
						 * else if (45 == currentTime.getMinute()) { timeFrameNumber = 45; }
						 */else if (30 == currentTime.getMinute()) {
						timeFrameNumber = 30;
					} else if (15 == currentTime.getMinute()) {
						timeFrameNumber = 15;
					} else {
						timeFrameNumber = 5;
					}

					break;
				} else {
					candleNumber++;
					startTime = closedTime;
					// closedTime = closedTime.plusMinutes(15);
					closedTime = closedTime.plusMinutes(5);
				}
			}
		}
		return candleNumber;
	}

	public void resetCandleNumber() {
		this.candleNumber = 0;
	}

	public int getTimeFrameNumber() {
		return timeFrameNumber;
	}

	/*
	 * private List<Integer> minute5 = new ArrayList(); private List<Integer>
	 * minute15 = new ArrayList(); private List<Integer> minute30 = new ArrayList();
	 * private List<Integer> minute60 = new ArrayList();
	 * 
	 * minute5.add(5); minute5.add(15); minute5.add(30); minute5.add(60);
	 * 
	 * minute15.add();
	 */

	/* private */public OptionChainDataBean updatePreviuousOpenInterestStyles(OptionChainDataBean optionChainDataBean) {
		if (null != optionChainDataBean && null != optionChainDataBean.getSymbol()) {
			OptionChainDataBean baseBean = optionChainBaseDataMap.get(optionChainDataBean.getSymbol());

			if (optionChainDataBean.getPreviousTop3StrikePriceCall() != 0
					&& optionChainDataBean.getTop3StrikePriceCall() != 0) {
				if (optionChainDataBean.getTop3StrikePriceCall() > optionChainDataBean
						.getPreviousTop3StrikePriceCall()) {
					optionChainDataBean.setTop3OpenInterestCallStyle(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
					optionChainDataBean.setAttentionStyleBuy(Constants.ATTENTION_STYLE_BUY);
					optionChainDataBean
							.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.SELL, Constants.BUY));
				} else if (optionChainDataBean.getTop3StrikePriceCall() < optionChainDataBean
						.getPreviousTop3StrikePriceCall()) {
					optionChainDataBean.setTop3OpenInterestCallStyle(Constants.RED_BOLD_FONT_STYLE_CLASS);
					optionChainDataBean.setAttentionStyleBuy(Constants.ATTENTION_STYLE_SELL);
					optionChainDataBean
							.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.BUY, Constants.SELL));
				} else {
					// optionChainDataBean.setTop3OpenInterestCallStyle(Constants.EMPTY_STRING);
				}
			}
			if (optionChainDataBean.getPreviousTop2StrikePriceCall() != 0
					&& optionChainDataBean.getTop2StrikePriceCall() != 0) {
				if (optionChainDataBean.getTop2StrikePriceCall() > optionChainDataBean
						.getPreviousTop2StrikePriceCall()) {
					optionChainDataBean.setTop2OpenInterestCallStyle(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
					optionChainDataBean.setAttentionStyleBuy(Constants.ATTENTION_STYLE_BUY);
					optionChainDataBean
							.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.SELL, Constants.BUY));
				} else if (optionChainDataBean.getTop2StrikePriceCall() < optionChainDataBean
						.getPreviousTop2StrikePriceCall()) {
					optionChainDataBean.setTop2OpenInterestCallStyle(Constants.RED_BOLD_FONT_STYLE_CLASS);
					optionChainDataBean.setAttentionStyleBuy(Constants.ATTENTION_STYLE_SELL);
					optionChainDataBean
							.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.BUY, Constants.SELL));
				} else {
					// optionChainDataBean.setTop2OpenInterestCallStyle(Constants.EMPTY_STRING);
				}
			}
			if (null != optionChainDataBean && null != baseBean) {
				if (optionChainDataBean.getPreviousTop1StrikePriceCall() != 0
						&& optionChainDataBean.getTop1StrikePriceCall() != 0) {
					if (optionChainDataBean.getTop1StrikePriceCall() > optionChainDataBean
							.getPreviousTop1StrikePriceCall()) {
						optionChainDataBean.setTop1OpenInterestCallStyle(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleBuy(Constants.ATTENTION_STYLE_BUY);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.SELL, Constants.BUY));
					} else if (optionChainDataBean.getTop1StrikePriceCall() < optionChainDataBean
							.getPreviousTop1StrikePriceCall()) {
						optionChainDataBean.setTop1OpenInterestCallStyle(Constants.RED_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleBuy(Constants.ATTENTION_STYLE_SELL);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.BUY, Constants.SELL));
					} else {
						// optionChainDataBean.setTop1OpenInterestCallStyle(Constants.EMPTY_STRING);
					}
				}

				if (optionChainDataBean.getPreviousTop3StrikePricePut() != 0
						&& optionChainDataBean.getTop3StrikePricePut() != 0) {
					if (optionChainDataBean.getTop3StrikePricePut() > optionChainDataBean
							.getPreviousTop3StrikePricePut()) {
						optionChainDataBean.setTop3OpenInterestPutStyle(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleSell(Constants.ATTENTION_STYLE_BUY);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.SELL, Constants.BUY));
					} else if (optionChainDataBean.getTop3StrikePricePut() < optionChainDataBean
							.getPreviousTop3StrikePricePut()) {
						optionChainDataBean.setTop3OpenInterestPutStyle(Constants.RED_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleSell(Constants.ATTENTION_STYLE_SELL);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.BUY, Constants.SELL));
					} else {
						// optionChainDataBean.setTop3OpenInterestPutStyle(Constants.EMPTY_STRING);
					}
				}
				if (optionChainDataBean.getPreviousTop2StrikePricePut() != 0
						&& optionChainDataBean.getTop2StrikePricePut() != 0) {
					if (optionChainDataBean.getTop2StrikePricePut() > optionChainDataBean
							.getPreviousTop2StrikePricePut()) {
						optionChainDataBean.setTop2OpenInterestPutStyle(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleSell(Constants.ATTENTION_STYLE_BUY);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.SELL, Constants.BUY));
					} else if (optionChainDataBean.getTop2StrikePricePut() < optionChainDataBean
							.getPreviousTop2StrikePricePut()) {
						optionChainDataBean.setTop2OpenInterestPutStyle(Constants.RED_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleSell(Constants.ATTENTION_STYLE_SELL);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.BUY, Constants.SELL));
					} else {
						// optionChainDataBean.setTop2OpenInterestPutStyle(Constants.EMPTY_STRING);
					}
				}
				if (optionChainDataBean.getPreviousTop1StrikePricePut() != 0
						&& optionChainDataBean.getTop1StrikePricePut() != 0) {
					if (optionChainDataBean.getTop1StrikePricePut() > optionChainDataBean
							.getPreviousTop1StrikePricePut()) {
						optionChainDataBean.setTop1OpenInterestPutStyle(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleSell(Constants.ATTENTION_STYLE_BUY);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.SELL, Constants.BUY));
					} else if (optionChainDataBean.getTop1StrikePricePut() < optionChainDataBean
							.getPreviousTop1StrikePricePut()) {
						optionChainDataBean.setTop1OpenInterestPutStyle(Constants.RED_BOLD_FONT_STYLE_CLASS);
						optionChainDataBean.setAttentionStyleSell(Constants.ATTENTION_STYLE_SELL);
						optionChainDataBean
								.setOITrend(optionChainDataBean.getOITrend().replaceAll(Constants.BUY, Constants.SELL));
					} else {
						optionChainDataBean.setTop1OpenInterestPutStyle(Constants.EMPTY_STRING);
					}
				}

				if (null != baseBean) {
					baseBean.setPreviousTop1StrikePriceCall(optionChainDataBean.getTop1StrikePriceCall());
					baseBean.setPreviousTop2StrikePriceCall(optionChainDataBean.getTop2StrikePriceCall());
					baseBean.setPreviousTop3StrikePriceCall(optionChainDataBean.getTop3StrikePriceCall());
					baseBean.setPreviousTop1StrikePricePut(optionChainDataBean.getTop1StrikePricePut());
					baseBean.setPreviousTop2StrikePricePut(optionChainDataBean.getTop2StrikePricePut());
					baseBean.setPreviousTop3StrikePricePut(optionChainDataBean.getTop3StrikePricePut());
					baseBean.setTop1OpenInterestCallStyle(Constants.EMPTY_STRING);
					baseBean.setTop2OpenInterestCallStyle(Constants.EMPTY_STRING);
					baseBean.setTop3OpenInterestCallStyle(Constants.EMPTY_STRING);
					baseBean.setTop1OpenInterestPutStyle(Constants.EMPTY_STRING);
					baseBean.setTop2OpenInterestPutStyle(Constants.EMPTY_STRING);
					baseBean.setTop3OpenInterestPutStyle(Constants.EMPTY_STRING);
					baseBean.setAttentionStyleBuy(Constants.EMPTY_STRING);
					baseBean.setAttentionStyleSell(Constants.EMPTY_STRING);
				}

				optionChainBaseDataMap.replace(optionChainDataBean.getSymbol(), baseBean);
			}
		}
		optionChainDataBean = updateOiNetChangeTotals(optionChainDataBean);
		return optionChainDataBean;
	}

	private OptionChainDataBean updateOiNetChangeTotals(OptionChainDataBean bean) {
			//TODO123:
		bean.setTotalOpenInterestChangeCall(kiteConnectTool.getRoundupToTwoValue(
				bean.getTop1OpenInterestChangeCall() + bean.getTop2OpenInterestChangeCall() + bean.getTop3OpenInterestChangeCall()));
		bean.setTotalOpenInterestChangePut(kiteConnectTool.getRoundupToTwoValue(
				(bean.getTop1OpenInterestChangePut() + bean.getTop2OpenInterestChangePut() + bean.getTop3OpenInterestChangePut())));
		
		bean.setTotalOINetChangeCall(kiteConnectTool.getRoundupToTwoValue(
				bean.getTop1OINetChangeCall() + bean.getTop2OINetChangeCall() + bean.getTop3OINetChangeCall()));
		bean.setTotalOINetChangePut(kiteConnectTool.getRoundupToTwoValue(
				(bean.getTop1OINetChangePut() + bean.getTop2OINetChangePut() + bean.getTop3OINetChangePut())));

		bean.setTotalOpenInterestChangeCallPrevious5(bean.getTotalOpenInterestChangeCallPrevious4());
		bean.setTotalOpenInterestChangePutPrevious5(bean.getTotalOpenInterestChangePutPrevious4());
		bean.setTotalOpenInterestChangeCallPrevious4(bean.getTotalOpenInterestChangeCallPrevious3());
		bean.setTotalOpenInterestChangePutPrevious4(bean.getTotalOpenInterestChangePutPrevious3());
		bean.setTotalOpenInterestChangeCallPrevious3(bean.getTotalOpenInterestChangeCallPrevious2());
		bean.setTotalOpenInterestChangePutPrevious3(bean.getTotalOpenInterestChangePutPrevious2());
		bean.setTotalOpenInterestChangeCallPrevious2(bean.getTotalOpenInterestChangeCallPrevious1());
		bean.setTotalOpenInterestChangePutPrevious2(bean.getTotalOpenInterestChangePutPrevious1());
		bean.setTotalOpenInterestChangeCallPrevious1(bean.getTotalOpenInterestChangeCall());
		bean.setTotalOpenInterestChangePutPrevious1(bean.getTotalOpenInterestChangePut());
		
		bean.setTotalOINetChangeCallPrevious5(bean.getTotalOINetChangeCallPrevious4());
		bean.setTotalOINetChangePutPrevious5(bean.getTotalOINetChangePutPrevious4());
		bean.setTotalOINetChangeCallPrevious4(bean.getTotalOINetChangeCallPrevious3());
		bean.setTotalOINetChangePutPrevious4(bean.getTotalOINetChangePutPrevious3());
		bean.setTotalOINetChangeCallPrevious3(bean.getTotalOINetChangeCallPrevious2());
		bean.setTotalOINetChangePutPrevious3(bean.getTotalOINetChangePutPrevious2());
		bean.setTotalOINetChangeCallPrevious2(bean.getTotalOINetChangeCallPrevious1());
		bean.setTotalOINetChangePutPrevious2(bean.getTotalOINetChangePutPrevious1());
		bean.setTotalOINetChangeCallPrevious1(bean.getTotalOINetChangeCall());
		bean.setTotalOINetChangePutPrevious1(bean.getTotalOINetChangePut());

		bean.setUnderlyingPricePrevious5(bean.getUnderlyingPricePrevious4());
		bean.setUnderlyingPricePrevious4(bean.getUnderlyingPricePrevious3());
		bean.setUnderlyingPricePrevious3(bean.getUnderlyingPricePrevious2());
		bean.setUnderlyingPricePrevious2(bean.getUnderlyingPricePrevious1());
		bean.setUnderlyingPricePrevious1(bean.getUnderlyingPrice());

		if (bean.getUnderlyingPrice() > bean.getTop1StrikePriceCall()) {
			// oITrend = STRONG_SELL;
		} else if (bean.getUnderlyingPrice() > bean.getTop2StrikePriceCall()) {
			// oITrend = CAN_SELL;
		} else if (bean.getUnderlyingPrice() > bean.getTop3StrikePriceCall()) {
			// oITrend = SELL;
		} else if (bean.getUnderlyingPrice() < bean.getTop1StrikePricePut()) {
			// oITrend = STRONG_BUY;
		} else if (bean.getUnderlyingPrice() < bean.getTop2StrikePricePut()) {
			// oITrend = CAN_BUY;
		} else if (bean.getUnderlyingPrice() < bean.getTop3StrikePricePut()) {
			// oITrend = BUY;
		}
		{
			/*if (((bean.getTotalOINetChangeCallPrevious1() > bean.getTotalOINetChangeCallPrevious2())
					&& (bean.getTotalOINetChangeCallPrevious2() > bean.getTotalOINetChangeCallPrevious3()))) {
				bean.setPriceMoveTrend(Constants.BUY);
				if ((bean.getTotalOINetChangePutPrevious1() < bean.getTotalOINetChangePutPrevious2())
						&& (bean.getTotalOINetChangePutPrevious2() < bean.getTotalOINetChangePutPrevious3())) {
					bean.setPriceMoveTrend(Constants.STRONG_BUY);
				}
			}

			if ((bean.getTotalOINetChangePutPrevious1() > bean.getTotalOINetChangePutPrevious2())
					&& (bean.getTotalOINetChangePutPrevious2() > bean.getTotalOINetChangePutPrevious3())) {
				bean.setPriceMoveTrend(Constants.SELL);
				if (((bean.getTotalOINetChangeCallPrevious1() < bean.getTotalOINetChangeCallPrevious2())
						&& (bean.getTotalOINetChangeCallPrevious2() < bean.getTotalOINetChangeCallPrevious3()))) {
					bean.setPriceMoveTrend(Constants.STRONG_SELL);
				}
			}*/
		}

		if (((bean.getTotalOINetChangeCallPrevious1() > bean.getTotalOINetChangeCallPrevious2())
				&& (bean.getTotalOINetChangeCallPrevious2() > bean.getTotalOINetChangeCallPrevious3()))) {
			// bean.setPriceMoveTrend(Constants.BUY);
			callPriceTrend = Constants.BUY;
			if ((bean.getTotalOINetChangePutPrevious1() < bean.getTotalOINetChangePutPrevious2())
					&& (bean.getTotalOINetChangePutPrevious2() < bean.getTotalOINetChangePutPrevious3())) {
				// bean.setPriceMoveTrend(Constants.STRONG_BUY);
				callPriceTrend = Constants.STRONG_BUY;
			}
			if (null != callPriceTrend && 0 != bean.getUnderlyingPricePrevious3()) {
				if ((bean.getUnderlyingPricePrevious3() <= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() <= bean.getUnderlyingPricePrevious1())) {
					callPriceTrend = callPriceTrend + Constants.SPACE + Constants.NEW_LONG_UP_TREND;
				} else if ((bean.getUnderlyingPricePrevious3() >= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() >= bean.getUnderlyingPricePrevious1())) {
					callPriceTrend = callPriceTrend + Constants.SPACE + Constants.NEW_SHORT_DOWN_TREND;
				}
			}
		}

		if ((bean.getTotalOINetChangePutPrevious1() > bean.getTotalOINetChangePutPrevious2())
				&& (bean.getTotalOINetChangePutPrevious2() > bean.getTotalOINetChangePutPrevious3())) {
			// bean.setPriceMoveTrend(Constants.SELL);
			putPriceTrend = Constants.SELL;
			if (((bean.getTotalOINetChangeCallPrevious1() < bean.getTotalOINetChangeCallPrevious2())
					&& (bean.getTotalOINetChangeCallPrevious2() < bean.getTotalOINetChangeCallPrevious3()))) {
				// bean.setPriceMoveTrend(Constants.STRONG_SELL);
				putPriceTrend = Constants.STRONG_SELL;
			}
			if (null != putPriceTrend && 0 != bean.getUnderlyingPricePrevious3()) {
				if ((bean.getUnderlyingPricePrevious3() <= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() <= bean.getUnderlyingPricePrevious1())) {
					putPriceTrend = putPriceTrend + Constants.SPACE + Constants.SHORT_COVERING;
				} else if ((bean.getUnderlyingPricePrevious3() >= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() >= bean.getUnderlyingPricePrevious1())) {
					putPriceTrend = putPriceTrend + Constants.SPACE + Constants.UNWIND_DOWN_TREND;
				}
			}

		}

		if (null != callPriceTrend && null != putPriceTrend) {
			bean.setPriceMoveTrend(callPriceTrend + Constants.SPACE + putPriceTrend);
		} else if (null != callPriceTrend) {
			bean.setPriceMoveTrend(callPriceTrend);
		} else if (null != putPriceTrend) {
			bean.setPriceMoveTrend(putPriceTrend);
		}
		callPriceTrend = null;
		putPriceTrend = null;

		if (bean.getPriceMoveTrend() == null && bean.getAttentionStyleBuy() != null
				&& bean.getAttentionStyleSell() != null) {
			if ((bean.getTotalOINetChangeCallPrevious1() > bean.getTotalOINetChangeCallPrevious2())
					&& (bean.getTotalOINetChangePutPrevious1() < bean.getTotalOINetChangePutPrevious2())) {
				bean.setPriceMoveTrend("<span style='color:blue;font-weight: bold'>BUY</span>");
			} else if ((bean.getTotalOINetChangeCallPrevious1() < bean.getTotalOINetChangeCallPrevious2())
					&& (bean.getTotalOINetChangePutPrevious1() > bean.getTotalOINetChangePutPrevious2())) {
				bean.setPriceMoveTrend("<span style='color:blue;font-weight: bold'>SELL</span>");
			} else {
				bean.setPriceMoveTrend("<span style='color:blue;font-weight: bold'>WAIT</span>");
			}
		}

		if (bean.getTotalOINetChangeCallPrevious1() > bean.getTotalOINetChangeCallPrevious2()) {
			bean.setTotalOINetChangeCallStyle(" style='color:green; font-weight: bold;'");
		} else if (bean.getTotalOINetChangeCallPrevious1() < bean.getTotalOINetChangeCallPrevious2()) {
			bean.setTotalOINetChangeCallStyle(" style='color:red; font-weight: bold;'");
		}

		if (bean.getTotalOINetChangePutPrevious1() < bean.getTotalOINetChangePutPrevious2()) {
			bean.setTotalOINetChangePutStyle(" style='color:green; font-weight: bold;'");
		} else if (bean.getTotalOINetChangePutPrevious1() > bean.getTotalOINetChangePutPrevious2()) {
			bean.setTotalOINetChangePutStyle(" style='color:red; font-weight: bold;'");
		}
		
		//TODO123 start
		if (((bean.getTotalOpenInterestChangeCallPrevious1() > bean.getTotalOpenInterestChangeCallPrevious2())
				&& (bean.getTotalOpenInterestChangeCallPrevious2() > bean.getTotalOpenInterestChangeCallPrevious3()))) {
			// bean.setPriceMoveTrend(Constants.BUY);
			callPriceOpenIntrestChangeTrend = Constants.BUY;
			if ((bean.getTotalOpenInterestChangePutPrevious1() < bean.getTotalOpenInterestChangePutPrevious2())
					&& (bean.getTotalOpenInterestChangePutPrevious2() < bean.getTotalOpenInterestChangePutPrevious3())) {
				// bean.setPriceMoveTrend(Constants.STRONG_BUY);
				callPriceOpenIntrestChangeTrend = Constants.STRONG_BUY;
			}
			if (null != callPriceOpenIntrestChangeTrend && 0 != bean.getUnderlyingPricePrevious3()) {
				if ((bean.getUnderlyingPricePrevious3() <= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() <= bean.getUnderlyingPricePrevious1())) {
					callPriceOpenIntrestChangeTrend = callPriceOpenIntrestChangeTrend + Constants.SPACE + Constants.NEW_LONG_UP_TREND;
				} else if ((bean.getUnderlyingPricePrevious3() >= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() >= bean.getUnderlyingPricePrevious1())) {
					callPriceOpenIntrestChangeTrend = callPriceOpenIntrestChangeTrend + Constants.SPACE + Constants.NEW_SHORT_DOWN_TREND;
				}
			}
		}

		if ((bean.getTotalOpenInterestChangePutPrevious1() > bean.getTotalOpenInterestChangePutPrevious2())
				&& (bean.getTotalOpenInterestChangePutPrevious2() > bean.getTotalOpenInterestChangePutPrevious3())) {
			// bean.setPriceMoveTrend(Constants.SELL);
			putPriceOpenIntrestChangeTrend = Constants.SELL;
			if (((bean.getTotalOpenInterestChangeCallPrevious1() < bean.getTotalOpenInterestChangeCallPrevious2())
					&& (bean.getTotalOpenInterestChangeCallPrevious2() < bean.getTotalOpenInterestChangeCallPrevious3()))) {
				// bean.setPriceMoveTrend(Constants.STRONG_SELL);
				putPriceOpenIntrestChangeTrend = Constants.STRONG_SELL;
			}
			if (null != putPriceOpenIntrestChangeTrend && 0 != bean.getUnderlyingPricePrevious3()) {
				if ((bean.getUnderlyingPricePrevious3() <= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() <= bean.getUnderlyingPricePrevious1())) {
					putPriceOpenIntrestChangeTrend = putPriceOpenIntrestChangeTrend + Constants.SPACE + Constants.SHORT_COVERING;
				} else if ((bean.getUnderlyingPricePrevious3() >= bean.getUnderlyingPricePrevious2())
						&& (bean.getUnderlyingPricePrevious2() >= bean.getUnderlyingPricePrevious1())) {
					putPriceOpenIntrestChangeTrend = putPriceOpenIntrestChangeTrend + Constants.SPACE + Constants.UNWIND_DOWN_TREND;
				}
			}

		}

		if (null != callPriceOpenIntrestChangeTrend && null != putPriceOpenIntrestChangeTrend) {
			bean.setPriceMoveTrend(callPriceOpenIntrestChangeTrend + Constants.SPACE + putPriceOpenIntrestChangeTrend);
		} else if (null != callPriceOpenIntrestChangeTrend) {
			bean.setPriceMoveTrend(callPriceOpenIntrestChangeTrend);
		} else if (null != putPriceOpenIntrestChangeTrend) {
			bean.setPriceMoveTrend(putPriceOpenIntrestChangeTrend);
		}
		callPriceOpenIntrestChangeTrend = null;
		putPriceOpenIntrestChangeTrend = null;

		if (bean.getPriceMoveTrend() == null && bean.getAttentionStyleBuy() != null
				&& bean.getAttentionStyleSell() != null) {
			if ((bean.getTotalOpenInterestChangeCallPrevious1() > bean.getTotalOpenInterestChangeCallPrevious2())
					&& (bean.getTotalOpenInterestChangePutPrevious1() < bean.getTotalOpenInterestChangePutPrevious2())) {
				bean.setPriceMoveTrend("<span style='color:blue;font-weight: bold'>BUY</span>");
			} else if ((bean.getTotalOpenInterestChangeCallPrevious1() < bean.getTotalOpenInterestChangeCallPrevious2())
					&& (bean.getTotalOpenInterestChangePutPrevious1() > bean.getTotalOpenInterestChangePutPrevious2())) {
				bean.setPriceMoveTrend("<span style='color:blue;font-weight: bold'>SELL</span>");
			} else {
				bean.setPriceMoveTrend("<span style='color:blue;font-weight: bold'>WAIT</span>");
			}
		}

		if (bean.getTotalOpenInterestChangeCallPrevious1() > bean.getTotalOpenInterestChangeCallPrevious2()) {
			bean.setTotalOpenInterestChangeCallStyle(" style='color:green; font-weight: bold;'");
		} else if (bean.getTotalOpenInterestChangeCallPrevious1() < bean.getTotalOpenInterestChangeCallPrevious2()) {
			bean.setTotalOpenInterestChangeCallStyle(" style='color:red; font-weight: bold;'");
		}

		if (bean.getTotalOpenInterestChangePutPrevious1() < bean.getTotalOpenInterestChangePutPrevious2()) {
			bean.setTotalOpenInterestChangePutStyle(" style='color:green; font-weight: bold;'");
		} else if (bean.getTotalOpenInterestChangePutPrevious1() > bean.getTotalOpenInterestChangePutPrevious2()) {
			bean.setTotalOpenInterestChangePutStyle(" style='color:red; font-weight: bold;'");
		}
		
		//TODO123 end

		//
		OptionChainDataBean baseBean = optionChainBaseDataMap.get(bean.getSymbol());
		if (null != baseBean) {
			
if (bean.getSymbol().equals("NIFTY") || bean.getSymbol().equals("BANKNIFTY")
					|| bean.getSymbol().equals("FINNIFTY")) {
						//System.out.println(bean.getSymbol());
					}
			
			baseBean.setTotalOpenInterestChangeCallPrevious5(bean.getTotalOpenInterestChangeCallPrevious5());
			baseBean.setTotalOpenInterestChangePutPrevious5(bean.getTotalOpenInterestChangePutPrevious5());
			baseBean.setTotalOpenInterestChangeCallPrevious4(bean.getTotalOpenInterestChangeCallPrevious4());
			baseBean.setTotalOpenInterestChangePutPrevious4(bean.getTotalOpenInterestChangePutPrevious4());
			baseBean.setTotalOpenInterestChangeCallPrevious3(bean.getTotalOpenInterestChangeCallPrevious3());
			baseBean.setTotalOpenInterestChangePutPrevious3(bean.getTotalOpenInterestChangePutPrevious3());
			baseBean.setTotalOpenInterestChangeCallPrevious2(bean.getTotalOpenInterestChangeCallPrevious2());
			baseBean.setTotalOpenInterestChangePutPrevious2(bean.getTotalOpenInterestChangePutPrevious2());
			baseBean.setTotalOpenInterestChangeCallPrevious1(bean.getTotalOpenInterestChangeCallPrevious1());
			baseBean.setTotalOpenInterestChangePutPrevious1(bean.getTotalOpenInterestChangePutPrevious1());

			baseBean.setTotalOINetChangeCallPrevious5(bean.getTotalOINetChangeCallPrevious5());
			baseBean.setTotalOINetChangePutPrevious5(bean.getTotalOINetChangePutPrevious5());
			baseBean.setTotalOINetChangeCallPrevious4(bean.getTotalOINetChangeCallPrevious4());
			baseBean.setTotalOINetChangePutPrevious4(bean.getTotalOINetChangePutPrevious4());
			baseBean.setTotalOINetChangeCallPrevious3(bean.getTotalOINetChangeCallPrevious3());
			baseBean.setTotalOINetChangePutPrevious3(bean.getTotalOINetChangePutPrevious3());
			baseBean.setTotalOINetChangeCallPrevious2(bean.getTotalOINetChangeCallPrevious2());
			baseBean.setTotalOINetChangePutPrevious2(bean.getTotalOINetChangePutPrevious2());
			baseBean.setTotalOINetChangeCallPrevious1(bean.getTotalOINetChangeCallPrevious1());
			baseBean.setTotalOINetChangePutPrevious1(bean.getTotalOINetChangePutPrevious1());

			baseBean.setUnderlyingPricePrevious5(bean.getUnderlyingPricePrevious5());
			baseBean.setUnderlyingPricePrevious4(bean.getUnderlyingPricePrevious4());
			baseBean.setUnderlyingPricePrevious3(bean.getUnderlyingPricePrevious3());
			baseBean.setUnderlyingPricePrevious2(bean.getUnderlyingPricePrevious2());
			baseBean.setUnderlyingPricePrevious1(bean.getUnderlyingPricePrevious1());

			optionChainBaseDataMap.replace(baseBean.getSymbol(), baseBean);
		} else {
			TradewareLogger.saveInfoLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
					Constants.METHOD_RETRIEVESYMBOLBEANLIST, "BASE BEAN NULL FOR - " + bean.getSymbol());
		}
		//
		/*if (null != bean.getPriceMoveTrend() && (bean.getPriceMoveTrend().contains(Constants.NEW_LONG_UP_TREND)
				|| bean.getPriceMoveTrend().contains(Constants.NEW_SHORT_DOWN_TREND)
				|| bean.getPriceMoveTrend().contains(Constants.SHORT_COVERING)
				|| bean.getPriceMoveTrend().contains(Constants.UNWIND_DOWN_TREND))) {
			System.out.println("Price move trend - " + bean.getSymbol() + " : " + bean.getPriceMoveTrend() + " : "
					+ bean.getOITrend());
		}*/
		return bean;
	}

	private String callPriceTrend = null, putPriceTrend = null;
	private String callPriceOpenIntrestChangeTrend = null, putPriceOpenIntrestChangeTrend = null;

	public void applyFreshRulesAndRestoreData() {
		getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData();
		
		OptionChainDataBean basebean = optionChainTempDataMap.get("PVR");
		
		 List<OptionChainDataBean>  list = DatabaseHelper.getInstance().findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(basebean.getSymbol());
		// for (OptionChainDataBean beanOI : list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			OptionChainDataBean beanOI = list.get(i);
			beanOI.getOITrend();
			beanOI.handleSortOrderAndStyle();
			beanOI = updatePreviuousOpenInterestStyles(beanOI);
			DatabaseHelper.getInstance().saveOptionChainData(beanOI);
		}
}
	
	private Hashtable<String, TopGainerLooserDataBean> topGainerLooserDataMap = new Hashtable<String, TopGainerLooserDataBean>();
	private static final String JSON_DATA_KEY_priority = "priority";
	private static final String JSON_DATA_KEY_SYMBOL = "symbol";
	private static final String JSON_DATA_KEY_INDEX_IDENTIFIER = "identifier";
	private static final String JSON_DATA_KEY_OPEN = "open";
	private static final String JSON_DATA_KEY_DAYHIGH = "dayHigh";
	private static final String JSON_DATA_KEY_DAYLOW = "dayLow";
	private static final String JSON_DATA_KEY_LASTPRICE = "lastPrice";
	private static final String JSON_DATA_KEY_PREVIOUSCLOSE = "previousClose";
	private static final String JSON_DATA_KEY_CHANGE = "change";
	private static final String JSON_DATA_KEY_PCHANGE = "pChange";
	private static final String JSON_DATA_KEY_FFMC = "ffmc";
	private static final String JSON_DATA_KEY_YEARHIGH = "yearHigh";
	private static final String JSON_DATA_KEY_YEARLOW = "yearLow";
	private static final String JSON_DATA_KEY_TOTALTRADEDVOLUME = "totalTradedVolume";
	private static final String JSON_DATA_KEY_TOTALTRADEDVALUE = "totalTradedValue";
	private static final String JSON_DATA_KEY_LASTUPDATETIME = "lastUpdateTime";
	private static final String JSON_DATA_KEY_NEARWKH = "nearWKH";
	private static final String JSON_DATA_KEY_NEARWKL = "nearWKL";
	private static final String JSON_DATA_KEY_PERCHANGE365D = "perChange365d";
	private static final String JSON_DATA_KEY_DATE365DAGO = "date365dAgo";
	private static final String JSON_DATA_KEY_CHART365DPATH = "chart365dPath";
	private static final String JSON_DATA_KEY_DATE30DAGO = "date30dAgo";
	private static final String JSON_DATA_KEY_PERCHANGE30D = "perChange30d";
	private static final String JSON_DATA_KEY_CHART30DPATH = "chart30dPath";
	private static final String JSON_DATA_KEY_CHARTTODAYPATH = "chartTodayPat";
	
	private static final String JSON_DATA_KEY_ADVANCE = "advance";
	private static final String JSON_DATA_KEY_DECLINES = "declines";
	private static final String JSON_DATA_KEY_ADVANCES = "advances";
	private static final String JSON_DATA_KEY_UNCHANGED = "unchanged";
	private static final String JSON_DATA_KEY_TIMESTAMP = "timestamp";
	
	private static String indexIdentifier;

	public void retrieveTopGainerLooserData(String dataURL) {
		topGainerLooserDataMap.clear();
		try {
			webClient.getPage("https://www.nseindia.com");
		dataURL = "https://www.nseindia.com/api/equity-stockIndices?index=NIFTY%20200";
		String pageResponse = webClient.getPage(dataURL).getWebResponse().getContentAsString();

		JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
		if (null != rawJsonData) {
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
			if (rawJsonDataArray.size() > 0) {
				indexIdentifier = String
						.valueOf(((JSONObject) rawJsonDataArray.get(0)).get(JSON_DATA_KEY_INDEX_IDENTIFIER));
				for (int i = 0; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					TopGainerLooserDataBean bean = new TopGainerLooserDataBean(
							String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL)));
					bean.setIndexSymbol(indexIdentifier);
					// bean.setSymbol(symbol);
					bean.setOpen(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_OPEN))));
					bean.setHigh(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_DAYHIGH))));
					bean.setLow(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_DAYLOW))));
					;
					bean.setPreviousClose(
							Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_PREVIOUSCLOSE))));
					bean.setLastPrice(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_LASTPRICE))));
					bean.setChange(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_CHANGE))));
					bean.setPercentageChange(
							Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_PCHANGE))));
					bean.setYearHigh(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_YEARHIGH))));
					bean.setYearLow(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_YEARLOW))));
					bean.setVolumes(
							Long.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_TOTALTRADEDVOLUME))));// Shares
					bean.setValueInLakhs(
							BigDecimal.valueOf(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_TOTALTRADEDVALUE)))));

					bean.setSortOrder(i);
					double pchange = Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_PCHANGE)));
					if (pchange > 0) {
						bean.setTopTenGainLooseSortOrder(i);
					} else {
						bean.setTopTenGainLooseSortOrder(i - rawJsonDataArray.size());
					}

					// bean.setDateTimeStamp(dateTimeStamp);
					// bean.setDateStamp(dateStamp);
					// kiteConnectTool.getRoundupToTwoValue(

					topGainerLooserDataMap.put(bean.getSymbol(), bean);
				}

			}
		}
		} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
						Constants.METHOD_RETRIEVETOPGAINERLOOSERDATA/* + bean.getSymbol()*/, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
	}
	}
