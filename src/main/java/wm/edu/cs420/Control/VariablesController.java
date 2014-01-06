
package wm.edu.cs420.Control;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import javax.servlet.http.HttpServletRequest;

import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.common.Instruments;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.stereotype.Controller;
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
import wm.edu.cs420.Data.DataVariableRefEditor;
import wm.edu.cs420.Data.InstrumentEditor;
import wm.edu.cs420.Data.PlanValidator;

/**
 * Controller for viewing & editing ICPlans
 */
@Controller
@SessionAttributes("ICPlan")
public class VariablesController {

	private static Logger logger = LoggerFactory.getLogger(VariablesController.class);
	
	@Autowired
	private final CS420Service cs420Service = null;

	@Autowired
	private PlanValidator icPlanValidator;
	
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
	 * The binder tells the controller how to convert instrument name to/from object
	 * @param binder
	 */
	@InitBinder
	private void instrumentBinder(WebDataBinder binder) {	    
	    binder.registerCustomEditor(Instrument.class, new InstrumentEditor(cs420Service));
	}
		
	
	@InitBinder
	private void dvrBinder(WebDataBinder binder) {	    
	    binder.registerCustomEditor(DataVariableRef.class, new DataVariableRefEditor(cs420Service));
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
	
	@RequestMapping(method=RequestMethod.POST, value="/addPlanVarsOnly")
	public ModelAndView editPlanNoViewChange(@ModelAttribute("ICPlan") ICPlan plan, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String username = CredentialsController.currentUserName();
		Account user = cs420Service.getUser(username);
		ICPlan origPlan = user.getPlan(plan.getId());
		
		if(origPlan == null){
			
			ForcedLogoutController.kickOut(request, null);
			return mv;
		}
		else{
			cs420Service.storeICPlan(plan);
		}
		mv.getModelMap().put("State", cs420Service.getICPlanByID(plan.getId()).getState());
		mv.setViewName("dataFromServer");
		return mv;
	
	}
	
}
