package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

@Entity
public class ComponentType {

	
    @Id
    @TableGenerator( name = "component_type_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "component_type_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "component_type_seq" )
    public Long id;
    
    @Constraints.Required
    public String type_name;
    
    public Long datasource_code;
    
    public String description;
    
    public String owner;
    
    public static ComponentType findById(Long id) {
        return JPA.em().find(ComponentType.class, id);
    }
    
    public static ComponentType findByName(String name) {
    	try {
            return (ComponentType) JPA.em()
                    .createQuery("SELECT c FROM ComponentType c WHERE c.type_name LIKE :cName")
                    .setParameter("cName", name)
                    .getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

    }

    public static Map<String,String> options() {
        List<ComponentType> componentTypeList = JPA.em().createQuery("from ComponentType order by type_name").getResultList();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(ComponentType c: componentTypeList) {
            options.put(c.id.toString(), c.type_name);
        }
        return options;
    }
    
    public void save() {
        JPA.em().persist(this);
    }
    
    public ComponentType merge() {
    	return JPA.em().merge(this);
    }
}
