package wm.edu.cs420.Control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;

@Controller
public class ForcedLogoutController {
	

	   
	   public static void kickOut (HttpServletRequest request, HttpServletResponse response) {
	        /* some business logic code */
	      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      if (auth != null){    
	         new SecurityContextLogoutHandler().logout(request, response, auth);
	         try {
				new PersistentTokenBasedRememberMeServices().logout(request, response, auth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
	      }
	   }
}

