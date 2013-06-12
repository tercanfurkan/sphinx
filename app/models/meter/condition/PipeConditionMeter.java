package models.meter.condition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.TableGenerator;

@Entity
@Inheritance
public abstract class PipeConditionMeter {

	public PipeConditionMeter() {

		this.scale = "";
		this.specialCautionLimit = 0F;
		this.valueOfPipe = 0F;
		this.meterValue = 0F;
	}
	
	@Id
	@Column(name="id")
	@TableGenerator(name = "PipeConditionMeter_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "PipeConditionMeter_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PipeConditionMeter_seq")
	public Long id;

	public String scale;

	public Float specialCautionLimit;

	public Float valueOfPipe;

	public Float meterValue;

	@Override
	public String toString() {
		String retVal = "Scale: " + this.scale + " - SpecialCautionLimit: "
				+ this.specialCautionLimit + " - valueOfPipe: " + this.valueOfPipe
				+ " - meterValue: " + this.meterValue;
		
		return retVal;
	}
}
