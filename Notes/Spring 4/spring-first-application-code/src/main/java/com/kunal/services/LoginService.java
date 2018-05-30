package com.kunal.services;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	public boolean auth(String username,String password) {
		
		return username.equals("kunal") && password.equals("123");
	} 
	
	// NOT USING THIS 
	// Spring security
	
}
