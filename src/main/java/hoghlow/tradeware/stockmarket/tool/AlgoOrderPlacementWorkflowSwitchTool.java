package hoghlow.tradeware.stockmarket.tool;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.util.Constants;
import hoghlow.tradeware.stockmarket.util.StockUtil;

public abstract class AlgoOrderPlacementWorkflowSwitchTool implements Constants {
	
	private String threadScannerLastRunnngSatusTime = "Scanner thread not runnng...";

	public String getThreadScannerLastRunnngSatusTime() {
		return threadScannerLastRunnngSatusTime;
	}

	public void setThreadScannerLastRunnngSatusTime(String threadScannerLastRunnngSatusTime) {
		this.threadScannerLastRunnngSatusTime = threadScannerLastRunnngSatusTime;
	}
	
	public static boolean todayTradingGoInd = false; // This should be able to alter any time to control trading
	public static boolean todayBuyTradingGoInd = false;// true;//false;
	public static boolean todaySellTradingGoInd = false;// true;

	public static boolean todayAllSellOnlyWithCloseRuleTradingGoInd = false;
	public static boolean todayAllBuyOnlyWithCloseRuleTradingGoInd = false;

	public static boolean nr4Switch = false;
	public static boolean buyCprCloseSwitch = false;
	public static boolean buyNotCprSwitch = false;
	public static boolean sellNotCprCloseSwitch = false;

	public static boolean todaySpecialRule = false;
	public static boolean matchNifty50BuyRuleSwitch = false;
	public static boolean matchNifty50SellRuleSwitch = false;

	public boolean canPlaceOrderBasedAlgoRules(StockDataBean bean) {
		boolean canPlaceOrder = false;
		// if (todayTradingGoInd && canPlaceOrderBasedAlgoSubRules(bean) &&
		// canPlaceOrders ) {
		if (todayTradingGoInd && canPlaceOrderBasedAlgoSubRules_2(bean) && AbstractAlgoTraderTool.canPlaceOrders) {
			canPlaceOrder = true;
		}
		return canPlaceOrder;
	}

	public boolean canPlaceOrderBasedAlgoSubRules(StockDataBean bean) {
		boolean algoSubRules = false;

		if (todaySellTradingGoInd && SELL.equals(bean.getTradableState()) && bean.isCloseRule()
				&& bean.isCloseHighRule1() /* && bean.isOpenHighLowHARule() */
				&& !bean.isSmallCandle() /* && bean.isOppositeHighLowRule() */) {
			algoSubRules = true;
		} else if (todayBuyTradingGoInd && BUY.equals(bean.getTradableState()) && bean.isCloseRule()
				&& bean.isCloseHighRule1() && bean.isOpenHighLowRule()/* && bean.isOpenHighLowHARule() */
				&& !bean.isSmallCandle() /* && bean.isOppositeHighLowRule() */) {
			algoSubRules = true;
		}
		return algoSubRules;
	}

	public boolean canPlaceOrderBasedAlgoSubRules_2(StockDataBean bean) {
		boolean algoSubRules = false;

		if (todaySpecialRule) {
			return canPlaceOrderBasedAlgoTodaySpecialRule(bean);
		}
		
		if (matchNifty50BuyRuleSwitch || matchNifty50SellRuleSwitch) {
			return canPlaceOrderBasedAlgoMatchNifty50Rule(bean) ;
		}

		if (nr4Switch) {
			return canPlaceOrderBasedAlgoSubRules_4_NR4(bean);
		}
		if (buyCprCloseSwitch || buyNotCprSwitch || sellNotCprCloseSwitch) {
			return canPlaceOrderBasedAlgoSubRules_5_CPR_Close(bean);
		}
		//
		if (todayAllSellOnlyWithCloseRuleTradingGoInd || todayAllBuyOnlyWithCloseRuleTradingGoInd) {
			return canPlaceOrderBasedAlgoSubRules_3(bean);
		}
		//

		if (todaySellTradingGoInd && SELL.equals(bean.getTradableState()) && bean.isCloseRule()
				&& bean.isCloseHighRule1() && bean.isOpenHighLowHARule()
				&& !bean.isSmallCandle() /* && bean.isOppositeHighLowRule() */
				/* && LAST_CANDLE_RED.equals(bean.getLastCandle()) */
				&& (LEVEL2_SELL.equals(bean.getVolumeTrend()) || LEVEL1_SELL.equals(bean.getVolumeTrend())
						|| LEVEL_NA.equals(bean.getVolumeTrend()))
				&& (bean.getStrengthFactor() > 1.0d && bean.getDay1VolStrengthFactor() > 1.0d
						&& bean.getDay2VolStrengthFactor() > 1.0d)) {
			algoSubRules = true;
		} else if (todayBuyTradingGoInd && BUY.equals(bean.getTradableState()) && bean.isCloseRule()
				&& bean.isCloseHighRule1() /* && bean.isOpenHighLowRule() */ && bean.isOpenHighLowHARule()
				&& !bean.isSmallCandle() /* && bean.isOppositeHighLowRule() */
				/* && LAST_CANDLE_GREEN.equals(bean.getLastCandle()) */
				&& (LEVEL2_BUY.equals(bean.getVolumeTrend()) || LEVEL1_BUY.equals(bean.getVolumeTrend()))
				&& (bean.getStrengthFactor() > 1.0d && bean.getDay1VolStrengthFactor() > 1.0d
						&& bean.getDay2VolStrengthFactor() > 1.0d)) {
			algoSubRules = true;
		}
		return algoSubRules;
	}

