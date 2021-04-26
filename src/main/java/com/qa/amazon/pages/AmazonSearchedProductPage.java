package com.qa.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.CommonMethods.CommonMethods;

public class AmazonSearchedProductPage extends CommonMethods {

	public AmazonSearchedProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBox;
	
	
	public AmazonSearchedProductPage getPriceFor() {
		return this;
	}
	
	public AmazonSearchedProductPage searchFor(String keyword) {
		return this;
	}

}
