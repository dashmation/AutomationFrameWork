package com.qa.test.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends CommonMethods {
	@FindBy(id = "usernameTextBox")
	WebElement EmailOrPhoneNumber_TextBox;

	@FindBy(how = How.ID, id = "lp1-next-btn")
	WebElement next_Button;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public LoginPage enterEmailOrPhoneNumber(String emailOrPhone) {
		sendText(EmailOrPhoneNumber_TextBox, emailOrPhone + Keys.TAB);
		return this;
	}

	public LoginPage clickOnNextButton() {
		clickOnElement(next_Button, "Next Button");
		return this;
	}

}
