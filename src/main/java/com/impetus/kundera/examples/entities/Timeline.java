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
@ColumnFamily(family = "Timeline", keyspace = "Examples")
public class Timeline implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    @Column(name = "userId")
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
    public Timeline()
    {
    }

    /**
     * @param userId
     * @param tweetId
     * @param tweetBody
     */
    public Timeline(String userId, String tweetId, String tweetBody)
    {
        super();
        this.userId = userId;
        this.tweetId = tweetId;
        this.tweetBody = tweetBody;
        timestamp = ((Long)(new java.util.Date().getTime())).toString();
        this.id = userId+tweetId;

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
     * @return the id
     */
    public String getId()
    {
        return id;
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

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

}
