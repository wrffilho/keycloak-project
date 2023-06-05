package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario") 
public class UsuarioController {

    @GetMapping
	public String getUsuario() {
		
    	return "Acessou";

	}
    
    @GetMapping("/usr")
   	public String salvar() {
   		
       	return "Acessou 2";

   	}
}
