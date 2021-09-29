import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.tradeware.stockmarket.util.Constants;

public class Test {

	public static void main(String[] args) {
		
		double  val = 597.35;
		System.out.println((int)((val+100) - (val%100)));
		
		/*System.out.println(getKiteOptionKey("NIFTY", "30-Sep-2021", "17000", "CE")); 
		
		System.out.println(getKiteOptionKeyWeekly("NIFTY", "16-Sep-2021", "17000", "CE")); */
		
		//NFO:NIFTY21Sep17000CE
		
		/*System.out.println(100/48.0);
		System.out.println(isMontlyOrWeeklyExpiry("16-Sep-2021"));
		System.out.println(isMontlyOrWeeklyExpiry("07-Oct-2021"));
		System.out.println(isMontlyOrWeeklyExpiry("30-Sep-2021"));
		System.out.println(isMontlyOrWeeklyExpiry("28-Oct-2021"));*/
		/*System.out.println("Entry....................");
		TwilioSendSmsTool.sendTradeEntrySms("RELIANCE Trade BUY activated @ 2000.00");
System.out.println("Exit.................");*/
		
		/*char char1;
		char char2;
		StringBuilder sb = new StringBuilder();
	    for(char1 = 'a'; char1 <= 'z'; ++char1) {
	    	for(char2 = 'a'; char2 <= 'z'; ++char2) {
	    		sb.append(char1).append(char2);
	    		System.out.print(sb.toString() + " ");
	    		sb.delete(0, sb.length());
	    	}
	      System.out.println("");
	    }
	    */
		/*int n=10;
		for(int i=n; i>n-5;i--) {
			System.out.println(i);
		}*/
		/*double topHigh = 541d;
		double topLow = 532d;
		System.out.println((topHigh / (topLow != 0 ? topLow : 1)) * 100); */
		
		//LocalDate localDate = LocalDate.parse("08-08-2021");
		 /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    LocalDate localDate = LocalDate.parse("08-08-2021", formatter);
		
		localDate = localDate.minusDays(60);
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		System.out.println(zonedDateTime.format(dtf).toUpperCase());*/
		
		System.out.println(Math.abs( 100-110));
		
		Set<Integer> set = new TreeSet<Integer>();
		
		set.add(7);
		set.add(3);
		set.add(5);
		set.add(2);
		System.out.println(set);
	}

	static String TIME_ZONE = "Asia/Kolkata";
	static String DATE_PATTERN_EXP_DATES = "dd-MMM-yyyy";
	public static  Date convertStringToDate(String dateStr, String pattern) {
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
	      LocalDate localDate = LocalDate.parse(dateStr, formatter);
	     return Date.from(localDate.atStartOfDay(ZoneId.of(TIME_ZONE)).toInstant());
	}
	
	
	public static  boolean isMontlyOrWeeklyExpiry(String expiryDateStr) {
		boolean isMontlyExpiry = false;
		Date expiryDate = convertStringToDate(expiryDateStr, DATE_PATTERN_EXP_DATES);
		LocalDate localDate = expiryDate.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate();
		
		LocalDate sameMonthLastThursday = localDate
		.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		LocalDate sameMonthLastWednesday = localDate
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
		LocalDate sameMonthLastTuesday = localDate
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.TUESDAY));
		LocalDate sameMonthLastMonday = localDate
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
		
		isMontlyExpiry =  ((localDate.equals(sameMonthLastThursday) ) || (localDate.equals(sameMonthLastWednesday))
				|| (localDate.equals(sameMonthLastTuesday) ) || (localDate.equals(sameMonthLastMonday)));
		
		return isMontlyExpiry;
		}
	
	public static String getKiteOptionKey(String symbolId, String expiryDate, String strikePrice, String callPutType) {
		return (Constants.FUTURE_KEY_PREFIX_NFO + symbolId + expiryDate.substring(9, 11) + expiryDate.substring(3, 6)
				+ strikePrice + callPutType).toUpperCase();
	}
	
	public static String getKiteOptionKeyWeekly(String symbolId, String expiryDate, String strikePrice, String callPutType) {
		return (Constants.FUTURE_KEY_PREFIX_NFO + symbolId + expiryDate.substring(9, 11) + expiryDate.substring(3, 4)
		+ expiryDate.substring(0, 2) + strikePrice + callPutType).toUpperCase();
	}
	
	//NFO:NIFTY21Sep17000CE
	//  NFO:NIFTY21SEP17000CE

	    //NIFTY21J0713600CE
	//NFO:NIFTY21S1617000CE
}	
