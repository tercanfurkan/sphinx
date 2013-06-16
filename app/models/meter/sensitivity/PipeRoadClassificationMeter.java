package models.meter.sensitivity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class PipeRoadClassificationMeter extends AbstractPipeMeter {

	public PipeRoadClassificationMeter() {
		super();
		this.scale = "0-4";
		this.specialCautionLimit = 4F;
	}
	

//	@Id
//	@Column(name="id")
//	@TableGenerator(name = "roadClassificationMeter_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "roadClassificationMeter_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "roadClassificationMeter_seq")
//	public Long id;
}
