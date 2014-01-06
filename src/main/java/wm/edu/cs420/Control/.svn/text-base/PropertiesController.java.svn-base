package wm.edu.cs420.Control;



import java.util.Properties;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import javax.servlet.http.HttpServletRequest;

import gov.nasa.miic.common.ICPlan;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;



import wm.edu.cs420.Data.Account;
import wm.edu.cs420.Data.CS420Service;
import wm.edu.cs420.Data.MapWrapper;


/**
 * Controller for viewing & editing ICPlans
 */
@Controller
public class PropertiesController{

	private static Logger logger = LoggerFactory.getLogger(PropertiesController.class);
	
	@Autowired
	private final CS420Service cs420Service = null;
	
	
	@RequestMapping(method=RequestMethod.GET, value="/propsPage")
	public ModelAndView propsPage(HttpServletRequest request) {
		
		int id = Integer.parseInt(request.getParameter("planID"));
		ModelAndView modAndView = new ModelAndView();
		// get the plan from database
		Account user = cs420Service.getUser(CredentialsController.currentUserName());
		ICPlan plan = user.getPlan(id);		
		if(plan == null){
			
			ForcedLogoutController.kickOut(request, null);
			modAndView.setViewName("home");
			return modAndView;
		}
		Properties theProps = plan.getPredictorProperties();
		
	
		modAndView.setViewName("propsPage");
		modAndView.addObject("Props", new MapWrapper(theProps));
		modAndView.addObject("ID", id);
		return  modAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/postProps")
	public ModelAndView gimmeProps(@ModelAttribute("Props")MapWrapper props, BindingResult results, HttpServletRequest request)  {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dataFromServer");
		int id = Integer.parseInt(request.getParameter("ID"));
		Account user = cs420Service.getUser(CredentialsController.currentUserName());
		ICPlan plan = user.getPlan(id);
		
		if(plan == null){
			
			ForcedLogoutController.kickOut(request, null);
			return mv;
		}
		
		plan.setPredictorProperties(props.getMap());
		cs420Service.storeICPlan(plan);
		plan = cs420Service.getICPlanByID(plan.getId());
		mv.getModel().put("State", plan.getState());
		return mv;
	}
	
}
