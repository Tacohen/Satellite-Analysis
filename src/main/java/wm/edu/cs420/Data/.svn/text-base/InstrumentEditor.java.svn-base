package wm.edu.cs420.Data;

import gov.nasa.miic.common.Instrument;
import gov.nasa.miic.planprocessing.MIICService;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class InstrumentEditor extends PropertyEditorSupport {

	private MIICService miicService;
	
	public InstrumentEditor(MIICService miicService) {
		this.miicService = miicService;
	}

	@Override
	public void setAsText(String name) throws IllegalArgumentException {
		Instrument inst = miicService.getInstrumentByName(name);
		this.setValue(inst);
	}

	@Override
	public String getAsText() {
		if (this.getValue() == null) return "";
		
		Instrument inst = (Instrument) this.getValue();
		return inst.getName();
	}
}
