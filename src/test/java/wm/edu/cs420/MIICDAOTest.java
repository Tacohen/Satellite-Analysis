package wm.edu.cs420;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;


import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.EventPredictionRange;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.util.MiicUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import wm.edu.cs420.Data.Account;
import wm.edu.cs420.Data.Analysis;
import wm.edu.cs420.Data.CS420Service;
import wm.edu.cs420.Data.Contact;
import wm.edu.cs420.Data.HibernateDAO;
import wm.edu.cs420.Data.UserRole;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/applicationContext-hibernate.xml","file:src/main/webapp/WEB-INF/spring/miicConfig.xml" })
public class MIICDAOTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private final HibernateDAO hibernateDAO = null;

	@Autowired
	private Instrument modisaqua = null;

	@Autowired
	private Instrument goes13 = null;
	
	@Autowired
	private CS420Service CS420Service = null;

	@Test
	public void StoreUserTest(){

		List<UserRole> r = new ArrayList<UserRole>();
		UserRole role = new UserRole();
		role.setUserRoleId(0);
		r.add(role);

		Contact user1 = new Contact();
		user1.setEmail("test@test.wm.edu");
		user1.setFirstName("joe");
		user1.setLastName("acosta");
		user1.setPassword("joee");
		user1.setUserName("joe_acosta");

		ICPlan p = new ICPlan();
		p.setId(1);
		p.setName("new_plan");
		p.setOwner("joe_acosta");
		p.setCreated(Calendar.getInstance());
		p.setPredictorProperties(null);
		p.setReferenceDataVariableRefs(null);
		p.setTgt(null);

		try{
			hibernateDAO.createRoleUser(user1);
		}catch (Exception e){
			//user already added
		}

		Account user = hibernateDAO.getUser("joe_acosta");

		assertTrue(user.getFirstName().equals("joe"));
		assertTrue(user.getFirstName().equals(user1.getFirstName()));

	}

	@Test
	public void StoreAnalysisAndPlanTest(){
		Analysis a = new Analysis();

		Calendar start = MiicUtils.getGMTCalendar(2011,0,1,0,0,0,0);
		Calendar end = MiicUtils.getGMTCalendar(2011,0,2,0,0,0,0);

		// ICPlan has all the details of the study: the instruments, events, etc.
		ICPlan plan = new ICPlan(goes13, modisaqua);
		plan.setId(1);
		plan.setName("new_plan");
		

		// Tell the plan what date range to collect events for
		plan.addEventPredictionRange(new EventPredictionRange(start, end));

		// From modisaqua use these vars: TIME,Latitude,Longitude,EV_250_Aggr1km_RefSB (band 1),SensorAzimuth,SensorZenith,SolarZenith,SolarAzimuth
		Set<DataVariableRef> refVars = new HashSet<DataVariableRef>();
		refVars.add(modisaqua.getDataVariableRef("TIME"));
		refVars.add(modisaqua.getDataVariableRef("Latitude"));
		refVars.add(modisaqua.getDataVariableRef("Longitude"));			
		refVars.add(modisaqua.getDataVariableRef("SensorAzimuth"));
		refVars.add(modisaqua.getDataVariableRef("SensorZenith"));
		refVars.add(modisaqua.getDataVariableRef("SolarZenith"));
		refVars.add(modisaqua.getDataVariableRef("SolarAzimuth"));
		refVars.add(modisaqua.getDataVariableRef("RelativeAzimuth")); 
		refVars.add(modisaqua.getDataVariableRef("EV_250_Aggr1km_RefSB","1"));

		plan.setReferenceDataVariableRefs(refVars);

		// form goes13 use these vars: TIME, latitude, longitude, channel_data (band 0)
		Set<DataVariableRef> tgtVars = new HashSet<DataVariableRef>();
		tgtVars.add(goes13.getDataVariableRef("TIME"));
		tgtVars.add(goes13.getDataVariableRef("latitude"));
		tgtVars.add(goes13.getDataVariableRef("longitude"));
		tgtVars.add(goes13.getDataVariableRef("ViewingZenith")); 
		tgtVars.add(goes13.getDataVariableRef("RelativeAzimuth")); 
		tgtVars.add(goes13.getDataVariableRef("channel_data","0"));

		plan.setTargetDataVariableRefs(tgtVars);

		Account user = hibernateDAO.getUser("joe_acosta");
		
		plan.setOwner(user.getUserName());
		
		CS420Service.storeICPlan(plan, user);
		
		assertTrue(CS420Service.getUser("joe_acosta").getPlan(1).getName().equals("new_plan"));
	}
	
	@Test
	public void testAnalysis(){
		Calendar start = MiicUtils.getGMTCalendar(2011,0,1,0,0,0,0);
		Calendar end = MiicUtils.getGMTCalendar(2011,0,2,0,0,0,0);
		
		// ICPlan has all the details of the study: the instruments, events, etc.
				ICPlan plan = new ICPlan(goes13, modisaqua);
				plan.setId(1);
				plan.setName("new_plan");
				

				// Tell the plan what date range to collect events for
				plan.addEventPredictionRange(new EventPredictionRange(start, end));

				// From modisaqua use these vars: TIME,Latitude,Longitude,EV_250_Aggr1km_RefSB (band 1),SensorAzimuth,SensorZenith,SolarZenith,SolarAzimuth
				Set<DataVariableRef> refVars = new HashSet<DataVariableRef>();
				refVars.add(modisaqua.getDataVariableRef("TIME"));
				refVars.add(modisaqua.getDataVariableRef("Latitude"));
				refVars.add(modisaqua.getDataVariableRef("Longitude"));			
				refVars.add(modisaqua.getDataVariableRef("SensorAzimuth"));
				refVars.add(modisaqua.getDataVariableRef("SensorZenith"));
				refVars.add(modisaqua.getDataVariableRef("SolarZenith"));
				refVars.add(modisaqua.getDataVariableRef("SolarAzimuth"));
				refVars.add(modisaqua.getDataVariableRef("RelativeAzimuth")); 
				refVars.add(modisaqua.getDataVariableRef("EV_250_Aggr1km_RefSB","1"));

				plan.setReferenceDataVariableRefs(refVars);

				// form goes13 use these vars: TIME, latitude, longitude, channel_data (band 0)
				Set<DataVariableRef> tgtVars = new HashSet<DataVariableRef>();
				tgtVars.add(goes13.getDataVariableRef("TIME"));
				tgtVars.add(goes13.getDataVariableRef("latitude"));
				tgtVars.add(goes13.getDataVariableRef("longitude"));
				tgtVars.add(goes13.getDataVariableRef("ViewingZenith")); 
				tgtVars.add(goes13.getDataVariableRef("RelativeAzimuth")); 
				tgtVars.add(goes13.getDataVariableRef("channel_data","0"));

				plan.setTargetDataVariableRefs(tgtVars);

				Account user = hibernateDAO.getUser("joe_acosta");
				
				plan.setOwner(user.getUserName());
		
		Analysis a = new Analysis();
		a.setId(0);
		a.setName("test_analysis");
		a.setPlan(plan);
		//a.setPlan(CS420Service.getUser("joe_acosta").getPlan(1));
		CS420Service.storeAnalysis(a);
		assertTrue(CS420Service.getAnalysis(0).getName().equals("test_analysis"));
		
	}

}
