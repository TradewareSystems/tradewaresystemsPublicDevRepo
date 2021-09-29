package com.tradeware.stockmarket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.clouddatabase.bean.OptionTradeFormDataBean;
import com.tradeware.clouddatabase.engine.NSEIndiaBankNiftyTradeToolNewNseSite;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.tool.TradewareOptionStrategyBuildTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.StockUtil;
@Controller
public class TradewareOptionStrategyController {

	// should be in common controller
	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}

	@Autowired
	private NSEIndiaBankNiftyTradeToolNewNseSite bankNiftyTradeTool;

	@Autowired
	private TradewareOptionStrategyBuildTool strategyBuildTool;

	@Autowired
	private TradewareTool tradewareTool;

	// It has to run on weekly basis store into data base, every day on start get
	// data from tradeware database.
	@RequestMapping(value = "/prepareExpiryDateListData", method = RequestMethod.GET)
	public @ResponseBody ModelAndView prepareDataMapsForIndexWithNseSite() {
		strategyBuildTool.prepareDataMapsForIndexWithNseSite();
		return tradewareTool.prepareHeaderSwitchView("home2");
	}

	@RequestMapping(value = "/createOptionTrade", method = RequestMethod.GET)
	public @ResponseBody ModelAndView createOptionTrade() {
		if (tradewareTool.getTradeExpiryDatesList(Constants.NIFTY).isEmpty()
				|| tradewareTool.getTradeStrikePricesList(Constants.NIFTY).isEmpty()) {
			strategyBuildTool.prepareDataMapsForIndexWithNseSite();
		}

		ModelAndView view = tradewareTool.prepareHeaderSwitchView("options/createOptionTradeOrder");
		view.addObject(Constants.OPTION_TRADE_ORDER, new OptionTradeFormDataBean());
		view.addObject(Constants.INDEX_MAP, tradewareTool.getIndexMap());
		view.addObject(Constants.TRADE_STRATEGY_OPTIONS, tradewareTool.getTradeStrategyOptionsMap());
		return view;
	}

	
/*	@RequestMapping(value = "/createOptionTrade2", method = RequestMethod.GET)
	public @Resp ModelAndView createOptionTrade(@RequestParam(value = "symbolId") String symbolId) {
		ModelAndView view = tradewareTool.prepareHeaderSwitchView("nkp/createOptionTradeOrder");
		view.addObject(Constants.INDEX_MAP, tradewareTool.getIndexMap());
		view.addObject(Constants.OPTION_TRADE_ORDER, new OptionChainLiveTradeBean());
		System.out.println(symbolId);
		if (null != symbolId) {
			if (tradewareTool.getIndexMap().keySet().contains(symbolId)) {
				view.addObject(Constants.EXPIRY_DATES, tradewareTool.getTradeExpiryDatesList());
				view.addObject(Constants.STRIKE_PRICES, tradewareTool.getTradeStrikePricesList());
			} else {

			}
		}

		return view;
	}*/
	 

	@RequestMapping(value = "/createOptionTrade2", method = RequestMethod.GET)
	@ResponseBody
	public List<String> createOptionTrade(@RequestParam(value = "symbolId") String symbolId) {
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockWholeOptionData(symbolId);
		return tradewareTool.getTradeExpiryDatesList(symbolId);
	}

	@RequestMapping(value = "/createOptionTrade3", method = RequestMethod.POST)
	@ResponseBody
