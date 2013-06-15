package models.wrapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Query;

import javax.persistence.Id;

import models.form.PipeIndex;

import play.db.jpa.JPA;

@Entity
public class PipeIndexWrapper {
	
	
	@Id
	public Long pipe_id;
	public Long pipe_datasource_code;
	public String pipe_identifier;
	public String pipe_class;
	public String diameter_original_mm;
	public Integer diameter_mm;
	public String diameter_status;
	public Float pipe_length_m;
	public Integer installation_year;
	public Integer original_installation_year;
	public String material;
	public String model;
	public String owner_ps_area;
	public Float cqm_pipe_type_limit;
	public Integer cqm_pipe_type_value;
	public Float cqm_pipe_type_meter;
	public Float cqm_wastewater_flow_limit;
	public Float cqm_wastewater_flow_annual_flow_m3;
	public Float cqm_wastewater_flow_pipe_value;
	public Float cqm_wastewater_flow_pipe_meter;
	public Float cqm_groundwater_area_limit;
	public Float cqm_groundwater_area_pipe_value;
	public Float cqm_groundwater_area_pipe_meter;
	public Float cqm_floor_area_limit;
	public Long cqm_floor_area;
	public Float cqm_floor_area_total_pipe_length;
	public Float cqm_floor_area_pipe_value;
	public Float cqm_floor_area_pipe_meter;
	public Float cqm_road_class_limit;
	public Float cqm_road_class_pipe_value;
	public Float cqm_road_class_pipe_meter;
	public Float cqm_limit_total;
	public Float pipe_consequence_index;
	public Float cdm_blockage_limit;
	public Float cdm_blockage_pipe_value;
	public Float cdm_blockage_pipe_meter;
	public Float cdm_flushing_limit;
	public Float cdm_flushing_pipe_value;
	public Float cdm_flushing_pipe_meter;
	public Float cdm_cctv_limit;
	public boolean inspected;
	public Float cdm_cctv_pipe_value;
	public Float cdm_cctv_pipe_meter;
	public Float cdm_cctv34_limit;
	public Float cdm_cctv34_pipe_value;
	public Float cdm_cctv34_pipe_meter;
	public Float cdm_extrawater_limit;
	public Float cdm_extrawater_total_flow_m3_a;
	public Float cdm_extrawater_total_consumption_m3_a;
	public Float cdm_extrawater_pipe_value;
	public Float cdm_extrawater_pipe_meter;
	public Float cdm_limit_total;
	public Float pipe_condition_index;
	public Float pipe_total_index;
	
	
	public static class PipeIndexWrapperPage {

		private final int pageSize;
		private final long totalRowCount;
		private final int pageIndex;
		private final double totalLengthOfPipes;
		private List<PipeIndexWrapper> list;

		public PipeIndexWrapperPage(List<PipeIndexWrapper> data, long total, int page,
				int pageSize, double totalLengthOfPipes) {
			this.list = data;
			this.totalRowCount = total;
			this.pageIndex = page;
			this.pageSize = pageSize;
			this.totalLengthOfPipes = totalLengthOfPipes;
		}
		
