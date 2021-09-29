package hoghlow.tradeware.stockmarket.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.LTPQuote;
import com.zerodhatech.models.User;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.log.NkpAlgoLogger;
import hoghlow.tradeware.stockmarket.util.StockUtil;

public abstract class AbstractAlgoTraderTool extends AlgoOrderPlacementWorkflowSwitchTool {
	
	public static boolean scanForBuyOrSell = true;
	public static boolean canPlaceOrders = true;
	private StockDataBean nifty50Bean;

	private ConcurrentHashMap<String, StockDataBean> baseDataMap = new ConcurrentHashMap<String, StockDataBean>();
	private ConcurrentHashMap<String, StockDataBean> highLowMap1200 = new ConcurrentHashMap<String, StockDataBean>();
	//private ConcurrentHashMap<String, StockDataBean> highLowMap1200Report = new ConcurrentHashMap<String, StockDataBean>();

	protected Map<String, Integer> getSymbolMap() {
		return StockUtil.getSymbols();
	}

	protected Map<String, Integer> getIndexSymbols() {
		return StockUtil.getIndexSymbols();
	}

	public String getKiteFuturekey(String symbol) {
		String currExp = getCurrentMonthExpiryDate();
		return (FUTURE_KEY_PREFIX_NFO + symbol + currExp.substring(7, 9) + currExp.substring(2, 5)
				+ FUTURE_KEY_SUFFIX_NFO).toUpperCase();
	}
	
	public double getRoundupValue(double value) {
		return new BigDecimal(value).setScale(1, RoundingMode.DOWN).doubleValue();
	}

	public double getRoundupValue2(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
	}
	
