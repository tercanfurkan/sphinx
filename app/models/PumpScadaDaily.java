package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

//import play.data.format.Formats;
import play.data.validation.Constraints;

@SequenceGenerator(name = "pump_scada_daily_seq", sequenceName = "pump_scada_daily_seq")
public class PumpScadaDaily {
	
	//TODO: where is the pumpName property

	
    @Id
    @TableGenerator( name = "pump_scada_daily_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "pump_scada_daily_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "pump_scada_daily_seq" )
    public Long id;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    public Component component;
    
    @Constraints.Required
//    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date date;
    
    public Integer activitiy_count;
    
    public Float average_current;
    
//    @Formats.DateTime(pattern="HH:mm:")
    public Date pumping_duration_alone;
    
}
