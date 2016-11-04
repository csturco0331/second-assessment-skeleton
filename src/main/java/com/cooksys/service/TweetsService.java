package com.cooksys.service;

import java.util.List;

import com.cooksys.projections.ContextProjection;
import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;

public interface TweetsService {

	List<TweetProjection> getTweets();

	TweetProjection getTweet(Long id);

	List<HashtagProjection> getTags(Long id);

	List<UserProjection> getLikes(Long id);

//	ContextProjection getContext(Long id);

	List<TweetProjection> getReplies(Long id);

	List<TweetProjection> getReposts(Long id);

	List<UserProjection> getMentions(Long id);
//
//	TweetProjection postTweet(String content, CredentialsProjection credentials);
//
//	TweetProjection postReply(Long id, String content, CredentialsProjection credentials);
//
//	TweetProjection postRepost(Long id, CredentialsProjection credentials);
//
//	TweetProjection deleteTweet(Long id);

}
