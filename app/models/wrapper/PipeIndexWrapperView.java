package models.wrapper;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;

import play.db.jpa.JPA;

@Entity
public final class PipeIndexWrapperView {
	
	@Id
	public Long pipeId;
	public Long pipeCode;
	public String pipeName;
	public String psName;
	public Float pipeLength;
	public Float pipeDiameter;
	public Float psAnnualConsumption;
	public Long pipeFlushing;
	public Long pipeBlockage;
	public Float pipeCctvSum;
	public Float pipeCctv34Sum;
	public Integer pipeTypeValue;
	public Long totalFloorArea;
	public Float allPipeLengthFloorArea;
	public Integer pipeGroundWaterAreaClassification;
	public Float psFlowSum;
	public Integer pipeRoadClassification;
	
	public static List<PipeIndexWrapperView> getPipeIndexWrapperViewList() {
		
		List<PipeIndexWrapperView> list = null;
		Query query = JPA
				.em()
				.createQuery(
						"Select p from PipeIndexWrapperView p");
		list = (List<PipeIndexWrapperView>) query.getResultList();
		return list;
	}
}
