/**
 * 
 */
package com.impetus.kundera.examples.dao;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.naming.ConfigurationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import lucandra.CassandraUtils;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.KSMetaData;
import org.apache.cassandra.db.ColumnFamilyType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.locator.AbstractReplicationStrategy;
import org.apache.cassandra.locator.SimpleStrategy;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.impetus.kundera.examples.entities.Followers;
import com.impetus.kundera.examples.entities.Friends;
import com.impetus.kundera.examples.entities.Timeline;
import com.impetus.kundera.examples.entities.Tweet;
import com.impetus.kundera.examples.entities.User;
import com.impetus.kundera.examples.entities.Userline;


/**
 * The Class Twissandra.
 *
 * @author impetus
 */

//TODO move all implementations to  declaration in interface.
public class Twissandra extends SuperDao implements Twitter
{

    /** The embedded server cassandra. */
    private static EmbeddedCassandraService cassandra;

    /** The eservice. */
    static ExecutorService                    eservice;

    /** The em. */
    private EntityManager em;
    
    /** The client. */
    private Cassandra.Client client;
    
    private static final Logger logger =  Logger.getLogger(Twissandra.class);
    
    /**
     * Instantiates a new twissandra.
     */
    public Twissandra()
    {
        try
        {
            startCassandraServer();
            startSolandra();
            initClient();
            loadData();
        }
        
        catch (ConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Start solandra.
     */
    private void startSolandra() {


        CassandraUtils.cacheInvalidationInterval = 0; // real-time
        

        try
        {
            // Load solandra specific schema
            CassandraUtils.setStartup();
            CassandraUtils.createCassandraSchema();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            System.exit(0);
        }
    
        
    }
    
    /**
     * Load data.
     *
     * @throws ConfigurationException the configuration exception
     * @throws TException the t exception
     * @throws NotFoundException the not found exception
     * @throws InvalidRequestException the invalid request exception
     */
    private  void loadData() throws org.apache.cassandra.config.ConfigurationException, TException, NotFoundException, InvalidRequestException 
    {
       
        Class<? extends AbstractReplicationStrategy> simple = SimpleStrategy.class;
        Map<String, String> ret = new HashMap<String,String>();
        ret.put("replication_factor", "1");
        CfDef user_Def = new CfDef("Examples", "User");
        CfDef userName_Def = new CfDef("Examples", "Username");
        CfDef friends_Def = new CfDef("Examples", "Friends");
        CfDef followers_Def = new CfDef("Examples", "Followers");
        CfDef tweet_Def = new CfDef("Examples", "Tweet");
        CfDef userLine_Def = new CfDef("Examples", "Userline");
        CfDef timeLine_Def = new CfDef("Examples", "Timeline");
        List<CfDef> cfDefs = new ArrayList<CfDef>();
        cfDefs.add(user_Def);
        cfDefs.add(userName_Def);
        cfDefs.add(friends_Def);
        cfDefs.add(followers_Def);
        cfDefs.add(tweet_Def);
        cfDefs.add(userLine_Def);
        cfDefs.add(timeLine_Def);
        
        client.send_system_add_keyspace(new KsDef("Examples", simple.getCanonicalName(),1, cfDefs));
        
//        KsDef def = client.describe_keyspaces().get(0);
//        System.out.println(def.name + def.getCf_defsSize());
        
        KSMetaData metadata = new KSMetaData("Examples", simple, ret,1, standardCFMD("Examples", "User"),
                                                                           standardCFMD("Examples", "Username"),
                                                                           standardCFMD("Examples", "Friends"),
                                                                           standardCFMD("Examples", "Followers"),
                                                                           standardCFMD("Examples", "Tweet"),
                                                                           standardCFMD("Examples", "Userline"),
                                                                           standardCFMD("Examples", "Timeline"));
        for (CFMetaData cfm : metadata.cfMetaData().values())
        {
            CFMetaData.map(cfm);
        }
          
        DatabaseDescriptor.setTableDefinition(metadata, DatabaseDescriptor.getDefsVersion());
    }
    
    /**
     * Standard cfmd.
     *
     * @param ksName the ks name
     * @param cfName the cf name
     * @return the cF meta data
     */
    private static CFMetaData standardCFMD(String ksName, String cfName)
    {
        /**
         * String tableName, String cfName, ColumnFamilyType cfType,
         *  AbstractType comparator, AbstractType subcolumnComparator,
         *  String comment, double rowCacheSize, double keyCacheSize, 
         *  double readRepairChance, int gcGraceSeconds, AbstractType defaultValidator, 
         *  int minCompactionThreshold, int maxCompactionThreshold, int rowCacheSavePeriodInSeconds, 
         *  int keyCacheSavePeriodInSeconds, int memTime, Integer memSize, Double memOps, 
         *  Map<ByteBuffer, ColumnDefinition> column_metadata
         */
        return new CFMetaData(ksName, cfName, ColumnFamilyType.Standard, UTF8Type.instance, null,"colfamily",
                              Double.valueOf("0"),Double.valueOf("0"),Double.valueOf("0"),0,
                              UTF8Type.instance,0,0,0,0,0,Integer.valueOf(0),Double.valueOf("0"),new HashMap<ByteBuffer, ColumnDefinition>());
    }
    
    /**
     * Start cassandra server.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ConfigurationException the configuration exception
     */
    private static void startCassandraServer() throws IOException, ConfigurationException
    {
    
        if (!checkIfServerRunning())
        {
            cassandra = new EmbeddedCassandraService();
            cassandra.start();
        }
    }

    /**
     * Inits the client.
     */
    private void initClient()
    {
        TSocket socket = new TSocket("127.0.0.1", 9165);
        TTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new Cassandra.Client(protocol);

        try
        {
            socket.open();
        }
        catch (TTransportException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(Exception ex) 
        {
            ex.printStackTrace();
        }
        
       
    
    }
   
    /**
     * Check if server running.
     *
     * @return true, if successful
     */
    private static boolean checkIfServerRunning()
    {
        try
        {
            Socket socket = new Socket("127.0.0.1", 9165);
            return socket.getInetAddress() != null;
        } 
        catch (UnknownHostException e)
        {
            return false;
        }
        catch (IOException e)
        {
            return false;
        }
    }
    
    /* (non-Javadoc)
     * @see com.impetus.kundera.examples.dao.Twitter#addUser(java.lang.String, java.lang.String)
     */
    public void addUser(String username, String password)
    {
        em = init("cassandra");
        User user = new User(username, password);
        user.setId(username);
        em.persist(user);   
     }
    
    

    
    /* (non-Javadoc)
     * @see com.impetus.kundera.examples.dao.Twitter#tweet(java.lang.String, java.lang.String)
     */
    public void addTweet(String userName, String userId, String tweetMsg)
    {
        em = init("cassandra");
        Tweet tweet = new Tweet(userId, tweetMsg);
        em.persist(tweet);
        Userline userLine = new Userline(userId, tweet.getTweetId(), tweetMsg);
        em.persist(userLine);
        Timeline timeLine = new Timeline(userId, tweet.getTweetId(), tweetMsg);
        em.persist(timeLine);
        logger.info("Adding tweet for User::" + userName);
        //Get all your followers and add tweet to their entry too.
        String followerSql = "Select f from Followers f where f.userId =:userId";
        Query q = em.createQuery(followerSql);
        q.setParameter("userId", userId);
        List<Followers> followers = q.getResultList();
        for (Followers follower: followers)
        {
            logger.info("Adding tweet for followers::" + follower.getFriendId());
            timeLine = new Timeline(follower.getFriendId(), tweet.getTweetId(), tweetMsg);
            em.persist(timeLine);
        }
        
    }

    
    /* (non-Javadoc)
     * @see com.impetus.kundera.examples.dao.Twitter#follow(java.lang.String, java.lang.String)
     */
    public void startFollowing(String userid, String friend)
    {
        em = init("cassandra");
        Friends friends = new Friends(userid, friend);
        em.persist(friends);
        Followers follower = new Followers(userid, friend);
        em.persist(follower);
    }
    
    /**
     * Find a friend.
     *
     * @param userId the user id
     * @param userName the user name
     */
    public void findAFriend(String userId, String userName)
    {
        em = init("cassandra");
        String sql = "select a from Friends a where a.userId  =:userId";   
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        
        logger.info("finding friend for::" + userName);
        
        List<Friends> friendLst = q.getResultList();
        for(Friends friend : friendLst)
        {
            logger.info("Found friend::" + friend.getFriendId());    
        }
        
    }
    
    /**
     * Find followers.
     *
     * @param userId the user id
     * @param userName the user name
     */
    public void findFollowers(String userId, String userName)
    {
        em = init("cassandra");
        List<Followers> followers = getFollowers(userId, userName);
        for(Followers follower : followers)
        {
            logger.info("Found followers::" + follower.getFriendId());    
        }
        
    }

    
    
    /**
     * Find a user.
     *
     * @param userName the user name
     * @return the user
     */
    public User findAUser(String userName)
    {
        em = init("cassandra");
        String usrSql = "Select a from User a where a.userName  =:userName";
        Query q = em.createQuery(usrSql);
        q.setParameter("userName", userName);
        List<User> userLst = q.getResultList();
        return userLst.isEmpty()?null:userLst.get(0);
        
    }

    /**
     * Find tweets.
     *
     * @param userName the user name
     * @param userId the user id
     */
    public void findTweets(String userName, String userId)
    {
        em = init("cassandra");
        logger.info("Finding tweets for ::" + userName);
      String tweetSql = "Select t from Timeline t where t.userId =:userId";
      Query q = em.createQuery(tweetSql);
      q.setParameter("userId", userId);
      List<Timeline> tweets = q.getResultList();
      for(Timeline tweet : tweets)
      {
          logger.info(tweet.getTweetBody());
      }
    }

    /**
     * Find tweets for friends.
     *
     * @param userName the user name
     * @param userId the user id
     */
    public void findTweetsForFriends(String userName, String userId)
    {
        em = init("cassandra");
        logger.info("Finding tweets for followers of ::" + userName);
      List<Followers> followers = getFollowers(userId, userName);
      String tweetSql = "Select t from Timeline t where t.userId =:userId";
//      Query q = em.createQuery(tweetSql);
      for(Followers follower : followers)
      {
          Query q = em.createQuery(tweetSql);
          q.setParameter("userId", follower.getFriendId());
         List<Timeline> tweets = q.getResultList();
      for(Timeline tweet : tweets)
      {
          logger.info(tweet.getTweetBody());
      }
      }
    }
    
    /**
     * Gets the followers.
     *
     * @param userId the user id
     * @param userName the user name
     * @return the followers
     */
    private List<Followers> getFollowers(String userId, String userName)
    {
        String sql = "select a from Followers a where a.userId  =:userId";   
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        List<Followers> followers = q.getResultList();
        return followers;
    }

	
	/* (non-Javadoc)
	 * @see com.impetus.kundera.examples.dao.Twitter#close()
	 */
	@Override
	public void close() {
		if(em != null) {
			em.close();
		}
		
	}
   
}
