package hoghlow.tradeware.stockmarket.controller;

import org.springframework.stereotype.Controller;

@Controller
public class AlgoTradeWebController {

	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView initializeHomePage(Model model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("home");
		if (!StockUtil.kiteConnectAdmins.isEmpty() && !StockUtil.kiteConnectAdminsHist.isEmpty()) {
			mv = HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
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
					NkpAlgoLogger.printWithNewLine(StockUtil.serverHostName);
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
		} else if (StockUtil.kiteConnectAdmins.contains(userId)
				&& StockUtil.kiteConnectAdminUsers.contains(userId)) {
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

			
			 * The request token can to be obtained after completion of login process. Check
			 * out https://kite.trade/docs/connect/v1/#login-flow for more information. A
			 * request token is valid for only a couple of minutes and can be used only
			 * once. An access token is valid for one whole day. Don't call this method for
			 * every app run. Once an access token is received it should be stored in
			 * preferences or database for further usage.
			 
			User kiteConnectUser = kiteConnect.generateSession(reqestToken,
					StockUtil.getAdmins().get(StockUtil.getAdmins().get(userId)));// "your_apiSecret");
			kiteConnect.setAccessToken(kiteConnectUser.accessToken);
			kiteConnect.setPublicToken(kiteConnectUser.publicToken);
			StockUtil.kiteConnectAdmins.put(userId, kiteConnect);
			StockUtil.kiteConnectAdminUsers.put(userId, kiteConnectUser);
			NkpAlgoLogger.printWithNewLine(String.valueOf(StockUtil.kiteConnectAdminUsers.size()));
			if (StockUtil.DEFAULT_USER.equals(userId)) {
				// getUserAccessToken(StockUtil.DEFAULT_USER);
			}
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

			
			 * The request token can to be obtained after completion of login process. Check
			 * out https://kite.trade/docs/connect/v1/#login-flow for more information. A
			 * request token is valid for only a couple of minutes and can be used only
			 * once. An access token is valid for one whole day. Don't call this method for
			 * every app run. Once an access token is received it should be stored in
			 * preferences or database for further usage.
			 

			StockUtil.kiteConnectAdmins.put(userId, kiteConnect);
			StockUtil.kiteConnectAdminUsers.put(userId, new User());
			NkpAlgoLogger.printWithNewLine(String.valueOf(StockUtil.kiteConnectAdminUsers.size()));
			// } catch (JSONException e) {
			
			StockUtil.kiteConnectAdminsHist.put(userId, kiteConnect);
			
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
		private ModelAndView getHome2ModelAndView() {
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
	// force starters
	@RequestMapping(value = "forceStartApp", method = RequestMethod.GET)
	public ModelAndView setupNkpAlgoTraderTradeDataForcely() {
		String response = Constants.FORCE_START_APP_REQUEST;

		HighLowStrategyTool nseIndiaTool = HighLowStrategyTool.getInstance();

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
			// cloudDataBaseTool.prepareAlgoCloudData();
			nseIndiaTool.updateKiteFutureKeyAndInstrumentToken();
			nseIndiaTool.updateKiteFutureKeyAndInstrumentTokenForNifty50();
			NkpAlgoLogger.printWithNewLine(String.valueOf(HighLowStrategyTool.getInstance().getFromDateForKiteHistData_9_15()));
			
			
			nseIndiaTool.get5MinutueCandleData();
			HighLowStrategyTool.getInstance().get5MinutueCandleDataForNifty50(HighLowStrategyTool.getInstance().getNifty50Bean());
			HighLowScannerThread scanner = new HighLowScannerThread();
			scanner.run();

			StockUtil.getAppBean().setAppStatus(NkpAppInfoBean.STATUS_APP_UP_AND_RUNNING);
			NkpAlgoLogger.printWithNewLine(Constants.FORCE_START_APP_SUCCESSFUL);
			response = Constants.FORCE_START_APP_SUCCESSFUL;
		}

		ModelAndView view = HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		view.addObject("responseMessage", response);
		return view;
	}
	
	@RequestMapping(value = "/appCheck", method = RequestMethod.GET)
	public ResponseEntity<String> appCheck() {
		return new ResponseEntity<>(HighLowStrategyTool.getInstance().tempVerify(false), HttpStatus.MOVED_PERMANENTLY);
	}*/
}
