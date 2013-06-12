package models.meter.sensitivity;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import play.db.jpa.JPA;

import models.Component;
import models.Component.AreaAndMeters;
import models.Component.AreaMeterList;

@Entity
public class PipeSensitivityIndex {

	public PipeSensitivityIndex() {
		this.annualWasteWaterFlowMeter = new PipeAnnualWasteWaterFlowMeter();
		this.groundWaterAreaMeter = new PipeGroundWaterAreaMeter();
		this.pressureMeter = new PipePressureMeter();
		this.relativeFloorAreaMeter = new PipeRelativeFloorAreaMeter();
		this.roadClassificationMeter = new PipeRoadClassificationMeter();
		this.indexValue = 0F;
		this.totalSpecialCautionLimit = 0F;
		this.pipeIdentifier = "";
		this.pipeOwnerComponent = "";
	}

	public PipeSensitivityIndex(PipeAnnualWasteWaterFlowMeter annualWasteWaterFlowM,
			PipeGroundWaterAreaMeter groundWaterAreaM, PipePressureMeter pressureM,
			PipeRelativeFloorAreaMeter relativeFloorAreaM, PipeRoadClassificationMeter roadClassificationM) {

		this.annualWasteWaterFlowMeter = annualWasteWaterFlowM;
		this.groundWaterAreaMeter = groundWaterAreaM;
		this.pressureMeter = pressureM;
		this.relativeFloorAreaMeter = relativeFloorAreaM;
		this.roadClassificationMeter = roadClassificationM;
		this.indexValue = 0F;
		this.totalSpecialCautionLimit = 0F;
		this.pipeIdentifier = "";
		this.pipeOwnerComponent = "";
	}

	@Id
	@Column(name="id")
	@TableGenerator(name = "pipe_sensitivity_index_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "pipe_sensitivity_index_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pipe_sensitivity_index_seq")
	public Long id;
	
	public Float indexValue;
	public Float totalSpecialCautionLimit;
	public String pipeIdentifier;
	public String pipeOwnerComponent;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeAnnualWasteWaterFlowMeter annualWasteWaterFlowMeter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeGroundWaterAreaMeter groundWaterAreaMeter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipePressureMeter pressureMeter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeRelativeFloorAreaMeter relativeFloorAreaMeter;
	
	@OneToOne(cascade = CascadeType.ALL)
	public PipeRoadClassificationMeter roadClassificationMeter;
	
	public void calculateResult() {
		
		calculateIndex();
		calculateTotalSpecialCautionLimit();
	}

	private void calculateIndex() {
		
		Float retVal = 0F;
		retVal += this.annualWasteWaterFlowMeter.meterValue;
		retVal += this.groundWaterAreaMeter.meterValue;
		retVal += this.pressureMeter.meterValue;
		retVal += this.relativeFloorAreaMeter.meterValue;
		retVal += this.roadClassificationMeter.meterValue;
		
		this.indexValue = retVal;
	}
	
	private void calculateTotalSpecialCautionLimit() {
		Float retVal = 5F;
		
		this.totalSpecialCautionLimit = retVal;
	}
	
	public String toString() {
		
		String retVal = "{pipeIdentifier: " + this.pipeIdentifier + "} - ";
		retVal += "{pressureMeter: " + this.pressureMeter.meterValue + "} - " +
				"{groundWaterAreaMeter: " + this.groundWaterAreaMeter.meterValue + "} - " +
						"{annualWasteWaterFlowMeter: " + this.annualWasteWaterFlowMeter.meterValue + "} - " +
								"{relativeFloorAreaMeter: " + this.relativeFloorAreaMeter.meterValue + "} - " +
										"{roadClassificationMeter: " + this.roadClassificationMeter.meterValue + "} - " +
										"{pipeOwnerComponent: " + this.pipeOwnerComponent + "}";
		
		return retVal;
	}
	
	public static class SensitivityIndexPage {

		private final int pageSize;
		private final long totalRowCount;
		private final int pageIndex;
		private List<PipeSensitivityIndex> list;

		public SensitivityIndexPage(List<PipeSensitivityIndex> data, long total, int page,
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

		public List<PipeSensitivityIndex> getList() {
			return list;
		}

		public void setList(List<PipeSensitivityIndex> list) {
			this.list = list;
		}
	}
	
	public static SensitivityIndexPage getSensitivityIndexPage(int page, int pageSize,
			String sortBy, String order, String filter) {
		if (page < 1)
			page = 1;

		Long total = 0L;
		int totalInt = 0;
		List<PipeSensitivityIndex> indexList;
		// try {
		Query query = JPA
				.em()
				.createQuery(
						"select count(c.componentDetail.sensitivityIndex) from Component c where lower(c.componentDetail.sensitivityIndex.pipeIdentifier) like ?");
		query.setParameter(1, "%" + filter.toLowerCase() + "%");
		total = (Long) query.getSingleResult();
		totalInt = new BigDecimal(total).intValueExact();

		Query query2 = JPA
				.em()
				.createQuery(
						"select c.componentDetail.sensitivityIndex from Component c where lower(c.componentDetail.sensitivityIndex.pipeIdentifier) like ? order by c.componentDetail.sensitivityIndex."
								+ sortBy + " " + order);
		query2.setParameter(1, "%" + filter.toLowerCase() + "%");
		query2.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
		indexList = query2.getResultList();
		
		return new SensitivityIndexPage(indexList, total, page, pageSize);
	}
}
