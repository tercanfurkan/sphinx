package modelWrappers;

import java.util.HashMap;
import java.util.Map;

import models.PsScadaHourly;
import models.PumpScadaHourly;

public class ScadaHrWrapper {
	
	public ScadaHrWrapper() {
		this.psSHMap = new HashMap<String, PsScadaHourly>();
		this.pumpSHMap = new HashMap<String, PumpScadaHourly>();
	}
	
	public ScadaHrWrapper(Map<String, PsScadaHourly> psSHMap, Map<String, PumpScadaHourly> pumpSHMap) {
		this.psSHMap = psSHMap;
		this.pumpSHMap = pumpSHMap;
	}
	
	public Map<String, PumpScadaHourly> pumpSHMap;
	public Map<String, PsScadaHourly> psSHMap;

}
