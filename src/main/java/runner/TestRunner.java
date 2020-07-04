package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "D:/Cucumber/myTMO/myTelBDDCucumber/src/main/java/feature/assignment.feature", 
			glue = {"stepDefinition" },
			format = { "pretty", "html:test-output", "json:json_output",
				"junit:junit_xml" },
			dryRun = false,
			strict = true,
			monochrome = true,
			tags= {"@currentTest"})
public class TestRunner {

}
