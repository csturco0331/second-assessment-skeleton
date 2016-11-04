package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Tweet;
import com.cooksys.projections.TweetProjection;

public interface TweetsRepository extends JpaRepository<Tweet, Long> {

	List<TweetProjection> findAllProjectedBy();

	TweetProjection findById(Long id);

	List<TweetProjection> findRepliesById(Long id);

	List<TweetProjection> findRepostsById(Long id);
	
	List<TweetProjection> findTweetsByAuthor_Username(String username);
	
	List<TweetProjection> findTweetsByHashtags_Label(String label);

	List<TweetProjection> findMentionedByMentions_Username(String username);

}
