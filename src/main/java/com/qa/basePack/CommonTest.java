package com.qa.basePack;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class CommonTest extends DriverTech{
	
	@BeforeSuite(alwaysRun = true)
	public void suiteStart() {
		System.out.println("#######################################Test Suite Started####################################");
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		if (autoType.toLowerCase().equals("web")) {
			launchApplicationWithWebDriverManager();
		} else if (autoType.toLowerCase().equals("windows")) {
			launchWindowApplication();
		} else if (autoType.toLowerCase().equals("android")) {
			launchAndroidApp();
		} else {
			Assert.fail("Failed to Start since invalid autoType given");
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			Reporter.log(browser.toUpperCase() + " closed Successfully");
			System.out.println(browser.toUpperCase() + " closed Successfully");
		} else if (windowSession != null) {
			windowSession.quit();
			Reporter.log(appName + " App closed Successfully");
			System.out.println(appName + " App closed Successfully");
		}
	}
	
	@BeforeSuite(alwaysRun = true)
	public void suiteEnd() {
		System.out.println("#######################################Test Suite Completed####################################");
	}
}
