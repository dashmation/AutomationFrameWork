package stepDefinition;

import com.qa.test.api.RestAssuredExtension;

public class TestInitialize {

	public void init() {
		RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
		System.out.println(restAssuredExtension.getClass());
	}

}
