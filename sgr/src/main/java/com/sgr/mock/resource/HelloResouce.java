package com.sgr.mock.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResouce {
	
	@GetMapping("/heroku")
	public String heyHeroku() {
		return "This first Heroku cloud app";
		
	}

}
