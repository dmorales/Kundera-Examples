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
@ColumnFamily(family = "Username", keyspace = "Examples")
public class Username implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "userName")
    private String userName;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

}