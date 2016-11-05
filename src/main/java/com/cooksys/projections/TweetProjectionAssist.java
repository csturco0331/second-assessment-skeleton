package com.cooksys.projections;

import java.sql.Timestamp;

public interface TweetProjectionAssist {

	Long getId();
	
	UserProjection getAuthor();
	
	Timestamp getPosted();
	
	String getContent();
	
}
