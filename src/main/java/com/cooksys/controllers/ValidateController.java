package com.cooksys.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.User;
import com.cooksys.service.ValidateService;

/**
 * Created November 6, 2016. Intended use is to handle requests from
 * end user that deal with validation of information inside the linked DataBase
 * @author Cory Scott Turco
 */
@RestController
@RequestMapping("validate")
public class ValidateController {

	private ValidateService validateService;
	
	public ValidateController(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	/**
	 * Checks whether or not a given {@link Hashtag} exists.
	 * @param label {@link String} to search by
	 * @return {@link Boolean} that is true if a {@link Hashtag} with the give label is found, else false
	 */
	@GetMapping("/tag/exists/{label}")
	public Boolean hashtagExists(@PathVariable String label) {
		return validateService.hashtagExists(label);
	}
	
	/**
	 * Checks whether or not a given username exists.
	 * @param username {@link String} to search by
	 * @return {@link Boolean} that is true if a non deleted {@link User} is found with the given username, else false
	 */
	@GetMapping("/username/exists/@{username}")
	public Boolean usernameExists(@PathVariable String username) {
		return validateService.usernameExists(username);
	}
	
	/**
	 * Checks whether or not a given username is available.
	 * @param username {@link String} to search by
	 * @return {@link Boolean} that is true if no {@link User} is found (deleted or not) with the given username, else false
	 */
	@GetMapping("/username/available/@{username}")
	public Boolean usernameAvailable(@PathVariable String username) {
		return validateService.usernameAvailable(username);
	}
}