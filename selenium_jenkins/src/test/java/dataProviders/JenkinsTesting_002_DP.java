package dataProviders;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import testScripts.JenkinsTesting_002;
import utils.ExcelUtility;

public class JenkinsTesting_002_DP {

	static Logger logger = Logger.getLogger(JenkinsTesting_002_DP.class.getName());

	@DataProvider(name = "JenkinsTesting_002_DP")
	public static Object[][] JenkinsTesting_002_DataProvider() {

		Object[][] obj = new Object[1][3];

		try {

			JenkinsTesting_002 object_Testing_002 = new JenkinsTesting_002();
			int row = object_Testing_002.getclassName("TestData.xlsx", "Login");
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
