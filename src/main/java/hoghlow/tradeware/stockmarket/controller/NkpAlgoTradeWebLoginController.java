package hoghlow.tradeware.stockmarket.controller;

import org.springframework.stereotype.Controller;

@Controller
public class NkpAlgoTradeWebLoginController {

	/*@RequestMapping(value = "/appLogin", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" }, consumes = { "application/x-www-form-urlencoded" })
	public ModelAndView login(@ModelAttribute("userForm") NkpUser user, BindingResult result, Model model,
			HttpServletRequest req) {
		ModelAndView response = null;
		if (null != user.getUserName() && null != user.getPassword() && "admin".equals(user.getUserName())
				&& "password".equals(user.getPassword())) {
			req.getSession().setAttribute("user_verify", true);
			response = getModelAndView("home2");
		} else {
			response = getModelAndView("appLogin");
		}
		return response;
	}

	@RequestMapping(value = "/appLoginPage", method = RequestMethod.GET)
	public ModelAndView loginPage(Model model, HttpServletRequest req) {
		ModelAndView response = null;
		response = getModelAndView("appLogin");
		NkpUser user = new NkpUser();
		model.addAttribute("userForm", user);
		NkpAlgoLogger.printWithNewLine("reddirect...........");
		return response;
	}

	// should be in common controller
	public ModelAndView getModelAndView(String source) {
		ModelAndView modelAndView = new ModelAndView(source);
		// modelAndView.addObject("hostName", StockUtil.serverHostName);
		return modelAndView;
	}*/
}
