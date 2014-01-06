package wm.edu.cs420.Control;




import java.util.ArrayList;
import java.util.List;



import javax.servlet.http.HttpServletRequest;


import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.graphics.RegressionPlotter;
import gov.nasa.miic.regression.RegressionException;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wm.edu.cs420.Data.Analysis;
import wm.edu.cs420.Data.AnalysisValidator;
import wm.edu.cs420.Data.DataVariableRefEditor;
import wm.edu.cs420.Data.Filter;
import wm.edu.cs420.Data.FilterValidator;
import wm.edu.cs420.Data.FormFilter;
import wm.edu.cs420.Data.IDListWrapper;
import wm.edu.cs420.Data.InstrumentEditor;
import wm.edu.cs420.Data.ListWrapper;
import wm.edu.cs420.Data.StaticMethods;
import wm.edu.cs420.Data.CS420Service;

@Controller
public class RegressionController {

	@Autowired
	private final CS420Service cs420Service = null;
	
	@Autowired
	private RegressionPlotter regressionPlotter;
	
	@Autowired
	private AnalysisValidator analysisValidator;
	
	@Autowired
	private FilterValidator filterValidator;
	
	@InitBinder
	private void instrumentBinder(WebDataBinder binder) {	    
	    binder.registerCustomEditor(Instrument.class, new InstrumentEditor(cs420Service));
	}
		
	
	@InitBinder
	private void dvrBinder(WebDataBinder binder) {	    
	    binder.registerCustomEditor(DataVariableRef.class, new DataVariableRefEditor(cs420Service));
	}
	
