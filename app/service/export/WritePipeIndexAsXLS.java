package service.export;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import models.wrapper.PipeIndexWrapper;

public class WritePipeIndexAsXLS {
	
	public static File writePipeIndexAsXLS(String fileName, List<PipeIndexWrapper> badConditionList, List<PipeIndexWrapper> badConsequenceList, List<PipeIndexWrapper> otherList) throws RowsExceededException, WriteException, IOException, IllegalArgumentException, IllegalAccessException {
		
		File retFile = new File("export/" + fileName + ".xls");
		
		WritableWorkbook workbook = Workbook.createWorkbook(retFile);
		workbook.createSheet("condition", 0);
		workbook.createSheet("consequence", 1);
		workbook.createSheet("other", 2);
		System.out.println("------workbook created");
		WritableSheet badConditionSheet = workbook.getSheet(0);
		WritableSheet badConsequenceSheet = workbook.getSheet(1);
		WritableSheet otherSheet = workbook.getSheet(2);
		System.out.println("------sheets created");
		Label label = null;
		
		Class wrapperClass = badConditionList.get(0).getClass();
		Field[] valueObjFields = wrapperClass.getDeclaredFields();
		
		
		for (int i = 0; i < valueObjFields.length; i++) {
			label = new Label(i,0,valueObjFields[i].getName());
			badConditionSheet.addCell(label);
		}		
		for (int i = 0; i < valueObjFields.length; i++) {
			label = new Label(i,0,valueObjFields[i].getName());
			badConsequenceSheet.addCell(label);
		}		
		for (int i = 0; i < valueObjFields.length; i++) {
			label = new Label(i,0,valueObjFields[i].getName());
			otherSheet.addCell(label);
		}
		System.out.println("------headers filled");
		
		int row  = 1;
		for (PipeIndexWrapper wrapper : badConditionList) {
			for (int i = 0; i < valueObjFields.length; i++) {
		         valueObjFields[i].setAccessible(true);
		         Object o = valueObjFields[i].get(wrapper);
		         if (o instanceof String) {
		     		Label labelVal = new Label(i, row, (String) o); 
		     		badConditionSheet.addCell(labelVal);  
		         } else if (o instanceof Integer){
		     		Number numberVal = new Number(i, row, (int) o); 
		     		badConditionSheet.addCell(numberVal);
		         } else if (o instanceof Long){
			     		Number numberVal = new Number(i, row, (Long) o); 
			     		badConditionSheet.addCell(numberVal);
			     } else if (o instanceof Float){
			     		Number numberVal = new Number(i, row, (Float) o); 
			     		badConditionSheet.addCell(numberVal);
			     }  else if (o instanceof Double){
			     		Number numberVal = new Number(i, row, (Double) o); 
			     		badConditionSheet.addCell(numberVal);
			     }
			}
			row ++;
		}		
		row  = 1;
		for (PipeIndexWrapper wrapper : badConsequenceList) {
			for (int i = 0; i < valueObjFields.length; i++) {
		         valueObjFields[i].setAccessible(true);
		         Object o = valueObjFields[i].get(wrapper);
		         if (o instanceof String) {
		     		Label labelVal = new Label(i, row, (String) o); 
		     		badConsequenceSheet.addCell(labelVal);  
		         } else if (o instanceof Integer){
		     		Number numberVal = new Number(i, row, (int) o); 
		     		badConsequenceSheet.addCell(numberVal);
		         } else if (o instanceof Long){
			     		Number numberVal = new Number(i, row, (Long) o); 
			     		badConsequenceSheet.addCell(numberVal);
			     } else if (o instanceof Float){
			     		Number numberVal = new Number(i, row, (Float) o); 
			     		badConsequenceSheet.addCell(numberVal);
			     }  else if (o instanceof Double){
			     		Number numberVal = new Number(i, row, (Double) o); 
			     		badConsequenceSheet.addCell(numberVal);
			     }
			}
			row ++;
		}		
		row  = 1;
		for (PipeIndexWrapper wrapper : otherList) {
			for (int i = 0; i < valueObjFields.length; i++) {
		         valueObjFields[i].setAccessible(true);
		         Object o = valueObjFields[i].get(wrapper);
		         if (o instanceof String) {
		     		Label labelVal = new Label(i, row, (String) o); 
		     		otherSheet.addCell(labelVal);  
		         } else if (o instanceof Integer){
		     		Number numberVal = new Number(i, row, (int) o); 
		     		otherSheet.addCell(numberVal);
		         } else if (o instanceof Long){
			     		Number numberVal = new Number(i, row, (Long) o); 
			     		otherSheet.addCell(numberVal);
			     } else if (o instanceof Float){
			     		Number numberVal = new Number(i, row, (Float) o); 
			     		otherSheet.addCell(numberVal);
			     }  else if (o instanceof Double){
			     		Number numberVal = new Number(i, row, (Double) o); 
			     		otherSheet.addCell(numberVal);
			     }
			}
			row ++;
		}
		System.out.println("------content filled");

		
		workbook.write(); 
		System.out.println("------workbook written to file");
		workbook.close();
		System.out.println("------workbook closed!");
		
		return retFile;
	}

}
