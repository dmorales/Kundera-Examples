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
package com.impetus.kundera.examples.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.impetus.kundera.examples.entities.ExternalLink;
import com.impetus.kundera.examples.entities.Preference;
import com.impetus.kundera.examples.entities.Tweet;
import com.impetus.kundera.examples.entities.User;

/**
 * Data access object class for mongo implementation of twitter.
 * 
 * @author amresh.singh
 */
public class Twingo extends SuperDao implements Twitter
{
    private EntityManager em;

    public Twingo(String persistenceUnitName)
    {
        if (em == null)
        {
            try
            {
                em = init(persistenceUnitName);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void close()
    {
        if (em != null)
        {
            em.close();
        }
    }

    @Override
    public void addUser(String userId, String name, String password, String relationshipStatus)
    {
        User user = new User(userId, name, password, relationshipStatus);
        em.persist(user);
    }

    @Override
    public void savePreference(String userId, Preference preference)
    {
        User user = em.find(User.class, userId);
        user.setPreference(preference);
        em.persist(user);
    }

    @Override
    public void addExternalLink(String userId, String linkType, String linkAddress)
    {
        User user = em.find(User.class, userId);
        user.addExternalLink(new ExternalLink(linkType, linkAddress));

        em.persist(user);
    }

    @Override
    public void addTweet(String userId, String tweetBody, String device)
    {
        User user = em.find(User.class, userId);
        user.addTweet(new Tweet(tweetBody, device));
        em.persist(user);
    }

    @Override
    public void startFollowing(String userId, String friendUserId)
    {
        User user = em.find(User.class, userId);
        User friend = em.find(User.class, friendUserId);

        user.addFriend(friend);
        em.persist(user);

        friend.addFollower(user);
        em.persist(friend);
    }

    @Override
    public void addFollower(String userId, String followerUserId)
    {
        User user = em.find(User.class, userId);
        User follower = em.find(User.class, followerUserId);

        user.addFollower(follower);
        em.persist(user);

        follower.addFriend(user);
        em.persist(follower);
    }

    @Override
    public List<Tweet> getAllTweets(String userId)
    {
        Query q = em.createQuery("select u from User u where u.userId =:userId");
        q.setParameter("userId", userId);
        List<User> users = q.getResultList();
        if (users == null || users.isEmpty())
        {
            return null;
        }
        else
        {
            return users.get(0).getTweets();
        }
    }

    /*
     * @Override public void startFollowing(String thisUserId, String
     * friendUserId) { Friends friend = new Friends(thisUserId, friendUserId);
     * em.persist(friend);
     * 
     * Followers follower = new Followers(friendUserId, thisUserId);
     * em.persist(follower); }
     * 
     * @Override public void addTweet(String userid, String tweetmsg, String
     * userName) { // Persist Tweet Tweet tweet = new Tweet(userid, tweetmsg);
     * em.persist(tweet);
     * 
     * Userline userLine = new Userline(userid, tweet.getTweetId(), tweetmsg);
     * em.persist(userLine);
     * 
     * // Persist Time Line for this user Timeline timeLine = new
     * Timeline(userid, tweet.getTweetId(), tweetmsg); em.persist(timeLine);
     * 
     * // Persist Time Line for all users who follow this one String followerSql
     * = "Select f from Followers f where f.userId =:userId"; Query q =
     * em.createQuery(followerSql); q.setParameter("userId", userid);
     * List<Followers> followers = q.getResultList(); for (Followers follower :
     * followers) { timeLine = new Timeline(follower.getFriendId(),
     * tweet.getTweetId(), tweetmsg); em.persist(timeLine);
     * 
     * }
     * 
     * }
     * 
     * public Tweet getTweet(String tweetId) { Tweet tweet =
     * em.find(Tweet.class, tweetId); return tweet; }
     * 
     * public List<Tweet> getAllTweets(String userId) { Query q =
     * em.createQuery("Select ul from Userline ul where ul.userId =:userId");
     * q.setParameter("userId", userId);
     * 
     * List<Userline> userLines = q.getResultList();
     * 
     * List<Tweet> allTweets = new ArrayList<Tweet>(); if (userLines != null &&
     * !userLines.isEmpty()) { for (Userline ul : userLines) { Tweet tweet = new
     * Tweet(); tweet.setTweetId(ul.getTweetId()); tweet.setUserId(userId);
     * tweet.setBody(ul.getTweetBody());
     * tweet.setTweetTimeStamp(ul.getTimestamp());
     * 
     * allTweets.add(tweet); }
     * 
     * }
     * 
     * return allTweets; }
     * 
     * public List<Timeline> getTimeLine(String userId) { Query q =
     * em.createQuery("Select tl from Timeline tl where tl.userId =:userId");
     * q.setParameter("userId", userId);
     * 
     * List<Timeline> timeLines = q.getResultList();
     * 
     * return timeLines; }
     * 
     * public User getUserById(String userId) { return em.find(User.class,
     * userId); }
     * 
     * public User getUserByName(String userName) { Query q =
     * em.createQuery("select u from User u where u.userName like :userName");
     * q.setParameter("userName", userName);
     * 
     * List<User> users = q.getResultList(); if (users == null ||
     * users.isEmpty()) { return null; } else { return users.get(0); } }
     */

}
