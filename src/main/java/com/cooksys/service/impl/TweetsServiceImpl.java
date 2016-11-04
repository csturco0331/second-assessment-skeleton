package com.cooksys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
		return tweetsRepo.findAllProjectedBy();
	}

	@Override
	public TweetProjection getTweet(Long id) {
		return tweetsRepo.findById(id);
	}

	@Override
	public List<HashtagProjection> getTags(Long id) {
		return hashtagRepo.findHashtagsByTweets_Id(id);
	}

	@Override
	public List<UserProjection> getLikes(Long id) {
		return userRepo.findLikesByLiked_Id(id);
	}

//	@Override
//	public ContextProjection getContext(Long id) {
//		return null;
//	}

	@Override
	public List<TweetProjection> getReplies(Long id) {
		return tweetsRepo.findRepliesById(id);
	}

	@Override
	public List<TweetProjection> getReposts(Long id) {
		return tweetsRepo.findRepostsById(id);
	}

	@Override
	public List<UserProjection> getMentions(Long id) {
		return userRepo.findMentionsByMentioned_Id(id);
	}
//
//	@Override
//	public TweetProjection postTweet(String content, CredentialsProjection credentials) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TweetProjection postReply(Long id, String content, CredentialsProjection credentials) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TweetProjection postRepost(Long id, CredentialsProjection credentials) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TweetProjection deleteTweet(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
