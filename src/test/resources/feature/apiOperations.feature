Feature: To Test API Operations 

Scenario: GET Operations 
	Given validate Status Code 
	
Scenario: GET With Path Parmeter Operations 
	Given validate With Path "2" 
	
Scenario: GET With Query Parmeter Operations 
	Given validate With Query Params 
	
Scenario: Validate Name And Place 
	Given Perform GET Operation For "/posts" 
	Then User Should See author as "Subhrajyoti Dash" 
	Then User Should See title as "java" 
	
Scenario: POST Validation With Query Parameters 
	Given Perform POST Operation With Query Params 
	Then Validate The Status Code As "201" 
	
Scenario: POST Validation With Path Parameters 
	Given Perform POST Operation With Path Params 
	Then Validate The Status Code As "201" 
	
Scenario: Verify DELETE Operation After POST 
	Given I Ensure To Perform POST Operation "/posts" With Body As 
		|id|title|author|
		|8|creo|P.Amrit Kumar|
	And I Perform DELETE Operation For "/posts/{id}/" 
		|id|
		|8|
	And I Perform GET Operation With Path Parameter For "/posts/{id}/" 
		|id|
		|8|
	Then I Should Not See The Title As "creo" 
	
Scenario: Verify PUT Operation After POST 
	Given I Ensure To Perform POST Operation "/posts" With Body As 
		|id|title|author|
		|8|creo|P.Amrit Kumar|
	And I Perform PUT Operation For "/posts/{id}/" 
		|id|title|author|
		|8|catia|P.Amrit Kumar|
	And I Perform GET Operation With Path Parameter For "/posts/{id}/" 
		|id|
		|8|
	Then I Should See The Title As "catia" 
	And I Perform DELETE Operation For "/posts/{id}/" 
		|id|
		|8|
		
