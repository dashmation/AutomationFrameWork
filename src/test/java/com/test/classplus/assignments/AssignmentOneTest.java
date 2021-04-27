package com.test.classplus.assignments;

import org.testng.annotations.Test;

public class AssignmentOneTest extends AssignmentOneBAU{

	@Test
	public void Assignment1() {
		goToHttpswwwamazonin();
		searchForiPhoneXR64GBYellowInAmazon();
		selectTheMatchingPhoneOnceListApears();
		getThePriceOfTheSelectediPhoneInAmazon();
		nowGoToHttpswwwflipkartcom();
		searchForiPhoneXR64GBYellowInFlipkart();
		getThePriceOfTheSelectediPhoneInFlipkart();
		compareThePriceOnBothTheWebsiteAndPrintTheLesserInConsole();
	}
}
