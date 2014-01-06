package wm.edu.cs420.Data;

import gov.nasa.miic.common.ICPlan;

import gov.nasa.miic.persistence.MIICDAO;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(isolation=Isolation.REPEATABLE_READ)
@ContextConfiguration(locations ={ "file:src/main/webapp/WEB-INF/spring/applicationContext-hibernate.xml"})
public class HibernateDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MIICDAO miicDAO;
	
	
	
	//TODO: Test this!
	public void removeAccount(Account account){
		sessionFactory.getCurrentSession().delete(account);
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	

	public void storeAnalysis(Analysis analysis){
		sessionFactory.getCurrentSession().persist(analysis);
	}
	
	public void storeFilter(Filter filter){
		sessionFactory.getCurrentSession().persist(filter);
	}
	
	@SuppressWarnings("unchecked")
	public void removeAnalysisRelatedToPlan(ICPlan plan){
		
		List<Analysis> analysiss = (List<Analysis>) sessionFactory.getCurrentSession().createQuery("from Analysis where " +
				"PLAN_ID = :id").setLong("id", plan.getId()).list();
		for(Analysis analysis : analysiss){
			deleteAnalysis(analysis);
		}
	}
	
	public void deleteFilter(Filter filter){
		sessionFactory.getCurrentSession().delete(filter);
	}

	public void createRoleUser(Contact contact) {
		Account account = new Account(contact);
		sessionFactory.getCurrentSession().persist(account);
		
		UserRole role = new UserRole();
		role.setAuthority(UserRole.ROLE_USER);
		account.addUserRole(role);
		role.setUser(account);
		sessionFactory.getCurrentSession().persist(role);
	}

	public Account getUser(String username) {
		Account account = (Account)sessionFactory.getCurrentSession().createQuery("from USERS where "+
							"USERNAME = :name").setString("name", username).uniqueResult();
		
		return account;
	}
	
	public void updateAccount(Account account){
		sessionFactory.getCurrentSession().update(account);
	}

	public void storeICPlan(ICPlan plan, Account user) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("update ICPlan set USER_ID = :val, "+
						"owner = :username where id= :planId");
		query.setLong("val", user.getId());
		query.setString("username", user.getUserName());
		query.setLong("planId", plan.getId());
		query.executeUpdate();
		user.addPlan(plan);
	}

	public void updateDataRefs(ICPlan plan) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(plan.getTargetDataVariableRefs());
		sessionFactory.getCurrentSession().update(plan.getReferenceDataVariableRefs());
	}


	@SuppressWarnings("unchecked")
	public List<Analysis> getAnalysisForPlan(ICPlan plan) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Analysis where plan_id = :id order by name");
		query.setLong("id", plan.getId());
		return (List<Analysis>) query.list();
	}


	public Analysis getAnalysis(int analysisID) {
		
		return (Analysis) sessionFactory.getCurrentSession().get(Analysis.class, analysisID);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteAnalysis(Analysis analysis){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from filters where analysis_id = :id");
		query.setLong("id", analysis.getId());
		query.executeUpdate();
		sessionFactory.getCurrentSession().delete(analysis);
	}

	@SuppressWarnings({ "unchecked" })
	public void updateAnalysises(ICPlan plan) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Analysis where plan_id = :id order by name");
		query.setLong("id", plan.getId());
		List<Analysis> analysises = (List<Analysis>)query.list();
		for(Analysis analysis : analysises){
			for(Filter filter : analysis.getFilters()){
				if(!plan.getTargetDataVariableRefs().contains(filter.getDepend())){
					deleteFilter(filter);
				}
				else if(!plan.getReferenceDataVariableRefs().contains(filter.getIndepend())){
					deleteFilter(filter);
				}
			}
			if(!plan.getTargetDataVariableRefs().contains(analysis.getDepend())){
				deleteAnalysis(analysis);
			}
			else if(!plan.getReferenceDataVariableRefs().contains(analysis.getIndepend())){
				deleteAnalysis(analysis);
			}
		}
	}


	public void update(Filter filter) {
		
		
		sessionFactory.getCurrentSession().update(filter);
		
	}
	
	public void deleteFiltersByIds(int[] list) {
		for(int i = 0; i< list.length; ++i){
			deleteFilterById(list[i]);
		}
		
	}


	private void deleteFilterById(int i) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("delete from filters where id = :fid");
		query.setLong("fid", i);
		query.executeUpdate();
	}
	

}
