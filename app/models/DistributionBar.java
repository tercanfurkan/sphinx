package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;

import play.db.jpa.JPA;

@Entity
public class DistributionBar {
	@Id
	public Long id;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double undoubled;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double notUndoubled;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double closeToWaterwork;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double notCloseToWaterwork;
    


	public static DistributionBar getMeterDistributions() {
		Query query = JPA.em().createQuery("from DistributionBar dist");
		return (DistributionBar) query.getSingleResult();
	}
}