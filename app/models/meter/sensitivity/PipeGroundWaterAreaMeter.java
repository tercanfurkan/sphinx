package models.meter.sensitivity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class PipeGroundWaterAreaMeter extends AbstractPipeMeter{

	public PipeGroundWaterAreaMeter() {
		super();
		this.scale = "0-3";
		this.specialCautionLimit = 1F;
	}
	
//	@Id
//	@Column(name="id")
//	@TableGenerator(name = "groundWaterAreaMeter_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "groundWaterAreaMeter_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "groundWaterAreaMeter_seq")
//	public Long id;
}
