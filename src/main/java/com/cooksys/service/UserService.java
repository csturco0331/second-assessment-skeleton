package com.cooksys.service;

import java.util.List;

import com.cooksys.entity.User;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;

public interface UserService {

	List<UserProjection> getUsers();

	UserProjection getUser(String username);

//	List<TweetProjection> getFeed(String username);

	List<TweetProjection> getTweets(String username);

	List<TweetProjection> getMentions(String username);

	List<UserProjection> getFollowers(String username);

	List<UserProjection> getFollowing(String username);

	UserProjection postUser(User user);
//
//	void postFollow(String username, CredentialsProjection credentials);
//
//	void postUnfollow(String username, CredentialsProjection credentials);
//
//	UserProjection patchUser(String username, CredentialsProjection credentials, ProfileProjection profile);
//
//	UserProjection deleteUser(String username, CredentialsProjection credentials);

}
