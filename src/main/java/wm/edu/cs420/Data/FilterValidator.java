package wm.edu.cs420.Data;

public class FilterValidator {
	
	String regex = "[0-9]*.{0,1}[0-9]+|[0-9]+.{0,1}[0-9]*";
	
	public String validate(FormFilter filter){
		String errorMessage = "";
		
		if(filter.getDependMin().length() > 0 || filter.getDependMax().length() >0 ){
			if(!filter.getDependMin().matches(regex) || !filter.getDependMax().matches(regex)){
				errorMessage+= "Invalid Input For Target Variable"; 
			}
		}
		
		if(filter.getIndependMin().length() > 0 || filter.getIndependMax().length() > 0){
			if(!filter.getIndependMin().matches(regex) || !filter.getIndependMax().matches(regex)){
				if(errorMessage.length()>0)
					errorMessage+="; ";
				errorMessage+= "Invalid Input For Reference Variable"; 
			}
		}
		if(filter.getDependCV().length() > 0){
			if(!filter.getDependCV().matches(regex)){
				if(errorMessage.length()>0)
					errorMessage+="; ";
				errorMessage+= "Invalid Input For Target CVFilter "; 
			}
		}
		if(filter.getIndependCV().length() > 0){
			if(!filter.getIndependCV().matches(regex)){
				if(errorMessage.length()>0)
					errorMessage+="; ";
				errorMessage+= "Invalid Input For Reference CVFilter "; 
			}
		}
		if(filter.getDelta().length() > 0){
			if(!filter.getDelta().matches(regex)){
				System.out.println("invalidating");
				if(errorMessage.length()>0)
					errorMessage+="; ";
				errorMessage+= "Invalid Input For Difference"; 
			}
		}
		
		return errorMessage;
	}
}
