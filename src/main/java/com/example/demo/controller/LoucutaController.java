package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loucura") 
public class LoucutaController {
	
	 @GetMapping
		public String getUsuario() {
			
	    	return "Acessou";

		}


}
