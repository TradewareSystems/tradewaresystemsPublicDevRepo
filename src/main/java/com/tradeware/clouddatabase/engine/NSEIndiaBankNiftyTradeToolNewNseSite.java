package com.tradeware.clouddatabase.engine;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.tradeware.clouddatabase.bean.OptionTradeLiveDataBean;
import com.tradeware.clouddatabase.service.SymbolService;
import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaBankNiftyTradeToolNewNseSite {

	private static WebClient webClient = null;

	@Autowired
	private SymbolService symbolService;

	@Autowired
	private KiteConnectTool kiteConnectTool;

	@Autowired
	private TradewareTool tradewareTool;

	/*
	 * private List<String> tradeExpiryDatesList = new ArrayList<String>(); private
	 * List<Integer> tradeStrikePricesList = new ArrayList<Integer>();
	 */

	public NSEIndiaBankNiftyTradeToolNewNseSite() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setTimeout(20000);
	}

	private static final String BASE_URL = "https://www.nseindia.com";
	private static final String BASE_URL_MRKT = "https://www.nseindia.com/api/marketStatus";
	// private static final String OPTION_TRADE_URL =
	// "https://www.nseindia.com/api/quote-derivative?symbol=BANKNIFTY";
	private static final String OPTION_TRADE_URL = "https://www.nseindia.com/api/quote-derivative?symbol=";

	private static final String JSON_DATA_KEY_STOCKS = "stocks";
	private static final String JSON_DATA_KEY_METADATA = "metadata";
	private static final String JSON_DATA_KEY_MARKET_DEPT_ORDER_BOOK = "marketDeptOrderBook";
	
	private static final String JSON_DATA_KEY_BID = "bid";
	private static final String JSON_DATA_KEY_ASK = "ask";
	private static final String JSON_DATA_KEY_PRICE = "price";
	private static final String JSON_DATA_KEY_QUANTITY = "quantity";
	private static final String JSON_DATA_KEY_TRADE_INFO = "tradeInfo";
	private static final String JSON_DATA_KEY_VMAP = "vmap";
	private static final String JSON_DATA_KEY_OI = "openInterest";
	private static final String JSON_DATA_KEY_CHANGE_IN_OI = "changeinOpenInterest";
	private static final String JSON_DATA_KEY_PCHANGE_IN_OI = "pchangeinOpenInterest";
	private static final String JSON_DATA_KEY_MARKET_LOT = "marketLot";
	private static final String JSON_DATA_KEY_CHANGE = "change";
	private static final String JSON_DATA_KEY_PCHANGE = "pChange";
	private static final String JSON_DATA_KEY_OTHER_INFO = "otherInfo";
	private static final String JSON_DATA_KEY_IMPLIED_VOLATILITY = "impliedVolatility";
	private static final String JSON_DATA_KEY_DAILY_VOLATILITY = "dailyvolatility";
	private static final String JSON_DATA_KEY_ANNUALISED_VOLATILITY = "annualisedVolatility";

	private static final String JSON_DATA_KEY_INSTRUMENTTYPE = "instrumentType";
	private static final String JSON_DATA_KEY_INSTRUMENT_TYPE_VALUE_OPTON = "Index Options";
	private static final String JSON_DATA_KEY_INSTRUMENT_TYPE_INDEX_FUTURES = "Index Futures";
	private static final String JSON_DATA_KEY_OPTIONTYPE = "optionType";
	private static final String JSON_DATA_KEY_EXPIRYDATE = "expiryDate";// "12-Aug-2021"
	private static final String JSON_DATA_KEY_STRIKEPRICE = "strikePrice";
	private static final String JSON_DATA_KEY_LASTPRICE = "lastPrice";
	private static final String JSON_DATA_KEY_UNDERLYINGVALUE = "underlyingValue";
	private static final String JSON_DATA_KEY_OPTIONTYPE_CALL = "Call";
	private static final String JSON_DATA_KEY_OPTIONTYPE_PUT = "Put";

	private static final String JSON_DATA_KEY_FILTER = "filter";
	private static final String JSON_DATA_KEY_EXPIRY_DATES = "expiryDates";
	private static final String JSON_DATA_KEY_STRIKE_PRICES = "strikePrices";

