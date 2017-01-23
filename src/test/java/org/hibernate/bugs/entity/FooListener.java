package org.hibernate.bugs.entity;

import javax.persistence.PrePersist;

public class FooListener {

    @PrePersist
    public void preUpdate(Foo foo) {
        System.out.println(foo);
        System.out.println(foo.getMap().size());
    }

}
