package com.qa.amazonapp;

import java.util.ArrayList;
import java.util.List;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class AmazonCheckOutPage extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private MobileElement product_Description;

	@AndroidFindBy(className = "")
	private MobileElement product_Price;

	@AndroidFindBy(className = "")
	private MobileElement placeYourOrderAndPay;

	public AmazonCheckOutPage(AppiumDriver<?> driver) {
		super(driver);
	}

	public List<String> getDetailsOfCheckOutItem() {
		List<String> details = new ArrayList<>();
		details.add(getText(product_Price));
		details.add(getText(product_Description));
		return details;
	}

	public AmazonCheckOutPage clickToOrder() {
		clickOnElement(placeYourOrderAndPay, "Place Your Order And Pay");
		return this;
	}
}
