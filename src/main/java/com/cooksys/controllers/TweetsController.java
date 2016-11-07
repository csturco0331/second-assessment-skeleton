package com.cooksys.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Context;
import com.cooksys.entity.Credentials;
import com.cooksys.entity.Tweet;
import com.cooksys.entity.User;
import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;
import com.cooksys.service.TweetsService;

/**
 * Created November 6, 2016. Intended use is to handle requests from
 * end user that deal with {@link Tweet}s inside of the linked DataBase
 * @author Cory Scott Turco
 */
@RestController
@RequestMapping("tweets")
public class TweetsController {

	private TweetsService tweetsService;

	public TweetsController(TweetsService tweetsService) {
		this.tweetsService = tweetsService;
	}
	
	/**
	 * Retrieves all (non-deleted) {@link TweetProjection}s. The tweets should appear in reverse-chronological order.
	 * @return {@link List}<{@link TweetProjection}>
	 */
	@GetMapping
	public List<TweetProjection> getTweets() {
		return tweetsService.getTweets();
	}
	
	/**
	 * Retrieves a {@link TweetProjection} with a given id. If no such tweet exists, or the given tweet is deleted, 
	 * an error should be sent in lieu of a response.
	 * @param id {@link Long}
	 * @return {@link TweetProjection}
	 * @throws Exception
	 */
	@GetMapping("/{id}")
	public TweetProjection getTweet(@PathVariable Long id) throws Exception {
		return tweetsService.getTweet(id);
	}
	
	/**
	 * Retrieves the {@link HashtagProjection}s associated with the {@link Tweet} with the given id. If that tweet 
	 * is deleted or otherwise doesn't exist, an error should be sent in lieu of a response. IMPORTANT Remember that 
	 * tags and mentions must be parsed by the server!
	 * @param id {@link Long}
	 * @return {@link List}<{@link HashtagProjection}>
	 * @throws Exception
	 */
	@GetMapping("/{id}/tags")
	public List<HashtagProjection> getTags(@PathVariable Long id) throws Exception {
		return tweetsService.getTags(id);
	}
	
	/**
	 * Retrieves the active {@link UserProjection}s who have liked the {@link Tweet} with the given id. If that tweet 
	 * is deleted or otherwise doesn't exist, an error should be sent in lieu of a response. Deleted users should be 
	 * excluded from the response.
	 * @param id {@link Long}
	 * @return {@link List}<{@link UserProjection}>
	 * @throws Exception
	 */
	@GetMapping("/{id}/likes")
	public List<UserProjection> getLikes(@PathVariable Long id) throws Exception {
		return tweetsService.getLikes(id);
	}
	
	/**
	 * Retrieves the {@link Context} of the {@link Tweet} with the given id. If that tweet is deleted 
	 * or otherwise doesn't exist, an error should be sent in lieu of a response. IMPORTANT: While deleted tweets 
	 * should not be included in the before and after properties of the result, transitive replies should. What that 
	 * means is that if a reply to the target of the context is deleted, but there's another reply to the deleted 
	 * reply, the deleted reply should be excluded but the other reply should remain.
	 * @param id {@link Long}
	 * @return {@link Context}
	 * @throws Exception
	 */
	@GetMapping("/{id}/context")
	public Context getContext(@PathVariable Long id) throws Exception {
		return tweetsService.getContext(id);
	}
	
	/**
	 * Retrieves the direct replies to the {@link Tweet} with the given id. If that tweet is deleted or 
	 * otherwise doesn't exist, an error should be sent in lieu of a response. Deleted replies to the tweet 
	 * should be excluded from the response.
	 * @param id {@link Long}
	 * @return	{@link List}<{@link TweetProjection}>
	 * @throws Exception
	 */
	@GetMapping("/{id}/replies")
	public List<TweetProjection> getReplies(@PathVariable Long id) throws Exception {
		return tweetsService.getReplies(id);
	}
	
	/**
	 * Retrieves the direct reposts of the {@link Tweet} with the given id. If that tweet is deleted or 
	 * otherwise doesn't exist, an error should be sent in lieu of a response. Deleted reposts of the tweet 
	 * should be excluded from the response.
	 * @param id {@link Long}
	 * @return	{@link List}<{@link TweetProjection}>
	 * @throws Exception
	 */
	@GetMapping("/{id}/reposts")
	public List<TweetProjection> getReposts(@PathVariable Long id) throws Exception {
		return tweetsService.getReposts(id);
	}
	
	/**
	 * Retrieves the {@link UserProjection}s mentioned in the {@link Tweet} with the given id. If that tweet is 
	 * deleted or otherwise doesn't exist, an error should be sent in lieu of a response. Deleted users should 
	 * be excluded from the response. IMPORTANT Remember that tags and mentions must be parsed by the server!
	 * @param id {@link Long}
	 * @return	{@link List}<{@link TweetProjection}>
	 * @throws Exception
	 */
	@GetMapping("/{id}/mentions")
	public List<UserProjection> getMentions(@PathVariable Long id) throws Exception {
		return tweetsService.getMentions(id);
	}
	
