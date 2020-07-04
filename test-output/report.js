$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("D:/Cucumber/myTMO/myTelBDDCucumber/src/main/java/feature/assignment.feature");
formatter.feature({
  "line": 1,
  "name": "Assignment Feature",
  "description": "",
  "id": "assignment-feature",
  "keyword": "Feature"
});
formatter.before({
  "duration": 4071272300,
  "status": "passed"
});
formatter.scenario({
  "line": 10,
  "name": "Automation task 2",
  "description": "",
  "id": "assignment-feature;automation-task-2",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 9,
      "name": "@currentTest"
    }
  ]
});
formatter.step({
  "line": 11,
  "name": "Enter \"65575\" value",
  "keyword": "Given "
});
formatter.step({
  "line": 12,
  "name": "Click get square root button",
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "Make sure the result is correct for \"65575\"",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "65575",
      "offset": 7
    }
  ],
  "location": "AssignmentStepDefinition.enter_value(String)"
});
formatter.result({
  "duration": 674530000,
  "error_message": "java.lang.IllegalArgumentException: Can not set io.appium.java_client.windows.WindowsElement field com.qa.test.WebDriver.Calculator.number0 to org.openqa.selenium.remote.RemoteWebElement$$EnhancerByCGLIB$$d27c0df4\r\n\tat sun.reflect.UnsafeFieldAccessorImpl.throwSetIllegalArgumentException(UnsafeFieldAccessorImpl.java:167)\r\n\tat sun.reflect.UnsafeFieldAccessorImpl.throwSetIllegalArgumentException(UnsafeFieldAccessorImpl.java:171)\r\n\tat sun.reflect.UnsafeObjectFieldAccessorImpl.set(UnsafeObjectFieldAccessorImpl.java:81)\r\n\tat java.lang.reflect.Field.set(Field.java:764)\r\n\tat org.openqa.selenium.support.PageFactory.proxyFields(PageFactory.java:117)\r\n\tat org.openqa.selenium.support.PageFactory.initElements(PageFactory.java:105)\r\n\tat com.qa.test.WebDriver.Calculator.\u003cinit\u003e(Calculator.java:54)\r\n\tat stepDefinition.AssignmentStepDefinition.enter_value(AssignmentStepDefinition.java:43)\r\n\tat ✽.Given Enter \"65575\" value(D:/Cucumber/myTMO/myTelBDDCucumber/src/main/java/feature/assignment.feature:11)\r\n",
  "status": "failed"
});
formatter.match({
  "location": "AssignmentStepDefinition.click_get_square_root_button()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "65575",
      "offset": 37
    }
  ],
  "location": "AssignmentStepDefinition.make_sure_the_result_is_correct(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "duration": 46878600,
  "status": "passed"
});
});