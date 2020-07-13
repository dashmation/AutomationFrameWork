package com.qa.amazonapp;

import java.util.List;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/*
 * This Class represents the page Object of SelectPaymentMethod
 */

public class SelectPaymentMethod extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private List<MobileElement> creditAndDebitCards;

	@AndroidFindBy(className = "")
	private List<MobileElement> moreWaysToPay;

	@AndroidFindBy(className = "")
	private MobileElement payOnDelivery;

	@AndroidFindBy(className = "")
	private MobileElement continueButton;

	public SelectPaymentMethod(AppiumDriver<?> driver) {
		super(driver);
	}

	public SelectPaymentMethod clickPayOnDeliveryAndContinue() {
		scrollToView(payOnDelivery);
		clickOnElement(payOnDelivery, getText(payOnDelivery));
		clickOnElement(continueButton, getText(continueButton));
		return this;
	}

}
