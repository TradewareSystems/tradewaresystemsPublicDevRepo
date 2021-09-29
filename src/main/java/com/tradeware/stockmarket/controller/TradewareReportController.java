package com.tradeware.stockmarket.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.clouddatabase.engine.NSEIndiaCloudDataBaseToolNewNseSite;
import com.tradeware.clouddatabase.engine.NSEIndiaCloudDataBaseToolNewNseSiteAdditional;
import com.tradeware.stockmarket.bean.AppInfoDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.scanner.TradewareScanner;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData15MinuteThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData60MinuteThread;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.StockUtil;

@Controller
public class TradewareReportController {

	@Autowired
	private TradewareTool tradewareTool;

	@Autowired
	private NSEIndiaCloudDataBaseToolNewNseSite nSEIndiaCloudDatabaseTool;
	
	@Autowired
	private NSEIndiaCloudDataBaseToolNewNseSiteAdditional nSEIndiaCloudDatabaseToolAdditinal;

	// connect to nseindia.in and retrieve stocks data and insert into t_symbol
	// table
	@RequestMapping(value = "/prepareTradewareDatabase", method = RequestMethod.GET)
	public ModelAndView getprepareAlgoCloudDataAndGetSymbolList() {
		nSEIndiaCloudDatabaseTool.getPrepareAlgoCloudDataAndGetSymbolList();
		//nSEIndiaCloudDatabaseToolAdditinal.getPrepareAlgoCloudDataAndGetSymbolList();
		
		return tradewareTool.prepareHeaderSwitchView("home2");
	}

