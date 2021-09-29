package com.tradeware.stockmarket.tool.thread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.traderules.TradewareOrderPlacementRuleTool;
import com.tradeware.stockmarket.util.Constants;

public abstract class TradingDataMapHistDataThread implements Runnable, Constants {

	protected String threadName;
	protected String dummyKey = "";
	protected int processedCounter = 0;
	protected Set<String> processedSymbols = new HashSet<String>();
	
	public static boolean run5MinuteThreadInSchedule;
	public static boolean run10MinuteThreadInSchedule;

	@Autowired
	protected TradewareTool tradewareTool;

	@Autowired
	protected KiteConnectTool kiteConnectTool;
	
	@Autowired
	protected TradewareOrderPlacementRuleTool tradewareOrderPlacementRuleTool;

	protected String getStrengthTradeBUY(double closeMinusLow2, double highMinusClose2) {
		String trade = NA;
		if (highMinusClose2 == 0 || ((3 * highMinusClose2) <= closeMinusLow2)) {
			trade = BUY3;
		} else if ((2 * highMinusClose2) <= closeMinusLow2) {
			trade = BUY2;
		} else if (highMinusClose2 < closeMinusLow2) {
			trade = BUY;
		}
		return trade + COMMA + closeMinusLow2 + COMMA + highMinusClose2;
	}

	protected String getStrengthTradeSELL(double closeMinusLow2, double highMinusClose2) {
		String trade = NA;
		if (closeMinusLow2 == 0 || ((3 * closeMinusLow2) <= highMinusClose2)) {
			trade = SEL3;
		} else if ((2 * closeMinusLow2) <= highMinusClose2) {
			trade = SEL2;
		} else if (closeMinusLow2 < highMinusClose2) {
			trade = SELL;
		}
		return trade + COMMA + closeMinusLow2 + COMMA + highMinusClose2;
	}
	
	//phase 2 start

	protected static ConcurrentHashMap<String, StrategyOrbDataBean> minute1HistDataMap = new ConcurrentHashMap<String, StrategyOrbDataBean>();
	protected static ConcurrentHashMap<String, StrategyOrbDataBean> minute5HistDataMap = new ConcurrentHashMap<String, StrategyOrbDataBean>();

	

	
	//phase 2 end
}
