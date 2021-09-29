package org.tradeware.stockmarket.schedulers;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.googlesheettool.GoogleSheetUtil;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.engine.telegramtool.TelegramMessageTool;
import org.tradeware.stockmarket.engine.tool.AlgoLevelUpdaterTool;
import org.tradeware.stockmarket.engine.tool.FreeCallDataTool;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.scanner.BuyOrSellStocksScannerThread_V2;
import org.tradeware.stockmarket.scanner.BuySellFutureScannerThead;
import org.tradeware.stockmarket.scanner.BuySellOptionChainRuleScannerThead;
import org.tradeware.stockmarket.util.Constants;
import org.tradeware.stockmarket.util.StockUtil;


//@Configuration
//@EnableScheduling
public class TraderwareSchedulerConfig {
	
	/**
	 * Every trading day by 8:30 AM start the NSEToosInia component to retrieve
	 * historical data and prepare Trade ware data set up.
	 **/
	@Scheduled(cron = "01 45 8 * * MON-FRI", zone = "IST")
	public void setupTradewareDataSetup() {
		// First kill the old instance and clean up all properties
		// NSEIndiaTool.killNSEIndiaToolInstance();

		// Create a fresh instance
		try {// Create a fresh instance
			NSEIndiaTool.getInstance();
			System.out.println(Constants.NSE_TOOL_PROCESS_SUCCESSFUL);
		} catch (Exception e) {
			System.out.println(Constants.FATAL_ERROR + Constants.ERROR_ON_DATA_SETUP);
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "01 00 9 * * MON-FRI", zone = "IST")
	public void prepareRandom20FreeCallData() {
		try {
			List<String> freeCallList = FreeCallDataTool.getInstance().prepareRandom20StockFreeCall();
			GoogleSheetUtil.getInstance().publishFreeCallDataInitial(freeCallList);
		} catch (Exception e) {
			System.out.println(Constants.FATAL_ERROR + Constants.ERROR_ON_FREE_CALL_DATA_SETUP);
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "01 05 9 * * MON-FRI", zone = "IST")
	public void createUserKiteNkpalgoSession() {
		try {
			//NSEIndiaTool.getInstance().createUserKiteNkpalgoSession();
			//GoogleSheetUtil.getInstance().createUserKiteSession();
		} catch (Exception e) {
			System.out.println(Constants.FATAL_ERROR + Constants.ERROR_ON_USER_SESSION_SETUP);
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "15 15 9 * * MON-FRI", zone = "IST")
	public void startAutoTradingScanners() {
		try {
			// created and start Option call trading thread scanner
			Thread scan = new Thread(new BuyOrSellStocksScannerThread_V2(
					StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)));
			// scan.start();
			System.out.println(Constants.OPTION_TRADE_SCANNER_PROCSS_SUCCESSFUL
					+ TradewareTraderUtil.getInstance().getCurrentTime());

			// created and start Future call trading thread scanner
			Thread scanFuturesThread = new Thread(
					new BuySellFutureScannerThead(StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)));
			//scanFuturesThread.start();
			System.out.println(Constants.FUTURE_TRADE_SCANNER_PROCSS_SUCCESSFUL
					+ TradewareTraderUtil.getInstance().getCurrentTime());

			// created and start Narrow 7 call trading thread scanner
			/*Thread narrow7Thread = new Thread(
					new Narrow7StockScannerThread(StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)));*/
			// narrow7Thread.run();
			System.out.println(Constants.NORROW7_TRADE_SCANNER_PROCSS_SUCCESSFUL
					+ TradewareTraderUtil.getInstance().getCurrentTime());
			// created and start Universal strategy call trading thread scanner
			// created and start Stronger/weaker call trading thread scanner
			// created and start ORB15 call trading thread scanner
		} catch (Exception e) {
			System.out.println(Constants.FATAL_ERROR + Constants.ERROR_ON_SCANNER_SETUP);
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "00 20 9 * * MON-FRI", zone = "IST")
	public void upatePositionalAndIntradayLevels() {
		try {
			if (null != KiteConnectTool.getInstance()) {
				Set<String> symbols = NSEIndiaTool.getInstance().getPositionalResultDataMap().keySet();
				AlgoLevelUpdaterTool.getInstance()
						.updateIntradyLevels(KiteConnectTool.getInstance().getKiteOHLCQuotes(symbols));
			}
		} catch (Exception e) {
			System.out.println(Constants.FATAL_ERROR + Constants.ERROR_ON_TRADE_LEVEL_SETUP);
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "00 0/14 9-16 * * MON-FRI", zone = "IST")
	public void sendAppStatus() {
		TelegramMessageTool.getInstance()
				.sendTelegramMessage("***MvcWeb(Future calls) last run:" + TelegramMessageTool.lastRuntime.toString());
		//BuySellOptionChainRuleScannerThead.getInstance().run();
	}
	
	/**
	 * TODO : update historical data from alpha vantage source
	 * @Scheduled(cron = "00 0/5 9-15 * * MON-FRI", zone = "IST")
	public void upateNarrow7Ltp() {
		if (null != KiteConnectTool.getInstance()) {
			Set<String> symbols = NSEIndiaTool.getInstance().getNarrow7Map().keySet();
			FreeCallDataTool.getInstance().updateNarrow7Levels(KiteConnectTool.getInstance().getKiteQuotes(symbols));
		}
	}*/
	
	/*@Scheduled(cron = "00 0/1 9-16 * * MON-FRI", zone = "IST")
	public void upateNarrow7Ltp() {
		if (TradewareTraderUtil.getInstance().isTradingTime()) {
			Narrow7StockScannerThread.getThreadInstance().runDummy();
		}
	}*/
	
	/*@Scheduled(cron = "0 45 9 * * MON-FRI", zone = "IST")
	public void orbStrategyLevels() {
		Thread scan = new Thread( new ORBJackpotScannerThread(
				StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)));
		//scan.start();
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<ORB JACKPOT THREAD STARTED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
	}*/
}
