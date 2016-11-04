package com.cooksys.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.User;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<UserProjection> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/@{username}")
	public UserProjection getUser(@PathVariable String username) {
		return userService.getUser(username);
	}
	
//	@GetMapping("/@{username}/feed")
//	public List<TweetProjection> getFeed(@PathVariable String username) {
//		return userService.getFeed(username);
//	}
	
	@GetMapping("/@{username}/tweets")
	public List<TweetProjection> getTweets(@PathVariable String username) {
		return userService.getTweets(username);
	}
	
	@GetMapping("/@{username}/mentions")
	public List<TweetProjection> getMentions(@PathVariable String username) {
		return userService.getMentions(username);
	}
	
	@GetMapping("/@{username}/followers")
	public List<UserProjection> getFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}
	
	@GetMapping("/@{username}/following")
	public List<UserProjection> getFollowing(@PathVariable String username) {
		return userService.getFollowing(username);
	}
	
	@PostMapping
	public UserProjection postUser(@RequestBody User user) {
		return userService.postUser(user);
	}
//	
//	@PostMapping("/@{username}/follow")
//	public void postFollow(@PathVariable String username, @RequestBody CredentialsProjection credentials) {
//		userService.postFollow(username, credentials);
//	}
//	
//	@PostMapping("/@{username}/unfollow")
//	public void postUnfollow(@PathVariable String username, @RequestBody CredentialsProjection credentials) {
//		userService.postUnfollow(username, credentials);
//	}
//	
//	@PatchMapping("/@{username}")
//	public UserProjection patchUser(@PathVariable String username, @RequestBody CredentialsProjection credentials, @RequestBody ProfileProjection profile) {
//		return userService.patchUser(username, credentials, profile);
//	}
//	
//	@DeleteMapping("/@{username}")
//	public UserProjection deleteUser(@PathVariable String username, @RequestBody CredentialsProjection credentials) {
//		return userService.deleteUser(username, credentials);
//	}
	
}
