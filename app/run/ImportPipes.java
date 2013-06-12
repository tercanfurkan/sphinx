package run;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import util.ReadXLS;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import models.ComponentType;
import models.PropertyType;

public class ImportPipes {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ImportPipes test = new ImportPipes();
		
		test.read();

	}
	
	@Transactional
	public void read() throws IOException {
		File inputWorkbook = new File("import/TeklaNISToExcel_24024_pipes501.xls");
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook, ws);
			String componentType = "Pipe";
			ComponentType ctype = ComponentType.findByName(componentType);
			
			Sheet sheet = w.getSheet("Putki");
			Map<Integer, PropertyType> ptypeMap = new HashMap<Integer, PropertyType>();
			ReadXLS.mapXlsColumns(sheet, ptypeMap);
			ReadXLS.addComponentTypeAndSavePropertyTypeList(ptypeMap, ctype);
			
			ReadXLS.saveXlsRows(sheet, ctype, ptypeMap);
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

}
