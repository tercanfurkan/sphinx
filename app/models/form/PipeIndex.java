package models.form;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import play.db.jpa.JPA;

import models.ComponentType;
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
		this.pressurePipeLimit = this.sensitivityIndex.pressureMeter.specialCautionLimit;
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
	public float pressurePipeLimit; 
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
	

}
