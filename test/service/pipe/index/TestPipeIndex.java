package service.pipe.index;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


import models.Component;
import models.ComponentProperty;
import models.PipeIndexResult;
import models.form.PipeIndex;
import models.meter.condition.PipeConditionIndex;
import models.wrapper.PipeConditionIndexWrapper;
import models.wrapper.PipeIndexSummary;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeIndexWrapper.PipeIndexWrapperPage;
import play.data.Form;
import play.db.jpa.JPA;
import service.PipeConditionIndexServ;
import util.MathUtilSphinx;

public class TestPipeIndex {
	
//	@Test
	public void testConditionIndexBatch() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						List<PipeIndexWrapper> indexWrapperList = null;
						
						indexWrapperList = ComponentProperty.getAllMeterVals();
						int count = 0;
						for(PipeIndexWrapper wrapper: indexWrapperList) {
							System.out.println(count);
							System.out.println(wrapper.pipe_identifier);
							count++;
							if (count == 50) break;
						}

					}
				});
			}
		});
	}

}
