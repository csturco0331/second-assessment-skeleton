package com.cooksys.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

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
		return userRepo.findAllProjectedBy();
	}

	@Override
	public UserProjection getUser(String username) {
		return userRepo.findByUsername(username);
	}

//	@Override
//	public List<TweetProjection> getFeed(String username) {
//		return null;
//	}

	@Override
	public List<TweetProjection> getTweets(String username) {
		return tweetsRepo.findTweetsByAuthor_Username(username);
	}

	@Override
	public List<TweetProjection> getMentions(String username) {
		return tweetsRepo.findMentionedByMentions_Username(username);
	}

	@Override
	public List<UserProjection> getFollowers(String username) {
		return userRepo.findFollowersByUsername(username);
	}

	@Override
	public List<UserProjection> getFollowing(String username) {
		return userRepo.findFollowingByUsername(username);
	}

	@Override
	public UserProjection postUser(User user) {
		user.setUsername(user.getCredentials().getUsername());
		user.setJoined(new Timestamp(System.currentTimeMillis()));
		if(userRepo.findByUsername(user.getUsername()) == null) {
			User temp = userRepo.save(user);
			return userRepo.findByUsername(temp.getUsername());
		}
		return null;
	}
//
//	@Override
//	public void postFollow(String username, CredentialsProjection credentials) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void postUnfollow(String username, CredentialsProjection credentials) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public UserProjection patchUser(String username, CredentialsProjection credentials, ProfileProjection profile) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public UserProjection deleteUser(String username, CredentialsProjection credentials) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
