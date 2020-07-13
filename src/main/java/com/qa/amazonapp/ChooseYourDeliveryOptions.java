package com.qa.amazonapp;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/*
 * This Class represents the page Object of ChooseYourDeliveryOptions
 */

public class ChooseYourDeliveryOptions extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private MobileElement continueButton;

	public ChooseYourDeliveryOptions(AppiumDriver<?> driver) {
		super(driver);
	}

	public ChooseYourDeliveryOptions clickToContinue() {
		scrollToView(continueButton);
		clickOnElement(continueButton, getText(continueButton));
		return this;
	}

}
