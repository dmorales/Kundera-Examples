package com.impetus.kundera.examples.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.impetus.kundera.api.ColumnFamily;

@Entity
@ColumnFamily(family = "Tweet", keyspace = "Examples")
public class Tweet implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String tweetId;

    @Column(name = "userid")
    private String userId;

    @Column(name = "tweetbody")
    private String body;

    @Column(name = "tstamp")
    private String tweetTimeStamp;

    
    /**
     * 
     */
    public Tweet()
    {
    }

    /**
     * @param tweetId
     * @param userId
     * @param body
     */
    public Tweet(String userId, String body)
    {
        
        this.userId = userId;
        this.body = body;
        tweetTimeStamp = ((Long)(new java.util.Date().getTime())).toString();
        this.tweetId = userId + tweetTimeStamp;
    }

    /**
     * @return the tweetId
     */
    public String getTweetId()
    {
        return tweetId;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @return the body
     */
    public String getBody()
    {
        return body;
    }

    /**
     * @return the tweetTimeStamp
     */
    public String getTweetTimeStamp()
    {
        return tweetTimeStamp ;
    }

    /**
     * @param tweetId the tweetId to set
     */
    public void setTweetId(String tweetId)
    {
        this.tweetId = tweetId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * @param tweetTimeStamp the tweetTimeStamp to set
     */
    public void setTweetTimeStamp(String tweetTimeStamp)
    {
        this.tweetTimeStamp = tweetTimeStamp;
    }

}
