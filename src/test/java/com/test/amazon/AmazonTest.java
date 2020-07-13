package com.test.amazon;

import org.testng.annotations.Test;

import com.qa.utility.Groups;

public class AmazonTest extends AmazonBAU {

	@Test(enabled = true, groups = { Groups.AMAZON })
	public void buying65InchTVFromAmazon() {
		LoginToAmazon();
		searchAndSelectToCheckOutFor("65-inch TV");
		selectDeliveryAddress("Subhrajyoti Dash");
		chooseYourDeliveryOptions();
		checkOutUsingPayOnDelivery();
		verifyProductAndCompleteOrder();
	}
}
