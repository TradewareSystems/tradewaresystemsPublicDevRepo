package nr4.sathvikashviktechtrader.stockmarket.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.NetworkException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.TokenException;
import com.zerodhatech.models.Quote;

import nr4.sathvikashviktechtrader.stockmarket.bean.Narrow7DataBean;
import nr4.sathvikashviktechtrader.stockmarket.log.NkpAlgoLogger;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;
import nr4.sathvikashviktechtrader.stockmarket.util.StockUtil;

public abstract class AbstractTool extends AlgoOrderPlacementWorkflowSwitchTool {

	
	private ConcurrentHashMap<String, Narrow7DataBean> baseForNR7AndUT = new ConcurrentHashMap<String, Narrow7DataBean>();
	private ConcurrentHashMap<String, Narrow7DataBean> monitorNR7 = new ConcurrentHashMap<String, Narrow7DataBean>();
	public ConcurrentHashMap<String, Narrow7DataBean> getBaseForNR7AndUT() {
		return baseForNR7AndUT;
	}

	public void setBaseForNR7AndUT(ConcurrentHashMap<String, Narrow7DataBean> baseForNR7AndUT) {
		this.baseForNR7AndUT = baseForNR7AndUT;
	}

	public ConcurrentHashMap<String, Narrow7DataBean> getMonitorNR7() {
		return monitorNR7;
	}
	
	public void setMonitorNR7(ConcurrentHashMap<String, Narrow7DataBean> monitorNR7) {
		this.monitorNR7 = monitorNR7;
	}

