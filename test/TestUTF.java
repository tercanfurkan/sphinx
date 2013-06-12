import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import models.ComponentType;
import models.PropertyType;

import util.ReadXLS;


public class TestUTF {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	
	private String inputFile;
	final static Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public static void main(String[] args) throws IOException {
		
		
		
		String original = "Alkuperäinen asennusvuosi";
		byte[] utf8Bytes = original.getBytes("UTF8");
		String parameterReturn = "";
		
		String newString = new String(utf8Bytes, UTF8_CHARSET);
		System.out.println(original);
		System.out.println(newString);
		
		TestUTF test = new TestUTF();
		test.setInputFile("import/PS_Pump_Manhole_ConsumerPoints_fromNIS_example.xls");
		parameterReturn = test.read();
		System.out.println(parameterReturn);

	}
	
	public String read() throws IOException {
		File inputWorkbook = new File(inputFile);
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook, ws);
			Sheet sheet = w.getSheet("Putki");
			
			String txt = sheet.getCell(3, 1).getContents();
			byte[] utf8Bytes = txt.getBytes("UTF8");
			return new String(utf8Bytes, UTF8_CHARSET);
			
		} catch (BiffException e) {
			e.printStackTrace();
			return "";
		}
	}

}
