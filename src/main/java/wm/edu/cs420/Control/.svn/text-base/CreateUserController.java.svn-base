package wm.edu.cs420.Control;


import gov.nasa.miic.planprocessing.MIICService;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import wm.edu.cs420.Data.CS420Service;
import wm.edu.cs420.Data.Contact;
import wm.edu.cs420.Data.ContactValidator;


@Controller
public class CreateUserController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private final MIICService cs420Service = null;
	
	@Autowired
	private ContactValidator contactValidator;
	
	@Autowired
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest req) {
		
		return "createUser";
	} 
	
	@RequestMapping(value = "/addContact", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("contact") Contact contact, BindingResult result, ModelMap model) {
		
		this.contactValidator.validate(contact, result);
		if (result.hasErrors()) {
			model.addAttribute("result", result);
			logger.info("Invalid Entry");
			return "createUser";
		}
		try{
			String password = passwordEncoder.encodePassword(contact.getPassword(), null);
			contact.setPassword(password);
			((CS420Service) cs420Service).createRoleUser(contact);
			return "home";
		}
		 catch (DataIntegrityViolationException	e){
			 this.contactValidator.addDupError(contact, result);
			 return "createUser";
		 }
		
	}

	/**
	 * @param model
	 * @return
	 */
	//@RequestMapping(value = "/contact", method = RequestMethod.GET)
	@ModelAttribute("contact")
	public String showContact(ModelMap model) {
		model.addAttribute("contact", new Contact());
		return "contact";
	}
}
