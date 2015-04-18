package com.example.graphviewsample.model;

public class TweetModel {
	 private long id;
	  private String tweetMessage;
	  private String tweetDateTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTweetMessage() {
		return tweetMessage;
	}
	public void setTweetMessage(String tweetMessage) {
		this.tweetMessage = tweetMessage;
	}
	public String getTweetDateTime() {
		return tweetDateTime;
	}
	public void setTweetDateTime(String tweetDateTime) {
		this.tweetDateTime = tweetDateTime;
	}

}
