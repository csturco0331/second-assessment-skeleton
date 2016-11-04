package com.cooksys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.repository.HashtagRepository;
import com.cooksys.repository.TweetsRepository;
import com.cooksys.service.TagsService;

@Service
public class TagsServiceImpl implements TagsService {

	private HashtagRepository hashtagRepo;
	private TweetsRepository tweetsRepo;
	
	public TagsServiceImpl(HashtagRepository hashtagRepo, TweetsRepository tweetsRepo) {
		this.hashtagRepo = hashtagRepo;
		this.tweetsRepo = tweetsRepo;
	}
	
	@Override
	public List<HashtagProjection> getHashtags() {
		return hashtagRepo.findAllProjectedBy();
	}

	@Override
	public List<TweetProjection> getLabel(String label) {
		return tweetsRepo.findTweetsByHashtags_Label(label);
	}

}
