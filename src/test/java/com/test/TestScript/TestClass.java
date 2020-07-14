package com.test.TestScript;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.qa.basePack.DriverTech;

public class TestClass extends DriverTech {

	@Test()
	public void verfiyLoginPage() {
		AssertJUnit.assertTrue(true);
	}

	@Test
	public void verfiySignPage() {
		AssertJUnit.assertTrue(false);
	}

	@Test
	public void verfiyPasswordResetPage() {
		AssertJUnit.assertTrue(false);
	}
	
	@Test
	public void verfiyProfilePage() {
		AssertJUnit.assertTrue(true);
	}

}
