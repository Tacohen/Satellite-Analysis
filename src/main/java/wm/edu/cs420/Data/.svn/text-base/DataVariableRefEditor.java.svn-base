package wm.edu.cs420.Data;

import gov.nasa.miic.common.DataVariableRef;
import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.planprocessing.MIICService;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


public class DataVariableRefEditor extends PropertyEditorSupport {

	private MIICService miicService;
	
	public DataVariableRefEditor(MIICService miicService) {
		this.miicService = miicService;
	}

	@Override
	public void setAsText(String name) throws IllegalArgumentException {
		// name should be: instrumentName:varName[:bandName]
		String[] vals = StringUtils.delimitedListToStringArray(name, ":");
		
		Instrument inst = miicService.getInstrumentByName(vals[0]);
		if (vals.length == 3) {
			this.setValue(inst.getDataVariableRef(vals[1], vals[2]));
		} else {
			this.setValue(inst.getDataVariableRef(vals[1]));
		}
	}

	@Override
	public String getAsText() {
		if (this.getValue() == null) return "";
		
		DataVariableRef dvr = (DataVariableRef) this.getValue();
		return dvr.getID();
	}
}
