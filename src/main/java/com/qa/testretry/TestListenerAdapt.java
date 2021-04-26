package com.qa.testretry;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestListenerAdapt extends TestListenerAdapter {
	public void onTestFailure(ITestResult result) {
		if (result.getMethod().getRetryAnalyzer() != null) {
			RetryAnalyzer retryAnalyzer = (RetryAnalyzer) result.getMethod().getRetryAnalyzer();

			if (retryAnalyzer.isRetryAvailable()) {
				result.setStatus(ITestResult.SKIP);
			} else {
				result.setStatus(ITestResult.FAILURE);
			}
			Reporter.setCurrentTestResult(result);
		}
	}
}