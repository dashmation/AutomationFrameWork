package com.qa.amazonapp;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class AmazonProductPage extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private MobileElement searchBox;

	@AndroidFindBy(className = "")
	private MobileElement product;

	@AndroidFindBy(className = "")
	private MobileElement product_Name;

	@AndroidFindBy(className = "")
	private MobileElement product_Price;

	@AndroidFindBy(className = "")
	private MobileElement product_Description;

	@AndroidFindBy(className = "")
	private MobileElement buyNow;

	@AndroidFindBy(className = "")
	private MobileElement addToCart;

	@AndroidFindBy(className = "")
	private MobileElement langSettingPopUp_English;

	@AndroidFindBy(className = "")
	private MobileElement langSettingPopUp_SaveChanges;

	public AmazonProductPage(AppiumDriver<?> driver) {
		super(driver);
	}

	public AmazonProductPage searchForItem(String item) {
		sendText(searchBox, item + Keys.ENTER);
		return this;
	}

	public List<String> getDetailsOfItem(String item) {
		List<String> details = new ArrayList<>();
		details.add(getText(product_Name));
		details.add(getText(product_Price));
		details.add(getText(product_Description));
		return details;
	}

}
