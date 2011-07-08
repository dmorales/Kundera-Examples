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
package com.impetus.kundera.examples;

import java.util.List;

import junit.framework.TestCase;

import com.impetus.kundera.examples.dao.TwitterService;
import com.impetus.kundera.examples.dao.Twitter;
import com.impetus.kundera.examples.entities.Preference;
import com.impetus.kundera.examples.entities.Tweet;

/**
 * Test case for MongoDB.
 *
 * @author amresh.singh
 */
public class TwitterTestSuite extends TestCase
{
    
    /** The user id1. */
    String userId1;

    /** The user id2. */
    String userId2;

    /** The twitter. */
    Twitter twitter;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUpInternal(String persistenceUnitName) throws Exception
    {
        userId1 = "0001";
        userId2 = "0002";

        twitter = new TwitterService(persistenceUnitName);

    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDownInternal() throws Exception
    {
        twitter.close();
    }

    /**
     * Execute suite.
     */
    public void executeSuite()
    {
        addUsers();
        savePreference();
        addExternalLinks();
        addTweets();
        user1FollowsUser2();
        user1FollowsUser2();
        user1AddsUser2AsFollower();
        getAllTweets();
    }
    
    /**
     * Adds the users.
     */
    public void addUsers()
    {
        twitter.addUser(userId1, "Amresh", "password1", "married");
        twitter.addUser(userId2, "Saurabh", "password2", "single");
    }

    /**
     * Save preference.
     */
    public void savePreference()
    {
        twitter.savePreference(userId1, new Preference("Motif", "2"));
        twitter.savePreference(userId2, new Preference("High Contrast", "3"));
    }
    
    /**
     * Adds the external links.
     */
    public void addExternalLinks() {
        twitter.addExternalLink(userId1, "Facebook", "http://facebook.com/coolnerd");
        twitter.addExternalLink(userId1, "LinkedIn", "http://linkedin.com/in/devilmate");
        
        twitter.addExternalLink(userId2, "GooglePlus", "http://plus.google.com/inviteme");
        twitter.addExternalLink(userId2, "Yahoo", "http://yahoo.com/profiles/itsmeamry");        
    }
    
    /**
     * Adds the tweets.
     */
    public void addTweets() {
        twitter.addTweet(userId1, "Here is my first tweet", "Web");
        twitter.addTweet(userId1, "Here is my second tweet", "Mobile");
        
        twitter.addTweet(userId2, "Here is my first tweet2", "Phone");
        twitter.addTweet(userId2, "Here is my second tweet2", "text");
    }
    
    /**
     * User1 follows user2.
     */
    public void user1FollowsUser2() {
        twitter.startFollowing(userId1, userId2);
    }
    
    /**
     * User1 adds user2 as follower.
     */
    public void user1AddsUser2AsFollower() {
        twitter.addFollower(userId1, userId2);
    }
    
    /**
     * Gets the all tweets.
     *
     * @return the all tweets
     */
    public void getAllTweets() {
        List<Tweet> tweetsUser1 = twitter.getAllTweets(userId1);
        List<Tweet> tweetsUser2 = twitter.getAllTweets(userId2);
        
        System.out.println(tweetsUser1);
        System.out.println(tweetsUser2);
        
    }
   
    /**
     * Test.
     */
    public void test()
    {
        //addUsers();
        //savePreference();
        //addExternalLinks();
        //addTweets();
        //user1FollowsUser2();
        getAllTweets();
        
    }

}
