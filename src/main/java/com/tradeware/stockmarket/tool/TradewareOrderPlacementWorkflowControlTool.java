package com.tradeware.stockmarket.tool;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbMonthlyReportDataBean;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

public abstract class TradewareOrderPlacementWorkflowControlTool extends TradewareOptionTradingTool {
	
	public String getThreadScannerLastRunnngSatusTime() {
		return threadScannerLastRunnngSatusTime;
	}

	public void setThreadScannerLastRunnngSatusTime(String threadScannerLastRunnngSatusTime) {
		this.threadScannerLastRunnngSatusTime = threadScannerLastRunnngSatusTime;
	}
	
	
	// This should be able to alter any time to control trading
		public static boolean todayTradingGoInd = false;
		public static boolean todayTradingNotForceClosedInd = true;
		public static boolean todayTradingAllGoInd = false;
		public static boolean todayTradingAllOhlcGoInd = false;
		public static boolean todayTradingOhlcAndTradeStateMatchGoInd = false; //
		public static boolean todayTradingCustomRuleGoInd = false;
		public static boolean todayTradingGoCustomOHLCMatchInd = false;
	@Autowired 
	protected KiteConnectTool kiteConnectTool;
	
	
	
	
	
	//Phase 2 start
	
	public abstract boolean canPlaceOrders();
	public abstract String getReportDate();
	public abstract int getQueryCandleNumber();
private String threadScannerLastRunnngSatusTime = "00:00:00";

private String threadScannerStatus = "DOWN";

public String getThreadScannerStatus() {
	return threadScannerStatus;
}
public void setThreadScannerStatus(String threadScannerStatus) {
	this.threadScannerStatus = threadScannerStatus;
}

	// For report

		public static String SWITCH_TAG_ON = "ON";
		public static String SWITCH_TAG_OFF = "OFF";
		public static String SWITCH_TAG_START = "<b><span style='color:";
		private StringBuffer tempBuffer = new StringBuffer();

		public static String GREEN = "green", RED = "red";
		public static String TOTAL_PROFIT_LOSS = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:";
		public static String END_TAG1 = "'>", END_TAG2 = "</span></b>";
		public static String PROFIT_LOSS_AFTER_TAXES = "&nbsp;&nbsp;<b> <span style='color:";
		public static String TOTAL_TRADES = "Total Trades : ", SPACE_HTML = "&nbsp;&nbsp;";
		public static String REPORT_TABLE_DATA_LIST_MSG = "reportList";
		public static String PROFIT_LOSS_MSG = "profitLoss";
		public static String TOTAL_TRADES_MSG = "totalTrades";
		public static String MONTHLY_REPORT_MSG = "monthlyReportFor";
		public static String STRATEGY_TITLE_HEADER = "strategy_title";
		public static String TODAY_TRADING_GO_IND_SWITCH = "tradingSwitch";
		public static String TODAY_TRADING_NOT_FORCE_CLOSED_IND_SWITCH = "tradingNotForceClosedSwitch";
		public static String TODAY_TRADING_ALL_GO_IND_SWITCH = "tradingAllSwitch";
		public static String TODAY_TRADING_ALL_OHLC_GO_IND_SWITCH = "tradingAllOhlcSwitch";
		public static String TODAY_TRADING_ALL_OHLC_AND_TRADE_STATE_MATCH_GO_IND_SWITCH = "tradingOhlcTradeStateMatchSwitch";
		public static String TODAY_TRADING_CUSTOM_RULE_GO_IND_SWITCH = "tradingCustomRuleSwitch";
		public static String TODAY_TRADING_CUSTOM_RULE_GO_CUSTOM_OHLC_MATCH = "tradingCustomOHLCMatchSwitch";
				
		private static String REPORT_DATE = "reportDate";	
		private static String QUERY_CANDLE_NUMBER = "queryCandleNumber";
		private static String HREAD_SCANNER_STATUS = "threadScannerStatus";
		private static String THREAD_SCANNER_LAST_RUNNNG_SATUS_TIME = "scannerRunnngSatusTime";

