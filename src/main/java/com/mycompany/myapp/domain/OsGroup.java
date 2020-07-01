package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OsGroup.
 */
@Entity
@Table(name = "os_group")
public class OsGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "osGroups", allowSetters = true)
    private Attribute attributes;

    @ManyToOne
    @JsonIgnoreProperties(value = "osGroups", allowSetters = true)
    private OsSubgroup osSubgroups;

    @ManyToOne
    @JsonIgnoreProperties(value = "osGroups", allowSetters = true)
    private Applet applets;

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

    public OsGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attribute getAttributes() {
        return attributes;
    }

    public OsGroup attributes(Attribute attribute) {
        this.attributes = attribute;
        return this;
    }

    public void setAttributes(Attribute attribute) {
        this.attributes = attribute;
    }

    public OsSubgroup getOsSubgroups() {
        return osSubgroups;
    }

    public OsGroup osSubgroups(OsSubgroup osSubgroup) {
        this.osSubgroups = osSubgroup;
        return this;
    }

    public void setOsSubgroups(OsSubgroup osSubgroup) {
        this.osSubgroups = osSubgroup;
    }

    public Applet getApplets() {
        return applets;
    }

    public OsGroup applets(Applet applet) {
        this.applets = applet;
        return this;
    }

    public void setApplets(Applet applet) {
        this.applets = applet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OsGroup)) {
            return false;
        }
        return id != null && id.equals(((OsGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
