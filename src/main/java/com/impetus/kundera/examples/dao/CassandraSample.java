/**
 * 
 */
package com.impetus.kundera.examples.dao;

import com.impetus.kundera.examples.entities.User;

// TODO: Auto-generated Javadoc
/**
 * The Class CassandraSample.
 * 
 * @author impetus
 */
public class CassandraSample {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		// Add a user
		// follow a friend.
		// find a friend
		// tweeets
		// find tweets for a user
		// find tweets for a follower.

		Twissandra twissandra = new Twissandra();
		addUser(twissandra);
		// searchAndFollow(twissandra);
		findAFriend(twissandra);
		findFollowers(twissandra);
		// tweet(twissandra, "This is my first tweet");
		// tweet(twissandra, "This is my another tweet");
		// findTweets(twissandra);
		// findFriendTweets(twissandra);
		// System.exit(0);
	}

	/**
	 * Adds the user.
	 * 
	 * @param twissandra
	 *            the twissandra
	 */
	private static void addUser(Twissandra twissandra) {
		twissandra.addUser("imp", "imp");
	}

	/**
	 * Search and follow.
	 * 
	 * @param twissandra
	 *            the twissandra
	 */
	private static void searchAndFollow(Twissandra twissandra) {
		User user = twissandra.findAUser("imp");
		if (user != null) {
			twissandra.follow(user.getId(), "f_imp");
			twissandra.follow(user.getId(), "f_imp2");
		} else {
			System.out.println("There is something wrong! User not found!!!");
		}
	}

	/**
	 * Find a friend.
	 */
	private static void findAFriend(Twissandra twissandra) {
		User user = twissandra.findAUser("impetus");
		if (user != null) {
			twissandra.findAFriend(user.getId(), user.getUserName());
		} else {
			System.out.println("There is something wrong! User not found!!!");
		}
	}

	/**
	 * Find a friend.
	 */
	private static void findFollowers(Twissandra twissandra) {
		User user = twissandra.findAUser("impetus");
		if (user != null) {
			twissandra.findFollowers(user.getId(), user.getUserName());
		} else {
			System.out.println("There is something wrong! User not found!!!");
		}
	}

	/**
	 * Tweet.
	 */
	private static void tweet(Twissandra twissandra, String tweetMsg) {
		User user = twissandra.findAUser("impetus");
		if (user != null) {
			twissandra.tweet(user.getUserName(), user.getId(), tweetMsg);
		} else {
			System.out.println("There is something wrong! User not found!!!");
		}
	}

	/**
	 * Find tweets.
	 */
	private static void findTweets(Twissandra twissandra) {
		User user = twissandra.findAUser("impetus");

		if (user != null) {
			twissandra.findTweets(user.getUserName(), user.getId());
		} else {
			System.out.println("There is something wrong! User not found!!!");
		}
	}

	/**
	 * Friend tweets.
	 */
	private static void findFriendTweets(Twissandra twissandra) {
		User user = twissandra.findAUser("impetus");

		if (user != null) {
			twissandra.findTweetsForFriends(user.getUserName(), user.getId());
		} else {
			System.out.println("There is something wrong! User not found!!!");
		}

	}
}