package wm.edu.cs420.Control;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wm.edu.cs420.Data.ChartBuilder;
import wm.edu.cs420.Data.Login;
import wm.edu.cs420.Data.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private LoginValidator loginValidator;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest req) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "home";
	} 
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home2(Locale locale, Model model, HttpServletRequest req) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "home";
	} 
	
	@RequestMapping(value="/loggedIn", method = RequestMethod.GET)
	public String homeLoggedIn(Locale loacale, Model model, HttpServletRequest req){
		return "loggedIn";
	}
	
	//@RequestMapping(value = "/contact", method = RequestMethod.GET)
	@ModelAttribute("/login")
	public String showLogin(ModelMap model) {
			model.addAttribute("login",new Login());
			return "login";
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("error", "true");
		return "login";
 
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public String validate(@ModelAttribute("login") Login login, BindingResult result, ModelMap model) {
		this.loginValidator.validate(login, result);
		if (result.hasErrors()) {
			model.addAttribute("result", result);
			return "home";
		} else {
			return "loggedIn";
		}
	}
	
}