//	Double callPrice;
//	Double putPriice;
//	Double underlyingIndexPrice;

	private static String getSymbolForURL(String symbol) {
		return symbol.replaceAll("&", "%26").toUpperCase();
		// String[] check = {"L%26TFH", "BANKNIFTY", "M%26MFIN", "M%26M"};
	}

	/*private boolean findNextStrikePrce(String symbolId) {
		boolean hasNext = null != strikePriceIterator && strikePriceIterator.hasNext();
		strikePrice = null;
		if (hasNext) {
			strikePrice = strikePriceIterator.next();
			liveDataBean = new OptionTradeLiveDataBean();
			liveDataBean.setSymbolId(symbolId);
			liveDataBean.setStrikePrice(strikePrice);
			liveOptionDataList.add(liveDataBean);
			liveOptionDataMap.put(symbolId, liveOptionDataList);
		}
		return hasNext;
	}*/

	//private OptionTradeLiveDataBean liveDataBean;
	private Double  underlyingFuturePrice;
	private List<OptionTradeLiveDataBean> liveOptionDataList;
	private Map<String, List<OptionTradeLiveDataBean>> liveOptionDataMap;
	//private static Integer strikePrice;
	//private static Iterator<Integer> strikePriceIterator;
	//private static Set<Integer> strikePriceInputSet = new TreeSet<Integer>();
	
	private void prepareResponseData(String symbolId, List<String> expiryDate,
			List<String> strikePriceList) {
		liveOptionDataList = new LinkedList<OptionTradeLiveDataBean>();
		liveOptionDataMap = new LinkedHashMap<String, List<OptionTradeLiveDataBean>>();
		
		for (String closeDate : expiryDate) {
			for (String strikePrice : strikePriceList) {
				OptionTradeLiveDataBean liveDataBean = new OptionTradeLiveDataBean();
				liveDataBean.setSymbolId(symbolId);
				liveDataBean.setStrikePrice(Double.valueOf(strikePrice));
				liveDataBean.setExpiryDate(closeDate);
				liveOptionDataList.add(liveDataBean);
			}
		}
		liveOptionDataMap.put(symbolId, liveOptionDataList);
	}
	
	private OptionTradeLiveDataBean findResponseDataBean(String expiryDate, String strikePrice) {
		OptionTradeLiveDataBean bean = null;
		for (OptionTradeLiveDataBean beanTemp : liveOptionDataList) {
			if (null != expiryDate && expiryDate.equals(beanTemp.getExpiryDate()) && null != strikePrice
					&& Double.valueOf(strikePrice).doubleValue() == beanTemp.getStrikePrice().doubleValue()) {
				bean = beanTemp;
				break;
			}
		}
		return bean;
	}
	
	public void connectToNSEIndiaAndGetStockOptionData(String symbolId, List<String> expiryDate,
			List<String> strikePriceList) {
		int i = 0;
		try {
			//strikePriceInputSet.clear();
			//Collections.addAll(strikePriceInputSet, strikePriceArray);
			//strikePriceIterator = strikePriceInputSet.iterator();
			//liveOptionDataList = new LinkedList<OptionTradeLiveDataBean>();
			//liveOptionDataMap = new LinkedHashMap<String, List<OptionTradeLiveDataBean>>();
			//findNextStrikePrce(symbolId);
			
			prepareResponseData(symbolId, expiryDate, strikePriceList);

			String response = webClient.getPage(OPTION_TRADE_URL + getSymbolForURL(symbolId)).getWebResponse()
					.getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_STOCKS);
			if (null != rawJsonDataArray && !rawJsonDataArray.isEmpty()) {
				for (; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					JSONObject metadata = (JSONObject) jsonSymbolObject.get(JSON_DATA_KEY_METADATA);
					if (JSON_DATA_KEY_INSTRUMENT_TYPE_VALUE_OPTON.equals(metadata.get(JSON_DATA_KEY_INSTRUMENTTYPE))) {
						if (expiryDate.contains(metadata.get(JSON_DATA_KEY_EXPIRYDATE))) {
							/*if (strikePrice.intValue() == (Integer
											.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE))))
													.intValue()) {*/
							if (strikePriceList.contains(String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)))) {
								OptionTradeLiveDataBean liveDataBean = findResponseDataBean(
										String.valueOf(metadata.get(JSON_DATA_KEY_EXPIRYDATE)),
										String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)));
								JSONObject mktDeptOrdrBook = (JSONObject) jsonSymbolObject.get(JSON_DATA_KEY_MARKET_DEPT_ORDER_BOOK);
								if (JSON_DATA_KEY_OPTIONTYPE_CALL.equals(metadata.get(JSON_DATA_KEY_OPTIONTYPE))) {
									liveDataBean.setLastPriceCall(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_LASTPRICE))));
									liveDataBean.setChangeCall(kiteConnectTool.getRoundupToTwoValue(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_CHANGE)))));
									liveDataBean.setPchangeCall(kiteConnectTool.getRoundupToTwoValue(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_PCHANGE)))));
									
									JSONArray bidArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_BID);
									JSONArray askArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_ASK);
									liveDataBean.setBidPriceCall(Double.valueOf(
											String.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setAskPriceCall(Double.valueOf(
											String.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setBidQuantityCall(Long.valueOf(String
											.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									liveDataBean.setAskQuantityCall(Long.valueOf(String
											.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									
									JSONObject tradeInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_TRADE_INFO);
									liveDataBean.setVwapCall(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_VMAP))));
									liveDataBean.setOpenInterestCall(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_OI))));
									liveDataBean.setChangeInOpenInterestCall(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_CHANGE_IN_OI))));
									liveDataBean.setPchangeInOpenInterestCall(kiteConnectTool.getRoundupToTwoValue(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_PCHANGE_IN_OI)))));
									
									JSONObject otherInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_OTHER_INFO);
									liveDataBean.setImpliedVolatilityCall(Double.valueOf(
											String.valueOf(otherInfo.get(JSON_DATA_KEY_IMPLIED_VOLATILITY))));
									liveDataBean.setDailyvolatilityCall(Double.valueOf(
											String.valueOf(otherInfo.get(JSON_DATA_KEY_DAILY_VOLATILITY))));
									liveDataBean.setAnnualisedVolatilityCall(Double.valueOf(
											String.valueOf(otherInfo.get(JSON_DATA_KEY_ANNUALISED_VOLATILITY))));
									/*if (null !=liveDataBean.getLastPricePut() && !findNextStrikePrce(symbolId)) {
										//break;
									}*/
								} else if (JSON_DATA_KEY_OPTIONTYPE_PUT
										.equals(metadata.get(JSON_DATA_KEY_OPTIONTYPE))) {
									liveDataBean.setLastPricePut(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_LASTPRICE))));
									liveDataBean.setChangePut(kiteConnectTool.getRoundupToTwoValue(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_CHANGE)))));
									liveDataBean.setPchangePut(kiteConnectTool.getRoundupToTwoValue(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_PCHANGE)))));
									
									JSONArray bidArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_BID);
									JSONArray askArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_ASK);
									liveDataBean.setBidPricePut(Double.valueOf(
											String.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setAskPricePut(Double.valueOf(
											String.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setBidQuantityPut(Long.valueOf(String
											.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									liveDataBean.setAskQuantityPut(Long.valueOf(String
											.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									
									JSONObject tradeInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_TRADE_INFO);
									liveDataBean.setVwapPut(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_VMAP))));
									liveDataBean.setOpenInterestPut(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_OI))));
									liveDataBean.setChangeInOpenInterestPut(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_CHANGE_IN_OI))));
									liveDataBean.setPchangeInOpenInterestPut(kiteConnectTool.getRoundupToTwoValue(Double.valueOf(
											String.valueOf(tradeInfo.get(JSON_DATA_KEY_PCHANGE_IN_OI)))));
									
									JSONObject otherInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_OTHER_INFO);
									liveDataBean.setImpliedVolatilityPut(Double.valueOf(
											String.valueOf(otherInfo.get(JSON_DATA_KEY_IMPLIED_VOLATILITY))));
									liveDataBean.setDailyvolatilityPut(Double.valueOf(
											String.valueOf(otherInfo.get(JSON_DATA_KEY_DAILY_VOLATILITY))));
									liveDataBean.setAnnualisedVolatilityPut(Double.valueOf(
											String.valueOf(otherInfo.get(JSON_DATA_KEY_ANNUALISED_VOLATILITY))));

									//liveDataBean.setExpiryDate(String.valueOf(metadata.get(JSON_DATA_KEY_EXPIRYDATE)));
									liveDataBean.setUnderlyingStockPrice(Double.valueOf(
											String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_UNDERLYINGVALUE))));
									/*if (null !=liveDataBean.getLastPriceCall() && !findNextStrikePrce(symbolId)) {
										//break;
									}*/
								}
							} else {
								continue;
							}
						} else {
							continue;
						}

					} else {
			////System.out.println("1.  -->" + metadata.get(JSON_DATA_KEY_INSTRUMENTTYPE) + "  ::  " + i);
						if (JSON_DATA_KEY_INSTRUMENT_TYPE_VALUE_OPTON
								.equals(metadata.get(JSON_DATA_KEY_INSTRUMENT_TYPE_INDEX_FUTURES))) {
							underlyingFuturePrice = (Double.valueOf(
									String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_UNDERLYINGVALUE))));
						}
							
						continue;
					}

				}
			}
			
			for (OptionTradeLiveDataBean dataBean : liveOptionDataList) {
				dataBean.setUnderlyingFuturePrice(underlyingFuturePrice);
			}
			//
			/*for (OptionTradeLiveDataBean bean: liveOptionDataList) {
				System.out.println(bean);
				System.out.println(i+"-----------------------------------------------");
				System.out.println(i+"-----------------------------------------------");
				System.out.println("");
			}*/
			
			//
		} catch (FailingHttpStatusCodeException | IOException e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionData(symbolId, expiryDate, strikePriceList);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionData(symbolId, expiryDate, strikePriceList);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		}
	}

	public void connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(String symbolId) {
		try {
			if (tradewareTool.getTradeExpiryDatesList(symbolId).isEmpty()
					|| tradewareTool.getTradeStrikePricesList(symbolId).isEmpty()) {
				webClient.getPage(BASE_URL);
				String response = webClient.getPage(OPTION_TRADE_URL + getSymbolForURL(symbolId)).getWebResponse()
						.getContentAsString();
				JSONParser jsonParser = new JSONParser();
				JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
				JSONArray expiryDates = (JSONArray) rawJsonData.get(JSON_DATA_KEY_EXPIRY_DATES);
				JSONArray strikePrices = (JSONArray) rawJsonData.get(JSON_DATA_KEY_STRIKE_PRICES);

				for (int k = 0; k < expiryDates.size(); k++) {
					if (!tradewareTool.getTradeExpiryDatesList(symbolId).contains(expiryDates.get(k))) {
						tradewareTool.getTradeExpiryDatesList(symbolId).add(String.valueOf(expiryDates.get(k)));
					}
				}

				Integer tickerLowTemp = null;
				Integer tickerHighTemp = null;
				Integer ticker = null;
				
				for (int i = strikePrices.size()/2; i < strikePrices.size(); i++) {
					if  (null == tickerLowTemp && Integer.valueOf(String.valueOf(strikePrices.get(i))) > 0) {
						tickerLowTemp = Integer.valueOf(String.valueOf(strikePrices.get(i)));
					} else if  (null == tickerHighTemp && null != tickerLowTemp &&  Integer.valueOf(String.valueOf(strikePrices.get(i))) > tickerLowTemp) {
						tickerHighTemp = Integer.valueOf(String.valueOf(strikePrices.get(i)));
						ticker = tickerHighTemp - tickerLowTemp;
						break;
					} 
				}
				
				double lastPrice = Double.valueOf(String.valueOf(rawJsonData.get(JSON_DATA_KEY_UNDERLYINGVALUE)));
				//Constants.BANKNIFTY
				if (Constants.NIFTY.equals(symbolId)) {
					tradewareTool.setNiftyLiveStockPrice(lastPrice);
				} else if (Constants.BANKNIFTY.equals(symbolId)) {
					tradewareTool.setBankNiftyLiveStockPrice(lastPrice);
				} else if (Constants.FINNIFTY.equals(symbolId)) {
					tradewareTool.setFinanceNiftyLiveStockPrice(lastPrice);
				}
				
				
				int roundLtp = (int) lastPrice;
				// int nearLow = (int) (roundLtp - (roundLtp % /* ticker */100));
				// int nearHigh = (int) (nearLow + 100/* ticker */);
				int nearLow = (int) (roundLtp - (roundLtp % ticker));
				int nearHigh = (int) (nearLow + ticker);

				lastPrice = (nearHigh - lastPrice) < (lastPrice - nearLow) ? nearHigh : nearLow;

				for (int i = 0; i < strikePrices.size(); i++) {
					Integer strikePrice = Integer.valueOf(String.valueOf(strikePrices.get(i)));
					if (!tradewareTool.getTradeStrikePricesList(symbolId).contains(strikePrice)
							&& ((strikePrice >= (lastPrice - (20 * ticker)))
									&& (strikePrice <= (lastPrice + (20 * ticker))))) {
						tradewareTool.getTradeStrikePricesList(symbolId).add(strikePrice);
					}
				}
			}
		} catch (FailingHttpStatusCodeException | IOException e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(symbolId);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(symbolId);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		}
	}
	
	public void flterOutNextExpiryDates(String symbolId) {
		try {
			if (!tradewareTool.getTradeExpiryDatesList(symbolId).isEmpty()) {
				List<String> expiryDatesAsStr = tradewareTool.getTradeExpiryDatesList(symbolId);

				if (null != expiryDatesAsStr && !expiryDatesAsStr.isEmpty()) {
					// create a formater
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_EXP_DATES);

					for (String expiryDatesAsString : expiryDatesAsStr) {
						// create a LocalDate object and
						LocalDate expiryDatesAsLocalDate = LocalDate.parse(expiryDatesAsString, formatter);
						if (LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).isEqual(expiryDatesAsLocalDate)
								|| LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).isBefore(expiryDatesAsLocalDate)) {
							if (!tradewareTool.getNextExpiryDatesList().contains(expiryDatesAsString)
									&& tradewareTool.getNextExpiryDatesList().size() <4) {
								tradewareTool.getNextExpiryDatesList().add(expiryDatesAsString);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			try {
				/*
				 * webClient.getPage(BASE_URL);
				 * connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(symbolId);
				 */
			} catch (Exception e1) {
			}
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//fetch whole data
	public void prepareResponseWholeData(String symbolId, List<String> expiryDateList, List<String> strikePriceList) {
		if (null == tradewareTool.getLiveWholeOptionDataMap()
				|| !tradewareTool.getLiveWholeOptionDataMap().containsKey(symbolId)) {
			tradewareTool.setLiveWholeOptionDataList(new LinkedList<OptionTradeLiveDataBean>());
			tradewareTool.setLiveWholeOptionDataMap(new LinkedHashMap<String, List<OptionTradeLiveDataBean>>());

			for (String closeDate : expiryDateList) {
				for (String strikePrice : strikePriceList) {
					OptionTradeLiveDataBean liveDataBean = new OptionTradeLiveDataBean();
					liveDataBean.setSymbolId(symbolId);
					liveDataBean.setStrikePrice(Double.valueOf(strikePrice));
					liveDataBean.setExpiryDate(closeDate);
					tradewareTool.getLiveWholeOptionDataList().add(liveDataBean);
				}
			}
			tradewareTool.getLiveWholeOptionDataMap().put(symbolId, tradewareTool.getLiveWholeOptionDataList());
		}
	}
	public OptionTradeLiveDataBean findResponseDataBean(List<OptionTradeLiveDataBean> optionDataList, String expiryDate, String strikePrice) {
		OptionTradeLiveDataBean bean = null;
		for (OptionTradeLiveDataBean beanTemp : optionDataList) {
			if (null != expiryDate && expiryDate.equals(beanTemp.getExpiryDate()) && null != strikePrice
					&& Double.valueOf(strikePrice).doubleValue() == beanTemp.getStrikePrice().doubleValue()) {
				bean = beanTemp;
				break;
			}
		}
		return bean;
	}
	
	/*private double getValue(Double val) {
		return null != val ? val : 0d;
	}
	private int getValue(Integer val) {
		return null != val ? val : 0;
	}*/
	
	public  Map<String, List<OptionTradeLiveDataBean>> connectToNSEIndiaAndGetStockWholeOptionData(String symbolId) {
		int i = 0;
		List<String> expiryDateList = tradewareTool.getTradeExpiryDatesList(symbolId);
		List<String> strikePriceList = tradewareTool.getTradeStrikePricesList(symbolId).stream().map(Object::toString)
                .collect(Collectors.toList());
		try {
			 
			prepareResponseWholeData(symbolId, expiryDateList, strikePriceList);

			String response = webClient.getPage(OPTION_TRADE_URL + getSymbolForURL(symbolId)).getWebResponse()
					.getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_STOCKS);
			if (null != rawJsonDataArray && !rawJsonDataArray.isEmpty()) {
				for (; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					JSONObject metadata = (JSONObject) jsonSymbolObject.get(JSON_DATA_KEY_METADATA);
					if (JSON_DATA_KEY_INSTRUMENT_TYPE_VALUE_OPTON.equals(metadata.get(JSON_DATA_KEY_INSTRUMENTTYPE))) {
						if (expiryDateList.contains(metadata.get(JSON_DATA_KEY_EXPIRYDATE))) {
							/*if (strikePrice.intValue() == (Integer
											.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE))))
													.intValue()) {*/
							if (strikePriceList.contains(String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)))) {
								OptionTradeLiveDataBean liveDataBean = findResponseDataBean(tradewareTool.getLiveWholeOptionDataMap().get(symbolId),
										String.valueOf(metadata.get(JSON_DATA_KEY_EXPIRYDATE)),
										String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)));
								JSONObject mktDeptOrdrBook = (JSONObject) jsonSymbolObject.get(JSON_DATA_KEY_MARKET_DEPT_ORDER_BOOK);
								if (null != liveDataBean) {
									if (JSON_DATA_KEY_OPTIONTYPE_CALL.equals(metadata.get(JSON_DATA_KEY_OPTIONTYPE))) {
										liveDataBean.setLastPriceCall(
												Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_LASTPRICE))));
										liveDataBean.setChangeCall(kiteConnectTool.getRoundupToTwoValue(
												Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_CHANGE)))));
										liveDataBean.setPchangeCall(kiteConnectTool.getRoundupToTwoValue(
												Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_PCHANGE)))));
										
										JSONArray bidArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_BID);
										JSONArray askArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_ASK);
										liveDataBean.setBidPriceCall(Double.valueOf(
												String.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_PRICE))));
										liveDataBean.setAskPriceCall(Double.valueOf(
												String.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_PRICE))));
										liveDataBean.setBidQuantityCall(Long.valueOf(String
												.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
										liveDataBean.setAskQuantityCall(Long.valueOf(String
												.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
										
										/*bidPriceCallAvgFull = 0;
										askPriceCallAvgFull = 0;
										bidQuantityCallFull = 0;
										askQuantityCallFull = 0;
										for (int k = 0; k < bidArray.size(); k++) {
											bidPriceCallAvgFull = bidPriceCallAvgFull + getValue(Double.valueOf(String
													.valueOf(((JSONObject) bidArray.get(k)).get(JSON_DATA_KEY_PRICE))));
											bidQuantityCallFull = bidQuantityCallFull + getValue(Integer.valueOf(String
													.valueOf(((JSONObject) bidArray.get(k)).get(JSON_DATA_KEY_QUANTITY))));
										}
										for (int k = 0; k < askArray.size(); k++) {
											askPriceCallAvgFull = askPriceCallAvgFull + getValue(Double.valueOf(String
													.valueOf(((JSONObject) askArray.get(k)).get(JSON_DATA_KEY_PRICE))));
											askQuantityCallFull = askQuantityCallFull + getValue(Integer.valueOf(String
													.valueOf(((JSONObject) askArray.get(k)).get(JSON_DATA_KEY_QUANTITY))));
										}
										liveDataBean.setBidPriceCall(kiteConnectTool
												.getRoundupToTwoValue(bidPriceCallAvgFull / bidArray.size()));
										liveDataBean.setAskPriceCall(kiteConnectTool
												.getRoundupToTwoValue(askPriceCallAvgFull / bidArray.size()));
										liveDataBean.setBidQuantityCall((int) bidQuantityCallFull / bidArray.size());
										liveDataBean.setAskQuantityCall((int) askQuantityCallFull / bidArray.size());*/
										
										
										JSONObject tradeInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_TRADE_INFO);
										liveDataBean.setVwapCall(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_VMAP))));
										liveDataBean.setOpenInterestCall(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_OI))));
										liveDataBean.setChangeInOpenInterestCall(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_CHANGE_IN_OI))));
										liveDataBean.setPchangeInOpenInterestCall(kiteConnectTool.getRoundupToTwoValue(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_PCHANGE_IN_OI)))));
										liveDataBean.setLotSize(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_MARKET_LOT))));
										
										JSONObject otherInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_OTHER_INFO);
										liveDataBean.setImpliedVolatilityCall(Double.valueOf(
												String.valueOf(otherInfo.get(JSON_DATA_KEY_IMPLIED_VOLATILITY))));
										liveDataBean.setDailyvolatilityCall(Double.valueOf(
												String.valueOf(otherInfo.get(JSON_DATA_KEY_DAILY_VOLATILITY))));
										liveDataBean.setAnnualisedVolatilityCall(Double.valueOf(
												String.valueOf(otherInfo.get(JSON_DATA_KEY_ANNUALISED_VOLATILITY))));
										/*if (null !=liveDataBean.getLastPricePut() && !findNextStrikePrce(symbolId)) {
											//break;
										}*/
									} else if (JSON_DATA_KEY_OPTIONTYPE_PUT
											.equals(metadata.get(JSON_DATA_KEY_OPTIONTYPE))) {
										liveDataBean.setLastPricePut(
												Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_LASTPRICE))));
										liveDataBean.setChangePut(kiteConnectTool.getRoundupToTwoValue(
												Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_CHANGE)))));
										liveDataBean.setPchangePut(kiteConnectTool.getRoundupToTwoValue(
												Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_PCHANGE)))));
										
										JSONArray bidArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_BID);
										JSONArray askArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_ASK);
										liveDataBean.setBidPricePut(Double.valueOf(
												String.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_PRICE))));
										liveDataBean.setAskPricePut(Double.valueOf(
												String.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_PRICE))));
										liveDataBean.setBidQuantityPut(Long.valueOf(String
												.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
										liveDataBean.setAskQuantityPut(Long.valueOf(String
												.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
										/*bidPriceCallAvgFull = 0;
										askPriceCallAvgFull = 0;
										bidQuantityCallFull = 0;
										askQuantityCallFull = 0;
										for (int k = 0; k < bidArray.size(); k++) {
											bidPriceCallAvgFull = bidPriceCallAvgFull + getValue(Double.valueOf(String
													.valueOf(((JSONObject) bidArray.get(k)).get(JSON_DATA_KEY_PRICE))));
											bidQuantityCallFull = bidQuantityCallFull + getValue(Integer.valueOf(String
													.valueOf(((JSONObject) bidArray.get(k)).get(JSON_DATA_KEY_QUANTITY))));
										}
										for (int k = 0; k < askArray.size(); k++) {
											askPriceCallAvgFull = askPriceCallAvgFull + getValue(Double.valueOf(String
													.valueOf(((JSONObject) askArray.get(k)).get(JSON_DATA_KEY_PRICE))));
											askQuantityCallFull = askQuantityCallFull + getValue(Integer.valueOf(String
													.valueOf(((JSONObject) askArray.get(k)).get(JSON_DATA_KEY_QUANTITY))));
										}
										liveDataBean.setBidPriceCall(kiteConnectTool
												.getRoundupToTwoValue(bidPriceCallAvgFull / bidArray.size()));
										liveDataBean.setAskPriceCall(kiteConnectTool
												.getRoundupToTwoValue(askPriceCallAvgFull / bidArray.size()));
										liveDataBean.setBidQuantityCall((int) bidQuantityCallFull / bidArray.size());
										liveDataBean.setAskQuantityCall((int) askQuantityCallFull / bidArray.size());*/
										
										JSONObject tradeInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_TRADE_INFO);
										liveDataBean.setVwapPut(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_VMAP))));
										liveDataBean.setOpenInterestPut(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_OI))));
										liveDataBean.setChangeInOpenInterestPut(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_CHANGE_IN_OI))));
										liveDataBean.setPchangeInOpenInterestPut(kiteConnectTool.getRoundupToTwoValue(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_PCHANGE_IN_OI)))));
										liveDataBean.setLotSize(Double.valueOf(
												String.valueOf(tradeInfo.get(JSON_DATA_KEY_MARKET_LOT))));
										
										JSONObject otherInfo = (JSONObject) mktDeptOrdrBook.get(JSON_DATA_KEY_OTHER_INFO);
										liveDataBean.setImpliedVolatilityPut(Double.valueOf(
												String.valueOf(otherInfo.get(JSON_DATA_KEY_IMPLIED_VOLATILITY))));
										liveDataBean.setDailyvolatilityPut(Double.valueOf(
												String.valueOf(otherInfo.get(JSON_DATA_KEY_DAILY_VOLATILITY))));
										liveDataBean.setAnnualisedVolatilityPut(Double.valueOf(
												String.valueOf(otherInfo.get(JSON_DATA_KEY_ANNUALISED_VOLATILITY))));
	
										//liveDataBean.setExpiryDate(String.valueOf(metadata.get(JSON_DATA_KEY_EXPIRYDATE)));
										liveDataBean.setUnderlyingStockPrice(Double.valueOf(
												String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_UNDERLYINGVALUE))));
										/*if (null !=liveDataBean.getLastPriceCall() && !findNextStrikePrce(symbolId)) {
											//break;
										}*/
									}
								} else {
									System.out.println("liveDataBean is Null ::: NSEIndiaBankNiftyTradeToolNewNseSite::connectToNSEIndiaAndGetStockWholeOptionData");
									System.out.println(symbolId + "  :: "+String.valueOf(metadata.get(JSON_DATA_KEY_EXPIRYDATE)) + "  "+String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)));
									
								}
							} else {
								continue;
							}
						} else {
							continue;
						}

					} else {
		//System.out.println("1.  -->" + metadata.get(JSON_DATA_KEY_INSTRUMENTTYPE) + "  ::  " + i);
						if (JSON_DATA_KEY_INSTRUMENT_TYPE_VALUE_OPTON
								.equals(metadata.get(JSON_DATA_KEY_INSTRUMENT_TYPE_INDEX_FUTURES))) {
							underlyingFuturePrice = (Double.valueOf(
									String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_UNDERLYINGVALUE))));
							
							if (Constants.NIFTY.equals(symbolId)) {
								tradewareTool.setNiftyLiveFuturePrice(underlyingFuturePrice);
							} else if (Constants.BANKNIFTY.equals(symbolId)) {
								tradewareTool.setBankNiftyLiveFuturePrice(underlyingFuturePrice);
							} else if (Constants.FINNIFTY.equals(symbolId)) {
								tradewareTool.setFinanceNiftyLiveFuturePrice(underlyingFuturePrice);
							}
						}
							
						continue;
					}

				}
			}
			
			/*for (OptionTradeLiveDataBean dataBean : tradewareTool.getLiveWholeOptionDataList()) {
				dataBean.setUnderlyingFuturePrice(underlyingFuturePrice);
			}
			//
			for (OptionTradeLiveDataBean bean: tradewareTool.getLiveWholeOptionDataList()) {
				System.out.println(bean);
				System.out.println(i+"-----------------------------------------------");
				System.out.println(i+"-----------------------------------------------");
				System.out.println("");
			}*/
			
			//
		} catch (FailingHttpStatusCodeException | IOException e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionData(symbolId, expiryDateList, strikePriceList);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionData(symbolId, expiryDateList, strikePriceList);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		}
		
		return tradewareTool.getLiveWholeOptionDataMap();
	}
	//fetch whole data
	
	
	
	
	
	
	
	
	
	
	
	private ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCDataMap = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();

	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> connectToNSEIndiaAndGetStockOptionData1(String symbolId,
			List<String> expiryDate, List<String> strikePriceList) {
		try {
			liveOHLCDataMap.clear();
			String response = webClient.getPage(OPTION_TRADE_URL + getSymbolForURL(symbolId)).getWebResponse()
					.getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_STOCKS);
			if (null != rawJsonDataArray && !rawJsonDataArray.isEmpty()) {
				for (int i = 0; i < rawJsonDataArray.size(); i++) {
					JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
					JSONObject metadata = (JSONObject) jsonSymbolObject.get(JSON_DATA_KEY_METADATA);
					if (JSON_DATA_KEY_INSTRUMENT_TYPE_VALUE_OPTON.equals(metadata.get(JSON_DATA_KEY_INSTRUMENTTYPE))) {
						if (expiryDate.contains(metadata.get(JSON_DATA_KEY_EXPIRYDATE))) {
							if (strikePriceList.contains(String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)))) {

								JSONObject mktDeptOrdrBook = (JSONObject) jsonSymbolObject
										.get(JSON_DATA_KEY_MARKET_DEPT_ORDER_BOOK);
								if (JSON_DATA_KEY_OPTIONTYPE_CALL.equals(metadata.get(JSON_DATA_KEY_OPTIONTYPE))) {
									KiteLiveOHLCDataBean liveDataBean = new KiteLiveOHLCDataBean();
									liveDataBean.setSymbolId(symbolId);
									liveDataBean.setOptionStrikePrice(
											String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)));
									liveDataBean.setOptionExpiryDate(
											String.valueOf(metadata.get(JSON_DATA_KEY_EXPIRYDATE)));
									liveDataBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(
											symbolId, liveDataBean.getOptionExpiryDate(),
											liveDataBean.getOptionStrikePrice(), Constants.OPTION_CALL));

									liveDataBean.setLtp(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_LASTPRICE))));
									JSONArray bidArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_BID);
									JSONArray askArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_ASK);
									liveDataBean.setBidPrice(Double.valueOf(
											String.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setAskPrice(Double.valueOf(
											String.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setBidQuantity(Long.valueOf(String
											.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									liveDataBean.setAskQuantity(Long.valueOf(String
											.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									liveOHLCDataMap.put(liveDataBean.getKiteFutureKey(), liveDataBean);
								} else if (JSON_DATA_KEY_OPTIONTYPE_PUT
										.equals(metadata.get(JSON_DATA_KEY_OPTIONTYPE))) {
									KiteLiveOHLCDataBean liveDataBean = new KiteLiveOHLCDataBean();
									liveDataBean.setSymbolId(symbolId);
									liveDataBean.setOptionStrikePrice(
											String.valueOf(metadata.get(JSON_DATA_KEY_STRIKEPRICE)));
									liveDataBean.setOptionExpiryDate(
											String.valueOf(metadata.get(JSON_DATA_KEY_EXPIRYDATE)));
									liveDataBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(
											symbolId, liveDataBean.getOptionExpiryDate(),
											liveDataBean.getOptionStrikePrice(), Constants.OPTION_PUT));
									liveDataBean.setLtp(
											Double.valueOf(String.valueOf(metadata.get(JSON_DATA_KEY_LASTPRICE))));

									JSONArray bidArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_BID);
									JSONArray askArray = (JSONArray) mktDeptOrdrBook.get(JSON_DATA_KEY_ASK);
									liveDataBean.setBidPrice(Double.valueOf(
											String.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setAskPrice(Double.valueOf(
											String.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_PRICE))));
									liveDataBean.setBidQuantity(Long.valueOf(String
											.valueOf(((JSONObject) bidArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									liveDataBean.setAskQuantity(Long.valueOf(String
											.valueOf(((JSONObject) askArray.get(0)).get(JSON_DATA_KEY_QUANTITY))));
									liveOHLCDataMap.put(liveDataBean.getKiteFutureKey(), liveDataBean);
								}
							} else {
								continue;
							}
						} else {
							continue;
						}

					}
				}
			}

		} catch (FailingHttpStatusCodeException | IOException e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionData1(symbolId, expiryDate, strikePriceList);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				webClient.getPage(BASE_URL);
				connectToNSEIndiaAndGetStockOptionData1(symbolId, expiryDate, strikePriceList);
			} catch (FailingHttpStatusCodeException | IOException e1) {
			}
			e.printStackTrace();
		}
		return liveOHLCDataMap;
	}
	
}
