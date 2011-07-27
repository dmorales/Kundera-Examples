/**
 * 
 */
package com.impetus.kundera.examples.query;

import java.util.List;

import com.impetus.kundera.examples.TwitterTestSuite;
import com.impetus.kundera.examples.entities.Tweet;


/**
 * The Class CassandraQuerySuite.
 *
 * @author vivek.mishra
 */
public class CassandraQuerySuite extends TwitterTestSuite
{
    /**
     * Sets the up internal.
     *
     * @param persistenceUnitName the new up internal
     * @throws Exception the exception
     */
    protected void setUpInternal(String persistenceUnitName) throws Exception
    {
        super.setUpInternal(persistenceUnitName);
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
        super.tearDownInternal();
    }

    /**
     * Execute suite.
     */
    public void executeQuerySuite()
    {
//        addUsers();
//        savePreference();
//        addExternalLinks();
//        addTweets();
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
        assertEquals(1, user1Tweet.size());
        assertEquals(1, user2Tweet.size());
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
       assertEquals(1, webTweets.size());
       assertEquals(1, mobileTweets.size());
       
   }
}
