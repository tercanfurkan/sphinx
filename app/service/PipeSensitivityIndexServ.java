package service;

import java.util.Date;

import util.DateUtilSphinx;

import models.Component;
import models.meter.sensitivity.PipeSensitivityIndex;
import models.wrapper.PipeSensitivityIndexWrapper;

public class PipeSensitivityIndexServ {
	
	public static PipeSensitivityIndex getPipeSensitivityIndex(Component pipeComponent, Float wasteWaterTreatmentPlantAnnualFlow, Date startDate, Date endDate) {
		
		PipeSensitivityIndex sensitivityIndex = new PipeSensitivityIndex();
		
		Date startDateAWWF = DateUtilSphinx.convertStringToDate("2011-01-01 00:00:00");
		Date endDateAWWF = DateUtilSphinx.convertStringToDate("2012-01-01 00:00:01");
		
		sensitivityIndex.annualWasteWaterFlowMeter = PipeSensitivityIndexMeterServ.pipeAnnualWasteWaterFlowMeter(pipeComponent.id, wasteWaterTreatmentPlantAnnualFlow, startDateAWWF, endDateAWWF);
		sensitivityIndex.groundWaterAreaMeter = PipeSensitivityIndexMeterServ.pipeGroundWaterAreaMeter(pipeComponent.id);
		sensitivityIndex.pressureMeter = PipeSensitivityIndexMeterServ.pipePressureMeter(pipeComponent.id);
		sensitivityIndex.relativeFloorAreaMeter = PipeSensitivityIndexMeterServ.pipeRelativeFloorAreaMeter(pipeComponent.id);
		sensitivityIndex.roadClassificationMeter = PipeSensitivityIndexMeterServ.pipeRoadClassificationMeter(pipeComponent.id);
		sensitivityIndex.pipeIdentifier = pipeComponent.name;
		sensitivityIndex.pipeOwnerComponent = pipeComponent.ownerComponent.name;
		return sensitivityIndex;
	}
	
	public static PipeSensitivityIndex getPipeSensitivityIndex(PipeSensitivityIndexWrapper wrapper, Float wasteWaterTreatmentPlantAnnualFlow) {
		
		PipeSensitivityIndex sensitivityIndex = new PipeSensitivityIndex();
		
		sensitivityIndex.annualWasteWaterFlowMeter = PipeSensitivityIndexMeterServ.pipeAnnualWasteWaterFlowMeter(wrapper.pipeDiameter);
		sensitivityIndex.groundWaterAreaMeter = PipeSensitivityIndexMeterServ.pipeGroundWaterAreaMeter(wrapper.pipeGroundWaterAreaClassification);
		sensitivityIndex.pressureMeter = PipeSensitivityIndexMeterServ.pipePressureMeter(wrapper.isPressurePipe);
		sensitivityIndex.relativeFloorAreaMeter = PipeSensitivityIndexMeterServ.pipeRelativeFloorAreaMeter(wrapper.allPipeLengthFloorArea, wrapper.totalFloorArea);
		sensitivityIndex.roadClassificationMeter = PipeSensitivityIndexMeterServ.pipeRoadClassificationMeter(wrapper.pipeRoadClassification);
		sensitivityIndex.pipeIdentifier = wrapper.pipeName;
		sensitivityIndex.pipeOwnerComponent = wrapper.psName;
		
		sensitivityIndex.calculateResult();
		return sensitivityIndex;
	}

}
