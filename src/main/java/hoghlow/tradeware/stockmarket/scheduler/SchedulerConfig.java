package hoghlow.tradeware.stockmarket.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import hoghlow.tradeware.stockmarket.log.NkpAlgoLogger;
import hoghlow.tradeware.stockmarket.scanner.HighLowScannerThread;
import hoghlow.tradeware.stockmarket.tool.HighLowStrategyTool;

//@Configuration
//@EnableScheduling
public class SchedulerConfig {
	@Scheduled(cron = "01 12 9 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "01 05 9 * * MON-FRI", zone = "IST")
	public void updateKiteKeyAndInstrumentToken() {
		HighLowStrategyTool.getInstance().updateKiteFutureKeyAndInstrumentToken();
		HighLowStrategyTool.getInstance().updateKiteFutureKeyAndInstrumentTokenForNifty50();
		
		NkpAlgoLogger.printWithNewLine(String.valueOf(HighLowStrategyTool.getInstance().getFromDateForKiteHistData_9_15()));
	}

	@Scheduled(cron = "25 00 10 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "02 30 9 * * MON-FRI", zone = "IST")
	public void find5MinCandleDataOn_9_25_To_30_OpenEqLowOrOpenEqHighTrades() {
		HighLowStrategyTool.getInstance().get5MinutueCandleData();
		HighLowStrategyTool.getInstance().get5MinutueCandleDataForNifty50(HighLowStrategyTool.getInstance().getNifty50Bean());
		
		HighLowScannerThread scanner = new HighLowScannerThread();
		scanner.start();
	}
	
	@Scheduled(cron = "01 20 15 * * MON-FRI", zone = "IST")
	public void forceExitTrades() {
		HighLowStrategyTool.getInstance().forceExitTradesAt3_20PM();
	}

	//@Scheduled(cron = "02 0/5 9-16 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "25 0/15 9-16 * * MON-FRI", zone = "IST")
	@Scheduled(cron = "25 0/20 9-16 * * MON-FRI", zone = "IST")
	public void get5MinutueCandleData() {
		long inTime = System.currentTimeMillis();
		//HighLowStrategyTool.scanForBuyOrSell = false;
		boolean canPlaceOrders = HighLowStrategyTool.getInstance().canPlaceOrders();
		try {
			if (canPlaceOrders && HighLowStrategyTool.getInstance().canUpdate5MinutesDate()) {
				HighLowStrategyTool.getInstance().get5MinutueCandleData();
				HighLowStrategyTool.getInstance().get5MinutueCandleDataForNifty50(HighLowStrategyTool.getInstance().getNifty50Bean());
			}
		} catch (Exception e) {

		}
		HighLowStrategyTool.scanForBuyOrSell = canPlaceOrders;
		long outTime = System.currentTimeMillis();
		NkpAlgoLogger.printWithNewLine("get5MinutueCandleData() Duration >  "+((outTime - inTime)/1000d));
	}
	
	@Scheduled(cron = "59 29 15 * * MON-FRI", zone = "IST")
	public void updateIsTradingTimw() {
		HighLowStrategyTool.isTradingTime = false;
		HighLowStrategyTool.canUpdate5MinutesDate = false;
	}

	// @Scheduled(cron = "01 01 10 * * MON-FRI", zone = "IST")
	public void createUserKiteNkpalgoSession() {
		try {
			HighLowStrategyTool.getInstance().createUserKiteNkpalgoSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
