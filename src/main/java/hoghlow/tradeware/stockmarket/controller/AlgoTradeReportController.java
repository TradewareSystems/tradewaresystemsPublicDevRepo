package hoghlow.tradeware.stockmarket.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.helper.DatabaseHelper;
import hoghlow.tradeware.stockmarket.tool.HighLowStrategyTool;

//@Controller
public class AlgoTradeReportController {
	// should be in common controller
	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}

	// @Autowired
	private HighLowStrategyTool nseIndiaTool = HighLowStrategyTool.getInstance();

	//@RequestMapping(value = "highLowReportFull", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReport() {
		List<StockDataBean> list = null;//new ArrayList<StockDataBean>(nseIndiaTool.getHighLowMap1200Report().values());

		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getTradedInAt().compareTo(right.getTradedInAt());
			}
		};
		Collections.sort(list, comparator);

		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		int counter = 0;
		for (StockDataBean bean : list) {
			if (Constants.BUY.equals(bean.getTradedState()) || Constants.SELL.equals(bean.getTradedState())
					|| Constants.BUY_EXIT_PROFIT.equals(bean.getTradedState())
					|| Constants.BUY_EXIT_LOSS.equals(bean.getTradedState())
					|| Constants.SELL_EXIT_PROFIT.equals(bean.getTradedState())
					|| Constants.SELL_EXIT_LOSS.equals(bean.getTradedState()))
				totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
			counter++;
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
		String styleColor = totalProfitLoss > 0 ? "green" : "red";
		String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
				+ totalProfitLoss + "</span></b>";
		styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
		profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
				+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
		String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
		ModelAndView mv = getModelAndView("highLowReportFull");
		mv.addObject("highLowList", list);
		mv.addObject("profitLoss", profitLoss);
		mv.addObject("totalTrades", totalTrades);
		return mv;
	}

	//@RequestMapping(value = "highLowReportRunning", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getNarrow7Report() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(nseIndiaTool.getHighLowMap1200().values());

		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getStockName().compareTo(right.getStockName());
			}
		};
		Collections.sort(list, comparator);

		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		int counter = 0;
		for (StockDataBean bean : list) {
			if (Constants.BUY.equals(bean.getTradedState()) || Constants.SELL.equals(bean.getTradedState())
					|| Constants.BUY_EXIT_PROFIT.equals(bean.getTradedState())
					|| Constants.BUY_EXIT_LOSS.equals(bean.getTradedState())
					|| Constants.SELL_EXIT_PROFIT.equals(bean.getTradedState())
					|| Constants.SELL_EXIT_LOSS.equals(bean.getTradedState()))
				totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
			counter++;
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
		String styleColor = totalProfitLoss > 0 ? "green" : "red";
		String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
				+ totalProfitLoss + "</span></b>";
		styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
		profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
				+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
		String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
		ModelAndView mv = getModelAndView("highLowReportRunning");
		mv.addObject("highLowList", list);
		mv.addObject("profitLoss", profitLoss);
		mv.addObject("totalTrades", totalTrades);
		return mv;
	}
	
	
	//Only SELL
	//@RequestMapping(value = "highLowReportFullSell", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportOnlySell() {
		List<StockDataBean> list = new ArrayList<StockDataBean>();
		int counter = 0;
		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		for(StockDataBean bean : list) {
		//for (StockDataBean bean : nseIndiaTool.getHighLowMap1200Report().values()) {
			if (Constants.SELL.equals(bean.getTradedState()) || Constants.SELL_EXIT_PROFIT.equals(bean.getTradedState())
					|| Constants.SELL_EXIT_LOSS.equals(bean.getTradedState())) {
				totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
				counter++;
				list.add(bean);
			}
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
		String styleColor = totalProfitLoss > 0 ? "green" : "red";
		String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
				+ totalProfitLoss + "</span></b>";
		styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
		profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
				+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
		String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
		
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getTradedInAt().compareTo(right.getTradedInAt());
			}
		};
		Collections.sort(list, comparator);
		
		/*ModelAndView mv = getModelAndView("highLowReportSellOnly");
		mv.addObject("highLowListSell", list);
		mv.addObject("profitLossSell", profitLoss);
		mv.addObject("totalTradesSell", totalTrades);*/
		ModelAndView mv = getModelAndView("highLowReportFull");
		mv.addObject("highLowList", list);
		mv.addObject("profitLoss", profitLoss);
		mv.addObject("totalTrades", totalTrades);
		return mv;
	}
	
	//Only SELL + Closable rule
		//@RequestMapping(value = "highLowReportFullSellClosable", method = RequestMethod.GET)
		public @ResponseBody ModelAndView getFullDayReportOnlySellWithClosableRule() {
			List<StockDataBean> list = new ArrayList<StockDataBean>();
			List<StockDataBean> baseList = DatabaseHelper.getInstance().getBuyOrSellOnlyWithClosableRule("SELL");
			int counter = 0;
			double totalProfitLoss = 0;
			double totalProfitLossAfterTaxes = 0;
			for (StockDataBean bean : baseList) {
				/*if ((Constants.SELL.equals(bean.getTradedState()) || Constants.SELL_EXIT_PROFIT.equals(bean.getTradedState())
						|| Constants.SELL_EXIT_LOSS.equals(bean.getTradedState())) && (bean.isCloseRule() || bean.isCloseHighRule1() || bean.isCloseHighRule2())) {*/
					totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
					counter++;
					list.add(bean);
				//}
			}
			totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
			totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
			String styleColor = totalProfitLoss > 0 ? "green" : "red";
			String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
					+ totalProfitLoss + "</span></b>";
			styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
			profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
					+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
			String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
			
			Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
				@Override
				public int compare(StockDataBean left, StockDataBean right) {
					return left.getTradedInAt().compareTo(right.getTradedInAt());
				}
			};
			Collections.sort(list, comparator);
			
			/*ModelAndView mv = getModelAndView("highLowReportSellOnly");
			mv.addObject("highLowListSell", list);
			mv.addObject("profitLossSell", profitLoss);
			mv.addObject("totalTradesSell", totalTrades);*/
			ModelAndView mv = getModelAndView("highLowReportFullSellOnly");
			mv.addObject("highLowList", list);
			mv.addObject("profitLoss", profitLoss);
			mv.addObject("totalTrades", totalTrades);
			return mv;
		}
	//Only buy
	//@RequestMapping(value = "highLowReportFullBuy", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportBuy() {
		List<StockDataBean> list = new ArrayList<StockDataBean>();
		
		int counter = 0;
		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		for(StockDataBean bean : list) {
			//for (StockDataBean bean : nseIndiaTool.getHighLowMap1200Report().values()) {
			if (Constants.BUY.equals(bean.getTradedState()) || Constants.BUY_EXIT_PROFIT.equals(bean.getTradedState())
					|| Constants.BUY_EXIT_LOSS.equals(bean.getTradedState())) {
				totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
				list.add(bean);
				counter++;
			}
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
		String styleColor = totalProfitLoss > 0 ? "green" : "red";
		String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
				+ totalProfitLoss + "</span></b>";
		styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
		profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
				+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
		String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
		
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getTradedInAt().compareTo(right.getTradedInAt());
			}
		};
		Collections.sort(list, comparator);
		
		/*ModelAndView mv = getModelAndView("highLowReportBuyOnly");
		mv.addObject("highLowListBuy", list);
		mv.addObject("profitLossBuy", profitLoss);
		mv.addObject("totalTradesBuy", totalTrades);*/
		ModelAndView mv = getModelAndView("highLowReportFull");
		mv.addObject("highLowList", list);
		mv.addObject("profitLoss", profitLoss);
		mv.addObject("totalTrades", totalTrades);
		return mv;
	}
	
	//Only buy
		//@RequestMapping(value = "highLowReportFullBuyClosable", method = RequestMethod.GET)
		public @ResponseBody ModelAndView getFullDayReportBuyWithClosableRule() {
			List<StockDataBean> list = new ArrayList<StockDataBean>();
			List<StockDataBean> baseList = DatabaseHelper.getInstance().getBuyOrSellOnlyWithClosableRule("BUY");
			int counter = 0;
			double totalProfitLoss = 0;
			double totalProfitLossAfterTaxes = 0;
			for (StockDataBean bean : baseList) {
				/*if ((Constants.BUY.equals(bean.getTradedState()) || Constants.BUY_EXIT_PROFIT.equals(bean.getTradedState())
						|| Constants.BUY_EXIT_LOSS.equals(bean.getTradedState())) && (bean.isCloseRule() || bean.isCloseHighRule1() || bean.isCloseHighRule2())) {*/
					totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
					list.add(bean);
					counter++;
				//}
			}
			totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
			totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
			String styleColor = totalProfitLoss > 0 ? "green" : "red";
			String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
					+ totalProfitLoss + "</span></b>";
			styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
			profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
					+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
			String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
			
			Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
				@Override
				public int compare(StockDataBean left, StockDataBean right) {
					return left.getTradedInAt().compareTo(right.getTradedInAt());
				}
			};
			Collections.sort(list, comparator);
			
			/*ModelAndView mv = getModelAndView("highLowReportBuyOnly");
			mv.addObject("highLowListBuy", list);
			mv.addObject("profitLossBuy", profitLoss);
			mv.addObject("totalTradesBuy", totalTrades);*/
			ModelAndView mv = getModelAndView("highLowReportFullBuyOnly");
			mv.addObject("highLowList", list);
			mv.addObject("profitLoss", profitLoss);
			mv.addObject("totalTrades", totalTrades);
			return mv;
		}
	//Profit only
	//@RequestMapping(value = "highLowReportFullProfit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportProfitOnly() {
		List<StockDataBean> list = new ArrayList<StockDataBean>();
		
		int counter = 0;
		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		for(StockDataBean bean : list) {
			//for (StockDataBean bean : nseIndiaTool.getHighLowMap1200Report().values()) {
			if (Constants.BUY.equals(bean.getTradedState()) || Constants.SELL.equals(bean.getTradedState())
					|| Constants.BUY_EXIT_PROFIT.equals(bean.getTradedState())
					// || Constants.BUY_EXIT_LOSS.equals(bean.getTradableState())
					|| Constants.SELL_EXIT_PROFIT.equals(bean.getTradedState())
			// || Constants.SELL_EXIT_LOSS.equals(bean.getTradableState())
			) {
				totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
				list.add(bean);
				counter++;
			}
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
		String styleColor = totalProfitLoss > 0 ? "green" : "red";
		String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
				+ totalProfitLoss + "</span></b>";
		styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
		profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
				+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
		String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
		
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getTradedInAt().compareTo(right.getTradedInAt());
			}
		};
		Collections.sort(list, comparator);
		/*ModelAndView mv = getModelAndView("highLowReportProfitOnly");
		mv.addObject("highLowListProfit", list);
		mv.addObject("profitLossProfit", profitLoss);
		mv.addObject("totalTradesProfit", totalTrades);*/
		
		ModelAndView mv = getModelAndView("highLowReportFull");
		mv.addObject("highLowList", list);
		mv.addObject("profitLoss", profitLoss);
		mv.addObject("totalTrades", totalTrades);
		return mv;
	}
	//Loss only
	//@RequestMapping(value = "highLowReportFullLoss", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportLossOnly() {
		List<StockDataBean> list = new ArrayList<StockDataBean>();
		
		double totalProfitLoss = 0;
		double totalProfitLossAfterTaxes = 0;
		int counter = 0;
		for(StockDataBean bean : list) {
			//for (StockDataBean bean : nseIndiaTool.getHighLowMap1200Report().values()) {
			if (Constants.BUY.equals(bean.getTradedState()) || Constants.SELL.equals(bean.getTradedState())
			// || Constants.BUY_EXIT_PROFIT.equals(bean.getTradableState())
					|| Constants.BUY_EXIT_LOSS.equals(bean.getTradedState())
					// || Constants.SELL_EXIT_PROFIT.equals(bean.getTradableState())
					|| Constants.SELL_EXIT_LOSS.equals(bean.getTradedState())) {
				totalProfitLoss = totalProfitLoss + (null != bean.getProfitLossAmt() ? bean.getProfitLossAmt() : 0);
				list.add(bean);
				counter++;
			}
		}
		totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
		totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss- (counter * 250)).setScale(2, 0).doubleValue();
		String styleColor = totalProfitLoss > 0 ? "green" : "red";
		String profitLoss = "&nbsp;&nbsp;<b>Total Profit/Loss : <span style='color:" + styleColor + "'>"
				+ totalProfitLoss + "</span></b>";
		styleColor = totalProfitLossAfterTaxes > 0 ? "green" : "red";
		profitLoss = profitLoss + "&nbsp;&nbsp;<b> <span style='color:" + styleColor + "'>"
				+ String.valueOf(totalProfitLossAfterTaxes ) + "</span></b>";
		String totalTrades = "Total Trades : " + counter + "&nbsp;&nbsp;";
		
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getTradedInAt().compareTo(right.getTradedInAt());
			}
		};
		Collections.sort(list, comparator);
		/*ModelAndView mv = getModelAndView("highLowReportLossOnly");
		mv.addObject("highLowListLoss", list);
		mv.addObject("profitLossLoss", profitLoss);
		mv.addObject("totalTradesLoss", totalTrades);*/
		
		ModelAndView mv = getModelAndView("highLowReportFull");
		mv.addObject("highLowList", list);
		mv.addObject("profitLoss", profitLoss);
		mv.addObject("totalTrades", totalTrades);
		return mv;
	}
	
	
}
