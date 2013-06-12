package models.meter.condition;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.TableGenerator;

import models.meter.sensitivity.PipeSensitivityIndex;
import models.meter.sensitivity.PipeSensitivityIndex.SensitivityIndexPage;
import play.db.jpa.JPA;

@Entity
public class PipeConditionIndex {

	public PipeConditionIndex() {
		this.blockageMeter = new PipeBlockageMeter();
		this.flushingEventMeter = new PipeFlushingEventMeter();
		this.cctv34Meter = new PipeCctv34Meter();
		this.cctvTotalMeter = new PipeCctvTotalMeter();
		this.extraWaterMeter = new PipeExtraWaterMeter();
		this.indexValue = 0F;
		this.totalSpecialCautionLimit = 0F;
		this.pipeIdentifier = "";
		this.pipeOwnerComponent = "";
	}

	public PipeConditionIndex(PipeBlockageMeter blockageM,
			PipeFlushingEventMeter flushingEventM, PipeCctv34Meter cctv34M,
			PipeCctvTotalMeter cctvTotalM, PipeExtraWaterMeter extraWaterM) {

		this.blockageMeter = blockageM;
		this.flushingEventMeter = flushingEventM;
		this.cctv34Meter = cctv34M;
		this.cctvTotalMeter = cctvTotalM;
		this.extraWaterMeter = extraWaterM;
		this.indexValue = 0F;
		this.totalSpecialCautionLimit = 0F;
		this.pipeIdentifier = "";
		this.pipeOwnerComponent = "";
	}

	@Id
	@Column(name="id")
	@TableGenerator(name = "pipe_condition_index_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "pipe_condition_index_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pipe_condition_index_seq")
	public Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeBlockageMeter blockageMeter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeFlushingEventMeter flushingEventMeter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeCctv34Meter cctv34Meter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeCctvTotalMeter cctvTotalMeter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeExtraWaterMeter extraWaterMeter;
	
	public Float indexValue;
	public Float totalSpecialCautionLimit;
	public String pipeIdentifier;
	public String pipeOwnerComponent;
	
	public void calculateResult() {
		calculateIndex();
		calculateTotalSpecialCautionLimit();
	}

	private void calculateIndex() {
		
		Float retVal = 0F;
		retVal += this.blockageMeter.meterValue;
		retVal += this.flushingEventMeter.meterValue;
		retVal += this.cctv34Meter.meterValue;
		retVal += this.cctvTotalMeter.meterValue;
		retVal += this.extraWaterMeter.meterValue;
		
		this.indexValue = retVal; 
	}
	
	private void calculateTotalSpecialCautionLimit() {
		
		Float retVal = 0F;
		retVal += 3;
		if (this.cctvTotalMeter.isAvailable) {
			retVal += 2;
		}
		
		this.totalSpecialCautionLimit = retVal; 
	}
	
	public String toString() {
		
		String retVal = "";
		retVal += "{blockageMeter: " + blockageMeter.meterValue + "} - " +
				"{flushingEventMeter: " + flushingEventMeter.meterValue + "} - " +
						"{cctv34Meter: " + cctv34Meter.meterValue + "} - " +
								"{cctvTotalMeter: " + cctvTotalMeter.meterValue + "} - " +
										"{extraWaterMeter: " + extraWaterMeter.meterValue + "} - " +
												"{pipeIdentifier: " + pipeIdentifier + "} - " +
						"{pipeOwnerComponent: " + pipeOwnerComponent + "} - " +
								"{totalSpecialCautionLimit: " + totalSpecialCautionLimit + "} - " +
										"{indexValue: " + indexValue + "}";
		
		return retVal;
	}
	
	public static class ConditionIndexPage {

		private final int pageSize;
		private final long totalRowCount;
		private final int pageIndex;
		private List<PipeConditionIndex> list;

		public ConditionIndexPage(List<PipeConditionIndex> data, long total, int page,
				int pageSize) {
			this.list = data;
			this.totalRowCount = total;
			this.pageIndex = page;
			this.pageSize = pageSize;
		}

		public long getTotalRowCount() {
			return totalRowCount;
		}

		public int getPageIndex() {
			return pageIndex;
		}

		public boolean hasPrev() {
			return pageIndex > 1;
		}

		public boolean hasNext() {
			return (totalRowCount / pageSize) >= pageIndex;
		}

		public String getDisplayXtoYofZ() {
			int start = ((pageIndex - 1) * pageSize + 1);
			int end = start + Math.min(pageSize, list.size()) - 1;
			return start + " to " + end + " of " + totalRowCount;
		}

		public List<PipeConditionIndex> getList() {
			return list;
		}

		public void setList(List<PipeConditionIndex> list) {
			this.list = list;
		}
	}
	
	public static ConditionIndexPage getConditionIndexPage(int page, int pageSize,
			String sortBy, String order, String filter) {
		System.out.println(sortBy);
		if (page < 1)
			page = 1;

		Long total = 0L;
		int totalInt = 0;
		List<PipeConditionIndex> indexList;
		// try {
		Query query = JPA
				.em()
				.createQuery(
						"select count(c.componentDetail.conditionIndex) from Component c where lower(c.componentDetail.conditionIndex.pipeIdentifier) like ?");
		query.setParameter(1, "%" + filter.toLowerCase() + "%");
		total = (Long) query.getSingleResult();
		totalInt = new BigDecimal(total).intValueExact();

		Query query2 = JPA
				.em()
				.createQuery(
						"select c.componentDetail.conditionIndex from Component c where lower(c.componentDetail.conditionIndex.pipeIdentifier) like ? order by c.componentDetail.conditionIndex."
								+ sortBy + " " + order);
		query2.setParameter(1, "%" + filter.toLowerCase() + "%");
		query2.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
		indexList = query2.getResultList();
		
		return new ConditionIndexPage(indexList, total, page, pageSize);
	}
}
