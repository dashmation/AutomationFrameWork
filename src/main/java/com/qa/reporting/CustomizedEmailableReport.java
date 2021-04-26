package com.qa.reporting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;


public class CustomizedEmailableReport extends TestListenerAdapter
		implements IReporter, ITestListener, IInvokedMethodListener {
	private static final String WEB_DRIVER_ATTR = "web.driver";
	ITestContext context;
	private static final Logger L = LoggerFactory.getLogger(CustomizedEmailableReport.class);

	// ~ Instance fields ------------------------------------------------------

	private PrintWriter out;
	private int row;
	private Integer testIndex;
	private int methodIndex;

	private static Map<String, String> testMap = new HashMap<String, String>();
	private static int testCount = 0;
	private static int successCount = 0;
	private static int failedCount = 0;
	private static int skippedCount = 0;

	// ~ Methods --------------------------------------------------------------

	/** Creates summary of the run */
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outdir) {
		L.info("Start:Generating Customized-Emailable-Report.html");
		try {
			out = createWriter(outdir);
		} catch (IOException e) {
			L.error("Error Creating Customized-Emailable-Report: ", e);
			return;
		}

		startHtml(out);
		generateSuiteSummaryReport(suites);
		generateMethodSummaryReport(suites);
		generateMethodDetailReport(suites);
		endHtml(out);
		out.flush();
		out.close();
		L.info("End:Generated Customized-Emailable-Report.html");
	}

	protected PrintWriter createWriter(String outdir) throws IOException {

		new File(outdir).mkdirs();
		return new PrintWriter(
				new BufferedWriter(new FileWriter(new File(outdir, "customized-emailable-report.html"))));
	}

	/**
	 * Creates a table showing the highlights of each test method with links to the
	 * method details
	 */
	protected void generateMethodSummaryReport(List<ISuite> suites) {
		methodIndex = 0;
		startResultSummaryTable("methodOverview");
		int testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
				titleRow(suite.getName(), 5);
			}
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				String testName = testContext.getName();
				this.testIndex = testIndex;
				resultSummary(suite, testContext.getFailedConfigurations(), testName, "failed",
						" (configuration methods)");
				resultSummary(suite, testContext.getFailedTests(), testName, "failed", "");
				resultSummary(suite, testContext.getSkippedConfigurations(), testName, "skipped",
						" (configuration methods)");
				resultSummary(suite, testContext.getSkippedTests(), testName, "skipped", "");
				resultSummary(suite, testContext.getPassedTests(), testName, "passed", "");
				testIndex++;
			}
		}
		out.println("</table>");
	}

	/** Creates a section showing known results for each method */
	protected void generateMethodDetailReport(List<ISuite> suites) {
		methodIndex = 0;
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				if (r.values().size() > 0) {
					out.println("<h1>" + testContext.getName() + "</h1>");
				}
				resultDetail(testContext.getFailedConfigurations());
				resultDetail(testContext.getFailedTests());
				resultDetail(testContext.getSkippedConfigurations());
				resultDetail(testContext.getSkippedTests());
				resultDetail(testContext.getPassedTests());
			}
		}
	}

	/**
	 * @param tests
	 */
	private void resultSummary(ISuite suite, IResultMap tests, String testname, String style, String details) {
		if (tests.getAllResults().size() > 0) {
			StringBuffer buff = new StringBuffer();
			String lastClassName = "";
			int mq = 0;
			int cq = 0;
			for (ITestNGMethod method : getMethodSet(tests, suite)) {
				row += 1;
				methodIndex += 1;
				ITestClass testClass = method.getTestClass();
				String className = testClass.getName();
				if (mq == 0) {
					String id = (testIndex == null ? null : "t" + Integer.toString(testIndex));
					titleRow(testname + " &#8212; " + style + details, 5, id);
					testIndex = null;
				}
				if (!className.equalsIgnoreCase(lastClassName)) {
					if (mq > 0) {
						cq += 1;
						out.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
						if (mq > 1) {
							out.print(" rowspan=\"" + mq + "\"");
						}
						out.println(">" + lastClassName + "</td>" + buff);
					}
					mq = 0;
					buff.setLength(0);
					lastClassName = className;
				}
				Set<ITestResult> resultSet = tests.getResults(method);
				long end = Long.MIN_VALUE;
				long start = Long.MAX_VALUE;
				long startMS = 0;
				for (ITestResult testResult : tests.getResults(method)) {
					if (testResult.getEndMillis() > end) {
						end = testResult.getEndMillis() / 1000;
					}
					if (testResult.getStartMillis() < start) {
						startMS = testResult.getStartMillis();
						start = startMS / 1000;
					}

				}

				DateFormat formatter = new SimpleDateFormat("kk:mm:ss");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(startMS);

				mq += 1;
				if (mq > 1) {
					buff.append("<tr class=\"" + style + (cq % 2 == 0 ? "odd" : "even") + "\">");
				}
				String description = method.getDescription();
				String testInstanceName = resultSet.toArray(new ITestResult[] {})[0].getTestName();
				buff.append("<td><a href=\"#m" + methodIndex + "\">" + qualifiedName(method) + " "
						+ (description != null && description.length() > 0 ? "(\"" + description + "\")" : "") + "</a>"
						+ (null == testInstanceName ? "" : "<br>(" + testInstanceName + ")") + "</td>"
						+ "<td style=\"text-align:right\">" + formatter.format(calendar.getTime()) + "</td>"
						+ "<td class=\"numi\">" + millisToTimeConversion(end - start) + "</td>" + "</tr>");
				// resultSet.size()
			}
			if (mq > 0) {
				cq += 1;
				out.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
				if (mq > 1) {
					out.print(" rowspan=\"" + mq + "\"");
				}
				out.println(">" + lastClassName + "</td>" + buff);
			}
		}
	}

	private String millisToTimeConversion(long seconds) {

		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;

		int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
		seconds -= minutes * SECONDS_IN_A_MINUTE;

		int hours = minutes / MINUTES_IN_AN_HOUR;
		minutes -= hours * MINUTES_IN_AN_HOUR;

		return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int) seconds);
	}

	private String prefixZeroToDigit(int num) {
		int number = num;
		if (number <= 9) {
			String sNumber = "0" + number;
			return sNumber;
		} else
			return "" + number;

	}

	/** Starts and defines columns result summary table */
	private void startResultSummaryTable(String style) {
		tableStart(style, "summary");
		out.println("<tr><th>Class</th>" + "<th>Method</th><th>Start Time </th><th>Time<br/>(hh:mm:ss)</th></tr>");
		row = 0;
	}

	private String qualifiedName(ITestNGMethod method) {
		StringBuilder addon = new StringBuilder();
		String[] groups = method.getGroups();
		int length = groups.length;
		if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
			addon.append("(");
			for (int i = 0; i < length; i++) {
				if (i > 0) {
					addon.append(", ");
				}
				addon.append(groups[i]);
			}
			addon.append(")");
		}

		return "<b>" + method.getMethodName() + "</b> " + addon;
	}

	private void resultDetail(IResultMap tests) {

		Set<ITestResult> testResults = tests.getAllResults();
		List<ITestResult> list = new ArrayList<ITestResult>(testResults);
		Collections.sort(list, new TestResultsSorter());

		for (ITestResult result : list) {
			ITestNGMethod method = result.getMethod();
			methodIndex++;
			String cname = method.getTestClass().getName();
			out.println("<h2 id=\"m" + methodIndex + "\">" + cname + ":" + method.getMethodName() + "</h2>");
			Set<ITestResult> resultSet = tests.getResults(method);
			generateForResult(result, method, resultSet.size());
			out.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");

		}
	}

	private void generateForResult(ITestResult ans, ITestNGMethod method, int resultSetSize) {
		Object[] parameters = ans.getParameters();
		boolean hasParameters = parameters != null && parameters.length > 0;
		if (hasParameters) {
			tableStart("result", null);
			out.print("<tr class=\"param\">");
			for (int x = 1; x <= parameters.length; x++) {
				out.print("<th>Param." + x + "</th>");
			}
			out.println("</tr>");
			out.print("<tr class=\"param stripe\">");
			for (Object p : parameters) {
				out.println("<td>" + Utils.escapeHtml(Utils.toString(p)) + "</td>");
			}
			out.println("</tr>");
		}
		List<String> msgs = Reporter.getOutput(ans);
		boolean hasReporterOutput = msgs.size() > 0;
		Throwable exception = ans.getThrowable();
		boolean hasThrowable = exception != null;
		if (hasReporterOutput || hasThrowable) {
			if (hasParameters) {
				out.print("<tr><td");
				if (parameters.length > 1) {
					out.print(" colspan=\"" + parameters.length + "\"");
				}
				out.println(">");
			} else {
				out.println("<div>");
			}
			if (hasReporterOutput) {
				if (hasThrowable) {
					out.println("<h3>Test Messages</h3>");
				}
				for (String line : msgs) {
					out.println("<span>" + line + "</span>" + "<br/>");
				}
			}
			if (hasThrowable) {
				boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					out.println("<h3>" + (wantsMinimalOutput ? "Expected Exception" : "Failure") + "</h3>");
				}
				generateExceptionReport(exception, method);
			}
			if (hasParameters) {
				out.println("</td></tr>");
			} else {
				out.println("</div>");
			}
		}
		if (hasParameters) {
			out.println("</table>");
		}
	}

	/**
	 * Write all parameters
	 *
	 * @param tests
	 */
	@SuppressWarnings("unused")
	private void getParameters(IResultMap tests) {

		for (ITestResult result : tests.getAllResults()) {
			methodIndex++;
			Object[] parameters = result.getParameters();
			boolean hasParameters = parameters != null && parameters.length > 0;
			if (hasParameters) {

				for (Object p : parameters) {
					out.println(Utils.escapeHtml(Utils.toString(p)) + " | ");
				}
			}

		}
	}

	protected void generateExceptionReport(Throwable exception, ITestNGMethod method) {
		out.print("<div class=\"stacktrace\">");
		out.print(Utils.shortStackTrace(exception, true));
		out.println("</div>");
	}

	/**
	 * Since the methods will be sorted chronologically, we want to return the
	 * ITestNGMethod from the invoked methods.
	 */
	private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
		List<IInvokedMethod> r = Lists.newArrayList();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
		for (IInvokedMethod im : invokedMethods) {
			// System.out.println("suite.getAllInvokedMethods() .."+im);
			if (tests.getAllMethods().contains(im.getTestMethod())) {
				r.add(im);
			}
		}
		Collections.sort(r, new TestSorter());

		List<ITestNGMethod> result = Lists.newArrayList();

		// Add all the invoked methods
		for (IInvokedMethod m : r) {
			for (ITestNGMethod temp : result) {
				if (!temp.equals(m.getTestMethod()))
					result.add(m.getTestMethod());
			}
		}

		// Add all the methods that weren't invoked (e.g. skipped) that we
		// haven't added yet

		Collection<ITestNGMethod> allMethodsCollection = tests.getAllMethods();
		List<ITestNGMethod> allMethods = new ArrayList<ITestNGMethod>(allMethodsCollection);
		Collections.sort(allMethods, new TestMethodSorter());

		for (ITestNGMethod m : allMethods) {
			// System.out.println("tests.getAllMethods() .."+m);
			if (!result.contains(m)) {
				result.add(m);
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	public void generateSuiteSummaryReport(List<ISuite> suites) {
		tableStart("testOverview", null);
		out.print("<tr>");
		tableColumnStart("Test");
		tableColumnStart("# passed");
		// tableColumnStart("Scenarios<br/>Passed");
		tableColumnStart("# skipped");
		tableColumnStart("# failed");
		// tableColumnStart("Error messages");
		// tableColumnStart("Parameters");
		tableColumnStart("Start<br/>Time");
		tableColumnStart("End<br/>Time");
		tableColumnStart("Total<br/>Time(hh:mm:ss)");
		tableColumnStart("Included<br/>Groups");
		tableColumnStart("Excluded<br/>Groups");

		out.println("</tr>");
		NumberFormat formatter = new DecimalFormat("#,##0.0");
		int qty_tests = 0;
		int qty_pass_m = 0;
		int qty_pass_s = 0;
		int qty_skip = 0;
		int qty_fail = 0;
		long time_start = Long.MAX_VALUE;
		long time_end = Long.MIN_VALUE;
		testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
				titleRow(suite.getName(), 10);
			}
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				startSummaryRow(overview.getName());
				int q = getMethodSet(overview.getPassedTests(), suite).size();
				qty_pass_m += q;
				summaryCell(q, Integer.MAX_VALUE);
				/*
				 * q = overview.getPassedTests().size(); qty_pass_s += q; summaryCell(q,
				 * Integer.MAX_VALUE);
				 */
				q = getMethodSet(overview.getSkippedTests(), suite).size();
				qty_skip += q;
				summaryCell(q, 0);
				q = getMethodSet(overview.getFailedTests(), suite).size();
				qty_fail += q;
				summaryCell(q, 0);

				SimpleDateFormat summaryFormat = new SimpleDateFormat("kk:mm:ss");
				summaryCell(summaryFormat.format(overview.getStartDate()), true);
				out.println("</td>");

				summaryCell(summaryFormat.format(overview.getEndDate()), true);
				out.println("</td>");

				time_start = Math.min(overview.getStartDate().getTime(), time_start);
				time_end = Math.max(overview.getEndDate().getTime(), time_end);
				/*
				 * summaryCell( formatter.format((overview.getEndDate().getTime() - overview
				 * .getStartDate().getTime()) / 1000.) + " seconds", true);
				 */
				summaryCell(millisToTimeConversion(
						(overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 1000), true);

				summaryCell(overview.getIncludedGroups());
				summaryCell(overview.getExcludedGroups());
				out.println("</tr>");
				testIndex++;
			}
		}
		if (qty_tests > 1) {
			out.println("<tr class=\"total\"><td>Total</td>");
			summaryCell(qty_pass_m, Integer.MAX_VALUE);
			// summaryCell(qty_pass_s, Integer.MAX_VALUE);
			summaryCell(qty_skip, 0);
			summaryCell(qty_fail, 0);
			// summaryCell(" ", true);
			summaryCell(" ", true);
			summaryCell(" ", true);
			// summaryCell(" ", true);
			/*
			 * summaryCell( formatter.format(((time_end - time_start) / 1000.) / 60.) +
			 * " minutes", true);
			 */
			summaryCell(millisToTimeConversion(((time_end - time_start) / 1000)), true);
			out.println("<td colspan=\"3\">&nbsp;</td></tr>");
		}
		out.println("</table>");
	}

	private void summaryCell(String[] val) {
		StringBuffer b = new StringBuffer();
		for (String v : val) {
			b.append(v + " ");
		}
		summaryCell(b.toString(), true);
	}

	private void summaryCell(String v, boolean isgood) {
		out.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v + "</td>");
	}

	private void startSummaryRow(String label) {
		row += 1;
		out.print("<tr" + (row % 2 == 0 ? " class=\"stripe\"" : "")
				+ "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t" + testIndex + "\"><b>" + label
				+ "</b></a>" + "</td>");

	}

	private void summaryCell(int v, int maxexpected) {
		summaryCell(String.valueOf(v), v <= maxexpected);
	}

	private void tableStart(String cssclass, String id) {
		out.println("<table cellspacing=\"0\" cellpadding=\"0\""
				+ (cssclass != null ? " class=\"" + cssclass + "\"" : " style=\"padding-bottom:2em\"")
				+ (id != null ? " id=\"" + id + "\"" : "") + ">");
		row = 0;
	}

	private void tableColumnStart(String label) {
		out.print("<th>" + label + "</th>");
	}

	private void titleRow(String label, int cq) {
		titleRow(label, cq, null);
	}

	private void titleRow(String label, int cq, String id) {
		out.print("<tr");
		if (id != null) {
			out.print(" id=\"" + id + "\"");
		}
		out.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
		row = 0;
	}

	/** Starts HTML stream */
	protected void startHtml(PrintWriter out) {
		out.println(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<title>TestNG Report</title>");
		out.println("<script>");
		out.println(
				"function toggleImage(data) {	var c = document.getElementById('overlay');	document.getElementById('showImage').src = data;	c.style.display = (c.style.display == 'block') ? 'none' : 'block';}");
		out.println("</script>");
		out.println("<style type=\"text/css\">");
		out.println(
				"span#close { text-align: -webkit-center; text-decoration-line:underline; width: 4%; float: right; cursor: pointer; font-weight: bold; font-size: large; background-color: white;}");
		out.println(
				"img#showImage {height: 100%; min-width:25%; margin-left: auto; margin-right: auto; display: block; top: 20px; position: relative;}");
		out.println(
				"div#overlay {  position: fixed; display: none; width: 100%; height: 100%; top: 0; left: 0; right: 0; bottom: 0; background-color: rgba(0,0,0,0.5); z-index: 2;}");
		out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
		out.println("td,th {border:1px solid #009;padding:.25em .5em}");
		out.println(".result th {vertical-align:bottom}");
		out.println(".param th {padding-left:1em;padding-right:1em}");
		out.println(".param td {padding-left:.5em;padding-right:2em}");
		out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
		out.println(".numi,.numi_attn {text-align:right}");
		out.println(".total td {font-weight:bold}");
		out.println(".passedodd td {background-color: #0A0}");
		out.println(".passedeven td {background-color: #3F3}");
		out.println(".skippedodd td {background-color: #CCC}");
		out.println(".skippedodd td {background-color: #DDD}");
		out.println(".failedodd td,.numi_attn {background-color: #F33}");
		out.println(".failedeven td,.stripe .numi_attn {background-color: #D00}");
		out.println(".stacktrace {white-space:pre;font-family:monospace}");
		out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println(
				"<div id='overlay' style='display:none;'><div style='width:90%; position:fixed;'><span id='close' onclick='toggleImage();'>Close</span></div><img id='showImage' src='' ><img></div>");
	}

	/** Finishes HTML stream */
	protected void endHtml(PrintWriter out) {
		out.println("</body></html>");
	}

	// ~ Inner Classes --------------------------------------------------------
	/** Arranges methods by classname and method name */
	private class TestSorter implements Comparator<IInvokedMethod> {
		// ~ Methods
		// -------------------------------------------------------------

		/** Arranges methods by classname and method name */
		public int compare(IInvokedMethod o1, IInvokedMethod o2) {
			int r = o1.getTestMethod().getTestClass().getName().compareTo(o2.getTestMethod().getTestClass().getName());
			if (r == 0) {
				// r = o1.getTestMethod().compareTo(o2.getTestMethod());
			}
			return r;
		}

	}

	private class TestMethodSorter implements Comparator<ITestNGMethod> {
		public int compare(ITestNGMethod o1, ITestNGMethod o2) {
			int r = o1.getTestClass().getName().compareTo(o2.getTestClass().getName());
			if (r == 0) {
				r = o1.getMethodName().compareTo(o2.getMethodName());
			}
			return r;
		}
	}

	private class TestResultsSorter implements Comparator<ITestResult> {
		public int compare(ITestResult o1, ITestResult o2) {
			int result = o1.getTestClass().getName().compareTo(o2.getTestClass().getName());
			if (result == 0) {
				result = o1.getMethod().getMethodName().compareTo(o2.getMethod().getMethodName());
			}
			return result;
		}
	}

	private void printStatus() {
		System.out.println("\n===========================================================");
		float sPercent = (float) successCount / testCount * 100;
		sPercent = Math.round(sPercent);

		float fPercent = (float) failedCount / testCount * 100;
		fPercent = Math.round(fPercent);

		float tPercent = (float) (failedCount + successCount + skippedCount) / testCount * 100;
		tPercent = Math.round(tPercent);

		float skPercent = (float) skippedCount / testCount * 100;
		skPercent = Math.round(skPercent);
		System.out.println("Success: " + successCount + " (" + sPercent + "%)");
		System.out.println("Failed: " + failedCount + " (" + fPercent + "%)");
		System.out.println("Skipped: " + skippedCount + " (" + skPercent + "%)");
		System.out.println("Execution Progress: " + (failedCount + successCount + skippedCount) + "/" + testCount + " ("
				+ tPercent + "%)");
		long millis = (System.currentTimeMillis() - Constants.suiteStartTime);
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		System.out.println("Total Execution Duration (HH:mm:ss): " + hms);
		System.out.println("\n===========================================================");
	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		successCount++;
		testMap.put(result.getMethod().getMethodName().toString(), "SUCCESS");
		printStatus();

	}

	@Override
	public void onTestFailure(ITestResult result) {
		failedCount++;
		testMap.put(result.getMethod().getMethodName().toString(), "FAILED");
		printStatus();

		WebDriver driver = (WebDriver) result.getAttribute(WEB_DRIVER_ATTR);

		if (!result.isSuccess()) {
			try {
				String url = driver.getCurrentUrl();
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				scrFile = appendUrlToImage(url, scrFile);
				String encodedFileString = encodeFileToBase64Binary(scrFile);
				Reporter.setCurrentTestResult(result);
				String imgSrc = "data:image/png;base64," + encodedFileString;
				Reporter.log("<img width='100' height='100' src=" + imgSrc + " onclick=toggleImage(this.src);> ");
			} catch (Exception e) {
				L.error("Error while capturing screenshot: " + e.getCause());
			}
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		if (testMap.containsValue(result.getMethod().getMethodName().toString())) {
			System.out.println(result.getMethod().getMethodName().toString());
		} else
			testMap.put(result.getMethod().getMethodName().toString(), "SKIPPED");

		printStatus();
		if (testMap.containsKey("SKIPPED"))
			skippedCount = testMap.get("SKIPPED").length();

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		if (!context.getName().equalsIgnoreCase("@BeforeSuite-Operations")) {
			ITestNGMethod[] methods = context.getAllTestMethods();
			testCount = testCount + methods.length;
			for (int i = 0; i < methods.length; i++) {
				testMap.put(methods[i].getMethodName().toString(), "YET_TO_START");
			}
		}
	}

	@Override
	public void onFinish(ITestContext testContext) {
		afterInvocation(null, null);
		Set<ITestResult> failedTests = testContext.getFailedTests().getAllResults();
		Set<ITestResult> skippedTests = testContext.getSkippedTests().getAllResults();
		Set<ITestResult> passedTests = testContext.getPassedTests().getAllResults();
		if (!failedTests.isEmpty()) {
			for (ITestResult result : failedTests) {
				ITestNGMethod method = result.getMethod();
				if (testContext.getFailedTests().getResults(method).size() > 1) {
					L.info("failed test case removed as duplicate:" + result.getTestClass().toString());
					failedTests.remove(result);
				} else {
					if (testContext.getPassedTests().getResults(method).size() > 0) {
						L.info("failed test case remove as pass retry:" + result.getTestClass().getTestName());
						failedTests.remove(result);
					}
				}
			}
		}
		if (!skippedTests.isEmpty()) {
			for (ITestResult result : skippedTests) {
				ITestNGMethod method = result.getMethod();
				if (testContext.getSkippedTests().getResults(method).size() > 1) {
					skippedTests.remove(result);
				} else {
					if (testContext.getPassedTests().getResults(method).size() > 0) {
						skippedTests.remove(result);
					} else {
						if (testContext.getFailedTests().getResults(method).size() > 0) {
							skippedTests.remove(result);
						}
					}
				}
			}
		}
		if (!passedTests.isEmpty()) {
			for (ITestResult result : passedTests) {
				ITestNGMethod method = result.getMethod();
				if (testContext.getPassedTests().getResults(method).size() > 1) {
					passedTests.remove(result);
				}
			}
		}

	}

	private static String encodeFileToBase64Binary(File file) {
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
			fileInputStreamReader.close();
		} catch (FileNotFoundException e) {
			L.info(e.getMessage());
		} catch (IOException e) {
			L.info(e.getMessage());
		}

		return encodedfile;
	}

	private File appendUrlToImage(String url, File scrFile) throws Exception {
		final BufferedImage image = ImageIO.read(scrFile);

		Graphics g = image.getGraphics();
		g.setFont(g.getFont().deriveFont(20f));
		g.setColor(Color.BLACK);
		g.drawString(url, 50, 50);
		g.dispose();
		ImageIO.write(image, "png", scrFile);
		return scrFile;
	}

	/***
	 * This method helps handle soft verifications
	 * 
	 * @param method
	 * @param result
	 */
	public void afterInvocation(IInvokedMethod method, ITestResult result) {

		Reporter.setCurrentTestResult(result);
		try {
			if (method.isTestMethod()) {
				List<Throwable> verificationFailures = Verify.getVerificationFailures();

				// if there are verification failures...
				if (verificationFailures.size() > 0) {

					// set the test to failed
					result.setStatus(ITestResult.FAILURE);

					// if there is an assertion failure add it to verificationFailures
					if (result.getThrowable() != null) {
						verificationFailures.add(result.getThrowable());
					}

					int size = verificationFailures.size();
					// if there's only one failure just set that
					if (size == 1) {
						result.setThrowable(verificationFailures.get(0));
					} else {
						// create a failure message with all failures and stack traces (except last
						// failure)
						StringBuffer failureMessage = new StringBuffer("Multiple failures (").append(size)
								.append("):\n\n");
						for (int i = 0; i < size - 1; i++) {
							failureMessage.append("Failure ").append(i + 1).append(" of ").append(size).append(":\n");
							Throwable t = verificationFailures.get(i);
							String fullStackTrace = Utils.shortStackTrace(t, false);
							failureMessage.append(fullStackTrace).append("\n\n");
						}

						// final failure
						Throwable last = verificationFailures.get(size - 1);
						failureMessage.append("Failure ").append(size).append(" of ").append(size).append(":\n");
						failureMessage.append(last.toString());

						// set merged throwable
						Throwable merged = new Throwable(failureMessage.toString());
						merged.setStackTrace(last.getStackTrace());

						result.setThrowable(merged);
					}
				}
			}
		} catch (Exception e) {

		}
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

	}
}