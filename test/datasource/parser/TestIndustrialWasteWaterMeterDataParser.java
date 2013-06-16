package datasource.parser;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import jxl.read.biff.BiffException;

import datasource.reader.IndustrialWasteWaterMeterDataReader;

import play.db.jpa.JPA;

public class TestIndustrialWasteWaterMeterDataParser {
	
//	@Test
	public void testIndustrialWasteWaterMeterDataParser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
						
						String inputFile = "import/Luoteisrinne 2-Suomenoja.xls";
						String sheetName = "Putki";
						String columnName = "ID";
						List<Long> pipeCodeList = null;
						IndustrialWasteWaterMeterDataReader reader = new IndustrialWasteWaterMeterDataReader(inputFile, sheetName, columnName);
						IndustrialWasteWaterMeterDataParser parser = new IndustrialWasteWaterMeterDataParser();
						try {
							pipeCodeList = reader.getPipesFromWorkBookSheetAndColumn();
						} catch (BiffException | IOException e) {
							// TODO Auto-generated catch block
							System.out.println("testIndustrialWasteWaterMeterDataParser -- something bad happened");
							e.printStackTrace();
						}
						parser.setPipesInListAsIndustrial(pipeCodeList);
					}
				});
			}
		});
	}

}
