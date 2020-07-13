/**
 * 
 */
package com.test.TestScript;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qa.basePack.DriverTech;
import com.qa.pages.MyStorePage;
import com.qa.utility.Groups;

/**
 * This Class is to Contains Test Methods for Test Cases
 *
 */
public class MyStoreTest extends DriverTech {
	/*
	 * 
	 */
	@Test(enabled = true, groups = { Groups.AIRTELX })
	public void verifyUseCaseOne() {
		Reporter.log("Go to Popular items section");
		Reporter.log("Check items with the lowest price and add into the Cart");
		Reporter.log("Now browse to your Cart and take a screenshot of the product that got added to your Cart");
		Reporter.log("Verify that product is Successfully added.");
		Reporter.log("=================Actual=====================");
		MyStorePage myStorePage = new MyStorePage(this.driver);
		myStorePage.clickOnpopularItemSection().verifyLowestValueProductSuccessFullyAdded();
	}

	@Test(enabled = true, groups = { Groups.AIRTELX })
	public void verifyUseCaseTwo() {
		Reporter.log("Go to Popular items section");
		Reporter.log(
				"Check for the number of items which have a discount if available and also verify that the final price after discount is correct or not");
		Reporter.log("=================Actual=====================");
		MyStorePage myStorePage = new MyStorePage(this.driver);
		myStorePage.verifyTheNumberOfItemsHavingDiscounts();
	}
}
