/*-
 * Copyright 2017-2018 Axians SAIV S.p.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
-*/
package org.hibernate.bugs;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "groups")
public abstract class AbstractGroup<H extends AbstractHost<H, G, R>, G extends AbstractGroup<H, G, R>, R extends AbstractGroupRelationship<H, G, R>> {

    @Id
    @Column(name = "groupid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private Set<R> hostsRelationship;

    @ManyToOne
    @JoinColumn(name = "parent_hostid", referencedColumnName = "groupid")
    private G parent;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<R> getHostsRelationship() {
        return this.hostsRelationship;
    }

    public void setHostsRelationship(Set<R> hostsRelationship) {
        this.hostsRelationship = hostsRelationship;
    }

    public G getParent() {
        return this.parent;
    }

    public void setParent(G parent) {
        this.parent = parent;
    }

}
