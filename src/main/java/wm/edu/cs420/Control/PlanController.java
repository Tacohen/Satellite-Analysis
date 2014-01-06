package wm.edu.cs420.Control;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;


import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.common.Instruments;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;



import wm.edu.cs420.Data.Account;
import wm.edu.cs420.Data.CS420Service;
import wm.edu.cs420.Data.CustomPlanValidator;
import wm.edu.cs420.Data.MapWrapper;
import wm.edu.cs420.Data.PlanValidator;
import wm.edu.cs420.Data.InstrumentEditor;
import wm.edu.cs420.Data.StaticMethods;

/**
 * Controller for viewing & editing ICPlans
 */
@Controller
@SessionAttributes("ICPlan")
public class PlanController {

	
	@Autowired
	private final CS420Service cs420Service = null;

	@Autowired
	private PlanValidator icPlanValidator;
	
	@Autowired
	private CustomPlanValidator customPlanVal;
	/**
	 * The binder tells the controller how to convert instrument name to/from object
	 * @param binder
	 */
	@InitBinder
	private void instrumentBinder(WebDataBinder binder) {	    
	    binder.registerCustomEditor(Instrument.class, new InstrumentEditor(cs420Service));
	}
	
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
	 * Make instruments always available in model for this controller
	 * @return
	 */
	@ModelAttribute("instruments")
	public Instruments allInstruments() {
		Instruments instrs = new Instruments();
		instrs.getInstrumentList().addAll(this.cs420Service.getInstruments());
		return instrs;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/viewPlans")
	public ModelMap allPlans() {
		
		ModelMap planMap = new ModelMap();
		String username = CredentialsController.currentUserName();
		Account user = cs420Service.getUser(username);
		
		planMap.addAttribute("ICPlans", user.getPlans());
		return planMap;
	}
	
	/**
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/plan")
	public ModelAndView planHandler(HttpServletRequest request) {
		
		
		
		ModelAndView mv = new ModelAndView();
		
		
		// get the plan	
		String username = CredentialsController.currentUserName();
		Account user = cs420Service.getUser(username);
		int id = Integer.parseInt(request.getParameter("planID"));
		// get the plan from database
		ICPlan plan = user.getPlan(id);	
		if (plan==null){
			
			ForcedLogoutController.kickOut(request, null);
			mv.setViewName("home");
			return mv;
		}
		
		mv.getModel().put("ICPlan", plan);
		// get the plan from database
					
		Properties theProps = plan.getPredictorProperties();		
		mv.addObject("Props", new MapWrapper(theProps));
		List<DataVariableRef> refList = StaticMethods.getRefList(plan.getRef().getDataVariableRefs(), 
															plan.getReferenceDataVariableRefs());
		List<DataVariableRef> tgtList = StaticMethods.getRefList(plan.getTgt().getDataVariableRefs(), 
				 												plan.getTargetDataVariableRefs()); 
		mv.getModel().put("refList", refList);
		mv.getModel().put("tgtList", tgtList);
		mv.setViewName("planSetting");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/addPlan")
	public ModelAndView editPlan(@ModelAttribute("ICPlan") ICPlan plan, BindingResult result) {
		
		String username = CredentialsController.currentUserName();
		Account user = cs420Service.getUser(username);
		ModelAndView mv = new ModelAndView();
		
		// basic form validation using ICPlanValidator
		this.icPlanValidator.validate(plan, result);
		
		if (result.hasErrors()) {
			
			// go back to edit plan page w/ errors
			mv.addObject("ICPlan", plan);
			mv.setViewName("createStudy");
			return mv;
		}
		
		try {
			
			// store plan in database
			cs420Service.storeICPlan(plan, user);
		} catch (Exception e) {
			
			// go back to edit plan page w/ error
			mv.getModel().put("error", e.getLocalizedMessage());
			mv.addObject("ICPlan", plan);
			mv.setViewName("createStudy");
			return mv;
		}
		
		user = cs420Service.getUser(username);
		mv.getModel().put("ICPlans", user.getPlans());
		mv.setViewName("viewPlans");
				
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/edit")
	public ModelAndView modPlan(@ModelAttribute("ICPlan") ICPlan plan, BindingResult result, HttpServletRequest request) {
		
		
		ModelAndView mv = new ModelAndView();
		String username = CredentialsController.currentUserName();
		if(StaticMethods.invalidPlanAndUser(username, plan, cs420Service)){
			ForcedLogoutController.kickOut(request, null);
			mv.setViewName("home");
			return mv;
		}
		
		String errorMes = customPlanVal.validatePlan(plan);
		
		if(errorMes != null){
			
			mv.getModel().put("EventCount", errorMes);
			mv.setViewName("dataFromServer");
			return mv;
		}
		try {
			// store plan in database
			cs420Service.storeICPlan(plan);
			
		} catch (Exception e) {
			
			// go back to edit plan page w/ error
			//result.rejectValue("errorMessage","bad.icplan",e.getLocalizedMessage());
			plan = cs420Service.getICPlanByID(plan.getId());
			mv.getModel().put("errorTgtRef", e.getLocalizedMessage());
		}
		
		mv.getModel().put("ICPlan", plan);
		mv.setViewName("planSetting");
		Properties theProps = plan.getPredictorProperties();		
		mv.addObject("Props", new MapWrapper(theProps));
		List<DataVariableRef> refList = StaticMethods.getRefList(plan.getRef().getDataVariableRefs(), 
															plan.getReferenceDataVariableRefs());
		List<DataVariableRef> tgtList = StaticMethods.getRefList(plan.getTgt().getDataVariableRefs(), 
				 												plan.getTargetDataVariableRefs()); 
		mv.getModel().put("refList", refList);
		mv.getModel().put("tgtList", tgtList);	
		return mv;
	}
	
	
	@RequestMapping(value = "/createStudy", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest req) {
		model.addAttribute("ICPlan", new ICPlan());
		
		return "createStudy";
	} 
	
	@RequestMapping(value="/deletePlan", method= RequestMethod.POST)
	public ModelAndView deletePlan(HttpServletRequest req){
		
		int id = Integer.parseInt(req.getParameter("planID"));
		Account user = cs420Service.getUser(CredentialsController.currentUserName());
		ICPlan plan = user.getPlan(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("viewPlans");
		if(plan == null){
			
			ForcedLogoutController.kickOut(req, null);
			return mv;
		}
		
		cs420Service.deleteICPlan(plan);
		user = cs420Service.getUser(CredentialsController.currentUserName());
		mv.getModel().put("ICPlans", user.getPlans());
		return mv;
	}
	
}