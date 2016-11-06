package com.cooksys.projections;

import java.sql.Timestamp;

public interface TweetAssist {

	Long getId();
	
	UserProjection getAuthor();
	
	Timestamp getPosted();
	
	String getContent();
	
}
