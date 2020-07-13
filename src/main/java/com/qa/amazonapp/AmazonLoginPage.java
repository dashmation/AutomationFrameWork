package com.qa.amazonapp;

import org.testng.Assert;
import org.testng.Reporter;

import com.qa.CommonMethods.CommonMobileMethods;
import com.qa.utility.CommonUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/*
 * This Class represents the page Object of AmazonLoginPage
 */

public class AmazonLoginPage extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private MobileElement selectEnglish;

	@AndroidFindBy(className = "")
	private MobileElement selectHindi;

	@AndroidFindBy(className = "")
	private MobileElement AlreadyACustomerThenSignIn;

	@AndroidFindBy(className = "")
	private MobileElement mobileNumberTextBox;

	@AndroidFindBy(className = "")
	private MobileElement continueButton;

	@AndroidFindBy(className = "")
	private MobileElement passwordTextBox;

	@AndroidFindBy(className = "")
	private MobileElement loginButton;

	public AmazonLoginPage(AppiumDriver<?> driver, LANGUAGE language) {
		super(driver);
		switch (language) {
		case ENGLISH:
			selectLanguage(language);
			break;
		case HINDI:
			selectLanguage(language);
			break;
		default:
			break;
		}
	}

	private AmazonLoginPage selectLanguage(LANGUAGE language) {
		try {
			switch (language) {
			case ENGLISH:
				clickOnElement(selectEnglish, "English");
				break;
			case HINDI:
				clickOnElement(selectHindi, "Hindi");
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
		return this;
	}

	private AmazonLoginPage selectAlreadyACustomerThenSignIn() {
		try {
			clickOnElement(AlreadyACustomerThenSignIn, "Already Customer");
		} catch (Exception e) {
		}
		return this;
	}

	private AmazonLoginPage enterUserNameAndClickNext() {
		try {
			sendText(mobileNumberTextBox, CommonUtils.getValue("username"));
			clickOnElement(continueButton, "Continue Button");
			Assert.assertTrue(isElementDisplayed(passwordTextBox), "No Password Page is displaying");
		} catch (Exception e) {
			Reporter.log("Unable to enterUserNameAndClickNext due to " + e.getMessage());
			Assert.fail();
		}
		return this;
	}

	private AmazonLoginPage enterPasswordAndClickLogin() {
		try {
			sendText(passwordTextBox, CommonUtils.getValue("password"));
			clickOnElement(loginButton, "login button");
			Assert.assertTrue(!isElementsDisplayed(passwordTextBox, loginButton), "Password Page is still displaying");
		} catch (Exception e) {
			Reporter.log("Unable to enterUserNameAndClickNext due to " + e.getMessage());
			Assert.fail();
		}
		return this;
	}

	public AmazonProductPage loginToAmazon() {
		selectAlreadyACustomerThenSignIn();
		enterUserNameAndClickNext();
		enterPasswordAndClickLogin();
		return new AmazonProductPage((AppiumDriver<?>) this.driver);
	}
}
