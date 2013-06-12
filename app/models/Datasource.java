package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

@Entity
public class Datasource {

	
    @Id
    @TableGenerator( name = "datasource_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "datasource_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "datasource_seq" )
    public Long id;
    
    @Constraints.Required
    public String name;
    
    public String descriprion;
    
    public void save() {
        JPA.em().persist(this);
    }

}
