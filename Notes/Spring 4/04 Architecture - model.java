Architecture

	- Model 1 Architecture						[ JSP ]
	- Model 2 Architecture  					[ MVC ]
	- Modification of Model 2 Architecture		[ Spring MVC - Front Controller ]

===========================================================================================================================
1. 	Model 1 Architecture

	- Entired flow was base on JSP
	- No servlet or controllers
	- Because of everything in JSP views as well as business & controller its difficult to maintane.

===========================================================================================================================

2. Model 2 Architecture or 'MVC'

	- 	MVC
		 - Model		- data 	  ['Actual model class']
		 - View 		- JSP 	  ['take information from Model and show it to the screen']	
		 - Controller  	- Servlet ['accepts a request and redirect to view']

		 There other view thecnologies
		 	{velocity,freemarker}
		 		- adv Hard to write java logic code

===========================================================================================================================

3. Modification of Model 2 Architecture
	- In model 2 Architecture request will directly go to perticuler servlet

	- In Modification of model 2 Architecture
		- All the request first go to dispatcher servlet then dispatcher servlet would deside which one to call.
		- The pattern where every request go to one servlet irespective kind of request thats called a 'Front controller Pattern'
			- Advantage
				- Everything goes to same place so you can control wants going on.
				- Easy add and remove functionality
				- Eg. adding authentication to ervey request





