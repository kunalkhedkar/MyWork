
----------[Session ]

	- simply add anotation @SessionAttributes("name")


----------[ Validation using hibernate-validator] [5.4.1.Final]
			
			1. Added Dependency in pom.xml

			2. Inside Model class
				Add anotation for Validation like size,max,notNull,notEmpty

				'@Size(min = 10, message = "Enter atleast 10 Characters.")'
				'private String desc;'

			3. Inside controller
				Add anotation @Valid
					'@RequestMapping(value = "/update-todo", method = RequestMethod.POST)'
					'public String updateTodo(@Valid Todo todo, 'BindingResult result') { }'

			4. If there is any error it will store in BindingResult object
				we can check for error by calling method 
					bindingResult.hasErrors() - return boolean

	


----------[Command Object]

	- it will bind form data to model class and vice versa.
	- we can set data to Command Object and it set data  to form automatically

	Eg. 
		1. Add Command object to form attribute in jsp
			'<form:form method="post" commandName="todo">'

		2. Add same object to controller
			'@RequestMapping(value = "/update-todo", method = RequestMethod.POST)'
			'public String updateTodo(ModelMap model,Todo todo) { }'
