package wm.edu.cs420.Data;

import java.util.ArrayList;
import java.util.List;

public class ListWrapper{

	List<FormFilter> filters;
	
	public ListWrapper(){
		filters = new ArrayList<FormFilter>();
	}
	
	public ListWrapper(List<FormFilter> filters){
		this.filters = filters;
	}
	
	
	public List<FormFilter> getList(){
		return filters;
	}
	
	public void setList(List<FormFilter> filters){
		this.filters = filters;
	}
	
	public void set(int index, FormFilter filter){
		filters.set(index, filter);
	}
	public void add(FormFilter filter){
		this.filters.add(filter);
	}
	public FormFilter get(int index){
		return filters.get(index);
	}
	
	public boolean getValueAt(int index){
		for(FormFilter filter: filters){
			if (filter.getId() == index){
				return filter.getSelected();
			}
		}
		return false;
	}
}
