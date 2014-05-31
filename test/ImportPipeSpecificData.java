
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.io.IOException;

import org.junit.Test;

import play.db.jpa.JPA;
import util.ReadXLS;

public class ImportPipeSpecificData {
	
	public static final String IMPORT_PIPEDATA_BASE_DIR = "import/pipedata/";
	public static final String PATH_TO_PIPE_SPECIFIC_DATA_FILE = IMPORT_PIPEDATA_BASE_DIR + "Espoo_new_data_for_Furkan.xls";

	@Test
	public void importPipeSpecificData() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {

						try {
							ReadXLS pipeSpecificXLS = new ReadXLS(PATH_TO_PIPE_SPECIFIC_DATA_FILE);
							pipeSpecificXLS.importPipeDataInDataSheet(ReadXLS.PIPES_OPERATIONAL_TYPE_KERAILYVIEMARI_SHEET);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
	
	public void importUndoubledPipes() {
		
	}
}
