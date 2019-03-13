package dataProviders;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import testScripts.JenkinsTesting_001;
import utils.ExcelUtility;

public class JenkinsTesting_001_DP {

	static Logger logger = Logger.getLogger(JenkinsTesting_001_DP.class.getName());

	@DataProvider(name = "JenkinsTesting_001_DP")
	public static Object[][] JenkinsTesting_001_DataProvider() {

		Object[][] obj = new Object[1][3];

		try {

			JenkinsTesting_001 object_Testing_001 = new JenkinsTesting_001();
			int row = object_Testing_001.getclassName("TestData.xlsx", "Login");
			logger.info("Excel row : " + row);

			for (int j = 0; j < 3; j++) {

				obj[0][j] = ExcelUtility.getStringCellData(row, j + 1);
				logger.info("Test data : " + j + " : " + obj[0][j]);
			}

		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		return obj;
	}
}
