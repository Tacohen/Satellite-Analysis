package wm.edu.cs420.Data;

import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.regression.CVFilter;
import gov.nasa.miic.regression.CountFilter;
import gov.nasa.miic.regression.DeltaFilter;
import gov.nasa.miic.regression.RangeFilter;
import gov.nasa.miic.regression.RegressionFilter;

import java.util.List;
import java.util.Vector;

public class FormFilter {

	private String delta;
	
	private String dependMin;
	
	private String dependMax;
	
	private String dependCV;
	
	private String independMin;
	
	private String independMax;
	
	private String independCV;
	
	private DataVariableRef depend;
	
	private DataVariableRef independ;
	
	private boolean countFilter;
	
	private int id;
	private boolean selected;
	
	public FormFilter(){
		delta ="";
		dependMin ="";
		dependMax ="";
		dependCV="";
		independCV="";
		independMin = "";
		independMax = "";
		countFilter =false;	
		this.selected=false;
	}
	
	public void setFormFilter(Filter filter){
		if(filter.getHasDelta()){
			this.delta += filter.getDelta();
		}
		if(filter.getHasDependCV()){
			this.dependCV+= filter.getDependCV();
		}
		if(filter.getHasIndependCV()){
			this.independCV+= filter.getIndependCV();
		}
		if(filter.getHasDependMinMax()){
			this.dependMin+= filter.getDependMin();
			this.dependMax+= filter.getDependMax();
		}
		if(filter.getHasIndependMinMax()){
			this.independMin+= filter.getIndependMin();
			this.independMax+= filter.getIndependMax();
		}
		this.id = filter.getId();
		this.depend = filter.getDepend();
		this.independ = filter.getIndepend();
		this.countFilter = filter.getCountFilter();
		this.selected = filter.getSelected();
	}
	public String getDelta() {
		
		return delta;
	}
	public void setDelta(String delta) {
		
		this.delta = delta;
	}

	public String getDependMin() {
		return dependMin;
	}

	public void setDependMin(String min) {
		this.dependMin = min;
	}

	public String getDependMax() {
		return dependMax;
	}

	public void setDependMax(String max) {
		this.dependMax = max;
	}

	public String getDependCV() {
		return dependCV;
	}

	public void setDependCV(String dependCV) {
		this.dependCV = dependCV;
	}

	public String getIndependCV() {
		return independCV;
	}

	public void setIndependCV(String independCV) {
		this.independCV = independCV;
	}
	
	
	public void removeWhiteSpace(){
		String regex="\\s*";
		
		dependMin= dependMin.replace(regex, "");
		dependMax= dependMax.replace(regex, "");
		independMin = independMin.replace(regex, "");
		independMax = independMax.replace(regex, "");
		dependCV = dependCV.replace(regex, "");
		independCV= independCV.replace(regex, "");
		delta= delta.replace(regex, "");
	}
	
	
	public List<RegressionFilter> createFilters(){
		List<RegressionFilter> filters = new Vector<RegressionFilter>();
		
		System.out.println(this.getCountFilter());
		
		if (!this.dependMax.equals("") && !this.dependMin.equals("")){
			
			filters.add(new RangeFilter(this.depend, null, Double.parseDouble(this.dependMin),
					Double.parseDouble(this.dependMax)));
		}
		if(!this.independMax.equals("") && !this.independMin.equals("")){
			
			filters.add(new RangeFilter(null, this.independ, Double.parseDouble(this.independMin), 
					Double.parseDouble(this.independMax)));
		}
		if (!this.dependCV.equals("")){
			
			filters.add(new CVFilter(this.depend, Double.parseDouble(this.dependCV), true));
		}
		if (!this.independCV.equals("")){
			
			filters.add(new CVFilter(this.independ, Double.parseDouble(this.independCV), false));
		}
		if(!this.delta.equals("")){
			
			filters.add(new DeltaFilter(this.depend, this.independ, Double.parseDouble(this.delta)));
		}
		if(this.countFilter){
			
			filters.add(new CountFilter(this.depend, this.independ));
		}
		
		return filters;
	}
	
	

	public DataVariableRef getIndepend() {
		return independ;
	}

	public void setIndepend(DataVariableRef independ) {
		this.independ = independ;
	}

	public DataVariableRef getDepend() {
		return depend;
	}

	public void setDepend(DataVariableRef depend) {
		this.depend = depend;
	}

	public boolean getCountFilter() {
		return countFilter;
	}

	public void setCountFilter(boolean countFilter) {
		this.countFilter = countFilter;
	}

	public String getIndependMin() {
		return independMin;
	}

	public void setIndependMin(String independMin) {
		this.independMin = independMin;
	}

	public String getIndependMax() {
		return independMax;
	}

	public void setIndependMax(String independMax) {
		this.independMax = independMax;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDependCV2(){
		if(this.dependCV.length() == 0){
			return "-";
		}
		return dependCV;
	}
	public String getIndependCV2(){
		if(this.dependCV.length() == 0){
			return "-";
		}
		return independCV;
	}
	
	public String getDelta2(){
		if(delta.length() == 0){
			return "-";
			
		}
		return delta;
	}
	
	public void setSelected(boolean value){
		this.selected = false;
	}
	
	
	public boolean getSelected(){
		return this.selected;
	}
	
	public String getSelected2(){
		if(this.selected){
			return "t";
		}
		else{
			return null;
		}
	}
}
