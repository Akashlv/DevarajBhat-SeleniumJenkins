package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class homeSearchPage extends BaseClassForPageObjects {

	
	static By locatorForLogOffbutton = By.xpath("//*[@id='logoff']/a");
	// LogOff button
	public static WebElement identifyLogOffbutton() {

		return attDrv.findElement(locatorForLogOffbutton);
	}
	
	static By locatorForSynchronossLogo = By.id("cust-logo");
	// Synchronoss Logo
	public static WebElement identifySynchronossLogo() {

		return attDrv.findElement(locatorForSynchronossLogo);
	}
	
	static By locatorForProjectPortalLogo = By.xpath("//*[@id='header']/table/tbody/tr/td[2]/img");
	// Project Portal Logo
	public static WebElement identifyProjectPortalLogo() {

		return attDrv.findElement(locatorForProjectPortalLogo);
	}
}
