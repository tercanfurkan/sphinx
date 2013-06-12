package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import models.enums.PumpName;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

@Entity 
@SequenceGenerator(name = "pump_scada_hourly_seq", sequenceName = "pump_scada_hourly_seq")
public class PumpScadaHourly {

	
    @Id
    @TableGenerator( name = "pump_scada_hourly_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "pump_scada_hourly_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 100 )
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "pump_scada_hourly_seq" )
    public Long id;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    public Component component = new Component();
    
    public PumpName pumpName;
    
    @Constraints.Required
    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    public Date timestamp;
    
//    P1 T is last calculated yield of pump 1 [l/s]
    @Column(name = "PT", columnDefinition = "NUMERIC(9,2)")
    public Float PT;
    
//    P1 V is average current of pump 1 [A]
    @Column(name = "PV", columnDefinition = "NUMERIC(9,2)")
    public Float PV;
    
//  P1 P is is calculation of pumped flow by pump 1 [m3/h]
    @Column(name = "PP", columnDefinition = "NUMERIC(9,2)")
    public Float PP;
    
//    P1KA is pumping duration of pump 1 when it is pumping alone [s]
    @Column(name = "PKA") 
    public Integer PKA;
    
    
    
//    @Column(columnDefinition = "NUMERIC(9,2)")
//    public String average_current;
    
    public void save(Component component) {
    	this.component = component;
        JPA.em().persist(this);
    }
    
    public void save() {
    	JPA.em().persist(this);
    }
    
}
