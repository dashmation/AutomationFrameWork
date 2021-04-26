package com.qa.stepDefinition;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qa.api.Operations;
import com.qa.api.RestAssuredExtension;
import com.qa.pojo.PostsPojo;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;

public class APIStepDefinition {
	Operations operations;
	private Response response;

	@Given("^validate Status Code$")
	public void validate_Status_Code() {
		operations = new Operations();
		operations.simpleGetOperation();
	}

	@Given("^validate With Path \"(.*)\"$")
	public void validate_With_Path_Params(String pathValue) {
		operations = new Operations();
		operations.simpleGetWithPathParams(pathValue);
	}

	@Given("^validate With Query Params$")
	public void validate_With_Query_Params() {
		operations = new Operations();
		operations.simpleGetWithQueryParams(2);
	}

	@Given("^Perform GET Operation For \"(.*)\"$")
	public void perform_GET_Operation_For(String uri) {
		response = RestAssuredExtension.getOperation(uri);
	}

	@Then("^User Should See author as \"(.*)\"$")
	public void user_Should_See_name_as(String author) {
		List<String> authorList = response.getBody().jsonPath().get("author");
		assertTrue("author Not Found", authorList.contains(author));
		System.out.println(author + " Found");
	}

	@Then("^User Should See title as \"(.*)\"$")
	public void user_Should_See_place_as(String title) {
		List<String> titleList = response.getBody().jsonPath().get("title");
		assertTrue("title Not Found", titleList.contains(title));
		System.out.println(title + " Found");
	}

	@Given("^Perform POST Operation With Query Params$")
	public void perform_POST_Operation_With_Query_Params() {
		response = RestAssuredExtension.postWithQueryAndBodyParams();
	}

	@Given("^Perform POST Operation With Path Params$")
	public void perform_POST_Operation_With_Path_Params() {
		response = RestAssuredExtension.postWithPathAndBodyParams("posts");
	}

	@Then("^Validate The Status Code As \"(.*)\"$")
	public void validate_The_Status_Code_As(int statusCode) {
		response.then().statusCode(statusCode);
	}

	@Given("^I Ensure To Perform POST Operation \"(.*)\" With Body As$")
	public void i_Ensure_To_Perform_POST_Operation_With_Body_As(String url, DataTable data) {
		List<List<String>> body = data.raw();
		Map<String, String> finalBody = new HashMap<String, String>();
		finalBody.put("id", body.get(1).get(0));
		finalBody.put("title", body.get(1).get(1));
		finalBody.put("author", body.get(1).get(2));
		RestAssuredExtension.performPOSTOperation(url, finalBody);
	}

	@Given("^I Perform DELETE Operation For \"(.*)\"$")
	public void i_Perform_DELETE_Operation_For(String url, DataTable path) {
		List<List<String>> data = path.raw();
		Map<String, Integer> pathParams = new HashMap<String, Integer>();
		pathParams.put("id", Integer.parseInt(data.get(1).get(0)));
		RestAssuredExtension.performDELETEOperation(url, pathParams);
	}

	@Given("^I Perform GET Operation With Path Parameter For \"(.*)\"$")
	public void i_Perform_GET_Operation_With_Path_Parameter(String url, DataTable path) {
		List<List<String>> data = path.raw();
		Map<String, Integer> pathParams = new HashMap<String, Integer>();
		pathParams.put("id", Integer.parseInt(data.get(1).get(0)));
		response = RestAssuredExtension.performGETOperation(url, pathParams);
	}

	@Then("^I Should Not See The Title As \"(.*)\"$")
	public void i_Should_Not_See_The_Title_As(String title) {
		assertTrue("title Found", response.getBody().asString().equals("{}"));
		System.out.println(title + " can't be found in server as expected");
	}

	@Given("^I Perform PUT Operation For \"([^\"]*)\"$")
	public void i_Perform_PUT_Operation_For(String url, DataTable dataInput) throws Throwable {
		List<List<String>> data = dataInput.raw();
		Map<String, String> body = new HashMap<String, String>();
		body.put("id", data.get(1).get(0));
		body.put("title", data.get(1).get(1));
		body.put("author", data.get(1).get(2));
		Map<String, Integer> pathParams = new HashMap<String, Integer>();
		pathParams.put("id", Integer.parseInt(data.get(1).get(0)));
		RestAssuredExtension.performPUTOperationWithPathParams(url, body, pathParams);
	}

	@Then("^I Should See The Title As \"([^\"]*)\"$")
	public void i_Should_See_The_Title_As(String title) throws Throwable {
		PostsPojo posts = response.getBody().as(PostsPojo.class);
		assertTrue("title Not Found", posts.getTitle().equals(title));
		System.out.println("Changed " + title + " has been found in server as expected using POJO class");
		assertTrue("title Not Found", response.getBody().jsonPath().get("title").equals(title));
		System.out.println("Changed " + title + " has been found in server as expected");
	}
}
