package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TableGenerator;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

@Entity
public class GroundWaterArea {

	@Id
	@TableGenerator(name = "groundwater_area_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "groundwater_area_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "groundwater_area_seq")
	public Long id;

	@Constraints.Required
	public Long datasourceCode;

	public String classificationText;

	public Integer classificationValue;

	public String area;

	public String city;

	// TilaMaara
	public String quantity;

	// TilaKemia
	public String chemicalCondition;

	public String riskAssesment;

	@Formats.DateTime(pattern = "dd.mm.yyyy HH:mm:ss")
	public Date importDate;

	public static GroundWaterArea findByDatasourceCode(Long code) {
		GroundWaterArea area = null;
		try {
			Query query = JPA.em().createQuery("SELECT g FROM GroundWaterArea g WHERE g.datasourceCode = :gCode");
			query.setParameter("gCode", code).getSingleResult();
			area = (GroundWaterArea) query.getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
		}

		return area;
	}

	public void save() {
		JPA.em().persist(this);
	}
}
