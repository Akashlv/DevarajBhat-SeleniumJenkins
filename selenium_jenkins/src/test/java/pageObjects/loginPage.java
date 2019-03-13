package pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class loginPage extends BaseClassForPageObjects {

	static Logger logger = Logger.getLogger("PortalLoginPage");

	static WebElement element;

	// User Name text box
	static By locatorForUserNameTextBox = By.name("j_username");

	public static WebElement identifyUserNameTextBox() {

		return attDrv.findElement(locatorForUserNameTextBox);
	}

	// Password Text box
	static By locatorForPasswordTextBox = By.name("j_password");

	public static WebElement identifyPasswordTextBox() {

		return attDrv.findElement(locatorForPasswordTextBox);
	}

	// Login Button
	static By locatorloginButton = By.name("login");

	public static WebElement identifyLoginButton() {

		return attDrv.findElement(locatorloginButton);
	}
}
