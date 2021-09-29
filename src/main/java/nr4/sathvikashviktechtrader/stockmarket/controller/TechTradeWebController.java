package nr4.sathvikashviktechtrader.stockmarket.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.stockmarket.bean.AppInfoDataBean;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;

import nr4.sathvikashviktechtrader.stockmarket.bean.StockDataBean;
import nr4.sathvikashviktechtrader.stockmarket.helper.DatabaseHelper;
import nr4.sathvikashviktechtrader.stockmarket.log.NkpAlgoLogger;
import nr4.sathvikashviktechtrader.stockmarket.scanner.NR7andUTScannerThread;
import nr4.sathvikashviktechtrader.stockmarket.tool.NR7AndUTCommonTool;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;
import nr4.sathvikashviktechtrader.stockmarket.util.StockUtil;

@Controller
public class TechTradeWebController {
	/******@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView initializeHomePage(Model model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("home");
		
		if (!StockUtil.kiteConnectAdmins.isEmpty() && !StockUtil.kiteConnectAdminsHist.isEmpty()) {
			mv = getHome2ModelAndView();
		}
		iniateHomePath(req);
		return mv;
	}

	private void iniateHomePath(HttpServletRequest req) {
		if (null != req) {
			try {
				String hostName = InetAddress.getLocalHost().getHostAddress();
				if (!hostName.contains(".cfapps.io")) {
					hostName = "http://" + hostName + ":" + req.getServerPort() + "/";
				} else {
					hostName = "https://" + hostName + "/";
				}
				if (null == StockUtil.serverHostName) {
					StockUtil.serverHostName = hostName;
					NkpAlgoLogger.printWithNewLine(StockUtil.serverHostName + " @ "
							+ NR7AndUTCommonTool.getInstance().getCurrentDateTime());
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
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

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> login(@RequestParam(value = "userId") String userId) {
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			return validate;
		}
		KiteConnect kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userId));
		kiteConnect.setUserId(userId);
		String kiteConnectURL = kiteConnect.getLoginURL();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create(kiteConnectURL));
		return new ResponseEntity<>(header, HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping(value = "/kiteRedirect", method = RequestMethod.GET)
	// public ResponseEntity<String> kiteRedirect(@RequestParam(value =
	// "request_token") String request_token,
	public ModelAndView kiteRedirect(@RequestParam(value = "request_token") String request_token,
			@RequestParam(value = "userId") String userId) {
		String reqestToken = request_token;
		ModelAndView responseView = null;// new ModelAndView();
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			responseView = new ModelAndView("home");// , "userValidation", validate);
			return responseView;
		}

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userId));
			kiteConnect.setUserId(userId);

			NkpAlgoLogger.printWithNewLine(">>kiteConnect created for userId = " + userId);
			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					NkpAlgoLogger.printWithNewLine("session expired");
				}
			});
			User kiteConnectUser = kiteConnect.generateSession(reqestToken,
					StockUtil.getAdmins().get(StockUtil.getAdmins().get(userId)));// "your_apiSecret");
			kiteConnect.setAccessToken(kiteConnectUser.accessToken);
			kiteConnect.setPublicToken(kiteConnectUser.publicToken);
			StockUtil.kiteConnectAdmins.put(userId, kiteConnect);
		} catch (KiteException e) {
			NkpAlgoLogger.printWithNewLine("kiteRedirect - " + e.message + " " + e.code + " " + e.getClass().getName());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return initializeHomePage(null, null);
	}

	@RequestMapping(value = "/createSession", method = RequestMethod.GET)
	public ModelAndView createSession(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "accessToken") String accessToken) {

		ModelAndView responseView = null;
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			// responseView = new ModelAndView("home" , "userValidation", validate);
			responseView = new ModelAndView("home");
			return responseView;
		}

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userId));
			kiteConnect.setUserId(userId);
			kiteConnect.setAccessToken(accessToken);

			NkpAlgoLogger.printWithNewLine(">>kiteConnect created for userId = " + userId);
			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					NkpAlgoLogger.printWithNewLine("session expired");
				}
			});
			StockUtil.kiteConnectAdmins.put(userId, kiteConnect);
			//System.out.println(kiteConnect.getAccessToken()+"  ->>kiteConnect created for userId = " + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return initializeHomePage(null, null);
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
	@RequestMapping(value = "/loginHist", method = RequestMethod.GET)
	public ResponseEntity<String> loginHistoricalDataApi(@RequestParam(value = "userId") String userId) {
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			return validate;
		}
		KiteConnect kiteConnect = new KiteConnect(StockUtil.getAdminsHist().get(userId));
		kiteConnect.setUserId(userId);
		String kiteConnectURL = kiteConnect.getLoginURL();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create(kiteConnectURL));
		return new ResponseEntity<>(header, HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping(value = "/kiteRedirectHist", method = RequestMethod.GET)
	// public ResponseEntity<String> kiteRedirect(@RequestParam(value =
	// "request_token") String request_token,
	public ModelAndView kiteRedirectHistoricalDataApi(@RequestParam(value = "request_token") String request_token,
			@RequestParam(value = "userId") String userId) {
		String reqestToken = request_token;
		ModelAndView responseView = null;// new ModelAndView();
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			responseView = new ModelAndView("home");// , "userValidation", validate);
			return responseView;
		}

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdminsHist().get(userId));
			kiteConnect.setUserId(userId);

			NkpAlgoLogger.printWithNewLine(">>kiteConnect created for userId = " + userId);
			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					NkpAlgoLogger.printWithNewLine("session expired");
				}
			});
			User kiteConnectUser = kiteConnect.generateSession(reqestToken,
					StockUtil.getAdminsHist().get(StockUtil.getAdminsHist().get(userId)));// "your_apiSecret");
			kiteConnect.setAccessToken(kiteConnectUser.accessToken);
			kiteConnect.setPublicToken(kiteConnectUser.publicToken);
			StockUtil.kiteConnectAdminsHist.put(userId, kiteConnect);
		} catch (KiteException e) {
			NkpAlgoLogger.printWithNewLine("kiteRedirect - " + e.message + " " + e.code + " " + e.getClass().getName());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdminsHist().get(userId));
			kiteConnect.setUserId(userId);
			kiteConnect.setAccessToken(accessToken);

			NkpAlgoLogger.printWithNewLine(">>kiteConnect created for userId = " + userId);
			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					NkpAlgoLogger.printWithNewLine("session expired");
				}
			});

			StockUtil.kiteConnectAdminsHist.put(userId, kiteConnect);
			//System.out.println(kiteConnect.getAccessToken()+"  ->>kiteConnect created for userId = " + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	// force starters
	@RequestMapping(value = "forceStartApp", method = RequestMethod.GET)
	public ModelAndView setupNkpAlgoTraderTradeDataForcely() {
		String response = Constants.FORCE_START_APP_REQUEST;

		NR7AndUTCommonTool nseIndiaTool = NR7AndUTCommonTool.getInstance();

		if (!nseIndiaTool.isTradingTime()) {
			response = Constants.FORCE_START_APP_TIME_VALID + nseIndiaTool.getCurrentDateTime();
		} else if (NkpAppInfoBean.STATUS_APP_UP_AND_RUNNING == (StockUtil.getAppBean().getAppStatus())) {
			response = Constants.FORCE_START_APP_UP_RUNNING + nseIndiaTool.getCurrentDateTime();
		} else if (NkpAppInfoBean.STATUS_APP_START_IN_PROGRESS == (StockUtil.getAppBean().getAppStatus())) {
			response = Constants.FORCE_START_APP_IN_PROGRESS + nseIndiaTool.getCurrentDateTime();
		} else if (!StockUtil.kiteConnectAdmins.keySet().contains(StockUtil.DEFAULT_USER)) {
			response = Constants.FORCE_START_APP_ADMIN_LOGIN_VALID + nseIndiaTool.getCurrentDateTime();
		} else {
			// set application status is in progress
			StockUtil.getAppBean().setAppStatus(NkpAppInfoBean.STATUS_APP_START_IN_PROGRESS);
			NR7AndUTCommonTool.getInstance().updateKiteFutureKeyAndInstrumentToken();
			NR7AndUTCommonTool.getInstance().get15MinutueCandleData();
			NR7andUTScannerThread scanner = new NR7andUTScannerThread();
			scanner.start();

			StockUtil.getAppBean().setAppStatus(NkpAppInfoBean.STATUS_APP_UP_AND_RUNNING);
			NkpAlgoLogger.printWithNewLine(Constants.FORCE_START_APP_SUCCESSFUL);
			response = Constants.FORCE_START_APP_SUCCESSFUL;
		}

		ModelAndView view = getHome2ModelAndView();
		view.addObject("responseMessage", response);
		return view;
	} *****/

