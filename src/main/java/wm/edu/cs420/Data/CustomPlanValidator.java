package wm.edu.cs420.Data;

import gov.nasa.miic.common.ICPlan;

public class CustomPlanValidator {
	
	String regex ="\\s*";
	
	public String validatePlan(ICPlan plan){
		if(plan.getRef().getName().matches(regex) || plan.getTgt().getName().matches(regex)||
				plan.getEventPredictionRanges().get(0).getBeginDate().getTime().toString().matches(regex) ||
				plan.getEventPredictionRanges().get(0).getEndDate().getTime().toString().matches(regex) ||
				plan.getRef().getName().length() == 0 || plan.getTgt().getName().length()  == 0||
				plan.getEventPredictionRanges().get(0).getBeginDate().getTime().toString().length()  == 0 ||
				plan.getEventPredictionRanges().get(0).getEndDate().getTime().toString().length() == 0 ){
			System.out.println("failed");
			return "Cannot Have Empty Field For Dates or Instruments";
		}
		if(plan.getEventPredictionRanges().get(0).getBeginDate().getTime().after(
				plan.getEventPredictionRanges().get(0).getEndDate().getTime())){
			System.out.println("fail <");
			return "Start Date Must Be Before End Date";
		}
		return null;
	}
}
