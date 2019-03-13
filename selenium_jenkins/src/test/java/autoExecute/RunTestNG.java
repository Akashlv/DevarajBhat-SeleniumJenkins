package autoExecute;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;

public class RunTestNG {

	public static void main(String[] args) {
		
		String property = System.getProperty("user.dir");

		TestNG runner = new TestNG();

		List<String> suitefiles = new ArrayList<String>();

		suitefiles.add(property + "//testng.xml");

		runner.setTestSuites(suitefiles);

		runner.run();
	}

}
