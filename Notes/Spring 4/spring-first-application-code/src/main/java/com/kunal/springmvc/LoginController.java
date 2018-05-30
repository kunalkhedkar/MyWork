package com.kunal.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kunal.services.LoginService;

@Controller
@SessionAttributes("name")
public class LoginController {

	@Autowired
	LoginService loginservice;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String sayHello(ModelMap modelMap) {
		modelMap.put("name", "kunal");
		return "welcome";
		// return "/views/login.jsp";    
	}

//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String handleLoginRequest(@RequestParam String name, @RequestParam String password, ModelMap model) {
//
//		boolean result=loginservice.auth(name, password);
//		
//		if (result) {
//			model.put("name", name);
//			return "welcome"; 
//		}
//		
//		model.put("erroressage", "Invalid username or password");
//		return "login";
//
//	}
}