	// All seLl only with close rule
	public boolean canPlaceOrderBasedAlgoSubRules_3(StockDataBean bean) {
		boolean algoSubRules = false;
		if (todayAllSellOnlyWithCloseRuleTradingGoInd && SELL.equals(bean.getTradableState()) && bean.isCloseRule()
				&& (bean.isCloseHighRule1() || bean.isCloseHighRule2()) && !bean.isSmallCandle()
				&& (bean.getStrengthFactor() > 1.0d)) {
			// && (bean.isCloseRule() || bean.isCloseHighRule1() || bean.isCloseHighRule2())
			// && !bean.isSmallCandle()&& (bean.getStrengthFactor() > 2.0d)) {
			algoSubRules = true;
		} else if (todayAllBuyOnlyWithCloseRuleTradingGoInd && BUY.equals(bean.getTradableState()) && bean.isCloseRule()
				&& (bean.isCloseHighRule1() || bean.isCloseHighRule2()) && !bean.isSmallCandle()
				&& (bean.getStrengthFactor() > 1.0d)) {
			// && (bean.isCloseRule() ||bean.isCloseHighRule1() || bean.isCloseHighRule2())
			// && !bean.isSmallCandle()&& (bean.getStrengthFactor() > 2.0d)) {
			algoSubRules = true;
		}
		return algoSubRules;
	}

	public boolean canPlaceOrderBasedAlgoSubRules_4_NR4(StockDataBean bean) {
		boolean algoSubRules = false;
		if (nr4Switch && bean.isNr4Rule() && !bean.isSmallCandle() && ((BUY.equals(bean.getTradableState())
				&& ((null != bean.getMinute5CPR() && bean.getMinute5CPR().doubleValue() > 0)
						&& (null != bean.getMinute15CPR() && bean.getMinute15CPR().doubleValue() > 0)))
				|| (SELL.equals(bean.getTradableState())
						&& ((null != bean.getMinute5CPR() && bean.getMinute5CPR().doubleValue() < 0)
								|| (null != bean.getMinute15CPR() && bean.getMinute15CPR().doubleValue() < 0))))) {
			algoSubRules = true;
		}

		return algoSubRules;
	}

	public boolean canPlaceOrderBasedAlgoSubRules_5_CPR_Close(StockDataBean bean) {
		boolean algoSubRules = false;
		if (buyNotCprSwitch && BUY.equals(bean.getTradableState()) && !bean.isSmallCandle()
				&& ((null != bean.getMinute5CPR() && bean.getMinute5CPR().doubleValue() <= 0)
						&& (null != bean.getMinute15CPR() && bean.getMinute15CPR().doubleValue() <= 0))) {
			algoSubRules = true;
		}
		if (buyCprCloseSwitch && BUY.equals(bean.getTradableState()) && !bean.isSmallCandle()
				&& ((null != bean.getMinute5CPR() && bean.getMinute5CPR().doubleValue() > 0)
						&& (null != bean.getMinute15CPR() && bean.getMinute15CPR().doubleValue() > 0))
				&& ((bean.isCloseRule() && bean.isCloseHighRule1() && bean.isCloseHighRule2())
						&& (bean.getStrengthFactor() > 1.0d && bean.getDay1VolStrengthFactor() > 1.0d
								&& bean.getDay2VolStrengthFactor() > 1.0d))) {
			algoSubRules = true;
		}
		if (sellNotCprCloseSwitch && SELL.equals(bean.getTradableState()) && !bean.isSmallCandle()
				&& ((null != bean.getMinute5CPR() && bean.getMinute5CPR().doubleValue() <= 0)
						&& (null != bean.getMinute15CPR() && bean.getMinute15CPR().doubleValue() < 0))
				&& ((bean.isCloseRule() && bean.isCloseHighRule1() && bean.isCloseHighRule2())
						&& (bean.getStrengthFactor() > 1.0d && bean.getDay1VolStrengthFactor() > 1.0d
								&& bean.getDay2VolStrengthFactor() > 1.0d))) {
			algoSubRules = true;
		}
		return algoSubRules;
	}

