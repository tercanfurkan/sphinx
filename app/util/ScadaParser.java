package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.transform.impl.AddPropertyTransformer;

import modelWrappers.ComAndProp;
import modelWrappers.ScadaHrWrapper;
import modelWrappers.ScadaWrapper;
import models.Component;
import models.ComponentProperty;
import models.PsScadaHourly;
import models.PumpScadaHourly;
import models.enums.PumpName;
import models.enums.ReadStatus;

public class ScadaParser {

	public static Collection<Component> comList = new ArrayList<Component>();
	public static Collection<ScadaWrapper> scadaHourlyList = new ArrayList<ScadaWrapper>();
	public static Collection<ComponentProperty> componentPropList = new ArrayList<ComponentProperty>();
	public static Map<Integer, ComAndProp> cpMap = new HashMap<Integer, ComAndProp>();
	public static String tmpComName = "";
	public static ScadaWrapper wrapper = null;
	public static PumpScadaHourly psh = null;
	public static Map<String, PumpScadaHourly> pumpSHMap = new HashMap<String, PumpScadaHourly>();
	public static Map<String, PsScadaHourly> psSHMap = new HashMap<String, PsScadaHourly>();

	public static void parseScadaDoc(List<String> lines) {

		String line = lines.get(1);
		String[] recordsOnLine = line.split(",");
		int index = 0;
		for (String str : recordsOnLine) {
			ComAndProp cp = new ComAndProp(str);
			if (cp.status == ReadStatus.OK) {
				cpMap.put(index, cp);
//				System.out.println("COM : " + cp.component + " | PROP : " + cp.property + " | SUBCOMP : " + cp.subComponent + " | INDEX : " + index);
			}
			index++;
		}
	}

	public static ScadaHrWrapper wrapScadaData(List<String> lines) {

		pumpSHMap.clear();
		psSHMap.clear();
		for (int i = 4; i < lines.size()-4; i++) {

			String[] recordsOnLineTmp = lines.get(i).split(",");

			List<String> recordsOnLine1 = ReadCSV
					.stringArraytoArrayList(recordsOnLineTmp);
			List<String> recordsOnLine = ReadCSV
					.configureEmptyValues(recordsOnLine1);
			
			for (int j = 2; j < recordsOnLine.size(); j++) {
				
				String rowValue = recordsOnLine.get(j); 
				int index = j;
				String stringDate = ReadCSV.parseDate(recordsOnLine.get(0)) + " "
						+ ReadCSV.parseTime(recordsOnLine.get(1)) + ":00:00";
				
				ComAndProp cp = cpMap.get(index);
				String mapKey = cp.component + stringDate;
				Date timestamp = ReadCSV.formatDate(stringDate);
				
				if (cp.subComponent  == null) {
					addPropOfComp(cp.component, mapKey, timestamp, cp.property, rowValue);
					
				}
				else {
					mapKey = mapKey + cp.subComponent;
					PumpScadaHourly pumpScadaHr = null;
					boolean exists = false;
					if (pumpSHMap.containsKey(mapKey)) {
						exists = true;
					}
					else {
						pumpScadaHr = new PumpScadaHourly();
						pumpScadaHr.component = Component.findPSComponent(cp.component);
						pumpScadaHr.timestamp = timestamp;
					}
					addPumpToComp(pumpScadaHr, mapKey, cp.subComponent, exists);
					addPropOfSubComp(mapKey, cp.property, rowValue);
				}		
			}
		}
		ScadaHrWrapper shw = new ScadaHrWrapper(psSHMap, pumpSHMap);
		return shw;
	}
	
