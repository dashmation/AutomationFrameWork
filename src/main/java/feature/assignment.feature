Feature: Assignment Feature 


Scenario: Automation task 1 
	Given Navigate to "google.com" 
	When Search for "Clarivate Analytics" 
	Then Make sure "clarivate.com" is the first search result 
	
@currentTest	
Scenario: Automation task 2 
	Given Enter "65575" value 
	When Click get square root button 
	Then Make sure the result is correct for "65575"