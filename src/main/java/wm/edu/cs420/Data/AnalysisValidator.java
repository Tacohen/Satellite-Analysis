package wm.edu.cs420.Data;

import org.apache.commons.lang.StringUtils;


public class AnalysisValidator{
	
	

	
	public String validate(Analysis target) {
		
		String errors = "";
		
		if(StringUtils.isBlank(target.getName())){
			errors += "Name cannot be blank";
		}
		if(target.getDepend() == null){
			if (errors.length()>0)
				errors+="; ";
			errors += "Select a target variable";
		}
		if(target.getIndepend() == null){
			if(errors.length() >0 )
				errors+="; ";
			errors+="Select a reference variable";
		}
		if (errors.length()==0)
			return null;
		
		return errors;
	}
}
