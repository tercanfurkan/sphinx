package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

public class PropertyTypeRelation {

	
    @Id
    public Long id;
    
    @Constraints.Required
    public String name;
    
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