	public ModelAndView prepareHeaderString(List<StrategyOrbDataBean> list, String jsp) {
		int counter = 0;
		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		double totalManualProfitLossAfterTaxes = 0;
		
		double successRatio = 0;
		double profitRatio = 0;
		double lossRatio = 0;
		double profitLossAmtVal = 0;
		for (StrategyOrbDataBean bean : list) {
			totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmtVal() ? bean.getProfitLossAmtVal() : 0);

			totalManualProfitLossAfterTaxes = totalManualProfitLossAfterTaxes
					+ ((null != bean.getManualBookProfitLossAmtVal() && 0 != bean.getManualBookProfitLossAmtVal())
							? bean.getManualBookProfitLossAmtVal()
							: (null != bean.getProfitLossAmtVal() ? bean.getProfitLossAmtVal() : 0));

			profitLossAmtVal = null != bean.getProfitLossAmtVal() ? bean.getProfitLossAmtVal() : 0;
			successRatio = successRatio + ((profitLossAmtVal * 100)/Constants.TARGET_AMOUNT_7500);
			
			profitLossAmtVal = null != bean.getPositiveDirectionProfit() ? bean.getPositiveDirectionProfit() : 0;
			profitRatio = profitRatio + ((profitLossAmtVal * 100)/Constants.TARGET_AMOUNT_7500);
			
			profitLossAmtVal = null != bean.getNegativeDirectionLoss() ? bean.getNegativeDirectionLoss() : 0;
			lossRatio = lossRatio + ((profitLossAmtVal * 100)/Constants.TARGET_AMOUNT_7500);
			counter++;
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss - (counter * 150)).setScale(2, 0).doubleValue();
		totalManualProfitLossAfterTaxes = BigDecimal.valueOf((totalManualProfitLossAfterTaxes - (counter * 150)))
				.setScale(2, 0).doubleValue();

		String profitLoss = TOTAL_PROFIT_LOSS + (totalProfitLoss > 0 ? GREEN : RED) + END_TAG1 + totalProfitLoss
				+ END_TAG2;
		profitLoss = profitLoss + PROFIT_LOSS_AFTER_TAXES + (totalProfitLossAfterTaxes > 0 ? GREEN : RED) + END_TAG1
				+ String.valueOf(totalProfitLossAfterTaxes) + END_TAG2;
		
		//for manual profit loss
		profitLoss = profitLoss + PROFIT_LOSS_AFTER_TAXES + (totalManualProfitLossAfterTaxes >  0 ? GREEN : RED) + END_TAG1
				+ String.valueOf(totalManualProfitLossAfterTaxes) + END_TAG2;
		
		// Success percentage
		String successRatioStr = PROFIT_LOSS_AFTER_TAXES + RED + END_TAG1
				+ String.valueOf(
						new BigDecimal(0 != lossRatio ? (lossRatio / counter) : lossRatio).setScale(2, 0).doubleValue())
				+ END_TAG2;
		successRatioStr = successRatioStr + SPACE_HTML
				+ String.valueOf(new BigDecimal(0 != successRatio ? (successRatio / counter) : successRatio)
						.setScale(2, 0).doubleValue())
				+ END_TAG2;
		successRatioStr = successRatioStr + PROFIT_LOSS_AFTER_TAXES + GREEN + END_TAG1 + String.valueOf(
				new BigDecimal(0 != profitRatio ? (profitRatio / counter) : profitRatio).setScale(2, 0).doubleValue())
				+ END_TAG2;

