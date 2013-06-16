package models.meter.condition;

import javax.persistence.Entity;


@Entity
public class PipeExtraWaterMeter extends PipeConditionMeter {

	public PipeExtraWaterMeter() {
		super();
		this.specialCautionLimit = 15F;
		this.totalFlow = 0F;
		this.totalConsumption = 0F;
		this.ownerPsComponent = "";
	}
	
	public Float totalFlow;
	public Float totalConsumption;
	public String ownerPsComponent;
	
	@Override
	public String toString() {
		String retVal = super.toString();
		retVal += " - totalFlow: " + this.totalFlow + " - totalConsumption: " + this.totalConsumption + " - ownerPsComponent: " + this.ownerPsComponent;
		
		return retVal;
	}
}
