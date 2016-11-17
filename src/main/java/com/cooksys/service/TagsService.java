package com.cooksys.service;

import java.util.List;

import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;

public interface TagsService {

	List<HashtagProjection> getHashtags();
	
	List<HashtagProjection> getPartialHashtags(String label) throws Exception;
	
	List<TweetProjection> getPartialLabel(String label) throws Exception;

	List<TweetProjection> getLabel(String label) throws Exception;

}
