package com.qa.stepDefinition;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStepDefinition {

	WebDriver getDriver;
	WebDriverWait wait;

	@Before("@sanity")
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "D:/Softwares/chromedriver_win32/chromedriver.exe");
		getDriver = new ChromeDriver();
		getDriver.manage().window().maximize();
		System.out.println("Browser launched");

	}

	@After("@sanity")
	public void tearDown() {
		getDriver.quit();
		System.out.println("User Closed The Browser");
	}

	@Before("@sanity")
	public void beforeSanityMethod() {
		System.out.println("beforeSanityMethod");

	}

	@After("@sanity")
	public void afterSanityMethod() {
		System.out.println("afterSanityMethod");
	}

	@Given("^User is already on Login Page$")
	public void user_is_already_on_Login_Page() {
		getDriver.get("https://my.t-mobile.com");
		System.out.println("Browsed the Log In page");
	}

	@When("^Title of the Login Page is My T-Mobile Login - Pay Bills Online & Manage Your T-Mobile Account$")
	public void title_of_the_Login_Page_is_My_T_Mobile_Login_Pay_Bills_Online_Manage_Your_T_Mobile_Account() {
		boolean result = getDriver.getTitle()
				.equals("My T-Mobile Login - Pay Bills Online & Manage Your T-Mobile Account");
		Assert.assertEquals(true, result);
		System.out.println("Page Title is as expected");
	}

	@Then("^User Enters msisdn in Username Page$")
	public void user_Enters_msisdn_in_Username_Page(DataTable dataTable) {
		for (Map<String, String> dataMapped : dataTable.asMaps(String.class, String.class)) {
			getDriver.findElement(By.id("usernameTextBox")).sendKeys(dataMapped.get("msisdn"));
			System.out.println("MSISDN entered succesfully");
		}
	}

	@And("^User Clicks on Next Button$")
	public void user_Clicks_on_Next_Button() {
		wait = new WebDriverWait(getDriver, 10);
		WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("lp1-next-btn")));
		nextButton.click();
		System.out.println("Clicked on Next button");
	}

	@Then("^User Enters password in Password Page$")
	public void user_Enters_password_in_Password_Page() {
		wait = new WebDriverWait(getDriver, 10);
		WebElement passwordTextBox = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='passwordTextBox']")));
		passwordTextBox.sendKeys("Sec@2019");
		System.out.println("Password entered succesfully");
	}

	@And("^User clicks on Next Button$")
	public void user_clicks_on_Next_Button() {
		getDriver.findElement(By.id("lp2-login-btn")).click();
		System.out.println("Clicked on Next button");
	}

	@Then("^User is on 2FA page$")
	public void user_is_on_2FA_page() {
		wait = new WebDriverWait(getDriver, 10);
		WebElement continueButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text()='Continue']")));
		boolean result = continueButton.isDisplayed();
		Assert.assertEquals(true, result);
		System.out.println("User is on 2FA page");
	}

	@Then("^User should be able to see the msisdn in PasswordPage$")
	public void user_should_be_able_to_see_the_msisdn_in_PasswordPage() {
		wait = new WebDriverWait(getDriver, 10);
		WebElement userNamePopulated = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("lp2-userName-txt")));
		boolean result = userNamePopulated.getText().equals("(470) 430-8727");
		Assert.assertEquals(true, result);
		System.out.println("User is on Password Page");
	}
}
