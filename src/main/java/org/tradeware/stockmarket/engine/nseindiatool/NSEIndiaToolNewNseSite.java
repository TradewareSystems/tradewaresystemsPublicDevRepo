package org.tradeware.stockmarket.engine.nseindiatool;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tradeware.stockmarket.bean.Narrow7StockDataBean;
import org.tradeware.stockmarket.bean.ORBStockDataBean;
import org.tradeware.stockmarket.bean.OptionChainDataBean;
import org.tradeware.stockmarket.bean.OptionStockDataBean;
import org.tradeware.stockmarket.bean.StockDataBean;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.tradewaredatabase.tool.NSEIndiaCloudDataBaseToolNewNseSite_Org;
import org.tradeware.stockmarket.util.Constants;
import org.tradeware.stockmarket.util.StockUtil;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.tradeware.clouddatabase.bean.OptionChainInfoBean;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.service.OptionChainInfoService;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.DatabaseMapper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaToolNewNseSite implements Constants {

	private static NSEIndiaToolNewNseSite singletonTool;
	private static WebClient webClient = null;

	// These maps are just tracing purpose, holds the history of each stock
	private Hashtable<String, List<StockDataBean>> wholeHistoricalDataOfEachStock = new Hashtable<String, List<StockDataBean>>();
	private Hashtable<String, List<StockDataBean>> wholeLast5DaysDataOfEachStock = new Hashtable<String, List<StockDataBean>>();

	private Hashtable<String, StockDataBean> historicalDataMap = new Hashtable<String, StockDataBean>();
	private Hashtable<String, StockDataBean> last5DaysResultDataMap = new Hashtable<String, StockDataBean>();
	private ConcurrentHashMap<String, StockDataBean> positionalResultDataMap = new ConcurrentHashMap<String, StockDataBean>();
	// IF ANY STOCK GEtS REMOVEd FROM NSE SITE
	private List<String> failedResultDataMap = new ArrayList<String>();
	private Hashtable<String, OptionStockDataBean> positionalOptionsMap = new Hashtable<String, OptionStockDataBean>();
	private Hashtable<String, StockDataBean> positionalFutureMap = new Hashtable<String, StockDataBean>();
	private ConcurrentHashMap<String, Narrow7StockDataBean> narrow7Map = new ConcurrentHashMap<String, Narrow7StockDataBean>();
	private ConcurrentHashMap<String, ORBStockDataBean> orbJackPotMap = new ConcurrentHashMap<String, ORBStockDataBean>();
	
	public Hashtable<String, OptionStockDataBean> getPositionalOptionsMap() {
		return positionalOptionsMap;
	}

	public Hashtable<String, StockDataBean> getPositionalFutureMap() {
		return positionalFutureMap;
	}

	public ConcurrentHashMap<String, Narrow7StockDataBean> getNarrow7Map() {
		return narrow7Map;
	}

	public ConcurrentHashMap<String, ORBStockDataBean> getOrbJackPotMap() {
		return orbJackPotMap;
	}

	public Hashtable<String, StockDataBean> getLast5DaysResultDataMap() {
		return last5DaysResultDataMap;
	}

	public ConcurrentHashMap<String, StockDataBean> getPositionalResultDataMap() {
		return positionalResultDataMap;
	}

	public Hashtable<String, String> getKiteFutureKeyMap() {
		return kiteFutureKeyMap;
	}

	private Hashtable<String, String> kiteFutureKeyMap = new Hashtable<String, String>();

	private Hashtable<String, OptionChainDataBean> optionChainDataMap = new Hashtable<String, OptionChainDataBean>();
	public Hashtable<String, OptionChainDataBean> getOptionChainDataMap() {
		return optionChainDataMap;
	}
	private Hashtable<String, OptionChainDataBean> optionChainDataMapIndex = new Hashtable<String, OptionChainDataBean>();
	public Hashtable<String, OptionChainDataBean> getOptionChainDataMapIndex() {
		return optionChainDataMapIndex;
	}
	
	public List<String> getFailedResultDataMap() {
		return failedResultDataMap;
	}

	public static NSEIndiaToolNewNseSite getInstance() {
		try {
			if (null == singletonTool) {
				webClient = new WebClient();
				webClient.getOptions().setJavaScriptEnabled(false);
				webClient.getOptions().setUseInsecureSSL(true);
				webClient.getOptions().setCssEnabled(false);
				
				webClient.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				webClient.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
				webClient.addRequestHeader("Accept-Language", "en-US,en;q=0.5");
				webClient.addRequestHeader("Connection", "keep-alive");
				//webClient.addRequestHeader("Cookie", "_ga=GA1.2.1108189851.1593122201; pointerfo=1; underlying1=INFY; instrument1=FUTSTK; optiontype1=-; expiry1=30JUL2020; strikeprice1=-; RT=\"z=1&dm=nseindia.com&si=30710a1f-2540-4448-9e33-7a770b0ebcae&ss=kcpomakh&sl=0&tt=0&bcn=%2F%2F684d0d37.akstat.io%2F\"; NSE-TEST-1=1927290890.20480.0000; _gid=GA1.2.1231178383.1594946160; JSESSIONID=ADCB21AF297C9519220D00EEA5EB3F28.tomcat1; pointer=5; sym1=RELIANCE; sym2=INFRATEL; sym3=IRCTC; sym4=ABFRL; sym5=IPCALAB; ak_bmsc=F621639370AB062BBB9798D5C8ED7D3317CB3F263C3B000090A4125FB3594369~plvDBnTQevPcteD3pYFsERMdNCLs5Xvwv5Bxejtd02bvt0WWEiuQ7zfp1Nuh7uw03yp3uaDFC3MrpAwLSrurUfkMP63nLKbOF7ZG6+M/CSBs+ZxTRvVtCArpkBbLwjJvdBpPt72CLnYJ5HureVDoUsrIKi+GvPe9gvqHbTYSwj0ph+qVKgq1oDGQHJt5Z5zp/bRryY1ukXsK5QsIAT3DfmNSHzCnviOmIgAx8u5xa9vFA=; bm_sv=73B7317B2D7B2A63CC34891AB9EFE7E1~L0te/McA/HQf4P3MQsziokKK/L/eG5CQp/wwjQN7/yzG9+v8hlj2s0AIJHgd/sDJcAe5MDN+ar6b88YghWU+hqGCXaYKjhhIrPeVlmilNdLhV93Z775Bqb/nWA7l8twT3pEzSvMomAOdn6Oo3hyCEh/0PC1gPLq+2cduuf9IcL4=; bm_mi=F5602C986BFFD436101AB4FC47B759CC~fau2IjM/honi2zUkj5P6KbZtA6+XVpPO0TNU1pbVXVs7gXHHnQ9gtEyn2u+S1sQ9d1v4wVKNWGOm6qoUsEINxn0MVw0OD4Zu2L/SjA9EiEz2UNDOimXKoyog9eG9GOyzuEdELSwDMXddx05vWPqTQVKjGCcaO8EpeUD3sYjiB9M0EDsvVt+4WyseBf2UjZYgH4orRGdtW3zecaWLtvVuwqaM/XFCMDJx/cl74NqtyfM8iu9VR58B0rxHKl2JZ8d+fKB2J9EF8alvlJDZhGsm36zyAlJ+wyuAc8wqGKKiaPVasjd5D3MKjGbxrIZZsxN3");
				webClient.addRequestHeader("Host", "www1.nseindia.com");
				webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
				webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:77.0) Gecko/20100101 Firefox/77.0");
				
				singletonTool = new NSEIndiaToolNewNseSite();
				singletonTool.init_NSEIndiaTool();
			}
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		return singletonTool;
	}
	
	private WebClient getWebClient() {
		if (null == webClient) {
			webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setCssEnabled(false);
			
			//webClient.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			//webClient.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
			//webClient.addRequestHeader("Accept-Language", "en-US,en;q=0.5");
			//webClient.addRequestHeader("Connection", "keep-alive");
			//webClient.addRequestHeader("Cookie", "_ga=GA1.2.1108189851.1593122201; pointerfo=1; underlying1=INFY; instrument1=FUTSTK; optiontype1=-; expiry1=30JUL2020; strikeprice1=-; RT=\"z=1&dm=nseindia.com&si=30710a1f-2540-4448-9e33-7a770b0ebcae&ss=kcpomakh&sl=0&tt=0&bcn=%2F%2F684d0d37.akstat.io%2F\"; NSE-TEST-1=1927290890.20480.0000; _gid=GA1.2.1231178383.1594946160; JSESSIONID=ADCB21AF297C9519220D00EEA5EB3F28.tomcat1; pointer=5; sym1=RELIANCE; sym2=INFRATEL; sym3=IRCTC; sym4=ABFRL; sym5=IPCALAB; ak_bmsc=F621639370AB062BBB9798D5C8ED7D3317CB3F263C3B000090A4125FB3594369~plvDBnTQevPcteD3pYFsERMdNCLs5Xvwv5Bxejtd02bvt0WWEiuQ7zfp1Nuh7uw03yp3uaDFC3MrpAwLSrurUfkMP63nLKbOF7ZG6+M/CSBs+ZxTRvVtCArpkBbLwjJvdBpPt72CLnYJ5HureVDoUsrIKi+GvPe9gvqHbTYSwj0ph+qVKgq1oDGQHJt5Z5zp/bRryY1ukXsK5QsIAT3DfmNSHzCnviOmIgAx8u5xa9vFA=; bm_sv=73B7317B2D7B2A63CC34891AB9EFE7E1~L0te/McA/HQf4P3MQsziokKK/L/eG5CQp/wwjQN7/yzG9+v8hlj2s0AIJHgd/sDJcAe5MDN+ar6b88YghWU+hqGCXaYKjhhIrPeVlmilNdLhV93Z775Bqb/nWA7l8twT3pEzSvMomAOdn6Oo3hyCEh/0PC1gPLq+2cduuf9IcL4=; bm_mi=F5602C986BFFD436101AB4FC47B759CC~fau2IjM/honi2zUkj5P6KbZtA6+XVpPO0TNU1pbVXVs7gXHHnQ9gtEyn2u+S1sQ9d1v4wVKNWGOm6qoUsEINxn0MVw0OD4Zu2L/SjA9EiEz2UNDOimXKoyog9eG9GOyzuEdELSwDMXddx05vWPqTQVKjGCcaO8EpeUD3sYjiB9M0EDsvVt+4WyseBf2UjZYgH4orRGdtW3zecaWLtvVuwqaM/XFCMDJx/cl74NqtyfM8iu9VR58B0rxHKl2JZ8d+fKB2J9EF8alvlJDZhGsm36zyAlJ+wyuAc8wqGKKiaPVasjd5D3MKjGbxrIZZsxN3");
			//webClient.addRequestHeader("Host", "www1.nseindia.com");
			//webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
			//webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:77.0) Gecko/20100101 Firefox/77.0");
		}
		return webClient;
	}
	
	public NSEIndiaToolNewNseSite() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println("NseIndia initilization..............");
		singletonTool = this;
	}

	//private NSEIndiaTool() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
	public void init_NSEIndiaTool() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		historicalDataMap.clear();
		wholeHistoricalDataOfEachStock.clear();

		//connectToNSEIndiaAndGetStockData();
		getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData();
		for (String symbol : StockUtil.getSymbols().keySet()) {
			kiteFutureKeyMap.put(symbol, TradewareTraderUtil.getInstance().getKiteFuturekey(symbol));
			//retriveHistoricalDataForEachStock(symbol);
			retriveHistoricalDataForEachStockNew(symbol);
			//Open interest start
			if (!failedResultDataMap.contains(symbol)) {
				OptionChainDataBean beanOI = NSEIndiaToolOptionChainToolNewSite.getInstance().retriveOptionChainDataForEachStock(optionChainDataMap.get(symbol));
				//optionChainDataMap.put(symbol, beanOI);
				StockUtil.getSymbolTickerMap().put(symbol, beanOI.getTickerSize());
				//System.out.println("Option Ticker : "+symbol+" - "+StockUtil.getSymbolTickerMap().get(symbol));
				beanOI.setCandleNumber(getCandleNumber());
				beanOI.setParentRecordInd(true);
				beanOI.getOITrend();
				beanOI.handleSortOrderAndStyle();
				updateTopOptionStrongBuySellTrades(optionChainDataMap.get(symbol));
			}
		}
		updateTopOptionStrongBuySellTradesTag();
		optionChainDataMap = NSEIndiaToolOptionChainToolNewSite.getInstance().retriveOptionChainSpurtsDataForEachStock(optionChainDataMap);
		/*DatabaseHelper.getInstance().*/saveAllOptionChainData(new ArrayList(optionChainDataMap.values()));
		//Open interest End
