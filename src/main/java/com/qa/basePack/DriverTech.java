package com.qa.basePack;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.InputMismatchException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;

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
	final String app = CommonUtils.getValue("app");
	final String deviceName = CommonUtils.getValue("deviceName");
	final String udid = CommonUtils.getValue("udid");
	final String AndroiddeviceName = CommonUtils.getValue("AndroiddeviceName");
	final String platformVersion = CommonUtils.getValue("platformVersion");
	final String appPackage = CommonUtils.getValue("appPackage");
	final String appActivity = CommonUtils.getValue("appActivity");
	String screenshotPath = null;
	boolean flag = false;
	public ChromeOptions options;
	
	public WebDriver getDriver() {
		return driver;
	}

	public void launchApplication() {
		try {
			String directory = System.getProperty("directory");
			if (browser.toLowerCase().equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", directory);
				driver = new ChromeDriver();
				Reporter.log(browser + "Launched Successfully");

			} else if (browser.toLowerCase().equals("mozila")) {
				System.setProperty("webdriver.gecko.driver", directory);
				driver = new FirefoxDriver();
				Reporter.log(browser + "Launched Successfully");

			} else if (browser.toLowerCase().equals("ie")) {
				System.setProperty("webdriver.ie.driver", directory);
				driver = new InternetExplorerDriver();
				Reporter.log(browser + "Launched Successfully");

			} else {
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
			if (browser.toLowerCase().equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				Reporter.log(browser + "Launched Successfully");

			} else if (browser.toLowerCase().equals("mozila")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				Reporter.log(browser + "Launched Successfully");

			} else if (browser.toLowerCase().equals("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				Reporter.log(browser + "Launched Successfully");

			} else {
				throw new InputMismatchException("Please enter valid input");
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
		desCap.setCapability("app", app);
		desCap.setCapability("platformName", Platform.WINDOWS);
		desCap.setCapability("deviceName", deviceName);
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
		desCap.setCapability(MobileCapabilityType.UDID, udid);
		desCap.setCapability(MobileCapabilityType.NO_RESET, false);
		desCap.setCapability("deviceName", AndroiddeviceName);
		desCap.setCapability("platformVersion", platformVersion);
		desCap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
		desCap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
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
		getDriver().manage().deleteAllCookies();
		Reporter.log("All cookies cleared");
	}

	public void addCookie(String name, String value) {
		Cookie ck = new Cookie(name, value);
		getDriver().manage().addCookie(ck);
	}

	protected String getCookieByName(String key) {
		return getDriver().manage().getCookieNamed(key).toString();
	}

	protected String getScreenShot(String userDefindName) {
		String path = "";
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		File Dest = new File("./" + screenshotPath + "/" + userDefindName + getDate() + ".jpg");
		try {
			FileUtils.copyFile(src, Dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		path = Dest.getAbsolutePath();
		return path;
	}

	protected String takeScreenShotFor(String userDefindName) {
		String fileString = "";
		try {
			if (flag == true) {
				byte[] fileContent = FileUtils.readFileToByteArray(new File(getScreenShot(userDefindName)));
				String encodedString = Base64.getEncoder().encodeToString(fileContent);
				fileString = "data:image/jpg;base64," + encodedString;
			} else {
				TakesScreenshot newScreen = (TakesScreenshot) this.driver;
				String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
				fileString = "data:image/jpg;base64, " + scnShot;
			}
		} catch (Exception e) {
			Reporter.log("Failed to take screenshot due to" + e.getMessage(), true);
			e.printStackTrace();
		}
		System.out.println(fileString);
		return fileString;
	}

	protected String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String outPut = dtf.format(now);
		return outPut;
	}

	public enum DATE_FORMATS {
		yyyyMMdd, yyyyFSlASHMMFSlASHdd, ddHYPENMMHYPENyyyy, yyyyHYPENMMHYPENdd, ddFSlASHMMFSlASHyyyy,
		MMFSlASHddFSlASHyyyy;
	}

	protected String getDate(DATE_FORMATS DATE_FORMATS) {
		DateTimeFormatter dtf = null;
		switch (DATE_FORMATS) {
			case yyyyMMdd:
				dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
				break;
			case yyyyFSlASHMMFSlASHdd:
				dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				break;
			case ddHYPENMMHYPENyyyy:
				dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				break;
			case yyyyHYPENMMHYPENdd:
				dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				break;
			case ddFSlASHMMFSlASHyyyy:
				dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				break;
			case MMFSlASHddFSlASHyyyy:
				dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				break;
			default:
				break;
		}
		LocalDateTime now = LocalDateTime.now();
		String outPut = dtf.format(now);
		System.out.println(outPut);
		return outPut;
	}

	protected String getDate(DATE_FORMATS DATE_FORMATS, long minusdays) {
		DateTimeFormatter dtf = null;
		switch (DATE_FORMATS) {
			case yyyyMMdd:
				dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
				break;
			case yyyyFSlASHMMFSlASHdd:
				dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				break;
			case ddHYPENMMHYPENyyyy:
				dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				break;
			case yyyyHYPENMMHYPENdd:
				dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				break;
			case ddFSlASHMMFSlASHyyyy:
				dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				break;
			case MMFSlASHddFSlASHyyyy:
				dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				break;
			default:
				break;
		}
		LocalDateTime now = LocalDateTime.now().minusDays(minusdays);
		String outPut = dtf.format(now);
		System.out.println(outPut);
		return outPut;
	}

	public String getPropertyValueBasedOn(String key) {
		String value = System.getProperty(key);
		return value;
	}

}