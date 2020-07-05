package com.automation.frameWork.baseTest;

import java.net.URL;
import java.util.InputMismatchException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.qa.test.utility.CommonUtils;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverTech {
	public WebDriver driver = null;
	public WindowsDriver<WindowsElement> windowSession = null;
	final String uri = CommonUtils.getValue("url");
	final String browser = CommonUtils.getValue("browser").toLowerCase();
	final String appName = CommonUtils.getValue("appName");
	final String autoType = CommonUtils.getValue("automationOn");
	public ChromeOptions options;

	public void launchApplication() {
		try {
			String directory = System.getProperty("directory");
			switch (browser.toLowerCase()) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", directory);
				driver = new ChromeDriver();
				Reporter.log(browser + "Launched Successfully");
				break;
			case "mozila":
				System.setProperty("webdriver.gecko.driver", directory);
				driver = new FirefoxDriver();
				Reporter.log(browser + "Launched Successfully");
				break;
			case "ie":
				System.setProperty("webdriver.ie.driver", directory);
				driver = new InternetExplorerDriver();
				Reporter.log(browser + "Launched Successfully");
				break;
			default:
				throw new InputMismatchException("Please enter valid input");
			}
			driver.manage().window().maximize();
			driver.get(uri);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void launchApplicationWithWebDriverManager() {
		try {
			switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				Reporter.log(browser + " Launched Successfully");
				System.out.println(browser.toUpperCase() + " Launched Successfully");
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				Reporter.log(browser + " Launched Successfully");
				System.out.println(browser.toUpperCase() + " Launched Successfully");
				break;
			case "ie":
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				Reporter.log(browser + " Launched Successfully");
				System.out.println(browser.toUpperCase() + " Launched Successfully");
				break;
			default:
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				driver = new ChromeDriver(options);
				Reporter.log("Headless browser launched Successfully");
				break;
			}
			driver.manage().window().maximize();
			if (!uri.isEmpty()) {
				driver.get(uri);
			}
			clearCookies();
		} catch (Exception e) {
			Reporter.log("Unable to launch the browser due to" + e.getMessage());
			Assert.fail();
		}
	}

	public void launchWindowApplication() {
		DesiredCapabilities desCap = new DesiredCapabilities();
		desCap.setCapability("app", CommonUtils.getValue("app"));
		desCap.setCapability("platformName", CommonUtils.getValue("platformName"));
		desCap.setCapability("deviceName", CommonUtils.getValue("deviceName"));
		try {
			windowSession = new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723"), desCap);
			Reporter.log(CommonUtils.getValue("appName") + " has been launched successfully");
			System.out.println(appName + " has been launched successfully");
		} catch (Exception e) {
			Reporter.log("Unable to perform launchWindowApplication due to " + e.getMessage());
			System.out.println("Unable to perform launchWindowApplication due to " + e.getMessage());
		}
	}

	protected void clearCookies() {
		driver.manage().deleteAllCookies();
		Reporter.log("All cookies cleared");
	}

	@BeforeMethod
	public void setUp() {
		if (autoType.equalsIgnoreCase("web")) {
			launchApplicationWithWebDriverManager();
		} else if (autoType.equals("windows")) {
			launchWindowApplication();
		} else {
			Assert.fail("Failed to Start since invalid autoType given");
		}
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.close();
			Reporter.log(browser.toUpperCase() + " Closed Successfully");
			System.out.println(browser.toUpperCase() + " Closed Successfully");
		} else if (windowSession != null) {
			windowSession.quit();
			Reporter.log(appName + " App Closed Successfully");
			System.out.println(appName + " App Closed Successfully");
		}
	}
}
