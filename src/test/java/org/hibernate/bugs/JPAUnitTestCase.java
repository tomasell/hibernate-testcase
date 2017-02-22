package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        this.entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @Test
    @TestForIssue(jiraKey = "HHH-11515")
    public void hhh11515Test() throws Exception {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Foo> from = cq.from(Foo.class);
        MapJoin<Foo, Bar, Long> joinMap = from.joinMap("map");
        Path<Bar> key = joinMap.key();
        Path<Long> id = key.get("id");
        cq.select(id);
        entityManager.createQuery(cq).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @After
    public void destroy() {
        this.entityManagerFactory.close();
    }

}
