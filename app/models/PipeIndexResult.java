package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import play.db.jpa.JPA;

@Entity
public class PipeIndexResult {
	
	public PipeIndexResult (Long pipeCode, String pipeIdentifier, Float consequenceIndex, Float consequenceTotalLimit, Float conditionIndex, Float conditionTotalLimit, Float pipeLength) {
		this.pipeCode = pipeCode;
		this.pipeIdentifier = pipeIdentifier;
		this.consequenceIndex = consequenceIndex;
		this.consequenceTotalLimit = consequenceTotalLimit;
		this.conditionIndex = conditionIndex;
		this.conditionTotalLimit = conditionTotalLimit;
		this.isInspected = (conditionTotalLimit == 5) ? true:false;
		this.pipeLength = pipeLength;
	}
	
	public PipeIndexResult() {
		
	}
	
	@Id
	@Column(name = "id")
	@TableGenerator(name = "indexresult_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "indexresult_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "indexresult_seq")
	public Long id;
	
	public Date date;
	
	public Long pipeCode;
	public String pipeIdentifier;
	
//	@Column(columnDefinition="Decimal(10,3)")
	public Float consequenceIndex;
	
//	@Column(columnDefinition="Decimal(3,1)")
	public Float consequenceTotalLimit;
	
//	@Column(columnDefinition="Decimal(10,3)")
	public Float conditionIndex;
	
//	@Column(columnDefinition="Decimal(3,1)")
	public Float conditionTotalLimit;
	
	public boolean isInspected;
	
//	@Column(columnDefinition="Decimal(10,3)")
	public Float pipeLength;

//	public static void batchInsert(List<PipeIndexResult> indexResultList) {
//		
//		Date newDate = new Date();
//		
//		for(PipeIndexResult result : indexResultList) {
//			System.out.println(result.pipeCode + " --inserted");
//			result.date = newDate;
//			JPA.em().persist(result);
//		}
//	}
	
	public void save() {
		
		JPA.em().merge(this);
		System.out.println(this.pipeCode + " --inserted");
	}

	
}
