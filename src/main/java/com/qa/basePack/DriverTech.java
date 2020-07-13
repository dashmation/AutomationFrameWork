package com.qa.basePack;

import java.net.URL;
import java.util.InputMismatchException;

import org.openqa.selenium.Platform;
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

import com.qa.utility.CommonUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
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
			Reporter.log("Unable to perform launch Application due to " + e.getMessage());
			System.out.println("Unable to perform launch Application due to " + e.getMessage());
		}
	}

	public void launchAndroidApp() {
		new DesiredCapabilities();
		DesiredCapabilities desCap = DesiredCapabilities.android();
		desCap.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
		desCap.setCapability(MobileCapabilityType.UDID, CommonUtils.getValue("udid"));
		desCap.setCapability(MobileCapabilityType.NO_RESET, false);
		desCap.setCapability("deviceName", CommonUtils.getValue("deviceName"));
		desCap.setCapability("platformVersion", CommonUtils.getValue("platformVersion"));
		desCap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, CommonUtils.getValue("appPackage"));
		desCap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, CommonUtils.getValue("appActivity"));
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), desCap);
			Reporter.log(CommonUtils.getValue("appName") + " has been launched successfully");
			System.out.println(appName + " has been launched successfully");
		} catch (Exception e) {
			Reporter.log("Unable to perform launch Application due to " + e.getMessage());
			System.out.println("Unable to perform launch Application due to " + e.getMessage());
		}
	}

	protected void clearCookies() {
		driver.manage().deleteAllCookies();
		Reporter.log("All cookies cleared");
	}

	@BeforeMethod
	public void setUp() {
		switch (autoType.toLowerCase()) {
		case "web":
			launchApplicationWithWebDriverManager();
			break;
		case "windows":
			launchWindowApplication();
			break;
		case "android":
			launchAndroidApp();
			break;
		default:
			Assert.fail("Failed to Start since invalid autoType given");
			break;
		}
	}

	@AfterMethod
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
}