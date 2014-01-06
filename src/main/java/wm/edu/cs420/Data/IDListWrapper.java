package wm.edu.cs420.Data;

import java.util.ArrayList;
import java.util.List;

public class IDListWrapper {
	int[] list;
	
	public IDListWrapper(){
		list = new int[0];
	}
	public IDListWrapper(int[] list){
		this.list = list;
	}
	
	public int[] getList(){
		return this.list;
	}
	public void setList(int[] list){
		this.list=list;
	}
}
