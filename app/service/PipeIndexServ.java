package service;

import java.util.ArrayList;
import java.util.List;

import util.MathUtilSphinx;

import models.ComponentProperty;
import models.form.PipeIndex;
import models.meter.condition.PipeBlockageMeter;
import models.meter.condition.PipeCctv34Meter;
import models.meter.condition.PipeCctvTotalMeter;
import models.meter.condition.PipeFlushingEventMeter;
import models.meter.sensitivity.PipeAnnualWasteWaterFlowMeter;
import models.meter.sensitivity.PipeGroundWaterAreaMeter;
import models.meter.sensitivity.PipeRelativeFloorAreaMeter;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeIndexWrapperView;

public class PipeIndexServ {
	
	static PipeIndexWrapper retWrapper;

	public static List<PipeIndexWrapper> calculatePipeIndex(List<PipeIndexWrapperView> viewList, PipeIndex meterLimit) {
		
		List<PipeIndexWrapper> retValList = new ArrayList<PipeIndexWrapper>();
		for (PipeIndexWrapperView view : viewList) {
			retWrapper = new PipeIndexWrapper();
			calculatePipeConditionIndex(view, meterLimit);
			calculatePipeConsequenceIndex(view, meterLimit);
			retWrapper.pipe_id = view.pipeId;
			retWrapper.pipe_identifier = view.pipeName;
			retWrapper.pipe_length_m = view.pipeLength;
			retWrapper.pipe_datasource_code = view.pipeCode;
			retValList.add(retWrapper);
		}
		
		return retValList;
	}
	
	public static PipeIndexWrapper calculatePipeConsequenceIndex(PipeIndexWrapperView view, PipeIndex meterLimit) {
		
		retWrapper.cqm_wastewater_flow_pipe_value = view.pipeDiameter;
		retWrapper.cqm_wastewater_flow_pipe_meter = retWrapper.cqm_wastewater_flow_pipe_value / meterLimit.wasteWaterLimit;
		retWrapper.cqm_wastewater_flow_limit = meterLimit.wasteWaterLimit;
		
		retWrapper.cqm_groundwater_area_pipe_value = (float) view.pipeGroundWaterAreaClassification;
		retWrapper.cqm_groundwater_area_pipe_meter = retWrapper.cqm_groundwater_area_pipe_value / meterLimit.groundWaterAreaLimit;
		retWrapper.cqm_groundwater_area_limit = meterLimit.groundWaterAreaLimit;
		
		retWrapper.cqm_pipe_type_value = view.pipeTypeValue;
		retWrapper.cqm_pipe_type_meter = (retWrapper.cqm_pipe_type_value * view.pipeDiameter) / (meterLimit.pipeTypeLimit * 500F);
		retWrapper.cqm_pipe_type_limit = meterLimit.pipeTypeLimit;
		
		retWrapper.cqm_floor_area_pipe_value = (view.allPipeLengthFloorArea == 0F) ? 0F : (float) view.totalFloorArea / view.allPipeLengthFloorArea;
		retWrapper.cqm_floor_area_pipe_meter = retWrapper.cqm_floor_area_pipe_value / meterLimit.floorAreaLimit;
		retWrapper.cqm_floor_area_limit = meterLimit.floorAreaLimit;
		
		retWrapper.cqm_road_class_pipe_value = (float) view.pipeRoadClassification;
		retWrapper.cqm_road_class_pipe_meter = (retWrapper.cqm_road_class_pipe_value != 0) ? (meterLimit.roadClassLimit / retWrapper.cqm_road_class_pipe_value) : 0F;
		retWrapper.cqm_road_class_limit = meterLimit.roadClassLimit;
		
		retWrapper.pipe_consequence_index = retWrapper.cqm_wastewater_flow_pipe_meter
				+ retWrapper.cqm_groundwater_area_pipe_meter
				+ retWrapper.cqm_pipe_type_meter
				+ retWrapper.cqm_floor_area_pipe_meter
				+ retWrapper.cqm_road_class_pipe_meter; 
		
		retWrapper.cqm_limit_total = 5F;
		
		return retWrapper;
	}
	
