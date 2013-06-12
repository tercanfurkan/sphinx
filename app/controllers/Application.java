package controllers;

import java.io.File;
import java.util.Collections;
import java.util.List;

import models.Component;
import models.Component.AreaAndMeters;
import models.Component.AreaMeterList;
import models.form.PipeIndex;
import models.meter.condition.PipeConditionIndex;
import models.meter.condition.PipeConditionIndex.ConditionIndexPage;
import models.meter.sensitivity.PipeSensitivityIndex;
import models.meter.sensitivity.PipeSensitivityIndex.SensitivityIndexPage;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeIndexWrapper.PipeIndexWrapperPage;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import util.MathUtilSphinx;

import views.html.*;

public class Application extends Controller {

	/**
	 * This result directly redirect to application home.
	 */
	public static Result GO_HOME = redirect(routes.Application.list(0, "name",
			"asc", ""));

	/**
	 * Handle default path requests, redirect to Components list
	 */
	public static Result index() {
		return GO_HOME;
	}

	public static Result GO_METER_CALC = redirect(routes.Application.psareas(0,
			"name", "asc", "", 25, 25, 25, 25));

	public static Result listpsareas() {
		return GO_METER_CALC;
	}

	// @Transactional(readOnly=true)
	// public static Result calculatemeters(List<Component> comList) {
	//
	// Map<String, Double> componentMeterMap = new HashMap<String, Double>();
	//
	//
	// return ok(
	// meterlist.render(
	// Component.componentList(1L)
	// )
	// );
	// }

	public static Result importPage() {

		return ok(importPage.render());
	}

	public static Result upload() {
		play.mvc.Http.MultipartFormData body = request().body()
				.asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			try {
				String fileName = picture.getFilename();
				String contentType = picture.getContentType();
				File file = picture.getFile();
				org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory
						.create(file);
				System.out.println("Number Of Sheets"
						+ workbook.getNumberOfSheets());
				org.apache.poi.ss.usermodel.Sheet sheet = workbook
						.getSheetAt(0);
				System.out.println("Number Of Rows:" + sheet.getLastRowNum());
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(5);
				System.out.println("Cell Value:"
						+ row.getCell(5).getNumericCellValue());
				System.out.println("cikti");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ok("File uploaded");
		} else {
			flash("error", "Missing file");
			return redirect(routes.Application.importPage());
		}
	}

	/**
	 * Display PS areas to select for calculating meters.
	 * 
	 */
	@Transactional(readOnly = true)
	public static Result psareas(int page, String sortBy, String order,
			String filter, int wExtra, int wCCTV, int wOperational,
			int wSensitivity) {

		AreaMeterList aml = Component.areaAndMeterPage(page, 10, sortBy, order,
				filter);
		List<AreaAndMeters> amList = aml.getList();

		Double hvExtraWater = 0D;
		Double hvCCTVCondition = 0D;
		Double hvOperationalDisturbance = 0D;
		Double hvSocialSensitivity = 0D;
		for (AreaAndMeters am : amList) {
			if (am.component.componentDetail.extraWaterMeter > hvExtraWater)
				hvExtraWater = am.component.componentDetail.extraWaterMeter;

			if (am.component.componentDetail.CCTVConditionMeter > hvCCTVCondition)
				hvCCTVCondition = am.component.componentDetail.CCTVConditionMeter;

			if (am.component.componentDetail.operationalDisturbance > hvOperationalDisturbance)
				hvOperationalDisturbance = am.component.componentDetail.operationalDisturbance;

			if (am.component.componentDetail.socialSensitivity > hvSocialSensitivity)
				hvSocialSensitivity = am.component.componentDetail.socialSensitivity;
		}
		for (AreaAndMeters am : amList) {
			am.extraWaterMeter = (am.component.componentDetail.extraWaterMeter / hvExtraWater)
					* wExtra;
			am.extraWaterMeter = MathUtilSphinx.truncateDouble(
					am.extraWaterMeter, 2);
			am.conditionCCTVMeter = (am.component.componentDetail.CCTVConditionMeter / hvCCTVCondition)
					* wCCTV;
			am.conditionCCTVMeter = MathUtilSphinx.truncateDouble(
					am.conditionCCTVMeter, 2);
			am.operationalDisturbanceMeter = (am.component.componentDetail.operationalDisturbance / hvOperationalDisturbance)
					* wOperational;
			am.operationalDisturbanceMeter = MathUtilSphinx.truncateDouble(
					am.operationalDisturbanceMeter, 2);
			am.socialSensitivityMeter = (am.component.componentDetail.socialSensitivity / hvSocialSensitivity)
					* wSensitivity;
			am.socialSensitivityMeter = MathUtilSphinx.truncateDouble(
					am.socialSensitivityMeter, 2);
			am.pi = MathUtilSphinx
					.truncateDouble(
							(am.extraWaterMeter + am.conditionCCTVMeter
									+ am.operationalDisturbanceMeter + am.socialSensitivityMeter),
							2);
		}

		List<AreaAndMeters> toSortList = amList;
		Collections.sort(toSortList, MathUtilSphinx.comparatorExtraWater);

		aml.setList(toSortList);
		return ok(psareas.render(aml, sortBy, order, filter, wExtra, wCCTV,
				wOperational, wSensitivity));
	}