	@RequestMapping(value = "getSymbols", method = RequestMethod.GET)
	public ModelAndView getSymbols() {
		ModelAndView view = tradewareTool.prepareHeaderSwitchView("symbols");
		List<SymbolDataBean> list = DatabaseHelper.getInstance().findAllSymbols();
		view.addObject("symbolList", list);
		if (null != list && !list.isEmpty()) {
			String lastUpdated = tradewareTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp());
			view.addObject("lastUpated", lastUpdated);
		}
		return view;
	}

	@RequestMapping(value = "getSymbolsByTrend", method = RequestMethod.GET)
	public ModelAndView findAllOrderByYrHiLoNearPerDesc() {
		ModelAndView view = tradewareTool.prepareHeaderSwitchView("symbols");
		List<SymbolDataBean> list = DatabaseHelper.getInstance().findAllOrderByYrHiLoNearPerDesc();
		view.addObject("symbolList", list);
		if (null != list && !list.isEmpty()) {
			String lastUpdated = tradewareTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp());
			view.addObject("lastUpated", lastUpdated);
		}
		return view;
	}
	
	@RequestMapping(value = "getSymbolsBySmaTrend", method = RequestMethod.GET)
	public ModelAndView findAllOrderByClose200SmaTradableRatio() {
		ModelAndView view = tradewareTool.prepareHeaderSwitchView("symbols");
		List<SymbolDataBean> list = DatabaseHelper.getInstance().findAllOrderByClose200SmaTradableRatio();
		view.addObject("symbolList", list);
		if (null != list && !list.isEmpty()) {
			String lastUpdated = tradewareTool.getGivenDateTimeAsAMPM(list.get(0).getDateTimeStamp());
			view.addObject("lastUpated", lastUpdated);
		}
		return view;
	}

	@RequestMapping(value = "reportFull", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReport() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc());
		return tradewareTool.prepareHeaderString(filterOutDayLevelTrades(list), "reportFull");
	}

	private List<StrategyOrbDataBean> filterOutDayLevelTrades(List<StrategyOrbDataBean> list) {
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (null != bean.getDayLevelTradeInd() && bean.getDayLevelTradeInd()) {
				it.remove();
			}
		}
		return list;
	}

	// It removes Buyer-Seller rule failed and Small Candle rows
	@RequestMapping(value = "reportFullFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportFilter() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc());
		return tradewareTool.prepareHeaderString(filterOutDayLevelTrades(list), "reportFull");
	}

	// force start
	@Autowired
	private TradewareScanner scanner;

	@Autowired
	private TradingDataMapHistData15MinuteThread histData15MinuteRunnable;
	private Thread histData15MinuteThread;

	@Autowired
	private TradingDataMapHistData60MinuteThread histData60MinuteRunnable;
	private Thread histData60MinuteThread;

	@RequestMapping(value = "forceStartDayTrading", method = RequestMethod.GET)
	public ModelAndView setupNkpAlgoTraderTradeDataForcely() {
		String response = Constants.FORCE_START_APP_REQUEST;
		if (!tradewareTool.isTradingTime()) {
			response = Constants.FORCE_START_APP_TIME_VALID + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode();
		} else if (AppInfoDataBean.STATUS_APP_UP_AND_RUNNING == (StockUtil.getAppBean().getAppStatus())) {
			response = Constants.FORCE_START_APP_UP_RUNNING + tradewareTool.getCurrentTime();
		} else if (AppInfoDataBean.STATUS_APP_START_IN_PROGRESS == (StockUtil.getAppBean().getAppStatus())) {
			response = Constants.FORCE_START_APP_IN_PROGRESS + tradewareTool.getCurrentTime();
		} else if (!StockUtil.kiteConnectAdmins.keySet().contains(StockUtil.DEFAULT_USER)) {
			response = Constants.FORCE_START_APP_ADMIN_LOGIN_VALID + tradewareTool.getCurrentTime();
		} else {
			try {
				// set application status is in progress
				StockUtil.getAppBean().setAppStatus(AppInfoDataBean.STATUS_APP_START_IN_PROGRESS);

				// Step1 : @Scheduled(cron = "01 55 8 * * MON-FRI", zone = "IST")
				if (tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
					tradewareTool.updateKiteFutureKeyAndInstrumentToken();
				}
				TradingDataMapHistData60MinuteThread.fromDate = tradewareTool.getFromDateForKiteHistData_9_00();
				TradingDataMapHistData60MinuteThread.toDate = tradewareTool.getToDateForKiteHistData_15_30();
				histData60MinuteThread = new Thread(histData60MinuteRunnable, tradewareTool.getCurrentTime());
				histData60MinuteThread.start();
				System.out.println(Constants.NSE_TOOL_PROCESS_SUCCESSFUL);

				// Step2 : @Scheduled(cron = "01 42 9 * * MON-FRI", zone = "IST")
				if (tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
					tradewareTool.updateKiteFutureKeyAndInstrumentToken();
				}
				tradewareTool.validateOHLCRule();

				// Step3 : @Scheduled(cron = "02 45 9 * * MON-FRI", zone = "IST")

				long inTime = System.currentTimeMillis();

				histData15MinuteThread = new Thread(histData15MinuteRunnable, tradewareTool.getCurrentTime());
				histData15MinuteThread.start();

				scanner.start();
				long outTime = System.currentTimeMillis();
				TradewareLogger.saveInfoLog(Constants.CLASS_SCHEDULER_CONFIG,
						Constants.METHOD_START_AND_RUN_BUY_SELL_SCANNER,
						Constants.CALL_ENTRY_EXIT_DURATION + ((outTime - inTime) / 1000d));

				StockUtil.getAppBean().setAppStatus(AppInfoDataBean.STATUS_APP_UP_AND_RUNNING);
				System.out.println(Constants.FORCE_START_APP_SUCCESSFUL);
				response = Constants.FORCE_START_APP_SUCCESSFUL;
			} catch (Exception e) {
				// if any failure reset application status
				StockUtil.getAppBean().setAppStatus(AppInfoDataBean.STATUS_APP_DOWN);
				response = Constants.FORCE_START_APP_UNSUCCESS + tradewareTool.getCurrentTime();
			}
		}
		ModelAndView view = tradewareTool.prepareHeaderSwitchView("home2");
		view.addObject("responseMessage", response);
		return view;
	}
	
	
	// 03-20-2021 - start
	@RequestMapping(value = "report", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getRunningReport() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(DatabaseHelper.getInstance().getRunningTrades());
		return tradewareTool.prepareHeaderString(filterOutDayLevelTrades(list), "report");
	}
	
	
	
	
	
	
	
	
	
	//Day level start
	@RequestMapping(value = "/reportDayLevelRunning", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getRunningTradesDayLevelTrade() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(DatabaseHelper.getInstance().getRunningTradesDayLevelTrade());
		return tradewareTool.prepareHeaderString(list, "report");
	}
	@RequestMapping(value = "/reportDayLevel", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayLevlReport() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampAndDayLevelTradeIndOrderBySymbolNameAscTradedAtDtTmDesc(true));
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	@RequestMapping(value = "/reportDayLevelFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayLevlReportFilter() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateFilterAndDayLevelTradeindItemsOrderByTradedAtDtTmDesc(true));
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/reportDayLevelFilterOhlc", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayLevlReportFilterOhlc() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateAndOhlcStateNotNullAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc());
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	@RequestMapping(value = "/reportDayLevelFilterOhlcEqualTradable", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayLevlReportFilterOhlcEqualTradableState() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateAndOhlcStateNotNullAndOhlcStateEqualToTradableStateAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc());
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	//Day level end
	// 03-20-2021 - end
}
