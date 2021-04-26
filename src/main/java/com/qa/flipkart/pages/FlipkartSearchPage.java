package com.qa.flipkart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.qa.CommonMethods.CommonMethods;

public class FlipkartSearchPage extends CommonMethods {

	public FlipkartSearchPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "q")
	private WebElement searchBox;

	@FindBy(how = How.CLASS_NAME, className = "L0Z3Pu")
	private WebElement submitToSearch;

	private FlipkartSearchPage enterKeyWordToSearch(String keywords) {
		sendText(searchBox, keywords);
		return this;
	}

	private FlipkartSearchPage submitToSearch() {
		clickOnElement(submitToSearch, "SubmitToSearch");
		return this;
	}

	public FlipkartSearchPage searchElement(String keywords) {
		enterKeyWordToSearch(keywords);
		submitToSearch();
		return this;
	}

	public FlipkartSearchPage redirectToAmazonHomePage() {
		redirectTo("https://www.flipkart.com");
		return this;
	}
}
