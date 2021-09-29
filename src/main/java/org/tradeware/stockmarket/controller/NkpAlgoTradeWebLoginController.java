package org.tradeware.stockmarket.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tradeware.stockmarket.bean.TradewareUser;
import org.tradeware.stockmarket.util.StockUtil;


@Controller
public class NkpAlgoTradeWebLoginController {

	/*
	 * //TODO:CONFLICT_ORB 
	 * @RequestMapping(value = "/appLogin", method = RequestMethod.POST, 
			//consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			//MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
			produces = {"application/json", "application/xml"}
    ,  consumes = {"application/x-www-form-urlencoded"})*/
	
	// public ModelAndView login(@RequestBody TradewareUser user, HttpServletRequest req)
	// {
	//public ModelAndView login(@ModelAttribute("appLogin") TradewareUser user, BindingResult result, HttpServletRequest req) {
	public ModelAndView login(@ModelAttribute("userForm") TradewareUser user,
			BindingResult result, Model model,HttpServletRequest req) {
		ModelAndView response = null;
		if (null != user.getUserName() && null != user.getPassword() && "admin".equals(user.getUserName())
				&& "admin".equals(user.getPassword())) {
			req.getSession().setAttribute("user_verify", true);
			response = getModelAndView("home2");
		} else {
			response = getModelAndView("appLogin");
		}
		return response;
	}

	@RequestMapping(value = "/appLoginPage", method = RequestMethod.GET)
	//public ModelAndView loginPage() {
	public ModelAndView loginPage(Model model, HttpServletRequest req) {
		ModelAndView response = null;
		response = getModelAndView("appLogin");
		TradewareUser user = new TradewareUser();
		model.addAttribute("userForm", user);
		System.out.println("reddirect...........");
		return response;
	}

	// should be in common controller
	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}
}
