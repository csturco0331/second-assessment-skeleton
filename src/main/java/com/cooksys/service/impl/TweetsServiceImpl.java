package com.cooksys.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.entity.Credentials;
import com.cooksys.entity.Hashtag;
import com.cooksys.entity.Tweet;
import com.cooksys.entity.TweetPost;
import com.cooksys.entity.User;
import com.cooksys.projections.ContextProjection;
import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;
import com.cooksys.repository.HashtagRepository;
import com.cooksys.repository.TweetsRepository;
import com.cooksys.repository.UserRepository;
import com.cooksys.service.TweetsService;

@Service
public class TweetsServiceImpl implements TweetsService {

	TweetsRepository tweetsRepo;
	HashtagRepository hashtagRepo;
	UserRepository userRepo;
	
	public TweetsServiceImpl(TweetsRepository tweetsRepo, HashtagRepository hashtagRepo, UserRepository userRepo) {
		this.tweetsRepo = tweetsRepo;
		this.hashtagRepo = hashtagRepo;
		this.userRepo = userRepo;
	}
	
	@Override
	public List<TweetProjection> getTweets() {
		return tweetsRepo.findByDeletedFlagOrderByPostedAsc(false);
	}

	@Override
	public TweetProjection getTweet(Long id) throws Exception {
		TweetProjection tweet = tweetsRepo.findByIdAndDeletedFlag(id, false);
		if(tweet == null) throw new Exception("No Tweet found");
		return tweet;
	}

	@Override
	public List<HashtagProjection> getTags(Long id) throws Exception {
		if(tweetsRepo.findByIdAndDeletedFlag(id, false) == null) throw new Exception("No Tweet found");
		return hashtagRepo.findHashtagsByTweets_IdAndTweets_DeletedFlag(id, false);
	}

	@Override
	public List<UserProjection> getLikes(Long id) throws Exception {
		if(tweetsRepo.findByIdAndDeletedFlag(id, false) == null) throw new Exception("No Tweet found");
		return userRepo.findLikesByLiked_IdAndDeletedFlag(id, false);
	}

	@Override
	public ContextProjection getContext(Long id) throws Exception {
		throw new Exception("Not Implemented");
	}

	@Override
	public List<TweetProjection> getReplies(Long id) throws Exception {
		if(tweetsRepo.findByIdAndDeletedFlag(id, false) == null) throw new Exception("No Tweet found");
		return tweetsRepo.findRepliesByIdAndDeletedFlagAndReplies_DeletedFlag(id, false, false);
	}

	@Override
	public List<TweetProjection> getReposts(Long id) throws Exception {
		if(tweetsRepo.findByIdAndDeletedFlag(id, false) == null) throw new Exception("No Tweet found");
		return tweetsRepo.findRepostsByIdAndDeletedFlagAndReposts_DeletedFlag(id, false, false);
	}

	@Override
	public List<UserProjection> getMentions(Long id) throws Exception {
		if(tweetsRepo.findByIdAndDeletedFlag(id, false) == null) throw new Exception("No Tweet found");
		return userRepo.findMentionsByMentioned_IdAndDeletedFlag(id, false);
	}

	@Override
	@Transactional
	public TweetProjection postTweet(TweetPost post) throws Exception {
		User user = userRepo.findFirstByUsername(post.getCredentials().getUsername());
		if(user == null || user.getDeletedFlag() || !user.getCredentials().getPassword().equals(post.getCredentials().getPassword()))
			throw new Exception("Credentials do not match or User not found");
		Tweet tweet = new Tweet(user, new Timestamp(System.currentTimeMillis()), post.getContent(), parseTags(post.getContent()), parseMentions(post.getContent()));
		Tweet result = tweetsRepo.saveAndFlush(tweet);
		return tweetsRepo.findByIdAndDeletedFlag(result.getId(), false);
	}
	
	@Transactional
	private List<User> parseMentions(String content) throws Exception {
		List<User> users = new ArrayList<>();
		Matcher m = Pattern.compile("(\\s|\\A)@(\\w+)").matcher(content);
		while(m.find()) {
			String username = m.group();
			users.add(userRepo.findFirstByUsername(username.substring(1)));
		}
		users.removeAll(Collections.singleton(null));
		return users;
	}

	@Transactional
	private List<Hashtag> parseTags(String content) throws Exception {
		List<Hashtag> hashtags = new ArrayList<>();
		Matcher m = Pattern.compile("(\\s|\\A)#(\\w+)").matcher(content);
		while(m.find()) {
			String label = m.group().substring(1);
			Hashtag hashtag = hashtagRepo.findFirstByLabel(label);
			if(hashtag == null) hashtag = new Hashtag(label, new Timestamp(System.currentTimeMillis()));
			else hashtag.setLastUsed(new Timestamp(System.currentTimeMillis()));
			hashtags.add(hashtag);
		}
		return hashtags;
	}

	@Override
	@Transactional
	public TweetProjection postReply(Long id, TweetPost post) throws Exception {
		User user = userRepo.findFirstByUsername(post.getCredentials().getUsername());
		if(user == null || user.getDeletedFlag() || !user.getCredentials().getPassword().equals(post.getCredentials().getPassword()))
			throw new Exception("Credentials do not match or User not found");
		Tweet retrieved = tweetsRepo.findFirstByIdAndDeletedFlag(id, false);
		if (retrieved == null) throw new Exception("No Tweet found");
		Tweet tweet = new Tweet(user, new Timestamp(System.currentTimeMillis()), post.getContent(), parseTags(post.getContent()), parseMentions(post.getContent()));
		tweet.setInReplyTo(retrieved);
		Tweet result = tweetsRepo.saveAndFlush(tweet);
		return tweetsRepo.findByIdAndDeletedFlag(result.getId(), false);
	}

	@Override
	@Transactional
	public TweetProjection postRepost(Long id, Credentials credentials) throws Exception {
		User user = userRepo.findFirstByUsername(credentials.getUsername());
		if(user == null || user.getDeletedFlag() || !user.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match or User not found");
		Tweet retrieved = tweetsRepo.findFirstByIdAndDeletedFlag(id, false);
		if (retrieved == null) throw new Exception("No Tweet found");
		Tweet post = new Tweet(user, new Timestamp(System.currentTimeMillis()), retrieved);	//Should the hashtags and mentions be imported?
		post = tweetsRepo.saveAndFlush(post);
		return tweetsRepo.findByIdAndDeletedFlag(post.getId(), false);
	}

	@Override
	@Transactional
	public TweetProjection deleteTweet(Long id, Credentials credentials) throws Exception {
		User user = userRepo.findFirstByUsername(credentials.getUsername());
		if(user == null || user.getDeletedFlag() || !user.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match or not found");
		Tweet tweet = tweetsRepo.findFirstByIdAndDeletedFlag(id, false);
		if(tweet == null) throw new Exception("No Tweet found");
		tweet.setDeletedFlag(true);
		tweet = tweetsRepo.saveAndFlush(tweet);
		return tweetsRepo.findByIdAndDeletedFlag(tweet.getId(), true);
	}

}
