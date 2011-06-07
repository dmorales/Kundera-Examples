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
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.locator.AbstractReplicationStrategy;
import org.apache.cassandra.locator.SimpleStrategy;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.NotFoundException;
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
import com.impetus.kundera.examples.entities.UserName;


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

    static ExecutorService                    eservice;

    private EntityManager em;
    
    private Cassandra.Client client;
    
    public Twissandra()
    {
        try
        {
            startCassandraServer();
            //startSolandra();
            initClient();
            loadData();
           if(em ==null) {
               em = init("cassandra");
           }
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
    
   /* private void startSolandra() {


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
    
        
    }*/
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
        return new CFMetaData(ksName, cfName, ColumnFamilyType.Standard, BytesType.instance, null,"colfamily",
                              Double.valueOf("0"),Double.valueOf("0"),Double.valueOf("0"),0,
                              BytesType.instance,0,0,0,0,0,Integer.valueOf(0),Double.valueOf("0"),new HashMap<ByteBuffer, ColumnDefinition>());
    }
    /**
     * Start cassandra server.
     * @throws ConfigurationException 
     *
     * @throws Exception the exception
     */
    private static void startCassandraServer() throws IOException, ConfigurationException
    {
    
        if (!checkIfServerRunning())
        {
            cassandra = new EmbeddedCassandraService();
            cassandra.start();
        }
    }

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
        User user = new User(username, password);
        em.persist(user);   
        UserName userName = new UserName();
        userName.setId(user.getId());
        userName.setUserName(username);
        em.persist(userName);
//        User found = em.find(User.class,userid);
////        System.out.println(found !=null);
//        System.out.println("Added a new User: " + found.getUserName() + found.getPassword());
//        System.out.println(em.find(Username.class, userid) != null);
     }
    
    

    
    /* (non-Javadoc)
     * @see com.impetus.kundera.examples.dao.Twitter#tweet(java.lang.String, java.lang.String)
     */
    public void tweet(String userName, String userId, String tweetMsg)
    {
//        String tweetId = "tweet_"+userId;
        Tweet tweet = new Tweet(userId, tweetMsg);
        em.persist(tweet);
        Tweet found = em.find(Tweet.class, tweet.getTweetId());
//        System.out.println(found != null);
//        System.out.println("New tweet added: " + found.getBody());
        Userline userLine = new Userline(userId, tweet.getTweetId(), tweetMsg);
        em.persist(userLine);
        Timeline timeLine = new Timeline(userId, tweet.getTweetId(), tweetMsg);
        em.persist(timeLine);
        System.out.println("Adding tweet for User::" + userName);
        //Get all your followers and add tweet to their entry too.
        String followerSql = "Select f from Followers f where f.userId =:userId";
        Query q = em.createQuery(followerSql);
        q.setParameter("userId", userId);
        List<Followers> followers = q.getResultList();
        for (Followers follower: followers)
        {
            System.out.println("Adding tweet for followers::" + follower.getFriendId());
            timeLine = new Timeline(follower.getFriendId(), tweet.getTweetId(), tweetMsg);
            em.persist(timeLine);
            Timeline tm = em.find(Timeline.class,(follower.getFriendId()+tweet.getTweetId()));
            System.out.println(tm.getTweetBody());
            System.out.println(tm.getUserId());
            
        }
        
    }

    
    /* (non-Javadoc)
     * @see com.impetus.kundera.examples.dao.Twitter#follow(java.lang.String, java.lang.String)
     */
    public void follow(String userid, String friend)
    {
        Friends friends = new Friends(userid, friend);
        em.persist(friends);
        Followers follower = new Followers(userid, friend);
        em.persist(follower);
    }
    
    public void findAFriend(String userId, String userName)
    {
        String sql = "select a from Friends a where a.userId  =:userId";   
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        
        System.out.println("finding friend for::" + userName);
        
        List<Friends> friendLst = q.getResultList();
        for(Friends friend : friendLst)
        {
            System.out.println("Found friend::" + friend.getFriendId());    
        }
        
    }
    
    public void findFollowers(String userId, String userName)
    {
        List<Followers> followers = getFollowers(userId, userName);
        for(Followers follower : followers)
        {
            System.out.println("Found followers::" + follower.getFriendId());    
        }
        
    }

    
    
    public User findAUser(String userName)
    {
        String usrSql = "Select a from User a where a.userName  =:userName";
        Query q = em.createQuery(usrSql);
        q.setParameter("userName", userName);
        List<User> userLst = q.getResultList();
        return userLst.isEmpty()?null:userLst.get(0);
        
    }

    public void findTweets(String userName, String userId)
    {
      System.out.println("Finding tweets for ::" + userName);
      String tweetSql = "Select t from Timeline t where t.userId like :userId";
      Query q = em.createQuery(tweetSql);
      q.setParameter("userId", userId);
      List<Timeline> tweets = q.getResultList();
      System.out.println(tweets.isEmpty());
      for(Timeline tweet : tweets)
      {
          System.out.println(tweet.getTweetBody());
      }
    }

    public void findTweetsForFriends(String userName, String userId)
    {
      System.out.println("Finding tweets for followers of ::" + userName);
      List<Followers> followers = getFollowers(userId, userName);
      String tweetSql = "Select t from Timeline t where t.userId =:userId";
//      Query q = em.createQuery(tweetSql);
      for(Followers follower : followers)
      {
          Query q = em.createQuery(tweetSql);
          System.out.println("user is ::" + follower.getFriendId());
          q.setParameter("userId", follower.getFriendId());
         List<Timeline> tweets = q.getResultList();
      for(Timeline tweet : tweets)
      {
          System.out.println(tweet.getTweetBody());
      }
      }
    }
    
    private List<Followers> getFollowers(String userId, String userName)
    {
        String sql = "select a from Followers a where a.userId  =:userId";   
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        List<Followers> followers = q.getResultList();
        System.out.println("followers for user:" + userName);
        return followers;
    }

	
	@Override
	public void close() {
		if(em != null) {
			em.close();
		}
		
	}
   
}
