Controller

controller contains functions which handle requests

	Need two things
		1. class 		anoted with @Controller
		2. functions 	anoted with @RequestMapping


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String sayHello() {
		return "login";
		// return "/views/login.jsp";    
	} 

	- by default it handles both get as well as post method

		@RequestMapping contains two main fields
			1. value 
				- for which url it has to response
			2. methode 
				- request method type [ get , psot]


	1) -- Response --

		- by default it returns view url "/views/login.jsp"  
		  so if we want to send string response we need to add anotation @ResponseBody


    2) -- Accept Parameter
			
			- Inside method Parameter body
				we can Accept Parameter by using anotation @RequestParam    eg. @RequestParam String name

			@RequestMapping(value = "/login")
			public String sayHello(@RequestParam String username,@RequestParam String password) {
				// use usename and password got from request
				return "/views/login.jsp";    
			}

		note: (@RequestParam("<htmal feild name>") String username)
		if we put String variable and html feild name same no need to specify it seperatly

	3) -- Send Data In Response 

			- use ModelMap Object put data to it and these data will automatically availible to views
				
				@RequestMapping(value = "/login")
				public String sayHello(ModelMap modelMap, @RequestParam String username) {
				
					modelMap.put("name",name);

				 return "/views/login.jsp";    
				}

				inside view using EL we can access data 
				- welcome ${name}

