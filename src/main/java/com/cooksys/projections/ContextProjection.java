package com.cooksys.projections;

import java.util.List;

public interface ContextProjection {

	TweetProjection getTarget();
	
	List<TweetProjection> getBefore();
	
	List<TweetProjection> getAfter();
	
}
