package com.cooksys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.repository.HashtagRepository;
import com.cooksys.repository.TweetsRepository;
import com.cooksys.service.TagsService;

@Service
public class TagsServiceImpl implements TagsService {

	@Autowired
	private HashtagRepository hashtagRepo;
	@Autowired
	private TweetsRepository tweetsRepo;
	
	@Override
	public List<HashtagProjection> getHashtags() {
		return hashtagRepo.findAllProjectedBy();
	}
	
	@Override
	public List<HashtagProjection> getPartialHashtags(String label) throws Exception {
		label = label.toLowerCase();
		List<HashtagProjection> results = hashtagRepo.findByLabelIgnoreCaseContaining(label);
		if (results.isEmpty()) throw new Exception("No Hashtag found");
		return results;
	}
	
	@Override
	public List<TweetProjection> getPartialLabel(String label) throws Exception {
		label = label.toLowerCase();
		if(hashtagRepo.findByLabelIgnoreCaseContaining(label).isEmpty()) throw new Exception("No Hashtag found");
		return tweetsRepo.findDistinctTweetsByHashtags_LabelIgnoreCaseContainingAndDeletedFlagFalseOrderByPostedDesc(label);
	}

	@Override
	public List<TweetProjection> getLabel(String label) throws Exception {
		label = label.toLowerCase();
		System.out.println(label);
		if(hashtagRepo.findByLabelIgnoringCase(label) == null) throw new Exception("No Hashtag found");
		return tweetsRepo.findDistinctTweetsByHashtags_LabelIgnoreCaseAndDeletedFlagFalseOrderByPostedDesc(label);
	}

}
