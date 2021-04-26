package com.qa.testretry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryAnalyzer implements IRetryAnalyzer {
	private int MAX_RETRY_COUNT = 0;

	public int getMAX_RETRY_COUNT() {
		return MAX_RETRY_COUNT;
	}

	public void setMAX_RETRY_COUNT(int mAX_RETRY_COUNT) {
		if (System.getProperty("retry_count") != "") {
			setMAX_RETRY_COUNT(Integer.parseInt(System.getProperty("retry_count")));
		} else {
			this.MAX_RETRY_COUNT = mAX_RETRY_COUNT;
		}
	}

	AtomicInteger count = new AtomicInteger(getMAX_RETRY_COUNT());

	public boolean isRetryAvailable() {
		return (count.intValue() > 0);
	}

	@Override
	public boolean retry(ITestResult result) {
		boolean retry = false;
		if (isRetryAvailable()) {
			System.out.println("Going to retry test case: " + result.getMethod() + ", "
					+ (MAX_RETRY_COUNT - count.intValue() + 1) + " out of " + MAX_RETRY_COUNT);
			retry = true;
			count.decrementAndGet();
		}
		return retry;
	}
}
