package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.bugs.entity.Bar;
import org.hibernate.bugs.entity.Foo;
import org.hibernate.testing.TestForIssue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("sqlserver");
    }

    @Test
    @TestForIssue(jiraKey = "HHH-11513")
    public void groupByEntityTest() throws Exception {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Foo> foo = cq.from(Foo.class);
        Path<Bar> bar = foo.get("bar");
        cq.multiselect(bar, cb.count(foo));
        cq.groupBy(bar);
        entityManager.createQuery(cq).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    @TestForIssue(jiraKey = "HHH-11513")
    public void groupByEntityInMapKeyTest() throws Exception {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Foo> foo = cq.from(Foo.class);
        MapJoin<Foo, Bar, Integer> map = foo.joinMap("map");
        Path<Bar> bar = map.key();
        cq.multiselect(bar, cb.count(foo));
        cq.groupBy(bar);
        entityManager.createQuery(cq).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @After
    public void destroy() {
        this.entityManagerFactory.close();
    }

}