	public static PipeIndexWrapper calculatePipeConditionIndex(PipeIndexWrapperView view, PipeIndex meterLimit) {
		
		Float totalFlow = view.psFlowSum != null ? view.psFlowSum : 0F;
		// there are only values for year 2012
		Float totalConsumption = view.psAnnualConsumption != null ? view.psAnnualConsumption : 0F;
		
		retWrapper.cdm_blockage_pipe_value = view.pipeBlockage != null ? (float) view.pipeBlockage : 0F;
		retWrapper.cdm_blockage_pipe_meter = retWrapper.cdm_blockage_pipe_value / meterLimit.blockageLimit;
		retWrapper.cdm_blockage_limit = meterLimit.blockageLimit;
		
		retWrapper.cdm_flushing_pipe_value = view.pipeFlushing != null ? (float) view.pipeFlushing : 0F;
		retWrapper.cdm_flushing_pipe_meter = retWrapper.cdm_flushing_pipe_value / meterLimit.flushingLimit;
		retWrapper.cdm_flushing_limit = meterLimit.flushingLimit;
		
		retWrapper.cdm_cctv_pipe_value = (view.pipeCctvSum != null) ? view.pipeCctvSum : 0F;
		retWrapper.cdm_cctv_pipe_meter = retWrapper.cdm_cctv_pipe_value / meterLimit.cctvLimit;
		retWrapper.inspected = (retWrapper.cdm_cctv_pipe_value != 0) ? 1:0;
		retWrapper.cdm_cctv_limit = meterLimit.cctvLimit;
		
		retWrapper.cdm_cctv34_pipe_value = (view.pipeCctv34Sum != null) ? view.pipeCctv34Sum : 0F;
		retWrapper.cdm_cctv34_pipe_meter = retWrapper.cdm_cctv34_pipe_value / meterLimit.cctv34Limit;
		retWrapper.cdm_cctv34_limit = meterLimit.cctv34Limit;
		
		retWrapper.cdm_extrawater_pipe_value = totalConsumption != 0F ? (float) (totalFlow / (totalConsumption * 0.8)) : 0F;
		retWrapper.cdm_extrawater_pipe_meter = (retWrapper.cdm_extrawater_pipe_value - 1) / meterLimit.extraWaterLimit;
		if (retWrapper.cdm_extrawater_pipe_meter < 0 || MathUtilSphinx.truncateDouble(retWrapper.cdm_extrawater_pipe_meter.doubleValue(), 2) == 0D)
			retWrapper.cdm_extrawater_pipe_meter = 0F;
		retWrapper.cdm_extrawater_limit = meterLimit.extraWaterLimit;
		
		retWrapper.cdm_overflow_limit = meterLimit.overFlowLimit;
		retWrapper.cdm_overflow_m3_a = (view.ofAnnualOverFlow != null) ? view.ofAnnualOverFlow : 0F;
		retWrapper.cdm_overflow_pipe_value = retWrapper.cdm_overflow_m3_a;
		retWrapper.cdm_overflow_pipe_meter = retWrapper.cdm_overflow_pipe_value / meterLimit.overFlowLimit;
		retWrapper.cdm_industrial_ww_pipe_value = (view.pipeInIndustrialRoot) ? 1:0;
		retWrapper.cdm_industrial_ww_pipe_meter = retWrapper.cdm_industrial_ww_pipe_value;
		
		
		retWrapper.pipe_condition_index = retWrapper.cdm_blockage_pipe_meter
				+ retWrapper.cdm_flushing_pipe_meter
				+ retWrapper.cdm_cctv_pipe_meter
				+ retWrapper.cdm_cctv34_pipe_meter
				+ retWrapper.cdm_blockage_pipe_meter
				+ retWrapper.cdm_overflow_pipe_meter
				+ retWrapper.cdm_industrial_ww_pipe_meter;
		
		retWrapper.cdm_limit_total = (retWrapper.inspected == 1) ? 7F : 5F;
		
		return retWrapper;
	}
}
