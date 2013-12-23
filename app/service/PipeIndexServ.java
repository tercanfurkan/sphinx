package service;

import java.util.ArrayList;
import java.util.List;

import models.form.MeterLimitVal;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeIndexWrapperView;

public class PipeIndexServ {
	
	static PipeIndexWrapper retWrapper;
	
	public static List<PipeIndexWrapper> calculatePipeIndex(List<PipeIndexWrapperView> viewList, MeterLimitVal meterLimitVal) {
		
		List<PipeIndexWrapper> retValList = new ArrayList<PipeIndexWrapper>();
		for (PipeIndexWrapperView view : viewList) {
			retWrapper = new PipeIndexWrapper();
			retWrapper.diameter_mm = view.pipeDiameter.intValue();
			retWrapper.pipe_class = view.pipeClassName;
			calculatePipeConditionIndex(view, meterLimitVal);
			calculatePipeConsequenceIndex(view, meterLimitVal);
			retWrapper.pipe_id = view.pipeId;
			retWrapper.pipe_identifier = view.pipeName;
			retWrapper.pipe_length_m = view.pipeLength;
			retWrapper.pipe_datasource_code = view.pipeCode;
			retWrapper.cdm_extrawater_total_consumption_m3_a = view.psAnnualConsumption;
			retWrapper.cdm_extrawater_total_flow_m3_a = view.psFlowSum;
			retWrapper.cqm_floor_area = view.totalFloorArea;
			retWrapper.cqm_floor_area_total_pipe_length = view.allPipeLengthFloorArea;
			retWrapper.cqm_wastewater_flow_annual_flow_m3 = view.psAnnualConsumption;
			retWrapper.owner_ps_area = view.psName;
			retWrapper.material = view.pipeMaterial;
			
			retWrapper.installation_year = view.pipeInstallationYear;
			retValList.add(retWrapper);
		}
		
		return retValList;
	}
	
