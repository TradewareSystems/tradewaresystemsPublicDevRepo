package com.tradeware.stockmarket.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeware.stockmarket.bean.AppInfoDataBean;
import com.tradeware.stockmarket.bean.UserDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.scanner.TradewareScanner;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData15MinuteStochasticThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData15MinuteThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData1HourStochasticThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData1MinuteThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData5MinuteStochasticThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData5MinuteThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData60MinuteThread;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.Constants2;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.StockUtil;

@Controller
public class TradewareWebController {

	@Autowired 
	private KiteConnectTool kiteConnectTool;	
	
	@Autowired
	private TradewareTool tradewareTool;
	
	@Autowired
	private ObjectMapper mapper;
	
	private ModelAndView getHome2ModelAndView() {
		// return tradewareTool.prepareHeaderSwitchView("home2");

		ModelAndView mv = tradewareTool.prepareHeaderSwitchView("home2");
		return mv;
	}

	private void initateHomePath(HttpServletRequest req) {
		if (null != req) {
			try {
				String hostName = InetAddress.getLocalHost().getHostAddress();
				if (!hostName.contains(".cfapps.io") && !hostName.contains(".cfapps.io")) {
					hostName = "http://" + hostName + ":" + req.getServerPort() + "/";
				} else {
					hostName = "https://" + hostName + "/";
				}
				if (null == StockUtil.serverHostName) {
					StockUtil.serverHostName = hostName;
				}
			} catch (UnknownHostException e) {
				TradewareLogger.saveErrorLog(Constants2.CLASS_TRADEWARE_WEB_CONTROLLER, Constants2.METHOD_INITATE_HOME_PATH,
						e, Constants2.ERROR_TYPE_UNKNOWN_HOST_EXCEPTION, Constants2.ERROR_SEVERIRITY_FATAL);
			}
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView initializeHomePage(Model model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("home");

		if (!StockUtil.kiteConnectAdmins.isEmpty() && !StockUtil.kiteConnectAdminsHist.isEmpty()) {
			mv = getHome2ModelAndView();
		}
		initateHomePath(req);
		return mv;
	}

	// For secure login
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView appLogin() {
		return initializeHomePage(null, null);
	}

	@RequestMapping(value = "/home2", method = RequestMethod.GET)
	public ModelAndView initializeHomePageRedirect() {
		return initializeHomePage(null, null);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private ResponseEntity<String> validateUserLogin(String userId) {
		ResponseEntity<String> response = null;
		if (!StockUtil.getAdmins().containsKey(userId)) {
			response = new ResponseEntity<>(Constants.INVALID_USER, HttpStatus.BAD_REQUEST);
		} else if (StockUtil.kiteConnectAdmins.contains(userId) && StockUtil.kiteConnectAdmins.contains(userId)) {
			response = new ResponseEntity<>(userId + Constants.ALREADY_LOGGED_IN, HttpStatus.OK);
		}
		return response;
	}

	@RequestMapping(value = "/kiteLogin", method = RequestMethod.GET)
	public ResponseEntity<String> login(@RequestParam(value = "userId") String userId) {
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			return validate;
		}
		UserDataBean bean = DatabaseHelper.getInstance().getUserDataBean(userId);
		return new ResponseEntity<>(kiteConnectTool.kiteConnectLogin(bean), HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping(value = "/kiteRedirect", method = RequestMethod.GET)
	// public ResponseEntity<String> kiteRedirect(@RequestParam(value =
	// "request_token") String request_token,
	public /*ModelAndView*/ String kiteRedirect(@RequestParam(value = "request_token") String request_token,
			@RequestParam(value = "userId") String userId) {
		ModelAndView responseView = null;// new ModelAndView();
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			responseView = new ModelAndView("home");// , "userValidation", validate);
			//return responseView;
			return "redirect:/home";
		}
		UserDataBean bean = DatabaseHelper.getInstance().getUserDataBean(userId);
		bean.setRequestToken(request_token);
		bean = kiteConnectTool.kiteConnectRedirectAfterLogin(bean);
		//return initializeHomePage(null, null);
		kiteConnectTool.validKiteAccess();
		return "redirect:/home2";
	}

	@RequestMapping(value = "/createSession", method = RequestMethod.GET)
	public /*ModelAndView*/ String createSession(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "accessToken") String accessToken) {

		ModelAndView responseView = null;
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			// responseView = new ModelAndView("home" , "userValidation", validate);
			responseView = new ModelAndView("home");
			//return responseView;
			return "redirect:/home";
		}

		try {
			UserDataBean bean = new UserDataBean();
			bean.setUserId(userId);
			bean.setAccessToken(accessToken);
			kiteConnectTool.kiteConnectCreateSession(bean);
			kiteConnectTool.validKiteAccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return initializeHomePage(null, null);
		return "redirect:/home2";
	}

	@RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
	// public ModelAndView getUserAccessToken(@RequestParam(value = "userId") String
	// userId) {
	public ModelAndView getUserAccessToken() {
		String userId = StockUtil.DEFAULT_USER;
		ModelAndView mv = getHome2ModelAndView();
		if (StockUtil.kiteConnectAdmins.keySet().contains(userId)) {
			mv.addObject("accessToken", StockUtil.kiteConnectAdmins.get(userId).getAccessToken());
		}
		if (StockUtil.kiteConnectAdminsHist.keySet().contains(userId)) {
			mv.addObject("accessTokenHist", StockUtil.kiteConnectAdminsHist.get(userId).getAccessToken());
		}
		return mv;
	}

	// Historical data start
	@RequestMapping(value = "/kiteLoginHist", method = RequestMethod.GET)
	public ResponseEntity<String> loginHistoricalDataApi(@RequestParam(value = "userId") String userId) {
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			return validate;
		}
		UserDataBean bean = DatabaseHelper.getInstance().getUserDataBean(userId);
		return new ResponseEntity<>(kiteConnectTool.kiteConnectLoginHist(bean), HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping(value = "/kiteRedirectHist", method = RequestMethod.GET)
	// public ResponseEntity<String> kiteRedirect(@RequestParam(value =
	// "request_token") String request_token,
	public ModelAndView kiteRedirectHistoricalDataApi(@RequestParam(value = "request_token") String request_token,
			@RequestParam(value = "userId") String userId) {
		ModelAndView responseView = null;// new ModelAndView();
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			responseView = new ModelAndView("home");// , "userValidation", validate);
			return responseView;
		}

		UserDataBean bean = DatabaseHelper.getInstance().getUserDataBean(userId);
		bean.setRequestToken(request_token);
		bean = kiteConnectTool.kiteConnectRedirectHistAfterLogin(bean);
		return initializeHomePage(null, null);
	}

	@RequestMapping(value = "/createSessionHist", method = RequestMethod.GET)
	public ModelAndView createSessionHistoricalDataApi(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "accessToken") String accessToken) {

		ModelAndView responseView = null;
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			// responseView = new ModelAndView("home" , "userValidation", validate);
			responseView = new ModelAndView("home");
			return responseView;
		}

		UserDataBean bean = DatabaseHelper.getInstance().getUserDataBean(userId);
		bean.setAccessToken(accessToken);
		bean = kiteConnectTool.kiteConnectCreateSessionHist(bean);
		return initializeHomePage(null, null);
	}

