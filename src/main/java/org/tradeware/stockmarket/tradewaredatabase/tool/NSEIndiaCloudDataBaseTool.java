package org.tradeware.stockmarket.tradewaredatabase.tool;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tradeware.stockmarket.tradewaredatabase.service.ISettingLkpService;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.entity.SymbolEntity;
import com.tradeware.clouddatabase.service.SymbolService;
import com.tradeware.stockmarket.util.Constants;
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaCloudDataBaseTool {

	private static WebClient webClient = null;
	@Autowired
	private ISettingLkpService settingLkpService;
	@Autowired
	private SymbolService symbolService;
	public NSEIndiaCloudDataBaseTool() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setCssEnabled(false);
	}
	
//	@PostConstruct
//	public void init() {
//		webClient = new WebClient();
//		webClient.getOptions().setJavaScriptEnabled(false);
//		webClient.getOptions().setUseInsecureSSL(true);
//		webClient.getOptions().setCssEnabled(false);
//	}
	
	//private static final String BASE_URL = "https://nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm";
	private static final String BASE_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm";
	//private static final String STOCKS_URL = "https://nseindia.com/live_market/dynaContent/live_watch/stock_watch/";
	private static final String STOCKS_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/";
	private static final String STOCKS_URL_2 = "StockWatch.json";
	//private static final String FO_STOCKS_LOT_SIZE_URL = "https://nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuoteFO.jsp?instrument=FUTSTK&expiry=28NOV2019&type=-&strike=-&underlying=";
	private static final String FO_STOCKS_LOT_SIZE_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/"
			+ "get_quote/GetQuoteFO.jsp?instrument=FUTSTK&expiry=28MAY2020&type=-&strike=-&underlying=";

	
	private static final String IMAGE_ATTRIBUTE = "img";
	private static final String IMAGE_SOURCE_ATTRIBUTE = "/common/images/btn_go.gif";
	private static final String RESPONSE_DIV = "responseDiv";
	private static final String JSON_DATA_KEY_MARKET_LOT = "marketLot";
	private static final String JSON_DATA_KEY_VWAP_LOT = "vwap";
	private static final String VALUE_ATTRIBUTE = "value";
	private static final String INSTITUTIONAL_STOCK_WATCK = "iL";
	private static final String FUTURE_AND_OPTION_STOCK_WATC = "foSec";
	private static final String BASE_URL_DROPDOWN_SELECTION = "bankNiftySelect";
	private static final String SOVEREIGN_GOLD_BONDS_STOCK_WATCK = "sovGold";

	private static final String JSON_DATA_KEY_DATA = "data";
	private static final String JSON_DATA_KEY_SYMBOL = "symbol";
	private static final String JSON_DATA_KEY_52WEEK_HIGH = "wkhi";
	private static final String JSON_DATA_KEY_52WEEK_LOW = "wklo";
	private static final String JSON_DATA_KEY_LTP = "ltP";
	private static final String COMMA = ",";
	private static final String EMPTY_STRING = "";

	private Map<String, SymbolBean> fnoStocks = new TreeMap<String, SymbolBean>();
	private Map<String, SymbolBean> nonFnoStocks = new LinkedHashMap<String, SymbolBean>();

	//public void prepareAlgoCloudData() {
	private void prepareAlgoCloudData() {
		connectToNSEIndiaAndGetStockData();
		
		//temp code and uncomment above //bean.setLotSize(connectToNSEIndiaAndGetLotSizeForFnOStock(bean.getSymbol()));
		System.out.println("fnoStocks size - "+fnoStocks.size());
		for (String symb : fnoStocks.keySet()) {
				SymbolBean beanObj = fnoStocks.get(symb);
				System.out.println("map.put(\""+beanObj.getSymbolId()+"\", new Integer("+beanObj.getLotSize()+"));");
			}
		System.out.println("Final FnOSIZE "+fnoStocks.size());
		//temp code end
		saveStocksDataToCloudDatabase();
	}

	private void connectToNSEIndiaAndGetStockData() {
		try {
			HtmlPage page = webClient.getPage(BASE_URL);
			HtmlSelect bankNiftySelect = (HtmlSelect) page.getElementById(BASE_URL_DROPDOWN_SELECTION);
			List<HtmlOption> listOption = bankNiftySelect.getOptions();

			for (HtmlOption option : listOption) {
				String stockType = option.getAttribute(VALUE_ATTRIBUTE);
				System.out.println("stockType >>" + stockType);
				String stockUrl = STOCKS_URL + stockType + STOCKS_URL_2;
				if (INSTITUTIONAL_STOCK_WATCK.equals(stockType)) {
					break;
				} else if (SOVEREIGN_GOLD_BONDS_STOCK_WATCK.equals(stockType)) {
					continue;
				} else if (FUTURE_AND_OPTION_STOCK_WATC.equals(stockType)) {
					connectToNSEIndiaAndGetStockData(stockUrl, stockType, true);
				} else {
					connectToNSEIndiaAndGetStockData(stockUrl, stockType, false);
				}
			}
		} catch (FailingHttpStatusCodeException | IOException e) {
			// e.printStackTrace();
			System.out.println("Error at NSEIndiaCloudDataBaseTool.connectToNSEIndiaAndGetStockData()" + e.getCause());
		}
	}

	private void connectToNSEIndiaAndGetStockData(String stockUrl, String sector, boolean fnoInd) {
		try {
			String response = webClient.getPage(stockUrl).getWebResponse().getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
			for (int i = 0; i < rawJsonDataArray.size(); i++) {
				JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
				String symbol = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL));
				SymbolBean bean = new SymbolBean();
				bean.setEquityInd(true);
				bean.setFnoInd(fnoInd);
//System.out.println(symbol);
				bean.setSector(sector);
				bean.setIndexInd(false);
				

				bean.setSymbolId(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL)));
				bean.setWeek52High(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_52WEEK_HIGH))
						.replaceAll(COMMA, EMPTY_STRING)));
				bean.setWeek52Low(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_52WEEK_LOW))
						.replaceAll(COMMA, EMPTY_STRING)));
				Double lastPrice = Double.valueOf(
						String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_LTP)).replaceAll(COMMA, EMPTY_STRING));
				if (fnoInd) {
					bean.setLotSize(connectToNSEIndiaAndGetLotSizeForFnOStock(bean.getSymbolId()));
					fnoStocks.put(bean.getSymbolId(), bean);
					System.out.println("map.put(\""+bean.getSymbolId()+"\", new Integer("+bean.getLotSize()+"));");
				} else {
					if (!nonFnoStocks.keySet().contains(symbol)) {
						bean.setLotSize(((int) (25000 / lastPrice)) * 5);
						nonFnoStocks.put(bean.getSymbolId(), bean);
					}
				}
				//System.out.println("Lot size finding - " + bean.getSymbol());
			}
		} catch (FailingHttpStatusCodeException | IOException | ParseException e) {
			// e.printStackTrace();
			System.out.println(
					"Error at NSEIndiaCloudDataBaseTool.connectToNSEIndiaAndGetStockData(SymbolBean bean, String stockUrl)"
							+ e.getCause());
		}
	}

	private static String getSymbolForURL(String symbol) {
		return symbol.replaceAll("&", "%26");
		// String[] check = {"L%26TFH", "BANKNIFTY", "M%26MFIN", "M%26M"};
	}

	private Integer connectToNSEIndiaAndGetLotSizeForFnOStock(String symbol) {
		Integer marketLot = null;
		try {
			symbol = getSymbolForURL(symbol);
			//HtmlPage page = webClient.getPage(FO_STOCKS_LOT_SIZE_URL + symbol);
			String tempUrl1 = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuoteFO.jsp?underlying=";
			String tempUrl2 = "&instrument=FUTSTK&expiry=30JUL2020&type=-&strike=-";
			HtmlPage page = webClient.getPage(tempUrl1 + symbol + tempUrl2);
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
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(
					"Error at NSEIndiaCloudDataBaseTool.connectToNSEIndiaAndGetLotSizeForFnOStock(String symbol)"
							+ e.getCause());
		}
		return marketLot;
	}

	// temp
	private Integer connectToNSEIndiaAndGetLotSizeForStock(String symbol) {
		Integer marketLot = null;
		Double vmapVal = null;
		try {
			symbol = getSymbolForURL(symbol);
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
							vmapVal = Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_VWAP_LOT))
									.replaceAll(COMMA, EMPTY_STRING));
							marketLot = ((int) (25000 / vmapVal)) * 5;
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

	// temp
	//List<SymbolEntity> stockListEntity = null;
	List<SymbolBean> stockList = new ArrayList<SymbolBean>();

	private void saveStocksDataToCloudDatabase() {
		//stockListEntity = new ArrayList<SymbolEntity>();
		//List<SymbolBean> stockList = new ArrayList<SymbolBean>();
		// stockList.addAll(fnoStocks.values());
		// stockList.addAll(nonFnoStocks.values());
		int counter = 1;
		
		for (SymbolBean bean : nonFnoStocks.values()) {
			if (!stockList.contains(bean)) {
				//bean.setSymbolId(counter);
				ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
				bean.setDateTimeStamp(Date.from(zdt.toInstant()));
				stockList.add(bean);
				counter++;
			}
		}
		System.out.println("stockListsize"+stockList.size());
		
		for (SymbolBean bean : fnoStocks.values()) {
			//bean.setSymbolId(counter);
			bean.setFnoInd(true);
			if (bean.getLotSize() >= 100 && bean.getLotSize() <=50000) {
				bean.setCategoryFilter(1);
			} else {
				bean.setCategoryFilter(2);
			}
			bean.setValidInd(true);
			bean.setFnoInd(true);
			stockList.add(bean);
			counter++;
		}
System.out.println("stockListsize"+stockList.size());
		

		// until prepare database start
		for (SymbolBean bean : stockList) {
			ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
			bean.setDateTimeStamp(Date.from(zdt.toInstant()));
			list.add(bean);
			/*
			 * System.out.println("SymbolBean [symbolId=" + bean.getSymbolId() + "symbol=" +
			 * bean.getSymbol() + ", lotSize=" + bean.getLotSize() + ",  fnoInd=" +
			 * bean.getFnoInd() + ", week52High=" + bean.getWeek52High() + ", week52Low=" +
			 * bean.getWeek52Low() + ", sector=" + bean.getSector() + "]");
			 */
			/*SymbolEntity entity = new SymbolEntity();
			entity.populateEntity(bean);
			stockListEntity.add(entity); it will converted in service*/
		}
		System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>Entity list >> " + stockList.size());
		try {
			symbolService.saveAll(stockList);//(stockListEntity);
		} catch (Exception e) {
			System.out.println("Error on db save>>>>>>>>>>>>>");
			e.printStackTrace();
		}
		System.out.println(">>> Data base created successfully....");
		// until prepare database end
	}

	private List<SymbolBean> list = new ArrayList<SymbolBean>();

	/*public List<SymbolEntity> getList() {
		prepareAlgoCloudData();
		return stockListEntity;
	}*/
	
	public List<SymbolBean> getprepareAlgoCloudDataAndGetSymbolList() {
		prepareAlgoCloudData();
		return stockList;
	}
	
	public List<SymbolBean> getList1() {
		prepareAlgoCloudData();
		return list;
	}
	
	public List<SymbolBean> retrieveSymbolBeanList() {
		List<SymbolBean> symbolList = null;
		try {
			symbolList = symbolService.findAll();
		} catch (Exception e) {
			System.out.println("Error on db retrieve>>>>>>>>>>>>>");
			e.printStackTrace();
		}
		return symbolList;
	}
	
	// until prepare database end
}
