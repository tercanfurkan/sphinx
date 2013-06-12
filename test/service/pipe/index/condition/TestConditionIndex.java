package service.pipe.index.condition;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.constant.SysProperties;
import org.junit.Test;

import models.Component;
import models.ComponentProperty;
import models.PsScadaHourly;
import models.meter.condition.PipeConditionIndex;
import models.wrapper.PipeConditionIndexWrapper;
import play.db.jpa.JPA;
import service.PipeConditionIndexServ;
import util.DateUtilSphinx;

public class TestConditionIndex {

	// @Test
	public void testConditionIndex() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						// create variables for the pipe list to be processed
						List<Component> inScopePipeList = null;

						// define start date and end date and
						// wasteWaterTreatmentPlantAnnualFlow
						Date startDate = DateUtilSphinx
								.convertStringToDate("2012-01-01 00:00:00");
						Date endDate = DateUtilSphinx
								.convertStringToDate("2013-01-01 00:00:01");

						// fetch all pipe ids in the scope (pipes belonging to
						// ps areas that has a flow in the db
						inScopePipeList = Component
								.getPipesInPsAreaHavingPsScadaHourly();
						// for each pipe id calculate the condition index
						int count = 0;
						for (Component pipe : inScopePipeList) {
							System.out.println(pipe.id);
							if (count == 3000)
								return;
							count++;

							// .. call condition index calculation method and
							// assign to the object
							// System.out.println("getPipeConditionIndex()");
							PipeConditionIndex conditionIndex = PipeConditionIndexServ
									.getPipeConditionIndex(pipe, startDate,
											endDate);
							pipe.componentDetail.conditionIndex = conditionIndex;
							// System.out.println("merge()");
							pipe.merge();
							// System.out.println("COMPLETE");

						}
					}
				});
			}
		});
	}

	//OK
	@Test
	public void testConditionIndexBatch() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
//						Date startDate = DateUtilSphinx
//								.convertStringToDate("2012-01-01 00:00:00");
//						Date endDate = DateUtilSphinx
//								.convertStringToDate("2013-01-01 00:00:01");
						
						List<PipeConditionIndexWrapper> wrapperList = null;
//						List<Object[]> flowWrapperList = null;
//						Double psFlow = null;
						Component comp = null;
						PipeConditionIndex conditionIndex = new PipeConditionIndex();
						
//						Map<Long, Double> flowWrapperMap = new HashMap<>();
						
						wrapperList = ComponentProperty
								.getPipeConditionMeterVals();
						
//						flowWrapperList = PsScadaHourly.getAllPsSumOfFI_01(startDate, endDate);
						
//						for(Object[] flowWrapper : flowWrapperList) {
//							flowWrapperMap.put(((BigInteger)flowWrapper[0]).longValue(), (Double)flowWrapper[1]);
//						}
						
						for (PipeConditionIndexWrapper wrapper : wrapperList) {
//							psFlow = flowWrapperMap.get(wrapper.psId);
							comp = Component.findById(wrapper.pipeId);
							conditionIndex = PipeConditionIndexServ.getPipeConditionIndex(wrapper);
							comp.componentDetail.conditionIndex = conditionIndex;
							comp.merge();
							System.out.println(wrapper.pipeId);
						}

					}
				});
			}
		});
	}
}
