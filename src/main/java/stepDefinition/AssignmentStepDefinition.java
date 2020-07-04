package stepDefinition;

import com.automation.frameWork.baseTest.DriverTech;
import com.qa.test.pages.Calculator;
import com.qa.test.pages.GooglePage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AssignmentStepDefinition extends DriverTech {

	GooglePage googlePage = null;
	Calculator calc = null;

	@Before("@currentTest")
	public void launchApplication() {
		setUp();
	}

	@Given("^Navigate to \"(.*)\"$")
	public void navigate_to(String url) {
		googlePage = new GooglePage(this.driver);
		googlePage.verifyGooglePage(url);
	}

	@When("^Search for \"(.*)\"$")
	public void search_for(String keyWord) {
		googlePage = new GooglePage(this.driver);
		googlePage.searchForTheKeyWord(keyWord);
	}

	@Then("^Make sure \"(.*)\" is the first search result$")
	public void make_sure_is_the_first_search_result(String value) {
		googlePage = new GooglePage(this.driver);
		googlePage.verifyExpectedValueInIndex(value, 1);
	}

	@Given("^Enter \"(.*)\" value$")
	public void enter_value(String value) {
		calc = new Calculator(this.windowSession);
		calc.enterNumber(value);
	}

	@When("^Click get square root button$")
	public void click_get_square_root_button() {
		calc = new Calculator(this.windowSession);
		calc.clickOnSquareRoot();
	}

	@Then("^Make sure the result is correct for \"(.*)\"$")
	public void make_sure_the_result_is_correct(String value) {
		calc = new Calculator(this.windowSession);
		String expected = String.valueOf(Math.sqrt(Double.parseDouble(value)));
		calc.validateResult(expected);
	}

	@After("@currentTest")
	public void closeBrowser() {
		tearDown();
	}
}
