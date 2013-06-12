package service;

import java.util.Date;

import models.BuildingArea;
import models.Component;
import models.PsScadaHourly;
import models.meter.sensitivity.PipeAnnualWasteWaterFlowMeter;
import models.meter.sensitivity.PipeGroundWaterAreaMeter;
import models.meter.sensitivity.PipePressureMeter;
import models.meter.sensitivity.PipeRelativeFloorAreaMeter;
import models.meter.sensitivity.PipeRoadClassificationMeter;

public class PipeSensitivityIndexMeterServ {

	/**
	 * @post if the componentId represent a pressure pipe class pipe, returns 1
	 *       else 0
	 * 
	 * @param pipeComponentId
	 * @return
	 */
	public static PipePressureMeter pipePressureMeter(Long pipeComponentId) {

		// read special caution limit value (1)
		// assign scale (0/1)
		// Contractor assignes special caution limit value as 1, and scale as
		// (0/1)
		PipePressureMeter pressurePipeMeter = new PipePressureMeter();

		// read pipe and assign value either if it is a pressure pipe or not
		// (0/1)
		String[] pressurePipeClassArray = { "Paineviemäri - Jäte",
				"Paineviemäri, digit. - Jäte", "Paineviemäri, epävarma - Jäte",
				"Paineviemäri, korkeus arvioitu" };
		boolean isMemberOfList = Component.isMemberOfClassList(pipeComponentId,
				pressurePipeClassArray);
		pressurePipeMeter.valueOfPipe = isMemberOfList ? 1F : 0F;

		// divide pipe value by limit value
		pressurePipeMeter.meterValue = (pressurePipeMeter.valueOfPipe / pressurePipeMeter.specialCautionLimit);

		return pressurePipeMeter;
	}

	public static PipePressureMeter pipePressureMeter(boolean isPressurePipe) {

		// read special caution limit value (1)
		// assign scale (0/1)
		// Contractor assignes special caution limit value as 1, and scale as
		// (0/1)
		PipePressureMeter pressurePipeMeter = new PipePressureMeter();
		pressurePipeMeter.valueOfPipe = isPressurePipe ? 1F : 0F;

		// divide pipe value by limit value
		pressurePipeMeter.meterValue = (pressurePipeMeter.valueOfPipe / pressurePipeMeter.specialCautionLimit);

		return pressurePipeMeter;
	}

	/**
	 * @post return meter value 0 if pipe not in any groundwater area which
	 *       means logically groundwater area classigication is 0
	 * 
	 * @param pipeComponentId
	 * @return
	 */

	public static PipeGroundWaterAreaMeter pipeGroundWaterAreaMeter(
			Long pipeComponentId) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		PipeGroundWaterAreaMeter pGroundWaterAreaMeter = new PipeGroundWaterAreaMeter();

		// fetch component and ground water area of the component by componentId
		Component pipeComponent = Component.findById(pipeComponentId);

		// assign value of pipe the classification value of the groundwaterarea
		// if pipe does not belong to any groundwaterarea it is 0
		valueOfPipe = (float) (pipeComponent.groundWaterArea != null ? pipeComponent.groundWaterArea.classificationValue
				: 0);

		// divide value of pipe by specialCationLimit
		meterValue = valueOfPipe / pGroundWaterAreaMeter.specialCautionLimit;

		// fill the pipeGroundWaterAreaMeter object and return it
		pGroundWaterAreaMeter.valueOfPipe = valueOfPipe;
		pGroundWaterAreaMeter.meterValue = meterValue;