	private static void calculatePipeConsequenceIndex(
			PipeIndexWrapperView view, MeterLimitVal meterLimitVal) {

		//slider vals pipe diameter
		retWrapper.cqm_wastewater_flow_pipe_value = view.pipeDiameter;
		if(view.pipeDiameter < meterLimitVal.diameterMin)
			retWrapper.cqm_wastewater_flow_pipe_meter = 0F;
		else if (view.pipeDiameter > meterLimitVal.diameterMax)
			retWrapper.cqm_wastewater_flow_pipe_meter = 2F;
		else 
			retWrapper.cqm_wastewater_flow_pipe_meter = 1F;
		retWrapper.cqm_wastewater_flow_min_limit = meterLimitVal.diameterMin;
		retWrapper.cqm_wastewater_flow_max_limit = meterLimitVal.diameterMax;
		
		//radiobutton vals
		retWrapper.cqm_groundwater_area_pipe_value = (float) view.pipeGroundWaterAreaClassification;
		if (retWrapper.cqm_groundwater_area_pipe_value == 3F)
			retWrapper.cqm_groundwater_area_pipe_meter = meterLimitVal.gwaImportantVal;
		else if (retWrapper.cqm_groundwater_area_pipe_value == 2F)
			retWrapper.cqm_groundwater_area_pipe_meter = meterLimitVal.gwaSuitableVal;
		else if (retWrapper.cqm_groundwater_area_pipe_value == 1F)
			retWrapper.cqm_groundwater_area_pipe_meter = meterLimitVal.gwaOtherVal;
		else
			retWrapper.cqm_groundwater_area_pipe_meter = 0F;
		
		//radiobutton vals
		retWrapper.cqm_pipe_type_value = view.pipeTypeValue;
		if (retWrapper.pipe_class.startsWith("Paine")) {
			if (retWrapper.diameter_mm < 200) //small pressure
				retWrapper.cqm_pipe_type_meter = meterLimitVal.pipeTypeSmallPressureVal;
			else //pressure
				retWrapper.cqm_pipe_type_meter = meterLimitVal.pipeTypePressure;
		}
		else if (retWrapper.pipe_class.startsWith("Päävi")) {
			//gravity sewer main
			retWrapper.cqm_pipe_type_meter = meterLimitVal.pipeTypeGravetyVal;
		}
		else if (retWrapper.pipe_class.startsWith("Kerä")) {
			//collection sewers 
			retWrapper.cqm_pipe_type_meter = meterLimitVal.pipeTypeCollectionVal;
		}
		else {
			//Syöksyputki - lateral gravity sewers
			retWrapper.cqm_pipe_type_meter = 0F;
		}
		
		//slider vals floor area
		retWrapper.cqm_floor_area_pipe_value = (view.allPipeLengthFloorArea == 0F) ? 0F : (float) view.totalFloorArea / view.allPipeLengthFloorArea;
		if (retWrapper.cqm_floor_area_pipe_value < meterLimitVal.floorAreaMin)
			retWrapper.cqm_floor_area_pipe_meter = 0F;
		else if (retWrapper.cqm_floor_area_pipe_value > meterLimitVal.floorAreaMax)
			retWrapper.cqm_floor_area_pipe_meter = 2F;
		else
			retWrapper.cqm_floor_area_pipe_meter = 1F;
		retWrapper.cqm_floor_area_pipe_min_limit = meterLimitVal.floorAreaMin;
		retWrapper.cqm_floor_area_pipe_max_limit = meterLimitVal.floorAreaMax;
		
		//radiobutton vals
		retWrapper.cqm_road_class_pipe_value = (float) view.pipeRoadClassification;
		if (retWrapper.cqm_road_class_pipe_value == 4F)
			retWrapper.cqm_road_class_pipe_meter = meterLimitVal.roadCollectorVal;
		else if (retWrapper.cqm_road_class_pipe_value == 3F)
			retWrapper.cqm_road_class_pipe_meter = meterLimitVal.roadLocalMainVal;
		else if (retWrapper.cqm_road_class_pipe_value == 2F)
			retWrapper.cqm_road_class_pipe_meter = meterLimitVal.roadRegionalMain2Val;
		else
			retWrapper.cqm_road_class_pipe_meter = meterLimitVal.roadRegionalMain1Val;
		
		//slider beach distance
		retWrapper.cqm_beach_distance_pipe_value = view.ofDistanceToBeach;
		if (retWrapper.cqm_beach_distance_pipe_value < meterLimitVal.beachDistanceMin)
			retWrapper.cqm_beach_distance_pipe_meter = 0F;
		else if (retWrapper.cqm_beach_distance_pipe_value > meterLimitVal.beachDistanceMax)
			retWrapper.cqm_beach_distance_pipe_meter = 2F;
		else
			retWrapper.cqm_beach_distance_pipe_meter = 1F;
		retWrapper.cqm_beach_distance_pipe_min_limit = meterLimitVal.beachDistanceMin;
		retWrapper.cqm_beach_distance_pipe_max_limit = meterLimitVal.beachDistanceMax;
		
		retWrapper.pipe_consequence_index = retWrapper.cqm_wastewater_flow_pipe_meter
				+ retWrapper.cqm_groundwater_area_pipe_meter
				+ retWrapper.cqm_pipe_type_meter
				+ retWrapper.cqm_road_class_pipe_meter
				+ retWrapper.cqm_beach_distance_pipe_meter
				+ retWrapper.cqm_floor_area_pipe_meter;
		
		retWrapper.cqm_limit_total = 6F;
	}
	
