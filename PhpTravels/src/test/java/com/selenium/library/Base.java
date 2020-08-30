package com.selenium.library;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.apache.log4j.Logger;

public class Base {
	final static Logger logger = Logger.getLogger(Base.class);

	public static WebDriver driver;
	public static LibraryStar lib;
    private String myBrowser;
    private String myDemoMode;
    
	@BeforeMethod // this method will run before every single test method
	public void set_up() {
		try {
			logger.info("Start a single test...");
			lib = new LibraryStar(driver);
			if(myDemoMode.toLowerCase().contains("on")) {
				lib.setDemoMode(true);
			}
			driver = lib.startBrowser(myBrowser);
		} catch (Exception e) {
			logger.error("Error: ", e);
			//assertTrue(false);
		}
	}

	@AfterMethod // this method will run after every single test OR after test
					// method fails
	public void tearDown(ITestResult result) {
		try {
			logger.info("End a single test...");
			if (ITestResult.FAILURE == result.getStatus()) {
				// capture screenshot here
				lib.screenCapture(result.getName());
			}
			if (driver != null) {
				driver.quit();
			}
			Thread.sleep(5 * 1000);
			// if(driver.getWindowHandles().size() > 0) {
			// driver.close(); // closing the browser
			// }
		} catch (Exception e) {
			logger.error("Error: ", e);
			//assertTrue(false);
		}
	}
	@BeforeClass     //this method will run only one time right before all test starts
	public void beforeAllTests() {
		try {
		logger.info("Starting all the Test...");
		PropertyManager properties = new PropertyManager("src/test/resources/config.properties");
		myBrowser = properties.readProperty("browserType");
		myDemoMode = properties.readProperty("demoMode");
		if(myDemoMode.toLowerCase().contains("on")) {
			logger.info("Demo mode is On");
		}else {
			logger.info("Demo mode is Off");
		}
	}catch (Exception e) {
		logger.error("Error: ", e);
		//assertTrue(false);
	}}

	@AfterClass // this method will run after all the tests are completed
	public void afterAllTests() {
		try {
			if (driver != null) {
				driver.quit();
			}
			logger.info("All the test is completed...");
		} catch (Exception e) {
			logger.error("Error: ", e);
			//assertTrue(false);
		}
	}
}