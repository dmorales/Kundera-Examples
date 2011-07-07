/**
 * 
 */
package com.impetus.kundera.examples.dao;

import javax.persistence.EntityManager;

/**
 * The Class TwiBase.
 * 
 * @author impetus
 */
public class TwiBase extends SuperDao implements Twitter
{
    private EntityManager em;

    TwiBase()
    {
        if(em ==null) {
            try
            {
                em = init("hbase");
            }
            catch (Exception e)
            {
            
                System.out.println(e.getMessage());
            }
        }
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.impetus.kundera.examples.dao.Twitter#addUser(java.lang.String,
     * java.lang.String)
     */
    public void addUser(String username, String password)
    {
//        User user = new User(UUID.fromString(username).toString(), username, password);
//        em.persist(user);
        em.persist(null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.impetus.kundera.examples.dao.Twitter#tweet(java.lang.String,
     * java.lang.String)
     */
    public void tweet(String userid, String tweetmsg)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.impetus.kundera.examples.dao.Twitter#follow(java.lang.String,
     * java.lang.String)
     */
    public void startFollowing(String userid, String friend)
    {

    }

    @Override
    public void addTweet(String userid, String tweetmsg, String userName)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public void close() {
		if(em != null) {
			em.close();
		}
		
	}

}
