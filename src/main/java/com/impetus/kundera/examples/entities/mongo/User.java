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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.impetus.kundera.api.Document;

/**
 * User Entity Class
 * @author amresh.singh
 */

@Entity
@Document(name="users", db="tweengotest")
public class User {
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="passowrd")
	private String password;
	
	@OneToMany (cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private List<Follower> followers;	//List of my follower's IDs
	
	@OneToMany (cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private List<Friend> following;		//List of IDs of people whom I follow
	
	public User() {
		
	}	
	
	public User(String userName, String password) {		
		this.userName = userName;
		this.password = password;
	}
	
	public String toString() {
		return "User [" + this.userId + "] " + this.userName + "/" + this.password;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the followers
	 */
	public List<Follower> getFollowers() {
		return followers;
	}

	/**
	 * @param follower the followers to Add to this user
	 */
	public void addFollower(Follower follower) {
		if(this.followers == null || this.followers.isEmpty()) {
			this.followers = new ArrayList<Follower>();
			
		}
		this.followers.add(follower);
	}

	/**
	 * @return the following
	 */
	public List<Friend> getFollowing() {
		return following;
	}

	/**
	 * @param friendToFollow the friend to start following
	 */
	public void startFollowing(Friend friendToFollow) {
		if(this.following == null || this.following.isEmpty()) {
			this.following = new ArrayList<Friend>();
		}
		this.following.add(friendToFollow);		
	}

}
