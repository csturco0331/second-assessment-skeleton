package com.cooksys.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.entity.Context;
import com.cooksys.entity.Credentials;
import com.cooksys.entity.Hashtag;
import com.cooksys.entity.Tweet;
import com.cooksys.entity.User;
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
		return tweetsRepo.findByDeletedFlagFalseOrderByPostedDesc();
	}

	@Override
	public TweetProjection getTweet(Long id) throws Exception {
		TweetProjection tweet = tweetsRepo.findByIdAndDeletedFlag(id, false);
		if (tweet == null)
			throw new Exception("No Tweet found");
		return tweet;
	}

	@Override
	public List<HashtagProjection> getTags(Long id) throws Exception {
		if (tweetsRepo.findByIdAndDeletedFlag(id, false) == null)
			throw new Exception("No Tweet found");
		return hashtagRepo.findHashtagsByHashtagTweets_IdAndHashtagTweets_DeletedFlagFalse(id);
	}

	@Override
	public List<UserProjection> getLikes(Long id) throws Exception {
		if (tweetsRepo.findByIdAndDeletedFlag(id, false) == null)
			throw new Exception("No Tweet found");
		return userRepo.findLikesByLiked_IdAndDeletedFlagFalse(id);
	}

	@Override
	public Context getContext(Long id) throws Exception {
		if (tweetsRepo.findByIdAndDeletedFlag(id, false) == null)
			throw new Exception("No Tweet found");
		TweetProjection target = tweetsRepo.findById(id);
		List<TweetProjection> before = new LinkedList<>();
		if (target.getInReplyTo() != null) {
			TweetProjection temp = tweetsRepo.findById(target.getInReplyTo().getId());
			while (true) {
				if(tweetsRepo.findByIdAndDeletedFlag(temp.getId(), false) != null)
					before.add(temp);
				if(temp.getInReplyTo() != null)
					temp = tweetsRepo.findById(temp.getInReplyTo().getId());
				else break;
			}
		}
		List<TweetProjection> after = getAfter(tweetsRepo.findFirstByIdAndDeletedFlagFalse(target.getId()));
		after.sort(TweetProjection.sortByPosted());
		return new Context(target, before, after);
	}

	private List<TweetProjection> getAfter(Tweet target) throws Exception {
		List<Tweet> root = tweetsRepo.findByInReplyTo_Id(target.getId());
		List<TweetProjection> results = new LinkedList<>();
		for(Tweet tweet : root) {
			if(tweet.getDeletedFlag() == false)
				results.add(tweetsRepo.findByIdAndDeletedFlag(tweet.getId(), false));
			results.addAll(getAfter(tweet));
		}
		return results;
	}

	@Override
	public List<TweetProjection> getReplies(Long id) throws Exception {
		if (tweetsRepo.findByIdAndDeletedFlag(id, false) == null)
			throw new Exception("No Tweet found");
		return tweetsRepo.findByInReplyTo_IdAndDeletedFlagFalse(id);
	}

	@Override
	public List<TweetProjection> getReposts(Long id) throws Exception {
		if (tweetsRepo.findByIdAndDeletedFlag(id, false) == null)
			throw new Exception("No Tweet found");
		return tweetsRepo.findByRepostOf_IdAndDeletedFlagFalse(id);
	}

	@Override
	public List<UserProjection> getMentions(Long id) throws Exception {
		if (tweetsRepo.findByIdAndDeletedFlag(id, false) == null)
			throw new Exception("No Tweet found");
		return userRepo.findMentionsByMentioned_IdAndDeletedFlagFalse(id);
	}

	@Override
	@Transactional
	public TweetProjection postTweet(Tweet post) throws Exception {
		User user = userRepo.findFirstByUsernameAndDeletedFlagFalse(post.getCredentials().getUsername());
		if (user == null || !user.getCredentials().getPassword().equals(post.getCredentials().getPassword()))
			throw new Exception("Credentials do not match or User not found");
		Tweet tweet = new Tweet(user, new Timestamp(System.currentTimeMillis()), post.getContent(),
				parseTags(post.getContent()), parseMentions(post.getContent()));
		Tweet result = tweetsRepo.saveAndFlush(tweet);
		return tweetsRepo.findByIdAndDeletedFlag(result.getId(), false);
	}

	@Transactional
	private List<User> parseMentions(String content) throws Exception {
		List<User> users = new ArrayList<>();
		Matcher m = Pattern.compile("(\\s|\\A)@(\\w+)").matcher(content);
		while (m.find()) {
			String username = m.group();
			users.add(userRepo.findFirstByUsernameAndDeletedFlagFalse(username.substring(2)));
		}
		users.removeAll(Collections.singleton(null));
		return users;
	}

	@Transactional
	private List<Hashtag> parseTags(String content) throws Exception {
		Timestamp creation = new Timestamp(System.currentTimeMillis());
		List<Hashtag> hashtags = new ArrayList<>();
		Matcher m = Pattern.compile("(\\s|\\A)#(\\w+)").matcher(content);
		while (m.find()) {
			String label = m.group().substring(2);
			Hashtag hashtag = hashtagRepo.findFirstByLabelIgnoreCase(label);
			if (hashtag == null)
				hashtag = new Hashtag(label, creation);
			else
				hashtag.setLastUsed(creation);
			hashtagRepo.saveAndFlush(hashtag);
			hashtag = hashtagRepo.findFirstByLabelIgnoreCase(label);
			hashtags.add(hashtag);
		}
		return hashtags;
	}

	@Override
	@Transactional
	public void postLike(Long id, Credentials credentials) throws Exception {
		User user = userRepo.findFirstByUsernameAndDeletedFlagFalse(credentials.getUsername());
		if (user == null || !user.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match a User");
		Tweet tweet = tweetsRepo.findFirstByIdAndDeletedFlagFalse(id);
		if (tweet == null)
			throw new Exception("No Tweet found");
		if (tweet.getLikes().contains(user))
			return;
		tweet.getLikes().add(user);
		tweetsRepo.saveAndFlush(tweet);
	}

	@Override
	public void postUnlike(Long id, Credentials credentials) throws Exception {
		User user = userRepo.findFirstByUsernameAndDeletedFlagFalse(credentials.getUsername());
		if (user == null || !user.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match a User");
		Tweet tweet = tweetsRepo.findFirstByIdAndDeletedFlagFalse(id);
		if (tweet == null)
			throw new Exception("No Tweet found");
		if (!tweet.getLikes().contains(user))
			return;
		tweet.getLikes().remove(user);
		tweetsRepo.saveAndFlush(tweet);
	}
	
	@Override
	@Transactional
	public TweetProjection postReply(Long id, Tweet post) throws Exception {
		User user = userRepo.findFirstByUsernameAndDeletedFlagFalse(post.getCredentials().getUsername());
		if (user == null || !user.getCredentials().getPassword().equals(post.getCredentials().getPassword()))
			throw new Exception("Credentials do not match or User not found");
		Tweet retrieved = tweetsRepo.findFirstByIdAndDeletedFlagFalse(id);
		if (retrieved == null)
			throw new Exception("No Tweet found");
		Tweet tweet = new Tweet(user, new Timestamp(System.currentTimeMillis()), post.getContent(),
				parseTags(post.getContent()), parseMentions(post.getContent()));
		tweet.setInReplyTo(retrieved);
		Tweet result = tweetsRepo.saveAndFlush(tweet);
		return tweetsRepo.findByIdAndDeletedFlag(result.getId(), false);
	}

	@Override
	@Transactional
	public TweetProjection postRepost(Long id, Credentials credentials) throws Exception {
		User user = userRepo.findFirstByUsernameAndDeletedFlagFalse(credentials.getUsername());
		if (user == null || !user.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match or User not found");
		Tweet retrieved = tweetsRepo.findFirstByIdAndDeletedFlagFalse(id);
		if (retrieved == null)
			throw new Exception("No Tweet found");
		Tweet post = new Tweet(user, new Timestamp(System.currentTimeMillis()), retrieved); // Should
																							// the
																							// hashtags
																							// and
																							// mentions
																							// be
																							// imported?
		post = tweetsRepo.saveAndFlush(post);
		return tweetsRepo.findByIdAndDeletedFlag(post.getId(), false);
	}

	@Override
	@Transactional
	public TweetProjection deleteTweet(Long id, Credentials credentials) throws Exception {
		User user = userRepo.findFirstByUsernameAndDeletedFlagFalse(credentials.getUsername());
		if (user == null || !user.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match or not found");
		Tweet tweet = tweetsRepo.findFirstByIdAndDeletedFlagFalse(id);
		if (tweet == null)
			throw new Exception("No Tweet found");
		if (!tweet.getAuthor().getUsername().equals(credentials.getUsername())
				|| !tweet.getAuthor().getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Not the Author of this tweet");
		tweet.setDeletedFlag(true);
		tweet = tweetsRepo.saveAndFlush(tweet);
		return tweetsRepo.findByIdAndDeletedFlag(tweet.getId(), true);
	}

}
