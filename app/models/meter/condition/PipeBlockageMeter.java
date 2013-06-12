package models.meter.condition;

import javax.persistence.Entity;


@Entity
public class PipeBlockageMeter extends PipeConditionMeter{
	
	public PipeBlockageMeter() {
		super();
		this.scale = "c/a";
		this.specialCautionLimit = 2F;
	}

}
