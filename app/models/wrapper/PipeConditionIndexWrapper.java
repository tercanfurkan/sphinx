package models.wrapper;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PipeConditionIndexWrapper {
	

	
	@Id
	public Long pipeId;
	public String pipeName;
	public Long psId;
	public Long psNumber;
	public String psName;
	public Float psFlowSum;
	public Double psAnnualConsumption;
	public Integer pipeBlockage;
	public Integer pipeFlushing;
	public Integer pipeCctvSum;
	public Integer pipeCctv34Sum;

	public String toString() {
		
		String retVal = "";
		retVal += "{pipeId: " + pipeId + "} - " +
				"{pipeIdentifier: " + pipeName + "} - " +
						"{psId: " + psId + "} - " +
										"{psAnnualConsumption: " + psAnnualConsumption + "} - " +
												"{blockageCount: " + pipeBlockage + "} - " +
						"{flushingCount: " + pipeFlushing + "} - " +
								"{cctvSum: " + pipeCctvSum + "} - " +
										"{cctv34Sum: " + pipeCctv34Sum + "}";
		
		return retVal;
	}
}