	public boolean canPlaceOrderBasedAlgoTodaySpecialRule(StockDataBean bean) {
		boolean algoSubRules = false;
		if (todaySpecialRule && bean.getStockName().startsWith("ICICIBANK") && !bean.isSmallCandle()
				&& bean.isCloseRule() && bean.isCloseHighRule1() && bean.isOpenHighLowHARule()
				&& (bean.getStrenthTradableState() != null
						&& bean.getStrenthTradableState().equals(bean.getStrenthTradableStateNifty()))) {
			algoSubRules = true;
		}
		return algoSubRules;
	}

	public boolean canPlaceOrderBasedAlgoMatchNifty50Rule(StockDataBean bean) {
		boolean algoSubRules = false;
		if (((matchNifty50BuyRuleSwitch && BUY.equals(bean.getTradableState()))
				|| (matchNifty50SellRuleSwitch && SELL.equals(bean.getTradableState())) && bean.isCloseRule()
						&& bean.isCloseHighRule1())) {
			algoSubRules = true;
		}
		return algoSubRules;
	}

	// For report

	public static ModelAndView currentModelAndView;
	public static List<StockDataBean> currentModelAndViewReportList;

	public static String SWITCH_TAG_ON = "ON";
	public static String SWITCH_TAG_OFF = "OFF";
	public static String SWITCH_TAG_START = "<b><span style='color:";
	private StringBuffer tempBuffer = new StringBuffer();

	public static String GREEN = "green", RED = "red";
	public static String TOTAL_PROFIT_LOSS = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:";
	public static String END_TAG1 = "'>", END_TAG2 = "</span></b>";
	public static String PROFIT_LOSS_AFTER_TAXES = "&nbsp;&nbsp;<b> <span style='color:";
	public static String TOTAL_TRADES = "Total Trades : ", SPACE_HTML = "&nbsp;&nbsp;";
	public static String HIGH_LOW_LIST_MSG = "highLowList";
	public static String PROFIT_LOSS_MSG = "profitLoss";
	public static String TOTAL_TRADES_MSG = "totalTrades";
	public static String TRADING_SWITCH = "tradingSwitch";
	public static String SELL_SWITCH = "sellSwitch";
	public static String BUY_SWITCH = "buySwitch";
	public static String SELL_ONLY_CLOSE_SWITCH = "sellOnlyWithCloseSwitch";
	public static String BUY_ONLY_CLOSE_SWITCH = "buyOnlyWithCloseSwitch";
	public static String NR4_SWITCH = "nr4Switch";
	public static String BUY_CPR_CLOSE_SWITCH = "buyCprCloseSwitch";
	public static String BUY_NOT_CPR_SWITCH = "buyNotCprSwitch";
	public static String SELL_NOT_CPR_CLOSE_SWITCH = "sellNotCprCloseSwitch";
	public static String TODAY_SPECIFIC_RULE_SWITCH = "todaySpecialRuleSwitch";
	public static String MATCH_NIFTY_50_BUY_RULE = "matchNifty50BuyRuleSwitch";
	public static String MATCH_NIFTY_50_SELL_RULE = "matchNifty50SellRuleSwitch";
	public static String STRATEGY_TITLE_HEADER = "strategy_title";
	public static String STRATEGY_TITLE_HEADER_NR4 = "NR4 Tadres";
	public static String STRATEGY_TITLE_HEADER_CPR_BUY = "CPR BUY Trades";
	public static String STRATEGY_TITLE_HEADER_CPR_SELL = "CPR SELL Trades";
	public static String STRATEGY_TITLE_HEADER_NOT_CPR_BUY = "NOT CPR BUY Trades";
	public static String STRATEGY_TITLE_HEADER_NOT_CPR_SELL = "NOT CPR SELL Trades";
	public static String STRATEGY_TITLE_HEADER_CPR_CLOSE_BUY = "CPR-Close BUY Trades";
	public static String STRATEGY_TITLE_HEADER_NOT_CPR_CLOSE_SELL = "NOT CPR-Close SELL Trades";
	private static String REPORT_DATE = "reportDate";	
	private static String THREAD_SCANNER_LAST_RUNNNG_SATUS_TIME = "scannerRunnngSatusTime";
	
