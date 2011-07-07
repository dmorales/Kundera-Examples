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

import com.impetus.kundera.examples.dao.Twingo;
import com.impetus.kundera.examples.dao.Twitter;
import com.impetus.kundera.examples.entities.User;

import junit.framework.TestCase;

/**
 * Test case for MongoDB
 * 
 * @author amresh.singh
 */
public class TestMongoDB extends TestCase
{
    User user1;

    User user2;

    Twitter twitter;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        twitter = new Twingo("twingo");

    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        twitter.close();
    }

    public void addUser()
    {
        twitter.addUser("0001", "Amresh", "password1", "married");
        twitter.addUser("0002", "Saurabh", "password2", "single");
    }

    /*
     * public void follow() { User user1 =
     * twitter.getUserByName(this.user1.getUserName()); User user2 =
     * twitter.getUserByName(this.user2.getUserName());
     * 
     * twitter.startFollowing(user2.getId(), user1.getId()); }
     * 
     * public void tweet() { User user1 =
     * twitter.getUserByName(this.user1.getUserName());
     * twitter.addTweet(user1.getId(), "Kundera is so much fun!",
     * user1.getUserName()); }
     */
    public void test()
    {
        addUser();
        // follow();
        // tweet();
    }

}
