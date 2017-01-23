package org.hibernate.bugs.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@EntityListeners(FooListener.class)
public class Foo {

    @Id
    private long id;

    @Version
    private long version;

    @ElementCollection
    private Map<String, String> map = new HashMap<>();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, String> getMap() {
        return this.map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