	private Date tradeDateForReport = null;
	public void setTradeDateForReport(Date tradeDateForReport) {
		this.tradeDateForReport  = tradeDateForReport;
	}
	public Date getTradeDateForReport() {
		if (null != tradeDateForReport) {
			return tradeDateForReport;
		}
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
		LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 00, 00, 00)); // Trading
																												// start
		if (currentTime.isAfter(tradeStartTime)) {
			if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				//Do nothing
			} else  if (currentTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
				currentTime = currentTime.minusDays(2);
			} else  if (currentTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
				currentTime = currentTime.minusDays(1);
			}
		} else if (currentTime.isBefore(tradeStartTime)) {
			if (currentTime.getDayOfWeek() == DayOfWeek.MONDAY) {
				currentTime = currentTime.minusDays(3);
			} else if (currentTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
				currentTime = currentTime.minusDays(2);
			} else /*if (currentTime.getDayOfWeek() == DayOfWeek.SATURDAY)*/ {
				currentTime = currentTime.minusDays(1);
			}
		} 
		for (LocalDate holiday : getNSEHolidays()) {
			NkpAlgoLogger.printWithNewLine(holiday +" - "+currentTime.toLocalDate()+" - "+holiday.isEqual(currentTime.toLocalDate()));
			if (holiday.isEqual(currentTime.toLocalDate())) {
				currentTime = currentTime.minusDays(1);
				break;
			}
		}
		//currentTime = currentTime.minusDays(5);
		NkpAlgoLogger.printWithNewLine("---- >> "+Date.from(currentTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
		Date date = Date.from(currentTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateAsString = sdf.format(date);
		NkpAlgoLogger.printWithNewLine("---- >> "+dateAsString);
		try {
			tradeDateForReport = sdf.parse(dateAsString);
			return tradeDateForReport;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	private List<LocalDate> nseHolidays = new ArrayList<LocalDate>();
	protected List<LocalDate> getNSEHolidays() {
		if (nseHolidays.isEmpty()) {
			nseHolidays.add(LocalDate.of(2020, 4, 10));
			nseHolidays.add(LocalDate.of(2020, 4, 14));
			nseHolidays.add(LocalDate.of(2020, 5, 1));
			nseHolidays.add(LocalDate.of(2020, 5, 25));
			nseHolidays.add(LocalDate.of(2020, 10, 2));
			nseHolidays.add(LocalDate.of(2020, 11, 16));
			nseHolidays.add(LocalDate.of(2020, 11, 30));
			nseHolidays.add(LocalDate.of(2020, 12, 25));
		}
		return nseHolidays;
	}

	public String getCurrentMonthExpiryDate() {
		LocalDate currentMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of(TIME_ZONE))
				.isAfter(LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(TIME_ZONE))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE)).plusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}
		ZonedDateTime zonedDateTime = currentMonthExpiryDate.atStartOfDay(ZoneId.of(TIME_ZONE));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
		return zonedDateTime.format(dtf).toUpperCase();
	}

	// One time call from scheduler to set future key and instrument
	public void updateKiteFutureKeyAndInstrumentToken() {
		try {
			NkpAlgoLogger.logInfo(AbstractAlgoTraderTool.class, "updateKiteFutureKeyAndInstrumentToken", "ENTRY <<<");
			if (null != getSymbolMap() && !getSymbolMap().isEmpty()) {
				Map<String, LTPQuote> quotesMap = null;
				Object[] symbolArr = getSymbolMap().keySet().toArray();
				List<String> symbolsArray = new ArrayList<String>();
				for (int i = 0; i < symbolArr.length; i++) {
					symbolsArray.add(FUTURE_KEY_PREFIX_NSE + symbolArr[i].toString());
					symbolsArray.add(getKiteFuturekey(symbolArr[i].toString()));
				}
				quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getLTP(symbolsArray.toArray(new String[0]));

				for (String key : quotesMap.keySet()) {
					NkpAlgoLogger.printWithNewLine(key);
					if (key.startsWith(FUTURE_KEY_PREFIX_NSE)) {
						String symbol = key.replace(FUTURE_KEY_PREFIX_NSE, EMPTY_STRING);
						String futureKey = getKiteFuturekey(symbol);
						LTPQuote cashQuote = quotesMap.get(key);
						LTPQuote futureQuote = quotesMap.get(futureKey);

						StockDataBean cashBean = new StockDataBean(getSymbolMap().get(symbol), symbol);
						cashBean.setKiteFutureKey(key);
						cashBean.setInstrumentToken(cashQuote.instrumentToken);

						StockDataBean futureBean = new StockDataBean(getSymbolMap().get(symbol),
								symbol + FUTURE_SYMBOL);
						futureBean.setKiteFutureKey(futureKey);
						futureBean.setInstrumentToken(futureQuote.instrumentToken);

						baseDataMap.put(key, cashBean);
						baseDataMap.put(futureKey, futureBean);
						NkpAlgoLogger.printWithNoNewLine(PROGRESS_DOT);
					}
				}
				NkpAlgoLogger.printWithNewLine("baseDataMap -------   "+baseDataMap.size());
			} else {
				NkpAlgoLogger.logError(AbstractAlgoTraderTool.class, "updateKiteFutureKeyAndInstrumentToken",
						"FATAL ERROR - base map is empty" + getCurrentDateTime());
			}
			NkpAlgoLogger.logInfo(AbstractAlgoTraderTool.class, "updateKiteFutureKeyAndInstrumentToken", "EXIT >>>");
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.logError(AbstractAlgoTraderTool.class, "updateKiteFutureKeyAndInstrumentToken",
					"FATAL ERROR EXCEPTION - base map is empty" + getCurrentDateTime());
			e.printStackTrace();
		}
	}

	public String getCurrentDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
		return LocalDateTime.now(ZoneId.of(TIME_ZONE)).format(dtf);
	}

	public String getCurrentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_PATTERN);
		return LocalDateTime.now(ZoneId.of(TIME_ZONE)).format(dtf);
	}
	
	public String getCurrentTime(Date date) {
		/*String PATTERN = "hh:mm:ss";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
		return date.toInstant().atZone(ZoneId.of(TIME_ZONE)).format(dtf);*/
		SimpleDateFormat dtf = new SimpleDateFormat(TIME_PATTERN);
		return dtf.format(date);
	}
	
	public Date getCurrentDateTimeAsDate() {
		return Timestamp.valueOf(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
	}
	
	public String getReportDate() {
		SimpleDateFormat dtf = new SimpleDateFormat(DATE_PATTERN_REPORT_DATE);
		return dtf.format(getTradeDateForReport());
	}
	
	// One time call from scheduler to set future key and instrument
	public void updateKiteFutureKeyAndInstrumentTokenForNifty50() {
		try {
			if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
				if (null != getIndexSymbols() && !getIndexSymbols().isEmpty()) {
					Map<String, LTPQuote> quotesMap = null;
					Object[] symbolArr = getIndexSymbols().keySet().toArray();
					List<String> symbolsArray = new ArrayList<String>();
					for (int i = 0; i < symbolArr.length; i++) {
						//symbolsArray.add(FUTURE_KEY_PREFIX_NSE + symbolArr[i].toString());
						String key = getKiteFuturekey(symbolArr[i].toString());
						if (key.startsWith(FUTURE_KEY_PREFIX_NFO)) {
							if (key.contains("NIFTY 50"))  {
								key = key.replaceAll("NIFTY 50", "NIFTY");	
							} else {
								key = key.replaceAll(SPACE, EMPTY_STRING);	
							}
						}
						 symbolsArray.add(key);
					}
					quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
							.getLTP(symbolsArray.toArray(new String[0]));

					for (String key : quotesMap.keySet()) {
						if (key.startsWith(FUTURE_KEY_PREFIX_NSE)) {
							String symbol = key.replace(FUTURE_KEY_PREFIX_NSE, EMPTY_STRING);
							// String futureKey = getKiteFuturekey(symbol);
							LTPQuote cashQuote = quotesMap.get(key);
							// LTPQuote futureQuote = quotesMap.get(futureKey);

							StockDataBean cashBean = new StockDataBean(getIndexSymbols().get(symbol), symbol);
							cashBean.setKiteFutureKey(key);
							cashBean.setInstrumentToken(cashQuote.instrumentToken);

							setNifty50Bean(cashBean);
						} else if (key.startsWith(FUTURE_KEY_PREFIX_NFO)) {
							//String symbol = key.replace(FUTURE_KEY_PREFIX_NSE, EMPTY_STRING);
							LTPQuote futureQuote = quotesMap.get(key);
							StockDataBean futureBean = new StockDataBean(75, "NIFTY");
							futureBean.setKiteFutureKey(key);
							futureBean.setInstrumentToken(futureQuote.instrumentToken);
							setNifty50Bean(futureBean);
						}
					}

				} else {
					NkpAlgoLogger.logError(AbstractAlgoTraderTool.class,
							"updateKiteFutureKeyAndInstrumentTokenForNifty50", "FATAL ERROR " + getCurrentDateTime());
				}
			} else {
				NkpAlgoLogger.logError(AbstractAlgoTraderTool.class, "updateKiteFutureKeyAndInstrumentTokenForNifty50",
						"FATAL ERROR ADMIN LOGIN REQUIRED**********************************************"
								+ getCurrentDateTime());
			}
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.logError(AbstractAlgoTraderTool.class, "updateKiteFutureKeyAndInstrumentTokenForNifty50",
					"FATAL ERROR ExCEPTION" + getCurrentDateTime());
			e.printStackTrace();
		}
	}
	
	public static boolean isTradingTime = false;

	public boolean isTradingTime() {
		if (!isTradingTime) {
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
			LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 15, 05, 59)); // Trading
																													// start
			LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 29, 59, 59)); // closed

			// Still have to consider , NSE holidays from DB table
			if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
					isTradingTime = true;
				}
			}
		}
		return isTradingTime;
	}
	
	public boolean canPlaceOrders() {
		canPlaceOrders = false;
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
		LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 15, 05, 59)); // Trading
																														// start
		LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 00, 00, 00));//(15, 10, 00, 00)); // closed

		// Still have to consider , NSE holidays from DB table
		if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
				canPlaceOrders = true;
			}
		}
		return canPlaceOrders;
	}
	
	public boolean canOrderPlaceNow() {
		boolean canOrderPlaceNow = false;
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
		// Trading start
		LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 15, 05, 59));
		LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(11, 00, 01, 0)); // closed

		// Still have to consider , NSE holidays from DB table
		if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
				canOrderPlaceNow = true;
			}
		}
		return canOrderPlaceNow;// true;
	}
	
	public static boolean canUpdate5MinutesDate = false;

	public boolean canUpdate5MinutesDate() {
		if (!canUpdate5MinutesDate) {
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
			//LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 34, 59, 00)); 
			LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(10, 14, 59, 00));
			LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 29, 59, 59)); // closed

			// Still have to consider , NSE holidays from DB table
			if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
					canUpdate5MinutesDate = true;
				}
			}
		}
		return canUpdate5MinutesDate;
	}
	
	
	
	private static final String USER_ACCESS_TOKEN = "https://script.google.com/macros/s/AKfycbzXaQkhLSIm6yIqTwR50mwRxMQkIfwQKBrO_oAKN_XzsxlU089Z/exec?action=getUserApi&uid=ZI7952";

	public void createUserKiteNkpalgoSession() {
		StringBuilder postURL = new StringBuilder("");
		postURL.delete(0, postURL.length());
		StringBuffer response = new StringBuffer();
		try {
			postURL.append(USER_ACCESS_TOKEN);
			URL postURLObj = new URL(postURL.toString());
			HttpsURLConnection urlConn = (HttpsURLConnection) postURLObj.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// :"wERCTjkMhmTdoO7xCpz24xL8THPdglZz"}]}
			response.delete(0, response.lastIndexOf(":\"") + 2);
			response.delete(response.lastIndexOf("\""), response.length());

		} catch (MalformedURLException e) {
			NkpAlgoLogger.printWithNewLine("Error at User access toknen - \r\n" + e.getMessage());
		} catch (FailingHttpStatusCodeException | IOException e1) {
			NkpAlgoLogger.printWithNewLine("Error at User access toknen 2222- \r\n" + e1.getMessage());
		} catch (Exception e2) {
			NkpAlgoLogger.printWithNewLine("Error at User access toknen 333- \r\n" + e2.getMessage());
		}

		String userId = "ZI7952";

		if (!StockUtil.getAdmins().containsKey(userId)) {
			// return new ResponseEntity<>("Invaliduser, Not an admin user.",
			// HttpStatus.BAD_REQUEST);
		} /*
			 * else if (StockUtil.kiteConnectObjectsForAdmins.keySet().contains(userId) &&
			 * StockUtil.kiteConnectAdminUsers.keySet().contains(userId)) { return new
			 * ResponseEntity<>("Nkp algo trader admin user " + userId +
			 * " alrady logged in. And setup the Nkp algo trader data for the day.",
			 * HttpStatus.OK); }
			 */

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userId));
			kiteConnect.setUserId(userId);
			kiteConnect.setAccessToken(response.toString());

			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					NkpAlgoLogger.printWithNewLine("session expired");
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

			StockUtil.kiteConnectAdmins.put(userId, kiteConnect);
			StockUtil.kiteConnectAdminUsers.put(userId, new User());
			NkpAlgoLogger.printWithNewLine(String.valueOf(StockUtil.kiteConnectAdminUsers.size()));
			if (StockUtil.DEFAULT_USER.equals(userId)) {
				// prepareFreeCallsData(kiteConnect);
			}
		}  catch (/*JSONException*/ Exception e) {
			e.printStackTrace();
		}
		NkpAlgoLogger.printWithNewLine("KiteConnect-Nkpalgotrader session successfully crated..");

	}

	/*public static boolean isScanForBuyOrSell() {
		return scanForBuyOrSell;
	}

	public static void setScanForBuyOrSell(boolean scanForBuyOrSell) {
		AbstractAlgoTraderTool.scanForBuyOrSell = scanForBuyOrSell;
	}*/

	public StockDataBean getNifty50Bean() {
		return nifty50Bean;
	}

	public void setNifty50Bean(StockDataBean nifty50Bean) {
		this.nifty50Bean = nifty50Bean;
	}

	public ConcurrentHashMap<String, StockDataBean> getBaseDataMap() {
		return baseDataMap;
	}

	public void setBaseDataMap(ConcurrentHashMap<String, StockDataBean> baseDataMap) {
		this.baseDataMap = baseDataMap;
	}

	public ConcurrentHashMap<String, StockDataBean> getHighLowMap1200() {
		return highLowMap1200;
	}

	public void setHighLowMap1200(ConcurrentHashMap<String, StockDataBean> highLowMap1200) {
		this.highLowMap1200 = highLowMap1200;
	}

	/*public ConcurrentHashMap<String, StockDataBean> getHighLowMap1200Report() {
		return highLowMap1200Report;
	}

	public void setHighLowMap1200Report(ConcurrentHashMap<String, StockDataBean> highLowMap1200Report) {
		this.highLowMap1200Report = highLowMap1200Report;
	}*/
}