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
 * Entity class for a user's friends, i.e. people whom this user is following
 * @author amresh.singh
 */

@Entity
@Document(name="following", db="tweengotest")
public class Friend {
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="following_since")
	private String followingSince;
	
	
	public Friend() {
		
	}
	
	public Friend(String userId, String followingSince) {
		this.userId = userId;
		this.followingSince = followingSince;
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
	 * @return the followingSince
	 */
	public String getFollowingSince() {
		return followingSince;
	}

	/**
	 * @param followingSince the followingSince to set
	 */
	public void setFollowingSince(String followingSince) {
		this.followingSince = followingSince;
	}
	
	
	

}
