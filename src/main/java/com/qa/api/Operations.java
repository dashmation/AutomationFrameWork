package com.qa.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;


public class Operations {

	public void simpleGetOperation() {
		given().
				contentType(ContentType.JSON).
		when().
				get("http://localhost:3000/adress/2").
		then().
				statusCode(200).
		and().
				body("name",hasToString(equalToIgnoringCase("Tanu")));
	}
	
	public void simpleGetWithPathParams(String pathValue) {
		given().
				contentType(ContentType.JSON).
		with().
				pathParam("adress", pathValue).
				
		when().
				get("http://localhost:3000/adress/{adress}").
		then().
				statusCode(200).
		and().
				body("name", hasToString(equalToIgnoringCase("TANU")));
	}
	
	public void simpleGetWithQueryParams(int queryid) {
		given().
				contentType(ContentType.JSON).
		with().
				queryParam("id", queryid).
				
		when().
				get("http://localhost:3000/adress").
		then().
				statusCode(200).
		and().
				body("place",hasItem("Anandpur"));
	}

}
