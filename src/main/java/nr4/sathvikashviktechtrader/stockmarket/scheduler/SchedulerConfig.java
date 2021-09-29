package nr4.sathvikashviktechtrader.stockmarket.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import nr4.sathvikashviktechtrader.stockmarket.log.NkpAlgoLogger;
import nr4.sathvikashviktechtrader.stockmarket.scanner.NR7andUTScannerThread;
import nr4.sathvikashviktechtrader.stockmarket.tool.NR7AndUTCommonTool;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;

//@Configuration
//@EnableScheduling
public class SchedulerConfig {
	//@Scheduled(cron = "01 36 9 * * MON-FRI", zone = "IST")
	@Scheduled(cron = "01 47 9 * * MON-FRI", zone = "IST")
	public void updateKiteKeyAndInstrumentToken() {
		NR7AndUTCommonTool.getInstance().updateKiteFutureKeyAndInstrumentToken();
		// NR7AndUTCommonTool.getInstance().updateKiteFutureKeyAndInstrumentTokenForNifty50();
	}

	@Scheduled(cron = "25 15 10 * * MON-FRI", zone = "IST") //Original ORB CPR Break out
	public void startAndRunBuySellScanner() {
		NR7andUTScannerThread scanner = new NR7andUTScannerThread();
		scanner.start();
	}
	
	@Scheduled(cron = "03 0/15 10-16 * * MON-FRI", zone = "IST")
	public void get15MinutueCandleData() {
		long inTime = System.currentTimeMillis();
		//refreshOiData();//NR7AndUTCommonTool.setScanForBuyOrSell(false);
		boolean canPlaceOrders = NR7AndUTCommonTool.getInstance().canPlaceOrders();
		try {
			if (canPlaceOrders && NR7AndUTCommonTool.getInstance().canUpdate15MinutesData()) {
				NR7AndUTCommonTool.getInstance().get15MinutueCandleData();
				//NR7AndUTCommonTool.getInstance().get5MinutueCandleDataForNifty50(HighLowStrategyTool.getInstance().getNifty50Bean());
			}
		} catch (Exception e) {

		}
		
		NR7AndUTCommonTool.setScanForBuyOrSell(canPlaceOrders);
		long outTime = System.currentTimeMillis();
		NkpAlgoLogger.saveInfoLog(Constants.CLASS_SCHEDULERCONFIG, Constants.METHOD_GET15MINUTUECANDLEDATA,
				Constants.CALL_ENTRY + " > Duration >  "+((outTime - inTime)/1000d));
	}

	@Scheduled(cron = "01 20 15 * * MON-FRI", zone = "IST")
	public void forceExitTrades() {
		NR7AndUTCommonTool.getInstance().forceExitTradesAt3_20PM();
	}

	@Scheduled(cron = "59 29 15 * * MON-FRI", zone = "IST")
	public void updateIsTradingTimw() {
		NR7AndUTCommonTool.getInstance().setTradingTime(false);
		// NR7AndUTCommonTool.canUpdate5MinutesDate = false;
	}
	
	//@Scheduled(cron = "03 0/15 9-16 * * MON-FRI", zone = "IST")
	public void refreshOiData() {
		/*long inTime = System.currentTimeMillis();
		try {
			ConcurrentHashMap<String, StockDataBean> dataMap = NR7AndUTCommonTool.getInstance().getBaseDataMap();
			dataMap = NR7AndUTCommonTool.getInstance().updateDayOHLC_OI(dataMap);
			dataMap = NR7AndUTCommonTool.getInstance().updateDayOHLC_OI_String(dataMap);
			NR7AndUTCommonTool.getInstance().setBaseDataMap(dataMap);
		} catch (Exception e) {
			NkpAlgoLogger.printWithNewLine("Exceptioon at refreshOiData()----");
			e.printStackTrace();
		}
		long outTime = System.currentTimeMillis();
		NkpAlgoLogger.printWithNewLine("refreshOiData() Duration >  " + ((outTime - inTime) / 1000d));*/
	}
}
