package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.bugs.entity.Foo;
import org.hibernate.testing.TestForIssue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using the Java Persistence API.
 */
public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy() {
        this.entityManagerFactory.close();
    }

    @Test
    @TestForIssue(jiraKey = "HHH-11414")
    public void hhh11414Test() throws Exception {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Foo foo = new Foo();
        foo.getMap().put("foo", "test");

        System.out.println(foo);
        System.out.println(foo.getMap().size());

        System.out.println("before merge");

        Foo merge = entityManager.merge(foo);

        System.out.println("after merge");

        System.out.println(merge);
        System.out.println(merge.getMap().size());

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
