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
	
	List<TweetProjection> findTweetsByAuthor_UsernameAndAuthor_DeletedFlagOrderByPostedAsc(String username, boolean b);
	
	List<TweetProjection> findTweetsByHashtags_Label(String label);

	List<TweetProjection> findMentionedByMentions_UsernameAndMentions_DeletedFlagOrderByPostedAsc(String username, boolean b);

}
