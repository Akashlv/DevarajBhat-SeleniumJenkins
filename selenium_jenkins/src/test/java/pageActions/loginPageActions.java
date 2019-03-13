package pageActions;

import org.testng.Reporter;
import pageObjects.loginPage;
import pageObjects.BaseClassForPageObjects;

public class loginPageActions {

	public static void loginToApplication(String browser, String url, String userName, String Password) throws InterruptedException {

		BaseClassForPageObjects.setDriver(browser);
		Reporter.log("Browser is : " + browser);
		
		BaseClassForPageObjects.launchAttPortal(url);
		Reporter.log("URL is : " + url);
		
		loginPage.identifyUserNameTextBox().sendKeys(userName);
		Reporter.log("User Name is : " + userName);

		loginPage.identifyPasswordTextBox().sendKeys(Password);
		Reporter.log("Password is : " + Password);

		loginPage.identifyLoginButton().click();
		Reporter.log("Clicked on login button");
	}
}
