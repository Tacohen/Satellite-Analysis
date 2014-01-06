package wm.edu.cs420.Data;




import gov.nasa.miic.common.DataVariableRef;


import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

@Entity(name="filters")
public class Filter {

	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(targetEntity=Analysis.class)
	@JoinColumn(name="analysis_id")
	private Analysis analysis;
	
	@Column(name="delta", unique = false, nullable=true)
	private double delta;
	
	@Column(name = "depend_min", unique = false, nullable = true)
	private double dependMin;
	
	@Column(name = "depend_max", unique = false, nullable = true)
	private double dependMax;
	
	@Column(name = "independ_min", unique = false, nullable = true)
	private double independMin;
	
	@Column(name = "independ_max", unique = false, nullable = true)
	private double independMax;
	
	@Column(name = "depend_cv", unique = false, nullable = true)
	private double dependCV;
	
	@Column(name = "independ_cv", unique = false, nullable = true)
	private double independCV;
	
	@Column(name = "count_filter", unique = false, nullable = true)
	private boolean countFilter;
	
	@Column(name="has_depend_min_max")
	private boolean hasDependMinMax;
	
	@Column(name="has_independ_min_max")
	private boolean hasIndependMinMax;
	
	@Column(name="has_depend_cv")
	private boolean hasDependCV;
	
	@Column(name="has_independ_cv")
	private boolean hasIndependCV;
	
	@Column(name="has_delta")
	private boolean hasDelta;
	
	@Column(name="selected")
	private boolean selected;
	
	@Autowired
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="band", column=@Column(name="target_band"))
	})
	@AssociationOverrides({
		@AssociationOverride(name = "dataVariable", joinColumns = {
				@JoinColumn(name="target_variableName", referencedColumnName="name"),
				@JoinColumn(name="target_instrumentName", referencedColumnName="instrumentName")
		})
	})
	private DataVariableRef depend;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="band", column=@Column(name="reference_band"))
	})
	@AssociationOverrides({
		@AssociationOverride(name = "dataVariable", joinColumns = {
				@JoinColumn(name="reference_variableName", referencedColumnName="name"),
				@JoinColumn(name="reference_instrumentName", referencedColumnName="instrumentName")
		})
	})
	private DataVariableRef independ;

	public Filter(){
		this.setHasDelta(false);
		this.setHasDependCV(false);
		this.setHasDependMinMax(false);
		this.setHasIndependCV(false);
		this.setHasIndependMinMax(false);
		this.selected = false;
	}
	
	public Filter(FormFilter formFilter){
		
		if(formFilter.getDelta().length() > 0){
			this.delta = Double.parseDouble(formFilter.getDelta());
			this.setHasDelta(true);
		}
		if(formFilter.getDependCV().length() > 0){
			this.dependCV = Double.parseDouble(formFilter.getDependCV());
			this.hasDependCV = true;
		}
		if(formFilter.getIndependCV().length() > 0){
			this.independCV = Double.parseDouble(formFilter.getIndependCV());
			this.hasIndependCV = true;
		}
		
		if(formFilter.getDependMin().length() > 0){
			this.dependMax = Double.parseDouble(formFilter.getDependMax());
			this.dependMin = Double.parseDouble(formFilter.getDependMin());
			this.hasDependMinMax = true;
		}
		
		if(formFilter.getIndependMin().length()>0){
			this.independMax = Double.parseDouble(formFilter.getIndependMax());
			this.independMin = Double.parseDouble(formFilter.getIndependMin());
			this.hasIndependMinMax = true;
		}
		this.depend = formFilter.getDepend();
		this.independ = formFilter.getIndepend();
		this.countFilter = formFilter.getCountFilter();
		this.selected = formFilter.getSelected();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDependMin() {
		return dependMin;
	}

	public void setDependMin(double dependMin) {
		this.dependMin = dependMin;
	}

	public double getDependMax() {
		return dependMax;
	}

	public void setDependMax(double dependMax) {
		this.dependMax = dependMax;
	}

	public double getIndependMin() {
		return independMin;
	}

	public void setIndependMin(double independMin) {
		this.independMin = independMin;
	}

	public double getIndependMax() {
		return independMax;
	}

	public void setIndependMax(double independMax) {
		this.independMax = independMax;
	}

	public double getDependCV() {
		return dependCV;
	}

	public void setDependCV(double dependCV) {
		this.dependCV = dependCV;
	}

	public double getIndependCV() {
		return independCV;
	}

	public void setIndependCV(double independCV) {
		this.independCV = independCV;
	}

	public boolean getCountFilter() {
		return countFilter;
	}

	public void setCountFilter(boolean countFilter) {
		this.countFilter = countFilter;
	}

	public DataVariableRef getDepend() {
		return depend;
	}

	public void setDepend(DataVariableRef depend) {
		this.depend = depend;
	}

	public DataVariableRef getIndepend() {
		return independ;
	}

	public void setIndepend(DataVariableRef independ) {
		this.independ = independ;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public boolean getHasDependMinMax() {
		return hasDependMinMax;
	}

	public void setHasDependMinMax(boolean hasDependMinMx) {
		this.hasDependMinMax = hasDependMinMx;
	}

	public boolean getHasIndependMinMax() {
		return hasIndependMinMax;
	}

	public void setHasIndependMinMax(boolean hasIndependMinMax) {
		this.hasIndependMinMax = hasIndependMinMax;
	}

	public boolean getHasDependCV() {
		return hasDependCV;
	}

	public void setHasDependCV(boolean hasDependCV) {
		this.hasDependCV = hasDependCV;
	}

	public boolean getHasIndependCV() {
		return hasIndependCV;
	}

	public void setHasIndependCV(boolean hasIndependCV) {
		this.hasIndependCV = hasIndependCV;
	}

	public boolean getHasDelta() {
		return hasDelta;
	}

	public void setHasDelta(boolean hasDelta) {
		this.hasDelta = hasDelta;
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}	
}
