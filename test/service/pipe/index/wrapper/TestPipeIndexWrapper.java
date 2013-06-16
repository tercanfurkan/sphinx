package service.pipe.index.wrapper;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;

import org.junit.Test;

import models.form.PipeIndex;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeIndexWrapperView;
import play.db.jpa.JPA;
import service.PipeIndexServ;

public class TestPipeIndexWrapper {
	
	@Test
	public void testSavePipeIndexWrapper() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						List<PipeIndexWrapper> indexWrapperList = null;;
						List<PipeIndexWrapperView> pipeIndexWrapperViewList = null;
						PipeIndex pipeIndexDefault = new PipeIndex();
						
						pipeIndexWrapperViewList = PipeIndexWrapperView.getPipeIndexWrapperViewList();
						indexWrapperList = PipeIndexServ.calculatePipeIndex(pipeIndexWrapperViewList, pipeIndexDefault);
						int count = 1;
						for (PipeIndexWrapper wrapper : indexWrapperList) {
							wrapper.merge();
							count++;
							System.out.println(count);
						}
					}
				});
			}
		});
	}

}
