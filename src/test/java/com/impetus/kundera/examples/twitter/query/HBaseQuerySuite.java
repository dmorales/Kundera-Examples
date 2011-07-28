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
package com.impetus.kundera.examples.twitter.query;

import java.util.List;

import com.impetus.kundera.examples.twitter.TwitterTestSuite;
import com.impetus.kundera.examples.twitter.entities.Tweet;

/**
 * Test suite for HBase query
 * @author amresh.singh
 */
public class HBaseQuerySuite extends TwitterTestSuite
{
    /**
     * Sets the up internal.
     * @param persistenceUnitName the new up internal
     * @throws Exception the exception
     */
    protected void setUpInternal(String persistenceUnitName) throws Exception
    {
        super.setUpInternal(persistenceUnitName);
    }

   
    /**
     * Tear down internal.
     *
     * @throws Exception the exception
     */
    protected void tearDownInternal() throws Exception
    {
        super.tearDownInternal();
    }

    /**
     * Execute suite.
     */
    public void executeQuerySuite()
    {        
        getTweetsByBody();
        getTweetsByDevice();
    }
    /**
     * Gets the tweets by body.
     *
     * @return the tweets by body
     */
    public void getTweetsByBody()
    {
        List<Tweet> user1Tweet = twitter.findTweetByBody("Here");
        List<Tweet> user2Tweet = twitter.findTweetByBody("Saurabh");
        
        assertNotNull(user1Tweet);
        assertNotNull(user2Tweet);
        assertEquals(2, user1Tweet.size());
        assertEquals(2, user2Tweet.size());
    }
    
   /**
    * Gets the tweet by device.
    *
    * @return the tweet by device
    */
   public void getTweetsByDevice()
   {
       List<Tweet> webTweets = twitter.findTweetByDevice("Web");
       List<Tweet> mobileTweets = twitter.findTweetByDevice("Mobile");
       assertNotNull(webTweets);
       assertNotNull(mobileTweets);
       assertEquals(2, webTweets.size());
       assertEquals(2, mobileTweets.size());
   }     
   
}
