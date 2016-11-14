package com.cooksys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Credentials;
import com.cooksys.entity.Profile;
import com.cooksys.entity.User;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;
import com.cooksys.service.UserService;

/**
 * Created November 6, 2016. Intended use is to handle requests from
 * end user that deal with {@link User}s inside of the linked DataBase
 * @author Cory Scott Turco
 */
@CrossOrigin
@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Retrieves all active (non-deleted) {@link UserProjection}s as a {@link List}.
	 * @return {@link List}<{@link UserProjection}>
	 */
	@GetMapping
	public List<UserProjection> getUsers() {
		return userService.getUsers();
	}
	
	/**
	 * Retrieves a {@link UserProjection} with the given username. 
	 * If no such user exists or is deleted, an error should be sent in lieu of a response.
	 * @param username {@link String} to search by
	 * @return {@link UserProjection}
	 * @throws Exception
	 */
	@GetMapping("/@{username}")
	public UserProjection getUser(@PathVariable String username) throws Exception {
		return userService.getUser(username);
	}
	
	/**
	 * Retrieves all (non-deleted) {@link TweetProjection}s authored by the {@link User} with the given username, 
	 * as well as all (non-deleted) tweets authored by users the given user is following. 
	 * This includes simple tweets, reposts, and replies. The tweets should appear in 
	 * reverse-chronological order. If no active user with that username exists (deleted 
	 * or never created), an error should be sent in lieu of a response.
	 * @param username {@link String} to search by
	 * @return {@link List}<{@link TweetProjection}>
	 * @throws Exception
	 */
	@GetMapping("/@{username}/feed")
	public List<TweetProjection> getFeed(@PathVariable String username) throws Exception {
		return userService.getFeed(username);
	}
	
	/**
	 * Retrieves all (non-deleted) {@link TweetProjection}s authored by the {@link User} with the given username. 
	 * This includes simple tweets, reposts, and replies. The tweets should appear in reverse-chronological
	 *  order. If no active user with that username exists (deleted or never created), an error should be 
	 *  sent in lieu of a response.
	 * @param username {@link String} to search by
	 * @return {@link List}<{@link TweetProjection}>
	 * @throws Exception
	 */
	@GetMapping("/@{username}/tweets")
	public List<TweetProjection> getTweets(@PathVariable String username) throws Exception {
		return userService.getTweets(username);
	}
	
	/**
	 * Retrieves all (non-deleted) {@link TweetProjection}s in which the {@link User} with the given username is mentioned.
	 * The tweets should appear in reverse-chronological order. If no active user with that username exists, an 
	 * error should be sent in lieu of a response. A user is considered "mentioned" by a tweet if the tweet has 
	 * content and the user's username appears in that content following a @.
	 * @param username {@link String} to search by
	 * @return {@link List}<{@link TweetProjection}>
	 * @throws Exception
	 */
	@GetMapping("/@{username}/mentions")
	public List<TweetProjection> getMentions(@PathVariable String username) throws Exception {
		return userService.getMentions(username);
	}
	
	/**
	 * Retrieves the followers of the {@link User} with the given username. Only active users should be 
	 * included in the response. If no active user with the given username exists, an error should be 
	 * sent in lieu of a response.
	 * @param username {@link String} to search by
	 * @return {@link List}<{@link UserProjection}>
	 * @throws Exception
	 */
	@GetMapping("/@{username}/followers")
	public List<UserProjection> getFollowers(@PathVariable String username) throws Exception {
		return userService.getFollowers(username);
	}
	
	/**
	 * Retrieves the users followed by the {@link User} with the given username. Only active users should be 
	 * included in the response. If no active user with the given username exists, an error should be 
	 * sent in lieu of a response.
	 * @param username {@link String} to search by
	 * @return {@link List}<{@link UserProjection}>
	 * @throws Exception
	 */
	@GetMapping("/@{username}/following")
	public List<UserProjection> getFollowing(@PathVariable String username) throws Exception {
		return userService.getFollowing(username);
	}
	
	/**
	 * Creates a new {@link User}. If any required fields are missing or the username provided is already taken, 
	 * an error should be sent in lieu of a response. If the given {@link Credentials} match a previously-deleted user, 
	 * re-activate the deleted user instead of creating a new one.
	 * @param user {@link User} with {@link Credentials} and {@link Profile}
	 * @return {@link UserProjection}
	 * @throws Exception
	 */
	@PostMapping
	public UserProjection postUser(@RequestBody User user) throws Exception {
		return userService.postUser(user);
	}
	
	/**
	 * Subscribes the {@link User} whose {@link Credentials} are provided by the request body to the user whose username 
	 * is given in the url. If there is already a following relationship between the two users, no such followable user 
	 * exists (deleted or never created), or the credentials provided do not match an active user in the database, an 
	 * error should be sent as a response. If successful, no data is sent.
	 * @param username {@link String}
	 * @param credentials {@link Credentials}
	 * @throws Exception
	 */
	@PostMapping("/@{username}/follow")
	public void postFollow(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
		userService.postFollow(username, credentials);
	}
	
	/**
	 * Unsubscribes the {@link User} whose {@link Credentials} are provided by the request body from the user whose username
	 * is given in the url. If there is no preexisting following relationship between the two users, no such followable user 
	 * exists (deleted or never created), or the credentials provided do not match an active user in the database, an error 
	 * should be sent as a response. If successful, no data is sent.
	 * @param username {@link String}
	 * @param credentials {@link Credentials}
	 * @throws Exception
	 */
	@PostMapping("/@{username}/unfollow")
	public void postUnfollow(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
		userService.postUnfollow(username, credentials);
	}
	
	/**
	 * Updates the {@link Profile} of a {@link User} with the given username. If no such user exists, the user is deleted, 
	 * or the provided {@link Credentials} do not match the user, an error should be sent in lieu of a response. In the case of a 
	 * successful update, the returned user should contain the updated data.
	 * @param username {@link String}
	 * @param user	{@link User} with {@link Credentials} and {@link Profile}
	 * @return {@link UserProjection}
	 * @throws Exception
	 */
	@PatchMapping("/@{username}")
	public UserProjection patchUser(@PathVariable String username, @RequestBody User user) throws Exception {
		return userService.patchUser(username, user);
	}
	
	/**
	 * "Deletes" a {@link User} with the given username. If no such user exists or the provided {@link Credentials} do not match the user, 
	 * an error should be sent in lieu of a response. If a user is successfully "deleted", the response should contain the user data prior 
	 * to deletion. IMPORTANT: This action should not actually drop any records from the database! Instead, develop a way to keep track of 
	 * "deleted" users so that if a user is re-activated, all of their tweets and information are restored.
	 * @param username {@link String}
	 * @param credentials {@link Credentials}
	 * @return {@link UserProjection}
	 * @throws Exception
	 */
	@DeleteMapping("/@{username}")
	public UserProjection deleteUser(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
		return userService.deleteUser(username, credentials);
	}
	
}