	public static void addPropOfComp(String componentName, String mapKey, Date timestamp, String propName, String propValue) {
		
		System.out.println(componentName);
		
		PsScadaHourly psScadaHr = null;
		boolean exists = false;
		if (psSHMap.containsKey(mapKey)) {
			exists = true;
		}
		else {
			psScadaHr = new PsScadaHourly();
			psScadaHr.component = Component.findPSComponent(componentName);
			psScadaHr.timestamp = timestamp;
		}
		
		switch (propName) {
		case "FI-01":
			if (exists) {
				psSHMap.get(mapKey).FI_01 = Float.parseFloat(propValue);
			}
			else {
				psScadaHr.FI_01 = Float.parseFloat(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		case "PINT":
			if (exists) {
				psSHMap.get(mapKey).PINT = Float.parseFloat(propValue);
			}
			else {
				psScadaHr.PINT = Float.parseFloat(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		case "FI-02":
			if (exists) {
				psSHMap.get(mapKey).FI_02 = Float.parseFloat(propValue);
			}
			else {
				psScadaHr.FI_02 = Float.parseFloat(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		case "YHTK":
			if (exists) {
				psSHMap.get(mapKey).YHTK = Float.parseFloat(propValue);
			}
			else {
				psScadaHr.YHTK = Float.parseFloat(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		case "EQ-01":
			if (exists) {
				psSHMap.get(mapKey).EQ_01 = Float.parseFloat(propValue);
			}
			else {
				psScadaHr.EQ_01 = Float.parseFloat(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		case "2YHTKA":
			if (exists) {
				psSHMap.get(mapKey).YHTKA2 = Integer.parseInt(propValue);
			}
			else {
				psScadaHr.YHTKA2 = Integer.parseInt(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		case "3YHTKA":
			if (exists) {
				psSHMap.get(mapKey).YHTKA3 = Integer.parseInt(propValue);
			}
			else {
				psScadaHr.YHTKA3 = Integer.parseInt(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		case "4YHTKA":
			if (exists) {
				psSHMap.get(mapKey).YHTKA4 = Integer.parseInt(propValue);
			}
			else {
				psScadaHr.YHTKA4 = Integer.parseInt(propValue);
				psSHMap.put(mapKey, psScadaHr);
			}
			break;

		default:
		}
	}
	
	public static void addPumpToComp(PumpScadaHourly pumpScadaHr, String mapKey, String pumpName, boolean exists) {
		
		switch (pumpName) {

		case "P1":
			if (exists)
				pumpSHMap.get(mapKey).pumpName = PumpName.P1;
			else {
				pumpScadaHr.pumpName = PumpName.P1;
				pumpSHMap.put(mapKey, pumpScadaHr);
			}
			break;

		case "P2":
			if (exists)
				pumpSHMap.get(mapKey).pumpName = PumpName.P2;
			else {
				pumpScadaHr.pumpName = PumpName.P2;
				pumpSHMap.put(mapKey, pumpScadaHr);
			}
			break;

		case "P3":
			if (exists)
				pumpSHMap.get(mapKey).pumpName = PumpName.P3;
			else {
				pumpScadaHr.pumpName = PumpName.P3;
				pumpSHMap.put(mapKey, pumpScadaHr);
			}
			break;

		case "P4":
			if (exists)
				pumpSHMap.get(mapKey).pumpName = PumpName.P4;
			else {
				pumpScadaHr.pumpName = PumpName.P4;
				pumpSHMap.put(mapKey, pumpScadaHr);
			}
			break;
			
		default:
		}
	}
	
	public static void addPropOfSubComp (String mapKey, String propName, String propValue) {	

		//TODO: put inside subcomponent case
		switch (propName) {

		case "T":
			pumpSHMap.get(mapKey).PT = Float.parseFloat(propValue);
			break;

		case "V":
			pumpSHMap.get(mapKey).PV = Float.parseFloat(propValue);
			break;

		case "P":
			pumpSHMap.get(mapKey).PP = Float.parseFloat(propValue);
			break;
			
		case "PKA":
			pumpSHMap.get(mapKey).PKA = Integer.parseInt(propValue);
			break;
		default:
		}
	}
}
