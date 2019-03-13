package pageActions;

import org.apache.log4j.Logger;
import pageObjects.homeSearchPage;

public class homeSearchPageAction {
	
	static Logger logger = Logger.getLogger(homeSearchPageAction.class.getName());

	// Method to click loggOff button
	public static void clickLogOff() {

		homeSearchPage.identifyLogOffbutton().click();
		logger.info("Clicked on LogOff button");
	}

}
