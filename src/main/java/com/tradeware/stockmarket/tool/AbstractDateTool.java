package com.tradeware.stockmarket.tool;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
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

import org.springframework.beans.factory.annotation.Autowired;

import com.tradeware.stockmarket.bean.SystemErrorDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

public class AbstractDateTool extends TradewareOrderPlacementWorkflowControlTool {

	@Autowired
	protected DatabaseHelper databaseHelper;
	
	private boolean isTradingTime = false;
	private Date tradeDateForReport;
	private Date tradedCloseTimeBeffore;
	protected Date toDateForKiteHistDate;
	private Date fromDateForKiteHistData_9_00;
	private Date toDateForKiteHistData_15_30;
	private Date fromDateForKiteHistDataOnCurrentDay_9_00;
	private Date fromDateForKiteHistDataOnCurrentDay_9_15;
	private Date toDateForKiteHistDateOnCurrentDay_9_45;
	
	private Boolean is15MinteDayLevelCandle = null;//false;
	
	private Date fromDateForKiteHistDataStochastic1Hour_9_00;
	private Date fromDateForKiteHistDataStochastic5Or15Min_9_00;
	
	//Need to refactor start
	// Dates and Amounts util start
		public boolean isTradingTime() {
			if (!isTradingTime) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 15, 05, 59));
				LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 29, 59, 59));

				// Still have to consider , NSE holidays from DB table
				if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
					if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
						isTradingTime = true;
					}
				}
			}
			return isTradingTime;
		}
		public void setTradingTime(boolean isTradingTime) {
			this.isTradingTime = isTradingTime;
		}

		public String getCurrentDateTime() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
			return LocalDateTime.now(ZoneId.of(TIME_ZONE)).format(dtf);
		}
		
		public String getCurrentDateTimeForTradeName() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_TRADE_NAME);
			return LocalDateTime.now(ZoneId.of(TIME_ZONE)).format(dtf);
		}

		public String getCurrentDateTimeAsAMPM() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_WITH_AM_PM);
			return LocalDateTime.now(ZoneId.of(TIME_ZONE)).format(dtf);
		}
		
		public String getCurrentDateTimeAsAMPM_12HrsMode() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_WITH_AM_PM_2);
			return LocalDateTime.now(ZoneId.of(TIME_ZONE)).format(dtf);
		}
		
		public String getGivenDateTimeAsAMPM(Date date) {
			ZonedDateTime zdt = date.toInstant().atZone(ZoneId.of(Constants.TIME_ZONE));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_WITH_AM_PM_2);
			return zdt.format(dtf);
		}
		
		public String getCurrentTime() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_PATTERN);
			return LocalDateTime.now(ZoneId.of(TIME_ZONE)).format(dtf);
		}

		public String getCurrentTime(Date date) {
			if (null != date) {
				SimpleDateFormat dtf = new SimpleDateFormat(TIME_PATTERN);
				return dtf.format(date);
			}
			return EMPTY_STRING;
		}
		
		public String getDateAsString(Date date) {
			if (null != date) {
				SimpleDateFormat dtf = new SimpleDateFormat(DATE_PATTERN_REPORT_DATE);
				return dtf.format(date);
			}
			return EMPTY_STRING;
		}
		
		public String getDateAsStringAMPM(Date date) {
			if (null != date) {
				SimpleDateFormat dtf = new SimpleDateFormat(DATE_PATTERN_WITH_AM_PM);
				return dtf.format(date);
			}
			return EMPTY_STRING;
		}

		public Date getCurrentDateTimeAsDate() {
			return Timestamp.valueOf(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
			
			/*LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			tradeBean.setTradedAtDtTm(Timestamp.valueOf(ldt));*/
		}
		
		public Date getCrrentDateTimeStamp() {
			ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
			return Date.from(zdt.toInstant());
		}

		public String getCurrentTimeAsString(Date date) {
			if (null != date) {
				/*SimpleDateFormat dtf = new SimpleDateFormat(Constants.TIME_PATTERN);
				return dtf.format(date);*/
				ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(Constants.TIME_ZONE));
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.TIME_PATTERN);
				return zdt.format(dtf);
			}
			return Constants.EMPTY_STRING;
		}
		
		public Date convertStringToDate(String dateStr) {
			Date dateAsDate = null;
			DateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN_EXP_DATES);
			try {
				dateAsDate = formatter.parse(dateStr);
			} catch (ParseException e) {
			
			}
			return dateAsDate;
		}
		
	public Date convertStringToDate(String dateStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate localDate = LocalDate.parse(dateStr, formatter);
		return Date.from(localDate.atStartOfDay(ZoneId.of(TIME_ZONE)).toInstant());
	}

	public boolean isMontlyOrWeeklyExpiry(String expiryDateStr) {
		boolean isMontlyExpiry = false;
		Date expiryDate = convertStringToDate(expiryDateStr, DATE_PATTERN_EXP_DATES);
		LocalDate localDate = expiryDate.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate();

		LocalDate sameMonthLastThursday = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		LocalDate sameMonthLastWednesday = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
		LocalDate sameMonthLastTuesday = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.TUESDAY));
		LocalDate sameMonthLastMonday = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));

		isMontlyExpiry = ((localDate.equals(sameMonthLastThursday)) || (localDate.equals(sameMonthLastWednesday))
				|| (localDate.equals(sameMonthLastTuesday)) || (localDate.equals(sameMonthLastMonday)));

		return isMontlyExpiry;
	}
	
	public String stripTrailingZeros(Double doubleValue) {
		BigDecimal bigDecimal = new BigDecimal(doubleValue);  
		return bigDecimal.stripTrailingZeros().toPlainString();
	}
	
	public String stripTrailingZeros(String doubleValue) {
		BigDecimal bigDecimal = new BigDecimal(doubleValue);  
		return bigDecimal.stripTrailingZeros().toPlainString();
	}
	//nEED TO rEFACCTOR End

	//12/17/2020 For NR7 start
	public String getPreviousMonthStartDate() {
		LocalDate previousMonthStartDate = null;
		if ((LocalDate.now(ZoneId.of(TIME_ZONE)).isAfter(
				LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(TIME_ZONE))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			previousMonthStartDate = LocalDate.now(ZoneId.of(TIME_ZONE)).minusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).plusDays(1);
		} else {
			previousMonthStartDate = LocalDate.now(ZoneId.of(TIME_ZONE)).minusMonths(2)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).plusDays(1);
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NSE);
		return previousMonthStartDate.format(dtf);
	}

	public String getPreviousMonthExpiryDate() {
		LocalDate previousMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of(TIME_ZONE)).isAfter(
				LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(TIME_ZONE))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE)).minusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}

		/*
		 * if (!isCurrentMonthFirstCloseAvailable &&
		 * isCurrentMonthFirstCloseAvailable(previousMonthExpiryDate)) {
		 * previousMonthExpiryDate = previousMonthExpiryDate.plusDays(1); }
		 */
		String PATTERN = "dd-MMM-yyyy";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
		return previousMonthExpiryDate.format(dtf);
	}

	public String getCurrentMonthFirstDay() {
		LocalDate previousMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of(TIME_ZONE)).isAfter(
				LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(TIME_ZONE))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE)).minusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}
		previousMonthExpiryDate = previousMonthExpiryDate.plusDays(1);
		String PATTERN = "dd-MMM-yyyy";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
		return previousMonthExpiryDate.format(dtf);
	}

	public String getCurrentMonthExpiryDate() {
		LocalDate currentMonthExpiryDate = null;
		if (((LocalDate.now(ZoneId.of(TIME_ZONE))
				.equals(LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				|| (LocalDate.now(ZoneId.of(TIME_ZONE)).isAfter(
						LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)))))
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
	
	/*public String getCurrentMonthExpiryDate1() {
		LocalDate currentMonthExpiryDate = null;
		LocalDate currentDate = LocalDate.now(ZoneId.of(TIME_ZONE));
		LocalDate currentMonthLastThursdayDate = LocalDate.now(ZoneId.of(TIME_ZONE))
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		
		if (( (currentDate
				.equals(currentMonthLastThursdayDate) || currentDate
				.isAfter(currentMonthLastThursdayDate)) )
				&& (currentDate.getMonth().equals(currentMonthLastThursdayDate.getMonth()))) {
			currentMonthExpiryDate = currentDate.plusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		}  else {
			currentMonthExpiryDate = currentMonthLastThursdayDate;
		}
		ZonedDateTime zonedDateTime = currentMonthExpiryDate.atStartOfDay(ZoneId.of(TIME_ZONE));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
		return zonedDateTime.format(dtf).toUpperCase();
	}*/
	
	//For New Nse India
		public String getCurrentMonthExpiryDateNewNseSite() {
			LocalDate currentMonthExpiryDate = null;
			if ((LocalDate.now(ZoneId.of(TIME_ZONE)).isAfter(
					LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
					&& (LocalDate.now(ZoneId.of(TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(TIME_ZONE))
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
				currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE)).plusMonths(1)
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
			} else {
				currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIME_ZONE))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

			}

			ZonedDateTime zonedDateTime = currentMonthExpiryDate.atStartOfDay(ZoneId.of(TIME_ZONE));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NSE);
			return zonedDateTime.format(dtf).toUpperCase();
		}

		private String previousMonthFromDateStr = null;

		public String getPreviousMonthFromDateNewNseSite() {
			if (null == previousMonthFromDateStr) {
				LocalDate previousMonthFromDate = null;
				if ((LocalDate.now(ZoneId.of(TIME_ZONE)).isAfter(
						LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
						&& (LocalDate.now(ZoneId.of(TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(TIME_ZONE))
								.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
					previousMonthFromDate = LocalDate.now(ZoneId.of(TIME_ZONE))
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				} else {
					previousMonthFromDate = LocalDate.now(ZoneId.of(TIME_ZONE)).minusMonths(1)
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

				}
				previousMonthFromDate = previousMonthFromDate.minusMonths(3);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
				previousMonthFromDateStr = previousMonthFromDate.format(dtf);
			}
			return previousMonthFromDateStr;
		}

		private String previousMonthToDateStr = null;
		public String getPreviousMonthToDateNewNseSite() {
			if (null == previousMonthToDateStr) {
				LocalDate previousMonthFromDate = null;
				if ((LocalDate.now(ZoneId.of(TIME_ZONE)).isAfter(
						LocalDate.now(ZoneId.of(TIME_ZONE)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
						&& (LocalDate.now(ZoneId.of(TIME_ZONE)).getMonth().equals((LocalDate.now(ZoneId.of(TIME_ZONE))
								.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
					previousMonthFromDate = LocalDate.now(ZoneId.of(TIME_ZONE))
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				} else {
					previousMonthFromDate = LocalDate.now(ZoneId.of(TIME_ZONE)).minusMonths(1)
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

				}

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
				previousMonthToDateStr = previousMonthFromDate.format(dtf);
			}
			return previousMonthToDateStr;
		}
		
		public String getCurrentMonthFromDateNewNseSite() {
			LocalDate localDate = LocalDate.now();
			localDate = localDate.minusDays(1).minusMonths(1);
			ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of(TIME_ZONE));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
			return zonedDateTime.format(dtf).toUpperCase();
		}
		
		public String getCurrentMonthToDateNewNseSite() {
			LocalDate localDate = LocalDate.now();
			localDate = localDate.minusDays(1);
			ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of(TIME_ZONE));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
			return zonedDateTime.format(dtf).toUpperCase();
		}
		
		public String getFromDateWithOneYearHistDataNewNseSite() {
			LocalDate localDate = LocalDate.now();
			localDate = localDate.minusDays(1).minusYears(1);
			ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of(TIME_ZONE));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
			return zonedDateTime.format(dtf).toUpperCase();
		}
		
	public String getFromDateByToDateForOneYearHistDataNewNseSite(String dateAsString, int dayToMins) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
		LocalDate localDate = LocalDate.parse(dateAsString, formatter);
		localDate = localDate.minusDays(dayToMins);
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of(TIME_ZONE));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
		return zonedDateTime.format(dtf).toUpperCase();
	}
		
		//public String getPreviousMonthExpiryDate() {
		public Date getMonthlyReportStartDate() {
			LocalDate previousMonthExpiryDate = null;
			LocalDate anyDateForReportMonth = getTradeDateForReport().toInstant().atZone(ZoneId.of(TIME_ZONE))
					.toLocalDate();
			if ((anyDateForReportMonth.isAfter(
					anyDateForReportMonth.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
					&& (anyDateForReportMonth.getMonth().equals((anyDateForReportMonth
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
				previousMonthExpiryDate = anyDateForReportMonth
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				previousMonthExpiryDate = previousMonthExpiryDate.plusDays(1);
			} else {
				previousMonthExpiryDate = anyDateForReportMonth.minusMonths(1)
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				previousMonthExpiryDate = previousMonthExpiryDate.plusDays(1);

			}

			return Date.from(previousMonthExpiryDate.atStartOfDay(ZoneId.of(TIME_ZONE)).toInstant());
		}
		
		//public String getCurrentMonthExpiryDate() {
		public Date getMonthlyReportEndDate() {
			LocalDate currentMonthExpiryDate = null;
			LocalDate anyDateForReportMonth = getTradeDateForReport().toInstant().atZone(ZoneId.of(TIME_ZONE))
					.toLocalDate();
			if ((anyDateForReportMonth
					.isAfter(anyDateForReportMonth.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
					&& (anyDateForReportMonth.getMonth().equals((anyDateForReportMonth
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
				currentMonthExpiryDate = anyDateForReportMonth.plusMonths(1)
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
			} else {
				currentMonthExpiryDate = anyDateForReportMonth
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

			}
			return Date.from(currentMonthExpiryDate.atStartOfDay(ZoneId.of(TIME_ZONE)).toInstant());
		}

		//Phase 2  start
		
		

		public Date getFromDateForKiteHistData_9_00() {
			if (null == fromDateForKiteHistData_9_00) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date from = Calendar.getInstance().getTime();
				if (currentTime.getDayOfWeek() == DayOfWeek.MONDAY) {
					currentTime = currentTime.minusDays(3);
				} else {
					currentTime = currentTime.minusDays(1);
				}

				for (LocalDate holiday : getNSEHolidays()) {
					if (holiday.isEqual(currentTime.toLocalDate())) {
						currentTime = currentTime.minusDays(1);
						break;
					}
				}
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					/*currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 9, 0, 0);*/ //woring
					
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 0, 0, 0);
					from = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					//e.printStackTrace();
				}
				fromDateForKiteHistData_9_00 = from;
			}
			return fromDateForKiteHistData_9_00;
		}
		
		public Date getFromDateForKiteHistDataStochastic1Hour_9_00() {
			if (null == fromDateForKiteHistDataStochastic1Hour_9_00) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date from = Calendar.getInstance().getTime();
				if (currentTime.getDayOfWeek() == DayOfWeek.MONDAY||currentTime.getDayOfWeek() == DayOfWeek.TUESDAY) {
					currentTime = currentTime.minusDays(5);
				} else {
					currentTime = currentTime.minusDays(3);
				}

				for (LocalDate holiday : getNSEHolidays()) {
					if (holiday.isEqual(currentTime.toLocalDate())) {
						currentTime = currentTime.minusDays(1);
						break;
					}
				}
				
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					/*currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 9, 0, 0);*/ //woring
					
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 0, 0, 0);
					from = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					//e.printStackTrace();
				}
				fromDateForKiteHistDataStochastic1Hour_9_00 = from;
			}
			return fromDateForKiteHistDataStochastic1Hour_9_00;
		}
		
		public Date getFromDateForKiteHistDataStochastic5Or15Min_9_00() {
			if (null == fromDateForKiteHistDataStochastic5Or15Min_9_00) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date from = Calendar.getInstance().getTime();
				if (currentTime.getDayOfWeek() == DayOfWeek.MONDAY) {
					currentTime = currentTime.minusDays(3);
				} else {
					currentTime = currentTime.minusDays(1);
				}

				for (LocalDate holiday : getNSEHolidays()) {
					if (holiday.isEqual(currentTime.toLocalDate())) {
						currentTime = currentTime.minusDays(1);
						break;
					}
				}
				
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					/*currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 9, 0, 0);*/ //woring
					
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 0, 0, 0);
					from = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					//e.printStackTrace();
				}
				fromDateForKiteHistDataStochastic5Or15Min_9_00 = from;
			}
			return fromDateForKiteHistDataStochastic5Or15Min_9_00;
		}
		
		public Date getToDateForKiteHistData_15_30() {
			if (null == toDateForKiteHistData_15_30) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date toDate = Calendar.getInstance().getTime();
/*				if (currentTime.getDayOfWeek() == DayOfWeek.MONDAY) {
					currentTime = currentTime.minusDays(3);
				} else {
					currentTime = currentTime.minusDays(1);
				}

				for (LocalDate holiday : getNSEHolidays()) {
					if (holiday.isEqual(currentTime.toLocalDate())) {
						currentTime = currentTime.minusDays(1);
						break;
					}
				}*/
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					/*currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 15, 30, 0);*/
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 0, 0, 0);
					toDate = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					//e.printStackTrace();
				}
				toDateForKiteHistData_15_30 = toDate;
			}
			return toDateForKiteHistData_15_30;
		}

		public Date getFromDateForKiteHistDataOnCurrentDay_9_00() {
			if (null == fromDateForKiteHistDataOnCurrentDay_9_00) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date from = Calendar.getInstance().getTime();
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 9, 0, 0);
					from = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					TradewareLogger.saveErrorLog(CLASS_ABSTRACT_DATE_TOOL, METHOD_GET_FROM_DATE_FOR_KITE_HIST_DATA_ON_CURRENT_DAY_9_00,
							e, ERROR_TYPE_PARSE_EXCEPTION, ERROR_SEVERIRITY_FATAL);
				}
				fromDateForKiteHistDataOnCurrentDay_9_00 = from;
			}
			return fromDateForKiteHistDataOnCurrentDay_9_00;
		}
		
		public Date getFromDateForKiteHistDataOnCurrentDay_9_15() {
			if (null == fromDateForKiteHistDataOnCurrentDay_9_15) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date from = Calendar.getInstance().getTime();
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 9, 15, 0);
					from = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					TradewareLogger.saveErrorLog(CLASS_ABSTRACT_DATE_TOOL, METHOD_GET_FROM_DATE_FOR_KITE_HIST_DATA_ON_CURRENT_DAY_9_15,
							e, ERROR_TYPE_PARSE_EXCEPTION, ERROR_SEVERIRITY_FATAL);
				}
				fromDateForKiteHistDataOnCurrentDay_9_15 = from;
			}
			return fromDateForKiteHistDataOnCurrentDay_9_15;
		}

		public Date getToDateForKiteHistDate_WithCurrentTime() {
			if (null == toDateForKiteHistDate) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date toDate = Calendar.getInstance().getTime();
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), currentTime.getHour(), currentTime.getMinute(),
							currentTime.getSecond());
					toDate = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					TradewareLogger.saveErrorLog(CLASS_ABSTRACT_DATE_TOOL, METHOD_GET_TO_DATE_FOR_KITE_HIST_DATA_WITH_CURRENT_TIME,
							e, ERROR_TYPE_PARSE_EXCEPTION, ERROR_SEVERIRITY_FATAL);
				}
				toDateForKiteHistDate = toDate;
			}
			return toDateForKiteHistDate;
		}
		
		protected Date getToDateForKiteHistDateOnCurrentDay_9_45() {
			if (null == toDateForKiteHistDateOnCurrentDay_9_45) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				Date toDate = Calendar.getInstance().getTime();
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
							currentTime.getDayOfMonth(), 9, 45, 3);
					toDate = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					TradewareLogger.saveErrorLog(CLASS_ABSTRACT_DATE_TOOL, METHOD_GET_TO_DATE_FOR_KITE_HIST_DATA_ON_CURRENT_DAY_9_45, e,
							ERROR_TYPE_PARSE_EXCEPTION, ERROR_SEVERIRITY_FATAL);
				}
				toDateForKiteHistDateOnCurrentDay_9_45 = toDate;
			}
			return toDateForKiteHistDateOnCurrentDay_9_45;
		}

		public void setToDateForKiteHistDate(Date toDateForKiteHistDate) {
			this.toDateForKiteHistDate = toDateForKiteHistDate;
		}

		public String getReportDate() {
			SimpleDateFormat dtf = new SimpleDateFormat(Constants.DATE_PATTERN_REPORT_DATE);
			return dtf.format(getTradeDateForReport());
		}
		
		public void setTradeDateForReport(Date tradeDateForReport) {
			this.tradeDateForReport = tradeDateForReport;
		}

		public Date getTradeDateForReport() {
			if (null != tradeDateForReport) {
				return tradeDateForReport;
			}
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
			LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 00, 00, 00));
			if (currentTime.isAfter(tradeStartTime)) {
				if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
					// Do nothing
				} else if (currentTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
					currentTime = currentTime.minusDays(2);
				} else if (currentTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
					currentTime = currentTime.minusDays(1);
				}
			} else if (currentTime.isBefore(tradeStartTime)) {
				if (currentTime.getDayOfWeek() == DayOfWeek.MONDAY) {
					currentTime = currentTime.minusDays(3);
				} else if (currentTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
					currentTime = currentTime.minusDays(2);
				} else /* if (currentTime.getDayOfWeek() == DayOfWeek.SATURDAY) */ {
					currentTime = currentTime.minusDays(1);
				}
			}
			for (LocalDate holiday : getNSEHolidays()) {
				if (holiday.isEqual(currentTime.toLocalDate())) {
					currentTime = currentTime.minusDays(1);
					break;
				}
			}
			// currentTime = currentTime.minusDays(5);
			Date date = Date.from(currentTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_REPORT_DATE);
			String dateAsString = sdf.format(date);
			try {
				tradeDateForReport = sdf.parse(dateAsString);
				tradedCloseTimeBeffore = Timestamp.valueOf(LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
						currentTime.getDayOfMonth(), 15, 19, 59));
				return tradeDateForReport;
			} catch (ParseException e) {
				SystemErrorDataBean exception = new SystemErrorDataBean(CLASS_ABSTRACT_DATE_TOOL,
						METHOD_GET_TRADE_DATE_FOR_REPORT, getExceptionStackTraceAsStrting(e), ERROR_TYPE_PARSE_EXCEPTION,
						ERROR_SEVERIRITY_FATAL, Boolean.TRUE);
				DatabaseHelper.getInstance().saveSystemErrorDataBean(exception);
			}
			return date;
		}
		
	public void applyReportDate(String reportDate) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_REPORT_DATE);
		LocalDate date = LocalDate.parse(reportDate, dtf);
		// ZonedDateTime zdt = date.atStartOfDay(ZoneId.of(Constants.TIME_ZONE));
		Date selectedReportedDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		this.setTradeDateForReport(selectedReportedDate);
		LocalDateTime selectedReportedDateLocalDateTime = selectedReportedDate.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		tradedCloseTimeBeffore = Timestamp.valueOf(LocalDateTime.of(selectedReportedDateLocalDateTime.getYear(),
				selectedReportedDateLocalDateTime.getMonth(), selectedReportedDateLocalDateTime.getDayOfMonth(), 15, 19,
				59));

	}
		
		public Date getTradedCloseTimeBeffore() {
			if (null != tradeDateForReport) {
				return tradedCloseTimeBeffore;
			}
			getTradeDateForReport();
			return getTradedCloseTimeBeffore();
		}
		// TODO - have to move to database and shouls able to configurable.
		protected List<LocalDate> getNSEHolidays() {
			if (null == nseHolidayList || nseHolidayList.isEmpty()) {
				nseHolidayList = new ArrayList<LocalDate>();
				nseHolidayList.add(LocalDate.of(2021, 1, 26));
				nseHolidayList.add(LocalDate.of(2021, 3, 11));
				nseHolidayList.add(LocalDate.of(2021, 3, 29));
				nseHolidayList.add(LocalDate.of(2021, 4, 2));
				nseHolidayList.add(LocalDate.of(2021, 4, 14));
				nseHolidayList.add(LocalDate.of(2021, 4, 21));
				nseHolidayList.add(LocalDate.of(2021, 5, 13));
				nseHolidayList.add(LocalDate.of(2021, 7, 21));
				nseHolidayList.add(LocalDate.of(2021, 8, 19));
				nseHolidayList.add(LocalDate.of(2021, 9, 10));
				nseHolidayList.add(LocalDate.of(2021, 10, 15));
				nseHolidayList.add(LocalDate.of(2021, 11, 5));
				nseHolidayList.add(LocalDate.of(2021, 11, 19));
				
				/*nseHolidayList.add(LocalDate.of(2020, 4, 10));
				nseHolidayList.add(LocalDate.of(2020, 4, 14));
				nseHolidayList.add(LocalDate.of(2020, 5, 1));
				nseHolidayList.add(LocalDate.of(2020, 5, 25));
				nseHolidayList.add(LocalDate.of(2020, 10, 2));
				nseHolidayList.add(LocalDate.of(2020, 11, 16));
				nseHolidayList.add(LocalDate.of(2020, 11, 30));
				nseHolidayList.add(LocalDate.of(2020, 12, 25));*/
			}
			return nseHolidayList;
		}
		private List<LocalDate> nseHolidayList;
		
		
		// TODO - Move to proper place
		public String getExceptionStackTraceAsStrting(Throwable e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			return stringWriter.toString();
		}
		
		
		private int candleNumber = 0;
		public int getCandleNumber() {
			if (0 == candleNumber) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); 
				LocalDateTime startTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 00, 00, 00));
				LocalDateTime closedTime = startTime.plusMinutes(15);
				while(true) {
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
		
		private int queryCandleNumber = 25;

		public int getQueryCandleNumber() {
			return queryCandleNumber;
		}

		public void setQueryCandleNumber(int queryCandleNumber) {
			this.queryCandleNumber = queryCandleNumber;
		}
		//phase 2 end
		
		
		
		
		
		
		
		
		
		//Phase 3 start
		private boolean canPlaceOrders = false;
		private boolean  canUpdate15MinutesData= false;

		public void setCanPlaceOrders(boolean canPlaceOrders) {
			this.canPlaceOrders = canPlaceOrders;
		}
		public boolean canPlaceOrders() {
			if (!canPlaceOrders) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 15, 05, 59));
				LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 00, 00, 00));

				// Still have to consider , NSE holidays from DB table
				if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
					if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
						canPlaceOrders = true;
					}
				}
			}
			return canPlaceOrders;
		}
		
		private boolean canPlaceOptionOrders = false;
		
		public void setCanPlaceOptionOrders(boolean canPlaceOptionOrders) {
			this.canPlaceOptionOrders = canPlaceOptionOrders;
		}
		public boolean canPlaceOptionOrders() {
			if (!canPlaceOptionOrders) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 20, 05, 59));
				LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15,28, 00, 00));

				// Still have to consider , NSE holidays from DB table
				if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
					if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
						canPlaceOptionOrders = true;
					}
				}
			}
			return canPlaceOptionOrders;
		}
		
		/*public boolean isCanUpdate15MinutesData() {
			if (!canUpdate15MinutesData) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 44, 00, 00));//(9, 44, 59, 00));
				LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 29, 59, 59));//(15, 00, 59, 59));
				// Still have to consider , NSE holidays from DB table
				if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
					if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
						canUpdate15MinutesData = true;
					}
				}
			}
			return canUpdate15MinutesData;
		}*/
		
		public boolean isCanUpdate15MinutesData() {
			if (!canUpdate15MinutesData) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 24, 00, 00));//(9, 44, 59, 00));
				LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 29, 59, 59));//(15, 00, 59, 59));
				// Still have to consider , NSE holidays from DB table
				if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
					if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
						canUpdate15MinutesData = true;
					}
				}
			}
			return canUpdate15MinutesData;
		}
		public void setCanUpdate15MinutesData(boolean canUpdate15MinutesData) {
			this.canUpdate15MinutesData = canUpdate15MinutesData;
		}

		// For Heikin-Ashi levels
		// Dates and Amounts util start
		private Boolean isTimeBefore10_15AM = null;

		public void setIsTimeBefore10_15AM(Boolean isTimeBefore10_15AM) {
			this.isTimeBefore10_15AM = isTimeBefore10_15AM;
		}

		/*public boolean isTimeBefore10_15AM() {
			if (null == isTimeBefore10_15AM) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime timeAt10_15 = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(10, 14, 59, 99));
				isTimeBefore10_15AM = currentTime.isBefore(timeAt10_15);

			}
			return isTimeBefore10_15AM;
		}*/
		public boolean isTimeBefore10_15AM() {
			if (null == isTimeBefore10_15AM) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime timeAt10_15 = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(9, 44, 59, 99));
				isTimeBefore10_15AM = currentTime.isBefore(timeAt10_15);

			}
			return isTimeBefore10_15AM;
		}
		//04-21-2021  start - afterSomeSuccess
		private Boolean isTimeBefore03_20PM = null;
		public boolean isTimeBefore03_20PM() {
			if (null == isTimeBefore03_20PM) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime timeAt3_20 = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 19, 59, 59));
				isTimeBefore03_20PM = currentTime.isBefore(timeAt3_20);

			}
			return isTimeBefore03_20PM;
		}
		//04-21-2021  start - afterSomeSuccess
		
	/*public boolean is15MinteDayLevelCandle() {
		if (null == is15MinteDayLevelCandle) {
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
			LocalDateTime startCandleTime_9_45 = LocalDate.now(ZoneId.of(TIME_ZONE))
					.atTime(LocalTime.of(9, 44, 59, 99));
			LocalDateTime startCandleTime_10_00 = LocalDate.now(ZoneId.of(TIME_ZONE))
					.atTime(LocalTime.of(9, 59, 59, 99));
			is15MinteDayLevelCandle = (currentTime.isAfter(startCandleTime_9_45)
					&& currentTime.isBefore(startCandleTime_10_00));

		}
		return is15MinteDayLevelCandle;
	}*/
		
		public boolean is15MinteDayLevelCandle() {
			if (null == is15MinteDayLevelCandle) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
				LocalDateTime startCandleTime_9_45 = LocalDate.now(ZoneId.of(TIME_ZONE))
						.atTime(LocalTime.of(9, 24, 59, 99));
				LocalDateTime startCandleTime_10_00 = LocalDate.now(ZoneId.of(TIME_ZONE))
						.atTime(LocalTime.of(9, 29, 59, 99));
				is15MinteDayLevelCandle = (currentTime.isAfter(startCandleTime_9_45)
						&& currentTime.isBefore(startCandleTime_10_00));

			}
			return is15MinteDayLevelCandle;
		}
	public void setIs15MinteDayLevelCandle(Boolean is15MinteDayLevelCandle) {
		this.is15MinteDayLevelCandle = is15MinteDayLevelCandle;
	}

		public Date getHeikinAshiFromDateForKiteHistData() {
			if (isTimeBefore10_15AM()) {
				if (null == heikinAshiFromDateForKiteHistData) {
					LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
					
					/*if(currentTime.getDayOfWeek() == DayOfWeek.MONDAY) {
						currentTime = currentTime.minusDays(3);
					}  else {
						currentTime = currentTime.minusDays(1);
					}*/
					currentTime = currentTime.minusDays(1);
					LocalDate currentDay = currentTime.toLocalDate();
					while (getNSEHolidays().contains(currentDay) || (currentTime.getDayOfWeek() == DayOfWeek.SUNDAY
							|| currentTime.getDayOfWeek() == DayOfWeek.SATURDAY)) {
						currentTime = currentTime.minusDays(1);
						currentDay = currentDay.minusDays(1);
					}
					
					SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
					Date from = Calendar.getInstance().getTime();
					try {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
						currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
								currentTime.getDayOfMonth(), 9, 15, 0);
						from = formatter.parse(currentTime.format(dtf));
					} catch (ParseException e) {
						TradewareLogger.saveErrorLog(CLASS_ABSTRACT_DATE_TOOL, METHOD_GET_HEIKIN_ASHI_FROM_DATE_FOR_KITE_HIST_DATA,
								e, ERROR_TYPE_PARSE_EXCEPTION, ERROR_SEVERIRITY_FATAL);
					}
					heikinAshiFromDateForKiteHistData = from;
				}
			} else {
				heikinAshiFromDateForKiteHistData = getFromDateForKiteHistDataOnCurrentDay_9_15();
			}
			
			return heikinAshiFromDateForKiteHistData;
		}
		private Date heikinAshiFromDateForKiteHistData;
		public void setHeikinAshiFromDateForKiteHistData(Date heikinAshiFromDateForKiteHistData) {
			this.heikinAshiFromDateForKiteHistData = heikinAshiFromDateForKiteHistData;
		}
		//Phase 3 END
		
		
	// phase 4 start
	public Date getPreviousFromDateForKiteHistHrData() {
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed

		currentTime = currentTime.minusDays(1);
		LocalDate currentDay = currentTime.toLocalDate();
		while (getNSEHolidays().contains(currentDay) || (currentTime.getDayOfWeek() == DayOfWeek.SUNDAY
				|| currentTime.getDayOfWeek() == DayOfWeek.SATURDAY)) {
			currentTime = currentTime.minusDays(1);
			currentDay = currentDay.minusDays(1);
		}

		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
		Date from = Calendar.getInstance().getTime();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
			currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(),
					9, 15, 0);
			from = formatter.parse(currentTime.format(dtf));
		} catch (ParseException e) {
			TradewareLogger.saveErrorLog(CLASS_ABSTRACT_DATE_TOOL, METHOD_GET_PREVIOUS_FROM_DATE_FOR_KITE_HIST_HR_DATA,
					e, ERROR_TYPE_PARSE_EXCEPTION, ERROR_SEVERIRITY_FATAL);
		}

		return from;
	}

	public Date getPreviousToDateForKiteHistHrData() {
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed

		currentTime = currentTime.minusDays(1);
		LocalDate currentDay = currentTime.toLocalDate();
		while (getNSEHolidays().contains(currentDay) || (currentTime.getDayOfWeek() == DayOfWeek.SUNDAY
				|| currentTime.getDayOfWeek() == DayOfWeek.SATURDAY)) {
			currentTime = currentTime.minusDays(1);
			currentDay = currentDay.minusDays(1);
		}

		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
		Date to = Calendar.getInstance().getTime();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
			currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(),
					15, 30, 0);
			to = formatter.parse(currentTime.format(dtf));
		} catch (ParseException e) {
			TradewareLogger.saveErrorLog(CLASS_ABSTRACT_DATE_TOOL, METHOD_GET_PREVIOUS_TO_DATE_FOR_KITE_HIST_HR_DATA, e,
					ERROR_TYPE_PARSE_EXCEPTION, ERROR_SEVERIRITY_FATAL);
		}

		return to;
	}
	
	
	public String getCurrentMonthExpiryDateNR7() {
		LocalDate currentMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of("Asia/Kolkata")).isAfter(
				LocalDate.now(ZoneId.of("Asia/Kolkata")).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of("Asia/Kolkata")).getMonth().equals((LocalDate.now(ZoneId.of("Asia/Kolkata"))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of("Asia/Kolkata")).plusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of("Asia/Kolkata"))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}

		String PATTERN = "dd-MMM-yyyy";
		// ZonedDateTime.of(currentMonthExpiryDate.atStartOfDay(ZoneId.of("IST")),
		// ZoneId.of("IST")).toInstant();
		ZonedDateTime zonedDateTime = currentMonthExpiryDate.atStartOfDay(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
		return zonedDateTime.format(dtf);
	}
	
	public double getchangePercentage(Double lastPrice, Double close) {
		double change_percent = 0d;
		if (null != lastPrice && null != close) {
			change_percent = kiteConnectTool.getRoundupToTwoValue(((lastPrice - close) * 100) / close);
		}
		return change_percent;
	}
		//phase 4 end
	
}
