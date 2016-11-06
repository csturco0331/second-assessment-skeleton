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
	
	List<TweetProjection> findTweetsByAuthor_UsernameAndDeletedFlagOrderByPostedAsc(String username, boolean b);
	
	List<TweetProjection> findTweetsByHashtags_LabelAndDeletedFlagOrderByPostedDesc(String label, boolean b);

	List<TweetProjection> findMentionedByMentions_UsernameAndDeletedFlagOrderByPostedAsc(String username, boolean b);

	List<TweetProjection> findByDeletedFlagOrderByPostedDesc(boolean b);

	Tweet findFirstByIdAndDeletedFlag(Long id, boolean b);

	TweetProjection findById(Long id);

	List<TweetProjection> findByInReplyTo_IdAndDeletedFlag(Long id, boolean b);

	List<TweetProjection> findAuthor_WhoIAmFollowing_TweetsByAuthor_Username(String username);

	List<Tweet> findByInReplyTo_Id(Long id);

}
