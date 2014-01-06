package wm.edu.cs420.Control;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



public class CredentialsController{

		
        public static UserDetails currentUserDetails(){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                return (UserDetails) (principal instanceof UserDetails ? principal : null);
            }
     
            return null;
        }
        
        public static String currentUserName(){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                return ((UserDetails) (principal instanceof UserDetails ? principal : null)).getUsername();
            }
            return null;
        }
        
}