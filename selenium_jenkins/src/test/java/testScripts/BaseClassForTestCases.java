package testScripts;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import utils.ExcelUtility;
import utils.PropertyFile;

public class BaseClassForTestCases {

	Logger logger = Logger.getLogger(BaseClassForTestCases.class.getName());

	protected String[] supportedBrowsers = { "FireFox" };
	protected static Properties appPropertyFile;
	protected String applicationURL = "";
	public static String userName;
	public static String passWord;

	int row;

	// Instance variables
	protected PropertyFile propertyFile = new PropertyFile();

	// Method to fetch return excel data sheet row number for the matching test case
	public int getclassName(String excelDataWorkBookName, String userStory) {

		try {
			ExcelUtility.excelInitialize(excelDataWorkBookName, userStory);
			Thread.sleep(3000);
			String sTestCaseName = this.toString();
			row = ExcelUtility.getRowContains(sTestCaseName, 0);

			System.out.println("ROW :" + row);

		} catch (IOException e) {
			e.printStackTrace();
			Reporter.log("unable to intialize excecl file" + e.getMessage());
			logger.info("unable to intialize excecl file" + e.getMessage());

		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info("Exception for thread sleep" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception while reading from excel: " + e.getMessage());
		}

		return row;
	}

	public void setAppCredentials(String testEnv) {

		try {

			if (testEnv.equalsIgnoreCase("QA")) {

				appPropertyFile = propertyFile.readProperFile("\\src\\test\\resources\\qaapplication.properties");
				logger.info("Successfully set QA Environement application property file path");

				applicationURL = appPropertyFile.getProperty("vfoUrl");
				logger.info("VFO url is : " + applicationURL);
				
				// AUT Sent User ASR
				userName = appPropertyFile.getProperty("userName");
				passWord = appPropertyFile.getProperty("passWord");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
