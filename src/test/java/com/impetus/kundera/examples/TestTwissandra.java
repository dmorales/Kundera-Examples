/*
 * Copyright 2011 Impetus Infotech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.impetus.kundera.examples;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.ConfigurationException;

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
import org.apache.cassandra.thrift.TBinaryProtocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Test case for Cassandra.
 * 
 * @author amresh.singh
 */
public class TestTwissandra extends TwitterTestSuite
{

    /** The client. */
    private Cassandra.Client client;
    
    private static final Log LOG = LogFactory.getLog(TestTwissandra.class);

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        startCassandraServer();
        setUpInternal("twissandra");
        initClient();
        loadData();
    }

    /**
     * Test on execute.
     */
    public void testOnExecute()
    {
        executeSuite();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception
    {
        tearDownInternal();
    }

    /**
     * Start cassandra server.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ConfigurationException
     *             the configuration exception
     */
    private static void startCassandraServer() throws IOException, ConfigurationException
    {

        if (!checkIfServerRunning())
        {
            EmbeddedCassandraService cassandra = new EmbeddedCassandraService();
            cassandra.start();
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

    /**
     * Load data.
     * 
     * @throws ConfigurationException
     *             the configuration exception
     * @throws TException
     *             the t exception
     * @throws NotFoundException
     *             the not found exception
     * @throws InvalidRequestException
     *             the invalid request exception
     */
    private void loadData() throws org.apache.cassandra.config.ConfigurationException, TException, NotFoundException,
            InvalidRequestException
    {

        Class<? extends AbstractReplicationStrategy> simple = SimpleStrategy.class;
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("replication_factor", "1");
        CfDef user_Def = new CfDef("Kundera-Examples", "users");
        user_Def.setComparator_type("UTF8Type");
        user_Def.setColumn_type("Super");
        user_Def.setSubcomparator_type("UTF8Type");
        user_Def.setDefault_validation_class("UTF8Type");
        CfDef preference_Def = new CfDef("Kundera-Examples", "preference");
        CfDef external_Def = new CfDef("Kundera-Examples", "externalLinks");
        List<CfDef> cfDefs = new ArrayList<CfDef>();
        cfDefs.add(user_Def);
        cfDefs.add(preference_Def);
        cfDefs.add(external_Def);

        client.send_system_add_keyspace(new KsDef("Examples", simple.getCanonicalName(), 1, cfDefs));

        KSMetaData metadata = new KSMetaData("Kundera-Examples", simple, ret, 1, standardCFMD("Kundera-Examples", "users", ColumnFamilyType.Super),
                standardCFMD("Kundera-Examples", "preference", ColumnFamilyType.Standard), standardCFMD("Kundera-Examples", "externalLinks",ColumnFamilyType.Standard));
        for (CFMetaData cfm : metadata.cfMetaData().values())
        {
            CFMetaData.map(cfm);
        }

        DatabaseDescriptor.setTableDefinition(metadata, DatabaseDescriptor.getDefsVersion());

    }

    /**
     * Standard cfmd.
     * 
     * @param ksName
     *            the ks name
     * @param cfName
     *            the cf name
     * @return the cF meta data
     */
    private static CFMetaData standardCFMD(String ksName, String cfName, ColumnFamilyType columnFamilyType)
    {

        return new CFMetaData(ksName, cfName, columnFamilyType, UTF8Type.instance, null, "colfamily", Double
                .valueOf("0"), Double.valueOf("0"), Double.valueOf("0"), 0, UTF8Type.instance, 0, 0, 0, 0, 0, Integer
                .valueOf(0), Double.valueOf("0"), new HashMap<ByteBuffer, ColumnDefinition>());
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
        catch (TTransportException ttex)
        {
         
            LOG.error(ttex.getMessage());
        }
        catch (Exception ex)
        {
            LOG.error(ex.getMessage());
        }
    }
}
