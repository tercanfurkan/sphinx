package util;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.postgresql.jdbc2.optional.SimpleDataSource;

import play.db.jpa.Transactional;
import scala.Array;

import modelWrappers.ScadaHrWrapper;
import modelWrappers.ScadaWrapper;
import models.Component;
import models.ComponentProperty;
import models.ComponentType;
import models.PropertyType;
import models.PumpScadaHourly;
import models.enums.PropertyDataType;
import models.enums.PumpName;

public class ReadCSV {
	
	public static Collection<Component> comList = new ArrayList<Component>();
	public static Collection<ScadaWrapper> scadaHourlyList = new ArrayList<ScadaWrapper>();
	public static Collection<ComponentProperty> componentPropList = new ArrayList<ComponentProperty>();  
	public static List<String> tmpStringList = new ArrayList<String>();
	public static int columnsInLine = 0;
	public static Map<String, ScadaWrapper> wrapperMap = new HashMap<String, ScadaWrapper>();
	public static ScadaHrWrapper scadaHrWrapper = new ScadaHrWrapper();

	public void parse(String filename) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
				new File(filename)
			));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		printLines(lines);
//		System.out.println("\n\n-----------------\n\n");
//		printRecordsfor3(lines);
//		printRecords(lines);
//		printConsumerPoints(lines);
		ScadaParser.parseScadaDoc(lines);
		scadaHrWrapper = ScadaParser.wrapScadaData(lines);
	}

	private void printLines(Collection<String> lines) {
		for (String line : lines) {
//			System.out.println(line);
		}
	}
	
	public static Collection<Component> getComponents() {
		new ReadCSV().parse("pslist.csv");
		return comList;
	}
	
	public static Map<String, ScadaWrapper> getScadaHourlyMap() {
		new ReadCSV().parse("EfeSus Tunti 010112-220112.csv");
		return wrapperMap;
	}
	
	public static ScadaHrWrapper getScadaHourly() {
		new ReadCSV().parse("import/EfeSus Tunti 091112-291112.csv");
		return scadaHrWrapper;
	}
	
	public static Collection<ComponentProperty> getComPropList() {
		new ReadCSV().parse("consumption.csv");
		return componentPropList;
	}
	
	public static String parseTime(String sTime) {
		
//		System.out.println("string time: " +sTime+ "  char at 0: " + sTime.charAt(1) + "  char at 1: " + sTime.charAt(2) + " -- " + iTime);
//		System.out.println("This is STIME: " + sTime);
		return sTime.substring(4, 6);
	}
	
	public static String parseDate(String sDate) {
		if (sDate.charAt(1) == '.') sDate = "0" + sDate;
		if (sDate.charAt(4) == '.') sDate = sDate.substring(0,3) + "0" + sDate.substring(3);
		
		return sDate;
	}
	
	public static Date formatDate(String sDate)  {
        DateFormat oldFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldDate = null;
		try {
			oldDate = (Date)oldFormatter.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oldDate = (Date)formatter.parse(formatter.format(oldDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println(oldDate);
        return oldDate;
	}
	
	public static List<String> configureEmptyValues(List<String> recordsOnLine) {
		List<String> newList = new ArrayList<String>();
		if (recordsOnLine.size() > tmpStringList.size()) {
			tmpStringList.clear();
			tmpStringList = recordsOnLine;
		}
		for (String str : recordsOnLine) {
			if (str == null || str.equals("")) 
				newList.add("-1");
			else {
				newList.add(str);
			}
		}
		if (newList.size() < tmpStringList.size()) {
			int loopCount = tmpStringList.size() - newList.size();
			for (int i=0; i<loopCount; i++) {
				newList.add("-1");
			}
		}
		return newList;
	}
	
	public static List<String> stringArraytoArrayList(String[] strArray) {
		List<String> strList = new ArrayList<String>();
		for (String str : strArray) {
			strList.add(str);
		}
		return strList;
	}
	
	public void checkColumnCount(Collection<String> strList) {
		for (String line : strList) {
			columnsInLine = line.split(",").length;
			break;
		}
	}
	
	private void printRecords(Collection<String> lines) {
		checkColumnCount(lines);
		for (String line : lines) {
			String[] recordsOnLineTmp = line.split(",");
			
			List<String> recordsOnLine1 = stringArraytoArrayList(recordsOnLineTmp);
			List<String> recordsOnLine = configureEmptyValues(recordsOnLine1);
//			Component co = new Component();
//			co.number = Integer.parseInt(recordsOnLine[0]);
//			co.name = recordsOnLine[1];
//			co.scope = Boolean.valueOf(recordsOnLine[2]);
//			co.owner = "furkan";
//			co.component_type = ComponentType.findById(1L);
//			System.out.println(co.number +"|"+ co.name +"|"+ co.scope);
//			comList.add(co);
			
			ScadaWrapper wrapper = new ScadaWrapper();
			PumpScadaHourly psh1 = new PumpScadaHourly();
			PumpScadaHourly psh2 = new PumpScadaHourly();
			wrapper.component = Component.findByName("JVP195");
//			System.out.println(" colm0 : " + recordsOnLine.get(0) + " colm1 : " + recordsOnLine.get(1) + " colm2 : " + recordsOnLine.get(2) + " colm3 : " + recordsOnLine.get(3)
//					 + " colm4 : " + recordsOnLine.get(4) + " colm5 : " + recordsOnLine.get(5) + " colm6 : " + recordsOnLine.get(6) + " colm7 : " + recordsOnLine.get(7)
//					 + " colm8 : " + recordsOnLine.get(8) + " colm9 : " + recordsOnLine.get(9) + " colm10 : " + recordsOnLine.get(10));
			String stringDate = parseDate(recordsOnLine.get(0)) + " " + parseTime(recordsOnLine.get(1)) + ":00:00";
			wrapper.psScadaHourly.timestamp = formatDate(stringDate);
			wrapper.psScadaHourly.FI_01 = Float.valueOf(recordsOnLine.get(2));
			wrapper.psScadaHourly.PINT = Float.valueOf(recordsOnLine.get(7));
			wrapper.psScadaHourly.FI_02 = Float.valueOf(recordsOnLine.get(8));
			wrapper.psScadaHourly.YHTK = Float.valueOf(recordsOnLine.get(9));
			wrapper.psScadaHourly.EQ_01 = Float.valueOf(recordsOnLine.get(10));
			
//			wrapper.psScadaHourly.F1_01 = recordsOnLine.get(2);
//			wrapper.psScadaHourly.PINT = recordsOnLine.get(7);
//			wrapper.psScadaHourly.F1_02 = recordsOnLine.get(8);
//			wrapper.psScadaHourly.YHTK = recordsOnLine.get(9);
//			wrapper.psScadaHourly.EQ_01 = recordsOnLine.get(10);
			
			psh1.pumpName = PumpName.P1;
			psh1.timestamp = formatDate(stringDate);
			psh1.PT = Float.parseFloat(recordsOnLine.get(3));
			psh1.PV = Float.parseFloat(recordsOnLine.get(5));	
			
//			psh1.average_current = recordsOnLine.get(5);	
			wrapper.pumpScadaHourlyList.add(psh1);
			
			psh2 = new PumpScadaHourly();
			psh2.pumpName = PumpName.P2;
			psh2.timestamp = formatDate(stringDate);
			psh2.PT = Float.parseFloat(recordsOnLine.get(4));
			psh2.PV = Float.parseFloat(recordsOnLine.get(6));
			
//			psh2.average_current = recordsOnLine.get(6);
			wrapper.pumpScadaHourlyList.add(psh2);
			scadaHourlyList.add(wrapper);
//			System.out.println();
//			System.out.println(stringDate + " -- " + wrapper.psScadaHourly.timestamp);
		}
	}
	
	private void printRecordsfor3(Collection<String> lines) {
		checkColumnCount(lines);
		for (String line : lines) {
			String[] recordsOnLineTmp = line.split(",");
			
			List<String> recordsOnLine1 = stringArraytoArrayList(recordsOnLineTmp);
			List<String> recordsOnLine = configureEmptyValues(recordsOnLine1);
			
			ScadaWrapper wrapper = new ScadaWrapper();
			PumpScadaHourly psh1 = new PumpScadaHourly();
			PumpScadaHourly psh2 = new PumpScadaHourly();
			PumpScadaHourly psh3 = new PumpScadaHourly();
			wrapper.component = Component.findByName("JVP3");
			String stringDate = parseDate(recordsOnLine.get(0)) + " " + parseTime(recordsOnLine.get(1)) + ":00:00";
			wrapper.psScadaHourly.timestamp = formatDate(stringDate);
			wrapper.psScadaHourly.FI_01 = Float.valueOf(recordsOnLine.get(2));
			wrapper.psScadaHourly.PINT = Float.valueOf(recordsOnLine.get(6));
			wrapper.psScadaHourly.FI_02 = Float.valueOf(recordsOnLine.get(7));
			wrapper.psScadaHourly.YHTK = Float.valueOf(recordsOnLine.get(8));
			wrapper.psScadaHourly.EQ_01 = Float.valueOf(recordsOnLine.get(9));
			
			psh1.pumpName = PumpName.P1;
			psh1.timestamp = formatDate(stringDate);
			psh1.PV = Float.parseFloat(recordsOnLine.get(3));		
			wrapper.pumpScadaHourlyList.add(psh1);
			
			psh2.pumpName = PumpName.P2;
			psh2.timestamp = formatDate(stringDate);
			psh2.PV = Float.parseFloat(recordsOnLine.get(4));
			wrapper.pumpScadaHourlyList.add(psh2);
			
			psh3.pumpName = PumpName.P3;
			psh3.timestamp = formatDate(stringDate);
			psh3.PV = Float.parseFloat(recordsOnLine.get(5));
			wrapper.pumpScadaHourlyList.add(psh3);
			
			scadaHourlyList.add(wrapper);
		}
	}
	
	private void printConsumerPoints(Collection<String> lines) {
		for (String line : lines) {
			String[] recordsOnLine = line.split(",");
			PropertyType annualCon = new PropertyType();
			PropertyType conPoints = new PropertyType();
			ComponentProperty annualConProperty = new ComponentProperty();
			ComponentProperty conPointsProperty = new ComponentProperty();
			String componentName = recordsOnLine[1];
			Component component = Component.findByName(componentName);
			
			annualCon.componentType = ComponentType.findById(component.component_type.id);
			annualCon.propertyDataType = PropertyDataType.NUMERICAL;
			annualCon.indicator = true;
			annualCon.name = "Areal Annual Consumption Forecast";
//			annualCon.save();
			annualConProperty.component = component;
//			annualConProperty.date = new Date();
			annualConProperty.name = component.name + "-ACF2012";
			annualConProperty.propertyType = annualCon;
			annualConProperty.valueDecimal = Double.parseDouble(recordsOnLine[3]);
//			annualConProperty.save();
			componentPropList.add(annualConProperty);
			
			conPoints.componentType = ComponentType.findById(component.component_type.id);
			conPoints.propertyDataType = PropertyDataType.NUMERICAL;
			conPoints.indicator = false;
			conPoints.name = "Areal Total Number of Consumer Points";
//			conPoints.save();
			conPointsProperty.component = component;
//			conPointsProperty.date = new Date();
			conPointsProperty.name = component.name + "-TNOCP2012";
			conPointsProperty.propertyType = conPoints;
			conPointsProperty.valueDecimal = Double.parseDouble(recordsOnLine[2]);
//			conPointsProperty.save();
			componentPropList.add(conPointsProperty);
		}
	}

	@Transactional
	public static void main(String[] args) {
		new ReadCSV().parse("EfeSus Tunti 010112-220112.csv");
	}
	
}