	private ModelAndView getHome2ModelAndView() {
		return NR7AndUTCommonTool.getInstance().prepareHeaderSwitchView("home2");
	}

	/*
	 * @RequestMapping(value = "report", method = RequestMethod.GET)
	 * public @ResponseBody ModelAndView getrunnngReport() { List<StockDataBean>
	 * list = new ArrayList<StockDataBean>(
	 * NR7AndUTCommonTool.getInstance().getTradingDataMap().values());
	 * return NR7AndUTCommonTool.getInstance().prepareHeaderString(list,
	 * "report"); }
	 * 
	 * @RequestMapping(value = "reportFull", method = RequestMethod.GET)
	 * public @ResponseBody ModelAndView getFullDayReport() { List<StockDataBean>
	 * list = new ArrayList<StockDataBean>(
	 * NR7AndUTCommonTool.getInstance().getReportDataMap().values());
	 * return NR7AndUTCommonTool.getInstance().prepareHeaderString(list,
	 * "reportFull"); }
	 */

	//@RequestMapping(value = "report", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getRunningReport() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().getRunningTrades());
		return NR7AndUTCommonTool.getInstance().prepareHeaderString(list, "report");
	}

	//@RequestMapping(value = "reportFull", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReport() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampOrderBySymbolAscTradedAtDtAsc());
		return NR7AndUTCommonTool.getInstance().prepareHeaderString(list, "reportFull");
	}
	//It removes Buyer-Seller rule failed and Small Candle rows
	//@RequestMapping(value = "reportFullFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportFilter() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampFilterItemsOrderByTradedAtDtDesc());
		return NR7AndUTCommonTool.getInstance().prepareHeaderString(list, "reportFull");
	}

	@RequestMapping(value = "/tradedReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getTradedByCustomRuleReport() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDt(true));
		return NR7AndUTCommonTool.getInstance().prepareHeaderString(list, "reportFull");
	}

	@RequestMapping(value = "/tradedReport1", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc());
		return NR7AndUTCommonTool.getInstance().prepareHeaderString(list, "reportFull");
	}

	@RequestMapping(value = "/tradedReport2", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance()
				.findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc());
		return NR7AndUTCommonTool.getInstance().prepareHeaderString(list, "reportFull");
	}


	// Maintain setting lookups
	//TODO: @RequestMapping(value = "/tradingSwitch", method = RequestMethod.GET)
	public ModelAndView updateTradingSwitch() {
		NR7AndUTCommonTool.todayTradingGoInd = !(NR7AndUTCommonTool.todayTradingGoInd);
		return NR7AndUTCommonTool.getInstance().prepareHeaderSwitchView("home2");
	}

	@RequestMapping(value = "applyReportDate", method = RequestMethod.GET)
	public @ResponseBody ModelAndView applyReportDate(@RequestParam(value = "reportDate") String reportDate) {
		if (null != reportDate && !Constants.EMPTY_STRING.equals(reportDate)) {
			LocalDate dateInstance = LocalDate.parse(reportDate);
			Date tradeDay = Date.from(dateInstance.atStartOfDay().atZone(ZoneId.of(Constants.TIME_ZONE)).toInstant());
			NR7AndUTCommonTool.getInstance().setTradeDateForReport(tradeDay);
		}
		return NR7AndUTCommonTool.getInstance().prepareHeaderSwitchView("home2");
	}
	
	@ModelAttribute("symbolMap")
	public Map<String,String> symbolsOptionMap() {
		Map<String,String> symbolMap = new TreeMap<String,String>();
		for (String symbol : StockUtil.getAllSymbols().keySet()) {
			symbolMap.put(symbol, symbol);
		}
	return symbolMap;	
	}

	//@RequestMapping(value = "/reportSystemError", method = RequestMethod.GET)
	public ModelAndView reportSystemError() {
		ModelAndView view = NR7AndUTCommonTool.getInstance().prepareHeaderSwitchView("reportSystemError");
		view.addObject("systemErrorList", DatabaseHelper.getInstance().findAllByDateStampOrderByDateTimeStampDesc());
		return view;
	}
}
