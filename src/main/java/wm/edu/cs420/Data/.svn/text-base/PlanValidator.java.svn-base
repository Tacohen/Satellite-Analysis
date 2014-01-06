package wm.edu.cs420.Data;



import gov.nasa.miic.common.ICPlan;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PlanValidator implements Validator {

	
	@Override
	public boolean supports(Class<?> clazz) {
		return ICPlan.class.isAssignableFrom(clazz);
	}

	/**
	 * To be a valid ICPlan, it must have a target & reference instrument 
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "tgt", "required.tgt","Target instrument is required.");
		ValidationUtils.rejectIfEmpty(errors, "ref", "required.ref","Reference instrument is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name","Plan name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventPredictionRanges[0].beginDate.time", "required.begindate","Begin date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventPredictionRanges[0].endDate.time", "required.enddate","End date is required.");
	}

}
