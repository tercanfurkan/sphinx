package models.meter.condition;

import javax.persistence.Entity;


@Entity
public class PipeCctv34Meter extends PipeConditionMeter {
	
	public PipeCctv34Meter() {
		super();
		this.scale = "c";
		this.specialCautionLimit = 7F;
	}
}
