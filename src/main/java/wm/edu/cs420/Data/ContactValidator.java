package wm.edu.cs420.Data;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.apache.commons.validator.routines.*;

@Component
public class ContactValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Contact.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object contact, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "contact.firstName","Required Field.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "contact.lastName", "Required Field.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "contact.email","Required Field.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "contact.userName", "RequiredField.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "contact.password", "Required Field.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "contact.confirmPassword", "Required Field.");
		Contact con = (Contact) contact;
		String[] regexs = new String[] {"a*"};
		RegexValidator regex = new RegexValidator(regexs, false);
		EmailValidator validate = EmailValidator.getInstance();
		
		if(!validate.isValid(con.getEmail())){
			errors.rejectValue("email", "contact.email", "Invalid Email");
		}
		
		if(!con.getIsValid()){
			
			errors.rejectValue("password", "contact.password", "Passwords Do Not Match");
		}
		
	}
	public void addDupError(Object contact, Errors errors){
		errors.rejectValue("userName", "contact.userName", "Account Name Already Exists");
	}

}
