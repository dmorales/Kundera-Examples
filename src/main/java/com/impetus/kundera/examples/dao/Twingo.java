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
package com.impetus.kundera.examples.dao;

import javax.persistence.EntityManager;

import com.impetus.kundera.examples.entities.User;
import com.impetus.kundera.examples.entities.UserName;


/**
 * Data access object class for mongo implementation of twitter.
 * @author amresh.singh
 */
public class Twingo extends SuperDao implements Twitter {
	private EntityManager em;
	
	public Twingo(String persistenceUnitName) {
		if(em == null) {
			try {
				em = init(persistenceUnitName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void addUser(String username, String password) {
		User user = new User(username, password);  	
    	
        em.persist(user);     
        
        //Persist UserName entity (inverted index)
        UserName un = new UserName(user.getUserName(), user.getId());
        em.persist(un);
		
	}

	
	@Override
	public void tweet(String userid, String tweetmsg, String userName) {
		
		
	}

	
	@Override
	public void follow(String userid, String friend) {
		
		
	}
	
	

}
