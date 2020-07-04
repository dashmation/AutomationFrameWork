Feature: myTMO Login Feature 

@sanity @currentTest
Scenario: myTMO Login Test Scenarios 
	Given User is already on Login Page 
	When Title of the Login Page is My T-Mobile Login - Pay Bills Online & Manage Your T-Mobile Account 
	Then User Enters msisdn in Username Page 
		|msisdn|
		|4704308727|
	And User Clicks on Next Button 
	Then User Enters password in Password Page 
	And User clicks on Next Button 
	Then User is on 2FA page 
	
@Regression
Scenario: myTMO Password Page MSISDN display 
	Given User is already on Login Page 
	When Title of the Login Page is My T-Mobile Login - Pay Bills Online & Manage Your T-Mobile Account 
	Then User Enters msisdn in Username Page 
		|msisdn|
		|4704308727|
	And User Clicks on Next Button 
	Then User should be able to see the msisdn in PasswordPage