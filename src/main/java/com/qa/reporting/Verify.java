package com.qa.reporting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Verify {
	public static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	
	/***
	 * Verify true method for soft assertions
	 * @param condition
	 */
    
    public static void assertTrue(boolean condition) {
    	try {
    		Assert.assertTrue(condition);
    	} catch(Throwable e) {
    		addVerificationFailure(e,"");
    	}
    }
    
    /***
	 * Verify true method for soft assertions
	 * @param condition
	 */
    
    public static void fail(String message) {
    	try {
    		Assert.fail(message);
    	} catch(Throwable e) {
    		addVerificationFailure(e,message);
    	}
    }
    
    /***
	 * Verify true method for soft assertions
	 * @param condition
	 */
    public static void assertTrue(boolean condition, String message) {
    	try {
    		Assert.assertTrue(condition, message);
    	} catch(Throwable e) {
    		addVerificationFailure(e,message);
    	}
    }
    /***
	 * Verify false method for soft assertions
	 * @param condition
	 */
    public static void assertFalse(boolean condition) {
    	try {
    		Assert.assertFalse(condition);
		} catch(Throwable e) {
    		addVerificationFailure(e,"");
		}
    }
    /***
	 * Verify false method for soft assertions
	 * @param condition
	 */
    public static void assertFalse(boolean condition, String message) {
    	try {
    		Assert.assertFalse(condition, message);
    	} catch(Throwable e) {
    		addVerificationFailure(e,message);
    	}
    }
    /***
	 * Verify equals method for soft assertions
	 * @param condition
	 */
    public static void assertEquals(boolean actual, boolean expected) {
    	try {
    		Assert.assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e,"");
		}
    }
    /***
	 * Verify equals method for soft assertions
	 * @param condition
	 */
    public static void assertEquals(Object actual, Object expected) {
    	try {
    		Assert.assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e,"");
		}
    }
    
    /***
	 * Verify equals method for soft assertions
	 * @param condition
	 */
    public static void assertEquals(Object actual, Object expected,String message) {
    	try {
    		Assert.assertEquals(actual, expected,message);
		} catch(Throwable e) {
    		addVerificationFailure(e,message);
		}
    }
    
    /***
	 * Verify equals method for soft assertions
	 * @param condition
	 */
    public static void assertEquals(Object[] actual, Object[] expected) {
    	try {
    		Assert.assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e,"");
		}
    }
    /***
	 * List out verification failures after test execution done
	 * @param condition
	 */
	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
	/***
	 * add failures to map
	 * @param condition
	 */
	public static void addVerificationFailure(Throwable e,String message) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
		
	}

}
