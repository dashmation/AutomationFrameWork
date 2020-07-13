package com.qa.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature/assignment.feature", glue = { "stepDefinition" }, format = {
		"pretty", "html:test-output", "json:json_output",
		"junit:junit_xml" }, dryRun = false, strict = true, monochrome = true, tags = { "@currentTest" })
public class TestRunner {
}
