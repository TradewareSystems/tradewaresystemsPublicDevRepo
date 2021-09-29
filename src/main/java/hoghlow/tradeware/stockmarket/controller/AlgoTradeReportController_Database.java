package hoghlow.tradeware.stockmarket.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.helper.DatabaseHelper;
import hoghlow.tradeware.stockmarket.log.NkpAlgoLogger;
import hoghlow.tradeware.stockmarket.tool.HighLowStrategyTool;

@Controller
public class AlgoTradeReportController_Database {
	// should be in common controller
	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}

	@RequestMapping(value = "highLowReportFull", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReport() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllTradesByTradedDate());
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}
	/*@RequestMapping(value = "highLowReportFullByDate", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReport(@RequestParam(value = "reportDate") String reportDate) {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllTradesByTradedDate(reportDate));
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}*/
	
	@RequestMapping(value = "highLowReportFullMatchNifty50", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllTradesByTradedDateAndTrendMactchWithNifty50() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllTradesByTradedDateAndTrendMactchWithNifty50());
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}
	
	@RequestMapping(value = "highLowReportFullMatchNifty50WithCloseRule", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRule() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRule());
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}
	
	@RequestMapping(value = "highLowReportRunning", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getRunningReport() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().getRunningTrades());
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportRunning");
	}

	// Only SELL
	@RequestMapping(value = "highLowReportFullSell", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportOnlySell() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().getBuyOrSellOnlyTrades(Constants.SELL));
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}

	// Only buy
	@RequestMapping(value = "highLowReportFullBuy", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportBuy() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().getBuyOrSellOnlyTrades(Constants.BUY));
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}

	// Profit only
	@RequestMapping(value = "highLowReportFullProfit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportProfitOnly() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().getProfitOrLossOnlyTrades(Constants.PROFIT));
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull"); // highLowReportProfitOnly
	}

	// Loss only
	@RequestMapping(value = "highLowReportFullLoss", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportLossOnly() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().getProfitOrLossOnlyTrades(Constants.LOSS));
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull"); // highLowReportLossOnly
	}

	// Only SELL + Closable rule
	@RequestMapping(value = "highLowReportFullSellClosable", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportOnlySellWithClosableRule() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().getBuyOrSellOnlyWithClosableRule(Constants.SELL));
		// return prepareHeaderString(list, "highLowReportFullSellOnly");
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}

	// Only buy
	@RequestMapping(value = "highLowReportFullBuyClosable", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFullDayReportBuyWithClosableRule() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(
				DatabaseHelper.getInstance().getBuyOrSellOnlyWithClosableRule(Constants.BUY));
		// return prepareHeaderString(list, "highLowReportFullBuyOnly");
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFull");
	}

	// Custom queries
	@RequestMapping(value = "customSellOnly", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRuleSell() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByCustomRuleSell());
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFullSellOnlyCustom");
	}

	@RequestMapping(value = "customBuyOnly", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRuleBuy() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByCustomRuleBuy());
		return HighLowStrategyTool.getInstance().prepareHeaderString(list, "highLowReportFullBuyOnlyCustom");
	}
	
	@RequestMapping(value = "nr4Trades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByNr4RuleInd() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByNr4RuleIndAndReportDate());
		ModelAndView mv = HighLowStrategyTool.getInstance().prepareHeaderString(list, "addittionalReport");
		mv.addObject(HighLowStrategyTool.STRATEGY_TITLE_HEADER, HighLowStrategyTool.STRATEGY_TITLE_HEADER_NR4);
		return mv;
	}
	@RequestMapping(value = "buyCprTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByBuyTradedStateAndCPRGreaterThanZero() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByTradedStateAndCPRGreaterThanZero(Constants.BUY));
		ModelAndView mv = HighLowStrategyTool.getInstance().prepareHeaderString(list, "addittionalReport");
		mv.addObject(HighLowStrategyTool.STRATEGY_TITLE_HEADER, HighLowStrategyTool.STRATEGY_TITLE_HEADER_CPR_BUY);
		return mv;
	}
	@RequestMapping(value = "sellCprTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllBySellTradedStateAndCPRGreaterThanZero() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByTradedStateAndCPRGreaterThanZero(Constants.SELL));
		ModelAndView mv = HighLowStrategyTool.getInstance().prepareHeaderString(list, "addittionalReport");
		mv.addObject(HighLowStrategyTool.STRATEGY_TITLE_HEADER, HighLowStrategyTool.STRATEGY_TITLE_HEADER_CPR_SELL);
		return mv;
	}
	@RequestMapping(value = "buyNotCprTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByBuyTradedStateAndCPRLessThanOREqualToZero() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByTradedStateAndCPRLessThanOREqualToZero(Constants.BUY));
		ModelAndView mv = HighLowStrategyTool.getInstance().prepareHeaderString(list, "addittionalReport");
		mv.addObject(HighLowStrategyTool.STRATEGY_TITLE_HEADER, HighLowStrategyTool.STRATEGY_TITLE_HEADER_NOT_CPR_BUY);
		return mv;
	}
	@RequestMapping(value = "sellNotCprTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllBySellTradedStateAndCPRLessThanOREqualToZero() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByTradedStateAndCPRLessThanOREqualToZero(Constants.SELL));
		ModelAndView mv = HighLowStrategyTool.getInstance().prepareHeaderString(list, "addittionalReport");
		mv.addObject(HighLowStrategyTool.STRATEGY_TITLE_HEADER, HighLowStrategyTool.STRATEGY_TITLE_HEADER_NOT_CPR_SELL);
		return mv;
	}
	@RequestMapping(value = "buyCprCloseTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByTradedStateAndCPRGreaterThanZeroAndCloseRules() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByTradedStateAndCPRGreaterThanZeroAndCloseRules(Constants.BUY));
		ModelAndView mv = HighLowStrategyTool.getInstance().prepareHeaderString(list, "addittionalReport");
		mv.addObject(HighLowStrategyTool.STRATEGY_TITLE_HEADER, HighLowStrategyTool.STRATEGY_TITLE_HEADER_CPR_CLOSE_BUY);
		return mv;
	}
	@RequestMapping(value = "sellNotCprCloseTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByTradedStateAndCPRLessThanOREqualToZeroAndCloseRules() {
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllByTradedStateAndCPRLessThanOREqualToZeroAndCloseRules(Constants.SELL));
		ModelAndView mv = HighLowStrategyTool.getInstance().prepareHeaderString(list, "addittionalReport");
		mv.addObject(HighLowStrategyTool.STRATEGY_TITLE_HEADER, HighLowStrategyTool.STRATEGY_TITLE_HEADER_NOT_CPR_CLOSE_SELL);
		return mv;
	}
	
	
	
	@RequestMapping(value = "/stockDetails", method = RequestMethod.GET)
	public ResponseEntity<String> getStockDetails(@RequestParam(value = "stockId") String stockId) { 
		String details = stockId;
		NkpAlgoLogger.printWithNewLine("stockId ------------> "+stockId);
		List<StockDataBean> list = new ArrayList<StockDataBean>(DatabaseHelper.getInstance().findAllTradesByTradedDate());
		for (StockDataBean bean : list) {
			if (stockId.equals(bean.getStockId())) {
				details = details +bean.toString();
				break;
			}
		}
		return new ResponseEntity<String>(details, HttpStatus.OK);
	}
	
	@RequestMapping(value = "applyReportDate", method = RequestMethod.GET)
	public  @ResponseBody ModelAndView applyReportDate(@RequestParam(value = "reportDate") String reportDate) {
		if (null != reportDate && !Constants.EMPTY_STRING.equals(reportDate)) {
			LocalDate dateInstance = LocalDate.parse(reportDate);
			Date tradeDay = Date.from(dateInstance.atStartOfDay().atZone(ZoneId.of(Constants.TIME_ZONE))
				      .toInstant());
			HighLowStrategyTool.getInstance().setTradeDateForReport(tradeDay);
		}	
		return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
	}
	
	@RequestMapping(value = "/getReportOrderByTime", method = RequestMethod.GET)
	public  @ResponseBody ModelAndView sortReportByTime() {
		if (HighLowStrategyTool.currentModelAndView != null && HighLowStrategyTool.currentModelAndViewReportList !=null) {
			
			Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
				@Override
				public int compare(StockDataBean left, StockDataBean right) {
					if (null == left.getTradedInAt()) {
						return 0;
					} else if (null == right.getTradedInAt()) {
						return 1;
					} else {
						return left.getTradedInAt().compareTo(right.getTradedInAt());
					}
				}
			};
			Collections.sort(HighLowStrategyTool.currentModelAndViewReportList, comparator);
			HighLowStrategyTool.currentModelAndView.addObject(HighLowStrategyTool.HIGH_LOW_LIST_MSG, HighLowStrategyTool.currentModelAndViewReportList);
			return HighLowStrategyTool.currentModelAndView;
		}
		return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
	}
	
	// Maintain setting lookups
		@RequestMapping(value = "/tradingSwitch", method = RequestMethod.GET)
		public ModelAndView updateTradingSwitch() {
			HighLowStrategyTool.todayTradingGoInd = !(HighLowStrategyTool.todayTradingGoInd);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}

		@RequestMapping(value = "/buySwitch", method = RequestMethod.GET)
		public ModelAndView updateBuyTradingSwitch() {
			HighLowStrategyTool.todayBuyTradingGoInd = !(HighLowStrategyTool.todayBuyTradingGoInd);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}

		@RequestMapping(value = "/sellSwitch", method = RequestMethod.GET)
		public ModelAndView updateSellTradingSwitch() {
			HighLowStrategyTool.todaySellTradingGoInd = !(HighLowStrategyTool.todaySellTradingGoInd);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/sellOnlyCloseSwitch", method = RequestMethod.GET)
		public ModelAndView updateSellOnlyWithCloseTradingSwitch() {
			HighLowStrategyTool.todayAllSellOnlyWithCloseRuleTradingGoInd = !(HighLowStrategyTool.todayAllSellOnlyWithCloseRuleTradingGoInd);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/buyOnlyCloseSwitch", method = RequestMethod.GET)
		public ModelAndView updateBuyOnlyWithCloseTradingSwitch() {
			HighLowStrategyTool.todayAllBuyOnlyWithCloseRuleTradingGoInd = !(HighLowStrategyTool.todayAllBuyOnlyWithCloseRuleTradingGoInd);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/nr4Switch", method = RequestMethod.GET)
		public ModelAndView updateNr4TradingSwitch() {
			HighLowStrategyTool.nr4Switch = !(HighLowStrategyTool.nr4Switch);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/buyNotCprSwitch", method = RequestMethod.GET)
		public ModelAndView updateBuyNotCprSwitchSwitch() {
			HighLowStrategyTool.buyNotCprSwitch = !(HighLowStrategyTool.buyNotCprSwitch);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/buyCprCloseSwitch", method = RequestMethod.GET)
		public ModelAndView updateBuyCprCloseSwitch() {
			HighLowStrategyTool.buyCprCloseSwitch = !(HighLowStrategyTool.buyCprCloseSwitch);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/sellNotCprCloseSwitch", method = RequestMethod.GET)
		public ModelAndView updateSellNotCprCloseSwitch() {
			HighLowStrategyTool.sellNotCprCloseSwitch = !(HighLowStrategyTool.sellNotCprCloseSwitch);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/todaySpecialRuleSwitch", method = RequestMethod.GET)
		public ModelAndView updateTodaySpecialRuleSwitch() {
			HighLowStrategyTool.todaySpecialRule = !(HighLowStrategyTool.todaySpecialRule);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/matchNifty50BuyRuleSwitch", method = RequestMethod.GET)
		public ModelAndView updateMatchNifty50BuyRuleSwitch() {
			HighLowStrategyTool.matchNifty50BuyRuleSwitch = !(HighLowStrategyTool.matchNifty50BuyRuleSwitch);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
		@RequestMapping(value = "/matchNifty50SellRuleSwitch", method = RequestMethod.GET)
		public ModelAndView updateMatchNifty50SellRuleSwitch() {
			HighLowStrategyTool.matchNifty50SellRuleSwitch = !(HighLowStrategyTool.matchNifty50SellRuleSwitch);
			return HighLowStrategyTool.getInstance().prepareHeaderSwitchView("home2");
		}
		
	@RequestMapping(value = "/testTradeEntry", method = RequestMethod.GET)
	public @ResponseBody ModelAndView testTradeEntry() {
		DatabaseHelper.getInstance().getSymbolBeanToSaveForTestData();
		ModelAndView mv = new ModelAndView("home2");
	
		return mv;
	}
	@RequestMapping(value = "/testTradeExit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView testTradeExit() {
		DatabaseHelper.getInstance().updateTradeForTestData();
		ModelAndView mv = new ModelAndView("home2");
		return mv;
	}
}