package service;

import java.util.Date;

import models.Component;
import models.meter.condition.PipeConditionIndex;
import models.wrapper.PipeConditionIndexWrapper;

public class PipeConditionIndexServ {

	public static PipeConditionIndex getPipeConditionIndex(Component pipeComponent, Date startDate, Date endDate) {
		
		PipeConditionIndex conditionIndex = new PipeConditionIndex();
		
//		System.out.println("pipeBlockageMeter");
		conditionIndex.blockageMeter = PipeConditionIndexMeterServ.pipeBlockageMeter(pipeComponent.id);
		
//		System.out.println("pipeFlushingEventMeter");
		conditionIndex.flushingEventMeter = PipeConditionIndexMeterServ.pipeFlushingEventMeter(pipeComponent.id);
		
//		System.out.println("pipeCctvTotalMeter");
		conditionIndex.cctvTotalMeter = PipeConditionIndexMeterServ.pipeCctvTotalMeter(pipeComponent.id);
		
//		System.out.println("pipeCctv34Meter");
		conditionIndex.cctv34Meter = PipeConditionIndexMeterServ.pipeCctv34Meter(pipeComponent.id);
		
//		System.out.println("pipeExtraWaterMeter");
		conditionIndex.extraWaterMeter = PipeConditionIndexMeterServ.pipeExtraWaterMeter(pipeComponent.id, startDate, endDate);
		
		conditionIndex.pipeIdentifier = pipeComponent.name;
		conditionIndex.pipeOwnerComponent = pipeComponent.ownerComponent.name;
		
//		System.out.println("calculateResult");
		conditionIndex.calculateResult();
		return conditionIndex;
	}
	
	public static PipeConditionIndex getPipeConditionIndex(PipeConditionIndexWrapper wrapper) {
		
		PipeConditionIndex conditionIndex = new PipeConditionIndex();
		
		conditionIndex.blockageMeter = PipeConditionIndexMeterServ.pipeBlockageMeter(wrapper.pipeBlockage);
		
		conditionIndex.flushingEventMeter = PipeConditionIndexMeterServ.pipeFlushingEventMeter(wrapper.pipeFlushing);
		
		conditionIndex.cctvTotalMeter = PipeConditionIndexMeterServ.pipeCctvTotalMeter(wrapper.pipeCctvSum);
		
		conditionIndex.cctv34Meter = PipeConditionIndexMeterServ.pipeCctv34Meter(wrapper.pipeCctv34Sum);
		
		conditionIndex.extraWaterMeter = PipeConditionIndexMeterServ.pipeExtraWaterMeter(wrapper.psAnnualConsumption.doubleValue(), wrapper.psFlowSum, wrapper.psName);
		
		conditionIndex.pipeIdentifier = wrapper.pipeName;
		conditionIndex.pipeOwnerComponent = wrapper.psName;
		
		conditionIndex.calculateResult();
		
		return conditionIndex;
	}
}
