package datasource.parser;

import java.util.List;

import models.Component;


/**
 * This class parses Industrial Waste Water data 
 * from its datasource (IndustrialWasteWaterMeterDataReader)
 * This class creates a relation between pipes and industrial waste water data
 * This class marks each pipe if they are in the root of industrial waste water
 * @author wa.tercano1
 *
 */
public class IndustrialWasteWaterMeterDataParser {
	
	public void setPipesInListAsIndustrial(List<Long> pipeCodeList) {
		
		Component compToUpdate = null;
		
		for (Long pipeCode : pipeCodeList) {
			
			compToUpdate = Component.findByDatasourceCode(pipeCode);
			if (compToUpdate != null) {
				compToUpdate.inIndustrialRoot = true;
				compToUpdate.merge();
			}
			else {
				System.out.println("======={IndustrialWasteWaterMeterDataParser}: compToUpdate is not found in db");
			}
		}
	}

}
