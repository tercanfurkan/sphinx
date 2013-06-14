package service.integration.component;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;

import org.junit.Test;

import models.ComponentProperty;
import models.wrapper.PipeIndexWrapper;
import play.db.jpa.JPA;

public class TestComponentIntegration {
	
//	@Test
	public void setParentOfChildComponent() {
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
