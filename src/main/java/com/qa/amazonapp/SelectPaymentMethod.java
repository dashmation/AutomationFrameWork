package com.qa.amazonapp;

import java.util.List;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SelectPaymentMethod extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private List<MobileElement> creditAndDebitCards;

	@AndroidFindBy(className = "")
	private List<MobileElement> moreWaysToPay;

	@AndroidFindBy(className = "")
	private MobileElement continueButton;

	public SelectPaymentMethod(AppiumDriver<?> driver) {
		super(driver);
	}

}
