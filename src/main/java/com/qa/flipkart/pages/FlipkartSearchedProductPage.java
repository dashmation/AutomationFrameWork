package com.qa.flipkart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.CommonMethods.CommonMethods;

public class FlipkartSearchedProductPage extends CommonMethods {

	public FlipkartSearchedProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBox;
	
	
	public FlipkartSearchedProductPage getPriceFor() {
		return this;
	}
	
	public FlipkartSearchedProductPage searchFor(String keyword) {
		return this;
	}
}
