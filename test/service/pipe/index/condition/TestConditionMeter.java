package service.pipe.index.condition;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.Date;

import models.meter.condition.PipeBlockageMeter;
import models.meter.condition.PipeCctv34Meter;
import models.meter.condition.PipeCctvTotalMeter;
import models.meter.condition.PipeExtraWaterMeter;
import models.meter.condition.PipeFlushingEventMeter;

import org.junit.Test;

import play.db.jpa.JPA;
import service.PipeConditionIndexMeterServ;
import util.DateUtilSphinx;

public class TestConditionMeter {

	//OK
//	@Test
	public void testPipeExtraWaterMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						Date startDate = DateUtilSphinx.convertStringToDate("2012-01-01 00:00:00");
						Date endDate = DateUtilSphinx.convertStringToDate("2013-01-01 00:00:01");
						
//						SpecialCautionLimit: 0.15 - valueOfPipe: 2.3627481 - meterValue: 15.751654 - totalFlow: 21695.7 - totalConsumption: 11478.0 - ownerPsComponent: JVP7
//						PipeExtraWaterMeter pPressureMeter = PipeConditionIndexMeterServ.pipeExtraWaterMeter(2253301L, startDate, endDate);
						
//						SpecialCautionLimit: 0.15 - valueOfPipe: 4.2207766 - meterValue: 28.13851 - totalFlow: 750953.8 - totalConsumption: 222398.0 - ownerPsComponent: JVP13
						PipeExtraWaterMeter pPressureMeter = PipeConditionIndexMeterServ.pipeExtraWaterMeter(2254651L, startDate, endDate);
						
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pPressureMeter.toString());
					}
				});
			}
		});
	}
	
	//OK
//	@Test
	public void testPipeBlockageMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						//1 blockage
//						PipeBlockageMeter pPressureMeter = PipeConditionIndexMeterServ.pipeBlockageMeter(2267169L);
						
						//2 blockage
//						PipeBlockageMeter pPressureMeter = PipeConditionIndexMeterServ.pipeBlockageMeter(2262445L);
						
						//1 blockage
						PipeBlockageMeter pPressureMeter = PipeConditionIndexMeterServ.pipeBlockageMeter(2266854L);
						
						// No blockage
//						PipeBlockageMeter pPressureMeter = PipeConditionIndexMeterServ.pipeBlockageMeter(2276044L);
						
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pPressureMeter.toString());
					}
				});
			}
		});
	}
	
	//OK
//	@Test
	public void testPipeFlushingEventMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						// No flushing events
						PipeFlushingEventMeter pMeter = PipeConditionIndexMeterServ.pipeFlushingEventMeter(2266854L);
						
						// 3 flushing events
//						PipeFlushingEventMeter pMeter = PipeConditionIndexMeterServ.pipeFlushingEventMeter(2253617L);
						
						// 17 flushing events
//						PipeFlushingEventMeter pMeter = PipeConditionIndexMeterServ.pipeFlushingEventMeter(2253657L);
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pMeter.toString());
					}
				});
			}
		});
	}
	
	//OK
//	@Test
	public void testPipeCctvTotalMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						// sum of flows for 2253483L = 18
//						PipeCctvTotalMeter pMeter = PipeConditionIndexMeterServ.pipeCctvTotalMeter(2257036L);
						
						// 4 inspections exist for 2253483L
//						PipeCctvTotalMeter pMeter = PipeConditionIndexMeter.pipeCctvTotalMeter(2253483L);
						
						// no inspections exist for 2299541L
						PipeCctvTotalMeter pMeter = PipeConditionIndexMeterServ.pipeCctvTotalMeter(2299541L);
						
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pMeter.toString());
					}
				});
			}
		});
	}
	
	
	//OK
//	@Test
	public void testPipeCctv34Meter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						// sum of 3 & 4 flows for 2253483L = 10
//						PipeCctv34Meter pMeter = PipeConditionIndexMeterServ.pipeCctv34Meter(2257036L);
						
						// 2 inspections exist for 2253483L
//						PipeCctv34Meter pMeter = PipeConditionIndexMeterServ.pipeCctv34Meter(2253483L);
						
						// no inspections exist for 2299541L
						PipeCctv34Meter pMeter = PipeConditionIndexMeterServ.pipeCctv34Meter(2299541L);
						
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pMeter.toString());
					}
				});
			}
		});
	}
}
