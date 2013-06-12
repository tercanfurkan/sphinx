package models;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TableGenerator;

import models.wrapper.PipeConditionIndexWrapper;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeSensitivityIndexWrapper;

import play.data.format.Formats;
import play.db.jpa.JPA;

@Entity
public class ComponentProperty {

	@Id
	@TableGenerator(name = "component_property_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "component_property_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 200)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "component_property_seq")
	public Long id;

	public String name;

	@Formats.DateTime(pattern = "dd.mm.yyyy HH:mm:ss")
	public Date date;

	@Formats.DateTime(pattern = "dd.mm.yyyy HH:mm:ss")
	public Date import_date;

	public Boolean deleted;

	public Boolean correct;

	public String description;

	public String owner;

	@Column(columnDefinition = "TEXT")
	public String valueString;

	public Double valueDecimal;

	@ManyToOne(cascade = CascadeType.MERGE)
	public Component component;

	@ManyToOne(cascade = CascadeType.MERGE)
	public PropertyType propertyType;

	@ManyToOne(cascade = CascadeType.MERGE)
	public Datasource datasource;

	public void save() {
		JPA.em().persist(this);
	}

	public static void batchInsert(List<ComponentProperty> cpropList) {
		final EntityTransaction tx = JPA.em().getTransaction();
		tx.begin();
		int count = 0;
		for (ComponentProperty cprop : cpropList) {
			JPA.em().persist(cprop);
			count++;

			if (count % 100 == 0) {
				tx.commit();
				tx.begin();
			}
		}
		tx.commit();
	}

	public static Double getArealSumOFYearlyConsumption(Long componentId,
			Date dateMin, Date dateMax) {

		ComponentProperty cp = null;
		PropertyType pt = PropertyType
				.findByName("Areal Annual Consumption Forecast");
		Query query = JPA
				.em()
				.createQuery(
						"SELECT cp from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId and date >:cpDateMin and date <:cpDateMax");
		query.setParameter("cId", componentId);
		query.setParameter("pType", pt.id);
		query.setParameter("cpDateMin", dateMin);
		query.setParameter("cpDateMax", dateMax);
		try {
			cp = (ComponentProperty) query.getSingleResult();
		} catch (NoResultException e) {
			System.out
					.println("No consumption info for component and date period");
			return 0D;
		}

		return cp.valueDecimal;
	}

	public static Double getSumOFYearlyConsumption(Date dateMin, Date dateMax) {

		PropertyType pt = PropertyType
				.findByName("Areal Annual Consumption Forecast");
		Query query = JPA
				.em()
				.createQuery(
						"SELECT sum(cp.valueDecimal) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.scope =:cScope and date >:cpDateMin and date <:cpDateMax");
		query.setParameter("cScope", Boolean.TRUE);
		query.setParameter("pType", pt.id);
		query.setParameter("cpDateMin", dateMin);
		query.setParameter("cpDateMax", dateMax);
		Double retVal = (Double) query.getSingleResult();
		return retVal;
	}

	public static Double getSumOfDecimalValues(Long componentId,
			Long propertyTypeId, Date dateMin, Date dateMax) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(cp.valueDecimal) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId and date >:cpDateMin and date <:cpDateMax");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		query.setParameter("cpDateMin", dateMin);
		query.setParameter("cpDateMax", dateMax);
		return (Double) query.getSingleResult();
	}

	public static Double getSumOfFlaws(Long componentId, Long propertyTypeId) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(cp.valueDecimal) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.ownerComponent.id =:cId");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		return (Double) query.getSingleResult();
	}

	public static Double getSumOfValueDecimal(Long componentId,
			Long propertyTypeId) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(cp.valueDecimal) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		return (Double) query.getSingleResult();
	}

	public static Double getSumOfValueDecimalBiggerThenValue(Long componentId,
			Long propertyTypeId, Double biggerThenValue) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(cp.valueDecimal) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId and cp.valueDecimal > :pbiggerThenValue");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		query.setParameter("pbiggerThenValue", biggerThenValue);
		return (Double) query.getSingleResult();
	}

	public static Long getCountOfPropertyTypeEntriesOfComponent(
			Long componentId, Long propertyTypeId, Double[] valueDecimalArray) {

		List<Double> valueDecimalList = Arrays.asList(valueDecimalArray);

		Query query = JPA
				.em()
				.createQuery(
						"SELECT COUNT(cp) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId and cp.valueDecimal in :cValueDecimalList");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		query.setParameter("cValueDecimalList", valueDecimalList);
		return (Long) query.getSingleResult();
	}

	public static Long getCountOfPropertyTypeEntriesOfComponent(
			Long componentId, Long propertyTypeId) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT COUNT(cp) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		return (Long) query.getSingleResult();
	}

	public static Double getTotalDistanceOfInspectedPipesOfPs(Long componentId,
			Long propertyTypeId) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(c.componentDetail.length_3d) from ComponentProperty cp JOIN cp.component c where cp.propertyType.id =:pType and c.ownerComponent.id =:cId");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		return (Double) query.getSingleResult();
	}

	public static Long getAmountOfProperty(Long componentId, Long propertyTypeId) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT COUNT(cp) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.ownerComponent.id =:cId");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		return (Long) query.getSingleResult();
	}

	public static Long getAmountOfProperty(Long componentId,
			Long propertyTypeId, Date minDate) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT COUNT(cp) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.ownerComponent.id =:cId and cp.date >:cpDateMin");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		query.setParameter("cpDateMin", minDate);
		return (Long) query.getSingleResult();
	}

	public static Long getAmountOfPropertySameLevel(Long componentId,
			Long propertyTypeId, Date minDate, Date maxDate) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT COUNT(cp) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId and cp.date >:cpDateMin and cp.date <:cpDateMax");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		query.setParameter("cpDateMin", minDate);
		query.setParameter("cpDateMax", maxDate);
		return (Long) query.getSingleResult();
	}

	public static Long getPropertyCountEqualAndAfterComponentInstallationDate(
			Long componentId, Long propertyTypeId) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT COUNT(cp) from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId and cast(SUBSTRING(cast(cp.date as character), 1,4) as integer) >= cp.component.componentDetail.installation_year");
		query.setParameter("cId", componentId);
		query.setParameter("pType", propertyTypeId);
		return (Long) query.getSingleResult();
	}

	public static Double getTotalDistanceOfPipesOfPs(Long componentId) {
		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(c.componentDetail.length_3d) from ComponentProperty cp JOIN cp.component c where c.ownerComponent.id =:cId");
		query.setParameter("cId", componentId);
		return (Double) query.getSingleResult();
	}

	public static List<PipeConditionIndexWrapper> getPipeConditionMeterVals() {

		List<PipeConditionIndexWrapper> wrapperList = null;

		String queryStr = "select c.child_id as pipeId"
				+ ", c.childname as pipeName"
				+ ", c.parent_id as psId"
				+ ", c.parentnumber as psNumber"
				+ ", c.parentname as psName"
				+ ", flow.flowsum as psFlowSum"
				+ ", cpCons.consumption as psAnnualConsumption"
				+ ", cpBlock.count as pipeBlockage"
				+ ", cpFlush.count as pipeFlushing"
				+ ", cpCCTV.sum as pipeCctvSum"
				+ ", cpCCTV34.sum as pipeCctv34Sum"
				+ " FROM componentownerwithflow c"
				+ " LEFT JOIN psyearlyflow flow on flow.component_id = c.parent_id and flow.year = 2012"
				+ " LEFT JOIN pstotalconsumption cpCons ON cpCons.component_id = c.parent_id and cpCons.year = 2012"
				+ " LEFT JOIN blockage cpBlock ON cpBlock.component_id = c.child_id"
				+ " LEFT JOIN flushing cpFlush ON cpFlush.component_id = c.child_id"
				+ " LEFT JOIN cctvsum cpCCTV ON cpCCTV.component_id = c.child_id"
				+ " LEFT JOIN cctv34sum cpCCTV34 ON cpCCTV34.component_id = c.child_id"
				+ " order by c.parentnumber, c.child_id";

		// String queryStr = "select c.id as pipeId" +
		// ", c.name as pipeIdentifier" +
		// ", jtc.owner_component_id as psId" +
		// ", cpCons.valuedecimal as psAnnualConsumption" +
		// ", count(cpBlock.id) as blockageCount" +
		// ", count(cpFlush.id) as flushingCount" +
		// ", sum(cpCCTV.valuedecimal) as cctvSum" +
		// ", sum(CASE WHEN cpCCTV.valuedecimal > 2 THEN cpCCTV.valuedecimal ELSE 0 END) as cctv34Sum"
		// +
		// " FROM component c" +
		// " JOIN jt_component_owner_component jtc ON jtc.component_id = c.id" +
		// " and jtc.owner_component_id in" +
		// " (select component_id from psscadahourly)" +
		// " JOIN componentproperty cpCons ON cpCons.component_id = jtc.owner_component_id"
		// +
		// " and cpCons.propertytype_id = 4" +
		// " LEFT JOIN componentproperty cpBlock ON cpBlock.component_id = c.id"
		// +
		// " and cpBlock.propertytype_id = 6660" +
		// " LEFT JOIN componentproperty cpFlush ON cpFlush.component_id = c.id"
		// +
		// " and cpFlush.propertytype_id = 6700" +
		// " LEFT JOIN componentproperty cpCCTV ON cpCCTV.component_id = c.id" +
		// " and cpCCTV.propertytype_id = 6620" +
		// " group by c.id, c.name, jtc.owner_component_id, cpCons.valuedecimal"
		// +
		// " order by jtc.owner_component_id";

		Query query = JPA.em().createNativeQuery(queryStr,
				PipeConditionIndexWrapper.class);
		wrapperList = (List<PipeConditionIndexWrapper>) query.getResultList();
		return wrapperList;
	}

	public static List<PipeSensitivityIndexWrapper> getSensitivityMeterVals() {

		List<PipeSensitivityIndexWrapper> wrapperList = null;

		String queryStr = "select c.child_id as pipeId"
				+ ", c.childname as pipeName"
				+ ", c.parent_id as psId"
				+ ", c.parentnumber as psNumber"
				+ ", c.parentname as psName"
				+ ", c.childlength as pipeLength"
				+ ", c.childdiameter as pipeDiameter"
				+ ", (CASE WHEN c.childclass in (104430, 104431, 104432) THEN 1 ELSE 0 END) as isPressurePipe"
				+ ", (CASE WHEN ba.totalfloorarea is not null THEN ba.totalfloorarea ELSE 0 END) as totalFloorArea"
				+ ", (CASE WHEN ba.allpipelength is not null THEN ba.allpipelength ELSE 0 END)as allPipeLengthFloorArea"
				+ ", (CASE WHEN gwa.classification is not null THEN gwa.classification ELSE 0 END) as pipeGroundWaterAreaClassification"
				+ ", ps.flowsum as psFlowSum"
				+ ", (CASE WHEN c.childroadclassification is not null THEN c.childroadclassification ELSE 0 END) as pipeRoadClassification"
				+ " FROM componentownerwithflow c"
				+ " LEFT JOIN component_buildingarea ba on ba.component_id = c.child_id"
				+ " LEFT JOIN component_groundwaterarea gwa on gwa.component_id = c.child_id"
				+ " LEFT JOIN psyearlyflow ps on ps.component_id = c.parent_id and ps.year = 2012"
				+ " order by c.parentnumber, c.child_id";

		// String queryStr = "select c.id as pipeId" +
		// ", c.name as pipeIdentifier" +
		// ", jtc.owner_component_id as psId" +
		// ", cpCons.valuedecimal as psAnnualConsumption" +
		// ", count(cpBlock.id) as blockageCount" +
		// ", count(cpFlush.id) as flushingCount" +
		// ", sum(cpCCTV.valuedecimal) as cctvSum" +
		// ", sum(CASE WHEN cpCCTV.valuedecimal > 2 THEN cpCCTV.valuedecimal ELSE 0 END) as cctv34Sum"
		// +
		// " FROM component c" +
		// " JOIN jt_component_owner_component jtc ON jtc.component_id = c.id" +
		// " and jtc.owner_component_id in" +
		// " (select component_id from psscadahourly)" +
		// " JOIN componentproperty cpCons ON cpCons.component_id = jtc.owner_component_id"
		// +
		// " and cpCons.propertytype_id = 4" +
		// " LEFT JOIN componentproperty cpBlock ON cpBlock.component_id = c.id"
		// +
		// " and cpBlock.propertytype_id = 6660" +
		// " LEFT JOIN componentproperty cpFlush ON cpFlush.component_id = c.id"
		// +
		// " and cpFlush.propertytype_id = 6700" +
		// " LEFT JOIN componentproperty cpCCTV ON cpCCTV.component_id = c.id" +
		// " and cpCCTV.propertytype_id = 6620" +
		// " group by c.id, c.name, jtc.owner_component_id, cpCons.valuedecimal"
		// +
		// " order by jtc.owner_component_id";

		Query query = JPA.em().createNativeQuery(queryStr,
				PipeSensitivityIndexWrapper.class);
		wrapperList = (List<PipeSensitivityIndexWrapper>) query.getResultList();
		return wrapperList;
	}
	
	public static List<PipeIndexWrapper> getAllMeterVals() {

		List<PipeIndexWrapper> wrapperList = null;

		String queryStr = "SELECT c.id as pipe_id, c.datasource_code as pipe_datasource_code, c.name as pipe_identifier"
				+ ", cd.datasource_class_name as pipe_class, cd.diameter as diameter_original_mm, cd.diametervalue as diameter_mm"
+ ", cd.note as diameter_status, cd.length_3d as pipe_length_m, cd.installation_year, cd.original_installation_year, cd.material, cd.model"
+ ",pi.pipeownercomponent as owner_ps_area"
+ ",pressure.specialcautionlimit as cqm_pressure_pipe_limit, pressure.valueofpipe as cqm_pressure_pipe_value, pressure.metervalue as cqm_pressure_pipe_meter"
+ ",cast(wwflow.specialcautionlimit as numeric(36,3)) as cqm_wastewater_flow_limit, wwflow.annualflow as cqm_wastewater_flow_annual_flow_m3"
+ ",cast(wwflow.valueofpipe as numeric(36,3)) as cqm_wastewater_flow_pipe_value, cast(wwflow.metervalue as numeric(36,3)) as cqm_wastewater_flow_pipe_meter"
+ ",ground.specialcautionlimit as cqm_groundwater_area_limit, ground.valueofpipe as cqm_groundwater_area_pipe_value, cast(ground.metervalue as numeric(36,3)) as cqm_groundwater_area_pipe_meter"
+ ",floor.specialcautionlimit as cqm_floor_area_limit, floor.floorarea as cqm_floor_area, floor.totalpipelengthinfloorarea as cqm_floor_area_total_pipe_length, cast(floor.valueofpipe as numeric(36,3)) as cqm_floor_area_pipe_value, cast(floor.metervalue as numeric(36,3)) as cqm_floor_area_pipe_meter"
+ ",road.specialcautionlimit as cqm_road_class_limit, road.valueofpipe as cqm_road_class_pipe_value, cast(road.metervalue as numeric(36,3)) as cqm_road_class_pipe_meter"
+ ",pi.totalspecialcautionlimit as cqm_limit_total"
+ ",cast(pi.indexvalue as numeric(36,3)) as pipe_consequence_index"
+ ",block.specialcautionlimit as cdm_blockage_limit, block.valueofpipe as cdm_blockage_pipe_value, block.metervalue as cdm_blockage_pipe_meter"
+ ",flush.specialcautionlimit as cdm_flushing_limit, flush.valueofpipe as cdm_flushing_pipe_value, flush.metervalue as cdm_flushing_pipe_meter"
+ ",cctv.specialcautionlimit as cdm_cctv_limit, cctv.isavailable as cdm_cctv_is_available, cctv.valueofpipe as cdm_ccvtv_pipe_value, cast(cctv.metervalue as numeric(36,2)) as cdm_cctv_pipe_meter"
+ ",cctv34.specialcautionlimit as cdm_cctv34_limit, cctv34.valueofpipe as cdm_ccvtv34_pipe_value, cast(cctv34.metervalue as numeric (36,2))  as cdm_cctv34_pipe_meter"  
+ ",extraW.specialcautionlimit as cdm_extrawater_limit, cast(extraW.totalflow as numeric(36,2)) as cdm_extrawater_total_flow_m3_a, cast(extraW.totalconsumption as numeric(36,2)) as cdm_extrawater_total_consumption_m3_a, cast(extraW.valueofpipe as numeric(36,2)) as cdm_extrawater_pipe_value, cast(extraW.metervalue as numeric(36,2)) as cdm_extrawater_pipe_meter"
+ ",pc.totalspecialcautionlimit as cdm_limit_total"
+ ",cast(pc.indexvalue as numeric(36,3)) as pipe_condition_index"
+ ",cast((pc.indexvalue + pi.indexvalue) as numeric(36,3)) as pipe_total_index"
+ " FROM component c"
+ " JOIN jt_component_owner_component jtc ON jtc.component_id = c.id"
+ " JOIN componentdetail cd ON cd.id = c.componentdetail_id"
+ " JOIN pipesensitivityindex pi ON pi.id = cd.sensitivityindex_id"
+ " JOIN abstractpipemeter pressure ON pressure.id = pi.pressuremeter_id"
+ " JOIN abstractpipemeter wwflow ON wwflow.id = pi.annualwastewaterflowmeter_id"
+ " JOIN abstractpipemeter ground ON ground.id = pi.groundwaterareameter_id"
+ " JOIN abstractpipemeter floor ON floor.id = pi.relativefloorareameter_id"
+ " JOIN abstractpipemeter road ON road.id = pi.roadclassificationmeter_id"
+ " JOIN pipeconditionindex pc ON pc.id = cd.conditionindex_id"
+ " JOIN pipeconditionmeter block ON block.id = pc.blockagemeter_id"
+ " JOIN pipeconditionmeter flush ON flush.id = pc.flushingeventmeter_id"
+ " JOIN pipeconditionmeter cctv ON cctv.id = pc.cctvtotalmeter_id"
+ " JOIN pipeconditionmeter cctv34 ON cctv34.id = pc.cctv34meter_id"
+ " JOIN pipeconditionmeter extraW ON extraW.id = pc.extrawatermeter_id"
+ " order by (pi.indexvalue + pc.indexvalue) desc, jtc.owner_component_id, c.id";

		Query query = JPA.em().createNativeQuery(queryStr,
				PipeIndexWrapper.class);
		wrapperList = (List<PipeIndexWrapper>) query.getResultList();
		return wrapperList;
	}
}
