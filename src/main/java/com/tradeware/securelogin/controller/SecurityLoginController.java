package com.tradeware.securelogin.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tradeware.securelogin.service.EmailService;
import com.tradeware.securelogin.service.EmailUtil;
import com.tradeware.stockmarket.bean.UserDataBean;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;


@Controller
@SessionAttributes({ "currentTradewareSystemsUser" })
public class SecurityLoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)//not required
	public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "/login";
    }
//We don't define /login POST controller, it is provided by Spring Security
	@RequestMapping(value = "/login", method = RequestMethod.POST)//not required
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, @ModelAttribute("loginForm")  Model model) {
		//System.out.println(">>>>>>>>error>>>>>>>>>>>>>  "+error);
		//System.out.println(">>>>>>>>perform_login>>>>>>>>>>>>>  "+model);
		String errorMessge = null;
		if (error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}
		if (logout != null) {
			errorMessge = "You have been successfully logged out !!";
		}
		model.addAttribute("errorMessge", errorMessge);
		return  "redirect:/home2";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout=true";
	}

	// Temp start
	@RequestMapping(value = "/loginFail", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
		String errorMessge = null;
		if (error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}

		model.addAttribute("errorMessge", errorMessge);
		return "login";
	}
	 @Autowired
	    @Qualifier("userValidator")
	    private Validator userValidator;
	     
	    @InitBinder
	    private void initBinder(WebDataBinder binder) {
	       
	       // SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	        dateFormat.setLenient(false);
	        binder.registerCustomEditor( Date.class, "dateOfBirth",  new CustomDateEditor(dateFormat, true, 10));
	        binder.setValidator(userValidator);
	    }
	
	    @Autowired 
	 //   @Qualifier("emailService")
	    private EmailService emailService;
	    
	   
	    
	@Autowired
	private DatabaseHelper databaseHelper;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new UserDataBean());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String  registration(Model model, @ModelAttribute("userForm") UserDataBean userForm, BindingResult bindingResult, HttpServletRequest request) { 
		userValidator.validate(userForm, bindingResult);
		model.addAttribute("userForm", userForm);
		
		// Lookup user in database by e-mail
		UserDataBean userExists = databaseHelper.getUserDataBeanByEmail(userForm.getEmail());
		if (userExists != null) {
			model.addAttribute("errorMessge",
					Constants.EMAIL_ALREADY_REGISTRED);
			bindingResult.reject("email");
		} else {
			 userExists = databaseHelper.getUserDataBean(userForm.getUserId());
			if (userExists != null) {
				model.addAttribute("errorMessge",
						Constants.USER_ID_ALREADY_REGISTRED);
				bindingResult.reject("userId");
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		
		// Disable user until they click on confirmation link in email
		userForm.setEmailActivationInd(false);
	    // Generate random 36-character string token for confirmation link
		userForm.setConfirmationToken(UUID.randomUUID().toString());
	        
			
		String appUrl = request.getScheme() + "://" + request.getServerName();
		
		SimpleMailMessage registrationEmail = new SimpleMailMessage();
		registrationEmail.setTo(userForm.getEmail());
		registrationEmail.setSubject("Registration Confirmation");
		registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
				+ appUrl + "/confirm?token=" + userForm.getConfirmationToken());
		registrationEmail.setFrom("noreply@domain.com");
		//emailService.sendEmail(registrationEmail);
		
		
		//Approch 2to send mail
System.out.println("SimpleEmail Start");
		
	    String smtpHostServer = "smtp.gmail.com";//"smtp.example.com";
	    String emailID = "email_me@example.com";
	    String host = "smtp.gmail.com";


	    Properties props = System.getProperties();

	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.ssl.enable", "true");
	    props.put("mail.smtp.auth", "true");



	    //Session session = Session.getInstance(props, null);
	 // Get the Session object.// and pass username and password
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("TradewareSystems@gmail.com", "India$123");

            }

        });
	    
	    EmailUtil.sendEmail(session, userForm.getEmail(),"Registration Confirmation", "To confirm your e-mail address, please click the link below:\n"
				+ appUrl + "/confirm?token=" + userForm.getConfirmationToken());
		
		model.addAttribute("successMessage", "A confirmation e-mail has been sent to " + userForm.getEmail());
		
		
		//System.out.println("<<<<<<<<<<<<<<<<1>>>>>>>>>>>>>>>>>>>>>>>>>>"+userForm.getPassword());
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPasswordConfirm()));
		userForm.setDateTimeStamp(Calendar.getInstance().getTime());
		//System.out.println("<<<<<<<<<<<<<<<<1>>>>>>>>>>>>>>>>>>>>>>>>>>"+userForm.getPassword());
		databaseHelper.saveUserDataBean(userForm);
		//securityLoginUserService.autoLogin(userForm.getUserId(), userForm.getPassword());
		return "redirect:/home2";
	}
	
	
	// Process confirmation link
		/*@RequestMapping(value="/confirm", method = RequestMethod.GET)
		public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {
				
			UserDataBean user = databaseHelper.getUserDataBeanByConfirmationToken(token);
				
			if (user == null) { // No token found in DB
				modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
			} else { // Token found
				modelAndView.addObject("confirmationToken", user.getConfirmationToken());
			}
				
			modelAndView.setViewName("confirm");
			return modelAndView;		
		}*/
	
	@RequestMapping(value="/confirm", method = RequestMethod.GET)
	public String showConfirmationPage(Model model, @RequestParam("token") String token) {
			
		UserDataBean user = databaseHelper.getUserDataBeanByConfirmationToken(token);
			
		if (user == null) { // No token found in DB
			model.addAttribute("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { // Token found
			model.addAttribute("confirmationToken", user.getConfirmationToken());
		}
			
		return "/confirm";		
	}
		
		// Process confirmation link
		@RequestMapping(value="/confirm", method = RequestMethod.POST)
		//public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map requestParams, RedirectAttributes redir) {
		public /*ModelAndView*/String processConfirmationForm(/*ModelAndView modelAndView*/Model model, BindingResult bindingResult, @RequestParam Map requestParams, RedirectAttributes redir) {
					
			//modelAndView.setViewName("confirm");
			
			/*Zxcvbn passwordCheck = new Zxcvbn();
			
			Strength strength = passwordCheck.measure(requestParams.get("password"));
			
			if (strength.getScore() < 3) {
				bindingResult.reject("password");
				
				redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

				modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
				System.out.println(requestParams.get("token"));
				return modelAndView;
			}*/
		
			// Find the user associated with the reset token
			UserDataBean user = databaseHelper.getUserDataBeanByConfirmationToken(requestParams.get("token").toString());

			// Set new password
			//user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

			// Set user to enabled
			user.setEmailActivationInd(true);
			
			// Save user
			databaseHelper.saveUserDataBean(user);
			
			//modelAndView.addObject("successMessage", "Your password has been set!");
			model.addAttribute("successMessage", "Your password has been set!");
			//return modelAndView;	
			return "/confirm";
		}
}
