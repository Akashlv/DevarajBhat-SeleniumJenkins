package pageObjects;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import utils.PropertyFile;

public class BaseClassForPageObjects {

	static Logger logger = Logger.getLogger(BaseClassForPageObjects.class.getName());

	PropertyFile propertyFile = new PropertyFile();

	public static WebDriver attDrv;

	@SuppressWarnings("deprecation")
	public static WebDriver setDriver(String browser) throws InterruptedException {

		DesiredCapabilities capabilities;
		String property = System.getProperty("user.dir");

		logger.info("Load drivers from system resources and launch the browser");

		if (browser.equalsIgnoreCase("Internet Explorer") || browser.equalsIgnoreCase("IE")) {

			System.setProperty("webdriver.ie.driver", ClassLoader.getSystemResource("IEDriverServer.exe").getFile());

			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("requireWindowFocus", true);

			attDrv = new InternetExplorerDriver(capabilities);

		} else if (browser.equalsIgnoreCase("Chrome") || browser.equalsIgnoreCase("Google Chrome")) {
			
			System.setProperty("webdriver.chrome.driver", property + "\\src\\test\\resources\\chromedriver.exe");

			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			attDrv = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("FireFox") || browser.equalsIgnoreCase("FF")) {

			System.setProperty("webdriver.gecko.driver", ClassLoader.getSystemResource("geckodriver.exe").getFile());
			capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			attDrv = new FirefoxDriver();

		} else {

			Reporter.log("Please enter valid value for parameter Browser", true);
		}

		attDrv.manage().window().maximize();
		return attDrv;
	}

	// Method to launch app
	public static void launchAttPortal(String url) {

		attDrv.get(url);
		attDrv.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// Close the driver
	public static void tearDown() {

		attDrv.close();
		logger.info("Closed Active Browser");

	}

	// Close the driver
	public static void tearDownNotification() {

		attDrv.close();
//		ViewXmlPopUpPage.identifyCloseButton_viewXmlPopUp().click();
		logger.info("Closed Active Browser");
	}

	// Quit the driver
	public static void quitBrowser() {

		attDrv.quit();
		logger.info("Closed All Browsers");
	}

	// Explicit wait
	public static void seleniumImplicitWait(int seconds) {

		attDrv.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	// Take screen shots
	public static void getscreenshot(String testCaseNumber) {
		try {

			String testDataFile = System.getProperty("user.dir").concat("\\screenshots\\");
			File scrFile = ((TakesScreenshot) attDrv).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(testDataFile + testCaseNumber + ".png"));

		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}

	}

	// Method to scroll until to view an element
	public static void scrollToViewAnElement(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) attDrv;
		js.executeScript("arguments[0].scrollIntoView();", element);

	}

	// Method to set customized profile to download in firefox
	public static DesiredCapabilities FirefoxDriverProfile(String downloadFilepath) throws Exception {

		// Changing default file downloading location path using the
		// FirefoxProfile.setpreference method.
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		FirefoxOptions options = new FirefoxOptions();// Added this line after upgrading selenium to 3.7
		DesiredCapabilities capabilities = DesiredCapabilities.firefox(); // Added this line after upgrading selenium to
																			// 3.7
		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("browser.download.dir", downloadFilepath);

		firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,text/xml,application/json");
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,text/xml,application/json");
		firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
		firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
		firefoxProfile.setPreference("browser.download.manager.useWindow", false);
		firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
		firefoxProfile.setPreference("browser.download.manager.closeWhenDone", false);

		options.setProfile(firefoxProfile);// Added this line after upgrading selenium to 3.7
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);// Added this line after upgrading selenium
																			// to 3.7
		return capabilities;
	}

	// Method to set customized profile to download in firefox
	public static DesiredCapabilities FirefoxDriverOptions(String downloadFilepath) throws Exception {

		FirefoxProfile firefoxProfile = new FirefoxProfile();
		FirefoxOptions options = new FirefoxOptions();// Added this line after upgrading selenium to 3.7
		DesiredCapabilities capabilities = DesiredCapabilities.firefox(); // Added this line after upgrading selenium to
																			// 3.7
		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("browser.download.dir", downloadFilepath);

		firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,text/xml,application/json");
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,text/xml,application/json");
		firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
		firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
		firefoxProfile.setPreference("browser.download.manager.useWindow", false);
		firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
		firefoxProfile.setPreference("browser.download.manager.closeWhenDone", false);

		options.setProfile(firefoxProfile);// Added this line after upgrading selenium to 3.7
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);// Added this line after upgrading selenium
																			// to 3.7
		return capabilities;

	}

	// method to get current window handle
	public static String getCurrentWindowHandle() {

		return attDrv.getWindowHandle();
	}

	// Method to switch driver window handle
	public static void switchWindowHandle(String windowHandle) {

		attDrv.switchTo().window(windowHandle);
	}

	// Method to switch driver to Current Window
	public static void switchToCurrentWindow() {

		for (String winHandle : attDrv.getWindowHandles()) {
			attDrv.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's
													// your newly opened window)
		}
	}

	// Method to get number of windows Opened
	public static int getCountOfWindowsOpened() {

		Set<String> totalwindows = attDrv.getWindowHandles();
		return totalwindows.size();
	}

	// method to get current window handle
	public static int getWindowsHandleSize() {

		return attDrv.getWindowHandles().size();
	}

	// Method to handle alert and retrieve alert text
	public static String getAlertText(String action) {

		String alertText = "";
		try {

			Alert alert = attDrv.switchTo().alert();
			alertText = alert.getText();
			logger.info("Alert text : " + alertText);

			if (action.equalsIgnoreCase("accept")) {

				alert.accept();
				logger.info("Pop Up accepted successfully");

			} else {

				alert.dismiss();
				logger.info("Pop Up dismissed successfully");
			}

		} catch (Exception e) {
			logger.info("No pop up exist");
		}

		return alertText;
	}

	// Customized wait methods
	public static void waitUntilGivenWebElementIsDisplayed(int count, WebElement element) {

		for (int i = 0; i < count; i++) {

			try {
				if (element.isDisplayed()) {
					Thread.sleep(1000);
					break;
				}
			} catch (Exception e) {
				continue;
			}

		}

	}

	// Method to parse date format
	public static String dateParser(String inputFrmt, String expFrmt, String dateString) throws ParseException {

		DateFormat dddStdDateFormat = new SimpleDateFormat(inputFrmt);
		SimpleDateFormat ddd_updateReq = new SimpleDateFormat(expFrmt);

		return ddd_updateReq.format(dddStdDateFormat.parse(dateString));
	}

	// Method to parse 12hr time format to 24hr time format
	public static String paresTime(String inputFrmt, String expFrmt, String time) throws ParseException {

		SimpleDateFormat _24hrSdf = new SimpleDateFormat(inputFrmt);
		SimpleDateFormat _12hrSdf = new SimpleDateFormat(expFrmt);

		Date tempparsed = _24hrSdf.parse(time);
		String dfdtParsed = _12hrSdf.format(tempparsed);

		return dfdtParsed;
	}

	// Method to mimic tab key
	public static void tabkey() {

		Actions actBuilder = new Actions(attDrv);
		actBuilder.sendKeys(Keys.TAB).perform();
	}

	// To get the Title
	public static String getTitle() {

		String title = attDrv.getTitle();

		return title;
	}
}
