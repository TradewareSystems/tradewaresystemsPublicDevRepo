package com.tradeware.stockmarket.scheduler;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradeware.clouddatabase.engine.NSEIndiaToolOptionChainTool;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;

//@Configuration
//@EnableScheduling
public class SchedulerConfigTemp {
	
	@Autowired
	private TradewareTool sathvikAshvikTechTraderTool;
	@Autowired 
	private KiteConnectTool kiteConnectTool;
	
	/*@Scheduled(cron = "02 47 9 * * MON-FRI", zone = "IST")
	public void startAndRunBuySellScanner() throws JSONException, IOException, KiteException {
		if(sathvikAshvikTechTraderTool.getBaseDataMap().isEmpty() || sathvikAshvikTechTraderTool.getBaseDataMapAll().isEmpty()) {
			sathvikAshvikTechTraderTool.updateKiteFutureKeyAndInstrumentToken();
		}

		StrategyOrbDataBean bean1 = sathvikAshvikTechTraderTool.getBaseDataMap().get("NSE:INFRATEL");
		StrategyOrbDataBean bean2 = sathvikAshvikTechTraderTool.getBaseDataMap().get(sathvikAshvikTechTraderTool.getKiteFuturekey("INFRATEL"));
		
		sathvikAshvikTechTraderTool.getBaseDataMap().clear();
		sathvikAshvikTechTraderTool.getBaseDataMapAll().clear();
		
		//System.out.println("Token : "+bean1.getInstrumentToken());
		
		sathvikAshvikTechTraderTool.getBaseDataMap().put(bean1.getKiteFutureKey(), bean1);
		sathvikAshvikTechTraderTool.getBaseDataMapAll().put(bean2.getKiteFutureKey(), bean2);
		Date fromdate = sathvikAshvikTechTraderTool.getFromDateForKiteHistDataOnCurrentDay_9_15();
		//System.out.println("FROM_DATE : "+fromdate);
		Date toDate = sathvikAshvikTechTraderTool.getToDateForKiteHistData_WithCurrentTime();
		for (int i=1; i<=10;i++) {
			//System.out.println("FROM_DATE : "+fromdate);
			//System.out.println("TO_DATE : "+toDate);
			HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER).getHistoricalData(
					fromdate,
					toDate,
					String.valueOf(bean1.getInstrumentToken()),
					Constants.KITE_HIST_DATA_15_MINUTES_INTERVAL, false);
			toDate.setMinutes(toDate.getMinutes() + 15);
			
			sathvikAshvikTechTraderTool.volumeStrenthTrend(histData.dataArrayList, bean1);
		}
		
	}*/
	
	@Autowired
	private NSEIndiaToolOptionChainTool nSEIndiaTool;

	
	//@Scheduled(cron = "02 6 16 * * MON-FRI", zone = "IST")
	public void startAndRunBuySellScanner() throws JSONException, IOException, KiteException, InterruptedException {
		
		for (int i=1; i<=5;i++) {
		
		try {
			//sathvikAshvikTechTraderTool.tempVerify(true);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_SCHEDULER_CONFIG, "tempVerify_123", e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		
		if(sathvikAshvikTechTraderTool.getBaseDataMap().isEmpty() || sathvikAshvikTechTraderTool.getBaseDataMapAll().isEmpty()) {
			sathvikAshvikTechTraderTool.updateKiteFutureKeyAndInstrumentToken();
		}
		
	/*optionChainUpdateScannerThread = new Thread(optionChainUpdateScannerRunnable,
			sathvikAshvikTechTraderTool.getCurrentTime());
	optionChainUpdateScannerThread.start();
	*/
		
		nSEIndiaTool.retrieveTopGainerLooserData(null);
		nSEIndiaTool.refreshOIDataForAll(false);
	try {
		//sathvikAshvikTechTraderTool.tempVerify(true);
	} catch (Exception e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_SCHEDULER_CONFIG, "tempVerify_123", e,
				Constants.ERROR_TYPE_EXCEPTION);
	}
	Thread.sleep(30000);
		}
	}
}
