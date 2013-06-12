package models.meter.condition;

import javax.persistence.Entity;


@Entity
public class PipeFlushingEventMeter extends PipeConditionMeter{

	public PipeFlushingEventMeter() {
		super();
		this.scale = "c/a";
		this.specialCautionLimit = 2F;
	}
}
