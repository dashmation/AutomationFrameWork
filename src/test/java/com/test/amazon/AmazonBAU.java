package com.test.amazon;

import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.Reporter;

import com.qa.CommonMethods.CommonMobileMethods.LANGUAGE;
import com.qa.amazonapp.AmazonCheckOutPage;
import com.qa.amazonapp.AmazonLoginPage;
import com.qa.amazonapp.AmazonProductPage;
import com.qa.amazonapp.ChooseYourDeliveryOptions;
import com.qa.amazonapp.SelectADeliveryAddress;
import com.qa.amazonapp.SelectPaymentMethod;
import com.qa.basePack.DriverTech;

import io.appium.java_client.AppiumDriver;

public class AmazonBAU extends DriverTech {
	AmazonLoginPage amazonLoginPage;
	AmazonProductPage amazonProductPage;
	SelectADeliveryAddress selectADeliveryAddress;
	ChooseYourDeliveryOptions chooseYourDeliveryOptions;
	SelectPaymentMethod selectPaymentMethod;
	AmazonCheckOutPage amazonCheckOutPage;

	public void LoginToAmazon() {
		amazonLoginPage = new AmazonLoginPage((AppiumDriver<?>) driver, LANGUAGE.ENGLISH);
		amazonLoginPage.loginToAmazon();
		Reporter.log("Logged in to Application successfully");
	}

	public void searchAndSelectToCheckOutFor(String item) {
		amazonProductPage = new AmazonProductPage((AppiumDriver<?>) driver);
		amazonProductPage.searchForItem(item);
		int randomProduct = new Random().nextInt(amazonProductPage.totalProduct());
		amazonProductPage.getDetailsOfItem(randomProduct);
		amazonProductPage.scrollToDesiredProductAndClick(randomProduct);
		amazonProductPage.clickToBuy();
		Reporter.log("Search And Select " + item + " successfully");
	}

	public void selectDeliveryAddress(String name) {
		selectADeliveryAddress = new SelectADeliveryAddress((AppiumDriver<?>) driver);
		selectADeliveryAddress.selectAddressAndCotinue(name);
		Reporter.log("Selected " + name + " address successfully");
	}

	public void chooseYourDeliveryOptions() {
		chooseYourDeliveryOptions = new ChooseYourDeliveryOptions((AppiumDriver<?>) driver);
		chooseYourDeliveryOptions.clickToContinue();
		Reporter.log("Choosen Delivery Options Successfully");
	}

	public void checkOutUsingPayOnDelivery() {
		selectPaymentMethod = new SelectPaymentMethod((AppiumDriver<?>) driver);
		selectPaymentMethod.clickPayOnDeliveryAndContinue();
		Reporter.log("Selected Pay On Delivery as Payment Option Successfully");
	}

	public void verifyProductAndCompleteOrder() {
		amazonCheckOutPage = new AmazonCheckOutPage((AppiumDriver<?>) driver);
		List<String> getChecOutProductDetails = amazonCheckOutPage.getDetailsOfCheckOutItem();
		amazonProductPage = new AmazonProductPage((AppiumDriver<?>) driver);
		Assert.assertTrue(getChecOutProductDetails.equals(amazonProductPage.getProductDetails()),
				"Info for both product and checkout page are not matching");
		Reporter.log("Info for both product and checkout page are matching as expected");
		amazonCheckOutPage.clickToOrder();
		Reporter.log("Order Place Successfully");
	}
}
