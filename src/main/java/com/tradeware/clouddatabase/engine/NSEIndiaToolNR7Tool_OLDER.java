package com.tradeware.clouddatabase.engine;

import java.util.ArrayList;
import java.util.List;
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
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaToolNR7Tool_OLDER {
	@Autowired
	private TradewareTool sathvikAshvikTechTraderTool;
	
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void retrieveHistoricalDataForNR7Tradees() throws ParseException {
		ConcurrentHashMap<String, Narrow7DataBean> map = sathvikAshvikTechTraderTool.getNr7TradeDataMap();
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
			String URL = StockUtil.getEquityHistDataUrl(symbol,
					sathvikAshvikTechTraderTool.getCurrentMonthFromDateNewNseSite(),
					sathvikAshvikTechTraderTool.getCurrentMonthToDateNewNseSite());
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
			String URL = StockUtil.getDerivativeHistDataUrl(symbol,
					sathvikAshvikTechTraderTool.getCurrentMonthExpiryDateNR7(),
					sathvikAshvikTechTraderTool.getCurrentMonthFromDateNewNseSite(),
					sathvikAshvikTechTraderTool.getCurrentMonthToDateNewNseSite());
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
			String URL = StockUtil.getDerivativeHistDataUrl(symbol,
					sathvikAshvikTechTraderTool.getPreviousMonthExpiryDate(),
					sathvikAshvikTechTraderTool.getPreviousMonthFromDateNewNseSite(),
					sathvikAshvikTechTraderTool.getPreviousMonthToDateNewNseSite());
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
		//positinalDataBean.setKiteFutureKey(sathvikAshvikTechTraderTool.getKiteFuturekey(symbol));
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
		
		sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(mapKey).setTopHigh(topHigh);
		sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(mapKey).setTopLow(topLow);
		
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
		
		sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(mapKey).setTopHigh(topHigh);
		sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(mapKey).setTopLow(topLow);
		
		return Narrow7DataBeanList;
	}
	
	public void processNarrow7Strategy() {
		for (String symbol : nr7DataMap.keySet()) {
			List<Narrow7DataBean> list = nr7DataMap.get(symbol);
			if (list.size() > 4) {
				//for (int i=list.size()-1;i>5;i--) {
				//for (int i=0;i<list.size()-SIX_INT;i++) {
				for (int i=0;i<list.size()-THREE_INT;i++) {
					Narrow7DataBean nr7CheckBean = sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(symbol).clone();
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
			Narrow7DataBean nr7CheckBean = sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(symbol).clone();
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
					/*sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setNarrow7RuleInd(Boolean.TRUE);
					sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getNr7TradeDates().add(bean.getTradingDate());
					if (sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getBuyAtVal() == null) {
						sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setBuyAtVal(bean.getDay1High().doubleValue());
						sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setSellAtVal(bean.getDay1Low().doubleValue());
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
	//private static final String WEEK52_HIGH_EQ = "CH_52WEEK_HIGH_PRICE";
	//private static final String WEEK52_LOW_EQ= "CH_52WEEK_LOW_PRICE";
	//private static final String VWAP_EQ=  "VWAP";
	
	
	
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
							sathvikAshvikTechTraderTool.getNr7TradeDataMap().put(bean.getKiteFutureKey(), bean);
							sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setNarrow7RuleInd(Boolean.TRUE);
							sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getNr7TradeDates().add(bean.getTradingDate());
							if (sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getBuyAtVal() == null) {
								sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setBuyAtVal(bean.getDay1High().doubleValue());
								sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setSellAtVal(bean.getDay1Low().doubleValue());
							}
						}
						else if (bean.getDay2Open() > bean.getDay2Close() 
								&& bean.getDay1Open() < bean.getDay1Close()
								&& bean.getDay2Open() <= bean.getDay1High()) {
							bean.setTradableStateId("SELL");
							sathvikAshvikTechTraderTool.getNr7TradeDataMap().put(bean.getKiteFutureKey(), bean);
							sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setNarrow7RuleInd(Boolean.TRUE);
							sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getNr7TradeDates().add(bean.getTradingDate());
							if (sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).getBuyAtVal() == null) {
								sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setBuyAtVal(bean.getDay1High().doubleValue());
								sathvikAshvikTechTraderTool.getNr7TradeDataMap().get(bean.getKiteFutureKey()).setSellAtVal(bean.getDay1Low().doubleValue());
							}
						}
					}
				}
			}
		}
		
}
