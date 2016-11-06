package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Tweet;
import com.cooksys.projections.TweetProjection;

public interface TweetsRepository extends JpaRepository<Tweet, Long> {

	List<TweetProjection> findAllProjectedBy();

	TweetProjection findByIdAndDeletedFlag(Long id, boolean b);

	List<TweetProjection> findRepliesByIdAndDeletedFlagAndReplies_DeletedFlag(Long id, boolean b, boolean c);

	List<TweetProjection> findByRepostOf_IdAndDeletedFlag(Long id, boolean b);
	
	List<TweetProjection> findTweetsByAuthor_UsernameAndAuthor_DeletedFlagOrderByPostedAsc(String username, boolean b);
	
	List<TweetProjection> findTweetsByHashtags_LabelAndDeletedFlagOrderByPostedAsc(String label, boolean b);

	List<TweetProjection> findMentionedByMentions_UsernameAndMentions_DeletedFlagOrderByPostedAsc(String username, boolean b);

	List<TweetProjection> findByDeletedFlagOrderByPostedAsc(boolean b);

	Tweet findFirstByIdAndDeletedFlag(Long id, boolean b);

	TweetProjection findById(Long id);

	List<TweetProjection> findByInReplyTo_IdAndDeletedFlag(Long id, boolean b);

	List<TweetProjection> findAuthor_WhoIAmFollowing_TweetsByAuthor_Username(String username);

}
