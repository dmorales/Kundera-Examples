package com.impetus.kundera.examples.dao;

import java.util.List;

import com.impetus.kundera.examples.entities.ExternalLink;
import com.impetus.kundera.examples.entities.Preference;
import com.impetus.kundera.examples.entities.Tweet;

/**
 * Single window application for Twitter application.
 * Contains methods for performing CRUD operations on users and their tweets.
 */
public interface Twitter
{
    
    

    void addUser(String userId, String name, String password, String relationshipStatus);    

    void savePreference(String userId, Preference preference);

    void addExternalLink(String userId, String linkType, String linkAddress);
    
    void addTweet(String userId, String tweetBody, String device);

    void startFollowing(String userId, String friendUserId);

    void addFollower(String userId, String followerUserId);

    List<Tweet> getAllTweets(String userId);
    
    void close();

}