		String totalTrades = successRatioStr + SPACE_HTML + TOTAL_TRADES + counter + SPACE_HTML;
		ModelAndView mv = prepareHeaderSwitchView(jsp);
		mv.addObject(REPORT_TABLE_DATA_LIST_MSG, list);
		mv.addObject(PROFIT_LOSS_MSG, profitLoss);
		mv.addObject(TOTAL_TRADES_MSG, totalTrades);
		return mv;
	}

	// Monthly start
	public ModelAndView prepareHeaderStringForMonthlyReport(List<StrategyOrbMonthlyReportDataBean> list, String jsp) {
		int counter = 0;
		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		double totalManualProfitLossAfterTaxes = 0;

		double successRatio = 0;
		double profitRatio = 0;
		double lossRatio = 0;
		double profitLossAmtVal = 0;
		for (StrategyOrbMonthlyReportDataBean bean : list) {
			
			if(null != bean.getProfitLossAmtVal()) {
				 bean.setProfitLossAmtVal(new BigDecimal(bean.getProfitLossAmtVal()).setScale(2, 0).doubleValue());
				totalProfitLoss = totalProfitLoss + bean.getProfitLossAmtVal();	
			} 
			if(null != bean.getProfitLossAmtValFinal()) {
				 bean.setProfitLossAmtValFinal(new BigDecimal(bean.getProfitLossAmtValFinal()).setScale(2, 0).doubleValue());
				 totalProfitLossAfterTaxes = totalProfitLossAfterTaxes + bean.getProfitLossAmtValFinal();	
			} 
			if(null != bean.getProfitLossAmtValManalFinal()) {
				 bean.setProfitLossAmtValManalFinal(new BigDecimal(bean.getProfitLossAmtValManalFinal()).setScale(2, 0).doubleValue());
				 totalManualProfitLossAfterTaxes = totalManualProfitLossAfterTaxes + bean.getProfitLossAmtValManalFinal();	
			}
			if (null != bean.getTradeCountPerDay()) {
				counter = counter + bean.getTradeCountPerDay();
			}

			profitLossAmtVal = null != bean.getProfitLossAmtVal() ? bean.getProfitLossAmtVal() : 0;
			successRatio = successRatio + ((profitLossAmtVal * 100) / Constants.TARGET_AMOUNT_7500);

			/*profitLossAmtVal = null != bean.getPositiveDirectionProfit() ? bean.getPositiveDirectionProfit() : 0;
			profitRatio = profitRatio + ((profitLossAmtVal * 100) / Constants.TARGET_AMOUNT_7500);

			profitLossAmtVal = null != bean.getNegativeDirectionLoss() ? bean.getNegativeDirectionLoss() : 0;
			lossRatio = lossRatio + ((profitLossAmtVal * 100) / Constants.TARGET_AMOUNT_7500);*/
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLossAfterTaxes).setScale(2, 0).doubleValue();
		totalManualProfitLossAfterTaxes = BigDecimal.valueOf(totalManualProfitLossAfterTaxes).setScale(2, 0).doubleValue();

		String profitLoss = TOTAL_PROFIT_LOSS + (totalProfitLoss > 0 ? GREEN : RED) + END_TAG1 + totalProfitLoss
				+ END_TAG2;
		profitLoss = profitLoss + PROFIT_LOSS_AFTER_TAXES + (totalProfitLossAfterTaxes > 0 ? GREEN : RED) + END_TAG1
				+ String.valueOf(totalProfitLossAfterTaxes) + END_TAG2;

		// for manual profit loss
		profitLoss = profitLoss + PROFIT_LOSS_AFTER_TAXES + (totalManualProfitLossAfterTaxes > 0 ? GREEN : RED)
				+ END_TAG1 + String.valueOf(totalManualProfitLossAfterTaxes) + END_TAG2;

		// Success percentage
		String successRatioStr = PROFIT_LOSS_AFTER_TAXES + RED + END_TAG1
				+ String.valueOf(
						new BigDecimal(0 != lossRatio ? (lossRatio / counter) : lossRatio).setScale(2, 0).doubleValue())
				+ END_TAG2;
		successRatioStr = successRatioStr + SPACE_HTML
				+ String.valueOf(new BigDecimal(0 != successRatio ? (successRatio / counter) : successRatio)
						.setScale(2, 0).doubleValue())
				+ END_TAG2;
		successRatioStr = successRatioStr + PROFIT_LOSS_AFTER_TAXES + GREEN + END_TAG1 + String.valueOf(
				new BigDecimal(0 != profitRatio ? (profitRatio / counter) : profitRatio).setScale(2, 0).doubleValue())
				+ END_TAG2;

		String totalTrades = successRatioStr + SPACE_HTML + TOTAL_TRADES + counter + SPACE_HTML;
		ModelAndView mv = prepareHeaderSwitchView(jsp);
		mv.addObject(REPORT_TABLE_DATA_LIST_MSG, list);
		mv.addObject(PROFIT_LOSS_MSG, profitLoss);
		mv.addObject(TOTAL_TRADES_MSG, totalTrades);
		mv.addObject(MONTHLY_REPORT_MSG, getTheMonth(tradewareTool.getMonthlyReportEndDate().getMonth()));
		return mv;
	}

	@Autowired
	private TradewareTool tradewareTool;
	private static String[] monthNames;

	public static String getTheMonth(int month) {
		if (null == monthNames) {
			monthNames = new String[12];
			monthNames[0] = "January";
			monthNames[1] = "February";
			monthNames[2] = "March";
			monthNames[3] = "April";
			monthNames[4] = "May";
			monthNames[5] = "June";
			monthNames[6] = "July";
			monthNames[7] = "August";
			monthNames[8] = "September";
			monthNames[9] = "October";
			monthNames[10] = "November";
			monthNames[11] = "December";
		}
		return monthNames[month] + " Report";
	}
	
	// Monthly end
		public ModelAndView prepareHeaderSwitchView(String source) {
			ModelAndView mv = new ModelAndView(source);
			mv.addObject(TODAY_TRADING_GO_IND_SWITCH, getTradingSwitch());
			mv.addObject(TODAY_TRADING_NOT_FORCE_CLOSED_IND_SWITCH, getTodayTradingNotForceClosedeSwitch());
			mv.addObject(TODAY_TRADING_ALL_GO_IND_SWITCH, getTradingAllGoSwitch());
			mv.addObject(TODAY_TRADING_ALL_OHLC_GO_IND_SWITCH, getTradingAllOhlcGoSwitch());
			mv.addObject(TODAY_TRADING_ALL_OHLC_AND_TRADE_STATE_MATCH_GO_IND_SWITCH,
					getTradingAllOhlcAndTradeStateMatchGoSwitch());
			mv.addObject(TODAY_TRADING_CUSTOM_RULE_GO_IND_SWITCH, getTradingCustomRuleGoSwitch());
			mv.addObject(TODAY_TRADING_CUSTOM_RULE_GO_CUSTOM_OHLC_MATCH, getTodayTradingGoCustomOHLCMatchInd());
			
			mv.addObject(THREAD_SCANNER_LAST_RUNNNG_SATUS_TIME,getThreadScannerLastRunnngSatusTime());
			mv.addObject(REPORT_DATE, getReportDate());
			mv.addObject(QUERY_CANDLE_NUMBER, String.valueOf(getQueryCandleNumber()));
			mv.addObject(HREAD_SCANNER_STATUS, threadScannerStatus);

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
		public String getTodayTradingNotForceClosedeSwitch() {
			clearBuffer();
			if (todayTradingNotForceClosedInd) {
				tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
			} else {
				tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
			}
			return tempBuffer.toString();
		}
		public String getTradingAllGoSwitch() {
			clearBuffer();
			if (todayTradingAllGoInd) {
				tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
			} else {
				tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
			}
			return tempBuffer.toString();
		}
		
		public String getTradingAllOhlcGoSwitch() {
			clearBuffer();
			if (todayTradingAllOhlcGoInd) {
				tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
			} else {
				tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
			}
			return tempBuffer.toString();
		}
		public String getTradingAllOhlcAndTradeStateMatchGoSwitch() {
			clearBuffer();
			if (todayTradingOhlcAndTradeStateMatchGoInd) {
				tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
			} else {
				tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
			}
			return tempBuffer.toString();
		}
		public String getTradingCustomRuleGoSwitch() {
			clearBuffer();
			if (todayTradingCustomRuleGoInd) {
				tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
			} else {
				tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
			}
			return tempBuffer.toString();
		}
		public String getTodayTradingGoCustomOHLCMatchInd() {
			clearBuffer();
			if (todayTradingGoCustomOHLCMatchInd) {
				tempBuffer.append(SWITCH_TAG_START).append(GREEN).append(END_TAG1).append(SWITCH_TAG_ON).append(END_TAG2);
			} else {
				tempBuffer.append(SWITCH_TAG_START).append(RED).append(END_TAG1).append(SWITCH_TAG_OFF).append(END_TAG2);
			}
			return tempBuffer.toString();
		}
	//phase 2 end

		
		
		
		
		
		
		
		
		
		
		
		/*// temp Option chain
		public void sendOptionChainTradeMessage(OptionChainDataBean bean) {
			if (null != bean.getOITrend() && bean.getOITrend().startsWith(Constants.STARTS_WITH_STRONG)) {
				if (bean.getOITrend().startsWith(Constants.VERY_STRONG_BUY)) {
					TelegramMessageTool.getInstance()
							.sendTelegramMessageTrade(Constants.OI + bean.getSymbol() + Constants.SPACE
									+ Constants.VERY_STRONG_BUY + Constants.SPACE + bean.getOITrend() + Constants.SPACE
									+ bean.getPriceMoveTrend());
				} else if (bean.getOITrend().startsWith(Constants.VERY_STRONG_SELL)) {
					TelegramMessageTool.getInstance()
							.sendTelegramMessageTrade(bean.getSymbol() + Constants.SPACE + Constants.VERY_STRONG_SELL
									+ Constants.SPACE + bean.getOITrend() + Constants.SPACE + bean.getPriceMoveTrend());
				}
			}
		}*/
		
		
/*		private StringBuffer sb = new StringBuffer();
		public String tempVerify(boolean logInfoIntiDatabase) {
			sb.delete(0, sb.length());
			MemoryMXBean memBean = ManagementFactory.getMemoryMXBean() ;
	        MemoryUsage heapMemoryUsage = memBean.getHeapMemoryUsage();
	        Runtime systemRunTime = Runtime.getRuntime();
			try {
				if (logInfoIntiDatabase) {
					sb.append(getThreadScannerLastRunnngSatusTime());
					sb.append("TradingDataMapSize:" +getTradingDataMap().size()).append(Constants.COMMA_SPACE);
					sb.append("SymbolArraySize:" + kiteConnectTool.getSymbolSetForOHLCInput().size());
					for (String key : getTradingDataMap().keySet()) {
						sb.append(key).append(Constants.COMMA_SPACE);
					}
					TechTraderLogger.saveInfoLog(Constants.CLASS_SCHEDULERCONFIG, "tempVerify-getTradingDataMap",
							sb.toString());
					
					sb.delete(0, sb.length());
					sb.append(getThreadScannerLastRunnngSatusTime());
					sb.append("getTradingDataMap().size() - " +getTradingDataMap().size());
					sb.append("getSymbolArrayForOHLC().size() - " + kiteConnectTool.getSymbolSetForOHLCInput().size());
					for (String key : kiteConnectTool.getSymbolSetForOHLCInput()) {
						sb.append(key).append(Constants.COMMA_SPACE);
					}
					TechTraderLogger.saveInfoLog(Constants.CLASS_SCHEDULERCONFIG, "tempVerify-getSymbolArrayForOHLC",
							sb.toString());
					
				        
				        sb.delete(0, sb.length());
				        sb.append("getMax():").append(heapMemoryUsage.getMax() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
				        sb.append("getCommitted():").append(heapMemoryUsage.getCommitted() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
				        sb.append("getUsed():").append(heapMemoryUsage.getUsed() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
				        sb.append("getInit():").append(heapMemoryUsage.getInit() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
				        
				       // memoryMxBean.getHeapMemoryUsage().getUsed()      <=> runtime.totalMemory() - runtime.freeMemory()
				      //  		memoryMxBean.getHeapMemoryUsage().getCommitted() <=> runtime.totalMemory()
				      //  		memoryMxBean.getHeapMemoryUsage().getMax()       <=> runtime.maxMemory()
				        sb.append("RT-totalMemory").append(systemRunTime.totalMemory() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
				        sb.append("RT-maxMemory").append(systemRunTime.maxMemory() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
				        sb.append("RT-freeMemory").append(systemRunTime.freeMemory() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
				        
				    	TelegramMessageTool.getInstance().sendTelegramMessage(sb.toString());
				        TechTraderLogger.saveInfoLog(Constants.CLASS_SCHEDULERCONFIG, "tempVerify-MemoryDetails",
								sb.toString());
				} else {
					sb.append(getThreadScannerLastRunnngSatusTime());
					sb.append(Constants.BREAK_LINE);
					sb.append("KITE_KEY:").append(StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getAccessToken());
					sb.append(Constants.BREAK_LINE);
					sb.append("TradingDataMapSize:" +getTradingDataMap().size());
					sb.append(Constants.BREAK_LINE);
					sb.append("SymbolArraySize:" + kiteConnectTool.getSymbolSetForOHLCInput().size());
					sb.append(Constants.BREAK_LINE);
					
					sb.append("getMax():").append(heapMemoryUsage.getMax() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
			        sb.append("getCommitted():").append(heapMemoryUsage.getCommitted() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
			        sb.append("getUsed():").append(heapMemoryUsage.getUsed() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
			        sb.append("getInit():").append(heapMemoryUsage.getInit() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
				}
			} catch (Exception e) {
				sb.append(e.getMessage());
				TechTraderLogger.saveFatalErrorLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL, "tempVerify", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return sb.toString();
		}*/
		
}
