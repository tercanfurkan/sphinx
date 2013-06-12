package service;

import java.util.Date;
import java.util.List;

import util.DateUtilSphinx;
import util.MathUtilSphinx;


import models.BuildingArea;
import models.Component;
import models.ComponentProperty;
import models.PsScadaHourly;
import models.meter.sensitivity.PipeAnnualWasteWaterFlowMeter;
import models.meter.sensitivity.PipeGroundWaterAreaMeter;
import models.meter.sensitivity.PipePressureMeter;
import models.meter.sensitivity.PipeRelativeFloorAreaMeter;
import models.meter.sensitivity.PipeRoadClassificationMeter;


public class MeterService {

	public static Double extraWater(Long componentId) {
		
		Date dateMin1 = DateUtilSphinx.convertStringToDate("2011-01-01 00:00:00");
		Date dateMax1 = DateUtilSphinx.convertStringToDate("2012-00-01 00:00:01");
		
		Date dateMin = DateUtilSphinx.convertStringToDate("2012-01-01 00:00:00");
		Date dateMax = DateUtilSphinx.convertStringToDate("2013-00-01 00:00:01");

		Double totalFlow = PsScadaHourly.getArealSumOfFI_01(componentId, dateMin, dateMax);
		System.out.println("total flow: " + totalFlow);
		Double totalConsumption = ComponentProperty.getArealSumOFYearlyConsumption(componentId, dateMin, dateMax);
		System.out.println("total consumption: " + totalConsumption);
		Double retVal = (double) (totalFlow/(totalConsumption*0.8));
		return MathUtilSphinx.truncateDouble(retVal,2);
	}
	
	public static Float getMeterResult (Long componentId, List<Long> propertTypeIdList, String calculationString, Date dateMin, Date dateMax) {
		
		Long propertyTypeId = propertTypeIdList.get(0);
		Double cpSumOfDecimalValues =  ComponentProperty.getSumOfDecimalValues(componentId, propertyTypeId, dateMin, dateMax);
		
		return 0F;
	}
	
	public static Double getStructuralCondition(Long componentId) {
		
		Date dateMin1 = DateUtilSphinx.convertStringToDate("2011-01-01 00:00:00");
		Date dateMax1 = DateUtilSphinx.convertStringToDate("2012-00-01 00:00:01");
		
		Date dateMin = DateUtilSphinx.convertStringToDate("2012-01-01 00:00:00");
		Date dateMax = DateUtilSphinx.convertStringToDate("2013-00-01 00:00:01");
		
		Double sumOfFlaws = ComponentProperty.getSumOfFlaws(componentId, 6620L);
		System.out.println("sumOfFlaws: " + sumOfFlaws);
		Double totalDistanceOfInspectedPipes = ComponentProperty.getTotalDistanceOfInspectedPipesOfPs(componentId, 6620L);
		System.out.println("totalDistanceOfInspectedPipes: " + totalDistanceOfInspectedPipes);
		Double result = -1D;
		
		try {
			result = sumOfFlaws/totalDistanceOfInspectedPipes;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return MathUtilSphinx.truncateDouble(result,2);
	}
	
	public static Double getOperationalDisturbance(Long componentId) {
		
		Date dateMin = DateUtilSphinx.convertStringToDate("2002-01-01 00:00:00");
		
		Long amountOfBlockages = 0L;
		Long amountOfFlushingEvents = 0L;
		Double totalDistanceOfPipes = 0D;
		
		amountOfBlockages = ComponentProperty.getAmountOfProperty(componentId, 6660L, dateMin);
		System.out.println("amountOfBlockages: " + amountOfBlockages);
		amountOfFlushingEvents = ComponentProperty.getAmountOfProperty(componentId, 6700L);
		System.out.println("amountOfFlushingEvents: " + amountOfFlushingEvents);
		totalDistanceOfPipes =  ComponentProperty.getTotalDistanceOfPipesOfPs(componentId);
		totalDistanceOfPipes = totalDistanceOfPipes * 0.001;
		System.out.println("totalDistanceOfPipes: " + totalDistanceOfPipes);
		Double result = -1D;
		
		try {
			result = (amountOfBlockages/totalDistanceOfPipes*10) + (amountOfFlushingEvents/totalDistanceOfPipes*2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return MathUtilSphinx.truncateDouble(result, 2);
	}
	
	public static Double getSocialSensitivity(Long componentId) {
		
		Date dateMin1 = DateUtilSphinx.convertStringToDate("2011-01-01 00:00:00");
		Date dateMax1 = DateUtilSphinx.convertStringToDate("2012-00-01 00:00:01");
		
		Date dateMin = DateUtilSphinx.convertStringToDate("2012-01-01 00:00:00");
		Date dateMax = DateUtilSphinx.convertStringToDate("2013-00-01 00:00:01");
		
		Double arealAnnualWaterConsumption = 0D;
		Double arealAnnualWasteWater = 0D;
		Double annualWaterConsumption = 0D;
		Double annualWasteWater = 0D;
		Double arealTotalLenghtOfPressurePipes = 0D;
		Double totalLenghtOfPressurePipes = 0D;
		
		arealAnnualWasteWater = PsScadaHourly.getArealSumOfFI_01(componentId, dateMin, dateMax);
		annualWasteWater = PsScadaHourly.getSumOfFI_01(dateMin, dateMax);
		arealAnnualWaterConsumption = ComponentProperty.getArealSumOFYearlyConsumption(componentId, dateMin, dateMax);
		annualWaterConsumption = ComponentProperty.getSumOFYearlyConsumption(dateMin, dateMax);
		
		arealTotalLenghtOfPressurePipes = Component.getTotalLenghtOfPipes(componentId, "Paineviemäri - Jäte");
		arealTotalLenghtOfPressurePipes = arealTotalLenghtOfPressurePipes + Component.getTotalLenghtOfPipes(componentId, "Paineviemäri, digit. - Jäte");
		arealTotalLenghtOfPressurePipes = arealTotalLenghtOfPressurePipes + Component.getTotalLenghtOfPipes(componentId, "Paineviemäri, epävarma - Jäte");
		arealTotalLenghtOfPressurePipes = arealTotalLenghtOfPressurePipes + Component.getTotalLenghtOfPipes(componentId, "Paineviemäri, korkeus arvioitu");
		
		totalLenghtOfPressurePipes = Component.getTotalLenghtOfPipes("Paineviemäri - Jäte");
		totalLenghtOfPressurePipes = totalLenghtOfPressurePipes + Component.getTotalLenghtOfPipes("Paineviemäri, digit. - Jäte");
		totalLenghtOfPressurePipes = totalLenghtOfPressurePipes + Component.getTotalLenghtOfPipes("Paineviemäri, epävarma - Jäte");
		totalLenghtOfPressurePipes = totalLenghtOfPressurePipes + Component.getTotalLenghtOfPipes("Paineviemäri, korkeus arvioitu");
		
		System.out.println("a water consumption : "+arealAnnualWaterConsumption);
		System.out.println(" water consumption : "+annualWaterConsumption);
		System.out.println("a waste water : "+arealAnnualWasteWater);
		System.out.println("waste water : "+annualWasteWater);
		System.out.println("a total length : "+arealTotalLenghtOfPressurePipes);
		System.out.println("total length"+totalLenghtOfPressurePipes);
		
		
		Double retVal = 0D;
		try {
			retVal = (arealAnnualWaterConsumption/annualWaterConsumption) + (arealAnnualWasteWater/annualWasteWater) + (arealTotalLenghtOfPressurePipes/totalLenghtOfPressurePipes);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return MathUtilSphinx.truncateDouble(retVal,2);
	}
}