//processNarrow7Strategy();
		// filter if any failuers
		if (!failedResultDataMap.isEmpty()) {
			for (String symb : failedResultDataMap) {
				//StockUtil.getSymbols().remove(symb);
				System.out.println("Failed - " + symb);
			}
		}
		
		while(!failedResultDataMap.isEmpty()) {
			retryOnFaildResultDataMap();
		}
		
		//Optichain analysis for NIFTY & BANKNIFTY
		retriveOptionChainDataForEachIndex();
	}
	
	public void retryOnFaildResultDataMap() {
		if (!failedResultDataMap.isEmpty()) {
			Iterator<String> it = failedResultDataMap.listIterator();

			while (it.hasNext()) {
				String symbol = it.next();
				kiteFutureKeyMap.put(symbol, TradewareTraderUtil.getInstance().getKiteFuturekey(symbol));
				retriveHistoricalDataForEachStockNew(symbol);
				OptionChainDataBean beanOI = NSEIndiaToolOptionChainToolNewSite.getInstance()
						.retriveOptionChainDataForEachStock(optionChainDataMap.get(symbol));
				StockUtil.getSymbolTickerMap().put(symbol, beanOI.getTickerSize());
				beanOI.setCandleNumber(getCandleNumber());
				beanOI.setParentRecordInd(true);
				updateTopOptionStrongBuySellTrades(optionChainDataMap.get(symbol));
				it.remove();
			}
		}
	}
	
	//optionChainInfoService
	@Autowired
	private OptionChainInfoService optionChainInfoService; 
	@Autowired
	DatabaseMapper databaseMapper;
		public List<OptionChainInfoBean> saveAllOptionChainData(List<OptionChainDataBean> beans) {
			List<OptionChainInfoBean> baseList  = new ArrayList<OptionChainInfoBean>(beans.size());;
			try {
				
				for (OptionChainDataBean bean : beans) {
					baseList.add(
							//DatabaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(bean, sathvikAshvikTechTraderTool));
							databaseMapper.mapToOptionChainInforBean(bean));
				}
				 baseList = optionChainInfoService.saveAll(baseList);
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog("NSEIndiaToolNewNseSite",
						"saveAllOptionChainData", e,
						"EXCEPTION");
			}
			return baseList;
		}
		
		private int candleNumber = 0;
		public int getCandleNumber() {
			if (0 == candleNumber) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIMEZONE_IST)); 
				LocalDateTime startTime = LocalDate.now(ZoneId.of(Constants.TIMEZONE_IST)).atTime(LocalTime.of(9, 00, 00, 00));
				//LocalDateTime closedTime = startTime.plusMinutes(15);
				LocalDateTime closedTime = startTime.plusMinutes(5);
				while(TradewareTraderUtil.getInstance().isTradingTime()) {
					if (currentTime.isAfter(startTime) && currentTime.isBefore(closedTime)) {
						break;
					} else {
						candleNumber++;
						startTime = closedTime;
						closedTime = closedTime.plusMinutes(15);
					}
				}
			}
			return candleNumber;
		}
		
		public void restCandleNumber() {
			this.candleNumber = 0;
		}
	
	
	public void refreshOIDataForAll() {
		optionChainDataMap.clear();
		prepareOptionChainDataMap();
		clearTopOptionStrongBuySellTrades();
		refreshOIData(StockUtil.getSymbols().keySet());
		updateTopOptionStrongBuySellTradesTag();
		saveAllOptionChainData(new ArrayList(optionChainDataMap.values()));
		retriveOptionChainDataForEachIndex();
	}
	
	public void refreshOIDataForTops() {
		//clearTopOptionStrongBuySellTrades();
		refreshOIData(topAllOptionStrongTrades);
	}
	
	public void refreshOIData(Set<String> symbolList) {
		for (String symbol : symbolList) {
			System.out.println("option channg data - "+symbol);
			if (!failedResultDataMap.contains(symbol)) {
				OptionChainDataBean beanOI = NSEIndiaToolOptionChainToolNewSite.getInstance()
						.retriveOptionChainDataForEachStock(optionChainDataMap.get(symbol));
				//StockUtil.getSymbolTickerMap().put(symbol, beanOI.getTickerSize());
				beanOI.setCandleNumber(getCandleNumber());
				beanOI.setParentRecordInd(false);
				updateTopOptionStrongBuySellTrades(beanOI);
				beanOI.getOITrend();
				beanOI.handleSortOrderAndStyle();
			}
		}
		// Need to avoid full loop here
		optionChainDataMap = NSEIndiaToolOptionChainToolNewSite.getInstance()
				.retriveOptionChainSpurtsDataForEachStock(optionChainDataMap);
	}

	public void retryFailedHistoricalData() {
		failedResultDataMap.clear();
		for (String symbol : failedResultDataMap) {
			retriveHistoricalDataForEachStockNew(symbol);
			// Open interest start
			if (!failedResultDataMap.contains(symbol)) {
				OptionChainDataBean beanOI = NSEIndiaToolOptionChainToolNewSite.getInstance()
						.retriveOptionChainDataForEachStock(optionChainDataMap.get(symbol));
				StockUtil.getSymbolTickerMap().put(symbol, beanOI.getTickerSize());
			}
		}
	}

	private void retriveOptionChainDataForEachIndex() {
		optionChainDataMapIndex.clear();
		for (String index: StockUtil.getIndexSymbols().keySet()) {
			OptionChainDataBean indexOi = new OptionChainDataBean(index);
			indexOi.setIndexInd(true);
			indexOi.setLotSize(StockUtil.getIndexSymbols().get(index));
			indexOi = NSEIndiaToolOptionChainToolNewSite.getInstance().retriveOptionChainDataForEachStock(indexOi);
			indexOi.setTickerSize(indexOi.getStrikePrice2() - indexOi.getStrikePrice1());
			indexOi.getOITrend();
			indexOi.handleSortOrderAndStyle();
			optionChainDataMapIndex.put(index, indexOi);
		}
	} 
	private void retriveHistoricalDataForEachStock(String symbol) {
		try {
			// get previous month data
			HtmlPage inDataPagetraday = getWebClient().getPage(StockUtil.get2WeeksHistoricalDataUrlAjax(symbol,
					TradewareTraderUtil.getInstance().getCurrentMonthExpiryDate()));
			NodeList posdataRowElements = inDataPagetraday.getElementsByTagName(Constants.TAG_TABLE_ROW);
			processLast5DaysAvgData(symbol, posdataRowElements);
			// processLast5DaysAvgData must be call before processPositionalAvgData

			String previousMonthExpiry = TradewareTraderUtil.getInstance().getPreviousMonthExpiryDate()
					.replaceAll(HYPHEN, EMPTY_STRING).toUpperCase();
			HtmlPage historicalDataPage = getWebClient()
					.getPage(StockUtil.get3MonthsHistoricalDataUrlAjax(symbol, previousMonthExpiry));
			NodeList dataRowElements = historicalDataPage.getElementsByTagName(Constants.TAG_TABLE_ROW);
			processPositionalAvgData(symbol, dataRowElements);
		} catch (Exception e) {
			System.out.println(symbol + "   -    " + e.getMessage());
			failedResultDataMap.add(symbol);
		}
	}
	
	private void retriveHistoricalDataForEachStockNew(String symbol) {
		try {
			 getWebClient().getPage("https://www.nseindia.com");
			// get previous month data
			String URL = StockUtil.getLast5DayAvgDataUrl(symbol,
					TradewareTraderUtil.getInstance().getCurrentMonthExpiryDateNewNseSite(),
					TradewareTraderUtil.getInstance().getCurrentMonthFromDateNewNseSite(),
					TradewareTraderUtil.getInstance().getCurrentMonthToDateNewNseSite());
			
			String pageResponse = getWebClient().getPage(URL).getWebResponse().getContentAsString();
			processLast5DaysAvgData(symbol, pageResponse);
			// processLast5DaysAvgData must be call before processPositionalAvgData

			URL = StockUtil.getLast5DayAvgDataUrl(symbol,
					TradewareTraderUtil.getInstance().getPreviousMonthExpiryDate(),
					TradewareTraderUtil.getInstance().getPreviousMonthFromDateNewNseSite(),
					TradewareTraderUtil.getInstance().getPreviousMonthToDateNewNseSite());
			 pageResponse = getWebClient().getPage(URL).getWebResponse().getContentAsString();
			// System.out.println(URL);
			processPositionalAvgData(symbol, pageResponse);
		} catch (Exception e) {
			System.out.println(symbol + "   -    " + e.getMessage());
			failedResultDataMap.add(symbol);
		}
	}

	private void processLast5DaysAvgData(String symbol, NodeList dataRowElements) {
		List<StockDataBean> stockDataBeanList = new ArrayList<StockDataBean>();
		StockDataBean last5DaysAvgDataBean = null;

		double openMinsLow = ZERO_DOUBLE, highMinusOpen = ZERO_DOUBLE, avgHighLowOpen = ZERO_DOUBLE,
				topHigh = ZERO_DOUBLE, topLow = ZERO_DOUBLE, high = ZERO_DOUBLE, low = ZERO_DOUBLE;
		long sumContacts = ZERO_LONG, sunAvgHLCwithContracts = ZERO_LONG;
		int validItemsFor5DayCounter = ZERO_INT;
		// last 5 days data
		for (int index = ONE_INT; index < dataRowElements.getLength(); index++) {
			HtmlTableRow rowElement = (HtmlTableRow) dataRowElements.item(index);
			List<HtmlTableCell> rowElementDataCells = rowElement.getCells();
			if (null == last5DaysAvgDataBean) {
				boolean isTodayTradeClose = TradewareTraderUtil.getInstance().isTodayTradeClose();
				last5DaysAvgDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
				last5DaysAvgDataBean.setKiteFutureKey(TradewareTraderUtil.getInstance().getKiteFuturekey(symbol));
				last5DaysAvgDataBean.setOpen(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(rowElementDataCells.get(ONE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				last5DaysAvgDataBean.setHigh(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(rowElementDataCells.get(TWO_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				last5DaysAvgDataBean.setLow(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(rowElementDataCells.get(THREE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				last5DaysAvgDataBean.setClose(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(rowElementDataCells.get(FOUR_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));

			}
			//if (FIVE_INT > validItemsFor5DayCounter) {
				StockDataBean stockDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
				stockDataBean.setOpen(
						new BigDecimal(rowElementDataCells.get(ONE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				stockDataBean.setHigh(
						new BigDecimal(rowElementDataCells.get(TWO_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				stockDataBean.setLow(
						new BigDecimal(rowElementDataCells.get(THREE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				stockDataBean.setClose(
						new BigDecimal(rowElementDataCells.get(FOUR_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				stockDataBean.setNumberOfContracts(
						new Integer(rowElementDataCells.get(SIX_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
				if (stockDataBeanList.isEmpty()) {
					last5DaysAvgDataBean.setPreviousClose(stockDataBean.getClose());
					last5DaysAvgDataBean.setPreviousLow(stockDataBean.getLow());
					last5DaysAvgDataBean.setPreviousHigh(stockDataBean.getHigh());
				}
			if (FIVE_INT > validItemsFor5DayCounter) {
				//stockDataBeanList.add(stockDataBean);
				// monthly average calculation
				highMinusOpen = stockDataBean.getHigh().doubleValue() - stockDataBean.getOpen().doubleValue();
				openMinsLow = stockDataBean.getOpen().doubleValue() - stockDataBean.getLow().doubleValue();
				if (highMinusOpen < openMinsLow) {
					avgHighLowOpen = avgHighLowOpen + highMinusOpen;
				} else {
					avgHighLowOpen = avgHighLowOpen + openMinsLow;
				}

				sumContacts = sumContacts + stockDataBean.getNumberOfContracts();
				double avgHLC = ((stockDataBean.getHigh().doubleValue() + stockDataBean.getLow().doubleValue()
						+ stockDataBean.getClose().doubleValue()) / THREE_INT);
				avgHLC = avgHLC * stockDataBean.getNumberOfContracts();
				sunAvgHLCwithContracts = sunAvgHLCwithContracts + Math.round(avgHLC);
			}
			validItemsFor5DayCounter++;
			if (FIVE_INT == validItemsFor5DayCounter) {
				wholeLast5DaysDataOfEachStock.put(symbol, stockDataBeanList);

				last5DaysAvgDataBean.setLast5DaysAvgMin(BigDecimal.valueOf(BigDecimal.valueOf(avgHighLowOpen / FIVE_INT)
						.setScale(TWO_INT, RoundingMode.FLOOR).doubleValue()));

				last5DaysAvgDataBean.setAvgHLC(Math.round(sunAvgHLCwithContracts / sumContacts));
			}
			if (null == last5DaysAvgDataBean.getTopHigh() && null == last5DaysAvgDataBean.getTopLow()) {
				high = Double.valueOf(rowElementDataCells.get(TWO_INT).asText().replaceAll(HYPHEN, EMPTY_STRING));
				low = Double.valueOf(rowElementDataCells.get(THREE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING));
				if (ZERO_INT == topHigh || high > topHigh) {
					topHigh = high;
				}
				if (ZERO_INT == topLow || low < topLow) {
					topLow = low;
				}
			}
			// for first day close
			if (rowElementDataCells.get(ZERO_INT).asText()
					.equals(TradewareTraderUtil.getInstance().getCurrentMonthFirstDay())) {
				last5DaysAvgDataBean.setFirstDayClose(new BigDecimal(getRoundupToTwoValue(
						Double.valueOf(rowElementDataCells.get(4).asText().replaceAll(HYPHEN, EMPTY_STRING)))));
				last5DaysAvgDataBean.setTopHigh(BigDecimal.valueOf(topHigh));
				last5DaysAvgDataBean.setTopLow(BigDecimal.valueOf(topLow));
			}
			stockDataBeanList.add(stockDataBean);
		}

		last5DaysResultDataMap.put(symbol, last5DaysAvgDataBean);

		System.out.println(
				"last5DaysResultDataMap ready --" + last5DaysResultDataMap.size() + " -  " + symbol + " :  avgminval : "
						+ BigDecimal.valueOf(avgHighLowOpen / 5).setScale(2, RoundingMode.FLOOR).toString());
	}

	private void processPositionalAvgData(String symbol, NodeList dataRowElements) {
		List<StockDataBean> stockDataBeanList = new ArrayList<StockDataBean>();
		StockDataBean montlyAvgDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
		StockDataBean positinalDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
		positinalDataBean.setKiteFutureKey(TradewareTraderUtil.getInstance().getKiteFuturekey(symbol));
		double averageTurnover = ZERO_DOUBLE, averageContracts = ZERO_DOUBLE;

		// String expireDate = getPreviousMonthExpiryDate();
		String startDate = TradewareTraderUtil.getInstance().getPreviousMonthStartDate();

		for (int index = ONE_INT; index < dataRowElements.getLength(); index++) {
			HtmlTableRow rowElement = (HtmlTableRow) dataRowElements.item(index);
			List<HtmlTableCell> rowElementDataCells = rowElement.getCells();

			StockDataBean stockDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
			stockDataBean.setOpen(
					new BigDecimal(rowElementDataCells.get(ONE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
			stockDataBean.setHigh(
					new BigDecimal(rowElementDataCells.get(TWO_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
			stockDataBean.setLow(
					new BigDecimal(rowElementDataCells.get(THREE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
			stockDataBean.setClose(
					new BigDecimal(rowElementDataCells.get(FOUR_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
			stockDataBean.setSettlePrice(
					new BigDecimal(rowElementDataCells.get(FIVE_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
			stockDataBean.setNumberOfContracts(
					new Integer(rowElementDataCells.get(SIX_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
			stockDataBean.setTurnover(
					new BigDecimal(rowElementDataCells.get(SEVEN_INT).asText().replaceAll(HYPHEN, EMPTY_STRING)));
			stockDataBeanList.add(stockDataBean);
			// monthly average calculation
			averageContracts = averageContracts + stockDataBean.getNumberOfContracts().doubleValue();
			averageTurnover = averageTurnover + stockDataBean.getTurnover().doubleValue();
			if (rowElementDataCells.get(ZERO_INT).asText().equals(startDate)) {
				break;
			}
		}

		if (!last5DaysResultDataMap.isEmpty() && null != last5DaysResultDataMap.get(symbol)
				&& null != last5DaysResultDataMap.get(symbol).getFirstDayClose()) {
			montlyAvgDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getFirstDayClose());
			positinalDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getFirstDayClose());
			montlyAvgDataBean.setAvgHLC(last5DaysResultDataMap.get(symbol).getAvgHLC());
			positinalDataBean.setAvgHLC(last5DaysResultDataMap.get(symbol).getAvgHLC());
		} else {
			montlyAvgDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getPreviousClose());
			positinalDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getPreviousClose());
		}
		wholeHistoricalDataOfEachStock.put(symbol, stockDataBeanList);

		montlyAvgDataBean.setAverageContracts(
				BigDecimal.valueOf(averageContracts / stockDataBeanList.size()).setScale(TWO_INT, RoundingMode.FLOOR));
		montlyAvgDataBean.setAverageTurnover(
				(BigDecimal.valueOf(averageTurnover / stockDataBeanList.size())).setScale(TWO_INT, RoundingMode.FLOOR));

		montlyAvgDataBean.setLotSize(StockUtil.getSymbols().get(symbol));
		positinalDataBean.setLotSize(StockUtil.getSymbols().get(symbol));

		historicalDataMap.put(symbol, montlyAvgDataBean);
		positinalDataBean.setAverageContracts(montlyAvgDataBean.getAverageContracts());
		positinalDataBean.setAverageTurnover(montlyAvgDataBean.getAverageTurnover());
		positinalDataBean.setPreviousClose(last5DaysResultDataMap.get(symbol).getPreviousClose());
		positinalDataBean.setPreviousLow(last5DaysResultDataMap.get(symbol).getPreviousLow());
		positinalDataBean.setPreviousHigh(last5DaysResultDataMap.get(symbol).getPreviousHigh());
		// caluclations
		positinalDataBean.setAtpValue(new BigDecimal((positinalDataBean.getAverageTurnover().doubleValue()
				/ positinalDataBean.getAverageContracts().doubleValue()) * (100000d / positinalDataBean.getLotSize()))
						.setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setDifferenceValue(new BigDecimal(Math.abs(
				positinalDataBean.getFirstDayClose().doubleValue() - positinalDataBean.getAtpValue().doubleValue()))
						.setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setPositionalUptrendValue((new BigDecimal(positinalDataBean.getFirstDayClose().doubleValue()
				+ positinalDataBean.getDifferenceValue().doubleValue())).setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setPositionalDowntrendValue((new BigDecimal(positinalDataBean.getFirstDayClose().doubleValue()
				- positinalDataBean.getDifferenceValue().doubleValue())).setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setTopHigh(last5DaysResultDataMap.get(symbol).getTopHigh());
		positinalDataBean.setTopLow(last5DaysResultDataMap.get(symbol).getTopLow());
		positinalDataBean.setLast5DaysAvgMin(last5DaysResultDataMap.get(symbol).getLast5DaysAvgMin());
		
		// For user BUY/SELL list
		if (null != positinalDataBean.getPositionalUptrendValue() && null != positinalDataBean.getPreviousClose()
				&& positinalDataBean.getPositionalUptrendValue().doubleValue() < positinalDataBean.getPreviousClose()
						.doubleValue()) {
			positinalDataBean.setPreviousDayPosSignal(BUY);
		} else if (null != positinalDataBean.getPositionalDowntrendValue()
				&& null != positinalDataBean.getPreviousClose() && positinalDataBean.getPositionalDowntrendValue()
						.doubleValue() > positinalDataBean.getPreviousClose().doubleValue()) {
			positinalDataBean.setPreviousDayPosSignal(SELL);
		} else if (null != positinalDataBean.getPositionalUptrendValue()
				&& null != positinalDataBean.getPositionalDowntrendValue()
				&& null != positinalDataBean.getPreviousClose()
				&& NA.equals(positinalDataBean.getPreviousDayPosSignal())) {
			
			OptionStockDataBean optionBean = new OptionStockDataBean(positinalDataBean);
			//positinalDataBean.setOption9DaySMA(last5DaysResultDataMap.get(symbol).getOption9DaySMA());
			positionalOptionsMap.put(symbol, optionBean);
		}

		positionalResultDataMap.put(symbol, positinalDataBean);
	}
	
	public double getRoundupToTwoValue(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
	}
	
	private static final String OPEN = "FH_OPENING_PRICE";
	private static final String HIGH = "FH_TRADE_HIGH_PRICE";
	private static final String LOW = "FH_TRADE_LOW_PRICE";
	private static final String CLOSE = "FH_CLOSING_PRICE";
	private static final String VOLUMES_TRADED = "FH_TOT_TRADED_QTY";
	private static final String TRADNG_DATE = "FH_TIMESTAMP";
	private static final String SETTLE_PRICE = "FH_SETTLE_PRICE";
	private static final String TOTAL_TRADED_VALUE = "FH_TOT_TRADED_VAL";
	private static final String MARKET_LOT = "FH_MARKET_LOT";
	
	private JSONParser jsonParser = new JSONParser();
	private void processLast5DaysAvgData(String symbol, String pageResponse) throws ParseException {
		List<StockDataBean> stockDataBeanList = new ArrayList<StockDataBean>();
		StockDataBean last5DaysAvgDataBean = null;

		double openMinsLow = ZERO_DOUBLE, highMinusOpen = ZERO_DOUBLE, avgHighLowOpen = ZERO_DOUBLE,
				topHigh = ZERO_DOUBLE, topLow = ZERO_DOUBLE, high = ZERO_DOUBLE, low = ZERO_DOUBLE;
		long sumContacts = ZERO_LONG, sunAvgHLCwithContracts = ZERO_LONG;
		int validItemsFor5DayCounter = ZERO_INT;
		// last 5 days data
		JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
		JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
		for (int i = 0; i < rawJsonDataArray.size(); i++) {
			JSONObject jsonDataObject = (JSONObject) rawJsonDataArray.get(i);
		
			if (null == last5DaysAvgDataBean) {
				boolean isTodayTradeClose = TradewareTraderUtil.getInstance().isTodayTradeClose();
				last5DaysAvgDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
				last5DaysAvgDataBean.setKiteFutureKey(TradewareTraderUtil.getInstance().getKiteFuturekey(symbol));
				last5DaysAvgDataBean.setOpen(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(OPEN)))));
				last5DaysAvgDataBean.setHigh(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH)))));
				last5DaysAvgDataBean.setLow(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(LOW)))));
				last5DaysAvgDataBean.setClose(isTodayTradeClose ? new BigDecimal(ZERO_INT)
						: new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(CLOSE)))));
			}
			//if (FIVE_INT > validItemsFor5DayCounter) {
			StockDataBean stockDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
			stockDataBean.setOpen(
					new BigDecimal(getRoundupToTwoValue(Double.valueOf(String.valueOf(jsonDataObject.get(OPEN))))));
			stockDataBean.setHigh(
					new BigDecimal(getRoundupToTwoValue(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH))))));
			stockDataBean.setLow(
					new BigDecimal(getRoundupToTwoValue(Double.valueOf(String.valueOf(jsonDataObject.get(LOW))))));
			stockDataBean.setClose(
					new BigDecimal(getRoundupToTwoValue(Double.valueOf(String.valueOf(jsonDataObject.get(CLOSE))))));
			Integer volumesTraded = (Integer.valueOf(String.valueOf(jsonDataObject.get(VOLUMES_TRADED))))
					/ Integer.valueOf(String.valueOf(jsonDataObject.get(MARKET_LOT)));
			stockDataBean.setNumberOfContracts(volumesTraded);
			if (stockDataBeanList.isEmpty()) {
				last5DaysAvgDataBean.setPreviousClose(
						BigDecimal.valueOf(getRoundupToTwoValue(stockDataBean.getClose().doubleValue())));
				last5DaysAvgDataBean.setPreviousLow(stockDataBean.getLow());
				last5DaysAvgDataBean.setPreviousHigh(stockDataBean.getHigh());
			}
			if (FIVE_INT > validItemsFor5DayCounter) {
				//stockDataBeanList.add(stockDataBean);
				// monthly average calculation
				highMinusOpen = stockDataBean.getHigh().doubleValue() - stockDataBean.getOpen().doubleValue();
				openMinsLow = stockDataBean.getOpen().doubleValue() - stockDataBean.getLow().doubleValue();
				if (highMinusOpen < openMinsLow) {
					avgHighLowOpen = avgHighLowOpen + highMinusOpen;
				} else {
					avgHighLowOpen = avgHighLowOpen + openMinsLow;
				}

				sumContacts = sumContacts + stockDataBean.getNumberOfContracts();
				double avgHLC = ((stockDataBean.getHigh().doubleValue() + stockDataBean.getLow().doubleValue()
						+ stockDataBean.getClose().doubleValue()) / THREE_INT);
				avgHLC = avgHLC * stockDataBean.getNumberOfContracts();
				sunAvgHLCwithContracts = sunAvgHLCwithContracts + Math.round(avgHLC);
			}
			validItemsFor5DayCounter++;
			if (FIVE_INT == validItemsFor5DayCounter) {
				wholeLast5DaysDataOfEachStock.put(symbol, stockDataBeanList);

				last5DaysAvgDataBean.setLast5DaysAvgMin(BigDecimal.valueOf(BigDecimal.valueOf(avgHighLowOpen / FIVE_INT)
						.setScale(TWO_INT, RoundingMode.FLOOR).doubleValue()));

				last5DaysAvgDataBean.setAvgHLC(Math.round(sunAvgHLCwithContracts / sumContacts));
			}
			if (null == last5DaysAvgDataBean.getTopHigh() && null == last5DaysAvgDataBean.getTopLow()) {
				high = Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH))));
				low = Double.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(LOW))));
				if (ZERO_INT == topHigh || high > topHigh) {
					topHigh = high;
				}
				if (ZERO_INT == topLow || low < topLow) {
					topLow = low;
				}
			}
			// for first day close
			if (String.valueOf(jsonDataObject.get(TRADNG_DATE))
					.equals(TradewareTraderUtil.getInstance().getCurrentMonthFirstDay())) {
				last5DaysAvgDataBean.setFirstDayClose(
						BigDecimal.valueOf(getRoundupToTwoValue(Double.valueOf(String.valueOf(jsonDataObject.get(CLOSE))))));
				last5DaysAvgDataBean.setTopHigh(BigDecimal.valueOf(topHigh));
				last5DaysAvgDataBean.setTopLow(BigDecimal.valueOf(topLow));
			}
			stockDataBeanList.add(stockDataBean);
		
		}
		last5DaysResultDataMap.put(symbol, last5DaysAvgDataBean);

		System.out.println(
				"last5DaysResultDataMap ready --" + last5DaysResultDataMap.size() + " -  " + symbol + " :  avgminval : "
						+ BigDecimal.valueOf(avgHighLowOpen / 5).setScale(2, RoundingMode.FLOOR).toString());
	}
	
	private void processPositionalAvgData(String symbol, String pageResponse) throws ParseException {
		List<StockDataBean> stockDataBeanList = new ArrayList<StockDataBean>();
		StockDataBean montlyAvgDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
		StockDataBean positinalDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
		positinalDataBean.setKiteFutureKey(TradewareTraderUtil.getInstance().getKiteFuturekey(symbol));
		double averageTurnover = ZERO_DOUBLE, averageContracts = ZERO_DOUBLE;

		// String expireDate = getPreviousMonthExpiryDate();
		String startDate = TradewareTraderUtil.getInstance().getPreviousMonthStartDate();
		
		JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
		JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
		for (int i = 0; i < rawJsonDataArray.size(); i++) {
			JSONObject jsonDataObject = (JSONObject) rawJsonDataArray.get(i);
			StockDataBean stockDataBean = new StockDataBean(StockUtil.getSymbols().get(symbol), symbol);
			stockDataBean.setOpen(
					new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(OPEN)))));
			stockDataBean.setHigh(
					new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(HIGH)))));
			stockDataBean.setLow(
					new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(LOW)))));
			stockDataBean.setClose(
					new BigDecimal(Double.valueOf(String.valueOf(jsonDataObject.get(CLOSE)))));
			Integer volumesTraded = (Integer.valueOf(String.valueOf(jsonDataObject.get(VOLUMES_TRADED))))
					/ Integer.valueOf(String.valueOf(jsonDataObject.get(MARKET_LOT)));
			stockDataBean.setNumberOfContracts(volumesTraded);
			stockDataBean.setSettlePrice(
					new BigDecimal(String.valueOf(jsonDataObject.get(SETTLE_PRICE))));
			/*stockDataBean.setTurnover(
					new BigDecimal(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE))));*/
			stockDataBean.setTurnoverActual(BigDecimal.valueOf(Double.valueOf(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE)))));
			stockDataBean.setTurnover(
					new BigDecimal(getRoundupToTwoValue((Double.valueOf(String.valueOf(jsonDataObject.get(TOTAL_TRADED_VALUE))))/100000d)));
			stockDataBeanList.add(stockDataBean);
			// monthly average calculation
			averageContracts = averageContracts + stockDataBean.getNumberOfContracts().doubleValue();
			averageTurnover = averageTurnover + stockDataBean.getTurnover().doubleValue();
			//System.out.println(String.valueOf(jsonDataObject.get(TRADNG_DATE))+"  --  "+ (getRoundupToTwoValue(stockDataBean.getTurnover().doubleValue())));
			if (String.valueOf(jsonDataObject.get(TRADNG_DATE)).equals(startDate)) {
				break;
			}
		}

		if (!last5DaysResultDataMap.isEmpty() && null != last5DaysResultDataMap.get(symbol)
				&& null != last5DaysResultDataMap.get(symbol).getFirstDayClose()) {
			montlyAvgDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getFirstDayClose());
			positinalDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getFirstDayClose());
			montlyAvgDataBean.setAvgHLC(last5DaysResultDataMap.get(symbol).getAvgHLC());
			positinalDataBean.setAvgHLC(last5DaysResultDataMap.get(symbol).getAvgHLC());
		} else {
			montlyAvgDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getPreviousClose());
			positinalDataBean.setFirstDayClose(last5DaysResultDataMap.get(symbol).getPreviousClose());
		}
		wholeHistoricalDataOfEachStock.put(symbol, stockDataBeanList);

		montlyAvgDataBean.setAverageContracts(
				BigDecimal.valueOf(averageContracts / stockDataBeanList.size()).setScale(TWO_INT, RoundingMode.FLOOR));
		montlyAvgDataBean.setAverageTurnover(
				(BigDecimal.valueOf(averageTurnover / stockDataBeanList.size())).setScale(TWO_INT, RoundingMode.FLOOR));

		montlyAvgDataBean.setLotSize(StockUtil.getSymbols().get(symbol));
		positinalDataBean.setLotSize(StockUtil.getSymbols().get(symbol));

		historicalDataMap.put(symbol, montlyAvgDataBean);
		positinalDataBean.setAverageContracts(montlyAvgDataBean.getAverageContracts());
		positinalDataBean.setAverageTurnover(montlyAvgDataBean.getAverageTurnover());
		positinalDataBean.setPreviousClose(last5DaysResultDataMap.get(symbol).getPreviousClose());
		positinalDataBean.setPreviousLow(last5DaysResultDataMap.get(symbol).getPreviousLow());
		positinalDataBean.setPreviousHigh(last5DaysResultDataMap.get(symbol).getPreviousHigh());
		// caluclations
		positinalDataBean.setAtpValue(new BigDecimal((positinalDataBean.getAverageTurnover().doubleValue()
				/ positinalDataBean.getAverageContracts().doubleValue()) * (100000d / positinalDataBean.getLotSize()))
						.setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setDifferenceValue(new BigDecimal(Math.abs(
				positinalDataBean.getFirstDayClose().doubleValue() - positinalDataBean.getAtpValue().doubleValue()))
						.setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setPositionalUptrendValue((new BigDecimal(positinalDataBean.getFirstDayClose().doubleValue()
				+ positinalDataBean.getDifferenceValue().doubleValue())).setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setPositionalDowntrendValue((new BigDecimal(positinalDataBean.getFirstDayClose().doubleValue()
				- positinalDataBean.getDifferenceValue().doubleValue())).setScale(TWO_INT, RoundingMode.FLOOR));
		positinalDataBean.setTopHigh(last5DaysResultDataMap.get(symbol).getTopHigh());
		positinalDataBean.setTopLow(last5DaysResultDataMap.get(symbol).getTopLow());
		positinalDataBean.setLast5DaysAvgMin(last5DaysResultDataMap.get(symbol).getLast5DaysAvgMin());
		
		// For user BUY/SELL list
		if (null != positinalDataBean.getPositionalUptrendValue() && null != positinalDataBean.getPreviousClose()
				&& positinalDataBean.getPositionalUptrendValue().doubleValue() < positinalDataBean.getPreviousClose()
						.doubleValue()) {
			positinalDataBean.setPreviousDayPosSignal(BUY);
		} else if (null != positinalDataBean.getPositionalDowntrendValue()
				&& null != positinalDataBean.getPreviousClose() && positinalDataBean.getPositionalDowntrendValue()
						.doubleValue() > positinalDataBean.getPreviousClose().doubleValue()) {
			positinalDataBean.setPreviousDayPosSignal(SELL);
		} else if (null != positinalDataBean.getPositionalUptrendValue()
				&& null != positinalDataBean.getPositionalDowntrendValue()
				&& null != positinalDataBean.getPreviousClose()
				&& NA.equals(positinalDataBean.getPreviousDayPosSignal())) {
			
			OptionStockDataBean optionBean = new OptionStockDataBean(positinalDataBean);
			//positinalDataBean.setOption9DaySMA(last5DaysResultDataMap.get(symbol).getOption9DaySMA());
			positionalOptionsMap.put(symbol, optionBean);
		}

		positionalResultDataMap.put(symbol, positinalDataBean);
	}
	
	private void processNarrow7Strategy() {
		for (String symbol : wholeLast5DaysDataOfEachStock.keySet()) {
			if (null != last5DaysResultDataMap.get(symbol)) {
				Narrow7StockDataBean nr7Bean = new Narrow7StockDataBean(last5DaysResultDataMap.get(symbol));
				List<StockDataBean> list = wholeLast5DaysDataOfEachStock.get(symbol);
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
	private void applyNarrow7StrategyRule(Narrow7StockDataBean bean) {
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
	
	private static final String FO_STOCKS_URL = "https://nseindia.com/live_market/dynaContent/live_watch/stock_watch/foSecStockWatch.json"; 
	private static final String FO_STOCKS_LOT_SIZE_URL = "https://nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuoteFO.jsp?instrument=FUTSTK&expiry=30JUL2020&type=-&strike=-&underlying=";

	@Autowired
	//private NSEIndiaCloudDataBaseTool cloudDataBaseTool;
	private NSEIndiaCloudDataBaseToolNewNseSite_Org cloudDataBaseTool;
	
	public List<SymbolBean> prepareAlgoCloudDataAndGetSymbolList() 
	{
		return cloudDataBaseTool.getprepareAlgoCloudDataAndGetSymbolList();
	}
	
	//this method is replacement of connectToNSEIndiaAndGetStockData && connectToNSEIndiaAndGetLotSizeForFnOStock
	private void getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData() {
		List<SymbolBean> list = cloudDataBaseTool.retrieveSymbolBeanList();
		
		/*List<SymbolBean> list =new ArrayList<SymbolBean>();
		SymbolBean b2 = new SymbolBean();
		b2.setSymbolId("PVR");
		b2.setLotSize(407);
		b2.setFnoInd(true);
		b2.setWeek52High(350d);
		b2.setWeek52Low(161d);
		list.add(b2);
		SymbolBean b = new SymbolBean();
		b.setSymbolId("DRREDDY");
		b.setLotSize(250);
		b.setFnoInd(true);
		b.setWeek52High(1500d);
		b.setWeek52Low(1001d);
		list.add(b);
		SymbolBean b3 = new SymbolBean();
		b3.setSymbolId("BHARTIARTL");
		b3.setLotSize(1851);
		b3.setFnoInd(true);
		b3.setWeek52High(1500d);
		b3.setWeek52Low(1001d);
		list.add(b3);*/
		
		
//System.out.println("Total Sysmbols size - "+list.size());
		prepareOptionChainDataMap();
//System.out.println("Total FnO Sysmbols size - "+StockUtil.getSymbols().size());
	}
	
	private void prepareOptionChainDataMap() {
		List<SymbolBean> list = cloudDataBaseTool.retrieveSymbolBeanList();
		for (SymbolBean symbolBean : list) {
			if (null != symbolBean.getFnoInd() && symbolBean.getFnoInd()) {
			OptionChainDataBean bean = new OptionChainDataBean(symbolBean.getSymbolId());
			bean.setWeek52High(symbolBean.getWeek52High());
			bean.setWeek52Low(symbolBean.getWeek52Low());
			bean.setLotSize(symbolBean.getLotSize());
			optionChainDataMap.put(symbolBean.getSymbolId(), bean);
			StockUtil.getSymbols().put(symbolBean.getSymbolId(), symbolBean.getLotSize());
			}
		}
	}
	
	public void retrieveStocAndPrepareOptionHistoryData() {
		getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData();
		
		for (String symbol : StockUtil.getSymbols().keySet()) {
			kiteFutureKeyMap.put(symbol, TradewareTraderUtil.getInstance().getKiteFuturekey(symbol));
			retriveHistoricalDataForEachStock(symbol);
			//Open interest start
			if (!failedResultDataMap.contains(symbol)) {
				OptionChainDataBean beanOI = NSEIndiaToolOptionChainTool_Org.getInstance().retriveOptionChainDataForEachStock(optionChainDataMap.get(symbol));
				//optionChainDataMap.put(symbol, beanOI);
				StockUtil.getSymbolTickerMap().put(symbol, beanOI.getTickerSize());
				//System.out.println("Option Ticker : "+symbol+" - "+StockUtil.getSymbolTickerMap().get(symbol));
			}
		}
		optionChainDataMap = NSEIndiaToolOptionChainTool_Org.getInstance().retriveOptionChainSpurtsDataForEachStock(optionChainDataMap);
	}
	
	//Duplicate code, same is there in cloudDataBaseTool.prepareAlgoCloudData(); Need to delete
	private void connectToNSEIndiaAndGetStockData() {
		try {
			String response = webClient.getPage(FO_STOCKS_URL).getWebResponse().getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
			for (int i = 0; i < rawJsonDataArray.size(); i++) {
				JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
				String symbol = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL));

				OptionChainDataBean bean = new OptionChainDataBean(symbol);
				bean.setSymbol(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL)));
				bean.setWeek52High(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_52WEEK_HIGH))
						.replaceAll(COMMA, EMPTY_STRING)));
				bean.setWeek52Low(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_52WEEK_LOW))
						.replaceAll(COMMA, EMPTY_STRING)));
				optionChainDataMap.put(symbol, bean);
				StockUtil.getSymbols().put(symbol, connectToNSEIndiaAndGetLotSizeForFnOStock(symbol));
				//System.out.println("Lot size finding - " + bean.getSymbol() + " - " + StockUtil.getSymbols().get(symbol));
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			// e.printStackTrace();
			System.out.println(
					"Error at NSEIndiaCloudDataBaseTool.connectToNSEIndiaAndGetStockData(SymbolBean bean, String stockUrl)"
							+ e.getCause());
		}
	}
	
	private Integer connectToNSEIndiaAndGetLotSizeForFnOStock(String symbol) {
		Integer marketLot = null;
		try {
			symbol = StockUtil.getSymbolForURL(symbol);
			HtmlPage page = webClient.getPage(FO_STOCKS_LOT_SIZE_URL + symbol);
			DomNodeList<HtmlElement> nodeList = page.getForms().get(1).getElementsByTagName(IMAGE_ATTRIBUTE);
			Iterator<HtmlElement> iterator = nodeList.iterator();

			while (iterator.hasNext()) {
				HtmlElement element = iterator.next();
				HtmlImage goButton = (HtmlImage) element;
				if (IMAGE_SOURCE_ATTRIBUTE.equals(goButton.getSrcAttribute())) {
					Page newPage = goButton.click();
					if (newPage.isHtmlPage()) {
						HtmlPage htmlPage = (HtmlPage) newPage;
						DomElement domelem = htmlPage.getElementById(RESPONSE_DIV);
						String text = domelem.getTextContent();

						JSONParser jsonParser = new JSONParser();
						JSONObject rawJsonData = (JSONObject) jsonParser.parse(text.trim());
						JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);

						for (int i = 0; i < rawJsonDataArray.size(); i++) {
							JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
							marketLot = Integer.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_MARKET_LOT)));
						}
					}
				}
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			// e.printStackTrace();
			System.out.println(
					"Error at NSEIndiaCloudDataBaseTool.connectToNSEIndiaAndGetLotSizeForFnOStock(String symbol)"
							+ e.getCause());
		}
		return marketLot;
	}
	
	
	//Handle top option strong BUY/SELL Picks
	private Set<String> topOptionStrongBuyTrades = new HashSet<String>();
	private Set<String> topOptionStrongSellTrades = new HashSet<String>();
	private Set<String> topAllOptionStrongTrades = new HashSet<String>();
	private TreeMap<Double, String> sortedOptionTrades = new TreeMap<Double, String>();
	
	private static final String EMPTY_STR = "",  NA = "NA", BUY = "BUY", SELL = "SELL", STRONG_BUY = "STRONG_BUY", STRONG_SELL = "STRONG_SELL";

	private void updateTopOptionStrongBuySellTrades(OptionChainDataBean bean) {
		if (null != bean.getOITrend() && (bean.getOITrend().contains(STRONG_BUY)
				|| bean.getOITrend().contains(STRONG_SELL))) {
			topAllOptionStrongTrades.add(bean.getSymbol());

			if (bean.getOITrend().contains(STRONG_BUY)) {

				if (!sortedOptionTrades.containsKey(bean.getTop1OINetChangeCall())) {
					sortedOptionTrades.put(bean.getTop1OINetChangeCall(), bean.getSymbol());
				} else {
					sortedOptionTrades.put(bean.getTop1OINetChangeCall() + .001, bean.getSymbol());
				}

			} else if (bean.getOITrend().contains(STRONG_SELL)) {

				if (!sortedOptionTrades.containsKey(bean.getTop1OINetChangePut())) {
					sortedOptionTrades.put(bean.getTop1OINetChangePut(), bean.getSymbol());
				} else {
					sortedOptionTrades.put(bean.getTop1OINetChangePut() + .001, bean.getSymbol());
				}
			}
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	private void updateTopOptionStrongBuySellTradesTag() {
		List<String> list = new ArrayList<String>(sortedOptionTrades.values());
		for (int i = 0; i<3;i++) {
			OptionChainDataBean bean = optionChainDataMap.get(list.get(i));
			if (bean.getOITrend().contains(STRONG_SELL)) {
				//bean.setOITrend("<b>STRONG_BUY</b>");
				bean.setStyle(bean.getStyle()+" font-weight:bold;");
				optionChainDataMap.replace(list.get(i), bean);
			}
		}
		
		for (int i = list.size()-1; i>=(list.size() - 3);i--) {
			OptionChainDataBean bean = optionChainDataMap.get(list.get(i));
			if (bean.getOITrend().contains(STRONG_BUY)) {
				//bean.setOITrend("<b>STRONG_SELL</b>");
				bean.setStyle(bean.getStyle()+" font-weight:bold;");
				optionChainDataMap.replace(list.get(i), bean);
			}
		}
	}
	
	private void clearTopOptionStrongBuySellTrades() {
		topAllOptionStrongTrades.clear();
		topOptionStrongBuyTrades.clear();
		topOptionStrongSellTrades.clear();
	}
	
	
	// Playable concept
	private Hashtable<String, OptionChainDataBean> optionChainDataMapPlayable = new Hashtable<String, OptionChainDataBean>();

	public Hashtable<String, OptionChainDataBean> getOptionChainDataMapPlayable() {
		return optionChainDataMapPlayable;
	}

	public void refreshOIDataForPlayable() {
		optionChainDataMapPlayable.clear();
		for (OptionChainDataBean bean : StockUtil.getPlayOptionsList()) {
			bean.setLotSize(StockUtil.getSymbols().get(bean.getSymbol()));
			optionChainDataMapPlayable.put(bean.getSymbol(), bean);
		}
		for (String symbol : optionChainDataMapPlayable.keySet()) {
			System.out.println("option channg data - " + symbol);
			OptionChainDataBean beanOI = NSEIndiaToolOptionChainToolNewSite.getInstance()
					.retriveOptionChainDataForEachStock(optionChainDataMapPlayable.get(symbol));
			optionChainDataMapPlayable.replace(symbol, beanOI);
		}
		optionChainDataMapPlayable = NSEIndiaToolOptionChainToolNewSite.getInstance()
				.retriveOptionChainSpurtsDataForEachStock(optionChainDataMapPlayable);
	}

}
