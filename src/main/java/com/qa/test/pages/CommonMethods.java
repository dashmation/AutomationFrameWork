package com.qa.test.pages;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.automation.frameWork.baseTest.DriverTech;
import com.qa.test.utility.CommonUtils;

public class CommonMethods extends DriverTech {
	protected WebDriver driver;
	JavascriptExecutor javaScriptexe;
	WebDriverWait wait;

	public CommonMethods(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void sendText(WebElement textBox, String input) {
		checkPageIsReady();
		try {
			textBox.clear();
			textBox.sendKeys(input);
			Reporter.log("Entered " + input + " Successfully");
			System.out.println("Entered " + input + " Successfully");
		} catch (Exception e) {
			Reporter.log("Unable to enter due to " + e.getMessage());
			System.out.println("Unable to enter due to " + e.getMessage());
		}
	}

	public void clickOnElement(WebElement element, String NameOfTheElement) {
		checkPageIsReady();
		try {
			wait = new WebDriverWait(driver, Long.parseLong(CommonUtils.getValue("explicitelyWait")));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Reporter.log("Clicked On " + NameOfTheElement + " Successfully");
			System.out.println("Clicked On " + NameOfTheElement + " Successfully");
		} catch (Exception e) {
			Reporter.log("Unable To Click On" + NameOfTheElement + " Due To " + e.getMessage());
		}
	}

	public void checkPageIsReady() {
		try {
			javaScriptexe = (JavascriptExecutor) driver;
			for (int retry = 1; retry < 6; retry++) {
				boolean pageLoaded = (boolean) javaScriptexe.executeScript("return document.readyState==='complete'");
				if (pageLoaded) {
					break;
				}
			}
		} catch (Exception e) {
			Reporter.log("Unable to perform checkPageIsReady due to " + e.getMessage());
			System.out.println("Unable to perform checkPageIsReady due to " + e.getMessage());
		}
	}

	public void waitForTheWebElementToBeDisplayed(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Long.parseLong(CommonUtils.getValue("explicitelyWait")));
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			Reporter.log("Unable to perform waitForTheWebElementToBeDisplayed due to " + e.getMessage());
		}
	}

	public boolean isElementDisplayed(WebElement element) {
		boolean flag = false;
		try {
			waitForTheWebElementToBeDisplayed(element);
			if (element.isDisplayed())
				flag = true;
		} catch (Exception e) {
			Reporter.log("Element is not displaying");
		}
		return flag;
	}

	public boolean verifyCurrentURL(String expected) {
		boolean flag = false;
		try {
			if (this.driver.getCurrentUrl().contains(expected)) {
				flag = true;
				System.out.println("Currrent page URI contains " + expected);
			}
		} catch (Exception e) {
			Reporter.log("Unable to perform verifyCurrentURL due to " + e.getMessage());
			System.out.println("Unable to perform verifyCurrentURL due to " + e.getMessage());
		}
		return flag;
	}

	public String getText(WebElement element) {
		String textValue = "";
		try {
			waitForTheWebElementToBeDisplayed(element);
			textValue = element.getText();
		} catch (NoSuchElementException noSuchElement) {
			Reporter.log("Unable to get the text due to element not found as " + noSuchElement.getMessage(), true);
		} catch (Exception e) {
			Reporter.log("Unable to get the text due to " + e.getMessage(), true);
		}
		return textValue;
	}

	public void switchToFrame(WebElement element) {
		checkPageIsReady();
		try {
			driver.switchTo().frame(element);
			Reporter.log("Switch To Frame successfully", true);
		} catch (NoSuchElementException noSuchElement) {
			Reporter.log("Unable to switch To frame due to" + noSuchElement.getMessage(), true);
		} catch (Exception e) {
			Reporter.log("Unable to switch To frame due to" + e.getMessage(), true);
		}
	}

	public void takeScreenShotFor(String userDefindName) {
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src,
					new File(CommonUtils.getValue("screenshots") + "/" + userDefindName + getDate() + ".jpeg"));
		} catch (Exception e) {
			Reporter.log("Failed to take screenshot due to" + e.getMessage());
		}
	}

	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String outPut = dtf.format(now);
		return outPut;
	}

	public void scrollUpto(WebElement element) {
		try {
			javaScriptexe = (JavascriptExecutor) driver;
			javaScriptexe.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			Reporter.log("Unable to perform due to " + e.getMessage());
		}
	}

	public void clickUsingJSOn(WebElement element) {
		try {
			javaScriptexe = (JavascriptExecutor) driver;
			javaScriptexe.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			Reporter.log("Unable to click due to " + e.getMessage());
		}
	}
}
