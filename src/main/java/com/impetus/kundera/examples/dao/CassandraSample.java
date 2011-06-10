/**
 * 
 */
package com.impetus.kundera.examples.dao;

import org.apache.log4j.Logger;

import com.impetus.kundera.examples.entities.User;

/**
 * The Class CassandraSample.
 * 
 * @author impetus
 */
public class CassandraSample
{

    private static final Logger logger = Logger.getLogger(CassandraSample.class);

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args)
    {
        Twissandra twissandra = new Twissandra();
        addUser(twissandra);
        searchAndFollow(twissandra);
        findAFriend(twissandra);
        findFollowers(twissandra);
        tweet(twissandra, "T1");
        tweet(twissandra, "T2");
        findTweets(twissandra);
        findFriendTweets(twissandra);
        System.exit(0);
    }

    /**
     * Adds the user.
     * 
     * @param twissandra
     *            the twissandra
     */
    private static void addUser(Twissandra twissandra)
    {
        twissandra.addUser("imp", "imp");
        twissandra.close();

    }

    /**
     * Search and follow.
     * 
     * @param twissandra
     *            the twissandra
     */
    private static void searchAndFollow(Twissandra twissandra)
    {
        User user = twissandra.findAUser("imp");
        if (user != null)
        {
            twissandra.follow(user.getId(), "f_imp");
            twissandra.follow(user.getId(), "f_imp2");
        }
        else
        {
            logger.warn("There is something wrong! User not found!!!");
        }
        twissandra.close();
    }

    /**
     * Find a friend.
     */
    private static void findAFriend(Twissandra twissandra)
    {
        User user = twissandra.findAUser("imp");
        if (user != null)
        {
            twissandra.findAFriend(user.getId(), user.getUserName());
        }
        else
        {
            logger.warn("There is something wrong! User not found!!!");
        }
        twissandra.close();
    }

    /**
     * Find a friend.
     */
    private static void findFollowers(Twissandra twissandra)
    {
        User user = twissandra.findAUser("imp");
        if (user != null)
        {
            twissandra.findFollowers(user.getId(), user.getUserName());
        }
        else
        {
            logger.warn("There is something wrong! User not found!!!");
        }
        twissandra.close();
    }

    /**
     * Tweet.
     */
    private static void tweet(Twissandra twissandra, String tweetMsg)
    {
        User user = twissandra.findAUser("imp");
        if (user != null)
        {
            twissandra.tweet(user.getUserName(), user.getId(), tweetMsg);
        }
        else
        {
            logger.warn("There is something wrong! User not found!!!");
        }
        twissandra.close();
    }

    /**
     * Find tweets.
     */
    private static void findTweets(Twissandra twissandra)
    {
        User user = twissandra.findAUser("imp");

        if (user != null)
        {
            twissandra.findTweets(user.getUserName(), user.getId());
        }
        else
        {
            logger.warn("There is something wrong! User not found!!!");
        }
        twissandra.close();
    }

    /**
     * Friend tweets.
     */
    private static void findFriendTweets(Twissandra twissandra)
    {
        User user = twissandra.findAUser("imp");

        if (user != null)
        {
            twissandra.findTweetsForFriends(user.getUserName(), user.getId());
        }
        else
        {
            logger.warn("There is something wrong! User not found!!!");
        }
        twissandra.close();
    }
}