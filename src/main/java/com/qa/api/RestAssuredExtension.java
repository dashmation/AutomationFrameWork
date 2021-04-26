package com.qa.api;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredExtension {
	public static RequestSpecification requestSpecification;

	public RestAssuredExtension() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri("http://localhost:3000");
		requestSpecBuilder.setContentType(ContentType.JSON);
		requestSpecBuilder.addHeader("Authorization", getAuthToken());
		requestSpecification = requestSpecBuilder.build();
		requestSpecification = RestAssured.given().spec(requestSpecification);
	}

	private static String getAuthToken() {
		Response response = null;
		String authToken = "";
		try {
			Map<String, String> body = new HashMap<String, String>();
			body.put("email", "tanu@email.com");
			body.put("password", "tanu123");
			response = given().contentType(ContentType.JSON).body(body).post("http://localhost:3000/auth/login");
			authToken = "Bearer " + response.getBody().jsonPath().get("access_token");
		} catch (Exception e) {
			System.err.println("Unable to get the Auth Token due to " + e.getMessage());
		}
		return authToken;
	}

	public static Response getWithPathParams(Map<String, ?> pathParameters) {
		Response response = null;
		try {
			String pathParamsSet = "";
			for (Entry<String, ?> pathParams : pathParameters.entrySet()) {
				pathParamsSet = "/" + pathParams.getKey() + "/" + pathParams.getValue() + pathParamsSet;
			}
			response = requestSpecification.when().get(pathParamsSet);
		} catch (Exception e) {
			System.err.print("Unable to getWithPathParams due to " + e.getMessage());
		}
		return response;
	}

	public static Response getWithQueryParams(String URI, Map<String, String> queryParams) {
		Response response = null;
		try {
			requestSpecification.queryParams(queryParams);
			requestSpecification.basePath(URI);
			response = requestSpecification.get();
		} catch (Exception e) {
			System.err.print("Unable to getWithQueryParams due to " + e.getMessage());
		}
		return response;
	}

	public static Response getOperation(String URI) {
		Response response = null;
		try {
			requestSpecification.basePath(URI);
			response = requestSpecification.get();
		} catch (Exception e) {
			System.err.print("Unable to getOperation due to " + e.getMessage());
		}
		return response;
	}

	public static Response postWithQueryAndBodyParams() {
		Response response = null;
		try {
			HashMap<String, String> postContent = new HashMap<String, String>();
			postContent.put("id", "7");
			postContent.put("title", "design");
			postContent.put("author", "Matru Prasad");
			if (getWithQueryParams("/posts", postContent).getBody().asString().equals("[]")) {
				requestSpecification.with().body(postContent);
				requestSpecification.basePath("/posts");
				response = requestSpecification.post();
			} else {
				System.out.println(postContent.toString() + " is already available");
			}
		} catch (Exception e) {
			System.err.println("Unable to create postWithBodyParams due to " + e.getMessage());
		}
		return response;
	}

	public static Response postWithPathAndBodyParams(String basePath) {
		Response response = null;
		try {
			HashMap<String, String> postContent = new HashMap<String, String>();
			postContent.put("id", "7");
			postContent.put("title", "design");
			postContent.put("author", "Matru Prasad");
			Map<String, Integer> pathParams = new HashMap<String, Integer>();
			pathParams.put(basePath, Integer.parseInt(postContent.get("id")));
			if (getWithPathParams(pathParams).getBody().asString().equals("{}")) {
				requestSpecification.with().body(postContent);
				requestSpecification.basePath("/" + basePath);
				response = requestSpecification.post();
			} else {
				System.out.println(postContent.toString() + " is already available");
			}
		} catch (Exception e) {
			System.err.println("Unable to create postWithBodyParams due to " + e.getMessage());
		}
		return response;
	}

	public static Response performPOSTOperation(String url, Map<String, String> body) {
		Response response = null;
		try {
			response = requestSpecification.body(body).post(url);
		} catch (Exception e) {
			System.err.println("Unabel to perform POSTOperation due to " + e.getMessage());
		}
		return response;
	}

	public static Response performDELETEOperation(String url, Map<String, ?> pathParams) {
		Response response = null;
		try {
			requestSpecification.pathParams(pathParams);
			response = requestSpecification.delete(url);
		} catch (Exception e) {
			System.err.println("Unabel to perform DELETEOperation due to " + e.getMessage());
		}
		return response;
	}

	public static Response performGETOperation(String url, Map<String, ?> pathParams) {
		Response response = null;
		try {
			response = requestSpecification.pathParams(pathParams).get(url);
		} catch (Exception e) {
			System.err.println("Unabel to perform GETOperation due to " + e.getMessage());
		}
		return response;
	}

	public static Response performPUTOperationWithPathParams(String url, Map<String, ?> body,
			Map<String, ?> pathParams) {
		Response response = null;
		try {
			response = requestSpecification.pathParams(pathParams).body(body).put(url);
		} catch (Exception e) {
			System.err.println("Unabel to perform PUTOperationWithPathParams due to " + e.getMessage());
		}
		return response;
	}

}
