Rest Api

	1. Create Controller with anotation @RestController


	2.  Return any class object Which contains setter and getter [ for convertion ]

	3. we need converter so we add library jackson in pom.xml

		Eg.  
			@RequestMapping(path="/todos")
			public List<Todo> retrieveAllTodos(){
				
				return todoService.retrieveTodos("kunal");
			}


	4. To work with parameter 

			url: 'http://localhost:8080/todo/3'

			@RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
			public Todo retrieveTodo(@PathVariable("id") int id) {
				return todoService.retrieveTodo(id);
			}