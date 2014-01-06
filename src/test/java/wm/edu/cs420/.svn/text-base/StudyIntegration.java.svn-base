package wm.edu.cs420;

import static org.junit.Assert.*;

import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.EventPredictionRange;
import gov.nasa.miic.common.ICEvent;
import gov.nasa.miic.common.ICPlan;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.graphics.DataVarPlotter;
import gov.nasa.miic.graphics.RegressionPlotter;
import gov.nasa.miic.planprocessing.ICPlanExecutor;
import gov.nasa.miic.planprocessing.ICPlanExecutor.TaskType;
import gov.nasa.miic.planprocessing.MIICService;
import gov.nasa.miic.regression.DeltaFilter;
import gov.nasa.miic.regression.RangeFilter;
import gov.nasa.miic.regression.Regression;
import gov.nasa.miic.regression.RegressionFilter;

import gov.nasa.miic.util.MiicUtils;
import gov.nasa.miic.regression.RegressionHelper;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import java.util.Vector;


import opendap.dap.NoSuchVariableException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/miicConfig.xml"})
public class StudyIntegration extends AbstractJUnit4SpringContextTests {
		
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
	
	@Test
	public void modisAqua_GOES13_Intercal_Jan1_2011() throws IOException, NoSuchVariableException, InterruptedException, ClassNotFoundException {
	
		try {
		
						
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			
			// 1) Create the plan with a target & ref instrument
			//    Target = goes13, Reference = modisaqua
			ICPlan plan = new ICPlan(goes13, modisaqua);
						
			// 2) Save the plan using the service interface
			miicService.storeICPlan(plan);
			
			// *** After saving, he plan now has a unique ID and also has 
			// *** Event Predictor Props
			assertTrue( "no plan ID",plan.getId() != null && plan.getId() > 0);
			assertFalse( "no predictor props", plan.getPredictorProperties().isEmpty());
			
			// Print the props
			Properties props = plan.getPredictorProperties();
			PrintWriter writer = new PrintWriter(System.out);
			props.store(writer, "Event Predictor Properties");
			
			// 3) Set the begin/end dates for event prediction using a single
			//    EventPredictionRange object.
			//    We have data available for January 1 2011
			Calendar start = MiicUtils.getGMTCalendar(2011,0,1,0,0,0,0);
			Calendar end = MiicUtils.getGMTCalendar(2011,0,2,0,0,0,0);
			plan.addEventPredictionRange(new EventPredictionRange(start, end));

			// print the plan
			System.out.println("Plan before event predition:");
			plan.print(System.out);
	
			
			// 4) We now have everything we need to predict events!
			//    Use the executor from the service interface.
			//
			ICPlanExecutor planExec = miicService.getPlanExecutor(plan.getId());	
			System.out.println("Predict events...");			
			planExec.executePlanUntilTask(TaskType.PREDICT);
			
			// *** prediction happens in a different thread -- wait for that to finish
			// *** (your web-app can call planExec.isDone() to poll instead)
			planExec.waitUntilDone();
			
			// *** if prediction worked, the state should be EVENTS_PREDICTED
			// *** if it failed, state will be PREDICTION_FAILED and the error message 
			// *** will be in plan.getReason()			
			assertTrue ("Prediction failed: "+plan.getReason(),
						plan.getState() == ICPlan.State.EVENTS_PREDICTED);
			
			// print the plan
			System.out.println("Plan after event predition:");
			plan.print(System.out);
						
			// 5) Specify which variables to collect
			// NOTE: You can get the list of all available vars from the instrument 
			// modisaqua.getDataVariableRefs()
			
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
			
			
			// 6) We now have everything we need to locate/collect data
			//    They are two separate steps but you can probably treat them as 
			//    one step since data you can't find is basically the same thing
			//    as data you can't download!
			
			
			System.out.println("Locating data...");
			planExec.executePlanUntilTask(TaskType.LOCATE);
			planExec.waitUntilDone();
			
			assertTrue("Can't locate "+plan.getReason(),plan.getState() == ICPlan.State.DATA_LOCATED);
		
			// print the plan
			System.out.println("Plan after event location:");
			plan.print(System.out);
			
			System.out.println("Acquiring data...");
			planExec.executePlanUntilTask(TaskType.COLLECT);
			planExec.waitUntilDone();
			
			assertTrue("Can't acquire "+plan.getReason(),plan.getState() == ICPlan.State.DATA_ACQUIRED);			
			
			
			// 7) We now have data! Analysis can begin.
			//    First, let's just plot all the vars for all the events
			//    just to see what they looks like
						
			int event = 0;
			for (ICEvent e : plan.getEvents()) {
							
				// for each reference var
				for (DataVariableRef referenceVar : plan.getReferenceDataVariableRefs()) {
								
					File chart = new File("/tmp/Jan_1_2011_Event_"+(event)+"_"+plan.getRef().getName()+"_"+referenceVar.getName()+".png");
								
					// plot the mean data values
					try {
						dataVarPlotter.plotDataMeanVar(chart, e.getReferenceData(), referenceVar);
					} catch (NoSuchVariableException ex) {
						logger.warn(ex);
					}
				}
							
				// for each target var
				for (DataVariableRef targetVar : plan.getTargetDataVariableRefs()) {
								
					File chart = new File("/tmp/Jan_1_2011_Event_"+(event)+"_"+plan.getTgt().getName()+"_"+targetVar.getName()+".png");
							
					// plot the mean data values
					try {
						dataVarPlotter.plotDataMeanVar(chart, e.getTargetData(), targetVar);
					} catch (NoSuchVariableException ex) {
						logger.warn(ex);
					}
				}
							
				++event;
			}
			
			// 8) Add analysis filters. 
			// 
			// NOTE: You will need to make Entities & a DAO to store these to the 
			//       database w/ Hibernate. You should also make a service
			//       interface to simplify how the web-tier uses analysis features!!!
			// 
			
			List<RegressionFilter> filters = new Vector<RegressionFilter>();

			// The filters you can use are called DeltaFilter & RangeFilter
			// DeltaFilter: filter on the difference between target & reference var
			// RangeFilter: filter on the absolute range of one individual var

			// Example: filter where difference between rgt & ref RelativeAzimuth > 20 degrees
			// NOTE: target instrument supplies the "dependent" variable, 
			//       reference supplies the "independent" variable
			
			DataVariableRef targetRA = goes13.getDataVariableRef("RelativeAzimuth");
			DataVariableRef referenceRA = modisaqua.getDataVariableRef("RelativeAzimuth");
			
			DeltaFilter razFilter = new DeltaFilter(targetRA, referenceRA, 20);
			filters.add(razFilter);
			
			// Another filter: Filter out regression points where reference EV_250 values are outside the range (0,1)
			// EV_250 is from the reference instrument which is independent.
			// Use null for the other variable.
			DataVariableRef filterVar = modisaqua.getDataVariableRef("EV_250_Aggr1km_RefSB","1");	
			RangeFilter rangeFilter = new RangeFilter(null, filterVar, 0,1);
			filters.add(rangeFilter);
			
			// 9) Perform the linear regression.
			// 
			// NOTE: Again, need Entities, DAO, Service interface....
						
			// Example: linear regression on vars: EV_250_Aggr1km_RefSB 1 vs channel_data 0
			//
			DataVariableRef tgtVar = goes13.getDataVariableRef("channel_data","0");
			DataVariableRef refVar = modisaqua.getDataVariableRef("EV_250_Aggr1km_RefSB","1");
	
			Regression reg = RegressionHelper.LinearRegression( plan, tgtVar, refVar, filters );

			// plot regression & save as PNG
			String chartname = "/tmp/"+modisaqua.getName()+ "_vs_" + goes13.getName() + "_Jan1_2011_Regression.png";
			File imageFile = new File(chartname);
			regressionPlotter.plotRegressionHeatmap(imageFile, reg, goes13.getName()+ " "+tgtVar.getName(),
					modisaqua.getName()+" "+refVar.getName());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
