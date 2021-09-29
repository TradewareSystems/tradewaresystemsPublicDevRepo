package com.tradeware.stockmarket.scheduler;

import java.lang.Thread.State;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.tradeware.clouddatabase.engine.NSEIndiaBankNiftyTradeToolNewNseSite;
import com.tradeware.clouddatabase.engine.NSEIndiaToolOptionChainTool;
import com.tradeware.clouddatabase.engine.OptionChainUpdateScannerThread;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.scanner.TradewareOptionStrangleScanner;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;

@Configuration
@EnableScheduling
public class SchedulerConfigOptionChain {

	@Autowired
	private NSEIndiaToolOptionChainTool nSEIndiaTool;
	
	@Autowired
	private NSEIndiaBankNiftyTradeToolNewNseSite bankNiftyTradeTool;

	@Autowired
	private TradewareTool traderTool;
	
	@Autowired
	private OptionChainUpdateScannerThread optionChainUpdateScannerRunnable;
	
	private Thread optionChainUpdateScannerThread;
	
	@Autowired
	private TradewareOptionStrangleScanner tradewareOptionStrangleScanner;

	//@Scheduled(cron = "01 38 10 * * MON-FRI", zone = "IST")
	@Scheduled(cron = "01 17 9 * * MON-FRI", zone = "IST")
	public void updateKiteKeyAndInstrumentToken() {
		
		if(traderTool.getBaseDataMap().isEmpty() || traderTool.getBaseDataMapAll().isEmpty()) {
			bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.NIFTY);
			bankNiftyTradeTool.flterOutNextExpiryDates(Constants.NIFTY);
			traderTool.updateKiteFutureKeyAndInstrumentTokennWithOutKite();
		}
		startTheOptionStrategyscanner();
		//changes for option chain based
		/*sathvikAshvikTechTraderTool.prepareOptionChainDataMap();
		sathvikAshvikTechTraderTool.updateKiteFutureKeyAndInstrumentTokenOptionChainBased();
		sathvikAshvikTechTraderTool.validateOHLCRule();*/
	}

	@Scheduled(cron = "02 0/5 9-16 * * MON-FRI", zone = "IST")
	//@Scheduled(cron = "02 0/2 9-16 * * MON-FRI", zone = "IST")
	public void get5MinutueCandleData() {
		try {
			startTheOptionStrategyscanner();
			boolean isTradingTime = false;
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
			LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE))
					.atTime(LocalTime.of(9, 19, 59, 59));
			LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE))
					.atTime(LocalTime.of(15, 29, 59, 59));

			// Still have to consider , NSE holidays from DB table
			if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
					isTradingTime = true;
				}
			}
			//if (isTradingTime ) {
			//if (isTradingTime && ((currentTime.getMinute() % 15) != 0)) {
			if (isTradingTime) {
				 /* nSEIndiaTool.retrieveTopGainerLooserData(null);
					nSEIndiaTool.refreshOIDataForAll(false);*/
				if(traderTool.getBaseDataMap().isEmpty() || traderTool.getBaseDataMapAll().isEmpty()) {
					bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.NIFTY);
					bankNiftyTradeTool.flterOutNextExpiryDates(Constants.NIFTY);
					traderTool.updateKiteFutureKeyAndInstrumentTokennWithOutKite();
				}
				optionChainUpdateScannerThread = new Thread(optionChainUpdateScannerRunnable,
						traderTool.getCurrentTime());
				optionChainUpdateScannerThread.start();
				traderTool
				.setThreadScannerLastRunnngSatusTime("RUNNING - " + traderTool.getCurrentTime());
				}
			//TODO: Code handle Mother candle logic and OI logic
			/*if (isTradingTime && (currentTime.getMinute() != 0 && currentTime.getMinute()/15 != 0)) {
			  nSEIndiaTool.retrieveTopGainerLooserData(null);
				nSEIndiaTool.refreshOIDataForAll(currentTime.getMinute() == 0);
			}
			if (currentTime.getMinute() == 0) {
				sathvikAshvikTechTraderTool.updateKiteFutureKeyAndInstrumentTokenOptionChainBasedEvertHourCheck();
			}*/
			/*if (isTradingTime) {
				nSEIndiaTool.retrieveTopGainerLooserData(null);
				nSEIndiaTool.refreshOIDataForAll(currentTime.getMinute() == 0);
			}*/
			
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
					"updateKiteKeyAndInstrumentToken", e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	
	private void startTheOptionStrategyscanner() {
		if( State.NEW == tradewareOptionStrangleScanner.getState()) {
			tradewareOptionStrangleScanner.start();
		} else if( State.TERMINATED == tradewareOptionStrangleScanner.getState()) {
			System.err.println("IT GOT TERMINATED IN EARLIER CYCLE");
			tradewareOptionStrangleScanner.start();
		}
	}
}
