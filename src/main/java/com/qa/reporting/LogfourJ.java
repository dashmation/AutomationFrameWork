package com.qa.reporting;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LogfourJ extends Logger {
	
	public LogfourJ(String className) {
		super(className);
	}
	

	
	private static Logger logger = null;

	public static Logger getLogger() {
		return logger;
	}

	public static Logger init(String className) {
		PatternLayout layout = new PatternLayout();
		String conversionPattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p %C{1}:%L - %m%n";
		layout.setConversionPattern(conversionPattern);

		// creates console appender
		ConsoleAppender consoleAppender = new ConsoleAppender();
		consoleAppender.setLayout(layout);
		consoleAppender.activateOptions();

		// creates file appender
		FileAppender fileAppender = new FileAppender();
		fileAppender.setFile(System.getProperty("user.dir") + "/TestLogs/logs/" + className + ".log");
		fileAppender.setLayout(layout);
		fileAppender.setThreshold(Level.INFO);
		fileAppender.activateOptions();

		logger = Logger.getLogger(className);
		logger.removeAllAppenders();
		logger.setLevel(Level.INFO);
		logger.addAppender(consoleAppender);
		logger.addAppender(fileAppender);
		
		return logger;
	}
	
	public void info(String info) {
		logger.info(info);
	}

	public void info(Exception e) {
		logger.info(e);
	}

	public void error(String info) {
		logger.error(info);
	}

	public void error(Exception e) {
		logger.error(e);
	}

	public void info(String info, Exception e) {
		logger.info(info);
	}

	public void debug(String debug) {
		logger.debug(debug);
	}
}