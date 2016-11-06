package com.cooksys.projections;

import java.sql.Timestamp;
import java.util.Comparator;

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
	
//	@Override
//	default public int compareTo(TweetProjection other) {
//	    return Long.compare(this.getPosted().getTime(), other.getPosted().getTime());
//	}
	
	public static Comparator<TweetProjection> sortByPosted() {
		Comparator<TweetProjection> comp = new Comparator<TweetProjection>() {

			@Override
			public int compare(TweetProjection one, TweetProjection two) {
				return Long.compare(two.getPosted().getTime(), one.getPosted().getTime());
			}
		};
		return comp;
	}
	
}
