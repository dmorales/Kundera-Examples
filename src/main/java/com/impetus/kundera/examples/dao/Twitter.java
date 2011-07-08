package com.impetus.kundera.examples.dao;

import java.util.List;

import org.apache.zookeeper.server.quorum.Follower;

import com.impetus.kundera.examples.entities.Preference;
import com.impetus.kundera.examples.entities.Tweet;
import com.impetus.kundera.examples.entities.User;

// TODO: Auto-generated Javadoc
/**
 * Single window application for Twitter application.
 * Contains methods for performing CRUD operations on users and their tweets.
 */
public interface Twitter
{
    
    

    /**
     * Adds the user.
     *
     * @param userId the user id
     * @param name the name
     * @param password the password
     * @param relationshipStatus the relationship status
     */
    void addUser(String userId, String name, String password, String relationshipStatus);    

    /**
     * Save preference.
     *
     * @param userId the user id
     * @param preference the preference
     */
    void savePreference(String userId, Preference preference);

    /**
     * Adds the external link.
     *
     * @param userId the user id
     * @param linkType the link type
     * @param linkAddress the link address
     */
    void addExternalLink(String userId, String linkType, String linkAddress);
    
    /**
     * Adds the tweet.
     *
     * @param userId the user id
     * @param tweetBody the tweet body
     * @param device the device
     */
    void addTweet(String userId, String tweetBody, String device);

    /**
     * Start following.
     *
     * @param userId the user id
     * @param friendUserId the friend user id
     */
    void startFollowing(String userId, String friendUserId);

    /**
     * Adds the follower.
     *
     * @param userId the user id
     * @param followerUserId the follower user id
     */
    void addFollower(String userId, String followerUserId);

    /**
     * Gets the all tweets.
     *
     * @param userId the user id
     * @return the all tweets
     */
    List<Tweet> getAllTweets(String userId);
    
    /**
     * Returns a list of followers.
     * @param userId user id
     * @return list of all followers.
     */
    List<User> getFollowers(String userId);
    
    
    /**
     * Close.
     */
    void close();

}
