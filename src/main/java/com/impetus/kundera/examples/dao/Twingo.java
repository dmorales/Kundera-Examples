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
package com.impetus.kundera.examples.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;


import com.impetus.kundera.examples.entities.mongo.Follower;
import com.impetus.kundera.examples.entities.mongo.Friend;
import com.impetus.kundera.examples.entities.mongo.TimeLine;
import com.impetus.kundera.examples.entities.mongo.Tweet;
import com.impetus.kundera.examples.entities.mongo.User;
import com.impetus.kundera.examples.entities.mongo.UserLine;
import com.impetus.kundera.examples.entities.mongo.UserName;
import com.impetus.kundera.examples.utils.ExampleUtils;

/**
 * Data access object class for mongo implementation of twitter.
 * @author amresh.singh
 */
public class Twingo extends SuperDao implements Twitter {
	private EntityManager em;
	
	public Twingo(String persistenceUnitName) {
		if(em == null) {
			try {
				em = init(persistenceUnitName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		if(em != null) {
			em.close();
		}
	}
	
	@Override
	public void addUser(String username, String password) {    	
    	//Persist User Entity
		String userId = ExampleUtils.getUniqueId();
		User user = new User(username, password); 
		user.setUserId(userId);
    	
        em.persist(user);     
        
        //Persist UserName entity (inverted index)
        UserName un = new UserName(user.getUserName(), user.getUserId());
        em.persist(un);
		
	}

	
	@Override
	public void tweet(String userid, String tweetmsg, String userName) {
		
		
	}

	
	@Override
	public void follow(String userid, String friend) {
		
		
	}
	
	
	public void registerUser(User user) {        	
    	String userId = ExampleUtils.getUniqueId(); 
    	
    	//Persist User Entity
    	user.setUserId(userId);
        em.persist(user);     
        
        //Persist UserName entity (inverted index)
        UserName un = new UserName(user.getUserName(), userId);
        em.persist(un);
    }
    
    public void followAFriend(User thisUser, String otherUserId) {
    	String timestamp = "" + ExampleUtils.getCurrentTimestamp();
    	//Add other user to my following list
    	Friend friend = new Friend(otherUserId, timestamp);    	
    	thisUser.startFollowing(friend);
    	em.persist(thisUser);
    	
    	//Add myself to other user's follower list
    	User otherUser = em.find(User.class, otherUserId);
    	Follower follower = new Follower(thisUser.getUserId(), timestamp);
    	otherUser.addFollower(follower);
    	em.persist(otherUser);    	
    }
    
    
    public void addTweet(Tweet tweet) {
    	//Persist Tweet
    	tweet.setTweetId(ExampleUtils.getUniqueId());
    	tweet.setAdded("" + ExampleUtils.getCurrentTimestamp());
    	
    	em.persist(tweet);
    	
    	//Persist User Line
    	UserLine ul = em.find(UserLine.class, tweet.getUserId());
    	if(ul == null) {
    		ul = new UserLine(tweet.getUserId(), tweet.getTweetId());
    	} else {
    		ul.addTweet(tweet.getTweetId());
    	}    	
    	em.persist(ul);    	
    	
    	//Persist Time Line for this user    	
    	TimeLine utl = em.find(TimeLine.class, tweet.getUserId());
    	if(utl == null) {
    		utl = new TimeLine();
    		utl.setUserId(tweet.getUserId());    		
    	}
    	utl.addTweet(tweet.getTweetId());    	
    	em.persist(utl);
    	
    	//Persist Time Line for all users who follow this one
    	User user = em.find(User.class, tweet.getUserId());
    	List<Follower> followers = user.getFollowers();
    	if(followers != null && ! followers.isEmpty()) {
    		for(Follower follower : followers) {
        		TimeLine ftl = em.find(TimeLine.class, follower.getUserId());
        		if(ftl == null) {
        			ftl = new TimeLine();
        			ftl.setUserId(follower.getUserId());
        		}
        		
        		ftl.addTweet(tweet.getTweetId());    		
        		em.persist(ftl);
        	}  
    	} 	      	
    }  
    
    public Tweet getTweet(String tweetId) {
    	Tweet tweet = em.find(Tweet.class, tweetId);
    	return tweet;
    }
    
    public List<Tweet> getAllTweets(String userId) {
    	UserLine ul = em.find(UserLine.class, userId);
    	List<String> tweetIds = ul.getTweets();
    	
    	List<Tweet> allTweets = new ArrayList<Tweet>();
    	if(tweetIds != null && ! tweetIds.isEmpty()) {
    		for(String tweetId : tweetIds) {
        		allTweets.add(getTweet(tweetId));
        	}
    	}
    	
    	return allTweets;    	
    }
    
    public List<Tweet> getTimeLine(String userId) {
    	TimeLine tl = em.find(TimeLine.class, userId);
    	List<String> tweetIds = tl.getTweets();
    	
    	List<Tweet> allTimeLine = new ArrayList<Tweet>();
    	if(tweetIds != null && ! tweetIds.isEmpty()) {
    		for(String tweetId : tweetIds) {
    			allTimeLine.add(getTweet(tweetId));
    		}
    	}
    	return allTimeLine;
    }
    
    public User getUserById(String userId) {
    	return em.find(User.class, userId);    	
    }
    
    public User getUserByName(String userName) {
    	UserName un = em.find(UserName.class, userName);
    	if(un == null) {
    		throw new PersistenceException("No user found with name " + userName);
    	}
    	User user = em.find(User.class, un.getUserId());
    	return user;    	
    }
	
	

}
