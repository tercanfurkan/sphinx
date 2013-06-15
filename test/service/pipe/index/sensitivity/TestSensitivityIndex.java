package service.pipe.index.sensitivity;



import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Component;
import models.ComponentProperty;
import models.meter.condition.PipeCctv34Meter;
import models.meter.sensitivity.PipeSensitivityIndex;
import models.wrapper.PipeConditionIndexWrapper;
import models.wrapper.PipeSensitivityIndexWrapper;

import org.junit.Test;

import play.db.jpa.JPA;
import service.PipeConditionIndexMeterServ;
import service.PipeConditionIndexServ;
import service.PipeSensitivityIndexServ;
import util.DateUtilSphinx;

public class TestSensitivityIndex {

//	@Test
	public void testSensitivityIndex() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						// create variables for the pipe list to be processed
						List<Component> inScopePipeList = null;
						
						// create list object for storing sensitivity index results for all pipes in the list
						List<PipeSensitivityIndex> sensitivityIndexList = new ArrayList<>();
						
						// define start date and end date and wasteWaterTreatmentPlantAnnualFlow
						Date startDate = DateUtilSphinx.convertStringToDate("2009-01-01 00:00:00");
						Date endDate = DateUtilSphinx.convertStringToDate("2010-01-01 00:00:01");
						Float wasteWaterTreatmentPlantAnnualFlow = 6480161.45F;
						
						// fetch all pipe ids in the scope (pipes belonging to ps areas that has a flow in the db
						inScopePipeList = Component.getPipesInPsAreaHavingPsScadaHourly();
						// for each pipe id calculate the sensitivity index
						for(Component pipe : inScopePipeList) {
							System.out.println(pipe.id);
						
							// .. call sensitivity index calculation method and assign to the object
							PipeSensitivityIndex sensitivityIndex = PipeSensitivityIndexServ.getPipeSensitivityIndex(pipe, wasteWaterTreatmentPlantAnnualFlow, startDate, endDate);
							pipe.componentDetail.sensitivityIndex = sensitivityIndex;
							pipe.merge();
//							sensitivityIndexList.add(sensitivityIndex);
						}

						// print sensitivity index for each pipe
						for(PipeSensitivityIndex sensitivityIndex : sensitivityIndexList) {
							System.out.println("----------------------------RESULT-----------------------------");
							System.out.println(sensitivityIndex.toString());
						}
					}
				});
			}
		});
	}
	
//	@Test
	public void testSensitivityIndexBatch() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						Component comp = null;
						PipeSensitivityIndex sensitivityIndex = new PipeSensitivityIndex();
						
						List<PipeSensitivityIndexWrapper> wrapperList = ComponentProperty.getSensitivityMeterVals();
						
						// define wasteWaterTreatmentPlantAnnualFlow
						Float wasteWaterTreatmentPlantAnnualFlow = 35000000F;
						
						for (PipeSensitivityIndexWrapper wrapper : wrapperList) {
							comp = Component.findById(wrapper.pipeId);
							System.out.println(wrapper.pipeId);
							sensitivityIndex = PipeSensitivityIndexServ.getPipeSensitivityIndex(wrapper, wasteWaterTreatmentPlantAnnualFlow);
							comp.componentDetail.sensitivityIndex = sensitivityIndex;
							comp.merge();
						}
					}
				});
			}
		});
	}
}
