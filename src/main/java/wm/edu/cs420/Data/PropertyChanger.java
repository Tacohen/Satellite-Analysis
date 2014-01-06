package wm.edu.cs420.Data;



import java.util.Enumeration;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;

public class PropertyChanger  {
	
	public String key;
	
	public String value;
	
	public static Map<String, String> createPropertyList(Properties properties){
		Enumeration<?> props = properties.propertyNames();
		String keyy;
		Map<String, String> propertyEditors = new HashMap<String, String>();
		for (Enumeration<?> e = props; e.hasMoreElements();){
			keyy = (String)e.nextElement();
			propertyEditors.put(keyy, properties.getProperty(keyy));
		}
		
		return propertyEditors;
	}
	
		
	
	public PropertyChanger(String key, String value){
		this.setKey(key);
		this.setValue(value);
	}
	
	public PropertyChanger(){
		
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
