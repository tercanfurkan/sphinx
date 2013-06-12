package service.pipe.index.sensitivity;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.Date;

import models.meter.sensitivity.PipeAnnualWasteWaterFlowMeter;
import models.meter.sensitivity.PipeGroundWaterAreaMeter;
import models.meter.sensitivity.PipePressureMeter;
import models.meter.sensitivity.PipeRelativeFloorAreaMeter;
import models.meter.sensitivity.PipeRoadClassificationMeter;
import play.db.jpa.JPA;
import service.PipeSensitivityIndexMeterServ;
import util.DateUtilSphinx;

public class TestSensitivityMeter {

	public void testPipePressureMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						PipePressureMeter pPressureMeter = PipeSensitivityIndexMeterServ.pipePressureMeter(2275771L);
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pPressureMeter.toString());
					}
				});
			}
		});
	}
	

	public void testPipeGroundWaterAreaMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						PipeGroundWaterAreaMeter pPressureMeter = PipeSensitivityIndexMeterServ.pipeGroundWaterAreaMeter(2253376L);
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pPressureMeter.toString());
					}
				});
			}
		});
	}
	
	
	
	
	public void testPipeRelativeFloorAreaMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						PipeRelativeFloorAreaMeter pPressureMeter = PipeSensitivityIndexMeterServ.pipeRelativeFloorAreaMeter(2276158L);
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pPressureMeter.toString());
					}
				});
			}
		});
	}
	
	
	public void testPipeRoadClassificationMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						PipeRoadClassificationMeter pPressureMeter = PipeSensitivityIndexMeterServ.pipeRoadClassificationMeter(2253327L);
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pPressureMeter.toString());
					}
				});
			}
		});
	}
	
	
	public void testPipeAnnualWasteWaterFlowMeter() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						Date startDate = DateUtilSphinx.convertStringToDate("2011-01-01 00:00:00");
						Date endDate = DateUtilSphinx.convertStringToDate("2012-0-01 00:00:01");
						
						PipeAnnualWasteWaterFlowMeter pPressureMeter = PipeSensitivityIndexMeterServ.pipeAnnualWasteWaterFlowMeter(2299548L, 2292134F, startDate, endDate);
						System.out.println("----------------------------RESULT-----------------------------");
						System.out.println(pPressureMeter.toString());
					}
				});
			}
		});
	}
}
