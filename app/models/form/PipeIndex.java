package models.form;

import models.meter.condition.PipeConditionIndex;
import models.meter.sensitivity.PipeSensitivityIndex;

public class PipeIndex {
	
	public PipeIndex() {
		
		this.sensitivityIndex = new PipeSensitivityIndex();
		this.conditionIndex = new PipeConditionIndex();
		
		this.sortBy = "indexValue";
		this.order = "desc";
		this.filter = "";
		this.wasteWaterLimit = this.sensitivityIndex.annualWasteWaterFlowMeter.specialCautionLimit;
		this.groundWaterAreaLimit = this.sensitivityIndex.groundWaterAreaMeter.specialCautionLimit;
		this.pipeTypeLimit = this.sensitivityIndex.pressureMeter.specialCautionLimit;
		this.floorAreaLimit = this.sensitivityIndex.relativeFloorAreaMeter.specialCautionLimit;
		this.roadClassLimit = this.sensitivityIndex.roadClassificationMeter.specialCautionLimit;
		this.blockageLimit = this.conditionIndex.blockageMeter.specialCautionLimit;
		this.flushingLimit = this.conditionIndex.flushingEventMeter.specialCautionLimit;
		this.extraWaterLimit = this.conditionIndex.extraWaterMeter.specialCautionLimit;
		this.cctvLimit = this.conditionIndex.cctvTotalMeter.specialCautionLimit;
		this.cctv34Limit = this.conditionIndex.cctv34Meter.specialCautionLimit;
	}

	public int page;
	public String sortBy;
	public String order;
	public String filter;
	public String orderDesc = "desc";
	public String orderAsc = "asc";
	public float wasteWaterLimit;
	public float groundWaterAreaLimit; 
	public float pipeTypeLimit; 
	public float floorAreaLimit;
	public float roadClassLimit;
	public float blockageLimit;
	public float flushingLimit;
	public float extraWaterLimit;
	public float cctvLimit;
	public float cctv34Limit;
	public boolean isInspected;
	public String[] filterNameList = {"", "", ""}; 
	public String[] filterValueList = {"", "", ""}; 
	public String[] filterOperatorList = {"", "", ""}; 
	public PipeSensitivityIndex sensitivityIndex;
	public PipeConditionIndex conditionIndex;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(blockageLimit);
		result = prime * result + Float.floatToIntBits(cctv34Limit);
		result = prime * result + Float.floatToIntBits(cctvLimit);
		result = prime * result + Float.floatToIntBits(extraWaterLimit);
		result = prime * result + Float.floatToIntBits(floorAreaLimit);
		result = prime * result + Float.floatToIntBits(flushingLimit);
		result = prime * result + Float.floatToIntBits(groundWaterAreaLimit);
		result = prime * result + Float.floatToIntBits(pipeTypeLimit);
		result = prime * result + Float.floatToIntBits(roadClassLimit);
		result = prime * result + Float.floatToIntBits(wasteWaterLimit);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PipeIndex other = (PipeIndex) obj;
		if (Float.floatToIntBits(blockageLimit) != Float
				.floatToIntBits(other.blockageLimit))
			return false;
		if (Float.floatToIntBits(cctv34Limit) != Float
				.floatToIntBits(other.cctv34Limit))
			return false;
		if (Float.floatToIntBits(cctvLimit) != Float
				.floatToIntBits(other.cctvLimit))
			return false;
		if (Float.floatToIntBits(extraWaterLimit) != Float
				.floatToIntBits(other.extraWaterLimit))
			return false;
		if (Float.floatToIntBits(floorAreaLimit) != Float
				.floatToIntBits(other.floorAreaLimit))
			return false;
		if (Float.floatToIntBits(flushingLimit) != Float
				.floatToIntBits(other.flushingLimit))
			return false;
		if (Float.floatToIntBits(groundWaterAreaLimit) != Float
				.floatToIntBits(other.groundWaterAreaLimit))
			return false;
		if (Float.floatToIntBits(pipeTypeLimit) != Float
				.floatToIntBits(other.pipeTypeLimit))
			return false;
		if (Float.floatToIntBits(roadClassLimit) != Float
				.floatToIntBits(other.roadClassLimit))
			return false;
		if (Float.floatToIntBits(wasteWaterLimit) != Float
				.floatToIntBits(other.wasteWaterLimit))
			return false;
		return true;
	}
	
	
}
