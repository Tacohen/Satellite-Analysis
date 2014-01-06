package wm.edu.cs420.Data;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Contact.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object login, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "login.userName","Required Field.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "login.password", "Required Field.");
	}

}
