package com.cooksys.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "hashtag")
@SecondaryTable(name = "hashtag_tweet")
public class Hashtag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String label;
	
	@Column(nullable = false, updatable = false)
	private Timestamp firstUsed;
	
	@Column(nullable = false)
	private Timestamp lastUsed;
	
	@ManyToMany
	@JoinTable(name = "hashtag_tweet",
			joinColumns = @JoinColumn(name = "hashtag_id"),
			inverseJoinColumns = @JoinColumn(name = "tweet_id"))
	private List<Tweet> tweets;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Timestamp getFirstUsed() {
		return firstUsed;
	}

	public void setFirstUsed(Timestamp firstUsed) {
		this.firstUsed = firstUsed;
	}

	public Timestamp getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Timestamp lastUsed) {
		this.lastUsed = lastUsed;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
}
