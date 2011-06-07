package com.impetus.kundera.examples.dao;


/**
 * The Interface Twitter.
 */
public interface Twitter {
	
	void close();
	
    /**
     * Adds the user.
     *
     * @param username the username
     * @param password the password
     */
    void addUser(String username, String password);

    /**
     * Tweet.
     *
     * @param userid the userid
     * @param tweetmsg the tweetmsg
     */
    void tweet(String userid, String tweetmsg, String userName);

    /**
     * Follow.
     *
     * @param userid the userid
     * @param friend the friend
     */
    void follow(String userid, String friend); 

}
