package util.datacorrection.pipe;

import java.util.HashMap;
import java.util.Map;

import models.Component;

public class DiameterCorrector {

	public static void correctComponentDiameterSlash(Long componentId) {

		Component component = Component.findById(componentId);
		String diameterStrValue = "";
		Integer diameterValue = 0;

		assert component.component_type.type_name.equals("Pipe") : "this method only works for pipes. this is not a pipe";

		// assign diameter value to diameterStrValue
		diameterStrValue = component.componentDetail.diameter;

		// if diameterStrValue is not an empty string
		if (!diameterStrValue.equals("")) {

			// if diameterStrValue includes "/" character get the substring the
			// smaller integer value assign to diameterValue
			if (diameterStrValue.contains("/")) {
				String diameterArray[] = diameterStrValue.split("/");
				diameterValue = Integer.parseInt(diameterArray[0]) < Integer
						.parseInt(diameterArray[1]) ? Integer
						.parseInt(diameterArray[0]) : Integer
						.parseInt(diameterArray[1]);

				// assign diameterValue to
				// component.componentDetail.diameterValue and a note to note
				// and merge component
				component.componentDetail.diameterValue = diameterValue;
				component.componentDetail.note = "{diameter:diameterValue:slash eliminated}";
				component.merge();
			}
		}

	}
	
	public static void copyCorrectDiameterToDiameterValue(Long componentId) {
		
		Component component = Component.findById(componentId);
		String diameterStrValue = "";
		Integer diameterValue = 0;

		assert component.component_type.type_name.equals("Pipe") : "this method only works for pipes. this is not a pipe";

		// assign diameter value to diameterStrValue
		diameterStrValue = component.componentDetail.diameter;

		// if diameterStrValue is not an empty string
		if (!diameterStrValue.equals("")) {

			// if diameterStrValue does not include "/" character, copy diameterStrValue as diameterValue to component.componentDetail.diameterValue
			if (!diameterStrValue.contains("/")) {
				diameterValue = Integer.parseInt(diameterStrValue);
				component.componentDetail.diameterValue = diameterValue;
				component.componentDetail.note = "{diameter:diameterValue:copied}";
				component.merge();
			}
		}
	}
	
	public static void setClassAverageDiameterWhereDiameterEmpty(Long componentId) {
		
		Component component = Component.findById(componentId);
		int averagePipeClassDiameter = 0;
		int classCode = 0;
		String biggerAndEqualThenValueStr = "";
		String smallerAndEqualThenValueStr = "";
		Map<String, Integer> classCodeFilterMap = new HashMap<String, Integer>();

		assert component.component_type.type_name.equals("Pipe") : "this method only works for pipes. this is not a pipe";
		
		// if component.componentDetail.diameterValue is null 
		if (component.componentDetail.diameterValue == null) {
			
			if (!component.componentDetail.diameter.equals("")) return;
			System.out.println(component.id +"="+component.componentDetail.datasource_class_code);
			// .. find class average diameter
			
			// .. .. find class of pipe
			classCode = component.componentDetail.datasource_class_code;
			
			// .. .. generate query filter to fetch pipes having relevant class
			
			// .. .. get /10000 of the datasource class code. if 10, else if 72, else if 76. 
			if (classCode/10000 == 10) {
				
				// .. .. if 10 then get datasource_class_code%100 = a. Then datasource_class_code >1044a0 and datasource_class_code < 1044(a+1)0
				int base10Value = (classCode%100)/10;
				biggerAndEqualThenValueStr = "1044" + base10Value + "0";
				smallerAndEqualThenValueStr = "1044" + base10Value + "9";
				classCodeFilterMap.put("biggerAndEqualThenValue", Integer.parseInt(biggerAndEqualThenValueStr));
				classCodeFilterMap.put("smallerAndEqualThenValue", Integer.parseInt(smallerAndEqualThenValueStr));
			}
			else if (classCode/10000 == 72) {
				classCodeFilterMap.put("biggerAndEqualThenValue", 724410);
				classCodeFilterMap.put("smallerAndEqualThenValue", 724419);
			}
			else if (classCode/10000 == 76) {
				classCodeFilterMap.put("biggerAndEqualThenValue", 764410);
				classCodeFilterMap.put("smallerAndEqualThenValue", 764419);
			}
			
			// .. .. get average diameter of all pipes belonging to relevant classes and assign to averagePipeClassDiameter
//			System.out.println(classCode + " - " + classCodeFilterMap.get("biggerAndEqualThenValue") + " - " + classCodeFilterMap.get("smallerAndEqualThenValue"));
			averagePipeClassDiameter = Component.getPipeClassAverageDiameter(classCode, classCodeFilterMap);
			
			// .. .. set component.componentDetail.diameterValue as diameterValue and add note and merge component
			component.componentDetail.diameterValue = averagePipeClassDiameter;
			component.componentDetail.note = "{diameter:diameterValue:set as class average}";
			component.merge();
			
//			System.out.println(component.id + " = " + component.componentDetail.diameterValue);
		}
		


	}
}
