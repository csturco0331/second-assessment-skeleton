package com.cooksys.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.service.ValidateService;

@RestController
@RequestMapping("validate")
public class ValidateController {

	private ValidateService validateService;
	
	public ValidateController(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	@GetMapping("/tag/exists/{label}")
	public Boolean hashtagExists(@PathVariable String label) {
		return validateService.hashtagExists(label);
	}
	
	@GetMapping("/username/exists/@{username}")
	public Boolean usernameExists(@PathVariable String username) {
		return validateService.usernameExists(username);
	}
	
	@GetMapping("/username/available/@{username}")
	public Boolean usernameAvailable(@PathVariable String username) {
		return validateService.usernameAvailable(username);
	}
}