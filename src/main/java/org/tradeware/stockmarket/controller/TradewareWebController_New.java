package org.tradeware.stockmarket.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tradeware.stockmarket.bean.TradewareAppInfoBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaToolNewNseSite;
import org.tradeware.stockmarket.engine.tool.AlgoLevelUpdaterTool;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.scanner.BuyOrSellStocksScannerThread_V2;
import org.tradeware.stockmarket.scanner.BuySellFutureScannerThead;
import org.tradeware.stockmarket.scanner.Narrow7StockScannerThread;
import org.tradeware.stockmarket.util.Constants;
import org.tradeware.stockmarket.util.StockUtil;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;

@Controller
//@EnableAutoConfiguration
public class TradewareWebController_New {

	//TODO:CONFLICT_ORB @RequestMapping(value = "/", method = RequestMethod.GET)
	//public ModelAndView initializeHomePage(Model model) {
	public ModelAndView initializeHomePage(Model model,HttpServletRequest req) {
		String homePage = "home";
		if (!StockUtil.kiteConnectObjectsForAdmins.isEmpty()) {
			homePage = "home2";
		}
		iniateHomePath(req);
		return getModelAndView(homePage);
	}
	
	private void iniateHomePath(HttpServletRequest req) {
		try {
			String hostName = InetAddress.getLocalHost().getHostAddress();
			if (!hostName.contains(".cfapps.io")) {
				hostName = "http://" + hostName + ":" + req.getServerPort() + "/";
			} else {
				hostName = "https://" + hostName + "/";
			}
			if (null == StockUtil.serverHostName) {
				StockUtil.serverHostName = hostName;
				System.out.println(StockUtil.serverHostName);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	private ResponseEntity<String> validateUserLogin(String userId) {
		ResponseEntity<String> response = null;
		if (!StockUtil.getAdmins().containsKey(userId)) {
			response = new ResponseEntity<>(Constants.INVALID_USER, HttpStatus.BAD_REQUEST);
		} else if (StockUtil.kiteConnectObjectsForAdmins.contains(userId)
				&& StockUtil.kiteConnectAdminUsers.contains(userId)) {
			response = new ResponseEntity<>(userId + Constants.ALREADY_LOGGED_IN, HttpStatus.OK);
		}
		return response;
	}

	//TODO:CONFLICT_ORB @RequestMapping(value = "/login", method = RequestMethod.GET)
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
	
	//TODO:CONFLICT_ORB @RequestMapping(value = "/kiteRedirect", method = RequestMethod.GET)
	//public ResponseEntity<String> kiteRedirect(@RequestParam(value = "request_token") String request_token,
	public ModelAndView   kiteRedirect(@RequestParam(value = "request_token") String request_token, 
			@RequestParam(value = "userId") String userId) {
		String reqestToken = request_token;
		ModelAndView responseView = null;//new ModelAndView();
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			responseView =  getModelAndView("home");// , "userValidation", validate);
			return responseView;
		}

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userId));
			kiteConnect.setUserId(userId);

			System.out.println(">>kiteConnect created for userId = " + userId);
			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					System.out.println("session expired");
				}
			});

			/*
			 * The request token can to be obtained after completion of login process. Check
			 * out https://kite.trade/docs/connect/v1/#login-flow for more information. A
			 * request token is valid for only a couple of minutes and can be used only
			 * once. An access token is valid for one whole day. Don't call this method for
			 * every app run. Once an access token is received it should be stored in
			 * preferences or database for further usage.
			 */
			User kiteConnectUser = kiteConnect.generateSession(reqestToken,
					StockUtil.getAdmins().get(StockUtil.getAdmins().get(userId)));// "your_apiSecret");
			kiteConnect.setAccessToken(kiteConnectUser.accessToken);
			kiteConnect.setPublicToken(kiteConnectUser.publicToken);
			StockUtil.kiteConnectObjectsForAdmins.put(userId, kiteConnect);
			StockUtil.kiteConnectAdminUsers.put(userId, kiteConnectUser);
			System.out.println(StockUtil.kiteConnectAdminUsers.size());
			if (StockUtil.DEFAULT_USER.equals(userId)) {
				//getUserAccessToken(StockUtil.DEFAULT_USER);
			}
		} catch (KiteException e) {
			System.out.println("kiteRedirect - " + e.message + " " + e.code + " " + e.getClass().getName());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getModelAndView("home2");
	}
	
	//TODO:CONFLICT_ORB @RequestMapping(value = "/createSession", method = RequestMethod.GET)
	public ModelAndView createSession(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "accessToken") String accessToken) {

		ModelAndView responseView = null;
		ResponseEntity<String> validate = validateUserLogin(userId);
		if (null != validate) {
			//responseView =  new ModelAndView("home" , "userValidation", validate);
			responseView =  getModelAndView("home");
			return responseView;
		}

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userId));
			kiteConnect.setUserId(userId);
			kiteConnect.setAccessToken(accessToken);
			
			System.out.println(">>kiteConnect created for userId = " + userId);
			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					System.out.println("session expired");
				}
			});

			/*
			 * The request token can to be obtained after completion of login process. Check
			 * out https://kite.trade/docs/connect/v1/#login-flow for more information. A
			 * request token is valid for only a couple of minutes and can be used only
			 * once. An access token is valid for one whole day. Don't call this method for
			 * every app run. Once an access token is received it should be stored in
			 * preferences or database for further usage.
			 */
			
			StockUtil.kiteConnectObjectsForAdmins.put(userId, kiteConnect);
			StockUtil.kiteConnectAdminUsers.put(userId, new User());
			System.out.println(StockUtil.kiteConnectAdminUsers.size());			
		//}  catch (JSONException e) {
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return getModelAndView("home2");
	}
	
	// force starters
	//TODO:CONFLICT_ORB @RequestMapping(value = "forceStartApp", method = RequestMethod.GET)
	@RequestMapping(value = "/forceStartAppNkp", method = RequestMethod.GET)
		public ModelAndView setupNkpAlgoTraderTradeDataForcely() {
			String response = Constants.FORCE_START_APP_REQUEST;
			/*if (!TradewareTraderUtil.getInstance().isTradingTime()) {
				response = Constants.FORCE_START_APP_TIME_VALID
						+ TradewareTraderUtil.getInstance().getCurrentTime();
			} else*/ if (TradewareAppInfoBean.STATUS_APP_UP_AND_RUNNING == (StockUtil.getAppBean().getAppStatus())) {
				response = Constants.FORCE_START_APP_UP_RUNNING
						+  TradewareTraderUtil.getInstance().getCurrentTime();
			}  else if (TradewareAppInfoBean.STATUS_APP_START_IN_PROGRESS == (StockUtil.getAppBean().getAppStatus())) {
				response = Constants.FORCE_START_APP_IN_PROGRESS
						+  TradewareTraderUtil.getInstance().getCurrentTime();
			} /*/TODO:CONFLICT_ORB else if (!StockUtil.kiteConnectObjectsForAdmins.keySet().contains(StockUtil.DEFAULT_USER)) {
				response = Constants.FORCE_START_APP_ADMIN_LOGIN_VALID
						+  TradewareTraderUtil.getInstance().getCurrentTime();
			}*/ else {
				try {
				//set application status is in progress
				StockUtil.getAppBean().setAppStatus(TradewareAppInfoBean.STATUS_APP_START_IN_PROGRESS);
				
				// Create a fresh instance
				/**NSEIndiaTool.getInstance();**/nSEIndiaTool.init_NSEIndiaTool();
				System.out.println(Constants.NSE_TOOL_PROCESS_SUCCESSFUL);
				
				//create secondary administrator or user kite connect sessions
				//GoogleSheetUtil.getInstance().createUserKiteSession();
				System.out.println(Constants.ADMIN_USER_KITE_SESSION__SUCCESSFUL);
				
				//Prepare free call data and publish to Google sheets/database
				/*List<String> freeCallList = FreeCallDataTool.getInstance().prepareRandom20StockFreeCall();
				GoogleSheetUtil.getInstance().publishFreeCallDataInitial(freeCallList);*/
				System.out.println(Constants.FREE_CALL_DATA_PROCSS_SUCCESSFUL);
				
				/** OLDER WAY OF THREAD
				 * BuyOrSellStocksScannerThread_V2 scan = new BuyOrSellStocksScannerThread_V2(
				 * StockUtil.kiteConnectObjectsForAdmins.get(adminUser)); scan.start();
				 */
				//created and start Option call trading thread scanner
				Thread scan = new Thread(new BuyOrSellStocksScannerThread_V2(
						StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)));
				//scan.start();
				System.out.println(Constants.OPTION_TRADE_SCANNER_PROCSS_SUCCESSFUL+  TradewareTraderUtil.getInstance().getCurrentTime());
				
				//created and start Future call trading thread scanner
				Thread scanFuturesThread = new Thread(new BuySellFutureScannerThead(
						StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)));
				//TODO:CONFLICT_ORB scanFuturesThread.start();
				System.out.println(Constants.FUTURE_TRADE_SCANNER_PROCSS_SUCCESSFUL+  TradewareTraderUtil.getInstance().getCurrentTime());
				
				//created and start Narrow 7 call trading thread scanner
				Thread narrow7Thread  = new Thread( new Narrow7StockScannerThread(
						StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)));
				//narrow7Thread.run();
				System.out.println(Constants.NORROW7_TRADE_SCANNER_PROCSS_SUCCESSFUL+  TradewareTraderUtil.getInstance().getCurrentTime());
				//created and start Universal strategy call trading thread scanner
				//created and start Stronger/weaker call trading thread scanner
				//created and start ORB15 call trading thread scanner
				
				//Update Algo trader positional/intraday levels
				if (null != KiteConnectTool.getInstance()) {
					Set<String> symbols = /**NSEIndiaTool.getInstance()**/nSEIndiaTool.getPositionalResultDataMap().keySet();
					AlgoLevelUpdaterTool.getInstance().updateIntradyLevels(KiteConnectTool.getInstance().getKiteOHLCQuotes(symbols));
				}
				System.out.println(Constants.ALGO_CALL_LEVEL_PROCESS_SUCCESSFUL+  TradewareTraderUtil.getInstance().getCurrentTime());
				
				StockUtil.getAppBean().setAppStatus(TradewareAppInfoBean.STATUS_APP_UP_AND_RUNNING);
				System.out.println(Constants.FORCE_START_APP_SUCCESSFUL);
				response = Constants.FORCE_START_APP_SUCCESSFUL;
				} catch(Exception e) {
					//if any failure reset application status
					StockUtil.getAppBean().setAppStatus(TradewareAppInfoBean.STATUS_APP_DOWN);
					response = Constants.FORCE_START_APP_UNSUCCESS
					+ TradewareTraderUtil.getInstance().getCurrentTime();
				}
			}
			ModelAndView view = getModelAndView("home2");
			view.addObject("responseMessage", response);
			return view;
		}
		
		//should be in common controller
		public ModelAndView getModelAndView(String source) {
			ModelAndView modelAndView = new ModelAndView(source);
			modelAndView.addObject("hostName", StockUtil.serverHostName);
			return modelAndView;
	}

	@Autowired
	//private NSEIndiaTool nSEIndiaTool;
	private NSEIndiaToolNewNseSite nSEIndiaTool;
}
