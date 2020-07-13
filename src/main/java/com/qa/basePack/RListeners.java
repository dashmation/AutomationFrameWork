package com.qa.basePack;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class RListeners implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		Reporter.log(result.getName() + " Started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Reporter.log(result.getName() + " Get Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.log(result.getName() + " Get Failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log(result.getName() + " Get Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
		Reporter.log(context.getName() + " onStart");
	}

	@Override
	public void onFinish(ITestContext context) {
		Reporter.log(context.getName() + " onFinish");
	}

}
