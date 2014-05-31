package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import models.BuildingArea;
import models.Component;
import models.ComponentProperty;
import models.ComponentType;
import models.GroundWaterArea;
import models.PropertyType;
import models.enums.PropertyDataType;
import util.constants.ComponentConstants;

public class ReadXLS {

	public static final String UNDOUBLED_PIPE_SHEET = "Undoubled";
	public static final String PIPES_NEAR_WATERWORKS_SHEET = "Waterworks";
	public static final String PIPES_NEAR_NUUKSIO_LAKE_SHEET = "Nuuksion_pj";
	public static final String PIPES_UNDER_RAILWAY_SHEET = "Railway";
	public static final String PIPES_IN_PROTECTED_AREA_SHEET = "Protected areas";
	public static final String PIPES_UNDER_WATER_BODY_SHEET = "Waters";
	public static final String PIPES_UNDER_PROTECTED_DITCH = "Ditches";
	public static final String PIPES_OPERATIONAL_TYPE_KERAILYVIEMARI_SHEET = "Pipe_oper_type_kerailyviemari";
	public static final String PIPES_OPERATIONAL_TYPE_PAAVIEMARI_SHEET = "Pipe_operational_paaviemari";

	private String inputFile;
	private int identifierIndex;
	private int psNameIndex;
	private int idIndex;
	private int tableIdIndex;
	private int conditionClassNameIndex;
	private int observationNumericalValueIndex;
	private int observationTextValueIndex;
	private int dateIndex;

	private int flushingEventComponentIdentifier;
	private int flushingEventDate;

	private String pathToDataFile = "";

	private static Map<String, Integer> columnIndexMap = new HashMap<String, Integer>();
	private static List<Integer> columnsToSkip = new ArrayList<Integer>();

	public ReadXLS() {
	}

	public ReadXLS(String pathToDataFile) {
		this.pathToDataFile = pathToDataFile;
	}

