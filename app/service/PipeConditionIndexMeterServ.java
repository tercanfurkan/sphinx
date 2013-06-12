package service;

import java.util.Date;

import util.MathUtilSphinx;

import models.Component;
import models.ComponentProperty;
import models.PsScadaHourly;
import models.meter.condition.PipeBlockageMeter;
import models.meter.condition.PipeCctv34Meter;
import models.meter.condition.PipeCctvTotalMeter;
import models.meter.condition.PipeExtraWaterMeter;
import models.meter.condition.PipeFlushingEventMeter;

public class PipeConditionIndexMeterServ {
	
	/**
	 * @pre start and end date must be the same for totalflow calculation and totalconsumption calculation.
	 * 		there is totalconsumption only for the year 2012
	 * 
	 * @param pipeComponentId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static PipeExtraWaterMeter pipeExtraWaterMeter(Long pipeComponentId, Date startDate, Date endDate) {
		
		Component pipeComponent = Component.findById(pipeComponentId);
		Component ownerPsComponent = null;
		Float meterValue = 0F;
		Float valueOfPipe = 0F;
		Float totalFlow = 0F;
		Float totalConsumption = 0F;
		PipeExtraWaterMeter pExtraWaterMeter = new PipeExtraWaterMeter();
		
		if (pipeComponent.ownerComponent != null) {
			
			ownerPsComponent = pipeComponent.ownerComponent;
			
			totalFlow = PsScadaHourly.getArealSumOfFI_01(ownerPsComponent.id, startDate, endDate).floatValue();
			
			// there are only values for year 2012
			totalConsumption = ComponentProperty.getArealSumOFYearlyConsumption(ownerPsComponent.id, startDate, endDate).floatValue();
			
			valueOfPipe = (totalConsumption != 0D) ? (float) (totalFlow / (totalConsumption * 0.8)) : 0L;
			meterValue = valueOfPipe / pExtraWaterMeter.specialCautionLimit;

			pExtraWaterMeter.ownerPsComponent = ownerPsComponent.name;
			pExtraWaterMeter.totalFlow = totalFlow;
			pExtraWaterMeter.totalConsumption = totalConsumption;
			pExtraWaterMeter.valueOfPipe = valueOfPipe;
			pExtraWaterMeter.meterValue = meterValue;
	
		} else {
			System.out.println("PS of pipe does not exist in Sphinx");
		}
		
		return pExtraWaterMeter;
	}
	
	public static PipeExtraWaterMeter pipeExtraWaterMeter(Double psAnnualConsumption, Float psFlow, String ownerPsComponent) {
		
		Float meterValue = 0F;
		Float valueOfPipe = 0F;
		Float totalFlow = 0F;
		Float totalConsumption = 0F;
		PipeExtraWaterMeter pExtraWaterMeter = new PipeExtraWaterMeter();
		
			totalFlow = psFlow != null ? psFlow : 0F;
			
			// there are only values for year 2012
			totalConsumption = psAnnualConsumption != null ? psAnnualConsumption.floatValue() : 0F;
			
			valueOfPipe = totalConsumption != 0F ? (float) (totalFlow / (totalConsumption * 0.8)) : 0F;
			meterValue = (valueOfPipe - 1) / pExtraWaterMeter.specialCautionLimit;
			
			if (meterValue < 0 || MathUtilSphinx.truncateDouble(meterValue.doubleValue(), 2) == 0D)
				meterValue = 0F;

			pExtraWaterMeter.ownerPsComponent = ownerPsComponent;
			pExtraWaterMeter.totalFlow = totalFlow;
			pExtraWaterMeter.totalConsumption = totalConsumption;
			pExtraWaterMeter.valueOfPipe = valueOfPipe;
			pExtraWaterMeter.meterValue = meterValue;
		
		return pExtraWaterMeter;
	}
	
	public static PipeBlockageMeter pipeBlockageMeter(Long pipeComponentId) {
		
		
		
		PipeBlockageMeter pBlockageMeter = new PipeBlockageMeter();
		Long amountOfBlockages = 0L;
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		// fetch blockage event related component properties of component where the event date is equal or after the pipe renovation (installation)
		amountOfBlockages = ComponentProperty.getPropertyCountEqualAndAfterComponentInstallationDate(pipeComponentId, 6660L);
		valueOfPipe = (float) amountOfBlockages;
		meterValue = valueOfPipe / pBlockageMeter.specialCautionLimit;
		
		pBlockageMeter.valueOfPipe = valueOfPipe;
		pBlockageMeter.meterValue = meterValue;
		
		return pBlockageMeter;
	}
	
	public static PipeBlockageMeter pipeBlockageMeter(Integer blockageCount) {
		
		PipeBlockageMeter pBlockageMeter = new PipeBlockageMeter();
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		valueOfPipe = blockageCount != null ? (float) blockageCount : 0F;
		meterValue = valueOfPipe / pBlockageMeter.specialCautionLimit;
		
		pBlockageMeter.valueOfPipe = valueOfPipe;
		pBlockageMeter.meterValue = meterValue;
		
		return pBlockageMeter;
	}
	
	/**
	 * @pre 
	 * @param pipeComponentId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static PipeFlushingEventMeter pipeFlushingEventMeter(Long pipeComponentId) {
			
		PipeFlushingEventMeter pFlushingEventMeter = new PipeFlushingEventMeter();
		Long amountOfFlushingEvents = 0L;
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		// fetch flushing event related component properties of component where the event date is equal or after the pipe renovation (installation)
		amountOfFlushingEvents = ComponentProperty.getPropertyCountEqualAndAfterComponentInstallationDate(pipeComponentId, 6700L);
		valueOfPipe = (float) amountOfFlushingEvents;
		meterValue = valueOfPipe / pFlushingEventMeter.specialCautionLimit;
		
		pFlushingEventMeter.valueOfPipe = valueOfPipe;
		pFlushingEventMeter.meterValue = meterValue;
		
		return pFlushingEventMeter;
	}
	
	/**
	 * @pre 
	 * @param pipeComponentId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static PipeFlushingEventMeter pipeFlushingEventMeter(Integer flushingCount) {
			
		PipeFlushingEventMeter pFlushingEventMeter = new PipeFlushingEventMeter();
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		valueOfPipe = flushingCount != null ? (float) flushingCount : 0F;
		meterValue = valueOfPipe / pFlushingEventMeter.specialCautionLimit;
		
		pFlushingEventMeter.valueOfPipe = valueOfPipe;
		pFlushingEventMeter.meterValue = meterValue;
		
		return pFlushingEventMeter;
	}
	
	public static PipeCctvTotalMeter pipeCctvTotalMeter(Long pipeComponentId) {
		
		PipeCctvTotalMeter pCctvTotalMeter = new PipeCctvTotalMeter();
		Double cctvTotal = 0D;
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		boolean isPipeAvailable = false;
		
		cctvTotal = ComponentProperty.getSumOfValueDecimal(pipeComponentId, 6620L);
		
		if (cctvTotal != null) {
			isPipeAvailable = true;
		} else cctvTotal = 0D;
		
		
		valueOfPipe = cctvTotal.floatValue();
		meterValue = valueOfPipe / pCctvTotalMeter.specialCautionLimit;
		
		pCctvTotalMeter.isAvailable = isPipeAvailable;
		pCctvTotalMeter.valueOfPipe = valueOfPipe;
		pCctvTotalMeter.meterValue = meterValue;
		
		return pCctvTotalMeter;
	}
	
	public static PipeCctvTotalMeter pipeCctvTotalMeter(Integer cctvSum) {
		
		PipeCctvTotalMeter pCctvTotalMeter = new PipeCctvTotalMeter();
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		boolean isPipeAvailable = false;
		
		if (cctvSum != null) {
			isPipeAvailable = true;
		} else cctvSum = 0;
		
		
		valueOfPipe = (float) cctvSum;
		meterValue = valueOfPipe / pCctvTotalMeter.specialCautionLimit;
		
		pCctvTotalMeter.isAvailable = isPipeAvailable;
		pCctvTotalMeter.valueOfPipe = valueOfPipe;
		pCctvTotalMeter.meterValue = meterValue;
		
		return pCctvTotalMeter;
	}
	
	public static PipeCctv34Meter pipeCctv34Meter(Long pipeComponentId) {
		
		PipeCctv34Meter pipeCctv34Meter = new PipeCctv34Meter();
//		Long pipeCasesOfCctv34Inspections = 0L;
		Double cctv34Total = 0D;
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
//		Double[] valueDecimalArray = {3D,4D};
		
		cctv34Total = ComponentProperty.getSumOfValueDecimalBiggerThenValue(pipeComponentId, 6620L, 2D);
				
//		pipeCasesOfCctv34Inspections = ComponentProperty.getCountOfPropertyTypeEntriesOfComponent(pipeComponentId, 6620L, valueDecimalArray);
		if (cctv34Total != null) {
		} else cctv34Total = 0D;
		
		valueOfPipe = cctv34Total.floatValue();
		meterValue = valueOfPipe / pipeCctv34Meter.specialCautionLimit;
		pipeCctv34Meter.valueOfPipe = valueOfPipe;
		pipeCctv34Meter.meterValue = meterValue;
		
		return pipeCctv34Meter;
	}
	
	public static PipeCctv34Meter pipeCctv34Meter(Integer cctv34Sum) {
		
		PipeCctv34Meter pipeCctv34Meter = new PipeCctv34Meter();
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		
		
		valueOfPipe = cctv34Sum != null ? (float) cctv34Sum : 0F;
		meterValue = valueOfPipe / pipeCctv34Meter.specialCautionLimit;
		
		pipeCctv34Meter.valueOfPipe = valueOfPipe;
		pipeCctv34Meter.meterValue = meterValue;
		
		return pipeCctv34Meter;
	}
}
