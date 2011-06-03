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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.impetus.kundera.api.Document;

/**
 * Entity class for all users' tweets
 * @author amresh.singh
 */

@Entity
@Document(name="tweets", db="tweengotest")
public class Tweet {	
	@Id
	@Column(name="tweet_id")
	private String tweetId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="body")
	private String body;
	
	@Column(name = "device")
	private String device;
	
	@Column(name = "added")
    private String added;	//dd/mm/yyyy
	
	public Tweet() {
		
	}
	
	public Tweet(String tweetId, String body, String device, String added) {
		this.tweetId = tweetId;
		this.body = body;
		this.device = device;
		this.added = added;
	}
	
	public String toString() {
		return this.userId + " sent Tweet[" + this.tweetId + "] " + this.body + " - from " + this.device + " on " + this.added;
	}	

	/**
	 * @return the tweetId
	 */
	public String getTweetId() {
		return tweetId;
	}

	/**
	 * @param tweetId the tweetId to set
	 */
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
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
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @return the added
	 */
	public String getAdded() {
		return added;
	}

	/**
	 * @param added the added to set
	 */
	public void setAdded(String added) {
		this.added = added;
	}

}
