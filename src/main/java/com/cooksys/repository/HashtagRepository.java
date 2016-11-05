package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Hashtag;
import com.cooksys.projections.HashtagProjection;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	HashtagProjection findByLabel(String label);

	List<HashtagProjection> findAllProjectedBy();
	
	List<HashtagProjection> findHashtagsByTweets_IdAndTweets_DeletedFlag(Long id, boolean b);

	Hashtag findFirstByLabel(String label);
	
}