		public double getTotalLengthOfPipes() {
			return totalLengthOfPipes;
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

		public List<PipeIndexWrapper> getList() {
			return list;
		}

		public void setList(List<PipeIndexWrapper> list) {
			this.list = list;
		}
	}
	
	
	public static PipeIndexWrapperPage getPipeIndexWrapperPage(int page, int pageSize, PipeIndex pipeIndexFields) {
		if (page < 1)
			page = 1;

		Long total = 0L;
		BigInteger totalBigInteger;
		Double totalLengthOfPipes = 0D;
		BigDecimal totalLengthOfPipesBD;
		List<PipeIndexWrapper> indexList;
		String sortBy = pipeIndexFields.sortBy;
		String order = pipeIndexFields.order;
		
		//count total length of pipes
		
		String queryStrSumLength = "SELECT sum(cd.length_3d)"
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
				+ " WHERE c.deleted = false";
		
		if (pipeIndexFields.isInspected) {
			queryStrSumLength += " and pi.totalspecialcautionlimit = 5";
		}
		if (!pipeIndexFields.filterNameList[0].equals("") && !pipeIndexFields.filterOperatorList[0].equals("") && !pipeIndexFields.filterValueList[0].equals("")) {
			queryStrSumLength += " and " + pipeIndexFields.filterNameList[0] + pipeIndexFields.filterOperatorList[0] + pipeIndexFields.filterValueList[0];
		}
		if (!pipeIndexFields.filterNameList[1].equals("") && !pipeIndexFields.filterOperatorList[1].equals("") && !pipeIndexFields.filterValueList[1].equals("")) {
			queryStrSumLength += " and " + pipeIndexFields.filterNameList[1] + pipeIndexFields.filterOperatorList[1] + pipeIndexFields.filterValueList[1];
		}
		if (!pipeIndexFields.filterNameList[2].equals("") && !pipeIndexFields.filterOperatorList[2].equals("") && !pipeIndexFields.filterValueList[2].equals("")) {
			queryStrSumLength += " and " + pipeIndexFields.filterNameList[2] + pipeIndexFields.filterOperatorList[2] + pipeIndexFields.filterValueList[2];
		}
		
		//count number of pipes
		
		String queryStrCount = "SELECT count(c.id)"
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
				+ " WHERE c.deleted = false";
		
		if (pipeIndexFields.isInspected) {
			queryStrCount += " and pi.totalspecialcautionlimit = 5";
		}
		if (!pipeIndexFields.filterNameList[0].equals("") && !pipeIndexFields.filterOperatorList[0].equals("") && !pipeIndexFields.filterValueList[0].equals("")) {
			queryStrCount += " and " + pipeIndexFields.filterNameList[0] + pipeIndexFields.filterOperatorList[0] + pipeIndexFields.filterValueList[0];
		}
		if (!pipeIndexFields.filterNameList[1].equals("") && !pipeIndexFields.filterOperatorList[1].equals("") && !pipeIndexFields.filterValueList[1].equals("")) {
			queryStrCount += " and " + pipeIndexFields.filterNameList[1] + pipeIndexFields.filterOperatorList[1] + pipeIndexFields.filterValueList[1];
		}
		if (!pipeIndexFields.filterNameList[2].equals("") && !pipeIndexFields.filterOperatorList[2].equals("") && !pipeIndexFields.filterValueList[2].equals("")) {
			queryStrCount += " and " + pipeIndexFields.filterNameList[2] + pipeIndexFields.filterOperatorList[2] + pipeIndexFields.filterValueList[2];
		}
		
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
				+ ",cctv.specialcautionlimit as cdm_cctv_limit, cctv.isavailable as cdm_cctv_is_available, cctv.valueofpipe as cdm_cctv_pipe_value, cast(cctv.metervalue as numeric(36,2)) as cdm_cctv_pipe_meter"
				+ ",cctv34.specialcautionlimit as cdm_cctv34_limit, cctv34.valueofpipe as cdm_cctv34_pipe_value, cast(cctv34.metervalue as numeric (36,2))  as cdm_cctv34_pipe_meter"
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
				+ " WHERE c.deleted = false";
		
			if (pipeIndexFields.isInspected) {
				queryStr += " and pi.totalspecialcautionlimit = 5";
			}
			if (!pipeIndexFields.filterNameList[0].equals("") && !pipeIndexFields.filterOperatorList[0].equals("") && !pipeIndexFields.filterValueList[0].equals("")) {
				queryStr += " and " + pipeIndexFields.filterNameList[0] + pipeIndexFields.filterOperatorList[0] + pipeIndexFields.filterValueList[0];
			}
			if (!pipeIndexFields.filterNameList[1].equals("") && !pipeIndexFields.filterOperatorList[1].equals("") && !pipeIndexFields.filterValueList[1].equals("")) {
				queryStr += " and " + pipeIndexFields.filterNameList[1] + pipeIndexFields.filterOperatorList[1] + pipeIndexFields.filterValueList[1];
			}
			if (!pipeIndexFields.filterNameList[2].equals("") && !pipeIndexFields.filterOperatorList[2].equals("") && !pipeIndexFields.filterValueList[2].equals("")) {
				queryStr += " and " + pipeIndexFields.filterNameList[2] + pipeIndexFields.filterOperatorList[2] + pipeIndexFields.filterValueList[2];
			}	

		
	queryStr += " order by " + sortBy + " " + order + ", jtc.owner_component_id, c.id ";
	System.out.println(queryStr);
	
		Query query0 = JPA.em().createNativeQuery(queryStrSumLength);
		totalLengthOfPipesBD = (BigDecimal) query0.getSingleResult();
		totalLengthOfPipes = totalLengthOfPipesBD != null ? totalLengthOfPipesBD.doubleValue() : 0D;
		
		Query query = JPA.em().createNativeQuery(queryStrCount);
		totalBigInteger = (BigInteger) query.getSingleResult();
		total = totalBigInteger != null ? totalBigInteger.longValue() : 0L;

		Query query2 = JPA.em().createNativeQuery(queryStr,
				PipeIndexWrapper.class);
		query2.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
		indexList = query2.getResultList();
		
		return new PipeIndexWrapperPage(indexList, total, page, pageSize, totalLengthOfPipes);
	}
	
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
            
        options.put("cd.diametervalue", "Diameter");
        options.put("cd.length_3d", "Pipe length");
        options.put("cd.installation_year", "Inst. year");
        options.put("cd.material", "Material");
        options.put("pi.pipeownercomponent", "Owner PS");
        options.put("pressure.metervalue", "Pressure pipe");
        options.put("wwflow.metervalue", "Wastewater flow");
        options.put("ground.metervalue", "Groundwater area");
        options.put("floor.metervalue", "Building area");
        options.put("road.metervalue", "Road class");
        options.put("block.metervalue", "Blockages");
        options.put("flush.metervalue", "Flushings");
        options.put("cctv.metervalue", "CCTV sum");
        options.put("cctv34.metervalue", "CCTV 3&4 sum");
        options.put("extraW.metervalue", "Extra water");
        options.put("pi.indexvalue", "Consequence index");
        options.put("pi.totalspecialcautionlimit", "Consequence total limit");
        options.put("pc.indexvalue", "Condition index");
        options.put("pc.totalspecialcautionlimit", "Condition total limit");
        
        return options;
    }
	
}