	/**
	 * Creates a new simple {@link Tweet}, with the author set to the {@link User} identified by the {@link Credentials} in the 
	 * request body. If the given credentials do not match an active user in the database, an error should be sent in lieu of a 
	 * response. The response should contain the newly-created tweet. Because this always creates a simple tweet, it must have a 
	 * content property and may not have inReplyTo or repostOf properties. IMPORTANT: when a tweet with content is created, the 
	 * server must process the tweet's content for @{username} mentions and #{hashtag} tags. There is no way to create hashtags 
	 * or create mentions from the API, so this must be handled automatically!
	 * @param post {link @Tweet} with {@link String} content and {@link Credentials}
	 * @return {@link TweetProjection}
	 * @throws Exception
	 */
	@PostMapping
	public TweetProjection postTweet(@RequestBody Tweet post) throws Exception {
		return tweetsService.postTweet(post);
	}
	
	/**
	 * Creates a "like" relationship between the {@link Tweet} with the given id and the {@link User} whose 
	 * {@link Credentials} are provided by the request body. If the tweet is deleted or otherwise doesn't exist, 
	 * or if the given credentials do not match an active user in the database, an error should be sent. Following 
	 * successful completion of the operation, no response body is sent.
	 * @param id {@link Long}
	 * @param credentials {@link Credentials}
	 * @throws Exception
	 */
	@PostMapping("/{id}/like")
	public void postLike(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		tweetsService.postLike(id, credentials);
	}
	
	/**
	 * Removes a "like" relationship between the {@link Tweet} with the given id and the {@link User} whose 
	 * {@link Credentials} are provided by the request body. If the tweet is deleted or otherwise doesn't exist, 
	 * or if the given credentials do not match an active user in the database, an error should be sent. Following 
	 * successful completion of the operation, no response body is sent.
	 * @param id {@link Long}
	 * @param credentials {@link Credentials}
	 * @throws Exception
	 */
	@PostMapping("/{id}/unlike")
	public void postUnlike(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		tweetsService.postUnlike(id, credentials);
	}
	
	/**
	 * Creates a reply {@link Tweet} to the tweet with the given id. The author of the newly-created tweet 
	 * should match the {@link Credentials} provided by the request body. If the given tweet is deleted or 
	 * otherwise doesn't exist, or if the given credentials do not match an active user in the database, an 
	 * error should be sent in lieu of a response. Because this creates a reply tweet, content is not optional. 
	 * Additionally, notice that the inReplyTo property is not provided by the request. The server must create 
	 * that relationship. The response should contain the newly-created tweet. IMPORTANT: when a tweet with 
	 * content is created, the server must process the tweet's content for @{username} mentions and #{hashtag} 
	 * tags. There is no way to create hashtags or create mentions from the API, so this must be handled 
	 * automatically!
	 * @param id {@link Long}
	 * @param post {@link Tweet} with {@link String} content and {@link Credentials}
	 * @return {@link TweetProjection}
	 * @throws Exception
	 */
	@PostMapping("/{id}/reply")
	public TweetProjection postReply(@PathVariable Long id, @RequestBody Tweet post) throws Exception {
		return tweetsService.postReply(id, post);
	}
	
	/**
	 * Creates a repost of the {@link Tweet} with the given id. The author of the repost should match the {@link Credentials} 
	 * provided in the request body. If the given tweet is deleted or otherwise doesn't exist, or the given credentials do not 
	 * match an active user in the database, an error should be sent in lieu of a response. Because this creates a repost 
	 * tweet, content is not allowed. Additionally, notice that the repostOf property is not provided by the request. The 
	 * server must create that relationship. The response should contain the newly-created tweet.
	 * @param id {@link Long}
	 * @param credentials {@link Credentials}
	 * @return {@link TweetProjection}
	 * @throws Exception
	 */
	@PostMapping("/{id}/repost")
	public TweetProjection postRepost(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		return tweetsService.postRepost(id, credentials);
	}
	
	/**
	 * "Deletes" the {@link Tweet} with the given id. If no such tweet exists or the provided {@link Credentials} 
	 * do not match author of the tweet, an error should be sent in lieu of a response. If a tweet is successfully 
	 * "deleted", the response should contain the tweet data prior to deletion. IMPORTANT: This action should not 
	 * actually drop any records from the database! Instead, develop a way to keep track of "deleted" tweets so 
	 * that even if a tweet is deleted, data with relationships to it (like replies and reposts) are still intact.
	 * @param id {@link Long}
	 * @param credentials {@link Credentials}
	 * @return {@link TweetProjection}
	 * @throws Exception
	 */
	@DeleteMapping("/{id}")
	public TweetProjection deleteTweet(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		return tweetsService.deleteTweet(id, credentials);
	}
	
}
