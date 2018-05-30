package com.kunal.springmvc;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kunal.exception.ExceptionController;
import com.kunal.services.TodoService;
import com.kunal.todo.Todo;

@Controller
@SessionAttributes("name")
public class TodoController {

	@Autowired
	TodoService todoService;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd-mm-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	

	@RequestMapping(value = "/todo-list")
	public String getTodos(ModelMap modelMap) {

		modelMap.put("todos", todoService.retrieveTodos(retrieveLoggedinUserName()));

		return "todo-list";
	}


 
	@RequestMapping(value = "/add-todo")
	public String addTodo(ModelMap modelMap) {
		
//		throw new RuntimeException("my new custom exception");
		
		modelMap.addAttribute("todo", new Todo(0, retrieveLoggedinUserName(), "", new Date(), false));
		return "todo";
	} 
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors())
			return "todo";

		todoService.addTodo((String) model.get("name"), todo.getDesc(), new Date(),
				false);
		model.clear();// to prevent request parameter "name" to be passed
		return "redirect:todo-list";
	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(ModelMap modelMap, @RequestParam int id) {  
		todoService.deleteTodo(id);
		modelMap.clear();
		return "redirect:todo-list";
	} 
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String updateTodoGet(ModelMap model,@RequestParam int id) {
			Todo tododata = todoService.retrieveTodo(id);
			model.addAttribute("todo", tododata); 
			return "todo";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		todo.setUser(retrieveLoggedinUserName());
		
		if (result.hasErrors())
			return "todo";

		todoService.updateTodo(todo);
		return "redirect:todo-list";
	}
	


	private String retrieveLoggedinUserName() {
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		
		return principal.toString();
	}
	
		
	private Log logger=LogFactory.getLog(ExceptionController.class);
	
		@ExceptionHandler(value=Exception.class)
		public String handleException(HttpServletRequest request,Exception e) {
			
			logger.error("Request : "+request.getRequestURL()+" threw exception ",e);
			
			return "error-specific";
			
		}
	
}
