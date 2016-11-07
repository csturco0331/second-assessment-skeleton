package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Hashtag;
import com.cooksys.projections.HashtagProjection;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	HashtagProjection findByLabelIgnoreCase(String label);

	List<HashtagProjection> findAllProjectedBy();
	
	List<HashtagProjection> findHashtagsByHashtagTweets_IdAndHashtagTweets_DeletedFlagFalse(Long id);

	Hashtag findFirstByLabelIgnoreCase(String label);
	
}
