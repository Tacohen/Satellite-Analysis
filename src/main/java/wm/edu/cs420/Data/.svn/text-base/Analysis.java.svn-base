package wm.edu.cs420.Data;


import java.util.ArrayList;
import java.util.List;

import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.ICPlan;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;


@Entity(name="Analysis")
public class Analysis {
	
	@Id
	@Column(name="analysis_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@ManyToOne(targetEntity = ICPlan.class)
	@JoinColumn(name = "plan_id", referencedColumnName = "id", insertable=false, updatable=false, nullable=false)
	@OrderBy("name")
	private ICPlan plan;
	
	@OneToMany(targetEntity= Filter.class, fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="analysis_id", referencedColumnName="analysis_id")
	private List<Filter> filters;

	@Column(nullable = false)
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
	
	@Column(nullable = false)
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
	
	@Column(name="plan_id")
	private int planId;

	@Column(name="name")
	private String name;
	
	public ICPlan getPlan() {
		return plan;
	}

	public void setPlan(ICPlan plan) {
		this.plan = plan;
		this.planId = plan.getId();
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getFilterCount(){
		return filters.size();
	}
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public List<FormFilter> getFormFilters(){
		List<FormFilter> formFilters = new ArrayList<FormFilter>();
		for(Filter filter: filters){
			FormFilter formFilter = new FormFilter();
			formFilter.setFormFilter(filter);
			formFilters.add(formFilter);
		}
		return formFilters;
	}
	
	public Filter getFilter(int id){
		
		for(Filter filter: filters){
			if(filter.getId() == id){
				
				filter.setSelected(true);
				return filter;
			}
		}
		return null;
		
	}

	public void deselectAll() {
		for(Filter filter: filters){
			filter.setSelected(false);
		}
	}
	
	public List<FormFilter> getSelectedFilters(){
		List<FormFilter> formFilters = new ArrayList<FormFilter>();
		for(Filter filter: filters){
			if(filter.getSelected()){
				FormFilter formFilter = new FormFilter();
				formFilter.setFormFilter(filter);
				formFilters.add(formFilter);
			}
		}
		if(formFilters.size() ==0){
			return null;
		}
		return formFilters;
	}

	public void addFilter(Filter filter) {
		this.filters.add(filter);
		
	}
}
