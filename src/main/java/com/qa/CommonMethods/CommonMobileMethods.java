package com.qa.CommonMethods;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.qa.basePack.DriverTech;
import com.qa.utility.CommonUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CommonMobileMethods extends DriverTech {
	protected AppiumDriver<?> driver;
	JavascriptExecutor javaScriptexe;
	WebDriverWait wait;
	TouchActions performTouch;

	public final long fluentWaitPoolingTime = Long.valueOf(CommonUtils.getValue("fluentWaitInterval"));
	public final long retry = Long.valueOf(CommonUtils.getValue("retry"));

	@AndroidFindBy(className = "UIAKeyboard")
	protected AndroidElement keyboard;

	public enum LANGUAGE {
		ENGLISH, HINDI;
	}

	public enum DIRECTION {
		UP, DOWN, RIGHT, LEFT;
	}

	public CommonMobileMethods(AppiumDriver<?> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public void sendText(MobileElement textBox, String input) {
		fluentWait(textBox);
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

	public void clickOnElement(MobileElement element, String NameOfTheElement) {
		fluentWait(element);
		try {
			performTouch = new TouchActions(this.driver);
			performTouch.singleTap(element);
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

	public void waitForTheMobileElementToBeDisplayed(MobileElement element) {
		try {
			wait = new WebDriverWait(driver, Long.parseLong(CommonUtils.getValue("explicitelyWait")));
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			Reporter.log("Unable to perform waitForTheMobileElementToBeDisplayed due to " + e.getMessage());
		}
	}

	public boolean isElementDisplayed(MobileElement element) {
		boolean flag = false;
		try {
			waitForTheMobileElementToBeDisplayed(element);
			if (element.isDisplayed())
				flag = true;
		} catch (Exception e) {
			Reporter.log("Element is not displaying");
		}
		return flag;
	}

	public boolean isElementsDisplayed(MobileElement... elements) {
		boolean finalflag = false;
		List<Boolean> flagList = new ArrayList<Boolean>();
		for (int counter = 0; counter < elements.length; counter++) {
			flagList.add(isElementDisplayed(elements[counter]) ? true : false);
		}
		finalflag = flagList.contains(false) ? false : true;
		return finalflag;
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

	public String getText(MobileElement element) {
		String textValue = "";
		try {
			waitForTheMobileElementToBeDisplayed(element);
			textValue = element.getText();
		} catch (NoSuchElementException noSuchElement) {
			Reporter.log("Unable to get the text due to element not found as " + noSuchElement.getMessage(), true);
		} catch (Exception e) {
			Reporter.log("Unable to get the text due to " + e.getMessage(), true);
		}
		return textValue;
	}

	public void switchToFrame(MobileElement element) {
		fluentWait(element);
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

	public void scroll(DIRECTION direction) {
		try {
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			switch (direction) {
			case UP:
				scrollObject.put("direction", "up");
				break;
			case DOWN:
				scrollObject.put("direction", "down");
				break;
			case RIGHT:
				scrollObject.put("direction", "right");
				break;
			case LEFT:
				scrollObject.put("direction", "left");
				break;
			default:
				break;
			}
			javaScriptexe = (JavascriptExecutor) driver;
			javaScriptexe.executeScript("mobile: scroll", scrollObject);
		} catch (Exception e) {
			Reporter.log("Unable to perform due to " + e.getMessage());
		}
	}

	public void clickUsingJSOn(MobileElement element) {
		try {
			javaScriptexe = (JavascriptExecutor) driver;
			javaScriptexe.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			Reporter.log("Unable to click due to " + e.getMessage());
		}
	}

	public void fluentWait(MobileElement element) {
		try {
			int counter = 0;
			while (counter <= retry) {
				if (isElementDisplayed(element)) {
					break;
				} else {
					Thread.sleep(fluentWaitPoolingTime);
					counter++;
				}
				if (counter == 10) {
					Reporter.log("No Expected MobileElement found");
				}
			}
		} catch (Exception e) {
		}
	}

	public void scrollToView(MobileElement element) {
		performTouch = new TouchActions(this.driver);
		performTouch.scroll(element, element.getLocation().getX(), element.getLocation().getY());
	}
}
