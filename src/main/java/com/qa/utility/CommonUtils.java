package com.qa.utility;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.Reporter;

public class CommonUtils {

	static Properties prop;

	static String dir = "src/main/resources/utils.properties";

	public static String getValue(String key) {
		String value = null;
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(dir);
			prop.load(fis);
			value = prop.getProperty(key).trim();
		} catch (Exception e) {
			Reporter.log("Unable To Get The Value Due To " + e.getMessage(), true);
		}
		return value;
	}
}
