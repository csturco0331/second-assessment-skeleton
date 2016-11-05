package com.cooksys.service;

import java.util.List;

import com.cooksys.entity.Credentials;
import com.cooksys.entity.TweetPost;
import com.cooksys.projections.ContextProjection;
import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.projections.UserProjection;

public interface TweetsService {

	List<TweetProjection> getTweets();

	TweetProjection getTweet(Long id) throws Exception;

	List<HashtagProjection> getTags(Long id) throws Exception;

	List<UserProjection> getLikes(Long id) throws Exception;

	ContextProjection getContext(Long id) throws Exception;

	List<TweetProjection> getReplies(Long id) throws Exception;

	List<TweetProjection> getReposts(Long id) throws Exception;

	List<UserProjection> getMentions(Long id) throws Exception;

	TweetProjection postTweet(TweetPost post) throws Exception;

	TweetProjection postReply(Long id, TweetPost post) throws Exception;

	TweetProjection postRepost(Long id, Credentials credentials) throws Exception;

	TweetProjection deleteTweet(Long id, Credentials credentials) throws Exception;

}
