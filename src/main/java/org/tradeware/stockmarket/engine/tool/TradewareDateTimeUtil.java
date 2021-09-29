package org.tradeware.stockmarket.engine.tool;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.tradeware.stockmarket.util.Constants;


public class TradewareDateTimeUtil implements Constants {

	protected TradewareDateTimeUtil() {}
	public String getKiteFuturekey(String symbol) {
		String currExp = getCurrentMonthExpiryDate();
		return (EXCHANGE_FUTUE_NFO + symbol + currExp.substring(INDEX_7, INDEX_9) + currExp.substring(INDEX_2, INDEX_5)
				+ FUTURE_KET_INSTRUMENT).toUpperCase();
	}

	public String getCurrentMonthExpiryDate() {
		LocalDate currentMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of(TIMEZONE_IST)).isAfter(
				LocalDate.now(ZoneId.of(TIMEZONE_IST)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIMEZONE_IST)).getMonth().equals((LocalDate.now(ZoneId.of(TIMEZONE_IST))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).plusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}

		ZonedDateTime zonedDateTime = currentMonthExpiryDate.atStartOfDay(ZoneId.of(TIMEZONE_IST));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
		return zonedDateTime.format(dtf).toUpperCase();
	}

	public String getPreviousMonthExpiryDate() {
		LocalDate previousMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of(TIMEZONE_IST)).isAfter(
				LocalDate.now(ZoneId.of(TIMEZONE_IST)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIMEZONE_IST)).getMonth().equals((LocalDate.now(ZoneId.of(TIMEZONE_IST))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).minusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NSE);
		return previousMonthExpiryDate.format(dtf);
	}

	public String getCurrentMonthFirstDay() {
		LocalDate previousMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of(TIMEZONE_IST)).isAfter(
				LocalDate.now(ZoneId.of(TIMEZONE_IST)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIMEZONE_IST)).getMonth().equals((LocalDate.now(ZoneId.of(TIMEZONE_IST))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			previousMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).minusMonths(ONE_INT)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}
		previousMonthExpiryDate = previousMonthExpiryDate.plusDays(ONE_INT);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NSE);
		return previousMonthExpiryDate.format(dtf);
	}

	public String getPreviousMonthStartDate() {
		LocalDate previousMonthStartDate = null;
		if ((LocalDate.now(ZoneId.of(TIMEZONE_IST)).isAfter(
				LocalDate.now(ZoneId.of(TIMEZONE_IST)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIMEZONE_IST)).getMonth().equals((LocalDate.now(ZoneId.of(TIMEZONE_IST))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			previousMonthStartDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).minusMonths(ONE_INT)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).plusDays(ONE_INT);
		} else {
			previousMonthStartDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).minusMonths(TWO_INT)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).plusDays(ONE_INT);
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NSE);
		return previousMonthStartDate.format(dtf);
	}

	public boolean isTradingTime() {
		boolean isTradingTime = false;
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIMEZONE_IST)); // Trading closed
		LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(NINE, FIFTEEN/* 25 */, FIVE, FIFTY_NINE)); // Trading
		// start
		LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(FIFTEEN, TWENTY_NINE, FIFTY_NINE, FIFTY_NINE)); // closed

		// Still have to consider , NSE holidays from DB table
		if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
				isTradingTime = true;
			}
		}
		return isTradingTime;
	}

	public boolean isTradingTimeJackPot() {
		boolean isTradingTime = false;
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIMEZONE_IST)); // Trading closed
		LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(NINE, TWENTY/* 25 */, FIVE, FIFTY_NINE)); // Trading
		// start
		LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(FIFTEEN, TWENTY_NINE, FIFTY_NINE, FIFTY_NINE)); // closed

		// Still have to consider , NSE holidays from DB table
		if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
				isTradingTime = true;
			}
		}
		return isTradingTime;
	}

	public boolean canOptionOrderPlaceNow() {
		boolean canOrderPlaceNow = false;
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIMEZONE_IST)); // Trading closed
		LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(NINE, FIFTEEN, FIVE_INT, FIFTY_NINE)); // Trading
		// start
		LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(ELEVEN, THIRTY, ONE_INT, ZERO_INT)); // closed

		LocalDateTime tradeBTSTStartTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(FIFTEEN, FIFTEEN, FIVE_INT, FIFTY_NINE)); // Trading
		// start
		LocalDateTime tradeBTSTClosedTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(FIFTEEN, TWENTY_NINE, FIFTY, ZERO_INT)); // closed

		// Still have to consider , NSE holidays from DB table
		if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
				canOrderPlaceNow = true;
			} else if (currentTime.isAfter(tradeBTSTStartTime) && currentTime.isBefore(tradeBTSTClosedTime)) {
				// canOrderPlaceNow = true;
			}
		}
		return true;//canOrderPlaceNow;
	}

	public boolean isTodayTradeClose() {
		LocalDateTime todaysTradeClosedTime = LocalDate.now(ZoneId.of(TIMEZONE_IST))
				.atTime(LocalTime.of(FIFTEEN, THIRTY, FIFTY_NINE, FIFTY_NINE)); // Trading closed, 15, 30, 59 ,59
		return LocalDateTime.now(ZoneId.of(TIMEZONE_IST)).isAfter(todaysTradeClosedTime);
	}
	
	public String getCurrentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_PATTERN);
		return LocalDateTime.now(ZoneId.of(TIMEZONE_IST)).format(dtf);
	}
	
	
	//For New Nse India
	public String getCurrentMonthExpiryDateNewNseSite() {
		LocalDate currentMonthExpiryDate = null;
		if ((LocalDate.now(ZoneId.of(TIMEZONE_IST)).isAfter(
				LocalDate.now(ZoneId.of(TIMEZONE_IST)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
				&& (LocalDate.now(ZoneId.of(TIMEZONE_IST)).getMonth().equals((LocalDate.now(ZoneId.of(TIMEZONE_IST))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).plusMonths(1)
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		} else {
			currentMonthExpiryDate = LocalDate.now(ZoneId.of(TIMEZONE_IST))
					.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

		}

		ZonedDateTime zonedDateTime = currentMonthExpiryDate.atStartOfDay(ZoneId.of(TIMEZONE_IST));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NSE);
		return zonedDateTime.format(dtf).toUpperCase();
	}

	private String previousMonthFromDateStr = null;

	public String getPreviousMonthFromDateNewNseSite() {
		if (null == previousMonthFromDateStr) {
			LocalDate previousMonthFromDate = null;
			if ((LocalDate.now(ZoneId.of(TIMEZONE_IST)).isAfter(
					LocalDate.now(ZoneId.of(TIMEZONE_IST)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
					&& (LocalDate.now(ZoneId.of(TIMEZONE_IST)).getMonth().equals((LocalDate.now(ZoneId.of(TIMEZONE_IST))
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
				previousMonthFromDate = LocalDate.now(ZoneId.of(TIMEZONE_IST))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
			} else {
				previousMonthFromDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).minusMonths(1)
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
			if ((LocalDate.now(ZoneId.of(TIMEZONE_IST)).isAfter(
					LocalDate.now(ZoneId.of(TIMEZONE_IST)).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY))))
					&& (LocalDate.now(ZoneId.of(TIMEZONE_IST)).getMonth().equals((LocalDate.now(ZoneId.of(TIMEZONE_IST))
							.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)).getMonth())))) {
				previousMonthFromDate = LocalDate.now(ZoneId.of(TIMEZONE_IST))
						.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
			} else {
				previousMonthFromDate = LocalDate.now(ZoneId.of(TIMEZONE_IST)).minusMonths(1)
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
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of(TIMEZONE_IST));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
		return zonedDateTime.format(dtf).toUpperCase();
	}
	
	public String getCurrentMonthToDateNewNseSite() {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.minusDays(1);
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of(TIMEZONE_IST));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_NEW_NSE_SITE);
		return zonedDateTime.format(dtf).toUpperCase();
	}
}
