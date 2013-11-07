package models.meter.sensitivity;

import javax.persistence.Entity;

@Entity
public class PipeRelativeFloorAreaMeter extends AbstractPipeMeter {

	public PipeRelativeFloorAreaMeter() {
		super();
		this.scale = "0-100%";
		this.specialCautionLimit = 20F;
		this.pipeLength = 0F;
		this.floorArea = 0L;
		this.totalPipeLengthInFloorArea = 0F;
	}
//	@Id
//	@Column(name="id")
//	@TableGenerator(name = "relativeFloorAreaMeter_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "relativeFloorAreaMeter_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "relativeFloorAreaMeter_seq")
//	public Long id;
	
	public Float pipeLength;
	public Long floorArea;
	public Float totalPipeLengthInFloorArea;

	@Override
	public String toString() {
		String retVal = super.toString();
		retVal += " - pipeLength: " + this.pipeLength + " - floorArea: " + this.floorArea + " - totalPipeLengthInFloorArea: " + this.totalPipeLengthInFloorArea;
		
		return retVal;
	}
}