		//TODO - needd to check
		public boolean canPlaceOrders() {
			boolean canPlaceOrders = false;
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
			LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(9, 15, 05, 59)); // Trading
																															// start
			LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(15, 00, 00, 00));//(15, 10, 00, 00)); // closed

			// Still have to consider , NSE holidays from DB table
			if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
					canPlaceOrders = true;
				}
			}
			return canPlaceOrders;
		}
		
		private static boolean canUpdate15MinutesData = false;

		public boolean canUpdate15MinutesData() {
			if (!canUpdate15MinutesData) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
				LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(10, 14, 59, 00)); // Trading
																														// start
				LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(15, 00, 59, 59)); //(15, 29, 59, 59)); // closed

				// Still have to consider , NSE holidays from DB table
				if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
					if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
						canUpdate15MinutesData = true;
					}
				}
			}
			return canUpdate15MinutesData;
		}
		//TODO - needd to check end
			// date time utils start
		public String getCurrentDateTime() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_HIST_DATA);
			return LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).format(dtf);
		}

		public String getCurrentTime() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.TIME_PATTERN);
			return LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).format(dtf);
		}
		public String getCurrentTime(Date date) {
			if (null == date)  {
				return "";
			}
			SimpleDateFormat dtf = new SimpleDateFormat(Constants.TIME_PATTERN);
			return dtf.format(date);
		}
		
		public String getReportDate() {
			SimpleDateFormat dtf = new SimpleDateFormat(Constants.DATE_PATTERN_REPORT_DATE);
			return dtf.format(getTradeDateForReport());
		}
		
		public Date getCurrentDateTimeAsDate() {
			return Timestamp.valueOf(LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)));
		}
		
		private Date tradeDateForReport = null;
		public void setTradeDateForReport(Date tradeDateForReport) {
			this.tradeDateForReport  = tradeDateForReport;
		}
		public Date getTradeDateForReport() {
			if (null != tradeDateForReport) {
				return tradeDateForReport;
			}
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(9, 00, 00, 00)); // Trading
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
				//NkpAlgoLogger.printWithNewLine(holiday +" - "+currentTime.toLocalDate()+" - "+holiday.isEqual(currentTime.toLocalDate()));
				if (holiday.isEqual(currentTime.toLocalDate())) {
					currentTime = currentTime.minusDays(1);
					break;
				}
			}
			//currentTime = currentTime.minusDays(5);
			//NkpAlgoLogger.printWithNewLine("---- >> "+Date.from(currentTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
			Date date = Date.from(currentTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateAsString = sdf.format(date);
			//NkpAlgoLogger.printWithNewLine("---- >> "+dateAsString);
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
		
		// Kite based logic start
		public ConcurrentHashMap<String, Narrow7DataBean> updateDayOHLC(ConcurrentHashMap<String, Narrow7DataBean> dataMap) {
			try {
				if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
					// NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "updateDayOHLC",
					// getCurrentDateTime());
					Object[] symbolArr = dataMap.keySet().toArray();
					String[] symbolsArray = new String[symbolArr.length];
					for (int i = 0; i < symbolArr.length; i++) {
						symbolsArray[i] = symbolArr[i].toString();
					}

					Map<String, Quote> ohlcMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
							.getQuote(symbolsArray);

					for (String symbol : ohlcMap.keySet()) {
						Narrow7DataBean bean = dataMap.get(symbol);
						if (null != bean) {
							double lastPrice = ohlcMap.get(symbol).lastPrice;
							double open = ohlcMap.get(symbol).ohlc.open;
							double high = ohlcMap.get(symbol).ohlc.high;
							double low = ohlcMap.get(symbol).ohlc.low;
							double close = ohlcMap.get(symbol).ohlc.close;

							bean.setLtp(lastPrice);
							bean.setOpen(open);
							bean.setHigh(high);
							bean.setLow(low);
							bean.setClose(close);
							if (ohlcMap.get(symbol).depth.buy.size() > 5 && ohlcMap.get(symbol).depth.sell.size() >= 5) {
								if (symbol.startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
									bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(4).getPrice());
									bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(4).getPrice());
								} else {
									bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
									bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());
								}
							} else {
								bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
								bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());
							}
							if (Constants.NA.equals(bean.getTradedState())) {
								bean.setVolumes((long) ohlcMap.get(symbol).volumeTradedToday);
							}

							bean.setOi(ohlcMap.get(symbol).oi);
							bean.setOiDayHigh(ohlcMap.get(symbol).oiDayHigh);
							bean.setOiDayLow(ohlcMap.get(symbol).oiDayLow);
							
							dataMap.replace(symbol, bean);
						}
					}
				} else {
					NkpAlgoLogger.logError(AbstractTool.class, "updateKiteFutureKeyAndInstrumentTokenForNifty50",
							"FATAL ERROR ADMIN LOGIN REQUIRED**********************************************"
									+ getCurrentDateTime());
				}
			} catch (org.json.JSONException e) {
				NkpAlgoLogger.logError(AbstractTool.class, "updateDayOHLC",
						"org.json.JSONException" + getCurrentDateTime());
				NkpAlgoLogger.printWithNewLine(">>> " + dataMap.size());
				//se.printStackTrace();
				//updateDayOHLC(dataMap);
			} catch (java.net.UnknownHostException e) {
				NkpAlgoLogger.logError(AbstractTool.class, "updateDayOHLC",
						"UnknownHostException" + getCurrentDateTime());
				NkpAlgoLogger.printWithNewLine(">>> " + dataMap.size());
				e.printStackTrace();
				//updateDayOHLC(dataMap);
			} catch (java.net.SocketTimeoutException e) {
				NkpAlgoLogger.logError(AbstractTool.class, "updateDayOHLC",
						"SocketTimeoutException" + getCurrentDateTime());
				NkpAlgoLogger.printWithNewLine(">>> " + dataMap.size());
				e.printStackTrace();
				updateDayOHLC(dataMap);
			} catch (NetworkException e) {
				NkpAlgoLogger.logError(AbstractTool.class, "updateDayOHLC", "NetworkException" + getCurrentDateTime());
				NkpAlgoLogger.printWithNewLine(">>> " + dataMap.size());
				e.printStackTrace();
			} catch (Exception e) {
				NkpAlgoLogger.logError(AbstractTool.class, "updateDayOHLC", "Exception" + getCurrentDateTime());
				e.printStackTrace();
			} catch (TokenException e) {
				NkpAlgoLogger.logError(AbstractTool.class, "updateDayOHLC", "TokenException" + getCurrentDateTime());
				e.printStackTrace();
			} catch (KiteException e) {
				NkpAlgoLogger.logError(AbstractTool.class, "updateDayOHLC", "KiteException" + getCurrentDateTime());
				e.printStackTrace();
			}
			return dataMap;
		}
		
		protected Date getToDateForKiteHistData_9_45() {
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN_HIST_DATA);
			Date to = Calendar.getInstance().getTime();
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_HIST_DATA);
				currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(),
						9, 45, 0);
				to = formatter.parse(currentTime.format(dtf));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return to;
		}

		protected Date getToDateForKiteHistData_9_15() {
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN_HIST_DATA);
			Date to = Calendar.getInstance().getTime();
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_HIST_DATA);
				currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(),
						9, 15, 0);
				to = formatter.parse(currentTime.format(dtf));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return to;
		}
		
		public double getRoundupValue(double value) {
			return new BigDecimal(value).setScale(1, RoundingMode.DOWN).doubleValue();
		}

		public double getRoundupValue2(double value) {
			return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
		}

		//// Kite based logic end
		String DATE_PATTERN = "ddMMMyyyy";

		public String getCurrentMonthExpiryDate() {
			LocalDate currentMonthExpiryDate = null;
			if ((LocalDate.now(ZoneId.of(Constants.TIME_ZONE))
					.isAfter(LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
					&& (LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(Constants.TIME_ZONE))
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
				currentMonthExpiryDate = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).plusMonths(1)
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
			} else {
				currentMonthExpiryDate = LocalDate.now(ZoneId.of(Constants.TIME_ZONE))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

			}
			ZonedDateTime zonedDateTime = currentMonthExpiryDate.atStartOfDay(ZoneId.of(Constants.TIME_ZONE));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
			return zonedDateTime.format(dtf).toUpperCase();
		}

		public String getKiteFuturekey(String symbol) {
			String currExp = getCurrentMonthExpiryDate();
			return (Constants.FUTURE_KEY_PREFIX_NFO + symbol + currExp.substring(7, 9) + currExp.substring(2, 5)
					+ Constants.FUTURE_KEY_SUFFIX_NFO).toUpperCase();
		}
}
