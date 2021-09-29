package nr4.sathvikashviktechtrader.stockmarket.tool;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import nr4.sathvikashviktechtrader.stockmarket.bean.StockDataBean;
import nr4.sathvikashviktechtrader.stockmarket.util.StockUtil;

public class AlgoOrderPlacementWorkflowSwitchTool {
	public static boolean todayTradingGoInd = false; // This should be able to alter any time to control trading
	
	public boolean canPlaceOrderBasedAlgoRules(StockDataBean bean) {
		boolean canPlaceOrder = false;

		if (todayTradingGoInd) {
		}
		bean.setTempCustomTradingRuleInd(canPlaceOrder);
		return todayTradingGoInd && canPlaceOrder;
	}
	
	public static String SWITCH_TAG_ON = "ON";
	public static String SWITCH_TAG_OFF = "OFF";
	public static String SWITCH_TAG_START = "<b><span style='color:";
	private StringBuffer tempBuffer = new StringBuffer();

	private static String GREEN = "green", RED = "red";
	private static String TOTAL_PROFIT_LOSS = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:";
	private static String END_TAG1 = "'>", END_TAG2 = "</span></b>";
	private static String PROFIT_LOSS_AFTER_TAXES = "&nbsp;&nbsp;<b> <span style='color:";
	private static String TOTAL_TRADES = "Total Trades : ", SPACE_HTML = "&nbsp;&nbsp;";
	public static String HIGH_LOW_LIST_MSG = "reportList";
	private static String PROFIT_LOSS_MSG = "profitLoss";
	private static String TOTAL_TRADES_MSG = "totalTrades";
	private static String TRADING_SWITCH = "tradingSwitch";
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
		return mv;
	}

	public ModelAndView prepareHeaderSwitchView(String source) {
		ModelAndView mv = new ModelAndView(source);
		mv.addObject(TRADING_SWITCH, getTradingSwitch());
		mv.addObject(REPORT_DATE, NR7AndUTCommonTool.getInstance().getReportDate());
		mv.addObject(THREAD_SCANNER_LAST_RUNNNG_SATUS_TIME, StockUtil.getThreadScannerLastRunnngSatusTime());
		return mv;
	}

	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}

	public void clearBuffer() {
		if (tempBuffer.length() > 0) {
			tempBuffer.delete(0, tempBuffer.length());
		}
	}

	public StringBuffer getTempBuffer() {
		return tempBuffer;
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
}
