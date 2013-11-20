package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


import models.Component;
import models.PipeIndexResult;
import models.Component.AreaAndMeters;
import models.Component.AreaMeterList;
import models.form.PipeIndex;
import models.meter.sensitivity.PipeSensitivityIndex;
import models.meter.sensitivity.PipeSensitivityIndex.SensitivityIndexPage;
import models.wrapper.PipeIndexSummary;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeIndexWrapperView;
import models.wrapper.PipeIndexWrapper.PipeIndexWrapperPage;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import service.PipeIndexServ;
import service.PipeSensitivityIndexServ;
import service.export.WritePipeIndexAsXLS;
import util.MathUtilSphinx;

import views.html.*;

public class Application extends Controller {
	
	static List<PipeIndexWrapper> indexWrapperList = null;;
	static List<PipeIndexWrapperView> pipeIndexWrapperViewList = null;

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
//				String fileName = picture.getFilename();
//				String contentType = picture.getContentType();
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
		Form<Component> componentForm = play.data.Form.form(Component.class);
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
		Form<Component> componentForm = play.data.Form.form(Component.class).bindFromRequest();
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
		Form<Component> ComponentForm = play.data.Form.form(Component.class);
		return ok(createForm.render(ComponentForm));
	}

	/**
	 * Handle the 'new Component form' submission
	 */
	@Transactional
	public static Result save() {
		Form<Component> ComponentForm = play.data.Form.form(Component.class).bindFromRequest();
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
		
		Form<PipeIndex> pipeIndexForm = play.data.Form.form(PipeIndex.class);
		PipeIndex pipeIndexDefault = new PipeIndex();
		PipeIndex pipeIndexFields = pipeIndexForm.bindFromRequest().get();	
		pipeIndexFields.sortBy = sortBy;
		pipeIndexFields.order = order;
		
		if (pipeIndexWrapperViewList == null) {
			System.out.println("--- pipeIndexWrapperViewList is now NULL! both pipeIndexWrapperViewList and indexWrapperList will be FILLED");
			pipeIndexWrapperViewList = PipeIndexWrapperView.getPipeIndexWrapperViewList();
			indexWrapperList = PipeIndexServ.calculatePipeIndex(pipeIndexWrapperViewList, pipeIndexFields);
		}
//		else if (!pipeIndexFields.equals(pipeIndexDefault)) {
//			System.out.println("--- pipeIndexFields is now not equal to DEFAULT! indexWrapperList will be FILLED");
//			indexWrapperList = PipeIndexServ.calculatePipeIndex(pipeIndexWrapperViewList, pipeIndexFields);
//		}
//		if (indexWrapperList == null  || indexWrapperList.size() == 0) {
//			System.out.println("--- indexWrapperList was NULL and will be FILLED!");
//			indexWrapperList = PipeIndexServ.calculatePipeIndex(pipeIndexWrapperViewList, pipeIndexFields);
//		}
		indexWrapperList = PipeIndexServ.calculatePipeIndex(pipeIndexWrapperViewList, pipeIndexFields);

		
		// fill the 'to the point' pipeIndexResult of each pipe
		// then fill the 'to the point' pipeIndexSummary and display on page
		PipeIndexSummary pipeIndexSummary = null; // pass this object to render
		Float conditionIndexLimit = 7.00F; // this is hardcoded. It is 5 and 3 for non inspected pipes
		Float consequenceIndexLimit = 6.00F;
		boolean hasExceededConditionIndex = false;
		boolean hasExceededConsequenceIndex = false;
		boolean hasExceededConditionAndConsequenceIndex = false;
		Float conditionPipeLengthInspected = 0.000F; // length of inspected pipes exceed condition index limit
		Float conditionPipeLengthNotInspected = 0.000F; // length of not inspected pipes exceed condition index limit
		Float consequencePipeLengthInspected = 0.000F; // length of inspected pipes exceed consequence index limit
		Float consequencePipeLengthNotInspected = 0.000F; // length of not inspected pipes exceed consequence index limit
		Float conditionAndConsequencePipeLength = 0.000F; // length of pipes that exceed condition and consequence index limit
		
//		System.out.println(indexWrapperList.size() + " " + conditionPipeLengthInspected);
		for(PipeIndexWrapper wrapper : indexWrapperList) {	
			
			hasExceededConditionIndex = (wrapper.pipe_condition_index >= wrapper.cdm_limit_total);
			hasExceededConsequenceIndex = (wrapper.pipe_consequence_index >= wrapper.cqm_limit_total -1);
			hasExceededConditionAndConsequenceIndex = (hasExceededConditionIndex && hasExceededConsequenceIndex);
			
			if (hasExceededConditionAndConsequenceIndex) conditionAndConsequencePipeLength += wrapper.pipe_length_m;
			
			if (wrapper.inspected == 1) {
				if (hasExceededConsequenceIndex) consequencePipeLengthInspected += wrapper.pipe_length_m;
				if (hasExceededConditionIndex) conditionPipeLengthInspected += wrapper.pipe_length_m;
			}
			else {
				if (hasExceededConsequenceIndex) consequencePipeLengthNotInspected += wrapper.pipe_length_m;
				if (hasExceededConditionIndex) conditionPipeLengthNotInspected += wrapper.pipe_length_m;
			}

		}
		
//		System.out.println(indexWrapperList.size() + " " + conditionPipeLengthInspected);
		
		// then fill the 'to the point' pipeIndexSummary and display on page
		pipeIndexSummary = new PipeIndexSummary(conditionIndexLimit, consequenceIndexLimit, conditionPipeLengthInspected, conditionPipeLengthNotInspected, consequencePipeLengthInspected, consequencePipeLengthNotInspected, conditionAndConsequencePipeLength);

		List<Long> diameterArray = new ArrayList<Long>();
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(0, 101));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(101, 201));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(201, 301));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(301, 401));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(401, 501));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(501, 601));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(601, 701));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(701, 801));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(801, 901));
		diameterArray.add(Component.getPipeCountDistributionsAccordingToDiameter(901, 1001));

		List<Long> groundWaterArray = new ArrayList<Long>();
		groundWaterArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(0, 1));
		groundWaterArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(1, 2));
		groundWaterArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(2, 3));
		groundWaterArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(3, 4));
		
		List<Long> areaArray = new ArrayList<Long>();
		areaArray.add(Component.getPipeCountDistributionsAccordingToRelativeFloorAreaMeters(0, 11));
		areaArray.add(Component.getPipeCountDistributionsAccordingToRelativeFloorAreaMeters(11, 21));
		areaArray.add(Component.getPipeCountDistributionsAccordingToRelativeFloorAreaMeters(21, 31));
		areaArray.add(Component.getPipeCountDistributionsAccordingToRelativeFloorAreaMeters(31, 41));
		areaArray.add(Component.getPipeCountDistributionsAccordingToRelativeFloorAreaMeters(41, 51));		

		List<Long> roadclassArray = new ArrayList<Long>();
		roadclassArray.add(Component.getPipeCountDistributionsAccordingToRoadClassification(1, 2));
		roadclassArray.add(Component.getPipeCountDistributionsAccordingToRoadClassification(2, 3));
		roadclassArray.add(Component.getPipeCountDistributionsAccordingToRoadClassification(3, 4));
		roadclassArray.add(Component.getPipeCountDistributionsAccordingToRoadClassification(4, 5));
		
		List<Long> beachArray = new ArrayList<Long>();
		beachArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(0, 101));
		beachArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(101, 201));
		beachArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(201, 301));
		beachArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(301, 401));
		beachArray.add(Component.getPipeCountDistributionsAccordingToBeachDistance(401, 501));	
		
		List<Long> blockageArray = new ArrayList<Long>();
		blockageArray.add(Component.getPipeCountDistributionsAccordingToBlockages(0, 1));
		blockageArray.add(Component.getPipeCountDistributionsAccordingToBlockages(1, 2));
		blockageArray.add(Component.getPipeCountDistributionsAccordingToBlockages(2, 3));
		blockageArray.add(Component.getPipeCountDistributionsAccordingToBlockages(3, 4));
		blockageArray.add(Component.getPipeCountDistributionsAccordingToBlockages(4, 5));		

		List<Long> flushingEventArray = new ArrayList<Long>();
		flushingEventArray.add(Component.getPipeCountDistributionsAccordingToFlushingEvents(0, 4));
		flushingEventArray.add(Component.getPipeCountDistributionsAccordingToFlushingEvents(4, 8));
		flushingEventArray.add(Component.getPipeCountDistributionsAccordingToFlushingEvents(8, 12));
		flushingEventArray.add(Component.getPipeCountDistributionsAccordingToFlushingEvents(12, 16));
		flushingEventArray.add(Component.getPipeCountDistributionsAccordingToFlushingEvents(16, 20));		

		List<Long> extraWaterArray = new ArrayList<Long>();
		extraWaterArray.add(Component.getPipeCountDistributionsAccordingToExtraWaterPercentage(0, 4));
		extraWaterArray.add(Component.getPipeCountDistributionsAccordingToExtraWaterPercentage(4, 8));
		extraWaterArray.add(Component.getPipeCountDistributionsAccordingToExtraWaterPercentage(8, 12));
		extraWaterArray.add(Component.getPipeCountDistributionsAccordingToExtraWaterPercentage(12, 16));
		extraWaterArray.add(Component.getPipeCountDistributionsAccordingToExtraWaterPercentage(16, 20));		

		List<Long> cctvDefectsArray = new ArrayList<Long>();
		cctvDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVDefects(0, 6));
		cctvDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVDefects(5, 11));
		cctvDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVDefects(11, 16));
		cctvDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVDefects(16, 21));
		cctvDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVDefects(21, 26));
		cctvDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVDefects(26, 31));
		cctvDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVDefects(31, 36));		

		List<Long> cctvMajorDefectsArray = new ArrayList<Long>();
		cctvMajorDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVMajorDefects(0, 6));
		cctvMajorDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVMajorDefects(5, 11));
		cctvMajorDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVMajorDefects(11, 16));
		cctvMajorDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVMajorDefects(16, 21));
		cctvMajorDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVMajorDefects(21, 26));
		cctvMajorDefectsArray.add(Component.getPipeCountDistributionsAccordingToCCTVMajorDefects(26, 31));	

		/*List<Long> annualOverFlowArray = new ArrayList<Long>();
		annualOverFlowArray.add(Component.getPipeCountDistributionsAccordingToAnnualOverFlow(0, 21));
		annualOverFlowArray.add(Component.getPipeCountDistributionsAccordingToAnnualOverFlow(21, 41));
		annualOverFlowArray.add(Component.getPipeCountDistributionsAccordingToAnnualOverFlow(41, 61));
		annualOverFlowArray.add(Component.getPipeCountDistributionsAccordingToAnnualOverFlow(61, 81));
		annualOverFlowArray.add(Component.getPipeCountDistributionsAccordingToAnnualOverFlow(81, 101));*/		
				
		List<Double> diameterLengthArray = new ArrayList<Double>();
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(0, 101));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(101, 201));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(201, 301));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(301, 401));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(401, 501));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(501, 601));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(601, 701));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(701, 801));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(801, 901));
		diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(901, 1001));
		
		pipeIndexForm = pipeIndexForm.fill(pipeIndexFields);
		return ok(views.html.pipeIndex.render(pipeIndexForm, sortBy, order, pipeIndexSummary.pipeIndexSummaryUI, 
											  diameterArray, groundWaterArray, areaArray, roadclassArray, 
											  beachArray, blockageArray, flushingEventArray, extraWaterArray,
											  cctvDefectsArray, cctvMajorDefectsArray, diameterLengthArray));
	}
	
	public static Result sendFile() {
		File file = null;
		try {
			file = WritePipeIndexAsXLS.writePipeIndexAsXLS("Pipe Index Results", indexWrapperList);
		} catch (WriteException | IOException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("------SOMETHING BAD HAPPENED");
			e.printStackTrace();
		}
		
		response().setContentType("application/x-download");  
		response().setHeader("Content-disposition","attachment; filename=Pipe Index Results.xls");
		  return ok(file);
		}
}