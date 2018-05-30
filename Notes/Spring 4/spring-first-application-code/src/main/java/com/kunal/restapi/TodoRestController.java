package com.kunal.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kunal.services.TodoService;
import com.kunal.todo.Todo;

@RestController
public class TodoRestController {

	@Autowired
	TodoService todoService;
	
	@RequestMapping(path="/todos")
	public List<Todo> retrieveAllTodos(){
		
		return todoService.retrieveTodos("kunal");
	}
	
	@RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
	public Todo retrieveTodo(@PathVariable("id") int id) {
		return todoService.retrieveTodo(id);
	}
	
	
	@RequestMapping(path="/check")
	public Test retrieveAllTosdos(){
		return new Test(191,"kunal");
	}
	
}
 

class Test{
	
	int id;
	String name;
	
	Test(){}
	
	public Test(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id; 
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}