package util.datacorrection.pipe;

import java.util.List;

import models.Component;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import org.junit.Test;

import play.db.jpa.JPA;

public class DiameterCorrection {

	
	public void correctPipeDiameterSlash() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						List<Component> componentList = Component.findByComponentType(2L);
						
						for (Component comp : componentList) {
							DiameterCorrector.correctComponentDiameterSlash(comp.id);
						}
					}
				});
			}
		});
	}
	
	
	public void copyCorrectPipeDiameterToDiameterValue() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						List<Component> componentList = Component.findByComponentType(2L);
						int count = 0;
						for (Component comp : componentList) {
							DiameterCorrector.copyCorrectDiameterToDiameterValue(comp.id);
						}
					}
				});
			}
		});
	}
	

	public void setClassAverageDiameterWhereDiameterEmpty() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						List<Component> componentList = Component.findByComponentType(2L);
						for (Component comp : componentList) {
							DiameterCorrector.setClassAverageDiameterWhereDiameterEmpty(comp.id);
						}
					}
				});
			}
		});
	}
}
