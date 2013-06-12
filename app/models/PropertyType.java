package models;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TableGenerator;

import models.enums.PropertyDataType;

//import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

@Entity
public class PropertyType {

	
    @Id
    @TableGenerator( name = "property_type_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "property_type_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 10 )
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "property_type_seq" )
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @Enumerated(EnumType.STRING)
    public PropertyDataType propertyDataType;
    
    public String owner;
    
    public String description;
    
    public Boolean deleted;
    
    public Date interval;
    
    public String validationType;
    
    public Boolean indicator;
    
//    @Formats.DateTime(pattern="dd.mm.yyyy HH:mm:ss")
    public Date date;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    public ComponentType componentType;
    
    public PropertyType save() {
    	PropertyType propType = null;
    	Long checkId = null;
    	try {
    		propType = findByNameAndDataType(this.name, this.propertyDataType);
			checkId = findByNameAndDataType(this.name, this.propertyDataType).id;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
    			
    	if (checkId == null) {
    		propType = JPA.em().merge(this);
    	}
    	return propType;
    }
    
    public static PropertyType findByName(String name) {
    	PropertyType type = null;
    	try {
        	type = (PropertyType) JPA.em()
                    .createQuery("SELECT p FROM PropertyType p WHERE p.name LIKE :pName")
                    .setParameter("pName", name)
                    .getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

        return type;
    }
    
    public static PropertyType findByNameAndDataType(String name, PropertyDataType pDataType) {
    	PropertyType type = null;
    	try {
    		
			Query query = JPA
					.em()
					.createQuery(
							"SELECT p FROM PropertyType p WHERE p.name LIKE :pName and p.propertyDataType =:pDataType");
			query.setParameter("pName", name);
			query.setParameter("pDataType", pDataType);
			type = (PropertyType) query.getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

        return type;
    }
    
    
//    public static Company findById(Long id) {
//        return JPA.em().find(Company.class, id);
//    }
//
//    public static Map<String,String> options() {
//        List<Company> companies = JPA.em().createQuery("from Company order by name").getResultList();
//        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
//        for(Company c: companies) {
//            options.put(c.id.toString(), c.name);
//        }
//        return options;
//    }
}
