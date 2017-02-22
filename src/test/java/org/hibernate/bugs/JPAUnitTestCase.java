package org.hibernate.bugs;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("h2");
    }

    @After
    public void destroy() {
        this.entityManagerFactory.close();
    }

}