	@RequestMapping(value = "/getAccessTokenHist", method = RequestMethod.GET)
	// public ModelAndView getUserAccessToken(@RequestParam(value = "userId") String
	// userId) {
	public ModelAndView getUserAccessTokenHistoricalDataApi() {
		String userId = StockUtil.DEFAULT_USER;
		ModelAndView mv = getHome2ModelAndView();
		if (StockUtil.kiteConnectAdmins.keySet().contains(userId)) {
			mv.addObject("accessToken", StockUtil.kiteConnectAdmins.get(userId).getAccessToken());
		}
		if (StockUtil.kiteConnectAdminsHist.keySet().contains(userId)) {
			mv.addObject("accessTokenHist", StockUtil.kiteConnectAdminsHist.get(userId).getAccessToken());
		}
		return mv;
	}
	// Historical data end
	
	
	
	//Phase 3 start -03-18-2021 start
	@RequestMapping(value = "applyReportDate", method = RequestMethod.GET)
	public @ResponseBody ModelAndView applyReportDate(@RequestParam(value = "reportDate") String reportDate) {
		if (null != reportDate && !Constants.EMPTY_STRING.equals(reportDate)) {
			tradewareTool.applyReportDate(reportDate);
		}
		return tradewareTool.prepareHeaderSwitchView("home2");
	}
	@RequestMapping(value = "queryCandleNumber", method = RequestMethod.GET)
	public @ResponseBody ModelAndView applyQueryCandleNumber(@RequestParam(value = "queryCandleNumber") String queryCandleNumber) {
		if (null != queryCandleNumber && !Constants.EMPTY_STRING.equals(queryCandleNumber)) {
			try {
				tradewareTool.setQueryCandleNumber(Integer.parseInt(queryCandleNumber));
			} catch (NumberFormatException e) {
				tradewareTool.setQueryCandleNumber(25);
			}
		}
		//return tradewareTool.prepareHeaderSwitchView("home2");
		return tradewareTool.prepareHeaderSwitchView("home2");
	}
	//Phase 3 start -03-18-2021 end
	
	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	@RequestMapping(value = "/manualTradeClose", method = RequestMethod.GET)
	public ResponseEntity<String> manualTradeClose(@RequestParam(value = "tradeId") String tradeId) {
	//public ModelAndView manualTradeClose(@RequestParam(value = "tradeId") String tradeId) {
		String responseData = null;
		if (null != tradeId && !Constants.EMPTY_STRING.equals(tradeId)) {
			try {
				responseData = mapper.writeValueAsString(tradewareTool.manualTradeClose(tradeId)).toString();
			} catch (/*JsonProcessing*/Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<String>(responseData, HttpStatus.OK);
		//return getHome2ModelAndView();
	}
	
	// Maintain setting lookups
		@RequestMapping(value = "/tradingSwitch", method = RequestMethod.GET)
		public ModelAndView updateTradingSwitch() {
			TradewareTool.todayTradingGoInd = !(TradewareTool.todayTradingGoInd);
			return tradewareTool.prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/tradingAllSwitch", method = RequestMethod.GET)
		public ModelAndView updateTradingAllSwitch() {
			TradewareTool.todayTradingAllGoInd = !(TradewareTool.todayTradingAllGoInd);
			return tradewareTool.prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/tradingAllOhlcSwitch", method = RequestMethod.GET)
		public ModelAndView updateTradingAllOhlcSwitch() {
			TradewareTool.todayTradingAllOhlcGoInd = !(TradewareTool.todayTradingAllOhlcGoInd);
			return tradewareTool.prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/tradingOhlcTradeStateMatchSwitch", method = RequestMethod.GET)
		public ModelAndView updateTradingAllOhlcAndTradeStateMatchSwitch() {
			TradewareTool.todayTradingOhlcAndTradeStateMatchGoInd = !(TradewareTool.todayTradingOhlcAndTradeStateMatchGoInd);
			return tradewareTool.prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/tradingCustomOHLCMatchSwitch", method = RequestMethod.GET)
		public ModelAndView updateTtradingCustomOHLCMatchIndSwitch() {
			TradewareTool.todayTradingGoCustomOHLCMatchInd = !(TradewareTool.todayTradingGoCustomOHLCMatchInd);
			return tradewareTool.prepareHeaderSwitchView("home2");
		}
	// 04-21-2021 end

		
		
		
		
		@Autowired
		private TradewareScanner scanner;
		@Autowired
		private TradingDataMapHistData60MinuteThread  histData60MinuteRunnable;
		private Thread histData60MinuteThread;
		@Autowired
		private TradingDataMapHistData15MinuteThread histData15MinuteRunnable;
		private Thread histData15MinuteThread;
		@Autowired
		private TradingDataMapHistData15MinuteStochasticThread tradingDataMapHistData15MinuteStochasticThread;
		private Thread stochasticHistData15MinuteThread;
		@Autowired
		private TradingDataMapHistData1HourStochasticThread tradingDataMapHistData1HourStochasticThread;
		private Thread stochasticHistData1HourThread;
		@Autowired
		private TradingDataMapHistData1MinuteThread tradingDataMapHistData1MinuteThread;
		private Thread histData1MinuteThread;
		@Autowired
		private TradingDataMapHistData5MinuteThread tradingDataMapHistData5MinuteThread;
		private Thread histData5MinuteThread;
		@Autowired
		private TradingDataMapHistData5MinuteStochasticThread tradingDataMapHistData5MinuteStochasticThread;
		private Thread stochasticHistData5MinuteThread;
		// force starters
		@RequestMapping(value = "forceStartApp", method = RequestMethod.GET)
		public ModelAndView setupNkpAlgoTraderTradeDataForcelyLevels() {
			String response = Constants.FORCE_START_APP_REQUEST;

			if (!tradewareTool.isTradingTime()) {
				response = Constants.FORCE_START_APP_TIME_VALID + tradewareTool.getCurrentDateTime();
			} else if (AppInfoDataBean.STATUS_APP_UP_AND_RUNNING == (StockUtil.getAppBean().getAppStatus())) {
				response = Constants.FORCE_START_APP_UP_RUNNING + tradewareTool.getCurrentDateTime();
			} else if (AppInfoDataBean.STATUS_APP_START_IN_PROGRESS == (StockUtil.getAppBean().getAppStatus())) {
				response = Constants.FORCE_START_APP_IN_PROGRESS + tradewareTool.getCurrentDateTime();
			} else if (!StockUtil.kiteConnectAdmins.keySet().contains(StockUtil.DEFAULT_USER)) {
				response = Constants.FORCE_START_APP_ADMIN_LOGIN_VALID + tradewareTool.getCurrentDateTime();
			} else {
				// set application status is in progress
				StockUtil.getAppBean().setAppStatus(AppInfoDataBean.STATUS_APP_START_IN_PROGRESS);

				//@Scheduled(cron = "01 40 9 * * MON-FRI", zone = "IST")
				tradewareTool.setThreadScannerStatus(Constants.APP_SERVER_STATUS_START_UP_IN_PROGRESS);
				if (tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
					tradewareTool.updateKiteFutureKeyAndInstrumentToken();
					// create empty AverageHistDataBean bean list
					tradewareTool.prepareAverageHLCDataMap();
				}

				TradingDataMapHistData60MinuteThread.fromDate = tradewareTool.getFromDateForKiteHistDataStochastic1Hour_9_00();
				TradingDataMapHistData60MinuteThread.toDate = tradewareTool.getToDateForKiteHistData_15_30();
				histData60MinuteThread = new Thread(histData60MinuteRunnable, tradewareTool.getCurrentTime());
				histData60MinuteThread.start();

				stochasticHistData1HourThread = new Thread(tradingDataMapHistData1HourStochasticThread,
						tradewareTool.getCurrentTime());
				stochasticHistData1HourThread.start();
				
				stochasticHistData15MinuteThread = new Thread(tradingDataMapHistData15MinuteStochasticThread,
						tradewareTool.getCurrentTime());
				stochasticHistData15MinuteThread.start();
				if (tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
					tradewareTool.updateKiteFutureKeyAndInstrumentToken();
				}
				tradewareTool.validateOHLCRule();
				
				//@Scheduled(cron = "02 45 9 * * MON-FRI", zone = "IST")
				long inTime = System.currentTimeMillis();
				
				histData15MinuteThread = new Thread(histData15MinuteRunnable, tradewareTool.getCurrentTime());
				histData15MinuteThread.start();

				scanner.start();
				tradewareTool.setThreadScannerStatus(Constants.APP_SERVER_STATUS_SCANNERS_RUNNING);
				
				long outTime = System.currentTimeMillis();
				TradewareLogger.saveInfoLog(Constants.CLASS_SCHEDULER_CONFIG,
						Constants.METHOD_START_AND_RUN_BUY_SELL_SCANNER,
						Constants.CALL_ENTRY_EXIT_DURATION + ((outTime - inTime) / 1000d));

				StockUtil.getAppBean().setAppStatus(AppInfoDataBean.STATUS_APP_UP_AND_RUNNING);
				//NkpAlgoLogger.printWithNewLine(Constants.FORCE_START_APP_SUCCESSFUL);
				response = Constants.FORCE_START_APP_SUCCESSFUL;
			}

			ModelAndView view = getHome2ModelAndView();
			view.addObject("responseMessage", response);
			return view;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
