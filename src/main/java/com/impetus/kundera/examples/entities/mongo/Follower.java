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
 * Entity Class for a user's followers
 * @author amresh.singh
 */

@Entity
@Document(name="followers", db="twingotest")
public class Follower {
	
	@Id
	@Column(name="user_id")	
	private String userId;
	
	@Column(name="follower_since")
	private String followerSince;
	
	public Follower() {
		
	}
	
	public Follower(String userId, String followerSince) {
		this.userId = userId;
		this.followerSince = followerSince;
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
	 * @return the followerSince
	 */
	public String getFollowerSince() {
		return followerSince;
	}

	/**
	 * @param followerSince the followerSince to set
	 */
	public void setFollowerSince(String followerSince) {
		this.followerSince = followerSince;
	}	
	
}
