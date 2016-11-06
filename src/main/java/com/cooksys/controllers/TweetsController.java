package com.cooksys.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Credentials;
import com.cooksys.entity.TweetPost;
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
	public TweetProjection getTweet(@PathVariable Long id) throws Exception {
		return tweetsService.getTweet(id);
	}
	
	@GetMapping("/{id}/tags")
	public List<HashtagProjection> getTags(@PathVariable Long id) throws Exception {
		return tweetsService.getTags(id);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserProjection> getLikes(@PathVariable Long id) throws Exception {
		return tweetsService.getLikes(id);
	}
	
	@GetMapping("/{id}/context")
	public ContextProjection getContext(@PathVariable Long id) throws Exception {
		return tweetsService.getContext(id);
	}
	
	@GetMapping("/{id}/replies")
	public List<TweetProjection> getReplies(@PathVariable Long id) throws Exception {
		return tweetsService.getReplies(id);
	}
	
	@GetMapping("/{id}/reposts")
	public List<TweetProjection> getReposts(@PathVariable Long id) throws Exception {
		return tweetsService.getReposts(id);
	}
	
	@GetMapping("/{id}/mentions")
	public List<UserProjection> getMentions(@PathVariable Long id) throws Exception {
		return tweetsService.getMentions(id);
	}
	
	@PostMapping
	public TweetProjection postTweet(@RequestBody TweetPost post) throws Exception {
		return tweetsService.postTweet(post);
	}
	
	@PostMapping("/{id}/like")
	public void postLike(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		tweetsService.postLike(id, credentials);
	}
	
	@PostMapping("/{id}/reply")
	public TweetProjection postReply(@PathVariable Long id, @RequestBody TweetPost post) throws Exception {
		return tweetsService.postReply(id, post);
	}
	
	@PostMapping("/{id}/repost")
	public TweetProjection postRepost(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		return tweetsService.postRepost(id, credentials);
	}
	
	@DeleteMapping("/{id}")
	public TweetProjection deleteTweet(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		return tweetsService.deleteTweet(id, credentials);
	}
	
}