	/*
	 * public void setInputFile(String inputFile) { this.inputFile = inputFile;
	 * }
	 * 
	 * public static void mapXlsColumns(Sheet sheet, Map<Integer, PropertyType>
	 * ptypeMap) {
	 * 
	 * PropertyType ptype = null; ptypeMap.clear(); columnIndexMap.clear();
	 * 
	 * for (int i = 0; i < sheet.getColumns(); i++) {
	 * 
	 * String cellContent = sheet.getCell(i, 0).getContents(); CellType
	 * cellContentType = sheet.getCell(i, 0).getType();
	 * 
	 * switch (cellContent) {
	 * 
	 * case "Identifier": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Pumping station area name": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "ID": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Table ID": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Class": if (columnIndexMap.containsKey(cellContent + " ID"))
	 * columnIndexMap.put(cellContent + " name", i); else
	 * columnIndexMap.put(cellContent + " ID", i); columnsToSkip.add(i); break;
	 * 
	 * case "x1": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "x2": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "y1": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "y2": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "z1": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "z2": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "X": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Y": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Z": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Length 3d (m)": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Installation date": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Type": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Model": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Material": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Diameter (mm)": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Z-top": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Z-bottom": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Original installation year": columnIndexMap.put(cellContent, i);
	 * columnsToSkip.add(i); break;
	 * 
	 * case "Address": columnIndexMap.put(cellContent, i); columnsToSkip.add(i);
	 * break;
	 * 
	 * case "Father ID": columnsToSkip.add(i); break;
	 * 
	 * case "Direction": columnsToSkip.add(i); break;
	 * 
	 * case "State": columnsToSkip.add(i); break;
	 * 
	 * case "Label x": columnsToSkip.add(i); break;
	 * 
	 * case "Label y": columnsToSkip.add(i); break;
	 * 
	 * case "LabelDirection": columnsToSkip.add(i); break;
	 * 
	 * case "Plan label": columnsToSkip.add(i); break;
	 * 
	 * case "Direction 2": columnsToSkip.add(i); break;
	 * 
	 * case "Direction 3": columnsToSkip.add(i); break;
	 * 
	 * case "Alkuperäinen asennusvuosi": columnsToSkip.add(i); break;
	 * 
	 * case "Pohjan korkeus": columnsToSkip.add(i); break;
	 * 
	 * case "Omistaja": columnsToSkip.add(i); break;
	 * 
	 * case "Maan pinnan korkeus": columnsToSkip.add(i); break;
	 * 
	 * default: if (cellContentType != CellType.EMPTY) { ptype = new
	 * PropertyType(); ptype.name = cellContent; if (sheet.getCell(i,
	 * 2).getType() == CellType.LABEL) ptype.propertyDataType =
	 * PropertyDataType.STRING; else if (sheet.getCell(i, 2).getType() ==
	 * CellType.NUMBER) ptype.propertyDataType = PropertyDataType.NUMERICAL;
	 * ptypeMap.put(i, ptype); } break; } }
	 * 
	 * for (String key : columnIndexMap.keySet()) { System.out.println(key +
	 * " : " + columnIndexMap.get(key)); } }
	 * 
	 * public void mapCCTVXlsColumns(Sheet sheet, Map<Integer, PropertyType>
	 * ptypeMap) {
	 * 
	 * ptypeMap.clear(); PropertyType ptype = new PropertyType(); ptype.name =
	 * "CCTV Blockage Inspection"; ptype.propertyDataType =
	 * PropertyDataType.BOTH; // used 0 for labeling cctv inspection property
	 * type ptypeMap.put(0, ptype);
	 * 
	 * for (int i = 0; i < sheet.getColumns(); i++) {
	 * 
	 * String cellContent = sheet.getCell(i, 0).getContents();
	 * 
	 * if (cellContent.equals("ID")) { idIndex = i; } else if
	 * (cellContent.equals("Table ID")) { tableIdIndex = i; } else if
	 * (cellContent.equals("Observation Date")) { dateIndex = i; } else if
	 * (cellContent.equals("Condition Class name")) { conditionClassNameIndex =
	 * i; } else if (cellContent.equals("Observation Numerical Value")) {
	 * observationNumericalValueIndex = i; } else if
	 * (cellContent.equals("Observation Text Value")) observationTextValueIndex
	 * = i; } }
	 * 
	 * public void mapFlushingEventsXlsColumns(Sheet sheet, Map<Integer,
	 * PropertyType> ptypeMap) {
	 * 
	 * ptypeMap.clear(); PropertyType ptype = new PropertyType(); ptype.name =
	 * "Flushing Events"; ptype.propertyDataType = PropertyDataType.STRING; //
	 * used 0 for labeling cctv inspection property type ptypeMap.put(0, ptype);
	 * 
	 * for (int i = 0; i < sheet.getColumns(); i++) {
	 * 
	 * String cellContent = sheet.getCell(i, 0).getContents();
	 * 
	 * if (cellContent.equals("Identifier")) { flushingEventComponentIdentifier
	 * = i; } else if (cellContent.equals("Date")) { flushingEventDate = i; } }
	 * }
	 * 
	 * public static void addComponentTypeAndSavePropertyTypeList( Map<Integer,
	 * PropertyType> ptypeMap, ComponentType ctype) {
	 * 
	 * DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date
	 * date = null; try { date = formatter.parse("2012-12-11 16:00:00"); } catch
	 * (ParseException e) { e.printStackTrace(); }
	 * 
	 * for (Integer key : ptypeMap.keySet()) { PropertyType ptype =
	 * ptypeMap.get(key); ptype.componentType = ctype; ptype.date = date; ptype
	 * = ptype.save(); ptypeMap.put(key, ptype); } }
	 * 
	 * public static void saveXlsRows(Sheet sheet, ComponentType ctype,
	 * Map<Integer, PropertyType> ptypeMap) {
	 * 
	 * // final EntityTransaction tx = JPA.em().getTransaction();
	 * 
	 * // Session session = (Session) JPA.em().getDelegate(); //
	 * StatelessSession stateless = //
	 * session.getSessionFactory().openStatelessSession(); //
	 * stateless.beginTransaction(); // tx.begin(); // int count = 0;
	 * 
	 * for (int i = 1; i < sheet.getRows(); i++) { // for (int i = 20000; i <
	 * 20699; i++) { Long datasource_code = Long .parseLong(sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.ID), i) .getContents());
	 * System.out.println("DATASOURCE CODE:" + datasource_code); Component
	 * checkComp = Component .findByDatasourceCode(datasource_code); if
	 * (checkComp == null) {
	 * 
	 * Component component = new Component(); component.component_type = ctype;
	 * 
	 * component.datasource_code = Long.parseLong(sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.ID), i) .getContents()); if
	 * (Component.findByDatasourceCode(component.datasource_code) != null) {
	 * System.out.println("-- COMPONENT IS AREADY INSERTED --"); continue; }
	 * component.name = sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.IDENTIFIER), i) .getContents();
	 * component.componentDetail.address = sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.ADDRESS), i) .getContents();
	 * component.componentDetail.datasource_class_code = Integer .parseInt(sheet
	 * .getCell( columnIndexMap .get(ComponentConstants.CLASS_ID),
	 * i).getContents()); component.componentDetail.datasource_class_name =
	 * sheet .getCell( columnIndexMap .get(ComponentConstants.CLASS_NAME),
	 * i).getContents(); component.componentDetail.diameter = sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.DIAMETER), i) .getContents();
	 * component.componentDetail.installation_year = Integer .parseInt(sheet
	 * .getCell( columnIndexMap .get(ComponentConstants.INSTALLATION_DATE),
	 * i).getContents()); component.componentDetail.length_3d =
	 * Double.parseDouble(sheet .getCell( columnIndexMap
	 * .get(ComponentConstants.LENGTH_3D), i) .getContents());
	 * component.componentDetail.material = sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.MATERIAL), i) .getContents(); try {
	 * component.componentDetail.model = sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.MODEL), i) .getContents(); } catch
	 * (Exception e) { component.componentDetail.model = sheet.getCell(
	 * columnIndexMap.get(ComponentConstants.TYPE), i) .getContents(); }
	 * 
	 * component.componentDetail.original_installation_year = Integer
	 * .parseInt(sheet .getCell( columnIndexMap
	 * .get(ComponentConstants.ORIGINAL_INSTALLATION_YEAR), i).getContents());
	 * try { component.componentDetail.x1 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.X1), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.x2 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.X2), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.y1 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.Y1), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.y2 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.Y2), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.z1 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.Z1), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.z2 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.Z2), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.x1 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.X), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.y1 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.Y), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.z1 = Double.parseDouble(sheet
	 * .getCell(columnIndexMap.get(ComponentConstants.Z), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.z1 = Double.parseDouble(sheet .getCell(
	 * columnIndexMap .get(ComponentConstants.Z_BOTTOM), i).getContents()); }
	 * catch (Exception e) { // TODO: handle exception } try {
	 * component.componentDetail.z2 = Double.parseDouble(sheet .getCell(
	 * columnIndexMap .get(ComponentConstants.Z_TOP), i) .getContents()); }
	 * catch (Exception e) { // TODO: handle exception }
	 * 
	 * if (sheet.getCell( columnIndexMap.get(ComponentConstants.PS_AREA_NAME),
	 * i) .getType() != CellType.EMPTY) { String name = sheet .getCell(
	 * columnIndexMap .get(ComponentConstants.PS_AREA_NAME), i).getContents();
	 * Component ownerComponent = Component.findPSComponent(name);
	 * 
	 * if (ownerComponent != null) { component.ownerComponent = ownerComponent;
	 * } else { component.description = name; } } DateFormat formatter = new
	 * SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"); Date date = null; try { date =
	 * formatter.parse("2013-02-27 12:00:00"); } catch (ParseException e) {
	 * e.printStackTrace(); } component.import_date = date; component =
	 * component.merge(); // stateless.insert(component.componentDetail); //
	 * stateless.insert(component); System.out.println("--- " + component.id +
	 * ":" + component.name + " --- ");
	 * 
	 * // List<ComponentProperty> cpropList = new ArrayList<>();
	 * 
	 * for (int j = 0; j < sheet.getColumns(); j++) {
	 * 
	 * String cellContent = sheet.getCell(j, i).getContents(); CellType
	 * cellContentType = sheet.getCell(j, i).getType();
	 * 
	 * if (!columnIndexMap.containsValue(j) && !columnsToSkip.contains(j)) { if
	 * (cellContentType != CellType.EMPTY || !cellContent.trim().equals("") ||
	 * cellContent != null) { ComponentProperty cprop = new ComponentProperty();
	 * cprop.component = component; cprop.propertyType = ptypeMap.get(j);
	 * cprop.import_date = date; if (cellContentType == CellType.NUMBER)
	 * cprop.valueDecimal = Double .parseDouble(cellContent); else
	 * cprop.valueString = cellContent;
	 * 
	 * // cpropList.add(cprop); // stateless.insert(cprop); cprop.save(); } } }
	 * // count++; // if (count % 5 == 0) { //
	 * stateless.getTransaction().commit(); // stateless.beginTransaction(); //
	 * } // ComponentProperty.batchInsert(cpropList); } } //
	 * stateless.getTransaction().commit(); }
	 * 
	 * public void saveCCTVXlsRows(Sheet sheet, ComponentType ctype,
	 * Map<Integer, PropertyType> ptypeMap) {
	 * 
	 * for (int i = 1; i < sheet.getRows(); i++) {
	 * 
	 * Component component = Component.findByDatasourceCode(Long
	 * .parseLong(sheet.getCell(idIndex, i).getContents()));
	 * 
	 * if (component == null) { System.out.println(sheet.getCell(idIndex,
	 * i).getContents()); continue; }
	 * 
	 * DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date
	 * date = null; try { date = formatter.parse("2012-12-11 15:00:00"); } catch
	 * (ParseException e) { e.printStackTrace(); }
	 * 
	 * ComponentProperty cprop = new ComponentProperty(); cprop.component =
	 * component; // key 0 is the cctv inspection property type in this case.
	 * cprop.propertyType = ptypeMap.get(0); cprop.import_date = date;
	 * 
	 * // add property value cprop.valueDecimal =
	 * Double.parseDouble(sheet.getCell( observationNumericalValueIndex,
	 * i).getContents()); // add property string value cprop.valueString =
	 * sheet.getCell(observationTextValueIndex, i) .getContents(); // add
	 * condition class name to description field cprop.description =
	 * sheet.getCell(conditionClassNameIndex, i) .getContents(); // add the date
	 * of the inspection try { cprop.date = DateUtilSphinx.formatDate(
	 * sheet.getCell(dateIndex, i).getContents(), "M/d/yy H:mm"); } catch
	 * (Exception e) { System.out.println(e); } cprop.save();
	 * System.out.println("component: " + cprop.component.name +
	 * " | component datasource_code: " + cprop.component.datasource_code +
	 * " | valueDec: " + cprop.valueString + " | description: " +
	 * cprop.description + " | date: " + cprop.date); } }
	 * 
	 * public void saveFlushingEventXlsRows(Sheet sheet, ComponentType ctype,
	 * Map<Integer, PropertyType> ptypeMap) {
	 * 
	 * for (int i = 1; i < sheet.getRows(); i++) {
	 * 
	 * // System.out.println("IDENTIFIER: " + //
	 * sheet.getCell(flushingEventComponentIdentifier, // i).getContents()); //
	 * System.out.println("COMPONENT TYPE: " + ctype.type_name); if
	 * (sheet.getCell(flushingEventComponentIdentifier, i)
	 * .getContents().trim().equals("")) {
	 * System.out.println("EMPTY IDENTIFIER"); continue; }
	 * 
	 * Component component = Component.findByNameAndComponentType(
	 * sheet.getCell(flushingEventComponentIdentifier, i) .getContents(),
	 * ctype);
	 * 
	 * if (component == null) { System.out.println("DONT EXIST: " +
	 * sheet.getCell(flushingEventComponentIdentifier, i) .getContents());
	 * continue; }
	 * 
	 * DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date
	 * date = null; try { date = formatter.parse("2012-12-11 16:00:00"); } catch
	 * (ParseException e) { e.printStackTrace(); }
	 * 
	 * ComponentProperty cprop = new ComponentProperty(); cprop.component =
	 * component; // key 0 is the cctv inspection property type in this case.
	 * cprop.propertyType = ptypeMap.get(0); cprop.import_date = date;
	 * 
	 * // add property string value cprop.valueString = "JV  Painehuuhtelu"; //
	 * add the date of the inspection try { //
	 * System.out.println(sheet.getCell(flushingEventDate, // i).getContents());
	 * cprop.date = DateUtilSphinx.formatDate( sheet.getCell(flushingEventDate,
	 * i).getContents(), "d/MM/yy"); } catch (Exception e) {
	 * System.out.println("DATE NOT TRANSFORMED : " +
	 * sheet.getCell(flushingEventDate, i).getContents() + "STACKTRACE : " + e);
	 * } cprop.save(); System.out.println("component: " + cprop.component.name +
	 * " | component datasource_code: " + cprop.component.datasource_code +
	 * " | valueString: " + cprop.valueString + " | description: " +
	 * cprop.description + " | date: " + cprop.date); } }
	 * 
	 * public void read() throws IOException { File inputWorkbook = new
	 * File(inputFile); WorkbookSettings ws = new WorkbookSettings();
	 * ws.setEncoding("Cp1252"); Workbook w; try { w =
	 * Workbook.getWorkbook(inputWorkbook, ws); String componentType = "Pipe";
	 * ComponentType ctype = ComponentType.findByName(componentType); if (ctype
	 * == null) { ctype = new ComponentType(); ctype.type_name = componentType;
	 * ctype = ctype.merge(); } Sheet sheet = w.getSheet("Putki"); Map<Integer,
	 * PropertyType> ptypeMap = new HashMap<Integer, PropertyType>();
	 * mapXlsColumns(sheet, ptypeMap);
	 * addComponentTypeAndSavePropertyTypeList(ptypeMap, ctype);
	 * saveXlsRows(sheet, ctype, ptypeMap); } catch (BiffException e) {
	 * e.printStackTrace(); } }
	 * 
	 * public void readComsumerPoints() throws IOException { File inputWorkbook
	 * = new File(inputFile); Workbook w; try { w =
	 * Workbook.getWorkbook(inputWorkbook); String componentType =
	 * "Consumer Point"; ComponentType ctype =
	 * ComponentType.findByName(componentType); if (ctype == null) { ctype = new
	 * ComponentType(); ctype.type_name = componentType; ctype = ctype.merge();
	 * } Sheet sheet = w.getSheet("Käyttöpaikka"); Map<Integer, PropertyType>
	 * ptypeMap = new HashMap<Integer, PropertyType>(); mapXlsColumns(sheet,
	 * ptypeMap); addComponentTypeAndSavePropertyTypeList(ptypeMap, ctype);
	 * saveXlsRows(sheet, ctype, ptypeMap); } catch (BiffException e) {
	 * e.printStackTrace(); } }
	 * 
	 * public void readCCTV() throws IOException { File inputWorkbook = new
	 * File(inputFile); WorkbookSettings ws = new WorkbookSettings();
	 * ws.setEncoding("Cp1252"); Workbook w; try { w =
	 * Workbook.getWorkbook(inputWorkbook, ws); String componentType = "Pipe";
	 * ComponentType ctype = ComponentType.findByName(componentType);
	 * 
	 * Sheet sheet = w.getSheet(0); Map<Integer, PropertyType> ptypeMap = new
	 * HashMap<Integer, PropertyType>(); mapCCTVXlsColumns(sheet, ptypeMap);
	 * addComponentTypeAndSavePropertyTypeList(ptypeMap, ctype);
	 * saveCCTVXlsRows(sheet, ctype, ptypeMap); } catch (BiffException e) {
	 * e.printStackTrace(); } }
	 * 
	 * public void readFlushingEvents() throws IOException { File inputWorkbook
	 * = new File(inputFile); WorkbookSettings ws = new WorkbookSettings();
	 * ws.setEncoding("Cp1252"); Workbook w; try { w =
	 * Workbook.getWorkbook(inputWorkbook, ws); String componentType = "Pipe";
	 * ComponentType ctype = ComponentType.findByName(componentType);
	 * 
	 * Sheet sheet = w.getSheet(0); Map<Integer, PropertyType> ptypeMap = new
	 * HashMap<Integer, PropertyType>(); mapFlushingEventsXlsColumns(sheet,
	 * ptypeMap); addComponentTypeAndSavePropertyTypeList(ptypeMap, ctype);
	 * saveFlushingEventXlsRows(sheet, ctype, ptypeMap); } catch (BiffException
	 * e) { e.printStackTrace(); } }
	 * 
	 * public static void main(String[] args) throws IOException { //
	 * readNISTest(); System.out.println("readXLS main"); ReadXLS test = new
	 * ReadXLS();
	 * test.setInputFile("import/TeklaNISToExcel_24024_Yet_to_insert_putki.xls"
	 * ); test.read();
	 * 
	 * // test.setInputFile("import/Blockage Inspections NIS.xls"); //
	 * test.readCCTV();
	 * 
	 * // test.setInputFile("import/Flushing_Events_NIS.xls"); //
	 * test.readFlushingEvents(); }
	 * 
	 * public static void readNISTest() {
	 * 
	 * try { System.out.println("girdi"); org.apache.poi.ss.usermodel.Workbook
	 * workbook = org.apache.poi.ss.usermodel.WorkbookFactory .create(new
	 * FileInputStream( "import/TeklaNISToExcel_24024_Sample.xlsx")); // or //
	 * sample.xls System.out.println("Number Of Sheets" +
	 * workbook.getNumberOfSheets()); org.apache.poi.ss.usermodel.Sheet sheet =
	 * workbook.getSheetAt(0); System.out.println("Number Of Rows:" +
	 * sheet.getLastRowNum());
	 * 
	 * org.apache.poi.ss.usermodel.Row row = sheet.getRow(5);
	 * System.out.println("Cell Value:" + row.getCell(5).getNumericCellValue());
	 * System.out.println("cikti"); } catch (Exception e) {
	 * System.out.println(e); e.printStackTrace(); } }
	 * 
	 * public List<Long> readRoadClassification() throws IOException { File
	 * inputWorkbook = new File(
	 * "import/road/Pipe_intersects_road4_buffer450cm.xls"); WorkbookSettings ws
	 * = new WorkbookSettings(); ws.setEncoding("Cp1252"); Workbook w;
	 * List<Long> pipelist = new ArrayList<Long>(); try { w =
	 * Workbook.getWorkbook(inputWorkbook, ws); Sheet[] sheetlist =
	 * w.getSheets(); int sheetcount = sheetlist.length; System.out.println(" "
	 * + sheetcount); int cnt = 0; for (int i = 0; i < sheetcount; i++) { Sheet
	 * sheet = sheetlist[i];
	 * 
	 * for (int j = 1; j < sheet.getRows(); j++) { Cell cll = sheet.getCell(0,
	 * j); if (cll.getType() == CellType.NUMBER) { cnt++;
	 * pipelist.add(Long.parseLong(cll.getContents())); } } }
	 * 
	 * // Sheet sheet = w.getSheet("Putki"); // Map<Integer, PropertyType>
	 * ptypeMap = new HashMap<Integer, // PropertyType>(); //
	 * mapXlsColumns(sheet, ptypeMap); //
	 * addComponentTypeAndSavePropertyTypeList(ptypeMap, ctype); //
	 * saveXlsRows(sheet, ctype, ptypeMap); } catch (BiffException e) {
	 * e.printStackTrace(); } return pipelist; }
	 * 
	 * public void savePipeClassification(List<Long> pipelist, Integer
	 * roadClass) throws IOException {
	 * 
	 * if (roadClass == null) roadClass = 3;
	 * 
	 * if (pipelist == null) pipelist = readRoadClassification();
	 * 
	 * for (Long id : pipelist) { System.out.println(id); Component component =
	 * Component.findByDatasourceCode(id); component.roadClassification =
	 * roadClass; component.merge(); } }
	 * 
	 * public List<Long> readPipeGroundWaterArea() throws IOException { File
	 * inputWorkbook = new File(
	 * "import/groundwaterarea/Pipes_GW19_buff100m.xls"); WorkbookSettings ws =
	 * new WorkbookSettings(); ws.setEncoding("Cp1252"); Workbook w; List<Long>
	 * pipelist = new ArrayList<Long>(); try { w =
	 * Workbook.getWorkbook(inputWorkbook, ws); Sheet[] sheetlist =
	 * w.getSheets(); int sheetcount = sheetlist.length; System.out.println(" "
	 * + sheetcount); int cnt = 0; for (int i = 0; i < sheetcount; i++) { Sheet
	 * sheet = sheetlist[i];
	 * 
	 * for (int j = 1; j < sheet.getRows(); j++) { Cell cll = sheet.getCell(0,
	 * j); if (cll.getType() == CellType.NUMBER) { cnt++;
	 * pipelist.add(Long.parseLong(cll.getContents())); } } }
	 * 
	 * // Sheet sheet = w.getSheet("Putki"); // Map<Integer, PropertyType>
	 * ptypeMap = new HashMap<Integer, // PropertyType>(); //
	 * mapXlsColumns(sheet, ptypeMap); //
	 * addComponentTypeAndSavePropertyTypeList(ptypeMap, ctype); //
	 * saveXlsRows(sheet, ctype, ptypeMap); } catch (BiffException e) {
	 * e.printStackTrace(); } return pipelist; }
	 * 
	 * public void savePipeGroundWaterArea(List<Long> pipelist, Long gwArea)
	 * throws IOException {
	 * 
	 * if (pipelist == null) pipelist = readPipeGroundWaterArea();
	 * 
	 * for (Long id : pipelist) { System.out.println(id); Component component =
	 * Component.findByDatasourceCode(id); GroundWaterArea area =
	 * GroundWaterArea.findByDatasourceCode(gwArea); component.groundWaterArea =
	 * area; component.merge(); } }
	 * 
	 * public HashMap<Integer, BuildingArea>
	 * readNeighbourghoodNoAndBuildingAreas() throws IOException { File
	 * inputWorkbook = new File("import/buildingarea/Pipes_Ramava.xls");
	 * WorkbookSettings ws = new WorkbookSettings(); ws.setEncoding("Cp1252");
	 * Workbook w; HashMap<Integer, BuildingArea> areaList = new
	 * HashMap<Integer, BuildingArea>(); try { w =
	 * Workbook.getWorkbook(inputWorkbook, ws); Sheet[] sheetlist =
	 * w.getSheets(); int sheetcount = sheetlist.length; System.out.println(" "
	 * + sheetcount); for (int i = 0; i < sheetcount; i++) { Sheet sheet =
	 * sheetlist[i];
	 * 
	 * for (int j = 1; j < sheet.getRows(); j++) { if (sheet.getCell(5,
	 * j).getType() == CellType.NUMBER) { int nbhoodNo =
	 * Integer.parseInt(sheet.getCell(5, j) .getContents()); if
	 * (!areaList.containsKey(nbhoodNo)) { BuildingArea area = new
	 * BuildingArea(); area.totalFloorArea = Long.parseLong(sheet.getCell( 2,
	 * j).getContents()); area.dwellingFloorArea = Long.parseLong(sheet
	 * .getCell(3, j).getContents()); area.nonDwellingFloorArea =
	 * Long.parseLong(sheet .getCell(4, j).getContents());
	 * areaList.put(nbhoodNo, area); System.out.println(nbhoodNo + " - " +
	 * area.totalFloorArea + " - " + area.dwellingFloorArea + " - " +
	 * area.nonDwellingFloorArea); } } } } } catch (BiffException e) {
	 * e.printStackTrace(); } return areaList; }
	 * 
	 * public void saveBuildingAreaSquareMeters( HashMap<Integer, BuildingArea>
	 * areaList) throws IOException {
	 * 
	 * for (Integer key : areaList.keySet()) { System.out.println(key);
	 * BuildingArea area = BuildingArea.findByNeighborhoodNo(key .longValue());
	 * area.totalFloorArea = areaList.get(key).totalFloorArea;
	 * area.dwellingFloorArea = areaList.get(key).dwellingFloorArea;
	 * area.nonDwellingFloorArea = areaList.get(key).nonDwellingFloorArea;
	 * area.update(); } }
	 * 
	 * public HashMap<Integer, BuildingArea> addBuildingAreaToEachPipe() throws
	 * IOException { File inputWorkbook = new
	 * File("import/buildingarea/Pipes_Ramava.xls"); WorkbookSettings ws = new
	 * WorkbookSettings(); ws.setEncoding("Cp1252"); Workbook w;
	 * HashMap<Integer, BuildingArea> areaList = new HashMap<Integer,
	 * BuildingArea>(); try { w = Workbook.getWorkbook(inputWorkbook, ws);
	 * Sheet[] sheetlist = w.getSheets(); int sheetcount = sheetlist.length;
	 * System.out.println(" " + sheetcount); for (int i = 0; i < sheetcount;
	 * i++) { System.out.println("SheetNO: " + i); Sheet sheet = sheetlist[i];
	 * 
	 * for (int j = 1; j < sheet.getRows(); j++) {
	 * 
	 * if (sheet.getCell(0, j).getType() == CellType.NUMBER && sheet.getCell(5,
	 * j).getType() == CellType.NUMBER) {
	 * 
	 * //TODO: read the ID in column 0 and the neighbourNo(KAUP) in column 5
	 * Long pipeDatasourceCode = Long.parseLong(sheet.getCell(0,
	 * j).getContents()); Long buildingAreaNeighborhoodNo =
	 * Long.parseLong(sheet.getCell(5,j).getContents());
	 * 
	 * //TODO: fetch component based on pipe ID. Likewise fetch building area
	 * based on KAUP Component pipeComponent =
	 * Component.findByDatasourceCode(pipeDatasourceCode); BuildingArea
	 * pipeBuildingArea =
	 * BuildingArea.findByNeighborhoodNo(buildingAreaNeighborhoodNo);
	 * 
	 * //TODO: assign the building area to the component and merge component.
	 * pipeComponent.buildingArea = pipeBuildingArea; pipeComponent.merge();
	 * System.out.println(pipeComponent.datasource_code + " building area( " +
	 * pipeComponent.buildingArea.neighborhoodNo + ") saved "); } } } } catch
	 * (BiffException e) { e.printStackTrace(); } return areaList; }
	 */

