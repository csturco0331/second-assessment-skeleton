package com.cooksys.service;

import java.util.List;

import com.cooksys.entity.Credentials;
import com.cooksys.entity.User;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;

public interface UserService {

	List<UserProjection> getUsers();

	UserProjection getUser(String username) throws Exception;

	UserProjection getValidatedUser(String username, String password) throws Exception;
	
	List<UserProjection> getPartialUsers(String username) throws Exception;

	List<TweetProjection> getFeed(String username) throws Exception;

	List<TweetProjection> getTweets(String username) throws Exception;

	List<TweetProjection> getMentions(String username) throws Exception;

	List<UserProjection> getFollowers(String username) throws Exception;

	List<UserProjection> getFollowing(String username) throws Exception;

	UserProjection postUser(User user) throws Exception;

	void postFollow(String username, Credentials credentials) throws Exception;

	void postUnfollow(String username, Credentials credentials) throws Exception;

	UserProjection patchUser(String username, User user) throws Exception;

	UserProjection deleteUser(String username, Credentials credentials) throws Exception;

}
