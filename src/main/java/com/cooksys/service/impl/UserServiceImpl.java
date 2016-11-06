package com.cooksys.service.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.entity.Credentials;
import com.cooksys.entity.User;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;
import com.cooksys.repository.TweetsRepository;
import com.cooksys.repository.UserRepository;
import com.cooksys.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepo;
	TweetsRepository tweetsRepo;
	
	public UserServiceImpl(UserRepository userRepo, TweetsRepository tweetsRepo) {
		this.userRepo = userRepo;
		this.tweetsRepo = tweetsRepo;
	}
	
	@Override
	public List<UserProjection> getUsers() {
		return userRepo.findAllByDeletedFlag(false);
	}

	@Override
	public UserProjection getUser(String username) throws Exception {
		UserProjection user = userRepo.findByUsernameAndDeletedFlag(username, false);
		if (user == null) throw new Exception("Username not found");
		return user;
	}

	@Override
	public List<TweetProjection> getFeed(String username) throws Exception {
		List<TweetProjection> tweets = getTweets(username);
		List<User> whoIAmFollowing = userRepo.findAllWhoIAmFollowingByFollowers_UsernameAndDeletedFlag(username, false);
		for(User user : whoIAmFollowing) {
			tweets.addAll(getTweets(user.getUsername()));
		}
		tweets.sort(TweetProjection.sortByPosted());
		return tweets;
		
	}

	@Override
	public List<TweetProjection> getTweets(String username) throws Exception {
		if(userRepo.findByUsernameAndDeletedFlag(username, false) == null) throw new Exception("Username not found");
		return tweetsRepo.findTweetsByAuthor_UsernameAndAuthor_DeletedFlagOrderByPostedAsc(username, false);
	}

	@Override
	public List<TweetProjection> getMentions(String username) throws Exception {
		if(userRepo.findByUsernameAndDeletedFlag(username, false) == null) throw new Exception("Username not found");
		return tweetsRepo.findMentionedByMentions_UsernameAndMentions_DeletedFlagOrderByPostedAsc(username, false);
	}

	@Override
	public List<UserProjection> getFollowers(String username) throws Exception {
		if(userRepo.findByUsernameAndDeletedFlag(username, false) == null) throw new Exception("Username not found");
		return userRepo.findFollowersByWhoIAmFollowing_UsernameAndDeletedFlag(username, false);
	}

	@Override
	public List<UserProjection> getFollowing(String username) throws Exception {
		if(userRepo.findByUsernameAndDeletedFlag(username, false) == null) throw new Exception("Username not found");
		return userRepo.findWhoIAmFollowingByFollowers_UsernameAndDeletedFlag(username, false);
	}

	@Override
	@Transactional
	public UserProjection postUser(User user) throws Exception {
		if(user.getCredentials().getUsername() == null || user.getCredentials().getPassword() == null || user.getProfile().getEmail() == null)
			throw new Exception("Required field was null");
		user.setUsername(user.getCredentials().getUsername());
		user.setJoined(new Timestamp(System.currentTimeMillis()));
		User search = userRepo.findFirstByUsername(user.getUsername());
		if(search == null) {
			User temp = userRepo.saveAndFlush(user);
			return userRepo.findByUsername(temp.getUsername());
		} if(search.getDeletedFlag() && search.getCredentials().getPassword().equals(user.getCredentials().getPassword())) {
			search.setDeletedFlag(false);
			User temp = userRepo.saveAndFlush(search);
			return userRepo.findByUsername(temp.getUsername());
		}
		throw new Exception("Username already Exists");
	}

//	@Transactional
//	private UserProjection recoverUser(User user) {
//		user.setDeletedFlag(false);
//		List<Tweet> tweets = user.getTweets();
//		for(Tweet tweet : tweets) {
//			tweet.setDeletedFlag(false);
//		}
//		User temp = userRepo.saveAndFlush(user);
//		return userRepo.findByUsername(temp.getUsername());
//	}

	@Override
	@Transactional
	public void postFollow(String username, Credentials credentials) throws Exception {
		User follower = userRepo.findFirstByUsernameAndDeletedFlag(credentials.getUsername(), false);
		User following = userRepo.findFirstByUsernameAndDeletedFlag(username, false);
		if(follower == null || follower.getDeletedFlag() || !follower.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match or not found");
		if(following == null || following.getDeletedFlag())
			throw new Exception("User to follow does not exist");
		if(follower.getWhoIAmFollowing().contains(following))
			throw new Exception("Already following user");
		follower.getWhoIAmFollowing().add(following);
		userRepo.saveAndFlush(follower);
	}

	@Override
	@Transactional
	public void postUnfollow(String username, Credentials credentials) throws Exception {
		User follower = userRepo.findFirstByUsernameAndDeletedFlag(credentials.getUsername(), false);
		User following = userRepo.findFirstByUsernameAndDeletedFlag(username, false);
		if(follower == null || follower.getDeletedFlag() || !follower.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match or not found");
		if(following == null || following.getDeletedFlag())
			throw new Exception("User to unfollow does not exist");
		if(!follower.getWhoIAmFollowing().contains(following))
			throw new Exception("Not following this user in the first place");
		follower.getWhoIAmFollowing().remove(following);
		userRepo.saveAndFlush(follower);
		System.out.println("Added follower");
	}

	@Override
	@Transactional
	public UserProjection patchUser(String username, User user) throws Exception {
		if(user.getCredentials().getUsername() == null || user.getCredentials().getPassword() == null || user.getProfile().getEmail() == null)
			throw new Exception("Required field was null");
		if(!username.equals(user.getCredentials().getUsername())) throw new Exception("Usernames do not match");
		user.setUsername(username);
		User search = userRepo.findFirstByUsernameAndDeletedFlag(username, false);
		if(search == null || search.getDeletedFlag()) {
			throw new Exception("Username does not exist");
		} if(search.getCredentials().getPassword().equals(user.getCredentials().getPassword())) {
			user.setId(search.getId());
			user.getCredentials().setId(search.getCredentials().getId());
			user.getProfile().setId(search.getProfile().getId());
			User temp = userRepo.saveAndFlush(user);
			return userRepo.findByUsername(temp.getUsername());
		}
		throw new Exception("Credentials do not match or not found");
	}

	@Override
	@Transactional
	public UserProjection deleteUser(String username, Credentials credentials) throws Exception {
		User user = userRepo.findFirstByUsernameAndDeletedFlag(username, false);
		if(!username.equals(credentials.getUsername())) throw new Exception("Usernames do not match");
		if(user == null || user.getDeletedFlag() || !user.getCredentials().getPassword().equals(credentials.getPassword()))
			throw new Exception("Credentials do not match or not found");
		user.setDeletedFlag(true);
		User temp = userRepo.save(user);
		return userRepo.findByUsername(temp.getUsername());
	}
}