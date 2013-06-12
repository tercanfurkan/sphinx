package models.meter.sensitivity;

import javax.persistence.Entity;

@Entity
public class PipeAnnualWasteWaterFlowMeter extends AbstractPipeMeter {

	public PipeAnnualWasteWaterFlowMeter() {
		super();
		this.scale = "0-100%";
		this.specialCautionLimit = 500F;
		this.ownerPsComponent = "";
		this.pipeVolume = 0F;
		this.annualFlow = 0F;
	}
//	@Id
//	@Column(name="id")
//	@TableGenerator(name = "annualWasteWaterFlowMeter_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "annualWasteWaterFlowMeter_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "annualWasteWaterFlowMeter_seq")
//	public Long id;
	
	public String ownerPsComponent;
	public Float pipeVolume;
	public Float annualFlow;
	
	@Override
	public String toString() {
		String retVal = super.toString();
		retVal += " - ownerPsComponent: " + this.ownerPsComponent + " - pipeVolume: " + this.pipeVolume + " - annualFlow: " + this.annualFlow;
		
		return retVal;
	}
}
