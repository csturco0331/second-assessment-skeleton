package com.cooksys.entity;

import java.util.List;

import com.cooksys.projections.TweetProjection;

public class Context {

	TweetProjection target;
	
	List<TweetProjection> before;
	
	List<TweetProjection> after;
	
	public Context() {
		
	}
	
	public Context(TweetProjection target, List<TweetProjection> before, List<TweetProjection> after) {
		this.target = target;
		this.before = before;
		this.after = after;
	}

	public TweetProjection getTarget() {
		return target;
	}

	public void setTarget(TweetProjection target) {
		this.target = target;
	}

	public List<TweetProjection> getBefore() {
		return before;
	}

	public void setBefore(List<TweetProjection> before) {
		this.before = before;
	}

	public List<TweetProjection> getAfter() {
		return after;
	}

	public void setAfter(List<TweetProjection> after) {
		this.after = after;
	}
	
}
