
package wm.edu.cs420.Control;

import java.io.File;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.Date;

import java.util.List;

import java.util.TimeZone;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;


import wm.edu.cs420.Data.Analysis;
import wm.edu.cs420.Data.CS420Service;

import wm.edu.cs420.Data.FormFilter;
import wm.edu.cs420.Data.InstrumentEditor;
import wm.edu.cs420.Data.StaticMethods;

import gov.nasa.miic.common.DataVariableRef;

import gov.nasa.miic.common.ICEvent;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;

import gov.nasa.miic.graphics.DataVarPlotter;
import gov.nasa.miic.graphics.RegressionPlotter;

import gov.nasa.miic.planprocessing.ICPlanExecutor;
import gov.nasa.miic.regression.RegressionException;


/**
 * Annotation-driven <em>MultiActionController</em> that handles all non-form
 * URL's.
 *
 */
@Controller
public class EventsController {

	@Autowired
	private CS420Service cs420Service;

	@Autowired
	private DataVarPlotter dataVarPlotter;
	
	@Autowired
	private RegressionPlotter regressionPlotter;
	
	// thread for building var plots
	Future<?> varPlotter = null;
	
	ExecutorService pool = Executors.newFixedThreadPool(1);
	
	/**
	 * The binder tells the controller how to convert to and from special types like "Date"
	 * @param binder
	 */
	@InitBinder
	private void gmtDateBinder(WebDataBinder binder) {
	    // The date format to parse or output your dates
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	    // Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(sdf, true);
	    // Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	    binder.registerCustomEditor(Calendar.class, editor);
	}
	
	/**
	 * The binder tells the controller how to convert instrument name to object
	 * @param binder
	 */
	@InitBinder
	private void instrumentBinder(WebDataBinder binder) {	    
	    binder.registerCustomEditor(Instrument.class, new InstrumentEditor(cs420Service));
	}

	
	/**
	 * Handler for displaying events
	 *
	 * <p>Note that this handler returns a plain {@link ModelMap} object instead of
	 * a ModelAndView, thus leveraging convention-based model attribute names.
	 * It relies on the RequestToViewNameTranslator to determine the logical
	 * view name based on the request URL: "/instruments.do" -&gt; "instruments".
	 *
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping(method=RequestMethod.GET, value="/events")
	public ModelAndView eventsHandler(final HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		final String username = CredentialsController.currentUserName();
		// get the planID from query parameter
		int id = Integer.parseInt(request.getParameter("planID"));

		// Give the model the plan object & execution state (from executor)
		final ICPlanExecutor planExec = cs420Service.getPlanExecutor(id);
		
		if(StaticMethods.invalidPlanAndUser(username, planExec.getPlan(), cs420Service)){
			model.setViewName("dataFromServer");
			return model;
		}
				
		// generate plot for each var for each event in separate thread
		if (varPlotter == null || varPlotter.isDone()) {
			
			varPlotter = pool.submit(new Runnable() { 
				public void run() {
					
					generatePlotsToDir(planExec, username, request.getServletContext());
				}
			});
		}
		//Object mon = new Object();
		//model.getModel().put("executing",!planExec.isDone());
		ICPlan plan = planExec.getPlan();
		model.getModel().put("ICPlan", plan);
		model.getModel().put("username", username);
		
		String defaultFilterPlot ="";
		String regInfo="";
		try {
			List<String> items = StaticMethods.generateFilterPlot(plan, username, 
					request.getServletContext(), regressionPlotter, null);
			defaultFilterPlot=items.get(0);
			regInfo=items.get(1);
		} catch (RegressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.getModel().put("FilterInfo", regInfo);
		model.getModel().put("filterImage", defaultFilterPlot);
		model.setViewName("imageScrollers");
		FormFilter filter= new FormFilter();
		filter.setDepend(StaticMethods.getFirstVar(plan, false));
		filter.setIndepend(StaticMethods.getFirstVar(plan, true));
		model.getModel().put("filter", filter);
		List<Analysis> analysises = cs420Service.getAnalysisForPlan(planExec.getPlan());
		model.getModel().put("Analysises", analysises);
		model.getModel().put("ICAnalysis", new Analysis());
		model.getModel().put("EncodedProps", StaticMethods.encodeProps(plan));
		return model;
	}
	
	
	private void generatePlotsToDir(ICPlanExecutor planExec, String username, ServletContext context) {
		
		ICPlan plan = planExec.getPlan();
		String path = context.getRealPath("WEB-INF/images") + File.separatorChar+ username+ File.separatorChar + "plan"+plan.getId()+
				File.separatorChar +plan.getEventPredictionRanges().get(0).getBeginDate().getTime()+""+
				plan.getEventPredictionRanges().get(0).getEndDate().getTime()+File.separatorChar+StaticMethods.encodeProps(plan)+
				File.separatorChar + plan.getTgt().getName()+plan.getRef().getName()+File.separatorChar;
		String pathRef = path+"verifyReferences"+File.separatorChar;
		String pathTgt = path+"verifyTargets"+File.separatorChar;
		checkAndCreatePath(pathRef);
		checkAndCreatePath(pathTgt);
		for (ICEvent e : plan.getEvents()) {
			
			if (e.getState() == ICEvent.State.DATA_ACQUIRED) {
				
				// for each reference var
				for (DataVariableRef referenceVar : plan.getReferenceDataVariableRefs()) {
					
					File chart = new File(pathRef+referenceVar.getName()+e.getId()+".png");
					
					if (!chart.exists()) {
						// plot the mean data values
						try {
							dataVarPlotter.plotDataMeanVar(chart, e.getReferenceData(), referenceVar);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				// for each target var
				for (DataVariableRef targetVar : plan.getTargetDataVariableRefs()) {
							
					File chart = new File(pathTgt + targetVar.getName()+e.getId() +".png");
						
					if (!chart.exists()) {
						// plot the mean data values
						try {
							dataVarPlotter.plotDataMeanVar(chart, e.getTargetData(), targetVar);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}		
		}
	}

	private void checkAndCreatePath(String pathRef) {
		File checkRef = new File(pathRef);
		
		if (!checkRef.isDirectory()){
			checkRef.mkdirs();
		}
	}
	
}
