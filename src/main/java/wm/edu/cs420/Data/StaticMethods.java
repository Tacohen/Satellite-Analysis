package wm.edu.cs420.Data;

import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.graphics.RegressionPlotter;
import gov.nasa.miic.regression.Regression;
import gov.nasa.miic.regression.RegressionException;
import gov.nasa.miic.regression.RegressionFilter;
import gov.nasa.miic.regression.RegressionHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import opendap.dap.DAP2Exception;
import opendap.dap.parser.ParseException;

public class StaticMethods {
	

	
	public static List<String> generateFilterPlot(ICPlan plan, String username,  ServletContext context, RegressionPlotter regressionPlotter,
												List<FormFilter> filters) throws RegressionException{
		
		
		String path = "images" + File.separatorChar+username+ File.separatorChar + 
				"plan"+plan.getId() + File.separatorChar+ plan.getEventPredictionRanges().get(0).getBeginDate().getTime()+
				plan.getEventPredictionRanges().get(0).getEndDate().getTime()+File.separatorChar + encodeProps(plan)+
				File.separatorChar+ "filterImages"+File.separatorChar+plan.getTgt().getName()+plan.getRef().getName()
				+File.separatorChar;
		
		String realPath = context.getRealPath("WEB-INF") + File.separatorChar + path;
		DataVariableRef tgt;
		DataVariableRef ref;
		
		Regression reg;
		List<RegressionFilter> filts; 
		String name;
		if(filters != null){
			EncodeToFilters info = encodeFilters(filters);
			tgt = filters.get(0).getDepend();
			ref = filters.get(0).getIndepend();
			name =tgt.getName()+ref.getName()+info.encodedString;
			filts = info.regFilters;
		}
		else{
			tgt = getFirstVar(plan, false);
			ref = getFirstVar(plan, true);
			name = tgt.getName()+ref.getName();
			filts = new ArrayList<RegressionFilter>();
		}
		
		
		File imageFile = new File(realPath+name +".png");
		
		
		checkAndCreatePath(realPath);
		
		reg = createRegression(plan, tgt, ref, filts);
		if(imageFile.exists()){
			List<String> items = new ArrayList<String>();
			items.add(path + name +".png");
			items.add(regInfo(reg));
			return items;
		}
		
		try {
			regressionPlotter.plotRegressionHeatmap(imageFile, reg, tgt.getName(),
						ref.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		List<String> items = new ArrayList<String>();
		items.add( path + name +".png");
		items.add(regInfo(reg));
		return items;
	}
	
	
	public static String generatePlotForVars(ICPlan plan, String username, RegressionPlotter regressionPlotter,
			DataVariableRef tgt, DataVariableRef ref, String path, List<RegressionFilter> filters) throws RegressionException{
		Regression reg;
		if(filters == null){
			reg = createRegression(plan, tgt, ref, new ArrayList<RegressionFilter>());
		}
		else{
			reg = createRegression(plan, tgt, ref, filters);
		}
		checkAndCreatePath(path);
		
		File imageFile = new File(path+tgt.getName() +ref.getName());

		try {
			regressionPlotter.plotRegressionHeatmap(imageFile, reg, tgt.getName(),
					ref.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tgt.getName() +ref.getName();
	}
	
	public static List<String> generateAnalysisPlot(Analysis analysis, String username, 
			RegressionPlotter plotter,ServletContext context, List<FormFilter> filters) throws RegressionException{
		ICPlan plan = analysis.getPlan();
		String path = "images" + File.separatorChar+username+ File.separatorChar + 
				"plan"+plan.getId() + File.separatorChar+ plan.getEventPredictionRanges().get(0).getBeginDate().getTime()+
				plan.getEventPredictionRanges().get(0).getEndDate().getTime()+File.separatorChar + encodeProps(plan)+
				File.separatorChar+ "filterImages"+File.separatorChar+plan.getTgt().getName()+plan.getRef().getName()
				+File.separatorChar;
		String filename =  analysis.getDepend().getName() + analysis.getIndepend().getName();
		String realPath = context.getRealPath("WEB-INF") + File.separatorChar + path;
		List<RegressionFilter> regFilters;
		if(filters != null){
			EncodeToFilters wrapper = encodeFiltersAnalysis(filters);
			regFilters = wrapper.regFilters;
			filename += wrapper.encodedString;
			
		}
		else{
			regFilters = new ArrayList<RegressionFilter>();
		}
		Regression reg  = createRegression(plan, analysis.getDepend(), analysis.getIndepend(), regFilters);
		checkAndCreatePath(realPath);
		String imageName = filename+".png";
		File imageFile = new File(realPath+imageName);
		if(imageFile.exists()){
			List<String> items = new ArrayList<String>();
			items.add(path + filename +".png");
			items.add(regInfo(reg));
			return items;
		}
		try {
			plotter.plotRegressionHeatmap(imageFile, reg, analysis.getDepend().getName(),
					analysis.getIndepend().getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> items = new ArrayList<String>();
		items.add(path+imageName);
		items.add(regInfo(reg));
		return items;
		
	}
	
	public static String regInfo(Regression reg){
		String info = "Intercept = "+reg.getResults().getParameterEstimate(0);
		info +="; Slope = "+reg.getResults().getParameterEstimate(1);
		info +="; R-squared = "+reg.getResults().getRSquared();
		return info;
	}
	
	public static Regression createRegression(ICPlan plan, DataVariableRef depend, 
			DataVariableRef independ, List<RegressionFilter> filters)  throws RegressionException {
		
		Regression reg = null;
		
		try {
			reg = RegressionHelper.LinearRegression(plan, depend, independ, filters);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAP2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reg;
	}
	
	
	
	public static List<DataVariableRef> getRefList(List<DataVariableRef> vars, Set<DataVariableRef> toExclude)
	{	
		for (DataVariableRef item : toExclude){
			
			vars.remove(item);
		}
		return vars;
	}
	
	public static boolean invalidPlanAndUser(String username, ICPlan plan, CS420Service cs420Service){
		Account user = cs420Service.getUser(username);
		return (user.getPlan(plan.getId()) == null);
	}
	
	
	public static DataVariableRef getFirstVar(ICPlan plan, boolean isRef){
		if(isRef){
			for (DataVariableRef refVar : plan.getReferenceDataVariableRefs()){
					
				return refVar;
				
			}
		}
	
		for (DataVariableRef refVar : plan.getTargetDataVariableRefs()){
			return refVar;
				
		}
		return null;
	}
	
	
	
	
	 @SuppressWarnings("rawtypes")
	public static String encodeProps(ICPlan plan){
		String encodeProps = "props";
		Properties props = plan.getPredictorProperties();
		Enumeration e = props.propertyNames();
	    while (e.hasMoreElements()) {
	      encodeProps += props.getProperty((String) e.nextElement());
	    }
	    return encodeProps;
	}
	 
	 
	 
	public static EncodeToFilters encodeFilters(List<FormFilter> filters){
		String encodeString = "";
		List<RegressionFilter> regs = new ArrayList<RegressionFilter>();
		for(FormFilter filter : filters){
			encodeString+= "d"+filter.getDelta()+"dm"+filter.getDependMin()+"dm"+filter.getDependMax()+"im"+
					filter.getIndependMin()+"im"+filter.getIndependMax()+"dc"+filter.getDependCV()+"ic"+
					filter.getIndependCV();
			if(filter.getCountFilter()){
				encodeString+="countFilter";
			}
			regs.addAll(filter.createFilters());
		}
		EncodeToFilters toReturn = new EncodeToFilters(regs, encodeString);
		return toReturn;
	}
	
	public static EncodeToFilters encodeFiltersAnalysis(List<FormFilter> filters){
		String encodeString = "";
		List<RegressionFilter> regs = new ArrayList<RegressionFilter>();
		for(FormFilter filter : filters){
			if(filter.getDelta().length() >0){
				encodeString+= "delta"+filter.getDelta();
			}
			if(filter.getDependMin().length() > 0) {
				encodeString += "depMin"+filter.getDependMin()+"depMax"+filter.getDependMax();
			}
			if(filter.getIndependMin().length() > 0){
				encodeString+= "indMin"+filter.getIndependMin() + "indMax"+ filter.getIndependMax();
			}
			if(filter.getDependCV().length() > 0){
				encodeString+="dc"+filter.getDependCV();
			}
			if(filter.getIndependCV().length() > 0){
				encodeString+="ic"+filter.getIndependCV();
			}
			if(filter.getCountFilter()){
				encodeString+="countFilter";
			}
			regs.addAll(filter.createFilters());
		}
		EncodeToFilters toReturn = new EncodeToFilters(regs, encodeFileName(encodeString));
		return toReturn;
	}
	
	public static String encodeFileName(String filename){
		int intRep=0;
		for(int i = 0; i < filename.length(); ++i){
			intRep += Character.getNumericValue(filename.charAt(i));
		}
		String newName = Integer.toHexString(intRep);
		return newName;
	}
	
	public static void checkAndCreatePath(String pathRef) {
		File checkRef = new File(pathRef);
		
		if (!checkRef.isDirectory()){
			checkRef.mkdirs();
		}
	}
	
	private static class EncodeToFilters{
		
		List<RegressionFilter> regFilters;
		String encodedString;
		
		public EncodeToFilters(List<RegressionFilter> regFilters, String encodedString){
			this.regFilters = regFilters;
			this.encodedString = encodedString;
		}
	}
	public static void deleteImages(String username, int planID, HttpServletRequest request){
		String path = request.getServletContext().getRealPath("WEB-INF/images/");
		File images = new File(path+File.separatorChar + username + File.separatorChar +"plan"+ planID);
		deleteFiles(images);
	}
	private static void deleteFiles(File images){
		if(images.isDirectory()){
			File files[] = images.listFiles();
			for(File file : files){
				deleteFiles(file);
			}
		}
		images.delete();
	}
}
