package com.tradeware.stockmarket.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.WebClient;
import com.tradeware.stockmarket.bean.Narrow7DataBean;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaToolNR7Tool {
	@Autowired
	private TradewareTool tradewareTool;
	
	@Autowired 
	private KiteConnectTool kiteConnectTool;
	
	private static WebClient webClient = null;
	private List<String> failedResultDataMap = new ArrayList<String>();
	
	private ConcurrentHashMap<String, List<Narrow7DataBean>> nr7DataMap = new ConcurrentHashMap<String, List<Narrow7DataBean>>();
	
	private WebClient getWebClient() {
		if (null == webClient) {
			webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setUseInsecureSSL(false);
			webClient.getOptions().setCssEnabled(false);
			
			webClient.addRequestHeader("Accept",
					"application/json; charset=utf-8");
			webClient.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
			webClient.addRequestHeader("Accept-Language", "en-US,en;q=0.5");
			webClient.addRequestHeader("Connection", "keep-alive");
			/*webClient.addRequestHeader("Cookie",
					"_ga=GA1.2.1108189851.1593122201; pointerfo=1; underlying1=INFY; instrument1=FUTSTK; optiontype1=-; expiry1=30JUL2020; strikeprice1=-; RT=\"z=1&dm=nseindia.com&si=30710a1f-2540-4448-9e33-7a770b0ebcae&ss=kcpomakh&sl=0&tt=0&bcn=%2F%2F684d0d37.akstat.io%2F\"; NSE-TEST-1=1927290890.20480.0000; _gid=GA1.2.1231178383.1594946160; JSESSIONID=ADCB21AF297C9519220D00EEA5EB3F28.tomcat1; pointer=5; sym1=RELIANCE; sym2=INFRATEL; sym3=IRCTC; sym4=ABFRL; sym5=IPCALAB; ak_bmsc=F621639370AB062BBB9798D5C8ED7D3317CB3F263C3B000090A4125FB3594369~plvDBnTQevPcteD3pYFsERMdNCLs5Xvwv5Bxejtd02bvt0WWEiuQ7zfp1Nuh7uw03yp3uaDFC3MrpAwLSrurUfkMP63nLKbOF7ZG6+M/CSBs+ZxTRvVtCArpkBbLwjJvdBpPt72CLnYJ5HureVDoUsrIKi+GvPe9gvqHbTYSwj0ph+qVKgq1oDGQHJt5Z5zp/bRryY1ukXsK5QsIAT3DfmNSHzCnviOmIgAx8u5xa9vFA=; bm_sv=73B7317B2D7B2A63CC34891AB9EFE7E1~L0te/McA/HQf4P3MQsziokKK/L/eG5CQp/wwjQN7/yzG9+v8hlj2s0AIJHgd/sDJcAe5MDN+ar6b88YghWU+hqGCXaYKjhhIrPeVlmilNdLhV93Z775Bqb/nWA7l8twT3pEzSvMomAOdn6Oo3hyCEh/0PC1gPLq+2cduuf9IcL4=; bm_mi=F5602C986BFFD436101AB4FC47B759CC~fau2IjM/honi2zUkj5P6KbZtA6+XVpPO0TNU1pbVXVs7gXHHnQ9gtEyn2u+S1sQ9d1v4wVKNWGOm6qoUsEINxn0MVw0OD4Zu2L/SjA9EiEz2UNDOimXKoyog9eG9GOyzuEdELSwDMXddx05vWPqTQVKjGCcaO8EpeUD3sYjiB9M0EDsvVt+4WyseBf2UjZYgH4orRGdtW3zecaWLtvVuwqaM/XFCMDJx/cl74NqtyfM8iu9VR58B0rxHKl2JZ8d+fKB2J9EF8alvlJDZhGsm36zyAlJ+wyuAc8wqGKKiaPVasjd5D3MKjGbxrIZZsxN3");
			*/webClient.addRequestHeader("Host", "www.nseindia.com");
			webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
			webClient.addRequestHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; rv:82.0) Gecko/20100101 Firefox/82.0");
		}
		return webClient;
	}
	
	public void prepareNR7Tradees() {
		try {
			retrieveHistoricalDataForNR7Tradees();
			processNarrow7Strategy();
			findStochasticTrend(nr7DataMap);
			caluclateRSI(nr7DataMap);
			findHeikinAshiTrend(nr7DataMap);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void retrieveHistoricalDataForNR7Tradees() throws ParseException {
		ConcurrentHashMap<String, Narrow7DataBean> map = tradewareTool.getNr7TradeDataMap();
		for (String key : map.keySet()) {
			Narrow7DataBean bean = map.get(key);
			if (key.startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
				String pageResponse = retrieveHistoricalDataForEquity(bean.getSymbolId());
				nr7DataMap.put(bean.getKiteFutureKey(), processHistDataForNR7Equity(bean.getSymbolId(), pageResponse,
						bean.getLotSize(), bean.getKiteFutureKey()));
			} else if (key.startsWith(Constants.FUTURE_KEY_PREFIX_NFO)) {
				retrievePreviousContractHistoricalData(bean.getSymbolId(), bean.getKiteFutureKey());
			}
		}
	}

	public String retrieveHistoricalDataForEquity(String symbol) {
		String pageResponse = null;
		topHigh = 0d;  topLow = 0d;
		try {
			//String URL = StockUtil.getEquityHistDataUrl(symbol,
			String URL = getEquityHistDataUrl(symbol,
					tradewareTool.getCurrentMonthFromDateNewNseSite(),
					tradewareTool.getCurrentMonthToDateNewNseSite());
			 pageResponse = getWebClient().getPage(URL).getWebResponse().getContentAsString();
		}catch(Exception e) {
			try {
				getWebClient().getPage("https://www.nseindia.com");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			retrieveHistoricalDataForEquity(symbol);
		}
		return pageResponse;
	}
	
	static Double topHigh = 0d; static Double topLow = 0d; 
	public void retrievePreviousContractHistoricalData(String symbol, String kiteFutureKey) {
		try {
			topHigh = 0d;  topLow = 0d;
			String pageResponse = retrievePreviousContractHistoricalDataStep1(symbol);
			List<Narrow7DataBean>  list = processPositionalAvgData(symbol, pageResponse,  kiteFutureKey);
			 
			pageResponse = retrievePreviousContractHistoricalDataStep2(symbol);
			 list.addAll(processPositionalAvgData(symbol, pageResponse, kiteFutureKey));
			 nr7DataMap.put(kiteFutureKey, list);
		} catch (Exception e) {
			System.out.println(symbol + "   -    " + e.getMessage());
			failedResultDataMap.add(symbol);
		}
	}
	
	public String retrievePreviousContractHistoricalDataStep1(String symbol) {
		String pageResponse = null;
		try {
			//String URL = StockUtil.getDerivativeHistDataUrl(symbol,
			String URL = getDerivativeHistDataUrl(symbol,
					tradewareTool.getCurrentMonthExpiryDateNR7(),
					tradewareTool.getCurrentMonthFromDateNewNseSite(),
					tradewareTool.getCurrentMonthToDateNewNseSite());
			 pageResponse = getWebClient().getPage(URL).getWebResponse().getContentAsString();
		}catch(Exception e) {
			try {
				getWebClient().getPage("https://www.nseindia.com");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			retrievePreviousContractHistoricalDataStep1(symbol);
		}
		return pageResponse;
	}
	
	public String retrievePreviousContractHistoricalDataStep2(String symbol) {
		String pageResponse = null;
		try {
			//String URL = StockUtil.getDerivativeHistDataUrl(symbol,
			String URL = getDerivativeHistDataUrl(symbol,
					tradewareTool.getPreviousMonthExpiryDate(),
					tradewareTool.getPreviousMonthFromDateNewNseSite(),
					tradewareTool.getPreviousMonthToDateNewNseSite());
			 pageResponse = getWebClient().getPage(URL).getWebResponse().getContentAsString();
		}catch(Exception e) {
			try {
				getWebClient().getPage("https://www.nseindia.com");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pageResponse = retrievePreviousContractHistoricalDataStep2(symbol);
		}
		return pageResponse;
	}
	
	private JSONParser jsonParser = new JSONParser();

	private List<Narrow7DataBean> processPositionalAvgData(String symbol, String pageResponse, String mapKey) throws ParseException {
		List<Narrow7DataBean> Narrow7DataBeanList = new ArrayList<Narrow7DataBean>();
		//positinalDataBean.setKiteFutureKey(tradewareTool.getKiteFuturekey(symbol));
		double averageTurnover = ZERO_DOUBLE, averageContracts = ZERO_DOUBLE;

		JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
		JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
		Integer lotSize = null;
		for (int i = 0; i < rawJsonDataArray.size(); i++) {
			JSONObject jsonDataObject = (JSONObject) rawJsonDataArray.get(i);
		 lotSize = Integer.valueOf(String.valueOf(jsonDataObject.get(MARKET_LOT)));
			Narrow7DataBean dataBean = new Narrow7DataBean(lotSize, symbol);
			dataBean.setOpen(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(OPEN)))));
			dataBean.setHigh(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH)))));
			dataBean.setLow(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(LOW)))));
			dataBean.setClose(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(CLOSE)))));
			Double volumesTraded = ((Double.valueOf(String.valueOf(jsonDataObject.get(VOLUMES_TRADED)))) / lotSize);
			dataBean.setNumberOfContracts(volumesTraded);
			dataBean.setSettlePrice(new Double(String.valueOf(jsonDataObject.get(SETTLE_PRICE))));
			dataBean.setTurnoverActual(
					Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE)))));
			dataBean.setTurnover(new Double(kiteConnectTool.getRoundupToTwoValue(
					(Double.valueOf(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE)))) / 100000d)));
			
			dataBean.setTradingDate(String.valueOf(jsonDataObject.get(TRADNG_DATE)));

			// monthly average calculation
			averageContracts = averageContracts + dataBean.getNumberOfContracts().doubleValue();
			averageTurnover = averageTurnover + dataBean.getTurnover().doubleValue();
			
				Double high = Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH))));
				Double low = Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(LOW))));
				if (ZERO_INT == topHigh || high > topHigh) {
					topHigh = high;
				}
				if (ZERO_INT == topLow || low < topLow) {
					topLow = low;
				}
			
			Narrow7DataBeanList.add(dataBean);
		}
		
		tradewareTool.getNr7TradeDataMap().get(mapKey).setTopHigh(topHigh);
		tradewareTool.getNr7TradeDataMap().get(mapKey).setTopLow(topLow);
		
		return Narrow7DataBeanList;
	}
	
	private List<Narrow7DataBean> processHistDataForNR7Equity(String symbol, String pageResponse, Integer lotSize, String mapKey) throws ParseException {
		List<Narrow7DataBean> Narrow7DataBeanList = new ArrayList<Narrow7DataBean>();

		JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
		JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
		for (int i = 0; i < rawJsonDataArray.size(); i++) {
			JSONObject jsonDataObject = (JSONObject) rawJsonDataArray.get(i);
			Narrow7DataBean dataBean = new Narrow7DataBean(lotSize, symbol);
			dataBean.setOpen(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(OPEN_EQ)))));
			dataBean.setHigh(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH_EQ)))));
			dataBean.setLow(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(LOW_EQ)))));
			dataBean.setClose(new Double(Double.valueOf(String.valueOf(jsonDataObject.get(CLOSE_EQ)))));
			Double volumesTraded = ((Double.valueOf(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE_EQ)))) / lotSize);
			dataBean.setNumberOfContracts(volumesTraded);
			//dataBean.setSettlePrice(new Double(String.valueOf(jsonDataObject.get(SETTLE_PRICE))));
			dataBean.setTurnoverActual(
					Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE_EQ)))));
			dataBean.setTurnover(new Double(kiteConnectTool.getRoundupToTwoValue(
					(Double.valueOf(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE_EQ)))) / 100000d)));
			
			dataBean.setTradingDate(String.valueOf(jsonDataObject.get(TRADNG_DATE_EQ)));
			//dataBean.setV
			//WEEK52_HIGH_EQ

			
				Double high = Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH_EQ))));
				Double low = Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(LOW_EQ))));
				if (ZERO_INT == topHigh || high > topHigh) {
					topHigh = high;
				}
				if (ZERO_INT == topLow || low < topLow) {
					topLow = low;
				}
				
			Narrow7DataBeanList.add(dataBean);
		}
		
		tradewareTool.getNr7TradeDataMap().get(mapKey).setTopHigh(topHigh);
		tradewareTool.getNr7TradeDataMap().get(mapKey).setTopLow(topLow);
		
		return Narrow7DataBeanList;
	}
	
	public void processNarrow7Strategy() {
		for (String symbol : nr7DataMap.keySet()) {
			List<Narrow7DataBean> list = nr7DataMap.get(symbol);
			if (list.size() > 4) {
				//for (int i=list.size()-1;i>5;i--) {
				//for (int i=0;i<list.size()-SIX_INT;i++) {
				for (int i=0;i<list.size()-THREE_INT;i++) {
					Narrow7DataBean nr7CheckBean = tradewareTool.getNr7TradeDataMap().get(symbol).clone();
					nr7CheckBean.setTradingDate(list.get(i).getTradingDate());

					nr7CheckBean.setDay1High(list.get(i).getHigh());
					nr7CheckBean.setDay1Low(list.get(i).getLow());
					nr7CheckBean.setDay1Open(list.get(i).getOpen());
					nr7CheckBean.setDay1Close(list.get(i).getClose());
					
					nr7CheckBean.setDay2High(list.get(i + ONE_INT).getHigh());
					nr7CheckBean.setDay2Low(list.get(i + ONE_INT).getLow());
					nr7CheckBean.setDay2Open(list.get(i + ONE_INT).getOpen());
					nr7CheckBean.setDay2Close(list.get(i + ONE_INT).getClose());
					
					nr7CheckBean.setDay3High(list.get(i + TWO_INT).getHigh());
					nr7CheckBean.setDay3Low(list.get(i + TWO_INT).getLow());
					nr7CheckBean.setDay3Open(list.get(i + TWO_INT).getOpen());
					nr7CheckBean.setDay3Close(list.get(i + TWO_INT).getClose());
					
					nr7CheckBean.setDay4High(list.get(i + THREE_INT).getHigh());
					nr7CheckBean.setDay4Low(list.get(i + THREE_INT).getLow());
					nr7CheckBean.setDay4Open(list.get(i + TWO_INT).getOpen());
					nr7CheckBean.setDay4Close(list.get(i + TWO_INT).getClose());
					
					/*nr7CheckBean.setDay5High(list.get(i + FOUR_INT).getHigh());
					nr7CheckBean.setDay5Low(list.get(i + FOUR_INT).getLow());
					nr7CheckBean.setDay6High(list.get(i + FIVE_INT).getHigh());
					nr7CheckBean.setDay6Low(list.get(i + FIVE_INT).getLow());
					nr7CheckBean.setDay7High(list.get(i + SIX_INT).getHigh());
					nr7CheckBean.setDay7Low(list.get(i + SIX_INT).getLow());*/
					
					applyNarrow7StrategyRule(nr7CheckBean);
					//getNarrow7Map().put(symbol, nr7CheckBean);
					
				}
			} 
			Narrow7DataBean nr7CheckBean = tradewareTool.getNr7TradeDataMap().get(symbol).clone();
			if ((null == nr7CheckBean.getNarrow7RuleInd() || !nr7CheckBean.getNarrow7RuleInd()) && list.size() >= 2) {
				for (int i=0;i<list.size()-ONE_INT;i++) {
					nr7CheckBean.setTradingDate(list.get(i).getTradingDate());

					nr7CheckBean.setDay1High(list.get(i).getHigh());
					nr7CheckBean.setDay1Low(list.get(i).getLow());
					nr7CheckBean.setDay1Open(list.get(i).getOpen());
					nr7CheckBean.setDay1Close(list.get(i).getClose());
					
					nr7CheckBean.setDay2High(list.get(i + ONE_INT).getHigh());
					nr7CheckBean.setDay2Low(list.get(i + ONE_INT).getLow());
					nr7CheckBean.setDay2Open(list.get(i + ONE_INT).getOpen());
					nr7CheckBean.setDay2Close(list.get(i + ONE_INT).getClose());
					nr7CheckBean.setStrategyRule("ORB");
					verifyEngulfingStrategyRule(nr7CheckBean);
				}
			}

		}
	}
	//TODO : This strategy rule logic should be some other class like util
		private void applyNarrow7StrategyRule(Narrow7DataBean bean) {
			double day1HighLowDiff = bean.getDay1High().doubleValue() - bean.getDay1Low().doubleValue();
			double day2HighLowDiff = bean.getDay2High().doubleValue() - bean.getDay2Low().doubleValue();
			double day3HighLowDiff = bean.getDay3High().doubleValue() - bean.getDay3Low().doubleValue();
			double day4HighLowDiff = bean.getDay4High().doubleValue() - bean.getDay4Low().doubleValue();
			/*double day5HighLowDiff = bean.getDay5High().doubleValue() - bean.getDay5Low().doubleValue();
			double day6HighLowDiff = bean.getDay6High().doubleValue() - bean.getDay6Low().doubleValue();
			double day7HighLowDiff = bean.getDay7High().doubleValue() - bean.getDay7Low().doubleValue();*/

			// Rule 1
			if (day1HighLowDiff < day2HighLowDiff && day1HighLowDiff < day3HighLowDiff && day1HighLowDiff < day4HighLowDiff
					/*&& day1HighLowDiff < day5HighLowDiff && day1HighLowDiff < day6HighLowDiff
					&& day1HighLowDiff < day7HighLowDiff*/) {
				// Rule 2
				if (((bean.getDay1High().doubleValue() < bean.getDay2High().doubleValue())
						&& (bean.getDay1Low().doubleValue() > bean.getDay2Low().doubleValue())) /*|| 
						((bean.getDay1High().doubleValue() > bean.getDay2High().doubleValue())
								&& (bean.getDay1Low().doubleValue() < bean.getDay2Low().doubleValue()))*/) {
					bean.setStrategyRule("NR7");
					verifyEngulfingStrategyRule(bean);
					/*tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setNarrow7RuleInd(Boolean.TRUE);
					tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getNr7TradeDates().add(bean.getTradingDate());
					if (tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getBuyAtVal() == null) {
						tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setBuyAtVal(bean.getDay1High().doubleValue());
						tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setSellAtVal(bean.getDay1Low().doubleValue());
					}*/
//	System.out.println(bean.getSymbolName()+" : "+ bean.getTradingDate());
					
				}
			}
		}
	/*
	private void processNarrow7Strategy() {
		for (String symbol : wholeLast5DaysDataOfEachStock.keySet()) {
			if (null != last5DaysResultDataMap.get(symbol)) {
				Narrow7DataBean nr7Bean = new Narrow7DataBean(last5DaysResultDataMap.get(symbol));
				List<Narrow7DataBean> list = wholeLast5DaysDataOfEachStock.get(symbol);
				nr7Bean.setDay1High(list.get(ZERO_INT).getHigh());
				nr7Bean.setDay1Low(list.get(ZERO_INT).getLow());
				nr7Bean.setDay2High(list.get(ONE_INT).getHigh());
				nr7Bean.setDay2Low(list.get(ONE_INT).getLow());
				nr7Bean.setDay3High(list.get(TWO_INT).getHigh());
				nr7Bean.setDay3Low(list.get(TWO_INT).getLow());
				nr7Bean.setDay4High(list.get(THREE_INT).getHigh());
				nr7Bean.setDay4Low(list.get(THREE_INT).getLow());
				nr7Bean.setDay5High(list.get(FOUR_INT).getHigh());
				nr7Bean.setDay5Low(list.get(FOUR_INT).getLow());
				nr7Bean.setDay6High(list.get(FIVE_INT).getHigh());
				nr7Bean.setDay6Low(list.get(FIVE_INT).getLow());
				nr7Bean.setDay7High(list.get(SIX_INT).getHigh());
				nr7Bean.setDay7Low(list.get(SIX_INT).getLow());
				applyNarrow7StrategyRule(nr7Bean);
				getNarrow7Map().put(symbol, nr7Bean);
			}
		}
	}
	
	//TODO : This strategy rule logic should be some other class like util
	private void applyNarrow7StrategyRule(Narrow7DataBean bean) {
		double day1HighLowDiff = bean.getDay1High().doubleValue() - bean.getDay1Low().doubleValue();
		double day2HighLowDiff = bean.getDay2High().doubleValue() - bean.getDay2Low().doubleValue();
		double day3HighLowDiff = bean.getDay3High().doubleValue() - bean.getDay3Low().doubleValue();
		double day4HighLowDiff = bean.getDay4High().doubleValue() - bean.getDay4Low().doubleValue();
		double day5HighLowDiff = bean.getDay5High().doubleValue() - bean.getDay5Low().doubleValue();
		double day6HighLowDiff = bean.getDay6High().doubleValue() - bean.getDay6Low().doubleValue();
		double day7HighLowDiff = bean.getDay7High().doubleValue() - bean.getDay7Low().doubleValue();

		// Rule 1
		if (day1HighLowDiff < day2HighLowDiff && day1HighLowDiff < day3HighLowDiff && day1HighLowDiff < day4HighLowDiff
				&& day1HighLowDiff < day5HighLowDiff && day1HighLowDiff < day6HighLowDiff
				&& day1HighLowDiff < day7HighLowDiff) {
			// Rule 2
			if ((bean.getDay1High().doubleValue() < bean.getDay2High().doubleValue())
					&& (bean.getDay1Low().doubleValue() > bean.getDay2Low().doubleValue())) {
				bean.setNarrow7Rule(Boolean.TRUE);
			}
		}
	}
	
	private void processNR7orUTData(Narrow7StockDataBean bean, DomNodeList<DomElement> dataRowElements) {
		double topLow = 0;
		double topHigh = 0;

		for (int i = 1; i < dataRowElements.size(); i++) {
			HtmlTableRow rowElement = (HtmlTableRow) dataRowElements.get(i);
			List<HtmlTableCell> rowElementDataCells = rowElement.getCells();
			Double open = Double.valueOf(rowElementDataCells.get(OPEN_INDEX).asText().replaceAll(COMMA, EMPTY_STRING));
			Double high = Double.valueOf(rowElementDataCells.get(HIGH_INDEX).asText().replaceAll(COMMA, EMPTY_STRING));
			Double low = Double.valueOf(rowElementDataCells.get(LOW_INDEX).asText().replaceAll(COMMA, EMPTY_STRING));
			Double close = Double.valueOf(rowElementDataCells.get(CLOSE_INDEX).asText().replaceAll(COMMA, EMPTY_STRING));
			
			if (high.doubleValue() > topHigh) {
				topHigh = high.doubleValue();
				bean.setTopHigh(high);
			}
			if (topLow == 0 || low.doubleValue() < topLow) {
				topLow = low.doubleValue();
				bean.setTopLow(low);
			}
			
			if (i <= 7) {
				if (i == 1) {
					bean.setDay1Open(open);
					bean.setDay1High(high);
					bean.setDay1Low(low);
					bean.setDay1Close(close);
				} else if (i == 2) {
					bean.setDay2Open(open);
					bean.setDay2High(high);
					bean.setDay2Low(low);
					bean.setDay2Close(close);
				} else if (i == 3) {
					bean.setDay3Open(open);
					bean.setDay3High(high);
					bean.setDay3Low(low);
					bean.setDay3Close(close);
				} else if (i == 4) {
					bean.setDay4Open(open);
					bean.setDay4High(high);
					bean.setDay4Low(low);
					bean.setDay4Close(close);
				} else if (i == 5) {
					bean.setDay5Open(open);
					bean.setDay5High(high);
					bean.setDay5Low(low);
					bean.setDay5Close(close);
				} else if (i == 6) {
					bean.setDay6Open(open);
					bean.setDay6High(high);
					bean.setDay6Low(low);
					bean.setDay6Close(close);
				} else if (i == 7) {
					bean.setDay7Open(open);
					bean.setDay7High(high);
					bean.setDay7Low(low);
					bean.setDay7Close(close);
				}
			}
		}
	}*/

	
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
	
	private static final String OPEN = "FH_OPENING_PRICE";
	private static final String HIGH = "FH_TRADE_HIGH_PRICE";
	private static final String LOW = "FH_TRADE_LOW_PRICE";
	private static final String CLOSE = "FH_CLOSING_PRICE";
	private static final String VOLUMES_TRADED = "FH_TOT_TRADED_QTY";
	private static final String TRADNG_DATE = "FH_TIMESTAMP";
	private static final String SETTLE_PRICE = "FH_SETTLE_PRICE";
	private static final String TOTAL_TRADED_VALUE = "FH_TOT_TRADED_VAL";
	private static final String MARKET_LOT = "FH_MARKET_LOT";
	
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
	
	private static final String OPEN_EQ = "CH_OPENING_PRICE";
	private static final String HIGH_EQ = "CH_TRADE_HIGH_PRICE";
	private static final String LOW_EQ = "CH_TRADE_LOW_PRICE";
	private static final String CLOSE_EQ = "CH_CLOSING_PRICE";
	private static final String TOTAL_TRADED_VALUE_EQ = "CH_TOT_TRADED_VAL";
	private static final String TRADNG_DATE_EQ = "mTIMESTAMP";
	private static final String WEEK52_HIGH_EQ = "CH_52WEEK_HIGH_PRICE";
	private static final String WEEK52_LOW_EQ= "CH_52WEEK_LOW_PRICE";
	private static final String VWAP_EQ=  "VWAP";
	
	
	
	// Phase 5 :: 05-15-2021 start - afterSomeSuccess
		public void verifyEngulfingStrategyRule(Narrow7DataBean bean) {
			if (null != bean && null != bean.getDay2High() && null != bean.getDay1High()
					&& null != bean.getDay2Close() && null != bean.getDay1Close() && null != bean.getDay2Open()
					&& null != bean.getDay1Open()) {
				// forward type mother candle
				if ((bean.getDay1High().doubleValue() < bean.getDay2High().doubleValue())
						&& (bean.getDay1Low().doubleValue() > bean.getDay2Low().doubleValue())) {
					if ((bean.getDay2Open() < bean.getDay2Close()
							&& bean.getDay2Close() >= bean.getDay1Open()
							&& bean.getDay2Close() >= bean.getDay1Close()
							&& bean.getDay2Open() <= bean.getDay1Open()
							&& bean.getDay2Open() <= bean.getDay1Close()
							&& bean.getDay2Open() <= bean.getDay1Low() )
							|| (bean.getDay2Open() > bean.getDay2Close()
									&& bean.getDay2Open() >= bean.getDay1Open()
									&& bean.getDay2Open() >= bean.getDay1Close()
									&& bean.getDay2Close() <= bean.getDay1Open()
									&& bean.getDay2Close() <= bean.getDay1Close()
											&& bean.getDay2Open() >= bean.getDay1High())) {
						
						//TODO
						if (bean.getDay2Open() < bean.getDay2Close() && 
								bean.getDay1Open() > bean.getDay1Close()
								&& bean.getDay2Open() <= bean.getDay1Low() ) {
							//tradable buy
							bean.setTradableStateId("BUY");
							tradewareTool.getNr7TradeDataMap().put(bean.getKiteFutureKey(), bean);
							tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setNarrow7RuleInd(Boolean.TRUE);
							tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getNr7TradeDates().add(bean.getTradingDate());
							if (tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getBuyAtVal() == null) {
								tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setBuyAtVal(bean.getDay1High().doubleValue());
								tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setSellAtVal(bean.getDay1Low().doubleValue());
							}
						}
						else if (bean.getDay2Open() > bean.getDay2Close() 
								&& bean.getDay1Open() < bean.getDay1Close()
								&& bean.getDay2Open() <= bean.getDay1High()) {
							bean.setTradableStateId("SELL");
							tradewareTool.getNr7TradeDataMap().put(bean.getKiteFutureKey(), bean);
							tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setNarrow7RuleInd(Boolean.TRUE);
							tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getNr7TradeDates().add(bean.getTradingDate());
							if (tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getBuyAtVal() == null) {
								tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setBuyAtVal(bean.getDay1High().doubleValue());
								tradewareTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setSellAtVal(bean.getDay1Low().doubleValue());
							}
						}
					}
				}
			}
		}
		
		
		
		
		
		
		
		
		private static final String FROM_DATE = "FROM_DATE";
		private static final String TO_DATE = "TO_DATE";
		private static final String EXPIRY_DATE = "EXPIRY_DATE";
		
		private static final String DERIVATIVE_HIST_DATA_URL = 
				"https://www.nseindia.com/api/historical/fo/derivatives?&from=FROM_DATE&to=TO_DATE&expiryDate=EXPIRY_DATE&instrumentType=FUTSTK&symbol=";
		
		private static final String EQUITY_HIST_DATA_URL = 
				"https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=FROM_DATE&to=TO_DATE&symbol=";
		
		static String historicalDataUrlAjaxSample, intradayDataUrlAjaxSample;// Temporary indicator
		public static String getDerivativeHistDataUrl(String symbol, String currentMonthExpiry, String fromDate,
				String toDate) {
			String historicalDataUrlAjax = DERIVATIVE_HIST_DATA_URL.replaceAll(FROM_DATE, fromDate)
					.replaceAll(TO_DATE, toDate).replaceAll(EXPIRY_DATE, currentMonthExpiry) + StockUtil.getSymbolForURL(symbol);
			if (null == historicalDataUrlAjaxSample) {
				historicalDataUrlAjaxSample = historicalDataUrlAjax;
				System.out.println(historicalDataUrlAjaxSample);
			}

			if ("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol) || "FINNIFTY".equals(symbol)) {
				historicalDataUrlAjax = historicalDataUrlAjax.replace("FUTSTK", "FUTIDX");
			}
			return historicalDataUrlAjax;
		}
		
		public static String getEquityHistDataUrl(String symbol, String fromDate,
				String toDate) {
			String historicalDataUrlAjax = EQUITY_HIST_DATA_URL.replaceAll(FROM_DATE, fromDate)
					.replaceAll(TO_DATE, toDate) + StockUtil.getSymbolForURL(symbol);
			if (null == historicalDataUrlAjaxSample) {
				historicalDataUrlAjaxSample = historicalDataUrlAjax;
				System.out.println(historicalDataUrlAjaxSample);
			}

			if ("NIFTY".equals(symbol) || "BANKNIFTY".equals(symbol) || "FINNIFTY".equals(symbol)) {
				historicalDataUrlAjax = historicalDataUrlAjax.replace("FUTSTK", "FUTIDX");
			}
			return historicalDataUrlAjax;
		}
		
		
		
		private double previousClose;
		private double lowForK;
		private double highForK;
		
		private double valueK1;
		private double valueD3;
		private double valueK2;
		private double valueD4;
		private double valueK3;
		private double valueD5;
		private double valueK4;
		private double valueK5;
		private String stochasticTrend; 
		
		private double valueK1Temp;
		private double valueK2Temp;
		private double valueK3Temp;
		
		private void clear() {
			previousClose = 0;
			lowForK = 0;
			highForK = 0;
		}
		private void clearValues() {
			valueK1 = 0;
			valueD3 = 0;
			valueK2 = 0;
			valueD4 = 0;
			valueK3 = 0;
			valueD5 = 0;
			valueK4 = 0;
			valueK5 = 0;
			valueK1Temp = 0;
			valueK2Temp = 0;
			valueK3Temp = 0;
			stochasticTrend = Constants.NA;
		}
		private double caluclateStochasticK(double prvClose, double topHigh, double topLow) {
			return ((prvClose - topLow) / (topHigh - (topLow != 0 ? topLow : 1))) * 100;
		}

	protected void findStochasticTrend(ConcurrentHashMap<String, List<Narrow7DataBean>> nr7DataMap) {
		clear();
		clearValues();
		try {
			for (String symbol : nr7DataMap.keySet()) {
				List<Narrow7DataBean> listBean = nr7DataMap.get (symbol);
				if (null != listBean && listBean.size() >= 9) {
					Narrow7DataBean data = listBean.get(0);
					double high1 = data.getHigh();
					double low1 = data.getLow();
					double close1 = data.getClose();

					data = listBean.get(1);
					double high2 = data.getHigh();
					double low2 = data.getLow();
					double close2 = data.getClose();

					data = listBean.get(2);
					double high3 = data.getHigh();
					double low3 = data.getLow();
					double close3 = data.getClose();

					data = listBean.get(3);
					double high4 = data.getHigh();
					double low4 = data.getLow();
					double close4 = data.getClose();

					data = listBean.get(4);
					double high5 = data.getHigh();
					double low5 = data.getLow();
					double close5 = data.getClose();

					data = listBean.get(5);
					double high6 = data.getHigh();
					double low6 = data.getLow();

					data = listBean.get(6);
					double high7 = data.getHigh();
					double low7 = data.getLow();

					data = listBean.get(7);
					double high8 = data.getHigh();
					double low8 = data.getLow();

					data = listBean.get(8);
					double high9 = data.getHigh();
					double low9 = data.getLow();

					previousClose = close1;
					lowForK = Math.min(Math.min(Math.min(Math.min(low1, low2), low3), low4), low5);
					highForK = Math.max(Math.max(Math.max(Math.max(high1, high2), high3), high4), high5);
					valueK1 = caluclateStochasticK(previousClose, highForK, lowForK);

					clear();
					previousClose = close2;
					lowForK = Math.min(Math.min(Math.min(Math.min(low2, low3), low4), low5), low6);
					highForK = Math.max(Math.max(Math.max(Math.max(high2, high3), high4), high5), high6);
					valueK2 = caluclateStochasticK(previousClose, highForK, lowForK);

					clear();
					previousClose = close3;
					lowForK = Math.min(Math.min(Math.min(Math.min(low3, low4), low5), low6), low7);
					highForK = Math.max(Math.max(Math.max(Math.max(high3, high4), high5), high6), high7);
					valueK3 = caluclateStochasticK(previousClose, highForK, lowForK);

					clear();
					previousClose = close4;
					lowForK = Math.min(Math.min(Math.min(Math.min(low4, low5), low6), low7), low8);
					highForK = Math.max(Math.max(Math.max(Math.max(high4, high5), high6), high7), high8);
					valueK4 = caluclateStochasticK(previousClose, highForK, lowForK);

					previousClose = close5;
					lowForK = Math.min(Math.min(Math.min(Math.min(low5, low6), low7), low8), low9);
					highForK = Math.max(Math.max(Math.max(Math.max(high5, high6), high7), high8), high9);
					valueK5 = caluclateStochasticK(previousClose, highForK, lowForK);

					valueK1Temp = kiteConnectTool.getRoundupToTwoValue(((valueK1 + valueK2 + valueK3) / 3));
					valueK2Temp = kiteConnectTool.getRoundupToTwoValue(((valueK2 + valueK3 + valueK4) / 3));
					valueK3Temp = kiteConnectTool.getRoundupToTwoValue(((valueK3 + valueK4 + valueK5) / 3));
					
					valueK1 = valueK1Temp;
					valueK2 = valueK2Temp;
					valueK3 = valueK3Temp;
					
					valueD3 = kiteConnectTool.getRoundupToTwoValue(((valueK1 + valueK2 + valueK3) / 3));
					valueD4  = kiteConnectTool.getRoundupToTwoValue(((valueK2 + valueK3 + valueK4) / 3));
					valueD5 = kiteConnectTool.getRoundupToTwoValue(((valueK3 + valueK4 + valueK5) / 3));
					/*
					 * if (valueAvgK > valueAvgD && valueAvgK < 40) { stochasticTrend = BUY; } else
					 * if (valueAvgK < valueAvgD && valueAvgK > 60) { stochasticTrend = SELL; }
					 */

					if (valueK3 > valueD3 && valueK1 < 40 && (valueK3 < valueK2 && valueK2 < valueK1)) {
						stochasticTrend = "VS_BUY";
					} else if (valueK1 < valueD3 && valueK1 > 60 && (valueK3 > valueK2 && valueK2 > valueK1)) {
						stochasticTrend = "VS_SELL";
					}

					else if (valueK1 > valueD3 && valueK1 < 40) {
						stochasticTrend = "BUY";
					} else if (valueK1 < valueD3 && valueK1 > 60) {
						stochasticTrend = "SELL";
					}
					Narrow7DataBean bean = tradewareTool.getNr7TradeDataMap().get(symbol);
					bean.setMin60StochasticValK1(kiteConnectTool.getRoundupToTwoValue(valueK1));
					bean.setMin60StochasticValD3(kiteConnectTool.getRoundupToTwoValue(valueD3));
					bean.setMin60StochasticValK2(kiteConnectTool.getRoundupToTwoValue(valueK2));
					bean.setMin60StochasticValD4(kiteConnectTool.getRoundupToTwoValue(valueD4));
					bean.setMin60StochasticValK3(kiteConnectTool.getRoundupToTwoValue(valueK3));
					bean.setMin60StochasticValD5(kiteConnectTool.getRoundupToTwoValue(valueD5));
					bean.setMin60StochasticTrend(stochasticTrend);
					tradewareTool.getNr7TradeDataMap().replace(symbol, bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void caluclateRSI(ConcurrentHashMap<String, List<Narrow7DataBean>> nr7DataMap) {
		try {
			
			for (String symbol : nr7DataMap.keySet()) {
				List<Narrow7DataBean> listBean = nr7DataMap.get (symbol);
				if (null != listBean && listBean.size() >= 20) {
					Narrow7DataBean bean = tradewareTool.getNr7TradeDataMap().get(symbol);
					double change = 0d, uptrend = 0d, downtrend = 0d, avgUptrend = 0d, avgDowntrend = 0d, rsi = 0d;
					Map<Integer, Double> uptrendMap = new HashMap<Integer, Double>();
					Map<Integer, Double> downtrendMap = new HashMap<Integer, Double>();
					for (int i = 0; i < listBean.size(); i++) {
						double close_1 = listBean.get(i).getClose();
						double close = listBean.get(i + 1).getClose();
						change = close - close_1;
						if (i < 15) {
							if (change > 0) {
								uptrendMap.put(i, change);
								downtrendMap.put(i, 0d);
							} else {
								uptrendMap.put(i, 0d);
								downtrendMap.put(i, Math.abs(change));
							}
						}
						
						if (i == 15) {
							uptrend = 0d; downtrend = 0d; avgUptrend = 0d; avgDowntrend = 0d; rsi = 0d;
							for (int count1 = 0; count1 < uptrendMap.size(); count1++) {
								uptrend = uptrend + uptrendMap.get(count1);
								downtrend = downtrend + downtrendMap.get(count1);
							}
							avgUptrend = uptrend / 14;
							avgDowntrend = downtrend / 14;
							rsi = 100 - (100 / ((avgUptrend / avgDowntrend) + 1));
							bean.setRsi1(kiteConnectTool.getRoundupToTwoValue(rsi));
						} else if (i == 16) {
							uptrend = 0d; downtrend = 0d; avgUptrend = 0d; avgDowntrend = 0d; rsi = 0d;
							for (int count1 = 1; count1 < uptrendMap.size(); count1++) {
								uptrend = uptrend + uptrendMap.get(count1);
								downtrend = downtrend + downtrendMap.get(count1);
							}
							avgUptrend = uptrend / 14;
							avgDowntrend = downtrend / 14;
							rsi = 100 - (100 / ((avgUptrend / avgDowntrend) + 1));
							bean.setRsi2(kiteConnectTool.getRoundupToTwoValue(rsi));
						} else if (i == 17) {
							uptrend = 0d; downtrend = 0d; avgUptrend = 0d; avgDowntrend = 0d; rsi = 0d;
							for (int count1 = 2; count1 < uptrendMap.size(); count1++) {
								uptrend = uptrend + uptrendMap.get(count1);
								downtrend = downtrend + downtrendMap.get(count1);
							}
							avgUptrend = uptrend / 14;
							avgDowntrend = downtrend / 14;
							rsi = 100 - (100 / ((avgUptrend / avgDowntrend) + 1));
							bean.setRsi3(kiteConnectTool.getRoundupToTwoValue(rsi));
							break;
						}
						
						
							/*if (i < 15) {
							if (change > 0) {
								uptrend = uptrend + change;
							} else {
								downtrend = downtrend + Math.abs(change);
							}
						}if (i == 15) {
							avgUptrend = uptrend / 14;
							avgDowntrend = downtrend / 14;
						}
						if (i == 15 || i == 16 || i == 17) {
							// EMA based
							if (change > 0) {
								avgUptrend = ((avgUptrend * 13) + change) / 14;
								avgDowntrend = (avgDowntrend * 13) / 14;
								rsi = 100 - (100 / ((avgUptrend / avgDowntrend) + 1));
							} else {
								avgDowntrend = ((avgDowntrend * 13) + Math.abs(change)) / 14;
								avgUptrend = (avgUptrend * 13) / 14;
								rsi = 100 - (100 / ((avgUptrend / avgDowntrend) + 1));
							}

							if (i == 15) {
								bean.setRsi1(kiteConnectTool.getRoundupToTwoValue(rsi));
							} else if (i == 16) {
								bean.setRsi2(kiteConnectTool.getRoundupToTwoValue(rsi));
							} else if (i == 17) {
								bean.setRsi3(kiteConnectTool.getRoundupToTwoValue(rsi));
								break;
							}
						}*/
					}
					
					bean.setMin60RsiTrend("NA");
					if (null != bean.getRsi1() && bean.getRsi1() < 30) {
						bean.setMin60RsiTrend("BUY");
					} else if (null != bean.getRsi1() && bean.getRsi1() > 70) {
						bean.setMin60RsiTrend("SELL");
					}
					
					if (null != bean.getRsi2() && null != bean.getRsi3()) {
						if ((null != bean.getRsi1() && bean.getRsi1() < 30)
								&& (bean.getRsi1() > bean.getRsi2() || bean.getRsi2() > bean.getRsi3())) {
							bean.setMin60RsiTrend("VS_BUY");
						} else if ((null != bean.getRsi1() && bean.getRsi1() > 70)
								&& (bean.getRsi1() < bean.getRsi2() || bean.getRsi2() < bean.getRsi3())) {
							bean.setMin60RsiTrend("VS_SELL");
						}
					}
					
					if (null != bean.getRsi2() && null != bean.getRsi3()) {
						if ((null != bean.getRsi1() && bean.getRsi1() < 30)
								&& (bean.getRsi1() > bean.getRsi2() && bean.getRsi2() > bean.getRsi3())) {
							bean.setMin60RsiTrend("VVS_BUY");
						} else if ((null != bean.getRsi1() && bean.getRsi1() > 70)
								&& (bean.getRsi1() < bean.getRsi2() && bean.getRsi2() < bean.getRsi3())) {
							bean.setMin60RsiTrend("VVS_SELL");
						}
					}
						
					tradewareTool.getNr7TradeDataMap().replace(symbol, bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Duplicate method
		protected void findHeikinAshiTrend(ConcurrentHashMap<String, List<Narrow7DataBean>> nr7DataMap) {
			try {
				
				for (String symbol : nr7DataMap.keySet()) {
					List<Narrow7DataBean> listBean = nr7DataMap.get (symbol);
					if (null != listBean && listBean.size() >= 4) {
						Narrow7DataBean data = listBean.get(0);
						double open3 = data.getOpen();
						double high3 = data.getHigh();
						double low3 = data.getLow();
						double close3 = data.getClose();
						
						data = listBean.get(1);
						double open2 = data.getOpen();
						double high2 = data.getHigh();
						double low2 = data.getLow();
						double close2 = data.getClose();

						data = listBean.get(2);
						double open1 = data.getOpen();
						double high1 = data.getHigh();
						double low1 = data.getLow();
						double close1 = data.getClose();

						data = listBean.get(3);
						double open0 = data.getOpen();
						double close0 = data.getClose();

						double heikenAshiOpen3 = kiteConnectTool.getRoundupToTwoValue((open2 + close2) / 2);
						double heikenAshiClose3 = kiteConnectTool.getRoundupToTwoValue((open3 + high3 + low3 + close3) / 4);
						double heikenAshiHigh3 = Math.max(Math.max(heikenAshiOpen3, heikenAshiClose3), high3);
						double heikenAshiLow3 = Math.min(Math.min(heikenAshiOpen3, heikenAshiClose3), low3);

						double heikenAshiOpen2 = kiteConnectTool.getRoundupToTwoValue((open1 + close1) / 2);
						double heikenAshiClose2 = kiteConnectTool.getRoundupToTwoValue((open2 + high2 + low2 + close2) / 4);
						double heikenAshiHigh2 = Math.max(Math.max(heikenAshiOpen2, heikenAshiClose2), high2);
						double heikenAshiLow2 = Math.min(Math.min(heikenAshiOpen2, heikenAshiClose2), low2);

						double heikenAshiOpen1 = kiteConnectTool.getRoundupToTwoValue((open0 + close0) / 2);
						double heikenAshiClose1 = kiteConnectTool.getRoundupToTwoValue((open1 + high1 + low1 + close1) / 4);
						double heikenAshiHigh1 = Math.max(Math.max(heikenAshiOpen1, heikenAshiClose1), high1);
						double heikenAshiLow1 = Math.min(Math.min(heikenAshiOpen1, heikenAshiClose1), low1);
						
						Narrow7DataBean bean = tradewareTool.getNr7TradeDataMap().get(symbol);
						if (heikenAshiClose3 > heikenAshiOpen3 && heikenAshiClose2 > heikenAshiOpen2
								&& heikenAshiClose1 > heikenAshiOpen1) {
							bean.setMin60HeikinAshiTrendId("BUY");
							if (heikenAshiOpen3 == heikenAshiLow3 && heikenAshiOpen2 == heikenAshiLow2
									&& heikenAshiOpen1 == heikenAshiLow1) {
								if (heikenAshiClose3 > heikenAshiClose2 && heikenAshiClose2 > heikenAshiClose1) {
									bean.setMin60HeikinAshiTrendId("VS_BUY");
									if (/*(((heikenAshiHigh3 - heikenAshiClose3) < (heikenAshiClose3 - heikenAshiOpen3))
											&& ((heikenAshiHigh2 - heikenAshiClose2) < (heikenAshiClose2 - heikenAshiOpen2))
											&& ((heikenAshiHigh1 - heikenAshiClose1) < (heikenAshiClose1 - heikenAshiOpen1)))
											||*/ (((heikenAshiClose3 - heikenAshiOpen3) < (heikenAshiClose2 - heikenAshiOpen2))
													&& ((heikenAshiClose1 - heikenAshiOpen1) < (heikenAshiClose1
															- heikenAshiOpen1)))) {
										bean.setMin60HeikinAshiTrendId("VVS_BUY");
									}
								}
							}

						} else if (heikenAshiClose3 < heikenAshiOpen3 && heikenAshiClose2 < heikenAshiOpen2
								&& heikenAshiClose1 < heikenAshiOpen1) {
							bean.setMin60HeikinAshiTrendId("SELL");
							if (heikenAshiOpen3 == heikenAshiHigh3 && heikenAshiOpen2 == heikenAshiHigh2
									&& heikenAshiOpen1 == heikenAshiHigh1) {
								if (heikenAshiClose3 < heikenAshiClose2 && heikenAshiClose2 < heikenAshiClose1) {
									bean.setMin60HeikinAshiTrendId("VS_SELL");
									if (/*(((heikenAshiHigh3 - heikenAshiClose3) > (heikenAshiClose3 - heikenAshiLow3))
											&& ((heikenAshiHigh2 - heikenAshiClose2) > (heikenAshiClose2 - heikenAshiLow2))
											&& ((heikenAshiHigh1 - heikenAshiClose1) > (heikenAshiClose1 - heikenAshiLow1)))
											||*/ (((heikenAshiOpen3 - heikenAshiClose3) > (heikenAshiOpen2 - heikenAshiClose2))
													&& ((heikenAshiOpen2 - heikenAshiClose2) > (heikenAshiOpen1
															- heikenAshiClose1)))) {
										bean.setMin60HeikinAshiTrendId("VVS_SELL");
									}
								}
							}
						} else {
							bean.setMin60HeikinAshiTrendId("NA");
						}
						tradewareTool.getNr7TradeDataMap().replace(symbol, bean);
					}
				}
				// 05-29-2021 end - afterSomeSuccess
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
}
