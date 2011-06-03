/*
 * Copyright 2011 Impetus Infotech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.impetus.kundera.examples.entities.mongo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.impetus.kundera.api.Document;

/**
 * Entity Class for containing events of Users and those User is following
 * @author amresh.singh
 */

@Entity
@Document(name="timelines", db="tweengotest")
public class TimeLine {	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="tweets")
	private List<String> tweets;	//List of Tweet Ids posted by this user and ones it follows
	
	public TimeLine() {
		
	}
	
	public TimeLine(String userId, String tweetId) {
		this.userId = userId;		
		addTweet(tweetId);		
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the tweets
	 */
	public List<String> getTweets() {
		return tweets;
	}

	/**
	 * @param tweetId the tweet to add
	 */
	public void addTweet(String tweetId) {
		if(this.tweets == null || this.tweets.isEmpty()) {
			this.tweets = new ArrayList<String>();
		}
		this.tweets.add(tweetId);
	}
	
}