	private List<Long> readDataSheet(String sheetName) throws IOException {
		assert !pathToDataFile.isEmpty();

		File inputWorkbook = new File(pathToDataFile);
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		Workbook w;
		List<Long> pipelist = new ArrayList<Long>();
		try {
			w = Workbook.getWorkbook(inputWorkbook, ws);
			Sheet sheet = w.getSheet(sheetName);

			for (int j = 1; j < sheet.getRows(); j++) {
				Cell cll = sheet.getCell(0, j);
				if (cll.getType() == CellType.NUMBER) {
					pipelist.add(Long.parseLong(cll.getContents()));
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return pipelist;
	}

	public void importPipeDataInDataSheet(String sheetName) throws IOException {
		List<Long> pipeDatasourceCodeList = readDataSheet(sheetName);

		System.out.println();
		System.out
				.println("---> " + sheetName + ": Importing data in progress..");
		System.out.println();
		System.out.println("---> " + sheetName
				+ ": list of non-existing pipe ids:");
		System.out
				.println("==========================================================");
		int nonExistingCount = 0;
		for (Long id : pipeDatasourceCodeList) {
			Component component = Component.findByDatasourceCode(id);
			if (component == null) {
				System.out.println(id);
				nonExistingCount++;
			} else {
				switch (sheetName) {

				case UNDOUBLED_PIPE_SHEET:
					component.undoubled = true;
					break;
				case PIPES_NEAR_WATERWORKS_SHEET:
					component.closeToWaterwork = true;
					break;
				case PIPES_NEAR_NUUKSIO_LAKE_SHEET:
					component.closeToNuuksioLake = true;
					break;
				case PIPES_UNDER_RAILWAY_SHEET:
					component.underRailway = true;
					break;
				case PIPES_IN_PROTECTED_AREA_SHEET:
					component.inProtectedArea = true;
				case PIPES_UNDER_WATER_BODY_SHEET:
					component.underWaterbody = true;
					break;
				case PIPES_UNDER_PROTECTED_DITCH:
					component.underProtectedDitch = true;
					break;
				case PIPES_OPERATIONAL_TYPE_KERAILYVIEMARI_SHEET:
					component.operationalType = "Keräilyviemäri";
					break;
				case PIPES_OPERATIONAL_TYPE_PAAVIEMARI_SHEET:
					component.operationalType = "Pääviemäri";
					break;
				}
				component.merge();
			}
		}
		System.out.println();
		System.out.println("---> " + sheetName + ": Import complete."
				+ nonExistingCount + " amount of pipe data not found.");
	}
}
