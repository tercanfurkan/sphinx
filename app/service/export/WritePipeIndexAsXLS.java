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
	
	public static File writePipeIndexAsXLS(String fileName, List<PipeIndexWrapper> wrapperList) throws RowsExceededException, WriteException, IOException, IllegalArgumentException, IllegalAccessException {
		
		File retFile = new File("export/" + fileName + ".xls");
		
		WritableWorkbook workbook = Workbook.createWorkbook(retFile);
		System.out.println("------workbook created");
		WritableSheet sheet = workbook.createSheet("All Index results", 0);
		System.out.println("------sheet created");
		Label label = null;
		
		Class wrapperClass = wrapperList.get(0).getClass();
		Field[] valueObjFields = wrapperClass.getDeclaredFields();
		
		
		for (int i = 0; i < valueObjFields.length; i++) {
			label = new Label(i,0,valueObjFields[i].getName());
			sheet.addCell(label);
		}
		System.out.println("------headers filled");
		
		int row  = 1;
		for (PipeIndexWrapper wrapper : wrapperList) {
			for (int i = 0; i < valueObjFields.length; i++) {
		         valueObjFields[i].setAccessible(true);
		         Object o = valueObjFields[i].get(wrapper);
		         if (o instanceof String) {
		     		Label labelVal = new Label(i, row, (String) o); 
		     		sheet.addCell(labelVal);  
		         } else if (o instanceof Integer){
		     		Number numberVal = new Number(i, row, (int) o); 
		     		sheet.addCell(numberVal);
		         } else if (o instanceof Long){
			     		Number numberVal = new Number(i, row, (Long) o); 
			     		sheet.addCell(numberVal);
			     } else if (o instanceof Float){
			     		Number numberVal = new Number(i, row, (Float) o); 
			     		sheet.addCell(numberVal);
			     }  else if (o instanceof Double){
			     		Number numberVal = new Number(i, row, (Double) o); 
			     		sheet.addCell(numberVal);
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