	public ModelAndView prepareHeaderString(List<StockDataBean> list, String jsp) {
		int counter = 0;
		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		for (StockDataBean bean : list) {
			totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
			counter++;
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss - (counter * 150)).setScale(2, 0).doubleValue();

		String profitLoss = TOTAL_PROFIT_LOSS + (totalProfitLoss > 0 ? GREEN : RED) + END_TAG1 + totalProfitLoss
				+ END_TAG2;
		profitLoss = profitLoss + PROFIT_LOSS_AFTER_TAXES + (totalProfitLossAfterTaxes > 0 ? GREEN : RED) + END_TAG1
				+ String.valueOf(totalProfitLossAfterTaxes) + END_TAG2;

		String totalTrades = TOTAL_TRADES + counter + SPACE_HTML;
		ModelAndView mv = prepareHeaderSwitchView(jsp);
		mv.addObject(HIGH_LOW_LIST_MSG, list);
		mv.addObject(PROFIT_LOSS_MSG, profitLoss);
		mv.addObject(TOTAL_TRADES_MSG, totalTrades);
		currentModelAndView = mv;
		currentModelAndViewReportList = list;
		return mv;
	}

	public ModelAndView prepareHeaderSwitchView(String source) {
		ModelAndView mv = new ModelAndView(source);
		mv.addObject(TRADING_SWITCH, HighLowStrategyTool.getInstance().getTradingSwitch());
		mv.addObject(BUY_SWITCH, HighLowStrategyTool.getInstance().getBuySwitch());
		mv.addObject(SELL_SWITCH, HighLowStrategyTool.getInstance().getSellSwitch());
		mv.addObject(SELL_ONLY_CLOSE_SWITCH, HighLowStrategyTool.getInstance().getSellOnlyWithCloseSwitch());
		mv.addObject(BUY_ONLY_CLOSE_SWITCH, HighLowStrategyTool.getInstance().getBuyOnlyWithCloseSwitch());
		mv.addObject(NR4_SWITCH, HighLowStrategyTool.getInstance().getNr4Switch());
		mv.addObject(BUY_NOT_CPR_SWITCH, HighLowStrategyTool.getInstance().getBuyNotCprSwitch());
		mv.addObject(BUY_CPR_CLOSE_SWITCH, HighLowStrategyTool.getInstance().getBuyCprCloseSwitch());
		mv.addObject(SELL_NOT_CPR_CLOSE_SWITCH, HighLowStrategyTool.getInstance().getSellNotCprCloseSwitch());
		mv.addObject(TODAY_SPECIFIC_RULE_SWITCH, HighLowStrategyTool.getInstance().getTodaySpecialRuleSwitch());
		mv.addObject(MATCH_NIFTY_50_BUY_RULE, HighLowStrategyTool.getInstance().getMatchNifty50BuyRuleSwitch());
		mv.addObject(MATCH_NIFTY_50_SELL_RULE, HighLowStrategyTool.getInstance().getMatchNifty50SellRuleSwitch());
		mv.addObject(REPORT_DATE, HighLowStrategyTool.getInstance().getReportDate());
		
		mv.addObject(THREAD_SCANNER_LAST_RUNNNG_SATUS_TIME,getThreadScannerLastRunnngSatusTime());
		
		return mv;
	}

	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}

	private void clearBuffer() {
		if (tempBuffer.length() > 0) {
			tempBuffer.delete(0, tempBuffer.length());
		}
	}

	public String getTradingSwitch() {
		clearBuffer();
		if (todayTradingGoInd) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getSellSwitch() {
		clearBuffer();
		if (todaySellTradingGoInd) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getBuySwitch() {
		clearBuffer();
		if (todayBuyTradingGoInd) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getSellOnlyWithCloseSwitch() {
		clearBuffer();
		if (todayAllSellOnlyWithCloseRuleTradingGoInd) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getBuyOnlyWithCloseSwitch() {
		clearBuffer();
		if (todayAllBuyOnlyWithCloseRuleTradingGoInd) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getNr4Switch() {
		clearBuffer();
		if (nr4Switch) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getBuyNotCprSwitch() {
		clearBuffer();
		if (buyNotCprSwitch) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getBuyCprCloseSwitch() {
		clearBuffer();
		if (buyCprCloseSwitch) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getSellNotCprCloseSwitch() {
		clearBuffer();
		if (sellNotCprCloseSwitch) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}

	public String getTodaySpecialRuleSwitch() {
		clearBuffer();
		if (todaySpecialRule) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}
	

	public String getMatchNifty50BuyRuleSwitch() {
		clearBuffer();
		if (matchNifty50BuyRuleSwitch) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}
	

	public String getMatchNifty50SellRuleSwitch() {
		clearBuffer();
		if (matchNifty50SellRuleSwitch) {
			tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
		} else {
			tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
		}
		return tempBuffer.toString();
	}
}
