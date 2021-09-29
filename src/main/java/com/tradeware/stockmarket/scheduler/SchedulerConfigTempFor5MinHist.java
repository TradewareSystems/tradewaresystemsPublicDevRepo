package com.tradeware.stockmarket.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData15MinuteStochasticThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData1HourStochasticThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData1MinuteThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData5MinuteStochasticThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData5MinuteThread;
import com.tradeware.stockmarket.tool.thread.TradingDataMapHistData60MinuteThread;
import com.tradeware.stockmarket.util.Constants;

//@Configuration
//@EnableScheduling
public class SchedulerConfigTempFor5MinHist implements Constants {

	@Autowired 
	private TradewareTool tradewareTool;
	
	@Autowired
	private TradingDataMapHistData1MinuteThread tradingDataMapHistData1MinuteThread;
	private Thread histData1MinuteThread;
	
	@Autowired
	private TradingDataMapHistData5MinuteThread tradingDataMapHistData5MinuteThread;
	private Thread histData5MinuteThread;
	
	@Autowired
	private TradingDataMapHistData5MinuteStochasticThread tradingDataMapHistData5MinuteStochasticThread;
	private Thread stochasticHistData5MinuteThread;
	
	@Autowired
	private TradingDataMapHistData15MinuteStochasticThread tradingDataMapHistData15MinuteStochasticThread;
	private Thread stochasticHistData15MinuteThread;

	@Autowired
	private TradingDataMapHistData1HourStochasticThread tradingDataMapHistData1HourStochasticThread;
	private Thread stochasticHistData1HourThread;

	@Autowired
	private TradingDataMapHistData60MinuteThread  histData60MinuteRunnable;
	private Thread histData60MinuteThread;
	
	@Scheduled(cron = "01 17 9 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "01 22 9 * * MON-FRI", zone = "IST")
	// @Scheduled(cron = "01 41 9 * * MON-FRI", zone = "IST")
	public void updatePreviousDayLastHourLevels() {
		TradingDataMapHistData60MinuteThread.fromDate = tradewareTool.getFromDateForKiteHistDataStochastic1Hour_9_00();
		TradingDataMapHistData60MinuteThread.toDate = tradewareTool.getToDateForKiteHistData_15_30();
		histData60MinuteThread = new Thread(histData60MinuteRunnable, tradewareTool.getCurrentTime());
		histData60MinuteThread.start();

		stochasticHistData1HourThread = new Thread(tradingDataMapHistData1HourStochasticThread,
				tradewareTool.getCurrentTime());
		stochasticHistData1HourThread.start();
		
		stochasticHistData15MinuteThread = new Thread(tradingDataMapHistData15MinuteStochasticThread,
				tradewareTool.getCurrentTime());
		stochasticHistData15MinuteThread.start();
	}
	
	//every 5 miinues call for 1 minute and 5 minute data setup
	@Scheduled(cron = "30 04,09,14,19,24,29,34,39,44,49,54,59 9-16 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "30 14,29,44,59 9-16 * * MON-FRI", zone = "IST")
	public void prepare5MinutueCandleAvgData() {
		if (tradewareTool.isTradingTime() && tradewareTool.isCanUpdate15MinutesData() && !tradewareTool.getBaseDataMap().isEmpty()) {
			KiteConnectTool.minute5KiteHistDataMap.clear();
			histData1MinuteThread = new Thread(tradingDataMapHistData1MinuteThread, tradewareTool.getCurrentTime());
			histData1MinuteThread.start();
			
			stochasticHistData5MinuteThread = new Thread(tradingDataMapHistData5MinuteStochasticThread,
					tradewareTool.getCurrentTime());
			stochasticHistData5MinuteThread.start();
			
			histData5MinuteThread = new Thread(tradingDataMapHistData5MinuteThread, tradewareTool.getCurrentTime());
			histData5MinuteThread.start();
		}
	}
	
	@Scheduled(cron = "25 14,29,44,59 9-16 * * MON-FRI", zone = "IST")
	public void prepare15MinutueCandleStochasticData() {
		if (tradewareTool.isCanUpdate15MinutesData() && !tradewareTool.getBaseDataMap().isEmpty()) {
			stochasticHistData15MinuteThread = new Thread(tradingDataMapHistData15MinuteStochasticThread,
					tradewareTool.getCurrentTime());
			stochasticHistData15MinuteThread.start();
		}
	}
	
	
	@Scheduled(cron = "01 59 9-14 * * MON-SAT", zone = "IST")
	public void runEveryHourStochasticCandleData() {
		if (tradewareTool.isCanUpdate15MinutesData() && !tradewareTool.getBaseDataMap().isEmpty()) {
		stochasticHistData1HourThread = new Thread(tradingDataMapHistData1HourStochasticThread,
				tradewareTool.getCurrentTime());
		stochasticHistData1HourThread.start();
		
		tradewareTool.validateOHLCRule();
		}
	}
}
