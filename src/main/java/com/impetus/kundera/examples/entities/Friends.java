package com.impetus.kundera.examples.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.impetus.kundera.api.ColumnFamily;

/**
 * @author impetus
 * 
 */
@Entity
@ColumnFamily(family = "Friends", keyspace = "Examples")
public class Friends implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "friendId")
    private String friendId;
    
    @Column(name = "userId")
    private String userId;

    @Column(name = "friendstimestamp")
    private String timestamp;

    /**
     * 
     */
    public Friends()
    {
    }

    /**
     * @param id
     * @param friendId
     */
    public Friends(String userId, String friendId)
    {
        this.userId = userId;
        this.friendId = friendId;
        timestamp = ((Long) (new java.util.Date().getTime())).toString();
        id = userId+friendId;
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
        return friendId;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }


    /**
     * @return the timestamp
     */
    public String getTimestamp()
    {
        return timestamp;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @param friendId the friendId to set
     */
    public void setFriendId(String friendId)
    {
        this.friendId = friendId;
    }
    
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

}
