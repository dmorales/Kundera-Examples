package com.impetus.kundera.examples.dao;

import java.util.List;

import com.impetus.kundera.examples.entities.ExternalLink;
import com.impetus.kundera.examples.entities.Preference;
import com.impetus.kundera.examples.entities.Tweet;

/**
 * The Interface Twitter.
 */
public interface Twitter
{

    void close();

    void addUser(String userId, String name, String password, String relationshipStatus);

    void addTweet(String userId, String tweetBody, String device);

    void savePreference(String userId, Preference preference);

    void addExternalLink(String userId, ExternalLink externalLink);

    void startFollowing(String userId, String friendUserId);

    void addFollower(String userId, String followerUserId);

    List<Tweet> getAllTweets(String userId);

}
