/**
 * 
 */
package com.impetus.kundera.examples.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.impetus.kundera.api.ColumnFamily;

/**
 * @author impetus
 * 
 */
@Entity
@ColumnFamily(family = "Userline", keyspace = "Examples")
public class Userline implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String userId;

    @Column(name = "tweetId")
    private String tweetId;

    @Column(name = "tweetbody")
    private String tweetBody;

    @Column(name = "usertimestamp")
    private String timestamp;

    
    /**
     * 
     */
    public Userline()
    {
    }

    /**
     * @param userId
     * @param tweetId
     * @param tweetBody
     */
    public Userline(String userId, String tweetId, String tweetBody)
    {
        this.userId = userId;
        this.tweetId = tweetId;
        this.tweetBody = tweetBody;
        timestamp = ((Long)(new java.util.Date().getTime())).toString();
        this.id = userId+tweetId;

    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @return the tweetId
     */
    public String getTweetId()
    {
        return tweetId;
    }

    /**
     * @return the tweetBody
     */
    public String getTweetBody()
    {
        return tweetBody;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp()
    {
        return timestamp;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @param tweetId the tweetId to set
     */
    public void setTweetId(String tweetId)
    {
        this.tweetId = tweetId;
    }

    /**
     * @param tweetBody the tweetBody to set
     */
    public void setTweetBody(String tweetBody)
    {
        this.tweetBody = tweetBody;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

}
