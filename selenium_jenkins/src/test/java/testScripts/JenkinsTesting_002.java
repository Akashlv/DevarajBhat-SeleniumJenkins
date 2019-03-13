package testScripts;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import dataProviders.JenkinsTesting_002_DP;
import pageActions.homeSearchPageAction;
import pageActions.loginPageActions;
import pageObjects.BaseClassForPageObjects;
import pageObjects.homeSearchPage;

public class JenkinsTesting_002 extends BaseClassForTestCases {

	Logger logger = Logger.getLogger(JenkinsTesting_002.class.getName());

	boolean actualResultFlag = false;

	@Test(dataProvider = "JenkinsTesting_002_DP", dataProviderClass = JenkinsTesting_002_DP.class)
	public void verifySychronossLogoInHomeSearchPage(String testEnv, String browser, String ExpectedCondition) {

		try {

			if (browser.equalsIgnoreCase("All")) {

				for (int i = 0; i < supportedBrowsers.length; i++) {
					browser = supportedBrowsers[i];
					testcaseJenkinsTesting_002(testEnv, browser, ExpectedCondition);
				}

			} else {

				testcaseJenkinsTesting_002(testEnv, browser, ExpectedCondition);
			}

		} catch (Exception e) {
			Assert.fail();
			Reporter.log(e.getMessage(), true);
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	public void testcaseJenkinsTesting_002(String testEnv, String browser, String ExpectedCondition) {

		try {

			setAppCredentials(testEnv);

			loginPageActions.loginToApplication(browser, applicationURL, userName, passWord);
			Reporter.log("Successfully logged into VFO");
			
			Assert.assertTrue(homeSearchPage.identifySynchronossLogo().isDisplayed());
			Reporter.log("Synchronoss Logo is displaying");

		} catch (Exception e) {
			actualResultFlag = false;
			BaseClassForPageObjects.getscreenshot("testcaseJenkinsTesting_002_Fail");
			Reporter.log(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
			Assert.assertEquals(actualResultFlag, true);

		} finally {
			
			homeSearchPageAction.clickLogOff();
			Reporter.log("Clicked on LogOff button");

			BaseClassForPageObjects.quitBrowser();
			Reporter.log("Quit Browser");
		}
	}

	@AfterMethod
	public void captureScreenShotIffailed(ITestResult testResult) {

		if (testResult.getStatus() == ITestResult.FAILURE) {
			try {
				BaseClassForPageObjects.getscreenshot(testResult.getTestName());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage());
			}
		}
	}
}