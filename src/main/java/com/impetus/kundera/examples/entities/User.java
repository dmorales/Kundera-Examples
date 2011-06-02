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
@ColumnFamily(family = "User", keyspace = "Examples")
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    /**
     * 
     */
    public User()
    {
    }

    /**
     * Constructor using fields.
     * 
     * @param id
     *            userId.
     * @param userName
     *            user name.
     * @param password
     *            password.
     */
    public User(String userName, String password)
    {
        this.id = userName;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

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
     * @return the password
     */
    public String getPassword()
    {
        return password;
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

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

}
