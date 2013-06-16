package datasource.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;


/**
 * This class parses Industrial Waste Water data 
 * from its datasource (xml/xls)
 * @author wa.tercano1
 *
 */
public class IndustrialWasteWaterMeterDataReader {
	
	public IndustrialWasteWaterMeterDataReader(String inputFile, String sheetName, String columnName) {

		this.inputFile = inputFile;
		this.sheetName = sheetName;
		this.columnName = columnName;
	}
	
	private String inputFile;
	private String sheetName;
	private String columnName;
	private Workbook workBook;
	
	private Workbook getXlsWorkBook() throws BiffException, IOException {
		
		File inputWorkbook = new File(inputFile);
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		workBook = Workbook.getWorkbook(inputWorkbook, ws);
		return workBook;
	}
	
	private Sheet getXlsSheet() {
		Sheet sheet = workBook.getSheet(sheetName);
		return sheet;
	}
	
	public List<Long> getPipesFromWorkBookSheetAndColumn() throws BiffException, IOException {
		workBook = getXlsWorkBook();
		Sheet sheet = getXlsSheet();
		List<Long> pipeCodeList = new ArrayList<Long>();
		
		int pipeCodeColumnIndex = 0;
		Long pipeCode = null;
		for (int i = 0; i < sheet.getColumns(); i++) {

			String cellContent = sheet.getCell(i, 0).getContents();
			if (cellContent == columnName) {
				pipeCodeColumnIndex = i;
			}
		}
		
		for (int i = 1; i < sheet.getRows(); i++) {
			pipeCode = Long.parseLong(sheet.getCell(pipeCodeColumnIndex, i).getContents());
			pipeCodeList.add(pipeCode);
		}
		
		return pipeCodeList;
	}
	


}
