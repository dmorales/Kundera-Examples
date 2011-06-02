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
@ColumnFamily(family = "Followers", keyspace = "Examples")
public class Followers implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    @Column(name="userId")
    private String userId;

    @Column(name = "friendId")
    private String followerId;

    @Column(name = "friendstimestamp")
    private String timestamp;

    /**
     * 
     */
    public Followers()
    {
    }


    /**
     * @param userId
     * @param followerId
     */
    public Followers(String userId, String followerId)
    {
        this.userId = userId;
        this.followerId = followerId;
        timestamp = ((Long) (new java.util.Date().getTime())).toString();
        id = "followed_id"+followerId+userId;
    }


    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the friendId
     */
    public String getFriendId()
    {
        return followerId;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp()
    {
        return timestamp;
    }



    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }


    /**
     * @param followerId the followerId to set
     */
    public void setFollowerId(String followerId)
    {
        this.followerId = followerId;
    }


    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }


    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    
}
