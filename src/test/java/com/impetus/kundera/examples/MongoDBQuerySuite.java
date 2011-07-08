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

import java.util.List;

import com.impetus.kundera.examples.entities.Tweet;

/**
 * Test suite for MongoDB Query
 * @author amresh.singh
 */
public class MongoDBQuerySuite extends TwitterTestSuite
{
    /**
     * Sets the up internal.
     * @param persistenceUnitName the new up internal
     * @throws Exception the exception
     */
    protected void setUpInternal(String persistenceUnitName) throws Exception
    {
        super.setUpInternal(persistenceUnitName);
    }

   
    /**
     * Tear down internal.
     *
     * @throws Exception the exception
     */
    protected void tearDownInternal() throws Exception
    {
        super.tearDownInternal();
    }

    /**
     * Execute suite.
     */
    public void executeTestSuite()
    {
        addUsers();
        savePreference();
        addExternalLinks();
        addTweets();
        getTweetsByBody();
        getTweetsByDevice();
    }
    /**
     * Gets the tweets by body.
     *
     * @return the tweets by body
     */
    public void getTweetsByBody()
    {
        throw new RuntimeException("Nested searching for MongoDB not provided by Kundera currently" +
        		". feature coming shortly");
    }
    
   /**
    * Gets the tweet by device.
    *
    * @return the tweet by device
    */
   public void getTweetsByDevice()
   {
       throw new RuntimeException("Nested searching for MongoDB not provided by Kundera currently" +
       ". feature coming shortly");
   }     
   

}
