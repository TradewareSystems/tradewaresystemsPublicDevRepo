package org.tradeware.stockmarket.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tradeware.stockmarket.bean.Narrow7StockDataBean;
import org.tradeware.stockmarket.bean.OptionChainDataBean;
import org.tradeware.stockmarket.bean.OptionStockDataBean;
import org.tradeware.stockmarket.bean.StockDataBean;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaToolNewNseSite;
import org.tradeware.stockmarket.util.StockUtil;

@Controller
public class TradewareReportController_NEW {
	
	@RequestMapping(value = "getTradeLevels", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getTradeSignalsAsHTMLTable() {

		List<StockDataBean> list = new ArrayList<>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getPositionalResultDataMap().values());
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getStockName().compareTo(right.getStockName()); // use your logic
			}
		};
		Collections.sort(list, comparator);
		//return new ModelAndView("tradeLevels" , "tradeLevels", list);
		ModelAndView mv = getModelAndView("nkp/tradeLevels");
		mv.addObject("tradeLevels", list);
		return mv;
	}
	
	@RequestMapping(value = "getOptionCallDayReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionCallDayReport() { //HttpServletRequest req) {
		List<OptionStockDataBean> list = new ArrayList<>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getPositionalOptionsMap().values());
		Comparator<OptionStockDataBean> comparator = new Comparator<OptionStockDataBean>() {
			@Override
			public int compare(OptionStockDataBean left, OptionStockDataBean right) {
				return left.getTradeState().compareTo(right.getTradeState());
			}
		};
		Collections.sort(list, comparator);

		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionsReport");
		mv.addObject( "optionCalls", list);
		return mv;
	}
	
	@RequestMapping(value = "getPositionalFutureCallDayReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getPositionalFutureCallDayReport() { //HttpServletRequest req) {
		List<StockDataBean> list = new ArrayList<>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getPositionalFutureMap().values());
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getTradeState().compareTo(right.getTradeState());
			}
		};
		Collections.sort(list, comparator);

		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/positionalFutureReport");
		mv.addObject( "posFutureCalls", list);
		return mv;
	}
	
	@RequestMapping(value = "getNarrow7CallDayReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getNarrow7CallDayReport() { //HttpServletRequest req) {
		List<Narrow7StockDataBean> list = new ArrayList<Narrow7StockDataBean>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getNarrow7Map().values());
		List<Narrow7StockDataBean> validSymbolList = new ArrayList<Narrow7StockDataBean>();
		for (Narrow7StockDataBean bean  :  list) {
			if (bean.getNarrow7Rule()) {
				validSymbolList.add(bean);
			}
		}
				
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getStockName().compareTo(right.getStockName());
			}
		};
		Collections.sort(validSymbolList, comparator);

		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/narrow7Report");
		mv.addObject( "narrow7Beans", validSymbolList);
		return mv;
	}
	
	@RequestMapping(value = "getNarrow7CallDayReport2", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getNarrow7CallDayReport2() { //HttpServletRequest req) {
		List<Narrow7StockDataBean> list = new ArrayList<Narrow7StockDataBean>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getNarrow7Map().values());
		List<Narrow7StockDataBean> validSymbolList = new ArrayList<Narrow7StockDataBean>();
		for (Narrow7StockDataBean bean  :  list) {
			if (bean.getNarrow7Rule()) {
				validSymbolList.add(bean);
			}
		}
				
		Comparator<StockDataBean> comparator = new Comparator<StockDataBean>() {
			@Override
			public int compare(StockDataBean left, StockDataBean right) {
				return left.getStockName().compareTo(right.getStockName());
			}
		};
		Collections.sort(validSymbolList, comparator);

		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/narrow7Report2");
		mv.addObject( "narrow7Beans", validSymbolList);
		return mv;
	}

	//@RequestMapping(value = "getOptionChainReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getOptionChainDataMap().values());
				
		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				return left.getSymbol().compareTo(right.getSymbol());
			}
		};
		Collections.sort(list, comparator);

		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport");
		mv.addObject( "optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
		}
		return mv;
	}
	
	//@RequestMapping(value = "getOptionChainReportSortByTrend", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport_SortByTrend() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getOptionChainDataMap().values());
				
		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				return left.getOITrend().compareTo(right.getOITrend());
			}
		};
		Collections.sort(list, comparator);

		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport");
		mv.addObject( "optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
		}
		return mv;
	}
	
	//@RequestMapping(value = "getOptionChainReport2", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport2() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getOptionChainDataMap().values());
				
		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				//return right.getOITrend().compareTo(left.getOITrend());
				return Double.valueOf(right.getStrongOrder()).compareTo(Double.valueOf(left.getStrongOrder()));
			}
		};
		Collections.sort(list, comparator);
		// Add index option chain here
		List<OptionChainDataBean> listIndex = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/
				nSEIndiaTool.getOptionChainDataMapIndex().values());
		for (OptionChainDataBean indexOi : listIndex) {
			list.add(0, indexOi);
		}
		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject( "optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
		}
		return mv;
	}
	
	//@RequestMapping(value = "getOptionChainReport2SortByTrend", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getOptionChainReport2_SortByTrend() {
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/nSEIndiaTool.getOptionChainDataMap().values());
				
		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				return right.getOITrend().compareTo(left.getOITrend());
			}
		};
		Collections.sort(list, comparator);
		
		// Add index option chain here
		List<OptionChainDataBean> listIndex = new ArrayList<OptionChainDataBean>(/** NSEIndiaTool.getInstance() **/
				nSEIndiaTool.getOptionChainDataMapIndex().values());
		for (OptionChainDataBean indexOi : listIndex) {
			list.add(0, indexOi);
		}

		//return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReport2");
		mv.addObject( "optionChainDataList", list);
		if (!list.isEmpty()) {
			mv.addObject("candleNumber", list.get(0).getCandleNumber());
		}
		return mv;
	}
	
	// should be in common controller
	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}
	
	// connect to nseindia.in and retrieve stocks data and insert into t_symbol
	// table
	//@RequestMapping(value = "/prepareTradewareDatabase", method = RequestMethod.GET)
	public ModelAndView getprepareAlgoCloudDataAndGetSymbolList() {
		/** NSEIndiaTool.getInstance() **/
		nSEIndiaTool.prepareAlgoCloudDataAndGetSymbolList();
		
		return getOptionChainReport2();
	}

	@Autowired
	//private NSEIndiaTool nSEIndiaTool;
	private NSEIndiaToolNewNseSite nSEIndiaTool;
	
	
	//@RequestMapping(value = "/refreshOIDataForAll", method = RequestMethod.GET)
	public ModelAndView refreshOIDataForAll() {
		nSEIndiaTool.refreshOIDataForAll();
		return getOptionChainReport2_SortByTrend();
	}
	
	@RequestMapping(value = "/refreshOIDataForTops", method = RequestMethod.GET)
	public ModelAndView refreshOIDataForTops() {
		nSEIndiaTool.refreshOIDataForTops();
		return getOptionChainReport2_SortByTrend();
	}
	
	@RequestMapping(value = "/bestTrades", method = RequestMethod.GET)
	public ModelAndView refreshOIDataForPlayable() {
		nSEIndiaTool.refreshOIDataForPlayable();
		List<OptionChainDataBean> list = new ArrayList<OptionChainDataBean>(
				nSEIndiaTool.getOptionChainDataMapPlayable().values());

		Comparator<OptionChainDataBean> comparator = new Comparator<OptionChainDataBean>() {
			@Override
			public int compare(OptionChainDataBean left, OptionChainDataBean right) {
				return Double.valueOf(right.getStrongOrder()).compareTo(Double.valueOf(left.getStrongOrder()));
			}
		};
		Collections.sort(list, comparator);

		// Add index option chain here
		List<OptionChainDataBean> listIndex = new ArrayList<OptionChainDataBean>(
				nSEIndiaTool.getOptionChainDataMapIndex().values());
		for (OptionChainDataBean indexOi : listIndex) {
			list.add(0, indexOi);
		}

		// return new ModelAndView("optionsReport" , "optionCalls", list);
		ModelAndView mv = getModelAndView("nkp/optionChainOIReportTradable");
		mv.addObject("optionChainDataList", list);
		return mv;
	}
}
