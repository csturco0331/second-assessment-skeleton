package com.cooksys.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.projections.ContextProjection;
import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;
import com.cooksys.service.TweetsService;

@RestController
@RequestMapping("tweets")
public class TweetsController {

	private TweetsService tweetsService;

	public TweetsController(TweetsService tweetsService) {
		this.tweetsService = tweetsService;
	}
	
	@GetMapping
	public List<TweetProjection> getTweets() {
		return tweetsService.getTweets();
	}
	
	@GetMapping("/{id}")
	public TweetProjection getTweet(@PathVariable Long id) {
		return tweetsService.getTweet(id);
	}
	
	@GetMapping("/{id}/tags")
	public List<HashtagProjection> getTags(@PathVariable Long id) {
		return tweetsService.getTags(id);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserProjection> getLikes(@PathVariable Long id) {
		return tweetsService.getLikes(id);
	}
	
//	@GetMapping("/{id}/context")
//	public ContextProjection getContext(@PathVariable Long id) {
//		return tweetsService.getContext(id);
//	}
	
	@GetMapping("/{id}/replies")
	public List<TweetProjection> getReplies(@PathVariable Long id) {
		return tweetsService.getReplies(id);
	}
	
	@GetMapping("/{id}/reposts")
	public List<TweetProjection> getReposts(@PathVariable Long id) {
		return tweetsService.getReposts(id);
	}
	
	@GetMapping("/{id}/mentions")
	public List<UserProjection> getMentions(@PathVariable Long id) {
		return tweetsService.getMentions(id);
	}
//	
//	@PostMapping
//	public TweetProjection postTweet(@RequestBody String content, @RequestBody CredentialsProjection credentials) {
//		return tweetsService.postTweet(content, credentials);
//	}
//	
//	@PostMapping("/{id}/reply")
//	public TweetProjection postReply(@PathVariable Long id, @RequestBody String content, @RequestBody CredentialsProjection credentials) {
//		return tweetsService.postReply(id, content, credentials);
//	}
//	
//	@PostMapping("/{id}/repost")
//	public TweetProjection postRepost(@PathVariable Long id, @RequestBody CredentialsProjection credentials) {
//		return tweetsService.postRepost(id, credentials);
//	}
//	
//	@DeleteMapping("/{id}")
//	public TweetProjection deleteTweet(@PathVariable Long id) {
//		return tweetsService.deleteTweet(id);
//	}
	
}
