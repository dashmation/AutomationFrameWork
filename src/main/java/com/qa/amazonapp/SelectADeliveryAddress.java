package com.qa.amazonapp;

import java.util.List;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/*
 * This Class represents the page Object of SelectADeliveryAddress
 */

public class SelectADeliveryAddress extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private List<MobileElement> addresses;

	@AndroidFindBy(className = "")
	private List<MobileElement> addressesInName;

	@AndroidFindBy(className = "")
	private List<MobileElement> deliverToThisAddress;

	public SelectADeliveryAddress(AppiumDriver<?> driver) {
		super(driver);
	}

	public SelectADeliveryAddress selectAddressAndCotinue(String Name) {
		for (int counter = 0; counter < addresses.size(); counter++) {
			if (getText(addressesInName.get(counter)).equals(Name)) {
				clickOnElement(addresses.get(counter), getText(addresses.get(counter)));
				clickOnElement(deliverToThisAddress.get(counter), getText(deliverToThisAddress.get(counter)));
				break;
			}
		}
		return this;
	}

}
