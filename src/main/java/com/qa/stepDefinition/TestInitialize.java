package com.qa.stepDefinition;

import com.qa.api.RestAssuredExtension;

public class TestInitialize {

	public void init() {
		RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
		System.out.println(restAssuredExtension.getClass());
	}

}
