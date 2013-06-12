package models;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import models.meter.condition.PipeConditionIndex;
import models.meter.sensitivity.PipeSensitivityIndex;

@Entity
public class ComponentDetail {
	
	public ComponentDetail() {
		
	}

	@Id
	@Column(name="id")
	@TableGenerator(name = "component_detail_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "component_detail_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "component_detail_seq")
	public Long id;

	 @Column(columnDefinition = "NUMERIC(19,9)")
    public Double x1;
	 @Column(columnDefinition = "NUMERIC(19,9)")
    public Double y1;
	 @Column(columnDefinition = "NUMERIC(19,9)")
    public Double z1;
	 @Column(columnDefinition = "NUMERIC(19,9)")
    public Double x2;
	 @Column(columnDefinition = "NUMERIC(19,9)")
    public Double y2;
	 @Column(columnDefinition = "NUMERIC(19,9)")
    public Double z2;
    
    public Integer datasource_class_code;
    public String datasource_class_name;    
    public Integer installation_year;    
    public Integer original_installation_year;
    public String model;
    public String material;
    public String diameter;
    public Integer diameterValue;
    @Column(columnDefinition = "NUMERIC(19,10)")
    public Double length_3d;
    public String address;
    public String note;
    
    @Column(columnDefinition = "NUMERIC(19,9)")
    public Double extraWaterMeter;
    @Column(columnDefinition = "NUMERIC(19,9)")
    public Double CCTVConditionMeter;
    @Column(columnDefinition = "NUMERIC(19,9)")
    public Double operationalDisturbance;
    @Column(columnDefinition = "NUMERIC(19,9)")
    public Double socialSensitivity;
    
    @OneToOne(cascade = CascadeType.ALL)
    public PipeSensitivityIndex sensitivityIndex;
    
    @OneToOne(cascade = CascadeType.ALL)
    public PipeConditionIndex conditionIndex;
}
