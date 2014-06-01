package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import models.Component;
import models.Component.AreaAndMeters;
import models.Component.AreaMeterList;
import models.form.MeterLimitVal;
import models.meter.sensitivity.PipeSensitivityIndex;
import models.meter.sensitivity.PipeSensitivityIndex.SensitivityIndexPage;
import models.wrapper.PipeIndexSummary;
import models.wrapper.PipeIndexWrapper;
import models.wrapper.PipeIndexWrapperView;
import models.DataLoader;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import service.PipeIndexServ;
import service.export.WritePipeIndexAsXLS;
import util.MathUtilSphinx;
import views.html.*;

import play.*;
import play.data.*;
import static play.data.Form.*;
import play.i18n.Messages;
import play.api.i18n.Lang;
import play.mvc.Call;
import play.i18n.Messages;
import play.api.i18n.Lang;

public class Application extends Controller {
	
	static List<PipeIndexWrapper> indexWrapperList = null;
	static List<PipeIndexWrapper> badConditionIndexWrapperList = new ArrayList<PipeIndexWrapper>();
	static List<PipeIndexWrapper> badConsequenceIndexWrapperList = new ArrayList<PipeIndexWrapper>();
	static List<PipeIndexWrapper> otherIndexWrapperList = new ArrayList<PipeIndexWrapper>();
	static List<PipeIndexWrapperView> pipeIndexWrapperViewList = null;
	
