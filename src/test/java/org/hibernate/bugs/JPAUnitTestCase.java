package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.bugs.entity.Order;
import org.hibernate.bugs.entity.Product;
import org.hibernate.testing.TestForIssue;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		this.entityManagerFactory.close();
	}

	@Test
	@TestForIssue(jiraKey = "HHH-11173")
	public void byteCodeEnanchedLazyInitializationFromCache() {
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		Order order = new Order();
		Product product = new Product();
		order.getProducts().add( product );
		entityManager.getTransaction().begin();
		entityManager.persist( product );
		entityManager.persist( order );
		entityManager.getTransaction().commit();
		entityManager = this.entityManagerFactory.createEntityManager();
		order = entityManager.find( Order.class, 1L );
		Assert.assertEquals( 1, order.getProducts().size() );
		entityManager.close();
	}

}
