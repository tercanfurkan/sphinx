package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.TableGenerator;

//import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

@Entity
public class BuildingArea {

	 @Id
	    @TableGenerator( name = "building_area_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "building_area_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 10 )
	    @GeneratedValue( strategy = GenerationType.TABLE, generator = "building_area_seq" )
	    public Long id;
	    
	    @Constraints.Required
	    public Long datasourceCode;
	    
	    public Long neighborhoodNo;
	    
	    public Long totalFloorArea;
	    
	    public Long dwellingFloorArea;
	    
	    public Long nonDwellingFloorArea;
	    
//	    @Formats.DateTime(pattern="dd.mm.yyyy HH:mm:ss")
	    public Date importDate;
	    
	    public static BuildingArea findByDatasourceCode(Long code) {
	    	BuildingArea area = null;
	    	try {
	        	area = (BuildingArea) JPA.em()
	                    .createQuery("SELECT p FROM BuildingArea p WHERE p.datasourceCode =:pCode")
	                    .setParameter("pCode", code)
	                    .getSingleResult();
			} catch (NoResultException e) {
				e.printStackTrace();;
			}

	        return area;
	    }
	    
	    public static BuildingArea findByNeighborhoodNo(Long no) {
	    	BuildingArea area = null;
	    	try {
	        	area = (BuildingArea) JPA.em()
	                    .createQuery("SELECT p FROM BuildingArea p WHERE p.neighborhoodNo =:pNo")
	                    .setParameter("pNo", no)
	                    .getSingleResult();
			} catch (NoResultException e) {
				e.printStackTrace();
			}

	        return area;
	    }
	    
		public void save() {
			JPA.em().persist(this);
		}
		
		public void update() {
			JPA.em().merge(this);
		}
}

