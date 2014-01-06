package wm.edu.cs420.Control;




import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.planprocessing.ICPlanExecutor;

import gov.nasa.miic.planprocessing.ICPlanExecutor.TaskType;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wm.edu.cs420.Data.Account;
import wm.edu.cs420.Data.CS420Service;


@Controller
public class PredictorController {


	@Autowired
	private final CS420Service cs420Service = null;
	
	
	@RequestMapping(value="predict", method= RequestMethod.POST)
	public ModelAndView predict(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		int id = Integer.parseInt(request.getParameter("planID"));
		
		Account user = cs420Service.getUser(CredentialsController.currentUserName());
		ICPlan plan = user.getPlan(id);
		if(plan == null){
			
			ForcedLogoutController.kickOut(request, null);
			return null;
		}
		mv.setViewName("dataFromServer");
		ICPlanExecutor planExec = cs420Service.getPlanExecutor(id);
		if(!planExec.isDone()){
			mv.getModel().put("EventCount", plan.getEvents().size());
			mv.getModel().put("Status", plan.getState());
			mv.getModel().put("Stop", "true");
			return mv;
		}
		
		
		planExec.executePlanUntilTask(TaskType.PREDICT);
		
		mv.setViewName("dataFromServer");
		
		planExec.waitUntilDone();
		plan = cs420Service.getICPlanByID(id);
		mv.getModel().put("EventCount", plan.getEvents().size());
		mv.getModel().put("State", plan.getState());
		return mv;
	}
	
	@RequestMapping(value = "locate", method= RequestMethod.POST)
	public ModelAndView locate(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		int id = Integer.parseInt(request.getParameter("planID"));
		ICPlan plan = cs420Service.getICPlanByID(id);
		
		if(plan == null || !plan.getOwner().equals(CredentialsController.currentUserName())){
			
			ForcedLogoutController.kickOut(request, null);
			return mv;
		}
		mv.setViewName("dataFromServer");
		ICPlanExecutor planExec = cs420Service.getPlanExecutor(id);
		if(!planExec.isDone()){
			mv.getModel().put("Status", plan.getState());
			mv.getModel().put("Stop", "true");
			return mv;
		}
		planExec.executePlanUntilTask(TaskType.LOCATE);
		planExec.waitUntilDone();
		mv.getModel().put("State", plan.getState());
		
		return mv;
	}
	
	@RequestMapping(value="collect", method=RequestMethod.POST)
	public ModelAndView collect(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		int id = Integer.parseInt(request.getParameter("planID"));
		ICPlan plan = cs420Service.getICPlanByID(id);
		if(plan == null || !plan.getOwner().equals(CredentialsController.currentUserName())){
			ForcedLogoutController.kickOut(request, null);
			mv.setViewName("dataFromServer");
			
			return mv;
		}
		
		ICPlanExecutor planExec = cs420Service.getPlanExecutor(id);
		if(!planExec.isDone()){
			mv.getModel().put("Status", plan.getState());
			mv.getModel().put("Stop", "true");
			return mv;
		}
		
		planExec.executePlanUntilTask(TaskType.COLLECT);
		planExec.waitUntilDone();
		
		mv.setViewName("eventTable");
		mv.getModel().put("ICPlan", plan);
		return mv;
	}
}
