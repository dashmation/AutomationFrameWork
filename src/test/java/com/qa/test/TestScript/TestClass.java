package com.qa.test.TestScript;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.frameWork.baseTest.DriverTech;

public class TestClass extends DriverTech {

	@Test()
	public void verfiyLoginPage() {
		Assert.assertTrue(true);
	}

	@Test
	public void verfiySignPage() {
		Assert.assertTrue(false);
	}

	@Test
	public void verfiyPasswordResetPage() {
		Assert.assertTrue(false);
	}
	
	@Test
	public void verfiyProfilePage() {
		Assert.assertTrue(true);
	}

}
