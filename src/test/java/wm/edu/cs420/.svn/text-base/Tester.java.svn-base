package wm.edu.cs420;

import static org.junit.Assert.*;

import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.EventPredictionRange;
import gov.nasa.miic.common.ICEvent;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.graphics.DataVarPlotter;
import gov.nasa.miic.graphics.RegressionPlotter;
import gov.nasa.miic.opendap.OPeNDAPFileCache;
import gov.nasa.miic.persistence.MIICDAO;
import gov.nasa.miic.planprocessing.ICPlanExecutor;
import gov.nasa.miic.planprocessing.ICPlanExecutorFactory;
import gov.nasa.miic.planprocessing.ICPlanExecutor.TaskType;
import gov.nasa.miic.planprocessing.MIICService;
import gov.nasa.miic.regression.CVFilter;
import gov.nasa.miic.regression.CountFilter;
import gov.nasa.miic.regression.DeltaFilter;
import gov.nasa.miic.regression.RangeFilter;
import gov.nasa.miic.regression.Regression;
import gov.nasa.miic.regression.RegressionException;
import gov.nasa.miic.regression.RegressionFilter;
import gov.nasa.miic.tle.CGITLE;
import gov.nasa.miic.tle.NoTLEException;
import gov.nasa.miic.tle.TLEFactory;
import gov.nasa.miic.util.MiicUtils;
import gov.nasa.miic.util.RegressionHelper;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import java.util.Vector;


import opendap.dap.NoSuchVariableException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;


@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration(locations ={  "file:src/main/webapp/WEB-INF/spring/miicConfig.xml"})
public class Tester extends AbstractJUnit4SpringContextTests {

	@Autowired
	private Instrument modisaqua = null;
	
	@Autowired
	private Instrument goes13 = null;
	
	@Autowired
	private DataVarPlotter dataVarPlotter;
	
	@Autowired
	private RegressionPlotter regressionPlotter;
	
	@Autowired
	private MIICService miicService;
	
	int issue;
	@Test
	 @Repeat(5)
	public void newTest(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		// compute events in January 1 2011
		Calendar start = MiicUtils.getGMTCalendar(2011,0,1,0,0,0,0);
		Calendar end = MiicUtils.getGMTCalendar(2011,0,2,0,0,0,0);
		System.out.println("modisaqua/goes13 intercalibration from ["+sdf.format(start.getTime())+ "] to ["+sdf.format(end.getTime())+"]");
						
		// ICPlan has all the details of the study: the instruments, events, etc.
		ICPlan plan = new ICPlan(goes13, modisaqua);
		plan.setId(999);
					
		// Tell the plan what date range to collect events for
		plan.addEventPredictionRange(new EventPredictionRange(start, end));
					
					// Tell the plan what vars to collect
								
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
		
		miicService.storeICPlan(plan);
		issue = plan.getRef().getDataVariableRefs().size();
		
		System.out.println(issue);
	}
	
	@Test
	public void failing(){
		ICPlan plan = miicService.getICPlanByID(1);
		//System.out.println("Too Many Entries here " + plan.getTargetDataVariableRefs().size());
		System.out.println("Should not be " + plan.getRef().getDataVariableRefs().size());
		assertTrue(plan.getRef().getDataVariableRefs().size() == 73);
		
	}
}