//		public /* ResponseEntity<Set<String>> */ List<String> createOptionTrade3(
//				@RequestParam Map<String, String> requestParams) {
//			bankNiftyTradeTool.connectToNSEIndiaAndGetStockWholeOptionData(requestParams.get(Constants.PARAM_SYMBOL_ID));
//			return tradewareTool.getTradeExpiryDatesList(requestParams.get(Constants.PARAM_SYMBOL_ID));
//		}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map createOptionTrade3(@RequestParam Map<String, String> requestParams) {
		Map map = new HashMap();
		// bankNiftyTradeTool.connectToNSEIndiaAndGetStockWholeOptionData(requestParams.get(Constants.PARAM_SYMBOL_ID));
		tradewareTool.prepareLiveOHLCDataMapsForIndexOptionsWithKiteApiOrNseSite(requestParams.get(Constants.PARAM_SYMBOL_ID), null, null);
		List<String> expiryDatesList = tradewareTool.getTradeExpiryDatesList(requestParams.get(Constants.PARAM_SYMBOL_ID));
		if (null == expiryDatesList || expiryDatesList.isEmpty()) {
			strategyBuildTool.prepareDataMapsForIndexWithNseSite();
			expiryDatesList = tradewareTool.getTradeExpiryDatesList(requestParams.get(Constants.PARAM_SYMBOL_ID));
		}
				
		map.put("expiryDatesList", tradewareTool.getTradeExpiryDatesList(requestParams.get(Constants.PARAM_SYMBOL_ID)));
		if (Constants.NIFTY.equals(requestParams.get(Constants.PARAM_SYMBOL_ID))) {
			map.put(Constants.STOCK_LAST_PRICE, tradewareTool.getNiftyLiveStockPrice());
			map.put(Constants.FUTURE_LAST_PRICE, tradewareTool.getNiftyLiveFuturePrice());
		} else if (Constants.BANKNIFTY.equals(requestParams.get(Constants.PARAM_SYMBOL_ID))) {
			map.put(Constants.STOCK_LAST_PRICE, tradewareTool.getBankNiftyLiveStockPrice());
			map.put(Constants.FUTURE_LAST_PRICE, tradewareTool.getBankNiftyLiveFuturePrice());
		} else if (Constants.FINNIFTY.equals(requestParams.get(Constants.PARAM_SYMBOL_ID))) {
			map.put(Constants.STOCK_LAST_PRICE, tradewareTool.getFinanceNiftyLiveStockPrice());
			map.put(Constants.FUTURE_LAST_PRICE, tradewareTool.getFinanceNiftyLiveFuturePrice());
		}

		return map;
	}

	@RequestMapping(value = "/optionTradeSubStrategy", method = RequestMethod.POST)
	@ResponseBody
	public List<String> optionTradeSubStrategy(@RequestParam Map<String, String> requestParams) {
		List<String> subList = null;
		if (null != requestParams && null != requestParams.get(Constants.OPTION_TRADE_STRATEGY)) {
			subList = strategyBuildTool.optionTradeSubStrategy(requestParams.get(Constants.OPTION_TRADE_STRATEGY));
		}
		return subList;
	}

	@RequestMapping(value = "/optionTradeSubStrategySelection", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> optionTradeSubStrategySelectEvent(@RequestParam Map<String, String> requestParams) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (Constants.OPTION_STRATEGY_RATIO_SPREAD.equals(requestParams.get(Constants.OPTION_TRADE_STRATEGY))) {
			map = strategyBuildTool.getCallRatioSpreadStrategyTradeData(
					requestParams.get(Constants.PARAM_SYMBOL_ID),
					requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY),
					requestParams.get(Constants.PARAM_EXPIRY_DATE), requestParams.get(Constants.INDEX_VALUE));
		} else if (Constants.OPTION_STRATEGY_STRADDLE.equals(requestParams.get(Constants.OPTION_TRADE_STRATEGY))) {
			map = strategyBuildTool.getStraddleStrategyTradeData(
					requestParams.get(Constants.PARAM_SYMBOL_ID),
					requestParams.get(Constants.OPTION_TRADE_STRATEGY),
					requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY),
					requestParams.get(Constants.PARAM_EXPIRY_DATE), requestParams.get(Constants.INDEX_VALUE));
		}
		return map;
	}

	@RequestMapping(value = "/mainStrikePriceList", method = RequestMethod.POST)
	@ResponseBody
	public List<Integer> getTradeMainStrikePricesList(@RequestParam Map<String, String> requestParams) {
		return strategyBuildTool.getTradeMainStrikePricesList(requestParams.get(Constants.PARAM_SYMBOL_ID),
				requestParams.get(Constants.OPTION_TRADE_STRATEGY), requestParams.get(Constants.INDEX_VALUE));
	}

	@RequestMapping(value = "/protectionStrikePriceList", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getSpreadProtectionTradeStrikePricesList(
			@RequestParam Map<String, String> requestParams) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (null != requestParams && null != requestParams.get(Constants.PARAM_SYMBOL_ID)
				&& null != requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY)
				&& null != requestParams.get(Constants.PARAM_EXPIRY_DATE)
				&& null != requestParams.get(Constants.PARAM_STRIKE_PRICE_LOWER_OF_ATM)) {

			map = strategyBuildTool.getSpreadProtectionTradeStrikePricesList(
					requestParams.get(Constants.PARAM_SYMBOL_ID),
					requestParams.get(Constants.OPTION_TRADE_STRATEGY),
					requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY),
					 requestParams.get(Constants.INDEX_VALUE),
					requestParams.get(Constants.PARAM_EXPIRY_DATE),
					requestParams.get(Constants.PARAM_STRIKE_PRICE_LOWER_OF_ATM));
		}
		return map;
	}

	@RequestMapping(value = "/onAtmStrikePriceSelectionOfButterFly", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> onAtmStrikePriceSelectionOfButterFly(@RequestParam Map<String, String> requestParams) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (null != requestParams && null != requestParams.get(Constants.PARAM_SYMBOL_ID)
				&& null != requestParams.get(Constants.OPTION_TRADE_STRATEGY)
				&& null != requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY)
				&& null != requestParams.get(Constants.PARAM_EXPIRY_DATE)
				&& null != requestParams.get(Constants.PARAM_STRIKE_PRICE_OF_ATM)) {
			map = strategyBuildTool.onAtmStrikePriceSelectionOfButterFly(requestParams.get(Constants.PARAM_SYMBOL_ID),
					requestParams.get(Constants.OPTION_TRADE_STRATEGY),
					requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY),
					requestParams.get(Constants.PARAM_EXPIRY_DATE),
					requestParams.get(Constants.PARAM_STRIKE_PRICE_OF_ATM));
		}
		return map;
	}

	@RequestMapping(value = "/protectionStrikePriceSelectionEvent", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> protectionStrikePriceSelectionEvent(@RequestParam Map<String, String> requestParams) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (null != requestParams && null != requestParams.get(Constants.PARAM_STRIKE_PRICE_HIGHER_OF_ATM)
				&& null != requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY)
				&& null != requestParams.get(Constants.PARAM_EXPIRY_DATE)) {
			map = strategyBuildTool.protectionStrikePriceSelectionEvent(
					requestParams.get(Constants.PARAM_SYMBOL_ID),
					requestParams.get(Constants.OPTION_TRADE_SUB_STRATEGY),
					requestParams.get(Constants.PARAM_EXPIRY_DATE),
					requestParams.get(Constants.PARAM_STRIKE_PRICE_LOWER_OF_ATM),
					requestParams.get(Constants.PARAM_STRIKE_PRICE_HIGHER_OF_ATM));
		}
		return map;
	}
	
	@RequestMapping(value = "/submitOptionTradeOrder", method = RequestMethod.POST)
	public @ResponseBody ModelAndView submitOptionTradeOrder(@ModelAttribute OptionTradeFormDataBean optionTradeOrder,
			ModelMap model) {

		OptionLiveTradeMainDataBean mainBean = tradewareTool.submitOptionTradeOrder(optionTradeOrder);
		List<OptionLiveTradeMainDataBean> list = new ArrayList<OptionLiveTradeMainDataBean>( );
		list.add(mainBean);
		ModelAndView mv = getModelAndView("options/reportOptionStrategyTradesView2");
		mv.addObject("tradeList", list);
		return mv;
	}

	// Just for test purpose start
	@RequestMapping(value = "findByOptionStrategyTradeId", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findByOptionStrategyTradeId(@RequestParam(value = "tradeId") Integer tradeId) {
		OptionLiveTradeMainDataBean bean = DatabaseHelper.getInstance().findByOptionStrategyTradeId(tradeId);
		return tradewareTool.prepareHeaderSwitchView("home2");
	}

	@RequestMapping(value = "findByOpenOptionStrategyTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView findByOpenOptionStrategyTrades() {

		List<OptionLiveTradeMainDataBean> list = new ArrayList<OptionLiveTradeMainDataBean>(
				DatabaseHelper.getInstance().findByOpenOptionStrategyTrades());

		return tradewareTool.prepareHeaderSwitchView("home2");
	}
	// Just for test purpose END



	// Reports
	@RequestMapping(value = "reportOptionStrategyTrades", method = RequestMethod.GET)
	public @ResponseBody ModelAndView reportOptionStrategyTrades() {
		List<OptionLiveTradeMainDataBean> list = new ArrayList<OptionLiveTradeMainDataBean>(
				DatabaseHelper.getInstance().findAllOptionStrategyTrades());
		ModelAndView mv = getModelAndView("options/reportOptionStrategyTrades");
		mv.addObject("tradeList", list);
		return mv;
	}

	// Reports
	@RequestMapping(value = "reportOptionStrategyTradesView2", method = RequestMethod.GET)
	public @ResponseBody ModelAndView reportOptionStrategyTradesView2() {
		List<OptionLiveTradeMainDataBean> list = new ArrayList<OptionLiveTradeMainDataBean>(
				DatabaseHelper.getInstance().findAllOptionStrategyTrades());
		ModelAndView mv = getModelAndView("options/reportOptionStrategyTradesView2");
		mv.addObject("tradeList", list);
		return mv;
	}
	//findAllByRunningTrade
	
	//findAllByTradedDateStamp
}
