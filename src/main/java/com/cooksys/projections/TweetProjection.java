package com.cooksys.projections;

import java.sql.Timestamp;

public interface TweetProjection {

	Long getId();
	
	UserProjection getAuthor();
	
	Timestamp getPosted();
	
	String getContent();
	
	TweetProjection getInReplyTo();
	
	TweetProjection getRepostOf();
	
}