	public static void calculatePipeConditionIndex(PipeIndexWrapperView view, MeterLimitVal meterLimitVal) {
		
		Float totalFlow = view.psFlowSum != null ? view.psFlowSum : 0F;
		// there are only values for year 2012
		Float totalConsumption = view.psAnnualConsumption != null ? view.psAnnualConsumption : 0F;
		
		//blockages
		retWrapper.cdm_blockage_pipe_value = view.pipeBlockage != null ? (float) view.pipeBlockage : 0F;
		if(retWrapper.cdm_blockage_pipe_value < meterLimitVal.blockageMin)
			retWrapper.cdm_blockage_pipe_meter = 0F;
		else if(retWrapper.cdm_blockage_pipe_value > meterLimitVal.blockageMax)
			retWrapper.cdm_blockage_pipe_meter = 2F;
		else
			retWrapper.cdm_blockage_pipe_meter = 1F;
		retWrapper.cdm_blockage_min_limit = meterLimitVal.blockageMin;
		retWrapper.cdm_blockage_max_limit = meterLimitVal.blockageMax;
		
		//flushings
		retWrapper.cdm_flushing_pipe_value = view.pipeFlushing != null ? (float) view.pipeFlushing : 0F;
		if(retWrapper.cdm_flushing_pipe_value < meterLimitVal.flushingMin)
			retWrapper.cdm_flushing_pipe_meter = 0F;
		else if(retWrapper.cdm_flushing_pipe_value > meterLimitVal.flushingMin)
			retWrapper.cdm_flushing_pipe_meter = 2F;
		else
			retWrapper.cdm_flushing_pipe_meter = 1F;
		retWrapper.cdm_flushing_min_limit = meterLimitVal.blockageMin;
		retWrapper.cdm_flushing_max_limit = meterLimitVal.blockageMax;
		
		//cctvs
		retWrapper.cdm_cctv_pipe_value = (view.pipeCctvSum != null) ? view.pipeCctvSum : 0F;		
		if(retWrapper.cdm_cctv_pipe_value < meterLimitVal.cctvMin)
			retWrapper.cdm_cctv_pipe_meter = 0F;
		else if(retWrapper.cdm_cctv_pipe_value > meterLimitVal.cctvMax)
			retWrapper.cdm_cctv_pipe_meter = 2F;
		else
			retWrapper.cdm_cctv_pipe_meter = 1F;
		retWrapper.inspected = (retWrapper.cdm_cctv_pipe_value != 0) ? 1:0;
		retWrapper.cdm_cctv_min_limit = meterLimitVal.cctvMin;
		retWrapper.cdm_cctv_max_limit = meterLimitVal.cctvMax;

		//cctv34s
		retWrapper.cdm_cctv34_pipe_value = (view.pipeCctv34Sum != null) ? view.pipeCctv34Sum : 0F;		
		if(retWrapper.cdm_cctv34_pipe_value < meterLimitVal.cctv34Min)
			retWrapper.cdm_cctv34_pipe_meter = 0F;
		else if(retWrapper.cdm_cctv34_pipe_value > meterLimitVal.cctv34Max)
			retWrapper.cdm_cctv34_pipe_meter = 2F;
		else
			retWrapper.cdm_cctv34_pipe_meter = 1F;
		retWrapper.cdm_cctv34_min_limit = meterLimitVal.cctvMin;
		retWrapper.cdm_cctv34_max_limit = meterLimitVal.cctvMax; 
		
		//extrawater
		retWrapper.cdm_extrawater_pipe_value = totalConsumption != 0F ? (float) (totalFlow / (totalConsumption * 0.8)) : 0F;
		if(retWrapper.cdm_extrawater_pipe_value < meterLimitVal.extraWaterMin)
			retWrapper.cdm_extrawater_pipe_meter = 0F;
		else if(retWrapper.cdm_extrawater_pipe_value > meterLimitVal.extraWaterMin)
			retWrapper.cdm_extrawater_pipe_meter = 2F;
		else
			retWrapper.cdm_extrawater_pipe_meter = 1F;
		retWrapper.cdm_extrawater_percentange_min_limit = meterLimitVal.extraWaterMin;
		retWrapper.cdm_extrawater_percentange_max_limit = meterLimitVal.extraWaterMax; 
		
//		retWrapper.cdm_industrial_ww_pipe_value = (view.pipeInIndustrialRoot) ? 1:0;
//		retWrapper.cdm_industrial_ww_pipe_meter = retWrapper.cdm_industrial_ww_pipe_value;
		
		
		retWrapper.pipe_condition_index = retWrapper.cdm_blockage_pipe_meter
				+ retWrapper.cdm_flushing_pipe_meter
				+ retWrapper.cdm_cctv_pipe_meter
				+ retWrapper.cdm_cctv34_pipe_meter
//				+ retWrapper.cdm_industrial_ww_pipe_meter
				+ retWrapper.cdm_extrawater_pipe_meter;
		
		retWrapper.cdm_limit_total = (retWrapper.inspected == 1) ? 5F : 3F;
	}
}
