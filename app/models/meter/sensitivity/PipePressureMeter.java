package models.meter.sensitivity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class PipePressureMeter extends AbstractPipeMeter{

	public PipePressureMeter() {
		super();
		this.scale = "0/1";
		this.specialCautionLimit = 2F;
	}
	
//	@Id
//	@Column(name="id")
//	@TableGenerator(name = "pressureMeter_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "pressureMeter_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pressureMeter_seq")
//	public Long id;

}