	@RequestMapping(method=RequestMethod.POST, value="deleteAnalysis")
	public ModelAndView deleteAnalysis(HttpServletRequest req){
		int analysisID = Integer.parseInt(req.getParameter("analysisID"));
		ModelAndView mv = new ModelAndView();
		Analysis analysis = cs420Service.getAnalysis(analysisID);
		String username = CredentialsController.currentUserName();
		ICPlan plan = analysis.getPlan();
		if(!plan.getOwner().equals(username)){
			ForcedLogoutController.kickOut(req, null);
			return mv;
		}
		cs420Service.deleteAnalysis(analysis);
		mv.setViewName("dataFromServer");
		return mv;
	}
	@RequestMapping(method=RequestMethod.POST, value="deleteFilters")
	public ModelAndView deleteFilters(@ModelAttribute("PossFilters") IDListWrapper wrapper, HttpServletRequest req){
		ModelAndView mv = new ModelAndView();
		int analysisID = Integer.parseInt(req.getParameter("analysisID"));
		Analysis analysis = cs420Service.getAnalysis(analysisID);
		ICPlan plan = analysis.getPlan();
		String username = CredentialsController.currentUserName();
		if(plan == null || !plan.getOwner().equals(username)){
			ForcedLogoutController.kickOut(req, null);
			return mv;
		}
		cs420Service.deleteFiltersByIds(wrapper.getList());
		
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="plotWithFilters")
	public ModelAndView plotRegWithFilters(@ModelAttribute("PossFilters") IDListWrapper wrapper, HttpServletRequest req){
		ModelAndView mv = new ModelAndView();
		int analysisId = Integer.parseInt(req.getParameter("analysisID"));
		
		Analysis analysis = cs420Service.getAnalysis(analysisId);
		String username = CredentialsController.currentUserName();
		ICPlan plan = analysis.getPlan();
		if(plan == null||!plan.getOwner().equals(username)){
			ForcedLogoutController.kickOut(req, null);
			return mv;
		}
		List<FormFilter> filters = new ArrayList<FormFilter>();
		int filterIDs[] = wrapper.getList();
		
		analysis.deselectAll();
		
		for(int i =0; i <filterIDs.length; ++i ){
			Filter filter = analysis.getFilter(filterIDs[i]);
			
			if(filter== null){
				ForcedLogoutController.kickOut(req, null);
				return mv;
			}
			filter.setSelected(true);
			//cs420Service.updateFilter(filter);
			FormFilter f = new FormFilter();
			f.setSelected(true);
			f.setFormFilter(filter);
			filters.add(f);
		}
		
		cs420Service.updateFilters(analysis);
		String plotLoc="";
		String regInfo ="";
		try {
			List<String> items =StaticMethods.generateAnalysisPlot(analysis, username, regressionPlotter, req.getServletContext(), filters);
			plotLoc = items.get(0);
			regInfo = items.get(1);
		} catch (RegressionException e) {
			mv.getModel().put("AnalysisError", e.getLocalizedMessage());
		}
		mv.getModel().put("AnalysisImage", plotLoc);
		mv.getModel().put("Analysis", analysis);
		mv.getModel().put("PlotInfo", regInfo);
		mv.getModel().put("PossFilters", new ListWrapper(analysis.getFormFilters()));
		mv.getModel().put("CurrFilters", new IDListWrapper());
		mv.setViewName("analysisView");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="regPlotInitial")
	public ModelAndView plotRegression(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		int analysisID = Integer.parseInt(req.getParameter("analysisID"));
		Analysis analysis = cs420Service.getAnalysis(analysisID);
		String username = CredentialsController.currentUserName();
		ICPlan plan = analysis.getPlan();
		if (plan==null || !plan.getOwner().equals(username)){
			mv.setViewName("dataFromServer");
			return mv;
		}
		
		String defaultFilterPlot="";
		String regInfo="";
		try {
			//StaticMethods.generateAnalysisPlot(analysis, username, regressionPlotter, req.getServletContext(), filters)
			List<String> items= StaticMethods.generateAnalysisPlot(analysis, username, regressionPlotter, 
					req.getServletContext(), analysis.getSelectedFilters());
			defaultFilterPlot = items.get(0);
			regInfo = items.get(1);
		} catch (RegressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mv.getModel().put("AnalysisImage", defaultFilterPlot);
		mv.getModel().put("Analysis", analysis);
		mv.getModel().put("PlotInfo", regInfo);
		mv.getModel().put("PossFilters", new ListWrapper(analysis.getFormFilters()));
		mv.getModel().put("CurrFilters", new IDListWrapper());
		mv.setViewName("analysisView");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="saveAnalysis")
	public ModelAndView saveAnalysis(@ModelAttribute("ICAnalysis") Analysis analysis, BindingResult result,
													HttpServletRequest req){
		int id = Integer.parseInt(req.getParameter("planID"));
		ModelAndView mv = new ModelAndView();
		String username = CredentialsController.currentUserName();
		ICPlan plan = cs420Service.getICPlanByID(id);
		if(plan == null || !plan.getOwner().equals(username)){
			mv.setViewName("dataFromServer");
			return mv;
		}
		analysis.setPlan(plan);
		String error = analysisValidator.validate(analysis);
		
		if(error != null){
			mv.getModel().put("ICPlan", plan);
			mv.getModel().put("ICAnalysis", analysis);
			mv.getModel().put("Errors", error);
			mv.getModel().put("Analysises", cs420Service.getAnalysisForPlan(plan));
			mv.setViewName("analysis");
			return mv;
		}
	
		if(!plan.getReferenceDataVariableRefs().contains(analysis.getIndepend()) ||
					!plan.getTargetDataVariableRefs().contains(analysis.getDepend())){
				
			
			return mv;	
		}
			
		cs420Service.storeAnalysis(analysis);
		mv.getModel().put("ICPlan", plan);
		mv.getModel().put("ICAnalysis", new Analysis());
		mv.getModel().put("Analysises", cs420Service.getAnalysisForPlan(plan));
		mv.setViewName("analysis");
		return mv;
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="createFilter")
	public ModelAndView createFilter(@ModelAttribute("filter") FormFilter filter, HttpServletRequest req){
		
		return filterHelper(filter, req, true);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="viewWithFilter")
	public ModelAndView plotRegressionWithFilter(@ModelAttribute("filter") FormFilter filter, HttpServletRequest req){
		
		return filterHelper(filter, req, false);
	}
	
	
	
	
	
	
	private ModelAndView filterHelper(FormFilter filter, HttpServletRequest req, boolean toSave){
		ModelAndView model = new ModelAndView();
		
		int id = Integer.parseInt(req.getParameter("planID"));
		
		String username = CredentialsController.currentUserName();
		ICPlan plan = cs420Service.getICPlanByID(id);
		if (plan == null || !plan.getOwner().equals(username)){
			
			ForcedLogoutController.kickOut(req, null);
			return model;
		}
		filter.removeWhiteSpace();
		FormFilter toBind = filter;
		model.setViewName("filterForm");
		String errorMessage = filterValidator.validate(filter);
		
		if (errorMessage.length() > 0){
			
			FormFilter filter2 = new FormFilter();
			filter2.setDepend(filter.getDepend());
			filter2.setIndepend(filter.getIndepend());
			model.getModel().put("filterError", errorMessage);
			filter= filter2;
		}
		
		List<FormFilter> filters = new ArrayList<FormFilter>();
		filters.add(filter);
		String imageLoc="";
		String regInfo = "";
		try{
			
			List<String> items=StaticMethods.generateFilterPlot(plan, username, req.getServletContext(), regressionPlotter, filters);
			imageLoc = items.get(0);
			regInfo = items.get(1);
			if(toSave){
				Analysis analysis = cs420Service.getAnalysis(Integer.parseInt(req.getParameter("analysisID")));
				Filter filterDB = new Filter(filter);
				filterDB.setAnalysis(analysis);
				filterDB.setSelected(true);
				cs420Service.storeFilter(filterDB);
				analysis.addFilter(filterDB);
				model.getModel().put("Analysis", analysis);
			}
		}catch(RegressionException e){
			
			filters.clear();
			DataVariableRef depend = filter.getDepend();
			DataVariableRef independ = filter.getIndepend();
			filter = new FormFilter();
			filter.setDepend(depend);
			filter.setIndepend(independ);
			filters.add(filter);
			model.getModel().put("filterError", e.getLocalizedMessage());
			
			try {
				List<String> items= StaticMethods.generateFilterPlot(plan, username, req.getServletContext(), regressionPlotter, filters);
				imageLoc = items.get(0);
				regInfo = items.get(1);
			} catch (RegressionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
		model.getModel().put("filterImage", imageLoc);
		model.getModel().put("FilterInfo", regInfo);
		model.getModel().put("ICPlan", plan);
		model.getModel().put("filter", toBind);
		
		return model;
	}
	
}
