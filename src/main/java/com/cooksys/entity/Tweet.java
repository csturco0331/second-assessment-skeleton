package com.cooksys.entity;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tweet")
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private User author;
	
	@Column(nullable = false, updatable = false)
	private Timestamp posted;
	
	private String content;
	
	@ManyToOne
	@JoinColumn
	private Tweet inReplyTo;
	
	@ManyToOne
	@JoinColumn
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies = new LinkedList<>();
	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts = new LinkedList<>();
	
	@ManyToMany
	@JoinTable(name = "tweets_hashtags",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	private List<Hashtag> hashtags = new LinkedList<>();
	
	@ManyToMany
	@JoinTable(name = "mention_tweet",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private List<User> mentions = new LinkedList<>();
	
	
	@ManyToMany
	@JoinTable(name = "like_join",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private List<User> likes = new LinkedList<>();
	
	@Column(nullable = false)
	private Boolean deletedFlag = false;
	
	@Transient
	private Credentials credentials;
	
	public Tweet() {
		
	}
	
	public Tweet(User author, Timestamp posted, String content, List<Hashtag> hashtags, List<User> mentions) {
		this.author = author;
		this.posted = posted;
		this.content = content;
		this.hashtags = hashtags;
		this.mentions = mentions;
	}

	public Tweet(User author, Timestamp posted, Tweet repostOf) {
		this.author = author;
		this.posted = posted;
		this.repostOf = repostOf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

	public List<Tweet> getReplies() {
		return replies;
	}

	public void setReplies(List<Tweet> replies) {
		this.replies = replies;
	}

	public List<Tweet> getReposts() {
		return reposts;
	}

	public void setReposts(List<Tweet> reposts) {
		this.reposts = reposts;
	}

	public List<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

	public List<User> getMentions() {
		return mentions;
	}

	public void setMentions(List<User> mentions) {
		this.mentions = mentions;
	}

	public List<User> getLikes() {
		return likes;
	}

	public void setLikes(List<User> likes) {
		this.likes = likes;
	}

	public Boolean getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Boolean deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
