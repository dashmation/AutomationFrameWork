package com.qa.amazonapp;

import java.util.ArrayList;
import java.util.List;

import com.qa.CommonMethods.CommonMobileMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

/*
 * This Class represents the page Object of AmazonProductPage
 */

public class AmazonProductPage extends CommonMobileMethods {

	@AndroidFindBy(className = "")
	private MobileElement searchBox;

	@AndroidFindBy(className = "")
	private List<MobileElement> product;

	@AndroidFindBy(className = "")
	private List<MobileElement> product_Name;

	@AndroidFindBy(className = "")
	private List<MobileElement> product_Price;

	@AndroidFindBy(className = "")
	private List<MobileElement> product_Description;

	@AndroidFindBy(className = "")
	private MobileElement buyNow;

	@AndroidFindBy(className = "")
	private MobileElement addToCart;

	@AndroidFindBy(className = "")
	private MobileElement langSettingPopUp_English;

	@AndroidFindBy(className = "")
	private MobileElement langSettingPopUp_SaveChanges;

	private List<String> productDetails;

	public List<String> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<String> productDetails) {
		this.productDetails = productDetails;
	}

	public AmazonProductPage(AppiumDriver<?> driver) {
		super(driver);
		handleLanguagePOPUP();
	}

	private AmazonProductPage handleLanguagePOPUP() {
		if (isElementDisplayed(langSettingPopUp_English)) {
			clickOnElement(langSettingPopUp_English, getText(langSettingPopUp_English));
			clickOnElement(langSettingPopUp_SaveChanges, getText(langSettingPopUp_SaveChanges));
		}
		return this;
	}

	public AmazonProductPage searchForItem(String item) {
		if (driver instanceof AndroidDriver) {
			sendText(searchBox, item + AndroidKey.ENTER);
		} else if (driver instanceof IOSDriver) {
			sendText(searchBox, item);
		}
		return this;
	}

	public AmazonProductPage getDetailsOfItem(int index) {
		List<String> details = new ArrayList<>();
		details.add(getText(product_Price.get(index)));
		details.add(getText(product_Description.get(index)));
		setProductDetails(details);
		return this;
	}

	public AmazonProductPage scrollToDesiredProductAndClick(int index) {
		scrollToView(product.get(index));
		clickOnElement(product.get(index), getText(product.get(index)));
		return this;
	}

	public AmazonProductPage clickToBuy() {
		scrollToView(buyNow);
		clickOnElement(buyNow, getText(buyNow));
		return this;
	}

	public AmazonProductPage addToCart() {
		scrollToView(addToCart);
		clickOnElement(addToCart, getText(addToCart));
		return this;
	}

	public int totalProduct() {
		return product.size();
	}

}
