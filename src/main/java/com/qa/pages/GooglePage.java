package com.qa.pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.testng.Reporter;

import com.qa.CommonMethods.CommonMethods;

public class GooglePage extends CommonMethods {
	@FindBy(xpath = ".//*[@name='q']")
	private WebElement googleSearchBox;

	@FindBy(how = How.NAME, name = "btnK")
	private WebElement buttonToSearch;

	@FindBys({ @FindBy(xpath = ".//cite") })
	private List<WebElement> searchResults;

	public GooglePage(WebDriver driver) {
		super(driver);
	}

	public GooglePage enterSearcheableKeyWord(String keyword) {
		sendText(googleSearchBox, keyword);
		return this;
	}

	public GooglePage clickOnNextButton() {
		clickOnElement(buttonToSearch, "Search Button");
		return this;
	}

	public GooglePage searchForTheKeyWord(String keyword) {
		enterSearcheableKeyWord(keyword + Keys.ENTER);
		return this;
	}

	public GooglePage verifyExpectedValueInIndex(String value, int index) {
		checkPageIsReady();
		try {
			if (searchResults.get(index - 1).getText().equals(value)) {
				Reporter.log(value + " found in index " + index);
				System.out.println(value + " found in result no " + index);
			} else {
				Assert.fail(value + " is not found in result no " + index);
			}
		} catch (Exception e) {
			Reporter.log("Unable to perform verifyExpectedValueInIndex due to " + e.getMessage());
			System.out.println("Unable to perform verifyExpectedValueInIndex due to " + e.getMessage());
		}
		return this;
	}

	public GooglePage verifyGooglePage(String expected) {
		Assert.assertTrue(verifyCurrentURL(expected));
		return null;
	}

}
