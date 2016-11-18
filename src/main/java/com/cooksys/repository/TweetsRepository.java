package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Tweet;
import com.cooksys.projections.TweetProjection;

public interface TweetsRepository extends JpaRepository<Tweet, Long> {

	TweetProjection findByIdAndDeletedFlag(Long id, boolean b);

	List<TweetProjection> findByRepostOf_IdAndDeletedFlagFalse(Long id);
	
	List<TweetProjection> findTweetsByAuthor_UsernameAndDeletedFlagFalseOrderByPostedDesc(String username);
	
	List<TweetProjection> findDistinctTweetsByHashtags_LabelContainingAndDeletedFlagFalseOrderByPostedDesc(String label);
	
	List<TweetProjection> findDistinctTweetsByHashtags_LabelAndDeletedFlagFalseOrderByPostedDesc(String label);

	List<TweetProjection> findMentionedByMentions_UsernameAndDeletedFlagFalseOrderByPostedDesc(String username);
	
	List<TweetProjection> findDistinctMentionedByMentions_UsernameContainingAndDeletedFlagFalseOrderByPostedDesc(String username);

	List<TweetProjection> findByDeletedFlagFalseOrderByPostedDesc();

	Tweet findFirstByIdAndDeletedFlagFalse(Long id);

	TweetProjection findById(Long id);

	List<TweetProjection> findByInReplyTo_IdAndDeletedFlagFalse(Long id);

	List<Tweet> findByInReplyTo_Id(Long id);

}
