package com.tradeware.stockmarket.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.tradeware.stockmarket.bean.AverageHistDataBean;
import com.tradeware.stockmarket.bean.Narrow7DataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

public class AbstractTradewareTool extends AbstractDateTool {

	private ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMap = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	private ConcurrentHashMap<String, StrategyOrbDataBean> baseIndexDataMap = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	private ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMapAll = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	
	private ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap5Minute = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	private ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap10Minute = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	private ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap15Minute = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	
	
	private ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap15MinuteDayLevels = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	
	private ConcurrentHashMap<String, StrategyOrbDataBean> iterableBaseDataMapFor5MiniuteLoop = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	private ConcurrentHashMap<String, StrategyOrbDataBean> iterableBaseDataMapFor10MiniuteLoop = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	private ConcurrentHashMap<String, StrategyOrbDataBean> iterableBaseDataMapFor15MiniuteLoop = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	
	public ConcurrentHashMap<String, AverageHistDataBean> averageHLCDataMap = new ConcurrentHashMap<String, AverageHistDataBean>();

	
	
	
	
	
	
	public ConcurrentHashMap<String, StrategyOrbDataBean> getBaseDataMap() {
		return baseDataMap;
	}
	public void setBaseDataMap(ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMap) {
		this.baseDataMap = baseDataMap;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getBaseIndexDataMap() {
		return baseIndexDataMap;
	}
	public void setBaseIndexDataMap(ConcurrentHashMap<String, StrategyOrbDataBean> baseIndexDataMap) {
		this.baseIndexDataMap = baseIndexDataMap;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getBaseDataMapAll() {
		return baseDataMapAll;
	}
	public void setBaseDataMapAll(ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMapAll) {
		this.baseDataMapAll = baseDataMapAll;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getTradingDataMap5Minute() {
		return tradingDataMap5Minute;
	}
	public void setTradingDataMap5Minute(ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap5Minute) {
		this.tradingDataMap5Minute = tradingDataMap5Minute;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getTradingDataMap10Minute() {
		return tradingDataMap10Minute;
	}
	public void setTradingDataMap10Minute(ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap10Minute) {
		this.tradingDataMap10Minute = tradingDataMap10Minute;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getTradingDataMap15Minute() {
		return tradingDataMap15Minute;
	}
	public void setTradingDataMap15Minute(ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap15Minute) {
		this.tradingDataMap15Minute = tradingDataMap15Minute;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getIterableBaseDataMapFor5MiniuteLoop() {
		return iterableBaseDataMapFor5MiniuteLoop;
	}
	public void setIterableBaseDataMapFor5MiniuteLoop(
			ConcurrentHashMap<String, StrategyOrbDataBean> iterableBaseDataMapFor5MiniuteLoop) {
		this.iterableBaseDataMapFor5MiniuteLoop = iterableBaseDataMapFor5MiniuteLoop;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getIterableBaseDataMapFor10MiniuteLoop() {
		return iterableBaseDataMapFor10MiniuteLoop;
	}
	public void setIterableBaseDataMapFor10MiniuteLoop(
			ConcurrentHashMap<String, StrategyOrbDataBean> iterableBaseDataMapFor10MiniuteLoop) {
		this.iterableBaseDataMapFor10MiniuteLoop = iterableBaseDataMapFor10MiniuteLoop;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getIterableBaseDataMapFor15MiniuteLoop() {
		return iterableBaseDataMapFor15MiniuteLoop;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> getTradingDataMap15MinuteDayLevels() {
		return tradingDataMap15MinuteDayLevels;
	}
	public void setTradingDataMap15MinuteDayLevels(
			ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap15MinuteDayLevels) {
		this.tradingDataMap15MinuteDayLevels = tradingDataMap15MinuteDayLevels;
	}
	public void setIterableBaseDataMapFor15MiniuteLoop(
			ConcurrentHashMap<String, StrategyOrbDataBean> iterableBaseDataMapFor15MiniuteLoop) {
		this.iterableBaseDataMapFor15MiniuteLoop = iterableBaseDataMapFor15MiniuteLoop;
	}

	public ConcurrentHashMap<String, AverageHistDataBean> getAverageHLCDataMap() {
		return averageHLCDataMap;
	}






	//Clean up required start
	private boolean scanForBuyOrSell = true;
	private boolean scanForBuyOrSell5Minute = true;
	private boolean scanForBuyOrSell10Minute = true;
	private boolean scanForBuyOrSell15Minute = true;











	public boolean isScanForBuyOrSell() {
		return scanForBuyOrSell;
	}
	public void setScanForBuyOrSell(boolean scanForBuyOrSell) {
		this.scanForBuyOrSell = scanForBuyOrSell;
	}
	public boolean isScanForBuyOrSell5Minute() {
		return scanForBuyOrSell5Minute;
	}
	public void setScanForBuyOrSell5Minute(boolean scanForBuyOrSell5Minute) {
		this.scanForBuyOrSell5Minute = scanForBuyOrSell5Minute;
	}
	public boolean isScanForBuyOrSell10Minute() {
		return scanForBuyOrSell10Minute;
	}
	public void setScanForBuyOrSell10Minute(boolean scanForBuyOrSell10Minute) {
		this.scanForBuyOrSell10Minute = scanForBuyOrSell10Minute;
	}
	public boolean isScanForBuyOrSell15Minute() {
		return scanForBuyOrSell15Minute;
	}
	public void setScanForBuyOrSell15Minute(boolean scanForBuyOrSell15Minute) {
		this.scanForBuyOrSell15Minute = scanForBuyOrSell15Minute;
	}
	public void refreshScanForBuyOrSellThread() {
		this.scanForBuyOrSell = (this.scanForBuyOrSell5Minute && this.scanForBuyOrSell10Minute
				&& this.scanForBuyOrSell15Minute);
	}
// Clean up required END

	
	
	
	
	//temp code to  only trade BANKNFTY start
	private static List<String> bankNiftyStocks = null;
	public List<String> getBankNiftyStocks () {
		if (null == bankNiftyStocks) {
			bankNiftyStocks = new ArrayList<String>();
			bankNiftyStocks.add("BANKNIFTY");
			bankNiftyStocks.add("NIFTY");
			bankNiftyStocks.add("FINNIFTY");
			bankNiftyStocks.add(Constants.NIFTY_50_FUT);
			bankNiftyStocks.add(Constants.BANKNIFTY_FUT);
			bankNiftyStocks.add(Constants.FINNIFTY_FUT);
			bankNiftyStocks.add("HDFCBANK");
			bankNiftyStocks.add("ICICIBANK");
			bankNiftyStocks.add("SBIN");
			bankNiftyStocks.add("KOTAKBANK");
			bankNiftyStocks.add("AXISBANK");
			bankNiftyStocks.add("INDUSINDBK");
			bankNiftyStocks.add("AUBANK");
			bankNiftyStocks.add("BANDHANBNK");
		}
		return bankNiftyStocks;
	}
	
	public void removeOtherThanBankNiftyStocks() {
		for (String key : baseDataMap.keySet()) {
			if (!getBankNiftyStocks().contains(baseDataMap.get(key).getSymbolName())) {
				baseDataMap.remove(key);
			}
		}

		for (String key : baseDataMapAll.keySet()) {
			if (!getBankNiftyStocks().contains(baseDataMapAll.get(key).getSymbolName())) {
				baseDataMapAll.remove(key);
			}
		}
	}
	
	//temp code to  only trade BANKNFTY end
	
	
	
	
	// One time call from scheduler to set future key and instrument
	public void updateKiteFutureKeyAndInstrumentTokennWithOutKite() {
		List<SymbolDataBean> symbolDataBeans = DatabaseHelper.getInstance()
				.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean.TRUE, Boolean.TRUE, 1);
		kiteConnectTool.updateKiteFutureKeyAndInstrumentTokenWithOutKite(symbolDataBeans, baseDataMap);
		symbolDataBeans = DatabaseHelper.getInstance()
				.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean.TRUE, Boolean.TRUE, 2);
		kiteConnectTool.updateKiteFutureKeyAndInstrumentTokenWithOutKite(symbolDataBeans, baseDataMapAll);

		// baseDataMapAll.putAll(kiteConnectTool.addIndexesToTrade());
		baseDataMapAll.putAll(kiteConnectTool.addIndexesToTrade2WithOutKite());
		
		
		//temp code to  only trade BANKNFTY start
		//removeOtherThanBankNiftyStocks();
		//temp code to  only trade BANKNFTY end
	}
	
	public void updateKiteFutureKeyAndInstrumentToken() {
		List<SymbolDataBean> symbolDataBeans = DatabaseHelper.getInstance()
				.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean.TRUE, Boolean.TRUE, 1);
		kiteConnectTool.updateKiteFutureKeyAndInstrumentToken(symbolDataBeans, baseDataMap);
		symbolDataBeans = DatabaseHelper.getInstance()
				.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean.TRUE, Boolean.TRUE, 2);
		kiteConnectTool.updateKiteFutureKeyAndInstrumentToken(symbolDataBeans, baseDataMapAll);

		// baseDataMapAll.putAll(kiteConnectTool.addIndexesToTrade());
		baseDataMapAll.putAll(kiteConnectTool.addIndexesToTrade2());
	}

	public void updateKiteFutureKeyAndInstrumentTokenForIndex() {
		kiteConnectTool.updateKiteFutureKeyAndInstrumentToken(databaseHelper.findAllValidIndexSymbols(),
				baseIndexDataMap);
	}

	// Phase 4 start
	public void prepareAverageHLCDataMap() {
		if (!getBaseDataMap().isEmpty()) {
			for (StrategyOrbDataBean bean : getBaseDataMap().values()) {
				getAverageHLCDataMap().put(bean.getKiteFutureKey(),
						new AverageHistDataBean(bean.getLotSize(), bean.getSymbolId(), bean.getKiteFutureKey()));
			}
		}
		if (!getBaseDataMapAll().isEmpty()) {
			for (StrategyOrbDataBean bean : getBaseDataMapAll().values()) {
				getAverageHLCDataMap().put(bean.getKiteFutureKey(),
						new AverageHistDataBean(bean.getLotSize(), bean.getSymbolId(), bean.getKiteFutureKey()));
			}
		}
	}
	// Phase 4 end
	
	
	
	private ConcurrentHashMap<String, Narrow7DataBean> nr7TradeDataMap = new ConcurrentHashMap<String,Narrow7DataBean>();

	public ConcurrentHashMap<String, Narrow7DataBean> getNr7TradeDataMap() {
		return nr7TradeDataMap;
	}
}
