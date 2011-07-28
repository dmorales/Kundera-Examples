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
package com.impetus.kundera.examples.twitter;

import com.impetus.kundera.examples.twitter.dao.Twitter;
import com.impetus.kundera.examples.twitter.query.HBaseQuerySuite;

/**
 * Test case for Twitter like application on HBase
 * @author amresh.singh
 */
public class TwibaseTest extends HBaseQuerySuite
{
    /** The user id1. */
    String userId1;

    /** The user id2. */
    String userId2;

    /** The twitter. */
    Twitter twitter;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        setUpInternal("twibase");
    }

    
    /**
     * Test on execute.
     */
    public void testOnExecute()
    {
       executeTestSuite();
    }
    
    public void testOnQuery() {
        executeQuerySuite();
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception
    {
        tearDownInternal();
    }
}