	/**
	 * Display the paginated list of Components.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 * @param sortBy
	 *            Column to be sorted
	 * @param order
	 *            Sort order (either asc or desc)
	 * @param filter
	 *            Filter applied on Component names
	 */
	@Transactional(readOnly = true)
	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(list.render(Component.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}

	/**
	 * Display the 'edit form' of a existing Component.
	 * 
	 * @param id
	 *            Id of the Component to edit
	 */
	@Transactional(readOnly = true)
	public static Result edit(Long id) {
		
		Component comp = Component.findById(id);
		Form<Component> componentForm = form(Component.class);
		componentForm = componentForm.fill(comp);
		return ok(editForm.render(id, componentForm));
	}

	/**
	 * Handle the 'edit form' submission
	 * 
	 * @param id
	 *            Id of the Component to edit
	 */
	@Transactional
	public static Result update(Long id) {
		Form<Component> componentForm = form(Component.class).bindFromRequest();
		if (componentForm.hasErrors()) {
			return badRequest(editForm.render(id, componentForm));
		}
		componentForm.get().update(id);
		flash("success", "Component " + componentForm.get().name
				+ " has been updated");
		return GO_HOME;
	}

	/**
	 * Display the 'new Component form'.
	 */
	@Transactional(readOnly = true)
	public static Result create() {
		Form<Component> ComponentForm = form(Component.class);
		return ok(createForm.render(ComponentForm));
	}

	/**
	 * Handle the 'new Component form' submission
	 */
	@Transactional
	public static Result save() {
		Form<Component> ComponentForm = form(Component.class).bindFromRequest();
		if (ComponentForm.hasErrors()) {
			return badRequest(createForm.render(ComponentForm));
		}
		ComponentForm.get().save();
		flash("success", "Component " + ComponentForm.get().name
				+ " has been created");
		return GO_HOME;
	}

	/**
	 * Handle Component deletion
	 */
	@Transactional
	public static Result delete(Long id) {
		Component.findById(id).delete();
		flash("success", "Component has been deleted");
		return GO_HOME;
	}

	@Transactional(readOnly = true)
	public static Result listPipes(int page, String sortBy, String order,
			String filter) {
		return ok(listPipes.render(
				Component.pagePipes(page, 10, sortBy, order, filter), sortBy,
				order, filter));
	}

	@Transactional(readOnly = true)
	public static Result listManholes(int page, String sortBy, String order,
			String filter) {
		return ok(listManholes.render(
				Component.pageManholes(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}

	/**
	 * Display pipe sensitivity meters and index.
	 * 
	 */
	@Transactional(readOnly = true)
	public static Result pipeSensitivityIndex(int page, String sortBy,
			String order, String filter, int lWasteWater,
			int lGroundWaterArea, int lPressurePipe, int lFloorArea,
			int lRoadClass) {
		System.out.println(lWasteWater);

		SensitivityIndexPage indexPage = PipeSensitivityIndex
				.getSensitivityIndexPage(page, 10, sortBy, order, filter);

		List<PipeSensitivityIndex> indexList = indexPage.getList();

		for (PipeSensitivityIndex index : indexList) {
			index.annualWasteWaterFlowMeter.meterValue = index.annualWasteWaterFlowMeter.valueOfPipe
					/ (float)lWasteWater;
			index.annualWasteWaterFlowMeter.meterValue = (float) MathUtilSphinx.truncateDouble(index.annualWasteWaterFlowMeter.meterValue, 3);
			
			index.groundWaterAreaMeter.meterValue = index.groundWaterAreaMeter.valueOfPipe
					/ (float)lGroundWaterArea;
			index.groundWaterAreaMeter.meterValue = (float) MathUtilSphinx.truncateDouble(index.groundWaterAreaMeter.meterValue, 3);
			
			index.pressureMeter.meterValue = index.pressureMeter.valueOfPipe
					/ (float)lPressurePipe;
			index.pressureMeter.meterValue = (float) MathUtilSphinx.truncateDouble(index.pressureMeter.meterValue, 3);
			
			index.relativeFloorAreaMeter.meterValue = index.relativeFloorAreaMeter.valueOfPipe
					/ (float)lFloorArea;
			index.relativeFloorAreaMeter.meterValue = (float) MathUtilSphinx.truncateDouble(index.relativeFloorAreaMeter.meterValue, 3);
			
			index.roadClassificationMeter.meterValue = index.roadClassificationMeter.valueOfPipe
					/ (float)lRoadClass;
			index.roadClassificationMeter.meterValue = (float) MathUtilSphinx.truncateDouble(index.roadClassificationMeter.meterValue, 3);
			
			index.indexValue = index.annualWasteWaterFlowMeter.meterValue
					+ index.groundWaterAreaMeter.meterValue
					+ index.pressureMeter.meterValue
					+ index.relativeFloorAreaMeter.meterValue
					+ index.roadClassificationMeter.meterValue;
			index.indexValue = (float) MathUtilSphinx.truncateDouble(index.indexValue, 3);
		}

//		indexPage.setList(indexList);
		return ok(pipeSensitivityIndex.render(indexPage, sortBy, order, filter, lWasteWater, lGroundWaterArea,
				lPressurePipe, lFloorArea, lRoadClass));
	}
	
	/**
	 * Display pipe sensitivity and condition meters and indexes.
	 * 
	 */
	@Transactional(readOnly = true)
	public static Result pipeIndex(int page, String sortBy, String order) {
		
		Form<PipeIndex> pipeIndexForm = form(PipeIndex.class);		
		PipeIndex pipeIndexFields = pipeIndexForm.bindFromRequest().get();	
		pipeIndexFields.sortBy = sortBy;
		pipeIndexFields.order = order;
		PipeIndexWrapperPage indexWrapperPage = PipeIndexWrapper.getPipeIndexWrapperPage(page, 15, pipeIndexFields);
		List<PipeIndexWrapper> indexWrapperList = indexWrapperPage.getList();
//		Double totalLengthOfPipes = 0D;
		
//		int count = 0;
		for(PipeIndexWrapper wrapper : indexWrapperList) {
//			count ++;
//			if (count <50) System.out.println(wrapper.pipe_condition_index);
			
			// re calculate index & meter vals
			
//			totalLengthOfPipes += wrapper.pipe_length_m;

			wrapper.cqm_wastewater_flow_pipe_meter = wrapper.cqm_wastewater_flow_pipe_value
					/ pipeIndexFields.wasteWaterLimit;
			wrapper.cqm_wastewater_flow_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cqm_wastewater_flow_pipe_meter, 3);
			
			wrapper.cqm_groundwater_area_pipe_meter = wrapper.cqm_groundwater_area_pipe_value
					/ pipeIndexFields.groundWaterAreaLimit;
			wrapper.cqm_groundwater_area_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cqm_groundwater_area_pipe_meter, 3);
			
			wrapper.cqm_pressure_pipe_meter = wrapper.cqm_pressure_pipe_value
					/ pipeIndexFields.pressurePipeLimit;
			wrapper.cqm_pressure_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cqm_pressure_pipe_meter, 3);
			
			wrapper.cqm_floor_area_pipe_meter = wrapper.cqm_floor_area_pipe_value
					/ pipeIndexFields.floorAreaLimit;
			wrapper.cqm_floor_area_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cqm_floor_area_pipe_meter, 3);
			
			wrapper.cqm_road_class_pipe_meter = wrapper.cqm_road_class_pipe_value
					/ pipeIndexFields.roadClassLimit;
			wrapper.cqm_road_class_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cqm_road_class_pipe_meter, 3);
			
			wrapper.pipe_consequence_index = wrapper.cqm_wastewater_flow_pipe_meter
					+ wrapper.cqm_groundwater_area_pipe_meter
					+ wrapper.cqm_pressure_pipe_meter
					+ wrapper.cqm_floor_area_pipe_meter
					+ wrapper.cqm_road_class_pipe_meter;
			wrapper.pipe_consequence_index = (float) MathUtilSphinx.truncateDouble(wrapper.pipe_consequence_index, 3);
			
			wrapper.cdm_blockage_pipe_meter = wrapper.cdm_blockage_pipe_value
					/ pipeIndexFields.blockageLimit;
			wrapper.cdm_blockage_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cdm_blockage_pipe_meter, 3);
			
