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

import com.impetus.kundera.examples.dao.Twitter;
import com.impetus.kundera.examples.dao.TwitterService;
import com.impetus.kundera.examples.entities.Preference;
import com.impetus.kundera.examples.entities.Tweet;
import com.impetus.kundera.examples.entities.User;

/**
 * Test case for MongoDB.
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

    /**
     * Sets the up internal.
     *
     * @param persistenceUnitName the new up internal
     * @throws Exception the exception
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
    /**
     * Tear down internal.
     *
     * @throws Exception the exception
     */
    protected void tearDownInternal() throws Exception
    {
        if (twitter != null)
        {
            twitter.close();
        }
    }

    /**
     * Execute suite.
     */
    protected void executeTestSuite()
    {
        addUsers();
        savePreference();
        addExternalLinks();
        addTweets();  
        user1FollowsUser2();        
        getAllTweets();        
    }
    
    /**
     * Adds the users.
     */
    protected void addUsers()
    {
        twitter.addUser(userId1, "Amresh", "password1", "married");
        twitter.addUser(userId2, "Saurabh", "password2", "single");
    }

    /**
     * Save preference.
     */
    protected void savePreference()
    {
        twitter.savePreference(userId1, new Preference("Motif", "2"));
        twitter.savePreference(userId2, new Preference("High Contrast", "3"));
    }
    
    /**
     * Adds the external links.
     */
    protected void addExternalLinks() {
        twitter.addExternalLink(userId1, "Facebook", "http://facebook.com/coolnerd");
        twitter.addExternalLink(userId1, "LinkedIn", "http://linkedin.com/in/devilmate");
        
        twitter.addExternalLink(userId2, "GooglePlus", "http://plus.google.com/inviteme");
        twitter.addExternalLink(userId2, "Yahoo", "http://yahoo.com/profiles/itsmeamry");        
    }
    
    /**
     * Adds the tweets.
     */
    protected void addTweets() {
        twitter.addTweet(userId1, "Here is my first tweet", "Web");
        twitter.addTweet(userId1, "Here is my second tweet", "Mobile");
        
        twitter.addTweet(userId2, "And here is first one from me", "Phone");
        twitter.addTweet(userId2, "Another one from me", "text");
    }
    
    /**
     * User1 follows user2.
     */
    protected void user1FollowsUser2() {
        twitter.startFollowing(userId1, userId2);
    }
    
    /**
     * User1 adds user2 as follower.
     */
    protected void user1AddsUser2AsFollower() {
        twitter.addFollower(userId1, userId2);
    }
    
    /**
     * Gets the all tweets.
     *
     * @return the all tweets
     */
    protected void getAllTweets() {
        List<Tweet> tweetsUser1 = twitter.getAllTweets(userId1);
        List<Tweet> tweetsUser2 = twitter.getAllTweets(userId2);
        
        assertNotNull(tweetsUser1);
        assertNotNull(tweetsUser2);
        
    }
   
    /**
     * Gets the all followers.
     *
     * @return the all followers
     */
    protected void getAllFollowers()
    {
        List<User> follower1 = twitter.getFollowers(userId1);
        List<User> follower2 = twitter.getFollowers(userId2);
        
        assertNull(follower1);
        assertNotNull(follower2);
    }
}
