package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

@Entity 
@SequenceGenerator(name = "ps_scada_hourly_seq", sequenceName = "ps_scada_hourly_seq")
public class PsScadaHourly {

	
    @Id
    @TableGenerator( name = "ps_scada_hourly_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "ps_scada_hourly_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 100 )
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "ps_scada_hourly_seq" )
    public Long id;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    public Component component = new Component();
    
    @Constraints.Required
    @Formats.DateTime(pattern="dd.mm.yyyy HH:mm:ss")
    public Date timestamp;
    
//    calculation of pumped flow [m3/h]
    @Column(name = "FI_01")
    public Float FI_01;
    
//    @Column(columnDefinition = "NUMERIC(9,2)")
    @Column(name = "FI_02")
    public Float FI_02;
    
//    @Column(columnDefinition = "NUMERIC(9,2)")
    @Column(name = "EQ_01")
    public Float EQ_01;
    
//    @Column(columnDefinition = "NUMERIC(9,2)")
    @Column(name = "YHTK")
    public Float YHTK;
    
    @Column(name = "YHTKA2") 
    public Integer YHTKA2;
    
    @Column(name = "YHTKA3") 
    public Integer YHTKA3;
    
    @Column(name = "YHTKA4") 
    public Integer YHTKA4;
    
    @Column(name = "PINT", columnDefinition = "NUMERIC(9,2)")
    public Float PINT;
    
    
    
    
////    @Column(columnDefinition = "NUMERIC(9,2)")
//    public String F1_01;
//    
////    @Column(columnDefinition = "NUMERIC(9,2)")
//    public String F1_02;
//    
////    @Column(columnDefinition = "NUMERIC(9,2)")
//    public String EQ_01;
//    
////    @Column(columnDefinition = "NUMERIC(9,2)")
//    public String YHTK;
//    
////    @Column(columnDefinition = "NUMERIC(9,2)")
//    public String PINT;
    
    public void save(Component component) {
    	this.component = component;
        JPA.em().persist(this);
    }
    
    public void save() {
    	JPA.em().persist(this);
    }
    
    public boolean isEmpty() {
    	Query query = JPA.em().createQuery("SELECT p FROM PsScadaHourly p WHERE p.component.name = :cName and p.timestamp = :pTimestamp");
    	query.setParameter("cName", this.component.name);
    	query.setParameter("pTimestamp", this.timestamp);
    	
    	if (query.getResultList().isEmpty())
    		return true;
    	return false;
    }
    
    public static Double getArealSumOfFI_01 (Long componentId, Date dateMin, Date dateMax) {
    	
    	Double retVal = 0D;
    	Query query = JPA.em().createQuery("SELECT SUM(p.FI_01) FROM PsScadaHourly p WHERE p.component.id =:cId AND p.timestamp >:pDateMin AND p.timestamp <:pDateMax");
    	query.setParameter("cId", componentId);
    	query.setParameter("pDateMin", dateMin);
    	query.setParameter("pDateMax", dateMax);
    	retVal = (Double) query.getSingleResult();
    	
    	return retVal != null ? retVal : 0D;
    }
    
    public static Double getSumOfFI_01 (Date dateMin, Date dateMax) {
    	Query query = JPA.em().createQuery("SELECT SUM(p.FI_01) FROM PsScadaHourly p WHERE p.component.scope =:cScope AND p.timestamp >:pDateMin AND p.timestamp <:pDateMax");
    	query.setParameter("cScope", Boolean.TRUE);
    	query.setParameter("pDateMin", dateMin);
    	query.setParameter("pDateMax", dateMax);
    	return (Double) query.getSingleResult();
    }
    
    public static List<Object[]> getAllPsSumOfFI_01(Date dateMin, Date dateMax) {
    	Query query = JPA.em().createNativeQuery("SELECT p.component_id, SUM(p.FI_01) FROM PsScadaHourly p WHERE p.timestamp >:pDateMin AND p.timestamp <:pDateMax group by p.component_id order by p.component_id");
    	query.setParameter("pDateMin", dateMin);
    	query.setParameter("pDateMax", dateMax);
    	return (List<Object[]>) query.getResultList();
    }
}