			wrapper.cdm_flushing_pipe_meter = wrapper.cdm_flushing_pipe_value
					/ pipeIndexFields.flushingLimit;
			wrapper.cdm_flushing_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cdm_flushing_pipe_meter, 3);
			
			wrapper.cdm_extrawater_pipe_meter = (wrapper.cdm_extrawater_pipe_value - 1)
					/ pipeIndexFields.extraWaterLimit;
			
			if (wrapper.cdm_extrawater_pipe_meter < 0 || MathUtilSphinx.truncateDouble(wrapper.cdm_extrawater_pipe_meter.doubleValue(), 2) == 0D)
				wrapper.cdm_extrawater_pipe_meter = 0F;
			wrapper.cdm_extrawater_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cdm_extrawater_pipe_meter, 3);
			
			wrapper.cdm_cctv_pipe_meter = wrapper.cdm_cctv_pipe_value
					/ pipeIndexFields.cctvLimit;
			wrapper.cdm_cctv_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cdm_cctv_pipe_meter, 3);
			
			wrapper.cdm_cctv34_pipe_meter = wrapper.cdm_cctv34_pipe_value
					/ pipeIndexFields.cctv34Limit;
			wrapper.cdm_cctv34_pipe_meter = (float) MathUtilSphinx.truncateDouble(wrapper.cdm_cctv34_pipe_meter, 3);
			
			wrapper.pipe_condition_index = wrapper.cdm_blockage_pipe_meter
					+ wrapper.cdm_flushing_pipe_meter
					+ wrapper.cdm_extrawater_pipe_meter;
			
			if (wrapper.cdm_cctv_is_available) {
				wrapper.pipe_condition_index += wrapper.cdm_cctv_pipe_meter + wrapper.cdm_cctv34_pipe_meter;
			}
			wrapper.pipe_condition_index = (float) MathUtilSphinx.truncateDouble(wrapper.pipe_condition_index, 3);
			
			wrapper.pipe_total_index = wrapper.pipe_consequence_index + wrapper.pipe_condition_index;
			wrapper.pipe_total_index = (float) MathUtilSphinx.truncateDouble(wrapper.pipe_total_index, 3);

		}

		pipeIndexForm = pipeIndexForm.fill(pipeIndexFields);
		return ok(pipeIndex.render(indexWrapperPage, pipeIndexForm, sortBy, order));
	}
}