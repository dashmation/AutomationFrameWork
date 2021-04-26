package com.qa.reporting;

import java.util.List;

import org.testng.IExecutionListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentListener implements IReporter, ISuiteListener, ITestListener, IExecutionListener {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;

	public void onExecutionStart() {
		System.out.println("onExecutionStart");
	}

	public void onExecutionFinish() {
		System.out.println("onExecutionFinish");

	}

	public void onTestStart(ITestResult result) {
		System.out.println("onTestStart(ITestResult result)");

	}

	public void onTestSuccess(ITestResult result) {
		logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
	}

	public void onTestFailure(ITestResult result) {
		// MarkupHelper is used to display the output in different colors
		logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
		logger.log(Status.FAIL,
				MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		// To capture screenshot path and store the path of the screenshot in the string
		// "screenshotPath"
		// We do pass the path captured by this method in to the extent reports using
		// "logger.addScreenCapture" method.
		// String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
	}

	public void onTestSkipped(ITestResult result) {
		logger.log(Status.SKIP,
				MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage(ITestResult result)");

	}

	public void onStart(ITestContext context) {
		System.out.println("onStart(ITestContext context)");

	}

	public void onFinish(ITestContext context) {
		System.out.println("onFinish(ITestContext context)");

	}

	public void onStart(ISuite suite) {
		htmlReporter = new ExtentHtmlReporter("CheckReport/Extent/test-output/STMExtentReport.html");
		// Create an object of Extent Reports
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "ODC Reporting");
		extent.setSystemInfo("Environment", "Production");
		extent.setSystemInfo("User Name", "ODC");
		htmlReporter.config().setDocumentTitle("Data Engineering");
		// Name of the report
		htmlReporter.config().setReportName("Pixel Profile");
		// Dark Theme
		htmlReporter.config().setTheme(Theme.DARK);

	}

	public void onFinish(ISuite suite) {
		extent.flush();
	}

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		System.out.println("generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory)");

	}

}
