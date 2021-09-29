package com.tradeware.stockmarket.scheduler;

import java.lang.Thread.State;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.scanner.TradewareOptionIntradayStraddleScanner;
import com.tradeware.stockmarket.scanner.TradewareOptionStrangleScanner;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Configuration
@EnableScheduling
public class SchedulerConfigOptionStrategy {

	@Autowired
	private TradewareOptionStrangleScanner tradewareOptionStrangleScanner;
	@Autowired
	protected TradewareTool tradewareTool;
	@Autowired
	protected DatabaseHelper databaseHelper;
	/*@Autowired
	private TradewareOptionIntradayStraddleScanner optionIntradayStraddleScanner;*/

	/*@Scheduled(cron = "01 00 10 * * MON-FRI", zone = "IST")
	public void updateKiteKeyAndInstrumentToken() {
		if (State.NEW == optionIntradayStraddleScanner.getState()) {
			optionIntradayStraddleScanner.start();
		}
	}*/
	
	/*@Scheduled(cron = "02 0/2 10-16 * * MON-FRI", zone = "IST")
	public void startTheOptionStrategyscanner2() {
		if (State.NEW == optionIntradayStraddleScanner.getState()) {
			optionIntradayStraddleScanner.start();
		}
	}*/

	@Scheduled(cron = "02 0/2 9-16 * * MON-FRI", zone = "IST")
	public void startTheOptionStrategyscanner() {
		if (State.NEW == tradewareOptionStrangleScanner.getState()) {
			tradewareOptionStrangleScanner.start();
		}
	}
	@Scheduled(cron = "01 00 10 * * MON-FRI", zone = "IST")
	public void algoTradeEntryForIntradayForStraddleEntry() {
		boolean cannAddAnIntradatStraddle = true;
		List<OptionLiveTradeMainDataBean> optionTradingDataList = databaseHelper
				.findByTradePositionAndTradeStrategy(Constants.TRADE_POSIITON_OPEN, Constants.OPTION_STRATEGY_STRADDLE);
		if (!optionTradingDataList.isEmpty()) {
			for (OptionLiveTradeMainDataBean dbBean : optionTradingDataList) {
				cannAddAnIntradatStraddle = !(Constants.OPTION_STRATEGY_STRADDLE.equals(dbBean.getTradeStrategy())
						&& (null != dbBean.getIntradayTradeInd() && dbBean.getIntradayTradeInd()));
			}
		}
		if (cannAddAnIntradatStraddle) {
			// Take intraday straddle entry at 10:00 AM
			tradewareTool.algoTradeEntryForIntradayForStraddleEntry();
		}
	}
	
	@Scheduled(cron = "02 45 09 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "02 22 10 * * MON-FRI", zone = "IST")
	public void algoTradeEntryForIntradayForStrangleleEntry() {
		boolean cannAddAnIntradatStrangle = true;
		List<OptionLiveTradeMainDataBean> optionTradingDataList = databaseHelper
				.findByTradePositionAndTradeStrategy(Constants.TRADE_POSIITON_OPEN, Constants.OPTION_STRATEGY_STRANGLE);
		if (!optionTradingDataList.isEmpty()) {
			for (OptionLiveTradeMainDataBean dbBean : optionTradingDataList) {
				cannAddAnIntradatStrangle = !(Constants.OPTION_STRATEGY_STRANGLE.equals(dbBean.getTradeStrategy())
						&& (null != dbBean.getIntradayTradeInd() && dbBean.getIntradayTradeInd()));
			}
		}
		if (cannAddAnIntradatStrangle) {
			// Take intraday straddle entry at 10:00 AM
			tradewareTool.algoTradeEntryForIntradayForStrangleleEntry();
		}
	}
	
	
	@Scheduled(cron = "02 10 03 * * MON-FRI", zone = "IST")
	public void algoTradeExit() {
		ConcurrentHashMap<String, OptionLiveTradeMainDataBean> map = tradewareTool.getTradingStrangleOptionDataMap();
		for (OptionLiveTradeMainDataBean mainBean : map.values()) {
			if (null != mainBean.getIntradayTradeInd() && mainBean.getIntradayTradeInd()) {
				tradewareOptionStrangleScanner.bookWholeTradeToClose(mainBean, map);
			}
		}
	}
}
