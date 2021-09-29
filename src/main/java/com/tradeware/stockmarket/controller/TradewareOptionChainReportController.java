package com.tradeware.stockmarket.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.clouddatabase.bean.OptionTradeFormDataBean;
import com.tradeware.clouddatabase.engine.NSEIndiaBankNiftyTradeToolNewNseSite;
import com.tradeware.clouddatabase.engine.NSEIndiaCloudDataBaseToolNewNseSite;
import com.tradeware.clouddatabase.engine.NSEIndiaToolOptionChainTool;
import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.bean.OptionChainDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.StockUtil;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.LTPQuote;

@Controller
public class TradewareOptionChainReportController {

	@RequestMapping(value = "getOptionChainReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/
				nSEIndiaTool.getOptionChainDataMap().values());

		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				return left.getSymbol().compareTo(right.getSymbol());
			}
		};
		Collections.sort(list, comparator);

		// return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());
		}
		return mv;
	}

	@RequestMapping(value = "getOptionChainReportSortByTrend", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport_SortByTrend() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/
				nSEIndiaTool.getOptionChainDataMap().values());

		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				return left.getOITrend().compareTo(right.getOITrend());
			}
		};
		Collections.sort(list, comparator);

		// return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());
		}
		return mv;
	}

	@RequestMapping(value = "getOptionChainReport2", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport2() {
		/* List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>( *//** NSEIndiaTool.getInstance() **/
		/*
		 * nSEIndiaTool.getOptionChainDataMap().values());
		 * 
		 * Comparator<OptionChainDataBean> comparator = new
		 * Comparator<OptionChainDataBean>() {
		 * 
		 * @Override public int compare(OptionChainDataBean left, OptionChainDataBean
		 * right) { //return right.getOITrend().compareTo(left.getOITrend()); return
		 * Double.valueOf(right.getStrongOrder()).compareTo(Double.valueOf(left.
		 * getStrongOrder())); } }; Collections.sort(list, comparator); // Add index
		 * option chain here List<OptionChainDataBean> listIndex = new
		 * ArrayList<OptionChainDataBean>(
		 *//** NSEIndiaTool.getInstance() **//*
												 * nSEIndiaTool.getOptionChainDataMapIndex().values()); for
												 * (OptionChainDataBean indexOi : listIndex) { list.add(0, indexOi); }
												 * //return new ModelAndView("optionsReport" , "optionCalls", list);
												 * ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
												 * mv.addObject( "optionChainDataList", list); if (!list.isEmpty()) {
												 * System.out.println("Candle Number - "+list.get(0).getCandleNumber());
												 * mv.addObject("candleNumber", list.get(0).getCandleNumber());
												 * 
												 * if( null != list.get(0).getDateTimeStamp() ) {
												 * mv.addObject("lastUpdateTime",
												 * getCurrentTime(list.get(0).getDateTimeStamp())); } } return mv;
												 */

		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				DatabaseHelper.getInstance().findAllByLatestOptinChainData());
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			// System.out.println("Candle Number - "+list.get(0).getCandleNumber() +" --
			// Time Frame Number - "+list.get(0).getTimeFrameNumber());
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());

			if (null != list.get(0).getDateTimeStamp()) {
				mv.addObject("lastUpdateTime",
						sathvikAshvikTechTraderTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp()));
			}
		}
		return mv;
	}

	@RequestMapping(value = "getOptionChainBankNiftyTrend", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReportForBankNiftyTrend() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				DatabaseHelper.getInstance().findAllByLatestOptinChainDataForBankNifty());
		ModelAndView mv = getModelAndView("nkp/optionChainBNTrendReport");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			// System.out.println("Candle Number - "+list.get(0).getCandleNumber() +" --
			// Time Frame Number - "+list.get(0).getTimeFrameNumber());
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());

			if (null != list.get(0).getDateTimeStamp()) {
				mv.addObject("lastUpdateTime",
						sathvikAshvikTechTraderTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp()));
			}
		}
		return mv;
	}

	@RequestMapping(value = "getOptionChainReportByGivenDate", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport2ByGivenDate() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				DatabaseHelper.getInstance().findAllOptinChainDataByGivenDate());
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			// System.out.println("Candle Number - "+list.get(0).getCandleNumber() +" --
			// Time Frame Number - "+list.get(0).getTimeFrameNumber());
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());

			if (null != list.get(0).getDateTimeStamp()) {
				mv.addObject("lastUpdateTime",
						sathvikAshvikTechTraderTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp()));
			}
		}
		return mv;
	}

	@RequestMapping(value = "getBestTradeReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getBestTradeReport() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				DatabaseHelper.getInstance().findAllBestTradeOptinChainData());
		ModelAndView mv = getModelAndView("nkp/optionChainOIReportTradable");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			// System.out.println("Candle Number - "+list.get(0).getCandleNumber() +" --
			// Time Frame Number - "+list.get(0).getTimeFrameNumber());
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());

			if (null != list.get(0).getDateTimeStamp()) {
				mv.addObject("lastUpdateTime",
						sathvikAshvikTechTraderTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp()));
			}
		}
		return mv;
	}

	@RequestMapping(value = "getBestTradeReportDetails", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(
			@RequestParam(value = "symbol") String symbol) {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				DatabaseHelper.getInstance().findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(symbol));
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());

			if (null != list.get(0).getDateTimeStamp()) {
				mv.addObject("lastUpdateTime",
						sathvikAshvikTechTraderTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp()));
			}
		}
		return mv;
	}

	/*
	 * public String getCurrentTime(Date date) { if (null != date) {
	 * SimpleDateFormat dtf = new SimpleDateFormat(Constants.TIME_PATTERN); return
	 * dtf.format(date); ZonedDateTime zdt =
	 * ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(Constants.TIME_ZONE));
	 * 
	 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.TIME_PATTERN);
	 * return zdt.format(dtf); } return Constants.EMPTY_STRING; }
	 */
	@RequestMapping(value = "getOptionChainReport2SortByTrend", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport2_SortByTrend() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/
				nSEIndiaTool.getOptionChainDataMap().values());

		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				return right.getOITrend().compareTo(left.getOITrend());
			}
		};
		Collections.sort(list, comparator);

		// Add index option chain here
		List<OptionChainDataBean> listIndex = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/
				nSEIndiaTool.getOptionChainDataMapIndex().values());
		for (OptionChainDataBean indexOi : listIndex) {
			list.add(0, indexOi);
		}

		// return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());
		}
		return mv;
	}

	@RequestMapping(value = "getOptionChainReport2SortByPercenChange", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport2_SortByPercentageChange() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				DatabaseHelper.getInstance().findAllByLatestOptinChainData());

		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {

				if (right.getChangePercentage() == null) {
					return -1;
				} else if (left.getChangePercentage() == null) {
					return 1;
				}
				return right.getChangePercentage().compareTo(left.getChangePercentage());
			}
		};
		Collections.sort(list, comparator);

		// Add index option chain here
		List<OptionChainDataBean> listIndex = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/
				nSEIndiaTool.getOptionChainDataMapIndex().values());
		for (OptionChainDataBean indexOi : listIndex) {
			list.add(0, indexOi);
		}

		// return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject("optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
			mv.addObject("failedOptionChainSymbolCount", nSEIndiaTool.getFailedResultDataMap().size());
			if (null != list.get(0).getDateTimeStamp()) {
				mv.addObject("lastUpdateTime",
						sathvikAshvikTechTraderTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp()));
			}
		}
		return mv;
	}

	// should be in common controller
	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}

	// connect to nseindia.in and retrieve stocks data and insert into t_symbol
	// table
	// @RequestMapping(value = "/prepareTradewareDatabase", method =
	// RequestMethod.GET)
	public ModelAndView getprepareAlgoCloudDataAndGetSymbolList() {
		/** NSEIndiaTool.getInstance() **/
		nSEIndiaCloudDatabaseTool.getPrepareAlgoCloudDataAndGetSymbolList();

		return sathvikAshvikTechTraderTool.prepareHeaderSwitchView("home2");
	}

	@Autowired
	private TradewareTool sathvikAshvikTechTraderTool;

	@Autowired
	private NSEIndiaToolOptionChainTool nSEIndiaTool;

	@Autowired
	private NSEIndiaCloudDataBaseToolNewNseSite nSEIndiaCloudDatabaseTool;

	@RequestMapping(value = "/refreshOIDataForAll", method = RequestMethod.GET)
	public ModelAndView refreshOIDataForAll() {
		nSEIndiaTool.refreshOIDataForAll(true);
		return getOptionChainReport2_SortByTrend();
	}

	@RequestMapping(value = "/forcePrepareOptionHistoryData", method = RequestMethod.GET)
	public ModelAndView refreshOIDataForTops() {
		nSEIndiaTool.forcePrepareOptionHistoryData();
		return getOptionChainReport2_SortByTrend();
	}

	@RequestMapping(value = "applyBestTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView applyBestTrades(@RequestParam(value = "symbol") String symbol,
			@RequestParam(value = "isChecked") Boolean isChecked) {
		if (null != symbol && !Constants.EMPTY_STRING.equals(symbol)) {
			isChecked = (null != isChecked && isChecked);

			// OptionChainDataBean bean =
			// DatabaseHelper.getInstance().findBySymbolIdAndDateStampAndParentRecordIndTrue(symbol);
			OptionChainDataBean bean = DatabaseHelper.getInstance()
					.findBySymbolIdAndParentRecordIndTrueForCurrentDay(symbol);
			// OptionChainDataBean bean = nSEIndiaTool.getOptionChainDataMap().get(symbol);
			/*
			 * if (bean == null) { bean =
			 * nSEIndiaTool.getOptionChainBaseDataMap().get(symbol); }
			 */
			if (bean != null) {
				bean.setBestTradeInd(isChecked);
				bean.setLtpOnDecision(bean.getUnderlyingPrice());
				bean.setChangePercentage(0.0);
				if (null != bean.getOITrend() && bean.getOITrend().contains(Constants.BUY)) {
					bean.setBestEntry(bean.getTop1StrikePriceCall());
					bean.setGoForTrade(Constants.BUY);
				}
				if (null != bean.getOITrend() && bean.getOITrend().contains(Constants.SELL)) {
					bean.setBestEntry(bean.getTop3StrikePricePut());
					bean.setGoForTrade(Constants.SELL);
				}
				DatabaseHelper.getInstance().updateBestTrade(bean);
			}
		}
		return getOptionChainReport2();
	}

	/*
	 * @RequestMapping(value = "/bestTrades", method = RequestMethod.GET) public
	 * ModelAndView refreshOIDataForPlayable() {
	 * nSEIndiaTool.refreshOIDataForPlayable(); List<OptionChainDataBean> list = new
	 * ArrayList<OptionChainDataBean>(
	 * nSEIndiaTool.getOptionChainDataMapPlayable().values());
	 * 
	 * Comparator<OptionChainDataBean> comparator = new
	 * Comparator<OptionChainDataBean>() {
	 * 
	 * @Override public int compare(OptionChainDataBean left, OptionChainDataBean
	 * right) { return
	 * Double.valueOf(right.getStrongOrder()).compareTo(Double.valueOf(left.
	 * getStrongOrder())); } }; Collections.sort(list, comparator);
	 * 
	 * // Add index option chain here List<OptionChainDataBean> listIndex = new
	 * ArrayList<OptionChainDataBean>(
	 * nSEIndiaTool.getOptionChainDataMapIndex().values()); for (OptionChainDataBean
	 * indexOi : listIndex) { list.add(0, indexOi); }
	 * 
	 * // return new ModelAndView("optionsReport" , "optionCalls", list);
	 * ModelAndView mv = getModelAndView("nkp/optionChainOIReportTradable");
	 * mv.addObject("optionChainDataList", list); return mv; }
	 */

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public ResponseEntity<String> test() {
		String str = "1     :";
		try {
			/*
			 * ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE))
			 * .atZone(ZoneId.of(Constants.TIME_ZONE)); str = str + zdt.toString() + "<br>";
			 * str = str + Date.from(zdt.toInstant()) + "<br>"; str = str +
			 * getCurrentTime(Date.from(zdt.toInstant())) + "<br>"; str = str +
			 * getCurrentTime(GregorianCalendar.from(zdt).getTime()) + "<br>"; str = str +
			 * "<br>" + "<br>" + "<br>";
			 * 
			 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.TIME_PATTERN);
			 * str = str + zdt.format(dtf); str = str + "<br>" + "<br>" + "<br>";
			 * 
			 * LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); str =
			 * str + ldt.format(dtf);
			 */
			ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			str = str + Date.from(zdt.toInstant());
			str = str + "<br>" + "<br>" + "<br>" + "2     :";

			zdt = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.of(Constants.TIME_ZONE));
			str = str + Date.from(zdt.toInstant());
			str = str + "<br>" + "<br>" + "<br>" + "3     :";

			zdt = ZonedDateTime.now(ZoneId.systemDefault());
			str = str + Date.from(zdt.toInstant());
			str = str + "<br>" + "<br>" + "<br>" + "4     :";

			zdt = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault());
			str = str + Date.from(zdt.toInstant());
			str = str + "<br>" + "<br>" + "<br>" + "5     :";

			LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			str = str + Timestamp.valueOf(ldt);
			str = str + "<br>" + "<br>" + "<br>" + "6     :";

			LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
			str = str + java.sql.Date.valueOf(ld);
			str = str + "<br>" + "<br>" + "<br>" + "7     :";

			str = str + Date.from(ld.atStartOfDay(ZoneId.of(Constants.TIME_ZONE)).toInstant());
			str = str + "<br>" + "<br>" + "<br>" + "8     :";

			ldt = LocalDateTime.now(ZoneId.systemDefault());
			str = str + Timestamp.valueOf(ldt);
			str = str + "<br>" + "<br>" + "<br>" + "9     :";

		} catch (Exception e) {
			str = "ERROR - " + e.getMessage();
		}
		return new ResponseEntity<>(str, HttpStatus.OK);
	}

	@Autowired
	private KiteConnectTool kiteConnectTool;
	static final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
	static Date startOfdate;
	static int errorCount = 0;
	static int processedCount = 0;
	static int retryErrorCount = 0;
	static int updateRecordCount = 0;
	static String symbolName = "";
	static String kiteKey = "";
	ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMapAll = new ConcurrentHashMap<String, StrategyOrbDataBean>();

	@RequestMapping(value = "test2", method = RequestMethod.GET)
	public ResponseEntity<String> test2() {
		errorCount = 0;
		processedCount = 0;
		retryErrorCount = 0;
		updateRecordCount = 0;
		String str = "1     :";
		if (sathvikAshvikTechTraderTool.getBaseDataMap().isEmpty()
				|| sathvikAshvikTechTraderTool.getBaseDataMapAll().isEmpty()) {
			sathvikAshvikTechTraderTool.updateKiteFutureKeyAndInstrumentToken();
			baseDataMapAll.putAll(sathvikAshvikTechTraderTool.getBaseDataMap());
			baseDataMapAll.putAll(sathvikAshvikTechTraderTool.getBaseDataMapAll());
		}

		TradewareLogger.saveInfoLog("INFO", "INFO", String.valueOf(baseDataMapAll.size()));

		List<StrategyOrbDataBean> beans = null;
		String dummyKey = null;
		try {
			beans = DatabaseHelper.getInstance().findAllByTradedDateStampOrderByTradedAtDtTmDesc();
			/*
			 * List<StrategyOrbDataBean> beans = new ArrayList<StrategyOrbDataBean>();
			 * StrategyOrbDataBean temp = new StrategyOrbDataBean(407, "PVR");
			 * temp.setSymbolName("PVR(FUT)");
			 * temp.setCandleNumber(4);temp.setTradableStateId(Constants.BUY);
			 * beans.add(temp);
			 */

			startOfdate = getFromDateForKiteHistDataOnCurrentDay_9_15();
			// System.out.println(startOfdate);

			for (StrategyOrbDataBean bean : beans) {
				dummyKey = bean.getSymbolName();
				try {

					realUpdate(bean);
					processedCount++;
				} catch (KiteException | Exception e) {
					errorCount++;
					if (errorCount < 100) {
						try {
							Thread.sleep(1000);
							realUpdate(bean);
							processedCount++;
						} catch (KiteException e1) {
							retryErrorCount++;
						}
					}
					System.out.println(
							"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>    " + errorCount);
					e.printStackTrace();
					TradewareLogger.saveFatalErrorLog(errorCount + "__s" + "MANUAL_RUN", "tempVerify" + dummyKey, e,
							Constants.ERROR_TYPE_EXCEPTION);
				}
			}
		} catch (Exception e) {
			str = "ERROR - " + e.getMessage();
		}
		return new ResponseEntity<>(String.valueOf(errorCount) + " - " + beans.size() + " ---  " + processedCount
				+ "---------" + retryErrorCount + "-----" + updateRecordCount, HttpStatus.OK);
	}

	@RequestMapping(value = "test3", method = RequestMethod.GET)
	public ResponseEntity<String> test3() {
		try {
			if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
				Map<String, LTPQuote> quotesMap = null;

				List<String> symbolsArray = new ArrayList<String>();
				symbolsArray.add("NSE:NIFTY 50");// NIFTY 50
				String nigtyFut = sathvikAshvikTechTraderTool.getKiteFutureKey("NIFTY");
				System.out.println(nigtyFut);
				symbolsArray.add(nigtyFut);

				quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getLTP(symbolsArray.toArray(new String[0]));

				for (String key : quotesMap.keySet()) {
					LTPQuote cashQuote = quotesMap.get(key);
					System.out.println(cashQuote.lastPrice);
				}

				symbolsArray.add("NSE:NIFTY BANK");// NIFTY 50
				String nigtyBankFut = sathvikAshvikTechTraderTool.getKiteFutureKey("BANKNIFTY");
				System.out.println(nigtyBankFut);
				symbolsArray.add(nigtyBankFut);

				quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getLTP(symbolsArray.toArray(new String[0]));

				for (String key : quotesMap.keySet()) {
					LTPQuote cashQuote = quotesMap.get(key);
					System.out.println(cashQuote.lastPrice);
				}

				symbolsArray.add("NSE:NIFTY FIN SERVICE");// NIFTY 50
				String niftyFinancalFut = sathvikAshvikTechTraderTool.getKiteFutureKey("FINNIFTY");
				System.out.println(niftyFinancalFut);
				symbolsArray.add(niftyFinancalFut);

				quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getLTP(symbolsArray.toArray(new String[0]));

				for (String key : quotesMap.keySet()) {
					LTPQuote cashQuote = quotesMap.get(key);
					System.out.println(cashQuote.lastPrice);
				}

				symbolsArray.add("NSE:NIFTY AUTO");
				symbolsArray.add("NSE:NIFTY MEDIA");
				symbolsArray.add("NSE:NIFTY INFRA");
				symbolsArray.add("NSE:NIFTY CONSUMPTION");
				symbolsArray.add("NSE:NIFTY FMCG");

				symbolsArray.add("NSE:NIFTY FMCG");
				symbolsArray.add("NSE:NIFTY FIN SERVICE");
				symbolsArray.add("NSE:NIFTY ENERGY");
				symbolsArray.add("NSE:NIFTY 100");
				symbolsArray.add("NSE:NIFTY 200");
				symbolsArray.add("NSE:NIFTY 500");

				symbolsArray.add("NSE:NIFTY IT");
				symbolsArray.add("NSE:NIFTY METAL");
				symbolsArray.add("NSE:NIFTY ENERGY");
				symbolsArray.add("NSE:NIFTY PHARMA");
				symbolsArray.add("NSE:NIFTY REALTY");
				symbolsArray.add("NSE:NIFTY PSU BANK");
				symbolsArray.add("NSE:NIFTY PRIVATEBANK");

				quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getLTP(symbolsArray.toArray(new String[0]));

				for (String key : quotesMap.keySet()) {
					LTPQuote cashQuote = quotesMap.get(key);
					System.out.println(key + " :  " + cashQuote.lastPrice + "		:	"
							+ (((int) (25000 / cashQuote.lastPrice)) * 5));
				}

				// kiteConnectTool.addIndexesToTrade();
				/*
				 * public ConcurrentHashMap<String, StrategyOrbDataBean> addIndexesToTrade( ) {
				 * ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMapIndex = new
				 * ConcurrentHashMap<String, StrategyOrbDataBean>(); try { StrategyOrbDataBean
				 * bean1 = new StrategyOrbDataBean(75, "NIFTY 50");
				 * bean1.setSymbolName("NIFTY 50"); bean1.setKiteFutureKey("NSE:NIFTY 50");
				 * baseDataMapIndex.put(bean1.getKiteFutureKey(), bean1);
				 * 
				 * StrategyOrbDataBean bean2 = new StrategyOrbDataBean(75, "NIFTY 50(FUT)");
				 * bean2.setSymbolName("NIFTY 50(FUT)");
				 * bean2.setKiteFutureKey(sathvikAshvikTechTraderTool.getKiteFuturekey("NIFTY"))
				 * ; baseDataMapIndex.put(bean2.getKiteFutureKey(), bean2);
				 * 
				 * StrategyOrbDataBean bean3 = new StrategyOrbDataBean(25, "BANKNIFTY");
				 * bean3.setSymbolName("BANK NIFTY"); bean3.setKiteFutureKey("NSE:NIFTY BANK");
				 * baseDataMapIndex.put(bean3.getKiteFutureKey(), bean3);
				 * 
				 * StrategyOrbDataBean bean4 = new StrategyOrbDataBean(25, "BANKNIFTY");
				 * bean4.setSymbolName("BANK NIFTY(FUT)");
				 * bean4.setKiteFutureKey(sathvikAshvikTechTraderTool.getKiteFuturekey(
				 * "BANKNIFTY")); baseDataMapIndex.put(bean4.getKiteFutureKey(), bean4);
				 * 
				 * StrategyOrbDataBean bean5 = new StrategyOrbDataBean(50, "NIFTY IT");
				 * bean5.setSymbolName("NIFTY IT"); bean5.setKiteFutureKey("NSE:NIFTY IT");
				 * baseDataMapIndex.put(bean5.getKiteFutureKey(), bean5);
				 * 
				 * StrategyOrbDataBean bean6 = new StrategyOrbDataBean(75, "NIFTY 100");
				 * bean6.setSymbolName("NIFTY 100"); bean6.setKiteFutureKey("NSE:NIFTY 100");
				 * baseDataMapIndex.put(bean6.getKiteFutureKey(), bean6);
				 * 
				 * StrategyOrbDataBean bean7 = new StrategyOrbDataBean(75, "NIFTY 500");
				 * bean7.setSymbolName("NIFTY 500"); bean7.setKiteFutureKey("NSE:NIFTY 500");
				 * baseDataMapIndex.put(bean7.getKiteFutureKey(), bean7);
				 * 
				 * StrategyOrbDataBean bean8 = new StrategyOrbDataBean(75, "NIFTY PHARMA");
				 * bean8.setSymbolName("NIFTY PHARMA");
				 * bean8.setKiteFutureKey("NSE:NIFTY PHARMA");
				 * baseDataMapIndex.put(bean8.getKiteFutureKey(), bean8);
				 * 
				 * StrategyOrbDataBean bean9 = new StrategyOrbDataBean(75, "NIFTY AUTO");
				 * bean9.setSymbolName("NIFTY AUTO"); bean9.setKiteFutureKey("NSE:NIFTY AUTO");
				 * baseDataMapIndex.put(bean9.getKiteFutureKey(), bean9);
				 * 
				 * StrategyOrbDataBean bean10 = new StrategyOrbDataBean(75,
				 * "NIFTY FIN SERVICE"); bean10.setSymbolName("NIFTY FIN SERVICE");
				 * bean10.setKiteFutureKey("NSE:NIFTY FIN SERVICE");
				 * baseDataMapIndex.put(bean10.getKiteFutureKey(), bean10);
				 * 
				 * StrategyOrbDataBean bean11 = new StrategyOrbDataBean(150,
				 * "NIFTY CONSUMPTION"); bean11.setSymbolName("NIFTY CONSUMPTION");
				 * bean11.setKiteFutureKey("NSE:NIFTY CONSUMPTION");
				 * baseDataMapIndex.put(bean11.getKiteFutureKey(), bean11);
				 * 
				 * StrategyOrbDataBean bean12 = new StrategyOrbDataBean(25, "NIFTY FMCG");
				 * bean12.setSymbolName("NIFTY FMCG");
				 * bean12.setKiteFutureKey("NSE:NIFTY FMCG");
				 * baseDataMapIndex.put(bean12.getKiteFutureKey(), bean12);
				 * 
				 * StrategyOrbDataBean bean13 = new StrategyOrbDataBean(50, "NIFTY ENERGY");
				 * bean13.setSymbolName("NIFTY ENERGY");
				 * bean13.setKiteFutureKey("NSE:NIFTY ENERGY");
				 * baseDataMapIndex.put(bean13.getKiteFutureKey(), bean13);
				 * 
				 * StrategyOrbDataBean bean14 = new StrategyOrbDataBean(250, "NIFTY INFRA");
				 * bean14.setSymbolName("NIFTY INFRA");
				 * bean14.setKiteFutureKey("NSE:NIFTY INFRA");
				 * baseDataMapIndex.put(bean14.getKiteFutureKey(), bean14);
				 * 
				 * StrategyOrbDataBean bean15 = new StrategyOrbDataBean(250, "NIFTY METAL");
				 * bean15.setSymbolName("NIFTY METAL");
				 * bean15.setKiteFutureKey("NSE:NIFTY METAL");
				 * baseDataMapIndex.put(bean15.getKiteFutureKey(), bean15);
				 * 
				 * StrategyOrbDataBean bean16 = new StrategyOrbDataBean(300, "NIFTY PSU BANK");
				 * bean16.setSymbolName("NIFTY PSU BANK");
				 * bean16.setKiteFutureKey("NSE:NIFTY PSU BANK");
				 * baseDataMapIndex.put(bean16.getKiteFutureKey(), bean16);
				 * 
				 * StrategyOrbDataBean bean17 = new StrategyOrbDataBean(300, "NIFTY MEDIA");
				 * bean17.setSymbolName("NIFTY MEDIA");
				 * bean17.setKiteFutureKey("NSE:NIFTY MEDIA");
				 * baseDataMapIndex.put(bean17.getKiteFutureKey(), bean17);
				 * 
				 * StrategyOrbDataBean bean18 = new StrategyOrbDataBean(750, "NIFTY REALTY");
				 * bean18.setSymbolName("NIFTY REALTY");
				 * bean18.setKiteFutureKey("NSE:NIFTY REALTY");
				 * baseDataMapIndex.put(bean18.getKiteFutureKey(), bean18);
				 * 
				 * Map<String, Quote> quotesMap =
				 * StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
				 * .getQuote(baseDataMapIndex.keySet().toArray(new String[0]));//
				 * (symbolMap.keySet().toArray(new // String[0]));
				 * 
				 * for (String key : quotesMap.keySet()) { Quote quote = quotesMap.get(key);
				 * 
				 * if (null != quotesMap.get(key).depth && null != quotesMap.get(key).depth.buy
				 * && !quotesMap.get(key).depth.buy.isEmpty()) {
				 * 
				 * StrategyOrbDataBean beanClone = baseDataMapIndex.get(key);
				 * 
				 * beanClone.setCandleTimeFrame(Constants.KITE_HIST_DATA_15_MINUTES_INTERVAL);
				 * beanClone.setInstrumentToken(quote.instrumentToken);
				 * baseDataMapIndex.replace(key, beanClone); } else {
				 * System.out.println("Removed : "+key);
				 * sathvikAshvikTechTraderTool.getBaseDataMapAll().remove(key); } } } catch
				 * (JSONException | IOException | KiteException e) {
				 * TechTraderLogger.saveFatalErrorLog(Constants.CLASS_KITECONNECTTOOL,
				 * Constants.METHOD_UPDATEDAYOHLC, e, Constants.ERROR_TYPE_KITEEXCEPTION);
				 * TechTraderLogger.sysoutPrintlnForLocalTest(Constants.CLASS_KITECONNECTTOOL +
				 * Constants.METHOD_UPDATEDAYOHLC + Constants.ERROR_TYPE_KITEEXCEPTION); }
				 * return baseDataMapIndex; }
				 */
			} else {
				/*
				 * NkpAlgoLogger.logError(AbstractAlgoTraderTool.class,
				 * "updateKiteFutureKeyAndInstrumentTokenForNifty50",
				 * "FATAL ERROR ADMIN LOGIN REQUIRED**********************************************"
				 * + getCurrentDateTime());
				 */
			}
		} catch (JSONException | IOException | KiteException e) {
			/*
			 * NkpAlgoLogger.logError(AbstractAlgoTraderTool.class,
			 * "updateKiteFutureKeyAndInstrumentTokenForNifty50", "FATAL ERROR ExCEPTION" +
			 * getCurrentDateTime());
			 */
			e.printStackTrace();
		}
		return new ResponseEntity("Test.....", HttpStatus.OK);
	}

	private void realUpdate(StrategyOrbDataBean bean) throws JSONException, IOException, KiteException {

		if (bean.getSymbolName().endsWith(Constants.FUTURE_SYMBOL)) {
			symbolName = bean.getSymbolName().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING);
			kiteKey = sathvikAshvikTechTraderTool.getKiteFutureKey(symbolName);
			bean.setInstrumentToken((baseDataMapAll.get(kiteKey)).getInstrumentToken());
		} else {
			bean.setInstrumentToken(
					(baseDataMapAll.get(Constants.FUTURE_KEY_PREFIX_NSE + bean.getSymbolId())).getInstrumentToken());
		}

		if (Constants.BUY.equals(bean.getTradableStateId()) || Constants.SELL.equals(bean.getTradableStateId())) {
			if (null != bean.getCandleNumber() && bean.getCandleNumber() > 0) {
				Date fromDate1Minte = new Date(
						startOfdate.getTime() + (((bean.getCandleNumber() - 2) * 15) * ONE_MINUTE_IN_MILLIS));
				Date toDate1Minte = new Date(
						startOfdate.getTime() + (((bean.getCandleNumber() - 1) * 15) * ONE_MINUTE_IN_MILLIS) - 2000);

				Date fromDate15Minte = getFromDateForKiteHistDataOnCurrentDay_9_15();
				Date toDate15Minte = new Date(
						startOfdate.getTime() + (((bean.getCandleNumber()) * 15) * ONE_MINUTE_IN_MILLIS));

				get1MinuteHist(fromDate1Minte, toDate1Minte, fromDate15Minte, toDate15Minte, bean);
				/*
				 * System.out.println(fromDate1Minte+" - "+toDate1Minte+" - "
				 * +fromDate15Minte+" - "+toDate15Minte);
				 * System.out.println(bean.getItemId()+" - "+bean.getSymbolName()+" - "+bean.
				 * getCandleNumber()+" - A_H:"+avgHigh+" - A_L:"+avgLow+" - A_C"
				 * +avgClose+" - Op:"+cuurentCandleOpen +" - VWAP:"+calculatedVWAP+" - H_C:"+
				 * sathvikAshvikTechTraderTool.getRoundupToTwoValue(avgHigh-avgClose)
				 * +" - C_L:"+kiteConnectTool.getRoundupToTwoValue(avgClose-avgLow));
				 */
				TradewareLogger.sysoutPrintlnForLocalTest(
						fromDate1Minte + " - " + toDate1Minte + " - " + fromDate15Minte + " - " + toDate15Minte);
				TradewareLogger.sysoutPrintlnForLocalTest(bean.getItemId() + " - " + bean.getSymbolName() + " - "
						+ bean.getCandleNumber() + " - A_H:" + avgHigh + " - A_L:" + avgLow + " - A_C" + avgClose
						+ " - Op:" + cuurentCandleOpen + " - VWAP:" + calculatedVWAP + " - H_C:"
						+ kiteConnectTool.getRoundupToTwoValue(avgHigh - avgClose) + " - C_L:"
						+ kiteConnectTool.getRoundupToTwoValue(avgClose - avgLow));
				TradewareLogger.sysoutPrintlnForLocalTest(
						"---------------------------------------------------------------------------------------------------------\r\n");

				TradewareLogger.saveInfoLog("INFO_INFO", "INFO_INFO",
						fromDate1Minte + " - " + toDate1Minte + " - " + fromDate15Minte + " - " + toDate15Minte
								+ " :::::: " + bean.getItemId() + " - " + bean.getSymbolName() + " - "
								+ bean.getCandleNumber() + " - A_H:" + avgHigh + " - A_L:" + avgLow + " - A_C"
								+ avgClose + " - Op:" + cuurentCandleOpen + " - VWAP:" + calculatedVWAP + " - H_C:"
								+ kiteConnectTool.getRoundupToTwoValue(avgHigh - avgClose) + " - C_L:"
								+ kiteConnectTool.getRoundupToTwoValue(avgClose - avgLow));

				// if(null == bean.getCurrentCandleAvgHigh()) {
				DatabaseHelper.getInstance().updateTradeAVGHLC(avgHigh, avgLow, avgClose, cuurentCandleOpen,
						kiteConnectTool.getRoundupToTwoValue(avgHigh - avgClose),
						kiteConnectTool.getRoundupToTwoValue(avgClose - avgLow), calculatedVWAP, bean.getItemId());
				updateRecordCount++;
				// }
			}

		}

	}

	public Date getFromDateForKiteHistDataOnCurrentDay_9_15() {
		Date tradeDay = sathvikAshvikTechTraderTool.getTradeDateForReport();
		LocalDateTime currentTime = LocalDateTime.ofInstant(tradeDay.toInstant(), ZoneId.of(Constants.TIME_ZONE));
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN_HIST_DATA);
		Date from = Calendar.getInstance().getTime();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_HIST_DATA);
			currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(),
					9, 15, 0);
			from = formatter.parse(currentTime.format(dtf));
		} catch (ParseException e) {
			TradewareLogger.saveErrorLog("TradewareOptionChainReportController",
					"getFromDateForKiteHistDataOnCurrentDay_9_15", e, Constants.ERROR_TYPE_PARSE_EXCEPTION,
					Constants.ERROR_SEVERIRITY_FATAL);
		}
		return from;
	}

	static double totalLow = 0;
	static double totalHigh = 0;
	static double totalClose = 0;

	static double avgLow = 0;
	static double avgHigh = 0;
	static double avgClose = 0;

	static double cuurentCandleOpen = 0;

	static double totalCandleAvgPrice = 0;
	static long totalCandleVolume = 0;
	// static double totalCandleCloseAvgPrice = 0;
	static double calculatedVWAP = 0;

	private void get1MinuteHist(Date fromDate, Date toDate, Date fromDate15Minte, Date toDate15Minute,
			StrategyOrbDataBean bean) throws JSONException, IOException, KiteException {
		totalLow = 0;
		totalHigh = 0;
		totalClose = 0;
		cuurentCandleOpen = 0;

		avgLow = 0;
		avgHigh = 0;
		avgClose = 0;

		totalCandleAvgPrice = 0;
		totalCandleVolume = 0;
		// totalCandleCloseAvgPrice = 0;
		calculatedVWAP = 0;

		HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER).getHistoricalData(
				fromDate, toDate, String.valueOf(bean.getInstrumentToken()),
				Constants.KITE_HIST_DATA_1_MINUTES_INTERVAL, false);

		List<HistoricalData> histDataList = histData.dataArrayList;
		if (null != histDataList && !histDataList.isEmpty()) {

			for (int i = 0; i <= histDataList.size() - 2; i++) {
				HistoricalData data = histDataList.get(i);
				totalLow = totalLow + data.low;
				totalHigh = totalHigh + data.high;
				totalClose = totalClose + data.close;
			}
			avgLow = kiteConnectTool.getRoundupToTwoValue(totalLow / (histDataList.size() - 1));
			avgHigh = kiteConnectTool.getRoundupToTwoValue(totalHigh / (histDataList.size() - 1));
			avgClose = kiteConnectTool.getRoundupToTwoValue(totalClose / (histDataList.size() - 1));

			HistoricalData histData15Minute = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
					.getHistoricalData(/* fromDate15Minte *//* startOfdate */ fromDate15Minte, toDate15Minute,
							String.valueOf(bean.getInstrumentToken()), Constants.KITE_HIST_DATA_15_MINUTES_INTERVAL,
							false);

			List<HistoricalData> histDataList15Minute = histData15Minute.dataArrayList;
			if (null != histDataList15Minute && !histDataList15Minute.isEmpty()) {
				HistoricalData data15 = histDataList15Minute.get(histDataList15Minute.size() - 2);
				cuurentCandleOpen = data15.open;
			}

			for (int i = 0; i <= histDataList15Minute.size() - 3; i++) {
				HistoricalData vwapHistdata = histDataList15Minute.get(i);
				totalCandleAvgPrice = totalCandleAvgPrice
						+ (((vwapHistdata.high + vwapHistdata.low + vwapHistdata.close) / 3) * vwapHistdata.volume);
				totalCandleVolume = totalCandleVolume + vwapHistdata.volume;

				// totalCandleCloseAvgPrice = totalCandleCloseAvgPrice + (vwapHistdata.close *
				// vwapHistdata.volume);

			}
			calculatedVWAP = kiteConnectTool.getRoundupToTwoValue(totalCandleAvgPrice / totalCandleVolume);

		} else {
			System.out.println("ERROR get1MinuteHist");
		}
	}

	@RequestMapping(value = "attentionCallAndPut", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByAttentionIndCallAndPut() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				// DatabaseHelper.getInstance().findAllByAttentionIndAndDateStamp("AND"));
				DatabaseHelper.getInstance().findAllByAttentionIndAndDateStampStrict());
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject("optionChainDataList", list);
		return mv;
	}

	@RequestMapping(value = "attentionCallOrPut", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByAttentionIndCallOrPut() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				// DatabaseHelper.getInstance().findAllByAttentionIndAndDateStamp("OR"));
				DatabaseHelper.getInstance().findAllByAttentionIndAndDateStampPartial());
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject("optionChainDataList", list);
		return mv;
	}

	@RequestMapping(value = "tempRefreshData", method = RequestMethod.GET)
	public @ResponseBody ModelAndView applyFreshRulesAndRestoreDate() {
		ModelAndView view = sathvikAshvikTechTraderTool.prepareHeaderSwitchView("home2");

		List<OptionChainDataBean> list = null;
		OptionChainDataBean basebean = null;
		int processed = 0;
		try {
			// nSEIndiaTool.applyFreshRulesAndRestoreData();
			nSEIndiaTool.getstockDataFromTradewareDataBaseInsteadOfConnectToNSEIndiaAndGetStockData();

			for (String symbol : nSEIndiaTool.getOptionChainTempDataMap().keySet()) {
				basebean = nSEIndiaTool.getOptionChainTempDataMap().get(symbol);
				list = DatabaseHelper.getInstance()
						.findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(basebean.getSymbol());
				if (null != list) {

					// for (OptionChainDataBean beanOI : list) {
					for (int i = list.size() - 1; i >= 0; i--) {
						OptionChainDataBean beanOI = list.get(i);
						beanOI.getOITrend();
						beanOI.handleSortOrderAndStyle();
						if (i <= list.size() - 2) {
							basebean = list.get(i + 1);
							beanOI.setPreviousTop1StrikePriceCall(basebean.getTop1StrikePriceCall());
							beanOI.setPreviousTop2StrikePriceCall(basebean.getTop2StrikePriceCall());
							beanOI.setPreviousTop3StrikePriceCall(basebean.getTop3StrikePriceCall());
							beanOI.setPreviousTop1StrikePricePut(basebean.getTop1StrikePricePut());
							beanOI.setPreviousTop2StrikePricePut(basebean.getTop2StrikePricePut());
							beanOI.setPreviousTop3StrikePricePut(basebean.getTop3StrikePricePut());

							if (i <= list.size() - 5) {
								beanOI.setTotalOpenInterestChangeCallPrevious5(
										list.get(i + 5).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious5(
										list.get(i + 5).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious4(
										list.get(i + 4).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious4(
										list.get(i + 4).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious3(
										list.get(i + 3).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious3(
										list.get(i + 3).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious2(
										list.get(i + 2).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious2(
										list.get(i + 2).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious1(
										basebean.getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious1(basebean.getTotalOpenInterestChangePut());

								beanOI.setTotalOINetChangeCallPrevious5(list.get(i + 5).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious5(list.get(i + 5).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious4(list.get(i + 4).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious4(list.get(i + 4).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious3(list.get(i + 3).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious3(list.get(i + 3).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious2(list.get(i + 2).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious2(list.get(i + 2).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious1(basebean.getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious1(basebean.getTotalOINetChangePut());

								beanOI.setUnderlyingPricePrevious5(list.get(i + 5).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious4(list.get(i + 4).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious3(list.get(i + 3).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious2(list.get(i + 2).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious1(basebean.getUnderlyingPrice());
							} else if (i <= list.size() - 4) {
								beanOI.setTotalOpenInterestChangeCallPrevious4(
										list.get(i + 4).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious4(
										list.get(i + 4).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious3(
										list.get(i + 3).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious3(
										list.get(i + 3).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious2(
										list.get(i + 2).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious2(
										list.get(i + 2).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious1(
										basebean.getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious1(basebean.getTotalOpenInterestChangePut());

								beanOI.setTotalOINetChangeCallPrevious4(list.get(i + 4).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious4(list.get(i + 4).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious3(list.get(i + 3).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious3(list.get(i + 3).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious2(list.get(i + 2).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious2(list.get(i + 2).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious1(basebean.getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious1(basebean.getTotalOINetChangePut());

								beanOI.setUnderlyingPricePrevious4(list.get(i + 4).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious3(list.get(i + 3).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious2(list.get(i + 2).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious1(basebean.getUnderlyingPrice());

							} else if (i <= list.size() - 4) {
								beanOI.setTotalOpenInterestChangeCallPrevious3(
										list.get(i + 3).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious3(
										list.get(i + 3).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious2(
										list.get(i + 2).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious2(
										list.get(i + 2).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious1(
										basebean.getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious1(basebean.getTotalOpenInterestChangePut());

								beanOI.setTotalOINetChangeCallPrevious3(list.get(i + 3).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious3(list.get(i + 3).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious2(list.get(i + 2).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious2(list.get(i + 2).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious1(basebean.getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious1(basebean.getTotalOINetChangePut());

								beanOI.setUnderlyingPricePrevious3(list.get(i + 3).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious2(list.get(i + 2).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious1(basebean.getUnderlyingPrice());
							} else if (i <= list.size() - 3) {
								beanOI.setTotalOpenInterestChangeCallPrevious2(
										list.get(i + 2).getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious2(
										list.get(i + 2).getTotalOpenInterestChangePut());
								beanOI.setTotalOpenInterestChangeCallPrevious1(
										basebean.getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious1(basebean.getTotalOpenInterestChangePut());
								beanOI.setTotalOINetChangeCallPrevious2(list.get(i + 2).getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious2(list.get(i + 2).getTotalOINetChangePut());
								beanOI.setTotalOINetChangeCallPrevious1(basebean.getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious1(basebean.getTotalOINetChangePut());
								beanOI.setUnderlyingPricePrevious2(list.get(i + 2).getUnderlyingPrice());
								beanOI.setUnderlyingPricePrevious1(basebean.getUnderlyingPrice());
							} else if (i <= list.size() - 2) {
								beanOI.setTotalOpenInterestChangeCallPrevious1(
										basebean.getTotalOpenInterestChangeCall());
								beanOI.setTotalOpenInterestChangePutPrevious1(basebean.getTotalOpenInterestChangePut());
								beanOI.setTotalOINetChangeCallPrevious1(basebean.getTotalOINetChangeCall());
								beanOI.setTotalOINetChangePutPrevious1(basebean.getTotalOINetChangePut());
								beanOI.setUnderlyingPricePrevious1(basebean.getUnderlyingPrice());
							}
						}
						beanOI = nSEIndiaTool.updatePreviuousOpenInterestStyles(beanOI);
						DatabaseHelper.getInstance().saveOptionChainData(beanOI);
					}
					processed++;
				}
			}
			String msg = "BASE MAP SIZE - " + nSEIndiaTool.getOptionChainTempDataMap().size() + ";  " + "PROCESSED-"
					+ processed + ";	-	" + basebean.getSymbol() + " - ";// +list.size();
			view.addObject("responseMessage", msg + "; Update successful...");
		} catch (Exception e) {
			view.addObject("responseMessage",
					"PROCESSED-" + processed + ";	-	" + "ERROR - " + basebean.getSymbol() + " \n" + e.getMessage());
			e.printStackTrace();
		}
		return view;
	}
}
