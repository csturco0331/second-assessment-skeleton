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
import javax.persistence.Table;

@Entity
@Table(name = "hashtag")
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
			joinColumns = @JoinColumn(name = "hashtags"),
			inverseJoinColumns = @JoinColumn(name = "tweets"))
	private List<Tweet> hashtagTweets;

	public Hashtag() {
		
	}
	
	public Hashtag(String label, Timestamp firstUsed) {
		this.label = label;
		this.firstUsed = firstUsed;
		this.lastUsed = firstUsed;
	}

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

	public List<Tweet> getHashtagTweets() {
		return hashtagTweets;
	}

	public void setHashtagTweets(List<Tweet> hashtagTweets) {
		this.hashtagTweets = hashtagTweets;
	}
	
}
