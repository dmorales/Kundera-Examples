/**
 * 
 */
package com.impetus.kundera.examples.twitter.dao;

import javax.persistence.EntityManager;

import com.impetus.kundera.loader.Configuration;

/**
 * The Class SuperDao.
 * 
 * @author impetus
 */
public class SuperDao
{

    /**
     * Inits the.
     * 
     * @param persistenceUnitName
     *            the persistence unit name
     * @return the entity manager
     * @throws Exception
     *             the exception
     */
    protected EntityManager init(String persistenceUnitName)
    {
        Configuration conf = new Configuration();
        return conf.getEntityManager(persistenceUnitName);

    }
}
