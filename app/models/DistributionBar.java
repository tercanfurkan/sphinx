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
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double closeToNuuksioLake;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double notCloseToNuuksioLake;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double underRailway;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double notUnderRailway;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double inProtectedArea;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double notInProtectedArea;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double underWaterbody;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double notUnderWaterbody;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double underProtectedDitch;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double notUnderProtectedDitch;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double kera;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double paavi;
    @Column(columnDefinition = "NUMERIC(19,10)")
	public Double otheroperationaltype;
    


	public static DistributionBar getMeterDistributions() {
		System.out.println("DistributionBar getMeterDistributions > ");	
		Query query = JPA.em().createQuery("from DistributionBar dist");
		return (DistributionBar) query.getSingleResult();
	}
}