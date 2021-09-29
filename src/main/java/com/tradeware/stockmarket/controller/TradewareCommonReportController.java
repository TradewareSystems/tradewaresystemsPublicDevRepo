package com.tradeware.stockmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Controller
public class TradewareCommonReportController {
	@Autowired
	private TradewareTool tradewareTool;
	
	@RequestMapping(value = "reportSystemError", method = RequestMethod.GET)
	public ModelAndView reportSystemError() {
		ModelAndView view = tradewareTool.prepareHeaderSwitchView("reportSystemError");
		view.addObject("systemErrorList", DatabaseHelper.getInstance().findAllByDateStampOrderByDateTimeStampDesc());
		return view;
	}
}
