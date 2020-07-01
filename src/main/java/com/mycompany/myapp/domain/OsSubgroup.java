package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A OsSubgroup.
 */
@Entity
@Table(name = "os_subgroup")
public class OsSubgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "osSubgroup")
    private Set<Attribute> attributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OsSubgroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public OsSubgroup attributes(Set<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public OsSubgroup addAttributes(Attribute attribute) {
        this.attributes.add(attribute);
        attribute.setOsSubgroup(this);
        return this;
    }

    public OsSubgroup removeAttributes(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.setOsSubgroup(null);
        return this;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OsSubgroup)) {
            return false;
        }
        return id != null && id.equals(((OsSubgroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsSubgroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
