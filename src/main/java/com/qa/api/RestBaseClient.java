package com.qa.api;

import java.util.Map;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;


public class RestBaseClient {
	public RequestSpecBuilder requestSpecBuilder = null;
	public RequestSpecification requestSpecification = null;

	public RequestSpecification getRequestSpecification() {
		return requestSpecification;
	}

	public void setRequestSpecification(RequestSpecification requestSpecification) {
		this.requestSpecification = requestSpecification;
	}

	public Response response = null;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public enum httpType {
		GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE;
	}

	public enum StatusCode {
		Continue, OK, Created, Accepted, NoContent, MovedPermanently, NotModified, BadRequest, Unauthorized, Forbidden,
		NotFound, Conflict, InternalServerError, BadGateway, ServiceUnavailable, GatewayTimeout;
	}

	String basePath = null;
	Map<String, String> queryParams = null;
	Map<String, String> headers = null;
	Map<String, String> cookies = null;
	String body = null;

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}

	public RestBaseClient(String baseURI) {
		requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(baseURI);
//		requestSpecBuilder.accept(ContentType.APPLICATION_JSON);
		requestSpecification = requestSpecBuilder.build();
		requestSpecification = RestAssured.given().spec(requestSpecification);
		this.setRequestSpecification(requestSpecification);
	}

	public void CallWith(httpType httpType) {
		requestSpecBuilder.addHeaders(this.getHeaders());
		if (getCookies() != null) {
			requestSpecBuilder.addCookies(getCookies());
		}
		if (getBasePath() != null) {
			requestSpecification.basePath(getBasePath());
		}
		if (getQueryParams() != null) {
			requestSpecification.queryParams(getQueryParams());
		}
		switch (httpType) {
		case GET:
			this.setResponse(requestSpecification.get());
			break;
		case HEAD:
			this.setResponse(requestSpecification.head());
			break;
		case POST:
			requestSpecification.body(this.getBody());
			this.setResponse(requestSpecification.post());
			break;
		case PUT:
			requestSpecification.body(getBody());
			this.setResponse(requestSpecification.put());
			break;
		case DELETE:

			break;
		case CONNECT:

			break;
		case OPTIONS:

			break;
		case TRACE:

			break;

		default:
			break;
		}
	}

	public void validateStatusCode(StatusCode StatusCode) {
		int currentStatusCode = this.getResponse().getStatusCode();
		switch (StatusCode) {
		case Continue:
			Assert.assertTrue(currentStatusCode == 100);
			break;
		case OK:
			Assert.assertTrue(currentStatusCode == 200);
			break;
		case Created:
			Assert.assertTrue(currentStatusCode == 201);
			break;
		case Accepted:
			Assert.assertTrue(currentStatusCode == 202);
			break;
		case NoContent:
			Assert.assertTrue(currentStatusCode == 204);
			break;
		case MovedPermanently:
			Assert.assertTrue(currentStatusCode == 301);
			break;
		case NotModified:
			Assert.assertTrue(currentStatusCode == 304);
			break;
		case BadRequest:
			Assert.assertTrue(currentStatusCode == 400);
			break;
		case Unauthorized:
			Assert.assertTrue(currentStatusCode == 401);
			break;
		case Forbidden:
			Assert.assertTrue(currentStatusCode == 403);
			break;
		case NotFound:
			Assert.assertTrue(currentStatusCode == 404);
			break;
		case Conflict:
			Assert.assertTrue(currentStatusCode == 409);
			break;
		case InternalServerError:
			Assert.assertTrue(currentStatusCode == 500);
			break;
		case BadGateway:
			Assert.assertTrue(currentStatusCode == 502);
			break;
		case ServiceUnavailable:
			Assert.assertTrue(currentStatusCode == 503);
			break;
		case GatewayTimeout:
			Assert.assertTrue(currentStatusCode == 504);
			break;
		default:
			break;
		}
	}

	public RequestLogSpecification logger() {
		return this.getRequestSpecification().log();
	}
}
