package models.wrapper;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PipeSensitivityIndexWrapper {

	@Id
	public Long pipeId;
	public String pipeName;
	public Long psId;
	public Long psNumber;
	public String psName;
	public Float pipeLength;
	public Integer pipeDiameter;
	public boolean isPressurePipe;
	public Long totalFloorArea;
	public Float allPipeLengthFloorArea;
	public Integer pipeGroundWaterAreaClassification;
	public Float psFlowSum;
	public Integer pipeRoadClassification;
}