	private static final int CONDITION_INDEX_EXCEED_LIMIT = 6;
	private static final int CONSEQUENCE_INDEX_EXCEED_LIMIT = 8;
	private static String currentLanguage = "en";
	
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
		response().setContentType("text/html; charset=utf-8");	
		return ok(list.render(Component.page(page, 10, sortBy, order, filter, currentLanguage),
				sortBy, order, filter), "utf-8");
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
				Component.pagePipes(page, 10, sortBy, order, filter, currentLanguage), sortBy,
				order, filter));
	}

	@Transactional(readOnly = true)
	public static Result listManholes(int page, String sortBy, String order,
			String filter) {
		
		return ok(listManholes.render(
				Component.pageManholes(page, 10, sortBy, order, filter, currentLanguage),
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
	public static Result indexResults(int page, String sortBy, String order) {
		//MeterLimitVal meterLimitVal = new MeterLimitVal();
		Form<MeterLimitVal> meterLimitValForm = play.data.Form.form(MeterLimitVal.class);
		MeterLimitVal meterLimitValFields = meterLimitValForm.bindFromRequest().get();
		
		if (pipeIndexWrapperViewList == null) {
			System.out.println("--- pipeIndexWrapperViewList is now NULL! both pipeIndexWrapperViewList and indexWrapperList will be FILLED");
			pipeIndexWrapperViewList = PipeIndexWrapperView.getPipeIndexWrapperViewList();
			indexWrapperList = PipeIndexServ.calculatePipeIndex(pipeIndexWrapperViewList, meterLimitValFields);
		}
		
		indexWrapperList = PipeIndexServ.calculatePipeIndex(pipeIndexWrapperViewList, meterLimitValFields);

		// fill the 'to the point' pipeIndexResult of each pipe
		// then fill the 'to the point' pipeIndexSummary and display on page
		PipeIndexSummary pipeIndexSummary = null; // pass this object to render
		Float conditionIndexLimit = 5F; // this is hardcoded. It is 5 and 3 for non inspected pipes
		Float consequenceIndexLimit = 2F;
		boolean hasExceededConditionIndex = false;
		boolean hasExceededConsequenceIndex = false;
		boolean hasExceededConditionAndConsequenceIndex = false;
		Float conditionPipeLengthInspected = 0.000F; // length of inspected pipes exceed condition index limit
		Float conditionPipeLengthNotInspected = 0.000F; // length of not inspected pipes exceed condition index limit
		Float consequencePipeLengthInspected = 0.000F; // length of inspected pipes exceed consequence index limit
		Float consequencePipeLengthNotInspected = 0.000F; // length of not inspected pipes exceed consequence index limit
		Float conditionAndConsequencePipeLength = 0.000F; // length of pipes that exceed condition and consequence index limit
		
		badConditionIndexWrapperList.clear();
		badConsequenceIndexWrapperList.clear();
		otherIndexWrapperList.clear();
		
		for(PipeIndexWrapper wrapper : indexWrapperList) {	
			
			if (wrapper.pipe_condition_index >= CONDITION_INDEX_EXCEED_LIMIT) {
				hasExceededConditionIndex = true;
				badConditionIndexWrapperList.add(wrapper);
			}
			
			if (wrapper.pipe_consequence_index >= CONSEQUENCE_INDEX_EXCEED_LIMIT) {
				hasExceededConsequenceIndex = true;
				badConsequenceIndexWrapperList.add(wrapper);
			}
			
			if (hasExceededConditionIndex && hasExceededConsequenceIndex)
				hasExceededConditionAndConsequenceIndex = true;
			else if (wrapper.pipe_condition_index < CONDITION_INDEX_EXCEED_LIMIT && wrapper.pipe_consequence_index < CONSEQUENCE_INDEX_EXCEED_LIMIT)
				otherIndexWrapperList.add(wrapper);
			
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
		
		pipeIndexSummary = new PipeIndexSummary(conditionIndexLimit, consequenceIndexLimit, conditionPipeLengthInspected, conditionPipeLengthNotInspected, consequencePipeLengthInspected, consequencePipeLengthNotInspected, conditionAndConsequencePipeLength);

		//meterLimitValForm = meterLimitValForm.fill(meterLimitValFields);
		System.out.println("MeterLimitValForm isPipeDiameterImportant > " + meterLimitValFields.isPipeDiameterImportant);
		System.out.println("MeterLimitValForm isFloorAreaImportant > " + meterLimitValFields.isFloorAreaImportant);	
		System.out.println("MeterLimitValForm isGwaImportant > " + meterLimitValFields.isGwaImportant);					
		System.out.println("MeterLimitValForm isSewerTypeImportant > " + meterLimitValFields.isSewerTypeImportant);
		System.out.println("MeterLimitValForm isRoadTypeImportant > " + meterLimitValFields.isRoadTypeImportant);
		System.out.println("MeterLimitValForm isBlockageImportant > " + meterLimitValFields.isBlockageImportant);
		System.out.println("MeterLimitValForm isFlushingImportant > " + meterLimitValFields.isFlushingImportant);
		System.out.println("MeterLimitValForm isInflowAndInfiltrationImportant > " + meterLimitValFields.isInflowAndInfiltrationImportant);
		System.out.println("MeterLimitValForm isCCTVDefectsImportant > " + meterLimitValFields.isCCTVDefectsImportant);
		System.out.println("MeterLimitValForm isCCTVMajorDefectsImportant > " + meterLimitValFields.isCCTVMajorDefectsImportant);		

		System.out.println("MeterLimitValForm diameterMin > " + meterLimitValFields.diameterMin);
		System.out.println("MeterLimitValForm diameterMax > " + meterLimitValFields.diameterMax);
		System.out.println("MeterLimitValForm floorAreaMin > " + meterLimitValFields.floorAreaMin);
		System.out.println("MeterLimitValForm floorAreaMax > " + meterLimitValFields.floorAreaMax);		

		System.out.println("MeterLimitValForm gwaImportantVal > " + meterLimitValFields.gwaImportantVal);
		System.out.println("MeterLimitValForm gwaSuitableVal > " + meterLimitValFields.gwaSuitableVal);
		System.out.println("MeterLimitValForm gwaOtherVal > " + meterLimitValFields.gwaOtherVal);
				
		System.out.println("MeterLimitValForm pipeTypeCollectionVal > " + meterLimitValFields.pipeTypeCollectionVal);
		System.out.println("MeterLimitValForm pipeTypeGravetyVal > " + meterLimitValFields.pipeTypeGravetyVal);
		System.out.println("MeterLimitValForm pipeTypePressure > " + meterLimitValFields.pipeTypePressure);
		System.out.println("MeterLimitValForm pipeTypeSmallPressureVal > " + meterLimitValFields.pipeTypeSmallPressureVal);		
		
		System.out.println("MeterLimitValForm roadRegionalMain1Val > " + meterLimitValFields.roadRegionalMain1Val);
		System.out.println("MeterLimitValForm roadRegionalMain2Val > " + meterLimitValFields.roadRegionalMain2Val);
		System.out.println("MeterLimitValForm roadLocalMainVal > " + meterLimitValFields.roadLocalMainVal);
		System.out.println("MeterLimitValForm roadCollectorVal > " + meterLimitValFields.roadCollectorVal);		
		
		System.out.println("MeterLimitValForm  blockageMin > " + meterLimitValFields.blockageMin);
		System.out.println("MeterLimitValForm  blockageMax > " + meterLimitValFields.blockageMax);
		System.out.println("MeterLimitValForm  flushingMin > " + meterLimitValFields.flushingMin);
		System.out.println("MeterLimitValForm  flushingMax > " + meterLimitValFields.flushingMax);
		System.out.println("MeterLimitValForm  extraWaterMin > " + meterLimitValFields.extraWaterMin);
		System.out.println("MeterLimitValForm  extraWaterMin > " + meterLimitValFields.extraWaterMax);
		System.out.println("MeterLimitValForm  cctvMin > " + meterLimitValFields.cctvMin);
		System.out.println("MeterLimitValForm  cctvMax > " + meterLimitValFields.cctvMax);
		System.out.println("MeterLimitValForm  cctv34Min > " + meterLimitValFields.cctv34Min);
		System.out.println("MeterLimitValForm  cctv34Max > " + meterLimitValFields.cctv34Max);		
		
		System.out.println("MeterLimitValForm  amountOfConsequenceRed > " + meterLimitValFields.amountOfConsequenceRed);
		System.out.println("MeterLimitValForm  amountOfConditionRed > " + meterLimitValFields.amountOfConditionRed);
		System.out.println("MeterLimitValForm  amountOfConsequenceYellow > " + meterLimitValFields.amountOfConsequenceYellow);
		System.out.println("MeterLimitValForm  amountOfConditionYellow > " + meterLimitValFields.amountOfConditionYellow);		
		
		final Map<String, String[]> values = request().body().asFormUrlEncoded();
		System.out.println("Request map values > " + request().body().asText()/*values.toString()*/);

		return ok(views.html.indexResults.render(sortBy, order, pipeIndexSummary.pipeIndexSummaryUI));
	}
	
	/**
	 * Display pipe sensitivity and condition meters and indexes.
	 * 
	 */
	@Transactional(readOnly = true)
	public static Result pipeIndex(int page, String sortBy, String order) {
        						
		DataLoader loader = new DataLoader();
		List<Double> diameterLengthArray = loader.loadDiameterData();
		List<Double> diameterLengthArray2 = new ArrayList<Double>();
		List<Double> areaLengthArray = loader.loadAreaLengthData();
		List<Double> groundWaterLengthArray = loader.loadGroundWaterLengthData();
		List<Double> roadclassLengthArray = loader.loadRoadClassLengthData();
		List<Double> blockageLengthArray = loader.loadBlockageLengthData();
		List<Double> flushingEventLengthArray = loader.loadFlushingEventLengthData();
		List<Double> extraWaterLengthArray = loader.loadExtraWaterLengthData();
		List<Double> cctvDefectsLengthArray = loader.loadCctvDefectsLengthData();
		List<Double> cctvMajorDefectsLengthArray = loader.loadCctvMajorDefectsLengthData();
		List<Double> undoubledPipeLengthArray = loader.loadUndoubledPipeLengthData();
		List<Double> waterworkArr = loader.loadPipesCloseToWaterworkData();
		List<Double> nuuksioArr = loader.loadPipesCloseToNuuksioLakeData();
		List<Double> protectedAreaArr = loader.loadPipesInProtectedAreaData();
		List<Double> operationalTypeArr = loader.loadPipesOperationalTypeData();
		List<Double> protectedDitchArr = loader.loadPipesUnderProtectedDitchData();
		List<Double> railwayArr = loader.loadPipesUnderRailwayData();
		List<Double> waterbodyArr = loader.loadPipesUnderWaterBodyData();
		
		Form<MeterLimitVal> meterLimitValForm = play.data.Form.form(MeterLimitVal.class);	
		
		response().setContentType("text/html; charset=utf-8");
		return ok(views.html.pipeIndex.render(loader, meterLimitValForm, sortBy, order,
											  diameterLengthArray, diameterLengthArray2, groundWaterLengthArray, areaLengthArray, roadclassLengthArray, 
											  blockageLengthArray, flushingEventLengthArray, extraWaterLengthArray,
											  cctvDefectsLengthArray, cctvMajorDefectsLengthArray, 
											  undoubledPipeLengthArray, waterworkArr, nuuksioArr, protectedAreaArr,
											  operationalTypeArr, protectedDitchArr, railwayArr, waterbodyArr), "utf-8");
	}
	
	public static Result sendFile() {
		File file = null;
		try {
			file = WritePipeIndexAsXLS.writePipeIndexAsXLS("Pipe Index Results", badConditionIndexWrapperList, badConsequenceIndexWrapperList, otherIndexWrapperList);
		} catch (WriteException | IOException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("------SOMETHING BAD HAPPENED");
			e.printStackTrace();
		}
		
		response().setContentType("application/x-download");  
		response().setHeader("Content-disposition","attachment; filename=Pipe Index Results.xls");
		  return ok(file);	
	}
	
	public static Result change(String langCode) {
		currentLanguage = langCode;
		changeLang(langCode);
		return GO_HOME;
    }	
}