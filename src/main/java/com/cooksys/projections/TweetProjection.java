package com.cooksys.projections;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;

public interface TweetProjection {

	Long getId();
	
	UserProjection getAuthor();
	
	Timestamp getPosted();
	
	String getContent();
	
	@Value("#{target.retrieveInReplyTo()}")
	TweetProjection getInReplyTo();
	
	@Value("#{target.retrieveRepostOf()}")
	TweetProjection getRepostOf();
	
}
