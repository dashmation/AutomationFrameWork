package com.qa.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.qa.CommonMethods.CommonMethods;

public class AmazonSearchPage extends CommonMethods {

	public AmazonSearchPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBox;

	@FindBy(how = How.ID, id = "nav-search-submit-button")
	private WebElement submitToSearch;

	private AmazonSearchPage enterKeyWordToSearch(String keywords) {
		sendText(searchBox, keywords);
		return this;
	}

	private AmazonSearchPage submitToSearch() {
		clickOnElement(submitToSearch, "SubmitToSearch");
		return this;
	}

	public AmazonSearchPage searchElement(String keywords) {
		enterKeyWordToSearch(keywords);
		submitToSearch();
		return this;
	}

	public AmazonSearchPage redirectToAmazonHomePage() {
		redirectTo("https://www.amazon.in");
		return this;
	}
}
