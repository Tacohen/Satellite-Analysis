package wm.edu.cs420.Data;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.planprocessing.ICPlanExecutor;
import gov.nasa.miic.planprocessing.MIICService;
import gov.nasa.miic.regression.Regression;

public class CS420Service implements  MIICService{

	@Autowired
	private HibernateDAO hibernateDAO; 
	
	@Autowired
	private MIICService miicService;
	
	
	@Override
	public void deleteICPlan(ICPlan arg0) {
		// TODO Auto-generated method stub
		hibernateDAO.removeAnalysisRelatedToPlan(arg0);
		miicService.deleteICPlan(arg0);
	}

	@Override
	public ICPlan getICPlanByID(Integer arg0) {
		// TODO Auto-generated method stub
		return miicService.getICPlanByID(arg0);
	}

	@Override
	public Collection<ICPlan> getICPlansByUser(String arg0) {
		// TODO Auto-generated method stub
		return miicService.getICPlansByUser(arg0);
	}

	@Override
	public Instrument getInstrumentByName(String arg0) {
		// TODO Auto-generated method stub
		return miicService.getInstrumentByName(arg0);
	}

	@Override
	public Collection<Instrument> getInstruments() {
		// TODO Auto-generated method stub
		return miicService.getInstruments();
	}

	@Override
	public ICPlanExecutor getPlanExecutor(int arg0) {
		// TODO Auto-generated method stub
		return miicService.getPlanExecutor(arg0);
	}

	@Override
	public void storeICPlan(ICPlan arg0) {
		// TODO Auto-generated method stub
		miicService.storeICPlan(arg0);
		hibernateDAO.updateAnalysises(arg0);
	}

	@Override
	public void storeInstrument(Instrument arg0) {
		// TODO Auto-generated method stub
		miicService.storeInstrument(arg0);
	}
	
	

	public void storeAnalysis(Analysis analysis){
		
		 hibernateDAO.storeAnalysis(analysis);
	}
	
	
	public void createRoleUser(Contact contact){
		hibernateDAO.createRoleUser(contact);
	}

	public Account getUser(String username) {
		// TODO Auto-generated method stub
		return hibernateDAO.getUser(username);
	}
	
	public void updateAccount(Account account){
		hibernateDAO.updateAccount(account);
	}

	public void storeICPlan(ICPlan plan, Account user) {
		miicService.storeICPlan(plan);
		hibernateDAO.storeICPlan(plan, user);
	}
	
	public List<Analysis> getAnalysisForPlan(ICPlan plan){
		return hibernateDAO.getAnalysisForPlan(plan);
	}
	
	public void updatePlan(ICPlan plan){
		hibernateDAO.updateDataRefs(plan);
		miicService.storeICPlan(plan);
	}

	public Analysis getAnalysis(int analysisID) {
		
		return hibernateDAO.getAnalysis(analysisID);
	}

	public void storeFilter(Filter filter) {
		hibernateDAO.storeFilter(filter);
		
	}

	public void updateFilters(Analysis analysis) {
		for(Filter filter: analysis.getFilters()){
			hibernateDAO.update(filter);
		}
		
	}

	public void deleteAnalysis(Analysis analysis) {
		// TODO Auto-generated method stub
		hibernateDAO.deleteAnalysis(analysis);
	}

	public void deleteFiltersByIds(int[] list) {
		hibernateDAO.deleteFiltersByIds(list);
	}
}
