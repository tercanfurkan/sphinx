package models.meter.condition;

import javax.persistence.Entity;


@Entity
public class PipeCctvTotalMeter extends PipeConditionMeter{
	
	public PipeCctvTotalMeter() {
		super();
		this.scale = "c";
		this.specialCautionLimit = 10F;
		this.isAvailable = false;
	}
	
	public boolean isAvailable;
	
	@Override
	public String toString() {
		String retVal = super.toString();
		retVal += " - isAvailable: " + this.isAvailable;
		
		return retVal;
	}
}
