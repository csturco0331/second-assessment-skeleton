package com.cooksys.projections;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;

public interface TweetProjection {

	Long getId();
	
	UserProjection getAuthor();
	
	Timestamp getPosted();
	
	String getContent();
	
	@Value("#{target.getInReplyTo()}")
	TweetAssist getInReplyTo();
	
	@Value("#{target.getRepostOf()}")
	TweetAssist getRepostOf();
	
}