		return pGroundWaterAreaMeter;
	}

	public static PipeGroundWaterAreaMeter pipeGroundWaterAreaMeter(
			Integer pipeGroundWaterAreaClassification) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		PipeGroundWaterAreaMeter pGroundWaterAreaMeter = new PipeGroundWaterAreaMeter();

		// assign value of pipe the classification value of the groundwaterarea
		// if pipe does not belong to any groundwaterarea it is 0
		valueOfPipe = (float) pipeGroundWaterAreaClassification;

		// divide value of pipe by specialCationLimit
		meterValue = valueOfPipe / pGroundWaterAreaMeter.specialCautionLimit;

		// fill the pipeGroundWaterAreaMeter object and return it
		pGroundWaterAreaMeter.valueOfPipe = valueOfPipe;
		pGroundWaterAreaMeter.meterValue = meterValue;

		return pGroundWaterAreaMeter;
	}

	/**
	 * @post returns meter value 0 if no ownerComponent exist or if no
	 *       calculated annual flow for the onwerComponent or if pipe diameter
	 *       is not string to float convertable
	 * 
	 * @param pipeComponentId
	 * @param totalAnnualWasteWaterFlow
	 * @param startDate
	 * @param endDate
	 * @return PipeAnnualWasteWaterFlowMeter object
	 */
	public static PipeAnnualWasteWaterFlowMeter pipeAnnualWasteWaterFlowMeter(
			Long pipeComponentId, Float totalAnnualWasteWaterFlow,
			Date startDate, Date endDate) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		Double annualFlow = 0D;
		Float pipeVolume = 0F;
		Float pipeDiameter = 0F;
		Float pipeLength = 0F;
		Float pipeRadius = 0F;
		Float pipeDiameterInMeters = 0F;
		Component pipeComponent = null;
		Component ownerPsComponent = null;
		PipeAnnualWasteWaterFlowMeter pWasteWaterFlowMeter = new PipeAnnualWasteWaterFlowMeter();

		// fetch pipe component by component id
		// the volume and the owner component is needed
		pipeComponent = Component.findById(pipeComponentId);

		// fetch owner owner component
		ownerPsComponent = pipeComponent.ownerComponent;

		// get annual flow of the component (annual wastewater flow of the ps)
		// if ownercomponent exist and if wastewater flow exist get annual flow
		if (ownerPsComponent == null) {

			System.out.println("PS of pipe not defined in Sphinx");
			return pWasteWaterFlowMeter;
		} else {

			annualFlow = PsScadaHourly.getArealSumOfFI_01(ownerPsComponent.id,
					startDate, endDate);

			if (annualFlow != null && annualFlow != 0D) {

				// get pipe diameter and pipe length
				pipeDiameter = (float) pipeComponent.componentDetail.diameterValue;
				pipeLength = pipeComponent.componentDetail.length_3d
						.floatValue();

				// calculate volume of pipe V = pi*r*r*h

				// convert diameter from mm to m
				pipeDiameterInMeters = (float) (pipeDiameter / 1000F);

				// get radius from diameter
				pipeRadius = pipeDiameterInMeters / 2F;

				pipeVolume = (float) (Math.PI * Math.pow(pipeRadius, 2) * pipeLength);

				// calculate value of pipe
				// ps annual flow * pipe volume / totalAnnualWasteWaterFlow
				valueOfPipe = (float) (annualFlow * pipeVolume / totalAnnualWasteWaterFlow);

				// calculate meter value and fill PipeAnnualWasteWaterFlowMeter
				// object
				// divide value of pipe by specialCationLimit
				meterValue = valueOfPipe
						/ pWasteWaterFlowMeter.specialCautionLimit;
				pWasteWaterFlowMeter.ownerPsComponent = ownerPsComponent.name;
				pWasteWaterFlowMeter.pipeVolume = pipeVolume;
				pWasteWaterFlowMeter.annualFlow = annualFlow.floatValue();
				pWasteWaterFlowMeter.valueOfPipe = valueOfPipe;
				pWasteWaterFlowMeter.meterValue = meterValue;
			} // end if

		} // end if

		return pWasteWaterFlowMeter;
	}

	public static PipeAnnualWasteWaterFlowMeter pipeAnnualWasteWaterFlowMeter(
			Integer pipeDiameter) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		PipeAnnualWasteWaterFlowMeter pWasteWaterFlowMeter = new PipeAnnualWasteWaterFlowMeter();

		valueOfPipe = pipeDiameter.floatValue();

		// calculate meter value and fill PipeAnnualWasteWaterFlowMeter object
		// divide value of pipe by specialCationLimit
		meterValue = valueOfPipe / pWasteWaterFlowMeter.specialCautionLimit;
		pWasteWaterFlowMeter.valueOfPipe = valueOfPipe;
		pWasteWaterFlowMeter.meterValue = meterValue;

		return pWasteWaterFlowMeter;
	}

	public static PipeAnnualWasteWaterFlowMeter pipeAnnualWasteWaterFlowMeter(
			Float psFlowSum, Integer pipeDiameter, Double pipeLength,
			String psName, Float totalAnnualWasteWaterFlow) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		Float pipeRadius = 0F;
		Float pipeDiameterInMeters = 0F;
		Float pipeVolume = 0F;
		PipeAnnualWasteWaterFlowMeter pWasteWaterFlowMeter = new PipeAnnualWasteWaterFlowMeter();

		// calculate volume of pipe V = pi*r*r*h

		// convert diameter from mm to m
		pipeDiameterInMeters = (float) (pipeDiameter / 1000F);

		// get radius from diameter
		pipeRadius = pipeDiameterInMeters / 2F;

		pipeVolume = (float) (Math.PI * Math.pow(pipeRadius, 2) * pipeLength);

		// calculate value of pipe
		// ps annual flow * pipe volume / totalAnnualWasteWaterFlow
		valueOfPipe = (float) (psFlowSum * pipeVolume / totalAnnualWasteWaterFlow);

		// calculate meter value and fill PipeAnnualWasteWaterFlowMeter object
		// divide value of pipe by specialCationLimit
		meterValue = valueOfPipe / pWasteWaterFlowMeter.specialCautionLimit;
		pWasteWaterFlowMeter.ownerPsComponent = psName;
		pWasteWaterFlowMeter.pipeVolume = pipeVolume;
		pWasteWaterFlowMeter.annualFlow = psFlowSum;
		pWasteWaterFlowMeter.valueOfPipe = valueOfPipe;
		pWasteWaterFlowMeter.meterValue = meterValue;

		return pWasteWaterFlowMeter;
	}

	/**
	 * @post returns full object if pipe belongs to any building area returns
	 *       meter value 0 if pipeLength or totalFloorArea is 0 or if pipe does
	 *       not belong to any building area
	 * 
	 * @param pipeComponentId
	 * @return PipeRelativeFloorAreaMeter
	 * 
	 */
	public static PipeRelativeFloorAreaMeter pipeRelativeFloorAreaMeter(
			Long pipeComponentId) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		Component pipeComponent = null;
		Float pipeLength = 0F;
		Float totalPipeLengthInFloorArea = 0F;
		BuildingArea pipeBuildingArea = null;
		PipeRelativeFloorAreaMeter pRelativeFloorAreaMeter = new PipeRelativeFloorAreaMeter();

		// get pipe length and building area
		pipeComponent = Component.findById(pipeComponentId);
		assert pipeComponent.componentDetail != null : "pipe does not have component detail attached";
		pipeLength = pipeComponent.componentDetail.length_3d.floatValue();

		pipeBuildingArea = pipeComponent.buildingArea;
		// there is no meter to calculate if pipe does not belong to any area
		// return 0 as metervalue
		if (pipeBuildingArea == null) {
			// TODO: make sure pipeLength is correctly returned
			return pRelativeFloorAreaMeter;
		}

		// fetch all pipes in the same building area. calculate sum of all pipe
		// lengths
		// assign the value to totalPipeLengthInFloorArea
		totalPipeLengthInFloorArea = Component
				.getTotalLenghtOfPipesInBuildingArea(pipeBuildingArea.id)
				.floatValue();

		// calculate value of pipe
		// pipeLength * floorArae / totalPipeLengthInFloorArea
		valueOfPipe = pipeLength * pipeBuildingArea.totalFloorArea
				/ totalPipeLengthInFloorArea;

		// calculate meter value
		// divide value of pipe by specialCautionLimit
		meterValue = valueOfPipe / pRelativeFloorAreaMeter.specialCautionLimit;

		// fill PipeRelativeFloorAreaMeter object and return
		pRelativeFloorAreaMeter.pipeLength = pipeLength;
		pRelativeFloorAreaMeter.floorArea = pipeBuildingArea.totalFloorArea;
		pRelativeFloorAreaMeter.totalPipeLengthInFloorArea = totalPipeLengthInFloorArea;
		pRelativeFloorAreaMeter.valueOfPipe = valueOfPipe;
		pRelativeFloorAreaMeter.meterValue = meterValue;

		return pRelativeFloorAreaMeter;
	}

	public static PipeRelativeFloorAreaMeter pipeRelativeFloorAreaMeter(
			Double pipeLength, Double allPipeLengthFloorArea,
			Long totalFloorArea) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		PipeRelativeFloorAreaMeter pRelativeFloorAreaMeter = new PipeRelativeFloorAreaMeter();

		// if there is no pipe inside floor area
		if (allPipeLengthFloorArea == 0D) {
			valueOfPipe = 0F;
		} else {
			// calculate value of pipe
			// pipeLength * floorArae / totalPipeLengthInFloorArea
			valueOfPipe = (float) (pipeLength * totalFloorArea / allPipeLengthFloorArea);
		}

		// calculate meter value
		// divide value of pipe by specialCautionLimit
		meterValue = valueOfPipe / pRelativeFloorAreaMeter.specialCautionLimit;

		// fill PipeRelativeFloorAreaMeter object and return
		pRelativeFloorAreaMeter.pipeLength = pipeLength.floatValue();
		pRelativeFloorAreaMeter.floorArea = totalFloorArea;
		pRelativeFloorAreaMeter.totalPipeLengthInFloorArea = allPipeLengthFloorArea
				.floatValue();
		pRelativeFloorAreaMeter.valueOfPipe = valueOfPipe;
		pRelativeFloorAreaMeter.meterValue = meterValue;

		return pRelativeFloorAreaMeter;
	}

	public static PipeRelativeFloorAreaMeter pipeRelativeFloorAreaMeter(
			Double allPipeLengthFloorArea, Long totalFloorArea) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		PipeRelativeFloorAreaMeter pRelativeFloorAreaMeter = new PipeRelativeFloorAreaMeter();

		// if there is no pipe inside floor area
		if (allPipeLengthFloorArea == 0D) {
			valueOfPipe = 0F;
		} else {
			// calculate value of pipe
			// pipeLength * floorArae / totalPipeLengthInFloorArea
			valueOfPipe = (float) (totalFloorArea / allPipeLengthFloorArea);
		}

		// calculate meter value
		// divide value of pipe by specialCautionLimit
		meterValue = valueOfPipe / pRelativeFloorAreaMeter.specialCautionLimit;

		// fill PipeRelativeFloorAreaMeter object and return
		pRelativeFloorAreaMeter.floorArea = totalFloorArea;
		pRelativeFloorAreaMeter.totalPipeLengthInFloorArea = allPipeLengthFloorArea
				.floatValue();
		pRelativeFloorAreaMeter.valueOfPipe = valueOfPipe;
		pRelativeFloorAreaMeter.meterValue = meterValue;

		return pRelativeFloorAreaMeter;
	}

	/**
	 * @post returns metervalue as 0 if pipe has no road classification which
	 *       logically means pipe road classification value is 0 in all other
	 *       cases returns road classification value divided by the
	 *       specialCautionLimit
	 * 
	 * @param pipeComponentId
	 * @return
	 */
	public static PipeRoadClassificationMeter pipeRoadClassificationMeter(
			Long pipeComponentId) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		Component pipeComponent = null;
		PipeRoadClassificationMeter pRoadClassificationMeter = new PipeRoadClassificationMeter();

		// fetch pipe component and assign roadClassification value to
		// valueOfPipe
		pipeComponent = Component.findById(pipeComponentId);
		valueOfPipe = pipeComponent.roadClassification != null ? pipeComponent.roadClassification
				: 0F;

		// calculate meterValue
		meterValue = valueOfPipe / pRoadClassificationMeter.specialCautionLimit;

		// fill PipeRoadClassificationMeter object and return
		pRoadClassificationMeter.valueOfPipe = valueOfPipe;
		pRoadClassificationMeter.meterValue = meterValue;

		return pRoadClassificationMeter;
	}

	public static PipeRoadClassificationMeter pipeRoadClassificationMeter(
			Integer pipeRoadClassification) {

		// create temp variables for valueOfPipe & meterValue
		Float valueOfPipe = 0F;
		Float meterValue = 0F;
		PipeRoadClassificationMeter pRoadClassificationMeter = new PipeRoadClassificationMeter();

		// assign roadClassification value to valueOfPipe
		valueOfPipe = (float) pipeRoadClassification;

		// calculate meterValue
		meterValue = valueOfPipe / pRoadClassificationMeter.specialCautionLimit;

		// fill PipeRoadClassificationMeter object and return
		pRoadClassificationMeter.valueOfPipe = valueOfPipe;
		pRoadClassificationMeter.meterValue = meterValue;

		return pRoadClassificationMeter;
	}
}
