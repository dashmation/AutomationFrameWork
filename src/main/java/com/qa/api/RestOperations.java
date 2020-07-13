package com.qa.api;

import static com.jayway.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.qa.utility.CommonUtils;

public class RestOperations {
	public RequestSpecBuilder requestSpecBuilder;
	public RequestSpecification requestSpecification;
	public Response response;

	final String URI = CommonUtils.getValue("endPointURL");
	final String userName = CommonUtils.getValue("userName");
	final String password = CommonUtils.getValue("password");

	public RestOperations() {
		try {
			requestSpecBuilder = new RequestSpecBuilder();
			requestSpecBuilder.setBaseUri(URI);
			requestSpecBuilder.setContentType(ContentType.JSON);
			requestSpecification = requestSpecBuilder.build();
			requestSpecification = given().spec(requestSpecification);
		} catch (Exception e) {
			Reporter.log("Unable to Initialized Rest Service Due To " + e.getMessage());
			Assert.fail();
		}
	}

	private String getAuthToken(String reponsePath) {
		String authToken = null;
		Map<String, String> body = new HashMap<String, String>();
		body.put(userName, password);
		response = requestSpecification.body(body).post("auth/login");
		authToken = "Bearer " + response.getBody().jsonPath().get(reponsePath);
		if (response.body().asString().isEmpty()) {
			getAuthToken(reponsePath);
		}
		return authToken;
	}

	public Map<String, String> getAuthHeader(String key, String reponsePath) {
		Map<String, String> headerAuth = new HashMap<String, String>();
		headerAuth.put(key, getAuthToken(reponsePath));
		return headerAuth;
	}
}
