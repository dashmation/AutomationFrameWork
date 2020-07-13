package com.qa.amazonapp;

import java.util.List;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SelectADeliveryAddress extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private List<MobileElement> addresses;

	@AndroidFindBy(className = "")
	private List<MobileElement> deliverToThisAddress;

	public SelectADeliveryAddress(AppiumDriver<?> driver) {
		super(driver);
	}

}
