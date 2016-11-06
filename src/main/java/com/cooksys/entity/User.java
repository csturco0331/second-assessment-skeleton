package com.cooksys.entity;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false, updatable = false)
	private Timestamp joined;
	
	@Column(nullable = false)
	private Boolean deletedFlag = false;
	
	//Look at fetch
	@OneToOne
	@Cascade({CascadeType.ALL})
	@JoinColumn
	private Profile profile;
	
	@OneToOne
	@Cascade({CascadeType.ALL})
	@JoinColumn
	private Credentials credentials;
	
	@OneToMany(mappedBy = "author")
	private List<Tweet> tweets;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "follows",
			joinColumns = @JoinColumn(name = "followings"),
			inverseJoinColumns = @JoinColumn(name = "followers"))
	private List<User> whoIAmFollowing = new LinkedList<>();
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "whoIAmFollowing")
	private List<User> followers = new LinkedList<>();
	
	@ManyToMany(mappedBy = "mentions")
	private List<Tweet> mentioned;
	
	@ManyToMany(mappedBy = "likes")
	private List<Tweet> liked;
	
	public User() {
		
	}
	
	public User(String username, Timestamp joined, Boolean deletedFlag, Profile profile, Credentials credentials, List<User> followers, List<User> followings) {
		this.username = username;
		this.joined = joined;
		this.deletedFlag = deletedFlag;
		this.profile = profile;
		this.followers = followers;
		this.whoIAmFollowing = followings;
		this.credentials = credentials;
	}

	public User(Credentials credentials, Profile profile) {
		this.username = credentials.getUsername();
		this.joined = new Timestamp(System.currentTimeMillis());
		this.deletedFlag = false;
		this.profile = new Profile(profile);
		this.followers = new LinkedList<>();
		this.whoIAmFollowing = new LinkedList<>();
		this.credentials = new Credentials(credentials);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(updatable = false)
	public Timestamp getJoined() {
		return joined;
	}

	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}

	public Boolean getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Boolean deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Tweet> getMentioned() {
		return mentioned;
	}

	public void setMentioned(List<Tweet> mentioned) {
		this.mentioned = mentioned;
	}

	public List<Tweet> getLiked() {
		return liked;
	}

	public void setLiked(List<Tweet> liked) {
		this.liked = liked;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public List<User> getWhoIAmFollowing() {
		return whoIAmFollowing;
	}

	public void setWhoIAmFollowing(List<User> followings) {
		this.whoIAmFollowing = followings;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

}
