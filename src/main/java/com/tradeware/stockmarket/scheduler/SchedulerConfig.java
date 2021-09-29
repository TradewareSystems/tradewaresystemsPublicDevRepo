package com.tradeware.stockmarket.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.tradeware.stockmarket.bean.Narrow7DataBean;
import com.tradeware.stockmarket.bean.ProfitLossSummaryDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.scanner.TradewareScanner;
import com.tradeware.stockmarket.tool.ProfitLossSummaryTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData15MinuteThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData1HourStochasticThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData60MinuteThread;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

//@Configuration
//@EnableScheduling
public class SchedulerConfig implements Constants {

	@Autowired
	private TradewareTool tradewareTool;

	@Autowired
	private TradewareScanner scanner;

	@Autowired
	private TradingDataMapHistData15MinuteThread histData15MinuteRunnable;
	private Thread histData15MinuteThread;
	
	
	@Scheduled(cron = "01 11 9 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "01 21 9 * * MON-FRI", zone = "IST")
	// @Scheduled(cron = "01 40 9 * * MON-FRI", zone = "IST")
	public void updateKiteInstrumentTokenAndAverageHLCDataMap() {
		tradewareTool.setThreadScannerStatus(APP_SERVER_STATUS_START_UP_IN_PROGRESS);
		if (tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
			tradewareTool.updateKiteFutureKeyAndInstrumentToken();
			// create empty AverageHistDataBean bean list
			tradewareTool.prepareAverageHLCDataMap();
		}
	}
	
	@Scheduled(cron = "01 23 9 * * MON-FRI", zone = "IST")
	// @Scheduled(cron = "01 42 9 * * MON-FRI", zone = "IST")
	public void updateKiteKeyAndInstrumentToken() {
		if (tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
			tradewareTool.updateKiteFutureKeyAndInstrumentToken();
		}
		tradewareTool.validateOHLCRule();
	}

	@Scheduled(cron = "05 25 9 * * MON-FRI", zone = "IST")
	// @Scheduled(cron = "02 45 9 * * MON-FRI", zone = "IST")
	public void startAndRunBuySellScanner() {
		long inTime = System.currentTimeMillis();

		histData15MinuteThread = new Thread(histData15MinuteRunnable, tradewareTool.getCurrentTime());
		histData15MinuteThread.start();

		scanner.start();
		tradewareTool.setThreadScannerStatus(APP_SERVER_STATUS_SCANNERS_RUNNING);

		long outTime = System.currentTimeMillis();
		TradewareLogger.saveInfoLog(Constants.CLASS_SCHEDULER_CONFIG, Constants.METHOD_START_AND_RUN_BUY_SELL_SCANNER,
				Constants.CALL_ENTRY_EXIT_DURATION + ((outTime - inTime) / 1000d));
	}

	@Scheduled(cron = "05 0/05 9-16 * * MON-SAT", zone = "IST")
	// @Scheduled(cron = "02 0/15 10-16 * * MON-SAT", zone = "IST")
	public void run15MinutueCandleData() {
		long inTime = System.currentTimeMillis();
		// boolean canPlaceOrders = tradewareTool.canPlaceOrders();
		if (tradewareTool.isTradingTime() && tradewareTool.isCanUpdate15MinutesData()) {
			tradewareTool.setScanForBuyOrSell(false);
			tradewareTool.setScanForBuyOrSell15Minute(false);

			try {
				if (/* canPlaceOrders && */ tradewareTool.isCanUpdate15MinutesData()) {
					histData15MinuteThread = new Thread(histData15MinuteRunnable, tradewareTool.getCurrentTime());
					histData15MinuteThread.start();
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_SCHEDULER_CONFIG,
						Constants.METHOD_RUN_15_MINUTUE_CANDLE_DATA, e, Constants.ERROR_TYPE_KITE_EXCEPTION);
			}
			if (TradewareLogger.isDebugLevelEnable) {
				long outTime = System.currentTimeMillis();
				TradewareLogger.saveInfoLog(Constants.CLASS_SCHEDULER_CONFIG,
						Constants.METHOD_RUN_15_MINUTUE_CANDLE_DATA,
						Constants.CALL_ENTRY_EXIT_DURATION + ((outTime - inTime) / 1000d));
			}
		}
	}
	
	@Scheduled(cron = "01 20 15 * * MON-FRI", zone = "IST")
	public void forceExitTrades() {
		tradewareTool.setCanPlaceOrders(false);
		tradewareTool.forceExitTradesAt3_20PM(tradewareTool.getTradingDataMap15Minute());
		tradewareTool.forceExitTradesAt3_20PM(tradewareTool.getTradingDataMap15MinuteDayLevels());
	}
	
	@Scheduled(cron = "30 29 15 * * MON-FRI", zone = "IST")
	public void forceExitTrades_CNS_ORdERS_PlacedAfter3_2() {
		tradewareTool.forceExitTradesAt3_20PM(tradewareTool.getTradingDataMap15Minute());
		tradewareTool.forceExitTradesAt3_20PM(tradewareTool.getTradingDataMap15MinuteDayLevels());
	}
	
	@Scheduled(cron = "00 59 14 * * MON-FRI", zone = "IST")
	public void updateIsUpdate15MinutesData() {
		tradewareTool.setCanPlaceOrders(false);
		tradewareTool.setCanUpdate15MinutesData(false);
	}
	
	@Scheduled(cron = "59 29 15 * * MON-FRI", zone = "IST")
	public void updateIsTradingTimw() {
		tradewareTool.setTradingTime(false);
		tradewareTool.setCanUpdate15MinutesData(false);
		//DatabaseHelper.getInstance().activityKite15MinuteHistDataProcess( ("DAY@SymbolsProcessed:"+TradingDataMapHistData15MinuteThread.totalDayProcessCount));
	
	
		tradewareTool.setThreadScannerStatus(APP_SERVER_STATUS_SCANNERS_COMPLETE);
	}
	
	
	@Autowired
	private ProfitLossSummaryTool profitLossSummaryTool;
	@Scheduled(cron = "59 32 15 * * MON-FRI", zone = "IST")
	public void saveDayProfitLossSummary() {
		/*List<ProfitLossSummaryDataBean> dataBeanList = new ArrayList<ProfitLossSummaryDataBean>(
				profitLossSummaryTool.getProfitLossSummaryMap().values());
		DatabaseHelper.getInstance().saveAllProfitLossSummaryList(dataBeanList);*/
		
		
		List<ProfitLossSummaryDataBean> dataBeanList = new ArrayList<ProfitLossSummaryDataBean>();
		dataBeanList
				.add(profitLossSummaryTool.getProfitLossSummaryMap().get(TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE));
		DatabaseHelper.getInstance().saveAllProfitLossSummaryList(dataBeanList);
		
	}
	
	@Scheduled(cron = "01 15 21 * * MON-FRI", zone = "IST")
	public void prepareNr7Data() {
		if(tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
			tradewareTool.updateKiteFutureKeyAndInstrumentToken();
		}
		
		for (String key : tradewareTool.getBaseDataMap().keySet()) {
			StrategyOrbDataBean orbBean = tradewareTool.getBaseDataMap().get(key);
			Narrow7DataBean nr7Bean = new Narrow7DataBean(orbBean.clone().getLotSize(),
					orbBean.getSymbolId().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
			nr7Bean.clone().setSymbolName(nr7Bean.getSymbolName());
			nr7Bean.setKiteFutureKey(orbBean.getKiteFutureKey());

			tradewareTool.getNr7TradeDataMap().put(key, nr7Bean);
		}

		for (String key : tradewareTool.getBaseDataMapAll().keySet()) {

			StrategyOrbDataBean orbBean = tradewareTool.getBaseDataMapAll().get(key);
			Narrow7DataBean nr7Bean = new Narrow7DataBean(orbBean.clone().getLotSize(),
					orbBean.getSymbolId().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
			nr7Bean.clone().setSymbolName(nr7Bean.getSymbolName());
			nr7Bean.setKiteFutureKey(orbBean.getKiteFutureKey());

			tradewareTool.getNr7TradeDataMap().put(key, nr7Bean);

		}
	}
}
