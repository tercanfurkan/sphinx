package util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.BuildingArea;
import models.GroundWaterArea;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Type;
import nl.knaw.dans.common.dbflib.ValueTooLargeException;

public class ReadDBF {

	public static void main(String[] args) {
		ReadDBF rdbf = new ReadDBF();
		rdbf.readBuildingAreas();
	}

	public void readGroundWaterAreas() {
		
		final Table table = new Table(new File(
				"import/groundwaterarea/Groundwater_Espoot.DBF"));

		try {
			table.open(IfNonExistent.ERROR);

			final Format dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			System.out.println("TABLE PROPERTIES");
			System.out.println("Name          : " + table.getName());
			System.out.println("Last Modified : "
					+ dateFormat.format(table.getLastModifiedDate()));
			System.out.println("--------------");
			System.out.println();
			System.out.println("FIELDS (COLUMNS)");

			final List<Field> fields = table.getFields();

			for (final Field field : fields) {
				System.out.println("  Name       : " + field.getName());
				System.out.println("  Type       : " + field.getType());
				System.out.println("  Length     : " + field.getLength());
				System.out.println("  Dec. Count : " + field.getDecimalCount());
				System.out.println();
			}

			System.out.println("--------------");
			System.out.println();
			System.out.println("RECORDS");

			final Iterator<Record> recordIterator = table.recordIterator();
			int count = 0;
			List<GroundWaterArea> arealist = new ArrayList<GroundWaterArea>();

			while (recordIterator.hasNext()) {
				GroundWaterArea area = new GroundWaterArea();
				final Record record = recordIterator.next();
				System.out.println(count++);

				for (final Field field : fields) {
					System.out.println("** " + field.getName());
					
					switch (field.getName()) {
					
					case "OBJECTID":
						area.datasourceCode = Long.parseLong((new String(record.getRawValue(field))).trim());
						break;
						
					case "PvAlueNimi":
						area.area = (new String(record.getRawValue(field))).trim();
						break;
						
					case "PvAlueLuok":
						area.classificationText = (new String(record.getRawValue(field))).trim();
						break;
						
					case "KuntaNimi":
						area.city = (new String(record.getRawValue(field))).trim();
						break;
						
					case "TilaMaara":
						area.quantity = (new String(record.getRawValue(field))).trim();
						break;
						
					case "TilaKemia":
						area.chemicalCondition = (new String(record.getRawValue(field))).trim();
						break;
						
					case "RiskiArvio":
						area.riskAssesment = (new String(record.getRawValue(field))).trim();
						break;

					default:
						break;
					}
					
					try {
						byte[] rawValue = record.getRawValue(field);
						System.out.println(field.getName()
								+ " : "
								+ (rawValue == null ? "<NULL>" : new String(
										rawValue)));
					} catch (ValueTooLargeException vtle) {
						// Cannot happen :)
						vtle.printStackTrace();
					}
				}

				arealist.add(area);
				System.out.println();
			}

			System.out.println("--------------");
			
			for (GroundWaterArea area : arealist) {
				System.out.println("id: " +area.datasourceCode);
				System.out.println("cc: " +area.chemicalCondition);
				System.out.println("c: " +area.city);
				System.out.println("a: " +area.area);
				System.out.println("cv: " +area.classificationText);
				System.out.println("q: " +area.quantity);
				System.out.println("ra: " +area.riskAssesment);
				System.out.println("--------------");
				
				area.save();
			}
			
		} catch (IOException ioe) {
			System.out.println("Trouble reading table or table not found");
			ioe.printStackTrace();
		} catch (DbfLibException dbflibException) {
			System.out.println("Problem getting raw value");
			dbflibException.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException ex) {
				System.out.println("Unable to close the table");
			}
		}
	}
	
	public void readBuildingAreas() {
		
		final Table table = new Table(new File(
				"import/buildingarea/RAMAVA.dbf"));

		try {
			table.open(IfNonExistent.ERROR);

			final Format dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			System.out.println("TABLE PROPERTIES");
			System.out.println("Name          : " + table.getName());
			System.out.println("Last Modified : "
					+ dateFormat.format(table.getLastModifiedDate()));
			System.out.println("--------------");
			System.out.println();
			System.out.println("FIELDS (COLUMNS)");

			final List<Field> fields = table.getFields();

			for (final Field field : fields) {
				System.out.println("  Name       : " + field.getName());
				System.out.println("  Type       : " + field.getType());
				System.out.println("  Length     : " + field.getLength());
				System.out.println("  Dec. Count : " + field.getDecimalCount());
				System.out.println();
			}

			System.out.println("--------------");
			System.out.println();
			System.out.println("RECORDS");

			final Iterator<Record> recordIterator = table.recordIterator();
			int count = 0;
			List<BuildingArea> arealist = new ArrayList<BuildingArea>();

			while (recordIterator.hasNext()) {
				BuildingArea area = new BuildingArea();
				final Record record = recordIterator.next();
				System.out.println(count++);

				for (final Field field : fields) {
					System.out.println("** " + field.getName());
					
					switch (field.getName()) {
					
					case "OBJECTID":
						area.datasourceCode = Long.parseLong((new String(record.getRawValue(field))).trim());
						break;
						
					case "KAUPOSANRO":
						area.neighborhoodNo = Long.parseLong((new String(record.getRawValue(field))).trim());
						break;

					default:
						break;
					}
					
					try {
						byte[] rawValue = record.getRawValue(field);
						System.out.println(field.getName()
								+ " : "
								+ (rawValue == null ? "<NULL>" : new String(
										rawValue)));
					} catch (ValueTooLargeException vtle) {
						// Cannot happen :)
					}
				}

				arealist.add(area);
				System.out.println();
			}

			System.out.println("--------------");
			
			for (BuildingArea area : arealist) {
				
				area.save();
			}
			
		} catch (IOException ioe) {
			System.out.println("Trouble reading table or table not found");
			ioe.printStackTrace();
		} catch (DbfLibException dbflibException) {
			System.out.println("Problem getting raw value");
			dbflibException.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException ex) {
				System.out.println("Unable to close the table");
			}
		}
	}
}
