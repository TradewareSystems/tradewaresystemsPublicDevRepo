package com.tradeware.stockmarket.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.stockmarket.bean.Narrow7DataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbMonthlyReportDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.tool.NSEIndiaToolNR7Tool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Controller
public class ReportControllerFilter {
	@Autowired
	private TradewareTool  tradewareTool;
	
	@RequestMapping(value = "filter1000", method = RequestMethod.GET)
	public ModelAndView filter1000() {
		return tradewareTool.prepareHeaderSwitchView("home2");
	}
	@RequestMapping(value = "filter1000Temp", method = RequestMethod.GET)
	public ModelAndView filter1000Temp() {
		return tradewareTool.prepareHeaderSwitchView("home3");
	}
	private List<StrategyOrbDataBean> filterOutTradesOnCandleNumber(List<StrategyOrbDataBean> list) {
		if (null != list) {
			Iterator<StrategyOrbDataBean> it = list.iterator();
			while (it.hasNext()) {
				StrategyOrbDataBean bean = it.next();
				if (null != bean.getCandleNumber() && bean.getCandleNumber() > tradewareTool.getQueryCandleNumber()) {
					it.remove();
				}
			}
		}
		return list;
	}
	
	@RequestMapping(value = "/reportFullFilterCustom2-1000-filter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView Custom2() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(DatabaseHelper.getInstance()
				.findAllByTradedDateStampAndOhlcStateEqTradableStateOrderBySymbolAscTradedAtDtTmAsc());
		list = filterOutTradesOnCandleNumber(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRule3VwapStrongFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule3AllVwapStrong() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByStrongVwapAndVolumeRule());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}

		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	//04-10-2021 start
	@RequestMapping(value = "/customRuleStrongVolumeFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStrongVolumePressureRule() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByStrongVolumePressureRule());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}

		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRuleStrongVolumeFilterMatch", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStrongVolumePressureRuleMatch() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByStrongVolumePressureRuleMatch());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}

		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRuleProfitSmaVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllBySmaVwapLevel2Rule() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllBySmaVwapLevel2Rule());

		/*Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}*/

		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	//04-10-2021 end
	
	// phase 4 start 04-23-2021 start
	@RequestMapping(value = "/initProfitableProdRule", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByInitProfitableProdRule() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByInitProfitableProdRule());
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/initProfitableProdRuleFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByInitProfitableFilterProdRule() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByInitProfitableFilterProdRule());
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	// phase 4 end 04-23-2021
	// Phase 4 :: 05-09-2021 start - afterSomeSuccess
	@RequestMapping(value = "/smaVwapRuleTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllBySmaVwapRuleTrades() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllBySmaVwapRuleTrades());
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/forwardEngulfingRuleTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByForwardEngulfingRuleTrades() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByForwardEngulfingRuleTrades());
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/fwdEngulfingSmaVwapRuleTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByForwardEngulfingAndSmaVwapRuleTrades() {
		Set<StrategyOrbDataBean> set = new HashSet<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByForwardEngulfingRuleTrades());
		set.addAll(DatabaseHelper.getInstance().findAllBySmaVwapRuleTrades());
		return tradewareTool.prepareHeaderString(new ArrayList<StrategyOrbDataBean>(set), "reportFull");
	}
	
	@RequestMapping(value = "/volumeStrengthTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByVolumeStrengthTrades() {
		Set<StrategyOrbDataBean> set = new HashSet<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByVolumeStrengthTrades());
		return tradewareTool.prepareHeaderString(new ArrayList<StrategyOrbDataBean>(set), "reportFull");
	}
	
	@RequestMapping(value = "/volumeStochStrengthTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStochasticVolumeStrengthTrades() {
		return tradewareTool.prepareHeaderString(DatabaseHelper.getInstance().findAllByStochasticVolumeStrengthTrades(),
				"reportFull");
	}
	
	@RequestMapping(value = "/volumeStrongStochStrengthTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStrongStochasticVolumeStrengthTrades() {
		return tradewareTool.prepareHeaderString(
				DatabaseHelper.getInstance().findAllByStrongStochasticVolumeStrengthTrades(), "reportFull");
	}
	
	@RequestMapping(value = "/stochasticRule1Trades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStochasticRule1Trades() {
		return tradewareTool.prepareHeaderString(DatabaseHelper.getInstance().findAllByStochasticRule1Trades(),
				"reportFull");
	}
	
	@RequestMapping(value = "/stochasticRule2Trades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStochasticRule2Trades() {
		return tradewareTool.prepareHeaderString(DatabaseHelper.getInstance().findAllByStochasticRule2Trades(),
				"reportFull");
	}
	
	@RequestMapping(value = "/stochasticStrongRule3Trades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStochasticStrongRule3Trades() {
		return tradewareTool.prepareHeaderString(DatabaseHelper.getInstance().findAllByStochasticStrongRule3Trades(),
				"reportFull");
	}
	
	@RequestMapping(value = "/stochasticBasicRuleTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStochasticBasicRuleTrades() {
		return tradewareTool.prepareHeaderString(DatabaseHelper.getInstance().findAllByStochasticBasicRuleTrades(),
				"reportFull");
	}

	@RequestMapping(value = "/allByProfiitTradess", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByProfiitTrades() {
		return tradewareTool.prepareHeaderString(DatabaseHelper.getInstance().findAllByProfiitTrades(), "reportFull");
	}
	
	
	@RequestMapping(value = "/forwardEngulfingMonthlyReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllMonthlyReportByForwardEngulfingRuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> list = new ArrayList<StrategyOrbMonthlyReportDataBean>(
				DatabaseHelper.getInstance().findAllMonthlyReportByForwardEngulfingRuleTrades());
		return tradewareTool.prepareHeaderStringForMonthlyReport(list, "reportMonthly");
	}
	
	@RequestMapping(value = "/forwardEngulfingLvl2MonthlyReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllMonthlyReportByForwardEngulfingLvl2RuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> list = new ArrayList<StrategyOrbMonthlyReportDataBean>(
				DatabaseHelper.getInstance().findAllMonthlyReportByForwardEngulfingLvl2RuleTrades());
		return tradewareTool.prepareHeaderStringForMonthlyReport(list, "reportMonthly");
	}
	
	@RequestMapping(value = "/forwardEngulfingLvl3MonthlyReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllMonthlyReportByForwardEngulfingLvl3RuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> list = new ArrayList<StrategyOrbMonthlyReportDataBean>(
				DatabaseHelper.getInstance().findAllMonthlyReportByForwardEngulfingLvl3RuleTrades());
		return tradewareTool.prepareHeaderStringForMonthlyReport(list, "reportMonthly");
	}
	
	@RequestMapping(value = "/smaVwapMonthlyReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllMonthlyReportBySmaVwapRuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> list = new ArrayList<StrategyOrbMonthlyReportDataBean>(
				DatabaseHelper.getInstance().findAllMonthlyReportBySmaVwapRuleTrades());
		return tradewareTool.prepareHeaderStringForMonthlyReport(list, "reportMonthly");
	}
	
	@RequestMapping(value = "/smaVwapMonthlyReportUnionAll", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllMonthlyReportBySmaVwapRuleUnionTrades() {
		List<StrategyOrbMonthlyReportDataBean> list = new ArrayList<StrategyOrbMonthlyReportDataBean>(
				DatabaseHelper.getInstance().findAllMonthlyReportBySmaVwapRuleUnionTrades());
		return tradewareTool.prepareHeaderStringForMonthlyReport(list, "reportMonthly");
	}
	
	@RequestMapping(value = "/smaVwapMonthlyReportRule2", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllMonthlyReportBySmaVwapRule2Trades() {
		List<StrategyOrbMonthlyReportDataBean> list = new ArrayList<StrategyOrbMonthlyReportDataBean>(
				DatabaseHelper.getInstance().findAllMonthlyReportBySmaVwapRule2Trades());
		return tradewareTool.prepareHeaderStringForMonthlyReport(list, "reportMonthly");
	}
	// Phase 4 :: 05-09-2021 end - afterSomeSuccess
	
	
	@RequestMapping(value = "/customRule1-filter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule1() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradingRule7TimesStrenth());
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					
					&& ((bean.getTradableStateId().equals(Constants.BUY) && null != bean.getBuySellQuantityRatio()
							&& bean.getBuySellQuantityRatio() > 1) || bean.getTradableStateId().equals(Constants.SELL))
					
					)) {
				it.remove();
			}
		}
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRule2-filter", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView findAllByCustomRule2() {
	public @ResponseBody ModelAndView findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap());
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					)) {
				it.remove();
			}
		}
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/niftyAndBankNiftyTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllNIFTYorBANKNIFTY() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllNIFTYorBANKNIFTY());
		/*Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayCrossInd() && bean.getPrevDayCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrend())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					)) {
				it.remove();
			}
		}*/
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/niftyAndBankNiftyTradesFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllNIFTYorBANKNIFTYFilter() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllNIFTYorBANKNIFTY());
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getPrevDayHrCrossInd())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					)) {
				it.remove();
			}
		}
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}

	/*@RequestMapping(value = "/customRule3VwapStrongFilter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule3AllVwapStrong() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByCustomRule3AllVwapStrong());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3)))) {
				it.remove();
			}
		}

		return tradewareTool.prepareHeaderString(list, "reportFull");
	}

	@RequestMapping(value = "/customRule3VwapStrongFilterOHLC", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule3AllVwapStrong2() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByCustomRule3AllVwapStrong());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId())))) {
				it.remove();
			}
		}

		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	
	@RequestMapping(value = "/reportFullFilterCustom2-1000-filter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView Custom2() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtTmAsc());
		List<StrategyOrbDataBean> listNew = new ArrayList<StrategyOrbDataBean>();
		for (StrategyOrbDataBean bean : list) {
			if (((bean.getCandleHighsDiff() < bean.getCandleLowsDiff()) && ((bean.getCandle2HighMinusClose() == 0)
					|| (bean.getCandle2HighMinusClose() * 3) <= bean.getCandle2CloseMinusLow()))
					|| ((bean.getCandleLowsDiff() < bean.getCandleHighsDiff()) && ((bean.getCandle2CloseMinusLow() == 0)
							|| (bean.getCandle2CloseMinusLow() * 3) <= bean.getCandle2HighMinusClose()))) {
				listNew.add(bean);
			}
		}
		listNew = filterOutGapTrades(listNew);
		
		//Additional filter
		Iterator<StrategyOrbDataBean> it = listNew.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId())))) {
				it.remove();
			}
		}
		return tradewareTool.prepareHeaderString(listNew, "reportFull");
	}
	
	private List<StrategyOrbDataBean> filterOutGapTrades(List<StrategyOrbDataBean> list) {
		if (null != list) {
			Iterator<StrategyOrbDataBean> it = list.iterator();
			while (it.hasNext()) {
				StrategyOrbDataBean bean = it.next();
				if ((null != bean.getGapUpDownMoveVal() && bean.getGapUpDownMoveVal().doubleValue() >= 1000)
						|| null != bean.getCandleNumber()
								&& bean.getCandleNumber() > tradewareTool.getQueryCandleNumber()) {
					it.remove();
				}
			}
		}
		return list;
	}
	
	
	@RequestMapping(value = "/reportFullFilterCustom2-filter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView Custom2QQ() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtTmAsc());
		List<StrategyOrbDataBean> listNew = new ArrayList<StrategyOrbDataBean>();
		for (StrategyOrbDataBean bean : list) {
			if (((bean.getCandleHighsDiff() < bean.getCandleLowsDiff()) && ((bean.getCandle2HighMinusClose() == 0)
					|| (bean.getCandle2HighMinusClose() * 3) <= bean.getCandle2CloseMinusLow()))
					|| ((bean.getCandleLowsDiff() < bean.getCandleHighsDiff()) && ((bean.getCandle2CloseMinusLow() == 0)
							|| (bean.getCandle2CloseMinusLow() * 3) <= bean.getCandle2HighMinusClose()))) {
				listNew.add(bean);
			}
		}
		
		// Additional filter
		double totalProfit = 0;
		Iterator<StrategyOrbDataBean> it = listNew.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId())))) {
				it.remove();
			} else {
				if (bean.getPositiveDirectionProfit() != null && bean.getPositiveDirectionProfit() > 2000) {
					totalProfit = totalProfit + 2000;
				} else {
					totalProfit = totalProfit + bean.getProfitLossAmtVal();		
				}
			}
			TradewareTool.profitLossFinalForNr7_2_step = totalProfit;
		}
		return tradewareTool.prepareHeaderString(listNew, "reportFull");
	}
	
	@RequestMapping(value = "/customRule1-filter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule1() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByCustomRule1());
		//return TradewareTool.prepareHeaderString(filterOutDayLevelTrades(list), "reportFull");
		// Additional filter
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					
					&& ((bean.getTradableStateId().equals(Constants.BUY) && null != bean.getBuySellQuantityRatio()
							&& bean.getBuySellQuantityRatio() > 1) || bean.getTradableStateId().equals(Constants.SELL))
					
					)) {
				it.remove();
			}
		}
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRule2-filter", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule2() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByCustomRule2());
		//return TradewareTool.prepareHeaderString(filterOutDayLevelTrades(list), "reportFull");
		// Additional filter
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					)) {
				it.remove();
			}
		}
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}*/
	
	
	//04-26-2021 for Home3 page start
	@RequestMapping(value = "/reportFullFilterCustom2-1000-filterVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView Custom2Vwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(DatabaseHelper.getInstance()
				.findAllByTradedDateStampAndOhlcStateEqTradableStateOrderBySymbolAscTradedAtDtTmAsc());
		list = filterOutTradesOnCandleNumber(list);
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRule3VwapStrongFilterVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule3AllVwapStrongVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByStrongVwapAndVolumeRule());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	//04-10-2021 start
	@RequestMapping(value = "/customRuleStrongVolumeFilterVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStrongVolumePressureRuleVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByStrongVolumePressureRule());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRuleStrongVolumeFilterMatchVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByStrongVolumePressureRuleMatchVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByStrongVolumePressureRuleMatch());

		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRuleProfitSmaVwap_Vwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllBySmaVwapLevel2RuleVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllBySmaVwapLevel2Rule());

		/*Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && !bean.getOhlcStateId().equals(Constants.BUY_SELL_SPIKE_3))
					)) {
				it.remove();
			}
		}*/

		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	@RequestMapping(value = "/customRuleSmaVwapPlusRule", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllBySmaVwapLevel2PlusRule() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllBySmaVwapLevel2PlusRule());
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	//04-10-2021 end
	
	// phase 4 start 04-23-2021 start
	@RequestMapping(value = "/initProfitableProdRuleVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByInitProfitableProdRuleVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByInitProfitableProdRule());
		filterOutOnVwapRange(list);
		
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!tradewareTool.verifyProfits(bean)) {
				it.remove();
			}
		}
			
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/initProfitableProdRuleFilterVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByInitProfitableFilterProdRuleVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByInitProfitableFilterProdRule());
		filterOutOnVwapRange(list);
		
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!tradewareTool.verifyProfits(bean)) {
				it.remove();
			}
		}
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	// phase 4 end 04-23-2021
	
	@RequestMapping(value = "/customRule1-filterVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllByCustomRule1Vwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradingRule7TimesStrenth());
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					
					&& ((bean.getTradableStateId().equals(Constants.BUY) && null != bean.getBuySellQuantityRatio()
							&& bean.getBuySellQuantityRatio() > 1) || bean.getTradableStateId().equals(Constants.SELL))
					
					)) {
				it.remove();
			}
		}
		
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/customRule2-filterVwap", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView findAllByCustomRule2() {
	public @ResponseBody ModelAndView findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwapVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap());
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					)) {
				it.remove();
			}
		}
		
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/niftyAndBankNiftyTradesVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllNIFTYorBANKNIFTYVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllNIFTYorBANKNIFTY());
		/*Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayCrossInd() && bean.getPrevDayCrossInd())
					&& bean.getTradableStateId().equals(bean.getCandleTypeTrend())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					)) {
				it.remove();
			}
		}*/
		
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	@RequestMapping(value = "/niftyAndBankNiftyTradesFilterVwap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findAllNIFTYorBANKNIFTYFilterVwap() {
		List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
				DatabaseHelper.getInstance().findAllNIFTYorBANKNIFTY());
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();
			if (!((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
					&& bean.getTradableStateId().equals(bean.getPrevDayHrCrossInd())
					//&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))
					)) {
				it.remove();
			}
		}
		
		
		filterOutOnVwapRange(list);
		return tradewareTool.prepareHeaderString(list, "reportFull");
	}
	
	private void filterOutOnVwapRange(List<StrategyOrbDataBean> list) {
		Iterator<StrategyOrbDataBean> it = list.iterator();
		while (it.hasNext()) {
			StrategyOrbDataBean bean = it.next();

			if (!((Constants.BUY.equals(bean.getTradableStateId())
					&& (null != bean.getVwapValue() && null != bean.getBuyAtVal()
							&& bean.getVwapValue() < bean.getBuyAtVal())
					&& (((bean.getVwapValue() - bean.getBuyAtVal())
							* bean.getLotSize()) <= Constants.TARGET_CO_STOP_LOSS_PROFIT_7500))
					|| (Constants.SELL.equals(bean.getTradableStateId())
							&& (null != bean.getVwapValue() && null != bean.getBuyAtVal()
									&& bean.getVwapValue() > bean.getSellAtVal())
							&& (((bean.getVwapValue() - bean.getSellAtVal())
									* bean.getLotSize()) <= Constants.TARGET_CO_STOP_LOSS_PROFIT_7500))

			)) {
				it.remove();
			}
		}
	}
	//04-26-2021 for Home3 page end
	
	
	
	//06-05-2021 for NR7 page start
	@Autowired
	private NSEIndiaToolNR7Tool nseIndiaToolNR7Tool;
	
	@RequestMapping(value = "testNr7", method = RequestMethod.GET)
	public @ResponseBody ModelAndView testNr7() {
		/*if(tradewareTool.getBaseDataMap().isEmpty() || tradewareTool.getBaseDataMapAll().isEmpty()) {
			tradewareTool.updateKiteFutureKeyAndInstrumentToken();
		}*/
		List<SymbolDataBean> symbolDataBeans = DatabaseHelper.getInstance()
				.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean.TRUE, Boolean.TRUE, 1);
		symbolDataBeans.addAll(DatabaseHelper.getInstance()
				.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean.TRUE, Boolean.TRUE, 2));

		/*for (String key : tradewareTool.getBaseDataMap().keySet()) {
			StrategyOrbDataBean orbBean = tradewareTool.getBaseDataMap().get(key);
			Narrow7DataBean nr7Bean = new Narrow7DataBean(orbBean.getLotSize(),
					orbBean.getSymbolId().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
			nr7Bean.setSymbolName(orbBean.getSymbolName());
			nr7Bean.setKiteFutureKey(orbBean.getKiteFutureKey());

			tradewareTool.getNr7TradeDataMap().put(key, nr7Bean);
		}*/

		/*for (String key : tradewareTool.getBaseDataMapAll().keySet()) {

			StrategyOrbDataBean orbBean = tradewareTool.getBaseDataMapAll().get(key);
		if (!("NIFTY 50".equals(orbBean.getSymbolId()) || "BANKNIFTY".equals(orbBean.getSymbolId())
				|| "FINNIFTY".equals(orbBean.getSymbolId()))) {
			Narrow7DataBean nr7Bean = new Narrow7DataBean(orbBean.getLotSize(),
					orbBean.getSymbolId().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
			nr7Bean.setSymbolName(orbBean.getSymbolName());
			nr7Bean.setKiteFutureKey(orbBean.getKiteFutureKey());

			tradewareTool.getNr7TradeDataMap().put(key, nr7Bean);
			}
		}*/
		
		
		for (SymbolDataBean bean : symbolDataBeans) {
			if (!("NIFTY 50".equals(bean.getSymbolId()) || "BANKNIFTY".equals(bean.getSymbolId())
					|| "FINNIFTY".equals(bean.getSymbolId()))) {
				Narrow7DataBean nr7Bean = new Narrow7DataBean(bean.getLotSize(),
						bean.getSymbolId().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
				nr7Bean.setKiteFutureKey(Constants.FUTURE_KEY_PREFIX_NSE + bean.getSymbolId());
				nr7Bean.setSymbolName(tradewareTool.getSymbolName(nr7Bean.getKiteFutureKey()));
				tradewareTool.getNr7TradeDataMap().put(nr7Bean.getKiteFutureKey(), nr7Bean);

				if (null != bean.getFnoInd() && bean.getFnoInd()) {
					Narrow7DataBean nr7FutBean = new Narrow7DataBean(bean.getLotSize(),
							bean.getSymbolId().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
					nr7FutBean.setKiteFutureKey(tradewareTool.getKiteFutureKey(bean.getSymbolId()));
					nr7FutBean.setSymbolName(tradewareTool.getSymbolName(nr7FutBean.getKiteFutureKey()));
					tradewareTool.getNr7TradeDataMap().put(nr7FutBean.getKiteFutureKey(), nr7FutBean);
				}

			}
		}
		nseIndiaToolNR7Tool.prepareNR7Tradees(); 
		ModelAndView mv=tradewareTool.getModelAndView( "dayLevelNR7Hist");
		mv.addObject("reportList", tradewareTool.getNr7TradeDataMap().values());
		return mv;
	}
	//06-05-2021 for NR7 page end
}
